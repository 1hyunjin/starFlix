package com.ssafy.starflix.api.model.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.starflix.api.model.dto.MeteorDTO;

@Mapper
public interface MeteorDAO {
	
	void insert(MeteorDTO meteor) throws SQLException;
	
	List<MeteorDTO> selectList(int year) throws SQLException;
}
