package org.salmane.notificationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.salmane.notificationservice.event.BookingCompletedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingConsumer {

    private final EmailService emailService;

    @KafkaListener(topics = "booking-confirmation", groupId = "notification-service")
    public void listenForConfirmation(BookingCompletedEvent bookingCompletedEvent) {
        log.info("Received BookingCompletedEvent on confirmation topic listener");
        emailService.sendTicketConfirmationEmail(bookingCompletedEvent);
        log.info("Confirmation email sent to user {}", bookingCompletedEvent.userId());
    }

    @KafkaListener(topics = "booking-cancellation", groupId = "notification-service")
    public void listenForCancellation(BookingCompletedEvent bookingCompletedEvent) {
        log.info("Received BookingCompletedEvent on Cancellation topic listener");
        emailService.sendTicketCancellationEmail(bookingCompletedEvent);
        log.info("Cancellation email sent to user {}", bookingCompletedEvent.userId());
    }

}
