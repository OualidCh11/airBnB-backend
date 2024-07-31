package com.example.AirbnbBackEnd.repository;

import com.example.AirbnbBackEnd.entity.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ListingDao extends JpaRepository<Listing,Long>{
}
