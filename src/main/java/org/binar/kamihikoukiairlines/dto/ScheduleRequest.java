package org.binar.kamihikoukiairlines.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ScheduleRequest {
    @FutureOrPresent
    private LocalDate departureDate;

    @FutureOrPresent
    private LocalDate arrivalDate;

    @JsonFormat(pattern="HH:mm:ss")
    @JsonProperty(defaultValue = "00:00:00")
    private LocalTime departureTime;

    @JsonFormat(pattern="HH:mm:ss")
    @JsonProperty(defaultValue = "00:00:00")
    private LocalTime arrivalTime;

//    private Integer passengerCount;

    private String price;

    @JsonProperty(defaultValue = "ECONOMY")
    private String seatClass;
    private Long routeId;

}
