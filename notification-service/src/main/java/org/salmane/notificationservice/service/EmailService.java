package org.salmane.notificationservice.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.salmane.notificationservice.event.BookingCompletedEvent;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public void sendTicketConfirmationEmail(BookingCompletedEvent booking) {
        try {
            Context context = new Context();
            context.setVariable("kickoffTime", booking.kickoffTime().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy HH:mm")));
            context.setVariable("stadium", booking.stadium());
            context.setVariable("city", booking.city());
            context.setVariable("homeTeam", booking.homeTeam());
            context.setVariable("awayTeam", booking.awayTeam());
            context.setVariable("ticketId", booking.ticketId());
            context.setVariable("userId", booking.userId());
            context.setVariable("seatNumber", booking.seatNumber());
            context.setVariable("category", booking.category());
            context.setVariable("price", booking.price());

            // Process the template
            String emailContent = templateEngine.process("email-confirmation", context);

            // Create the email message
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("stadium.hub@salmane.com");
            helper.setTo("salmane@gmail.com");
            helper.setSubject("Match Ticket Confirmation - " + booking.homeTeam() + " vs " + booking.awayTeam());
            helper.setText(emailContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email confirmation", e);
        }
    }

    public void sendTicketCancellationEmail(BookingCompletedEvent booking) {
        try {
            Context context = new Context();
            context.setVariable("kickoffTime", booking.kickoffTime().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy HH:mm")));
            context.setVariable("city", booking.city());
            context.setVariable("homeTeam", booking.homeTeam());
            context.setVariable("awayTeam", booking.awayTeam());
            context.setVariable("ticketId", booking.ticketId());
            context.setVariable("userId", booking.userId());

            // Process the template
            String emailContent = templateEngine.process("email-cancellation", context);

            // Create the email message
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("stadium.hub@salmane.com");
            helper.setTo("salmane@gmail.com");
            helper.setSubject("Match Ticket Cancellation - " + booking.homeTeam() + " vs " + booking.awayTeam());
            helper.setText(emailContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send cancellation email", e);
        }
    }

}
