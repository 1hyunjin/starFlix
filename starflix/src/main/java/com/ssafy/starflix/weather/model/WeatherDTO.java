package com.ssafy.starflix.weather.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WeatherDTO {
	
	private String region;
	
	private String fcsDate;
	
	private String fcsTime;
	
	private String weatherState;
	
	private String rainyState;
}
