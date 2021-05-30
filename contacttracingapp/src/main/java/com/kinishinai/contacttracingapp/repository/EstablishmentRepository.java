package com.kinishinai.contacttracingapp.repository;

import com.kinishinai.contacttracingapp.model.Establishment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstablishmentRepository extends JpaRepository<Establishment, Long> {
    Optional<Establishment> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<Establishment> findByMobileNumber(long mobileNumber);
}
