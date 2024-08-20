package com.team4db.deskbookingapp.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "office_desks")
@Getter
@Setter
public class Desk {
  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String deskId;

  private String mapPosition;

//  @ManyToOne private User currentUser;

  // will include date + time of booking start. Needs parsing.
//  private LocalDateTime bookedAt;

  // will include date + time of booking end. Needs parsing.
//  private LocalDateTime endOfBooking;

//  private boolean isBooked;

  // TODO if needed
//  @ElementCollection(targetClass = User.class)
//  private List<User> bookedByHistory;
}
