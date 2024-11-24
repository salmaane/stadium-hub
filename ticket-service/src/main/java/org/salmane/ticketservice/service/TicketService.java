package org.salmane.ticketservice.service;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.salmane.ticketservice.Enum.TicketStatus;
import org.salmane.ticketservice.dao.TicketDAO;
import org.salmane.ticketservice.dto.*;
import org.salmane.ticketservice.exception.TicketNotFoundException;
import org.salmane.ticketservice.model.Ticket;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketService {
    private final TicketDAO ticketDAO;

    public List<TicketResponse> getMatchTickets(String matchId, String category, TicketStatus status) {
        List<Ticket> tickets;

        if (category != null && status != null) {
            // Find by matchId, category, and status
            tickets = ticketDAO.findByMatchIdAndCategoryAndStatus(matchId, category, status);
        } else if (category != null) {
            // Find by matchId and category
            tickets = ticketDAO.findByMatchIdAndCategory(matchId, category);
        } else if (status != null) {
            // Find by matchId and status
            tickets = ticketDAO.findByMatchIdAndStatus(matchId, status);
        } else {
            // Find by matchId only
            tickets = ticketDAO.findByMatchId(matchId);
        }

        return tickets.stream().map(ticket -> new TicketResponse(
                ticket.getId(), ticket.getMatchId(), ticket.getUserId(),
                ticket.getSeatNumber(), ticket.getCategory(),
                ticket.getStatus(), ticket.getPrice()
        )).toList();
    }

    public Boolean generateMatchTickets(String matchId, List<SeatCategory> seatCategories) {
        try {
            List<Ticket> ticketsBatch = new ArrayList<>();
            int BATCH_SIZE = 10_000;

            for(SeatCategory seatCategory : seatCategories) {
                for(int i=0; i<seatCategory.totalSeats(); i++) {
                    Ticket ticket = Ticket.builder()
                            .id(UUID.randomUUID().toString())
                            .matchId(matchId)
                            .category(seatCategory.category())
                            .price(seatCategory.price())
                            .seatNumber(i+1)
                            .status(TicketStatus.AVAILABLE)
                            .build();

                    ticketsBatch.add(ticket);

                    if(ticketsBatch.size() >= BATCH_SIZE) {
                        ticketDAO.saveAll(ticketsBatch);
                        ticketsBatch.clear();
                    }
                }
            }

            ticketDAO.saveAll(ticketsBatch);
        } catch (Exception e) {
            log.error("tickets of match {} failed to generate : {}", matchId, e.getMessage());
            return false;
        }
        return true;
    }

    @Retry(name = "reserveTicketRetry", fallbackMethod = "reserveTicketFallback")
    public String reserveTicket(ReservationRequest reservationRequest) {
        Ticket ticket = ticketDAO.findFirstByMatchIdAndCategoryAndStatus(
                reservationRequest.matchId(), reservationRequest.category(), TicketStatus.AVAILABLE
        );

        if(ticket == null) {
            log.warn("No tickets left for the match {}", reservationRequest.matchId());
            return null;
        }

        ticket.setUserId(reservationRequest.userId());
        ticket.setStatus(TicketStatus.RESERVED);

        ticketDAO.save(ticket);
        log.info("Ticket {} reserved successfully for user {}", ticket.getId(), reservationRequest.userId());
        return ticket.getId();
    }

    public String reserveTicketFallback(ReservationRequest reservationRequest, Throwable t) {
        log.error("Failed to reserve ticket after retries for match {}: {}",
                reservationRequest.matchId(), t.getMessage());
        return null;
    }

    public TicketResponse confirmTicketPurchase(ConfirmationRequest confirmationRequest) {
        Ticket ticket = ticketDAO.findByIdAndUserId(confirmationRequest.ticketId(), confirmationRequest.userId())
            .orElseThrow(
                () -> new TicketNotFoundException("Ticket "+ confirmationRequest.ticketId() +" not found.")
            );

        ticket.setStatus(TicketStatus.SOLD);

        ticketDAO.save(ticket);
        log.info("Ticket {} sold successfully for user {}", ticket.getId(), confirmationRequest.userId());
        return new TicketResponse(
                ticket.getId(), ticket.getMatchId(), ticket.getUserId(),
                ticket.getSeatNumber(), ticket.getCategory(),
                ticket.getStatus(), ticket.getPrice()
        );
    }

    public TicketResponse cancelTicketReservation(ConfirmationRequest cancellationRequest) {
        Ticket ticket = ticketDAO.findByIdAndUserId(cancellationRequest.ticketId(), cancellationRequest.userId())
                .orElseThrow(
                        () -> new TicketNotFoundException("Ticket "+ cancellationRequest.ticketId() +" not found.")
                );

        ticket.setStatus(TicketStatus.AVAILABLE);
        ticket.setUserId(null);

        ticketDAO.save(ticket);
        log.info("Ticket {} reservation cancelled successfully for user {}", ticket.getId(), cancellationRequest.userId());
        return new TicketResponse(
                ticket.getId(), ticket.getMatchId(), ticket.getUserId(),
                ticket.getSeatNumber(), ticket.getCategory(),
                ticket.getStatus(), ticket.getPrice()
        );
    }

    public void deleteByMatch(String matchId) {
        ticketDAO.deleteByMatchId(matchId);
    }
}
