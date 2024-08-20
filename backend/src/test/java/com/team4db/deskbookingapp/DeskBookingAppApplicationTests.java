package com.team4db.deskbookingapp;

import com.team4db.deskbookingapp.mapper.DeskMapper;
import com.team4db.deskbookingapp.mapper.ManagerMapper;
import com.team4db.deskbookingapp.mapper.UserMapper;
import com.team4db.deskbookingapp.model.Desk;
import com.team4db.deskbookingapp.model.Manager;
import com.team4db.deskbookingapp.model.User;
import com.team4db.deskbookingapp.modelDTOs.DeskDTO;
import com.team4db.deskbookingapp.modelDTOs.ManagerDTO;
import com.team4db.deskbookingapp.modelDTOs.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DeskBookingAppApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void shouldMapDeskToDTO() {
		Desk desk = new Desk();
		desk.setDeskId("10");

		DeskDTO deskDTO = DeskMapper.INSTANCE.deskToDeskDTO(desk);
		Assertions.assertEquals(deskDTO.getDeskId(), "10");
	}

	@Test
	public void shouldMapUserToDTO() {
		User user = new User();
		user.setUserID("aaaa");

		UserDTO userDTO = UserMapper.INSTANCE.userToUserDTO(user);
		Assertions.assertEquals(userDTO.getUserID(), "aaaa");
	}

	@Test
	public void shouldMapManagerToDTO() {
		Manager manager = new Manager();
		manager.setManagerId("111");

		ManagerDTO managerDTO = ManagerMapper.INSTANCE.managerToManagerDTO(manager);
		Assertions.assertEquals(managerDTO.getManagerId(), "111");
	}


}
