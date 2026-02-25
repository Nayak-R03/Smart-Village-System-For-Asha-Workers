package com.svhrs.model;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class FamilyMemberListener {

    @PrePersist
    public void beforeSave(FamilyMember member) {
        // Example: auto-set name to uppercase
        if (member.getName() != null) {
            member.setName(member.getName().toUpperCase());
        }
    }

    @PreUpdate
    public void beforeUpdate(FamilyMember member) {
        // Example: update name to uppercase on update
        if (member.getName() != null) {
            member.setName(member.getName().toUpperCase());
        }
    }
}
