package com.danish.backend.authflow;

import com.danish.backend.user.EmailAlreadyExistsException;
import com.danish.backend.user.User;
import com.danish.backend.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void addNewUser(@Valid RegisterRequest registerRequest) {

        if(userRepository.existsUserByEmail(registerRequest.getEmail())){
            throw new EmailAlreadyExistsException("Email already exists");
        }
        String passwordHash = passwordEncoder.encode(registerRequest.getPassword());

        User user = new User();

        user.setEmail(registerRequest.getEmail());
        user.setPasswordHash(passwordHash);

        userRepository.save(user);



    }
}
