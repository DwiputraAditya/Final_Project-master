package org.binar.kamihikoukiairlines.repository;

import org.binar.kamihikoukiairlines.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findBookingById(Long bookingId);
    List<Booking> findAllByUsersIdAndIsSuccess(Long userId, Boolean isSuccess);
    List<Booking> findAllByUsersId(Long userId);
    Booking findBookingByBookingCode(String booking);
}
