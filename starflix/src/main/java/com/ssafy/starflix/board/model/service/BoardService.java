package com.ssafy.starflix.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ssafy.starflix.board.model.dao.BoardDAO;
import com.ssafy.starflix.board.model.dto.BoardDTO;

@Service
public class BoardService {

	@Autowired
	private BoardDAO bdao;

	@Autowired
	private LikeService lservice;

	public List<BoardDTO> getList(Map<String, String> map) throws Exception {
		Map<String, String> param = new HashMap<String, String>();
		param.put("type", map.get("type") == null ? "" : map.get("type"));
		param.put("keyword", map.get("keyword") == null ? "" : map.get("keyword"));
		return bdao.selectList(param);
	}

	public BoardDTO getArticle(int bno) throws Exception {
		return bdao.selectOne(bno);
	}

	public void writeArticle(BoardDTO board) throws Exception {
		bdao.insertOne(board);
	}

	public void modifyArticle(BoardDTO board) throws Exception {
		bdao.updateOne(board);
	}

	public void deleteArticle(int bno) throws Exception {
		bdao.deleteOne(bno);
	}

	public void updateReadCount(int bno) throws Exception {
		bdao.updateReadCount(bno);
	}

//	public int likeArticle(int bno, String userId) throws Exception {
//		System.out.println(bno);
//		// 사용자가 이미 해당 게시글을 좋아요 눌렀는지 확인
//		int likeCnt = lservice.isLikedByUser(bno, userId);
//		
//		System.out.println("likeCnt : " + likeCnt);
//
//		// 이미 좋아요를 누른 상태 -> 좋아요를 취소하고 hit_count 를 감소해야함.
//		if (likeCnt == 1) {
//			System.out.println("이미 좋아요를 누른 상태임!!!");
//			lservice.cancelLikeArticle(bno, userId);
//			return bdao.decreaseHitCount(bno);
//		}
//		// 좋아요를 누르지 않은 경우, 좋아요 추가하고 likeCount를 증가시킴.
//		else {
//			System.out.println("좋아요 안누른상태임!!!!");
//			lservice.addLikeArticle(bno, userId);
//			return bdao.increaseHitCount(bno);
//		}
//	}

	// 좋아요를 누르지 않은 경우, 좋아요 추가하고 likeCount를 증가시킴.
	public int likeArticle(int bno, String userId) throws Exception {
		lservice.addLikeArticle(bno, userId);
		return bdao.increaseHitCount(bno);
	}

	// 이미 좋아요를 누른 상태 -> 좋아요를 취소하고 hit_count 를 감소해야함.
	public int dislikeArtice(int bno, String userId) throws Exception {
		lservice.cancelLikeArticle(bno, userId);
		return bdao.decreaseHitCount(bno);
	}
}
