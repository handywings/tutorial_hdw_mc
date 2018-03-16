package com.hdw.mccable.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="address")
public class Address {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;
	
	@Column(name = "detail", columnDefinition = "TEXT")
	private String detail;
	
	// �Ţ���
	@Column(name = "no")
	private String no; 
	
	// ������
	@Column(name = "section")
	private String section; 
	
	// �Ҥ��
	@Column(name = "building")
	private String building; 
		
	// �Ţ�����ͧ
	@Column(name = "room")
	private String room; 
	
	// ��鹷��
	@Column(name = "floor")
	private String floor; 
	
	// �����ҹ
	@Column(name = "village")
	private String village; 
	
	// ��͡-���
	@Column(name = "alley")
	private String alley; 
	
	// ���
	@Column(name = "road")
	private String road; 
	
	// �Ӻ�-�ǧ
	@Column(name = "subdistrict")
	private String subdistrict; 
	
	// �����-ࢵ
	@Column(name = "district")
	private String district; 
	
	// �ѧ��Ѵ
	@Column(name = "province")
	private String province; 
	
	// ������ɳ��� 
	@Column(name = "postcode")
	private String postcode; 
	
	// �����
	@Column(name = "country")
	private String country; 
	
	// �еԨٴ
	@Column(name = "latitude")
	private String latitude; 
	
	// �ͧ�Ԩٴ
	@Column(name = "longitude")
	private String longitude; 
	
	// �������������
	@Column(name = "addressType",length=3)
	private String addressType; 
	
	@Column(name = "imagePath")
	private String imagePath;
	
	@Column(name = "nearbyPlaces")
	private String nearbyPlaces;
	
	@Column(name = "overrideAddressId", nullable=true)
	private Long overrideAddressId;
	
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
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn (name="companyId", nullable=true)
	private Company company;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn (name="stockId", nullable=true)
	private Stock stock;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name="zoneId", nullable=true)
	private Zone zone;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name="zoneIdOld", nullable=true)
	private Zone zoneOld; // โซนเก่า
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="serviceApplicationId", nullable=true)
	private ServiceApplication serviceApplication;
	
	@Column(name = "isDeleted")
	private boolean isDeleted;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="customerId", nullable=true)
	private Customer customer;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="provinceId", nullable=true)
	private Province provinceModel;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="amphurId", nullable=true)
	private Amphur amphur;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="districtId", nullable=true)
	private District districtModel;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="worksheetMoveId", nullable=true)
	private WorksheetMove worksheetMove;
	
	@Column(name = "overrideAddressType",length=3)
	private String overrideAddressType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getAlley() {
		return alley;
	}

	public void setAlley(String alley) {
		this.alley = alley;
	}

	public String getRoad() {
		return road;
	}

	public void setRoad(String road) {
		this.road = road;
	}

	public String getSubdistrict() {
		return subdistrict;
	}

	public void setSubdistrict(String subdistrict) {
		this.subdistrict = subdistrict;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getNearbyPlaces() {
		return nearbyPlaces;
	}

	public void setNearbyPlaces(String nearbyPlaces) {
		this.nearbyPlaces = nearbyPlaces;
	}

	public Long getOverrideAddressId() {
		return overrideAddressId;
	}

	public void setOverrideAddressId(Long overrideAddressId) {
		this.overrideAddressId = overrideAddressId;
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

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

	public ServiceApplication getServiceApplication() {
		return serviceApplication;
	}

	public void setServiceApplication(ServiceApplication serviceApplication) {
		this.serviceApplication = serviceApplication;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Province getProvinceModel() {
		return provinceModel;
	}

	public void setProvinceModel(Province provinceModel) {
		this.provinceModel = provinceModel;
	}

	public Amphur getAmphur() {
		return amphur;
	}

	public void setAmphur(Amphur amphur) {
		this.amphur = amphur;
	}

	public District getDistrictModel() {
		return districtModel;
	}

	public void setDistrictModel(District districtModel) {
		this.districtModel = districtModel;
	}

	public String getOverrideAddressType() {
		return overrideAddressType;
	}

	public void setOverrideAddressType(String overrideAddressType) {
		this.overrideAddressType = overrideAddressType;
	}

	public WorksheetMove getWorksheetMove() {
		return worksheetMove;
	}

	public void setWorksheetMove(WorksheetMove worksheetMove) {
		this.worksheetMove = worksheetMove;
	}

	public Zone getZoneOld() {
		return zoneOld;
	}

	public void setZoneOld(Zone zoneOld) {
		this.zoneOld = zoneOld;
	} 
}
