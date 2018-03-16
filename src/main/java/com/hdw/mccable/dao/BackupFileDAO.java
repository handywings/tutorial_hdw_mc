package com.hdw.mccable.dao;

import java.util.List;

import com.hdw.mccable.entity.BackupFile;

public interface BackupFileDAO {
	public List<BackupFile> findAll();
	public BackupFile findById(Long id);
	public Long save(BackupFile backupFile) throws Exception;
}
