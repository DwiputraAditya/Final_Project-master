package org.binar.kamihikoukiairlines.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.binar.kamihikoukiairlines.dto.BookingRequest;
import org.binar.kamihikoukiairlines.dto.PaymentDTO;
import org.binar.kamihikoukiairlines.model.Booking;
import org.binar.kamihikoukiairlines.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Booking", description = "Booking Controller | contains : Payment, Add Booking, Get All Booking, Get Booking By Id, Get History Booking By User Id, Get Booking By User Id and Success, Get Booking By Booking Code")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @GetMapping("/getAllBooking")
    public List<Booking> getAllBooking(){
        return bookingService.getAllBooking();
    }

    @PostMapping("/addBooking")
    public ResponseEntity<Booking> bookTicket(@RequestBody BookingRequest bookingRequest) {
        try {
            Booking newBooking = bookingService.createBooking(bookingRequest);
            return ResponseEntity.ok(newBooking);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @PostMapping("/payment")
    public ResponseEntity<?> payment(@RequestBody PaymentDTO paymentDTO) {
        try {
            Booking booking = bookingService.payment(paymentDTO);
            return ResponseEntity.ok(booking);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/bookingById/{bookingId}")
    public ResponseEntity<Booking> getBookingHistoryById(@PathVariable Long bookingId) {
        try {
            Booking booking = bookingService.getBookingById(bookingId);
            return ResponseEntity.ok(booking);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/successfulBookingsHistory/{userId}")
    public List<Booking> getAllSuccessfulBookingsByUserId(@PathVariable Long userId) {
        return bookingService.findAllByUsersIdAndIsSuccess(userId, true);
    }

    @GetMapping("/historyById/{userId}")
    public List<Booking> getAllBookingsByUserId(@PathVariable Long userId) {
        return bookingService.findAllByUsersId(userId);
    }

    @GetMapping("/getHistoryByBookingCode/{bookingCode}")
    public ResponseEntity<Booking> findBookingByBookingCode(@PathVariable String bookingCode) {
        Booking booking = bookingService.findBookingByBookingCode(bookingCode);
        if (booking != null) {
            return ResponseEntity.ok(booking);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}