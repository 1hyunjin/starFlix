package com.ssafy.starflix.starPlace.model.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.starflix.starPlace.model.dto.JjimDTO;

@Mapper
public interface JjimDAO {
	
	void insert(@Param("userId") String userId, @Param("idx") int idx) throws SQLException;
	
	void delete(@Param("userId") String userId, @Param("idx") int idx) throws SQLException;
	
	List<JjimDTO> getList(@Param("userId") String userId) throws SQLException;
}
