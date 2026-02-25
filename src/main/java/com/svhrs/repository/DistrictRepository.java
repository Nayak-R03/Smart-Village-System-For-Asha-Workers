package com.svhrs.repository;

import com.svhrs.model.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
    List<District> findByState_Id(Long stateId); // Corrected method
}
