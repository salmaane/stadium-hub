package org.salmane.bookingservice.client;

import org.salmane.bookingservice.config.OAuthFeignConfig;
import org.salmane.bookingservice.dto.ConfirmationRequest;
import org.salmane.bookingservice.dto.ReservationRequest;
import org.salmane.bookingservice.dto.TicketResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ticket-service", path = "/api/tickets", configuration = OAuthFeignConfig.class)
public interface TicketClient {

    @PostMapping("/reserve")
    String reserveTicket(@RequestBody ReservationRequest reservationRequest);

    @PostMapping("/confirmation")
    public TicketResponse confirmTicketPurchase(@RequestBody ConfirmationRequest confirmationRequest);

    @PostMapping("/cancel")
    public TicketResponse cancelTicketReservation(@RequestBody ConfirmationRequest cancellationRequest);
}
