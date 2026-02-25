package com.svhrs.service;

import com.svhrs.model.Family;
import com.svhrs.repository.FamilyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FamilyService {

    @Autowired
    private FamilyRepository familyRepository;

    public Family saveFamily(Family family) {
        return familyRepository.save(family);
    }

    public Family getFamilyById(Long id) {
        return familyRepository.findById(id).orElse(null);
    }
}