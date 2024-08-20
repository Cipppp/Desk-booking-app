package com.team4db.deskbookingapp.jwt.payload.response;

import com.team4db.deskbookingapp.jwt.model.RefreshToken;

public record AuthenticationResponse(String jwt, RefreshToken refreshToken) {
}
