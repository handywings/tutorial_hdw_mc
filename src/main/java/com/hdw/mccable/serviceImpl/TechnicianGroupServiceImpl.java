package com.hdw.mccable.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdw.mccable.dao.TechnicianGroupDAO;
import com.hdw.mccable.entity.TechnicianGroup;
import com.hdw.mccable.service.TechnicianGroupService;

@Service
public class TechnicianGroupServiceImpl implements TechnicianGroupService{
	
	TechnicianGroupDAO technicainGroupDAO;

	public void setTechnicainGroupDAO(TechnicianGroupDAO technicainGroupDAO) {
		this.technicainGroupDAO = technicainGroupDAO;
	}

	
	@Transactional
	public List<TechnicianGroup> findAll() {
		return this.technicainGroupDAO.findAll();
	}

	@Transactional
	public Long save(TechnicianGroup technicianGroup) throws Exception {
		return this.technicainGroupDAO.save(technicianGroup);
	}

	@Transactional
	public TechnicianGroup getTechnicianGroupById(Long id) {
		return this.technicainGroupDAO.getTechnicianGroupById(id);
	}

	@Transactional
	public void update(TechnicianGroup technicianGroup) throws Exception {
		this.technicainGroupDAO.update(technicianGroup);
	}

	@Transactional
	public void delete(TechnicianGroup technicianGroup) throws Exception {
		this.technicainGroupDAO.delete(technicianGroup);
	}

}
