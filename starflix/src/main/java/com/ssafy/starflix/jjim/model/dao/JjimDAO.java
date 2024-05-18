package com.ssafy.starflix.jjim.model.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.starflix.jjim.model.dto.JjimDTO;

@Mapper
public interface JjimDAO {
	
	void insert(@Param("userId") String userId, @Param("starPlace") int starPlace) throws SQLException;
	
	void delete(@Param("userId") String userId, @Param("starPlace") int starPlace) throws SQLException;
	
	List<JjimDTO> getList(@Param("userId") String userId) throws SQLException;
	
	int findByUserId(@Param("userId") String userId, @Param("starPlace") int starPlace ) throws SQLException;
}
