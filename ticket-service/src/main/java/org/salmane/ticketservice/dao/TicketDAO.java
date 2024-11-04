package org.salmane.ticketservice.dao;

import org.salmane.ticketservice.Enum.TicketStatus;
import org.salmane.ticketservice.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface TicketDAO extends MongoRepository<Ticket, String> {

    Ticket findFirstByMatchIdAndCategoryAndStatus(String matchId, String category, TicketStatus status);
    Optional<Ticket> findByIdAndUserId(String id, String userId);

}
