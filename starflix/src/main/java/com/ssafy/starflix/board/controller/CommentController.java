package com.ssafy.starflix.board.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.starflix.board.model.dto.CommentDTO;
import com.ssafy.starflix.board.model.service.CommentService;

@RestController
@CrossOrigin("*")
@RequestMapping("/comments")
public class CommentController {

	@Autowired
	private CommentService cservice;

	// 댓글 등록
	@PostMapping("")
	public ResponseEntity<?> writeComment(@RequestBody CommentDTO comment) throws Exception {
		try {
			cservice.writeComment(comment);
			return ResponseEntity.ok(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// 게시글에 대한 댓글 리스트 조회
	@GetMapping("/list/{bno}")
	public ResponseEntity<?> getComments(@PathVariable("bno") int bno) throws Exception {
		try {
			List<CommentDTO> comments = cservice.getComments(bno);
			return ResponseEntity.ok().body(comments);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// 댓글 상세 조회
	@GetMapping("/{cno}")
	public ResponseEntity<?> getComment(@PathVariable("cno") int cno) {
		try {
			CommentDTO comment = cservice.readComment(cno);
			return ResponseEntity.ok().body(comment);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// 댓글 수정
	@PutMapping("/{cno}")
	public ResponseEntity<?> updateComment(@PathVariable("cno") int cno, @RequestBody CommentDTO comment)
			throws Exception {
		try {
			cservice.updateComment(comment);
			return ResponseEntity.ok(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// 댓글 삭제
	@DeleteMapping("/{cno}")
	public ResponseEntity<?> deleteComment(@PathVariable("cno") int cno) throws Exception {
		try {
			cservice.deleteComment(cno);
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
