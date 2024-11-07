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
    String id;
    String userId;
    String matchId;
    String ticketId;
    BookingStatus status;
    LocalDateTime bookingDate;
    LocalDateTime confirmationDate;
    LocalDateTime cancellationDate;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @Version
    private Integer version;
}
