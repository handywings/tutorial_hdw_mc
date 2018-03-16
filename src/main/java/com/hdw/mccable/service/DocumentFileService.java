package com.hdw.mccable.service;

import com.hdw.mccable.entity.DocumentFile;

public interface DocumentFileService {
	
	public DocumentFile getDocumentFileById(Long id);
	public Long save(DocumentFile documentFile) throws Exception;
	public void update(DocumentFile documentFile) throws Exception;
	
}
