package com.ssafy.starflix.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.starflix.jjim.model.dto.JjimDTO;
import com.ssafy.starflix.starPlace.model.service.StarPlaceService;
import com.ssafy.starflix.user.model.dto.LoginDTO;
import com.ssafy.starflix.user.model.dto.UserDTO;
import com.ssafy.starflix.user.model.dto.UserRequestDTO;
import com.ssafy.starflix.user.model.service.UserService;
import com.ssafy.starflix.util.JWTUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j
@Tag(name = "회원 컨트롤러", description = "로그인 로그아웃, 토큰처리등 회원의 인증관련 처리하는 클래스.")
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserService uservice;

	@Autowired
	private StarPlaceService sservice;

	@Autowired
	private JWTUtil jwtUtil;

	// 로그인
	@Operation(summary = "로그인", description = "아이디와 비밀번호를 이용하여 로그인 처리.")
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(
			@RequestBody @Parameter(description = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) LoginDTO loginDTO) {

		log.info("login user : {}", loginDTO);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;

		try {
			LoginDTO loginUser = uservice.login(loginDTO);
			if (loginUser != null) {
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
	@Operation(summary = "회원가입", description = "사용자 정보 입력 후 회원가입 진행")
	@PostMapping()
	public ResponseEntity<?> register(@RequestBody @Parameter(description = "호원가입 시 필요한 정보.") UserRequestDTO dto) {
		try {
			uservice.regist(dto);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@Operation(summary = "아이디로 회원인지 확인", description = "아이디 입력 시 db에서 회원존재시 email 데이터 반환, 회원 아이디 존재하지 않을 시 null 반환")
	@GetMapping()
	public ResponseEntity<?> getEmail(@RequestParam("userId") String userId) throws Exception{
		Map<String, Object> resultMap = new HashMap<>();
		String email = uservice.getEmailByUserId(userId);
		
		resultMap.put("userEmail", email);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}

	// 회원정보 상세
	@Operation(summary = "회원정보", description = "회원 정보를 담은 토큰을 반환한다. ")
	@GetMapping("/{userId}")
	public ResponseEntity<?> userInfo(@PathVariable("userId") @Parameter(description = "인증할 회원 아이디") String id,
			HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		UserDTO userDTO = uservice.userInfo(id);
		resultMap.put("userInfo", userDTO);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}

	// 회원정보 수정
	@Operation(summary = "회원정보 수정", description = "토큰 확인 후 회원정보 수정")
	@PutMapping("/{userId}")
	public ResponseEntity<?> changeInfo(@PathVariable("userId") @Parameter(description = "인증할 회원 아이디") String userId,
			@RequestBody @Parameter(description = "수정한 회원 정보") UserRequestDTO userRequestDTO,
			HttpServletRequest request) throws Exception {
		uservice.changeInfo(userRequestDTO);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	// 회원 비밀번호 변경 
	@Operation(summary = "비밀번호 수정", description = "이메일로 발급받은 비밀번호로 수정")
	@PutMapping("")
	public ResponseEntity<?> changePw(@RequestParam("userId")  @Parameter(description = "회원 아이디") String userId, @RequestParam("userPw")  @Parameter(description = "임시 비밀번호") String userPw) throws Exception {
		uservice.changePw(userId, userPw);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	// 회원정보 삭제
	@Operation(summary = "회원정보 삭제", description = "토큰 확인 후 회원정보 삭제")
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable("userId") @Parameter(description = "인증할 회원 아이디") String userId,
			HttpServletRequest request) throws Exception {

		uservice.deleteUser(userId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	// 로그아웃
	@Operation(summary = "로그아웃", description = "회원 정보를 담은 Token 을 제거한다.")
	@GetMapping("/logout")
	public ResponseEntity<?> removeToken(
			@RequestParam("userId") @Parameter(description = "로그아웃 할 회원의 아이디.", required = true) String userId) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			uservice.deleteRefreshToken(userId);
			return ResponseEntity.status(HttpStatus.OK).build();

		} catch (Exception e) {
			log.error("로그아웃 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			return new ResponseEntity<Map<String, Object>>(resultMap, status);
		}
	}

	@Operation(summary = "Access Token 재발급", description = "만료된 access token 을 재발급 받는다.")
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestParam String userId, HttpServletRequest request) throws Exception {

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refreshToken");
		log.debug("token : {}, userId : {} ", token, userId);

		if (token.equals(uservice.getRefreshToken(userId))) {
			String accessToken = jwtUtil.createAccessToken(userId);
			log.debug("accessToken : {}", accessToken);
			log.debug("정상적으로 accessToken 재발급!!");
			resultMap.put("access-token", accessToken);
			status = HttpStatus.OK;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
