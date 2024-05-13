package com.ssafy.starflix.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.starflix.user.model.dto.UserDTO;
import com.ssafy.starflix.user.model.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService uservice;

	// 로그인
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserDTO dto) {
		try {
			return ResponseEntity.ok().body(uservice.login(dto));

		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// 회원가입
	@PostMapping()
	public ResponseEntity<?> register(@RequestBody UserDTO dto) {
		try {
			uservice.regist(dto);
			return ResponseEntity.ok(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// 회원정보 상세
	@GetMapping("/{userId}")
	public ResponseEntity<?> userInfo(@PathVariable("userId") String id) {

		try {
			return ResponseEntity.ok().body(uservice.userInfo(id));
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// 회원정보 수정
	@PutMapping("/{userId}")
	public ResponseEntity<?> changeInfo(@RequestBody UserDTO dto) {
		try {
			uservice.changeInfo(dto);
			return ResponseEntity.ok(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// 회원정보 삭제
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable("userId") String userId) {

		try {
			uservice.deleteUser(userId);
			return ResponseEntity.ok(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
