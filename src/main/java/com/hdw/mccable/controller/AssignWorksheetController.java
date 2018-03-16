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

import org.apache.commons.lang.StringUtils;
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

import com.hdw.mccable.dto.AddPointWorksheetBean;
import com.hdw.mccable.dto.AddSetTopBoxWorksheetBean;
import com.hdw.mccable.dto.AddressBean;
import com.hdw.mccable.dto.AlertBean;
import com.hdw.mccable.dto.AmphurBean;
import com.hdw.mccable.dto.AnalyzeProblemsWorksheetBean;
import com.hdw.mccable.dto.AssignWorksheetSearchBean;
import com.hdw.mccable.dto.BorrowWorksheetBean;
import com.hdw.mccable.dto.CareerBean;
import com.hdw.mccable.dto.ConnectWorksheetBean;
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
import com.hdw.mccable.dto.InvoiceDocumentBean;
import com.hdw.mccable.dto.JsonRequestAssignWorksheet;
import com.hdw.mccable.dto.JsonResponse;
import com.hdw.mccable.dto.MovePointWorksheetBean;
import com.hdw.mccable.dto.MoveWorksheetBean;
import com.hdw.mccable.dto.PersonnelBean;
import com.hdw.mccable.dto.ProductItemBean;
import com.hdw.mccable.dto.ProductItemWorksheetBean;
import com.hdw.mccable.dto.ProvinceBean;
import com.hdw.mccable.dto.ReducePointWorksheetBean;
import com.hdw.mccable.dto.RepairConnectionWorksheetBean;
import com.hdw.mccable.dto.RequisitionDocumentBean;
import com.hdw.mccable.dto.RequisitionItemBean;
import com.hdw.mccable.dto.ServiceApplicationBean;
import com.hdw.mccable.dto.ServicePackageBean;
import com.hdw.mccable.dto.ServicePackageTypeBean;
import com.hdw.mccable.dto.ServiceProductBean;
import com.hdw.mccable.dto.SetupWorksheetBean;
import com.hdw.mccable.dto.StatusBean;
import com.hdw.mccable.dto.SubWorksheetBean;
import com.hdw.mccable.dto.TechnicainGroupWorkLoad;
import com.hdw.mccable.dto.TechnicianGroupBean;
import com.hdw.mccable.dto.TuneWorksheetBean;
import com.hdw.mccable.dto.UnitBean;
import com.hdw.mccable.dto.WorksheetBean;
import com.hdw.mccable.dto.ZoneBean;
import com.hdw.mccable.entity.Address;
import com.hdw.mccable.entity.EquipmentProductItem;
import com.hdw.mccable.entity.HistoryTechnicianGroupWork;
import com.hdw.mccable.entity.InternetProductItem;
import com.hdw.mccable.entity.Invoice;
import com.hdw.mccable.entity.MenuReport;
import com.hdw.mccable.entity.Personnel;
import com.hdw.mccable.entity.PersonnelAssign;
import com.hdw.mccable.entity.ProductItem;
import com.hdw.mccable.entity.ProductItemWorksheet;
import com.hdw.mccable.entity.RequisitionItem;
import com.hdw.mccable.entity.ServiceApplication;
import com.hdw.mccable.entity.SubWorksheet;
import com.hdw.mccable.entity.TechnicianGroup;
import com.hdw.mccable.entity.Worksheet;
import com.hdw.mccable.entity.WorksheetAnalyzeProblems;
import com.hdw.mccable.entity.Zone;
import com.hdw.mccable.service.HistoryTechnicianGroupWorkService;
import com.hdw.mccable.service.ServiceApplicationService;
import com.hdw.mccable.service.TechnicianGroupService;
import com.hdw.mccable.service.UnitService;
import com.hdw.mccable.service.WorkSheetService;
import com.hdw.mccable.service.ZoneService;
import com.hdw.mccable.utils.AlertUtil;
import com.hdw.mccable.utils.DateUtil;
import com.hdw.mccable.utils.Pagination;

@Controller
@RequestMapping("/assignworksheet")
public class AssignWorksheetController extends BaseController{
	final static Logger logger = Logger.getLogger(AssignWorksheetController.class);
	public static final String CONTROLLER_NAME = "assignworksheet/";
	public static final String INDIVIDUAL = "I";
	public static final String CORPORATE = "C";
	// initial service
	
	@Autowired(required=true)
	@Qualifier(value="zoneService")
	private ZoneService zoneService;
	
	@Autowired(required = true)
	@Qualifier(value = "technicianGroupService")
	private TechnicianGroupService technicianGroupService;
	
	@Autowired(required = true)
	@Qualifier(value = "workSheetService")
	private WorkSheetService workSheetService;
	
	@Autowired(required = true)
	@Qualifier(value = "historyTechnicianGroupWorkService")
	private HistoryTechnicianGroupWorkService historyTechnicianGroupWorkService;
	
	@Autowired(required = true)
	@Qualifier(value = "serviceApplicationService")
	private ServiceApplicationService serviceApplicationService;
	
	@Autowired(required=true)
	@Qualifier(value="unitService")
	private UnitService unitService;
	
	@Autowired
	private MessageSource messageSource;
	// End initial service
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView init(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : init][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView(); 
		ZoneController zoneController = new ZoneController();
		TechnicianGroupController technicianGroupController = new TechnicianGroupController();
		
		// get current session
		HttpSession session = request.getSession();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}

		//check permission
		if(isPermission()){
			try{ 
				 //init load
				//zone
				List<Zone> zones = zoneService.findAll();
				List<ZoneBean> zoneBeans = new ArrayList<ZoneBean>();
				for(Zone zone : zones){
					ZoneBean zoneBean = zoneController.populateEntityToDto(zone);
					zoneBeans.add(zoneBean);
				}
				modelAndView.addObject("zoneBeans", zoneBeans);
				
				//technician group
				List<TechnicianGroup> technicianGroups = technicianGroupService.findAll();
				List<TechnicianGroupBean> technicianGroupBeans = new ArrayList<TechnicianGroupBean>(); 
				for(TechnicianGroup technicianGroup : technicianGroups){
					TechnicianGroupBean TechnicianGroupBean = technicianGroupController.populateEntityToDto(technicianGroup);
					technicianGroupBeans.add(TechnicianGroupBean);
				}
				modelAndView.addObject("technicianGroupBeans", technicianGroupBeans);
				
				//init paging
				Pagination pagination = createPagination(1, 10, "assignworksheet",new AssignWorksheetSearchBean());
				modelAndView.addObject("pagination",pagination);
				
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
		session.removeAttribute("assignWorksheetSearchBean");
		session.removeAttribute("alert");
		
		modelAndView.setViewName(CONTROLLER_NAME+INIT);
		return modelAndView;
	}

	@RequestMapping(value = "page/{id}/itemPerPage/{itemPerPage}", method = RequestMethod.GET)
	public ModelAndView pagination(@PathVariable int id, @PathVariable int itemPerPage, Model model,
			HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : pagination][Type : Controller]");
		logger.info("[method : pagination][itemPerPage : " + itemPerPage + "]");
		ModelAndView modelAndView = new ModelAndView();
		ZoneController zoneController = new ZoneController();
		TechnicianGroupController technicianGroupController = new TechnicianGroupController();
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
				//zone
				List<Zone> zones = zoneService.findAll();
				List<ZoneBean> zoneBeans = new ArrayList<ZoneBean>();
				for(Zone zone : zones){
					ZoneBean zoneBean = zoneController.populateEntityToDto(zone);
					zoneBeans.add(zoneBean);
				}
				modelAndView.addObject("zoneBeans", zoneBeans);
				
				//technician group
				List<TechnicianGroup> technicianGroups = technicianGroupService.findAll();
				List<TechnicianGroupBean> technicianGroupBeans = new ArrayList<TechnicianGroupBean>(); 
				for(TechnicianGroup technicianGroup : technicianGroups){
					TechnicianGroupBean TechnicianGroupBean = technicianGroupController.populateEntityToDto(technicianGroup);
					technicianGroupBeans.add(TechnicianGroupBean);
				}
				modelAndView.addObject("technicianGroupBeans", technicianGroupBeans);
				
				// search worksheet bean
				AssignWorksheetSearchBean assignWorksheetSearchBean = (AssignWorksheetSearchBean) session
						.getAttribute("assignWorksheetSearchBean");
				// set value search worksheet
				if (assignWorksheetSearchBean != null) {
					modelAndView.addObject("assignWorksheetSearchBean", assignWorksheetSearchBean);
				} else {
					modelAndView.addObject("assignWorksheetSearchBean", new AssignWorksheetSearchBean());
				}
				
				//search process and pagination
				if(assignWorksheetSearchBean != null){
					Pagination pagination = createPagination(id, itemPerPage, "assignworksheet",assignWorksheetSearchBean);
					modelAndView.addObject("pagination", pagination);
				}else{
					Pagination pagination = createPagination(id, itemPerPage, "assignworksheet",new AssignWorksheetSearchBean());
					modelAndView.addObject("pagination", pagination);
				}
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		
		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);
		
		//remove session
		session.removeAttribute("alert");
		
		modelAndView.setViewName(CONTROLLER_NAME + INIT);
		return modelAndView;
	}
	
	// search request
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView searchWorksheetForAssign(
			@ModelAttribute("assignWorksheetSearchBean") AssignWorksheetSearchBean assignWorksheetSearchBean,
			HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {

		logger.info("[method : searchWorksheetForAssign][Type : Controller]");
		logger.info("[method : searchWorksheetForAssign][assignWorksheetSearchBean : " + assignWorksheetSearchBean.toString()
				+ "]");
		ModelAndView modelAndView = new ModelAndView();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		
		// check permission
		if (isPermission()) {
			generateSearchSession(assignWorksheetSearchBean, request);
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		modelAndView.setViewName(REDIRECT + "/assignworksheet/page/1/itemPerPage/10");
		return modelAndView;
	}
	
	// save assing worksheet
	@RequestMapping(value = "saveAssignWorksheet", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse saveAssignWorksheet(
			@RequestBody final List<JsonRequestAssignWorksheet> jsonRequestAssignWorksheets,HttpServletRequest request) {
		logger.info("[method : saveAssignWorksheet][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			// create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try { 
				for(JsonRequestAssignWorksheet JsonRequestAssignWorksheet : jsonRequestAssignWorksheets){
					//worksheet level
					Worksheet worksheet = workSheetService.getWorksheetById(JsonRequestAssignWorksheet.getWorksheetId());
					TechnicianGroup technicianGroup = technicianGroupService.getTechnicianGroupById(JsonRequestAssignWorksheet.getTechnicianGroupId());
					
					//save coppy history
					HistoryTechnicianGroupWork historyTechnicianGroupWork = new HistoryTechnicianGroupWork();
					historyTechnicianGroupWork.setDeleted(Boolean.FALSE);
					historyTechnicianGroupWork.setCreateDate(CURRENT_TIMESTAMP);
					historyTechnicianGroupWork.setCreatedBy(getUserNameLogin());
					historyTechnicianGroupWork.setTechnicianGroup(technicianGroup);
					//personnel Assign
					List<PersonnelAssign> personnelAssigns = new ArrayList<PersonnelAssign>();
					for(Personnel personnel : technicianGroup.getPersonnels()){
						PersonnelAssign personnelAssign = new PersonnelAssign();
						personnelAssign.setPersonnelId(personnel.getId());
						personnelAssign.setHistoryTechnicianGroupWork(historyTechnicianGroupWork);
						personnelAssign.setDeleted(Boolean.FALSE);
						personnelAssign.setCreateDate(CURRENT_TIMESTAMP);
						personnelAssign.setCreatedBy(getUserNameLogin());
						personnelAssigns.add(personnelAssign);
					}
					historyTechnicianGroupWork.setPersonnelAssigns(personnelAssigns);
					historyTechnicianGroupWork.setWorkSheet(worksheet);
					historyTechnicianGroupWork.setStatusHistory(messageSource.getMessage("history.worksheet.processing", null, LocaleContextHolder.getLocale()));
					Date assignDate = new DateUtil().convertStringToDateTimeDb(JsonRequestAssignWorksheet.getAssignDate());
					historyTechnicianGroupWork.setAssignDate(assignDate);
					historyTechnicianGroupWorkService.save(historyTechnicianGroupWork);
					
					//update status worksheet
					worksheet.setStatus(messageSource.getMessage("worksheet.status.value.r", null, LocaleContextHolder.getLocale()));
					//update status service application
					ServiceApplication serviceApplication = worksheet.getServiceApplication();
					if(worksheet.getWorkSheetType().equals("C_S")){
						serviceApplication.setStatus(messageSource.getMessage("worksheet.status.value.w", null, LocaleContextHolder.getLocale()));
					}
					
					serviceApplicationService.update(serviceApplication);
					
					//รอบบิล
					if(worksheet.getInvoice() != null){
						worksheet.getInvoice().setStatus(messageSource.getMessage("financial.invoice.status.waitpay", null, LocaleContextHolder.getLocale())); //รอชำระ
						worksheet.getInvoice().setCreateDate(assignDate);
					}
					
					workSheetService.update(worksheet);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				jsonResponse.setMessage(ex.getMessage());
				jsonResponse.setError(true);
			}
		} else {
			jsonResponse.setError(true);
		}

		generateAlert(jsonResponse, request,
				messageSource.getMessage("worksheet.assignment.transaction.title.success", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("worksheet.assignment.transaction.success", null,
						LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	// loadTechnicianGroupByDateWorksheet
	@RequestMapping(value = "loadTechnicianGroupByDateWorksheet", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public JsonResponse loadTechnicianGroupByDateWorksheet(@RequestBody final String dateLoadWork,
			HttpServletRequest request) {
		logger.info("[method : loadTechnicianGroupByDateWorksheet][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				 List<HistoryTechnicianGroupWork> historyTechnicianGroupWorkList = 
						 workSheetService.findHistoryTechnicianGroupWorkByDateAssign(dateLoadWork);
				 
				 List<TechnicianGroup> technicianGroupList = technicianGroupService.findAll();
				 List<TechnicainGroupWorkLoad> technicainGroupWorkLoadList = new ArrayList<TechnicainGroupWorkLoad>();
				 TechnicianGroupController technicianGroupController = new TechnicianGroupController();

				 for(TechnicianGroup technicianGroup : technicianGroupList){
					 int count = 0;
					 TechnicianGroupBean technicianGroupBean = technicianGroupController.populateEntityToDto(technicianGroup);
					 for(HistoryTechnicianGroupWork historyTechnicianGroupWork : historyTechnicianGroupWorkList){
						 if(historyTechnicianGroupWork.getTechnicianGroup().getId() == technicianGroupBean.getId()){
							 count = count+1;
						 }
					 }
					 TechnicainGroupWorkLoad technicainGroupWorkLoad = new TechnicainGroupWorkLoad();
					 technicainGroupWorkLoad.setTechnicianGroupBean(technicianGroupBean);
					 technicainGroupWorkLoad.setWorksheetSize(count);
					 technicainGroupWorkLoadList.add(technicainGroupWorkLoad);
				 }
				 
				 jsonResponse.setResult(technicainGroupWorkLoadList);
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
	
	// create pagination
	@SuppressWarnings("unchecked")
	Pagination createPagination(int currentPage, int itemPerPage, String controller,AssignWorksheetSearchBean assignWorksheetSearchBean) {
		if (itemPerPage == 0)itemPerPage = 10;
		
		Pagination pagination = new Pagination();
		pagination.setTotalItem(workSheetService.getCountTotal(assignWorksheetSearchBean));
		pagination.setCurrentPage(currentPage);
		pagination.setItemPerPage(itemPerPage);
		pagination.setUrl(controller);
		pagination = workSheetService.getByPageForAssign(pagination, assignWorksheetSearchBean);
		List<Worksheet> worksheetBeansValidateSize = (List<Worksheet>)pagination.getDataList();
		
		if(worksheetBeansValidateSize.size() <= 0){
			pagination.setCurrentPage(1);
			pagination = workSheetService.getByPageForAssign(pagination, assignWorksheetSearchBean);
		}
		//poppulate
		List<WorksheetBean> worksheetBeans = new ArrayList<WorksheetBean>();
		for(Worksheet Worksheet : (List<Worksheet>) pagination.getDataList()){
			worksheetBeans.add(populateEntityToDto(Worksheet)); 
		}
		pagination.setDataListBean(worksheetBeans);
		return pagination;
	}

	
	// create search session
	public void generateSearchSession(AssignWorksheetSearchBean assignWorksheetSearchBean, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("assignWorksheetSearchBean", assignWorksheetSearchBean);
	}

	public void generateAlert(JsonResponse jsonResponse, HttpServletRequest request, String title, String detail){
		new AlertUtil().generateAlert(jsonResponse, request, title, detail);
	}
	
	public WorksheetBean populateEntityToDtoInit(Worksheet worksheet){
		WorksheetBean worksheetBean = null;
		if (worksheet.getWorkSheetType().equals(
				messageSource.getMessage("worksheet.type.cable.addpoint", null, LocaleContextHolder.getLocale()))) {
			worksheetBean = new AddPointWorksheetBean();
			AddPointWorksheetBean addPointWorksheetBean = new AddPointWorksheetBean();
			addPointWorksheetBean.setAddPointPrice(worksheet.getWorksheetAddPoint().getAddPointPrice());
			addPointWorksheetBean.setMonthlyFree(worksheet.getWorksheetAddPoint().getMonthlyFree());
			worksheetBean.setAddPointWorksheetBean(addPointWorksheetBean);			
		} else if (worksheet.getWorkSheetType().equals(
				messageSource.getMessage("worksheet.type.cable.connect", null, LocaleContextHolder.getLocale()))) {
			worksheetBean = new ConnectWorksheetBean();
			
		} else if (worksheet.getWorkSheetType().equals(
				messageSource.getMessage("worksheet.type.cable.setup", null, LocaleContextHolder.getLocale()))) {
			worksheetBean = new SetupWorksheetBean();
			
		} else if (worksheet.getWorkSheetType()
				.equals(messageSource.getMessage("worksheet.type.cable.tune", null, LocaleContextHolder.getLocale()))) {
			worksheetBean = new TuneWorksheetBean();
			
		} else if (worksheet.getWorkSheetType().equals(
				messageSource.getMessage("worksheet.type.cable.repair", null, LocaleContextHolder.getLocale()))) {
			worksheetBean = new RepairConnectionWorksheetBean();
			
		} else if (worksheet.getWorkSheetType().equals(
				messageSource.getMessage("worksheet.type.cable.addsettop", null, LocaleContextHolder.getLocale()))) {
			worksheetBean = new AddSetTopBoxWorksheetBean();
			
		} else if (worksheet.getWorkSheetType().equals(
				messageSource.getMessage("worksheet.type.cable.movepoint", null, LocaleContextHolder.getLocale()))) {
			
			worksheetBean = new MovePointWorksheetBean();
			MovePointWorksheetBean movePointWorksheetBean = new MovePointWorksheetBean();
			movePointWorksheetBean.setDigitalPoint(worksheet.getWorksheetMovePoint().getDigitalPoint());
			movePointWorksheetBean.setAnalogPoint(worksheet.getWorksheetMovePoint().getAnalogPoint());
			movePointWorksheetBean.setMovePointPrice(worksheet.getWorksheetMovePoint().getMovePointPrice());
			worksheetBean.setMovePointWorksheetBean(movePointWorksheetBean);
			
		} else if (worksheet.getWorkSheetType().equals(
				messageSource.getMessage("worksheet.type.cable.reducepoint", null, LocaleContextHolder.getLocale()))) {
			worksheetBean = new ReducePointWorksheetBean();
			
		} else if (worksheet.getWorkSheetType()
				.equals(messageSource.getMessage("worksheet.type.cable.cut", null, LocaleContextHolder.getLocale()))) {
			worksheetBean = new CutWorksheetBean();
			
		} else if (worksheet.getWorkSheetType()
				.equals(messageSource.getMessage("worksheet.type.cable.move", null, LocaleContextHolder.getLocale()))) {
			
			worksheetBean = new MoveWorksheetBean();
			MoveWorksheetBean moveWorksheetBean = new MoveWorksheetBean();
			moveWorksheetBean.setAnalogPoint(worksheet.getWorksheetMove().getAnalogPoint());
			moveWorksheetBean.setDigitalPoint(worksheet.getWorksheetMove().getDigitalPoint());
			moveWorksheetBean.setMoveCablePrice(worksheet.getWorksheetMove().getMoveCablePrice());
			worksheetBean.setMoveWorksheetBean(moveWorksheetBean);
			
		} else if (worksheet.getWorkSheetType().equals(
				messageSource.getMessage("worksheet.type.cable.borrow", null, LocaleContextHolder.getLocale()))) {
			worksheetBean = new BorrowWorksheetBean();
		} else if (worksheet.getWorkSheetType().equals(
				messageSource.getMessage("worksheet.type.internet.analyzeproblems", null, LocaleContextHolder.getLocale()))) {
			worksheetBean = new AnalyzeProblemsWorksheetBean();
			AnalyzeProblemsWorksheetBean analyzeProblemsWorksheetBean = new AnalyzeProblemsWorksheetBean();
			analyzeProblemsWorksheetBean.setMenuReportId(worksheet.getWorksheetAnalyzeProblems().getMenuReport().getId());
			worksheetBean.setAnalyzeProblemsWorksheetBean(analyzeProblemsWorksheetBean);
		}
		
		Invoice invoice = worksheet.getInvoice();
		if(null != invoice){
			InvoiceDocumentBean invoiceDocumentBean = new InvoiceDocumentBean();
			invoiceDocumentBean.setId(invoice.getId());
			invoiceDocumentBean.setStatus(invoice.getStatus());
			invoiceDocumentBean.setStatusScan(invoice.getStatusScan());
			worksheetBean.setInvoiceDocumentBean(invoiceDocumentBean);
		}
		
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
		worksheetBean.setCreateByTh(worksheet.getCreatedBy());
		
		//update date/by
		worksheetBean.setUpdateDateTh(
				null == worksheet.getUpdatedDate() ? "" : formatDataTh.format(worksheet.getUpdatedDate()));
		worksheetBean.setUpdateByTh(worksheet.getUpdatedBy());
		
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
		
		if(worksheet.getWorkSheetType().equals(messageSource.getMessage("worksheet.type.cable.setup", null, LocaleContextHolder.getLocale()))) {
			serviceApplicationBean.setEasyInstallationDateTime(worksheet.getServiceApplication().getEasyInstallationDateTime());
		}
		serviceApplicationBean.setRefund(worksheet.getServiceApplication().getRefund());
		serviceApplicationBean.setFlagRefund(worksheet.getServiceApplication().isFlagRefund());
		
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
		servicePackageBean.setMonthlyService(worksheet.getServiceApplication().getServicePackage().isMonthlyService());
		ServicePackageTypeBean servicePackageTypeBean = new ServicePackageTypeBean();
		servicePackageTypeBean.setId(worksheet.getServiceApplication().getServicePackage().getServicePackageType().getId());
		servicePackageTypeBean.setPackageTypeName(worksheet.getServiceApplication().getServicePackage().getServicePackageType().getPackageTypeName());
		servicePackageBean.setServiceType(servicePackageTypeBean);
		serviceApplicationBean.setServicepackage(servicePackageBean);
		
		//reference service application
		if(worksheet.getServiceApplication().getReferenceServiceApplicationId()!=null){
			ServiceApplicationBean serviceApplicationRefBean = new ServiceApplicationBean();
			serviceApplicationRefBean.setId(worksheet.getServiceApplication().getReferenceServiceApplicationId());
			serviceApplicationBean.setReferenceServiceApplicationBean(serviceApplicationRefBean);
		}else{
			serviceApplicationBean.setReferenceServiceApplicationBean(null);
		}
		
		
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
//		List<AddressBean> addressBeans = new ArrayList<AddressBean>();
//		for (Address address : worksheet.getServiceApplication().getCustomer().getAddresses()) {
//			AddressBean addressBean = new AddressBean();
//			addressBean.setMessageSource(messageSource);
//			addressBean.setId(address.getId());
//			addressBean.setDetail(address.getDetail());
//			addressBean.setNo(address.getNo());
//			addressBean.setAlley(address.getAlley());
//			addressBean.setRoad(address.getRoad());
//			addressBean.setRoom(address.getRoom());
//			addressBean.setFloor(address.getFloor());
//			addressBean.setBuilding(address.getBuilding());
//			addressBean.setSection(address.getSection());
//			addressBean.setPostcode(address.getPostcode());
//			addressBean.setAddressType(address.getAddressType());
//			addressBean.setVillage(address.getVillage());
//			addressBean.setNearbyPlaces(address.getNearbyPlaces());
//			addressBean.setOverrideAddressId(address.getOverrideAddressId());
//
//			// user bean
//			// province
//			if (address.getProvinceModel() != null) {
//				ProvinceBean provinceBean = new ProvinceBean();
//				provinceBean.setId(address.getProvinceModel().getId());
//				provinceBean.setPROVINCE_NAME(address.getProvinceModel().getPROVINCE_NAME());
//				addressBean.setProvinceBean(provinceBean);
//			}
//
//			if (address.getAmphur() != null) {
//				// amphur
//				AmphurBean amphurBean = new AmphurBean();
//				amphurBean.setId(address.getAmphur().getId());
//				amphurBean.setAMPHUR_NAME(address.getAmphur().getAMPHUR_NAME());
//				amphurBean.setPOSTCODE(address.getAmphur().getPOSTCODE());
//				addressBean.setAmphurBean(amphurBean);
//			}
//
//			if (address.getDistrictModel() != null) {
//				// district
//				DistrictBean districtBean = new DistrictBean();
//				districtBean.setId(address.getDistrictModel().getId());
//				districtBean.setDISTRICT_NAME(address.getDistrictModel().getDISTRICT_NAME());
//				addressBean.setDistrictBean(districtBean);
//			}
//			
//			ServiceApplicationBean serviceApp = new ServiceApplicationBean();
////			serviceApp.setId(address.getServiceApplication().getId());
//			serviceApp.setId(worksheet.getServiceApplication().getId());
//			
//			addressBean.setServiceApplicationBean(serviceApp);
//			addressBean.collectAddress();
//			addressBeans.add(addressBean);
//		}
//		customerBean.setAddressList(addressBeans);

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
//		List<ProductItemBean> productItemBeans = new ArrayList<ProductItemBean>();
//		for (ProductItem productItem : worksheet.getServiceApplication().getProductItems()) {
//			
//			Boolean checkWorksheet = true;
//			if(checkWorksheet){
//			ProductItemBean productItemBean = new ProductItemBean();
//			productItemBean.setId(productItem.getId());
//			productItemBean.setType(productItem.getProductType());
//			productItemBean.setQuantity(productItem.getQuantity());
//			productItemBean.setFree(productItem.isFree());
//			productItemBean.setLend(productItem.isLend());
//			productItemBean.setAmount(productItem.getAmount());
//			productItemBean.setPrice(productItem.getPrice());
//			productItemBean.setProductTypeMatch(productItem.getProductTypeMatch());
//
//			// case equipment product
//			if (productItem.getProductType().equals(TYPE_EQUIMENT)) {
//				ProductAddController productAddController = new ProductAddController();
//				productAddController.setMessageSource(messageSource);
//				EquipmentProductBean equipmentProductBean = productAddController
//						.populateEntityToDto(productItem.getEquipmentProduct());
//				productItemBean.setProduct(equipmentProductBean);
//				productItemBean.getProduct().setTypeEquipment();
//
//				// case internet product
//			} else if (productItem.getProductType().equals(TYPE_INTERNET_USER)) {
//				ProductOrderInternetProductController interProduct = new ProductOrderInternetProductController();
//				interProduct.setMessageSource(messageSource);
//				InternetProductBean internetProductBean = interProduct
//						.populateEntityToDto(productItem.getInternetProduct());
//				productItemBean.setProduct(internetProductBean);
//				productItemBean.getProduct().setTypeInternet();
//
//				// case service product
//			} else if (productItem.getProductType().equals(TYPE_SERVICE)) {
//				ProductOrderServiceProductController serviceProduct = new ProductOrderServiceProductController();
//				serviceProduct.setMessageSource(messageSource);
//				ServiceProductBean serviceProductBean = serviceProduct
//						.populateEntityToDto(productItem.getServiceProduct());
//				productItemBean.setProduct(serviceProductBean);
//				productItemBean.getProduct().setTypeService();
//
//			}
//			
//			// set data product_item_worksheet
//			List<ProductItemWorksheetBean> productItemWorksheetBeanList = new ArrayList<ProductItemWorksheetBean>();
//			List<ProductItemWorksheet> productItemWorksheets = productItem.getProductItemWorksheets();
//			if(null != productItemWorksheets && productItemWorksheets.size() > 0){
//				for(ProductItemWorksheet productItemWorksheet:productItemWorksheets){
//					ProductItemWorksheetBean productItemWorksheetBean = new ProductItemWorksheetBean();
//					productItemWorksheetBean.setId(productItemWorksheet.getId());
//					productItemWorksheetBean.setAmount(productItemWorksheet.getAmount());
//					productItemWorksheetBean.setDeposit(productItemWorksheet.getDeposit());
//					productItemWorksheetBean.setFree(productItemWorksheet.isFree());
//					productItemWorksheetBean.setLend(productItemWorksheet.isLend());
//					productItemWorksheetBean.setDeposit(productItemWorksheet.getDeposit());
//					productItemWorksheetBean.setQuantity(productItemWorksheet.getQuantity());
//					productItemWorksheetBean.setReturnEquipment(productItemWorksheet.isReturnEquipment());
//					productItemWorksheetBean.setLendStatus(productItemWorksheet.getLendStatus());
//					productItemWorksheetBean.setProductTypeMatch(productItemWorksheet.getProductTypeMatch());
//					if(TYPE_EQUIMENT.equals(productItemWorksheet.getProductType())){
//						EquipmentProductItem equipmentProductItem = productItemWorksheet.getEquipmentProductItem();
//						if(null != equipmentProductItem){
//							EquipmentProductItemBean equipmentProductItemBean = new EquipmentProductItemBean();
//							equipmentProductItemBean.setId(equipmentProductItem.getId());
//							equipmentProductItemBean.setSerialNo(equipmentProductItem.getSerialNo());
//							equipmentProductItemBean.setBalance(equipmentProductItem.getBalance());
//							equipmentProductItemBean.setReservations(equipmentProductItem.getReservations());
//							equipmentProductItemBean.setNumberImport(equipmentProductItem.getNumberImport());
//							
//							productItemWorksheetBean.setEquipmentProductItemBean(equipmentProductItemBean);
//						}
//					}else if(TYPE_INTERNET_USER.equals(productItemWorksheet.getProductType())){
//						InternetProductItem internetProductItem = productItemWorksheet.getInternetProductItem();
//						if(null != internetProductItem){
//							InternetProductBeanItem internetProductBeanItem = new InternetProductBeanItem();
//							internetProductBeanItem.setId(internetProductItem.getId());
//							internetProductBeanItem.setUserName(internetProductItem.getUserName());
//							internetProductBeanItem.setPassword(internetProductItem.getPassword());
//							
//							productItemWorksheetBean.setInternetProductBeanItem(internetProductBeanItem);
//						}
//					}
//					
//					productItemWorksheetBeanList.add(productItemWorksheetBean);
//				}
//			}
//			productItemBean.setProductItemWorksheetBeanList(productItemWorksheetBeanList );
//			
//			productItemBeans.add(productItemBean);
//
//			}
//			//check worksheet setup and null
//		}
//		serviceApplicationBean.setProductitemList(productItemBeans);
		
		
		worksheetBean.setServiceApplication(serviceApplicationBean);
		
		//history
//		List<HistoryTechnicianGroupWorkBean> historyTechnicianGroupWorkBeans = new ArrayList<HistoryTechnicianGroupWorkBean>();
//		for(HistoryTechnicianGroupWork historyTechnicianGroupWork : worksheet.getHistoryTechnicianGroupWorks()){
//			HistoryTechnicianGroupWorkBean historyTechnicianGroupWorkBean = new HistoryTechnicianGroupWorkBean();
//			historyTechnicianGroupWorkBean.setId(historyTechnicianGroupWork.getId());
//			
//			TechnicianGroup technicianGroup = historyTechnicianGroupWork.getTechnicianGroup();
//			if(null != technicianGroup){
//				TechnicianGroupBean technicianGroupBean = new TechnicianGroupBean();
//				technicianGroupBean.setId(technicianGroup.getId());
//				PersonnelBean personnelBean = new PersonnelBean();
//				Personnel personnel = technicianGroup.getPersonnel();
//				personnelBean.setId(personnel.getId());
//				personnelBean.setFirstName(personnel.getFirstName());
//				personnelBean.setLastName(personnel.getLastName());
//				
//				technicianGroupBean.setTechnicianGroupName(technicianGroup.getTechnicianGroupName());
//				technicianGroupBean.setPersonnel(personnelBean);
//				
//				historyTechnicianGroupWorkBean.setTechnicainGroup(technicianGroupBean);
//			}
//			historyTechnicianGroupWorkBean.setRemarkNotSuccess(historyTechnicianGroupWork.getRemarkNotSuccess());
//			historyTechnicianGroupWorkBean.setStatusHistory(historyTechnicianGroupWork.getStatusHistory());
//			historyTechnicianGroupWorkBean.setAssingCurrentDate(historyTechnicianGroupWork.getAssignDate());
//			
//			//technician group
//			TechnicianGroupBean technicainGroup = new TechnicianGroupBean();
//			if(null != historyTechnicianGroupWork.getTechnicianGroup()){
//			technicainGroup.setId(historyTechnicianGroupWork.getTechnicianGroup().getId());
//			technicainGroup.setTechnicianGroupName(historyTechnicianGroupWork.getTechnicianGroup().getTechnicianGroupName());
//			}
//			//lead technicain group
//			PersonnelBean leaderPersonnel = new PersonnelBean();
//			if(null != historyTechnicianGroupWork.getTechnicianGroup() && null != historyTechnicianGroupWork.getTechnicianGroup().getPersonnel()){
//			leaderPersonnel.setId(historyTechnicianGroupWork.getTechnicianGroup().getPersonnel().getId());
//			leaderPersonnel.setFirstName(historyTechnicianGroupWork.getTechnicianGroup().getPersonnel().getFirstName());
//			leaderPersonnel.setLastName(historyTechnicianGroupWork.getTechnicianGroup().getPersonnel().getLastName());
//			}
//			technicainGroup.setPersonnel(leaderPersonnel);
//			historyTechnicianGroupWorkBean.setTechnicainGroup(technicainGroup);
//			
//			historyTechnicianGroupWorkBeans.add(historyTechnicianGroupWorkBean);
//		}
//		worksheetBean.setHistoryTechnicianGroupWorkBeans(historyTechnicianGroupWorkBeans);
//		worksheetBean.popSizeHistoryTechnicianGroup();
		
		//product item
//		productItemBeans = new ArrayList<ProductItemBean>();
//		for(ProductItem productItem : worksheet.getProductItems()){
//			ProductItemBean productItemBean = new ProductItemBean();
//			productItemBean.setProductTypeMatch(productItem.getProductTypeMatch());
//			if(productItem.getProductType().equals("E") || productItem.getProductType().equals("I")){
//				
//				List<ProductItemWorksheetBean> productItemWorksheetBeanList = new ArrayList<ProductItemWorksheetBean>();
//				for(ProductItemWorksheet productItemWorksheet : productItem.getProductItemWorksheets()){
//					ProductItemWorksheetBean productItemWorksheetBean = new ProductItemWorksheetBean();
//					productItemWorksheetBean.setId(productItemWorksheet.getId());
//					productItemWorksheetBean.setQuantity(productItemWorksheet.getQuantity());
//					productItemWorksheetBean.setPrice(productItemWorksheet.getPrice());
//					productItemWorksheetBean.setFree(productItemWorksheet.isFree());
//					productItemWorksheetBean.setLend(productItemWorksheet.isLend());
//					productItemWorksheetBean.setAmount(productItemWorksheet.getAmount());
//					productItemWorksheetBean.setProductTypeMatch(productItemWorksheet.getProductTypeMatch());
//					productItemWorksheetBean.setDeposit(productItemWorksheet.getDeposit());
//					if(productItem.getProductType().equals("E")){
//						if(productItemWorksheet.getEquipmentProductItem() != null){
//							
//							EquipmentProductItemBean equipmentProductItemBean = new EquipmentProductItemBean();
//							equipmentProductItemBean.setId(productItemWorksheet.getEquipmentProductItem().getId());
//							equipmentProductItemBean.setSerialNo(productItemWorksheet.getEquipmentProductItem().getSerialNo());
//							equipmentProductItemBean.setBalance(productItemWorksheet.getEquipmentProductItem().getBalance());
//							equipmentProductItemBean.setSpare(productItemWorksheet.getEquipmentProductItem().getSpare());
//							equipmentProductItemBean.setReservations(productItemWorksheet.getEquipmentProductItem().getReservations());
//							
//							//equipment product 
//							EquipmentProductBean equipmentProductBean = new EquipmentProductBean();
//							equipmentProductBean.setId(productItemWorksheet.getEquipmentProductItem().getEquipmentProduct().getId());
//							equipmentProductBean.setProductName(productItemWorksheet.getEquipmentProductItem().getEquipmentProduct().getProductName());
//							equipmentProductBean.setProductCode(productItemWorksheet.getEquipmentProductItem().getEquipmentProduct().getProductCode());
//							//set unit name
//							UnitBean unitBean = new UnitBean();
//							unitBean.setId(productItemWorksheet.getEquipmentProductItem().getEquipmentProduct().getUnit().getId());
//							unitBean.setUnitName(productItemWorksheet.getEquipmentProductItem().getEquipmentProduct().getUnit().getUnitName());
//							equipmentProductBean.setUnit(unitBean);
//							equipmentProductItemBean.setEquipmentProductBean(equipmentProductBean);
//							productItemWorksheetBean.setEquipmentProductItemBean(equipmentProductItemBean);
//							
//							RequisitionItem requisitionItem = productItemWorksheet.getRequisitionItem();
//							if(null != requisitionItem){
//								RequisitionItemBean requisitionItemBean = new RequisitionItemBean();
//								requisitionItemBean.setId(requisitionItem.getId());
//								Personnel personnel = requisitionItem.getPersonnel();
//								if(null != personnel){
//									PersonnelBean personnelBean = new PersonnelBean();
//									personnelBean.setId(personnel.getId());
//									personnelBean.setPersonnelCode(personnel.getPersonnelCode());
//									personnelBean.setFirstName(personnel.getFirstName());
//									personnelBean.setLastName(personnel.getLastName());
//									personnelBean.setNickName(personnel.getNickName());
//									requisitionItemBean.setPersonnelBean(personnelBean);
//								}
//								requisitionItemBean.setQuantity(requisitionItem.getQuantity());
//								
//								RequisitionDocumentBean requisitionDocumentBean = new RequisitionDocumentBean();
//								requisitionDocumentBean.setId(productItemWorksheet.getRequisitionItem().getRequisitionDocument().getId());
//								requisitionDocumentBean.setRequisitionDocumentCode(productItemWorksheet.getRequisitionItem().getRequisitionDocument().getRequisitionDocumentCode());
//								requisitionItemBean.setRequisitionDocumentBean(requisitionDocumentBean);
//								
//								productItemWorksheetBean.setRequisitionItemBean(requisitionItemBean);
//							}
//							productItemWorksheetBeanList.add(productItemWorksheetBean);
//						}
//						
//						productItemBean.setProductCategoryName(productItem.getEquipmentProduct().getEquipmentProductCategory().getEquipmentProductCategoryName());
//					}else if(productItem.getProductType().equals("I")){
//						if(productItemWorksheet.getInternetProductItem() != null){
//							
//							InternetProductBeanItem internetProductBeanItem = new InternetProductBeanItem();
//							internetProductBeanItem.setId(productItemWorksheet.getInternetProductItem().getId());
//							internetProductBeanItem.setUserName(productItemWorksheet.getInternetProductItem().getUserName());
//							internetProductBeanItem.setPassword(productItemWorksheet.getInternetProductItem().getPassword());
//							//internet product
//							InternetProductBean internetProductBean = new InternetProductBean();
//							internetProductBean.setMessageSource(messageSource);
//							internetProductBean.setId(productItemWorksheet.getInternetProductItem().getInternetProduct().getId());
//							internetProductBean.setProductCode(productItemWorksheet.getInternetProductItem().getInternetProduct().getProductCode());
//							internetProductBean.setProductName(productItemWorksheet.getInternetProductItem().getInternetProduct().getProductName());
//							internetProductBean.unitTypeInternet();
//							internetProductBeanItem.setInternetProductBean(internetProductBean);
//							productItemWorksheetBean.setInternetProductBeanItem(internetProductBeanItem);
//							productItemWorksheetBeanList.add(productItemWorksheetBean);
//						}
//						productItemBean.setProductCategoryName(productItem.getInternetProduct().getEquipmentProductCategory().getEquipmentProductCategoryName());
//					}
//				}
//				productItemBean.setProductItemWorksheetBeanList(productItemWorksheetBeanList);
//				
//			}else if(productItem.getProductType().equals("S")){
//				ProductOrderServiceProductController pServiceController = new ProductOrderServiceProductController();
//				pServiceController.setMessageSource(messageSource);
//				ServiceProductBean serviceProductBean = pServiceController.populateEntityToDto(productItem.getServiceProduct());
//				productItemBean.setType(productItem.getProductType());
//				productItemBean.setPrice(productItem.getPrice());
//				productItemBean.setFree(productItem.isFree());
//				productItemBean.setLend(productItem.isLend());
//				productItemBean.setAmount(productItem.getAmount());
//				productItemBean.setQuantity(productItem.getQuantity());
//				productItemBean.setServiceProductBean(serviceProductBean);
//				productItemBean.setProductCategoryName(productItem.getServiceProduct().getEquipmentProductCategory().getEquipmentProductCategoryName());
//				productItemBean.setProductTypeMatch(productItem.getProductTypeMatch());
//			}
//			
//			productItemBean.setType(productItem.getProductType());
//			productItemBean.setQuantity(productItem.getQuantity());
//			productItemBeans.add(productItemBean);
//		}
//		worksheetBean.setProductItemList(productItemBeans);
		
		//sub worksheet
//		List<SubWorksheetBean> subWorksheetBeanList = new ArrayList<SubWorksheetBean>();
//		for(SubWorksheet subWorksheet :  worksheet.getSubWorksheets()){
//			SubWorksheetBean subWorksheetBean = new SubWorksheetBean();
//			subWorksheetBean.setId(subWorksheet.getId());
//			subWorksheetBean.setWorkSheetType(subWorksheet.getWorkSheetType());
//			subWorksheetBean.setPrice(subWorksheet.getPrice());
//			subWorksheetBean.setMessageSource(messageSource);
//			subWorksheetBean.loadWorksheetTypeText();
//			subWorksheetBeanList.add(subWorksheetBean);
//		}
//		worksheetBean.setSubWorksheetBeanList(subWorksheetBeanList);
		
		return worksheetBean;
	}
	
	public WorksheetBean populateEntityToDto(Worksheet worksheet){
		WorksheetBean worksheetBean = null;
		if (worksheet.getWorkSheetType().equals(
				messageSource.getMessage("worksheet.type.cable.addpoint", null, LocaleContextHolder.getLocale()))) {
			worksheetBean = new AddPointWorksheetBean();
			AddPointWorksheetBean addPointWorksheetBean = new AddPointWorksheetBean();
			addPointWorksheetBean.setAddPointPrice(worksheet.getWorksheetAddPoint().getAddPointPrice());
			addPointWorksheetBean.setMonthlyFree(worksheet.getWorksheetAddPoint().getMonthlyFree());
			worksheetBean.setAddPointWorksheetBean(addPointWorksheetBean);			
		} else if (worksheet.getWorkSheetType().equals(
				messageSource.getMessage("worksheet.type.cable.connect", null, LocaleContextHolder.getLocale()))) {
			worksheetBean = new ConnectWorksheetBean();
			
		} else if (worksheet.getWorkSheetType().equals(
				messageSource.getMessage("worksheet.type.cable.setup", null, LocaleContextHolder.getLocale()))) {
			worksheetBean = new SetupWorksheetBean();
			
		} else if (worksheet.getWorkSheetType()
				.equals(messageSource.getMessage("worksheet.type.cable.tune", null, LocaleContextHolder.getLocale()))) {
			worksheetBean = new TuneWorksheetBean();
			
		} else if (worksheet.getWorkSheetType().equals(
				messageSource.getMessage("worksheet.type.cable.repair", null, LocaleContextHolder.getLocale()))) {
			worksheetBean = new RepairConnectionWorksheetBean();
			
		} else if (worksheet.getWorkSheetType().equals(
				messageSource.getMessage("worksheet.type.cable.addsettop", null, LocaleContextHolder.getLocale()))) {
			worksheetBean = new AddSetTopBoxWorksheetBean();
			
		} else if (worksheet.getWorkSheetType().equals(
				messageSource.getMessage("worksheet.type.cable.movepoint", null, LocaleContextHolder.getLocale()))) {
			
			worksheetBean = new MovePointWorksheetBean();
			MovePointWorksheetBean movePointWorksheetBean = new MovePointWorksheetBean();
			movePointWorksheetBean.setDigitalPoint(worksheet.getWorksheetMovePoint().getDigitalPoint());
			movePointWorksheetBean.setAnalogPoint(worksheet.getWorksheetMovePoint().getAnalogPoint());
			movePointWorksheetBean.setMovePointPrice(worksheet.getWorksheetMovePoint().getMovePointPrice());
			worksheetBean.setMovePointWorksheetBean(movePointWorksheetBean);
			
		} else if (worksheet.getWorkSheetType().equals(
				messageSource.getMessage("worksheet.type.cable.reducepoint", null, LocaleContextHolder.getLocale()))) {
			worksheetBean = new ReducePointWorksheetBean();
			
		} else if (worksheet.getWorkSheetType()
				.equals(messageSource.getMessage("worksheet.type.cable.cut", null, LocaleContextHolder.getLocale()))) {
			worksheetBean = new CutWorksheetBean();
			
		} else if (worksheet.getWorkSheetType()
				.equals(messageSource.getMessage("worksheet.type.cable.move", null, LocaleContextHolder.getLocale()))) {
			
			worksheetBean = new MoveWorksheetBean();
			MoveWorksheetBean moveWorksheetBean = new MoveWorksheetBean();
			moveWorksheetBean.setAnalogPoint(worksheet.getWorksheetMove().getAnalogPoint());
			moveWorksheetBean.setDigitalPoint(worksheet.getWorksheetMove().getDigitalPoint());
			moveWorksheetBean.setMoveCablePrice(worksheet.getWorksheetMove().getMoveCablePrice());
			worksheetBean.setMoveWorksheetBean(moveWorksheetBean);
			
		} else if (worksheet.getWorkSheetType().equals(
				messageSource.getMessage("worksheet.type.cable.borrow", null, LocaleContextHolder.getLocale()))) {
			worksheetBean = new BorrowWorksheetBean();
		} else if (worksheet.getWorkSheetType().equals(
				messageSource.getMessage("worksheet.type.internet.analyzeproblems", null, LocaleContextHolder.getLocale()))) {
			worksheetBean = new AnalyzeProblemsWorksheetBean();
			AnalyzeProblemsWorksheetBean analyzeProblemsWorksheetBean = new AnalyzeProblemsWorksheetBean();
			analyzeProblemsWorksheetBean.setMenuReportId(worksheet.getWorksheetAnalyzeProblems().getMenuReport().getId());
			worksheetBean.setAnalyzeProblemsWorksheetBean(analyzeProblemsWorksheetBean);
		}
		
		Invoice invoice = worksheet.getInvoice();
		if(null != invoice){
			InvoiceDocumentBean invoiceDocumentBean = new InvoiceDocumentBean();
			invoiceDocumentBean.setId(invoice.getId());
			invoiceDocumentBean.setStatus(invoice.getStatus());
			invoiceDocumentBean.setStatusScan(invoice.getStatusScan());
			worksheetBean.setInvoiceDocumentBean(invoiceDocumentBean);
		}
		
		worksheetBean.setIdWorksheetParent(worksheet.getId());
		worksheetBean.setWorkSheetCode(worksheet.getWorkSheetCode());
		worksheetBean.setAvailableDateTime(worksheet.getAvailableDateTime());
		
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
		worksheetBean.setRemarkSuccess(worksheet.getRemarkSuccess());
		worksheetBean.setJobDetails(worksheet.getJobDetails());
		worksheetBean.setCreateByTh(worksheet.getCreatedBy());
		
		//update date/by
		worksheetBean.setUpdateDateTh(
				null == worksheet.getUpdatedDate() ? "" : formatDataTh.format(worksheet.getUpdatedDate()));
		worksheetBean.setUpdateByTh(worksheet.getUpdatedBy());
		
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
		
		if(worksheet.getWorkSheetType().equals(messageSource.getMessage("worksheet.type.cable.setup", null, LocaleContextHolder.getLocale()))) {
			serviceApplicationBean.setEasyInstallationDateTime(worksheet.getServiceApplication().getEasyInstallationDateTime());
		}
		serviceApplicationBean.setRefund(worksheet.getServiceApplication().getRefund());
		serviceApplicationBean.setFlagRefund(worksheet.getServiceApplication().isFlagRefund());
		
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
		servicePackageBean.setMonthlyService(worksheet.getServiceApplication().getServicePackage().isMonthlyService());
		ServicePackageTypeBean servicePackageTypeBean = new ServicePackageTypeBean();
		servicePackageTypeBean.setId(worksheet.getServiceApplication().getServicePackage().getServicePackageType().getId());
		servicePackageTypeBean.setPackageTypeName(worksheet.getServiceApplication().getServicePackage().getServicePackageType().getPackageTypeName());
		servicePackageBean.setServiceType(servicePackageTypeBean);
		serviceApplicationBean.setServicepackage(servicePackageBean);
		
		//reference service application
		if(worksheet.getServiceApplication().getReferenceServiceApplicationId()!=null){
			ServiceApplicationBean serviceApplicationRefBean = new ServiceApplicationBean();
			serviceApplicationRefBean.setId(worksheet.getServiceApplication().getReferenceServiceApplicationId());
			serviceApplicationBean.setReferenceServiceApplicationBean(serviceApplicationRefBean);
		}else{
			serviceApplicationBean.setReferenceServiceApplicationBean(null);
		}
		
		
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
//			serviceApp.setId(address.getServiceApplication().getId());
			serviceApp.setId(worksheet.getServiceApplication().getId());
			
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
			
			Boolean checkWorksheet = true;
			if(checkWorksheet){
			ProductItemBean productItemBean = new ProductItemBean();
			productItemBean.setId(productItem.getId());
			productItemBean.setType(productItem.getProductType());
			productItemBean.setQuantity(productItem.getQuantity());
			productItemBean.setFree(productItem.isFree());
			productItemBean.setLend(productItem.isLend());
			productItemBean.setAmount(productItem.getAmount());
			productItemBean.setPrice(productItem.getPrice());
			productItemBean.setProductTypeMatch(productItem.getProductTypeMatch());

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
					productItemWorksheetBean.setProductTypeMatch(productItemWorksheet.getProductTypeMatch());
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
				if(null != personnel){
					personnelBean.setId(personnel.getId());
					personnelBean.setFirstName(personnel.getFirstName());
					personnelBean.setLastName(personnel.getLastName());
				}
				technicianGroupBean.setTechnicianGroupName(technicianGroup.getTechnicianGroupName());
				technicianGroupBean.setPersonnel(personnelBean);
				
				historyTechnicianGroupWorkBean.setTechnicainGroup(technicianGroupBean);
			}
			historyTechnicianGroupWorkBean.setRemarkNotSuccess(historyTechnicianGroupWork.getRemarkNotSuccess());
			historyTechnicianGroupWorkBean.setStatusHistory(historyTechnicianGroupWork.getStatusHistory());
			historyTechnicianGroupWorkBean.setAssingCurrentDate(historyTechnicianGroupWork.getAssignDate());
			
			//technician group
			TechnicianGroupBean technicainGroup = new TechnicianGroupBean();
			if(null != historyTechnicianGroupWork.getTechnicianGroup()){
			technicainGroup.setId(historyTechnicianGroupWork.getTechnicianGroup().getId());
			technicainGroup.setTechnicianGroupName(historyTechnicianGroupWork.getTechnicianGroup().getTechnicianGroupName());
			}
			//lead technicain group
			PersonnelBean leaderPersonnel = new PersonnelBean();
			if(null != historyTechnicianGroupWork.getTechnicianGroup() && null != historyTechnicianGroupWork.getTechnicianGroup().getPersonnel()){
			leaderPersonnel.setId(historyTechnicianGroupWork.getTechnicianGroup().getPersonnel().getId());
			leaderPersonnel.setFirstName(historyTechnicianGroupWork.getTechnicianGroup().getPersonnel().getFirstName());
			leaderPersonnel.setLastName(historyTechnicianGroupWork.getTechnicianGroup().getPersonnel().getLastName());
			}
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
			productItemBean.setProductTypeMatch(productItem.getProductTypeMatch());
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
							equipmentProductItemBean.setBalance(productItemWorksheet.getEquipmentProductItem().getBalance());
							equipmentProductItemBean.setSpare(productItemWorksheet.getEquipmentProductItem().getSpare());
							equipmentProductItemBean.setReservations(productItemWorksheet.getEquipmentProductItem().getReservations());
							
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
							
							RequisitionItem requisitionItem = productItemWorksheet.getRequisitionItem();
							if(null != requisitionItem){
								RequisitionItemBean requisitionItemBean = new RequisitionItemBean();
								requisitionItemBean.setId(requisitionItem.getId());
								Personnel personnel = requisitionItem.getPersonnel();
								if(null != personnel){
									PersonnelBean personnelBean = new PersonnelBean();
									personnelBean.setId(personnel.getId());
									personnelBean.setPersonnelCode(personnel.getPersonnelCode());
									personnelBean.setFirstName(personnel.getFirstName());
									personnelBean.setLastName(personnel.getLastName());
									personnelBean.setNickName(personnel.getNickName());
									requisitionItemBean.setPersonnelBean(personnelBean);
								}
								requisitionItemBean.setQuantity(requisitionItem.getQuantity());
								
								RequisitionDocumentBean requisitionDocumentBean = new RequisitionDocumentBean();
								requisitionDocumentBean.setId(productItemWorksheet.getRequisitionItem().getRequisitionDocument().getId());
								requisitionDocumentBean.setRequisitionDocumentCode(productItemWorksheet.getRequisitionItem().getRequisitionDocument().getRequisitionDocumentCode());
								requisitionItemBean.setRequisitionDocumentBean(requisitionDocumentBean);
								
								productItemWorksheetBean.setRequisitionItemBean(requisitionItemBean);
							}
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
			subWorksheetBean.setRemark(subWorksheet.getRemark());
			subWorksheetBean.setPrice(subWorksheet.getPrice());
			subWorksheetBean.setMessageSource(messageSource);
			subWorksheetBean.loadWorksheetTypeText();
			
			if(StringUtils.isBlank(subWorksheetBean.getWorkSheetTypeText()) && StringUtils.isNotBlank(subWorksheet.getWorkSheetType())){
				subWorksheetBean = new SubWorksheetBean();
				subWorksheetBean.setId(subWorksheet.getId());
				MenuReport menuReport = unitService.getMenuReportById(Long.valueOf(subWorksheet.getWorkSheetType()));
				String workSheetType = "-";
				if(null != menuReport){
					workSheetType = menuReport.getMenuReportName();
				}
				subWorksheetBean.setWorkSheetTypeText(workSheetType);
				subWorksheetBean.setRemark(subWorksheet.getRemark());
				subWorksheetBean.setPrice(subWorksheet.getPrice());
				subWorksheetBean.setMessageSource(messageSource);
			}
			
			subWorksheetBeanList.add(subWorksheetBean);
		}
		
		worksheetBean.setSubWorksheetBeanList(subWorksheetBeanList);
		
		return worksheetBean;
	}
	
	//getter and setter
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	public void setZoneService(ZoneService zoneService) {
		this.zoneService = zoneService;
	}
	public void setTechnicianGroupService(TechnicianGroupService technicianGroupService) {
		this.technicianGroupService = technicianGroupService;
	}
	public void setWorkSheetService(WorkSheetService workSheetService) {
		this.workSheetService = workSheetService;
	}
	public void setHistoryTechnicianGroupWorkService(HistoryTechnicianGroupWorkService historyTechnicianGroupWorkService) {
		this.historyTechnicianGroupWorkService = historyTechnicianGroupWorkService;
	}

	public UnitService getUnitService() {
		return unitService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}	
	
}
