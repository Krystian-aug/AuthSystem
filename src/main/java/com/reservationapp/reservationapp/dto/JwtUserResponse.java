package com.reservationapp.reservationapp.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class JwtUserResponse {
        private String token;
        private String username;
        private String lastname;
        private String email;
}

