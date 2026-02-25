package com.svhrs.web;

import com.svhrs.model.Asha;
import com.svhrs.service.AshaService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DashboardController {

    private final AshaService ashaService;

    public DashboardController(AshaService ashaService) {
        this.ashaService = ashaService;
    }

    private Long getCurrentAshaId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        try {
            return Long.parseLong(username); // ✅ convert logged-in username to Long
        } catch (NumberFormatException e) {
            return null; // handle invalid ID if necessary
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Long ashaId = getCurrentAshaId();
        Asha asha = ashaService.findByAshaId(ashaId).orElse(null);
        model.addAttribute("asha", asha);
        return "asha_dashboard";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        Long ashaId = getCurrentAshaId();
        Asha asha = ashaService.findByAshaId(ashaId).orElse(null);
        model.addAttribute("asha", asha);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute("asha") Asha updated, Model model) {
        Long ashaId = getCurrentAshaId();
        if (ashaId != null) {
            ashaService.updateProfile(ashaId, updated);
        }
        return "redirect:/profile?success";
    }
}
