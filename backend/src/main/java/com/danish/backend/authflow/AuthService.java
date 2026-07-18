package com.danish.backend.authflow;

import com.danish.backend.user.EmailAlreadyExistsException;
import com.danish.backend.user.User;
import com.danish.backend.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
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

    public LoginResponse findUser(LoginRequest loginRequest){

        User user = userRepository.findUserByEmail(loginRequest.getEmail()).orElseThrow(()->
                new InvalidCredentialException("Invalid Email or Password"));

        String passwordHash = user.getPasswordHash();

        boolean passMatches = passwordEncoder.matches(loginRequest.getPassword(), passwordHash);


        if(!passMatches){
            throw new InvalidCredentialException("Invalid Email or Password");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new LoginResponse(token);




    }
}
