package com.hdw.mccable.serviceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdw.mccable.dao.HistoryUseEquipmentDAO;
import com.hdw.mccable.entity.HistoryUseEquipment;
import com.hdw.mccable.service.HistoryUseEquipmentService;

@Service
public class HistoryUseEquipmentServiceImpl implements HistoryUseEquipmentService{
	
	private HistoryUseEquipmentDAO historyUseEquipmentDAO;

	public void setHistoryUseEquipmentDAO(HistoryUseEquipmentDAO historyUseEquipmentDAO) {
		this.historyUseEquipmentDAO = historyUseEquipmentDAO;
	}

	@Transactional
	public HistoryUseEquipment getHistoryUseEquipmentById(Long id) {
		return historyUseEquipmentDAO.getHistoryUseEquipmentById(id);
	}

	@Transactional
	public void update(HistoryUseEquipment historyUseEquipment) throws Exception {
		historyUseEquipmentDAO.update(historyUseEquipment);
	}

	@Transactional
	public Long save(HistoryUseEquipment historyUseEquipment) throws Exception {
		return historyUseEquipmentDAO.save(historyUseEquipment);
	}
	
	
}
