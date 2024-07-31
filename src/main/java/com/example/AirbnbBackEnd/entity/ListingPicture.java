package com.example.AirbnbBackEnd.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "listning_picture")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"file", "fileContentType", "isCover"})
@ToString(of = {"file", "fileContentType", "isCover"})
public class ListingPicture {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "listningPictureSequenceGenerator")
    @SequenceGenerator(name = "listningPictureSequenceGenerator" , sequenceName = "listing_picture_generator" , allocationSize = 1)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "listning_fk" , referencedColumnName = "id")
    private Listing listning;
    @Lob
    @Column(nullable = false)
    private byte[] file;
    private String fileContentType;
    private boolean isCover;
}
