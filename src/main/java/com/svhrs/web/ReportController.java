package com.svhrs.web;

import com.svhrs.model.FamilyMember;
import com.svhrs.service.FamilyMemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private FamilyMemberService familyMemberService;

    @GetMapping("/retrieve")
    public String retrieveReports() {
        return "report_retrieve";
    }

    @PostMapping("/retrieve")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> retrieveReportData(
            @RequestParam(required = false, defaultValue = "false") String headOnly,
            @RequestParam(required = false, defaultValue = "all") String ageFilter,
            @RequestParam(required = false) Integer startAge,
            @RequestParam(required = false) Integer endAge,
            @RequestParam("columns") String[] columnsArray,
            HttpSession session) {
        
        Long ashaId = (Long) session.getAttribute("ashaId");
        if (ashaId == null) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "ASHA ID not found in session");
            return ResponseEntity.badRequest().body(error);
        }

        Boolean headOnlyBool = "true".equalsIgnoreCase(headOnly);
        List<String> columns = List.of(columnsArray);
        
        List<FamilyMember> members = familyMemberService.getReportData(ashaId, headOnlyBool, ageFilter, startAge, endAge);
        
        // ✅ NEW: Get House No mapping
        Map<Long, String> houseNoMapping = familyMemberService.getHouseNoMapping(ashaId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("members", members);
        response.put("columns", columns);
        response.put("houseNoMapping", houseNoMapping);  // ✅ NEW: Send mapping to frontend
        
        return ResponseEntity.ok(response);
    }
}