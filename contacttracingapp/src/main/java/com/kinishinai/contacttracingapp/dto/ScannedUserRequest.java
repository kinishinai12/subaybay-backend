package com.kinishinai.contacttracingapp.dto;

import com.kinishinai.contacttracingapp.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScannedUserRequest {
    private double temperature;
    private String prevLocation;
    private String locationWhereScanned;
    private Instant dateScanned;
}
