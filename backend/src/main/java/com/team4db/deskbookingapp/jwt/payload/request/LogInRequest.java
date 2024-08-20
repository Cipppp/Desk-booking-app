package com.team4db.deskbookingapp.jwt.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogInRequest implements Serializable {
    private String email;
    private String password;
}
