package com.example.AirbnbBackEnd.entity;

import com.example.AirbnbBackEnd.sheredkernal.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "booking")
@AttributeOverrides({
        @AttributeOverride(name = "createDate", column = @Column(name = "booking_create_date")),
        @AttributeOverride(name = "lastModifiedDate", column = @Column(name = "booking_last_modified_date"))
})
public class Booking extends AbstractAuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "bookingSequenceGenerator")
    @SequenceGenerator(name = "bookingSequenceGenerator" , sequenceName = "booking_generator" , allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @UuidGenerator
    @Column(name = "public_id", nullable = false)
    private UUID publicId;

    @Column(name = "start_date", nullable = false)
    private OffsetDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private OffsetDateTime endDate;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @Column(name = "nb_of_travelers", nullable = false)
    private int numberOfTravelers;

    @Column(name = "fk_tenant", nullable = false)
    private UUID fkTenant;

    @Column(name = "fk_listing", nullable = false)
    private UUID fkListing;
    @Override
    public Long getId() {
        return id;
    }

}
