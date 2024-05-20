package com.ssafy.starflix.starPlace.model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.starflix.starPlace.model.dto.StarPlaceDTO;
import com.ssafy.starflix.starPlace.model.dto.StarPlaceRequestDTO;

@Mapper
public interface StarPlaceDAO {
	
	List<StarPlaceDTO> selectList(Map<String, String> param) throws SQLException;
	
	int insert(StarPlaceRequestDTO requestDTO) throws SQLException;
	
	StarPlaceDTO selectOne(int idx) throws SQLException;
	
	// 명소 공개 처리 
	int updatePublic(int idx) throws SQLException;
	
	// 명소 비공개 처리 
	int  updatePrivate(int idx) throws SQLException;
	
	// 명소 삭제 
	int deleteOne(int idx) throws SQLException;
	
	// Best 명소 10건 조회
	List<StarPlaceDTO> selectBestList() throws SQLException;
	
}
