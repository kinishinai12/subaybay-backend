package com.kinishinai.contacttracingapp.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    // user
    private String firstname;
    private String middlename;
    private String lastname;
    private Date birthday;
    private long mobileNumber;
    private boolean isVaccinated;
    private String password;
    private boolean isAdmin;
    //address
    private String region;
    private String province;
    private String city;
    private String barangay;
    //image
    private String imageName;
    private String path;

}
