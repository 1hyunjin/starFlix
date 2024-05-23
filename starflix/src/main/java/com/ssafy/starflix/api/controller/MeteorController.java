package com.ssafy.starflix.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.starflix.api.model.service.MeteorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin("*")
@RequestMapping("/meteor")
@Tag(name = "올해 유성우 이벤트 컨트롤러", description = "올해 유성우 이벤트 목록 조회 controller")
public class MeteorController {
	
	@Autowired
	private MeteorService mservice;
	
	@Operation(summary = "올해 유성우 이벤트 목록 조회")
	@GetMapping("")
	public ResponseEntity<?> getList(@RequestParam("year") @Parameter(description = "현재 연도") int year) throws Exception {
		return ResponseEntity.ok().body(mservice.getList(year));
	}

}
