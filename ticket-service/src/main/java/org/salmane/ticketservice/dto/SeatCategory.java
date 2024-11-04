package org.salmane.ticketservice.dto;

public record SeatCategory (
        String category,
        int totalSeats,
        int price
) {}
