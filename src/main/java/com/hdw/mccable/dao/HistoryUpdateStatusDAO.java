package com.hdw.mccable.dao;

import com.hdw.mccable.entity.HistoryUpdateStatus;

public interface HistoryUpdateStatusDAO {
	public void update(HistoryUpdateStatus historyUpdateStatus) throws Exception;
	public Long save(HistoryUpdateStatus historyUpdateStatus) throws Exception;
}
