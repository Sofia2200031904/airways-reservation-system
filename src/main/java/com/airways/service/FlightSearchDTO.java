package com.airways.service;

import lombok.Data;
import java.time.LocalDate;

@Data
public class FlightSearchDTO {
    private Long departureAirportId;
    private Long arrivalAirportId;
    private LocalDate departureDate;
    private Integer passengers;
    private String seatClass;
}
