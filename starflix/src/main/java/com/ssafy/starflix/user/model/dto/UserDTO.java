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
@Schema(title = "UserDTO : 회원 정보", description = "회원 정보를 나타낸다.")
public class UserDTO {
	
	@Schema(description = "아이디")
	private String userId;
	
	@Schema(description = "비밀번호")
	private String userPw;
	
	@Schema(description = "이름")
	private String userName;
	
	@Schema(description = "이메일")
	private String userEmail;
	
	@Schema(description = "refreshToken")
	private String refreshToken;
	
}
