package com.ssafy.starflix.weather.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	
	private Date fcsDateTime; // 추가된 필드 
	
	private String weatherState;
	
	private String rainyState;
	
	public Date getFcsDateTime() {
		return fcsDateTime;
	}
	
	public void setFcsDateTime(String fcsDate, String fscTime) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HHmm");
        this.fcsDateTime = sdf.parse(fcsDate + " " + fcsTime);
	}
}
