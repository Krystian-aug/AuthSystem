package com.reservationapp.reservationapp.service;

import com.reservationapp.reservationapp.dto.JwtUserResponse;
import com.reservationapp.reservationapp.dto.UserResponse;
import com.reservationapp.reservationapp.exception.EmailAlreadyExistsException;
import com.reservationapp.reservationapp.dto.UserRequest;
import com.reservationapp.reservationapp.entity.User;
import com.reservationapp.reservationapp.exception.InvalidCredentialsException;
import com.reservationapp.reservationapp.exception.UserNotFoundException;
import com.reservationapp.reservationapp.jwt.JwtService;
import com.reservationapp.reservationapp.repository.UserRepository;
import com.reservationapp.reservationapp.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.repository = userRepository;
        this.encoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public UserResponse register(UserRequest dtoUser){
        if(repository.existsByEmail(dtoUser.getEmail())){
            throw new EmailAlreadyExistsException("Email already in use");
        }
        User user = UserMapper.mapToUser(dtoUser);
        user.setPassword(encoder.encode(user.getPassword()));
        User savedUser = repository.save(user);
        return UserMapper.mapToUserResponse(savedUser);
    }

    public JwtUserResponse login(UserRequest dtoUser){
        User user = repository.findByEmail(dtoUser.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!encoder.matches(dtoUser.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }
        String token = jwtService.generateToken(user);
        return UserMapper.mapToJwtUserResponse(user, token);
    }

    public UserResponse getUserByEmail(String email) {
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UserResponse(user.getUsernameValue(), user.getLastname(), user.getEmail());
    }

}
