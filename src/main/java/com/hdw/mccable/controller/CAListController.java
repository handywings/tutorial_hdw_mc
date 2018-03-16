package com.hdw.mccable.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hdw.mccable.dto.AddressBean;
import com.hdw.mccable.dto.AlertBean;
import com.hdw.mccable.dto.AmphurBean;
import com.hdw.mccable.dto.CareerBean;
import com.hdw.mccable.dto.ContactBean;
import com.hdw.mccable.dto.CustomerBean;
import com.hdw.mccable.dto.CustomerFeatureBean;
import com.hdw.mccable.dto.CustomerTypeBean;
import com.hdw.mccable.dto.CutWorksheetBean;
import com.hdw.mccable.dto.DistrictBean;
import com.hdw.mccable.dto.EquipmentProductBean;
import com.hdw.mccable.dto.EquipmentProductItemBean;
import com.hdw.mccable.dto.HistoryTechnicianGroupWorkBean;
import com.hdw.mccable.dto.InternetProductBean;
import com.hdw.mccable.dto.InternetProductBeanItem;
import com.hdw.mccable.dto.JsonResponse;
import com.hdw.mccable.dto.PersonnelBean;
import com.hdw.mccable.dto.ProductItemBean;
import com.hdw.mccable.dto.ProductItemWorksheetBean;
import com.hdw.mccable.dto.ProvinceBean;
import com.hdw.mccable.dto.ServiceApplicationBean;
import com.hdw.mccable.dto.ServicePackageBean;
import com.hdw.mccable.dto.ServicePackageTypeBean;
import com.hdw.mccable.dto.ServiceProductBean;
import com.hdw.mccable.dto.StatusBean;
import com.hdw.mccable.dto.SubWorksheetBean;
import com.hdw.mccable.dto.TechnicianGroupBean;
import com.hdw.mccable.dto.UnitBean;
import com.hdw.mccable.dto.WorksheetBean;
import com.hdw.mccable.dto.WorksheetSearchBean;
import com.hdw.mccable.dto.ZoneBean;
import com.hdw.mccable.entity.Address;
import com.hdw.mccable.entity.EquipmentProductItem;
import com.hdw.mccable.entity.HistoryTechnicianGroupWork;
import com.hdw.mccable.entity.InternetProductItem;
import com.hdw.mccable.entity.Personnel;
import com.hdw.mccable.entity.ProductItem;
import com.hdw.mccable.entity.ProductItemWorksheet;
import com.hdw.mccable.entity.SubWorksheet;
import com.hdw.mccable.entity.TechnicianGroup;
import com.hdw.mccable.entity.Worksheet;
import com.hdw.mccable.service.WorkSheetService;
import com.hdw.mccable.utils.AlertUtil;

@Controller
@RequestMapping("/calist")
public class CAListController extends BaseController {
	final static Logger logger = Logger.getLogger(WorkSheetListController.class);
	public static final String CONTROLLER_NAME = "calist/";

	@Autowired(required = true)
	@Qualifier(value = "workSheetService")
	private WorkSheetService workSheetService;
	
	public static final String TYPE_EQUIMENT = "E";
	 public static final String TYPE_INTERNET_USER = "I";
	 public static final String TYPE_SERVICE = "S";
	 public static final String WAIT_FOR_PAY = "H"; // H = สถานะแรกคือ "รอจ่ายงาน"
	 public static final String CORPORATE = "C";
	 public static final String INDIVIDUAL = "I";
	 public static final String FILE_TYPE_IMAGE = "I"; //I = รูป	
	 public static final String FILE_TYPE_HOUSE_REGISTRATION = "H"; // H = สำเนาทะเบียนบ้าน
	 public static final String FILE_TYPE_IDENTITY = "P"; // P = สำเนาบัตรประจำตัวประชาชน
	 public static final String FILE_TYPE_OTHER = "O"; // O เอกสารอื่นๆ
		
	// initial service
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView init(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : init][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		// get current session
		HttpSession session = request.getSession();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			try {				
				List<Worksheet> worksheets = workSheetService.getCAList();
				List<WorksheetBean> worksheetBeans = new ArrayList<WorksheetBean>();
				for (Worksheet worksheet : worksheets) {
					WorksheetBean worksheetBean = new CutWorksheetBean();
					worksheetBean.setIdWorksheetParent(worksheet.getId());
					worksheetBean.setWorkSheetCode(worksheet.getWorkSheetCode());
					StatusBean statusBean = new StatusBean();
					statusBean.setStringValue(worksheet.getStatus());
					worksheetBean.setStatus(statusBean);
					SimpleDateFormat formatDataTh = new SimpleDateFormat(
							messageSource.getMessage("date.format.type1", null, LocaleContextHolder.getLocale()),
							new Locale("TH", "th"));
					worksheetBean.setCreateDateTh(
							null == worksheet.getCreateDate() ? "" : formatDataTh.format(worksheet.getCreateDate()));
					
					//worksheet type
					worksheetBean.setWorkSheetType(worksheet.getWorkSheetType());
					worksheetBean.setMessageSource(messageSource);
					worksheetBean.loadWorksheetTypeText();
					worksheetBean.setRemark(worksheet.getRemark());
					//service application
					ServiceApplicationBean serviceApplicationBean = new ServiceApplicationBean();
					serviceApplicationBean.setId(worksheet.getServiceApplication().getId());
					serviceApplicationBean.setServiceApplicationNo(worksheet.getServiceApplication().getServiceApplicationNo());
					SimpleDateFormat formatDataThSeriveApp = new SimpleDateFormat(
							messageSource.getMessage("date.format.type2", null, LocaleContextHolder.getLocale()),
							new Locale("TH", "th"));
					serviceApplicationBean.setCreateDateTh(
							null == worksheet.getServiceApplication().getCreateDate() ? "" : formatDataThSeriveApp.format(worksheet.getServiceApplication().getCreateDate()));
					StatusBean serviceAppStatus = new StatusBean();
					serviceAppStatus.setStringValue(worksheet.getServiceApplication().getStatus());
					serviceApplicationBean.setStatus(serviceAppStatus);
					serviceApplicationBean.setMonthlyServiceFee(worksheet.getServiceApplication().getMonthlyServiceFee());
					
					List<AddressBean> addresses = new ArrayList<AddressBean>();
					for(Address address : worksheet.getServiceApplication().getAddresses()){
						if(address.getAddressType().equals(messageSource.getMessage("address.type.address.setup", null, LocaleContextHolder.getLocale()))){
							AddressBean addressBean = new AddressBean();
							addressBean.setId(address.getId());
							addressBean.setMessageSource(messageSource);
							addressBean.setId(address.getId());
							addressBean.setDetail(address.getDetail());
							addressBean.setNo(address.getNo());
							addressBean.setAlley(address.getAlley());
							addressBean.setRoad(address.getRoad());
							addressBean.setRoom(address.getRoom());
							addressBean.setFloor(address.getFloor());
							addressBean.setBuilding(address.getBuilding());
							addressBean.setSection(address.getSection());
							addressBean.setPostcode(address.getPostcode());
							addressBean.setAddressType(address.getAddressType());
							addressBean.setVillage(address.getVillage());
							addressBean.setNearbyPlaces(address.getNearbyPlaces());
							addressBean.setOverrideAddressId(address.getOverrideAddressId());

							// user bean
							// province
							if (address.getProvinceModel() != null) {
								ProvinceBean provinceBean = new ProvinceBean();
								provinceBean.setId(address.getProvinceModel().getId());
								provinceBean.setPROVINCE_NAME(address.getProvinceModel().getPROVINCE_NAME());
								addressBean.setProvinceBean(provinceBean);
							}

							if (address.getAmphur() != null) {
								// amphur
								AmphurBean amphurBean = new AmphurBean();
								amphurBean.setId(address.getAmphur().getId());
								amphurBean.setAMPHUR_NAME(address.getAmphur().getAMPHUR_NAME());
								amphurBean.setPOSTCODE(address.getAmphur().getPOSTCODE());
								addressBean.setAmphurBean(amphurBean);
							}

							if (address.getDistrictModel() != null) {
								// district
								DistrictBean districtBean = new DistrictBean();
								districtBean.setId(address.getDistrictModel().getId());
								districtBean.setDISTRICT_NAME(address.getDistrictModel().getDISTRICT_NAME());
								addressBean.setDistrictBean(districtBean);
							}
							
							ZoneBean zoneBean = new ZoneBean();
							if(address.getZone() != null){
								zoneBean.setId(address.getZone().getId());
								zoneBean.setZoneName(address.getZone().getZoneName());
								zoneBean.setZoneDetail(address.getZone().getZoneDetail());
							}
							addressBean.setZoneBean(zoneBean);
							addressBean.collectAddress();
							addresses.add(addressBean);
						}
					}
					serviceApplicationBean.setAddressList(addresses);
					
					//service package
					ServicePackageBean servicePackageBean = new ServicePackageBean();
					servicePackageBean.setId(worksheet.getServiceApplication().getServicePackage().getId());
					servicePackageBean.setPackageName(worksheet.getServiceApplication().getServicePackage().getPackageName());
					servicePackageBean.setPackageCode(worksheet.getServiceApplication().getServicePackage().getPackageCode());
					ServicePackageTypeBean servicePackageTypeBean = new ServicePackageTypeBean();
					servicePackageTypeBean.setId(worksheet.getServiceApplication().getServicePackage().getServicePackageType().getId());
					servicePackageTypeBean.setPackageTypeName(worksheet.getServiceApplication().getServicePackage().getServicePackageType().getPackageTypeName());
					servicePackageBean.setServiceType(servicePackageTypeBean);
					serviceApplicationBean.setServicepackage(servicePackageBean);
					
					//customer
					CustomerBean customerBean = new CustomerBean();
					customerBean.setId(worksheet.getServiceApplication().getCustomer().getId());
					customerBean.setFirstName(worksheet.getServiceApplication().getCustomer().getFirstName());
					customerBean.setLastName(worksheet.getServiceApplication().getCustomer().getLastName());
					customerBean.setCustCode(worksheet.getServiceApplication().getCustomer().getCustCode());
					customerBean.setIdentityNumber(worksheet.getServiceApplication().getCustomer().getIdentityNumber());
					CareerBean careerBean = new CareerBean();
					careerBean.setId(worksheet.getServiceApplication().getCustomer().getCareer().getId());
					careerBean.setCareerName(worksheet.getServiceApplication().getCustomer().getCareer().getCareerName());
					customerBean.setCareerBean(careerBean);
					CustomerFeatureBean customerFeatureBean = new CustomerFeatureBean();
					customerFeatureBean.setId(worksheet.getServiceApplication().getCustomer().getCustomerFeature().getId());
					customerFeatureBean.setCustomerFeatureName(worksheet.getServiceApplication().getCustomer().getCustomerFeature().getCustomerFeatureName());
					customerBean.setCustomerFeatureBean(customerFeatureBean);

					// address
					List<AddressBean> addressBeans = new ArrayList<AddressBean>();
					for (Address address : worksheet.getServiceApplication().getCustomer().getAddresses()) {
						AddressBean addressBean = new AddressBean();
						addressBean.setMessageSource(messageSource);
						addressBean.setId(address.getId());
						addressBean.setDetail(address.getDetail());
						addressBean.setNo(address.getNo());
						addressBean.setAlley(address.getAlley());
						addressBean.setRoad(address.getRoad());
						addressBean.setRoom(address.getRoom());
						addressBean.setFloor(address.getFloor());
						addressBean.setBuilding(address.getBuilding());
						addressBean.setSection(address.getSection());
						addressBean.setPostcode(address.getPostcode());
						addressBean.setAddressType(address.getAddressType());
						addressBean.setVillage(address.getVillage());
						addressBean.setNearbyPlaces(address.getNearbyPlaces());
						addressBean.setOverrideAddressId(address.getOverrideAddressId());

						// user bean
						// province
						if (address.getProvinceModel() != null) {
							ProvinceBean provinceBean = new ProvinceBean();
							provinceBean.setId(address.getProvinceModel().getId());
							provinceBean.setPROVINCE_NAME(address.getProvinceModel().getPROVINCE_NAME());
							addressBean.setProvinceBean(provinceBean);
						}

						if (address.getAmphur() != null) {
							// amphur
							AmphurBean amphurBean = new AmphurBean();
							amphurBean.setId(address.getAmphur().getId());
							amphurBean.setAMPHUR_NAME(address.getAmphur().getAMPHUR_NAME());
							amphurBean.setPOSTCODE(address.getAmphur().getPOSTCODE());
							addressBean.setAmphurBean(amphurBean);
						}

						if (address.getDistrictModel() != null) {
							// district
							DistrictBean districtBean = new DistrictBean();
							districtBean.setId(address.getDistrictModel().getId());
							districtBean.setDISTRICT_NAME(address.getDistrictModel().getDISTRICT_NAME());
							addressBean.setDistrictBean(districtBean);
						}
						
						ServiceApplicationBean serviceApp = new ServiceApplicationBean();
						serviceApp.setId(address.getServiceApplication().getId());
						addressBean.setServiceApplicationBean(serviceApp);
						addressBean.collectAddress();
						addressBeans.add(addressBean);
					}
					customerBean.setAddressList(addressBeans);

					//customer type text
					CustomerTypeBean customerTypeBean = new CustomerTypeBean();
					customerTypeBean.setMessageSource(messageSource);
					customerTypeBean.setValue(worksheet.getServiceApplication().getCustomer().getCustType());
					if(customerTypeBean.getValue().equals(CORPORATE)){
						customerTypeBean.corPorate();
					}else if(customerTypeBean.getValue().equals(INDIVIDUAL)){
						customerTypeBean.inDividual();
					}
					customerBean.setCustomerType(customerTypeBean);
					
					// contact
					ContactBean contactBean = new ContactBean();
					contactBean.setId(worksheet.getServiceApplication().getCustomer().getContact().getId());
					contactBean.setMobile(worksheet.getServiceApplication().getCustomer().getContact().getMobile());
					contactBean.setEmail(worksheet.getServiceApplication().getCustomer().getContact().getEmail());
					contactBean.setFax(worksheet.getServiceApplication().getCustomer().getContact().getFax());
					customerBean.setContact(contactBean);
					
					serviceApplicationBean.setCustomer(customerBean);
					
					// set productItem
					List<ProductItemBean> productItemBeans = new ArrayList<ProductItemBean>();
					for (ProductItem productItem : worksheet.getServiceApplication().getProductItems()) {
//						Worksheet worksheet = productItem.getWorkSheet();
						Boolean checkWorksheet = true;
//						if(null == worksheet){
//							checkWorksheet = true;
//						}else{
//							String worksheetSetup = worksheet.getWorkSheetType();
//							if("C_S".equals(worksheetSetup)){
//								checkWorksheet = true;
//							}
//						}
						if(checkWorksheet){
						ProductItemBean productItemBean = new ProductItemBean();
						productItemBean.setId(productItem.getId());
						productItemBean.setType(productItem.getProductType());
						productItemBean.setQuantity(productItem.getQuantity());
						productItemBean.setFree(productItem.isFree());
						productItemBean.setLend(productItem.isLend());
						productItemBean.setAmount(productItem.getAmount());
						productItemBean.setPrice(productItem.getPrice());

						// case equipment product
						if (productItem.getProductType().equals(TYPE_EQUIMENT)) {
							ProductAddController productAddController = new ProductAddController();
							productAddController.setMessageSource(messageSource);
							EquipmentProductBean equipmentProductBean = productAddController
									.populateEntityToDto(productItem.getEquipmentProduct());
							productItemBean.setProduct(equipmentProductBean);
							productItemBean.getProduct().setTypeEquipment();

							// case internet product
						} else if (productItem.getProductType().equals(TYPE_INTERNET_USER)) {
							ProductOrderInternetProductController interProduct = new ProductOrderInternetProductController();
							interProduct.setMessageSource(messageSource);
							InternetProductBean internetProductBean = interProduct
									.populateEntityToDto(productItem.getInternetProduct());
							productItemBean.setProduct(internetProductBean);
							productItemBean.getProduct().setTypeInternet();

							// case service product
						} else if (productItem.getProductType().equals(TYPE_SERVICE)) {
							ProductOrderServiceProductController serviceProduct = new ProductOrderServiceProductController();
							serviceProduct.setMessageSource(messageSource);
							ServiceProductBean serviceProductBean = serviceProduct
									.populateEntityToDto(productItem.getServiceProduct());
							productItemBean.setProduct(serviceProductBean);
							productItemBean.getProduct().setTypeService();

						}
						
						// set data product_item_worksheet
						List<ProductItemWorksheetBean> productItemWorksheetBeanList = new ArrayList<ProductItemWorksheetBean>();
						List<ProductItemWorksheet> productItemWorksheets = productItem.getProductItemWorksheets();
						if(null != productItemWorksheets && productItemWorksheets.size() > 0){
							for(ProductItemWorksheet productItemWorksheet:productItemWorksheets){
								ProductItemWorksheetBean productItemWorksheetBean = new ProductItemWorksheetBean();
								productItemWorksheetBean.setId(productItemWorksheet.getId());
								productItemWorksheetBean.setAmount(productItemWorksheet.getAmount());
								productItemWorksheetBean.setDeposit(productItemWorksheet.getDeposit());
								productItemWorksheetBean.setFree(productItemWorksheet.isFree());
								productItemWorksheetBean.setLend(productItemWorksheet.isLend());
								productItemWorksheetBean.setDeposit(productItemWorksheet.getDeposit());
								productItemWorksheetBean.setQuantity(productItemWorksheet.getQuantity());
								productItemWorksheetBean.setReturnEquipment(productItemWorksheet.isReturnEquipment());
								productItemWorksheetBean.setLendStatus(productItemWorksheet.getLendStatus());
								if(TYPE_EQUIMENT.equals(productItemWorksheet.getProductType())){
									EquipmentProductItem equipmentProductItem = productItemWorksheet.getEquipmentProductItem();
									if(null != equipmentProductItem){
										EquipmentProductItemBean equipmentProductItemBean = new EquipmentProductItemBean();
										equipmentProductItemBean.setId(equipmentProductItem.getId());
										equipmentProductItemBean.setSerialNo(equipmentProductItem.getSerialNo());
										equipmentProductItemBean.setBalance(equipmentProductItem.getBalance());
										equipmentProductItemBean.setReservations(equipmentProductItem.getReservations());
										equipmentProductItemBean.setNumberImport(equipmentProductItem.getNumberImport());
										
										productItemWorksheetBean.setEquipmentProductItemBean(equipmentProductItemBean);
									}
								}else if(TYPE_INTERNET_USER.equals(productItemWorksheet.getProductType())){
									InternetProductItem internetProductItem = productItemWorksheet.getInternetProductItem();
									if(null != internetProductItem){
										InternetProductBeanItem internetProductBeanItem = new InternetProductBeanItem();
										internetProductBeanItem.setId(internetProductItem.getId());
										internetProductBeanItem.setUserName(internetProductItem.getUserName());
										internetProductBeanItem.setPassword(internetProductItem.getPassword());
										
										productItemWorksheetBean.setInternetProductBeanItem(internetProductBeanItem);
									}
								}
								
								productItemWorksheetBeanList.add(productItemWorksheetBean);
							}
						}
						productItemBean.setProductItemWorksheetBeanList(productItemWorksheetBeanList );
						
						productItemBeans.add(productItemBean);

						}
						//check worksheet setup and null
					}
					serviceApplicationBean.setProductitemList(productItemBeans);
					
					
					worksheetBean.setServiceApplication(serviceApplicationBean);
					
					//history
					List<HistoryTechnicianGroupWorkBean> historyTechnicianGroupWorkBeans = new ArrayList<HistoryTechnicianGroupWorkBean>();
					for(HistoryTechnicianGroupWork historyTechnicianGroupWork : worksheet.getHistoryTechnicianGroupWorks()){
						HistoryTechnicianGroupWorkBean historyTechnicianGroupWorkBean = new HistoryTechnicianGroupWorkBean();
						historyTechnicianGroupWorkBean.setId(historyTechnicianGroupWork.getId());
						
						TechnicianGroup technicianGroup = historyTechnicianGroupWork.getTechnicianGroup();
						if(null != technicianGroup){
							TechnicianGroupBean technicianGroupBean = new TechnicianGroupBean();
							technicianGroupBean.setId(technicianGroup.getId());
							PersonnelBean personnelBean = new PersonnelBean();
							Personnel personnel = technicianGroup.getPersonnel();
							personnelBean.setId(personnel.getId());
							personnelBean.setFirstName(personnel.getFirstName());
							personnelBean.setLastName(personnel.getLastName());
							
							technicianGroupBean.setTechnicianGroupName(technicianGroup.getTechnicianGroupName());
							technicianGroupBean.setPersonnel(personnelBean);
							
							historyTechnicianGroupWorkBean.setTechnicainGroup(technicianGroupBean);
						}
						historyTechnicianGroupWorkBean.setRemarkNotSuccess(historyTechnicianGroupWork.getRemarkNotSuccess());
						historyTechnicianGroupWorkBean.setStatusHistory(historyTechnicianGroupWork.getStatusHistory());
						historyTechnicianGroupWorkBean.setAssingCurrentDate(historyTechnicianGroupWork.getAssignDate());
						
						//technician group
						TechnicianGroupBean technicainGroup = new TechnicianGroupBean();
						technicainGroup.setId(historyTechnicianGroupWork.getTechnicianGroup().getId());
						technicainGroup.setTechnicianGroupName(historyTechnicianGroupWork.getTechnicianGroup().getTechnicianGroupName());
						//lead technicain group
						PersonnelBean leaderPersonnel = new PersonnelBean();
						leaderPersonnel.setId(historyTechnicianGroupWork.getTechnicianGroup().getPersonnel().getId());
						leaderPersonnel.setFirstName(historyTechnicianGroupWork.getTechnicianGroup().getPersonnel().getFirstName());
						leaderPersonnel.setLastName(historyTechnicianGroupWork.getTechnicianGroup().getPersonnel().getLastName());
						technicainGroup.setPersonnel(leaderPersonnel);
						historyTechnicianGroupWorkBean.setTechnicainGroup(technicainGroup);
						
						historyTechnicianGroupWorkBeans.add(historyTechnicianGroupWorkBean);
					}
					worksheetBean.setHistoryTechnicianGroupWorkBeans(historyTechnicianGroupWorkBeans);
					worksheetBean.popSizeHistoryTechnicianGroup();
					
					//product item
					productItemBeans = new ArrayList<ProductItemBean>();
					for(ProductItem productItem : worksheet.getProductItems()){
						ProductItemBean productItemBean = new ProductItemBean();
						
						if(productItem.getProductType().equals("E") || productItem.getProductType().equals("I")){
							
							List<ProductItemWorksheetBean> productItemWorksheetBeanList = new ArrayList<ProductItemWorksheetBean>();
							for(ProductItemWorksheet productItemWorksheet : productItem.getProductItemWorksheets()){
								ProductItemWorksheetBean productItemWorksheetBean = new ProductItemWorksheetBean();
								productItemWorksheetBean.setId(productItemWorksheet.getId());
								productItemWorksheetBean.setQuantity(productItemWorksheet.getQuantity());
								productItemWorksheetBean.setPrice(productItemWorksheet.getPrice());
								productItemWorksheetBean.setFree(productItemWorksheet.isFree());
								productItemWorksheetBean.setLend(productItemWorksheet.isLend());
								productItemWorksheetBean.setAmount(productItemWorksheet.getAmount());
								productItemWorksheetBean.setProductTypeMatch(productItemWorksheet.getProductTypeMatch());
								productItemWorksheetBean.setDeposit(productItemWorksheet.getDeposit());
								if(productItem.getProductType().equals("E")){
									if(productItemWorksheet.getEquipmentProductItem() != null){
										
										EquipmentProductItemBean equipmentProductItemBean = new EquipmentProductItemBean();
										equipmentProductItemBean.setId(productItemWorksheet.getEquipmentProductItem().getId());
										equipmentProductItemBean.setSerialNo(productItemWorksheet.getEquipmentProductItem().getSerialNo());
										//equipment product 
										EquipmentProductBean equipmentProductBean = new EquipmentProductBean();
										equipmentProductBean.setId(productItemWorksheet.getEquipmentProductItem().getEquipmentProduct().getId());
										equipmentProductBean.setProductName(productItemWorksheet.getEquipmentProductItem().getEquipmentProduct().getProductName());
										equipmentProductBean.setProductCode(productItemWorksheet.getEquipmentProductItem().getEquipmentProduct().getProductCode());
										//set unit name
										UnitBean unitBean = new UnitBean();
										unitBean.setId(productItemWorksheet.getEquipmentProductItem().getEquipmentProduct().getUnit().getId());
										unitBean.setUnitName(productItemWorksheet.getEquipmentProductItem().getEquipmentProduct().getUnit().getUnitName());
										equipmentProductBean.setUnit(unitBean);
										equipmentProductItemBean.setEquipmentProductBean(equipmentProductBean);
										productItemWorksheetBean.setEquipmentProductItemBean(equipmentProductItemBean);
										productItemWorksheetBeanList.add(productItemWorksheetBean);
									}
									
									productItemBean.setProductCategoryName(productItem.getEquipmentProduct().getEquipmentProductCategory().getEquipmentProductCategoryName());
								}else if(productItem.getProductType().equals("I")){
									if(productItemWorksheet.getInternetProductItem() != null){
										
										InternetProductBeanItem internetProductBeanItem = new InternetProductBeanItem();
										internetProductBeanItem.setId(productItemWorksheet.getInternetProductItem().getId());
										internetProductBeanItem.setUserName(productItemWorksheet.getInternetProductItem().getUserName());
										internetProductBeanItem.setPassword(productItemWorksheet.getInternetProductItem().getPassword());
										//internet product
										InternetProductBean internetProductBean = new InternetProductBean();
										internetProductBean.setMessageSource(messageSource);
										internetProductBean.setId(productItemWorksheet.getInternetProductItem().getInternetProduct().getId());
										internetProductBean.setProductCode(productItemWorksheet.getInternetProductItem().getInternetProduct().getProductCode());
										internetProductBean.setProductName(productItemWorksheet.getInternetProductItem().getInternetProduct().getProductName());
										internetProductBean.unitTypeInternet();
										internetProductBeanItem.setInternetProductBean(internetProductBean);
										productItemWorksheetBean.setInternetProductBeanItem(internetProductBeanItem);
										productItemWorksheetBeanList.add(productItemWorksheetBean);
									}
									productItemBean.setProductCategoryName(productItem.getInternetProduct().getEquipmentProductCategory().getEquipmentProductCategoryName());
								}
							}
							productItemBean.setProductItemWorksheetBeanList(productItemWorksheetBeanList);
							
						}else if(productItem.getProductType().equals("S")){
							ProductOrderServiceProductController pServiceController = new ProductOrderServiceProductController();
							pServiceController.setMessageSource(messageSource);
							ServiceProductBean serviceProductBean = pServiceController.populateEntityToDto(productItem.getServiceProduct());
							productItemBean.setType(productItem.getProductType());
							productItemBean.setPrice(productItem.getPrice());
							productItemBean.setFree(productItem.isFree());
							productItemBean.setLend(productItem.isLend());
							productItemBean.setAmount(productItem.getAmount());
							productItemBean.setQuantity(productItem.getQuantity());
							productItemBean.setServiceProductBean(serviceProductBean);
							productItemBean.setProductCategoryName(productItem.getServiceProduct().getEquipmentProductCategory().getEquipmentProductCategoryName());
							productItemBean.setProductTypeMatch(productItem.getProductTypeMatch());
						}
						
						productItemBean.setType(productItem.getProductType());
						productItemBean.setQuantity(productItem.getQuantity());
						productItemBeans.add(productItemBean);
					}
					worksheetBean.setProductItemList(productItemBeans);
					
					//sub worksheet
					List<SubWorksheetBean> subWorksheetBeanList = new ArrayList<SubWorksheetBean>();
					for(SubWorksheet subWorksheet :  worksheet.getSubWorksheets()){
						SubWorksheetBean subWorksheetBean = new SubWorksheetBean();
						subWorksheetBean.setId(subWorksheet.getId());
						subWorksheetBean.setWorkSheetType(subWorksheet.getWorkSheetType());
						subWorksheetBean.setPrice(subWorksheet.getPrice());
						subWorksheetBean.setMessageSource(messageSource);
						subWorksheetBean.loadWorksheetTypeText();
						subWorksheetBeanList.add(subWorksheetBean);
					}
					worksheetBean.setSubWorksheetBeanList(subWorksheetBeanList);
					
					worksheetBeans.add(worksheetBean);
				}
				
				
				modelAndView.addObject("worksheets",worksheetBeans);
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);

		// remove session
		session.removeAttribute("alert");

		modelAndView.setViewName(CONTROLLER_NAME + INIT);
		return modelAndView;
	}
	
	// create search session
	public void generateSearchSession(WorksheetSearchBean worksheetSearchBean, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("calistSearchBean", worksheetSearchBean);
	}
	
	@RequestMapping(value="confirm/{id}", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public JsonResponse updateStatusCA(@PathVariable Long id , Model model, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			//create timestamp
			try{
				logger.info("[method : updateStatusCA][Type : Controller]");
				if(null != id){
					//update zoneBean
					workSheetService.updateStatusCA(id);
					jsonResponse.setError(false);
				}else{
					jsonResponse.setError(true);
				}

				
			}catch(Exception ex){
				ex.printStackTrace();
				jsonResponse.setMessage(ex.getMessage());
				jsonResponse.setError(true);
				}
		}else{
			jsonResponse.setError(true);
		}
		
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("ca.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	public void generateAlert(JsonResponse jsonResponse, HttpServletRequest request, String title, String detail){
		new AlertUtil().generateAlert(jsonResponse, request, title, detail);
	}
}
