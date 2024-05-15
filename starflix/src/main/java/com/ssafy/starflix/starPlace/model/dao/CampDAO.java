package com.ssafy.starflix.starPlace.model.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.starflix.starPlace.model.dto.CampDTO;

@Mapper
public interface CampDAO {
	
	List<CampDTO> getList(int idx) throws SQLException;

}
