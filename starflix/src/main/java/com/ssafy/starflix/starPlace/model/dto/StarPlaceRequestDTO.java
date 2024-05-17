package com.ssafy.starflix.starPlace.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(title = "StarPlaceRequestDTO : 명소등록시 요청DTO")
public class StarPlaceRequestDTO {
	
	@Schema(description = "명소명")
	private String title;

	@Schema(description = "명소 주소")
	private String addr;
	
	@Schema(description = "작성자")
	private String writer;

	@Schema(description = "명소 설명")
	private String content;

	@Schema(description = "명소 이미지")
	private String img;

	@Schema(description = "명소 위도")
	private double lati;

	@Schema(description = "명소 경도")
	private double longj;

	@Schema(description = "공개 여부")
	private String isPublic;

}
