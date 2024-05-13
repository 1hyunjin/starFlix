package com.ssafy.starflix.user.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.starflix.user.model.dto.UserDTO;

@Mapper
public interface UserDAO {

	UserDTO login(UserDTO user); // 로그인

	int regist(UserDTO user); // 회원가입

	UserDTO userInfo(String id); // 회원정보 불러오기

	int changeInfo(UserDTO user); // 회원정보 변경

	int deleteUser(String id); // 회원탈퇴

}
