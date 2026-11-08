package com.airways.service;

import com.airways.model.Passenger;
import com.airways.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PassengerService {
    private final PassengerRepository passengerRepository;
    
    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }
    
    public Passenger getPassengerById(Long id) {
        return passengerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Passenger not found"));
    }
    
    public Passenger getPassengerByEmail(String email) {
        return passengerRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Passenger not found"));
    }
    
    public Passenger createPassenger(Passenger passenger) {
        return passengerRepository.save(passenger);
    }
    
    public Passenger updatePassenger(Long id, Passenger passengerDetails) {
        Passenger passenger = getPassengerById(id);
        passenger.setFirstName(passengerDetails.getFirstName());
        passenger.setLastName(passengerDetails.getLastName());
        passenger.setEmail(passengerDetails.getEmail());
        passenger.setPhone(passengerDetails.getPhone());
        passenger.setPassportNumber(passengerDetails.getPassportNumber());
        passenger.setDateOfBirth(passengerDetails.getDateOfBirth());
        passenger.setGender(passengerDetails.getGender());
        passenger.setNationality(passengerDetails.getNationality());
        return passengerRepository.save(passenger);
    }
    
    public void deletePassenger(Long id) {
        passengerRepository.deleteById(id);
    }
}
