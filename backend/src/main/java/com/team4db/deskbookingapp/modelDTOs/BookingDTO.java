package com.team4db.deskbookingapp.modelDTOs;

import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@Data
public class BookingDTO {
    private String bookingId;
    private String deskId;
    private String userId;
    private LocalDateTime bookedAt;
    //Day of the booking
    private LocalDateTime endOfBooking;    // 2022-07-05T12:59:11.332
}
