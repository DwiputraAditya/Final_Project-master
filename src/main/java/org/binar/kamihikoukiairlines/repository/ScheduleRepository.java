package org.binar.kamihikoukiairlines.repository;

import org.binar.kamihikoukiairlines.model.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    /*@Query("SELECT s FROM Schedule s JOIN s.route r WHERE r.departure.airportName = :departure AND r.arrival.airportName = :arrival AND r.aircraftDetail.seatCapacity >= :seatAvailable AND s.departureDate = :departureDate AND s.seatClass = :seatClass")
    Page<Schedule> findByDepartureAndArrivalAndDepartureDateAndSeatClass(
            @Param("departure") String departure,
            @Param("arrival") String arrival,
            @Param("departureDate") LocalDate departureDate,
            @Param("seatAvailable") Integer seatAvailable,
            @Param("seatClass") String seatClass,
            Pageable pageable);*/

    @Query("SELECT s FROM Schedule s JOIN s.route r WHERE r.departure.cityName = :departure AND r.arrival.cityName = :arrival AND r.aircraftDetail.seatCapacity >= :seatAvailable AND s.departureDate = :departureDate AND s.seatClass = :seatClass")
    Page<Schedule> findByDepartureAndArrivalAndDepartureDateAndSeatClass(
            @Param("departure") String departure,
            @Param("arrival") String arrival,
            @Param("departureDate") LocalDate departureDate,
            @Param("seatAvailable") Integer seatAvailable,
            @Param("seatClass") String seatClass,
            Pageable pageable);

}
