package com.team4db.deskbookingapp.jwt.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenRefreshResponse {
  private String accessToken;
  private String refreshToken;
  private final String tokenType = "Bearer";
}
