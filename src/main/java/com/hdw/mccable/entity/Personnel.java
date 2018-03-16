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
@Table(name="personnel")
public class Personnel {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;
	
	@Column(name = "personnelCode", nullable=false, length=20)
	private String personnelCode;
	
	@Column(name = "firstName", nullable=false, length=200)
	private String firstName; 
	
	@Column(name = "lastName", nullable=false, length=200)
	private String lastName;
	
	@Column(name = "nickName", nullable=true, length=200)
	private String nickName;
	
	@Column(name = "sex", nullable=false, length=6)
	private String sex; //Male or Female
	
	@Column(name = "prefix", nullable=false, length=120)
	private String prefix;
	
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="companyId", nullable=false)
	private Company company;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="positionId", nullable=false)
	private Position position;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="permissionGroupId", nullable=false)
	private PermissionGroup permissionGroup;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "personnel", cascade = CascadeType.ALL)
	private Contact contact;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "personnel")
	private TechnicianGroup technicianGroup;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="technicianGroupId", nullable=true)
	private TechnicianGroup technicianGroupSub;
	
	@Column(name = "isDeleted")
	private boolean isDeleted;
	
	//first user insert to system
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "personnel", cascade={CascadeType.ALL})
	List<EquipmentProductItem> equipmentProductItems;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "personnel", cascade = CascadeType.ALL)
	private HistoryUpdateStatus historyUpdateStatus;
	
//	@OneToOne(fetch = FetchType.LAZY, mappedBy = "personnel", cascade = CascadeType.ALL)
//	private RequisitionDocument requisitionDocument;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "requisitionDocument", cascade={CascadeType.ALL})
	List<RequisitionItem> requisitionItems;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name="historyTechnicianGroupWorkId", nullable=true)
	private HistoryTechnicianGroupWork historyTechnicianGroupWork;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "personnel", cascade = CascadeType.ALL)
	private Authentication authentication;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "personnel", cascade={CascadeType.ALL})
	List<Receipt> receipts;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "personnel", cascade={CascadeType.ALL})
	List<InvoiceHistoryPrint> invoiceHistoryPrints;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "personnel", cascade={CascadeType.ALL})
	List<ReceiptHistoryPrint> receiptHistoryPrints;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "personnel", cascade={CascadeType.ALL})
	List<Invoice> invoices;
	
	@Column(name = "isCashier",columnDefinition = "boolean default false", nullable = false)  //true = เป็นพนักงานเก็บเงิน, false = ไม่ใช่พนักเก็บเงิน
	private boolean isCashier;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPersonnelCode() {
		return personnelCode;
	}

	public void setPersonnelCode(String personnelCode) {
		this.personnelCode = personnelCode;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public PermissionGroup getPermissionGroup() {
		return permissionGroup;
	}

	public void setPermissionGroup(PermissionGroup permissionGroup) {
		this.permissionGroup = permissionGroup;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public TechnicianGroup getTechnicianGroup() {
		return technicianGroup;
	}

	public void setTechnicianGroup(TechnicianGroup technicianGroup) {
		this.technicianGroup = technicianGroup;
	}

	public TechnicianGroup getTechnicianGroupSub() {
		return technicianGroupSub;
	}

	public void setTechnicianGroupSub(TechnicianGroup technicianGroupSub) {
		this.technicianGroupSub = technicianGroupSub;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public List<EquipmentProductItem> getEquipmentProductItems() {
		return equipmentProductItems;
	}

	public void setEquipmentProductItems(List<EquipmentProductItem> equipmentProductItems) {
		this.equipmentProductItems = equipmentProductItems;
	}

	public HistoryUpdateStatus getHistoryUpdateStatus() {
		return historyUpdateStatus;
	}

	public void setHistoryUpdateStatus(HistoryUpdateStatus historyUpdateStatus) {
		this.historyUpdateStatus = historyUpdateStatus;
	}

	public HistoryTechnicianGroupWork getHistoryTechnicianGroupWork() {
		return historyTechnicianGroupWork;
	}

	public void setHistoryTechnicianGroupWork(HistoryTechnicianGroupWork historyTechnicianGroupWork) {
		this.historyTechnicianGroupWork = historyTechnicianGroupWork;
	}

	public List<RequisitionItem> getRequisitionItems() {
		return requisitionItems;
	}

	public void setRequisitionItems(List<RequisitionItem> requisitionItems) {
		this.requisitionItems = requisitionItems;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Authentication getAuthentication() {
		return authentication;
	}

	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}

	public List<Receipt> getReceipts() {
		return receipts;
	}

	public void setReceipts(List<Receipt> receipts) {
		this.receipts = receipts;
	}

	public List<InvoiceHistoryPrint> getInvoiceHistoryPrints() {
		return invoiceHistoryPrints;
	}

	public void setInvoiceHistoryPrints(List<InvoiceHistoryPrint> invoiceHistoryPrints) {
		this.invoiceHistoryPrints = invoiceHistoryPrints;
	}

	public List<ReceiptHistoryPrint> getReceiptHistoryPrints() {
		return receiptHistoryPrints;
	}

	public void setReceiptHistoryPrints(List<ReceiptHistoryPrint> receiptHistoryPrints) {
		this.receiptHistoryPrints = receiptHistoryPrints;
	}

	public List<Invoice> getInvoices() {
		return invoices;
	}
	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}

	public boolean isCashier() {
		return isCashier;
	}

	public void setCashier(boolean isCashier) {
		this.isCashier = isCashier;
	}
}
