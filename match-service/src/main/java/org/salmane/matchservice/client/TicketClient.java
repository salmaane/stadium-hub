package org.salmane.matchservice.client;

import org.salmane.matchservice.config.OAuthFeignConfig;
import org.salmane.matchservice.model.Match;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "ticket-service", path = "/api/tickets", configuration = OAuthFeignConfig.class)
public interface TicketClient {

//    @CircuitBreaker(name = "ticketService", fallbackMethod = "fallback")
    @PostMapping("/match/{matchId}/generate")
    Boolean generateMatchTickets(@PathVariable String matchId, @RequestBody List<Match.SeatCategory> seatCategories);

    @DeleteMapping("/match/{matchId}")
    void deleteByMatch(@PathVariable String matchId);

    default Boolean fallback(String matchId, List<Match.SeatCategory> seatCategories, Exception e) {
        return false;
    }
}
