package com.tnite.jobwinner.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "interview")
public class Interview {

	@Id
	private Integer id;
	@Column("job_application_id")
	private Integer jobApplicationId;
	@Column("interview_date")
	private LocalDate interviewDate;
	private String interviewer;
	private String description;
	private String status;
}
