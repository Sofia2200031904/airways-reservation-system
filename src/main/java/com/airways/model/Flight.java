package com.airways.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "flights")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String flightNumber;
    
    @ManyToOne
    @JoinColumn(name = "departure_airport_id", nullable = false)
    private Airport departureAirport;
    
    @ManyToOne
    @JoinColumn(name = "arrival_airport_id", nullable = false)
    private Airport arrivalAirport;
    
    @Column(nullable = false)
    private LocalDateTime departureTime;
    
    @Column(nullable = false)
    private LocalDateTime arrivalTime;
    
    @ManyToOne
    @JoinColumn(name = "aircraft_id", nullable = false)
    private Aircraft aircraft;
    
    @Column(nullable = false)
    private BigDecimal economyPrice;
    
    @Column(nullable = false)
    private BigDecimal businessPrice;
    
    @Column(nullable = false)
    private BigDecimal firstClassPrice;
    
    @Column(nullable = false)
    private Integer availableSeats;
    
    @Enumerated(EnumType.STRING)
    private FlightStatus status;
    
    private String gate;
    
    public enum FlightStatus {
        SCHEDULED, BOARDING, DEPARTED, ARRIVED, DELAYED, CANCELLED
    }
}
