package com.team4db.deskbookingapp.model;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "booking")
@Getter
@Setter
@RequiredArgsConstructor
public class Booking {
  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String bookingId;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "desk_id", nullable = false)
  private Desk desk;
  // frontend can specifiy desk by sending desk position
  // private String deskPosition;
  private LocalDateTime bookedAt;
  private LocalDateTime endOfBooking;
  //  private LocalDateTime startOfficeHours;
  //  private LocalDateTime endOfficeHours;

  @Override
  public String toString() {
    return "Booking{"
        + "bookingId='"
        + bookingId
        + '\''
        + ", bookedAt="
        + bookedAt
        + ", endOfBooking="
        + endOfBooking;
  }
}
