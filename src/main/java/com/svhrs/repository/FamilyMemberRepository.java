package com.svhrs.repository;

import com.svhrs.model.FamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {

    List<FamilyMember> findByAshaId(Long ashaId);

    // Find a member by ID and ASHA ID
    Optional<FamilyMember> findByIdAndAshaId(Long id, Long ashaId);

    // Find members by ASHA ID and relation
    List<FamilyMember> findByAshaIdAndRelation(Long ashaId, String relation);

    // Find all members where headId matches
    List<FamilyMember> findByHeadId(Long headId);
}
