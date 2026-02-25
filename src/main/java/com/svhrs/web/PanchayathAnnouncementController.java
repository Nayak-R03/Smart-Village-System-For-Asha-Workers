package com.svhrs.web;

import com.svhrs.model.Announcement;
import com.svhrs.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
//import java.util.List;

@Controller
@RequestMapping("/admin/panchayath")
public class PanchayathAnnouncementController {

    @Autowired
    private AnnouncementRepository announcementRepository;

    // Panchayath submits announcement
    @PostMapping("/announcement")
    public String submitAnnouncement(@ModelAttribute Announcement announcement) {
        announcement.setDateTime(LocalDateTime.now());
        announcement.setCreatedAt(LocalDateTime.now());
        announcement.setSenderName("Panchayath");
        announcement.setSenderType(Announcement.SenderType.PANCHAYATH);
        announcementRepository.save(announcement);
        return "redirect:/admin/panchayath/announcement";
    }

    // Panchayath page with form + list
    @GetMapping("/announcement")
    public String getPanchayathAnnouncementPage(Model model) {
        model.addAttribute("announcement", new Announcement());
        model.addAttribute("announcements", announcementRepository.findAllByOrderByDateTimeDesc());
        return "panchayath_announcements";
    }

    // ✅ For ASHA and others (read-only)
	/*
	 * @GetMapping("/view-announcements") public String
	 * viewAnnouncementsForAsha(Model model) { model.addAttribute("announcements",
	 * announcementRepository.findAllByOrderByDateTimeDesc()); return
	 * "announcements"; // reuse same Thymeleaf file }
	 */
}
