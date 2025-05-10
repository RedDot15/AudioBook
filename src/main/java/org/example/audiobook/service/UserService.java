package org.example.audiobook.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.audiobook.dto.request.user.*;
import org.example.audiobook.dto.response.UserResponse;
import org.example.audiobook.entity.User;
import org.example.audiobook.exception.ErrorCode;
import org.example.audiobook.exception.custom.AppException;
import org.example.audiobook.mapper.UserMapper;
import org.example.audiobook.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@Service
public class UserService {
	UserRepository userRepository;
	UserMapper userMapper;
	PasswordEncoder passwordEncoder;

	@PreAuthorize("hasRole('ADMIN')")
	public Set<UserResponse> getAll() {
		return userRepository.findAll().stream().map(userMapper::toUserResponse).collect(Collectors.toSet());
	}

	public UserResponse getMyInfo() {
		String userIdStr = SecurityContextHolder.getContext().getAuthentication().getName();
		UUID userId = UUID.fromString(userIdStr);

		User user = userRepository
				.findById(userId)
				.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

		// Return
		return userMapper.toUserResponse(user);
	}

	@PreAuthorize("hasRole('ADMIN')")
	public UserResponse add(UserCreateRequest userCreateRequest) {
		// Map to entity
		User newUser = userMapper.toUser(userCreateRequest);
		// Encode password
		newUser.setHashedPassword(passwordEncoder.encode(userCreateRequest.getPassword()));
		try {
			// Add & Return
			return userMapper.toUserResponse(userRepository.save(newUser));
		} catch (DataIntegrityViolationException exception) {
			throw new AppException(ErrorCode.USER_DUPLICATE);
		}
	}

//	@PreAuthorize("hasRole('ADMIN')")
//	public UserResponse update(UserUpdateRequest userUpdateRequest) {
//		// Get old
//		User foundUser = userRepository
//				.findById(userUpdateRequest.getId())
//				.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
//		// Update
//		userMapper.updateUser(foundUser, userUpdateRequest);
//		// Update password
//		if (userUpdateRequest.getPassword() != null)
//			foundUser.setHashedPassword(passwordEncoder.encode(userUpdateRequest.getPassword()));
//		try {
//			// Add & Return
//			return userMapper.toUserResponse(userRepository.save(foundUser));
//		} catch (DataIntegrityViolationException exception) {
//			throw new AppException(ErrorCode.USER_DUPLICATE);
//		}
//	}

	@PreAuthorize("hasRole('ADMIN') or hasAuthority('DELETE_USER')")
	public UUID delete(UUID id) {
		// Fetch & Not found/deleted exception
		User user =
				userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
		// Delete
		userRepository.delete(user);
		// Return ID
		return id;
	}

	public UserResponse register(UserRegisterRequest userRegisterRequest) {
		// Mapping userRequest -> userEntity
		User user = userMapper.toUser(userRegisterRequest);
		// Encode password
		user.setHashedPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
		try {
			// Save & Return
			return userMapper.toUserResponse(userRepository.save(user));
		} catch (DataIntegrityViolationException exception) {
			throw new AppException(ErrorCode.USER_DUPLICATE);
		}
	}

	@PreAuthorize(
			"(#userChangeInfoRequest.id == authentication.principal.claims['uid'])")
	public UserResponse updateInformation(UserChangeInfoRequest userChangeInfoRequest) {
		// Get old
		User foundUser = userRepository
				.findById(userChangeInfoRequest.getId())
				.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
		// Update
		userMapper.updateUser(foundUser, userChangeInfoRequest);
		try {
			// Save & Return
			return userMapper.toUserResponse(userRepository.save(foundUser));
		} catch (DataIntegrityViolationException exception) {
			throw new AppException(ErrorCode.USER_DUPLICATE);
		}
	}

	@PreAuthorize(
			"(#userChangePasswordRequest.id == authentication.principal.claims['uid'])")
	public UserResponse changePassword(UserChangePasswordRequest userChangePasswordRequest) {
		// Get old
		User foundUser =
				userRepository.findById(userChangePasswordRequest.getId()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
		// Old password unmatched exception
		if (!passwordEncoder.matches(userChangePasswordRequest.getOldPassword(), foundUser.getHashedPassword()))
			throw new AppException(ErrorCode.WRONG_PASSWORD);
		// Change password
		foundUser.setHashedPassword(passwordEncoder.encode(userChangePasswordRequest.getPassword()));
		// Save & Return
		return userMapper.toUserResponse(userRepository.save(foundUser));
	}

	public User updateUser(UUID id, UserUpdateRequest userUpdateRequest){
		User userResult = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
		if(userUpdateRequest.getDisplayName() != null && !userUpdateRequest.getDisplayName().isEmpty()){
			userResult.setDisplayName(userUpdateRequest.getDisplayName());
		}
		if(userUpdateRequest.getUsername() != null && !userUpdateRequest.getUsername().isEmpty()){
			userResult.setUsername(userUpdateRequest.getUsername());
		}
		if(userUpdateRequest.getDateOfBirth() != null && userUpdateRequest.getDateOfBirth().isBefore(LocalDate.now())){
			userResult.setDateOfBirth(userUpdateRequest.getDateOfBirth());
		}
		if(userUpdateRequest.getImageUrl() != null && !userUpdateRequest.getImageUrl().isEmpty()){
			userResult.setImageUrl(userUpdateRequest.getImageUrl());
		}
		return  userRepository.save(userResult);
	}

	public User getUser(UUID id){
		return  userRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND));
	}
}
