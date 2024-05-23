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
@Schema(title = "BoardRequestDTO : 게시판 요청 DTO", description = "게시판 수정, 작성에 사용하는 DTO.")
public class BoardRequestDTO {
	
	@Schema(description = "게시글 번호")
	private int bno;
	
	@Schema(description = "제목")
	private String title;
	
	@Schema(description = "내용")
	private String content;
	
	@Schema(description = "작성자")
	private String writer;

	public BoardRequestDTO(String title, String content, String writer) {
		super();
		this.title = title;
		this.content = content;
		this.writer = writer;
	}
}
