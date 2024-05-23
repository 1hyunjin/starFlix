package com.ssafy.starflix.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.starflix.api.model.dto.AsteDTO;
import com.ssafy.starflix.api.model.service.AsteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/aste")
@CrossOrigin("*")
@Tag(name = "천문박명 컨트롤러", description = "12개 지역의 천문박명시간을 가져오는 클래스.")
public class AsteController {
	
	@Autowired
	private AsteService aservice;
	
	@Operation(summary = "지역별 천문박명 시간 조회")
	@GetMapping
	public List<AsteDTO> getAsteInfo(@RequestParam("date")  @Parameter(description = "날짜") String date) throws Exception {
		return aservice.getAsteInfo(date);
	}

}
