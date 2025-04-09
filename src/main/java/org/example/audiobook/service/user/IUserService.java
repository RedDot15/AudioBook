package org.example.audiobook.service.user;

import org.example.audiobook.dto.UserDTO;
import org.example.audiobook.entity.User;

public interface IUserService {
    User createUser(UserDTO userDTO) throws Exception;
    String login(String email, String password) throws Exception;
}
