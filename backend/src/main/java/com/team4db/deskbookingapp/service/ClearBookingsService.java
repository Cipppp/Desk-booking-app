package com.team4db.deskbookingapp.service;

import com.team4db.deskbookingapp.model.User;
import com.team4db.deskbookingapp.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClearBookingsService {
  private final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
  private final BookingService bookingService;
  private final UserService userService;

  @Scheduled(cron = "0 05 20 * * MON-FRI")
  void clearBookings() throws InterruptedException {
    LocalDateTime now = LocalDateTime.now();
    String todaysDate = now.format(format);

    bookingService
        .getAllBookings()
        .forEach(
            booking -> {
              if (booking.getEndOfBooking().format(format).compareTo(todaysDate) == 0) {
                User user = userService.findUserByUserID(booking.getUser().getUserID());
                user.getUserBookings()
                    .removeIf(book -> book.getBookingId().compareTo(booking.getBookingId()) == 0);
                user.setNrOfBookedDesks(user.getNrOfBookedDesks() - 1);
                userService.updateUser(user);
                bookingService.deleteBookingById(booking.getBookingId());
                log.info("Booking has been automatically deleted!");
              }
            });
  }
}

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)
class SchedulingConfiguration {}
