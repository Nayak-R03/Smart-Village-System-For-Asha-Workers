package com.svhrs.web;

import com.svhrs.dto.AshaRegistrationDto;
import com.svhrs.model.Asha;
import com.svhrs.service.AshaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final AshaService ashaService;

    public AuthController(AshaService ashaService) {
        this.ashaService = ashaService;
    }

    // GET /register
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        model.addAttribute("asha", new AshaRegistrationDto()); // <-- DTO instead of Entity
        return "register";
    }


    // POST /register
    @PostMapping("/register")
    public String register(@ModelAttribute("asha") AshaRegistrationDto dto, Model model) {
        try {
            if (!dto.getPassword().equals(dto.getConfirmPassword())) {
                model.addAttribute("error", "Passwords do not match.");
                return "register";
            }

            ashaService.register(dto); // Save to DB
            model.addAttribute("message", "Registration successful. Please login.");
            return "redirect:/login";

        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            return "register";
        } catch (Exception e) {
            model.addAttribute("error", "Error during registration: " + e.getMessage());
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }
}
