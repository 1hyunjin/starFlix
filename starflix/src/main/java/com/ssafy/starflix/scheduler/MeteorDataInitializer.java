package com.ssafy.starflix.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ssafy.starflix.api.model.service.MeteorService;

@Component
public class MeteorDataInitializer implements CommandLineRunner {
	
	@Autowired
	private MeteorService mservice;
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("MeteorDataInitializer running ----초기화 작업 수행");
		mservice.fetchMeteorShowers();
	}

}
