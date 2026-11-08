package com.airways.repository;

import com.airways.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByBookingReference(String bookingReference);
    List<Booking> findByPassengerId(Long passengerId);
    List<Booking> findByFlightId(Long flightId);
    List<Booking> findByUserId(Long userId);
    List<Booking> findByStatus(Booking.BookingStatus status);
}
