package com.ssafy.starflix.user.controller;

import java.util.HashMap;
import java.util.Map;

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

import com.ssafy.starflix.user.model.dto.LoginDTO;
import com.ssafy.starflix.user.model.dto.UserDTO;
import com.ssafy.starflix.user.model.service.UserService;
import com.ssafy.starflix.util.JWTUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
@Tag(name = "회원 컨트롤러", description = "로그인 로그아웃, 토큰처리등 회원의 인증관련 처리하는 클래스.")
public class UserController {

	@Autowired
	private UserService uservice;
	
	@Autowired
	private JWTUtil jwtUtil;

	// 로그인
	@Operation(summary = "로그인", description = "아이디와 비밀번호를 이용하여 로그인 처리.")
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody @Parameter(description = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) LoginDTO loginDTO) {
		
		log.info("login user : {}", loginDTO);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		
		try {
			LoginDTO loginUser = uservice.login(loginDTO);
			if(loginUser != null) {
				String accessToken = jwtUtil.createAccessToken(loginUser.getUserId());
				String refreshToken = jwtUtil.createRefreshToken(loginUser.getUserId());
				log.debug("access token : {}", accessToken);
				log.debug("refresh token : {}", refreshToken);
				
				// 발급받은 refresh token을 DB에 저장 
				uservice.saveRefreshToken(loginUser.getUserId(), refreshToken);
				
				// JSON으로 token을 전달 
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token", refreshToken);
				
				status = HttpStatus.CREATED; // created -> 201 
				
			} else {
				resultMap.put("message", "아이디 또는 패스워드를 확인하세요");
				status = HttpStatus.UNAUTHORIZED;
			}
		} catch (Exception e) {
			log.debug("로그인 에러 발생 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
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
