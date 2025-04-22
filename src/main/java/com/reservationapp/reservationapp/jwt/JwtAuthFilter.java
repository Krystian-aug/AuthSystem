package com.reservationapp.reservationapp.jwt;

import com.reservationapp.reservationapp.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Logowanie ścieżki i metody
        System.out.println("JwtAuthFilter - Request path: " + request.getRequestURI());
        System.out.println("JwtAuthFilter - Request method: " + request.getMethod());

        String path = request.getRequestURI();

        if (request.getMethod().equals("POST") &&
                (path.matches("/api/users/register|/api/users/login"))) {
            System.out.println("JwtAuthFilter - Skipping auth filter for: " + path);
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String authHeader = request.getHeader("Authorization");
            System.out.println("JwtAuthFilter - Authorization header: " + authHeader);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                System.out.println("JwtAuthFilter - No Bearer token, skipping...");
                filterChain.doFilter(request, response);
                return;
            }

            String jwt = authHeader.substring(7);
            String userEmail = jwtService.extractEmail(jwt);
            System.out.println("JwtAuthFilter - Extracted email from JWT: " + userEmail);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

                if (jwtService.isTokenValid(jwt, (User) userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("JwtAuthFilter - Token valid, authentication set.");
                }
            }
        } catch (Exception e) {
            System.out.println("JwtAuthFilter - Exception: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Nieprawidłowy token JWT");
            return;
        }

        filterChain.doFilter(request, response);
    }

}

