package com.team4db.deskbookingapp.jwt.repository;

import com.team4db.deskbookingapp.jwt.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
  void deleteByUserId(String userId);
}
