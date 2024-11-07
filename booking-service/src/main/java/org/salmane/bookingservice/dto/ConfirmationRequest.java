package org.salmane.bookingservice.dto;

public record ConfirmationRequest(
        String ticketId,
        String userId
) {}
