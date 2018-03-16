package com.hdw.mccable.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="worksheet")
public class Worksheet {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name="serviceApplicationId", nullable=false)
	private ServiceApplication serviceApplication;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "workSheet", cascade={CascadeType.ALL})
	private List<HistoryTechnicianGroupWork> historyTechnicianGroupWorks;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "workSheet", cascade={CascadeType.ALL})
	List<ProductItem> productItems;
	
	// Begin Type WorkSheet
	
	@Column(name = "workSheetType")
	private String workSheetType; // AddPoint, Connect, ...
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "workSheet", cascade={CascadeType.ALL})
	private WorksheetAddPoint worksheetAddPoint; // เสริมจุดบริการ
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "workSheet", cascade={CascadeType.ALL})
	private WorksheetConnect worksheetConnect; // ซ่อมสัญญาณ
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "workSheet", cascade={CascadeType.ALL})
	private WorksheetAddSetTopBox worksheetAddSetTopBox; // ขอเพิ่มอุปกรณ์รับสัญญาณเคเบิลทีวี
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "workSheet", cascade={CascadeType.ALL})
	private WorksheetBorrow worksheetBorrow; // แจ้งยืมอุปกรณ์รับสัญญาณเคเบิลทีวี
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "workSheet", cascade={CascadeType.ALL})
	private WorksheetCut worksheetCut; // ตัดสาย
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "workSheet", cascade={CascadeType.ALL})
	private WorksheetMove worksheetMove; // ย้ายสาย
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "workSheet", cascade={CascadeType.ALL})
	private WorksheetMovePoint worksheetMovePoint; // ย้ายจุด
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "workSheet", cascade={CascadeType.ALL})
	private WorksheetReducePoint worksheetReducePoint; // ลดจุด
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "workSheet", cascade={CascadeType.ALL})
	private WorksheetRepairConnection worksheetRepairConnection; // ซ่อมสัญญาณ
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "workSheet", cascade={CascadeType.ALL})
	private WorksheetSetup worksheetSetup; // ติดตั้ง
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "workSheet", cascade={CascadeType.ALL})
	private WorksheetTune worksheetTune; // การจูนทีวี
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "workSheet", cascade={CascadeType.ALL})
	private WorksheetAnalyzeProblems worksheetAnalyzeProblems; // วิเคราะห์ปัญหา
	
	// End Type WorkSheet
	
	//sub worksheet
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "workSheet", cascade={CascadeType.ALL})
	List<SubWorksheet> subWorksheets;
	
	
	//payment
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "workSheet", cascade={CascadeType.ALL})
	private Invoice invoice;
	
	@Column(name = "status", length=1)
	private String status;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "remarkSuccess")
	private String remarkSuccess;
	
	@Column(name = "workSheetCode")
	private String workSheetCode;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createDate", nullable=false)
	private Date createDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatedDate")
	private Date updatedDate;
	
	@Column(name = "createdBy", nullable=false, length=150)
	private String createdBy;
	
	@Column(name = "updatedBy", length=150)
	private String updatedBy;
	
	@Column(name = "isDeleted")
	private boolean isDeleted;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dateOrderBill", nullable=true)
	private Date dateOrderBill;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "workSheet", cascade={CascadeType.ALL})
	List<HistoryRepair> historyRepairList;
	
	@Column(name = "availableDateTime")
	private String availableDateTime; // วันเวลาที่ลูกค้าสะดวก
	
	public String getAvailableDateTime() {
		return availableDateTime;
	}

	public void setAvailableDateTime(String availableDateTime) {
		this.availableDateTime = availableDateTime;
	}

	@Column(name = "jobDetails")
	private String jobDetails; // รายละเอียดงาน
	
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
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ServiceApplication getServiceApplication() {
		return serviceApplication;
	}

	public void setServiceApplication(ServiceApplication serviceApplication) {
		this.serviceApplication = serviceApplication;
	}

	public List<HistoryTechnicianGroupWork> getHistoryTechnicianGroupWorks() {
		return historyTechnicianGroupWorks;
	}

	public void setHistoryTechnicianGroupWorks(List<HistoryTechnicianGroupWork> historyTechnicianGroupWorks) {
		this.historyTechnicianGroupWorks = historyTechnicianGroupWorks;
	}

	public List<ProductItem> getProductItems() {
		return productItems;
	}

	public void setProductItems(List<ProductItem> productItems) {
		this.productItems = productItems;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getWorkSheetType() {
		return workSheetType;
	}

	public void setWorkSheetType(String workSheetType) {
		this.workSheetType = workSheetType;
	}

	public WorksheetAddPoint getWorksheetAddPoint() {
		return worksheetAddPoint;
	}

	public void setWorksheetAddPoint(WorksheetAddPoint worksheetAddPoint) {
		this.worksheetAddPoint = worksheetAddPoint;
	}

	public WorksheetConnect getWorksheetConnect() {
		return worksheetConnect;
	}

	public void setWorksheetConnect(WorksheetConnect worksheetConnect) {
		this.worksheetConnect = worksheetConnect;
	}

	public WorksheetAddSetTopBox getWorksheetAddSetTopBox() {
		return worksheetAddSetTopBox;
	}

	public void setWorksheetAddSetTopBox(WorksheetAddSetTopBox worksheetAddSetTopBox) {
		this.worksheetAddSetTopBox = worksheetAddSetTopBox;
	}

	public WorksheetBorrow getWorksheetBorrow() {
		return worksheetBorrow;
	}

	public void setWorksheetBorrow(WorksheetBorrow worksheetBorrow) {
		this.worksheetBorrow = worksheetBorrow;
	}

	public WorksheetCut getWorksheetCut() {
		return worksheetCut;
	}

	public void setWorksheetCut(WorksheetCut worksheetCut) {
		this.worksheetCut = worksheetCut;
	}

	public WorksheetMove getWorksheetMove() {
		return worksheetMove;
	}

	public void setWorksheetMove(WorksheetMove worksheetMove) {
		this.worksheetMove = worksheetMove;
	}

	public WorksheetMovePoint getWorksheetMovePoint() {
		return worksheetMovePoint;
	}

	public void setWorksheetMovePoint(WorksheetMovePoint worksheetMovePoint) {
		this.worksheetMovePoint = worksheetMovePoint;
	}

	public WorksheetReducePoint getWorksheetReducePoint() {
		return worksheetReducePoint;
	}

	public void setWorksheetReducePoint(WorksheetReducePoint worksheetReducePoint) {
		this.worksheetReducePoint = worksheetReducePoint;
	}

	public WorksheetRepairConnection getWorksheetRepairConnection() {
		return worksheetRepairConnection;
	}

	public void setWorksheetRepairConnection(WorksheetRepairConnection worksheetRepairConnection) {
		this.worksheetRepairConnection = worksheetRepairConnection;
	}

	public WorksheetSetup getWorksheetSetup() {
		return worksheetSetup;
	}

	public void setWorksheetSetup(WorksheetSetup worksheetSetup) {
		this.worksheetSetup = worksheetSetup;
	}

	public WorksheetTune getWorksheetTune() {
		return worksheetTune;
	}

	public void setWorksheetTune(WorksheetTune worksheetTune) {
		this.worksheetTune = worksheetTune;
	}

	public List<SubWorksheet> getSubWorksheets() {
		return subWorksheets;
	}

	public void setSubWorksheets(List<SubWorksheet> subWorksheets) {
		this.subWorksheets = subWorksheets;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Date getDateOrderBill() {
		return dateOrderBill;
	}

	public void setDateOrderBill(Date dateOrderBill) {
		this.dateOrderBill = dateOrderBill;
	}

	public List<HistoryRepair> getHistoryRepairList() {
		return historyRepairList;
	}

	public void setHistoryRepairList(List<HistoryRepair> historyRepairList) {
		this.historyRepairList = historyRepairList;
	}

	public WorksheetAnalyzeProblems getWorksheetAnalyzeProblems() {
		return worksheetAnalyzeProblems;
	}

	public void setWorksheetAnalyzeProblems(WorksheetAnalyzeProblems worksheetAnalyzeProblems) {
		this.worksheetAnalyzeProblems = worksheetAnalyzeProblems;
	}
	
}
