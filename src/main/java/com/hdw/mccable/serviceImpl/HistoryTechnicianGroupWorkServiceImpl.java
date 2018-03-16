package com.hdw.mccable.serviceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdw.mccable.dao.HistoryTechnicianGroupWorkDAO;
import com.hdw.mccable.entity.HistoryTechnicianGroupWork;
import com.hdw.mccable.service.HistoryTechnicianGroupWorkService;

@Service
public class HistoryTechnicianGroupWorkServiceImpl implements HistoryTechnicianGroupWorkService{
	
	private HistoryTechnicianGroupWorkDAO historyTechnicianGroupWorkDAO;
	
	public void setHistoryTechnicianGroupWorkDAO(HistoryTechnicianGroupWorkDAO historyTechnicianGroupWorkDAO) {
		this.historyTechnicianGroupWorkDAO = historyTechnicianGroupWorkDAO;
	}

	@Transactional
	public Long save(HistoryTechnicianGroupWork historyTechnicianGroupWork) throws Exception {
		return historyTechnicianGroupWorkDAO.save(historyTechnicianGroupWork);
	}
	
	@Transactional
	public void update(HistoryTechnicianGroupWork historyTechnicianGroupWork) throws Exception {
		historyTechnicianGroupWorkDAO.update(historyTechnicianGroupWork);		
	}

}
