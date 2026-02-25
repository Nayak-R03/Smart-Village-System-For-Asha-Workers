package com.svhrs.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "work_schedules")
public class WorkSchedule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "ashaId")
    private Long ashaId;
    
    @Column(name = "asha_name")
    private String ashaName;
    
    private String work;
    private String place;
    private Integer hours;
    
    @Column(name = "work_date")
    private LocalDate workDate;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    // Constructors
    public WorkSchedule() {}
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getAshaId() { return ashaId; }
    public void setAshaId(Long ashaId) { this.ashaId = ashaId; }
    
    public String getAshaName() { return ashaName; }
    public void setAshaName(String ashaName) { this.ashaName = ashaName; }
    
    public String getWork() { return work; }
    public void setWork(String work) { this.work = work; }
    
    public String getPlace() { return place; }
    public void setPlace(String place) { this.place = place; }
    
    public Integer getHours() { return hours; }
    public void setHours(Integer hours) { this.hours = hours; }
    
    
    public LocalDate getWorkDate() { return workDate; }
    public void setWorkDate(LocalDate workDate) { this.workDate = workDate; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
