package com.ssafy.starflix.api.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Schema(title = "AsetDTO : 천문박명 시간 및 지역, 날짜 info DTO")
public class AsteDTO {
	
	// 지역
	private String location;
	
	// 날짜
	private String locdate;
	
	// 천문박명 시간
	private String aste;
	
}
