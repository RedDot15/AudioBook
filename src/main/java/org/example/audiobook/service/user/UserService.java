package org.example.audiobook.service.user;


import lombok.RequiredArgsConstructor;
import org.example.audiobook.component.JwtTokenUtils;
import org.example.audiobook.dto.UserDTO;
import org.example.audiobook.entity.User;
import org.example.audiobook.exception.DataNotFoundException;
import org.example.audiobook.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    @Override
    public User createUser(UserDTO userDTO) throws Exception {
        String email = userDTO.getEmail();
        if(userRepository.existsByEmail(email)) {
            throw  new DataIntegrityViolationException("Email already exists");
        }
        String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
        User newUser = User.builder()
                .username(userDTO.getUsername())
                .email(email)
                .hashedPassword(hashedPassword)
                .dateOfBirth(userDTO.getDateOfBirth())
                .build();
        return userRepository.save(newUser);
    }

    @Override
    public String login(String email, String password) throws Exception {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            throw new DataNotFoundException("User not found");
        }
        User existingUser = optionalUser.get();
        if(!passwordEncoder.matches(password, existingUser.getHashedPassword())){
            throw new BadCredentialsException("Wrong password");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                existingUser.getEmail(),
                password,
                existingUser.getAuthorities()
        );
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }
}
