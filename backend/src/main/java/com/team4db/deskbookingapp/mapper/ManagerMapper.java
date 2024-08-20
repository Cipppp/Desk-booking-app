package com.team4db.deskbookingapp.mapper;

import com.team4db.deskbookingapp.model.Desk;
import com.team4db.deskbookingapp.model.Manager;
import com.team4db.deskbookingapp.modelDTOs.DeskDTO;
import com.team4db.deskbookingapp.modelDTOs.ManagerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ManagerMapper {
    ManagerMapper INSTANCE = Mappers.getMapper(ManagerMapper.class);

    @Mapping(target = "managerId", source = "manager.managerId")
    ManagerDTO managerToManagerDTO(Manager manager);
}
