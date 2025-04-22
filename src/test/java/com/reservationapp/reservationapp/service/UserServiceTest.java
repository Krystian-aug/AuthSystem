package com.reservationapp.reservationapp.service;

import com.reservationapp.reservationapp.dto.JwtUserResponse;
import com.reservationapp.reservationapp.dto.UserRequest;
import com.reservationapp.reservationapp.dto.UserResponse;
import com.reservationapp.reservationapp.entity.User;
import com.reservationapp.reservationapp.exception.EmailAlreadyExistsException;
import com.reservationapp.reservationapp.exception.InvalidCredentialsException;
import com.reservationapp.reservationapp.exception.UserNotFoundException;
import com.reservationapp.reservationapp.jwt.JwtService;
import com.reservationapp.reservationapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    private UserService service;
    private PasswordEncoder encoder;
    private JwtService jwtService;
    private UserRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(UserRepository.class);
        encoder = mock(PasswordEncoder.class);
        jwtService = mock(JwtService.class);
        service = new UserService(repository, encoder, jwtService);
    }

    @Test
    void register_newEmail_successfullyRegisters(){
        UserRequest request = new UserRequest("Jan", "Kowalski", "jan@example.com", "password");
        User user = new User(1L, "Jan", "Kowalski", "jan@example.com", "encodedPassword");

        when(repository.existsByEmail(request.getEmail())).thenReturn(false);
        when(encoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(repository.save(any(User.class))).thenReturn(user);

        UserResponse response = service.register(request);

        assertEquals("Jan", response.getUsername());
        assertEquals("Kowalski", response.getLastname());
        assertEquals("jan@example.com", response.getEmail());

    }
    @Test
    void register_emailAlreadyExists_throwsException(){
        UserRequest request = new UserRequest("Jan", "Kowalski", "jan@example.com", "password");
        when(repository.existsByEmail(request.getEmail())).thenReturn(true);
        assertThrows(EmailAlreadyExistsException.class, () -> service.register(request));
    }

    @Test
    void login_correctCredentials_returnsToken(){
        UserRequest request = new UserRequest("Jan", "Kowalski", "jan@example.com", "password");
        User user = new User(1L, "Jan", "Kowalski", "jan@example.com", "encodedPassword");

        when(repository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(encoder.matches("password", "encodedPassword")).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("jwt-token");

        JwtUserResponse response = service.login(request);

        assertEquals("jwt-token", response.getToken());
        assertEquals("jan@example.com", response.getEmail());
    }

    @Test
    void login_userNotFound_throwsException() {
        UserRequest request = new UserRequest("Jan", "Kowalski", "jan@example.com", "password");

        when(repository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> service.login(request));
    }

    @Test
    void login_invalidPassword_throwsException() {
        User user = new User(1L, "Jan", "Kowalski", "jan@example.com", "encodedPassword");
        UserRequest request = new UserRequest("Jan", "Kowalski", "jan@example.com", "wrongPassword");

        when(repository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(encoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> service.login(request));
    }

    @Test
    void getUserByEmail_existingEmail_returnsUserResponse() {
        User user = new User(1L, "Jan", "Kowalski", "jan@example.com", "password");

        when(repository.findByEmail("jan@example.com")).thenReturn(Optional.of(user));

        UserResponse response = service.getUserByEmail("jan@example.com");

        assertEquals("Jan", response.getUsername());
        assertEquals("jan@example.com", response.getEmail());
    }

    @Test
    void getUserByEmail_nonExistingEmail_throwsException() {
        when(repository.findByEmail("nieistnieje@example.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> service.getUserByEmail("nieistnieje@example.com"));
    }
}
