package com.hdw.mccable.dao;

import com.hdw.mccable.entity.RequisitionItem;;

public interface RequisitionItemDAO {

	public RequisitionItem getRequisitionItemById(Long id) throws Exception;
	public Long save(RequisitionItem requisitionItem) throws Exception;
	public void update(RequisitionItem requisitionItem) throws Exception;
	public void delete(RequisitionItem requisitionItem) throws Exception;

}
