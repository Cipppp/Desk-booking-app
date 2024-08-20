package com.team4db.deskbookingapp.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "managers")
public class Manager {
    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String managerId;

    private String firstName;
    private String lastName;
    private String department;

    @OneToMany
    private List<User> managedUsers;
}
