package com.airways.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String bookingReference;
    
    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;
    
    @ManyToOne
    @JoinColumn(name = "passenger_id", nullable = false)
    private Passenger passenger;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @Enumerated(EnumType.STRING)
    private SeatClass seatClass;
    
    @Column(nullable = false)
    private String seatNumber;
    
    @Column(nullable = false)
    private BigDecimal totalPrice;
    
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
    
    @Column(nullable = false)
    private LocalDateTime bookingDate;
    
    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private Payment payment;
    
    public enum SeatClass {
        ECONOMY, BUSINESS, FIRST_CLASS
    }
    
    public enum BookingStatus {
        CONFIRMED, PENDING, CANCELLED, COMPLETED
    }
}
