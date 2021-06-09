package com.kinishinai.contacttracingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponseForEstablishment {
    private String authenticationToken;
    private String email;
    private String refreshToken;
    private Instant expiresAt;
}
