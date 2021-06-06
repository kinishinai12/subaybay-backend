package com.kinishinai.contacttracingapp.service;

import com.kinishinai.contacttracingapp.exception.SubaybayException;
import com.kinishinai.contacttracingapp.model.RefreshToken;
import com.kinishinai.contacttracingapp.repository.RefreshTokenRepositoty;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
//user token service
public class RefreshTokenService {
    private final RefreshTokenRepositoty REFRESHTOKENREPOSITORY;

    public RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());

        return REFRESHTOKENREPOSITORY.save(refreshToken);
    }

    void validateRefreshToken(String token) {
        REFRESHTOKENREPOSITORY.findByToken(token)
                .orElseThrow(() -> new SubaybayException("Invalid refresh Token"));
    }

    public void deleteRefreshToken(String token) {
        REFRESHTOKENREPOSITORY.deleteByToken(token);
    }
}
