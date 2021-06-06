package com.kinishinai.contacttracingapp.repository;

import com.kinishinai.contacttracingapp.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepositoty extends JpaRepository<RefreshToken, Long> {
     Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);
}
