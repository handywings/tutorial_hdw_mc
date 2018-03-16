package com.hdw.mccable.service;

import java.util.List;

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
import com.hdw.mccable.utils.Pagination;

public interface WorkSheetService {
	public void update(Worksheet worksheet) throws Exception;
	public Long save(Worksheet worksheet) throws Exception;
	public void delete(Worksheet worksheet) throws Exception;
	public String genWorkSheetCode() throws Exception;
	public List<Worksheet> searchByWorksheetCodeAndserviceApplicationId(String workSheetType, Long serviceApplicationId);
	public Pagination getByPageForAssign(Pagination pagination,AssignWorksheetSearchBean assignWorksheetSearchBean);
	public int getCountTotal(AssignWorksheetSearchBean assignWorksheetSearchBean);
	public Worksheet getWorksheetById(Long id);
	public HistoryTechnicianGroupWork findHistoryTechnicianGroupWork(Long id);
	public Pagination getByPageForWorksheet(Pagination pagination,WorksheetSearchBean worksheetSearchBean);
	public int getCountTotal(WorksheetSearchBean worksheetSearchBean);
	public void deleteMemberAssignByHistoryTechnicianGroupWork(Long historyTechnicainGroupWork) throws Exception;
	public Long saveMemberAssign(PersonnelAssign personnelAssign) throws Exception;
	public void deleteProductItemWithWorksheetId(Long worksheetId) throws Exception;
	public WorksheetTune getWorksheetTuneById(Long id);
	public void updateWorksheetTune(WorksheetTune worksheetTune) throws Exception;
	public void deleteProductItemWorksheet(ProductItemWorksheet productItemWorksheet) throws Exception;
	public void updateWorksheetConnect(WorksheetConnect worksheetConnect) throws Exception;
	public WorksheetConnect getWorksheetConnectById(Long id);
	public List<HistoryTechnicianGroupWork> findHistoryTechnicianGroupWorkByDateAssign(String assignDate);
	public void deleteSubWorksheetByWorksheetId(Long id) throws Exception;
	public void deleteProductItemWithWorksheetIdAll(Long worksheetId) throws Exception;
	//cut worksheet
	public WorksheetCut getWorksheetCutById(Long id);
	public void updateWorksheetCut(WorksheetCut worksheetCut) throws Exception;
	//add point worksheet
	public WorksheetAddPoint getWorksheetAddPointById(Long id);
	public void updateWorksheetAddPoint(WorksheetAddPoint worksheetAddPoint) throws Exception;
	//add settop box
	public WorksheetAddSetTopBox getWorksheetAddSetTopBoxById(Long id);
	public void updateWorksheetAddSetTopBox(WorksheetAddSetTopBox worksheetAddSetTopBox) throws Exception;
	//borrow worksheet
	public WorksheetBorrow getWorksheetBorrowById(Long id);
	public void updateWorksheetBorrow(WorksheetBorrow worksheetBorrow) throws Exception;
	//move worksheet
	public WorksheetMove getWorksheetMoveById(Long id);
	public void updateWorksheetMove(WorksheetMove worksheetMove) throws Exception;
	//move point worksheet
	public WorksheetMovePoint getWorksheetMovePointById(Long id);
	public void updateWorksheetMovePoint(WorksheetMovePoint worksheetMovePoint) throws Exception;
	//reduce point worksheet
	public WorksheetReducePoint getWorksheetReducePointById(Long id);
	public void updateWorksheetReducePoint(WorksheetReducePoint worksheetReducePoint) throws Exception;
	//repair connection worksheet
	public WorksheetRepairConnection getWorksheetRepairConnectionById(Long id);
	public void updateWorksheetRepairConnection(WorksheetRepairConnection worksheetRepairConnection) throws Exception;
	
	public void deleteProductItemWithWorksheetIdTypeO(Long worksheetId) throws Exception;
	public void deleteProductItemWithWorksheetIdTypeN(Long worksheetId) throws Exception;
	public void deleteProductItemWithWorksheetIdTypeR(Long worksheetId) throws Exception;
	public void deleteProductItemWithWorksheetIdTypeA(Long worksheetId) throws Exception;
	public void deleteProductItemWithWorksheetIdTypeB(Long worksheetId) throws Exception;
	
	//history repair
	public void saveHistoryRepair(HistoryRepair historyRepair) throws Exception;
	
	public List<Worksheet> getCAList() throws Exception ;
	
	public void updateStatusCA(Long id) throws Exception ;
	public List<Worksheet> getDataWorksheetForReport(String reportrange, String jobType, String worksheetStatus,
			String zone, String split);
	public List<Worksheet> getDataWorksheetForReport(String reportrange, String jobType, String worksheetStatus,
			String zone, String split, String technician);
	
	public WorksheetAnalyzeProblems getWorksheetAnalyzeProblemsById(Long id);
	public void updateWorksheetAnalyzeProblems(WorksheetAnalyzeProblems worksheetAnalyzeProblems) throws Exception;
	
}
