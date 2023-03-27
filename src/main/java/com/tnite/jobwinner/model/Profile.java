package com.tnite.jobwinner.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profile")
public class Profile {
    
    @Id
    private Integer id;
    @Column("first_name")
    private String firstName;
    @Column("last_name")
    private String lastName;
    @Column("address_street_1")
    private String addressStreet1;
    @Column("address_street_2")
    private String addressStreet2;
    @Column("address_city")
    private String addressCity;
    @Column("address_state")
    private String addressState;
    @Column("address_zip")
    private String addressZip;
    private String linkedin;
    private String github;
    @Column("personal_website")
    private String personalWebsite;
    
}