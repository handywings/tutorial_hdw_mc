package com.hdw.mccable.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.jfree.util.Log;
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
import com.hdw.mccable.dto.BorrowWorksheetBean;
import com.hdw.mccable.dto.CompanyBean;
import com.hdw.mccable.dto.ConnectWorksheetBean;
import com.hdw.mccable.dto.CutWorksheetBean;
import com.hdw.mccable.dto.DistrictBean;
import com.hdw.mccable.dto.EquipmentProductBean;
import com.hdw.mccable.dto.EquipmentProductCategoryBean;
import com.hdw.mccable.dto.EquipmentProductItemBean;
import com.hdw.mccable.dto.InternetProductBean;
import com.hdw.mccable.dto.InternetProductBeanItem;
import com.hdw.mccable.dto.InvoiceDocumentBean;
import com.hdw.mccable.dto.JsonResponse;
import com.hdw.mccable.dto.MenuReportBean;
import com.hdw.mccable.dto.MovePointWorksheetBean;
import com.hdw.mccable.dto.MoveWorksheetBean;
import com.hdw.mccable.dto.PersonnelBean;
import com.hdw.mccable.dto.ProductBean;
import com.hdw.mccable.dto.ProductItemBean;
import com.hdw.mccable.dto.ProductItemWorksheetBean;
import com.hdw.mccable.dto.ProvinceBean;
import com.hdw.mccable.dto.ReducePointWorksheetBean;
import com.hdw.mccable.dto.RepairConnectionWorksheetBean;
import com.hdw.mccable.dto.RepairMatchItemBean;
import com.hdw.mccable.dto.RequisitionDocumentBean;
import com.hdw.mccable.dto.RequisitionItemBean;
import com.hdw.mccable.dto.SearchAllProductBean;
import com.hdw.mccable.dto.ServiceProductBean;
import com.hdw.mccable.dto.StockBean;
import com.hdw.mccable.dto.SubWorksheetBean;
import com.hdw.mccable.dto.TechnicianGroupBean;
import com.hdw.mccable.dto.TuneWorksheetBean;
import com.hdw.mccable.dto.UnitBean;
import com.hdw.mccable.dto.WorksheetBean;
import com.hdw.mccable.dto.WorksheetSearchBean;
import com.hdw.mccable.dto.WorksheetUpdateSnapShotBean;
import com.hdw.mccable.dto.ZoneBean;
import com.hdw.mccable.entity.Address;
import com.hdw.mccable.entity.Amphur;
import com.hdw.mccable.entity.Company;
import com.hdw.mccable.entity.Customer;
import com.hdw.mccable.entity.District;
import com.hdw.mccable.entity.EquipmentProduct;
import com.hdw.mccable.entity.EquipmentProductCategory;
import com.hdw.mccable.entity.EquipmentProductItem;
import com.hdw.mccable.entity.HistoryRepair;
import com.hdw.mccable.entity.HistoryTechnicianGroupWork;
import com.hdw.mccable.entity.HistoryUpdateStatus;
import com.hdw.mccable.entity.HistoryUseEquipment;
import com.hdw.mccable.entity.InternetProduct;
import com.hdw.mccable.entity.InternetProductItem;
import com.hdw.mccable.entity.Invoice;
import com.hdw.mccable.entity.MenuReport;
import com.hdw.mccable.entity.Personnel;
import com.hdw.mccable.entity.PersonnelAssign;
import com.hdw.mccable.entity.ProductItem;
import com.hdw.mccable.entity.ProductItemWorksheet;
import com.hdw.mccable.entity.Province;
import com.hdw.mccable.entity.Receipt;
import com.hdw.mccable.entity.RequisitionItem;
import com.hdw.mccable.entity.ServiceApplication;
import com.hdw.mccable.entity.ServiceProduct;
import com.hdw.mccable.entity.Stock;
import com.hdw.mccable.entity.SubWorksheet;
import com.hdw.mccable.entity.TechnicianGroup;
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
import com.hdw.mccable.entity.WorksheetTune;
import com.hdw.mccable.entity.Zone;
import com.hdw.mccable.service.AddressService;
import com.hdw.mccable.service.CompanyService;
import com.hdw.mccable.service.CustomerService;
import com.hdw.mccable.service.EquipmentProductCategoryService;
import com.hdw.mccable.service.EquipmentProductItemService;
import com.hdw.mccable.service.EquipmentProductService;
import com.hdw.mccable.service.FinancialService;
import com.hdw.mccable.service.HistoryTechnicianGroupWorkService;
import com.hdw.mccable.service.HistoryUseEquipmentService;
import com.hdw.mccable.service.InternetProductItemService;
import com.hdw.mccable.service.PersonnelService;
import com.hdw.mccable.service.ProductItemService;
import com.hdw.mccable.service.ProductItemWorksheetService;
import com.hdw.mccable.service.ProductService;
import com.hdw.mccable.service.RequisitionDocumentService;
import com.hdw.mccable.service.RequisitionItemService;
import com.hdw.mccable.service.ServiceApplicationService;
import com.hdw.mccable.service.ServiceProductService;
import com.hdw.mccable.service.StockService;
import com.hdw.mccable.service.TechnicianGroupService;
import com.hdw.mccable.service.UnitService;
import com.hdw.mccable.service.WorkSheetService;
import com.hdw.mccable.service.ZoneService;
import com.hdw.mccable.utils.AlertUtil;
import com.hdw.mccable.utils.DateUtil;
import com.hdw.mccable.utils.Pagination;
import com.hdw.mccable.utils.ProductUtil;

@Controller
@RequestMapping("/worksheetlist")
public class WorkSheetListController extends BaseController{
	
	final static Logger logger = Logger.getLogger(WorkSheetListController.class);
	public static final String CONTROLLER_NAME = "worksheetlist/";
	public static final String pageTypeCable = "cable";
	public static final String pageTypeInternet = "internet";
	public static final String shortTypeCable = "C";
	public static final String shortTypeInternet = "I";
	
	public static final String TYPE_EQUIMENT = "E";
	public static final String TYPE_INTERNET_USER = "I";
	public static final String TYPE_SERVICE = "S";
	
	//initial service
		@Autowired
		private MessageSource messageSource;
		
		@Autowired(required = true)
		@Qualifier(value = "serviceApplicationService")
		private ServiceApplicationService serviceApplicationService;
		
		@Autowired(required = true)
		@Qualifier(value = "workSheetService")
		private WorkSheetService workSheetService;
		
		@Autowired(required = true)
		@Qualifier(value = "serviceProductService")
		private ServiceProductService serviceProductService;
		
		@Autowired(required = true)
		@Qualifier(value = "productService")
		private ProductService productService;
		
		@Autowired(required = true)
		@Qualifier(value = "equipmentProductCategoryService")
		private EquipmentProductCategoryService equipmentProductCategoryService;
		
		@Autowired(required = true)
		@Qualifier(value = "stockService")
		private StockService stockService;
		
		@Autowired(required = true)
		@Qualifier(value = "equipmentProductService")
		private EquipmentProductService equipmentProductService;
		
		@Autowired(required = true)
		@Qualifier(value = "equipmentProductItemService")
		private EquipmentProductItemService equipmentProductItemService;

		@Autowired(required = true)
		@Qualifier(value = "productItemService")
		private ProductItemService productItemService;

		@Autowired(required = true)
		@Qualifier(value = "zoneService")
		private ZoneService zoneService;
		
		@Autowired(required = true)
		@Qualifier(value = "addressService")
		private AddressService addressService;
		
		@Autowired(required = true)
		@Qualifier(value = "technicianGroupService")
		private TechnicianGroupService technicianGroupService;
		
		@Autowired(required = true)
		@Qualifier(value = "personnelService")
		private PersonnelService personnelService;
		
		@Autowired(required = true)
		@Qualifier(value = "historyTechnicianGroupWorkService")
		private HistoryTechnicianGroupWorkService historyTechnicianGroupWorkService;

		@Autowired(required = true)
		@Qualifier(value = "internetProductItemService")
		private InternetProductItemService internetProductItemService;
		
		@Autowired(required = true)
		@Qualifier(value = "financialService")
		private FinancialService financialService;
		
		@Autowired(required = true)
		@Qualifier(value = "productItemWorksheetService")
		private ProductItemWorksheetService productItemWorksheetService;
		
		@Autowired(required = true)
		@Qualifier(value = "customerService")
		private CustomerService customerService;
		
		@Autowired(required = true)
		@Qualifier(value = "historyUseEquipmentService")
		private HistoryUseEquipmentService historyUseEquipmentService;
		
		@Autowired(required = true)
		@Qualifier(value = "requisitionDocumentService")
		private RequisitionDocumentService requisitionDocumentService;
		
		@Autowired(required = true)
		@Qualifier(value = "requisitionItemService")
		private RequisitionItemService requisitionItemService;
		
		@Autowired(required=true)
		@Qualifier(value="companyService")
		private CompanyService companyService;	
		
		@Autowired(required=true)
		@Qualifier(value="unitService")
		private UnitService unitService;
		
		//end initial service
		
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
				//init load
				//zone
				List<Zone> zones = zoneService.findAll();
				List<ZoneBean> zoneBeans = new ArrayList<ZoneBean>();
				ZoneController zoneController = new ZoneController();
				for(Zone zone : zones){
					ZoneBean zoneBean = zoneController.populateEntityToDto(zone);
					zoneBeans.add(zoneBean);
				}
				modelAndView.addObject("zoneBeans", zoneBeans);
				WorksheetSearchBean worksheetSearchBean = new WorksheetSearchBean();
				//init paging

				Pagination pagination = createPagination(1, 10, "All", "worksheetlist", worksheetSearchBean);
				
				worksheetSearchBean.setStatus("W");
				Pagination pagination_w = createPagination(1, 10, "All", "worksheetlist", worksheetSearchBean);
				
				worksheetSearchBean.setStatus("R");
				Pagination pagination_r = createPagination(1, 10, "All", "worksheetlist", worksheetSearchBean);
				
				worksheetSearchBean.setStatus("H");
				Pagination pagination_h = createPagination(1, 10, "All", "worksheetlist", worksheetSearchBean);
				
				worksheetSearchBean.setStatus("D");
				Pagination pagination_c = createPagination(1, 10, "All", "worksheetlist", worksheetSearchBean);
				
				worksheetSearchBean.setStatus("S");
				Pagination pagination_s = createPagination(1, 10, "All", "worksheetlist", worksheetSearchBean);
				
				modelAndView.addObject("worksheetSearchBean",worksheetSearchBean);
				modelAndView.addObject("pagination",pagination);
				modelAndView.addObject("pagination_w",pagination_w);
				modelAndView.addObject("pagination_r",pagination_r);
				modelAndView.addObject("pagination_h",pagination_h);
				modelAndView.addObject("pagination_c",pagination_c);
				modelAndView.addObject("pagination_s",pagination_s);
				
				
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
	
	// create pagination
	@SuppressWarnings("unchecked")
	Pagination createPagination(int currentPage, int itemPerPage, String tab, String controller,
			WorksheetSearchBean worksheetSearchBean) {
		if (itemPerPage == 0) itemPerPage = 10;
		if("".equals(tab)) tab = "All";
		worksheetSearchBean.setTab(tab);
		Pagination pagination = new Pagination();
		pagination.setTotalItem(workSheetService.getCountTotal(worksheetSearchBean));
		pagination.setCurrentPage(currentPage);
		pagination.setItemPerPage(itemPerPage);
		pagination.setUrl(controller);
		pagination = workSheetService.getByPageForWorksheet(pagination, worksheetSearchBean);
		List<Worksheet> worksheetBeansValidateSize = (List<Worksheet>) pagination.getDataList();

		if (worksheetBeansValidateSize.size() <= 0) {
			pagination.setCurrentPage(1);
			pagination = workSheetService.getByPageForWorksheet(pagination, worksheetSearchBean);
		}
		// poppulate
		AssignWorksheetController assignWorksheetController = new AssignWorksheetController();
		assignWorksheetController.setMessageSource(messageSource);
		List<WorksheetBean> worksheetBeans = new ArrayList<WorksheetBean>();
		for (Worksheet Worksheet : (List<Worksheet>) pagination.getDataList()) {
			worksheetBeans.add(assignWorksheetController.populateEntityToDtoInit(Worksheet));
		}
		pagination.setDataListBean(worksheetBeans);
		return pagination;
	}
		
//	Pagination compareStatus(Pagination pagination, String status){
//		Pagination pagination_final = new Pagination();
//		
//		pagination_final.setCurrentPage(pagination.getCurrentPage());
//		pagination_final.setItemPerPage(pagination.getItemPerPage());
//		pagination_final.setUrl(pagination.getUrl());
//		
//		List<WorksheetBean> worksheetBean = new ArrayList<WorksheetBean>();
//		if(null != pagination && null != pagination.getDataListBean()){
//			for(WorksheetBean bean:(List<WorksheetBean>)pagination.getDataListBean()){
//				if(status.equals(bean.getStatus().getStringValue())){
//					worksheetBean.add(bean);
//				}
//			}
//			pagination_final.setDataListBean(worksheetBean);
//			pagination_final.setTotalItem(worksheetBean.size());
//		}
//		return pagination_final;
//	}


	@RequestMapping(value = "page/{id}/itemPerPage/{itemPerPage}/tab/{tab}", method = RequestMethod.GET)
	public ModelAndView pagination(@PathVariable int id, @PathVariable int itemPerPage, 
			@PathVariable String tab, Model model,
			HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : pagination][Type : Controller]");
		logger.info("[method : pagination][itemPerPage : " + itemPerPage + "]");
		ModelAndView modelAndView = new ModelAndView();
		ZoneController zoneController = new ZoneController();
//		TechnicianGroupController technicianGroupController = new TechnicianGroupController();
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
				
				// search worksheet bean
				WorksheetSearchBean worksheetSearchBean = (WorksheetSearchBean) session
						.getAttribute("worksheetSearchBean");
				// set value search worksheet
				if (worksheetSearchBean != null) {
					modelAndView.addObject("worksheetSearchBean", worksheetSearchBean);
				} else {
					modelAndView.addObject("worksheetSearchBean", new WorksheetSearchBean());
				}
				
				//search process and pagination
				Pagination pagination = null;
				Pagination pagination_w = null;
				Pagination pagination_r = null;
				Pagination pagination_h = null;
				Pagination pagination_c = null;
				Pagination pagination_s = null;
				
				if(worksheetSearchBean != null){
					
					pagination = createPagination(id, itemPerPage, tab, "worksheetlist",worksheetSearchBean);
					
					worksheetSearchBean.setStatus("W");
					pagination_w = createPagination(id, itemPerPage, tab, "worksheetlist", worksheetSearchBean);
					
					worksheetSearchBean.setStatus("R");
					pagination_r = createPagination(id, itemPerPage, tab, "worksheetlist", worksheetSearchBean);
					
					worksheetSearchBean.setStatus("H");
					pagination_h = createPagination(id, itemPerPage, tab, "worksheetlist", worksheetSearchBean);
					
					worksheetSearchBean.setStatus("D");
					pagination_c = createPagination(id, itemPerPage, tab, "worksheetlist", worksheetSearchBean);
					
					worksheetSearchBean.setStatus("S");
					pagination_s = createPagination(id, itemPerPage, tab, "worksheetlist", worksheetSearchBean);
					
				}else{
					worksheetSearchBean = new WorksheetSearchBean();
					
					pagination = createPagination(id, itemPerPage, tab, "worksheetlist", worksheetSearchBean);
					

					worksheetSearchBean.setStatus("W");
					pagination_w = createPagination(id, itemPerPage, tab, "worksheetlist", worksheetSearchBean);
					
					worksheetSearchBean.setStatus("R");
					pagination_r = createPagination(id, itemPerPage, tab, "worksheetlist", worksheetSearchBean);
					
					worksheetSearchBean.setStatus("H");
					pagination_h = createPagination(id, itemPerPage, tab, "worksheetlist", worksheetSearchBean);
					
					worksheetSearchBean.setStatus("D");
					pagination_c = createPagination(id, itemPerPage, tab, "worksheetlist", worksheetSearchBean);
					
					worksheetSearchBean.setStatus("S");
					pagination_s = createPagination(id, itemPerPage, tab, "worksheetlist", worksheetSearchBean);
					
				}
				
				modelAndView.addObject("worksheetSearchBean",worksheetSearchBean);
				modelAndView.addObject("pagination",pagination);
				modelAndView.addObject("pagination_w",pagination_w);
				modelAndView.addObject("pagination_r",pagination_r);
				modelAndView.addObject("pagination_h",pagination_h);
				modelAndView.addObject("pagination_c",pagination_c);
				modelAndView.addObject("pagination_s",pagination_s);
				
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
	public ModelAndView searchWorksheet(
			@ModelAttribute("worksheetSearchBean") WorksheetSearchBean worksheetSearchBean,
			HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {

		logger.info("[method : searchWorksheet][Type : Controller]");
		logger.info("[method : searchWorksheet][worksheetSearchBean : " + worksheetSearchBean.toString()
				+ "]");
		ModelAndView modelAndView = new ModelAndView();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			generateSearchSession(worksheetSearchBean, request);
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		modelAndView.setViewName(REDIRECT + "/worksheetlist/page/1/itemPerPage/10/tab/"+worksheetSearchBean.getTab());
		return modelAndView;
	}
	
	// create search session
	public void generateSearchSession(WorksheetSearchBean worksheetSearchBean, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("worksheetSearchBean", worksheetSearchBean);
	}
	
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public ModelAndView detail(@PathVariable Long id, Model model,
			HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : detail][Type : Controller]");
		logger.info("[method : detail][id : " + id + "]");
		
		ModelAndView modelAndView = new ModelAndView();
		EquipmentProductCategoryController epcController = new EquipmentProductCategoryController();
		ProductAddController productAddController = new ProductAddController();
		productAddController.setProductService(productService);
		productAddController.setMessageSource(messageSource);
		
		List<TechnicianGroupBean> technicianGroupBeans = new ArrayList<TechnicianGroupBean>();
		
		// get current session
		HttpSession session = request.getSession();
		String configPage = "";
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			try {
				//call service
				List<TechnicianGroup> technicianGroups = technicianGroupService.findAll();
				if(null != technicianGroups && !technicianGroups.isEmpty()){
					TechnicianGroupController technicianGroupController = new TechnicianGroupController();
					for(TechnicianGroup technicianGroup:technicianGroups){
						technicianGroupBeans.add(technicianGroupController.populateEntityToDto(technicianGroup));
					}
				}
				// dropdown technicianGroup
				modelAndView.addObject("technicianGroupBeans", technicianGroupBeans);
				
				// dropdown equipmentCategory for search only
				List<EquipmentProductCategoryBean> epcSearchBeans = new ArrayList<EquipmentProductCategoryBean>();
				List<EquipmentProductCategory> epcSearchs = equipmentProductCategoryService.findAll();
				if(null != epcSearchs && !epcSearchs.isEmpty()){
					for (EquipmentProductCategory epcSearch : epcSearchs) {
						EquipmentProductCategoryBean epcSearchBean = new EquipmentProductCategoryBean();
						if(!"00002".equals(epcSearch.getEquipmentProductCategoryCode())){
							epcSearchBean = epcController.populateEntityToDto(epcSearch);
							epcSearchBeans.add(epcSearchBean);
						}
						
					}
				}
				modelAndView.addObject("epcSearchBeans", epcSearchBeans);
				
				// load equipment product
				List<ProductBean> prodSearchs = productAddController.loadEquipmentProductAndServiceProduct(new EquipmentProductBean());
				modelAndView.addObject("prodSearchs", prodSearchs);
				
				// dropdown stock
				List<StockBean> stockBeans = loadStockList();
				modelAndView.addObject("stockBeans", stockBeans);
				
				Worksheet worksheet = workSheetService.getWorksheetById(id);
				if(worksheet != null){
					configPage = loadConfigPage(worksheet.getWorkSheetType());
					WorksheetBean worksheetBean = loadWorksheetBean(worksheet);
					
					//current bill
					  List<InvoiceDocumentBean> invoiceCurrentBill = new ArrayList<InvoiceDocumentBean>();
					  List<InvoiceDocumentBean> invoiceOverBill = new ArrayList<InvoiceDocumentBean>();
					  
					  InvoiceController invController = new InvoiceController();
					  invController.setMessageSource(messageSource);
					  invController.setUnitService(unitService);
					  for(Invoice invoice : worksheet.getServiceApplication().getInvoices()){
						  if(invoice.getCreateDate() != null) {
							  //InvoiceDocumentBean invoiceDocumentBean = new InvoiceDocumentBean();
							  if(invoice.getStatus().equals("W")){
								  invoiceCurrentBill.add(invController.PoppulateInvoiceEntityToDto(invoice));
								  
							  }else if(invoice.getStatus().equals("O")){
								  invoiceOverBill.add(invController.PoppulateInvoiceEntityToDto(invoice));
							  }
						  }						  
					  }
					  worksheetBean.getServiceApplication().setInvoiceCurrentBill(invoiceCurrentBill);
					  worksheetBean.getServiceApplication().setInvoiceOverBill(invoiceOverBill);
					  worksheetBean.setCreateBy(worksheet.getCreatedBy());
					  	
					modelAndView.addObject("worksheetBean", worksheetBean);
					//load member in team
					if(worksheet.getHistoryTechnicianGroupWorks() != null && worksheet.getHistoryTechnicianGroupWorks().size() > 0){
						List<PersonnelAssign> personnelAssigns = worksheet.getHistoryTechnicianGroupWorks()
								.get(worksheet.getHistoryTechnicianGroupWorks().size()-1).getPersonnelAssigns();
						
						List<PersonnelBean> personnelBeans = new ArrayList<PersonnelBean>();
						PersonnelController personelController = new PersonnelController();
						personelController.setMessageSource(messageSource);
						for(PersonnelAssign personnelAssign : personnelAssigns){
							Personnel personnel = personnelService.getPersonnelById(personnelAssign.getPersonnelId());
							PersonnelBean personnelBean = personelController.populateEntityToDto(personnel);
							personnelBeans.add(personnelBean);
						}
						modelAndView.addObject("personnelBeans", personnelBeans);
						
						//technician
						if(null != worksheet.getHistoryTechnicianGroupWorks() &&
								null != worksheet.getHistoryTechnicianGroupWorks().get(worksheet.getHistoryTechnicianGroupWorks().size()-1).getTechnicianGroup()){
							Long currentTechnicainGroupId = worksheet.getHistoryTechnicianGroupWorks()
							.get(worksheet.getHistoryTechnicianGroupWorks().size()-1)
							.getTechnicianGroup().getId();
							modelAndView.addObject("currentTechnicainGroupId", currentTechnicainGroupId);
						}
						//status current history
						String statusCurrentHistory = worksheet.getHistoryTechnicianGroupWorks()
						.get(worksheet.getHistoryTechnicianGroupWorks().size()-1).getStatusHistory();
						modelAndView.addObject("statusCurrentHistory", statusCurrentHistory);
						
						//current remark not success
						String remarkNotSuccess = worksheet.getHistoryTechnicianGroupWorks()
								.get(worksheet.getHistoryTechnicianGroupWorks().size()-1).getRemarkNotSuccess();
						
						if(remarkNotSuccess == null){
							remarkNotSuccess = "";
						}
						modelAndView.addObject("remarkNotSuccess", remarkNotSuccess);
					}else{
						modelAndView.addObject("statusCurrentHistory", "");
					}
					
					// load equipment product
					List<EquipmentProductBean> epbSearchs = productAddController
							.loadEquipmentProductNotSN(new EquipmentProductBean());
					modelAndView.addObject("epbSearchs", epbSearchs);
					
					ServicePackageController spc = new ServicePackageController();
					spc.setMessageSource(messageSource);
					spc.setProductService(productService);
					spc.setServiceProductService(serviceProductService);
					List<ProductBean> productBeans = spc.searchProductService(new SearchAllProductBean());
					modelAndView.addObject("productBeans", productBeans);
					
					List<ProvinceBean> provinceBeans = new ArrayList<ProvinceBean>();
					List<Province> provinces = addressService.findAll();
					if(null != provinces && !provinces.isEmpty()){
						ServiceApplicationController s = new ServiceApplicationController();
						for(Province province:provinces){
							provinceBeans.add(s.populateEntityToDto(province));
						}
					}
					modelAndView.addObject("provinces", provinceBeans);
					
					List<ZoneBean> zoneBeans = new ArrayList<ZoneBean>();
					List<Zone> zones = zoneService.findAll();
					if(null != zones && !zones.isEmpty()){
						ZoneController zoneController = new ZoneController();
						for(Zone zone:zones){
							zoneBeans.add(zoneController.populateEntityToDto(zone));
						}
					}
					modelAndView.addObject("zones", zoneBeans);

					List<MenuReportBean> menuReportBeanList = new ArrayList<MenuReportBean>();
					List<MenuReport> menuReports = unitService.findAllMenuReport();
					if(null != menuReports){
						SimpleDateFormat formatDataTh = new SimpleDateFormat(messageSource.getMessage("date.format.type1", null, LocaleContextHolder.getLocale()), new Locale( "TH" , "th" ));
						for(MenuReport menuReport:menuReports){
							MenuReportBean menuReportBean = new MenuReportBean();
							menuReportBean.setId(menuReport.getId());
							menuReportBean.setMenuReportCode(menuReport.getMenuReportCode());
							menuReportBean.setMenuReportName(menuReport.getMenuReportName());
							menuReportBean.setCreateBy(menuReport.getCreatedBy());
							menuReportBean.setCreateDate(menuReport.getCreateDate());
							menuReportBean.setCreateDateTh(null==menuReport.getCreateDate()?"":formatDataTh.format(menuReport.getCreateDate()));
							menuReportBean.setUpdateBy(menuReport.getUpdatedBy());
							menuReportBean.setUpdateDate(menuReport.getUpdatedDate());
							menuReportBean.setUpdateDateTh(null==menuReport.getUpdatedDate()?"":formatDataTh.format(menuReport.getUpdatedDate()));
							menuReportBeanList.add(menuReportBean);
						}
					}
					modelAndView.addObject("menuReportBeanList", menuReportBeanList);
					
				}else{
					
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

		// remove session
		session.removeAttribute("alert");

		List<Company> companys = companyService.findAll();
		List<CompanyBean> companyList = new ArrayList<CompanyBean>();
		if(companys != null){
			for(Company company : companys){
				CompanyBean companyBean = new CompanyBean();
				companyBean.setId(company.getId());
				companyBean.setCompanyName(company.getCompanyName());
				companyList.add(companyBean);
			}
		}
		modelAndView.addObject("companyList", companyList);
		
		modelAndView.setViewName(CONTROLLER_NAME + configPage);
		return modelAndView;
	}
	
	@RequestMapping(value = "loadPersonnelTeam/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonResponse loadPersonnelTeam(@PathVariable String id, HttpServletRequest request) {
		logger.info("[method : loadPersonnelTeam][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				HistoryTechnicianGroupWork historyTechnicianGroupWork = workSheetService.findHistoryTechnicianGroupWork(Long.valueOf(id));
				if (historyTechnicianGroupWork != null) {
					//load personnel
					PersonnelController personelController = new PersonnelController();
					personelController.setMessageSource(messageSource);
					List<PersonnelBean> personnelBeans = new ArrayList<PersonnelBean>();
					for(PersonnelAssign personnelAssign : historyTechnicianGroupWork.getPersonnelAssigns()){
						Personnel personnel = personnelService.getPersonnelById(personnelAssign.getPersonnelId());
						PersonnelBean personnelBean = personelController.populateEntityToDto(personnel);
						personnelBeans.add(personnelBean);
					}
					jsonResponse.setResult(personnelBeans);
					jsonResponse.setMessage(historyTechnicianGroupWork.getRemarkNotSuccess());
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

	// updateJobDetails
	@RequestMapping(value = "updateJobDetails", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse updateJobDetails(@RequestBody final WorksheetUpdateSnapShotBean worksheetUpdateSnapShotBean,
			HttpServletRequest request) {
		logger.info("[method : updateJobDetails][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				Worksheet worksheet = workSheetService.getWorksheetById(worksheetUpdateSnapShotBean.getIdWorksheetParent());
				if (worksheet != null) {
					worksheet.setJobDetails(worksheetUpdateSnapShotBean.getJobDetails());
					workSheetService.update(worksheet);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				jsonResponse.setMessage(ex.getMessage());
				jsonResponse.setError(true);
			}
		}else{
			jsonResponse.setError(true);
		}
		return jsonResponse;
	}
	
	// udpate worksheet snapshot
	@RequestMapping(value = "updateSnapshotWorksheet", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse updateSnapshotWorksheet(
			@RequestBody final WorksheetUpdateSnapShotBean worksheetUpdateSnapShotBean,HttpServletRequest request) {
		logger.info("[method : updateSnapshotWorksheet][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			// create timestamp
//			System.out.println(worksheetUpdateSnapShotBean.getSetupWorksheetBean().getDateStartBill());
			try {
				boolean success = updateSeperateWorksheetType(worksheetUpdateSnapShotBean);
				if(success){
					Worksheet worksheet = workSheetService.getWorksheetById(worksheetUpdateSnapShotBean.getIdWorksheetParent());
					//update invoice
					if(worksheet != null){
						InvoiceController invoiceController = new InvoiceController();
						invoiceController.setMessageSource(messageSource);
						invoiceController.setFinancialService(financialService);
						invoiceController.setWorkSheetService(workSheetService);
						invoiceController.updateAmountInvoiceTypeWorksheet(worksheet.getId());
					}
					jsonResponse.setResult(worksheet.getInvoice().getId());
				}
				jsonResponse.setError(!success);
			} catch (Exception ex) {
				ex.printStackTrace();
				jsonResponse.setMessage(ex.getMessage());
				jsonResponse.setError(true);
			}
		} else {
			jsonResponse.setError(true);
		}

		generateAlert(jsonResponse, request,
				messageSource.getMessage("snapshot.transaction.title.success", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("snapshot.transaction.success", null,
						LocaleContextHolder.getLocale()));
		
		return jsonResponse;
	}
	
	//----------------------------------- none request method ------------------------------------------//
	
		//load worksheet
		public WorksheetBean loadWorksheetBean(Worksheet worksheet){
			String workSheetType = worksheet.getWorkSheetType();
			WorksheetBean worksheetBean = null;
			AssignWorksheetController assignWorksheetController = new AssignWorksheetController();
			assignWorksheetController.setMessageSource(messageSource);
			assignWorksheetController.setUnitService(unitService);
			worksheetBean = assignWorksheetController.populateEntityToDto(worksheet);
			//for cable
			if(workSheetType.equals(messageSource.getMessage("worksheet.type.cable.addpoint", 
					null,LocaleContextHolder.getLocale()))){
				 AddPointWorksheetBean addPointWorksheetBean = new AddPointWorksheetBean();
				 addPointWorksheetBean.setId(worksheet.getWorksheetAddPoint().getId());
				 addPointWorksheetBean.setAnalogPoint(worksheet.getWorksheetAddPoint().getAnalogPoint());
				 addPointWorksheetBean.setDigitalPoint(worksheet.getWorksheetAddPoint().getDigitalPoint());
				 addPointWorksheetBean.setAddPointPrice(worksheet.getWorksheetAddPoint().getAddPointPrice());
				 addPointWorksheetBean.setMonthlyFree(worksheet.getWorksheetAddPoint().getMonthlyFree());
				 worksheetBean.setAddPointWorksheetBean(addPointWorksheetBean);
			}else if(workSheetType.equals(messageSource.getMessage("worksheet.type.cable.connect", 
					null,LocaleContextHolder.getLocale()))){
				 //connect bean
				ConnectWorksheetBean connectWorksheetBean = new ConnectWorksheetBean();
				connectWorksheetBean.setId(worksheet.getWorksheetConnect().getId());
				worksheetBean.setConnectWorksheetBean(connectWorksheetBean);
				
			}else if(workSheetType.equals(messageSource.getMessage("worksheet.type.cable.setup", 
					null,LocaleContextHolder.getLocale()))){
				 
				
			}else if(workSheetType.equals(messageSource.getMessage("worksheet.type.cable.tune", 
					null,LocaleContextHolder.getLocale()))){
				 //tune bean
				TuneWorksheetBean tuneWorksheetBean = new TuneWorksheetBean();
				tuneWorksheetBean.setId(worksheet.getWorksheetTune().getId());
				tuneWorksheetBean.setTuneType(worksheet.getWorksheetTune().getTuneType());
				worksheetBean.setTuneWorksheetBean(tuneWorksheetBean);
				
			}else if(workSheetType.equals(messageSource.getMessage("worksheet.type.cable.repair", 
					null,LocaleContextHolder.getLocale()))){
				RepairConnectionWorksheetBean repairConnectionWorksheetBean = new RepairConnectionWorksheetBean();
				repairConnectionWorksheetBean.setId(worksheet.getWorksheetRepairConnection().getId());
				
				//load all product under service applicatio id
				ServiceApplication serviceApplication = new ServiceApplication();
				serviceApplication.setId(worksheet.getServiceApplication().getId());
				List<ProductItemWorksheet> productItemWorksheets = equipmentProductItemService.loadEquipmentProductItemHasSNAllStatus(serviceApplication);
				
				WorkSheetAddController wsa = new WorkSheetAddController();
				wsa.setMessageSource(messageSource);
				
				//check with current status
				String statusCurrentHistory = "";
				if(null != worksheet.getHistoryTechnicianGroupWorks() && worksheet.getHistoryTechnicianGroupWorks().size() > 0){
					statusCurrentHistory = worksheet.getHistoryTechnicianGroupWorks()
						.get(worksheet.getHistoryTechnicianGroupWorks().size()-1).getStatusHistory();
				}
				
				List<RepairMatchItemBean> repairMatchItemBeanList = new ArrayList<RepairMatchItemBean>();
				for(ProductItemWorksheet productItemWorksheet : productItemWorksheets){
					RepairMatchItemBean repairMatchItemBean = new RepairMatchItemBean();
					if(productItemWorksheet.getParent()== null && !"N".equals(productItemWorksheet.getProductTypeMatch())){
						repairMatchItemBean.setProductItemWorksheetsId(productItemWorksheet.getId());
						repairMatchItemBean.setEquipmentProductItemOld(wsa.populateProductItemIgnoreDeleted(productItemWorksheet.getEquipmentProductItem()));
						repairMatchItemBeanList.add(repairMatchItemBean);
					}
				}
				
				for(int i=0;i<repairMatchItemBeanList.size();i++){
					for(ProductItemWorksheet productItemWorksheet : productItemWorksheets){
						if(productItemWorksheet.getParent()!= null){
//							logger.info("Parent : "+productItemWorksheet.getParent().toString() + " == ProductItemWorksheetsId : "+repairMatchItemBeanList.get(i).getProductItemWorksheetsId().toString());
//							logger.info(productItemWorksheet.getParent().toString().equals(repairMatchItemBeanList.get(i).getProductItemWorksheetsId().toString()));
							if(productItemWorksheet.getParent().toString().equals(repairMatchItemBeanList.get(i).getProductItemWorksheetsId().toString())){
								repairMatchItemBeanList.get(i).setEquipmentProductItemNew(wsa.populateProductItemIgnoreDeleted(productItemWorksheet.getEquipmentProductItem()));
								repairMatchItemBeanList.get(i).setComment(productItemWorksheet.getComment());
							}
						}
					}
				}
				
				List<RepairMatchItemBean> repairMatchItemBeanListTemp = new ArrayList<RepairMatchItemBean>();
				if(!statusCurrentHistory.equals("R")){
					for(HistoryRepair historyRepair : worksheet.getHistoryRepairList()){
						RepairMatchItemBean repairMatchItemBean = new RepairMatchItemBean();
						repairMatchItemBean.setEquipmentProductItemNew(wsa.populateProductItemIgnoreDeleted(historyRepair.getEquipmentProductItem()));
						repairMatchItemBean.setComment(historyRepair.getComment());
						
						//load product old
						EquipmentProductItem equipmentProductItemOld = productService.findEquipmentProductItemById(historyRepair.getParent());
						repairMatchItemBean.setEquipmentProductItemOld(wsa.populateProductItemIgnoreDeleted(equipmentProductItemOld));
						//add temp
						repairMatchItemBeanListTemp.add(repairMatchItemBean);
					}
					
					repairMatchItemBeanList = repairMatchItemBeanListTemp;
				}
				
				repairConnectionWorksheetBean.setRepairMatchItemBeanList(repairMatchItemBeanList);
				worksheetBean.setRepairConnectionWorksheetBean(repairConnectionWorksheetBean);
				
			}else if(workSheetType.equals(messageSource.getMessage("worksheet.type.cable.addsettop", 
					null,LocaleContextHolder.getLocale()))){
				AddSetTopBoxWorksheetBean addSetTopBoxWorksheetBean = new AddSetTopBoxWorksheetBean(); 
				addSetTopBoxWorksheetBean.setId(worksheet.getWorksheetAddSetTopBox().getId());
				worksheetBean.setAddSetTopBoxWorksheetBean(addSetTopBoxWorksheetBean);
			}else if(workSheetType.equals(messageSource.getMessage("worksheet.type.cable.movepoint", 
					null,LocaleContextHolder.getLocale()))){
				 MovePointWorksheetBean movePointWorksheetBean = new MovePointWorksheetBean();
				 movePointWorksheetBean.setId(worksheet.getWorksheetMovePoint().getId());
				 movePointWorksheetBean.setAnalogPoint(worksheet.getWorksheetMovePoint().getAnalogPoint());
				 movePointWorksheetBean.setDigitalPoint(worksheet.getWorksheetMovePoint().getDigitalPoint());
				 movePointWorksheetBean.setMovePointPrice(worksheet.getWorksheetMovePoint().getMovePointPrice());
				 worksheetBean.setMovePointWorksheetBean(movePointWorksheetBean);
			}else if(workSheetType.equals(messageSource.getMessage("worksheet.type.cable.reducepoint", 
					null,LocaleContextHolder.getLocale()))){
				 ReducePointWorksheetBean reducePointWorksheetBean = new ReducePointWorksheetBean();
				 reducePointWorksheetBean.setId(worksheet.getWorksheetReducePoint().getId());
				 reducePointWorksheetBean.setAnalogPoint(worksheet.getWorksheetReducePoint().getAnalogPoint());
				 reducePointWorksheetBean.setDigitalPoint(worksheet.getWorksheetReducePoint().getDigitalPoint());
				 reducePointWorksheetBean.setMonthlyFree(worksheet.getWorksheetReducePoint().getMonthlyFree());
				 worksheetBean.setReducePointWorksheetBean(reducePointWorksheetBean);
			}else if(workSheetType.equals(messageSource.getMessage("worksheet.type.cable.cut", 
					null,LocaleContextHolder.getLocale()))){
				 //cut bean
				SimpleDateFormat formatDate3US = new SimpleDateFormat(
						messageSource.getMessage("date.format.type3", null, LocaleContextHolder.getLocale()),Locale.US);

				CutWorksheetBean cutWorksheetBean = new CutWorksheetBean();
				cutWorksheetBean.setId(worksheet.getWorksheetCut().getId());
				cutWorksheetBean.setReporter(worksheet.getWorksheetCut().getReporter());
				cutWorksheetBean.setMobile(worksheet.getWorksheetCut().getMobile());
				cutWorksheetBean.setCutWorkType(worksheet.getWorksheetCut().getCutWorkType());
				cutWorksheetBean.setReturnEquipment(worksheet.getWorksheetCut().isReturnEquipment());
				cutWorksheetBean.setSubmitCA(worksheet.getWorksheetCut().isSubmitCA());
				cutWorksheetBean.setCancelDate(null==worksheet.getWorksheetCut().getCancelDate()?"":formatDate3US.format(worksheet.getWorksheetCut().getCancelDate()));
				cutWorksheetBean.setEndPackageDate(null==worksheet.getWorksheetCut().getEndPackageDate()?"":formatDate3US.format(worksheet.getWorksheetCut().getEndPackageDate()));
				cutWorksheetBean.setSpecialDiscount(worksheet.getWorksheetCut().getSpecialDiscount());
				worksheetBean.setCutWorksheetBean(cutWorksheetBean);
				
			}else if(workSheetType.equals(messageSource.getMessage("worksheet.type.cable.move", 
					null,LocaleContextHolder.getLocale()))){
				MoveWorksheetBean moveWorksheetBean = new MoveWorksheetBean();
				moveWorksheetBean.setId(worksheet.getWorksheetMove().getId());
				moveWorksheetBean.setAnalogPoint(worksheet.getWorksheetMove().getAnalogPoint());
				moveWorksheetBean.setDigitalPoint(worksheet.getWorksheetMove().getDigitalPoint());
				moveWorksheetBean.setMoveCablePrice(worksheet.getWorksheetMove().getMoveCablePrice());
				List<Address> addressList = worksheet.getWorksheetMove().getAddresss();
				if(null != addressList && addressList.size() > 0){
					for(Address address:addressList){
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
			
						addressBean.collectAddress();
						// zone
						ZoneBean zoneBean = new ZoneBean();
						if (address.getZone() != null) {
							zoneBean.setId(address.getZone().getId());
							zoneBean.setZoneName(address.getZone().getZoneName());
							zoneBean.setZoneDetail(address.getZone().getZoneDetail());
						}
						addressBean.setZoneBean(zoneBean);
						moveWorksheetBean.setAddress(addressBean);
					}
				}
				worksheetBean.setMoveWorksheetBean(moveWorksheetBean);
			}else if(workSheetType.equals(messageSource.getMessage("worksheet.type.cable.borrow", 
					null,LocaleContextHolder.getLocale()))){
				BorrowWorksheetBean borrowWorksheetBean = new BorrowWorksheetBean();
				borrowWorksheetBean.setId(worksheet.getWorksheetBorrow().getId());
				worksheetBean.setBorrowWorksheetBean(borrowWorksheetBean);
			} else if (workSheetType.equals(messageSource.getMessage("worksheet.type.internet.analyzeproblems", 
					null, LocaleContextHolder.getLocale()))) {
				AnalyzeProblemsWorksheetBean analyzeProblemsWorksheetBean = new AnalyzeProblemsWorksheetBean();
				analyzeProblemsWorksheetBean.setId(worksheet.getWorksheetAnalyzeProblems().getId());
				analyzeProblemsWorksheetBean.setMenuReportId(worksheet.getWorksheetAnalyzeProblems().getMenuReport().getId());
				worksheetBean.setAnalyzeProblemsWorksheetBean(analyzeProblemsWorksheetBean);
			}
			//for internet
			
			//date start new bill
			if(worksheet.getServiceApplication().getServicePackage().isMonthlyService()){
				Calendar now = Calendar.getInstance(new Locale("EN", "en"));
				Calendar nowOldBill = Calendar.getInstance(new Locale("EN", "en"));
				Date oldDateBill = new Date();
				String dateStr = "";
//				ServiceApplication serviceApplicationTemp = serviceApplicationService.getServiceApplicationById(worksheet.getServiceApplication().getReferenceServiceApplicationId());
//				Worksheet worksheetTemp = new Worksheet();
//				for(Worksheet ws : serviceApplicationTemp.getWorksheets()){
//					if(ws.getWorkSheetType().equals("C_S")){
//						worksheetTemp = ws;
//					}
//				}
				List<HistoryTechnicianGroupWork> hisTech =  worksheet.getHistoryTechnicianGroupWorks();
				if(null != hisTech && hisTech.size() > 0){
					oldDateBill = hisTech.get(hisTech.size() - 1).getAssignDate();
					nowOldBill.setTime(oldDateBill);
					dateStr = nowOldBill.get(Calendar.DAY_OF_MONTH) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.YEAR);
					worksheetBean.setDateStartNewBill(dateStr);
				}
				
				String availableDateTime = worksheet.getAvailableDateTime();
				if(null != availableDateTime && !"".equals(availableDateTime)){
					String availableDateTimeArray[] = availableDateTime.split(" - ");
					if(availableDateTimeArray.length > 1){
						worksheetBean.setStartDate(availableDateTimeArray[0]);
						worksheetBean.setEndDate(availableDateTimeArray[1]);
					}
				}
				
				
				
			}
			
			
			return worksheetBean;
		}
	
		//load config page for render
		public String loadConfigPage(String workSheetType){
			String pageType = "";
			String page = "";
			String pageRender = "";
			if(workSheetType != null){
				//worksheet type cable or internet
				String shortType = workSheetType.substring(0,1);
				if(shortType.toUpperCase().equals(shortTypeCable)){
					pageType = pageTypeCable;
				}else if(shortType.toUpperCase().equals(shortTypeInternet)){
					pageType = pageTypeInternet;
				}
				//for cable
				if(workSheetType.equals(messageSource.getMessage("worksheet.type.cable.addpoint", 
						null,LocaleContextHolder.getLocale()))){
					pageRender = "addpoint";
					
				}else if(workSheetType.equals(messageSource.getMessage("worksheet.type.cable.connect", 
						null,LocaleContextHolder.getLocale()))){
					pageRender = "connect";
					
				}else if(workSheetType.equals(messageSource.getMessage("worksheet.type.cable.setup", 
						null,LocaleContextHolder.getLocale()))){
					pageRender = "setup";
					
				}else if(workSheetType.equals(messageSource.getMessage("worksheet.type.cable.tune", 
						null,LocaleContextHolder.getLocale()))){
					pageRender = "tune";
					
				}else if(workSheetType.equals(messageSource.getMessage("worksheet.type.cable.repair", 
						null,LocaleContextHolder.getLocale()))){
					pageRender = "repair";
					
				}else if(workSheetType.equals(messageSource.getMessage("worksheet.type.cable.addsettop", 
						null,LocaleContextHolder.getLocale()))){
					pageRender = "addsettop";
					
				}else if(workSheetType.equals(messageSource.getMessage("worksheet.type.cable.movepoint", 
						null,LocaleContextHolder.getLocale()))){
					pageRender = "movepoint";
					
				}else if(workSheetType.equals(messageSource.getMessage("worksheet.type.cable.reducepoint", 
						null,LocaleContextHolder.getLocale()))){
					pageRender = "reducepoint";
					
				}else if(workSheetType.equals(messageSource.getMessage("worksheet.type.cable.cut", 
						null,LocaleContextHolder.getLocale()))){
					pageRender = "cut";
					
				}else if(workSheetType.equals(messageSource.getMessage("worksheet.type.cable.move", 
						null,LocaleContextHolder.getLocale()))){
					pageRender = "move";
					
				}else if(workSheetType.equals(messageSource.getMessage("worksheet.type.cable.borrow", 
						null,LocaleContextHolder.getLocale()))){
					pageRender = "borrow";
				}else if(workSheetType.equals(messageSource.getMessage("worksheet.type.internet.analyzeproblems", 
						null,LocaleContextHolder.getLocale()))){
					pageRender = "analyzeproblems";
				}
				//for internet
				
				page = pageType+"/"+pageRender;
			} //end if check worksheet code null

			return page;
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
	
	
	public boolean updateSeperateWorksheetType(WorksheetUpdateSnapShotBean worksheetUpdateSnapShotBean){
		boolean success = true;
		//for cable
		if(worksheetUpdateSnapShotBean.getTypeWorksheet().equals(messageSource.getMessage("worksheet.type.cable.addpoint", 
				null,LocaleContextHolder.getLocale()))){
			success = updateSnapAddPoint(worksheetUpdateSnapShotBean);
			
		}else if(worksheetUpdateSnapShotBean.getTypeWorksheet().equals(messageSource.getMessage("worksheet.type.cable.connect", 
				null,LocaleContextHolder.getLocale()))){
			success = updateSnapConnect(worksheetUpdateSnapShotBean);
			
		}else if(worksheetUpdateSnapShotBean.getTypeWorksheet().equals(messageSource.getMessage("worksheet.type.cable.setup", 
				null,LocaleContextHolder.getLocale()))){
			success = updateSnapSetup(worksheetUpdateSnapShotBean);
			
		}else if(worksheetUpdateSnapShotBean.getTypeWorksheet().equals(messageSource.getMessage("worksheet.type.cable.tune", 
				null,LocaleContextHolder.getLocale()))){
			success = updateSnapTune(worksheetUpdateSnapShotBean);
			
		}else if(worksheetUpdateSnapShotBean.getTypeWorksheet().equals(messageSource.getMessage("worksheet.type.cable.repair", 
				null,LocaleContextHolder.getLocale()))){
			success = updateSnapRepair(worksheetUpdateSnapShotBean);
			
		}else if(worksheetUpdateSnapShotBean.getTypeWorksheet().equals(messageSource.getMessage("worksheet.type.cable.addsettop", 
				null,LocaleContextHolder.getLocale()))){
			success = updateSnapAddSetTop(worksheetUpdateSnapShotBean);
			
		}else if(worksheetUpdateSnapShotBean.getTypeWorksheet().equals(messageSource.getMessage("worksheet.type.cable.movepoint", 
				null,LocaleContextHolder.getLocale()))){
			success = updateSnapMovePoint(worksheetUpdateSnapShotBean);
			
		}else if(worksheetUpdateSnapShotBean.getTypeWorksheet().equals(messageSource.getMessage("worksheet.type.cable.reducepoint", 
				null,LocaleContextHolder.getLocale()))){
			success = updateSnapReducePoint(worksheetUpdateSnapShotBean);
			
		}else if(worksheetUpdateSnapShotBean.getTypeWorksheet().equals(messageSource.getMessage("worksheet.type.cable.cut", 
				null,LocaleContextHolder.getLocale()))){
			success = updateSnapCut(worksheetUpdateSnapShotBean);
			
		}else if(worksheetUpdateSnapShotBean.getTypeWorksheet().equals(messageSource.getMessage("worksheet.type.cable.move", 
				null,LocaleContextHolder.getLocale()))){
			success = updateSnapMove(worksheetUpdateSnapShotBean);
			
		}else if(worksheetUpdateSnapShotBean.getTypeWorksheet().equals(messageSource.getMessage("worksheet.type.cable.borrow", 
				null,LocaleContextHolder.getLocale()))){
			success = updateSnapBorrow(worksheetUpdateSnapShotBean);
		}else if(worksheetUpdateSnapShotBean.getTypeWorksheet().equals(messageSource.getMessage("worksheet.type.internet.analyzeproblems", 
				null,LocaleContextHolder.getLocale()))){
			success = updateSnapAnalyzeproblems(worksheetUpdateSnapShotBean);
		}
		//type internet
		
		return success;
	}
	
	/////// update method worksheet ///////
	public boolean updateHeadSnap(WorksheetUpdateSnapShotBean worksheetUpdateSnapShotBean){
		// create timestamp
		Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
		boolean success = true;
		try {
			Worksheet worksheet = workSheetService.getWorksheetById(worksheetUpdateSnapShotBean.getIdWorksheetParent());
			if(worksheet != null){
				//member--------------------------------------------------//
				List<HistoryTechnicianGroupWork> historyTechnicianGroupWorkList = worksheet.getHistoryTechnicianGroupWorks();
				if(null != historyTechnicianGroupWorkList && historyTechnicianGroupWorkList.size() > 0){
					Long currentSnapshotId = worksheet.getHistoryTechnicianGroupWorks()
							.get(worksheet.getHistoryTechnicianGroupWorks().size() - 1).getId();
					
					//temp history
					HistoryTechnicianGroupWork historyTechnicianGroupWork = worksheet.getHistoryTechnicianGroupWorks()
							.get(worksheet.getHistoryTechnicianGroupWorks().size() - 1);
					
					//delete old member assign
					workSheetService.deleteMemberAssignByHistoryTechnicianGroupWork(currentSnapshotId);
					//save new member assign
					for(PersonnelBean personnelBean : worksheetUpdateSnapShotBean.getPersonnelBeanList()){
						PersonnelAssign personnelAssign = new PersonnelAssign();
						personnelAssign.setDeleted(Boolean.FALSE);
						personnelAssign.setCreateDate(CURRENT_TIMESTAMP);
						personnelAssign.setCreatedBy(getUserNameLogin());
						personnelAssign.setPersonnelId(personnelBean.getId());
						personnelAssign.setHistoryTechnicianGroupWork(historyTechnicianGroupWork);
						workSheetService.saveMemberAssign(personnelAssign);
					}
				
				//End member--------------------------------------------------//
				
				//update status history---------------------------------------------------//
				if(worksheetUpdateSnapShotBean.getType().equals("S")){
					historyTechnicianGroupWork.setStatusHistory("S");
					historyTechnicianGroupWork.setRemarkNotSuccess(worksheetUpdateSnapShotBean.getRemark());
					worksheet.setStatus("S");
				}else if(worksheetUpdateSnapShotBean.getType().equals("N")){
					historyTechnicianGroupWork.setStatusHistory("N");
					historyTechnicianGroupWork.setRemarkNotSuccess(worksheetUpdateSnapShotBean.getRemarkFail());
					worksheet.setStatus("H");
				}
				historyTechnicianGroupWorkService.update(historyTechnicianGroupWork);
				
				}
				//End update status history---------------------------------------------------//
				
				//update product item---------------------------------------------------------//
				// product item
				List<ProductItem> productItemList = new ArrayList<ProductItem>();
				//loop delete old product
				if(!worksheetUpdateSnapShotBean.getTypeWorksheet().equals("C_S")){
				for(ProductItem productItem : worksheet.getProductItems()){
					if("N".equals(productItem.getProductTypeMatch()) && worksheetUpdateSnapShotBean.getType().equals("S")){
					for(ProductItemWorksheet productItemWorksheet : productItem.getProductItemWorksheets()){
						// update temp item
						EquipmentProductItem equipmentProductItemTempDel = productService.findEquipmentProductItemById(
								productItemWorksheet.getEquipmentProductItem() != null ? productItemWorksheet.getEquipmentProductItem().getId() : -1);
						
						if(equipmentProductItemTempDel != null && (!equipmentProductItemTempDel.getSerialNo().isEmpty())){

							equipmentProductItemTempDel.setReservations(0);
							equipmentProductItemTempDel.setStatus(STATUS_ACTIVE);
							equipmentProductItemTempDel.setBalance(1);

							productService.updateProductItem(equipmentProductItemTempDel);
							
						}else if(equipmentProductItemTempDel != null && (equipmentProductItemTempDel.getSerialNo().isEmpty())){
							equipmentProductItemTempDel.setReservations(equipmentProductItemTempDel.getReservations() - productItemWorksheet.getQuantity());
//							equipmentProductItemTempDel.setBalance(equipmentProductItemTempDel.getBalance() + productItemWorksheet.getQuantity());
//							if(equipmentProductItemTempDel.getBalance() < 0){
//								equipmentProductItemTempDel.setBalance(0);
//							}
							if(equipmentProductItemTempDel.getReservations() < 0){
								equipmentProductItemTempDel.setReservations(0);
							}
							
							productService.updateProductItem(equipmentProductItemTempDel);

							EquipmentProduct eqmp = equipmentProductItemTempDel.getEquipmentProduct();
							
							// อัพ product ตัวแม่
							if(null != eqmp){
								ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
								pro.setEquipmentProductService(equipmentProductService);
								pro.setMessageSource(messageSource);
								pro.autoUpdateStatusEquipmentProduct(eqmp);
							}
						}
						if(productItemWorksheet.getEquipmentProductItem() != null){
							if(null != worksheetUpdateSnapShotBean.getProductItemBeanList() && worksheetUpdateSnapShotBean.getProductItemBeanList().size() > 0){
								if(worksheetUpdateSnapShotBean.getType().equals("S")){
									workSheetService.deleteProductItemWorksheet(productItemWorksheet);
								}
							}
						}
					}
				}
				}
				
				if(null != worksheetUpdateSnapShotBean.getProductItemBeanList() && worksheetUpdateSnapShotBean.getProductItemBeanList().size() > 0){
					if(worksheetUpdateSnapShotBean.getType().equals("S")){
						//delete product item old
						workSheetService.deleteProductItemWithWorksheetIdTypeN(worksheet.getId());
					}
				}
				//save product item new
				for (ProductItemBean productItemBean : worksheetUpdateSnapShotBean.getProductItemBeanList()) {
					if(worksheetUpdateSnapShotBean.getType().equals("S")){
					if (productItemBean.getType().equals("E")) {
						ProductItem ProductItem = new ProductItem();
						ProductItem.setServiceApplication(worksheet.getServiceApplication());
						ProductItem.setCreateDate(CURRENT_TIMESTAMP);
						ProductItem.setCreatedBy(getUserNameLogin());
						ProductItem.setDeleted(Boolean.FALSE);
						ProductItem.setLend(Boolean.FALSE);
						ProductItem.setFree(Boolean.FALSE);
						ProductItem.setProductType(TYPE_EQUIMENT);
						ProductItem.setProductTypeMatch("N");
						ProductItem.setServiceApplication(worksheet.getServiceApplication());
						// set equipment
						EquipmentProduct equipmentProduct = equipmentProductService
								.getEquipmentProductById(productItemBean.getId());
						ProductItem.setEquipmentProduct(equipmentProduct);
						ProductItem.setWorkSheet(worksheet);
						// worksheet item
						List<ProductItemWorksheet> productItemWorksheetList = new ArrayList<ProductItemWorksheet>();
						for (ProductItemWorksheetBean productItemWorksheetBean : productItemBean
								.getProductItemWorksheetBeanList()) {
							ProductItemWorksheet ProductItemWorksheet = new ProductItemWorksheet();
							ProductItemWorksheet.setProductItem(ProductItem);
							ProductItemWorksheet.setPrice(productItemWorksheetBean.getPrice());
							ProductItemWorksheet.setCreateDate(CURRENT_TIMESTAMP);
							ProductItemWorksheet.setCreatedBy(getUserNameLogin());
							ProductItemWorksheet.setDeleted(Boolean.FALSE);
							ProductItemWorksheet.setLend(productItemWorksheetBean.isLend());
							ProductItemWorksheet.setFree(productItemWorksheetBean.isFree());
							ProductItemWorksheet.setQuantity(productItemWorksheetBean.getQuantity());
							ProductItemWorksheet.setProductTypeMatch("N");
							ProductItemWorksheet.setProductType(TYPE_EQUIMENT);
							
							if(!ProductItemWorksheet.isFree() && !ProductItemWorksheet.isLend()){
								ProductItemWorksheet.setAmount(productItemWorksheetBean.getQuantity() * productItemWorksheetBean.getPrice());
							}else{
								ProductItemWorksheet.setAmount(0f);
							}
							
							// update temp item
							EquipmentProductItem equipmentProductItemTemp = productService.findEquipmentProductItemById(
									productItemWorksheetBean.getEquipmentProductItemBean().getId());

							if (equipmentProductItemTemp != null
									&& (!equipmentProductItemTemp.getSerialNo().isEmpty())) {

								equipmentProductItemTemp.setStatus(STATUS_HOLD);
//								equipmentProductItemTemp.setBalance(
//										equipmentProductItemTemp.getBalance() - productItemWorksheetBean.getQuantity());
//								if (equipmentProductItemTemp.getBalance() < 0) {
//									equipmentProductItemTemp.setBalance(equipmentProductItemTemp.getBalance() * -1);
//								}
								productService.updateProductItem(equipmentProductItemTemp);

							} else if (equipmentProductItemTemp != null
									&& (equipmentProductItemTemp.getSerialNo().isEmpty())) {

								// update balance
//								equipmentProductItemTemp.setBalance(
//										equipmentProductItemTemp.getBalance() - productItemWorksheetBean.getQuantity());
//								if (equipmentProductItemTemp.getBalance() < 0) {
//									equipmentProductItemTemp.setBalance(equipmentProductItemTemp.getBalance() * -1);
//								}
//								productService.updateProductItem(equipmentProductItemTemp);								

							}

							EquipmentProduct eqmp = equipmentProductItemTemp.getEquipmentProduct();
							// อัพเดท product ตัวแม่
							if (null != eqmp) {
								ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
								pro.setEquipmentProductService(equipmentProductService);
								pro.setMessageSource(messageSource);
								pro.autoUpdateStatusEquipmentProduct(eqmp);
							}

							// load product item
//							EquipmentProductItem equipmentProductItem = productService.findEquipmentProductItemById(
//									productItemWorksheetBean.getEquipmentProductItemBean().getId());

							ProductItemWorksheet.setEquipmentProductItem(equipmentProductItemTemp);
							productItemWorksheetList.add(ProductItemWorksheet);
						}
						ProductItem.setProductItemWorksheets(productItemWorksheetList);
						productItemList.add(ProductItem);
					} else if (productItemBean.getType().equals("S")) {
						ProductItem ProductItem = new ProductItem();
						ProductItem.setServiceApplication(worksheet.getServiceApplication());
						ProductItem.setCreateDate(CURRENT_TIMESTAMP);
						ProductItem.setCreatedBy(getUserNameLogin());
						ProductItem.setDeleted(Boolean.FALSE);
						ProductItem.setLend(Boolean.FALSE);
						ProductItem.setFree(productItemBean.isFree());
						ProductItem.setProductType(TYPE_SERVICE);
						ProductItem.setPrice(productItemBean.getPrice());
						ProductItem.setQuantity(productItemBean.getQuantity());
						ProductItem.setAmount(productItemBean.getPrice()*productItemBean.getQuantity());
						ProductItem.setProductTypeMatch("N");
						ProductItem.setServiceApplication(worksheet.getServiceApplication());
						// set equipment
						ServiceProduct serviceProduct = serviceProductService
								.getServiceProductById(productItemBean.getId());
						ProductItem.setServiceProduct(serviceProduct);
						List<ProductItemWorksheet> productItemWorksheetList = new ArrayList<ProductItemWorksheet>();
						ProductItem.setProductItemWorksheets(productItemWorksheetList);
						ProductItem.setWorkSheet(worksheet);
						productItemList.add(ProductItem);
					}
					
				}
				}//end for
				}
				
				if(worksheetUpdateSnapShotBean.getType().equals("S")){
					worksheet.setProductItems(productItemList);
				}else{ // บันทึกใบงานไม่สำเร็จ คืนอุปกรณ์ที่ทำการจองไว้
//					List<ProductItem> productItemReqList = worksheet.getProductItems();
//					if(null != productItemReqList){
//						logger.info("productItemReqList size : "+productItemReqList.size());
//						for(ProductItem productItem:productItemReqList){
//							List<ProductItemWorksheet> productItemWorksheetReqList = productItem.getProductItemWorksheets();
//							logger.info("productItemWorksheetReqList size : "+productItemWorksheetReqList.size());
//							if(null != productItemWorksheetReqList){
//								for(ProductItemWorksheet proiws:productItemWorksheetReqList){
//									int quantity = proiws.getQuantity();
//									EquipmentProductItem eqItem = proiws.getEquipmentProductItem();
//									RequisitionItem req = proiws.getRequisitionItem();
//									int balance = eqItem.getBalance();
//									int spare = eqItem.getSpare();
//									int reservations = eqItem.getReservations();
//									if(null != req){ // เช็คว่าเป็นอุปกรณ์ที่อยู่ในใบเบิก ?
//										int returnEquipmentProductItem = req.getReturnEquipmentProductItem();
//										returnEquipmentProductItem = returnEquipmentProductItem - quantity;
//										if(returnEquipmentProductItem < 0){
//											returnEquipmentProductItem = returnEquipmentProductItem * (-1);
//										}
//										req.setReturnEquipmentProductItem(returnEquipmentProductItem);
//										req.setUpdatedBy(getUserNameLogin());
//										req.setUpdatedDate(BaseController.CURRENT_TIMESTAMP);
//										requisitionItemService.update(req);
//										eqItem.setSpare(spare + quantity); // นำอุปกรณ์คืนเข้าใบเบิก
//									}else{
//										eqItem.setBalance(balance + quantity); // นำอุปกรณ์คืนเข้าจำนวนเหลือ
//									}
//									reservations = reservations - quantity;
//									if(reservations < 0){
//										reservations = reservations * (-1);
//									}
//									
//									if(!"".equals(eqItem.getSerialNo())){ // มี SN
//										eqItem.setStatus(STATUS_ACTIVE);
//									}
//									
//									eqItem.setReservations(reservations);
//									eqItem.setUpdatedBy(getUserNameLogin());
//									eqItem.setUpdatedDate(BaseController.CURRENT_TIMESTAMP);
//									logger.info("eqItem ID : "+eqItem.getId());
//									equipmentProductItemService.update(eqItem);
//									
//									EquipmentProduct eqmp = eqItem.getEquipmentProduct();
//									// อัพเดท product ตัวแม่
//									if (null != eqmp) {
//										ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
//										pro.setEquipmentProductService(equipmentProductService);
//										pro.setMessageSource(messageSource);
//										pro.autoUpdateStatusEquipmentProduct(eqmp);
//									}
//									
//									// คืนอุปกรณ์
//									workSheetService.deleteProductItemWorksheet(proiws);
//								}
//							}
//						}
//					}
//					
//					List<ProductItem> productItemListTemp = new ArrayList<ProductItem>();
//					worksheet.setProductItems(productItemListTemp);
				}
				
				//check ด้วย ประเภท worksheet//////////////////////////////////////////////////////////////////////////
				if(worksheetUpdateSnapShotBean.getTypeWorksheet().equals("C_S")){
					if(worksheetUpdateSnapShotBean.getType().equals("S")){
						Date dateStartBill = new DateUtil().convertStringToDateTimeDb(worksheetUpdateSnapShotBean.getSetupWorksheetBean().getDateStartBill());
						worksheet.setDateOrderBill(dateStartBill);
						worksheet.getInvoice().setCreateDate(dateStartBill);
						
//						if(worksheet.getServiceApplication().getReferenceServiceApplicationId()!=null){
//							Date dateStartBill = new DateUtil().convertStringToDateTimeDb(worksheetUpdateSnapShotBean.getSetupWorksheetBean().getDateStartBill());
//							worksheet.setDateOrderBill(dateStartBill);
//							worksheet.getInvoice().setCreateDate(dateStartBill);
//						}else{
//							worksheet.setDateOrderBill(worksheet.getHistoryTechnicianGroupWorks().get(worksheet.getHistoryTechnicianGroupWorks().size() - 1).getAssignDate());
//						}
					}
				}
				//check ด้วย ประเภท worksheet//////////////////////////////////////////////////////////////////////////
				
				//end process product item
				
				//subworksheet------------------------------------------------------//
				workSheetService.deleteSubWorksheetByWorksheetId(worksheetUpdateSnapShotBean.getIdWorksheetParent());
				if(worksheetUpdateSnapShotBean.getType().equals("S")){
					List<SubWorksheet> subWorksheetList = new ArrayList<SubWorksheet>();
					for(SubWorksheetBean subWorksheetBean : worksheetUpdateSnapShotBean.getSubWorkSheetBeanList()){
						SubWorksheet subWorksheet = new SubWorksheet();
						subWorksheet.setWorkSheetType(subWorksheetBean.getWorkSheetType());
						subWorksheet.setRemark(subWorksheetBean.getRemark());
						subWorksheet.setPrice(subWorksheetBean.getPrice());
						subWorksheet.setWorkSheet(worksheet);
						subWorksheet.setCreateDate(CURRENT_TIMESTAMP);
						subWorksheet.setCreatedBy(getUserNameLogin());
						subWorksheet.setDeleted(Boolean.FALSE);
						subWorksheetList.add(subWorksheet);
					}
					worksheet.setSubWorksheets(subWorksheetList);
				}
				//end subworksheet--------------------------------------------------//
				
				worksheet.setAvailableDateTime(worksheetUpdateSnapShotBean.getAvailableDateTime());
				
				//update worksheet--------------------------------------------------//
				worksheet.setRemark(worksheetUpdateSnapShotBean.getRemark());
				worksheet.setRemarkSuccess(worksheetUpdateSnapShotBean.getRemarkSuccess());
				worksheet.setJobDetails(worksheetUpdateSnapShotBean.getJobDetails());
				worksheet.setUpdatedDate(CURRENT_TIMESTAMP);
				worksheet.setUpdatedBy(getUserNameLogin());
				workSheetService.update(worksheet);
				
				
			}else{
				success = false;
			}
			 
		} catch (Exception ex) {
			ex.printStackTrace();
			success = false;
		}
		
		return success;
	}
	
	//update snapshot method seperate type -------------------------//
	public boolean updateSnapTune(WorksheetUpdateSnapShotBean worksheetUpdateSnapShotBean){
		boolean success = true;
		try{
			success = updateHeadSnap(worksheetUpdateSnapShotBean);
			//call cut stock
			success = BeforeCutStock(worksheetUpdateSnapShotBean);
			//update tune worksheet
			WorksheetTune worksheetTune =  workSheetService.getWorksheetTuneById(worksheetUpdateSnapShotBean.getTuneWorksheetBean().getId());
			worksheetTune.setTuneType(worksheetUpdateSnapShotBean.getTuneWorksheetBean().getTuneType());
			workSheetService.updateWorksheetTune(worksheetTune);
			
		}catch(Exception ex){
			success = false;
		}
		return success;
	}
	
	public boolean updateSnapConnect(WorksheetUpdateSnapShotBean worksheetUpdateSnapShotBean){
		boolean success = true;
		try{
			success = updateHeadSnap(worksheetUpdateSnapShotBean);
			//call cut stock
			success = BeforeCutStock(worksheetUpdateSnapShotBean);
			
			if(success && (worksheetUpdateSnapShotBean.getType().equals("S"))){
				//update connect worksheet
				Worksheet worksheet = workSheetService.getWorksheetById(worksheetUpdateSnapShotBean.getIdWorksheetParent());
				for(int i=0;i<worksheet.getProductItems().size();i++){
					if("S".equals(worksheet.getProductItems().get(i).getProductType()) && "O".equals(worksheet.getProductItems().get(i).getProductTypeMatch())){
						worksheet.getProductItems().get(i).setAmount(worksheetUpdateSnapShotBean.getConnectWorksheetBean().getPrice());
						worksheet.getProductItems().get(i).setPrice(worksheetUpdateSnapShotBean.getConnectWorksheetBean().getPrice());
					}
				}
				//update status serviceapplication
				worksheet.getServiceApplication().setStatus("A");
				workSheetService.update(worksheet);
				
				//update worksheet connect
				WorksheetConnect worksheetConnect = workSheetService.getWorksheetConnectById(worksheetUpdateSnapShotBean.getConnectWorksheetBean().getId());
				workSheetService.updateWorksheetConnect(worksheetConnect);
			}
			
		}catch(Exception ex){
			success = false;
		}
		return success;
	}
	
	public boolean updateSnapSetup(WorksheetUpdateSnapShotBean worksheetUpdateSnapShotBean){
		Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
		boolean success = true;
		try{
			success = updateHeadSnap(worksheetUpdateSnapShotBean);
			//process product item setup
			
			//call cut stock
			success = BeforeCutStock(worksheetUpdateSnapShotBean);
			
			Worksheet worksheet = workSheetService.getWorksheetById(worksheetUpdateSnapShotBean.getIdWorksheetParent());
			ServiceApplication serviceApplication = worksheet.getServiceApplication();
			
			if(success && worksheetUpdateSnapShotBean.getType().equals("S")){
				serviceApplication.setStatus("A");
				// อัพเดทสถานะ customer
				Customer customer = serviceApplication.getCustomer();
				if(null != customer){
					customer.setActive(Boolean.TRUE);
					customerService.update(customer);
				}
				
				// อัพเดท ประวัติการใช้งานสินค้าและอุปกรณ์ 
				List<ProductItem> productItemList = serviceApplication.getProductItems();
				if(null != productItemList && productItemList.size() > 0){
					for(ProductItem productItem:productItemList){
						List<ProductItemWorksheet> productItemWorksheetList = productItem.getProductItemWorksheets();
						if(null != productItemWorksheetList && productItemWorksheetList.size() > 0){
							for(ProductItemWorksheet productItemWorksheet:productItemWorksheetList){
							EquipmentProductItem equipmentProductItem = productItemWorksheet.getEquipmentProductItem();
								if(null != equipmentProductItem){
									HistoryUseEquipment hisUseEq = new HistoryUseEquipment();
									hisUseEq.setCustomer(customer);
									hisUseEq.setServiceApplication(serviceApplication);
									hisUseEq.setEquipmentProductItem(equipmentProductItem);
									hisUseEq.setActiveDate(CURRENT_TIMESTAMP);
									hisUseEq.setCreateDate(CURRENT_TIMESTAMP);
									hisUseEq.setCreatedBy(getUserNameLogin());
									hisUseEq.setDeleted(Boolean.FALSE);
									if(productItemWorksheet.isLend()){
										hisUseEq.setStatus("L"); // ยืม
									}else if(productItemWorksheet.isFree()){
										hisUseEq.setStatus("F"); // ฟรี
									}else{
										hisUseEq.setStatus("O"); // ขายขาด
									}
									//historyUseEquipmentService.save(hisUseEq);
								}
							}
						}
					}
				}
				
			}else if(success && worksheetUpdateSnapShotBean.getType().equals("N")){
				serviceApplication.setStatus("H");
			}
			serviceApplicationService.update(serviceApplication);
			
		}catch(Exception ex){
			ex.printStackTrace();
			success = false;
		}
		return success;
	}
	
	public boolean updateSnapCut(WorksheetUpdateSnapShotBean worksheetUpdateSnapShotBean) {
		Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
		boolean success = true;
		try {
			success = updateHeadSnap(worksheetUpdateSnapShotBean);
			// call cut stock
			success = BeforeCutStock(worksheetUpdateSnapShotBean);
			// update cut worksheet
			WorksheetCut worksheetCut = workSheetService
					.getWorksheetCutById(worksheetUpdateSnapShotBean.getCutWorksheetBean().getId());
			
			
			ServiceApplication serviceApplication = serviceApplicationService
					.getServiceApplicationById(worksheetUpdateSnapShotBean.getCutWorksheetBean().getServiceApplication().getId());
			
			
			//set value update
			if(success && worksheetUpdateSnapShotBean.getType().equals("S")){
				SimpleDateFormat formatDateUS = new SimpleDateFormat(
						messageSource.getMessage("date.format.type3", null, LocaleContextHolder.getLocale()),Locale.US);
				if(worksheetCut != null){
					worksheetCut.setReporter(worksheetUpdateSnapShotBean.getCutWorksheetBean().getReporter());
					worksheetCut.setMobile(worksheetUpdateSnapShotBean.getCutWorksheetBean().getMobile());
					worksheetCut.setCutWorkType(worksheetUpdateSnapShotBean.getCutWorksheetBean().getCutWorkType());
					worksheetCut.setReturnEquipment(worksheetUpdateSnapShotBean.getCutWorksheetBean().isReturnEquipment());
					worksheetCut.setSubmitCA(worksheetUpdateSnapShotBean.getCutWorksheetBean().isSubmitCA());
					worksheetCut.setCancelDate("".equals(worksheetUpdateSnapShotBean.getCutWorksheetBean().getCancelDate())?null:formatDateUS.parse(worksheetUpdateSnapShotBean.getCutWorksheetBean().getCancelDate()));
					worksheetCut.setEndPackageDate("".equals(worksheetUpdateSnapShotBean.getCutWorksheetBean().getEndPackageDate())?null:formatDateUS.parse(worksheetUpdateSnapShotBean.getCutWorksheetBean().getEndPackageDate()));
					worksheetCut.setSpecialDiscount(worksheetUpdateSnapShotBean.getCutWorksheetBean().getSpecialDiscount());
					workSheetService.updateWorksheetCut(worksheetCut);
				}
				
				// มีการคืนอุปกรณ์ update รายการอุปกรณ์ที่เช่ายืม
				List<ProductItemWorksheetBean> productItemWorksheetBeans= worksheetUpdateSnapShotBean.getCutWorksheetBean().getProductItemWorksheetBeanList();
				if(null != productItemWorksheetBeans && productItemWorksheetBeans.size() > 0){
					for(ProductItemWorksheetBean productItemWorksheetBean:productItemWorksheetBeans){
						ProductItemWorksheet productItemWorksheet = productItemWorksheetService.getProductItemWorksheetById(productItemWorksheetBean.getId());
						if(null != productItemWorksheet){
							productItemWorksheet.setLendStatus(productItemWorksheetBean.getLendStatus());
							productItemWorksheet.setReturnEquipment(productItemWorksheetBean.isReturnEquipment());
							productItemWorksheet.setUpdatedBy(getUserNameLogin());
							productItemWorksheet.setUpdatedDate(CURRENT_TIMESTAMP);
							productItemWorksheetService.update(productItemWorksheet);

							EquipmentProductItem equipmentProductItem = productItemWorksheet.getEquipmentProductItem();
							if(null != equipmentProductItem  && !equipmentProductItem.getSerialNo().isEmpty()){
							
							HistoryUpdateStatus historyUpdateStatus = new HistoryUpdateStatus();
							historyUpdateStatus.setCreateDate(CURRENT_TIMESTAMP);
							historyUpdateStatus.setCreatedBy(getUserNameLogin());
							historyUpdateStatus.setDeleted(Boolean.FALSE);
							historyUpdateStatus.setEquipmentProductItem(equipmentProductItem);
							historyUpdateStatus.setInformer(getUserNameLogin());
							historyUpdateStatus.setDateRepair(CURRENT_TIMESTAMP);
							historyUpdateStatus.setRecordDate(CURRENT_TIMESTAMP);
							historyUpdateStatus.setRemark(messageSource.getMessage("cut.worksheet.remark", null, LocaleContextHolder.getLocale()));	
								
							// อัพเดทวันที่คืน เอาประวัติตัวล่าสุด
							if(productItemWorksheet.isReturnEquipment()){
								if (equipmentProductItem.getHistoryUseEquipments().size() > 0) {
									HistoryUseEquipment historyUseEquipmentProductOld = historyUseEquipmentService
											.getHistoryUseEquipmentById(equipmentProductItem.getHistoryUseEquipments()
													.get(equipmentProductItem.getHistoryUseEquipments().size() - 1)
													.getId());

								if (historyUseEquipmentProductOld != null) {
										historyUseEquipmentProductOld.setReturnDate(CURRENT_TIMESTAMP);
										historyUseEquipmentService.update(historyUseEquipmentProductOld);
									}

								}
								
								historyUpdateStatus.setStatus(STATUS_SELL);
							}
							//อัพเดทเป็นขายขาดกรณีไม่คืน ไม่สนใจสถานะเดิม
							else{
								historyUpdateStatus.setStatus(productItemWorksheetBean.getLendStatus());
							}
							
							List<HistoryUpdateStatus> historyUpdateStatusList = new ArrayList<HistoryUpdateStatus>();
							historyUpdateStatusList.add(historyUpdateStatus);
							equipmentProductItem.setHistoryUpdateStatuses(historyUpdateStatusList);
							equipmentProductItem.setStatus(historyUpdateStatus.getStatus());
							equipmentProductItemService.update(equipmentProductItem);
							
								EquipmentProduct eqmp = equipmentProductItem.getEquipmentProduct();
								// อัพเดท product ตัวแม่
								if (null != eqmp) {
									ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
									pro.setEquipmentProductService(equipmentProductService);
									pro.setMessageSource(messageSource);
									pro.autoUpdateStatusEquipmentProduct(eqmp);
								}
							}
						}
					}
				}

				Worksheet worksheet = workSheetService.getWorksheetById(worksheetUpdateSnapShotBean.getIdWorksheetParent());
				
				// ค่าใช้จ่ายเพิ่มเติม มีเฉพาะ serviceProduct
				List<ProductItemBean> productItemBeans = worksheetUpdateSnapShotBean.getCutWorksheetBean().getProductItemList();
				if(null != productItemBeans && productItemBeans.size() > 0){

					productItemService.deleteWorksheetIdAndProductType(worksheet.getId(), TYPE_SERVICE);
					for(ProductItemBean productItemBean:productItemBeans){
						ProductItem productItem = new ProductItem();
						productItem.setProductType(TYPE_SERVICE);
						productItem.setPrice(productItemBean.getServiceProductBean().getPrice());
						ServiceProduct serviceProduct = serviceProductService.getServiceProductById(productItemBean.getServiceProductBean().getId());
						productItem.setServiceProduct(serviceProduct);
						productItem.setWorkSheet(worksheet);
						productItem.setServiceApplication(serviceApplication);
						productItem.setCreateDate(CURRENT_TIMESTAMP);
						productItem.setCreatedBy(getUserNameLogin());
						productItemService.save(productItem);
					}
					
				}
				
				serviceApplication.setStatus("I"); // I = ยกเลิกใช้งาน
				serviceApplication.setCancelServiceDate(CURRENT_TIMESTAMP);
				serviceApplication.setUpdatedBy(getUserNameLogin());
				serviceApplication.setUpdatedDate(CURRENT_TIMESTAMP);
				serviceApplication.setRefund(worksheetUpdateSnapShotBean.getCutWorksheetBean().getServiceApplication().getRefund());
				serviceApplication.setFlagRefund(worksheetUpdateSnapShotBean.getCutWorksheetBean().getServiceApplication().isFlagRefund());
				serviceApplicationService.update(serviceApplication);
				
				
				Customer customer = serviceApplication.getCustomer();
				// เช็คลูกค้าคนนี้ serviceApplications มีที่ใช้งานอยู่ไหม
				List<ServiceApplication> serviceApplications = customer.getServiceApplications();
				Boolean active = false;
				if(null != serviceApplications && serviceApplications.size() > 0){
					for(ServiceApplication serviceApp:serviceApplications){
						if("A".equals(serviceApp.getStatus())){
							active = true;
						}
					}
				}
				
				if(!active){
					customer.setActive(Boolean.FALSE);
					customerService.update(customer);
				}
				
				// เช็คเกิน 15 วัน
//				Date orderBillThDate = null;
//				List<Worksheet> worksheets = serviceApplication.getWorksheets();
//				if(null != worksheets && worksheets.size() > 0){
//					for(Worksheet wo:worksheets){
//						if(null != wo.getDateOrderBill() && null != wo.getWorksheetSetup()){
//							orderBillThDate = wo.getDateOrderBill();
//						}
//					}
//				}
				
//				if(null != worksheet){
//					Date currentDate = new Date();
//					
//					Calendar c = Calendar.getInstance();
//			        c.setTime(orderBillThDate);
//			        c.add(Calendar.DAY_OF_MONTH, 15);
//			        
//			        orderBillThDate = c.getTime();
//			        
//			        Calendar cal = Calendar.getInstance();
//			        cal.setTime(currentDate);
//			        // Set time fields to zero
//			        cal.set(Calendar.HOUR_OF_DAY, 0);
//			        cal.set(Calendar.MINUTE, 0);
//			        cal.set(Calendar.SECOND, 0);
//			        cal.set(Calendar.MILLISECOND, 0);
//			        currentDate = cal.getTime();
//					
//			        logger.info("orderBillThDate : "+orderBillThDate);
//			        logger.info("currentDate : "+currentDate);
//			        logger.info("orderBillThDate before : "+orderBillThDate.before(currentDate));
//			        logger.info("orderBillThDate after : "+orderBillThDate.after(currentDate));
//			        
//			        if(orderBillThDate.before(currentDate)){ // เกิน 15 วัน
//			        	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("en", "US"));
//			        	String orderBillThStr = format.format(orderBillThDate);
//			        	createManualInvoiceCutWorksheet(serviceApplication.getId(), orderBillThStr, worksheetCut);
//			        }
//			        
//				}
				
			}

		} catch (Exception ex) {
			success = false;
		}
		return success;
	}
	
	// create order bill

	public void createManualInvoiceCutWorksheet(Long serviceApplicationId, String orderBillDate,WorksheetCut worksheetCut) {
		logger.info("[method : createManualInvoiceCutWorksheet][Type : Controller]");
		logger.info("[method : createManualInvoiceCutWorksheet][serviceApplicationId : "+serviceApplicationId+"]");
			try {
				Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
					ServiceApplication serviceApplication = serviceApplicationService.getServiceApplicationById(serviceApplicationId);
					//invoice set value
					Invoice invoice = new Invoice();
					invoice.setInvoiceCode(financialService.genInVoiceCode());
					invoice.setAmount(serviceApplication.getMonthlyServiceFee() - worksheetCut.getSpecialDiscount());
					invoice.setServiceApplication(serviceApplication);
					//invoice type
					invoice.setInvoiceType(messageSource.getMessage("financial.invoice.type.order", null, LocaleContextHolder.getLocale()));
					//invoice status
					invoice.setStatus(messageSource.getMessage("financial.invoice.status.waitpay", null, LocaleContextHolder.getLocale()));
					//invoice.setIssueDocDate();
					//invoice.setPaymentDate();
					invoice.setDeleted(Boolean.FALSE);
					invoice.setCreatedBy(getUserNameLogin());
					if(!"".equals(orderBillDate)){
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("en", "US"));
						invoice.setCreateDate(format.parse(orderBillDate));
					}else{
						invoice.setCreateDate(CURRENT_TIMESTAMP);
					}
					invoice.setIssueDocDate(CURRENT_TIMESTAMP);
					invoice.setPaymentDate(financialService.getPaymentOrderDate(serviceApplication.getId()));
					invoice.setStatusScan("N");
					invoice.setCutting(Boolean.TRUE);
					
					//receipt set value
					Receipt receipt = new Receipt();
					receipt.setReceiptCode(financialService.genReceiptCode());
					receipt.setAmount(invoice.getAmount());
					receipt.setStatus(messageSource.getMessage("financial.receipt.status.hold", null, LocaleContextHolder.getLocale()));
					receipt.setIssueDocDate(CURRENT_TIMESTAMP);
					//receipt.setPaymentDate();
					receipt.setDeleted(Boolean.FALSE);
					receipt.setCreatedBy(getUserNameLogin());
					receipt.setCreateDate(CURRENT_TIMESTAMP);
					
					receipt.setInvoice(invoice);
					invoice.setReceipt(receipt);
					try {
						financialService.saveInvoice(invoice);
					} catch (Exception e) {
						e.printStackTrace();
					}

			} catch (Exception ex) {
				ex.printStackTrace();
			}
	}
	
	public boolean updateSnapAddPoint(WorksheetUpdateSnapShotBean worksheetUpdateSnapShotBean) {
		boolean success = true;
		try {
			success = updateHeadSnap(worksheetUpdateSnapShotBean);
			// call cut stock
			success = BeforeCutStock(worksheetUpdateSnapShotBean);
			// update add point worksheet
			WorksheetAddPoint worksheetAddPoint = workSheetService
					.getWorksheetAddPointById(worksheetUpdateSnapShotBean.getAddPointWorksheetBean().getId());
			if(success && (worksheetUpdateSnapShotBean.getType().equals("S") || worksheetUpdateSnapShotBean.getType().equals("U"))){
				if(worksheetAddPoint != null){
					//set value update
					worksheetAddPoint.setAnalogPoint(worksheetUpdateSnapShotBean.getAddPointWorksheetBean().getAnalogPoint());
					worksheetAddPoint.setDigitalPoint(worksheetUpdateSnapShotBean.getAddPointWorksheetBean().getDigitalPoint());
					worksheetAddPoint.setAddPointPrice(worksheetUpdateSnapShotBean.getAddPointWorksheetBean().getAddPointPrice());
					worksheetAddPoint.setMonthlyFree(worksheetUpdateSnapShotBean.getAddPointWorksheetBean().getMonthlyFree());
					worksheetAddPoint.setActive(Boolean.TRUE);
					workSheetService.updateWorksheetAddPoint(worksheetAddPoint);
					
					// update MonthlyServiceFee
					ServiceApplication serviceApplication = serviceApplicationService
							.getServiceApplicationById(worksheetUpdateSnapShotBean.getAddPointWorksheetBean().getServiceApplication().getId());
					serviceApplication.setMonthlyServiceFee(serviceApplication.getMonthlyServiceFee() + worksheetUpdateSnapShotBean.getAddPointWorksheetBean().getMonthlyFree());
					serviceApplicationService.update(serviceApplication);
				}
			}

		} catch (Exception ex) {
			success = false;
		}
		return success;
	}
	
	public boolean updateSnapAddSetTop(WorksheetUpdateSnapShotBean worksheetUpdateSnapShotBean) {
		Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
		boolean success = true;
		try {
			success = updateHeadSnap(worksheetUpdateSnapShotBean);
			// call cut stock
			success = BeforeCutStock(worksheetUpdateSnapShotBean);
			// update add settop worksheet
			WorksheetAddSetTopBox worksheetAddSetTopBox = workSheetService
					.getWorksheetAddSetTopBoxById(worksheetUpdateSnapShotBean.getAddSetTopBoxWorksheetBean().getId());
			//set value update
			if(success && worksheetAddSetTopBox != null){
				workSheetService.updateWorksheetAddSetTopBox(worksheetAddSetTopBox);
				
				if("S".equals(worksheetUpdateSnapShotBean.getType())){
					Worksheet worksheet = worksheetAddSetTopBox.getWorkSheet();
					if(null != worksheet){
						List<ProductItem> productItemList = worksheet.getProductItems();
						if(null != productItemList && productItemList.size() > 0){
							for(ProductItem productItem:productItemList){
								if("A".equals(productItem.getProductTypeMatch())){
									List<ProductItemWorksheet> productItemWorksheetList = productItem.getProductItemWorksheets();
									if(null != productItemWorksheetList && productItemWorksheetList.size() > 0){
										for(ProductItemWorksheet productItemWorksheet:productItemWorksheetList){
											if("A".equals(productItemWorksheet.getProductTypeMatch())){
												EquipmentProductItem eqmpItem = productItemWorksheet.getEquipmentProductItem();
												if(null != eqmpItem && (!eqmpItem.getSerialNo().isEmpty())){
													eqmpItem.setStatus(STATUS_SELL);
													eqmpItem.setSpare(0);
													eqmpItem.setReservations(0);
													eqmpItem.setBalance(0);
													eqmpItem.setUpdatedBy(getUserNameLogin());
													eqmpItem.setUpdatedDate(CURRENT_TIMESTAMP);
													equipmentProductItemService.update(eqmpItem);
													EquipmentProduct eqmp = eqmpItem.getEquipmentProduct();
													
													// อัพ product ตัวแม่
													if(null != eqmp){
														ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
														pro.setEquipmentProductService(equipmentProductService);
														pro.setMessageSource(messageSource);
														pro.autoUpdateStatusEquipmentProduct(eqmp);
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}

		} catch (Exception ex) {
			success = false;
		}
		return success;
	}
	
	public boolean updateSnapBorrow(WorksheetUpdateSnapShotBean worksheetUpdateSnapShotBean) {
		Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
		boolean success = true;
		try {
			success = updateHeadSnap(worksheetUpdateSnapShotBean);
			// call cut stock
			success = BeforeCutStock(worksheetUpdateSnapShotBean);
			// update add borrow worksheet
			WorksheetBorrow worksheetBorrow = workSheetService
					.getWorksheetBorrowById(worksheetUpdateSnapShotBean.getBorrowWorksheetBean().getId());
			//set value update
			if(worksheetBorrow != null){
				workSheetService.updateWorksheetBorrow(worksheetBorrow);
				
				if("S".equals(worksheetUpdateSnapShotBean.getType())){
					Worksheet worksheet = worksheetBorrow.getWorkSheet();
					if(null != worksheet){
						List<ProductItem> productItemList = worksheet.getProductItems();
						if(null != productItemList && productItemList.size() > 0){
							for(ProductItem productItem:productItemList){
								if("B".equals(productItem.getProductTypeMatch())){
									List<ProductItemWorksheet> productItemWorksheetList = productItem.getProductItemWorksheets();
									if(null != productItemWorksheetList && productItemWorksheetList.size() > 0){
										for(ProductItemWorksheet productItemWorksheet:productItemWorksheetList){
											if("B".equals(productItemWorksheet.getProductTypeMatch())){
												EquipmentProductItem eqmpItem = productItemWorksheet.getEquipmentProductItem();
												if(null != eqmpItem && (!eqmpItem.getSerialNo().isEmpty())){
													eqmpItem.setStatus(STATUS_LEND);
													eqmpItem.setSpare(0);
													eqmpItem.setReservations(0);
													eqmpItem.setBalance(0);
													eqmpItem.setUpdatedBy(getUserNameLogin());
													eqmpItem.setUpdatedDate(CURRENT_TIMESTAMP);
													equipmentProductItemService.update(eqmpItem);
													EquipmentProduct eqmp = eqmpItem.getEquipmentProduct();
													
													// อัพ product ตัวแม่
													if(null != eqmp){
														ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
														pro.setEquipmentProductService(equipmentProductService);
														pro.setMessageSource(messageSource);
														pro.autoUpdateStatusEquipmentProduct(eqmp);
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}

		} catch (Exception ex) {
			success = false;
		}
		return success;
	}
	
	public boolean updateSnapMove(WorksheetUpdateSnapShotBean worksheetUpdateSnapShotBean) {
		Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
		boolean success = true;
		try {
			success = updateHeadSnap(worksheetUpdateSnapShotBean);
			// call cut stock
			success = BeforeCutStock(worksheetUpdateSnapShotBean);
			// update move worksheet
			WorksheetMove worksheetMove = workSheetService
					.getWorksheetMoveById(worksheetUpdateSnapShotBean.getMoveWorksheetBean().getId());
			
			//set value update
			if(success && (worksheetUpdateSnapShotBean.getType().equals("S") || worksheetUpdateSnapShotBean.getType().equals("U")) ){
				ServiceApplication serviceApplication = serviceApplicationService
						.getServiceApplicationById(worksheetUpdateSnapShotBean.getMoveWorksheetBean().getServiceApplication().getId());
				worksheetMove.setAnalogPoint(worksheetUpdateSnapShotBean.getMoveWorksheetBean().getAnalogPoint());
				worksheetMove.setDigitalPoint(worksheetUpdateSnapShotBean.getMoveWorksheetBean().getDigitalPoint());
				worksheetMove.setMoveCablePrice(worksheetUpdateSnapShotBean.getMoveWorksheetBean().getMoveCablePrice());
	
				List<Address> addressList = serviceApplication.getAddresses();
				if(null != addressList && addressList.size() > 0){
					if(worksheetUpdateSnapShotBean.getType().equals("S")){
					List<AddressBean> addressBeanList = worksheetUpdateSnapShotBean.getMoveWorksheetBean().getAddressList();
					for(Address address:addressList){
						if((address.getAddressType().equals("3") ||
								address.getAddressType().equals("4")) && addressBeanList.size() > 0){	
						AddressBean addressBean = addressBeanList.get(0);
						address.setNo(addressBean.getNo());
						address.setSection(addressBean.getSection());
						address.setBuilding(addressBean.getBuilding());
						address.setRoom(addressBean.getRoom());
						address.setFloor(addressBean.getFloor());
						address.setVillage(addressBean.getVillage());
						address.setAlley(addressBean.getAlley());
						address.setRoad(addressBean.getRoad());
						
						Province provinceModel = addressService.getProvinceById(addressBean.getProvinceBean().getId());
						address.setProvinceModel(provinceModel);
						
						Amphur amphur = addressService.getAmphurById(addressBean.getAmphurBean().getId());
						address.setAmphur(amphur);
						
						District districtModel = addressService.getDistrictById(addressBean.getDistrictBean().getId());
						address.setDistrictModel(districtModel);
						
						address.setPostcode(addressBean.getPostcode());
						address.setNearbyPlaces(addressBean.getNearbyPlaces());
						
						address.setWorksheetMove(worksheetMove);
						
						Zone zoneOld = zoneService.getZoneById(address.getZone().getId());
						address.setZoneOld(zoneOld);
						
						Zone zone = zoneService.getZoneById(addressBean.getZoneBean().getId());
						address.setZone(zone);
						
						
						address.setServiceApplication(serviceApplication);
						address.setUpdatedDate(CURRENT_TIMESTAMP);
						address.setUpdatedBy(getUserNameLogin());
						
						addressService.updateAddress(address);
						}
					}

					
				addressList = worksheetMove.getAddresss();
				if(null != addressList && addressList.size() > 0 && addressBeanList.size() > 0){
					for(Address address:addressList){
						AddressBean addressBean = addressBeanList.get(0);
						address.setNo(addressBean.getNo());
						address.setSection(addressBean.getSection());
						address.setBuilding(addressBean.getBuilding());
						address.setRoom(addressBean.getRoom());
						address.setFloor(addressBean.getFloor());
						address.setVillage(addressBean.getVillage());
						address.setAlley(addressBean.getAlley());
						address.setRoad(addressBean.getRoad());
						
						Province provinceModel = addressService.getProvinceById(addressBean.getProvinceBean().getId());
						address.setProvinceModel(provinceModel);
						
						Amphur amphur = addressService.getAmphurById(addressBean.getAmphurBean().getId());
						address.setAmphur(amphur);
						
						District districtModel = addressService.getDistrictById(addressBean.getDistrictBean().getId());
						address.setDistrictModel(districtModel);
						
						address.setPostcode(addressBean.getPostcode());
						address.setNearbyPlaces(addressBean.getNearbyPlaces());
						
						address.setWorksheetMove(worksheetMove);
						
						Zone zone = zoneService.getZoneById(addressBean.getZoneBean().getId());
						address.setZone(zone);
						
						address.setServiceApplication(serviceApplication);
						address.setUpdatedDate(CURRENT_TIMESTAMP);
						address.setUpdatedBy(getUserNameLogin());
						
						addressService.updateAddress(address);
					}
				}
					
				}else if(worksheetUpdateSnapShotBean.getType().equals("U")){
					List<AddressBean> addressBeanList = worksheetUpdateSnapShotBean.getMoveWorksheetBean().getAddressList();
					for(Address address:addressList){
							if(address.getAddressType().equals("6") && addressBeanList.size() > 0){	
							AddressBean addressBean = addressBeanList.get(0);
							address.setNo(addressBean.getNo());
							address.setSection(addressBean.getSection());
							address.setBuilding(addressBean.getBuilding());
							address.setRoom(addressBean.getRoom());
							address.setFloor(addressBean.getFloor());
							address.setVillage(addressBean.getVillage());
							address.setAlley(addressBean.getAlley());
							address.setRoad(addressBean.getRoad());
							
							Province provinceModel = addressService.getProvinceById(addressBean.getProvinceBean().getId());
							address.setProvinceModel(provinceModel);
							
							Amphur amphur = addressService.getAmphurById(addressBean.getAmphurBean().getId());
							address.setAmphur(amphur);
							
							District districtModel = addressService.getDistrictById(addressBean.getDistrictBean().getId());
							address.setDistrictModel(districtModel);
							
							address.setPostcode(addressBean.getPostcode());
							address.setNearbyPlaces(addressBean.getNearbyPlaces());
							
							address.setWorksheetMove(worksheetMove);
							
							Zone zoneOld = zoneService.getZoneById(address.getZone().getId());
							address.setZoneOld(zoneOld);
							
							Zone zone = zoneService.getZoneById(addressBean.getZoneBean().getId());
							address.setZone(zone);
							
							
							address.setServiceApplication(serviceApplication);
							address.setUpdatedDate(CURRENT_TIMESTAMP);
							address.setUpdatedBy(getUserNameLogin());
							
							addressService.updateAddress(address);
						}
					}
				}
				
			}
				
				worksheetMove.setUpdatedBy(getUserNameLogin());
				worksheetMove.setUpdatedDate(CURRENT_TIMESTAMP);
				workSheetService.updateWorksheetMove(worksheetMove);
				
			}
		} catch (Exception ex) {
			success = false;
		}
		return success;
	}
	
	public boolean updateSnapMovePoint(WorksheetUpdateSnapShotBean worksheetUpdateSnapShotBean) {
		boolean success = true;
		try {
			success = updateHeadSnap(worksheetUpdateSnapShotBean);
			// call cut stock
			success = BeforeCutStock(worksheetUpdateSnapShotBean);
			// update move point worksheet
			WorksheetMovePoint worksheetMovePoint = workSheetService
					.getWorksheetMovePointById(worksheetUpdateSnapShotBean.getMovePointWorksheetBean().getId());
			// set value update
			if(success && (worksheetUpdateSnapShotBean.getType().equals("S") || worksheetUpdateSnapShotBean.getType().equals("U"))){
				if (worksheetMovePoint != null) {
					worksheetMovePoint.setAnalogPoint(worksheetUpdateSnapShotBean.getMovePointWorksheetBean().getAnalogPoint());
					worksheetMovePoint.setDigitalPoint(worksheetUpdateSnapShotBean.getMovePointWorksheetBean().getDigitalPoint());
					worksheetMovePoint.setMovePointPrice(worksheetUpdateSnapShotBean.getMovePointWorksheetBean().getMovePointPrice());
					workSheetService.updateWorksheetMovePoint(worksheetMovePoint);
				}
			}
		} catch (Exception ex) {
			success = false;
		}
		return success;
	}
	
	public boolean updateSnapReducePoint(WorksheetUpdateSnapShotBean worksheetUpdateSnapShotBean) {
		boolean success = true;
		try {
			success = updateHeadSnap(worksheetUpdateSnapShotBean);
			// call cut stock
			success = BeforeCutStock(worksheetUpdateSnapShotBean);
			// update reduce point worksheet
			WorksheetReducePoint worksheetReducePoint = workSheetService
					.getWorksheetReducePointById(worksheetUpdateSnapShotBean.getReducePointWorksheetBean().getId());
			// set value update
			if(success && (worksheetUpdateSnapShotBean.getType().equals("S"))){
				if (worksheetReducePoint != null) {
					worksheetReducePoint.setAnalogPoint(worksheetUpdateSnapShotBean.getReducePointWorksheetBean().getAnalogPoint());
					worksheetReducePoint.setDigitalPoint(worksheetUpdateSnapShotBean.getReducePointWorksheetBean().getDigitalPoint());
					worksheetReducePoint.setMonthlyFree(worksheetUpdateSnapShotBean.getReducePointWorksheetBean().getMonthlyFree());
					worksheetReducePoint.setActive(Boolean.TRUE);
					workSheetService.updateWorksheetReducePoint(worksheetReducePoint);
					
					// update MonthlyServiceFee
					ServiceApplication serviceApplication = serviceApplicationService
							.getServiceApplicationById(worksheetUpdateSnapShotBean.getReducePointWorksheetBean().getServiceApplication().getId());
					float monthlyServiceFee = serviceApplication.getMonthlyServiceFee() - worksheetUpdateSnapShotBean.getReducePointWorksheetBean().getMonthlyFree();
					serviceApplication.setMonthlyServiceFee(monthlyServiceFee < 0?0f:monthlyServiceFee);
					serviceApplicationService.update(serviceApplication);
					
				}
			}else if(success && worksheetUpdateSnapShotBean.getType().equals("U")){
				if (worksheetReducePoint != null) {
					worksheetReducePoint.setAnalogPoint(worksheetUpdateSnapShotBean.getReducePointWorksheetBean().getAnalogPoint());
					worksheetReducePoint.setDigitalPoint(worksheetUpdateSnapShotBean.getReducePointWorksheetBean().getDigitalPoint());
					worksheetReducePoint.setMonthlyFree(worksheetUpdateSnapShotBean.getReducePointWorksheetBean().getMonthlyFree());
					workSheetService.updateWorksheetReducePoint(worksheetReducePoint);
				}
			}

		} catch (Exception ex) {
			success = false;
		}
		return success;
	}
	
	public boolean updateSnapRepair(WorksheetUpdateSnapShotBean worksheetUpdateSnapShotBean) {
		Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
		boolean success = true;
		try {
			success = updateHeadSnap(worksheetUpdateSnapShotBean);
			// call cut stock
			success = BeforeCutStock(worksheetUpdateSnapShotBean);
			
			if("S".equals(worksheetUpdateSnapShotBean.getType())){
			//คัดลอกประวัติลง log repair
			Worksheet worksheet = workSheetService.getWorksheetById(worksheetUpdateSnapShotBean.getIdWorksheetParent());
			List<HistoryRepair> historyRepairList = new ArrayList<HistoryRepair>();
			for(ProductItem productItem : worksheet.getProductItems()){
				if("R".equals(productItem.getProductTypeMatch()) && "E".equals(productItem.getProductType())){
					for(ProductItemWorksheet productItemWorksheet : productItem.getProductItemWorksheets()){
						HistoryRepair historyRepair = new HistoryRepair();
						historyRepair.setPrice(productItemWorksheet.getPrice());
						historyRepair.setDeposit(productItemWorksheet.getDeposit());
						historyRepair.setCreateDate(CURRENT_TIMESTAMP);
						historyRepair.setCreatedBy(getUserNameLogin());
						historyRepair.setDeleted(productItemWorksheet.isDeleted());
						historyRepair.setLend(productItemWorksheet.isLend());
						historyRepair.setQuantity(productItemWorksheet.getQuantity());
						historyRepair.setFree(productItemWorksheet.isFree());
						historyRepair.setProductType(productItemWorksheet.getProductType());
						historyRepair.setAmount(productItemWorksheet.getAmount());
						historyRepair.setComment(productItemWorksheet.getComment());
						historyRepair.setParent(productItemWorksheet.getParent());
						historyRepair.setEquipmentProductItem(productItemWorksheet.getEquipmentProductItem());
						historyRepair.setWorkSheet(worksheet);
						historyRepairList.add(historyRepair);
					}
				}
			}
			worksheet.setHistoryRepairList(historyRepairList);
			//บันทึก log
			workSheetService.update(worksheet);
			
			//เปลี่ยนโปรดักเก่าเป็นซ่อม ย้ายโปรดักใหม่แทนที่ และลบ productItem ของใบงานปัจจุบัน
			Worksheet worksheetUpdate = workSheetService.getWorksheetById(worksheetUpdateSnapShotBean.getIdWorksheetParent());
			for(ProductItem productItem : worksheetUpdate.getProductItems()){
				if("R".equals(productItem.getProductTypeMatch()) && "E".equals(productItem.getProductType())){
					for(ProductItemWorksheet productItemWorksheet : productItem.getProductItemWorksheets()){
						//update โปรดักเดิมเป็น ซ่อม
						ProductItemWorksheet productItemWorksheetOLD = productItemWorksheetService.getProductItemWorksheetById(productItemWorksheet.getParent());
						EquipmentProductItem equipmentProductItemOld = productItemWorksheetOLD.getEquipmentProductItem();
						//เก็บ log การปรับสถานะ
						HistoryUpdateStatus historyUpdateStatus = new HistoryUpdateStatus();
						historyUpdateStatus.setCreateDate(CURRENT_TIMESTAMP);
						historyUpdateStatus.setCreatedBy(getUserNameLogin());
						historyUpdateStatus.setDeleted(Boolean.FALSE);
						historyUpdateStatus.setEquipmentProductItem(equipmentProductItemOld);
						historyUpdateStatus.setInformer(getUserNameLogin());
						historyUpdateStatus.setDateRepair(CURRENT_TIMESTAMP);
						historyUpdateStatus.setRecordDate(CURRENT_TIMESTAMP);
						historyUpdateStatus.setRemark(productItemWorksheet.getComment());
						historyUpdateStatus.setStatus(STATUS_REPAIR);
						//historyUpdateStatus.setPersonnel(personnel);
						List<HistoryUpdateStatus> historyUpdateStatusList = new ArrayList<HistoryUpdateStatus>();
						historyUpdateStatusList.add(historyUpdateStatus);
						equipmentProductItemOld.setHistoryUpdateStatuses(historyUpdateStatusList);
						
						//เก็บค่าเพื่อสำหรับอัพเดทแม่
						EquipmentProduct eqmp = equipmentProductItemOld.getEquipmentProduct();
						int statusProductOldTemp = equipmentProductItemOld.getStatus();
						equipmentProductItemOld.setStatus(STATUS_REPAIR);
						productService.updateProductItem(equipmentProductItemOld);
						
						//อัพเดทวันที่คืน เอาประวัติตัวล่าสุด
						if(equipmentProductItemOld.getHistoryUseEquipments().size()>0){
							HistoryUseEquipment historyUseEquipmentProductOld = historyUseEquipmentService.getHistoryUseEquipmentById(equipmentProductItemOld.getHistoryUseEquipments().
									get(equipmentProductItemOld.getHistoryUseEquipments().size()-1).getId());
							
							if(historyUseEquipmentProductOld != null){
								historyUseEquipmentProductOld.setReturnDate(CURRENT_TIMESTAMP);
								historyUseEquipmentService.update(historyUseEquipmentProductOld);
							}
							
						}
						
						//อัพเดทโปรดักใหม่สถานะ และประวัติการใช้งาน
						EquipmentProductItem equipmentProductItemNewTemp = productService.findEquipmentProductItemById(productItemWorksheet.getEquipmentProductItem().getId());
						if(equipmentProductItemNewTemp!=null){
							equipmentProductItemNewTemp.setStatus(statusProductOldTemp);
							HistoryUseEquipment historyUseEquipment = new HistoryUseEquipment();
							historyUseEquipment.setActiveDate(CURRENT_TIMESTAMP);
							historyUseEquipment.setCreateDate(CURRENT_TIMESTAMP);
							historyUseEquipment.setCreatedBy(getUserNameLogin());
							historyUseEquipment.setDeleted(Boolean.FALSE);
							historyUseEquipment.setCustomer(worksheetUpdate.getServiceApplication().getCustomer());
							historyUseEquipment.setEquipmentProductItem(equipmentProductItemNewTemp);
							historyUseEquipment.setServiceApplication(worksheetUpdate.getServiceApplication());
							String statusUserEquipment = "";
							
							if(equipmentProductItemOld.getHistoryUseEquipments().size()>0){
								statusUserEquipment = equipmentProductItemOld.getHistoryUseEquipments().
										get(equipmentProductItemOld.getHistoryUseEquipments().size()-1).getStatus();
							}
							historyUseEquipment.setStatus(statusUserEquipment);
							
							List<HistoryUseEquipment> historyUseEquipmentList = new ArrayList<HistoryUseEquipment>();
							historyUseEquipmentList.add(historyUseEquipment);
							equipmentProductItemNewTemp.setHistoryUseEquipments(historyUseEquipmentList);
							//productService.updateProductItem(equipmentProductItemNewTemp);
						}
						
						// อัพเดท product ตัวแม่
						if (null != eqmp) {
							ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
							pro.setEquipmentProductService(equipmentProductService);
							pro.setMessageSource(messageSource);
							pro.autoUpdateStatusEquipmentProduct(eqmp);
						}
						
						//ย้ายโปรดักใหม่แทนโปรดักเก่า
						EquipmentProductItem equipmentProductItemNew = productService.findEquipmentProductItemById(productItemWorksheet.getEquipmentProductItem().getId());
						equipmentProductItemNew.setStatus(STATUS_LEND);
						equipmentProductItemNew.setUpdatedBy(getUserNameLogin());
						equipmentProductItemNew.setUpdatedDate(CURRENT_TIMESTAMP);
						productService.updateProductItem(equipmentProductItemNew);
						
						ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
						pro.setEquipmentProductService(equipmentProductService);
						EquipmentProduct equ = equipmentProductItemNew.getEquipmentProduct();
						// อัพ product ตัวแม่
						if(null != equ){
							pro.autoUpdateStatusEquipmentProduct(equ);
						}
						
						ProductItemWorksheet productItemWorksheetOld = productItemWorksheetService.getProductItemWorksheetById(productItemWorksheet.getParent());
						productItemWorksheetOld.setEquipmentProductItem(equipmentProductItemNew);
						productItemWorksheetOld.setParent(null);
						productItemWorksheetService.update(productItemWorksheetOld);
						
						//ลบโปรดักเดิมออกจากใบงาน เพราะเราเอาไปเก็บใน repair log แล้ว เข้
						if (productItemWorksheet.getEquipmentProductItem() != null) {
							workSheetService.deleteProductItemWorksheet(productItemWorksheet);
						}
					}
				}
			}//end for
			
			//ลบ product item ออกทั้งหมด
			workSheetService.deleteProductItemWithWorksheetIdTypeO(worksheetUpdate.getId());
			
			if(null != worksheet){
				ServiceApplication serviceApplication = worksheet.getServiceApplication();
				if(null != serviceApplication){
					Customer customer = serviceApplication.getCustomer();
					// เช็คลูกค้าคนนี้ serviceApplications มีที่ใช้งานอยู่ไหม
					if(null != customer){
						List<ServiceApplication> serviceApplications = customer.getServiceApplications();
						Boolean active = false;
						if(null != serviceApplications && serviceApplications.size() > 0){
							for(ServiceApplication serviceApp:serviceApplications){
								if("A".equals(serviceApp.getStatus())){
									active = true;
								}
							}
						}
						customer.setActive(active);
						customer.setUpdatedBy(getUserNameLogin());
						customer.setUpdatedDate(CURRENT_TIMESTAMP);
						customerService.update(customer);
					}
				}
			}
			
		}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			success = false;
		}
		return success;
	}

	//End update snapshot method seperate type -------------------------//
	public boolean BeforeCutStock(WorksheetUpdateSnapShotBean worksheetUpdateSnapShotBean){
		boolean success = true;
		try{
			//cut stock when success type
			if(worksheetUpdateSnapShotBean.getType().equals("S")){
				Worksheet worksheetTemp = workSheetService.getWorksheetById(worksheetUpdateSnapShotBean.getIdWorksheetParent());
				//worksheetTemp.getProductItems(); //สำหรับส่งไปตัดสต๊อก
				
				if(worksheetUpdateSnapShotBean.getTypeWorksheet().equals("C_S")){
					//ตัดสต๊อก
					cutStock(worksheetTemp.getProductItems(), worksheetTemp);
				}else{
					cutStockProductTypeMatch(worksheetTemp.getProductItems(), worksheetTemp);
				}
				
				//update status service application
				ServiceApplication serviceApplication = worksheetTemp.getServiceApplication();
				serviceApplication.setStatus(messageSource.getMessage("serviceapplication.status.active", null, LocaleContextHolder.getLocale()));
				serviceApplicationService.update(serviceApplication);
			}
			
		}catch(Exception ex){
			success = false;
		}
		return success;
	}
	
	public void cutStock(List<ProductItem> productItems,Worksheet worksheet) throws Exception{
		logger.info("[method : cutStock]");
		if(null != productItems && productItems.size() > 0){
			for(ProductItem productItem:productItems){
			List<ProductItemWorksheet> productItemWorksheets = productItem.getProductItemWorksheets();
				if(null != productItemWorksheets && productItemWorksheets.size() > 0){
					for(ProductItemWorksheet productItemWorksheet:productItemWorksheets){
						if("I".equals(productItemWorksheet.getProductType())){
							InternetProductItem internetProductItem = productItemWorksheet.getInternetProductItem();
							if(null != internetProductItem){
								internetProductItem.setStatus(Integer.toString(STATUS_SELL));
								internetProductItemService.update(internetProductItem);
							}
						}
						
						if("E".equals(productItemWorksheet.getProductType())){
							EquipmentProductItem equipmentProductItem = productItemWorksheet.getEquipmentProductItem();
							if(null != equipmentProductItem){
								if(productItemWorksheet.isFree() || (!productItemWorksheet.isFree() && !productItemWorksheet.isLend())){// ตัดเลย
									if(!"".equals(equipmentProductItem.getSerialNo())){ //มี S/N update status item
										equipmentProductItem.setStatus(STATUS_SELL);
										equipmentProductItemService.update(equipmentProductItem);
									}else{
										int reservations = equipmentProductItem.getReservations();// จอง
										int quantity = productItemWorksheet.getQuantity(); // จำนวนจากหน้าจอ
										reservations = reservations - quantity;
										if(reservations < 0){
											reservations = reservations * (-1);
										}
										equipmentProductItem.setReservations(reservations); // ลดจำนวนที่จองไว้
										equipmentProductItemService.update(equipmentProductItem);
									}

									EquipmentProduct equipmentProduct = equipmentProductItem.getEquipmentProduct();
									ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
									pro.setEquipmentProductService(equipmentProductService);
									EquipmentProduct equ = equipmentProductService.getEquipmentProductById(equipmentProduct.getId());
									// อัพ product ตัวแม่
									if(null != equ){
										pro.autoUpdateStatusEquipmentProduct(equ);
									}

								}else if(productItemWorksheet.isLend()){// ยืม
									// กรณีที่ยืม จะมีเฉพาะ อุปกรณ์ที่มี SN
									// ตัดสต๊อก ให้ไปลด จำนวนที่ จองไว้จากตัวแม่ และบวกเพิ่ม จำนวนที่ยืม 
									equipmentProductItem.setStatus(STATUS_LEND);
									equipmentProductItem.setSpare(0);
									equipmentProductItem.setReservations(0);
									equipmentProductItem.setBalance(1);
									equipmentProductItemService.update(equipmentProductItem);
									
									EquipmentProduct equipmentProduct = equipmentProductItem.getEquipmentProduct();
									ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
									pro.setEquipmentProductService(equipmentProductService);
									EquipmentProduct equ = equipmentProductService.getEquipmentProductById(equipmentProduct.getId());
									// อัพ product ตัวแม่
									if(null != equ){
										pro.autoUpdateStatusEquipmentProduct(equ);
									}

								}
							}
						}
	
					}
				}
			}
		}
		
		//create history use equipment
		updateHistoryUseEquipmentProduct(worksheet);
		
	}
	
	public void cutStockProductTypeMatch(List<ProductItem> productItems,Worksheet worksheet) throws Exception{
		logger.info("[method : cutStock]");
		if(null != productItems && productItems.size() > 0){
			for(ProductItem productItem:productItems){
			List<ProductItemWorksheet> productItemWorksheets = productItem.getProductItemWorksheets();
				if(null != productItemWorksheets && productItemWorksheets.size() > 0){
					for(ProductItemWorksheet productItemWorksheet:productItemWorksheets){
						if("N".equals(productItemWorksheet.getProductTypeMatch())){
							if("I".equals(productItemWorksheet.getProductType())){
								InternetProductItem internetProductItem = productItemWorksheet.getInternetProductItem();
								if(null != internetProductItem){
									internetProductItem.setStatus(Integer.toString(STATUS_SELL));
									internetProductItemService.update(internetProductItem);
								}
							}
							
							if("E".equals(productItemWorksheet.getProductType())){
								EquipmentProductItem equipmentProductItem = productItemWorksheet.getEquipmentProductItem();
								if(null != equipmentProductItem){
									if(productItemWorksheet.isFree() || (!productItemWorksheet.isFree() && !productItemWorksheet.isLend())){// ตัดเลย
										if(!"".equals(equipmentProductItem.getSerialNo())){ //มี S/N update status item
											equipmentProductItem.setStatus(STATUS_SELL);
											equipmentProductItemService.update(equipmentProductItem);
										}else{
//											int reservations = equipmentProductItem.getReservations();// จอง
//											int quantity = productItemWorksheet.getQuantity(); // จำนวนจากหน้าจอ
//											reservations = reservations - quantity;
//											if(reservations < 0){
//												reservations = reservations * (-1);
//											}
//											equipmentProductItem.setReservations(reservations); // ลดจำนวนที่จองไว้
//											equipmentProductItemService.update(equipmentProductItem);
										}
	
										EquipmentProduct equipmentProduct = equipmentProductItem.getEquipmentProduct();
										ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
										pro.setEquipmentProductService(equipmentProductService);
										EquipmentProduct equ = equipmentProductService.getEquipmentProductById(equipmentProduct.getId());
										// อัพ product ตัวแม่
										if(null != equ){
											pro.autoUpdateStatusEquipmentProduct(equ);
										}
	
									}else if(productItemWorksheet.isLend()){// ยืม
										// กรณีที่ยืม จะมีเฉพาะ อุปกรณ์ที่มี SN
										// ตัดสต๊อก ให้ไปลด จำนวนที่ จองไว้จากตัวแม่ และบวกเพิ่ม จำนวนที่ยืม 
										equipmentProductItem.setStatus(STATUS_LEND);
										equipmentProductItem.setSpare(0);
										equipmentProductItem.setReservations(0);
										equipmentProductItem.setBalance(1);
										equipmentProductItemService.update(equipmentProductItem);
										
										EquipmentProduct equipmentProduct = equipmentProductItem.getEquipmentProduct();
										ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
										pro.setEquipmentProductService(equipmentProductService);
										EquipmentProduct equ = equipmentProductService.getEquipmentProductById(equipmentProduct.getId());
										// อัพ product ตัวแม่
										if(null != equ){
											pro.autoUpdateStatusEquipmentProduct(equ);
										}
	
									}
								}
							}
						}
					}
				}
			}
		}
		
		//create history use equipment
		updateHistoryUseEquipmentProduct(worksheet);
		
	}
	
	public void updateHistoryUseEquipmentProduct(Worksheet worksheet){
		Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
		if(worksheet != null){
			for(ProductItem productItem : worksheet.getProductItems()){
				for(ProductItemWorksheet productItemWorksheet : productItem.getProductItemWorksheets()){
					//check product type Equipment
					if("E".equals(productItemWorksheet.getProductType())){
						EquipmentProductItem equipmentProductItem = productItemWorksheet.getEquipmentProductItem();
						//check s/n not empty
						if(equipmentProductItem != null && !equipmentProductItem.getSerialNo().isEmpty()){
							//update history user equipment
							HistoryUseEquipment hisUseEq = new HistoryUseEquipment();
							hisUseEq.setCustomer(productItem.getServiceApplication().getCustomer());
							hisUseEq.setServiceApplication(productItem.getServiceApplication());
							hisUseEq.setEquipmentProductItem(equipmentProductItem);
							hisUseEq.setActiveDate(CURRENT_TIMESTAMP);
							hisUseEq.setCreateDate(CURRENT_TIMESTAMP);
							hisUseEq.setCreatedBy(getUserNameLogin());
							hisUseEq.setDeleted(Boolean.FALSE);
							if(productItemWorksheet.isLend()){
								hisUseEq.setStatus("L"); // ยืม
							}else if(productItemWorksheet.isFree()){
								hisUseEq.setStatus("F"); // ฟรี
							}else{
								hisUseEq.setStatus("O"); // ขายขาด
							}
							//save log
							try {
								historyUseEquipmentService.save(hisUseEq);
							} catch (Exception e) {
								e.printStackTrace();
							}
							
						}
					}
					
				}
			}
			
		}//end if
	}
	
	@RequestMapping(value = "insertAndUpdateEquipmentProductItem", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse insertAndUpdateEquipmentProductItem(
			@RequestBody final WorksheetUpdateSnapShotBean worksheetUpdateSnapShotBean,HttpServletRequest request) {
		logger.info("[method : insertAndUpdateEquipmentProductItem][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		// create timestamp
		Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());

		if (isPermission()) {
			try {
				Worksheet worksheet = workSheetService.getWorksheetById(worksheetUpdateSnapShotBean.getIdWorksheetParent());
				if(null != worksheet){
					//update product item---------------------------------------------------------//
					// product item
					List<ProductItem> productItemList = new ArrayList<ProductItem>();
					//loop delete old product
					for(ProductItem productItem : worksheet.getProductItems()){
						if(productItem.getProductTypeMatch().equals("N")){
						for(ProductItemWorksheet productItemWorksheet : productItem.getProductItemWorksheets()){
							// update temp item
							EquipmentProductItem equipmentProductItemTempDel = productService.findEquipmentProductItemById(
									productItemWorksheet.getEquipmentProductItem() != null ? productItemWorksheet.getEquipmentProductItem().getId() : -1);
							
							if(equipmentProductItemTempDel != null && (!equipmentProductItemTempDel.getSerialNo().isEmpty())){									
								equipmentProductItemTempDel.setReservations(0);
								equipmentProductItemTempDel.setStatus(STATUS_ACTIVE);
								equipmentProductItemTempDel.setBalance(1);

								productService.updateProductItem(equipmentProductItemTempDel);
								
							}else if(equipmentProductItemTempDel != null && (equipmentProductItemTempDel.getSerialNo().isEmpty())){
								equipmentProductItemTempDel.setReservations(equipmentProductItemTempDel.getReservations() - productItemWorksheet.getQuantity());
								equipmentProductItemTempDel.setBalance(equipmentProductItemTempDel.getBalance() + productItemWorksheet.getQuantity());
								if(equipmentProductItemTempDel.getBalance() < 0){
									equipmentProductItemTempDel.setBalance(0);
								}
								if(equipmentProductItemTempDel.getReservations() < 0){
									equipmentProductItemTempDel.setReservations(0);
								}
								
								productService.updateProductItem(equipmentProductItemTempDel);

								EquipmentProduct eqmp = equipmentProductItemTempDel.getEquipmentProduct();
								
								// อัพ product ตัวแม่
								if(null != eqmp){
									ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
									pro.setEquipmentProductService(equipmentProductService);
									pro.setMessageSource(messageSource);
									pro.autoUpdateStatusEquipmentProduct(eqmp);
								}
								
							}
							if(productItemWorksheet.getEquipmentProductItem() != null){
								workSheetService.deleteProductItemWorksheet(productItemWorksheet);
							}
						}
					}
					}
					//delete product item old
					workSheetService.deleteProductItemWithWorksheetIdTypeN(worksheet.getId());
					//save product item new
					EquipmentProduct equipmentProduct = new EquipmentProduct();
					for (ProductItemBean productItemBean : worksheetUpdateSnapShotBean.getProductItemBeanList()) {
						ProductItem ProductItem = new ProductItem();
						if (productItemBean.getType().equals("E")) {
							ProductItem.setCreateDate(CURRENT_TIMESTAMP);
							ProductItem.setCreatedBy(getUserNameLogin());
							ProductItem.setDeleted(Boolean.FALSE);
							ProductItem.setLend(Boolean.FALSE);
							ProductItem.setFree(Boolean.FALSE);
							ProductItem.setProductType(TYPE_EQUIMENT);
							ProductItem.setProductTypeMatch("N");
							ProductItem.setServiceApplication(worksheet.getServiceApplication());
							// set equipment
							equipmentProduct = equipmentProductService
									.getEquipmentProductById(productItemBean.getId());
							ProductItem.setEquipmentProduct(equipmentProduct);
							ProductItem.setWorkSheet(worksheet);
							// worksheet item
							List<ProductItemWorksheet> productItemWorksheetList = new ArrayList<ProductItemWorksheet>();
							for (ProductItemWorksheetBean productItemWorksheetBean : productItemBean
									.getProductItemWorksheetBeanList()) {
								ProductItemWorksheet ProductItemWorksheet = new ProductItemWorksheet();
								ProductItemWorksheet.setProductItem(ProductItem);
								ProductItemWorksheet.setPrice(productItemWorksheetBean.getPrice());
								ProductItemWorksheet.setCreateDate(CURRENT_TIMESTAMP);
								ProductItemWorksheet.setCreatedBy(getUserNameLogin());
								ProductItemWorksheet.setDeleted(Boolean.FALSE);
								ProductItemWorksheet.setLend(Boolean.FALSE);
								ProductItemWorksheet.setFree(productItemWorksheetBean.isFree());
								ProductItemWorksheet.setQuantity(productItemWorksheetBean.getQuantity());
								ProductItemWorksheet.setProductType(TYPE_EQUIMENT);
								ProductItemWorksheet.setProductTypeMatch("N");
								if(!ProductItemWorksheet.isFree() && !ProductItemWorksheet.isLend()){
									ProductItemWorksheet.setAmount(productItemWorksheetBean.getQuantity() * productItemWorksheetBean.getPrice());
								}else{
									ProductItemWorksheet.setAmount(0f);
								}
								// update temp item
								EquipmentProductItem equipmentProductItemTemp = productService.findEquipmentProductItemById(
										productItemWorksheetBean.getEquipmentProductItemBean().getId());

								if (equipmentProductItemTemp != null && (!equipmentProductItemTemp.getSerialNo().isEmpty())) {

									if(equipmentProductItemTemp.getSpare() > 0){
										equipmentProductItemTemp.setSpare(0);
									}
									equipmentProductItemTemp.setStatus(STATUS_HOLD);
									equipmentProductItemTemp.setBalance(1);
									equipmentProductItemTemp.setReservations(1);

									productService.updateProductItem(equipmentProductItemTemp);

								} else if (equipmentProductItemTemp != null
										&& (equipmentProductItemTemp.getSerialNo().isEmpty())) {
									// update balance
									int quantity = productItemWorksheetBean.getQuantity();
									int spare = equipmentProductItemTemp.getSpare();
									int balance = equipmentProductItemTemp.getBalance();
									// นำ quantity มาลบออกจาก spare ก่อน
									spare -= quantity;
									if(spare < 0){ // เช็คว่าจำนวนที่ spare มีอยู่พอไหม
										equipmentProductItemTemp.setSpare(0); // ถ้าไม่พอจะ set spare = 0
										spare *= -1; // แล้วจำนวนที่ยังหักออกจาก spare ไม่หมดมาเก็บไว้ใน ตัวแปล spare เพื่อไปหักออกจาก balance ต่อ
										balance -= spare;
										equipmentProductItemTemp.setBalance(balance);
									}else{
										equipmentProductItemTemp.setSpare(spare);
									}
									equipmentProductItemTemp.setReservations(quantity);
									
									productService.updateProductItem(equipmentProductItemTemp);
								}
								// load product item
								EquipmentProductItem equipmentProductItem = productService.findEquipmentProductItemById(
										productItemWorksheetBean.getEquipmentProductItemBean().getId());

								ProductItemWorksheet.setEquipmentProductItem(equipmentProductItem);
								productItemWorksheetList.add(ProductItemWorksheet);
							}
							ProductItem.setProductItemWorksheets(productItemWorksheetList);
							
						} else if (productItemBean.getType().equals("S")) {
							ProductItem.setCreateDate(CURRENT_TIMESTAMP);
							ProductItem.setCreatedBy(getUserNameLogin());
							ProductItem.setDeleted(Boolean.FALSE);
							ProductItem.setLend(Boolean.FALSE);
							ProductItem.setFree(productItemBean.isFree());
							ProductItem.setProductType(TYPE_SERVICE);
							ProductItem.setPrice(productItemBean.getPrice());
							ProductItem.setQuantity(productItemBean.getQuantity());
							ProductItem.setAmount(productItemBean.getPrice()*productItemBean.getQuantity());
							ProductItem.setProductTypeMatch("N");
							ProductItem.setServiceApplication(worksheet.getServiceApplication());
							// set equipment
							ServiceProduct serviceProduct = serviceProductService
									.getServiceProductById(productItemBean.getId());
							ProductItem.setServiceProduct(serviceProduct);
							List<ProductItemWorksheet> productItemWorksheetList = new ArrayList<ProductItemWorksheet>();
							ProductItem.setProductItemWorksheets(productItemWorksheetList);
							ProductItem.setWorkSheet(worksheet);
						}
						productItemList.add(ProductItem);
						// อัพ product ตัวแม่
						if(null != equipmentProduct){
							ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
							pro.setEquipmentProductService(equipmentProductService);
							pro.setMessageSource(messageSource);
							pro.autoUpdateStatusEquipmentProduct(equipmentProduct);
						}
					}//end for
					
					worksheet.setProductItems(productItemList);
					worksheet.setUpdatedDate(CURRENT_TIMESTAMP);
					worksheet.setUpdatedBy(getUserNameLogin());
					//update worksheet
					worksheet.setRemark(worksheetUpdateSnapShotBean.getRemark());
					workSheetService.update(worksheet);
					
					//update invoice amount
					autoUpdateInvoice(worksheet);
				}

				jsonResponse.setError(false);
				
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
	
	@RequestMapping(value = "insertAndUpdateEquipmentProductItemAll", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse insertAndUpdateEquipmentProductItemAll(
			@RequestBody final WorksheetUpdateSnapShotBean worksheetUpdateSnapShotBean, HttpServletRequest request) {
		logger.info("[method : insertAndUpdateEquipmentProductItemAll][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		// create timestamp
		
		Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
		if (isPermission()) {
			try {
				Worksheet worksheet = workSheetService.getWorksheetById(worksheetUpdateSnapShotBean.getIdWorksheetParent());
				if(null != worksheet){
					//update product item---------------------------------------------------------//
					// product item
					List<ProductItem> productItemList = new ArrayList<ProductItem>();
					for(ProductItem productItem : worksheet.getProductItems()){
						if(!"R".equals(productItem.getProductTypeMatch()) && !"A".equals(productItem.getProductTypeMatch()) && !"B".equals(productItem.getProductTypeMatch())){
						for(ProductItemWorksheet productItemWorksheet : productItem.getProductItemWorksheets()){
							if(productItemWorksheet.getProductType().equals("E")){
								// update temp item
								EquipmentProductItem equipmentProductItemTempDel = productService.findEquipmentProductItemById(
										productItemWorksheet.getEquipmentProductItem() != null ? productItemWorksheet.getEquipmentProductItem().getId() : -1);
								
								if(equipmentProductItemTempDel != null && (!equipmentProductItemTempDel.getSerialNo().isEmpty())){ // มี SN								
									// อัพเดทใบเบิก นำจำนวนกลับคืน
									RequisitionItem requisitionItem = productItemWorksheet.getRequisitionItem();
									if(null != requisitionItem){
										if(null != requisitionItem){
											requisitionItem.setReturnEquipmentProductItem(0);
											requisitionItem.setUpdatedBy(getUserNameLogin());
											requisitionItem.setUpdatedDate(CURRENT_TIMESTAMP);
											requisitionItemService.update(requisitionItem);		
											
											equipmentProductItemTempDel.setStatus(STATUS_RESERVE);
											equipmentProductItemTempDel.setSpare(1);
										}
									}else{
										equipmentProductItemTempDel.setStatus(STATUS_ACTIVE);
										equipmentProductItemTempDel.setBalance(1);
									}
									equipmentProductItemTempDel.setReservations(0);

									productService.updateProductItem(equipmentProductItemTempDel);
									
									EquipmentProduct eqmp = equipmentProductItemTempDel.getEquipmentProduct();
									
									// อัพ product ตัวแม่
									if(null != eqmp){
										ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
										pro.setEquipmentProductService(equipmentProductService);
										pro.setMessageSource(messageSource);
										pro.autoUpdateStatusEquipmentProduct(eqmp);
									}
									
								}else if(equipmentProductItemTempDel != null && (equipmentProductItemTempDel.getSerialNo().isEmpty())){  // ไม่มี SN
									// อัพเดทใบเบิก นำจำนวนกลับคืน
									RequisitionItem requisitionItem = productItemWorksheet.getRequisitionItem();
									if(null != requisitionItem){
										if(null != requisitionItem){
											requisitionItem.setReturnEquipmentProductItem(0);
											requisitionItem.setUpdatedBy(getUserNameLogin());
											requisitionItem.setUpdatedDate(CURRENT_TIMESTAMP);
											requisitionItemService.update(requisitionItem);		
											
											equipmentProductItemTempDel.setSpare(equipmentProductItemTempDel.getSpare() + productItemWorksheet.getQuantity());
										}
									}else{
										equipmentProductItemTempDel.setBalance(equipmentProductItemTempDel.getBalance() + productItemWorksheet.getQuantity());
										if(equipmentProductItemTempDel.getBalance() > equipmentProductItemTempDel.getNumberImport()){
											equipmentProductItemTempDel.setBalance(equipmentProductItemTempDel.getNumberImport());
										}
									}
										
									equipmentProductItemTempDel.setReservations(equipmentProductItemTempDel.getReservations() - productItemWorksheet.getQuantity());
									if(equipmentProductItemTempDel.getReservations() < 0){
										equipmentProductItemTempDel.setReservations(0);
									}
										
									
									productService.updateProductItem(equipmentProductItemTempDel);
									
									EquipmentProduct eqmp = equipmentProductItemTempDel.getEquipmentProduct();
									
									// อัพ product ตัวแม่
									if(null != eqmp){
										ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
										pro.setEquipmentProductService(equipmentProductService);
										pro.setMessageSource(messageSource);
										pro.autoUpdateStatusEquipmentProduct(eqmp);
									}

								}	
								
								if(productItemWorksheet.getEquipmentProductItem() != null){
									workSheetService.deleteProductItemWorksheet(productItemWorksheet);
								}
							}else if(productItemWorksheet.getProductType().equals("I")){
								InternetProductItem internetProductItem = internetProductItemService.getInternetProductItemById(productItemWorksheet.getInternetProductItem().getId());
								internetProductItem.setStatus("0"); //ปกติ
								internetProductItemService.update(internetProductItem);
								if(internetProductItem != null){
									workSheetService.deleteProductItemWorksheet(productItemWorksheet);
								}
							}
							 
						}
					}
					}
					//delete product item old
					workSheetService.deleteProductItemWithWorksheetIdAll(worksheet.getId());
					//save product item new
					EquipmentProduct equipmentProduct = new EquipmentProduct();
					for (ProductItemBean productItemBean : worksheetUpdateSnapShotBean.getProductItemBeanList()) {
						ProductItem ProductItem = new ProductItem();
						if("C_S".equals(worksheet.getWorkSheetType())){
							ProductItem.setProductTypeMatch("O"); //O=โปรดักที่เกิดตอนสร้างใบงาน, N=โปรดักที่เกิดตอนเพิ่มอุปกรณ์เพิ่มตอน
						}else{
							ProductItem.setProductTypeMatch("N"); //O=โปรดักที่เกิดตอนสร้างใบงาน, N=โปรดักที่เกิดตอนเพิ่มอุปกรณ์เพิ่มตอน
						}
						if (productItemBean.getType().equals("E")) {
							int quantityProductItem = 0;
							ProductItem.setCreateDate(CURRENT_TIMESTAMP);
							ProductItem.setCreatedBy(getUserNameLogin());
							ProductItem.setDeleted(Boolean.FALSE);
							ProductItem.setLend(Boolean.FALSE);
							ProductItem.setFree(Boolean.FALSE);
							ProductItem.setProductType(TYPE_EQUIMENT);
							ProductItem.setServiceApplication(worksheet.getServiceApplication());
							// set equipment
							equipmentProduct = equipmentProductService
									.getEquipmentProductById(productItemBean.getId());
							ProductItem.setEquipmentProduct(equipmentProduct);
							ProductItem.setWorkSheet(worksheet);
							// worksheet item
							List<ProductItemWorksheet> productItemWorksheetList = new ArrayList<ProductItemWorksheet>();
							for (ProductItemWorksheetBean productItemWorksheetBean : productItemBean
									.getProductItemWorksheetBeanList()) {
								ProductItemWorksheet ProductItemWorksheet = new ProductItemWorksheet();
								if("C_S".equals(worksheet.getWorkSheetType())){
									ProductItemWorksheet.setProductTypeMatch("O"); //O=โปรดักที่เกิดตอนสร้างใบงาน, N=โปรดักที่เกิดตอนเพิ่มอุปกรณ์เพิ่มตอน
								}else{
									ProductItemWorksheet.setProductTypeMatch("N"); //O=โปรดักที่เกิดตอนสร้างใบงาน, N=โปรดักที่เกิดตอนเพิ่มอุปกรณ์เพิ่มตอน
								}
								quantityProductItem += productItemWorksheetBean.getQuantity();
								ProductItemWorksheet.setProductItem(ProductItem);
								ProductItemWorksheet.setPrice(productItemWorksheetBean.getPrice());
								ProductItemWorksheet.setCreateDate(CURRENT_TIMESTAMP);
								ProductItemWorksheet.setCreatedBy(getUserNameLogin());
								ProductItemWorksheet.setDeleted(Boolean.FALSE);
								ProductItemWorksheet.setLend(productItemWorksheetBean.isLend());
								ProductItemWorksheet.setFree(productItemWorksheetBean.isFree());
								ProductItemWorksheet.setQuantity(productItemWorksheetBean.getQuantity());
								ProductItemWorksheet.setProductType(TYPE_EQUIMENT);
								if(!ProductItemWorksheet.isFree() && !ProductItemWorksheet.isLend()){
									ProductItemWorksheet.setAmount(productItemWorksheetBean.getQuantity() * productItemWorksheetBean.getPrice());
								}else{
									ProductItemWorksheet.setAmount(0f);
								}

								// update temp item
								EquipmentProductItem equipmentProductItemTemp = productService.findEquipmentProductItemById(
										productItemWorksheetBean.getEquipmentProductItemBean().getId());

								if (equipmentProductItemTemp != null
										&& (!equipmentProductItemTemp.getSerialNo().isEmpty())) {  // มี SN
									
									// อัพเดทใบเบิก นำจำนวนกลับคืน
									RequisitionItemBean requisitionItemBean = productItemWorksheetBean.getRequisitionItemBean();
									if(null != requisitionItemBean && requisitionItemBean.getId() != null && requisitionItemBean.getId() > 0){
										RequisitionItem requisitionItem = requisitionItemService.getRequisitionItemById(requisitionItemBean.getId());
										if(null != requisitionItem){
											requisitionItem.setReturnEquipmentProductItem(productItemWorksheetBean.getQuantity());
											requisitionItem.setUpdatedBy(getUserNameLogin());
											requisitionItem.setUpdatedDate(CURRENT_TIMESTAMP);
											requisitionItemService.update(requisitionItem);
											
											ProductItemWorksheet.setRequisitionItem(requisitionItem);
											
											equipmentProductItemTemp.setSpare(0);
										}else{
											equipmentProductItemTemp.setBalance(0);
										}
									}else{
										equipmentProductItemTemp.setBalance(0);
									}
									
									equipmentProductItemTemp.setStatus(STATUS_HOLD);
									equipmentProductItemTemp.setReservations(1);

									productService.updateProductItem(equipmentProductItemTemp);
									
									EquipmentProduct eqmp = equipmentProductItemTemp.getEquipmentProduct();
									// อัพ product ตัวแม่
									if(null != eqmp){
										ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
										pro.setEquipmentProductService(equipmentProductService);
										pro.setMessageSource(messageSource);
										pro.autoUpdateStatusEquipmentProduct(eqmp);
									}

								} else if (equipmentProductItemTemp != null
										&& (equipmentProductItemTemp.getSerialNo().isEmpty())) { // ไม่มี SN
									
									int quantity = productItemWorksheetBean.getQuantity();
									int spare = equipmentProductItemTemp.getSpare();
									int balance = equipmentProductItemTemp.getBalance();
									int reservations = equipmentProductItemTemp.getReservations();
									// อัพเดทใบเบิก นำจำนวนกลับคืน
									RequisitionItemBean requisitionItemBean = productItemWorksheetBean.getRequisitionItemBean();
									if(null != requisitionItemBean && requisitionItemBean.getId() != null && requisitionItemBean.getId() > 0){
										RequisitionItem requisitionItem = requisitionItemService.getRequisitionItemById(requisitionItemBean.getId());
										if(null != requisitionItem){
											int returnEquipmentProductItem = requisitionItem.getReturnEquipmentProductItem();
											returnEquipmentProductItem = returnEquipmentProductItem - productItemWorksheetBean.getQuantity();
											if(returnEquipmentProductItem < 0){
												returnEquipmentProductItem = returnEquipmentProductItem * (-1);
											}
											requisitionItem.setReturnEquipmentProductItem(returnEquipmentProductItem);
											requisitionItem.setUpdatedBy(getUserNameLogin());
											requisitionItem.setUpdatedDate(CURRENT_TIMESTAMP);
											requisitionItemService.update(requisitionItem);
											
											ProductItemWorksheet.setRequisitionItem(requisitionItem);
											spare = spare - quantity;	
											if(spare < 0){
												spare = spare * (-1);
											}
											equipmentProductItemTemp.setSpare(spare);
										}else{
											balance = balance - quantity;
											if(balance < 0){
												balance = balance * (-1);
											}
											equipmentProductItemTemp.setBalance(balance);
										}
									}else{
										balance = balance - quantity;
										if(balance < 0){
											balance = balance * (-1);
										}
										equipmentProductItemTemp.setBalance(balance);
									}
									equipmentProductItemTemp.setReservations(reservations + quantity);
									productService.updateProductItem(equipmentProductItemTemp);
									
									EquipmentProduct eqmp = equipmentProductItemTemp.getEquipmentProduct();
									// อัพ product ตัวแม่
									if(null != eqmp){
										ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
										pro.setEquipmentProductService(equipmentProductService);
										pro.setMessageSource(messageSource);
										pro.autoUpdateStatusEquipmentProduct(eqmp);
									}

								}
								// load product item
								EquipmentProductItem equipmentProductItem = productService.findEquipmentProductItemById(
										productItemWorksheetBean.getEquipmentProductItemBean().getId());

								ProductItemWorksheet.setEquipmentProductItem(equipmentProductItem);
								productItemWorksheetList.add(ProductItemWorksheet);
							}
							ProductItem.setProductItemWorksheets(productItemWorksheetList);
							ProductItem.setQuantity(quantityProductItem);
						} else if (productItemBean.getType().equals("S")) {
							ProductItem.setCreateDate(CURRENT_TIMESTAMP);
							ProductItem.setCreatedBy(getUserNameLogin());
							ProductItem.setDeleted(Boolean.FALSE);
							ProductItem.setLend(Boolean.FALSE);
							ProductItem.setFree(productItemBean.isFree());
							ProductItem.setProductType(TYPE_SERVICE);
							ProductItem.setPrice(productItemBean.getPrice());
							ProductItem.setQuantity(productItemBean.getQuantity());
							ProductItem.setAmount(productItemBean.getPrice()*productItemBean.getQuantity());
							ProductItem.setServiceApplication(worksheet.getServiceApplication());
							// set equipment
							ServiceProduct serviceProduct = serviceProductService.getServiceProductById(productItemBean.getId());
							ProductItem.setServiceProduct(serviceProduct);
							List<ProductItemWorksheet> productItemWorksheetList = new ArrayList<ProductItemWorksheet>();
							ProductItem.setProductItemWorksheets(productItemWorksheetList);
							ProductItem.setWorkSheet(worksheet);
						}else if(productItemBean.getType().equals("I")){
							int quantityProductItem = 0;
							ProductItem.setCreateDate(CURRENT_TIMESTAMP);
							ProductItem.setCreatedBy(getUserNameLogin());
							ProductItem.setDeleted(Boolean.FALSE);
							ProductItem.setLend(Boolean.FALSE);
							ProductItem.setFree(Boolean.FALSE);
							ProductItem.setProductType(TYPE_INTERNET_USER);
							ProductItem.setServiceApplication(worksheet.getServiceApplication());
							// set internet
							InternetProduct internetProduct = productService
									.findInternetProductById(productItemBean.getId());
							ProductItem.setInternetProduct(internetProduct);
							ProductItem.setWorkSheet(worksheet);
							// worksheet item
							List<ProductItemWorksheet> productItemWorksheetList = new ArrayList<ProductItemWorksheet>();
							for (ProductItemWorksheetBean productItemWorksheetBean : productItemBean
									.getProductItemWorksheetBeanList()) {
								ProductItemWorksheet ProductItemWorksheet = new ProductItemWorksheet();
								if("C_S".equals(worksheet.getWorkSheetType())){
									ProductItemWorksheet.setProductTypeMatch("O"); //O=โปรดักที่เกิดตอนสร้างใบงาน, N=โปรดักที่เกิดตอนเพิ่มอุปกรณ์เพิ่มตอน
								}else{
									ProductItemWorksheet.setProductTypeMatch("N"); //O=โปรดักที่เกิดตอนสร้างใบงาน, N=โปรดักที่เกิดตอนเพิ่มอุปกรณ์เพิ่มตอน
								}
								quantityProductItem += productItemWorksheetBean.getQuantity();
								ProductItemWorksheet.setProductItem(ProductItem);
								ProductItemWorksheet.setPrice(productItemWorksheetBean.getPrice());
								ProductItemWorksheet.setCreateDate(CURRENT_TIMESTAMP);
								ProductItemWorksheet.setCreatedBy(getUserNameLogin());
								ProductItemWorksheet.setDeleted(Boolean.FALSE);
								ProductItemWorksheet.setLend(productItemWorksheetBean.isLend());
								ProductItemWorksheet.setFree(productItemWorksheetBean.isFree());
								ProductItemWorksheet.setQuantity(productItemWorksheetBean.getQuantity());
								ProductItemWorksheet.setProductType(TYPE_INTERNET_USER);
								
								if(!ProductItemWorksheet.isFree() && !ProductItemWorksheet.isLend()){
									ProductItemWorksheet.setAmount(productItemWorksheetBean.getQuantity() * productItemWorksheetBean.getPrice());
								}else{
									ProductItemWorksheet.setAmount(0f);
								}
								InternetProductItem internetProductItem = 
										internetProductItemService
										.getInternetProductItemById(productItemWorksheetBean.getInternetProductBeanItem().getId());
								internetProductItem.setStatus("5"); //ถูกใช้งานแล้ว
								internetProductItemService.update(internetProductItem);
								
								ProductItemWorksheet.setInternetProductItem(internetProductItem);
								productItemWorksheetList.add(ProductItemWorksheet);
							}
							ProductItem.setProductItemWorksheets(productItemWorksheetList);
							ProductItem.setQuantity(quantityProductItem);
						}
						productItemList.add(ProductItem);
						
						// อัพ product ตัวแม่
//						if(null != equipmentProduct){
//							ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
//							pro.setEquipmentProductService(equipmentProductService);
//							pro.setMessageSource(messageSource);
//							pro.autoUpdateStatusEquipmentProduct(equipmentProduct);
//						}
						
					}//end for
					
					worksheet.setProductItems(productItemList);
					
					//update worksheet
					worksheet.setRemark(worksheetUpdateSnapShotBean.getRemark());
					worksheet.setUpdatedDate(CURRENT_TIMESTAMP);
					worksheet.setUpdatedBy(getUserNameLogin());
					
					workSheetService.update(worksheet);
					
//					groupProductItem(worksheet.getServiceApplication().getId());
					
					//update invoice amount
					autoUpdateInvoice(worksheet);
				}
				jsonResponse.setError(false);

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
	
//	private void groupProductItem(Long serviceApplicationId) throws Exception {
//		List<ProductItem> productItemList = productItemService.getProductItemByServiceApplicationId(serviceApplicationId);
//		if(null != productItemList && productItemList.size() > 0){
//			 Collections.sort(productItemList, new Comparator<ProductItem>() {
//			      public int compare(final ProductItem object1, final ProductItem object2) {
//			    	  return object1.getProductType().compareTo(object2.getProductType());
//			      }
//			  });
//			 Collections.sort(productItemList, new Comparator<ProductItem>() {
//			      public int compare(final ProductItem object1, final ProductItem object2) {
//			    	  if(null == object1.getEquipmentProduct() || null == object2.getEquipmentProduct()){ return 0; }
//			    	  return object1.getEquipmentProduct().getId().compareTo(object2.getEquipmentProduct().getId());
//			      }
//			  });
//			int quantity = 1, index = 0, indexCurrent = 0;
//			Long equipmentProductId = 0l;
//			for(ProductItem productItem:productItemList){
//				if(TYPE_EQUIMENT.equals(productItem.getProductType())){
//					if(null != productItem.getEquipmentProduct() && equipmentProductId == productItem.getEquipmentProduct().getId()){
//						quantity += productItem.getQuantity();
//						List<ProductItemWorksheet> productItemWorksheetList = productItem.getProductItemWorksheets();
//						if(null != productItemWorksheetList && productItemWorksheetList.size() > 0){
//							for(ProductItemWorksheet productItemWorksheet:productItemWorksheetList){
//								productItemWorksheet.setProductItem(productItemList.get(indexCurrent));
//								productItemWorksheet.setUpdatedDate(CURRENT_TIMESTAMP);
//								productItemWorksheet.setUpdatedBy(getUserNameLogin());
//								productItemWorksheetService.update(productItemWorksheet);
//							}
//						}
//						productItemService.deleteProductItemById(productItem.getId());
//					}else{
//						indexCurrent = index;
//						quantity = productItem.getQuantity();
//						equipmentProductId = productItem.getEquipmentProduct().getId();
//					}
//					index++;
//				}
//				if(TYPE_EQUIMENT.equals(productItemList.get(indexCurrent).getProductType())){
//					productItemList.get(indexCurrent).setQuantity(quantity);
//					productItemService.update(productItemList.get(indexCurrent));
//				}
//			}
//			
//		}
//	
//	}

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
					equipmentProductBean = populateEntityToDto(equipmentProduct, "0");
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
	
	// loadEquipmentProduct
	@RequestMapping(value = "loadInternetProductWithId/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonResponse loadInternetProductWithId(@PathVariable String id, HttpServletRequest request) {
		logger.info("[method : loadInternetProductWithId][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				InternetProduct internetProduct = productService.findInternetProductById(Long.valueOf(id));
				if (internetProduct != null) {
					InternetProductBean internetProductBean = populateEntityToDto(internetProduct);
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
	
	public static InternetProductBean populateEntityToDto(InternetProduct inter) {
		InternetProductBean internetProductBean = new InternetProductBean();
		if(null != inter){
			internetProductBean.setId(inter.getId());
			internetProductBean.setProductName(inter.getProductName());
			internetProductBean.setProductCode(inter.getProductCode());
			
			// equipmentProductCategoryBean
			EquipmentProductCategoryBean epc = new EquipmentProductCategoryBean();
			epc.setId(inter.getEquipmentProductCategory().getId());
			epc.setEquipmentProductCategoryName(inter.getEquipmentProductCategory().getEquipmentProductCategoryName());
			epc.setEquipmentProductCategoryCode(inter.getEquipmentProductCategory().getEquipmentProductCategoryCode());
			internetProductBean.setProductCategory(epc);
			
			internetProductBean.setType(TYPE_INTERNET_USER);
			
			List<InternetProductBeanItem> internetProductBeanItems = new ArrayList<InternetProductBeanItem>();
			if(null != inter.getInternetProductItems() && inter.getInternetProductItems().size() > 0){
				for(InternetProductItem item:inter.getInternetProductItems()){
					if("0".equals(item.getStatus())){
						InternetProductBeanItem beanItem = new InternetProductBeanItem();
						beanItem.setId(item.getId());
						beanItem.setUserName(item.getUserName());
						beanItem.setPassword(item.getPassword());
						beanItem.setReference(item.getReference());
						internetProductBeanItems.add(beanItem);
					}
				}
				internetProductBean.setInternetProductBeanItems(internetProductBeanItems);
			}
			
		}
		return internetProductBean;
	}
	
	// loadEquipmentProduct
		@RequestMapping(value = "loadEquipmentProduct", method = RequestMethod.POST, produces = "application/json")
		@ResponseBody
		public JsonResponse loadEquipmentProduct(@RequestBody final EquipmentProductBean equipmentProductBean,
				HttpServletRequest request) {
			logger.info("[method : loadEquipmentProduct][Type : Controller]");
			JsonResponse jsonResponse = new JsonResponse();
			if (isPermission()) {
				try {
					logger.info("param EquipmentProductBean : " + equipmentProductBean.toString());
					
					ProductAddController productAddController = new ProductAddController();
					productAddController.setProductService(productService);
					productAddController.setMessageSource(messageSource);

					// load equipment product
					List<ProductBean> prodSearchs = productAddController.loadEquipmentProductAndServiceProduct(equipmentProductBean);
					
					jsonResponse.setError(false);
					jsonResponse.setResult(prodSearchs);

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
		
	// loadEquipmentProduct ค้นหา (หัวหน้ากลุ่ม)
	@RequestMapping(value = "loadEquipmentProductWithIdAndTechnicianGroupId/{id}/technicianGroupId/{technicianGroupId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonResponse loadEquipmentProductWithIdAndTechnicianGroupId(@PathVariable String id,@PathVariable String technicianGroupId, HttpServletRequest request) {
		logger.info("[method : loadEquipmentProductWithIdAndTechnicianGroupId][Type : Controller][technicianGroupId : "+technicianGroupId+"]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				EquipmentProduct equipmentProduct = productService.findEquipmentProductById(Long.valueOf(id));
				if (equipmentProduct != null) {
					EquipmentProductBean equipmentProductBean = new EquipmentProductBean();
					equipmentProductBean = populateEntityToDto(equipmentProduct, technicianGroupId);
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
	

	@RequestMapping(value = "loadServiceProductWithId/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonResponse loadServiceProductWithId(@PathVariable String id, HttpServletRequest request) {
		logger.info("[method : loadEquipmentProductWithId][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				
				ServiceProduct serviceProduct = serviceProductService.getServiceProductById(Long.valueOf(id));
				
				if (serviceProduct != null) {
					ServiceProductBean serviceProductBean = new ServiceProductBean();
					serviceProductBean = ProductAddController.populateEntityToDto(serviceProduct);
					jsonResponse.setError(false);
					jsonResponse.setResult(serviceProductBean);
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
	
	
	@RequestMapping(value = "astbInsertAndUpdateEquipmentProductItem", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse astbInsertAndUpdateEquipmentProductItem(
			@RequestBody final WorksheetUpdateSnapShotBean worksheetUpdateSnapShotBean, HttpServletRequest request) {
		logger.info("[method : astbInsertAndUpdateEquipmentProductItem][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		// create timestamp
		Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
		
		if (isPermission()) {
			try {
				Worksheet worksheet = workSheetService.getWorksheetById(worksheetUpdateSnapShotBean.getIdWorksheetParent());
				if(worksheet != null){
					List<ProductItem> productItemList = new ArrayList<ProductItem>();
					// loop delete old product
					for (ProductItem productItem : worksheet.getProductItems()) {
						for (ProductItemWorksheet productItemWorksheet : productItem.getProductItemWorksheets()) {
							if(productItemWorksheet.getProductTypeMatch().equals("A")){
							// update temp item
							EquipmentProductItem equipmentProductItemTempDel = productService
									.findEquipmentProductItemById(productItemWorksheet.getEquipmentProductItem() != null
											? productItemWorksheet.getEquipmentProductItem().getId() : -1);

							if (equipmentProductItemTempDel != null
									&& (!equipmentProductItemTempDel.getSerialNo().isEmpty())) {
								equipmentProductItemTempDel.setSpare(0);
								equipmentProductItemTempDel.setReservations(0);
								equipmentProductItemTempDel.setStatus(STATUS_ACTIVE);
								equipmentProductItemTempDel.setBalance(1);

								productService.updateProductItem(equipmentProductItemTempDel);
								
								EquipmentProduct eqmp = equipmentProductItemTempDel.getEquipmentProduct();
								// อัพ product ตัวแม่
								if (null != eqmp) {
									ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
									pro.setEquipmentProductService(equipmentProductService);
									pro.setMessageSource(messageSource);
									pro.autoUpdateStatusEquipmentProduct(eqmp);
								}

							} 
//							else if (equipmentProductItemTempDel != null
//									&& (equipmentProductItemTempDel.getSerialNo().isEmpty())) {
//									equipmentProductItemTempDel
//											.setReservations(equipmentProductItemTempDel.getReservations()
//													- productItemWorksheet.getQuantity());
//									equipmentProductItemTempDel.setBalance(equipmentProductItemTempDel.getBalance()
//											+ productItemWorksheet.getQuantity());
//									if (equipmentProductItemTempDel.getBalance() < 0) {
//										equipmentProductItemTempDel.setBalance(0);
//									}
//									if (equipmentProductItemTempDel.getReservations() < 0) {
//										equipmentProductItemTempDel.setReservations(0);
//									}
//
//									productService.updateProductItem(equipmentProductItemTempDel);
//
//									EquipmentProduct eqmp = equipmentProductItemTempDel.getEquipmentProduct();
//
//									// อัพ product ตัวแม่
//									if (null != eqmp) {
//										ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
//										pro.setEquipmentProductService(equipmentProductService);
//										pro.setMessageSource(messageSource);
//										pro.autoUpdateStatusEquipmentProduct(eqmp);
//									}
//							}
							if (productItemWorksheet.getEquipmentProductItem() != null) {
								workSheetService.deleteProductItemWorksheet(productItemWorksheet);
							}
						}
						}
					}
					// delete product item old
					workSheetService.deleteProductItemWithWorksheetIdTypeA(worksheet.getId());
					//save product item new
					EquipmentProduct equipmentProduct = new EquipmentProduct();
					for (ProductItemBean productItemBean : worksheetUpdateSnapShotBean.getProductItemBeanList()) {
						ProductItem ProductItem = new ProductItem();
						//if (productItemBean.getType().equals("E")) {
							ProductItem.setCreateDate(CURRENT_TIMESTAMP);
							ProductItem.setCreatedBy(getUserNameLogin());
							ProductItem.setDeleted(Boolean.FALSE);
							ProductItem.setLend(Boolean.FALSE);
							ProductItem.setFree(Boolean.FALSE);
							ProductItem.setProductType(TYPE_EQUIMENT);
							ProductItem.setProductTypeMatch("A");
							ProductItem.setServiceApplication(worksheet.getServiceApplication());
							// set equipment
							equipmentProduct = equipmentProductService
									.getEquipmentProductById(productItemBean.getId());
							ProductItem.setEquipmentProduct(equipmentProduct);
							ProductItem.setWorkSheet(worksheet);
							// worksheet item
							List<ProductItemWorksheet> productItemWorksheetList = new ArrayList<ProductItemWorksheet>();
							for (ProductItemWorksheetBean productItemWorksheetBean : productItemBean
									.getProductItemWorksheetBeanList()) {
								ProductItemWorksheet ProductItemWorksheet = new ProductItemWorksheet();
								ProductItemWorksheet.setProductItem(ProductItem);
								ProductItemWorksheet.setPrice(productItemWorksheetBean.getPrice());
								ProductItemWorksheet.setCreateDate(CURRENT_TIMESTAMP);
								ProductItemWorksheet.setCreatedBy(getUserNameLogin());
								ProductItemWorksheet.setDeleted(Boolean.FALSE);
								ProductItemWorksheet.setLend(Boolean.FALSE);
								ProductItemWorksheet.setFree(Boolean.FALSE);
								ProductItemWorksheet.setQuantity(1);
								ProductItemWorksheet.setProductType(TYPE_EQUIMENT);
								ProductItemWorksheet.setProductTypeMatch("A");
								if(!ProductItemWorksheet.isFree() && !ProductItemWorksheet.isLend()){
									ProductItemWorksheet.setAmount(productItemWorksheetBean.getAmount());
								}else{
									ProductItemWorksheet.setAmount(0f);
								}
								// update temp item
								EquipmentProductItem equipmentProductItemTemp = productService.findEquipmentProductItemById(
										productItemWorksheetBean.getEquipmentProductItemBean().getId());

								if (equipmentProductItemTemp != null
										&& (!equipmentProductItemTemp.getSerialNo().isEmpty())) {

									if(equipmentProductItemTemp.getSpare() > 0){
										equipmentProductItemTemp.setSpare(0);
									}
									equipmentProductItemTemp.setStatus(STATUS_HOLD);
									equipmentProductItemTemp.setBalance(0);
									equipmentProductItemTemp.setReservations(1);

									productService.updateProductItem(equipmentProductItemTemp);
									
									EquipmentProduct eqmp = equipmentProductItemTemp.getEquipmentProduct();
									// อัพ product ตัวแม่
									if(null != eqmp){
										ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
										pro.setEquipmentProductService(equipmentProductService);
										pro.setMessageSource(messageSource);
										pro.autoUpdateStatusEquipmentProduct(eqmp);
									}

								}
//								else if (equipmentProductItemTemp != null
//										&& (equipmentProductItemTemp.getSerialNo().isEmpty())) {
//									// update balance
//									int quantity = productItemWorksheetBean.getQuantity();
//									int spare = equipmentProductItemTemp.getSpare();
//									int balance = equipmentProductItemTemp.getBalance();
//									// นำ quantity มาลบออกจาก spare ก่อน
//									spare -= quantity;
//									if(spare < 0){ // เช็คว่าจำนวนที่ spare มีอยู่พอไหม
//										equipmentProductItemTemp.setSpare(0); // ถ้าไม่พอจะ set spare = 0
//										spare *= -1; // แล้วจำนวนที่ยังหักออกจาก spare ไม่หมดมาเก็บไว้ใน ตัวแปล spare เพื่อไปหักออกจาก balance ต่อ
//										balance -= spare;
//										equipmentProductItemTemp.setBalance(balance);
//									}else{
//										equipmentProductItemTemp.setSpare(spare);
//									}
//									equipmentProductItemTemp.setReservations(quantity);
//									
//									productService.updateProductItem(equipmentProductItemTemp);
//								}
								// load product item
								EquipmentProductItem equipmentProductItem = productService.findEquipmentProductItemById(
										productItemWorksheetBean.getEquipmentProductItemBean().getId());

								ProductItemWorksheet.setEquipmentProductItem(equipmentProductItem);
								productItemWorksheetList.add(ProductItemWorksheet);
							}
							ProductItem.setProductItemWorksheets(productItemWorksheetList);
							
						//}
						productItemList.add(ProductItem);
						
					}//end for
					
					worksheet.setProductItems(productItemList);
					worksheet.setUpdatedDate(CURRENT_TIMESTAMP);
					worksheet.setUpdatedBy(getUserNameLogin());
					workSheetService.update(worksheet);
					
					//update invoice amount
					autoUpdateInvoice(worksheet);
					
					jsonResponse.setError(false);
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
	
	
	@RequestMapping(value = "borrowInsertAndUpdateEquipmentProductItem", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse borrowInsertAndUpdateEquipmentProductItem(
			@RequestBody final WorksheetUpdateSnapShotBean worksheetUpdateSnapShotBean, HttpServletRequest request) {
		logger.info("[method : borrowInsertAndUpdateEquipmentProductItem][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		// create timestamp
		Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());

		if (isPermission()) {
			try {
				Worksheet worksheet = workSheetService
						.getWorksheetById(worksheetUpdateSnapShotBean.getIdWorksheetParent());
				if (worksheet != null) {
					List<ProductItem> productItemList = new ArrayList<ProductItem>();
					// loop delete old product
					for (ProductItem productItem : worksheet.getProductItems()) {
						for (ProductItemWorksheet productItemWorksheet : productItem.getProductItemWorksheets()) {
							if (productItemWorksheet.getProductTypeMatch().equals("B")) {
								// update temp item
								EquipmentProductItem equipmentProductItemTempDel = productService
										.findEquipmentProductItemById(
												productItemWorksheet.getEquipmentProductItem() != null
														? productItemWorksheet.getEquipmentProductItem().getId() : -1);

								if (equipmentProductItemTempDel != null
										&& (!equipmentProductItemTempDel.getSerialNo().isEmpty())) {
									equipmentProductItemTempDel.setSpare(0);
									equipmentProductItemTempDel.setReservations(0);
									equipmentProductItemTempDel.setStatus(STATUS_ACTIVE);
									equipmentProductItemTempDel.setBalance(1);

									productService.updateProductItem(equipmentProductItemTempDel);
									EquipmentProduct eqmp = equipmentProductItemTempDel.getEquipmentProduct();
									// อัพ product ตัวแม่
									if (null != eqmp) {
										ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
										pro.setEquipmentProductService(equipmentProductService);
										pro.setMessageSource(messageSource);
										pro.autoUpdateStatusEquipmentProduct(eqmp);
									}
								}

								if (productItemWorksheet.getEquipmentProductItem() != null) {
									workSheetService.deleteProductItemWorksheet(productItemWorksheet);
								}
							}
						}
					}
					// delete product item old
					workSheetService.deleteProductItemWithWorksheetIdTypeB(worksheet.getId());
					// save product item new
					EquipmentProduct equipmentProduct = new EquipmentProduct();
					for (ProductItemBean productItemBean : worksheetUpdateSnapShotBean.getProductItemBeanList()) {
						ProductItem ProductItem = new ProductItem();
						//if (productItemBean.getType().equals("E")) {
							ProductItem.setCreateDate(CURRENT_TIMESTAMP);
							ProductItem.setCreatedBy(getUserNameLogin());
							ProductItem.setDeleted(Boolean.FALSE);
							ProductItem.setLend(Boolean.TRUE);
							ProductItem.setFree(Boolean.FALSE);
							ProductItem.setProductType(TYPE_EQUIMENT);
							ProductItem.setServiceApplication(worksheet.getServiceApplication());
							ProductItem.setProductTypeMatch("B");
							// set equipment
							equipmentProduct = equipmentProductService.getEquipmentProductById(productItemBean.getId());
							ProductItem.setEquipmentProduct(equipmentProduct);
							ProductItem.setWorkSheet(worksheet);
							// worksheet item
							List<ProductItemWorksheet> productItemWorksheetList = new ArrayList<ProductItemWorksheet>();
							for (ProductItemWorksheetBean productItemWorksheetBean : productItemBean
									.getProductItemWorksheetBeanList()) {
								ProductItemWorksheet ProductItemWorksheet = new ProductItemWorksheet();
								ProductItemWorksheet.setProductItem(ProductItem);
								ProductItemWorksheet.setPrice(productItemWorksheetBean.getPrice());
								ProductItemWorksheet.setDeposit(productItemWorksheetBean.getDeposit());
								ProductItemWorksheet.setCreateDate(CURRENT_TIMESTAMP);
								ProductItemWorksheet.setCreatedBy(getUserNameLogin());
								ProductItemWorksheet.setDeleted(Boolean.FALSE);
								ProductItemWorksheet.setLend(Boolean.TRUE);
								ProductItemWorksheet.setQuantity(QUANTITY_PRODUCT_ITEM_WORKSHEET);
								ProductItemWorksheet.setFree(Boolean.FALSE);
								ProductItemWorksheet.setProductType(TYPE_EQUIMENT);
								ProductItemWorksheet.setProductTypeMatch("B");
								ProductItemWorksheet.setAmount(0);
								
//								if (!ProductItemWorksheet.isFree() && !ProductItemWorksheet.isLend()) {
//									ProductItemWorksheet.setAmount(productItemWorksheetBean.getAmount());
//								} else {
//									ProductItemWorksheet.setAmount(0f);
//								}
								// update temp item
								EquipmentProductItem equipmentProductItemTemp = productService
										.findEquipmentProductItemById(
												productItemWorksheetBean.getEquipmentProductItemBean().getId());

								if (equipmentProductItemTemp != null
										&& (!equipmentProductItemTemp.getSerialNo().isEmpty())) {

									if (equipmentProductItemTemp.getSpare() > 0) {
										equipmentProductItemTemp.setSpare(0);
									}
									equipmentProductItemTemp.setStatus(STATUS_HOLD);
									equipmentProductItemTemp.setBalance(0);
									equipmentProductItemTemp.setReservations(1);

									productService.updateProductItem(equipmentProductItemTemp);

								} 
								// load product item
								EquipmentProductItem equipmentProductItem = productService.findEquipmentProductItemById(
										productItemWorksheetBean.getEquipmentProductItemBean().getId());

								ProductItemWorksheet.setEquipmentProductItem(equipmentProductItem);
								productItemWorksheetList.add(ProductItemWorksheet);
							}
							ProductItem.setProductItemWorksheets(productItemWorksheetList);

						//}
						productItemList.add(ProductItem);
						// อัพ product ตัวแม่
						if (null != equipmentProduct) {
							ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
							pro.setEquipmentProductService(equipmentProductService);
							pro.setMessageSource(messageSource);
							pro.autoUpdateStatusEquipmentProduct(equipmentProduct);
						}
					} // end for

					worksheet.setProductItems(productItemList);
					worksheet.setUpdatedDate(CURRENT_TIMESTAMP);
					worksheet.setUpdatedBy(getUserNameLogin());
					workSheetService.update(worksheet);
					
					//update invoice amount
					autoUpdateInvoice(worksheet);
					
					jsonResponse.setError(false);
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
	
	
	@RequestMapping(value = "autoRepair", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse autoRepair(
			@RequestBody final WorksheetUpdateSnapShotBean worksheetUpdateSnapShotBean, HttpServletRequest request) {
		logger.info("[method : autoRepair][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		// create timestamp
		
		Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
		if (isPermission()) {
			try {
				Worksheet worksheet = workSheetService.getWorksheetById(worksheetUpdateSnapShotBean.getIdWorksheetParent());
				if (worksheet != null) {
					List<ProductItem> productItemList = new ArrayList<ProductItem>();
					// loop delete old product
					for (ProductItem productItem : worksheet.getProductItems()) {
						for (ProductItemWorksheet productItemWorksheet : productItem.getProductItemWorksheets()) {
							if (productItemWorksheet.getProductTypeMatch().equals("R")) {
								// update temp item
								EquipmentProductItem equipmentProductItemTempDel = productService
										.findEquipmentProductItemById(
												productItemWorksheet.getEquipmentProductItem() != null
														? productItemWorksheet.getEquipmentProductItem().getId() : -1);

								if (equipmentProductItemTempDel != null
										&& (!equipmentProductItemTempDel.getSerialNo().isEmpty())) { // มี SN
									equipmentProductItemTempDel.setSpare(0);
									equipmentProductItemTempDel.setReservations(0);
									equipmentProductItemTempDel.setStatus(STATUS_ACTIVE);
									equipmentProductItemTempDel.setBalance(1);
									equipmentProductItemTempDel.setUpdatedBy(getUserNameLogin());
									equipmentProductItemTempDel.setUpdatedDate(CURRENT_TIMESTAMP);

									productService.updateProductItem(equipmentProductItemTempDel);
									
									EquipmentProduct eqmp = equipmentProductItemTempDel.getEquipmentProduct();

									// อัพ product ตัวแม่
									if (null != eqmp) {
										ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
										pro.setEquipmentProductService(equipmentProductService);
										pro.setMessageSource(messageSource);
										pro.autoUpdateStatusEquipmentProduct(eqmp);
									}
									
								}
								
								if (productItemWorksheet.getEquipmentProductItem() != null) {
									workSheetService.deleteProductItemWorksheet(productItemWorksheet);
								}
							}
						}
						if(null != productItem.getProductItemWorksheets() && productItem.getProductItemWorksheets().size() > 0){
							workSheetService.deleteProductItemWithWorksheetIdTypeR(worksheet.getId());	
						}
					}

					for(RepairMatchItemBean repairMatchItemBean : worksheetUpdateSnapShotBean.getRepairConnectionWorksheetBean().getRepairMatchItemBeanList()){
						
						//new product item record
						ProductItemWorksheet productItemWorksheetOld = productItemWorksheetService.getProductItemWorksheetById(repairMatchItemBean.getOldItemId());
						EquipmentProductItem oldEquipmentItem = productItemWorksheetOld.getEquipmentProductItem();
						//product item create
						ProductItem ProductItem = new ProductItem();
						ProductItem.setCreateDate(CURRENT_TIMESTAMP);
						ProductItem.setCreatedBy(getUserNameLogin());
						ProductItem.setDeleted(Boolean.FALSE);
						ProductItem.setLend(productItemWorksheetOld.isLend());
						ProductItem.setFree(productItemWorksheetOld.isFree());
						ProductItem.setProductType(TYPE_EQUIMENT);
						ProductItem.setProductTypeMatch("R");
						ProductItem.setEquipmentProduct(oldEquipmentItem.getEquipmentProduct());
						ProductItem.setWorkSheet(worksheet);
						ProductItem.setQuantity(QUANTITY_PRODUCT_ITEM_WORKSHEET);
						ProductItem.setServiceApplication(worksheet.getServiceApplication());
						//product item worksheet
						List<ProductItemWorksheet> productItemWorksheetList = new ArrayList<ProductItemWorksheet>();
						ProductItemWorksheet ProductItemWorksheet = new ProductItemWorksheet();
						ProductItemWorksheet.setProductItem(ProductItem);
						ProductItemWorksheet.setPrice(productItemWorksheetOld.getPrice());
						ProductItemWorksheet.setDeposit(productItemWorksheetOld.getDeposit());
						ProductItemWorksheet.setCreateDate(CURRENT_TIMESTAMP);
						ProductItemWorksheet.setCreatedBy(getUserNameLogin());
						ProductItemWorksheet.setDeleted(Boolean.FALSE);
						ProductItemWorksheet.setLend(productItemWorksheetOld.isLend());
						ProductItemWorksheet.setFree(productItemWorksheetOld.isFree());
						ProductItemWorksheet.setQuantity(QUANTITY_PRODUCT_ITEM_WORKSHEET);
						ProductItemWorksheet.setFree(Boolean.FALSE);
						ProductItemWorksheet.setProductType(TYPE_EQUIMENT);
						ProductItemWorksheet.setProductTypeMatch("R");
						ProductItemWorksheet.setAmount(productItemWorksheetOld.getAmount());
						ProductItemWorksheet.setParent(productItemWorksheetOld.getId());
						ProductItemWorksheet.setComment(repairMatchItemBean.getComment());
						
						EquipmentProductItem equipmentProductItemTemp = productService
								.findEquipmentProductItemById(repairMatchItemBean.getNewItemId());

						if (equipmentProductItemTemp != null && (!equipmentProductItemTemp.getSerialNo().isEmpty())) {

							if(equipmentProductItemTemp.getSpare() > 0){
								equipmentProductItemTemp.setSpare(0);
							}
							equipmentProductItemTemp.setStatus(STATUS_HOLD);
							equipmentProductItemTemp.setBalance(0);
							equipmentProductItemTemp.setReservations(1);

							productService.updateProductItem(equipmentProductItemTemp);

						} 
						//load product item
						EquipmentProductItem equipmentProductItem = productService
								.findEquipmentProductItemById(repairMatchItemBean.getNewItemId());
						
						ProductItemWorksheet.setEquipmentProductItem(equipmentProductItem);
						productItemWorksheetList.add(ProductItemWorksheet);
						
						ProductItem.setProductItemWorksheets(productItemWorksheetList);
						productItemList.add(ProductItem);
						
						// อัพ product ตัวแม่
						if(null != oldEquipmentItem.getEquipmentProduct()){
							ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
							pro.setEquipmentProductService(equipmentProductService);
							pro.setMessageSource(messageSource);
							pro.autoUpdateStatusEquipmentProduct(oldEquipmentItem.getEquipmentProduct());
						}
					}//end for
					worksheet.setProductItems(productItemList);
					worksheet.setUpdatedDate(CURRENT_TIMESTAMP);
					worksheet.setUpdatedBy(getUserNameLogin());
					workSheetService.update(worksheet);
					
					//update invoice amount
					autoUpdateInvoice(worksheet);
					
					jsonResponse.setError(false);
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
	
	
	//close worksheet
	@RequestMapping(value = "closeWorksheet", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse closeWorksheet(@RequestBody final WorksheetUpdateSnapShotBean worksheetUpdateSnapShotBean, HttpServletRequest request) {
		logger.info("[method : closeWorksheet][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				Worksheet worksheet = workSheetService.getWorksheetById(worksheetUpdateSnapShotBean.getIdWorksheetParent());
				if(worksheet != null){
					Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
					//delete subworksheet
					workSheetService.deleteSubWorksheetByWorksheetId(worksheetUpdateSnapShotBean.getIdWorksheetParent());
					
					//คืนค่า และลบ product item
					for (ProductItem productItem : worksheet.getProductItems()) {
						for (ProductItemWorksheet productItemWorksheet : productItem.getProductItemWorksheets()) {
							if (productItemWorksheet.getProductType().equals("E")) {
								// update temp item
								EquipmentProductItem equipmentProductItemTempDel = productService
										.findEquipmentProductItemById(
												productItemWorksheet.getEquipmentProductItem() != null
														? productItemWorksheet.getEquipmentProductItem().getId() : -1);

								if (equipmentProductItemTempDel != null && (!equipmentProductItemTempDel.getSerialNo().isEmpty())) {
									RequisitionItem requisitionItem = productItemWorksheet.getRequisitionItem();
									if(null != requisitionItem){
										requisitionItem.setReturnEquipmentProductItem(1);
										requisitionItem.setUpdatedBy(getUserNameLogin());
										requisitionItem.setUpdatedDate(CURRENT_TIMESTAMP);
										requisitionItemService.update(requisitionItem);
										equipmentProductItemTempDel.setSpare(1);
									}else{
										equipmentProductItemTempDel.setBalance(1);
									}
									equipmentProductItemTempDel.setReservations(0);
									equipmentProductItemTempDel.setStatus(STATUS_ACTIVE);
									productItemWorksheet.setEquipmentProductItem(null); // set null เป็นการนำ  item ที่เลือกไว้ออก เฉพาะอุปกรที่มี SN
									productService.updateProductItem(equipmentProductItemTempDel);

								} else if (equipmentProductItemTempDel != null && (equipmentProductItemTempDel.getSerialNo().isEmpty())) {
									RequisitionItem requisitionItem = productItemWorksheet.getRequisitionItem();
									int quantity = productItemWorksheet.getQuantity();
									int spare = equipmentProductItemTempDel.getSpare();
									int balance = equipmentProductItemTempDel.getBalance();
									int reservations = equipmentProductItemTempDel.getReservations();
									if(null != requisitionItem){
										int returnEquipmentProductItem = requisitionItem.getReturnEquipmentProductItem();
										returnEquipmentProductItem = returnEquipmentProductItem - quantity;
										if(returnEquipmentProductItem < 0){
											returnEquipmentProductItem = returnEquipmentProductItem * (-1);
										}
										requisitionItem.setReturnEquipmentProductItem(returnEquipmentProductItem);
										requisitionItem.setUpdatedBy(getUserNameLogin());
										requisitionItem.setUpdatedDate(CURRENT_TIMESTAMP);
										requisitionItemService.update(requisitionItem);

										spare = spare + quantity;
										equipmentProductItemTempDel.setSpare(spare);
									}else{
										balance = balance + quantity;
										equipmentProductItemTempDel.setBalance(balance);
									}
									reservations = reservations - quantity;
									if(reservations < 0){
										reservations = reservations * (-1);
									}
									equipmentProductItemTempDel.setReservations(reservations);
									productService.updateProductItem(equipmentProductItemTempDel);

								}
								
								// อัพ product ตัวแม่
								if(equipmentProductItemTempDel != null){
									EquipmentProduct eqmp = equipmentProductItemTempDel.getEquipmentProduct();
									if (null != eqmp) {
										ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
										pro.setEquipmentProductService(equipmentProductService);
										pro.setMessageSource(messageSource);
										pro.autoUpdateStatusEquipmentProduct(eqmp);
									}
								}
							
							}else if(productItemWorksheet.getProductType().equals("I")){
								InternetProductItem internetProductItem = internetProductItemService.getInternetProductItemById(productItemWorksheet.getInternetProductItem().getId());
								internetProductItem.setStatus("0"); //ใช้ได้
								internetProductItemService.update(internetProductItem);
//								if(internetProductItem != null){
//									workSheetService.deleteProductItemWorksheet(productItemWorksheet);
//								}
							}
							productItemWorksheet.setInternetProductItem(null); // set null เป็นการนำ  item ที่เลือกไว้ออก
							productItemWorksheetService.update(productItemWorksheet);
						}
					} // end คืนค่า และลบ product item
					
					//delete product item old
//					workSheetService.deleteProductItemWithWorksheetIdAll(worksheet.getId());
					
					//กรณีปิดใบงานติดตั้งจะต้องไปอัพเดทใบสมัครด้วย
					if(worksheet.getWorkSheetType().equals("C_S")){
						logger.info("start update service application");
						ServiceApplication serviceApplication = serviceApplicationService.getServiceApplicationById(worksheet.getServiceApplication().getId());
						serviceApplication.setStatus("D");
						serviceApplicationService.update(serviceApplication);
					}
					
					
					Worksheet worksheetTemp = workSheetService.getWorksheetById(worksheetUpdateSnapShotBean.getIdWorksheetParent());
					//invoice และ receipt
					if(worksheetTemp.getInvoice() != null){
						worksheetTemp.getInvoice().setDeleted(Boolean.TRUE);
						worksheetTemp.getInvoice().setStatus(messageSource.getMessage("financial.invoice.status.cancel", null, LocaleContextHolder.getLocale()));
						worksheetTemp.getInvoice().setAmount(0);
						if(worksheetTemp.getInvoice().getReceipt() != null){
							worksheetTemp.getInvoice().getReceipt().setDeleted(Boolean.TRUE);
							worksheetTemp.getInvoice().getReceipt().setAmount(0);
						}
					}
					logger.info("start update worksheet");
					//เปลียนสถานะเป็น D = ปิดใบงาน
					worksheetTemp.setStatus(messageSource.getMessage("worksheet.status.value.d", null, LocaleContextHolder.getLocale()));
					
					if(null != worksheetTemp.getHistoryTechnicianGroupWorks() && worksheetTemp.getHistoryTechnicianGroupWorks().size() > 0){
					//เปลี่ยนสถานะการทำงานช่างให้เป็นไม่สำเร็จพร้อมเหตุผล
					HistoryTechnicianGroupWork historyTechnicianGroupWork = worksheetTemp.getHistoryTechnicianGroupWorks().get(worksheetTemp.getHistoryTechnicianGroupWorks().size() - 1);
					historyTechnicianGroupWork.setStatusHistory("C");
					historyTechnicianGroupWork.setRemarkNotSuccess(worksheetUpdateSnapShotBean.getRemark());
					}
					
					worksheetTemp.setRemark(worksheetUpdateSnapShotBean.getRemark());
					worksheetTemp.setUpdatedDate(CURRENT_TIMESTAMP);
					worksheetTemp.setUpdatedBy(getUserNameLogin());
					
					workSheetService.update(worksheetTemp);
					jsonResponse.setError(false);
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
				messageSource.getMessage("snapshot.transaction.title.success", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("snapshot.transaction.close.success", null, LocaleContextHolder.getLocale()));

		return jsonResponse;
	}
	
	public void autoUpdateInvoice(Worksheet worksheet){
		InvoiceController invoiceController = new InvoiceController();
		invoiceController.setMessageSource(messageSource);
		invoiceController.setFinancialService(financialService);
		invoiceController.setWorkSheetService(workSheetService);
		invoiceController.updateAmountInvoiceTypeWorksheet(worksheet.getId());
	}
	
	// populate bean
	public EquipmentProductBean populateEntityToDto(EquipmentProduct ep, String technicianGroupId) {
		EquipmentProductBean equipmentProductBean = new EquipmentProductBean();

		// set equimemtProductBean
		equipmentProductBean.setId(ep.getId());
		equipmentProductBean.setProductCode(ep.getProductCode());
		equipmentProductBean.setProductName(ep.getProductName());
		equipmentProductBean.setSupplier(ep.getSupplier());
		equipmentProductBean.setIsminimum(ep.isMinimum());
		equipmentProductBean.setMinimumNumber(ep.getMinimumNumber());
		equipmentProductBean.setType(TYPE_EQUIMENT);
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
		EquipmentProductItemBean equipmentProductItemBean = new EquipmentProductItemBean();
		List<EquipmentProductItemBean> equipmentProductItemBeans = new ArrayList<EquipmentProductItemBean>();
		for (EquipmentProductItem equipmentProductItem : ep.getEquipmentProductItems()) {
			if (!equipmentProductItem.isDeleted() && equipmentProductItem.getStatus() == 1
					|| equipmentProductItem.getStatus() == 4) {
				Boolean isAddItem = false;
				List<RequisitionItem> requisitionItems = equipmentProductItem.getRequisitionItems();
				if (null != requisitionItems && requisitionItems.size() > 0) {
					for (RequisitionItem requisitionItem : requisitionItems) {
						isAddItem = false;
						// อุปกรที่มีการเบิก
						List<RequisitionItemBean> requisitionItemBeans = new ArrayList<RequisitionItemBean>();
						equipmentProductItemBean = new EquipmentProductItemBean();
						RequisitionItemBean requisitionItemBean = new RequisitionItemBean();
						requisitionItemBean.setId(requisitionItem.getId());

						Personnel personnel = requisitionItem.getPersonnel();
						if(null != personnel){
							PersonnelBean personnelBean = new PersonnelBean();
							Long personnelId = personnel.getId();
							if ("0".equals(technicianGroupId)) {
								isAddItem = true;
							} else if (personnelId == Long.valueOf(technicianGroupId)) {
								isAddItem = true;
							}
							personnelBean.setId(personnel.getId());
							personnelBean.setFirstName(personnel.getFirstName());
							personnelBean.setLastName(personnel.getLastName());

							requisitionItemBean.setPersonnelBean(personnelBean);
						}
						
						RequisitionDocumentBean requisitionDocumentBean = new RequisitionDocumentBean();
						requisitionDocumentBean.setId(requisitionItem.getRequisitionDocument().getId());
						requisitionDocumentBean.setRequisitionDocumentCode(requisitionItem.getRequisitionDocument().getRequisitionDocumentCode());
						requisitionItemBean.setRequisitionDocumentBean(requisitionDocumentBean);

						requisitionItemBeans.add(requisitionItemBean);

						equipmentProductItemBean.setRequisitionItemBeans(requisitionItemBeans);

						equipmentProductItemBean.setId(equipmentProductItem.getId());
						equipmentProductItemBean.setCost(equipmentProductItem.getCost());
						equipmentProductItemBean.setReferenceNo(equipmentProductItem.getReference());
						equipmentProductItemBean.setSerialNo(equipmentProductItem.getSerialNo());
						equipmentProductItemBean.setNumberImport(equipmentProductItem.getNumberImport());
						equipmentProductItemBean.setBalance(requisitionItem.getQuantity()-requisitionItem.getReturnEquipmentProductItem());
						equipmentProductItemBean.setSalePrice(equipmentProductItem.getSalePrice());
						equipmentProductItemBean.setSpare(equipmentProductItem.getSpare());
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
						// import system datetime
						equipmentProductItemBean.setImportSystemDate(null == equipmentProductItem.getImportSystemDate()
								? "" : formatDateAndTimeTh.format(equipmentProductItem.getImportSystemDate()));

						// status
						ProductUtil productUtil = new ProductUtil();
						productUtil.setMessageSource(messageSource);
						equipmentProductItemBean
								.setStatus(productUtil.getMessageStatusProduct(equipmentProductItem.getStatus()));

						// set parent equipment
						EquipmentProductBean EquipmentProductParent = new EquipmentProductBean();
						EquipmentProductParent.setId(ep.getId());
						equipmentProductItemBean.setEquipmentProductBean(EquipmentProductParent);

						// add to equipmentproductBean List
						if (isAddItem) {
							equipmentProductItemBeans.add(equipmentProductItemBean);
						}
					}
					
					if(equipmentProductItem.getSerialNo().isEmpty()){
						// อุปกรที่มีอยู่ในคลัง
						equipmentProductItemBean = new EquipmentProductItemBean();
						equipmentProductItemBean.setId(equipmentProductItem.getId());
						equipmentProductItemBean.setCost(equipmentProductItem.getCost());
						equipmentProductItemBean.setReferenceNo(equipmentProductItem.getReference());
						equipmentProductItemBean.setSerialNo(equipmentProductItem.getSerialNo());
						equipmentProductItemBean.setNumberImport(equipmentProductItem.getNumberImport());
						equipmentProductItemBean.setBalance(equipmentProductItem.getBalance());
						equipmentProductItemBean.setSalePrice(equipmentProductItem.getSalePrice());
						equipmentProductItemBean.setSpare(equipmentProductItem.getSpare());
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
						// import system datetime
						equipmentProductItemBean.setImportSystemDate(null == equipmentProductItem.getImportSystemDate()
								? "" : formatDateAndTimeTh.format(equipmentProductItem.getImportSystemDate()));
	
						// status
						ProductUtil productUtil = new ProductUtil();
						productUtil.setMessageSource(messageSource);
						equipmentProductItemBean
								.setStatus(productUtil.getMessageStatusProduct(equipmentProductItem.getStatus()));
	
						// set parent equipment
						EquipmentProductBean EquipmentProductParent = new EquipmentProductBean();
						EquipmentProductParent.setId(ep.getId());
						equipmentProductItemBean.setEquipmentProductBean(EquipmentProductParent);
	
						// add to equipmentproductBean List
						if ("0".equals(technicianGroupId)) {
							equipmentProductItemBeans.add(equipmentProductItemBean);
						}
					}
				} else {
					if ("0".equals(technicianGroupId)) {
						isAddItem = true;
					} else {
						isAddItem = false;
					}
					equipmentProductItemBean = new EquipmentProductItemBean();
					equipmentProductItemBean.setId(equipmentProductItem.getId());
					equipmentProductItemBean.setCost(equipmentProductItem.getCost());
					equipmentProductItemBean.setReferenceNo(equipmentProductItem.getReference());
					equipmentProductItemBean.setSerialNo(equipmentProductItem.getSerialNo());
					equipmentProductItemBean.setNumberImport(equipmentProductItem.getNumberImport());
					equipmentProductItemBean.setBalance(equipmentProductItem.getBalance());
					equipmentProductItemBean.setSalePrice(equipmentProductItem.getSalePrice());
					equipmentProductItemBean.setSpare(equipmentProductItem.getSpare());
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
					// import system datetime
					equipmentProductItemBean.setImportSystemDate(null == equipmentProductItem.getImportSystemDate() ? ""
							: formatDateAndTimeTh.format(equipmentProductItem.getImportSystemDate()));

					// status
					ProductUtil productUtil = new ProductUtil();
					productUtil.setMessageSource(messageSource);
					equipmentProductItemBean
							.setStatus(productUtil.getMessageStatusProduct(equipmentProductItem.getStatus()));

					// set parent equipment
					EquipmentProductBean EquipmentProductParent = new EquipmentProductBean();
					EquipmentProductParent.setId(ep.getId());
					equipmentProductItemBean.setEquipmentProductBean(EquipmentProductParent);

					// add to equipmentproductBean List
					if (isAddItem) {
						equipmentProductItemBeans.add(equipmentProductItemBean);
					}

				}

			}
		}
		// set product item to product
		equipmentProductBean.setEquipmentProductItemBeans(equipmentProductItemBeans);

		return equipmentProductBean;
	}
	
	// populate bean
	public EquipmentProductBean populateEntityToDtoOld(EquipmentProduct ep, String technicianGroupId) {
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
		equipmentProductBean.setType(TYPE_EQUIMENT);
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
			if(!equipmentProductItem.isDeleted() && equipmentProductItem.getStatus() == 1 ||
					equipmentProductItem.getStatus() == 4){
			Boolean isAddItem = false;
			EquipmentProductItemBean equipmentProductItemBean = new EquipmentProductItemBean();
			equipmentProductItemBean.setId(equipmentProductItem.getId());
			equipmentProductItemBean.setCost(equipmentProductItem.getCost());
			equipmentProductItemBean.setReferenceNo(equipmentProductItem.getReference());
			equipmentProductItemBean.setSerialNo(equipmentProductItem.getSerialNo());
			equipmentProductItemBean.setNumberImport(equipmentProductItem.getNumberImport());
			equipmentProductItemBean.setBalance(equipmentProductItem.getBalance());
			equipmentProductItemBean.setSalePrice(equipmentProductItem.getSalePrice());
			equipmentProductItemBean.setSpare(equipmentProductItem.getSpare());
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
			
			List<RequisitionItem> requisitionItems = equipmentProductItem.getRequisitionItems();
			if(null != requisitionItems && requisitionItems.size() > 0){
				List<RequisitionItemBean> requisitionItemBeans = new ArrayList<RequisitionItemBean>();
				for(RequisitionItem requisitionItem:requisitionItems){
					RequisitionItemBean requisitionItemBean = new RequisitionItemBean();
					requisitionItemBean.setId(requisitionItem.getId());
					
					Personnel personnel = requisitionItem.getPersonnel();
					PersonnelBean personnelBean = new PersonnelBean();
					
					if("0".equals(technicianGroupId)){
						isAddItem = true;
					}else if(personnel.getId() == Long.valueOf(technicianGroupId)){
						isAddItem = true;
					}
					
					personnelBean.setId(personnel.getId());
					personnelBean.setFirstName(personnel.getFirstName());
					personnelBean.setLastName(personnel.getLastName());
					
					requisitionItemBean.setPersonnelBean(personnelBean);
					
					requisitionItemBeans.add(requisitionItemBean);
				}
				equipmentProductItemBean.setRequisitionItemBeans(requisitionItemBeans);
			}else{
				if("0".equals(technicianGroupId)){
					isAddItem = true;
				}else{
					isAddItem = false;
				}
			}
			
			// add to equipmentproductBean List
			if(isAddItem){
				equipmentProductItemBeans.add(equipmentProductItemBean);
			}
			
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
	
	// updateAnalyzeProblemsWorksheet
	@RequestMapping(value = "updateAnalyzeProblemsWorksheet", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse updateAnalyzeProblemsWorksheet(
			@RequestBody final WorksheetUpdateSnapShotBean worksheetUpdateSnapShotBean,HttpServletRequest request) {
		logger.info("[method : updateAnalyzeProblemsWorksheet][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {

			try {
				AnalyzeProblemsWorksheetBean analyzeProblemsWorksheetBean = worksheetUpdateSnapShotBean.getAnalyzeProblemsWorksheetBean();
				if(null != analyzeProblemsWorksheetBean){
					WorksheetAnalyzeProblems worksheetAnalyzeProblems = workSheetService.getWorksheetAnalyzeProblemsById(analyzeProblemsWorksheetBean.getId());
					MenuReport menuReport = unitService.getMenuReportById(analyzeProblemsWorksheetBean.getMenuReportId());
					worksheetAnalyzeProblems.setMenuReport(menuReport);
					workSheetService.updateWorksheetAnalyzeProblems(worksheetAnalyzeProblems);
				}
				jsonResponse.setError(false);
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
	
	public boolean updateSnapAnalyzeproblems(WorksheetUpdateSnapShotBean worksheetUpdateSnapShotBean){
		boolean success = true;
		try{
			success = updateHeadSnap(worksheetUpdateSnapShotBean);
			//call cut stock
			success = BeforeCutStock(worksheetUpdateSnapShotBean);
			
		}catch(Exception ex){
			success = false;
		}
		return success;
	}
	
	public void generateAlert(JsonResponse jsonResponse, HttpServletRequest request, String title, String detail){
		new AlertUtil().generateAlert(jsonResponse, request, title, detail);
	}
	
		//getter setter
		public void setMessageSource(MessageSource messageSource) {
			this.messageSource = messageSource;
		}

		public void setServiceApplicationService(ServiceApplicationService serviceApplicationService) {
			this.serviceApplicationService = serviceApplicationService;
		}

		public void setWorkSheetService(WorkSheetService workSheetService) {
			this.workSheetService = workSheetService;
		}

		public void setServiceProductService(ServiceProductService serviceProductService) {
			this.serviceProductService = serviceProductService;
		}

		public void setProductService(ProductService productService) {
			this.productService = productService;
		}

		public void setEquipmentProductCategoryService(EquipmentProductCategoryService equipmentProductCategoryService) {
			this.equipmentProductCategoryService = equipmentProductCategoryService;
		}

		public void setStockService(StockService stockService) {
			this.stockService = stockService;
		}

		public void setEquipmentProductService(EquipmentProductService equipmentProductService) {
			this.equipmentProductService = equipmentProductService;
		}

		public void setEquipmentProductItemService(EquipmentProductItemService equipmentProductItemService) {
			this.equipmentProductItemService = equipmentProductItemService;
		}

		public void setProductItemService(ProductItemService productItemService) {
			this.productItemService = productItemService;
		}

		public void setZoneService(ZoneService zoneService) {
			this.zoneService = zoneService;
		}

		public void setAddressService(AddressService addressService) {
			this.addressService = addressService;
		}

		public void setTechnicianGroupService(TechnicianGroupService technicianGroupService) {
			this.technicianGroupService = technicianGroupService;
		}

		public void setPersonnelService(PersonnelService personnelService) {
			this.personnelService = personnelService;
		}
	}
