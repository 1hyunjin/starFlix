package com.ssafy.starflix.review.model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.starflix.review.model.dto.ReviewDTO;
import com.ssafy.starflix.review.model.dto.ReviewRequestDTO;

@Mapper
public interface ReviewDAO {
	
	// 명소에 대한 리뷰 목록 조회
	List<ReviewDTO> selectAll(Map<String, String> param) throws SQLException;
	
	void insert(ReviewRequestDTO requestDTO) throws SQLException;
	
	void update(@Param("rno") int rno, @Param("reviewDTO") ReviewRequestDTO reviewDTO) throws SQLException;
	
	void deleteOne(@Param("rno") int rno) throws SQLException;
	
	List<ReviewDTO> selectGpt(int idx) throws SQLException;
	
	ReviewDTO selectOne(int rno) throws SQLException;
}
