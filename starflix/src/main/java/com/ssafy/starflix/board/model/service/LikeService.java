package com.ssafy.starflix.board.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ssafy.starflix.board.model.dao.LikeDAO;

@Service
public class LikeService {

	@Autowired
	private LikeDAO ldao;

	// 사용자가 해당 게시글에 좋아요를 했는지의 여부 확인
	public int isLikedByUser(int bno, String userId) throws Exception {
		int userLikeCnt = ldao.getLikeCountByUser(bno, userId);
		return userLikeCnt;
	}

	// 게시글마다의 좋아요 개수 확인
	public int getLikeByArticle(int bno) throws Exception {
		int articleLikeCnt = ldao.getLikeCountByArticle(bno);
		return articleLikeCnt;
	}

	// 좋아요 기록 추가
	public int addLikeArticle(int bno, String userId) throws Exception {
		return ldao.insertLike(bno, userId);
	}

	// 좋아요 기록 삭제
	public int cancelLikeArticle(int bno, String userId) throws Exception {
		return ldao.deleteLike(bno, userId);
	}

}
