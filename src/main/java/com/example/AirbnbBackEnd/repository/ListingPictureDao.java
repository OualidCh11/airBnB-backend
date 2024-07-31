package com.example.AirbnbBackEnd.repository;

import com.example.AirbnbBackEnd.entity.ListingPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingPictureDao extends JpaRepository<ListingPicture , Long> {
}
