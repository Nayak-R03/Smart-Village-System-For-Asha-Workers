package com.svhrs.service;

import com.svhrs.model.Asha;
import com.svhrs.repository.AshaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class PasswordResetService {

    private final AshaRepository ashaRepository;
    private final PasswordEncoder passwordEncoder;

    public PasswordResetService(AshaRepository ashaRepository, PasswordEncoder passwordEncoder) {
        this.ashaRepository = ashaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ✅ Reset password with EMAIL VERIFICATION
    public boolean resetPassword(Long ashaId, String email, String newPassword) {
        Optional<Asha> ashaOpt = ashaRepository.findByAshaId(ashaId);
        
        if (ashaOpt.isPresent()) {
            Asha asha = ashaOpt.get();
            
            // ✅ VERIFY EMAIL MATCHES
            if (asha.getEmail() == null || !asha.getEmail().equalsIgnoreCase(email.trim())) {
                return false; // Email doesn't match
            }
            
            asha.setPassword(passwordEncoder.encode(newPassword));
            ashaRepository.save(asha);
            
            // Simulate email notification
            sendPasswordResetEmail(asha.getEmail(), newPassword);
            return true;
        }
        return false;
    }

    private void sendPasswordResetEmail(String email, String newPassword) {
        System.out.println("Email sent to: " + email);
        System.out.println("Subject: Password Reset Confirmation");
        System.out.println("Message: Your password has been successfully reset.");
    }
}