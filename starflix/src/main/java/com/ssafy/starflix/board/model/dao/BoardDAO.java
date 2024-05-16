package com.ssafy.starflix.board.model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.starflix.board.model.dto.BoardDTO;
import com.ssafy.starflix.board.model.dto.BoardRequestDTO;

@Mapper
public interface BoardDAO {

	// 커뮤니티 게시글 목록
	List<BoardDTO> selectList(Map<String, String> param) throws SQLException;

	// 게시글 글보기
	BoardDTO selectOne(int bno) throws SQLException;

	// 게시글 작성
	void insertOne(BoardRequestDTO board) throws SQLException;

	// 게시글 수정
	void updateOne(BoardRequestDTO board) throws SQLException;

	// 게시글 삭제
	void deleteOne(int bno) throws SQLException;

	// 조회수 증가
	void updateReadCount(int bno) throws SQLException;

	// 좋아요 수 증가
	int increaseHitCount(int bno) throws SQLException;

	// 좋아요 수 감소
	int decreaseHitCount(int bno) throws SQLException;

}
