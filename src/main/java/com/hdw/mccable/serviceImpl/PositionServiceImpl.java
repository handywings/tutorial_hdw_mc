package com.hdw.mccable.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdw.mccable.dao.PositionDAO;
import com.hdw.mccable.entity.Position;
import com.hdw.mccable.service.PositionService;

@Service
public class PositionServiceImpl implements PositionService{

	private PositionDAO positionDAO;
	
	public void setPositionDAO(PositionDAO positionDAO) {
		this.positionDAO = positionDAO;
	}
	
	@Transactional
	public Position getPositionById(Long id) {
		return this.positionDAO.getPositionById(id);
	}

	@Transactional
	public List<Position> findAll() {
		return this.positionDAO.findAll();
	}

	@Transactional
	public void update(Position position) throws Exception {
		this.positionDAO.update(position);	
	}

	@Transactional
	public Long save(Position position) throws Exception {
		return this.positionDAO.save(position);
	}

	@Transactional
	public void delete(Position position) throws Exception {
		this.positionDAO.delete(position);
	}
	
	@Transactional
	public List<Position> findByCompanyId(Long companyId) {
		return positionDAO.findByCompanyId(companyId);
	}
	
	@Transactional
	public List<String> findByDuplicatePositionName() {
		return positionDAO.findByDuplicatePositionName();
	}

}
