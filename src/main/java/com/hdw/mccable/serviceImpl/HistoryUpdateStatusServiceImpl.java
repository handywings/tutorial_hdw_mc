package com.hdw.mccable.serviceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdw.mccable.dao.HistoryUpdateStatusDAO;
import com.hdw.mccable.entity.HistoryUpdateStatus;
import com.hdw.mccable.service.HistoryUpdateStatusService;

@Service
public class HistoryUpdateStatusServiceImpl implements HistoryUpdateStatusService{
	
	private HistoryUpdateStatusDAO historyUpdateStatusDAO;

	public void setHistoryUpdateStatusDAO(HistoryUpdateStatusDAO historyUpdateStatusDAO) {
		this.historyUpdateStatusDAO = historyUpdateStatusDAO;
	}

	@Transactional
	public void update(HistoryUpdateStatus historyUpdateStatus) throws Exception {
		historyUpdateStatusDAO.update(historyUpdateStatus);
	}
	
	@Transactional
	public Long save(HistoryUpdateStatus historyUpdateStatus) throws Exception {
		return historyUpdateStatusDAO.save(historyUpdateStatus);
	}

}
