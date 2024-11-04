package org.salmane.ticketservice.model;

import lombok.*;
import org.salmane.ticketservice.Enum.TicketStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document("ticket")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    private String id;
    private String matchId;
    private String userId;
    private int seatNumber;
    private String category;
    private TicketStatus status;
    private int price;
    @CreatedDate
    private LocalDateTime created_at;
    @LastModifiedDate
    private LocalDateTime updated_at;
    @Version
    private int version;
}
