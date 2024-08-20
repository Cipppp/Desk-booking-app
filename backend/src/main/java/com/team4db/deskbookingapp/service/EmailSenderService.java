package com.team4db.deskbookingapp.service;

import com.sun.mail.util.MailSSLSocketFactory;
import com.team4db.deskbookingapp.model.Desk;
import com.team4db.deskbookingapp.model.User;
import com.team4db.deskbookingapp.modelDTOs.BookingDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

@Service
@Slf4j
public class EmailSenderService {
  @Autowired private JavaMailSender simpleMailSender;
  @Value("${spring.mail.username}")
  private String emailSender;

  public String formatMessage(
      BookingDTO booking, String userLastName, String deskPosition, String typeOfAction) {
    StringBuilder emailBody = new StringBuilder();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    System.err.println("Booking " + booking);
    String formatBookingTime = booking.getEndOfBooking().format(format);

    if (typeOfAction.compareTo("booking") == 0) {
      emailBody
          .append("Dear ")
          .append(userLastName)
          .append(", thank you for using our application ")
          .append("your desk positioned at ")
          .append(deskPosition)
          .append(" is ready to use in the following interval")
          .append(": ")
          .append(formatBookingTime)
          .append(", ")
              .append(" 7:00 - 20:00.");
    } else {
      emailBody
          .append("Dear ")
          .append(userLastName)
          .append(", thank you for using our application ")
          .append("your booking for the desk positioned at ")
          .append(deskPosition)
          .append(" and for the following interval")
          .append(": ")
          .append(formatBookingTime)
          .append(", ")
          .append(" has been cancelled.");
    }
    return emailBody.toString();
  }

  public void sendSimpleMail(String toEmail, String subject, String body) {

    SimpleMailMessage message = new SimpleMailMessage();
//    message.setFrom("deskbookingappemail@gmail.com");
    message.setFrom(emailSender);
    message.setTo(toEmail);

    message.setText(body);
    message.setSubject(subject);

    simpleMailSender.send(message);
    log.info("Confirmation email sent!");
  }

  // TODO send fancy customized email
}
