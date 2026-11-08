package com.airways.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDTO {
    private String bookingReference;
    private String flightNumber;
    private String departureAirport;
    private String arrivalAirport;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String passengerName;
    private String seatNumber;
    private String seatClass;
    private BigDecimal totalPrice;
    private String status;
    private LocalDateTime bookingDate;
}
