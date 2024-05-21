package com.ssafy.starflix.api.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(title = "GptReviewDTO")
public class GptReviewDTO {

	@Schema(description = "별점")
	private double rate;

	@Schema(description = "분석요약")
	private String summary;

	@Schema(description = "장점")
	private String pros;

	@Schema(description = "단점")
	private String cons;
}
