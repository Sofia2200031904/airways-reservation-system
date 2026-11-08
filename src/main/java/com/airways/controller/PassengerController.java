package com.airways.controller;

import com.airways.model.Passenger;
import com.airways.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/passengers")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class PassengerController {
    private final PassengerService passengerService;
    
    @GetMapping
    public ResponseEntity<List<Passenger>> getAllPassengers() {
        return ResponseEntity.ok(passengerService.getAllPassengers());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Passenger> getPassengerById(@PathVariable Long id) {
        return ResponseEntity.ok(passengerService.getPassengerById(id));
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<Passenger> getPassengerByEmail(@PathVariable String email) {
        return ResponseEntity.ok(passengerService.getPassengerByEmail(email));
    }
    
    @PostMapping
    public ResponseEntity<Passenger> createPassenger(@RequestBody Passenger passenger) {
        return ResponseEntity.ok(passengerService.createPassenger(passenger));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Passenger> updatePassenger(@PathVariable Long id, @RequestBody Passenger passenger) {
        return ResponseEntity.ok(passengerService.updatePassenger(id, passenger));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        passengerService.deletePassenger(id);
        return ResponseEntity.noContent().build();
    }
}
