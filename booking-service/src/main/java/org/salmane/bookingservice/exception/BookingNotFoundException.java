package org.salmane.bookingservice.exception;

public class BookingNotFoundException extends RuntimeException {
    public BookingNotFoundException() {
        super();
    }
    public BookingNotFoundException(String message) {
        super(message);
    }
}
