package com.svhrs.web;

import com.svhrs.model.Announcement;
import com.svhrs.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/admin/healthcare")
public class HealthcareAnnouncementController {

    @Autowired
    private AnnouncementRepository announcementRepository;

    // Healthcare submits announcement
    @PostMapping("/announcement")
    public String submitAnnouncement(@ModelAttribute Announcement announcement) {
        announcement.setDateTime(LocalDateTime.now());
        announcement.setCreatedAt(LocalDateTime.now());
        announcement.setSenderName("Healthcare");
        announcement.setSenderType(Announcement.SenderType.HEALTHCARE);
        announcementRepository.save(announcement);
        return "redirect:/admin/healthcare/announcement";
    }

    // Healthcare page with form + list
    @GetMapping("/announcement")
    public String getHealthcareAnnouncementPage(Model model) {
        model.addAttribute("announcement", new Announcement());
        model.addAttribute("announcements", announcementRepository.findAllByOrderByDateTimeDesc());
        return "healthcare_announcements"; // ✅ corrected line
    }
}
