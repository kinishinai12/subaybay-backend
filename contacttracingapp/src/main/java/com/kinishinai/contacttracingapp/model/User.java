package com.kinishinai.contacttracingapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstname;
    private String middlename;
    private String lastname;
    private Date birthday;
    private long mobileNumber;
    private boolean isVaccinated;
    private String password;
    private Instant dateRegistered;
    private boolean isVerified;
    private boolean isAdmin;
}
