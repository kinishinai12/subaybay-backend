package com.kinishinai.contacttracingapp.repository;

import com.kinishinai.contacttracingapp.model.ScannedUserByEstablishment;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScannedUserByEstablishmentRepository extends PagingAndSortingRepository<ScannedUserByEstablishment, Long> {
}
