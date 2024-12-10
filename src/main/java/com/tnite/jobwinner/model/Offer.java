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
@Table(name = "offer")
public class Offer {

	@Id
	private Integer id;
	@Column("job_application_id")
	private Integer jobApplicationId;
	@Column("offer_date")
	private LocalDate offerDate;
	@Column("salary_offered")
	private String salaryOffered;
	private String description;

}
