package org.salmane.bookingservice.dto;

public record TicketResponse(
        String id,
        String matchId,
        String userId,
        int seatNumber,
        String category,
        int price
) {}
