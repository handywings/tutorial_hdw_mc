package com.hdw.mccable.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdw.mccable.dao.FunctionDAO;
import com.hdw.mccable.entity.Function;
import com.hdw.mccable.service.FunctionService;

@Service
public class FunctionServiceImpl implements FunctionService{

	private FunctionDAO functionDAO;
	
	public void setFunctionDAO(FunctionDAO functionDAO) {
		this.functionDAO = functionDAO;
	}

	@Transactional
	public Function getFunctionById(Long id) {
		return functionDAO.getFunctionById(id);
	}

	@Transactional
	public List<Function> findAll() {
		return functionDAO.findAll();
	}

	@Transactional
	public void update(Function function) throws Exception {
		functionDAO.update(function);
	}

	@Transactional
	public Long save(Function function) throws Exception {
		return functionDAO.save(function);
	}

	@Transactional
	public void delete(Function function) throws Exception {
		functionDAO.delete(function);
	}

}
