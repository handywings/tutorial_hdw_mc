package com.hdw.mccable.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.hdw.mccable.Manager.JasperJrxmlComponent;
import com.hdw.mccable.Manager.JasperRender;
import com.hdw.mccable.Manager.ParamsEnum;
import com.hdw.mccable.Manager.PdfMergeUtils;
import com.hdw.mccable.dto.AddressBean;
import com.hdw.mccable.dto.AlertBean;
import com.hdw.mccable.dto.CompanyBean;
import com.hdw.mccable.dto.EquipmentProductBean;
import com.hdw.mccable.dto.EquipmentProductCategoryBean;
import com.hdw.mccable.dto.EquipmentProductItemBean;
import com.hdw.mccable.dto.JsonResponse;
import com.hdw.mccable.dto.PersonnelBean;
import com.hdw.mccable.dto.ReportInvoiceOrReceiptBean;
import com.hdw.mccable.dto.RequisitionDocumentBean;
import com.hdw.mccable.dto.RequisitionDocumentBeanReport;
import com.hdw.mccable.dto.RequisitionDocumentItemBeanReport;
import com.hdw.mccable.dto.RequisitionItemBean;
import com.hdw.mccable.dto.StatusBean;
import com.hdw.mccable.dto.StockBean;
import com.hdw.mccable.dto.TechnicianGroupBean;
import com.hdw.mccable.entity.Company;
import com.hdw.mccable.entity.EquipmentProduct;
import com.hdw.mccable.entity.EquipmentProductCategory;
import com.hdw.mccable.entity.EquipmentProductItem;
import com.hdw.mccable.entity.Invoice;
import com.hdw.mccable.entity.Personnel;
import com.hdw.mccable.entity.RequisitionDocument;
import com.hdw.mccable.entity.RequisitionItem;
import com.hdw.mccable.entity.Stock;
import com.hdw.mccable.entity.TechnicianGroup;
import com.hdw.mccable.entity.Worksheet;
import com.hdw.mccable.service.EquipmentProductCategoryService;
import com.hdw.mccable.service.EquipmentProductItemService;
import com.hdw.mccable.service.EquipmentProductService;
import com.hdw.mccable.service.ProductService;
import com.hdw.mccable.service.RequisitionDocumentService;
import com.hdw.mccable.service.RequisitionItemService;
import com.hdw.mccable.service.StockService;
import com.hdw.mccable.service.TechnicianGroupService;
import com.hdw.mccable.utils.AlertUtil;
import com.hdw.mccable.utils.NumberFormat;
import com.hdw.mccable.utils.Pagination;

@Controller
@RequestMapping("/requisitionlist")
public class RequisitionListController extends BaseController{
	
	final static Logger logger = Logger.getLogger(RequisitionListController.class);
	public static final String CONTROLLER_NAME = "requisitionlist/";
	public static final String CONTROLLER_NAME_URL = "requisitionlist";
	Gson g = new Gson();
	
	//initial service
	@Autowired(required = true)
	@Qualifier(value = "requisitionDocumentService")
	private RequisitionDocumentService requisitionDocumentService;
	
	@Autowired(required = true)
	@Qualifier(value = "equipmentProductItemService")
	private EquipmentProductItemService equipmentProductItemService;
	
	@Autowired(required = true)
	@Qualifier(value = "equipmentProductService")
	private EquipmentProductService equipmentProductService;
	
	@Autowired(required = true)
	@Qualifier(value = "requisitionItemService")
	private RequisitionItemService requisitionItemService;
	
	@Autowired(required = true)
	@Qualifier(value = "technicianGroupService")
	private TechnicianGroupService technicianGroupService;
	
	@Autowired(required = true)
	@Qualifier(value = "equipmentProductCategoryService")
	private EquipmentProductCategoryService equipmentProductCategoryService;
	
	@Autowired(required = true)
	@Qualifier(value = "productService")
	private ProductService productService;
	
	@Autowired(required = true)
	@Qualifier(value = "jasperJrxmlComponent")
	private JasperJrxmlComponent jasperJrxmlComponent;
	
	@Autowired(required = true)
	@Qualifier(value = "stockService")
	private StockService stockService;
	
	@Autowired
    MessageSource messageSource;
	
	//end initial service
	
	public static final String key_final = "";
	public static final String withdraw_final = "";
	public static final String startDate_final = "";
	public static final String endDate_final = "";
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView init(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : init][Type : Controller]");
		SimpleDateFormat formatDataTh = new SimpleDateFormat(messageSource.getMessage("date.format.type5", null, LocaleContextHolder.getLocale()), new Locale( "TH" , "th" ));
		SimpleDateFormat formatUS = new SimpleDateFormat(messageSource.getMessage("date.format.type4", null, LocaleContextHolder.getLocale()), Locale.US);
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = request.getSession();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission
		if(isPermission()){
			try{
				
				RequisitionDocumentBean requisitionDocumentBean = (RequisitionDocumentBean) session.getAttribute("requisitionDocumentBean");
				if(session.getAttribute("requisitionDocumentBean") != null){
					modelAndView.addObject("requisitionDocumentBean", requisitionDocumentBean);
				}else{
					modelAndView.addObject("requisitionDocumentBean", new RequisitionDocumentBean());
				}
				
				Pagination pagination = new Pagination();
				if(requisitionDocumentBean != null){
					String startDate = requisitionDocumentBean.getDaterange().split(" - ")[0];
					String endDate = requisitionDocumentBean.getDaterange().split(" - ")[1];
					
					if(null != startDate && !"".equals(startDate)){
						Date sdate = formatDataTh.parse(startDate);
						startDate = formatUS.format(sdate);
					}
					if(null != endDate && !"".equals(endDate)){
						Date edate = formatDataTh.parse(endDate);
						endDate = formatUS.format(edate);
					}
					
					pagination = createPagination(1, 10, CONTROLLER_NAME_URL,requisitionDocumentBean.getKey(),
							requisitionDocumentBean.getWithdraw(),startDate,endDate);
				}else{
					pagination = createPagination(1, 10, CONTROLLER_NAME_URL,key_final,withdraw_final,startDate_final,endDate_final);
				}
				
				//call service
				Pagination paginationNormal = compareStatus(pagination,"N");
				Pagination paginationCancel = compareStatus(pagination,"C");
				modelAndView.addObject("pagination",pagination);
				modelAndView.addObject("paginationNormal",paginationNormal);
				modelAndView.addObject("paginationCancel",paginationCancel);
				
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
		}else{
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		
		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);
		
		//remove session
		session.removeAttribute("alert");
		
		modelAndView.setViewName(CONTROLLER_NAME+INIT);
		return modelAndView;
	}
	
	@RequestMapping(value = "page/{id}/itemPerPage/{itemPerPage}", method = RequestMethod.GET)
	public ModelAndView pagination(@PathVariable int id, @PathVariable int itemPerPage, Model model,
			HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : pagination][Type : Controller]");
		logger.info("[method : pagination][itemPerPage : "+itemPerPage+"]");
		SimpleDateFormat formatDataTh = new SimpleDateFormat(messageSource.getMessage("date.format.type5", null, LocaleContextHolder.getLocale()), new Locale( "TH" , "th" ));
		SimpleDateFormat formatUS = new SimpleDateFormat(messageSource.getMessage("date.format.type4", null, LocaleContextHolder.getLocale()), Locale.US);
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = request.getSession();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			try {
				
				RequisitionDocumentBean requisitionDocumentBean = (RequisitionDocumentBean) session.getAttribute("requisitionDocumentBean");
				if(session.getAttribute("requisitionDocumentBean") != null){
					modelAndView.addObject("requisitionDocumentBean", requisitionDocumentBean);
				}else{
					modelAndView.addObject("requisitionDocumentBean", new RequisitionDocumentBean());
				}
				
				Pagination pagination = new Pagination();
				if(requisitionDocumentBean != null){
					String startDate = requisitionDocumentBean.getDaterange().split(" - ")[0];
					String endDate = requisitionDocumentBean.getDaterange().split(" - ")[1];
					
					if(null != startDate && !"".equals(startDate)){
						Date sdate = formatDataTh.parse(startDate);
						startDate = formatUS.format(sdate);
					}
					if(null != endDate && !"".equals(endDate)){
						Date edate = formatDataTh.parse(endDate);
						endDate = formatUS.format(edate);
					}
					
					pagination = createPagination(1, 10, CONTROLLER_NAME_URL,requisitionDocumentBean.getKey(),
							requisitionDocumentBean.getWithdraw(),startDate,endDate);
				}else{
					pagination = createPagination(id, itemPerPage, CONTROLLER_NAME_URL,key_final,withdraw_final,startDate_final,endDate_final);
				}
				
				//call service
				Pagination paginationNormal = compareStatus(pagination,"N");
				Pagination paginationCancel = compareStatus(pagination,"C");
				modelAndView.addObject("pagination",pagination);
				modelAndView.addObject("paginationNormal",paginationNormal);
				modelAndView.addObject("paginationCancel",paginationCancel);
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}

		modelAndView.setViewName(CONTROLLER_NAME + INIT);
		return modelAndView;
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView search(@ModelAttribute("personnelSearchBean")
	RequisitionDocumentBean requisitionDocumentBean, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		
		logger.info("[method : search][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			generateSearchSession(requisitionDocumentBean, request);
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		
		modelAndView.setViewName(REDIRECT+"/requisitionlist");
		return modelAndView;
	}
	
	public void generateSearchSession(RequisitionDocumentBean requisitionDocumentBean, HttpServletRequest request){
		HttpSession session = request.getSession();
		session.setAttribute("requisitionDocumentBean", requisitionDocumentBean);
	}
	
	@RequestMapping(value = "view/{id}", method = RequestMethod.GET)
	public ModelAndView view(@PathVariable int id, Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : view][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission
		if(isPermission()){
			try{
				//call service
				RequisitionDocument requi = requisitionDocumentService.getRequisitionDocumentById(Long.valueOf(id));
				RequisitionDocumentBean bean = populateEntityToDto(requi);
				modelAndView.addObject("requisitionDocumentBean", bean);
				
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
		
		//remove session
		session.removeAttribute("alert");
		
		modelAndView.setViewName(CONTROLLER_NAME+VIEW);
		return modelAndView;
	}
	
	@RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable int id, Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : edit][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission
		if(isPermission()){
			try{
				//call service
				RequisitionDocument requi = requisitionDocumentService.getRequisitionDocumentById(Long.valueOf(id));
				RequisitionDocumentBean bean = populateEntityToDto(requi);
				modelAndView.addObject("requisitionDocumentBean", bean);
				
				List<TechnicianGroupBean> technicianGroupBeans = new ArrayList<TechnicianGroupBean>();
				List<TechnicianGroup> technicianGroups = technicianGroupService.findAll();
				if(null != technicianGroups && !technicianGroups.isEmpty()){
					TechnicianGroupController technicianGroupController = new TechnicianGroupController();
					for(TechnicianGroup technicianGroup:technicianGroups){
						technicianGroupBeans.add(technicianGroupController.populateEntityToDto(technicianGroup));
					}
				}
				modelAndView.addObject("technicianGroupBeans", technicianGroupBeans);
				
				// dropdown equipmentCategory for search only
				EquipmentProductCategoryController epcController = new EquipmentProductCategoryController();
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
				ProductAddController productAddController = new ProductAddController();
				productAddController.setProductService(productService);
				productAddController.setMessageSource(messageSource);
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
		
		//remove session
		session.removeAttribute("alert");
		
		modelAndView.setViewName(CONTROLLER_NAME+EDIT);
		return modelAndView;
	}
	
	@SuppressWarnings("unchecked")
	Pagination compareStatus(Pagination pagination, String status){
		Pagination pagination_final = new Pagination();
		List<RequisitionDocumentBean> requisitionDocumentBean = new ArrayList<RequisitionDocumentBean>();
		if(null != pagination && null != pagination.getDataListBean()){
			for(RequisitionDocumentBean bean:(List<RequisitionDocumentBean>)pagination.getDataListBean()){
				if(status.equals(bean.getStatus().getStringValue())){
					requisitionDocumentBean.add(bean);
				}
			}
			pagination_final.setDataListBean(requisitionDocumentBean);
		}
		return pagination_final;
	}
	
	@SuppressWarnings("unchecked")
	Pagination createPagination(int currentPage, int itemPerPage, String controller, String key, String withdraw, String startDate, String endDate){
		SimpleDateFormat formatDataTh = new SimpleDateFormat(messageSource.getMessage("date.format.type1", null, LocaleContextHolder.getLocale()), new Locale( "TH" , "th" ));
		if(itemPerPage==0)itemPerPage=10;
		if(currentPage==0)currentPage=1;
		Pagination pagination = new Pagination();
		pagination.setTotalItem(requisitionDocumentService.getCountTotal());
		pagination.setCurrentPage(currentPage);
		pagination.setItemPerPage(itemPerPage);
		pagination.setUrl(controller);
		try {
			pagination = requisitionDocumentService.getByPageCriteria(pagination, key, withdraw, startDate, endDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//populate
		List<RequisitionDocumentBean> requisitionDocumentBean = new ArrayList<RequisitionDocumentBean>();
		for(RequisitionDocument req : (List<RequisitionDocument>) pagination.getDataList()){
			RequisitionDocumentBean reqBean = new RequisitionDocumentBean();
			reqBean.setId(req.getId());
			reqBean.setRequisitionDocumentCode(req.getRequisitionDocumentCode());
			
			StatusBean status = new StatusBean();
			status.setStringValue(req.getStatus());
			reqBean.setStatus(status);
			
			String withdraw_result = "";
			
			if("R".equals(req.getWithdraw())){
				withdraw_result = messageSource.getMessage("withdraw.reserve", null,
						LocaleContextHolder.getLocale());
			}else if("O".equals(req.getWithdraw())){
				withdraw_result = messageSource.getMessage("withdraw.office", null,
						LocaleContextHolder.getLocale());
			}else if("U".equals(req.getWithdraw())){
				withdraw_result = messageSource.getMessage("withdraw.update", null,
						LocaleContextHolder.getLocale());
			}
			reqBean.setRemarkStatus(req.getRemarkStatus());
			reqBean.setWithdraw(withdraw_result);
			reqBean.setCreateDateTh(null==req.getCreateDate()?"":formatDataTh.format(req.getCreateDate()));
			
			TechnicianGroupController techController = new TechnicianGroupController();
			TechnicianGroup technicianGroup = req.getTechnicianGroup();
			TechnicianGroupBean technicianGroupBean = techController.populateEntityToDto(technicianGroup);
			
			reqBean.setTechnicianGroup(technicianGroupBean);
			
			PersonnelController personController = new PersonnelController();
			Personnel personnel = req.getPersonnel();
			PersonnelBean personnelBean = personController.populateEntityToDto(personnel);
			reqBean.setPersonnelBean(personnelBean);
			
			requisitionDocumentBean.add(reqBean);
		}
		pagination.setDataListBean(requisitionDocumentBean);
		//end populate
		
		return pagination;
	}
	
	@RequestMapping(value="updatestatus", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public JsonResponse updateStatus(@RequestBody final RequisitionDocumentBean requisitionDocumentBean, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			//create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try{
				logger.info("[method : save][Type : Controller]");
				if(null != requisitionDocumentBean){
					RequisitionDocument requisitionDocument = requisitionDocumentService.getRequisitionDocumentById(requisitionDocumentBean.getId());
					if(null != requisitionDocument){
						requisitionDocument.setStatus(requisitionDocumentBean.getStatus().getStringValue());
						requisitionDocument.setRemarkStatus(requisitionDocumentBean.getRemarkStatus());
						requisitionDocument.setUpdatedBy(getUserNameLogin());
						requisitionDocument.setUpdatedDate(CURRENT_TIMESTAMP);
	
						requisitionDocumentService.update(requisitionDocument);
						jsonResponse.setError(false);
					}else{
						jsonResponse.setMessage("RequisitionDocument null by RequisitionDocumentId : "+requisitionDocumentBean.getId());
						jsonResponse.setError(true);
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
		
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("requisitionDocument.transaction.updatestatus.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	public RequisitionDocumentBean populateEntityToDto(RequisitionDocument req) {
		RequisitionDocumentBean reqBean = new RequisitionDocumentBean();
		SimpleDateFormat formatDataTh = new SimpleDateFormat(messageSource.getMessage("date.format.type1", null, LocaleContextHolder.getLocale()), new Locale( "TH" , "th" ));
		if(null != req){
			reqBean.setId(req.getId());
			reqBean.setRequisitionDocumentCode(req.getRequisitionDocumentCode());
			reqBean.setRemarkStatus(req.getRemarkStatus());
			reqBean.setDetail(req.getDetail());
			
			String status = "";
			int statusInt = 0;
			if("N".equals(req.getStatus())){
				status = messageSource.getMessage("requisition.status.normal", null,
						LocaleContextHolder.getLocale());
				statusInt = 1;
			}else{
				status = messageSource.getMessage("requisition.status.cancel", null,
						LocaleContextHolder.getLocale());
				statusInt = 0;
			}
			StatusBean statusBean = new StatusBean();
			statusBean.setStringValue(status);
			statusBean.setNumberValue(statusInt);
			reqBean.setStatus(statusBean);
			
			String withdraw = "";
			
			if("R".equals(req.getWithdraw())){
				withdraw = messageSource.getMessage("withdraw.reserve", null,
						LocaleContextHolder.getLocale());
			}else if("O".equals(req.getWithdraw())){
				withdraw = messageSource.getMessage("withdraw.office", null,
						LocaleContextHolder.getLocale());
			}else if("U".equals(req.getWithdraw())){
				withdraw = messageSource.getMessage("withdraw.update", null,
						LocaleContextHolder.getLocale());
			}
			
			reqBean.setWithdraw(withdraw);
			reqBean.setWithdrawValue(req.getWithdraw());
			reqBean.setCreateDateTh(null==req.getCreateDate()?"":formatDataTh.format(req.getCreateDate()));
			
			TechnicianGroupController techController = new TechnicianGroupController();
			TechnicianGroup technicianGroup = req.getTechnicianGroup();
			TechnicianGroupBean technicianGroupBean = techController.populateEntityToDto(technicianGroup);
			
			reqBean.setTechnicianGroup(technicianGroupBean);
			
			PersonnelController personController = new PersonnelController();
			Personnel personnel = req.getPersonnel();
			PersonnelBean personnelBean = personController.populateEntityToDto(personnel);
			reqBean.setPersonnelBean(personnelBean);
			
			List<RequisitionItemBean> requisitionItemBeans = new ArrayList<RequisitionItemBean>();
			List<RequisitionItem> requisitionItems = req.getRequisitionItems();
			if(null != requisitionItems && !requisitionItems.isEmpty()){
				for(RequisitionItem item:requisitionItems){
					RequisitionItemBean itemBean = new RequisitionItemBean();
					itemBean.setId(item.getId());
					itemBean.setQuantity(item.getQuantity());
					
					// set EquipmentProduct
					ProductAddController productAddController = new ProductAddController();
					productAddController.setMessageSource(messageSource);
					EquipmentProduct equipment = item.getEquipmentProduct();
					EquipmentProductBean equipmentBean = productAddController.populateEntityToDto(equipment);
					itemBean.setEquipment(equipmentBean);
					
					// set EquipmentProductItem
					EquipmentProductItemBean equipmentProductItemBean = new EquipmentProductItemBean();
					EquipmentProductItem equipmentProductItem = item.getEquipmentProductItem();
					equipmentProductItemBean.setId(equipmentProductItem.getId());
					equipmentProductItemBean.setCost(equipmentProductItem.getCost());
					equipmentProductItemBean.setReferenceNo(equipmentProductItem.getReference());
					equipmentProductItemBean.setSerialNo(equipmentProductItem.getSerialNo());
					equipmentProductItemBean.setNumberImport(equipmentProductItem.getNumberImport());
					equipmentProductItemBean.setSalePrice(equipmentProductItem.getSalePrice());
					
					itemBean.setEquipmentItem(equipmentProductItemBean);
					itemBean.setReturnEquipmentProductItem(item.getReturnEquipmentProductItem());

					requisitionItemBeans.add(itemBean);
				}
			}
			
			reqBean.setRequisitionItemList(requisitionItemBeans);
			
		}
		
		return reqBean;
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value="update", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public JsonResponse update(@RequestBody final RequisitionDocumentBean requisitionDocumentBean, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			//create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try{
				logger.info("[method : update][Type : Controller]");
				if(null != requisitionDocumentBean){
					
					RequisitionDocument requisitionDocument = requisitionDocumentService.getRequisitionDocumentById(requisitionDocumentBean.getId());
					if(null != requisitionDocument){
						
						// ลบ ข้อมูล item และคือสถานะกลับคลัง
						List<RequisitionItem> requisitionItemList = requisitionDocument.getRequisitionItems();
						if(null != requisitionItemList && requisitionItemList.size() > 0){
							for(RequisitionItem requisitionItem:requisitionItemList){
								EquipmentProductItem equipmentProductItem = requisitionItem.getEquipmentProductItem();
								if(null != equipmentProductItem){
									if("R".equals(requisitionDocument.getWithdraw())){ // เบิกเพื่อไปสำรอง (ยังไม่ตัดสต็อก)
										if(!"".equals(equipmentProductItem.getSerialNo())){ // มี S/N
											equipmentProductItem.setBalance(1);
											equipmentProductItem.setSpare(0);
											equipmentProductItem.setStatus(STATUS_ACTIVE);
										}else{ // ไม่มี S/N
											// อัพเดทจำนวน อุปกรณ์
											int balanceOld = equipmentProductItem.getBalance(); // จำนวน อุปกรณ์เก่า
											int balanceNew = balanceOld + requisitionItem.getQuantity();
											equipmentProductItem.setBalance(balanceNew);
											
											int spareItemOld = equipmentProductItem.getSpare();
											int spareItemNew = spareItemOld - requisitionItem.getQuantity();
											if(spareItemNew < 0) spareItemNew = 0;
											equipmentProductItem.setSpare(spareItemNew);
										}
									}else{ // (ตัดสต็อกทันที)
										// อัพเดทจำนวน อุปกรณ์
										if(!"".equals(equipmentProductItem.getSerialNo())){ // มี S/N
											equipmentProductItem.setBalance(1);
											equipmentProductItem.setStatus(STATUS_ACTIVE);
										}else{ // ไม่มี S/N
											// อัพเดทจำนวน อุปกรณ์
											int balanceOld = equipmentProductItem.getBalance(); // จำนวน อุปกรณ์เก่า
											int balanceNew = balanceOld + requisitionItem.getQuantity();
											equipmentProductItem.setBalance(balanceNew);
										}
									}
									equipmentProductItemService.update(equipmentProductItem);
									EquipmentProduct equipmentProduct = equipmentProductItem.getEquipmentProduct();
									// อัพ product ตัวแม่
									if(null != equipmentProduct){
										ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
										pro.setEquipmentProductService(equipmentProductService);
										pro.setMessageSource(messageSource);
										pro.autoUpdateStatusEquipmentProduct(equipmentProduct);
									}
								}
								requisitionItemService.delete(requisitionItem);
							}
						}
						
						requisitionDocument = requisitionDocumentService.getRequisitionDocumentById(requisitionDocumentBean.getId());
						requisitionDocument.setWithdraw(requisitionDocumentBean.getWithdraw());
						requisitionDocument.setDetail(requisitionDocumentBean.getDetail());
						
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
						requisitionDocument.setUpdatedBy(getUserNameLogin());
						requisitionDocument.setUpdatedDate(CURRENT_TIMESTAMP);
						// set 
						requisitionDocumentService.update(requisitionDocument);
						
						// เตรียมข้อมูลสำหรับ insert item
						List<RequisitionItem> requisitionItems = new ArrayList<RequisitionItem>();
						List<RequisitionItemBean> requisitionItemBeans = requisitionDocumentBean.getRequisitionItemList();
						if(null != requisitionItemBeans && requisitionItemBeans.size() > 0){
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
									equipmentProductItem.setUpdatedBy(getUserNameLogin());
									equipmentProductItem.setUpdatedDate(CURRENT_TIMESTAMP);
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
	
	@RequestMapping(value = "exportPdf/requisitionDocumentId/{requisitionDocumentId}", method = RequestMethod.GET)
	public ModelAndView requisitionExportPdf(Model model,
			@PathVariable Long requisitionDocumentId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("[method : requisitionExportPdf][Type : Controller][requisitionDocumentId : "+requisitionDocumentId+"]");
		ModelAndView modelAndView = new ModelAndView();
		
		String nameReport = "requisitionDocument";
		//check permission
		if (isPermission()) {
			JasperRender jasperRender = new JasperRender();
			List<Object> resUse = new ArrayList<Object>();

			RequisitionDocument requi = requisitionDocumentService.getRequisitionDocumentById(Long.valueOf(requisitionDocumentId));
			RequisitionDocumentBean bean = populateEntityToDto(requi);
			RequisitionDocumentBeanReport rdbr = new RequisitionDocumentBeanReport();
			if(null != bean){
				rdbr.setRequisitionDocumentCode(bean.getRequisitionDocumentCode());
				rdbr.setCreateDateTh(bean.getCreateDateTh());
				rdbr.setWithdraw(bean.getWithdraw());
				rdbr.setDetail(bean.getDetail());
				if(bean.getTechnicianGroup().getPersonnel() != null){
					rdbr.setPersonnelName(bean.getTechnicianGroup().getPersonnel().getFirstName()+" "+
							bean.getTechnicianGroup().getPersonnel().getLastName()+" ("+bean.getTechnicianGroup().getPersonnel().getNickName()+")");
					rdbr.setPersonnelCode(bean.getTechnicianGroup().getPersonnel().getPersonnelCode());
				}else{
					rdbr.setPersonnelName(bean.getPersonnelBean().getFirstName()+" "+
							bean.getPersonnelBean().getLastName()+" ("+bean.getPersonnelBean().getNickName()+")");
					rdbr.setPersonnelCode(bean.getPersonnelBean().getPersonnelCode());
				}
				rdbr.setPersonnelByCode(bean.getPersonnelBean().getPersonnelCode());
				rdbr.setPersonnelByName(bean.getPersonnelBean().getFirstName()+" "+
						bean.getPersonnelBean().getLastName()+" ("+bean.getPersonnelBean().getNickName()+")");
				rdbr.setPersonnelCode(bean.getPersonnelBean().getPersonnelCode());
				
				List<RequisitionDocumentItemBeanReport> requisitionItemList = new ArrayList<RequisitionDocumentItemBeanReport>();
				List<RequisitionItemBean> requisitionItemBeanList = bean.getRequisitionItemList();
				if(null != requisitionItemBeanList && requisitionItemBeanList.size() > 0){
					int no = 1;
					for(RequisitionItemBean requisitionItemBean:requisitionItemBeanList){
						RequisitionDocumentItemBeanReport reqitem = new RequisitionDocumentItemBeanReport();
						reqitem.setNo(""+(no++));
						reqitem.setProductCode(requisitionItemBean.getEquipment().getProductCode());
						reqitem.setProductName(requisitionItemBean.getEquipment().getProductName());
						reqitem.setSerialNo(requisitionItemBean.getEquipmentItem().getSerialNo());
						reqitem.setQuantity(""+requisitionItemBean.getQuantity());
						reqitem.setUnitName(requisitionItemBean.getEquipment().getUnit().getUnitName());
						requisitionItemList.add(reqitem);
					}
				}			
				rdbr.setRequisitionItemList(requisitionItemList);
			}
			
			Map<String, Object> params = new HashMap<String, Object>();
			String pathLogo = request.getSession().getServletContext().getRealPath("/jasper/easynet.png");
			params.put("pathLogo", pathLogo);
			
			
			resUse.add(rdbr);
			jasperRender.setBeanList(resUse);
			jasperRender.setParams(params);

			try {
				byte[] bytesFinal = null;
				bytesFinal = jasperRender.processStream(ParamsEnum.StreamType.PDF,
						jasperJrxmlComponent.compileJasperReport(nameReport, request));

				response.reset();
				response.resetBuffer();
				response.setContentType("application/pdf");
				response.setContentLength(bytesFinal.length);
				ServletOutputStream ouputStream = response.getOutputStream();
				ouputStream.write(bytesFinal, 0, bytesFinal.length);
				ouputStream.flush();
				ouputStream.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		} else {
			// no permission
			modelAndView.setViewName(PERMISSION_DENIED);
			return modelAndView;
		}
		
		return null;
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
	
	public void generateAlert(JsonResponse jsonResponse, HttpServletRequest request, String title, String detail){
		new AlertUtil().generateAlert(jsonResponse, request, title, detail);
	}
	
	
}
