package com.ssafy.starflix.board.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class LikeDTO {
	
	private int bno;
	
	private String userId;

}
