package org.salmane.bookingservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.salmane.bookingservice.event.BookingCompletedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingProducer {

    private final KafkaTemplate<String, BookingCompletedEvent> kafkaTemplate;

    @Value("${spring.kafka.topics.confirmation-topic}")
    private String confirmationTopic;

    @Value("${spring.kafka.topics.cancellation-topic}")
    private String cancellationTopic;

    public void sendConfirmationEvent(BookingCompletedEvent bookingCompletedEvent) {
        log.info("Started sending booking confirmation event to notification service.");

        kafkaTemplate.send(confirmationTopic, bookingCompletedEvent);

        log.info("Ended sending booking confirmation event to notification service.");
    }

    public void sendCancellationEvent(BookingCompletedEvent bookingCompletedEvent) {
        log.info("Started sending booking cancellation event to notification service.");

        kafkaTemplate.send(cancellationTopic, bookingCompletedEvent);

        log.info("Ended sending booking cancellation event to notification service.");
    }
}
