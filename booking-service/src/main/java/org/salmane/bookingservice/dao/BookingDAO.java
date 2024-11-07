package org.salmane.bookingservice.dao;

import org.salmane.bookingservice.Enum.BookingStatus;
import org.salmane.bookingservice.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingDAO extends MongoRepository<Booking, String> {

    List<Booking> findByUserId(String userId);

    List<Booking> findByStatusAndCreatedAtBefore(BookingStatus bookingStatus, LocalDateTime expirationTime);
}
