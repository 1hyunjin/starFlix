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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.starflix.starPlace.model.dto.CampDTO;
import com.ssafy.starflix.starPlace.model.dto.ReviewDTO;
import com.ssafy.starflix.starPlace.model.dto.StarPlaceDTO;
import com.ssafy.starflix.starPlace.model.dto.StarPlaceRequestDTO;
import com.ssafy.starflix.starPlace.model.dto.TravelDTO;
import com.ssafy.starflix.starPlace.model.service.ReviewService;
import com.ssafy.starflix.starPlace.model.service.StarPlaceService;
import com.ssafy.starflix.starPlace.model.service.TravelService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin("*")
@Tag(name = "별자리 명소 컨트롤러", description = "명소 CRUD 및 관광지 정보를 처리하는 클래스.")
public class StarPlaceController {

	@Autowired
	private StarPlaceService sservice;

	@Autowired
	private ReviewService rservice;
	
	@Autowired
	private TravelService tservice;

	@Operation(summary = "목록조회")
	@GetMapping("/starPlaces")
	public ResponseEntity<?> getList(
			@RequestParam @Parameter(description = "검색 조건(타입(title, addr) , 키워드).") Map<String, String> map)
			throws Exception {
		List<StarPlaceDTO> list = sservice.getList(map);
		for (StarPlaceDTO starPlaceDTO : list) {
			System.out.println("starPlace : " + starPlaceDTO);
		}
		return ResponseEntity.ok().body(list);
	}

	// 명소 등록 
	@Operation(summary = "명소 등록")
	@PostMapping("/starPlace")
	public ResponseEntity<?> register(@RequestBody @Parameter(description = "등록시 필요한 명소dto") StarPlaceRequestDTO requestDTO) throws Exception{
		try {
			sservice.regist(requestDTO);
			return ResponseEntity.ok(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@Operation(summary = "명소 상세조회")
	@GetMapping("/starPlace/{idx}")
	public ResponseEntity<?> getPlace(@PathVariable("idx") @Parameter(description = "명소 번호") int idx) throws Exception {
		return ResponseEntity.ok().body(sservice.read(idx));
	}

	@Operation(summary = "명소 공개 처리")
	@PutMapping("/starPlace/{idx}/public")
	public ResponseEntity<?> setPublic(@PathVariable("idx") @Parameter(description = "명소 번호") int idx)
			throws Exception {
		try {
			sservice.setPublic(idx);
			return ResponseEntity.ok(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "명소 비공개 처리")
	@PutMapping("/starPlace/{idx}/private")
	public ResponseEntity<?> setPrivate(@PathVariable("idx") @Parameter(description = "명소 번호") int idx)
			throws Exception {
		try {
			sservice.setPrivate(idx);
			return ResponseEntity.ok(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "명소 삭제")
	@DeleteMapping("/starPlace/{idx}")
	public ResponseEntity<?> deletePlace(@PathVariable("idx") @Parameter(description = "명소 번호") int idx)
			throws Exception {
		try {
			sservice.deletePlace(idx);
			return ResponseEntity.ok(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "명소 주변 관광지 타입별 조회")
	@GetMapping("/starPlace/{idx}/travels")
	public ResponseEntity<?> getTravelListOfType(@PathVariable("idx") @Parameter(description = "명소 번호") int idx,
			@RequestParam("type") @Parameter(description = "관광타입(12:관광지, 14:문화시설, 15:축제공연행사, 25:여행코스, 28:레포츠, 32:숙박, 38:쇼핑, 39:음식점)") int type) {
		try {
			List<TravelDTO> list = tservice.getListOfType(idx, type);
			return ResponseEntity.ok().body(list);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "명소 주변 캠핑정보 조회")
	@GetMapping("/starPlace/{idx}/camping")
	public ResponseEntity<?> getCampingList(@PathVariable("idx") @Parameter(description = "명소 번호") int idx) {
		try {
			List<CampDTO> list = tservice.getCampingList(idx);
			return ResponseEntity.ok().body(list);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@Operation(summary = "명소 찜하기")
	@PostMapping("/starPlace/{idx}/jjim")
	public ResponseEntity<?> jjimPlace(@PathVariable("idx") @Parameter(description = "명소 번호") int idx,
										@RequestParam("userId") @Parameter(description = "회원 아이디") String userId){
		try {
			sservice.jjimPlace(userId, idx);
			return ResponseEntity.ok(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@Operation(summary = "명소 찜 취소")
	@PostMapping("/starPlace/{idx}/unJjim")
	public ResponseEntity<?> unJjimPlace(@PathVariable("idx") @Parameter(description = "명소 번호") int idx,
										@RequestParam("userId") @Parameter(description = "회원 아이디") String userId){
		try {
			sservice.unJjimPlace(userId, idx);
			return ResponseEntity.ok(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@Operation(summary = "명소에 대한 리뷰목록")
	@GetMapping("/starPlace/{idx}/reviews")
	public ResponseEntity<?> getReviewList(@PathVariable("idx") @Parameter(description = "명소 번호") int idx){
		List<ReviewDTO> reviewList = rservice.getList(idx);
		return ResponseEntity.ok().body(reviewList);
	}

	// error
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error : " + e.getMessage());
	}

}
