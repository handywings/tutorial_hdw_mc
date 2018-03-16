package com.hdw.mccable.service;

import java.util.List;

import com.hdw.mccable.entity.TechnicianGroup;

public interface TechnicianGroupService {
	public TechnicianGroup getTechnicianGroupById(Long id);
	public List<TechnicianGroup> findAll();
	public Long save(TechnicianGroup technicianGroup) throws Exception;
	public void update(TechnicianGroup technicianGroup) throws Exception;
	public void delete(TechnicianGroup technicianGroup) throws Exception;
}
