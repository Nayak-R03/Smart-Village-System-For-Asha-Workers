package com.svhrs.web;

import com.svhrs.model.Asha;
import com.svhrs.service.AshaService;
import com.svhrs.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AshaService ashaService;
    private final AdminService adminService;

    public AdminController(AshaService ashaService, AdminService adminService) {
        this.ashaService = ashaService;
        this.adminService = adminService;
    }

    @GetMapping("/login")
    public String adminLogin() {
        return "admin_login";
    }

    @GetMapping("/panchayath")
    public String panchayathLogin() {
        return "panchayath_login";
    }

    @GetMapping("/healthcare")
    public String healthcareLogin() {
        return "healthcare_login";
    }

    @GetMapping("/panchayath/dashboard")
    public String panchayathDashboard() {
        return "panchayath_dashboard";
    }

    @GetMapping("/healthcare/dashboard")
    public String healthcareDashboard(Model model) {
        List<Asha> ashas = ashaService.getAllAshas();
        model.addAttribute("ashas", ashas);
        return "healthcare_dashboard";
    }

    @GetMapping("/healthcare/asha-details")
    public String ashaDetails(Model model) {
        List<Asha> ashas = ashaService.getAllAshas();
        model.addAttribute("ashas", ashas);
        return "asha_details";
    }

    @GetMapping("/healthcare/announcements")
    public String healthcareAnnouncements() {
        return "healthcare_announcements";
    }
    @GetMapping("/healthcare/work-schedules-admin")
    public String adminWorkSchedules() {
        return "redirect:/admin/healthcare/work-schedules";
    }


    @PostMapping("/panchayath/login")
    public String panchayathLogin(@RequestParam String panchayathId, 
                                 @RequestParam String password, 
                                 Model model) {
        if (adminService.authenticatePanchayath(panchayathId, password)) {
            return "redirect:/admin/panchayath/dashboard";
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "panchayath_login";
        }
    }

    @PostMapping("/healthcare/login")
    public String healthcareLogin(@RequestParam String healthcareId, 
                                 @RequestParam String password, 
                                 Model model) {
        if (adminService.authenticateHealthcare(healthcareId, password)) {
            return "redirect:/admin/healthcare/dashboard";
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "healthcare_login";
        }
    }
}
