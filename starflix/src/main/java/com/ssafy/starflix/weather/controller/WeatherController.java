package com.ssafy.starflix.weather.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.starflix.weather.WeatherService;
import com.ssafy.starflix.weather.model.WeatherDTO;

@RestController
@RequestMapping("/weather")
@CrossOrigin("*")
public class WeatherController {

	@Autowired
	private WeatherService wservice;

	@GetMapping(value = "", produces = "application/json")
	public List<WeatherDTO> getWeatherData(@RequestParam("date") String date) throws Exception {
		try {
			return wservice.getWeatherDataForRegion(date);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
