package org.binar.kamihikoukiairlines.service;

import lombok.extern.slf4j.Slf4j;
import org.binar.kamihikoukiairlines.dto.BookingRequest;
import org.binar.kamihikoukiairlines.dto.PaymentDTO;
import org.binar.kamihikoukiairlines.model.Booking;
import org.binar.kamihikoukiairlines.model.Passenger;
import org.binar.kamihikoukiairlines.model.Schedule;
import org.binar.kamihikoukiairlines.model.Users;
import org.binar.kamihikoukiairlines.repository.BookingRepository;
import org.binar.kamihikoukiairlines.repository.PassengerRepository;
import org.binar.kamihikoukiairlines.repository.ScheduleRepository;
import org.binar.kamihikoukiairlines.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PassengerRepository passengerRepository;


    public List<Booking> getAllBooking(){
        log.info("Has successfully found all booking ticket!");
        return bookingRepository.findAll();
    }

//    public Booking bookTicket(Long userId, Long scheduleId) throws Exception {
//        Users users = userRepository.findById(userId)
//                .orElseThrow(() -> new Exception("User Id Not Found"));
//        Schedule schedule = scheduleRepository.findById(scheduleId)
//                .orElseThrow(() -> new Exception("Schedule Id Not Found"));
//
//        LocalDateTime dueDate = LocalDateTime.now().plusHours(2);
//        Booking booking = new Booking();
//        booking.setUsers(users);
//        booking.setSchedule(schedule);
//        booking.setBookingCode(generateBookingCode());
//        booking.setDueValid(dueDate);
//        booking.setIsSuccess(false);
//        booking.setIsValid(true);
//        log.info("Has successfully booking ticket!");
//        return bookingRepository.save(booking);
//    }

    @Transactional
    public Booking createBooking(BookingRequest bookingRequest) throws Exception {
        // Mendapatkan data pengguna berdasarkan ID
        Users user = userRepository.findById(bookingRequest.getUsersId())
                .orElseThrow(() -> new Exception("User not found"));

        // Mendapatkan data jadwal penerbangan berdasarkan ID
        Schedule schedule = scheduleRepository.findById(bookingRequest.getScheduleId())
                .orElseThrow(() -> new Exception("Schedule not found"));

        // Mendapatkan data penumpang berdasarkan ID yang diberikan
        List<Passenger> passengers = passengerRepository.findAllById(bookingRequest.getPassengerId());

        // Membuat objek Booking
        LocalDateTime dueDate = LocalDateTime.now().plusHours(2);
        Booking booking = new Booking();
        booking.setUsers(user);
        booking.setSchedule(schedule);
        booking.setPassengersList(passengers);
        booking.setBookingCode(generateBookingCode());
        booking.setDueValid(dueDate);
        booking.setIsSuccess(false);
        booking.setIsValid(true);

        log.info("Has successfully add booking data!");
        return bookingRepository.save(booking);
    }


    public Booking payment(PaymentDTO paymentDTO) {
        Booking booking = bookingRepository.findById(paymentDTO.getBookingId()).orElseThrow(() -> {
            throw new NotFoundException("Booking Id Not Found");
        });

        if (Boolean.TRUE.equals(booking.getIsSuccess())){
            HashMap<String, String> errorMessage = new HashMap<>();
            errorMessage.put("ERROR", "booking with "+booking.getBookingCode() + " code has successfully paid");
            throw new RuntimeException(String.valueOf(errorMessage));
        }else {
            if (LocalDateTime.now().isAfter(booking.getDueValid())) {
                booking.setIsValid(false);
                HashMap<String, String> errorMessage = new HashMap<>();
                errorMessage.put("ERROR", "booking code is invalid");
                throw new RuntimeException(String.valueOf(errorMessage));
            }else {
                booking.setIsSuccess(true);
                booking.setIsValid(false);
                booking.setPaymentMethod(paymentDTO.getPaymentMethod());
            }
        }
        log.info("Has successfully make a payment!");
        return bookingRepository.save(booking);
    }

    public Booking getBookingById(Long bookingId) throws Exception {
        log.info("Has successfully found booking by booking id!");
        return bookingRepository.findBookingById(bookingId)
                .orElseThrow(() -> new Exception("Booking Id Not Found"));
    }

    public List<Booking> findAllByUsersIdAndIsSuccess(Long userId, Boolean isSuccess) {
        log.info("Has successfully found booking history by user id and success!");
        return bookingRepository.findAllByUsersIdAndIsSuccess(userId, isSuccess);
    }

    public List<Booking> findAllByUsersId(Long userId) {
        log.info("Has successfully found history by user id!");
        return bookingRepository.findAllByUsersId(userId);
    }

    public Booking findBookingByBookingCode(String bookingCode) {
        log.info("Has successfully found booking by booking code!");
        return bookingRepository.findBookingByBookingCode(bookingCode);
    }

    private String generateBookingCode() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }

}