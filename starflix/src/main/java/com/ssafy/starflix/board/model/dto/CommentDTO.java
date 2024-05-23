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
@Schema(title = "CommentDTO : 댓글DTO", description = "댓글 조회, 리스트 조회에 사용.")
public class CommentDTO {
	
	@Schema(description = "댓글번호" )
	private int cno;

	@Schema(description = "게시글번호" )
	private int bno;

	@Schema(description = "작성자" )
	private String writer;

	@Schema(description = "댓글내용" )
	private String content;
	
	@Schema(description = "등록시간" )
	private String registerTime;
	
}
