package com.team4db.deskbookingapp.mapper;

import com.team4db.deskbookingapp.model.Booking;
import com.team4db.deskbookingapp.model.Desk;
import com.team4db.deskbookingapp.modelDTOs.BookingDTO;
import com.team4db.deskbookingapp.modelDTOs.DeskDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

    @Mapping(target = "bookingId", source = "booking.bookingId")
    BookingDTO bookingToBookingDTO(Booking booking);
}
