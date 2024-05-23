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
@Schema(title = "StarPlaceDTO : 관광지 정보", description = "관광지 정보를 나타낸다.")
public class TravelDTO {
	
	@Schema(description = "관광지번호")
	private int idx;
	
	@Schema(description = "관광타입")
	private int type;
	
	@Schema(description = "관광지이름")
	private String title;
	
	@Schema(description = "관광지주소")
	private String addr;
	
	@Schema(description = "관광지사진")
	private String img;
	
	@Schema(description = "시도코드")
	private int sidoCode;
	
	@Schema(description = "시도코드")
	private int gugunCode;
	
	@Schema(description = "위도")
	private double lati;

	@Schema(description = "경도")
	private double longj;

}
