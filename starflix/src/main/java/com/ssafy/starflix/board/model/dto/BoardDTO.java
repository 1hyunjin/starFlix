package com.ssafy.starflix.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class BoardDTO {
	
	private int bno;
	private String title;
	private String content;
	private String writer;
	private int readCount;
	private int hitCount; 
	private String registerTime;
	
	
}
