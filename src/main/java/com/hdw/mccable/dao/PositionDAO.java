package com.hdw.mccable.dao;

import java.util.List;

import com.hdw.mccable.entity.Position;

public interface PositionDAO {
	public Position getPositionById(Long id);
	public List<Position> findAll();
	public void update(Position company) throws Exception;
	public Long save(Position company) throws Exception;
	public void delete(Position company) throws Exception;
	public List<Position> findByCompanyId(Long companyId);
	public List<String> findByDuplicatePositionName();
}
