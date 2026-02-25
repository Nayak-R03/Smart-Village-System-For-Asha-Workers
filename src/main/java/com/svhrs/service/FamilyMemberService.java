package com.svhrs.service;

import com.svhrs.model.FamilyMember;
import com.svhrs.model.Family;
import com.svhrs.repository.FamilyMemberRepository;
import com.svhrs.repository.FamilyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Service
public class FamilyMemberService {

    @Autowired
    private FamilyMemberRepository familyMemberRepository;
    
    @Autowired
    private FamilyRepository familyRepository;

    // ✅ NEW METHOD: Save family head properly
    public FamilyMember createFamilyHead(FamilyMember head) {
        // Step 1: Save without headId first
        head.setHeadId(null);
        head.setRelation("Head");
        FamilyMember savedHead = familyMemberRepository.save(head);

        // Step 2: Update headId to point to itself
        savedHead.setHeadId(savedHead.getId());
        return familyMemberRepository.save(savedHead);
    }

    // Save or update a family member
    public FamilyMember saveFamilyMember(FamilyMember member) {
        return familyMemberRepository.save(member);
    }

    // Get a family member by its ID
    public FamilyMember getFamilyMemberById(Long id) {
        return familyMemberRepository.findById(id).orElse(null);
    }

    // Get a family member by ID and ASHA ID
    public FamilyMember getFamilyMemberByIdAndAshaId(Long id, Long ashaId) {
        return familyMemberRepository.findByIdAndAshaId(id, ashaId).orElse(null);
    }

    // Get family heads for a particular ASHA
    public List<FamilyMember> getFamilyHeadsByAshaId(Long ashaId) {
        return familyMemberRepository.findByAshaIdAndRelation(ashaId, "Head");
    }

    // Get all members under a family head
    public List<FamilyMember> getFamilyByHeadId(Long headId) {
        return familyMemberRepository.findByHeadId(headId);
    }

    // Get all members of an ASHA (heads + members)
    public List<FamilyMember> getAllMembersByAshaId(Long ashaId) {
        return familyMemberRepository.findByAshaId(ashaId);
    }

    // Fetch a single member (head) by ID
    public FamilyMember getMemberById(Long id) {
        return familyMemberRepository.findById(id).orElse(null);
    }

    // Delete a family member
    public void deleteFamilyMember(Long id) {
        familyMemberRepository.deleteById(id);
    }

    // Get report data with filters
    public List<FamilyMember> getReportData(Long ashaId, Boolean headOnly, String ageFilter, Integer startAge, Integer endAge) {
        List<FamilyMember> members;

        if (headOnly != null && headOnly) {
            members = getFamilyHeadsByAshaId(ashaId);
        } else {
            members = getAllMembersByAshaId(ashaId);
        }

        if (ageFilter != null && !ageFilter.equals("all") && startAge != null && endAge != null) {
            java.time.LocalDate today = java.time.LocalDate.now();
            members = members.stream()
                .filter(member -> {
                    if (member.getDob() == null) return false;
                    int age = today.getYear() - member.getDob().getYear();
                    if (today.getDayOfYear() < member.getDob().getDayOfYear()) {
                        age--;
                    }
                    return age >= startAge && age <= endAge;
                })
                .collect(java.util.stream.Collectors.toList());
        }

        return members;
    }
    
    // ✅ NEW METHOD: Get House No mapping for members
    public Map<Long, String> getHouseNoMapping(Long ashaId) {
        Map<Long, String> houseNoMap = new HashMap<>();
        
        // Get all families for this ASHA
        List<Family> families = familyRepository.findByAshaId(ashaId);
        
        // Create a mapping of family ID to House No
        for (Family family : families) {
            if (family.getHouseNo() != null) {
                houseNoMap.put(family.getId(), family.getHouseNo());
            }
        }
        
        return houseNoMap;
    }
}