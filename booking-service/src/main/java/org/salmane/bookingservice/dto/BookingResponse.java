package org.salmane.bookingservice.dto;

import org.salmane.bookingservice.Enum.BookingStatus;
import java.time.LocalDateTime;

public record BookingResponse(
        String id,
        String userId,
        String matchId,
        String ticketId,
        BookingStatus status,
        LocalDateTime bookingDate,
        LocalDateTime confirmationDate,
        LocalDateTime cancellationDate
) {}
