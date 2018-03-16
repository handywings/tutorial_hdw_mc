package com.hdw.mccable.service;

import java.util.List;

import com.hdw.mccable.entity.RequisitionDocument;
import com.hdw.mccable.utils.Pagination;


public interface RequisitionDocumentService {
	public List<RequisitionDocument> searchByWithdraw(String criteria, String withdraw, String startDate, String endDate) throws Exception;
	public RequisitionDocument getRequisitionDocumentById(Long id) throws Exception;
	public Long save(RequisitionDocument requisitionDocument) throws Exception;
	public void update(RequisitionDocument requisitionDocument) throws Exception;
	public void delete(RequisitionDocument requisitionDocument) throws Exception;
	public String genRequisitionDocumentCode() throws Exception;
	public Pagination getByPage(Pagination pagination);
	public Pagination getByPageCriteria(Pagination pagination, String criteria, String withdraw, String startDate, String endDate) throws Exception;
	public int getCountTotal();
}
