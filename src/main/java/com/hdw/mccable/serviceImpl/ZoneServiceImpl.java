package com.hdw.mccable.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdw.mccable.dao.ZoneDAO;
import com.hdw.mccable.entity.Zone;
import com.hdw.mccable.service.ZoneService;

@Service
public class ZoneServiceImpl implements ZoneService{

	private ZoneDAO zoneDAO;
	
	public void setZoneDAO(ZoneDAO zoneDAO) {
		this.zoneDAO = zoneDAO;
	}
	
	@Transactional
	public Zone getZoneById(Long id) {
		return this.zoneDAO.getZoneById(id);
	}

	@Transactional
	public List<Zone> findAll() {
		return this.zoneDAO.findAll();
	}

	@Transactional
	public void update(Zone position) throws Exception {
		this.zoneDAO.update(position);	
	}

	@Transactional
	public Long save(Zone position) throws Exception {
		return this.zoneDAO.save(position);
	}

	@Transactional
	public void delete(Zone position) throws Exception {
		this.zoneDAO.delete(position);
	}

	@Transactional
	public Zone getZoneByZoneDetail(String zoneDetail) {
		return this.zoneDAO.getZoneByZoneDetail(zoneDetail);
	}

}
