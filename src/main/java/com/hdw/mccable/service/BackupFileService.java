package com.hdw.mccable.service;

import java.util.List;

import com.hdw.mccable.entity.BackupFile;

public interface BackupFileService {
	public List<BackupFile> findAll();
	public BackupFile findById(Long id);
	public Long save(BackupFile backupFile) throws Exception;
}
