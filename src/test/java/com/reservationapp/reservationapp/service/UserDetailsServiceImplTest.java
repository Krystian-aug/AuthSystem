package com.reservationapp.reservationapp.service;

import com.reservationapp.reservationapp.entity.User;
import com.reservationapp.reservationapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserDetailsServiceImplTest {
    private UserRepository repository;
    private UserDetailsServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(UserRepository.class);
        service = new UserDetailsServiceImpl(repository);
    }

    @Test
    void loadUserByUsername_userExists_returnsUserDetails(){
        // given
        User user = new User(1L, "Jan", "Kowalski", "jan@example.com", "password");
        when(repository.findByEmail("jan@example.com")).thenReturn(Optional.of(user));

        //when
        UserDetails result = service.loadUserByUsername("jan@example.com");
        System.out.println(result);

        //then
        assertEquals("jan@example.com", result.getUsername());
        assertEquals("password", result.getPassword());
        verify(repository, times(1)).findByEmail("jan@example.com");
    }

    @Test
    void loadUserByUsername_userNotFound_throwsException(){
        when(repository.findByEmail("jan@example.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("jan@example.com"));
        verify(repository).findByEmail("jan@example.com");
    }
}
