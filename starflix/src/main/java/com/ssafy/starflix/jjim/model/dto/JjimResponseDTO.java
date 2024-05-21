package com.ssafy.starflix.jjim.model.dto;

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
@Schema(title = "JjimResponseDTO : 유저가 찜한 명소 정보")
public class JjimResponseDTO {
	
	@Schema(description = "찜 번호")
	private int idx;
	
	@Schema(description = "userId")
	private String userId;
	
	@Schema(description = "명소 번호")
	private int starPlace;
	
	@Schema(description = "명소 이름")
	private String title;
	
	@Schema(description = "명소 사진")
	private String img;

}
