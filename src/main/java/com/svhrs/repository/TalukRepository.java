package com.svhrs.repository;

import com.svhrs.model.Taluk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TalukRepository extends JpaRepository<Taluk, Long> {
    List<Taluk> findByDistrict_Id(Long districtId); // Use _ to navigate nested property
}
