package com.hdw.mccable.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hdw.mccable.dto.AlertBean;
import com.hdw.mccable.dto.CompanyBean;
import com.hdw.mccable.dto.EquipmentProductBean;
import com.hdw.mccable.dto.EquipmentProductCategoryBean;
import com.hdw.mccable.dto.EquipmentProductItemBean;
import com.hdw.mccable.dto.InternetProductBean;
import com.hdw.mccable.dto.InternetProductBeanItem;
import com.hdw.mccable.dto.JsonResponse;
import com.hdw.mccable.dto.RequisitionDocumentBean;
import com.hdw.mccable.dto.RequisitionItemBean;
import com.hdw.mccable.dto.StatusBean;
import com.hdw.mccable.dto.StockBean;
import com.hdw.mccable.dto.TechnicianGroupBean;
import com.hdw.mccable.dto.UnitBean;
import com.hdw.mccable.entity.EquipmentProduct;
import com.hdw.mccable.entity.EquipmentProductCategory;
import com.hdw.mccable.entity.EquipmentProductItem;
import com.hdw.mccable.entity.InternetProduct;
import com.hdw.mccable.entity.InternetProductItem;
import com.hdw.mccable.entity.Personnel;
import com.hdw.mccable.entity.RequisitionDocument;
import com.hdw.mccable.entity.RequisitionItem;
import com.hdw.mccable.entity.Stock;
import com.hdw.mccable.entity.TechnicianGroup;
import com.hdw.mccable.service.EquipmentProductCategoryService;
import com.hdw.mccable.service.EquipmentProductItemService;
import com.hdw.mccable.service.EquipmentProductService;
import com.hdw.mccable.service.PersonnelService;
import com.hdw.mccable.service.ProductService;
import com.hdw.mccable.service.RequisitionDocumentService;
import com.hdw.mccable.service.RequisitionItemService;
import com.hdw.mccable.service.StockService;
import com.hdw.mccable.service.TechnicianGroupService;
import com.hdw.mccable.utils.AlertUtil;
import com.hdw.mccable.utils.ProductUtil;

@Controller
@RequestMapping("/requisition")
public class RequisitionController extends BaseController{
	
	final static Logger logger = Logger.getLogger(RequisitionController.class);
	public static final String CONTROLLER_NAME = "requisition/";
	public static final String STATUS_NORMAL = "N";
	
	//initial service
	@Autowired(required = true)
	@Qualifier(value = "technicianGroupService")
	private TechnicianGroupService technicianGroupService;
	
	@Autowired(required = true)
	@Qualifier(value = "equipmentProductCategoryService")
	private EquipmentProductCategoryService equipmentProductCategoryService;
	
	@Autowired(required = true)
	@Qualifier(value = "stockService")
	private StockService stockService;
	
	@Autowired(required = true)
	@Qualifier(value = "requisitionDocumentService")
	private RequisitionDocumentService requisitionDocumentService;
	
	@Autowired(required = true)
	@Qualifier(value = "personnelService")
	private PersonnelService personnelService;
	
	@Autowired(required = true)
	@Qualifier(value = "equipmentProductItemService")
	private EquipmentProductItemService equipmentProductItemService;
	
	@Autowired(required = true)
	@Qualifier(value = "equipmentProductService")
	private EquipmentProductService equipmentProductService;
	
	@Autowired(required = true)
	@Qualifier(value = "productService")
	private ProductService productService;
	
	@Autowired(required = true)
	@Qualifier(value = "requisitionItemService")
	private RequisitionItemService requisitionItemService;
	
	
	@Autowired
    MessageSource messageSource;
	
	//end initial service
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView init(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : init][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		EquipmentProductCategoryController epcController = new EquipmentProductCategoryController();
		ProductAddController productAddController = new ProductAddController();
		productAddController.setProductService(productService);
		productAddController.setMessageSource(messageSource);
		
		List<TechnicianGroupBean> technicianGroupBeans = new ArrayList<TechnicianGroupBean>();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission
		if(isPermission()){
			try{
				//call service
				List<TechnicianGroup> technicianGroups = technicianGroupService.findAll();
				if(null != technicianGroups && !technicianGroups.isEmpty()){
					TechnicianGroupController technicianGroupController = new TechnicianGroupController();
					for(TechnicianGroup technicianGroup:technicianGroups){
						technicianGroupBeans.add(technicianGroupController.populateEntityToDto(technicianGroup));
					}
				}
				// dropdown equipmentCategory for search only
				List<EquipmentProductCategoryBean> epcSearchBeans = new ArrayList<EquipmentProductCategoryBean>();
				List<EquipmentProductCategory> epcSearchs = equipmentProductCategoryService.findTypeEquipmentOnly();
				if(null != epcSearchs && !epcSearchs.isEmpty()){
					for (EquipmentProductCategory epcSearch : epcSearchs) {
						EquipmentProductCategoryBean epcSearchBean = new EquipmentProductCategoryBean();
						epcSearchBean = epcController.populateEntityToDto(epcSearch);
						epcSearchBeans.add(epcSearchBean);
					}
				}
				modelAndView.addObject("epcSearchBeans", epcSearchBeans);
				
				// load equipment product
				List<EquipmentProductBean> epbSearchs = productAddController.loadEquipmentProduct(new EquipmentProductBean());
				modelAndView.addObject("epbSearchs", epbSearchs);
				
				// dropdown stock
				List<StockBean> stockBeans = loadStockList();
				modelAndView.addObject("stockBeans", stockBeans);
				
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
		}else{
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		
		HttpSession session = request.getSession();
		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);
		modelAndView.addObject("technicianGroupBeans", technicianGroupBeans);
		
		//remove session
		session.removeAttribute("alert");
		
		modelAndView.setViewName(CONTROLLER_NAME+INIT);
		return modelAndView;
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value="save", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public JsonResponse save(@RequestBody final RequisitionDocumentBean requisitionDocumentBean, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			//create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try{
				logger.info("[method : save][Type : Controller]");
				if(null != requisitionDocumentBean){
					RequisitionDocument requisitionDocument = new RequisitionDocument();

					requisitionDocument.setWithdraw(requisitionDocumentBean.getWithdraw());
					requisitionDocument.setDetail(requisitionDocumentBean.getDetail());
					requisitionDocument.setStatus(STATUS_NORMAL);
					requisitionDocument.setRequisitionDocumentCode(requisitionDocumentService.genRequisitionDocumentCode());
					
					// set TechnicianGroupBean
					TechnicianGroupBean technicianGroupBean = requisitionDocumentBean.getTechnicianGroup();
					TechnicianGroup technicianGroup = new TechnicianGroup();
					if(null != technicianGroupBean){
						technicianGroup = technicianGroupService.getTechnicianGroupById(technicianGroupBean.getId());				
						requisitionDocument.setTechnicianGroup(technicianGroup);
					}
			
					Personnel personnel = getPersonnelLogin();
					if(null != personnel){
						requisitionDocument.setPersonnel(personnel);
					}
					requisitionDocument.setCreatedBy(getUserNameLogin());
					requisitionDocument.setCreateDate(CURRENT_TIMESTAMP);
					requisitionDocument.setUpdatedBy(getUserNameLogin());
					requisitionDocument.setUpdatedDate(CURRENT_TIMESTAMP);
					// set 
					Long requisitionDocumentId = requisitionDocumentService.save(requisitionDocument);
					
					// เตรียมข้อมูลสำหรับ insert item
					List<RequisitionItem> requisitionItems = new ArrayList<RequisitionItem>();
					List<RequisitionItemBean> requisitionItemBeans = requisitionDocumentBean.getRequisitionItemList();
					if(null != requisitionItemBeans && requisitionItemBeans.size() > 0){
						requisitionDocument = requisitionDocumentService.getRequisitionDocumentById(requisitionDocumentId);
						for(RequisitionItemBean itemBean:requisitionItemBeans){
							EquipmentProductItem equipmentProductItem = equipmentProductItemService.getEquipmentProductItemById(itemBean.getId());
							if(null != equipmentProductItem){
								RequisitionItem requisitionItem = new RequisitionItem();
								EquipmentProduct equipmentProduct = productService.findEquipmentProductById(equipmentProductItem.getEquipmentProduct().getId());
								requisitionItem.setEquipmentProduct(equipmentProduct);
								requisitionItem.setEquipmentProductItem(equipmentProductItem);
								requisitionItem.setQuantity(itemBean.getQuantity());
								requisitionItem.setRequisitionDocument(requisitionDocument);
								
								personnel = technicianGroup.getPersonnel();
								requisitionItem.setPersonnel(personnel);
								
								requisitionItem.setCreatedBy(getUserNameLogin());
								requisitionItem.setCreateDate(CURRENT_TIMESTAMP);
								requisitionItemService.save(requisitionItem);
								
								if("R".equals(requisitionDocumentBean.getWithdraw())){ // เบิกเพื่อไปสำรอง (ยังไม่ตัดสต็อก)
									if(!"".equals(equipmentProductItem.getSerialNo())){ // มี S/N
										equipmentProductItem.setBalance(0);
										equipmentProductItem.setSpare(1);
										equipmentProductItem.setStatus(STATUS_RESERVE);
									}else{ // ไม่มี S/N
										// อัพเดทจำนวน อุปกรณ์
										int balanceOld = equipmentProductItem.getBalance(); // จำนวน อุปกรณ์เก่า
										int balanceNew = balanceOld - itemBean.getQuantity();
										if(balanceNew < 0) balanceNew = 0;
										equipmentProductItem.setBalance(balanceNew);
										
										int spareItem = equipmentProductItem.getSpare();
										spareItem += itemBean.getQuantity();
										equipmentProductItem.setSpare(spareItem);
									}
									
								}else{ // (ตัดสต็อกทันที)
									// อัพเดทจำนวน อุปกรณ์
									
									if(!"".equals(equipmentProductItem.getSerialNo())){ // มี S/N
										equipmentProductItem.setBalance(0);
										equipmentProductItem.setStatus(STATUS_INACTIVE);
									}else{ // ไม่มี S/N
										// อัพเดทจำนวน อุปกรณ์
										int balanceOld = equipmentProductItem.getBalance(); // จำนวน อุปกรณ์เก่า
										int balanceNew = balanceOld - itemBean.getQuantity();
										if(balanceNew < 0) balanceNew = 0;
										equipmentProductItem.setBalance(balanceNew);
									}
							
								}
								equipmentProductItemService.update(equipmentProductItem);
								
								// อัพ product ตัวแม่
								if(null != equipmentProduct){
									ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
									pro.setEquipmentProductService(equipmentProductService);
									pro.setMessageSource(messageSource);
									pro.autoUpdateStatusEquipmentProduct(equipmentProduct);
								}
							}
						}
					}
					
				}

				
			}catch(Exception ex){
				ex.printStackTrace();
				jsonResponse.setMessage(ex.getMessage());
				jsonResponse.setError(true);
				}
		}else{
			jsonResponse.setError(true);
		}
		
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("requisitionDocument.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	// loadEquipmentProduct
	@RequestMapping(value = "loadEquipmentProductWithId/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonResponse loadEquipmentProductWithId(@PathVariable String id, HttpServletRequest request) {
		logger.info("[method : loadEquipmentProductWithId][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				EquipmentProduct equipmentProduct = productService.findEquipmentProductById(Long.valueOf(id));
				if (equipmentProduct != null) {
					EquipmentProductBean equipmentProductBean = new EquipmentProductBean();
					equipmentProductBean = populateEntityToDto(equipmentProduct);
					jsonResponse.setError(false);
					jsonResponse.setResult(equipmentProductBean);
				} else {
					// input text for message exception
					throw new Exception("");
				}

			} catch (Exception ex) {
				ex.printStackTrace();
				jsonResponse.setMessage(ex.getMessage());
				jsonResponse.setError(true);
			}
		} else {
			jsonResponse.setError(true);
		}
		return jsonResponse;
	}
	
	// loadInternet
	@RequestMapping(value = "loadInternetWithId/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonResponse loadInternetWithId(@PathVariable String id, HttpServletRequest request) {
		logger.info("[method : loadInternetWithId][Type : Controller]");
		logger.info("[method : loadInternetWithId][id : "+id+"]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				InternetProduct internetProduct = productService.findInternetProductById(Long.valueOf(id));
				if (internetProduct != null) {
					InternetProductBean internetProductBean = new InternetProductBean();
					internetProductBean = populateEntityToDtoStatusZero(internetProduct);
					jsonResponse.setError(false);
					jsonResponse.setResult(internetProductBean);
				} else {
					// input text for message exception
					throw new Exception("");
				}

			} catch (Exception ex) {
				ex.printStackTrace();
				jsonResponse.setMessage(ex.getMessage());
				jsonResponse.setError(true);
			}
		} else {
			jsonResponse.setError(true);
		}
		return jsonResponse;
	}
	
	private InternetProductBean populateEntityToDtoStatusZero(InternetProduct internetProduct) {
		InternetProductBean internetProductBean = new InternetProductBean();
		if(null != internetProduct){
			internetProductBean.setId(internetProduct.getId());
			internetProductBean.setProductName(internetProduct.getProductName());
			internetProductBean.setProductCode(internetProduct.getProductCode());
			internetProductBean.setType("I");
			List<InternetProductItem> internetProductItems = internetProduct.getInternetProductItems();
			if(null != internetProductItems && internetProductItems.size() > 0){
				List<InternetProductBeanItem> internetProductBeanItems = new ArrayList<InternetProductBeanItem>();
				for(InternetProductItem internetProductItem:internetProductItems){
					if("0".equals(internetProductItem.getStatus())){
						InternetProductBeanItem internetProductBeanItem = new InternetProductBeanItem();
						internetProductBeanItem.setId(internetProductItem.getId());
						internetProductBeanItem.setUserName(internetProductItem.getUserName());
						internetProductBeanItem.setPassword(internetProductItem.getPassword());
						
//						internetProductBeanItem.setInternetProductBean(internetProductBean);
						
						internetProductBeanItems.add(internetProductBeanItem);
					}
				}
				internetProductBean.setInternetProductBeanItems(internetProductBeanItems);
			}
		}
		return internetProductBean;
	}
	
//	private InternetProductBean populateEntityToDto(InternetProduct internetProduct) {
//		InternetProductBean internetProductBean = new InternetProductBean();
//		if(null != internetProduct){
//			internetProductBean.setId(internetProduct.getId());
//			internetProductBean.setProductName(internetProduct.getProductName());
//			internetProductBean.setProductCode(internetProduct.getProductCode());
//			List<InternetProductItem> internetProductItems = internetProduct.getInternetProductItems();
//			if(null != internetProductItems && internetProductItems.size() > 0){
//				List<InternetProductBeanItem> internetProductBeanItems = new ArrayList<InternetProductBeanItem>();
//				for(InternetProductItem internetProductItem:internetProductItems){
//					InternetProductBeanItem internetProductBeanItem = new InternetProductBeanItem();
//					internetProductBeanItem.setId(internetProductItem.getId());
//					internetProductBeanItem.setUserName(internetProductItem.getUserName());
//					internetProductBeanItem.setPassword(internetProductItem.getPassword());
//					
//					internetProductBeanItems.add(internetProductBeanItem);
//				}
//				internetProductBean.setInternetProductBeanItems(internetProductBeanItems);
//			}
//		}
//		return internetProductBean;
//	}

	// populate bean
	public EquipmentProductBean populateEntityToDto(EquipmentProduct ep) {
		int rest = 0; // เหลือ
		int usable = 0; // ใช้ได้
		int reserve = 0; // สำรอง
		int lend = 0; // ยืม
		int out_of_order = 0; // เสีย/ ชำรุด
		int repair = 0; // ซ่อม
		EquipmentProductBean equipmentProductBean = new EquipmentProductBean();

		// set equimemtProductBean
		equipmentProductBean.setId(ep.getId());
		equipmentProductBean.setProductCode(ep.getProductCode());
		equipmentProductBean.setProductName(ep.getProductName());
		equipmentProductBean.setSupplier(ep.getSupplier());
		equipmentProductBean.setIsminimum(ep.isMinimum());
		equipmentProductBean.setMinimumNumber(ep.getMinimumNumber());
		//equipmentProductBean.setCost(ep.getCost());
		//equipmentProductBean.setSalePrice(ep.getSalePrice());

		// set unit
		UnitBean unitBean = new UnitBean();
		unitBean.setId(ep.getUnit().getId());
		unitBean.setUnitName(ep.getUnit().getUnitName());
		equipmentProductBean.setUnit(unitBean);

		// stock
		StockBean stockBean = new StockBean();
		stockBean.setId(ep.getStock().getId());
		stockBean.setStockCode(ep.getStock().getStockCode());
		stockBean.setStockName(ep.getStock().getStockName());
		CompanyBean companyBean = new CompanyBean();
		CompanyController companyController = new CompanyController();
		companyBean = companyController.populateEntityToDto(ep.getStock().getCompany());
		stockBean.setCompany(companyBean);
		equipmentProductBean.setStock(stockBean);

		// equipmentProductCategoryBean
		EquipmentProductCategoryBean epc = new EquipmentProductCategoryBean();
		epc.setId(ep.getEquipmentProductCategory().getId());
		epc.setEquipmentProductCategoryName(ep.getEquipmentProductCategory().getEquipmentProductCategoryName());
		epc.setEquipmentProductCategoryCode(ep.getEquipmentProductCategory().getEquipmentProductCategoryCode());
		equipmentProductBean.setProductCategory(epc);

		// product item
		List<EquipmentProductItemBean> equipmentProductItemBeans = new ArrayList<EquipmentProductItemBean>();
		for (EquipmentProductItem equipmentProductItem : ep.getEquipmentProductItems()) {
			if(!equipmentProductItem.isDeleted() && equipmentProductItem.getStatus() == 1){
			EquipmentProductItemBean equipmentProductItemBean = new EquipmentProductItemBean();
			equipmentProductItemBean.setId(equipmentProductItem.getId());
			equipmentProductItemBean.setCost(equipmentProductItem.getCost());
			equipmentProductItemBean.setReferenceNo(equipmentProductItem.getReference());
			equipmentProductItemBean.setSerialNo(equipmentProductItem.getSerialNo());
			equipmentProductItemBean.setNumberImport(equipmentProductItem.getNumberImport());
			equipmentProductItemBean.setBalance(equipmentProductItem.getBalance());
			equipmentProductItemBean.setSalePrice(equipmentProductItem.getSalePrice());
			equipmentProductItemBean.setRepair(equipmentProductItem.isRepair());

			SimpleDateFormat formatDataTh = new SimpleDateFormat(
					messageSource.getMessage("date.format.type2", null, LocaleContextHolder.getLocale()),
					new Locale("TH", "th"));
			// guarantee date
			equipmentProductItemBean.setGuaranteeDate(null == equipmentProductItem.getGuaranteeDate() ? ""
					: formatDataTh.format(equipmentProductItem.getGuaranteeDate()));
			equipmentProductItemBean.setOrderDate(null == equipmentProductItem.getOrderDate() ? ""
					: formatDataTh.format(equipmentProductItem.getOrderDate()));
			
			SimpleDateFormat formatDateAndTimeTh = new SimpleDateFormat(
					messageSource.getMessage("date.format.type1", null, LocaleContextHolder.getLocale()),
					new Locale("TH", "th"));
			//import system datetime
			equipmentProductItemBean.setImportSystemDate(null == equipmentProductItem.getImportSystemDate() ? ""
					: formatDateAndTimeTh.format(equipmentProductItem.getImportSystemDate()));
			
			//status
			ProductUtil productUtil = new ProductUtil();
			productUtil.setMessageSource(messageSource);
			equipmentProductItemBean.setStatus(productUtil.getMessageStatusProduct(equipmentProductItem.getStatus()));
			
			// เช็ค เหลือ ที่ใช้ได้ สำรอง ยืม เสีย/ชำรุด และ ซ่อม	0=เสีย, 1=ใช้งานได้ปกติ, 2=ำลังซ่อม/ซ่อม, 3=บืม, 4=สำรอง
			// เหลือ = ที่ใช้งานได้ รวมที่อยู่กับช่าง คือรวม รายการสำรอง 

				switch (equipmentProductItem.getStatus()) {
				case 0:
					out_of_order++;
					break;
				case 1:
					rest++;
					usable++;
					break;
				case 2:
					repair++;
					break;
				case 3:
					lend++;
					break;
				case 4:
					reserve++;
					rest++;
					break;
				}
			
			// set parent equipment 
			EquipmentProductBean EquipmentProductParent = new EquipmentProductBean();
			EquipmentProductParent.setId(ep.getId());	
			equipmentProductItemBean.setEquipmentProductBean(EquipmentProductParent);
			
			// add to equipmentproductBean List
			equipmentProductItemBeans.add(equipmentProductItemBean);
			
			}
		}
		// set product item to product
		equipmentProductBean.setEquipmentProductItemBeans(equipmentProductItemBeans);
		
		equipmentProductBean.setRest(rest);
		equipmentProductBean.setUsable(usable);
		equipmentProductBean.setReserve(reserve);
		equipmentProductBean.setLend(lend);
		equipmentProductBean.setRepair(repair);
		equipmentProductBean.setOutOfOrder(out_of_order);

		return equipmentProductBean;
	}
	
	// load stock dropdown
	public List<StockBean> loadStockList() {
		List<StockBean> stockBeans = new ArrayList<StockBean>();
		List<Stock> stocks = stockService.findAllOrderCompany();
		for (Stock stock : stocks) {
			StockBean stockBean = new StockBean();
			stockBean.setId(stock.getId());
			stockBean.setStockName(stock.getStockName());
			stockBean.setStockCode(stock.getStockCode());
			stockBean.setStockDetail(stock.getStockDetail());

			CompanyBean companyBean = new CompanyBean();
			companyBean.setId(stock.getCompany().getId());
			companyBean.setCompanyName(stock.getCompany().getCompanyName());
			stockBean.setCompany(companyBean);
			stockBeans.add(stockBean);
		}

		return stockBeans;
	}
	
	public RequisitionDocumentBean populateEntityToDto(RequisitionDocument requisitionDocument) {
		RequisitionDocumentBean bean = new RequisitionDocumentBean();
		
		if(null != requisitionDocument){
			StatusBean statusBean = new StatusBean();
			String status = "";
			if("N".equals(requisitionDocument.getStatus())){
				status = messageSource.getMessage("requisition.status.normal", null,
						LocaleContextHolder.getLocale());
			}else{
				status = messageSource.getMessage("requisition.status.cancel", null,
						LocaleContextHolder.getLocale());
			}
			statusBean.setStringValue(status);
			bean.setStatus(statusBean);
			bean.setWithdraw(requisitionDocument.getWithdraw());
			bean.setDetail(requisitionDocument.getDetail());
			
			TechnicianGroup technicianGroup = requisitionDocument.getTechnicianGroup();
			if(null != technicianGroup){
				technicianGroup = technicianGroupService.getTechnicianGroupById(technicianGroup.getId());
				TechnicianGroupController tcgController = new TechnicianGroupController();
				TechnicianGroupBean technicianGroupBean = tcgController.populateEntityToDto(technicianGroup);
				bean.setTechnicianGroup(technicianGroupBean);
			}
			
			
		}
		
		return bean;
	}


	public void generateAlert(JsonResponse jsonResponse, HttpServletRequest request, String title, String detail){
		new AlertUtil().generateAlert(jsonResponse, request, title, detail);
	}
	
	
}
