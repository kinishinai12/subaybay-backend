package com.kinishinai.contacttracingapp.repository;

import com.kinishinai.contacttracingapp.model.Address;
import com.kinishinai.contacttracingapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUser(User user);
}
