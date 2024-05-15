package com.ssafy.starflix.starPlace.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.starflix.starPlace.model.dao.CampDAO;
import com.ssafy.starflix.starPlace.model.dao.TravelDAO;
import com.ssafy.starflix.starPlace.model.dto.CampDTO;
import com.ssafy.starflix.starPlace.model.dto.TravelDTO;

@Service
public class TravelService {
	
	@Autowired
	private TravelDAO tdao;
	
	@Autowired
	private CampDAO cdao;
	
	public List<TravelDTO> getListOfType (int idx, int type) throws Exception{
		return tdao.getListOfType(idx, type);
	}
	
	public List<CampDTO> getCampingList(int idx) throws Exception{
		return cdao.getList(idx);
	}
}
