package org.binar.kamihikoukiairlines.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.binar.kamihikoukiairlines.dto.PassengerRequest;
import org.binar.kamihikoukiairlines.model.Passenger;
import org.binar.kamihikoukiairlines.model.Schedule;
import org.binar.kamihikoukiairlines.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passenger")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Passenger", description = "Passenger Controller | contains : Add Passengers/Add All Passenger")
public class PassengerController {

    @Autowired
    PassengerService passengerService;

    @GetMapping("/getAllPassengers")
    public List<Passenger> getAllPassengers(){
        return passengerService.getAllPassenger();
    }

    @PostMapping("/addPassengers")
    public ResponseEntity<List<Passenger>> addPassengers(@RequestBody List<PassengerRequest> passengerRequests) {
        try {
            List<Passenger> passengers = passengerService.addPassengers(passengerRequests);
            return ResponseEntity.ok(passengers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}