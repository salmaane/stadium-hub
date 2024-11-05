package org.salmane.matchservice.client;

import org.salmane.matchservice.model.Match;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.service.annotation.PostExchange;
import java.util.List;

public interface TicketClient {

    @PostExchange("/api/tickets/match/{matchId}/generate")
    Boolean generateMatchTickets(@PathVariable String matchId, @RequestBody List<Match.SeatCategory> seatCategories);

}
