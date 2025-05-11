package org.example.audiobook.service.authentication;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.example.audiobook.entity.User;
import org.example.audiobook.repository.UserRepository;
import org.example.audiobook.repository.authentication.InvalidatedTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Transactional
@Service
public class TokenService {
	NimbusJwtDecoder nimbusJwtDecoder;
	UserRepository userRepository;
	InvalidatedTokenRepository invalidatedTokenRepository;

	@NonFinal
	@Value("${jwt.signer-key}")
	String SIGNER_KEY;

	@NonFinal
	@Value("${jwt.valid-duration}")
	Long VALID_DURATION;

	@NonFinal
	@Value("${jwt.refreshable-duration}")
	Long REFRESHABLE_DURATION;

	public String generateToken(User user, Boolean isRefreshToken, String jti) {
		// Define Header
		JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
		// Calculate expiration time based on token type
		Long duration = isRefreshToken ? REFRESHABLE_DURATION : VALID_DURATION;
		Date expirationTime = Date.from(Instant.now().plus(duration, ChronoUnit.SECONDS));
		// Define Body: ClaimSet
		JWTClaimsSet.Builder claimsBuilder = new JWTClaimsSet.Builder()
				.subject(user.getId().toString())
				.issuer("reddot15.com")
				.issueTime(new Date())
				.expirationTime(expirationTime)
				.jwtID(isRefreshToken ? jti : null);

		if (!isRefreshToken) {
			claimsBuilder
					.claim("rid", jti)
					.claim("scope", user.getRole())
					.claim("uid", user.getId());
		}

		JWTClaimsSet jwtClaimsSet = claimsBuilder.build();
		// Define Body: Payload
		Payload payload = new Payload(jwtClaimsSet.toJSONObject());
		// Define JWSObject
		JWSObject jwsObject = new JWSObject(header, payload);
		// Sign JWSObject & Return
		try {
			jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
			return jwsObject.serialize();
		} catch (JOSEException e) {
			log.error("Cannot create token", e);
			throw new RuntimeException(e);
		}
	}

	public Jwt verifyToken(String token, Boolean isRefreshToken) {
		try {
			// Decode jwt (function include integrity verify & expiry verify)
			Jwt jwt = nimbusJwtDecoder.decode(token);
			// Validate token based on type
			String tokenId = isRefreshToken ? jwt.getClaim("jti") : jwt.getClaim("rid");
			if (Objects.isNull(tokenId) || invalidatedTokenRepository.existsById(tokenId)) {
				throw new JwtException("Invalid token");
			}
			UUID userId = UUID.fromString(jwt.getSubject());
			if (userRepository.findById(userId).isEmpty()) {
				throw new JwtException("Invalid user");
			}
			// Return jwt
			return jwt;
		} catch (JwtException e) {
			log.error("JWT decoding failed: {}", e.getMessage());
			throw e; // Re-throw to let Spring Security handle it
		}
	}

}
