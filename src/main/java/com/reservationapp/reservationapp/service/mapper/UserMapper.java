package com.reservationapp.reservationapp.service.mapper;

import com.reservationapp.reservationapp.dto.JwtUserResponse;
import com.reservationapp.reservationapp.dto.UserRequest;
import com.reservationapp.reservationapp.dto.UserResponse;
import com.reservationapp.reservationapp.entity.User;

public class UserMapper {
    public static User mapToUser(UserRequest userRequest) {
        return User.builder()
                .username(userRequest.getUsername())
                .lastname(userRequest.getLastname())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .build();
    }

    public static UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .username(user.getUsernameValue())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .build();
    }

    public static JwtUserResponse mapToJwtUserResponse(User user, String token) {
        return JwtUserResponse.builder()
                .username(user.getUsernameValue())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .token(token)
                .build();
    }

}
