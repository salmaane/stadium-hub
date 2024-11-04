package org.salmane.ticketservice.dto;

import org.salmane.ticketservice.Enum.TicketStatus;

public record TicketResponse(
        String id,
        String matchId,
        String userId,
        int seatNumber,
        String category,
        TicketStatus status,
        int price
) {}
