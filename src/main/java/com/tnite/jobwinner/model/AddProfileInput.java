package com.tnite.jobwinner.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddProfileInput {
    private String firstName;
    private String lastName;
    private String addressStreet1;
    private String addressStreet2;
    private String addressCity;
    private String addressState;
    private String addressZip;
    private String linkedin;
    private String github;
    private String personalWebsite;
    
}