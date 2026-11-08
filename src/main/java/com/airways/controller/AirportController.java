package com.airways.controller;

import com.airways.model.Airport;
import com.airways.model.City;
import com.airways.model.Country;
import com.airways.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/airports")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AirportController {
    private final AirportService airportService;
    
    @GetMapping
    public ResponseEntity<List<Airport>> getAllAirports() {
        return ResponseEntity.ok(airportService.getAllAirports());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Airport> getAirportById(@PathVariable Long id) {
        return ResponseEntity.ok(airportService.getAirportById(id));
    }
    
    @GetMapping("/code/{code}")
    public ResponseEntity<Airport> getAirportByCode(@PathVariable String code) {
        return ResponseEntity.ok(airportService.getAirportByCode(code));
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Airport>> searchAirports(@RequestParam String name) {
        return ResponseEntity.ok(airportService.searchAirports(name));
    }
    
    @PostMapping
    public ResponseEntity<Airport> createAirport(@RequestBody Airport airport) {
        return ResponseEntity.ok(airportService.createAirport(airport));
    }
    
    @GetMapping("/countries")
    public ResponseEntity<List<Country>> getAllCountries() {
        return ResponseEntity.ok(airportService.getAllCountries());
    }
    
    @GetMapping("/countries/{countryId}/cities")
    public ResponseEntity<List<City>> getCitiesByCountry(@PathVariable Long countryId) {
        return ResponseEntity.ok(airportService.getCitiesByCountry(countryId));
    }
}
