package com.svhrs.web;

import com.svhrs.model.FamilyMember;
import com.svhrs.model.Family;
import com.svhrs.service.FamilyMemberService;
import com.svhrs.service.FamilyService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/family")
public class FamilyController {

    @Autowired
    private FamilyMemberService familyMemberService;

    @Autowired
    private FamilyService familyService;

    // 🟢 Show all family heads for current ASHA (for viewing)
    @GetMapping("/list")
    public String listFamilies(Model model, HttpSession session) {
        Long ashaId = (Long) session.getAttribute("ashaId");
        if (ashaId == null) {
            return "redirect:/login";
        }
        List<FamilyMember> heads = familyMemberService.getFamilyHeadsByAshaId(ashaId);
        model.addAttribute("heads", heads != null ? heads : List.of());
        return "family_list";
    }

    // 🟢 Show form to create family head
    @GetMapping("/create")
    public String showCreateFamilyForm(Model model) {
        model.addAttribute("newHead", new FamilyMember());
        return "family_add_head";
    }

 // 🟢 Save new family head - UPDATED with House No
    @PostMapping("/create")
    public String createFamilyHead(@ModelAttribute("newHead") FamilyMember member,
                                   @RequestParam("familyName") String familyName,
                                   @RequestParam("houseNo") String houseNo,  // ✅ NEW parameter
                                   HttpSession session) {
        Long ashaId = (Long) session.getAttribute("ashaId");
        if (ashaId == null) {
            return "redirect:/login";
        }
        
        try {
            member.setAshaId(ashaId);

            // Create family entry
            Family family = new Family();
            family.setFamilyName(familyName != null ? familyName : "");
            family.setHouseNo(houseNo != null ? houseNo : "");  // ✅ NEW: Set House No
            family.setAshaId(ashaId);
            Family savedFamily = familyService.saveFamily(family);

            if (savedFamily == null || savedFamily.getId() == null) {
                return "redirect:/family/create?error=family_creation_failed";
            }

            // ✅ Use the new createFamilyHead method that handles the two-step save
            FamilyMember savedHead = familyMemberService.createFamilyHead(member);

            if (savedHead == null || savedHead.getHeadId() == null) {
                return "redirect:/family/create?error=head_creation_failed";
            }

            return "redirect:/family/head/" + savedHead.getHeadId();
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/family/create?error=system_error";
        }
    }
    // 🟢 View head details after creation
    @GetMapping("/head/{headId}")
    public String viewHead(@PathVariable Long headId, Model model, HttpSession session) {
        Long ashaId = (Long) session.getAttribute("ashaId");
        if (ashaId == null || headId == null) {
            return "redirect:/login";
        }
        
        try {
            List<FamilyMember> allMembers = familyMemberService.getFamilyByHeadId(headId);
            if (allMembers == null || allMembers.isEmpty()) {
                return "redirect:/family/list";
            }
            
            FamilyMember head = allMembers.stream()
                .filter(m -> m != null && "Head".equals(m.getRelation()) && headId.equals(m.getHeadId()))
                .findFirst()
                .orElse(null);
            
            if (head == null || head.getAshaId() == null || !head.getAshaId().equals(ashaId)) {
                return "redirect:/family/list";
            }
            
            List<FamilyMember> members = allMembers.stream()
                .filter(m -> m != null && !"Head".equals(m.getRelation()))
                .collect(Collectors.toList());
            
            model.addAttribute("head", head);
            model.addAttribute("members", members != null ? members : List.of());
            model.addAttribute("headId", headId);
            return "family_head_view";
        } catch (Exception e) {
            return "redirect:/family/list?error=" + e.getMessage();
        }
    }

    // 🟢 Show add member form for specific family
    @GetMapping("/addMember/{headId}")
    public String showAddMemberForm(@PathVariable Long headId, 
                                    @RequestParam(required = false) String from,
                                    Model model, HttpSession session) {
        Long ashaId = (Long) session.getAttribute("ashaId");
        if (ashaId == null || headId == null) {
            return "redirect:/login";
        }
        model.addAttribute("newMember", new FamilyMember());
        model.addAttribute("headId", headId);
        model.addAttribute("from", from != null ? from : "create");
        return "family_add_member";
    }

    // 🟢 Save new family member
    @PostMapping("/addMember/{headId}")
    public String addFamilyMember(@PathVariable Long headId,
                                  @RequestParam(required = false) String from,
                                  @ModelAttribute("newMember") FamilyMember member, 
                                  HttpSession session) {
        Long ashaId = (Long) session.getAttribute("ashaId");
        if (ashaId == null || headId == null) {
            return "redirect:/login";
        }
        
        try {
            member.setAshaId(ashaId);
            member.setHeadId(headId);
            member.setRelation("Member");
            familyMemberService.saveFamilyMember(member);
            
            if ("update".equals(from)) {
                return "redirect:/family/update/" + headId;
            }
            return "redirect:/family/head/" + headId;
        } catch (Exception e) {
            return "redirect:/family/addMember/" + headId + "?error=" + e.getMessage();
        }
    }

    // 🟢 View all members of a specific family head (read-only)
    @GetMapping("/view/{headId}")
    public String viewFamily(@PathVariable Long headId, Model model, HttpSession session) {
        Long ashaId = (Long) session.getAttribute("ashaId");
        if (ashaId == null || headId == null) {
            return "redirect:/login";
        }
        
        try {
            List<FamilyMember> allMembers = familyMemberService.getFamilyByHeadId(headId);
            
            if (allMembers == null || allMembers.isEmpty()) {
                return "redirect:/family/list";
            }
            
            FamilyMember firstMember = allMembers.get(0);
            if (firstMember == null || firstMember.getAshaId() == null || !firstMember.getAshaId().equals(ashaId)) {
                return "redirect:/family/list";
            }
            
            FamilyMember head = allMembers.stream()
                .filter(m -> m != null && "Head".equals(m.getRelation()) && headId.equals(m.getHeadId()))
                .findFirst()
                .orElse(null);
            
            List<FamilyMember> members = allMembers.stream()
                .filter(m -> m != null && !"Head".equals(m.getRelation()))
                .collect(Collectors.toList());
            
            model.addAttribute("head", head);
            model.addAttribute("members", members != null ? members : List.of());
            return "family_view";
        } catch (Exception e) {
            return "redirect:/family/list?error=" + e.getMessage();
        }
    }

    // 🟢 Show all family heads for updating
    @GetMapping("/update")
    public String showUpdateFamilyList(Model model, HttpSession session) {
        Long ashaId = (Long) session.getAttribute("ashaId");
        if (ashaId == null) {
            return "redirect:/login";
        }
        List<FamilyMember> heads = familyMemberService.getFamilyHeadsByAshaId(ashaId);
        model.addAttribute("heads", heads != null ? heads : List.of());
        return "family_update_list";
    }

    // 🟢 Show family members for updating (with Update/Delete buttons)
    @GetMapping("/update/{headId}")
    public String showUpdateFamilyDetails(@PathVariable Long headId, Model model, HttpSession session) {
        Long ashaId = (Long) session.getAttribute("ashaId");
        if (ashaId == null || headId == null) {
            return "redirect:/login";
        }
        
        try {
            List<FamilyMember> allMembers = familyMemberService.getFamilyByHeadId(headId);
            
            if (allMembers == null || allMembers.isEmpty()) {
                return "redirect:/family/update";
            }
            
            FamilyMember firstMember = allMembers.get(0);
            if (firstMember == null || firstMember.getAshaId() == null || !firstMember.getAshaId().equals(ashaId)) {
                return "redirect:/family/update";
            }
            
            FamilyMember head = allMembers.stream()
                .filter(m -> m != null && "Head".equals(m.getRelation()) && headId.equals(m.getHeadId()))
                .findFirst()
                .orElse(null);
            
            List<FamilyMember> members = allMembers.stream()
                .filter(m -> m != null && !"Head".equals(m.getRelation()))
                .collect(Collectors.toList());
            
            model.addAttribute("head", head);
            model.addAttribute("members", members != null ? members : List.of());
            model.addAttribute("headId", headId);
            return "family_update_details";
        } catch (Exception e) {
            return "redirect:/family/update?error=" + e.getMessage();
        }
    }

    // 🟢 Show update form for a family member
    @GetMapping("/updateMember/{id}")
    public String showUpdateMemberForm(@PathVariable Long id, Model model, HttpSession session) {
        Long ashaId = (Long) session.getAttribute("ashaId");
        if (ashaId == null || id == null) {
            return "redirect:/login";
        }
        
        try {
            FamilyMember member = familyMemberService.getFamilyMemberByIdAndAshaId(id, ashaId);

            if (member == null) {
                return "redirect:/family/update";
            }

            model.addAttribute("member", member);
            return "family_update";
        } catch (Exception e) {
            return "redirect:/family/update?error=" + e.getMessage();
        }
    }

    // 🟢 Handle update form submission
    @PostMapping("/updateMember/{id}")
    public String updateFamilyMember(@PathVariable Long id,
                                     @ModelAttribute("member") FamilyMember updatedMember,
                                     HttpSession session) {
        Long ashaId = (Long) session.getAttribute("ashaId");
        if (ashaId == null || id == null) {
            return "redirect:/login";
        }
        
        try {
            FamilyMember existing = familyMemberService.getFamilyMemberByIdAndAshaId(id, ashaId);

            if (existing != null && existing.getHeadId() != null) {
                existing.setName(updatedMember.getName());
                existing.setDob(updatedMember.getDob());
                existing.setGender(updatedMember.getGender());
                existing.setSpouse(updatedMember.getSpouse());
                existing.setFatherName(updatedMember.getFatherName());
                existing.setMotherName(updatedMember.getMotherName());
                existing.setEducation(updatedMember.getEducation());
                existing.setOccupation(updatedMember.getOccupation());

                familyMemberService.saveFamilyMember(existing);
                
                return "redirect:/family/update/" + existing.getHeadId();
            }

            return "redirect:/family/update";
        } catch (Exception e) {
            return "redirect:/family/update?error=" + e.getMessage();
        }
    }

    // 🟢 Delete a family member (not head)
    @PostMapping("/delete/{id}")
    public String deleteFamilyMember(@PathVariable Long id, HttpSession session) {
        Long ashaId = (Long) session.getAttribute("ashaId");
        if (ashaId == null || id == null) {
            return "redirect:/login";
        }
        
        try {
            FamilyMember member = familyMemberService.getFamilyMemberByIdAndAshaId(id, ashaId);
            
            if (member != null && member.getHeadId() != null) {
                Long headId = member.getHeadId();
                familyMemberService.deleteFamilyMember(id);
                return "redirect:/family/update/" + headId;
            }
            
            return "redirect:/family/update";
        } catch (Exception e) {
            return "redirect:/family/update?error=" + e.getMessage();
        }
    }

    // ✅ NEW: Show delete head options page
    @GetMapping("/deleteHead/{headId}")
    public String showDeleteHeadOptions(@PathVariable Long headId, Model model, HttpSession session) {
        Long ashaId = (Long) session.getAttribute("ashaId");
        if (ashaId == null || headId == null) {
            return "redirect:/login";
        }
        
        try {
            List<FamilyMember> allMembers = familyMemberService.getFamilyByHeadId(headId);
            
            if (allMembers == null || allMembers.isEmpty()) {
                return "redirect:/family/update";
            }
            
            FamilyMember head = allMembers.stream()
                .filter(m -> m != null && "Head".equals(m.getRelation()))
                .findFirst()
                .orElse(null);
            
            if (head == null || !head.getAshaId().equals(ashaId)) {
                return "redirect:/family/update";
            }
            
            List<FamilyMember> members = allMembers.stream()
                .filter(m -> m != null && !"Head".equals(m.getRelation()))
                .collect(Collectors.toList());
            
            model.addAttribute("head", head);
            model.addAttribute("members", members);
            model.addAttribute("headId", headId);
            return "family_delete_head_options";
        } catch (Exception e) {
            return "redirect:/family/update?error=" + e.getMessage();
        }
    }

    // ✅ NEW: Delete entire family
    @PostMapping("/deleteFamily/{headId}")
    public String deleteEntireFamily(@PathVariable Long headId, HttpSession session) {
        Long ashaId = (Long) session.getAttribute("ashaId");
        if (ashaId == null || headId == null) {
            return "redirect:/login";
        }
        
        try {
            // Get all family members
            List<FamilyMember> allMembers = familyMemberService.getFamilyByHeadId(headId);
            
            // Verify ownership
            if (allMembers != null && !allMembers.isEmpty()) {
                FamilyMember firstMember = allMembers.get(0);
                if (firstMember.getAshaId().equals(ashaId)) {
                    // Delete all family members
                    for (FamilyMember member : allMembers) {
                        familyMemberService.deleteFamilyMember(member.getId());
                    }
                }
            }
            
            return "redirect:/family/update?success=family_deleted";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/family/update?error=deletion_failed";
        }
    }

    // ✅ NEW: Replace head with another member
    @PostMapping("/replaceHead/{headId}")
    public String replaceHead(@PathVariable Long headId, 
                             @RequestParam("newHeadId") Long newHeadId,
                             HttpSession session) {
        Long ashaId = (Long) session.getAttribute("ashaId");
        if (ashaId == null || headId == null || newHeadId == null) {
            return "redirect:/login";
        }
        
        try {
            // Get current head
            FamilyMember currentHead = familyMemberService.getFamilyMemberByIdAndAshaId(newHeadId, ashaId);
            if (currentHead == null || !currentHead.getHeadId().equals(headId)) {
                return "redirect:/family/update?error=invalid_member";
            }
            
            // Get the old head
            List<FamilyMember> allMembers = familyMemberService.getFamilyByHeadId(headId);
            FamilyMember oldHead = allMembers.stream()
                .filter(m -> "Head".equals(m.getRelation()))
                .findFirst()
                .orElse(null);
            
            if (oldHead == null) {
                return "redirect:/family/update?error=head_not_found";
            }
            
            // Change old head to member
            oldHead.setRelation("Member");
            familyMemberService.saveFamilyMember(oldHead);
            
            // Change new member to head
            currentHead.setRelation("Head");
            familyMemberService.saveFamilyMember(currentHead);
            
            return "redirect:/family/update/" + headId + "?success=head_replaced";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/family/update?error=replacement_failed";
        }
    }
}