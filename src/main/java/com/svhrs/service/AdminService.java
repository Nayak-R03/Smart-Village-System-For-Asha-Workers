package com.svhrs.service;

import org.springframework.stereotype.Service;

@Service
public class AdminService {
    
    public boolean authenticatePanchayath(String panchayathId, String password) {
        return "panch123".equals(panchayathId) && "panch@123".equals(password);
    }
    
    public boolean authenticateHealthcare(String healthcareId, String password) {
        return "hcc123".equals(healthcareId) && "hcc@123".equals(password);
    }
}
