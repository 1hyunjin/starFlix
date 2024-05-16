package com.ssafy.starflix.user.model.dao;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.starflix.user.model.dto.LoginDTO;
import com.ssafy.starflix.user.model.dto.UserDTO;
import com.ssafy.starflix.user.model.dto.UserRequestDTO;

@Mapper
public interface UserDAO {

	LoginDTO login(LoginDTO user) throws SQLException; // 로그인

	int regist(UserRequestDTO user) throws SQLException; // 회원가입

	UserDTO userInfo(String id) throws SQLException; // 회원정보 불러오기

	int changeInfo(UserRequestDTO user) throws SQLException; // 회원정보 변경

	int deleteUser(String id) throws SQLException; // 회원탈퇴

	void saveRefreshToken(Map<String, String> map) throws SQLException; 
	
	void deleteRefreshToken(String id) throws SQLException;
	
	String getRefreshToken(String id) throws SQLException;
}
