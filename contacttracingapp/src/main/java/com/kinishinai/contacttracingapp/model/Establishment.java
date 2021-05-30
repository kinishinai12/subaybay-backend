package com.kinishinai.contacttracingapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Establishment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String establishmentName;
    private long mobileNumber;
    private String email;
    private String password;
    private String ownerFullName;
    private boolean isVerified;
    private Instant dateRegistered;
    // image to database
}
