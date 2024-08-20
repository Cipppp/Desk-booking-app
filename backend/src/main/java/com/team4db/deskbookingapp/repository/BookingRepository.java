package com.team4db.deskbookingapp.repository;

import com.team4db.deskbookingapp.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookingRepository extends JpaRepository<Booking, String> {
    Booking findBookingsByBookingId(String bookingId);
}
