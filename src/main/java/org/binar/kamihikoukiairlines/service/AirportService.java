package org.binar.kamihikoukiairlines.service;

import lombok.extern.slf4j.Slf4j;
import org.binar.kamihikoukiairlines.dto.AirportRequest;
import org.binar.kamihikoukiairlines.model.Airport;
import org.binar.kamihikoukiairlines.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AirportService {
    @Autowired
    AirportRepository airportRepository;


    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }


    public List<Airport> getAllAirport() {
        log.info("Has successfully found all airport data!");
        return airportRepository.findAll();
    }

    public Airport addAirport(AirportRequest airportRequest){
        Airport airport = new Airport();
        airport.setAirportName(airportRequest.getAirportName());
        airport.setAirportCode(airportRequest.getAirportCode());
        airport.setCityName(airportRequest.getCityName());
        airport.setCityCode(airportRequest.getCityCode());
        airport.setCountryName(airportRequest.getCountryName());
        airport.setCountryCode(airportRequest.getCountryCode());
        log.info("Has successfully add airport data!");
        return airportRepository.save(airport);
    }

    public Airport updateAirport(Long id, Airport airport){
        Airport tempat = airportRepository.findById(id).get();
        tempat.setAirportCode(airport.getAirportCode());
        tempat.setAirportName(airport.getAirportName());
        tempat.setCityCode(airport.getCityCode());
        tempat.setCityName(airport.getCityName());
        tempat.setCountryCode(airport.getCountryCode());
        tempat.setCityName(airport.getCityName());
        log.info("Has successfully update airport data!");
        return airportRepository.save(tempat);
    }

    public void deletAirport(Long id) {
        log.info("Has successfully delete airport data!");
        airportRepository.deleteById(id);
    }
}
