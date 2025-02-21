package com.tnite.jobwinner.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "job_application")
public class JobApplication {
    
    @Id
    private Integer id;
    @Column("company_name")
    private String companyName;
    @Column("job_title")
    private String jobTitle;
    @Column("salary_range")
    private String salaryRange;
    @Column("job_url")
    private String jobUrl;
    @Column("applied_date")
    private LocalDate appliedDate;
    private String description;
    private String note;
    private String status;
    
}