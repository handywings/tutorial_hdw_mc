package com.hdw.mccable.service;

import com.hdw.mccable.entity.HistoryUpdateStatus;

public interface HistoryUpdateStatusService {
	public void update(HistoryUpdateStatus historyUpdateStatus) throws Exception;
	public Long save(HistoryUpdateStatus historyUpdateStatus) throws Exception;
}
