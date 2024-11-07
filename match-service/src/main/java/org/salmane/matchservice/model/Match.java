package org.salmane.matchservice.model;

import lombok.*;
import org.salmane.matchservice.Enum.MatchStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document("match")
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Match {
    private String id;
    private LocalDateTime kickoffTime;
    private String stadium;
    private List<SeatCategory> seatCategories;
    private MatchStatus status;
    private String city;
    private String homeTeam;
    private String awayTeam;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @Version
    private Integer version;

    @Builder
    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SeatCategory {
        private String category;
        private int totalSeats;
        private int price;
    }
}
