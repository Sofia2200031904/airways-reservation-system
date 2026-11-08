package com.airways.service;

import com.airways.dto.BookingRequestDTO;
import com.airways.dto.BookingResponseDTO;
import com.airways.model.*;
import com.airways.repository.BookingRepository;
import com.airways.repository.PassengerRepository;
import com.airways.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {
    private final BookingRepository bookingRepository;
    private final PassengerRepository passengerRepository;
    private final PaymentRepository paymentRepository;
    private final FlightService flightService;
    
    public BookingResponseDTO createBooking(BookingRequestDTO request) {
        // Get flight
        Flight flight = flightService.getFlightById(request.getFlightId());
        
        // Check availability
        if (flight.getAvailableSeats() <= 0) {
            throw new RuntimeException("No seats available");
        }
        
        // Create or get passenger
        Passenger passenger = passengerRepository.findByEmail(request.getEmail())
            .orElseGet(() -> {
                Passenger newPassenger = new Passenger();
                newPassenger.setFirstName(request.getFirstName());
                newPassenger.setLastName(request.getLastName());
                newPassenger.setEmail(request.getEmail());
                newPassenger.setPhone(request.getPhone());
                newPassenger.setPassportNumber(request.getPassportNumber());
                newPassenger.setDateOfBirth(request.getDateOfBirth());
                newPassenger.setGender(Passenger.Gender.valueOf(request.getGender()));
                newPassenger.setNationality(request.getNationality());
                return passengerRepository.save(newPassenger);
            });
        
        // Create booking
        Booking booking = new Booking();
        booking.setBookingReference(generateBookingReference());
        booking.setFlight(flight);
        booking.setPassenger(passenger);
        booking.setSeatClass(Booking.SeatClass.valueOf(request.getSeatClass()));
        booking.setSeatNumber(generateSeatNumber());
        booking.setBookingDate(LocalDateTime.now());
        booking.setStatus(Booking.BookingStatus.CONFIRMED);
        
        // Calculate price
        BigDecimal price = calculatePrice(flight, booking.getSeatClass());
        booking.setTotalPrice(price);
        
        booking = bookingRepository.save(booking);
        
        // Update available seats
        flight.setAvailableSeats(flight.getAvailableSeats() - 1);
        flightService.updateFlight(flight.getId(), flight);
        
        // Create payment
        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(price);
        payment.setPaymentMethod(Payment.PaymentMethod.valueOf(request.getPaymentMethod()));
        payment.setStatus(Payment.PaymentStatus.COMPLETED);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setTransactionId(generateTransactionId());
        paymentRepository.save(payment);
        
        return convertToResponseDTO(booking);
    }
    
    public BookingResponseDTO getBookingByReference(String reference) {
        Booking booking = bookingRepository.findByBookingReference(reference)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
        return convertToResponseDTO(booking);
    }
    
    public List<BookingResponseDTO> getAllBookings() {
        return bookingRepository.findAll().stream()
            .map(this::convertToResponseDTO)
            .collect(Collectors.toList());
    }
    
    public void cancelBooking(String reference) {
        Booking booking = bookingRepository.findByBookingReference(reference)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
        
        booking.setStatus(Booking.BookingStatus.CANCELLED);
        bookingRepository.save(booking);
        
        // Update available seats
        Flight flight = booking.getFlight();
        flight.setAvailableSeats(flight.getAvailableSeats() + 1);
        flightService.updateFlight(flight.getId(), flight);
    }
    
    private String generateBookingReference() {
        return "BK" + System.currentTimeMillis();
    }
    
    private String generateSeatNumber() {
        Random random = new Random();
        int row = random.nextInt(30) + 1;
        char seat = (char) ('A' + random.nextInt(6));
        return row + String.valueOf(seat);
    }
    
    private String generateTransactionId() {
        return "TXN" + System.currentTimeMillis();
    }
    
    private BigDecimal calculatePrice(Flight flight, Booking.SeatClass seatClass) {
        return switch (seatClass) {
            case ECONOMY -> flight.getEconomyPrice();
            case BUSINESS -> flight.getBusinessPrice();
            case FIRST_CLASS -> flight.getFirstClassPrice();
        };
    }
    
    private BookingResponseDTO convertToResponseDTO(Booking booking) {
        BookingResponseDTO dto = new BookingResponseDTO();
        dto.setBookingReference(booking.getBookingReference());
        dto.setFlightNumber(booking.getFlight().getFlightNumber());
        dto.setDepartureAirport(booking.getFlight().getDepartureAirport().getName());
        dto.setArrivalAirport(booking.getFlight().getArrivalAirport().getName());
        dto.setDepartureTime(booking.getFlight().getDepartureTime());
        dto.setArrivalTime(booking.getFlight().getArrivalTime());
        dto.setPassengerName(booking.getPassenger().getFirstName() + " " + 
                             booking.getPassenger().getLastName());
        dto.setSeatNumber(booking.getSeatNumber());
        dto.setSeatClass(booking.getSeatClass().toString());
        dto.setTotalPrice(booking.getTotalPrice());
        dto.setStatus(booking.getStatus().toString());
        dto.setBookingDate(booking.getBookingDate());
        return dto;
    }
}
