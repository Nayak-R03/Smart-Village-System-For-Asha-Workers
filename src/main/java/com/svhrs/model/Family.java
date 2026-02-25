package com.svhrs.model;

import jakarta.persistence.*;

@Entity
@Table(name = "family")
public class Family {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String familyName;
    private Long ashaId;
    
    // ✅ NEW: Added House No
    private String houseNo;

    // Getters and Setters
    public Long getId() { 
        return id; 
    }

    public void setId(Long id) { 
        this.id = id; 
    }

    public String getFamilyName() { 
        return familyName; 
    }

    public void setFamilyName(String familyName) { 
        this.familyName = familyName; 
    }

    public Long getAshaId() { 
        return ashaId; 
    }

    public void setAshaId(Long ashaId) { 
        this.ashaId = ashaId; 
    }
    
    // ✅ NEW: House No getter and setter
    public String getHouseNo() {
        return houseNo;
    }
    
    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }
}