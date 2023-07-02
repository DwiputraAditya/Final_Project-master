package org.binar.kamihikoukiairlines.service;

import lombok.extern.slf4j.Slf4j;
import org.binar.kamihikoukiairlines.dto.ScheduleRequest;
import org.binar.kamihikoukiairlines.model.Route;
import org.binar.kamihikoukiairlines.model.Schedule;
import org.binar.kamihikoukiairlines.repository.AirportRepository;
import org.binar.kamihikoukiairlines.repository.RouteRepository;
import org.binar.kamihikoukiairlines.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ScheduleService {

    @Autowired
    RouteRepository routeRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    AirportRepository airportRepository;


    @Transactional
    public Schedule addSchedule(ScheduleRequest scheduleRequest) throws Exception {

        Route findRoute = routeRepository.findById(scheduleRequest.getRouteId())
                .orElseThrow(() -> new Exception("Route Not Found"));

        Schedule schedule = new Schedule();
        schedule.setArrivalDate(scheduleRequest.getArrivalDate());
        schedule.setArrivalTime(scheduleRequest.getArrivalTime());
        schedule.setDepartureDate(scheduleRequest.getDepartureDate());
        schedule.setDepartureTime(scheduleRequest.getDepartureTime());
        schedule.setPrice(scheduleRequest.getPrice());
        schedule.setSeatClass(scheduleRequest.getSeatClass());
        schedule.setRoute(findRoute);
        log.info("Has successfully add schedule data!");
        return scheduleRepository.save(schedule);

    }
    public List<Schedule> getAllSchedules() {
        log.info("Has successfully found all schedule data!");
        return scheduleRepository.findAll();
    }

    public Optional<Schedule> getScheduleById(Long id) {
        log.info("Has successfully found schedule data by schedule id!");
        return scheduleRepository.findById(id);
    }

    public Page<Schedule> searchFlightWithoutArrival(String departure, String arrival, LocalDate date, Integer seatAvailable,  String seatClass, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        log.info("Has successfully search flight data!");
        return scheduleRepository.findByDepartureAndArrivalAndDepartureDateAndSeatClass(departure, arrival, date, seatAvailable, seatClass, pageable);
    }
}
