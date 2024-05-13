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
public class CommentDTO {
	
	private int cno;

	private int bno;

	private String writer;

	private String content;
	
	private String registerTime;
	
}
