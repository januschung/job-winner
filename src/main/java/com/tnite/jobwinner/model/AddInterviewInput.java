package com.tnite.jobwinner.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddInterviewInput {
	private Integer jobApplicationId;
	private LocalDate interviewDate;
	private String interviewer;
	private String description;
	private String status;

}
