package com.example.AirbnbBackEnd.repository;

import com.example.AirbnbBackEnd.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface BookingRepository extends JpaRepository<Booking , Long> {
}
