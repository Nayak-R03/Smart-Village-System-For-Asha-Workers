package com.svhrs.web;

import com.svhrs.model.Asha;
import com.svhrs.model.WorkSchedule;
import com.svhrs.repository.AshaRepository;
import com.svhrs.repository.WorkScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/healthcare")
public class WorkScheduleController {

    @Autowired
    private WorkScheduleRepository workScheduleRepository;

    @Autowired
    private AshaRepository ashaRepository;

    // ✅ Show Work Schedule Form
    @GetMapping("/work-schedules")
    public String showWorkSchedulePage(Model model) {
        List<Asha> ashas = ashaRepository.findAll();
        List<WorkSchedule> schedules = workScheduleRepository.findAll();

        System.out.println("✅ ASHA list size: " + ashas.size());
        model.addAttribute("ashas", ashas);
        model.addAttribute("schedules", schedules);

        return "WorkSchedules"; // must match your HTML file name exactly
    }

    // ✅ Save Schedule
    @PostMapping("/work-schedules")
    public String saveWorkSchedule(
            @RequestParam("ashaId") Long ashaId,
            @RequestParam("work") String work,
            @RequestParam("place") String place,
            @RequestParam("hours") Integer hours,
            @RequestParam("workDate") String workDate) {

        Optional<Asha> ashaOpt = ashaRepository.findByAshaId(ashaId);
        if (ashaOpt.isPresent()) {
            Asha asha = ashaOpt.get();
            WorkSchedule schedule = new WorkSchedule();

            schedule.setAshaId(asha.getAshaId());
            schedule.setAshaName(asha.getName());
            schedule.setWork(work);
            schedule.setPlace(place);
            schedule.setHours(hours);
            schedule.setWorkDate(LocalDate.parse(workDate));
            schedule.setCreatedAt(LocalDateTime.now());

            workScheduleRepository.save(schedule);
            System.out.println("✅ Saved schedule for: " + asha.getName());
        } else {
            System.out.println("❌ ASHA not found for ID: " + ashaId);
        }

        return "redirect:/admin/healthcare/work-schedules";
    }

    // ✅ ASHA can view their own schedule
    @GetMapping("/asha/work-schedules/{ashaId}")
    public String viewAshaSchedules(@PathVariable Long ashaId, Model model) {
        List<WorkSchedule> schedules = workScheduleRepository.findByAshaId(ashaId);
        model.addAttribute("schedules", schedules);
        return "work_schedules_asha";
    }
}
