package org.salmane.bookingservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.salmane.bookingservice.Enum.BookingStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document("booking")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    private String id;
    private String userId;
    private String matchId;
    private String ticketId;
    private BookingStatus status;
    private LocalDateTime confirmationDate;
    private LocalDateTime cancellationDate;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @Version
    private Integer version;
}
