package com.reservationapp.reservationapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String username;

    @Column(nullable = false, length = 100)
    private String lastname;

    @Column(nullable = false, length = 255, unique = true)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(name = "date_of_establishment", nullable = false, updatable = false)
    private LocalDateTime dateOfEstablishment;

    public User(Long id, String username, String lastname, String email, String password) {
        this.id = id;
        this.username = username;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    @PrePersist
    protected void onCreate() {
        this.dateOfEstablishment = LocalDateTime.now();
    }

    // Implementacja metod z UserDetails

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Możesz dodać role użytkownika. Załóżmy, że masz jakąś rolę (np. "USER" lub "ADMIN")
        return List.of(() -> "ROLE_USER"); // Dla przykładu nadajemy rolę "ROLE_USER"
    }

    @Override
    public String getPassword() {
        return password;
    }

    //Custom getUsername getter
    public String getUsernameValue(){return username;}

    //For DetailsService - Spring Security
    @Override
    public String getUsername() {
        return email; // Używamy emaila jako username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Możesz dostosować logikę w razie potrzeby
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Możesz dostosować logikę w razie potrzeby
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Możesz dostosować logikę w razie potrzeby
    }

    @Override
    public boolean isEnabled() {
        return true; // Możesz dostosować logikę w razie potrzeby
    }
}
