package com.svhrs.web;

import com.svhrs.model.*;
import com.svhrs.service.LocationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/states")
    public List<State> getStates() {
        return locationService.getAllStates();
    }

    @GetMapping("/districts/{stateId}")
    public List<District> getDistricts(@PathVariable Long stateId) {
        return locationService.getDistrictsByState(stateId);
    }

    @GetMapping("/taluks/{districtId}")
    public List<Taluk> getTaluks(@PathVariable Long districtId) {
        return locationService.getTaluksByDistrict(districtId);
    }

    @GetMapping("/villages/{talukId}")
    public List<Village> getVillages(@PathVariable Long talukId) {
        return locationService.getVillagesByTaluk(talukId);
    }

    @GetMapping("/wards/{villageId}")
    public List<Ward> getWards(@PathVariable Long villageId) {
        return locationService.getWardsByVillage(villageId);
    }
}