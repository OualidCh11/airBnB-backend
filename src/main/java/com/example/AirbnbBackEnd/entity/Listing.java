package com.example.AirbnbBackEnd.entity;

import com.example.AirbnbBackEnd.sheredkernal.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "listing")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Listing extends AbstractAuditingEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "listingSequenceGenerator")
    @SequenceGenerator(name = "listingSequenceGenerator", sequenceName = "listing_generator", allocationSize = 1)
    private Long id;

    @UuidGenerator
    private UUID publicId;
    private String title;
    private String description;
    private int guests;
    private int bedrooms;
    private int beds;
    private int bathrooms;
    private int price;
    @Enumerated(EnumType.STRING)
    private BookingCategory bookingCategory;
    private String location;
    private UUID landlordPublicId;

    @OneToMany(mappedBy = "listning" ,   cascade = CascadeType.REMOVE)
    private Set<ListingPicture> listingPictures = new HashSet<>();

    @Override
    public Long getId(){
        return id;
    }
}
