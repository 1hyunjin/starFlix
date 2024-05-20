package com.ssafy.starflix.review.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.starflix.review.model.dao.ReviewDAO;
import com.ssafy.starflix.review.model.dto.ReviewDTO;
import com.ssafy.starflix.review.model.dto.ReviewRequestDTO;

@Service
public class ReviewService {
	
	@Autowired
	private ReviewDAO rdao;
	
	public List<ReviewDTO> getList(Map<String, String> map) throws Exception{
		Map<String, String> param = new HashMap<>();
		param.put("starPlace", map.get("starPlace") == null ? "" : map.get("starPlace"));
		param.put("userId", map.get("userId") == null ? "" : map.get("userId"));
		System.out.println("param = " + param);
		return rdao.selectAll(param);
	}
	
	public void register(ReviewRequestDTO requestDTO) throws Exception {
		rdao.insert(requestDTO);
	}
	
	public void modify(int rno, ReviewRequestDTO requestDTO) throws Exception {
		rdao.update(rno, requestDTO);
	}
	
	public void deleteOne(int rno) throws Exception{
		rdao.deleteOne(rno);
	}
	public List<ReviewDTO> gptList(int idx) throws Exception{
		return rdao.selectGpt(idx);
	}
}
