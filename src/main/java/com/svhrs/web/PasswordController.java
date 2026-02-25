package com.svhrs.web;

import com.svhrs.service.PasswordResetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PasswordController {

    private final PasswordResetService passwordResetService;

    public PasswordController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordForm() {
        return "forgot_password";
    }

    @PostMapping("/forgot-password")
    public String resetPassword(@RequestParam String ashaId,
                                @RequestParam String email,
                                @RequestParam String newPassword,
                                @RequestParam String confirmPassword,
                                Model model) {
        // Convert String ASHA ID to Long
        Long ashaIdLong;
        try {
            ashaIdLong = Long.parseLong(ashaId);
        } catch (NumberFormatException e) {
            model.addAttribute("error", "Invalid ASHA ID format");
            return "forgot_password";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "forgot_password";
        }

        if (newPassword.length() < 6) {
            model.addAttribute("error", "Password must be at least 6 characters long");
            return "forgot_password";
        }

        // ✅ Verify ASHA ID and EMAIL match
        boolean success = passwordResetService.resetPassword(ashaIdLong, email, newPassword);
        
        if (success) {
            model.addAttribute("success", true);
        } else {
            model.addAttribute("error", "ASHA ID or Email does not match our records");
        }

        return "forgot_password";
    }
}