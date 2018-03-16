package com.hdw.mccable.dao;

import com.hdw.mccable.entity.HistoryUseEquipment;

public interface HistoryUseEquipmentDAO {

	HistoryUseEquipment getHistoryUseEquipmentById(Long id);
	void update(HistoryUseEquipment historyUseEquipment) throws Exception;
	Long save(HistoryUseEquipment historyUseEquipment) throws Exception;

}
