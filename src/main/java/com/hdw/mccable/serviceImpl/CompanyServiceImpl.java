package com.hdw.mccable.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdw.mccable.dao.CompanyDAO;
import com.hdw.mccable.entity.Company;
import com.hdw.mccable.entity.Document;
import com.hdw.mccable.service.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService{
	
	private CompanyDAO companyDAO;

	public void setCompanyDAO(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}
	
	@Transactional
	public Company getCompanyById(Long id) {
		return this.companyDAO.getCompanyById(id);
	}
	
	@Transactional
	public List<Company> findAll() {
		return this.companyDAO.findAll();
	}
	
	@Transactional
	public void update(Company company) throws Exception {
		this.companyDAO.update(company);	
	}
	
	@Transactional
	public Long save(Company company) throws Exception {
		return this.companyDAO.save(company);
	}
	
	@Transactional
	public void delete(Company company) throws Exception {
		this.companyDAO.delete(company);
	}
	
	@Transactional
	public Company getParentCompanyById() {
		return this.companyDAO.getParentCompanyById();
	}

	@Transactional
	public List<Document> findDocumentAll() {
		return this.companyDAO.findDocumentAll();
	}

	@Transactional
	public void save(Document document) throws Exception {
		this.companyDAO.save(document);
	}

	@Transactional
	public Document getDocumentById(Long documentId) {
		return this.companyDAO.getDocumentById(documentId);
	}

	@Transactional
	public void update(Document document) throws Exception {
		this.companyDAO.update(document);
	}

}
