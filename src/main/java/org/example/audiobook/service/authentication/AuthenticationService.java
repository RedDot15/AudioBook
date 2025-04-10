package org.example.audiobook.service.authentication;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.example.audiobook.dto.request.authentication.AuthenticationRequest;
import org.example.audiobook.dto.request.authentication.RefreshRequest;
import org.example.audiobook.dto.response.authentication.AuthenticationResponse;
import org.example.audiobook.dto.response.authentication.RefreshResponse;
import org.example.audiobook.entity.User;
import org.example.audiobook.entity.authentication.InvalidatedTokenEntity;
import org.example.audiobook.exception.ErrorCode;
import org.example.audiobook.exception.custom.AppException;
import org.example.audiobook.repository.UserRepository;
import org.example.audiobook.repository.authentication.InvalidatedTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Transactional
@Service
public class AuthenticationService {
	PasswordEncoder passwordEncoder;
	UserRepository userRepository;
	InvalidatedTokenRepository invalidatedTokenRepository;
	TokenService tokenService;

	@NonFinal
	@Value("${jwt.refreshable-duration}")
	Long REFRESHABLE_DURATION;

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		// Fetch
		User user = userRepository
				.findByEmail(request.getEmail())
				.orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
		// Authenticate
		boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getHashedPassword());
		if (!authenticated) throw new AppException(ErrorCode.UNAUTHENTICATED);
		// Generate token
		String uuid = UUID.randomUUID().toString();
		String refreshToken = tokenService.generateToken(user, true, uuid);
		String accessToken = tokenService.generateToken(user, false, uuid);
		// Return token
		return AuthenticationResponse.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.authenticated(true)
				.build();
	}

	public RefreshResponse refresh(RefreshRequest request) {
		// Verify token
		try {
			Jwt jwt = tokenService.verifyToken(request.getRefreshToken(), true);
			// Get token information
			User user = userRepository
					.findByUsername(jwt.getSubject())
					.orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
			String jti = jwt.getClaim("jti");
			Date expiryTime = Date.from(jwt.getClaim("exp"));
			// Build & Save invalid token
			InvalidatedTokenEntity invalidatedTokenEntity = InvalidatedTokenEntity.builder()
					.id(jti)
					.expiryTime(expiryTime)
					.build();

			invalidatedTokenRepository.save(invalidatedTokenEntity);
			// Generate new token
			String uuid = UUID.randomUUID().toString();
			String refreshToken = tokenService.generateToken(user, true, uuid);
			String accessToken = tokenService.generateToken(user, false, uuid);
			// Return token
			return RefreshResponse.builder()
					.accessToken(accessToken)
					.refreshToken(refreshToken)
					.build();
		} catch (JwtException e) {
			throw new AppException(ErrorCode.UNAUTHENTICATED);
		}
	}

	public void logout() {
		// Get Jwt token from Context
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Jwt jwt = (Jwt) authentication.getPrincipal();
		// Get token information
		String jti = jwt.getClaim("rid");
		Date expiryTime = Date.from(Instant.from(jwt.getClaim("iat")).plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS));
		// Build & Save invalid token
		InvalidatedTokenEntity invalidatedTokenEntity =
				InvalidatedTokenEntity.builder().id(jti).expiryTime(expiryTime).build();

		invalidatedTokenRepository.save(invalidatedTokenEntity);
	}
}
