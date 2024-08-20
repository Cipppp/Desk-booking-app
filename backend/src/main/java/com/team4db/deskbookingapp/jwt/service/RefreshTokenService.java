package com.team4db.deskbookingapp.jwt.service;

import com.team4db.deskbookingapp.jwt.exception.TokenRefreshException;
import com.team4db.deskbookingapp.jwt.model.RefreshToken;
import com.team4db.deskbookingapp.jwt.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
@Slf4j

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
  @Value("${deskbookingapp.app.jwtRefreshTokenExpirationMs}")
  private Long jwtRefreshTokenExpirationMs;

  private final RefreshTokenRepository refreshTokenRepository;

  public Optional<RefreshToken> findByToken(String token) {
    return refreshTokenRepository.findById(token);
  }

  public RefreshToken createRefreshToken(String userId) {
    RefreshToken refreshToken = new RefreshToken();
    refreshToken.setUserId(userId);
    refreshToken.setExpiryDate(Instant.now().plusMillis(jwtRefreshTokenExpirationMs));
    refreshToken.setToken(UUID.randomUUID().toString());
    refreshToken = refreshTokenRepository.save(refreshToken);
    return refreshToken;
  }

  public RefreshToken verifyExpiration(RefreshToken token) {
    if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
      refreshTokenRepository.delete(token);
      log.error("Refresh token was expired. It's necessary a new signin request");
      throw new TokenRefreshException(
          token.getToken(), "Refresh token was expired. Please make a new signin request");
    }
    return token;
  }

  public void deleteByUserId(String userId) {
    refreshTokenRepository.deleteByUserId(userId);
  }

  public boolean checkUserLogged(String email) {
    if (refreshTokenRepository.findAll().stream().filter(token -> token.getUserId().compareTo(email) == 0).count() > 0) {
      log.error("User already logged in");
      return true;
    }
    return false;
  }
}
