package org.salmane.matchservice.client;

import org.salmane.matchservice.model.Match;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@FeignClient(name = "ticket-service", path = "/api/tickets")
public interface TicketClient {

    @PostMapping("/match/{matchId}/generate")
//    @CircuitBreaker(name = "ticketService", fallbackMethod = "fallback")
    Boolean generateMatchTickets(@PathVariable String matchId, @RequestBody List<Match.SeatCategory> seatCategories);

    default Boolean fallback(String matchId, List<Match.SeatCategory> seatCategories, Exception e) {
        return false;
    }
}
