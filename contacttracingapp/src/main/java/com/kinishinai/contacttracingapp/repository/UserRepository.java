package com.kinishinai.contacttracingapp.repository;

import com.kinishinai.contacttracingapp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByMobileNumber(long mobileNumber);

    Optional<User> findByMobileNumber(long mobileNumber);

    @Query(value="select * from User where firstname like %?1%", nativeQuery = true)
    Page<User> findByFirstname(String orElse, Pageable requestedPage);
}
