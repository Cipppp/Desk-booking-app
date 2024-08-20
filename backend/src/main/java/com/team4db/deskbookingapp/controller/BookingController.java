package com.team4db.deskbookingapp.controller;

import com.team4db.deskbookingapp.model.Booking;
import com.team4db.deskbookingapp.model.Desk;
import com.team4db.deskbookingapp.model.Time;
import com.team4db.deskbookingapp.repository.BookingRepository;
import com.team4db.deskbookingapp.service.BookingService;
import com.team4db.deskbookingapp.service.DeskService;
import com.team4db.deskbookingapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("booking")
@Slf4j
public class BookingController {
    private final UserService userService;
    private final BookingService bookingService;
    private final DeskService deskService;
    private final BookingRepository bookingRepository;

    @GetMapping("/freeDesks")
    public List<Desk> getFreeDesks(@RequestBody Time time) {
        List<Booking> bookingList = bookingService.getAllBookings();
        List<Desk> list = deskService.getAllDesks();
        List<String> deskIdList = new ArrayList<>();
        List<Booking> aux = new ArrayList<>();
        List<Desk> rez = new ArrayList<>();

        for(Booking booking : bookingList) {
            if (booking.getEndOfBooking().getYear() == time.getDate().getYear() &&
                    booking.getEndOfBooking().getMonth() == time.getDate().getMonth() &&
                    booking.getEndOfBooking().getDayOfMonth() == time.getDate().getDayOfMonth()) {
                aux.add(booking);
            }
        }

        for(Desk d : list) {
            deskIdList.add(d.getDeskId());
        }

        for (Booking booking : aux) {
            if (deskIdList.contains(booking.getDesk().getDeskId()))
                deskIdList.remove(booking.getDesk().getDeskId());
        }

        for(String id : deskIdList) {
            for (Desk d : list) {
                if(d.getDeskId().equals(id))
                    rez.add(d);
            }
        }
        return rez;
    }
}
