package com.kinishinai.contacttracingapp.repository;

import com.kinishinai.contacttracingapp.model.Image;
import com.kinishinai.contacttracingapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findByUser(User user);
}
