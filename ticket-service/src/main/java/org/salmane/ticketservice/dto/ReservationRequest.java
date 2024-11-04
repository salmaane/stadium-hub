package org.salmane.ticketservice.dto;

public record ReservationRequest(
        String matchId,
        String userId,
        String category
) {}
