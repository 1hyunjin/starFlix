package com.ssafy.starflix.review.controller;

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

import com.ssafy.starflix.review.model.dto.ReviewDTO;
import com.ssafy.starflix.review.model.dto.ReviewRequestDTO;
import com.ssafy.starflix.review.model.service.ReviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin("*")
@Tag(name = "리뷰 컨트롤러", description = "명소에 대한 리뷰 CRUD 클래스.")
@RequestMapping("/reviews")
public class ReviewController {

	@Autowired
	private ReviewService rservice;

	@Operation(summary = "리뷰 목록 조회")
	@GetMapping()
	public ResponseEntity<?> getReviews(
			@RequestParam @Parameter(description = "리뷰 조회 조건(명소 또는 유저 아이디).") Map<String, String> map)
			throws Exception {
		System.out.println(map);
		List<ReviewDTO> reviewList = rservice.getList(map);
		return ResponseEntity.ok().body(reviewList);
	}

	@Operation(summary = "명소 리뷰 등록")
	@PostMapping()
	public ResponseEntity<?> registReivew(
			@RequestBody @Parameter(description = "reviewRequestDTO") ReviewRequestDTO requestDTO) throws Exception {
		try {
			rservice.register(requestDTO);
			return ResponseEntity.ok(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "명소 리뷰 수정")
	@PutMapping("/{rno}")
	public ResponseEntity<?> updateReview(@PathVariable("rno") @Parameter(description = "리뷰번호") int rno,
			@Parameter(description = "수정할 requestDTO") @RequestBody ReviewRequestDTO requestDTO) throws Exception {
		try {
			rservice.modify(rno, requestDTO);
			return ResponseEntity.ok(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "명소 리뷰 삭제")
	@DeleteMapping("/{rno}")
	public ResponseEntity<?> deleteReview(@PathVariable("rno") @Parameter(description = "리뷰번호")  int rno) throws Exception {
		try {
			rservice.deleteOne(rno);
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
