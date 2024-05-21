package com.ssafy.starflix.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.starflix.api.model.dto.GptReviewDTO;
import com.ssafy.starflix.api.model.service.GptService;
import com.ssafy.starflix.review.model.dto.ReviewDTO;
import com.ssafy.starflix.review.model.service.ReviewService;

@RestController
@RequestMapping("/gpt")
public class GptController {
	
	@Autowired
	private GptService gptService;
	
	@Autowired
	private ReviewService rservice; 
	
	@GetMapping("/reviews")
	public ResponseEntity<?> generateReviews(@RequestParam int idx) throws Exception{
		List<ReviewDTO> reviewList = rservice.gptList(idx);
		GptReviewDTO result = gptService.generateAnswers(reviewList);
		return ResponseEntity.ok(result);
	}
}
