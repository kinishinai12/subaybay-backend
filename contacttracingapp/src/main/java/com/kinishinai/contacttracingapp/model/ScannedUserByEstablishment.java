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
public class ScannedUserByEstablishment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", referencedColumnName = "id")
    private User user;
    private double temperature;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scanByWhatEstablihsmentid", referencedColumnName = "id")
    private Establishment scanByWhatEstablihsment;
    private Instant dateScanned;
}
