package com.kinishinai.contacttracingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstablishmentRequest {
    private String establishmentName;
    private long mobileNumber;
    private String email;
    private String password;
    private String ownerFullName;
    private String logoPath;
    private String logoName;
}
