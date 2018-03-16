package com.hdw.mccable.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdw.mccable.dao.BackupFileDAO;
import com.hdw.mccable.entity.BackupFile;
import com.hdw.mccable.service.BackupFileService;

@Service
public class BackupFileServiceImpl implements BackupFileService{
	
	private BackupFileDAO backupFileDAO;
	
	public void setBackupFileDAO(BackupFileDAO backupFileDAO) {
		this.backupFileDAO = backupFileDAO;
	}

	
	@Transactional
	public List<BackupFile> findAll() {
		return backupFileDAO.findAll();
	}

	@Transactional
	public BackupFile findById(Long id) {
		return backupFileDAO.findById(id);
	}

	@Transactional
	public Long save(BackupFile backupFile) throws Exception {
		return backupFileDAO.save(backupFile);
	}

}
