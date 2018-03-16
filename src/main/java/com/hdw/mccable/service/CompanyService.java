package com.hdw.mccable.service;

import java.util.List;

import com.hdw.mccable.entity.Company;
import com.hdw.mccable.entity.Document;

public interface CompanyService {
	public Company getCompanyById(Long id);
	public List<Company> findAll();
	public void update(Company company) throws Exception;
	public Long save(Company company) throws Exception;
	public void delete(Company company) throws Exception;
	public Company getParentCompanyById();
	
	public List<Document> findDocumentAll();
	
	public void save(Document document) throws Exception;
	public Document getDocumentById(Long documentId);
	public void update(Document document) throws Exception;
	
}
