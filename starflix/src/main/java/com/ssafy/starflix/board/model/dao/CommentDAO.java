package com.ssafy.starflix.board.model.dao;

import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import com.ssafy.starflix.board.model.dto.CommentDTO;

@Mapper
public interface CommentDAO {
	
	int insert(CommentDTO comment) throws SQLException;

	List<CommentDTO> selectList(int bno) throws SQLException;

	int update(CommentDTO comment) throws SQLException;

	CommentDTO selectOne(int cno) throws SQLException;

	int delete(int cno) throws SQLException;
}
