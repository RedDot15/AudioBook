package org.example.audiobook.mapper;

import org.example.audiobook.dto.request.user.UserChangeInfoRequest;
import org.example.audiobook.dto.request.user.UserCreateRequest;
import org.example.audiobook.dto.request.user.UserRegisterRequest;
import org.example.audiobook.dto.request.user.UserUpdateRequest;
import org.example.audiobook.dto.response.UserResponse;
import org.example.audiobook.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
	// Add
    @Mapping(target = "hashedPassword", ignore = true)
	User toUser(UserCreateRequest userCreateRequest);

    @Mapping(target = "hashedPassword", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toUser(UserRegisterRequest userRegisterRequest);

	// Update
    @Mapping(target = "hashedPassword", ignore = true)
	void updateUser(@MappingTarget User user, UserUpdateRequest userUpdateRequest);

    @Mapping(target = "hashedPassword", ignore = true)
    @Mapping(target = "role", ignore = true)
	void updateUser(@MappingTarget User user, UserChangeInfoRequest userChangeInfoRequest);

	// Response
	UserResponse toUserResponse(User user);
}
