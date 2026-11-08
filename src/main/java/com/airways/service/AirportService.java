package com.airways.service;

import com.airways.model.Airport;
import com.airways.model.City;
import com.airways.model.Country;
import com.airways.repository.AirportRepository;
import com.airways.repository.CityRepository;
import com.airways.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AirportService {
    private final AirportRepository airportRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    
    public List<Airport> getAllAirports() {
        return airportRepository.findAll();
    }
    
    public Airport getAirportById(Long id) {
        return airportRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Airport not found"));
    }
    
    public Airport getAirportByCode(String code) {
        return airportRepository.findByCode(code)
            .orElseThrow(() -> new RuntimeException("Airport not found"));
    }
    
    public List<Airport> searchAirports(String name) {
        return airportRepository.findByNameContaining(name);
    }
    
    public Airport createAirport(Airport airport) {
        return airportRepository.save(airport);
    }
    
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }
    
    public List<City> getCitiesByCountry(Long countryId) {
        return cityRepository.findByCountryId(countryId);
    }
}
