package com.ssafy.starflix.api.model.dto;

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
public class MeteorDTO {
	
	private int year;
	
	private String locdate;
	
	private String astroTitle;
	
	private String astroEvent;

}
