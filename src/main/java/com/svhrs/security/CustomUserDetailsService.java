package com.svhrs.security;

import com.svhrs.model.Asha;
import com.svhrs.repository.AshaRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AshaRepository repo;

    public CustomUserDetailsService(AshaRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Long ashaId;
        try {
            // Convert username (from login form) to Long
            ashaId = Long.parseLong(username);
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("Invalid ASHA ID format (must be numeric): " + username);
        }

        // Find ASHA by numeric ID
        Asha asha = repo.findByAshaId(ashaId)
                .orElseThrow(() -> new UsernameNotFoundException("Asha not found with ID: " + ashaId));

        // Return Spring Security User object
        return User.withUsername(String.valueOf(asha.getAshaId()))
                .password(asha.getPassword())
                .roles("ASHA")
                .build();
    }
}
