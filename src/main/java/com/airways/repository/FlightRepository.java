package com.airways.repository;

import com.airways.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    Optional<Flight> findByFlightNumber(String flightNumber);
    
    @Query("SELECT f FROM Flight f WHERE f.departureAirport.id = :depAirportId " +
           "AND f.arrivalAirport.id = :arrAirportId " +
           "AND f.departureTime BETWEEN :startDate AND :endDate " +
           "AND f.status = 'SCHEDULED' " +
           "AND f.availableSeats > 0")
    List<Flight> searchFlights(
        @Param("depAirportId") Long depAirportId,
        @Param("arrAirportId") Long arrAirportId,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );
    
    List<Flight> findByDepartureAirportIdAndDepartureTimeBetween(
        Long airportId, LocalDateTime start, LocalDateTime end
    );
    
    List<Flight> findByStatus(Flight.FlightStatus status);
}
