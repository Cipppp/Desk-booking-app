package com.team4db.deskbookingapp.mapper;

import com.team4db.deskbookingapp.model.Manager;
import com.team4db.deskbookingapp.model.User;
import com.team4db.deskbookingapp.modelDTOs.ManagerDTO;
import com.team4db.deskbookingapp.modelDTOs.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "userID", source = "user.userID")
    UserDTO userToUserDTO(User user);

}
