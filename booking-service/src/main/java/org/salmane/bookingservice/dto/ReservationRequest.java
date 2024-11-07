package org.salmane.bookingservice.dto;

public record ReservationRequest(
        String userId,
        String matchId,
        String category
) {}
