package com.ssafy.starflix.review.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReviewRequestDTO {

	private int idx;
	private String writer;
	private String title;
	private String content;
	
}
