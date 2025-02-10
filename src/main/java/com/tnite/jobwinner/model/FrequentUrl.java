package com.tnite.jobwinner.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "frequent_url")
public class FrequentUrl {

	@Id
	private Integer id;
	private String title;
	private String url;

}
