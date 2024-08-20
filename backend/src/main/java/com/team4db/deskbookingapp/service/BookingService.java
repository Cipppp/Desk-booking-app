package com.team4db.deskbookingapp.service;

import com.team4db.deskbookingapp.model.Booking;
import com.team4db.deskbookingapp.model.Desk;
import com.team4db.deskbookingapp.model.User;
import com.team4db.deskbookingapp.modelDTOs.BookingDTO;
import com.team4db.deskbookingapp.repository.BookingRepository;
import com.team4db.deskbookingapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class BookingService {
  @Autowired private BookingRepository bookingRepository;

  @Autowired private UserRepository userRepository;

  public boolean checkExistenceOfBooking(String deskID, LocalDateTime newDate) {
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    return bookingRepository.findAll().stream()
        .anyMatch(
            book ->
                Objects.equals(book.getDesk().getDeskId(), deskID)
                    && book.getEndOfBooking().format(format).compareTo(newDate.format(format))
                        == 0);
  }

  public void makeBooking(BookingDTO booking, Desk desk, User userToUpdate) {
    Booking newBooking = new Booking();
    userToUpdate.setNrOfBookedDesks(userToUpdate.getNrOfBookedDesks() + 1);
    newBooking.setBookedAt(booking.getBookedAt());
    newBooking.setEndOfBooking(booking.getEndOfBooking());
    newBooking.setDesk(desk);
    newBooking.setUser(userToUpdate);
    bookingRepository.save(newBooking);

    userToUpdate.getUserBookings().add(newBooking);
    userRepository.save(userToUpdate);
  }

  public Booking findBookingsByBookingId(String bookingId) {
    return bookingRepository.findBookingsByBookingId(bookingId);
  }

  public List<Booking> getAllBookings() {
    List<Booking> list = bookingRepository.findAll();

    return list;
  }

  public void deleteBookingById(String bookingId) {
    bookingRepository.deleteById(bookingId);
  }
}
