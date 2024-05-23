package com.ssafy.starflix.jjim.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.starflix.jjim.model.dao.JjimDAO;
import com.ssafy.starflix.jjim.model.dto.JjimDTO;
import com.ssafy.starflix.jjim.model.dto.JjimResponseDTO;

@Service
public class JjimService {

	@Autowired
	private JjimDAO jdao;

	public List<JjimResponseDTO> getList(String userId) throws Exception {
		return jdao.getList(userId);
	}

	// 명소 찜하기
	public void jjimPlace(String userId, int starPlace) throws Exception {
		jdao.insert(userId, starPlace);
	}

	// 명소 찜 취소하기
	public void unJjimPlace(String userId, int starPlace) throws Exception {
		jdao.delete(userId, starPlace);
	}
	
	// 명소 상서 조회 시 명소 찜하기 눌렀는지 상태 확인 위한 메소드
	public int getStateByUserId(String userId, int  idx) throws Exception{
		return jdao.findByUserId(userId, idx);
	}
}
