package com.hdw.mccable.dao;

import com.hdw.mccable.entity.DocumentFile;

public interface DocumentFileDAO {

	public DocumentFile getDocumentFileById(Long id);
	public Long save(DocumentFile documentFile) throws Exception;
	public void update(DocumentFile documentFile) throws Exception;

}
