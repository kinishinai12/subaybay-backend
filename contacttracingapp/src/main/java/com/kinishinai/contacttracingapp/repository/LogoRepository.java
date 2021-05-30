package com.kinishinai.contacttracingapp.repository;

import com.kinishinai.contacttracingapp.model.Logo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogoRepository extends JpaRepository<Logo, Long> {
}
