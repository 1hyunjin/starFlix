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
@Schema(title = "CampDTO : 캠핑정보", description = "캠핑정보를 나타낸다.")
public class CampDTO {
	
	@Schema(description = "캠핑idx")
	private int idx; 
	
	@Schema(description = "이름")
	private String title;
	
	@Schema(description = "주소")
	private String addr;
	
	@Schema(description = "내용")
	private String content;
	
	@Schema(description = "사진")
	private String img;
	
	@Schema(description = "위도")
	private double lati;

	@Schema(description = "경도")
	private double longj;
	
	@Schema(description = "관광타입")
	private int type;
}
