package com.svhrs.web;

import com.svhrs.model.Asha;
import com.svhrs.model.Announcement;
import com.svhrs.model.WorkSchedule;
import com.svhrs.repository.AshaRepository;
import com.svhrs.repository.AnnouncementRepository;
import com.svhrs.repository.WorkScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AshaController {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private AshaRepository ashaRepository;

    // ✅ Add this repository to fetch ASHA work schedules
    @Autowired
    private WorkScheduleRepository workScheduleRepository;

 // ✅ Dashboard for ASHA
    @GetMapping("/asha/dashboard")
    public String dashboard(@AuthenticationPrincipal User user, 
                            Model model, 
                            jakarta.servlet.http.HttpSession session) {

        Long ashaId = Long.parseLong(user.getUsername());
        Asha asha = ashaRepository.findByAshaId(ashaId).orElse(null);

        if (asha != null) {
            // ✅ Store ASHA ID in session for later use (FamilyController, etc.)
            session.setAttribute("ashaId", asha.getAshaId());
            model.addAttribute("asha", asha);
            model.addAttribute("ashaId", ashaId);
        }

        return "asha_dashboard";
    }


    // ✅ Profile view
    @GetMapping("/asha/profile")
    public String profile(@AuthenticationPrincipal User user, Model model) {
        Long ashaId = Long.parseLong(user.getUsername());
        Asha asha = ashaRepository.findByAshaId(ashaId).orElse(null);

        model.addAttribute("asha", asha);
        return "asha_details";
    }

    // ✅ Announcements for ASHA
    @GetMapping("/announcements")
    public String announcements(Model model) {
        List<Announcement> announcements = announcementRepository.findAllByOrderByDateTimeDesc();
        model.addAttribute("announcements", announcements);
        return "asha_announcement";
    }

    // ✅ View ASHA's individual Work Schedules
    @GetMapping("/work-schedules")
    public String workSchedules(@AuthenticationPrincipal User user, Model model) {
        Long ashaId = Long.parseLong(user.getUsername());
        Asha asha = ashaRepository.findByAshaId(ashaId).orElse(null);

        if (asha != null) {
            // Fetch work schedules for this ASHA
            List<WorkSchedule> schedules = workScheduleRepository.findByAshaId(asha.getAshaId());
            model.addAttribute("schedules", schedules);
            model.addAttribute("asha", asha);
        } else {
            model.addAttribute("schedules", List.of());
            model.addAttribute("asha", null);
        }

        return "work_schedules_asha";
    }
}
