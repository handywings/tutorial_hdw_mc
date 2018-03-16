package com.hdw.mccable.dto;

import java.util.List;

public class WorksheetUpdateSnapShotBean extends DSTPUtilityBean{
	
	private Long idWorksheetParent;
	private String type; //S = สำเร็จ , H = ไม่สำเร็จ
	private String remarkFail; //มีค่ากรณีไม่สำเร็จ
	private String remark; //มีค่ากรณีไม่สำเร็จ
	private String remarkSuccess; //หมายเหตุ / ข้อมูลเพิ่มเติม (ใบงานสำเร็จ)
	private List<PersonnelBean> personnelBeanList; //สำหรับเก็บ personnel ใหม่ ทำการลบ personnel assign ที่ผูกกับ history ล่าสุดก่อน
	private List<ProductItemBean> productItemBeanList; 
	//private List<ProductItemWorksheetBean> productItemWorksheetBeanList;
	private String typeWorksheet; //ดูตามไฟล์ message.properties
	
	private String jobDetails; // รายละเอียดงาน
	
	//worksheet แยกตามประเภท
	// cable
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
	// internet
	
	private List<SubWorksheetBean> subWorkSheetBeanList; //ใบงานย่อย
	
	private String availableDateTime; // วันเวลาที่ลูกค้าสะดวก
	
	public String getAvailableDateTime() {
		return availableDateTime;
	}
	public void setAvailableDateTime(String availableDateTime) {
		this.availableDateTime = availableDateTime;
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
	public Long getIdWorksheetParent() {
		return idWorksheetParent;
	}
	public void setIdWorksheetParent(Long idWorksheetParent) {
		this.idWorksheetParent = idWorksheetParent;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRemarkFail() {
		return remarkFail;
	}
	public void setRemarkFail(String remarkFail) {
		this.remarkFail = remarkFail;
	}
	public List<PersonnelBean> getPersonnelBeanList() {
		return personnelBeanList;
	}
	public void setPersonnelBeanList(List<PersonnelBean> personnelBeanList) {
		this.personnelBeanList = personnelBeanList;
	}
	public List<ProductItemBean> getProductItemBeanList() {
		return productItemBeanList;
	}
	public void setProductItemBeanList(List<ProductItemBean> productItemBeanList) {
		this.productItemBeanList = productItemBeanList;
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
	public String getTypeWorksheet() {
		return typeWorksheet;
	}
	public void setTypeWorksheet(String typeWorksheet) {
		this.typeWorksheet = typeWorksheet;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<SubWorksheetBean> getSubWorkSheetBeanList() {
		return subWorkSheetBeanList;
	}
	public void setSubWorkSheetBeanList(List<SubWorksheetBean> subWorkSheetBeanList) {
		this.subWorkSheetBeanList = subWorkSheetBeanList;
	}
	public AnalyzeProblemsWorksheetBean getAnalyzeProblemsWorksheetBean() {
		return analyzeProblemsWorksheetBean;
	}
	public void setAnalyzeProblemsWorksheetBean(AnalyzeProblemsWorksheetBean analyzeProblemsWorksheetBean) {
		this.analyzeProblemsWorksheetBean = analyzeProblemsWorksheetBean;
	}
	 
}
