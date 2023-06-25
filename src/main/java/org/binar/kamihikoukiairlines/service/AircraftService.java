package org.binar.kamihikoukiairlines.service;

import lombok.extern.slf4j.Slf4j;
import org.binar.kamihikoukiairlines.model.Aircraft;
import org.binar.kamihikoukiairlines.repository.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AircraftService {

    @Autowired
    AircraftRepository aircraftRepository;


    public AircraftService(AircraftRepository aircraftRepository) {
        this.aircraftRepository = aircraftRepository;
    }


    public List<Aircraft> getAllAircraft() {
        log.info("Has successfully found all aircraft data!");
        return aircraftRepository.findAll();
    }

    public Aircraft addAircraft(Aircraft aircraft){
        log.info("Has successfully add aircraft!");
        return aircraftRepository.save(aircraft);
    }

    public Aircraft updateAircraft(Long id, Aircraft aircraft){
        Aircraft pesawat = aircraftRepository.findById(id).get();
        pesawat.setAircraftName(aircraft.getAircraftName());
        log.info("Has successfully update aircraft data!");
        return aircraftRepository.save(pesawat);
    }

    public void deleteAircraft(Long id) {
        log.info("Has successfully delete aircraft data!");
        aircraftRepository.deleteById(id);
    }
}
