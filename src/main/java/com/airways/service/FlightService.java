package com.airways.service;

import com.airways.dto.FlightSearchDTO;
import com.airways.model.Flight;
import com.airways.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FlightService {
    private final FlightRepository flightRepository;
    
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }
    
    public Flight getFlightById(Long id) {
        return flightRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Flight not found"));
    }
    
    public Flight getFlightByNumber(String flightNumber) {
        return flightRepository.findByFlightNumber(flightNumber)
            .orElseThrow(() -> new RuntimeException("Flight not found"));
    }
    
    public List<Flight> searchFlights(FlightSearchDTO searchDTO) {
        LocalDateTime startDateTime = searchDTO.getDepartureDate().atStartOfDay();
        LocalDateTime endDateTime = searchDTO.getDepartureDate().atTime(LocalTime.MAX);
        
        return flightRepository.searchFlights(
            searchDTO.getDepartureAirportId(),
            searchDTO.getArrivalAirportId(),
            startDateTime,
            endDateTime
        );
    }
    
    public Flight createFlight(Flight flight) {
        flight.setAvailableSeats(flight.getAircraft().getTotalSeats());
        flight.setStatus(Flight.FlightStatus.SCHEDULED);
        return flightRepository.save(flight);
    }
    
    public Flight updateFlight(Long id, Flight flightDetails) {
        Flight flight = getFlightById(id);
        flight.setDepartureTime(flightDetails.getDepartureTime());
        flight.setArrivalTime(flightDetails.getArrivalTime());
        flight.setEconomyPrice(flightDetails.getEconomyPrice());
        flight.setBusinessPrice(flightDetails.getBusinessPrice());
        flight.setFirstClassPrice(flightDetails.getFirstClassPrice());
        flight.setStatus(flightDetails.getStatus());
        flight.setGate(flightDetails.getGate());
        return flightRepository.save(flight);
    }
    
    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }
}
