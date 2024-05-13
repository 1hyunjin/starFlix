package com.ssafy.starflix.starPlace.model.dto;

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
public class StarPlaceDTO {

	// 명소 번호
	private int idx;

	// 명소명
	private String title;

	// 명소 주소
	private String addr;

	// 설명
	private String content;

	// 이미지
	private String img;

	// 위도
	private double lati;

	// 경도
	private double longj;

	// 공개 여부
	private String isPublic;
	
	// 일시
	private String registerTime;
	
	
}
