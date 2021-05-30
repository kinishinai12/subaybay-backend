package com.kinishinai.contacttracingapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ScannedUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", referencedColumnName = "id")
    private User user;
    private double temperature;
    private String prevLocation;
    private String locationWhereScanned;
    private boolean isPositive;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idScanBy", referencedColumnName = "id")
    private User scanBy;
    private Instant dateScanned;
}
