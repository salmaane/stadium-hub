package org.salmane.matchservice.dto;

import org.salmane.matchservice.model.Match;
import java.time.LocalDateTime;
import java.util.List;

public record MatchRequest(
        LocalDateTime kickoffTime,
        String stadium,
        List<Match.SeatCategory> seatCategories,
        String city,
        String homeTeam,
        String awayTeam
) {}
