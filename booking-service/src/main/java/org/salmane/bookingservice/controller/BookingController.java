package org.salmane.bookingservice.controller;

import lombok.RequiredArgsConstructor;
import org.salmane.bookingservice.Enum.BookingStatus;
import org.salmane.bookingservice.dto.ReservationRequest;
import org.salmane.bookingservice.dto.BookingResponse;
import org.salmane.bookingservice.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/{id}")
    public BookingResponse getBookingById(@PathVariable("id") String id) {
        return bookingService.getBookingById(id);
    }

    @GetMapping("/user/{userId}")
    public List<BookingResponse> getBookingsByUserId(@PathVariable("userId") String userId) {
        return bookingService.getBookingByUserId(userId);
    }

    @DeleteMapping("/{id}")
    public void deleteBookingById(@PathVariable("id") String id) {
        bookingService.deleteBookingById(id);
    }

    @PutMapping("/{id}/status")
    public BookingResponse updateBookingStatus(@PathVariable("id") String id, @RequestBody BookingStatus status) {
        return bookingService.updateBookingStatus(id, status);
    }

    @PostMapping("/reserve")
    public String reserveTicket(@RequestBody ReservationRequest reservationRequest) {
        return bookingService.reserveTicket(reservationRequest);
    }

    @GetMapping("/{id}/confirm")
    public ResponseEntity<String> confirmBooking(@PathVariable("id") String id) {
        if(bookingService.confirmBooking(id) != null) {
            return ResponseEntity.ok("Booking confirmed");
        } else {
            return new ResponseEntity<>("failed to confirm booking", HttpStatus.BAD_REQUEST);
        }
    }

}
