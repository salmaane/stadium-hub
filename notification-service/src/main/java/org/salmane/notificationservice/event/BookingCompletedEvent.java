package org.salmane.notificationservice.event;

import java.time.LocalDateTime;

public record BookingCompletedEvent(
        String matchId,
        LocalDateTime kickoffTime,
        String stadium,
        String city,
        String homeTeam,
        String awayTeam,
        String ticketId,
        String userId,
        int seatNumber,
        String category,
        int price
) {}
