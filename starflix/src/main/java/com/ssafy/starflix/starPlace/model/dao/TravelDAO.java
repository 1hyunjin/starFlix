package com.ssafy.starflix.starPlace.model.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.starflix.starPlace.model.dto.TravelDTO;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface TravelDAO {
	
	List<TravelDTO> getListOfType(@Param("idx") int idx, @Param("type") int type) throws SQLException;
}
