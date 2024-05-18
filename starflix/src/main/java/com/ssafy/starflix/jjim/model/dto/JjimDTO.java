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
@Schema(title = "JjimDTO : 찜 정보", description = "유저가 찜한 명소 정보")
public class JjimDTO {
	
	@Schema(description = "찜 번호")
	private int idx;
	
	@Schema(description = "userId")
	private String userId;
	
	@Schema(description = "명소 번호")
	private int starPlace;

}
