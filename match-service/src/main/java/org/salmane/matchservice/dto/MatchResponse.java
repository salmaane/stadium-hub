package org.salmane.matchservice.dto;

import org.salmane.matchservice.Enum.MatchStatus;
import org.salmane.matchservice.model.Match;
import java.time.LocalDateTime;
import java.util.List;

public record MatchResponse(
        String id,
        LocalDateTime kickoffTime,
        String stadium,
        MatchStatus status,
        String city,
        String homeTeam,
        String awayTeam,
        List<Match.SeatCategory> seatCategories
) {}
