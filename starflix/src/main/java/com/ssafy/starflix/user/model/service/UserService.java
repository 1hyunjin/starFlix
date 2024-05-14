package com.ssafy.starflix.user.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.starflix.user.model.dao.UserDAO;
import com.ssafy.starflix.user.model.dto.LoginDTO;
import com.ssafy.starflix.user.model.dto.UserDTO;

@Service
public class UserService {

	@Autowired
	private UserDAO udao;

	public LoginDTO login(LoginDTO dto) throws Exception{
		return udao.login(dto);
	}

	public int regist(UserDTO dto) throws Exception{
		return udao.regist(dto);
	}

	public UserDTO userInfo(String id) throws Exception {
		return udao.userInfo(id);
	}

	public int changeInfo(UserDTO dto) throws Exception {
		return udao.changeInfo(dto);
	}

	public int deleteUser(String id) throws Exception {
		return udao.deleteUser(id);
	}
	
	public void saveRefreshToken(String userId, String refreshToken) throws Exception {
		System.out.println(userId + " , " + refreshToken);
		Map<String, String> map = new HashMap<>();
		map.put("userId", userId);
		map.put("refreshToken", refreshToken);
		udao.saveRefreshToken(map);
	}

}
