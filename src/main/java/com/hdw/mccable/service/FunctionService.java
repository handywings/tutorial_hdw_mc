package com.hdw.mccable.service;

import java.util.List;

import com.hdw.mccable.entity.Function;

public interface FunctionService {
	public Function getFunctionById(Long id);
	public List<Function> findAll();
	public void update(Function function) throws Exception;
	public Long save(Function function) throws Exception;
	public void delete(Function function) throws Exception;
}
