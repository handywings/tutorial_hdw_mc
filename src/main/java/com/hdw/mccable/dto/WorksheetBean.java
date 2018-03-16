package com.hdw.mccable.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public abstract class WorksheetBean extends DSTPUtilityBean{
	@Autowired
    MessageSource messageSource;
	private Long idWorksheetParent;
	private ServiceApplicationBean serviceApplication;
	private List<ProductItemBean> productItemList;
	private List<JobScheduleBean> jobscheduleList;
	private StatusBean status;
	private boolean isDelete;
	private String remark;
	private String remarkNotSuccess;
	private String remarkSuccess;
	private String workSheetCode;
	private List<HistoryTechnicianGroupWorkBean> historyTechnicianGroupWorkBeans = new ArrayList<HistoryTechnicianGroupWorkBean>();
	private int historyTechnicianGroupWorkBeanSize;
	private List<ProductItemWorksheetBean> productItemWorksheetBeanList = new ArrayList<ProductItemWorksheetBean>();
	private String workSheetType;
	private String workSheetTypeText;
	private String currentDateAssignText;
	
	private InvoiceDocumentBean invoiceDocumentBean;
	
	//worksheet แยกตามประเภท
	//cable
	private AddPointWorksheetBean addPointWorksheetBean;
	private AddSetTopBoxWorksheetBean addSetTopBoxWorksheetBean;
	private BorrowWorksheetBean borrowWorksheetBean;
	private ConnectWorksheetBean connectWorksheetBean;
	private CutWorksheetBean cutWorksheetBean;
	private MovePointWorksheetBean movePointWorksheetBean;
	private MoveWorksheetBean moveWorksheetBean;
	private ReducePointWorksheetBean reducePointWorksheetBean;
	private RepairConnectionWorksheetBean repairConnectionWorksheetBean;
	private SetupWorksheetBean setupWorksheetBean;
	private TuneWorksheetBean tuneWorksheetBean;
	private AnalyzeProblemsWorksheetBean analyzeProblemsWorksheetBean;
	//internet
	
	private String jobDetails; // รายละเอียดงาน
	
	//subworksheet
	List<SubWorksheetBean> subWorksheetBeanList;
	
	private String assignDateTh;
	private String dateOrderBillTh;
	private String dateStartNewBill;
	
	private String availableDateTime; // วันเวลาที่ลูกค้าสะดวก
	
	private String startDate;
	private String endDate;
	
	public void loadWorksheetTypeText(){
		String type = getWorkSheetType();
		String typeText = "";
		if (type.equals(
				messageSource.getMessage("worksheet.type.cable.addpoint", null, LocaleContextHolder.getLocale()))) {
			typeText = messageSource.getMessage("worksheet.type.cable.addpoint.text", null, LocaleContextHolder.getLocale());
			
		} else if (type.equals(
				messageSource.getMessage("worksheet.type.cable.connect", null, LocaleContextHolder.getLocale()))) {
			typeText = messageSource.getMessage("worksheet.type.cable.connect.text", null, LocaleContextHolder.getLocale());
			
		} else if (type.equals(
				messageSource.getMessage("worksheet.type.cable.setup", null, LocaleContextHolder.getLocale()))) {
			typeText = messageSource.getMessage("worksheet.type.cable.setup.text", null, LocaleContextHolder.getLocale());
			
		} else if (type
				.equals(messageSource.getMessage("worksheet.type.cable.tune", null, LocaleContextHolder.getLocale()))) {
			typeText = messageSource.getMessage("worksheet.type.cable.tune.text", null, LocaleContextHolder.getLocale());
			
		} else if (type.equals(
				messageSource.getMessage("worksheet.type.cable.repair", null, LocaleContextHolder.getLocale()))) {
			typeText = messageSource.getMessage("worksheet.type.cable.repair.text", null, LocaleContextHolder.getLocale());
			
		} else if (type.equals(
				messageSource.getMessage("worksheet.type.cable.addsettop", null, LocaleContextHolder.getLocale()))) {
			typeText = messageSource.getMessage("worksheet.type.cable.addsettop.text", null, LocaleContextHolder.getLocale());
			
		} else if (type.equals(
				messageSource.getMessage("worksheet.type.cable.movepoint", null, LocaleContextHolder.getLocale()))) {
			typeText = messageSource.getMessage("worksheet.type.cable.movepoint.text", null, LocaleContextHolder.getLocale());
			
		} else if (type.equals(
				messageSource.getMessage("worksheet.type.cable.reducepoint", null, LocaleContextHolder.getLocale()))) {
			typeText = messageSource.getMessage("worksheet.type.cable.reducepoint.text", null, LocaleContextHolder.getLocale());
			
		} else if (type
				.equals(messageSource.getMessage("worksheet.type.cable.cut", null, LocaleContextHolder.getLocale()))) {
			typeText = messageSource.getMessage("worksheet.type.cable.cut.text", null, LocaleContextHolder.getLocale());
			
		} else if (type
				.equals(messageSource.getMessage("worksheet.type.cable.move", null, LocaleContextHolder.getLocale()))) {
			typeText = messageSource.getMessage("worksheet.type.cable.move.text", null, LocaleContextHolder.getLocale());
			
		} else if (type.equals(
				messageSource.getMessage("worksheet.type.cable.borrow", null, LocaleContextHolder.getLocale()))) {
			typeText = messageSource.getMessage("worksheet.type.cable.borrow.text", null, LocaleContextHolder.getLocale());
		} else if (type.equals(
				messageSource.getMessage("worksheet.type.internet.analyzeproblems", null, LocaleContextHolder.getLocale()))) {
			typeText = messageSource.getMessage("worksheet.type.internet.analyzeproblems.text", null, LocaleContextHolder.getLocale());
		}
		
		setWorkSheetTypeText(typeText);
	}
	
	public String getJobDetails() {
		return jobDetails;
	}

	public void setJobDetails(String jobDetails) {
		this.jobDetails = jobDetails;
	}

	public String getRemarkSuccess() {
		return remarkSuccess;
	}

	public void setRemarkSuccess(String remarkSuccess) {
		this.remarkSuccess = remarkSuccess;
	}
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getAvailableDateTime() {
		return availableDateTime;
	}

	public void setAvailableDateTime(String availableDateTime) {
		this.availableDateTime = availableDateTime;
	}

	public ServiceApplicationBean getServiceApplication() {
		return serviceApplication;
	}
	public void setServiceApplication(ServiceApplicationBean serviceApplication) {
		this.serviceApplication = serviceApplication;
	}
	public List<ProductItemBean> getProductItemList() {
		return productItemList;
	}
	public void setProductItemList(List<ProductItemBean> productItemList) {
		this.productItemList = productItemList;
	}
	public List<JobScheduleBean> getJobscheduleList() {
		return jobscheduleList;
	}
	public void setJobscheduleList(List<JobScheduleBean> jobscheduleList) {
		this.jobscheduleList = jobscheduleList;
	}
	public StatusBean getStatus() {
		return status;
	}
	public void setStatus(StatusBean status) {
		this.status = status;
	}
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getWorkSheetCode() {
		return workSheetCode;
	}
	public void setWorkSheetCode(String workSheetCode) {
		this.workSheetCode = workSheetCode;
	}
	
	public List<HistoryTechnicianGroupWorkBean> getHistoryTechnicianGroupWorkBeans() {
		return historyTechnicianGroupWorkBeans;
	}

	public void setHistoryTechnicianGroupWorkBeans(List<HistoryTechnicianGroupWorkBean> historyTechnicianGroupWorkBeans) {
		this.historyTechnicianGroupWorkBeans = historyTechnicianGroupWorkBeans;
	}

	public String getRemarkNotSuccess() {
		return remarkNotSuccess;
	}
	public void setRemarkNotSuccess(String remarkNotSuccess) {
		this.remarkNotSuccess = remarkNotSuccess;
	}
	public List<ProductItemWorksheetBean> getProductItemWorksheetBeanList() {
		return productItemWorksheetBeanList;
	}
	public void setProductItemWorksheetBeanList(List<ProductItemWorksheetBean> productItemWorksheetBeanList) {
		this.productItemWorksheetBeanList = productItemWorksheetBeanList;
	}
	public String getWorkSheetType() {
		return workSheetType;
	}
	public void setWorkSheetType(String workSheetType) {
		this.workSheetType = workSheetType;
	}
	public String getWorkSheetTypeText() {
		return workSheetTypeText;
	}
	public void setWorkSheetTypeText(String workSheetTypeText) {
		this.workSheetTypeText = workSheetTypeText;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public Long getIdWorksheetParent() {
		return idWorksheetParent;
	}

	public void setIdWorksheetParent(Long idWorksheetParent) {
		this.idWorksheetParent = idWorksheetParent;
	}

	public AddPointWorksheetBean getAddPointWorksheetBean() {
		return addPointWorksheetBean;
	}

	public void setAddPointWorksheetBean(AddPointWorksheetBean addPointWorksheetBean) {
		this.addPointWorksheetBean = addPointWorksheetBean;
	}

	public AddSetTopBoxWorksheetBean getAddSetTopBoxWorksheetBean() {
		return addSetTopBoxWorksheetBean;
	}

	public void setAddSetTopBoxWorksheetBean(AddSetTopBoxWorksheetBean addSetTopBoxWorksheetBean) {
		this.addSetTopBoxWorksheetBean = addSetTopBoxWorksheetBean;
	}

	public BorrowWorksheetBean getBorrowWorksheetBean() {
		return borrowWorksheetBean;
	}

	public void setBorrowWorksheetBean(BorrowWorksheetBean borrowWorksheetBean) {
		this.borrowWorksheetBean = borrowWorksheetBean;
	}

	public ConnectWorksheetBean getConnectWorksheetBean() {
		return connectWorksheetBean;
	}

	public void setConnectWorksheetBean(ConnectWorksheetBean connectWorksheetBean) {
		this.connectWorksheetBean = connectWorksheetBean;
	}

	public CutWorksheetBean getCutWorksheetBean() {
		return cutWorksheetBean;
	}

	public void setCutWorksheetBean(CutWorksheetBean cutWorksheetBean) {
		this.cutWorksheetBean = cutWorksheetBean;
	}

	public MovePointWorksheetBean getMovePointWorksheetBean() {
		return movePointWorksheetBean;
	}

	public void setMovePointWorksheetBean(MovePointWorksheetBean movePointWorksheetBean) {
		this.movePointWorksheetBean = movePointWorksheetBean;
	}

	public MoveWorksheetBean getMoveWorksheetBean() {
		return moveWorksheetBean;
	}

	public void setMoveWorksheetBean(MoveWorksheetBean moveWorksheetBean) {
		this.moveWorksheetBean = moveWorksheetBean;
	}

	public ReducePointWorksheetBean getReducePointWorksheetBean() {
		return reducePointWorksheetBean;
	}

	public void setReducePointWorksheetBean(ReducePointWorksheetBean reducePointWorksheetBean) {
		this.reducePointWorksheetBean = reducePointWorksheetBean;
	}

	public RepairConnectionWorksheetBean getRepairConnectionWorksheetBean() {
		return repairConnectionWorksheetBean;
	}

	public void setRepairConnectionWorksheetBean(RepairConnectionWorksheetBean repairConnectionWorksheetBean) {
		this.repairConnectionWorksheetBean = repairConnectionWorksheetBean;
	}

	public SetupWorksheetBean getSetupWorksheetBean() {
		return setupWorksheetBean;
	}

	public void setSetupWorksheetBean(SetupWorksheetBean setupWorksheetBean) {
		this.setupWorksheetBean = setupWorksheetBean;
	}

	public TuneWorksheetBean getTuneWorksheetBean() {
		return tuneWorksheetBean;
	}

	public void setTuneWorksheetBean(TuneWorksheetBean tuneWorksheetBean) {
		this.tuneWorksheetBean = tuneWorksheetBean;
	}
	public void popSizeHistoryTechnicianGroup(){
		setHistoryTechnicianGroupWorkBeanSize(historyTechnicianGroupWorkBeans.size());
	}
	public int getHistoryTechnicianGroupWorkBeanSize() {
		return historyTechnicianGroupWorkBeanSize;
	}
	public void setHistoryTechnicianGroupWorkBeanSize(int historyTechnicianGroupWorkBeanSize) {
		this.historyTechnicianGroupWorkBeanSize = historyTechnicianGroupWorkBeanSize;
	}

	public List<SubWorksheetBean> getSubWorksheetBeanList() {
		return subWorksheetBeanList;
	}

	public void setSubWorksheetBeanList(List<SubWorksheetBean> subWorksheetBeanList) {
		this.subWorksheetBeanList = subWorksheetBeanList;
	}

	public String getCurrentDateAssignText() {
		return currentDateAssignText;
	}

	public void setCurrentDateAssignText(String currentDateAssignText) {
		this.currentDateAssignText = currentDateAssignText;
	}

	public String getAssignDateTh() {
		return assignDateTh;
	}

	public void setAssignDateTh(String assignDateTh) {
		this.assignDateTh = assignDateTh;
	}

	public String getDateOrderBillTh() {
		return dateOrderBillTh;
	}

	public void setDateOrderBillTh(String dateOrderBillTh) {
		this.dateOrderBillTh = dateOrderBillTh;
	}

	public InvoiceDocumentBean getInvoiceDocumentBean() {
		return invoiceDocumentBean;
	}

	public void setInvoiceDocumentBean(InvoiceDocumentBean invoiceDocumentBean) {
		this.invoiceDocumentBean = invoiceDocumentBean;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public String getDateStartNewBill() {
		return dateStartNewBill;
	}

	public void setDateStartNewBill(String dateStartNewBill) {
		this.dateStartNewBill = dateStartNewBill;
	}

	public AnalyzeProblemsWorksheetBean getAnalyzeProblemsWorksheetBean() {
		return analyzeProblemsWorksheetBean;
	}

	public void setAnalyzeProblemsWorksheetBean(AnalyzeProblemsWorksheetBean analyzeProblemsWorksheetBean) {
		this.analyzeProblemsWorksheetBean = analyzeProblemsWorksheetBean;
	}
	
}
