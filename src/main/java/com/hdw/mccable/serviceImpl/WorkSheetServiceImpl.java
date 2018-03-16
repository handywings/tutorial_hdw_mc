package com.hdw.mccable.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdw.mccable.dao.WorkSheetDAO;
import com.hdw.mccable.dto.AssignWorksheetSearchBean;
import com.hdw.mccable.entity.HistoryRepair;
import com.hdw.mccable.entity.HistoryTechnicianGroupWork;
import com.hdw.mccable.entity.PersonnelAssign;
import com.hdw.mccable.entity.ProductItemWorksheet;
import com.hdw.mccable.dto.WorksheetSearchBean;
import com.hdw.mccable.entity.Worksheet;
import com.hdw.mccable.entity.WorksheetAddPoint;
import com.hdw.mccable.entity.WorksheetAddSetTopBox;
import com.hdw.mccable.entity.WorksheetAnalyzeProblems;
import com.hdw.mccable.entity.WorksheetBorrow;
import com.hdw.mccable.entity.WorksheetConnect;
import com.hdw.mccable.entity.WorksheetCut;
import com.hdw.mccable.entity.WorksheetMove;
import com.hdw.mccable.entity.WorksheetMovePoint;
import com.hdw.mccable.entity.WorksheetReducePoint;
import com.hdw.mccable.entity.WorksheetRepairConnection;
import com.hdw.mccable.entity.WorksheetTune;
import com.hdw.mccable.service.WorkSheetService;
import com.hdw.mccable.utils.Pagination;

@Service
public class WorkSheetServiceImpl implements WorkSheetService{
	
	private WorkSheetDAO workSheetDAO;

	public void setWorkSheetDAO(WorkSheetDAO workSheetDAO) {
		this.workSheetDAO = workSheetDAO;
	}

	@Transactional
	public void update(Worksheet worksheet) throws Exception {
		workSheetDAO.update(worksheet);
	}

	@Transactional
	public Long save(Worksheet worksheet) throws Exception {
		return workSheetDAO.save(worksheet);
	}

	@Transactional
	public void delete(Worksheet worksheet) throws Exception {
		workSheetDAO.delete(worksheet);
	}

	@Transactional
	public String genWorkSheetCode() throws Exception {
		return workSheetDAO.genWorkSheetCode();
	}

	@Transactional
	public List<Worksheet> searchByWorksheetCodeAndserviceApplicationId(String workSheetType, Long serviceApplicationId) {
		return workSheetDAO.searchByWorksheetCodeAndserviceApplicationId(workSheetType,serviceApplicationId);
	}
	
	@Transactional
	public Pagination getByPageForAssign(Pagination pagination, AssignWorksheetSearchBean assignWorksheetSearchBean) {
		return workSheetDAO.getByPageForAssign(pagination, assignWorksheetSearchBean);
	}
	
	@Transactional
	public int getCountTotal(AssignWorksheetSearchBean assignWorksheetSearchBean) {
		return workSheetDAO.getCountTotal(assignWorksheetSearchBean);
	}
	
	@Transactional
	public Worksheet getWorksheetById(Long id) {
		return workSheetDAO.getWorksheetById(id);
	}

	@Transactional
	public Pagination getByPageForWorksheet(Pagination pagination, WorksheetSearchBean worksheetSearchBean) {
		return workSheetDAO.getByPageForWorksheet(pagination,worksheetSearchBean);
	}

	@Transactional
	public int getCountTotal(WorksheetSearchBean worksheetSearchBean) {
		return workSheetDAO.getCountTotal(worksheetSearchBean);
	}
	
	@Transactional
	public HistoryTechnicianGroupWork findHistoryTechnicianGroupWork(Long id) {
		return workSheetDAO.findHistoryTechnicianGroupWork(id);
	}
	
	@Transactional
	public void deleteMemberAssignByHistoryTechnicianGroupWork(Long historyTechnicainGroupWork) throws Exception {
		workSheetDAO.deleteMemberAssignByHistoryTechnicianGroupWork(historyTechnicainGroupWork);
	}
	
	@Transactional
	public Long saveMemberAssign(PersonnelAssign personnelAssign) throws Exception {
		return workSheetDAO.saveMemberAssign(personnelAssign);
	}
	
	@Transactional
	public void deleteProductItemWithWorksheetId(Long worksheetId) throws Exception {
		workSheetDAO.deleteProductItemWithWorksheetId(worksheetId);
		
	}
	
	@Transactional
	public WorksheetTune getWorksheetTuneById(Long id) {
		return workSheetDAO.getWorksheetTuneById(id);
	}
	
	@Transactional
	public void updateWorksheetTune(WorksheetTune worksheetTune) throws Exception {
		workSheetDAO.updateWorksheetTune(worksheetTune);
	}
	
	@Transactional
	public void deleteProductItemWorksheet(ProductItemWorksheet productItemWorksheet) throws Exception {
		workSheetDAO.deleteProductItemWorksheet(productItemWorksheet);
	}
	
	@Transactional
	public void updateWorksheetConnect(WorksheetConnect worksheetConnect) throws Exception {
		workSheetDAO.updateWorksheetConnect(worksheetConnect);
	}
	
	@Transactional
	public WorksheetConnect getWorksheetConnectById(Long id) {
		return workSheetDAO.getWorksheetConnectById(id);
	}

	@Transactional
	public List<HistoryTechnicianGroupWork> findHistoryTechnicianGroupWorkByDateAssign(String assignDate) {
		return workSheetDAO.findHistoryTechnicianGroupWorkByDateAssign(assignDate);
	}
	
	@Transactional
	public void deleteSubWorksheetByWorksheetId(Long id) throws Exception {
		workSheetDAO.deleteSubWorksheetByWorksheetId(id);
	}
	
	@Transactional
	public void deleteProductItemWithWorksheetIdAll(Long worksheetId) throws Exception {
		workSheetDAO.deleteProductItemWithWorksheetIdAll(worksheetId);
	}
	
	@Transactional
	public WorksheetCut getWorksheetCutById(Long id) {
		return workSheetDAO.getWorksheetCutById(id);
	}

	@Transactional
	public void updateWorksheetCut(WorksheetCut worksheetCut) throws Exception {
		workSheetDAO.updateWorksheetCut(worksheetCut);
	}
	
	@Transactional
	public WorksheetAddPoint getWorksheetAddPointById(Long id) {
		return workSheetDAO.getWorksheetAddPointById(id);
	}
	
	@Transactional
	public void updateWorksheetAddPoint(WorksheetAddPoint worksheetAddPoint) throws Exception {
		workSheetDAO.updateWorksheetAddPoint(worksheetAddPoint);
	}

	@Transactional
	public WorksheetAddSetTopBox getWorksheetAddSetTopBoxById(Long id) {
		return workSheetDAO.getWorksheetAddSetTopBoxById(id);
	}

	@Transactional
	public void updateWorksheetAddSetTopBox(WorksheetAddSetTopBox worksheetAddSetTopBox) throws Exception {
		workSheetDAO.updateWorksheetAddSetTopBox(worksheetAddSetTopBox);
	}

	@Transactional
	public WorksheetBorrow getWorksheetBorrowById(Long id) {
		return workSheetDAO.getWorksheetBorrowById(id);
	}

	@Transactional
	public void updateWorksheetBorrow(WorksheetBorrow worksheetBorrow) throws Exception {
		workSheetDAO.updateWorksheetBorrow(worksheetBorrow);
	}
	
	@Transactional
	public WorksheetMove getWorksheetMoveById(Long id) {
		return workSheetDAO.getWorksheetMoveById(id);
	}

	@Transactional
	public void updateWorksheetMove(WorksheetMove worksheetMove) throws Exception {
		workSheetDAO.updateWorksheetMove(worksheetMove);
	}
	
	@Transactional
	public WorksheetMovePoint getWorksheetMovePointById(Long id) {
		return workSheetDAO.getWorksheetMovePointById(id);
	}

	@Transactional
	public void updateWorksheetMovePoint(WorksheetMovePoint worksheetMovePoint) throws Exception {
		workSheetDAO.updateWorksheetMovePoint(worksheetMovePoint);
	}

	@Transactional
	public WorksheetReducePoint getWorksheetReducePointById(Long id) {
		return workSheetDAO.getWorksheetReducePointById(id);
	}

	@Transactional
	public void updateWorksheetReducePoint(WorksheetReducePoint worksheetReducePoint) throws Exception {
		workSheetDAO.updateWorksheetReducePoint(worksheetReducePoint);
	}

	@Transactional
	public WorksheetRepairConnection getWorksheetRepairConnectionById(Long id) {
		return workSheetDAO.getWorksheetRepairConnectionById(id);
	}

	@Transactional
	public void updateWorksheetRepairConnection(WorksheetRepairConnection worksheetRepairConnection) throws Exception {
		workSheetDAO.updateWorksheetRepairConnection(worksheetRepairConnection);
	}
	
	@Transactional
	public void deleteProductItemWithWorksheetIdTypeO(Long worksheetId) throws Exception {
		workSheetDAO.deleteProductItemWithWorksheetIdTypeO(worksheetId);
	}
	
	@Transactional
	public void deleteProductItemWithWorksheetIdTypeN(Long worksheetId) throws Exception {
		workSheetDAO.deleteProductItemWithWorksheetIdTypeN(worksheetId);
	}
	
	@Transactional
	public void deleteProductItemWithWorksheetIdTypeR(Long worksheetId) throws Exception {
		workSheetDAO.deleteProductItemWithWorksheetIdTypeR(worksheetId);
	}
	
	@Transactional
	public void deleteProductItemWithWorksheetIdTypeA(Long worksheetId) throws Exception {
		workSheetDAO.deleteProductItemWithWorksheetIdTypeA(worksheetId);
	}
	
	@Transactional
	public void deleteProductItemWithWorksheetIdTypeB(Long worksheetId) throws Exception {
		workSheetDAO.deleteProductItemWithWorksheetIdTypeB(worksheetId);
	}
	
	@Transactional
	public void saveHistoryRepair(HistoryRepair historyRepair) throws Exception {
		workSheetDAO.saveHistoryRepair(historyRepair);
	}
	
	@Transactional
	public List<Worksheet> getCAList() throws Exception {
		return workSheetDAO.getCAList();
	}

	@Transactional
	public void updateStatusCA(Long id) throws Exception {
		workSheetDAO.updateStatusCA(id);
		
	}

	@Transactional
	public List<Worksheet> getDataWorksheetForReport(String reportrange, String jobType, String worksheetStatus,
			String zone, String split) {
		return workSheetDAO.getDataWorksheetForReport(reportrange, jobType, worksheetStatus, zone, split);
	}

	@Transactional
	public List<Worksheet> getDataWorksheetForReport(String reportrange, String jobType, String worksheetStatus,
			String zone, String split, String technician) {
		return workSheetDAO.getDataWorksheetForReport(reportrange, jobType, worksheetStatus, zone, split, technician);
	}

	@Transactional
	public WorksheetAnalyzeProblems getWorksheetAnalyzeProblemsById(Long id) {
		return workSheetDAO.getWorksheetAnalyzeProblemsById(id);
	}

	@Transactional
	public void updateWorksheetAnalyzeProblems(WorksheetAnalyzeProblems worksheetAnalyzeProblems) throws Exception {
		workSheetDAO.updateWorksheetAnalyzeProblems(worksheetAnalyzeProblems);
	}
	
	
}
