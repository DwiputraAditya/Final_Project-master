package org.binar.kamihikoukiairlines.service;

import lombok.extern.slf4j.Slf4j;
import org.binar.kamihikoukiairlines.dto.PassengerRequest;
import org.binar.kamihikoukiairlines.model.Booking;
import org.binar.kamihikoukiairlines.model.Passenger;
import org.binar.kamihikoukiairlines.model.Schedule;
import org.binar.kamihikoukiairlines.repository.BookingRepository;
import org.binar.kamihikoukiairlines.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PassengerService {

    @Autowired
    PassengerRepository passengerRepository;
    @Autowired
    BookingRepository bookingRepository;


    public List<Passenger> getAllPassenger() {
        return passengerRepository.findAll();
    }

    @Transactional
    public List<Passenger> addPassengers(List<PassengerRequest> passengerRequests) throws Exception {
        List<Passenger> passengers = new ArrayList<>();
        for (PassengerRequest passengerRequest : passengerRequests) {
            Passenger passenger = new Passenger();
            passenger.setTitle(passengerRequest.getTitle());
            passenger.setName(passengerRequest.getName());
            passenger.setSurname(passengerRequest.getSurname());
            passenger.setBirthday(passengerRequest.getBirthday());
            passenger.setCitizenship(passengerRequest.getCitizenship());
            passenger.setPassport(passengerRequest.getPassport());
            passenger.setCountryOfPublication(passengerRequest.getCountryOfPublication());
            passengers.add(passenger);
        }
        log.info("Has successfully add passengers data!");
        return passengerRepository.saveAll(passengers);
    }

    /*@Transactional
    public List<Passenger> addPassengers(List<PassengerRequest> passengers) throws Exception {
        List<Passenger> addedPassengers = new ArrayList<>();

        for (PassengerRequest passengerRequest : passengers) {
            Booking findBooking = bookingRepository.findById(passengerRequest.getBookingId())
                    .orElseThrow(() -> new Exception("Route not found"));

            Passenger passenger = new Passenger();
            passenger.setTitle(passengerRequest.getTitle());
            passenger.setName(passengerRequest.getName());
            passenger.setSurname(passengerRequest.getSurname());
            passenger.setBirthday(passengerRequest.getBirthday());
            passenger.setCitizenship(passengerRequest.getCitizenship());
            passenger.setPassport(passengerRequest.getPassport());
            passenger.setCountryOfPublication(passengerRequest.getCountryOfPublication());
            passenger.setBooking(findBooking);

            addedPassengers.add(passengerRepository.save(passenger));
        }
        return addedPassengers;
    }*/

    /*public List<Passenger> addAllPassenger(List<Passenger> passengers) {
        List<Passenger> allPassengers = passengerRepository.saveAll(passengers);
        return allPassengers;
    }*/
}