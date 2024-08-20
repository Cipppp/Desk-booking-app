package com.team4db.deskbookingapp.jwt.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogOutRequest {
  private String userId;
  private String userEmail;
}
