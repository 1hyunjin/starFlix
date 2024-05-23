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
@Schema(title = "CommentRequestDTO : 댓글요청 DTO", description = "댓글 수정, 작성에 사용하는 DTO.")
public class CommentRequestDTO {
	
	@Schema(description = "게시글번호" )
	private int bno;

	@Schema(description = "작성자" )
	private String writer;

	@Schema(description = "댓글내용" )
	private String content;
	
}