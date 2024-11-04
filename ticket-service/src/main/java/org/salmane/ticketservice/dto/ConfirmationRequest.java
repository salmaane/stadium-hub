package org.salmane.ticketservice.dto;

public record ConfirmationRequest(
        String ticketId,
        String userId
) {}
