package com.ssafy.starflix.starPlace.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.starflix.starPlace.model.dto.ReviewDTO;

@Mapper
public interface ReviewDAO {
	
	// 명소에 대한 리뷰 목록 조회
	List<ReviewDTO> selectAll(int idx);
}
