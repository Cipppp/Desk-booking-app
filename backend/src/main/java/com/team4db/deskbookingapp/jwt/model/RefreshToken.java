package com.team4db.deskbookingapp.jwt.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;

@Data
@Entity
public class RefreshToken {
  @Id private String token;
  private String userId;
  private Instant expiryDate;
}
