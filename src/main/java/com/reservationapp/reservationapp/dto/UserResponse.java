package com.reservationapp.reservationapp.dto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {
    private String username;
    private String lastname;
    private String email;
}
