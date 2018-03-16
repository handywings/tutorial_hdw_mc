package com.hdw.mccable.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdw.mccable.dao.RequisitionDocumentDAO;
import com.hdw.mccable.entity.RequisitionDocument;
import com.hdw.mccable.service.RequisitionDocumentService;
import com.hdw.mccable.utils.Pagination;

@Service
public class RequisitionDocumentServiceImpl implements RequisitionDocumentService{

	private RequisitionDocumentDAO requisitionDocumentDAO;
	
	public void setRequisitionDocumentDAO(RequisitionDocumentDAO requisitionDocumentDAO) {
		this.requisitionDocumentDAO = requisitionDocumentDAO;
	}

	@Transactional
	public List<RequisitionDocument> searchByWithdraw(String criteria, String withdraw, String startDate,
			String endDate) throws Exception {
		return requisitionDocumentDAO.searchByWithdraw(criteria, withdraw, startDate, endDate);
	}

	@Transactional
	public RequisitionDocument getRequisitionDocumentById(Long id) throws Exception {
		return requisitionDocumentDAO.getRequisitionDocumentById(id);
	}

	@Transactional
	public Long save(RequisitionDocument requisitionDocument) throws Exception {
		return requisitionDocumentDAO.save(requisitionDocument);
	}

	@Transactional
	public void update(RequisitionDocument requisitionDocument) throws Exception {
		requisitionDocumentDAO.update(requisitionDocument);
	}

	@Transactional
	public void delete(RequisitionDocument requisitionDocument) throws Exception {
		requisitionDocumentDAO.delete(requisitionDocument);
	}

	@Transactional
	public String genRequisitionDocumentCode() throws Exception {
		return requisitionDocumentDAO.genRequisitionDocumentCode();
	}

	@Transactional
	public Pagination getByPage(Pagination pagination) {
		return requisitionDocumentDAO.getByPage(pagination);
	}

	@Transactional
	public int getCountTotal() {
		return requisitionDocumentDAO.getCountTotal();
	}

	@Transactional
	public Pagination getByPageCriteria(Pagination pagination, String criteria, String withdraw, String startDate,
			String endDate) throws Exception {
		return requisitionDocumentDAO.getByPageCriteria(pagination, criteria, withdraw, startDate, endDate);
	}

}
