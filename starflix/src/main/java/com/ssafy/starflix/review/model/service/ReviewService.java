package com.ssafy.starflix.starPlace.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.starflix.starPlace.model.dao.ReviewDAO;
import com.ssafy.starflix.starPlace.model.dto.ReviewDTO;

@Service
public class ReviewService {
	
	@Autowired
	private ReviewDAO rdao;
	
	public List<ReviewDTO> getList(int idx){
		return rdao.selectAll(idx);
	}
}
