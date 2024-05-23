package com.ssafy.starflix.review.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "ReviewDTO : 명소에 대한 Review 정보를 담은 DTO")
public class ReviewDTO {
	
	@Schema(description = "리뷰번호")
	private int rno;
	
	@Schema(description = "명소번호")
	private int idx;
	
	@Schema(description = "제목")
	private String title;
	
	@Schema(description = "내용")
	private String content;
	
	@Schema(description = "작성자")
	private String writer;
	
	@Schema(description = "이미지")
	private String img;
	
	@Schema(description = "등록일시")
	private String registerTime;

}
