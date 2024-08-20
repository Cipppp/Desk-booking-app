package com.team4db.deskbookingapp.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ErrorMessage {
  private int statusCode;
  private Date timestamp;
  private String message;
  private String description;
}
