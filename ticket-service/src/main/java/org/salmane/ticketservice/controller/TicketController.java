package org.salmane.ticketservice.controller;

import lombok.RequiredArgsConstructor;
import org.salmane.ticketservice.Enum.TicketStatus;
import org.salmane.ticketservice.dto.ConfirmationRequest;
import org.salmane.ticketservice.dto.ReservationRequest;
import org.salmane.ticketservice.dto.SeatCategory;
import org.salmane.ticketservice.dto.TicketResponse;
import org.salmane.ticketservice.service.TicketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("/match/{matchId}")
    public List<TicketResponse> getMatchTickets(
            @PathVariable("matchId") String matchId,
            @RequestParam(name = "cat", required = false) String category,
            @RequestParam(name = "status", required = false) TicketStatus status
            ) {
        return ticketService.getMatchTickets(matchId, category, status);
    }

    @PostMapping("/match/{matchId}/generate")
    public Boolean generateMatchTickets(@PathVariable String matchId, @RequestBody List<SeatCategory> seatCategories) {
        return ticketService.generateMatchTickets(matchId, seatCategories);
    }

    @PostMapping("/reserve")
    public String reserveTicket(@RequestBody ReservationRequest reservationRequest) {
        return ticketService.reserveTicket(reservationRequest);
    }

    @PostMapping("/confirmation")
    public TicketResponse confirmTicketPurchase(@RequestBody ConfirmationRequest confirmationRequest) {
        return ticketService.confirmTicketPurchase(confirmationRequest);
    }

    @PostMapping("/cancel")
    public TicketResponse cancelTicketReservation(@RequestBody ConfirmationRequest cancellationRequest) {
        return ticketService.cancelTicketReservation(cancellationRequest);
    }

    @DeleteMapping("/match/{matchId}")
    public void deleteByMatch(@PathVariable String matchId) {
        ticketService.deleteByMatch(matchId);
    }
}
