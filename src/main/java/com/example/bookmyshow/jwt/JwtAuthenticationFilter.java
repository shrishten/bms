package com.example.bookmyshow.jwt;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.bookmyshow.repository.UserRepository;

import java.io.IOException; // ✅ CORRECTimport 
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("JWT FILTER HIT: " + request.getRequestURI());
        final String authHeader = request.getHeader("Authorization");
        String jwtToken;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println("Authorization header: " + authHeader);
        // Extract JWT token and username
        jwtToken = authHeader.substring(7);
        username = jwtService.extractUsername(jwtToken);
        // check if we have a username and no authentication is set in the security
        // context
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userdetails = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
            // validate the token
            if (jwtService.isTokenValid(jwtToken, userdetails)) {
                // create authentication with user roles
                List<SimpleGrantedAuthority> authorities = userdetails.getRole().stream()
                        .map(role -> new SimpleGrantedAuthority(role))
                        .collect(Collectors.toList());

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userdetails, null, authorities);
                // set auth details
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("AUTH SET: " +
                        SecurityContextHolder.getContext().getAuthentication());
                System.out.println("AUTHORITIES: " +
                        SecurityContextHolder.getContext()
                                .getAuthentication()
                                .getAuthorities());
            }
        }

        filterChain.doFilter(request, response);
    }
}
