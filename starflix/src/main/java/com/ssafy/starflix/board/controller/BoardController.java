package com.ssafy.starflix.board.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.starflix.board.model.dto.BoardDTO;
import com.ssafy.starflix.board.model.service.BoardService;
import com.ssafy.starflix.board.model.service.LikeService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin("*")
@RequestMapping("/board")
@Tag(name = "게시판 컨트롤러", description = "게시판 CRUD를 처리하는 클래스.")
public class BoardController {
	@Autowired
	private BoardService bservice;

	@Autowired
	private LikeService lservice;

	// 커뮤니티 게시글 목록
	@GetMapping()
	public ResponseEntity<?> getList(@RequestParam Map<String, String> map) throws Exception {
//		log.info("getList map = {}", map);

		List<BoardDTO> list = bservice.getList(map);

		return ResponseEntity.ok().body(list);
	}

	// 게시글 글보기
	@GetMapping("/{bno}")
	public ResponseEntity<BoardDTO> getArticle(@PathVariable("bno") int bno) throws Exception {
		bservice.updateReadCount(bno);
		return ResponseEntity.ok().body(bservice.getArticle(bno));
	}

	// 게시글 글 작성
	@PostMapping("/write")
	public ResponseEntity<?> writeArticle(@RequestBody(required = false) BoardDTO board) throws Exception {
		try {
			bservice.writeArticle(board);
			return ResponseEntity.ok(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// 게시글 글 수정
	@PutMapping("/{bno}")
	public ResponseEntity<?> modifyArticle(@RequestBody BoardDTO board) throws Exception {
		try {
			bservice.modifyArticle(board);
			return ResponseEntity.ok(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// 게시글 글 삭제
	@DeleteMapping("/{bno}")
	public ResponseEntity<?> deleteArticle(@PathVariable("bno") int bno) throws Exception {
		bservice.deleteArticle(bno);
		return ResponseEntity.ok(HttpStatus.OK);
	}

	// 사용자가 해당 게시글에 좋아요를 했는지의 여부 확인
	@GetMapping("/{bno}/like")
	public ResponseEntity<Integer> isArticleLikedByUser(@PathVariable("bno") int bno,
			@RequestParam("userId") String userId) throws Exception {
		int isLiked = lservice.isLikedByUser(bno, userId);
		return ResponseEntity.ok(isLiked); // 좋아요 했으면 1, 안했으면 0
	}

//	//좋아요 update 
//	@PostMapping("/{bno}/like")
//	public ResponseEntity<?> hitArticle(@PathVariable("bno") int bno, @RequestParam("userId") String userId){
//		try {
//			bservice.likeArticle(bno, userId);
//			return ResponseEntity.ok(HttpStatus.OK);
//		} catch (Exception e) {
//			return exceptionHandling(e);
//		}
//	}

	// 좋아요 증가
	@PostMapping("/{bno}/like")
	public ResponseEntity<?> likeArticle(@PathVariable("bno") int bno, @RequestParam("userId") String userId) {
		try {
			bservice.likeArticle(bno, userId);
			return ResponseEntity.ok(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// 좋아요 감소
	@PostMapping("/{bno}/dislike")
	public ResponseEntity<?> dislikeArticle(@PathVariable("bno") int bno, @RequestParam("userId") String userId) {
		try {
			bservice.dislikeArtice(bno, userId);
			return ResponseEntity.ok(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// error
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error : " + e.getMessage());
	}

}
