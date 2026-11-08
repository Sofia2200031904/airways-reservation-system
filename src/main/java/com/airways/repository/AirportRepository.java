package com.airways.repository;

import com.airways.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {
    Optional<Airport> findByCode(String code);
    List<Airport> findByCityId(Long cityId);
    List<Airport> findByNameContaining(String name);
}
