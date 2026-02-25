package com.svhrs.service;

import com.svhrs.model.*;
import com.svhrs.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    private final StateRepository stateRepository;
    private final DistrictRepository districtRepository;
    private final TalukRepository talukRepository;
    private final VillageRepository villageRepository;
    private final WardRepository wardRepository;

    public LocationService(StateRepository stateRepository,
                           DistrictRepository districtRepository,
                           TalukRepository talukRepository,
                           VillageRepository villageRepository,
                           WardRepository wardRepository) {
        this.stateRepository = stateRepository;
        this.districtRepository = districtRepository;
        this.talukRepository = talukRepository;
        this.villageRepository = villageRepository;
        this.wardRepository = wardRepository;
    }

    public List<State> getAllStates() {
        return stateRepository.findAll();
    }

    public List<District> getDistrictsByState(Long stateId) {
        return districtRepository.findByState_Id(stateId); // updated
    }

    public List<Taluk> getTaluksByDistrict(Long districtId) {
        return talukRepository.findByDistrict_Id(districtId); // updated
    }

    public List<Village> getVillagesByTaluk(Long talukId) {
        return villageRepository.findByTaluk_Id(talukId); // updated
    }

    public List<Ward> getWardsByVillage(Long villageId) {
        return wardRepository.findByVillage_Id(villageId); // updated
    }
}
