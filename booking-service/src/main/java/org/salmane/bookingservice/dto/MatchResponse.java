package org.salmane.bookingservice.dto;

import java.time.LocalDateTime;

public record MatchResponse(
        String id,
        LocalDateTime kickoffTime,
        String stadium,
        String city,
        String homeTeam,
        String awayTeam
) {}
