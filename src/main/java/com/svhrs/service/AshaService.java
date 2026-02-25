package com.svhrs.service;

import com.svhrs.dto.AshaRegistrationDto;
import com.svhrs.model.Asha;
import com.svhrs.repository.AshaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AshaService {

    private final AshaRepository repository;
    private final PasswordEncoder passwordEncoder;

    public AshaService(AshaRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    // Register a new ASHA
    public Asha register(AshaRegistrationDto dto) {
        // Convert dto ashaId to Long
        Long ashaId = Long.parseLong(dto.getAshaId());

        if (repository.existsByAshaId(ashaId)) {
            throw new IllegalArgumentException("Asha ID already registered.");
        }

        Asha a = new Asha();
        a.setAshaId(ashaId); // Long now
        a.setName(dto.getName());
        a.setDob(dto.getDob());
        a.setMobile(dto.getMobile());
        a.setEmail(dto.getEmail());
        a.setState(dto.getState());
        a.setDistrict(dto.getDistrict());
        a.setTaluk(dto.getTaluk());
        a.setVillage(dto.getVillage());
        a.setWard(dto.getWard());
        a.setPassword(passwordEncoder.encode(dto.getPassword()));

        return repository.save(a);
    }

    // ✅ Get ASHA by ID (optional)
    public Optional<Asha> findByAshaId(Long ashaId) {
        return repository.findByAshaId(ashaId);
    }

    // ✅ Get ASHA by ID (non-optional, throws if not found)
    public Asha findByUsername(Long ashaId) {
        return repository.findByAshaId(ashaId)
                .orElseThrow(() -> new RuntimeException("Asha not found with ID: " + ashaId));
    }

    // Update profile
    public Asha updateProfile(Long ashaId, Asha updated) {
        Asha existing = repository.findByAshaId(ashaId)
                .orElseThrow(() -> new RuntimeException("Asha not found"));

        existing.setName(updated.getName());
        existing.setDob(updated.getDob());
        existing.setMobile(updated.getMobile());
        existing.setEmail(updated.getEmail());
        existing.setState(updated.getState());
        existing.setDistrict(updated.getDistrict());
        existing.setTaluk(updated.getTaluk());
        existing.setVillage(updated.getVillage());
        existing.setWard(updated.getWard());
        // password update should be separate endpoint if needed

        return repository.save(existing);
    }

    // Get all ASHAs (for admin)
    public List<Asha> getAllAshas() {
        return repository.findAll();
    }
}
