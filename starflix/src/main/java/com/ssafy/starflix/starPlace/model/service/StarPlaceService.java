package com.ssafy.starflix.starPlace.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.starflix.starPlace.model.dao.JjimDAO;
import com.ssafy.starflix.starPlace.model.dao.StarPlaceDAO;
import com.ssafy.starflix.starPlace.model.dto.StarPlaceDTO;

@Service
public class StarPlaceService {

	@Autowired
	private StarPlaceDAO sdao;

	@Autowired
	private JjimDAO jdao;

	// type (title, addr) & keyword로 목록 조회
	public List<StarPlaceDTO> getList(Map<String, String> map) throws Exception {

		Map<String, String> param = new HashMap<String, String>();
		param.put("type", map.get("type") == null ? "" : map.get("type"));
		param.put("keyword", map.get("keyword") == null ? "" : map.get("keyword"));
		return sdao.selectList(param);
	}

	// 명소 상세 조회
	public StarPlaceDTO read(int idx) throws Exception {
		return sdao.selectOne(idx);
	}

	// 명소 공개 처리
	public int setPublic(int idx) throws Exception {
		return sdao.updatePublic(idx);
	}

	// 명소 비공개 처리
	public int setPrivate(int idx) throws Exception {
		return sdao.updatePrivate(idx);
	}

	// 명소 삭제
	public int deletePlace(int idx) throws Exception {
		return sdao.deleteOne(idx);
	}

	// 명소 찜하기
	public void jjimPlace(String userId, int idx) throws Exception {
		jdao.insert(userId, idx);
	}

	// 명소 찜 취소하기
	public void unJjimPlace(String userId, int idx) throws Exception {
		jdao.delete(userId, idx);
	}
}
