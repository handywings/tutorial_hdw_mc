package com.hdw.mccable.service;

import com.hdw.mccable.entity.HistoryUseEquipment;

public interface HistoryUseEquipmentService {
	public HistoryUseEquipment getHistoryUseEquipmentById(Long id);
	public void update(HistoryUseEquipment historyUseEquipment) throws Exception;
	public Long save(HistoryUseEquipment historyUseEquipment) throws Exception;
}
