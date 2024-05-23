package com.ssafy.starflix.board.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(title = "BoardDTO : 게시판 정보", description = "게시판 정보를 나타낸다.")
public class BoardDTO {
	
	@Schema(description = "게시글 번호")
	private int bno;
	
	@Schema(description = "제목")
	private String title;
	
	@Schema(description = "내용")
	private String content;
	
	@Schema(description = "작성자")
	private String writer;
	
	@Schema(description = "조회수")
	private int readCount;
	
	@Schema(description = "좋아요수")
	private int hitCount; 
	
	@Schema(description = "등록시간")
	private String registerTime;
	
	
}
