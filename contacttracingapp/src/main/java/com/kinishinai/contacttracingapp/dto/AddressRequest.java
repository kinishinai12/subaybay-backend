package com.kinishinai.contacttracingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {
    private String region;
    private String province;
    private String city;
    private String barangay;
}
