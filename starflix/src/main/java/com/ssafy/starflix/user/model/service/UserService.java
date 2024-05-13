package com.ssafy.starflix.user.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.starflix.user.model.dao.UserDAO;
import com.ssafy.starflix.user.model.dto.UserDTO;

@Service
public class UserService {

	@Autowired
	private UserDAO udao;

	public UserDTO login(UserDTO dto) {
		return udao.login(dto);
	}

	public int regist(UserDTO dto) {
		return udao.regist(dto);
	}

	public UserDTO userInfo(String id) {
		return udao.userInfo(id);
	}

	public int changeInfo(UserDTO dto) {
		return udao.changeInfo(dto);
	}

	public int deleteUser(String id) {
		return udao.deleteUser(id);
	}

}
