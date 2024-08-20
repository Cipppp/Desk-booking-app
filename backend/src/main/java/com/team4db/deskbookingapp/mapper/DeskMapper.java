package com.team4db.deskbookingapp.mapper;

import com.team4db.deskbookingapp.model.Desk;
import com.team4db.deskbookingapp.modelDTOs.DeskDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.awt.*;

@Mapper(componentModel = "spring")
public interface DeskMapper {
    DeskMapper INSTANCE = Mappers.getMapper(DeskMapper.class);

    @Mapping(target = "deskId", source = "desk.deskId")
    DeskDTO deskToDeskDTO(Desk desk);
}
