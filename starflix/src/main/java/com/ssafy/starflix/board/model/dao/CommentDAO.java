package com.ssafy.starflix.board.model.dao;

import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.starflix.board.model.dto.CommentDTO;
import com.ssafy.starflix.board.model.dto.CommentRequestDTO;

@Mapper
public interface CommentDAO {
	
	int insert(CommentRequestDTO comment) throws SQLException;

	List<CommentDTO> selectList(int bno) throws SQLException;

	int update(  @Param("cno") int cno, @Param("content") String content) throws SQLException;

	CommentDTO selectOne(int cno) throws SQLException;

	int delete(int cno) throws SQLException;
}
