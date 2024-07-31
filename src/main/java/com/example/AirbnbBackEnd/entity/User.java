package com.example.AirbnbBackEnd.entity;

import com.example.AirbnbBackEnd.sheredkernal.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name ="user")
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends AbstractAuditingEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE ,generator = "userSequenceGenerator")
    @SequenceGenerator(name = "userSequenceGenerator" , sequenceName = "user_generator" , allocationSize = 1)
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String imageURL;
    @UuidGenerator
    private String publicId;

    @ManyToMany
    @JoinTable(name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")})
    private Set<Authority> authorities = new HashSet<>();

    @Override
    public Long getId(){
        return id;
    }
}
