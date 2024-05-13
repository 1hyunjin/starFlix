package com.ssafy.starflix.user.model.dto;

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
public class UserDTO {
	
	private String userId;
	
	private String userPw;
	
	private String userName;
	
	private String userEmail;
	

}
