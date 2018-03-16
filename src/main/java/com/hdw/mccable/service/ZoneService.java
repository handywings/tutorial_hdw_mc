package com.hdw.mccable.service;

import java.util.List;

import com.hdw.mccable.entity.Zone;


public interface ZoneService {
	public Zone getZoneById(Long id);
	
	public List<Zone> findAll();
	public void update(Zone zone) throws Exception;
	public Long save(Zone zone) throws Exception;
	public void delete(Zone zone) throws Exception;
	public Zone getZoneByZoneDetail(String zoneDetail);
}
