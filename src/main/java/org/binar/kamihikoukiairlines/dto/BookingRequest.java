package org.binar.kamihikoukiairlines.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class BookingRequest {

    @NotBlank
    private Long usersId;
    @NotBlank
    private Long scheduleId;
    @NotBlank
    private List<Long> passengerId;

}