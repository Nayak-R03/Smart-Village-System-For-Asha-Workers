package com.svhrs.repository;

import com.svhrs.model.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WardRepository extends JpaRepository<Ward, Long> {
    List<Ward> findByVillage_Id(Long villageId); // Use _ for nested property
}
