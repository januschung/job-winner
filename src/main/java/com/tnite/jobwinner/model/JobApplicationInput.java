package com.tnite.jobwinner.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationInput {
    private String companyName;
    private String jobTitle;
    private String salaryRange;
    private LocalDate appliedDate;
    private String jobUrl;
    private String description;
    private String note;
    private String status;
}
