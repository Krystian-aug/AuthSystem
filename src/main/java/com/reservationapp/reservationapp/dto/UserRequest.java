package com.reservationapp.reservationapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @NotBlank(message = "Imię nie może być puste")
    private String username;

    @NotBlank(message = "Nazwisko nie może być puste")
    private String lastname;

    @Email(message = "Nieprawidłowy adres email")
    @NotBlank(message = "Email nie może być pusty")
    private String email;

    @Size(min = 6, message = "Hasło musi mieć co najmniej 6 znaków")
    private String password;

}
