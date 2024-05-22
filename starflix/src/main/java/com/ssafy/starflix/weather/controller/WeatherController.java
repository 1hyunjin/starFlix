package com.ssafy.starflix.weather.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.starflix.weather.model.WeatherDTO;
import com.ssafy.starflix.weather.model.service.WeatherService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/weather")
@CrossOrigin("*")
@Tag(name = "날씨 컨트롤러")
public class WeatherController {

	@Autowired
	private WeatherService wservice;

	@Operation(summary = "현재 기준 3일동안의 밤 9시 날씨 예보 정보 조회")
	@GetMapping(value = "", produces = "application/json")
	public List<List<WeatherDTO>> getWeatherData(@RequestParam("date")  @Parameter( description =  "- 하늘상태(SKY) 코드 : 맑음(1), 구름많음(3), 흐림(4)\n"
			+ "- 강수형태(PTY) 코드 : (단기) 없음(0), 비(1), 비/눈(2), 눈(3), 소나기(4)") String date) throws Exception {
		try {
			return wservice.getWeatherDataForRegion(date);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
