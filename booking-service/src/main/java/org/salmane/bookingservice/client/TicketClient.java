package org.salmane.bookingservice.client;

import org.salmane.bookingservice.dto.ConfirmationRequest;
import org.salmane.bookingservice.dto.ReservationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ticket-service", path = "/api/tickets")
public interface TicketClient {

    @PostMapping("/reserve")
    String reserveTicket(@RequestBody ReservationRequest reservationRequest);

    @PostMapping("/confirmation")
    public Boolean confirmTicketPurchase(@RequestBody ConfirmationRequest confirmationRequest);

    @PostMapping("/cancel")
    public Boolean cancelTicketReservation(@RequestBody ConfirmationRequest cancellationRequest);
}
