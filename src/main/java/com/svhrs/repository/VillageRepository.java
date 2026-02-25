package com.svhrs.repository;

import com.svhrs.model.Village;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VillageRepository extends JpaRepository<Village, Long> {
    List<Village> findByTaluk_Id(Long talukId); // Use _ for nested property
}
