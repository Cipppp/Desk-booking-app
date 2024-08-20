package com.team4db.deskbookingapp.jwt.payload.request;

import com.team4db.deskbookingapp.model.Manager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest implements Serializable {
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private Manager userManager;
}
