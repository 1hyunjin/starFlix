package com.ssafy.starflix.starPlace.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.starflix.starPlace.model.dto.StarPlaceDTO;
import com.ssafy.starflix.starPlace.model.service.StarPlaceService;

@RestController
@CrossOrigin("*")
public class StarPlaceController {

	@Autowired
	private StarPlaceService sservice;

	// 검색 조건 : type (title or addr)
	@GetMapping("/starPlaces")
	public ResponseEntity<?> getList(@RequestParam Map<String, String> map) throws Exception {
		List<StarPlaceDTO> list = sservice.getList(map);
		return ResponseEntity.ok().body(list);
	}

	// 명소 등록 - 파일 첨부 어떻게?
//	@PostMapping("/starPlace")
//	public ResponseEntity<?> register(@RequestBody StarPlaceDTO starPlace){
//		
//	}

	// 명소 상세 조회
	@GetMapping("/starPlace/{idx}")
	public ResponseEntity<?> getPlace(@PathVariable("idx") int idx) throws Exception {
		return ResponseEntity.ok().body(sservice.read(idx));
	}

	// 명소 비공개 처리
	@PutMapping("/starPlace/{idx}/public")
	public ResponseEntity<?> setPublic(@PathVariable("idx") int idx) throws Exception {
		try {
			sservice.setPublic(idx);
			return ResponseEntity.ok(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// 명소 공개 처리
	@PutMapping("/starPlace/{idx}/private")
	public ResponseEntity<?> setPrivate(@PathVariable("idx") int idx) throws Exception {
		try {
			sservice.setPrivate(idx);
			return ResponseEntity.ok(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// 명소 삭제
	@DeleteMapping("/starPlace/{idx}")
	public ResponseEntity<?> deletePlace(@PathVariable("idx") int idx) throws Exception {
		try {
			sservice.deletePlace(idx);
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
