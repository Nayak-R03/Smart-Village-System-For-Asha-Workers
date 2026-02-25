package com.svhrs.repository;

import com.svhrs.model.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FamilyRepository extends JpaRepository<Family, Long> {

    // Only get families for this ASHA
    List<Family> findByAshaId(Long ashaId);
}
