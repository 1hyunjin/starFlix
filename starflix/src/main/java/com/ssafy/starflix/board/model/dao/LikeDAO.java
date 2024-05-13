package com.ssafy.starflix.board.model.dao;

import java.sql.SQLException;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikeDAO {

	// 사용자가 하나의 게시글에 좋아요를 눌렀는지 확인 여부
	int getLikeCountByUser(@Param("bno") int bno, @Param("userId") String userId) throws SQLException;

	// 게시글마다의 좋아요 개수 확인
	int getLikeCountByArticle(@Param("bno") int bno) throws SQLException;

	// 좋아요 누르기
	int insertLike(@Param("bno") int bno, @Param("userId") String userId) throws SQLException;

	// 좋아요 취소
	int deleteLike(@Param("bno") int bno, @Param("userId") String userId) throws SQLException;

}
