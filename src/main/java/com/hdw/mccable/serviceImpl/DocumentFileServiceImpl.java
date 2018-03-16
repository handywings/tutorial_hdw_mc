package com.hdw.mccable.serviceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdw.mccable.dao.DocumentFileDAO;
import com.hdw.mccable.entity.DocumentFile;
import com.hdw.mccable.service.DocumentFileService;

@Service
public class DocumentFileServiceImpl implements DocumentFileService{

	private DocumentFileDAO documentFileDAO;
	
	public void setDocumentFileDAO(DocumentFileDAO documentFileDAO) {
		this.documentFileDAO = documentFileDAO;
	}

	@Transactional
	public DocumentFile getDocumentFileById(Long id) {
		return documentFileDAO.getDocumentFileById(id);
	}

	@Transactional
	public Long save(DocumentFile documentFile) throws Exception {
		return documentFileDAO.save(documentFile);
	}

	@Transactional
	public void update(DocumentFile documentFile) throws Exception {
		documentFileDAO.update(documentFile);
	}



}
