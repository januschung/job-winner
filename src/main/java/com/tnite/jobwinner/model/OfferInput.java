package com.tnite.jobwinner.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferInput {
	private Integer jobApplicationId;
	private LocalDate offerDate;
	private String salaryOffered;
	private String description;

}