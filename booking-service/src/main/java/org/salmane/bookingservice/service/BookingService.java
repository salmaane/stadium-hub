package org.salmane.bookingservice.service;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.salmane.bookingservice.Enum.BookingStatus;
import org.salmane.bookingservice.client.TicketClient;
import org.salmane.bookingservice.dao.BookingDAO;
import org.salmane.bookingservice.dto.ConfirmationRequest;
import org.salmane.bookingservice.dto.ReservationRequest;
import org.salmane.bookingservice.dto.BookingResponse;
import org.salmane.bookingservice.exception.BookingNotFoundException;
import org.salmane.bookingservice.model.Booking;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

    private final BookingDAO bookingDAO;
    private final TicketClient ticketClient;

    public BookingResponse getBookingById(String id) {
        Booking booking = bookingDAO.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking " + id + " not found."));

        return new BookingResponse(
                booking.getId(), booking.getUserId(), booking.getMatchId(), booking.getTicketId(),
                booking.getStatus(), booking.getBookingDate(),
                booking.getConfirmationDate(), booking.getCancellationDate()
        );
    }

    public List<BookingResponse> getBookingByUserId(String userId) {
        List<Booking> bookings = bookingDAO.findByUserId(userId);

        if(bookings.isEmpty()) {
            throw new BookingNotFoundException("No bookings of user " + userId + " found.");
        }

        return bookings.stream().map(booking -> new BookingResponse(
                booking.getId(), booking.getUserId(), booking.getMatchId(), booking.getTicketId(),
                booking.getStatus(), booking.getBookingDate(),
                booking.getConfirmationDate(), booking.getCancellationDate()
        )).toList();
    }

    public void deleteBookingById(String id) {
        bookingDAO.deleteById(id);
    }

    public BookingResponse updateBookingStatus(String id, BookingStatus status) {
        Booking booking = bookingDAO.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking " + id + " not found."));

        booking.setStatus(status);
        bookingDAO.save(booking);

        return new BookingResponse(
                booking.getId(), booking.getUserId(), booking.getMatchId(), booking.getTicketId(),
                booking.getStatus(), booking.getBookingDate(),
                booking.getConfirmationDate(), booking.getCancellationDate()
        );
    }

    @Retry(name = "reserveTicketRetry", fallbackMethod = "reserveTicketFallback")
    public String reserveTicket(ReservationRequest reservationRequest) {
            String ticketId = ticketClient.reserveTicket(reservationRequest);

        if(ticketId == null) {
            log.warn("No tickets available for match {}", reservationRequest.matchId());
            return null;
        }

        Booking booking = Booking.builder()
                .id(UUID.randomUUID().toString())
                .matchId(reservationRequest.matchId())
                .ticketId(ticketId)
                .status(BookingStatus.PENDING)
                .bookingDate(LocalDateTime.now())
                .userId(reservationRequest.userId())
                .build();

        bookingDAO.save(booking);
        log.info("Booking created with ID {} for user {}", booking.getId(), reservationRequest.userId());
        return booking.getId();
    }

    public String reserveTicketFallback(ReservationRequest reservationRequest, Throwable t) {
        log.error("Failed to reserve ticket after retries for match {}: {}", reservationRequest.matchId(), t.getMessage());
        return null;
    }

    public String confirmBooking(String id) {
        Booking booking = bookingDAO.findById(id).orElse(null);

        if(booking == null || !booking.getStatus().equals(BookingStatus.PENDING)) {
            log.error("Booking {} not found.", id);
            return null;
        }

        if(!ticketClient.confirmTicketPurchase(new ConfirmationRequest(booking.getTicketId(), booking.getUserId()))) {
            log.error("Failed to confirm ticket {} reservation", id);
            return null;
        }

        booking.setStatus(BookingStatus.CONFIRMED);
        bookingDAO.save(booking);

        return booking.getId();
    }

    public String cancelBooking(String id) {
        Booking booking = bookingDAO.findById(id).orElse(null);

        if(booking == null || !booking.getStatus().equals(BookingStatus.PENDING)) {
            log.error("Booking {} not found.", id);
            return null;
        }

        if(!ticketClient.cancelTicketReservation(new ConfirmationRequest(booking.getTicketId(), booking.getUserId()))) {
            log.error("Failed to cancel ticket {} reservation", id);
            return null;
        }

        booking.setStatus(BookingStatus.CANCELLED);
        bookingDAO.save(booking);

        return booking.getId();
    }


    public void cancelExpiredBookings() {
        LocalDateTime expirationTime = LocalDateTime.now().minusMinutes(1);
        List<Booking> expiredBookings = bookingDAO.findByStatusAndCreatedAtBefore(BookingStatus.PENDING, expirationTime);

        for (Booking booking : expiredBookings) {
            try {
                cancelBooking(booking.getId());
                log.info("Booking {} automatically canceled due to expiration", booking.getId());
            } catch (Exception e) {
                log.error("Failed to cancel expired booking {}: {}", booking.getId(), e.getMessage());
            }
        }
    }
}
