package com.airways.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "aircraft")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aircraft {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String registrationNumber;
    
    @Column(nullable = false)
    private String model;
    
    @Column(nullable = false)
    private String manufacturer;
    
    @Column(nullable = false)
    private Integer totalSeats;
    
    @Column(nullable = false)
    private Integer economySeats;
    
    @Column(nullable = false)
    private Integer businessSeats;
    
    @Column(nullable = false)
    private Integer firstClassSeats;
    
    @Enumerated(EnumType.STRING)
    private AircraftStatus status;
    
    public enum AircraftStatus {
        ACTIVE, MAINTENANCE, RETIRED
    }
}
