package com.ssafy.starflix.user.model.dto;

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
@Schema(title = "LoginDTO : 로그인 정보", description = "유저의 아이디와 비밀번호 정보")
public class LoginDTO {

	@Schema(description = "아이디")
	private String userId;

	@Schema(description = "비밀번호")
	private String userPw;

}
