package com.hdw.mccable.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hdw.mccable.dto.AlertBean;
import com.hdw.mccable.dto.AuthenticationBean;
import com.hdw.mccable.dto.CompanyBean;
import com.hdw.mccable.dto.ContactBean;
import com.hdw.mccable.dto.CountWorksheetBean;
import com.hdw.mccable.dto.JsonResponse;
import com.hdw.mccable.dto.PermissionGroupBean;
import com.hdw.mccable.dto.PersonnelBean;
import com.hdw.mccable.dto.PersonnelSearchBean;
import com.hdw.mccable.dto.PositionBean;
import com.hdw.mccable.dto.SeachCountWorksheetRequest;
import com.hdw.mccable.dto.TechnicianGroupBean;
import com.hdw.mccable.dto.WorksheetBean;
import com.hdw.mccable.dto.WorksheetSearchBean;
import com.hdw.mccable.entity.Authentication;
import com.hdw.mccable.entity.Company;
import com.hdw.mccable.entity.Contact;
import com.hdw.mccable.entity.PermissionGroup;
import com.hdw.mccable.entity.Personnel;
import com.hdw.mccable.entity.PersonnelAssign;
import com.hdw.mccable.entity.Position;
import com.hdw.mccable.entity.Worksheet;
import com.hdw.mccable.service.CompanyService;
import com.hdw.mccable.service.PermissionGroupService;
import com.hdw.mccable.service.PersonnelService;
import com.hdw.mccable.service.PositionService;
import com.hdw.mccable.service.WorkSheetService;
import com.hdw.mccable.utils.AlertUtil;
import com.hdw.mccable.utils.Pagination;
import com.hdw.mccable.utils.SecurityUtils;

@Controller
@RequestMapping("/personnel")
public class PersonnelController extends BaseController {

	final static Logger logger = Logger.getLogger(PersonnelController.class);
	public static final String CONTROLLER_NAME = "personnel/";
	
	// initial service
	@Autowired(required = true)
	@Qualifier(value = "personnelService")
	private PersonnelService personnelService;
	
	@Autowired(required=true)
	@Qualifier(value="companyService")
	private CompanyService companyService;
	
	@Autowired(required=true)
	@Qualifier(value="positionService")
	private PositionService positionService;
	
	@Autowired(required=true)
	@Qualifier(value="permissionGroupService")
	private PermissionGroupService permissionGroupService;
	
	@Autowired(required = true)
	@Qualifier(value = "workSheetService")
	private WorkSheetService workSheetService;
	
	@Autowired
    private MessageSource messageSource;
	// end initial service
	
	public static final String key = "";
	public static final String position = "";
	public static final Long permissionGroup = 0L;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView initPersonnel(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : initPersonnel][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		List<PersonnelBean> personnelBeans = new ArrayList<PersonnelBean>();
		HttpSession session = null;
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			try{
				//create dropdown permission group
				List<PermissionGroupBean> permissionGroupBeans = new ArrayList<PermissionGroupBean>();
				List<PermissionGroup> permissionGroups = new ArrayList<PermissionGroup>();
				permissionGroups = permissionGroupService.findAll();
				if(permissionGroups != null){
					for(PermissionGroup pg : permissionGroups){
						PermissionGroupBean permissionGroupBean = new PermissionGroupBean();
						permissionGroupBean.setId(pg.getId());
						permissionGroupBean.setPermissionGroupName(pg.getPermissionGroupName());
						permissionGroupBeans.add(permissionGroupBean);
					}
				}
				modelAndView.addObject("permissionGroupBeans", permissionGroupBeans);
				//End create dropdown permission group
				
				//create dropdown position
				List<String> positionDuplicates = positionService.findByDuplicatePositionName();
				List<PositionBean> positionBeans = new ArrayList<PositionBean>();
				for(String positionName : positionDuplicates){
					PositionBean positonBean = new PositionBean();
					positonBean.setPositionName(positionName);
					positionBeans.add(positonBean);
				}
				modelAndView.addObject("positionBeans",positionBeans);
				//End create dropdown position
				
				//search personnel
				//get session personnel form for send paramenter search
				session = request.getSession();
				PersonnelSearchBean personnelSearchBean = (PersonnelSearchBean) session.getAttribute("personnelSearchBean");
				if(session.getAttribute("personnelSearchBean") != null){
					modelAndView.addObject("personnelSearchBean", personnelSearchBean);
				}else{
					modelAndView.addObject("personnelSearchBean", new PersonnelSearchBean());
				}
				
				//call search process

				if(personnelSearchBean != null){
					personnelBeans = searchPersonnel(personnelSearchBean.getKey(), personnelSearchBean.getPosition(), personnelSearchBean.getPermissionGroup());
				}else{
					personnelBeans = searchPersonnel(key, position, permissionGroup);
				}
				//personnel after search
				modelAndView.addObject("personnels", personnelBeans);
				logger.info("[method : initEmployee][personnelBean : "+ personnelBeans.toString() +"]");
				
			}catch(Exception ex){
				ex.printStackTrace();
				//redirect page error
			}
			
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		//alert session
		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);
		
		//remove session
		session.removeAttribute("alert");
		session.removeAttribute("personnelSearchBean");
		
		modelAndView.setViewName(CONTROLLER_NAME + INIT);
		return modelAndView;
	}
	
	
	@RequestMapping(value = "view/{id}", method = RequestMethod.GET)
	public ModelAndView openView(@PathVariable String id, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : openView][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		// get current session
		HttpSession session = request.getSession();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		if (isPermission()) {
			try {
				// load personnel
				PersonnelBean personnelBean = loadPersonnel(Long.valueOf(id));
				modelAndView.addObject("personnelBean", personnelBean);
				
				// load worksheet count
				List<PersonnelAssign> personnelAssignList = new ArrayList<PersonnelAssign>();
				SeachCountWorksheetRequest seachCountWorksheetRequest = (SeachCountWorksheetRequest) session.getAttribute("seachCountWorksheetRequest");
				if(seachCountWorksheetRequest != null){
					modelAndView.addObject("seachCountWorksheetRequest",seachCountWorksheetRequest);
					personnelAssignList = searchAssignPersonnel(seachCountWorksheetRequest);
				}else{
					SeachCountWorksheetRequest seachCountWorksheetRequestInit = new SeachCountWorksheetRequest();
					seachCountWorksheetRequestInit.setPersonnelId(Long.valueOf(id));
					seachCountWorksheetRequestInit.setDateRange("");
					modelAndView.addObject("seachCountWorksheetRequest",new SeachCountWorksheetRequest());
					personnelAssignList = searchAssignPersonnel(seachCountWorksheetRequestInit);
				}
				
				//loop count
				CountWorksheetBean countWorksheetBean = new CountWorksheetBean();
				countWorksheetBean.init();
				for(PersonnelAssign personnelAssign : personnelAssignList){
					String status = personnelAssign.getHistoryTechnicianGroupWork().getStatusHistory();
					String workSheetType = personnelAssign.getHistoryTechnicianGroupWork().getWorkSheet().getWorkSheetType();
					
					if(workSheetType.equals(messageSource.getMessage("worksheet.type.cable.addpoint", 
							null,LocaleContextHolder.getLocale()))){
						
						countWorksheetBean.setCountC_AP(countWorksheetBean.getCountC_AP()+1);
						
						if(status != null && status.equals("S")) {
							countWorksheetBean.setCountSC_AP(countWorksheetBean.getCountSC_AP()+1);
						} else if(status != null && status.equals("C")) {
							countWorksheetBean.setCountFC_AP(countWorksheetBean.getCountFC_AP()+1);
						} else if(status != null && status.equals("N")) {
							countWorksheetBean.setCountNC_AP(countWorksheetBean.getCountNC_AP()+1);
						}
					}else if(workSheetType.equals(messageSource.getMessage("worksheet.type.cable.connect", 
							null,LocaleContextHolder.getLocale()))){
						countWorksheetBean.setCountC_C(countWorksheetBean.getCountC_C() + 1);
						
						if(status != null && status.equals("S")) {
							countWorksheetBean.setCountSC_C(countWorksheetBean.getCountSC_C()+1);
						} else if(status != null && status.equals("C")) {
							countWorksheetBean.setCountFC_C(countWorksheetBean.getCountFC_C()+1);
						} else if(status != null && status.equals("N")) {
							countWorksheetBean.setCountNC_C(countWorksheetBean.getCountNC_C()+1);
						}
					}else if(workSheetType.equals(messageSource.getMessage("worksheet.type.cable.setup", 
							null,LocaleContextHolder.getLocale()))){
						countWorksheetBean.setCountC_S(countWorksheetBean.getCountC_S()+1);
						
						if(status != null && status.equals("S")) {
							countWorksheetBean.setCountSC_S(countWorksheetBean.getCountSC_S()+1);
						} else if(status != null && status.equals("C")) {
							countWorksheetBean.setCountFC_S(countWorksheetBean.getCountFC_S()+1);
						} else if(status != null && status.equals("N")) {
							countWorksheetBean.setCountNC_S(countWorksheetBean.getCountNC_S()+1);
						}
					}else if(workSheetType.equals(messageSource.getMessage("worksheet.type.cable.tune", 
							null,LocaleContextHolder.getLocale()))){
						countWorksheetBean.setCountC_TTV(countWorksheetBean.getCountC_TTV()+1);
						
						if(status != null && status.equals("S")) {
							countWorksheetBean.setCountSC_TTV(countWorksheetBean.getCountSC_TTV()+1);
						} else if(status != null && status.equals("C")) {
							countWorksheetBean.setCountFC_TTV(countWorksheetBean.getCountFC_TTV()+1);
						} else if(status != null && status.equals("N")) {
							countWorksheetBean.setCountNC_TTV(countWorksheetBean.getCountNC_TTV()+1);
						}
					}else if(workSheetType.equals(messageSource.getMessage("worksheet.type.cable.repair", 
							null,LocaleContextHolder.getLocale()))){
						countWorksheetBean.setCountC_RC(countWorksheetBean.getCountC_RC() +1);
						
						if(status != null && status.equals("S")) {
							countWorksheetBean.setCountSC_RC(countWorksheetBean.getCountSC_RC()+1);
						} else if(status != null && status.equals("C")) {
							countWorksheetBean.setCountFC_RC(countWorksheetBean.getCountFC_RC()+1);
						} else if(status != null && status.equals("N")) {
							countWorksheetBean.setCountNC_RC(countWorksheetBean.getCountNC_RC()+1);
						}
					}else if(workSheetType.equals(messageSource.getMessage("worksheet.type.cable.addsettop", 
							null,LocaleContextHolder.getLocale()))){
						countWorksheetBean.setCountC_ASTB(countWorksheetBean.getCountC_ASTB()+1);
						
						if(status != null && status.equals("S")) {
							countWorksheetBean.setCountSC_ASTB(countWorksheetBean.getCountSC_ASTB()+1);
						} else if(status != null && status.equals("C")) {
							countWorksheetBean.setCountFC_ASTB(countWorksheetBean.getCountFC_ASTB()+1);
						} else if(status != null && status.equals("N")) {
							countWorksheetBean.setCountNC_ASTB(countWorksheetBean.getCountNC_ASTB()+1);
						}
					}else if(workSheetType.equals(messageSource.getMessage("worksheet.type.cable.movepoint", 
							null,LocaleContextHolder.getLocale()))){
						countWorksheetBean.setCountC_MP(countWorksheetBean.getCountC_MP()+1);
						
						if(status != null && status.equals("S")) {
							countWorksheetBean.setCountSC_MP(countWorksheetBean.getCountSC_MP()+1);
						} else if(status != null && status.equals("C")) {
							countWorksheetBean.setCountFC_MP(countWorksheetBean.getCountFC_MP()+1);
						} else if(status != null && status.equals("N")) {
							countWorksheetBean.setCountNC_MP(countWorksheetBean.getCountNC_MP()+1);
						}
					}else if(workSheetType.equals(messageSource.getMessage("worksheet.type.cable.reducepoint", 
							null,LocaleContextHolder.getLocale()))){
						countWorksheetBean.setCountC_RP(countWorksheetBean.getCountC_RP()+1);
						
						if(status != null && status.equals("S")) {
							countWorksheetBean.setCountSC_RP(countWorksheetBean.getCountSC_RP()+1);
						} else if(status != null && status.equals("C")) {
							countWorksheetBean.setCountFC_RP(countWorksheetBean.getCountFC_RP()+1);
						} else if(status != null && status.equals("N")) {
							countWorksheetBean.setCountNC_RP(countWorksheetBean.getCountNC_RP()+1);
						}
					}else if(workSheetType.equals(messageSource.getMessage("worksheet.type.cable.cut", 
							null,LocaleContextHolder.getLocale()))){
						countWorksheetBean.setCountC_C(countWorksheetBean.getCountC_C()+1);
						
						if(status != null && status.equals("S")) {
							countWorksheetBean.setCountSC_C(countWorksheetBean.getCountSC_C()+1);
						} else if(status != null && status.equals("C")) {
							countWorksheetBean.setCountFC_C(countWorksheetBean.getCountFC_C()+1);
						} else if(status != null && status.equals("N")) {
							countWorksheetBean.setCountNC_C(countWorksheetBean.getCountNC_C()+1);
						}
					}else if(workSheetType.equals(messageSource.getMessage("worksheet.type.cable.move", 
							null,LocaleContextHolder.getLocale()))){
						countWorksheetBean.setCountC_M(countWorksheetBean.getCountC_M()+1);
						
						if(status != null && status.equals("S")) {
							countWorksheetBean.setCountSC_M(countWorksheetBean.getCountSC_M()+1);
						} else if(status != null && status.equals("C")) {
							countWorksheetBean.setCountFC_M(countWorksheetBean.getCountFC_M()+1);
						} else if(status != null && status.equals("N")) {
							countWorksheetBean.setCountNC_M(countWorksheetBean.getCountNC_M()+1);
						}
					}else if(workSheetType.equals(messageSource.getMessage("worksheet.type.cable.borrow", 
							null,LocaleContextHolder.getLocale()))){
						countWorksheetBean.setCountC_B(countWorksheetBean.getCountC_B()+1);
						
						if(status != null && status.equals("S")) {
							countWorksheetBean.setCountSC_B(countWorksheetBean.getCountSC_B()+1);
						} else if(status != null && status.equals("C")) {
							countWorksheetBean.setCountFC_B(countWorksheetBean.getCountFC_B()+1);
						} else if(status != null && status.equals("N")) {
							countWorksheetBean.setCountNC_B(countWorksheetBean.getCountNC_B()+1);
						}
					}
				}
				
				modelAndView.addObject("countWorksheetBean",countWorksheetBean);
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		
		//remove session
		session.removeAttribute("seachCountWorksheetRequest");
		
		modelAndView.setViewName(CONTROLLER_NAME + VIEW);
		return modelAndView;
	}
	
	public List<PersonnelAssign> searchAssignPersonnel(SeachCountWorksheetRequest seachCountWorksheetRequest){
		List<PersonnelAssign> personnelAssingList = 
				personnelService.findPersonnelAssign(seachCountWorksheetRequest.getPersonnelId(), seachCountWorksheetRequest.getDateRange());

		return personnelAssingList;
	}
	
	// search request
	@RequestMapping(value = "/searchCount", method = RequestMethod.POST)
	public ModelAndView searchCount(@ModelAttribute("seachCountWorksheetRequest") SeachCountWorksheetRequest seachCountWorksheetRequest,
			HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {

		logger.info("[method : searchCount][Type : Controller]");
		logger.info("[method : seachCountWorksheetRequest][seachCountWorksheetRequest : " + seachCountWorksheetRequest.toString() + "]");
		ModelAndView modelAndView = new ModelAndView();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			generateSearchSessionCount(seachCountWorksheetRequest, request);
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		modelAndView.setViewName(REDIRECT + "/personnel/view/" +seachCountWorksheetRequest.getPersonnelId());
		return modelAndView;
	}
	
	// create search count session
	public void generateSearchSessionCount(SeachCountWorksheetRequest seachCountWorksheetRequest, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("seachCountWorksheetRequest", seachCountWorksheetRequest);
	}
	
	@RequestMapping(value = "view/{id}/worksheet", method = RequestMethod.GET)
	public ModelAndView openWorksheet(@PathVariable String id, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : openWorksheet][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		// get current session
		HttpSession session = request.getSession();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		if (isPermission()) {
			try {
				// load personnel
				PersonnelBean personnelBean = loadPersonnel(Long.valueOf(id));
				modelAndView.addObject("personnelBean", personnelBean);
				
//				WorksheetSearchBean worksheetSearchBean = new WorksheetSearchBean();
//				//init paging
//				Pagination pagination = createPagination(1, 10, "All", "worksheetlist", worksheetSearchBean);
//				modelAndView.addObject("pagination",pagination);
				
				// load worksheet count
				List<PersonnelAssign> personnelAssignList = new ArrayList<PersonnelAssign>();
				SeachCountWorksheetRequest seachCountWorksheetRequest = (SeachCountWorksheetRequest) session.getAttribute("seachCountWorksheetRequest");
				if(seachCountWorksheetRequest != null){
					modelAndView.addObject("seachCountWorksheetRequest",seachCountWorksheetRequest);
					personnelAssignList = searchAssignPersonnel(seachCountWorksheetRequest);
				}else{
					SeachCountWorksheetRequest seachCountWorksheetRequestInit = new SeachCountWorksheetRequest();
					seachCountWorksheetRequestInit.setPersonnelId(Long.valueOf(id));
					seachCountWorksheetRequestInit.setDateRange("");
					modelAndView.addObject("seachCountWorksheetRequest",new SeachCountWorksheetRequest());
					personnelAssignList = searchAssignPersonnel(seachCountWorksheetRequestInit);
				}
				
				AssignWorksheetController assignWorksheetController = new AssignWorksheetController();
				assignWorksheetController.setMessageSource(messageSource);
				List<WorksheetBean> worksheetBeanList = new ArrayList<WorksheetBean>();
				
				for(PersonnelAssign personnelAssign : personnelAssignList){
					Worksheet worksheet = personnelAssign.getHistoryTechnicianGroupWork().getWorkSheet();
					//check dup
					boolean checkDup = false;
					for(WorksheetBean worksheetBean : worksheetBeanList){
						if(worksheetBean.getIdWorksheetParent() == worksheet.getId()){
							checkDup = true;
						}
					}
					if(!checkDup) worksheetBeanList.add(assignWorksheetController.populateEntityToDto(worksheet));
				}
				
				modelAndView.addObject("worksheetBeanList",worksheetBeanList);
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		modelAndView.setViewName(CONTROLLER_NAME + "worksheet");
		return modelAndView;
	}
	
	// search request
	@RequestMapping(value = "/searchWorksheet", method = RequestMethod.POST)
	public ModelAndView searchWorksheet(@ModelAttribute("seachCountWorksheetRequest") SeachCountWorksheetRequest seachCountWorksheetRequest,
			HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {

		logger.info("[method : searchWorksheet][Type : Controller]");
		logger.info("[method : seachCountWorksheetRequest][seachCountWorksheetRequest : " + seachCountWorksheetRequest.toString() + "]");
		ModelAndView modelAndView = new ModelAndView();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			generateSearchSessionCount(seachCountWorksheetRequest, request);
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		modelAndView.setViewName(REDIRECT + "/personnel/view/" + seachCountWorksheetRequest.getPersonnelId() + "/worksheet");
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
				worksheetBeans.add(assignWorksheetController.populateEntityToDto(Worksheet));
			}
			pagination.setDataListBean(worksheetBeans);
			return pagination;
		}
	
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView searchPersonnel(@ModelAttribute("personnelSearchBean")
		PersonnelSearchBean personnelSearchBean, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		
		logger.info("[method : searchPersonnel][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			generateSearchSession(personnelSearchBean, request);
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		
		modelAndView.setViewName(REDIRECT+"/personnel");
		return modelAndView;
	}

	public List<PersonnelBean> searchPersonnel(String key, String position, Long permissionGroup){
		logger.info("[method : searchPersonnel][Type : Controller]");
		List<PersonnelBean> personnelBeans = new ArrayList<PersonnelBean>();
		try{
			List<Personnel> personnels = personnelService.searchBykey(key, position, permissionGroup);
			if(personnels != null){
				for(Personnel personnel : personnels){
					PersonnelBean personnelBean = populateEntityToDto(personnel);
					personnelBeans.add(personnelBean);
				}
				
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return personnelBeans;
	}
	
	
	////////Ajax method /////////////////
	
	@RequestMapping(value="openAdd", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody JsonResponse openAdd() {
		logger.info("[method : openAdd][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		List<CompanyBean> companyBeans = new ArrayList<CompanyBean>();
		
		if(isPermission()){
			try{
				//company init
				companyBeans = loadCompany();
				jsonResponse.setResult(companyBeans);
				
				//position init
				List<PositionBean> positonBeans = new ArrayList<PositionBean>();
				if(companyBeans.size() > 0){
					//set position
					positonBeans = loadPositionWithCompnay(companyBeans.get(0).getId());
					jsonResponse.setResultStore01(positonBeans);
				}
				
				//permission group init
				List<PermissionGroupBean> permissionGroupBeans = new ArrayList<PermissionGroupBean>();
				//set permission group
				permissionGroupBeans = loadPermissionGroup();
				jsonResponse.setResultStore02(permissionGroupBeans);
			 
				jsonResponse.setError(false);
			}catch(Exception ex){
				ex.printStackTrace();
				jsonResponse.setMessage(ex.getMessage());
				jsonResponse.setError(true);
				}
		}else{
			jsonResponse.setError(true);
		}
		
		return jsonResponse;
	}
	
	@RequestMapping(value="loadDataWithCompany/{id}", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody JsonResponse loadDataWithCompany(@PathVariable String id) {
		logger.info("[method : loadDataWithCompany][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			try{
				//position init
				List<PositionBean> positonBeans = new ArrayList<PositionBean>();
				// set position
				positonBeans = loadPositionWithCompnay(Long.valueOf(id));
				jsonResponse.setResultStore01(positonBeans);
				jsonResponse.setError(false);
			}catch(Exception ex){
				ex.printStackTrace();
				jsonResponse.setMessage(ex.getMessage());
				jsonResponse.setError(true);
				}
		}else{
			jsonResponse.setError(true);
		}
		
		return jsonResponse;
	}
	
	//check duplication personnel code
	@RequestMapping(value="checkDuplicatePersonnelCode/{personnelCode}", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody JsonResponse checkDuplicatePersonnelCode(@PathVariable String personnelCode) {
		logger.info("[method : checkDuplicatePersonnelCode][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			try{
				boolean duplicate = personnelService.checkDuplicatePersonnelCode(personnelCode);	
				jsonResponse.setResult(duplicate);
				jsonResponse.setError(false);
			}catch(Exception ex){
				ex.printStackTrace();
				jsonResponse.setMessage(ex.getMessage());
				jsonResponse.setError(true);
				}
		}else{
			jsonResponse.setError(true);
		}
		
		return jsonResponse;
	}
	
	
	//save personnel
	@RequestMapping(value="save", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse savePersonnel(@RequestBody final PersonnelBean personnelBean, HttpServletRequest request) {
		logger.info("[method : savePersonnel][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			//create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try{
				//save
				//set bean
				Personnel personnel = new Personnel();
				personnel.setPersonnelCode(personnelBean.getPersonnelCode());
				personnel.setSex(personnelBean.getSex());
				personnel.setPrefix(personnelBean.getPrefix());
				personnel.setFirstName(personnelBean.getFirstName());
				personnel.setLastName(personnelBean.getLastName());
				personnel.setNickName(personnelBean.getNickName());
				personnel.setDeleted(Boolean.FALSE);
				personnel.setCreateDate(CURRENT_TIMESTAMP);
				//---> change when LoginController success
				personnel.setCreatedBy(getUserNameLogin());
				
				//set compnay
				Company company = companyService.getCompanyById(personnelBean.getCompany().getId());
				if(company != null){
					personnel.setCompany(company);
				}
				
				//set position
				Position position = positionService.getPositionById(personnelBean.getPosition().getId());
				if(position != null){
					personnel.setPosition(position);
				}
				
				//set permission group
				PermissionGroup permissionGroup = permissionGroupService.getPermissionGroupById(personnelBean.getPermissionGroup().getId());
				if(permissionGroup != null){
					personnel.setPermissionGroup(permissionGroup);
				}
				
				//set contact
				Contact contact = new Contact();
				contact.setMobile(personnelBean.getContact().getMobile());
				contact.setEmail(personnelBean.getContact().getEmail());
				contact.setCreateDate(CURRENT_TIMESTAMP);
				//---> change when LoginController success
				contact.setCreatedBy(getUserNameLogin());
				contact.setPersonnel(personnel);
				personnel.setContact(contact);
				
				//set authen
				Authentication auth = new Authentication();
				auth.setUsername(personnelBean.getAuthenticationBean().getUsername());
				auth.setPassword(SecurityUtils.encrypt(personnelBean.getAuthenticationBean().getPassword()));
//				auth.setPassword(AuthenUtil.generateHash(personnelBean.getAuthenticationBean().getPassword()));
				auth.setEnabled(Boolean.TRUE);
				auth.setCreateDate(CURRENT_TIMESTAMP);
				//---> change when LoginController success
				auth.setCreatedBy(getUserNameLogin());
				auth.setPersonnel(personnel);
				personnel.setAuthentication(auth);
				
				Long personnelId = personnelService.save(personnel);
				if(personnelId != null){
					//auto generate personnel code
					if(personnel.getPersonnelCode() == null || personnel.getPersonnelCode().isEmpty()){
						Personnel personnelTemp = personnelService.getPersonnelById(personnelId);
						if(personnelTemp != null){
							personnelTemp.setPersonnelCode(generatePersonnelCode(personnelId));
							personnelService.update(personnelTemp);
						}else{
							//input text for message exception
							throw new Exception("");
						}
					 }
					//End auto generate personnel code
					jsonResponse.setError(false);
				}else{
					//input text for message exception
					throw new Exception("");
				}
				
			}catch(Exception ex){
				ex.printStackTrace();
				jsonResponse.setMessage(ex.getMessage());
				jsonResponse.setError(true);
				}
		}else{
			jsonResponse.setError(true);
		}
		
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("personnel.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	
	@SuppressWarnings("unused")
	@RequestMapping(value="delete/{id}", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public JsonResponse deletePersonnel(@PathVariable String id, HttpServletRequest request) {
		logger.info("[method : deletePersonnel][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			try{
				Personnel personnel = personnelService.getPersonnelById(Long.valueOf(id));
				personnel.setDeleted(Boolean.TRUE);
				personnel.setTechnicianGroupSub(null);
				if(personnel != null){
					personnelService.update(personnel);
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
		
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.delete.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("personnel.transaction.delete.success", null, LocaleContextHolder.getLocale()));
		//two way
		//generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.delete.success", new Object[]{personnel.getFirstname}, LocaleContextHolder.getLocale()), messageSource.getMessage("personnel.transaction.delete.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	@RequestMapping(value="openEdit/{id}", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody JsonResponse openEdit(@PathVariable String id) {
		logger.info("[method : openEdit][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			try{
				//load personnel
				PersonnelBean personnelBean = loadPersonnel(Long.valueOf(id));
				jsonResponse.setResult(personnelBean);
				
				//company init
				List<CompanyBean> companyBeans = new ArrayList<CompanyBean>();
				companyBeans = loadCompany();
				jsonResponse.setResultStore01(companyBeans);
				
				//position init
				List<PositionBean> positonBeans = new ArrayList<PositionBean>();
				if(personnelBean.getCompany() != null){
					//set position
					positonBeans = loadPositionWithCompnay(personnelBean.getCompany().getId());
					jsonResponse.setResultStore02(positonBeans);
				}
				
				//permission group init
				List<PermissionGroupBean> permissionGroupBeans = new ArrayList<PermissionGroupBean>();
				//set permission group
				permissionGroupBeans = loadPermissionGroup();
				jsonResponse.setResultStore03(permissionGroupBeans);

				jsonResponse.setError(false);
				
			}catch(Exception ex){
				ex.printStackTrace();
				jsonResponse.setMessage(ex.getMessage());
				jsonResponse.setError(true);
				}
		}else{
			jsonResponse.setError(true);
		}
		
		return jsonResponse;
	}
	
	// update personnel
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse updatePersonnel(@RequestBody final PersonnelBean personnelBean, HttpServletRequest request) {
		logger.info("[method : updatePersonnel][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			// create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try {
				// update
				// set bean
				Personnel personnel = personnelService.getPersonnelById(personnelBean.getId());
				if(personnel != null){
					personnel.setPersonnelCode(personnelBean.getPersonnelCode());
					personnel.setSex(personnelBean.getSex());
					personnel.setPrefix(personnelBean.getPrefix());
					personnel.setFirstName(personnelBean.getFirstName());
					personnel.setLastName(personnelBean.getLastName());
					personnel.setNickName(personnelBean.getNickName());
					personnel.setUpdatedDate(CURRENT_TIMESTAMP);
					// ---> change when LoginController success
					personnel.setCreatedBy(getUserNameLogin());

					// set compnay
					Company company = companyService.getCompanyById(personnelBean.getCompany().getId());
					if (company != null) {
						personnel.setCompany(company);
					}

					// set position
					Position position = positionService.getPositionById(personnelBean.getPosition().getId());
					if (company != null) {
						personnel.setPosition(position);
					}

					// set permission group
					PermissionGroup permissionGroup = permissionGroupService
							.getPermissionGroupById(personnelBean.getPermissionGroup().getId());
					if (permissionGroup != null) {
						personnel.setPermissionGroup(permissionGroup);
					}

					// set contact
					personnel.getContact().setMobile(personnelBean.getContact().getMobile());
					personnel.getContact().setEmail(personnelBean.getContact().getEmail());
					
					if(personnel.getAuthentication() != null){
						personnel.getAuthentication().setUsername(personnelBean.getAuthenticationBean().getUsername());
						personnel.getAuthentication().setPassword(SecurityUtils.encrypt(personnelBean.getAuthenticationBean().getPassword()));
//						personnel.getAuthentication().setPassword(AuthenUtil.generateHash(personnelBean.getAuthenticationBean().getPassword()));						
					}else{
						Authentication auth = new Authentication();
						auth.setUsername(personnelBean.getAuthenticationBean().getUsername());
						auth.setPassword(SecurityUtils.encrypt(personnelBean.getAuthenticationBean().getPassword()));
//						auth.setPassword(AuthenUtil.generateHash(personnelBean.getAuthenticationBean().getPassword()));
						auth.setEnabled(Boolean.TRUE);
						auth.setCreateDate(CURRENT_TIMESTAMP);
						//---> change when LoginController success
						auth.setCreatedBy(getUserNameLogin());
						auth.setPersonnel(personnel);
						personnel.setAuthentication(auth);
						
					}
					
					personnelService.update(personnel);
					jsonResponse.setError(false);
				}else{
					//input text for message exception
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

		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("personnel.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	@RequestMapping(value = "generatePersonnelSearchSession", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse generatePersonnelSearchSession(@RequestBody final PersonnelSearchBean personnelSearchBean, HttpServletRequest request) {
		logger.info("[method : generatePersonnelSearchSession][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			generateSearchSession(personnelSearchBean, request);
		}else {
			jsonResponse.setError(true);
		}
		
		return jsonResponse;
	}
	
	@RequestMapping(value = "changepassword/{id}", method = RequestMethod.GET)
	public ModelAndView changepassword(@PathVariable String id, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : changepassword][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		// get current session
		HttpSession session = request.getSession();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		if (isPermission()) {
			try {
				// load personnel
				PersonnelBean personnelBean = loadPersonnel(Long.valueOf(id));
				modelAndView.addObject("personnelBean", personnelBean);
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}

		modelAndView.setViewName(CONTROLLER_NAME + "change_password");
		return modelAndView;
	}
	
	//load company to dropdown
	public List<CompanyBean> loadCompany() throws Exception{
		CompanyController companyController = new CompanyController();
		List<CompanyBean> companyBeans = new ArrayList<CompanyBean>();
		try{
			List<Company> companys = companyService.findAll();
			for(Company company : companys){
				CompanyBean companyBean = new CompanyBean();
				companyBean = companyController.populateEntityToDto(company);
				companyBeans.add(companyBean);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			throw(ex);
		}
		return companyBeans;
	}
	//load position to dropdown
	public List<PositionBean> loadPositionWithCompnay(Long companyId) throws Exception{
		PositionController positionController = new PositionController();
		List<PositionBean> positionBeans = new ArrayList<PositionBean>();
		try{
			List<Position> positions = new ArrayList<Position>();
			positions = positionService.findByCompanyId(companyId);
			for(Position position : positions){
				PositionBean positionBean = new PositionBean();
				positionBean = positionController.populateEntityToDto(position);
				positionBeans.add(positionBean);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			throw(ex);
		}
		
		return positionBeans;
	}
	
	//load permission group
	public List<PermissionGroupBean> loadPermissionGroup() throws Exception{
		List<PermissionGroupBean> permissionGroupBeans = new ArrayList<PermissionGroupBean>();
		try{
			List<PermissionGroup> permissionGroups = new ArrayList<PermissionGroup>();
			permissionGroups = permissionGroupService.findAll();
			for(PermissionGroup permissGroup : permissionGroups){
				//new permissiongroup bean
				PermissionGroupBean permissionGroupBean = new PermissionGroupBean();
				permissionGroupBean.setId(permissGroup.getId());
				permissionGroupBean.setPermissionGroupName(permissGroup.getPermissionGroupName());
				permissionGroupBeans.add(permissionGroupBean);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			throw(ex);
		}
		
		return permissionGroupBeans;
	}
	
	public PersonnelBean loadPersonnel(Long id) throws Exception{
		PersonnelBean personnelBean = new PersonnelBean();
		try{
			Personnel personnel = personnelService.getPersonnelById(id);
			if(personnel != null){
				personnelBean = populateEntityToDto(personnel);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			throw(ex);
		}
		return personnelBean;
	}

	public PersonnelBean populateEntityToDto(Personnel personnel){
		PersonnelBean personnelBean = new PersonnelBean();
		personnelBean.setId(personnel.getId());
		personnelBean.setSex(personnel.getSex());
		personnelBean.setPrefix(personnel.getPrefix());
		personnelBean.setPersonnelCode(personnel.getPersonnelCode());
		personnelBean.setFirstName(personnel.getFirstName());
		personnelBean.setLastName(personnel.getLastName());
		personnelBean.setNickName(personnel.getNickName());
		//company
		CompanyBean companyBean = new CompanyBean();
		companyBean.setId(personnel.getCompany().getId());
		companyBean.setCompanyName(personnel.getCompany().getCompanyName());
		personnelBean.setCompany(companyBean);
		//position
		PositionBean postionBean = new PositionBean();
		postionBean.setId(personnel.getPosition().getId());
		postionBean.setPositionName(personnel.getPosition().getPositionName());
		personnelBean.setPosition(postionBean);
		
		//permission group
		PermissionGroupBean PermissionGroupBean = new PermissionGroupBean();
		PermissionGroupBean.setId(personnel.getPermissionGroup().getId());
		PermissionGroupBean.setPermissionGroupName(personnel.getPermissionGroup().getPermissionGroupName());
		personnelBean.setPermissionGroup(PermissionGroupBean);
		
		//contact
		ContactBean contactBean = new ContactBean();
		contactBean.setId(personnel.getContact().getId());
		contactBean.setMobile(personnel.getContact().getMobile());
		contactBean.setEmail(personnel.getContact().getEmail());
		personnelBean.setContact(contactBean);
		
		//technician sub group
		if(personnel.getTechnicianGroupSub() != null){
			TechnicianGroupBean technicianGroupBean = new TechnicianGroupBean();
			technicianGroupBean.setId(personnel.getTechnicianGroupSub().getId());
			technicianGroupBean.setTechnicianGroupName(personnel.getTechnicianGroupSub().getTechnicianGroupName());
			personnelBean.setTechnicianGroupBean(technicianGroupBean);
		}
		
		//authen
		if(personnel.getAuthentication() != null){
			AuthenticationBean authenticationBean = new AuthenticationBean();
			authenticationBean.setUsername(personnel.getAuthentication().getUsername());
			authenticationBean.setPassword(SecurityUtils.decrypt(personnel.getAuthentication().getPassword()));
//			authenticationBean.setPassword(AuthenUtil.reverseHash(personnel.getAuthentication().getPassword()));
			authenticationBean.setEnabled(personnel.getAuthentication().isEnabled());
			personnelBean.setAuthenticationBean(authenticationBean);
		}
		
		return personnelBean;
	}
	
	public void generateAlert(JsonResponse jsonResponse, HttpServletRequest request, String title, String detail){
		new AlertUtil().generateAlert(jsonResponse, request, title, detail);
	}
	
	public String generatePersonnelCode(Long personnelId){
		StringBuffer str = new StringBuffer();
		//set EMP
		str = str.append("EMP");
		//set number for personnel id
		final DecimalFormat decimalFormat = new DecimalFormat("000000");
		str = str.append(decimalFormat.format(personnelId));
		//set dash
		str = str.append("-");
		//get current year

		Calendar now = Calendar.getInstance(new Locale("EN","en"));

		int year = now.get(Calendar.YEAR);
		str = str.append(year);
		
		return str.toString();
	}
	
	public void generateSearchSession(PersonnelSearchBean personnelSearchBean, HttpServletRequest request){
		HttpSession session = request.getSession();
		session.setAttribute("personnelSearchBean", personnelSearchBean);
	}

	//getter or setter dependency
	public void setPersonnelService(PersonnelService personnelService) {
		this.personnelService = personnelService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public void setPositionService(PositionService positionService) {
		this.positionService = positionService;
	}
	
	public void setPermissionGroupService(PermissionGroupService permissionGroupService) {
		this.permissionGroupService = permissionGroupService;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

}
