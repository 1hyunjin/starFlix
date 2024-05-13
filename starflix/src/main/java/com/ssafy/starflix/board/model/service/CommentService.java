package com.ssafy.starflix.board.model.service;

import org.springframework.stereotype.Service;
import com.ssafy.starflix.board.model.dao.CommentDAO;
import com.ssafy.starflix.board.model.dto.CommentDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CommentService {

	@Autowired
	private CommentDAO cdao;

	public int writeComment(CommentDTO comment) throws Exception {
		return cdao.insert(comment);
	}

	public List<CommentDTO> getComments(int bno) throws Exception {
		return cdao.selectList(bno);
	}

	public int updateComment(CommentDTO comment) throws Exception {
		return cdao.update(comment);
	}

	public CommentDTO readComment(int cno) throws Exception {
		return cdao.selectOne(cno);
	}

	public int deleteComment(int cno) throws Exception {
		return cdao.delete(cno);
	}

}
