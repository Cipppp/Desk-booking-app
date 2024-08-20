package com.team4db.deskbookingapp.modelDTOs;

import com.team4db.deskbookingapp.model.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
public class ManagerDTO {
//    @Id
//    @GeneratedValue(generator="system-uuid")
//    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String managerId;

    private String firstName;
    private String lastName;
    private String department;

//    @OneToMany
//    private List<User> managedUsers;
}
