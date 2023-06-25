package org.binar.kamihikoukiairlines.service;

import lombok.extern.slf4j.Slf4j;
import org.binar.kamihikoukiairlines.model.Aircraft;
import org.binar.kamihikoukiairlines.model.Airport;
import org.binar.kamihikoukiairlines.model.Route;
import org.binar.kamihikoukiairlines.repository.AircraftRepository;
import org.binar.kamihikoukiairlines.repository.AirportRepository;
import org.binar.kamihikoukiairlines.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RouteService {
    @Autowired
    RouteRepository routeRepository;

    @Autowired
    AirportRepository airportRepository;

    @Autowired
    AircraftRepository aircraftRepository;

    @Transactional
    public Route addRoute(Long departureAirportId, Long arrivalAirportId, Long aircraftId) throws Exception {
        Airport departureAirport = airportRepository.findById(departureAirportId)
                .orElseThrow(() -> new Exception("Departure airport not found with ID: " + departureAirportId));
        Airport arrivalAirport = airportRepository.findById(arrivalAirportId)
                .orElseThrow(() -> new Exception("Arrival airport not found with ID: " + arrivalAirportId));
        Aircraft aircraft = aircraftRepository.findById(aircraftId)
                .orElseThrow(() -> new Exception("Aircraft not found with ID: " + aircraftId));

        Route route = new Route();
        route.setDeparture(departureAirport);
        route.setArrival(arrivalAirport);
        route.setAircraftDetail(aircraft);
        log.info("Has successfully add route data!");
        return routeRepository.save(route);
    }

    public List<Route> getAllRoute(){
        log.info("Has successfully found all route data!");
        return routeRepository.findAll();
    }

    public Optional<Route> getRouteById(Long id) {
        log.info("Has successfully found route data by route id!");
        return routeRepository.findById(id);
    }

    public Route updateRoute(Long routeId, Long departureAirportId, Long arrivalAirportId, Long aircraftId) throws Exception {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new Exception("Route not found with ID: " + routeId));

        Airport departureAirport = airportRepository.findById(departureAirportId)
                .orElseThrow(() -> new Exception("Departure airport not found with ID: " + departureAirportId));
        Airport arrivalAirport = airportRepository.findById(arrivalAirportId)
                .orElseThrow(() -> new Exception("Arrival airport not found with ID: " + arrivalAirportId));
        Aircraft aircraft = aircraftRepository.findById(aircraftId)
                .orElseThrow(() -> new Exception("Aircraft not found with ID: " + aircraftId));

        route.setDeparture(departureAirport);
        route.setArrival(arrivalAirport);
        route.setAircraftDetail(aircraft);
        log.info("Has successfully update route data!");
        return routeRepository.save(route);
    }

    public void deletRoute(Long id) {
        log.info("Has successfully delete route data!");
        airportRepository.deleteById(id);
    }
}


