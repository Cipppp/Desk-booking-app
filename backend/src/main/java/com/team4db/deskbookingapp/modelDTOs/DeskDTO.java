package com.team4db.deskbookingapp.modelDTOs;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeskDTO {

    private String deskId;

//    private UserDTO currentUser;
//
//    // will include date + time of booking start. Needs parsing.
//    private LocalDateTime bookedAt;
//
//    // will include date + time of booking end. Needs parsing.
//    private LocalDateTime endOfBooking;

    private String mapPosition;

    private boolean isBooked;

    public String getDeskId() {
        return deskId;
    }

    public void setDeskId(String deskId) {
        this.deskId = deskId;
    }
}

