package com.reservationapp.reservationapp.controller;

import com.reservationapp.reservationapp.dto.JwtUserResponse;
import com.reservationapp.reservationapp.dto.UserRequest;
import com.reservationapp.reservationapp.dto.UserResponse;
import com.reservationapp.reservationapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest userRequest) {
        System.out.println("UserController - /register hit");
        UserResponse userResponse = service.register(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtUserResponse> login(@RequestBody UserRequest userRequest) {
        System.out.println("UserController - /login hit");
        JwtUserResponse userResponse = service.login(userRequest);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(Authentication authentication) {
        System.out.println("UserController - /me hit");
        String email = authentication.getName(); // lub (User) authentication.getPrincipal()
        UserResponse response = service.getUserByEmail(email); // stwórz taką metodę w serwisie

        return ResponseEntity.ok(response);
    }


}
