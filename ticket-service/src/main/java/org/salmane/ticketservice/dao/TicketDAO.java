package org.salmane.ticketservice.dao;

import org.salmane.ticketservice.Enum.TicketStatus;
import org.salmane.ticketservice.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface TicketDAO extends MongoRepository<Ticket, String> {

    Ticket findFirstByMatchIdAndCategoryAndStatus(String matchId, String category, TicketStatus status);

    Optional<Ticket> findByIdAndUserId(String id, String userId);

    List<Ticket> findByMatchId(String matchId);

    List<Ticket> findByMatchIdAndCategory(String matchId, String category);

    List<Ticket> findByMatchIdAndStatus(String matchId, TicketStatus status);

    List<Ticket> findByMatchIdAndCategoryAndStatus(String matchId, String category, TicketStatus status);

    void deleteByMatchId(String matchId);
}
