package com.example.AirbnbBackEnd.repository;

import com.example.AirbnbBackEnd.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

public interface UserDao extends JpaRepository<User,Long>{

    Optional<User> findByEmail(String enail);
    Optional<User> findOneByPublicId(UUID publicId);
}
