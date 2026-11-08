package com.airways.controller;

import com.airways.dto.BookingRequestDTO;
import com.airways.dto.BookingResponseDTO;
import com.airways.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class BookingController {
    private final BookingService bookingService;
    
    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(@RequestBody BookingRequestDTO request) {
        return ResponseEntity.ok(bookingService.createBooking(request));
    }
    
    @GetMapping("/{reference}")
    public ResponseEntity<BookingResponseDTO> getBookingByReference(@PathVariable String reference) {
        return ResponseEntity.ok(bookingService.getBookingByReference(reference));
    }
    
    @GetMapping
    public ResponseEntity<List<BookingResponseDTO>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }
    
    @DeleteMapping("/{reference}")
    public ResponseEntity<Void> cancelBooking(@PathVariable String reference) {
        bookingService.cancelBooking(reference);
        return ResponseEntity.noContent().build();
    }
}
