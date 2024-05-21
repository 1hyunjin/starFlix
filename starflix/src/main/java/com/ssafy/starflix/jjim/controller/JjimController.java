package com.ssafy.starflix.jjim.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.starflix.jjim.model.dto.JjimDTO;
import com.ssafy.starflix.jjim.model.dto.JjimResponseDTO;
import com.ssafy.starflix.jjim.model.service.JjimService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin("*")
@Tag(name = "명소 찜 컨트롤러", description = "찜 CRUD  클래스.")
@RequestMapping("/jjims")
public class JjimController {

	@Autowired
	private JjimService jservice;

	@Operation(summary = "유저가 찜한 명소 목록 조회")
	@GetMapping()
	public ResponseEntity<?> getList(@RequestParam("userId") String userId) {
		try {
			List<JjimResponseDTO> list = jservice.getList(userId);
			System.out.println(list);
			return ResponseEntity.ok().body(list);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@Operation(summary = "명소 찜하기")
	@PostMapping("/jjim")
	public ResponseEntity<?> jjimPlace(@RequestParam("starPlace") @Parameter(description = "명소 번호") int starPlace, 
									@RequestParam("userId") String userId){
		try {
			jservice.jjimPlace(userId, starPlace);
			return ResponseEntity.ok(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "명소 찜 취소")
	@PostMapping("/unJjim")
	public ResponseEntity<?> unJjimPlace(@RequestParam("idx") @Parameter(description = "명소 번호") int starPlace,
			@RequestParam("userId") @Parameter(description = "회원 아이디") String userId) {
		try {
			jservice.unJjimPlace(userId, starPlace);
			return ResponseEntity.ok(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	

	// error
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error : " + e.getMessage());
	}

}
