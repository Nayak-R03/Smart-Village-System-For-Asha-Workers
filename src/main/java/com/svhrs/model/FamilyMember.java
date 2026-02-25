package com.svhrs.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "family_members")
public class FamilyMember {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private LocalDate dob;
    private String gender;
    private String spouse;
    private String fatherName;
    private String motherName;
    private String education;
    private String occupation;
    private String relation;
    private Long ashaId;
    
    // ✅ IMPORTANT: Just a plain Long field, NO @ManyToOne or @JoinColumn
    private Long headId;
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public LocalDate getDob() {
        return dob;
    }
    
    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getSpouse() {
        return spouse;
    }
    
    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }
    
    public String getFatherName() {
        return fatherName;
    }
    
    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }
    
    public String getMotherName() {
        return motherName;
    }
    
    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }
    
    public String getEducation() {
        return education;
    }
    
    public void setEducation(String education) {
        this.education = education;
    }
    
    public String getOccupation() {
        return occupation;
    }
    
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
    
    public String getRelation() {
        return relation;
    }
    
    public void setRelation(String relation) {
        this.relation = relation;
    }
    
    public Long getAshaId() {
        return ashaId;
    }
    
    public void setAshaId(Long ashaId) {
        this.ashaId = ashaId;
    }
    
    public Long getHeadId() {
        return headId;
    }
    
    public void setHeadId(Long headId) {
        this.headId = headId;
    }
}