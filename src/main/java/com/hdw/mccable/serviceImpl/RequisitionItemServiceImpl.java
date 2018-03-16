package com.hdw.mccable.serviceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdw.mccable.dao.RequisitionItemDAO;
import com.hdw.mccable.entity.RequisitionItem;
import com.hdw.mccable.service.RequisitionItemService;

@Service
public class RequisitionItemServiceImpl implements RequisitionItemService{

	private RequisitionItemDAO requisitionItemDAO;
	
	public void setRequisitionItemDAO(RequisitionItemDAO requisitionItemDAO) {
		this.requisitionItemDAO = requisitionItemDAO;
	}

	@Transactional
	public RequisitionItem getRequisitionItemById(Long id) throws Exception {
		return requisitionItemDAO.getRequisitionItemById(id);
	}

	@Transactional
	public Long save(RequisitionItem requisitionItem) throws Exception {
		return requisitionItemDAO.save(requisitionItem);
	}

	@Transactional
	public void update(RequisitionItem requisitionItem) throws Exception {
		requisitionItemDAO.update(requisitionItem);
	}

	@Transactional
	public void delete(RequisitionItem requisitionItem) throws Exception {
		requisitionItemDAO.delete(requisitionItem);
	}

}
