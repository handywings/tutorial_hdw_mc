package com.hdw.mccable.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.hdw.mccable.dto.JsonRequestMemberTechnicianGroup;
import com.hdw.mccable.dto.JsonResponse;
import com.hdw.mccable.dto.PersonnelBean;
import com.hdw.mccable.dto.TechnicianGroupBean;
import com.hdw.mccable.entity.Personnel;
import com.hdw.mccable.entity.TechnicianGroup;
import com.hdw.mccable.service.PersonnelService;
import com.hdw.mccable.service.TechnicianGroupService;
import com.hdw.mccable.utils.AlertUtil;

@Controller
@RequestMapping("/techniciangroup")
public class TechnicianGroupController extends BaseController {
	final static Logger logger = Logger.getLogger(TechnicianGroupController.class);
	public static final String CONTROLLER_NAME = "techniciangroup/";
	public static final String DETAIL_PAGE = "detail";

	// initial service
	@Autowired(required = true)
	@Qualifier(value = "technicianGroupService")
	private TechnicianGroupService technicianGroupService;

	@Autowired(required = true)
	@Qualifier(value = "personnelService")
	private PersonnelService personnelService;
	
	@Autowired
    MessageSource messageSource;

	// end initial service

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView initTechnicianGroup(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : initTechnicianGroup][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = null;
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			try {
				// technician group list
				List<TechnicianGroup> technicianGroups = technicianGroupService.findAll();
				List<TechnicianGroupBean> technicianGroupBeans = new ArrayList<TechnicianGroupBean>();
				for (TechnicianGroup technicianGroup : technicianGroups) {
					TechnicianGroupBean technicianGroupBean = new TechnicianGroupBean();
					technicianGroupBean = populateEntityToDto(technicianGroup);
					technicianGroupBeans.add(technicianGroupBean);
				}
				modelAndView.addObject("technicianGroupBeans", technicianGroupBeans);
				// End technician group list

			} catch (Exception ex) {
				ex.printStackTrace();
				// redirect page error

			}
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}

		// alert session
		session = request.getSession();
		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);

		// remove session
		session.removeAttribute("alert");

		modelAndView.setViewName(CONTROLLER_NAME + INIT);
		return modelAndView;
	}

	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public ModelAndView detailTechnicianGroup(@PathVariable Long id, Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : detailTechnicianGroup][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		PersonnelController personnelControler = new PersonnelController();
		HttpSession session = null;
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			// load technician group
			TechnicianGroup technicianGroup = technicianGroupService.getTechnicianGroupById(id);
			if (technicianGroup != null) {
				modelAndView.addObject("technicianGroupBean", populateEntityToDto(technicianGroup));
			} else {
				modelAndView.addObject("technicianGroupBean", new TechnicianGroupBean());
			}

			// load personnel not member
			List<Personnel> personnels = personnelService.getPersonnelNotMember();
			List<PersonnelBean> personnelBeans = new ArrayList<PersonnelBean>();

			for (Personnel personnel : personnels) {
				PersonnelBean personnelBean = new PersonnelBean();
				personnelBean = personnelControler.populateEntityToDto(personnel);
				personnelBeans.add(personnelBean);
			}
			modelAndView.addObject("personnelBeans", personnelBeans);

		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}

		// alert session
		session = request.getSession();
		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);

		// remove session
		session.removeAttribute("alert");

		modelAndView.setViewName(CONTROLLER_NAME + DETAIL_PAGE);
		return modelAndView;
	}

	@RequestMapping(value = "openAddTechnicianGroup", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JsonResponse openAddTechnicianGroup() {
		logger.info("[method : openAddTechnicianGroup][Type : Controller]");
		PersonnelController personnelController = new PersonnelController();
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {

				List<Personnel> personnels = personnelService.getPersonnelNotMember();
				List<PersonnelBean> personnelBeans = new ArrayList<PersonnelBean>();
				for (Personnel personnel : personnels) {
					PersonnelBean personnelBean = new PersonnelBean();
					personnelBean = personnelController.populateEntityToDto(personnel);
					personnelBeans.add(personnelBean);
				}
				jsonResponse.setResult(personnelBeans);
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

	// save personnel
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse saveTechnicianGroup(@RequestBody final TechnicianGroupBean technicianGroupBean,
			HttpServletRequest request) {
		logger.info("[method : saveTechnicianGroup][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			// create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try {
				// set technicain group
				TechnicianGroup technicianGroup = new TechnicianGroup();
				technicianGroup.setTechnicianGroupName(technicianGroupBean.getTechnicianGroupName());
				technicianGroup.setCreateDate(CURRENT_TIMESTAMP);
				technicianGroup.setDeleted(Boolean.FALSE);
				// ---> change when LoginController success
				technicianGroup.setCreatedBy(getUserNameLogin());

				// set member leader
				Personnel personnel = personnelService.getPersonnelById(technicianGroupBean.getPersonnel().getId());
				if (personnel != null) {
					technicianGroup.setPersonnel(personnel);
					// personnel.setTechnicianGroup(technicianGroup);
					Long technicianGroupId = technicianGroupService.save(technicianGroup);

					// check save success
					if (technicianGroupId != null) {
						TechnicianGroup tng = technicianGroupService.getTechnicianGroupById(technicianGroupId);
						personnel.setTechnicianGroupSub(tng);
						// ---> change when LoginController success
						personnel.setUpdatedBy(getUserNameLogin());
						personnel.setUpdatedDate(CURRENT_TIMESTAMP);
						personnelService.update(personnel);
					} else {
						// input text for message exception
						throw new Exception("");
					}

				} else {
					// input text for message exception
					throw new Exception("");
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
		
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("techniciangroup.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}

	@RequestMapping(value = "openEditTechnicianGroup/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JsonResponse openEditTechnicianGroup(@PathVariable String id) {
		logger.info("[method : openEditTechnicianGroup][Type : Controller]");
		PersonnelController personnelController = new PersonnelController();
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				// load technician group
				TechnicianGroup technicianGroup = technicianGroupService.getTechnicianGroupById(Long.valueOf(id));
				if (technicianGroup != null) {
					TechnicianGroupBean technicianGroupBean = populateEntityToDto(technicianGroup);
					jsonResponse.setResultStore01(technicianGroupBean);
				}

				List<Personnel> personnels = new ArrayList<Personnel>();
				if (technicianGroup != null) {
					personnels = personnelService.getPersonnelNotMember(technicianGroup.getPersonnel().getId());
				}
				List<PersonnelBean> personnelBeans = new ArrayList<PersonnelBean>();
				for (Personnel personnel : personnels) {
					PersonnelBean personnelBean = new PersonnelBean();
					personnelBean = personnelController.populateEntityToDto(personnel);
					personnelBeans.add(personnelBean);
				}
				jsonResponse.setResult(personnelBeans);

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

	// save personnel
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse updateTechnicianGroup(@RequestBody final TechnicianGroupBean technicianGroupBean,
			HttpServletRequest request) {
		logger.info("[method : updateTechnicianGroup][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			// create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try {
				// load technician group
				TechnicianGroup technicianGroup = technicianGroupService
						.getTechnicianGroupById(technicianGroupBean.getId());
				// load personnel new
				Personnel personnelNew = personnelService.getPersonnelById(technicianGroupBean.getPersonnel().getId());

				// update null to old personnel
				Personnel personnelOld = technicianGroup.getPersonnel();
				// ---> change when LoginController success
				personnelOld.setUpdatedBy(getUserNameLogin());
				personnelOld.setUpdatedDate(CURRENT_TIMESTAMP);
				personnelOld.setTechnicianGroupSub(null);
				personnelService.update(personnelOld);

				// update technician
				technicianGroup.setPersonnel(personnelNew);
				technicianGroup.setTechnicianGroupName(technicianGroupBean.getTechnicianGroupName());
				technicianGroup.setUpdatedDate(CURRENT_TIMESTAMP);

				// ---> change when LoginController success
				technicianGroup.setUpdatedBy(getUserNameLogin());
				technicianGroupService.update(technicianGroup);

				// update personnel new
				// ---> change when LoginController success
				personnelNew.setUpdatedBy(getUserNameLogin());
				personnelNew.setUpdatedDate(CURRENT_TIMESTAMP);
				personnelNew.setTechnicianGroupSub(technicianGroup);
				personnelService.update(personnelNew);
				jsonResponse.setError(false);

			} catch (Exception ex) {
				ex.printStackTrace();
				jsonResponse.setMessage(ex.getMessage());
				jsonResponse.setError(true);
			}
		} else {
			jsonResponse.setError(true);
		}

		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("techniciangroup.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}

	// save personnel
	@RequestMapping(value = "saveMember", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse saveMember(
			@RequestBody final List<JsonRequestMemberTechnicianGroup> jsonRequestMemberTechnicianGroups,
			HttpServletRequest request) {
		logger.info("[method : saveMember][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			// create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try {
				if (jsonRequestMemberTechnicianGroups.size() > 0) {
					TechnicianGroup technicianGroup = technicianGroupService
							.getTechnicianGroupById(jsonRequestMemberTechnicianGroups.get(0).getTechnicianGroupId());
					
					if (technicianGroup != null) {
						logger.info("[method : saveMember][Type : Controller][technicianGroup id : "+ technicianGroup.getId() + "]");
						for (JsonRequestMemberTechnicianGroup jsonRmt : jsonRequestMemberTechnicianGroups) {
							Personnel personnel = personnelService.getPersonnelById(jsonRmt.getId());
							if (personnel != null) {
								logger.info("[method : saveMember][Type : Controller][personnel id : "+ personnel.getId() + "]");
								personnel.setUpdatedBy(getUserNameLogin());
								personnel.setUpdatedDate(CURRENT_TIMESTAMP);
								personnel.setTechnicianGroupSub(technicianGroup);
								// update personnel
								 personnelService.update(personnel);
							}
						}
					}else{
						// input text for message exception
						throw new Exception("");
					}

				} else {
					// input text for message exception
					throw new Exception("");
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
		
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("techniciangroup.transaction.member.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonResponse deleteTechnicianGroup(@PathVariable String id, HttpServletRequest request) {
		logger.info("[method : deleteTechnicianGroup][Type : Controller]");

		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				// move personnel under technician group
				int result = personnelService.removePersonnelRefTechnicianGroup(Long.valueOf(id));
				logger.info("[method : deleteTechnicianGroup][Type : Controller]"
						+ "[return removePersonnelRefTechnicianGroup result : " + result + "]");

				// remove technician group
				TechnicianGroup technicianGroup = technicianGroupService.getTechnicianGroupById(Long.valueOf(id));
				if (technicianGroup != null) {
					technicianGroup.setDeleted(Boolean.TRUE);
					technicianGroup.setPersonnel(null);
					technicianGroupService.update(technicianGroup);
				} else {
					// input text for message exception
					throw new Exception("");
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

		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.delete.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("techniciangroup.transaction.delete.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "deleteMember/{technicianGroupId}/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonResponse deleteMember(@PathVariable String id, @PathVariable String technicianGroupId,
			HttpServletRequest request) {
		logger.info("[method : deleteMember][Type : Controller]");

		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				TechnicianGroup TechnicianGroup = technicianGroupService
						.getTechnicianGroupById(Long.valueOf(technicianGroupId));
				Personnel personnel = personnelService.getPersonnelById(Long.valueOf(id));

				if (TechnicianGroup.getPersonnel().getId() == personnel.getId()) {
					// input text for message exception
					throw new Exception("");
				} else {
					if (personnel != null) {
						personnel.setTechnicianGroupSub(null);
						personnelService.update(personnel);
					} else {
						// input text for message exception
						throw new Exception("");
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				jsonResponse.setMessage(ex.getMessage());
				jsonResponse.setError(true);
			}
		} else {
			jsonResponse.setError(true);
		}
		if (!jsonResponse.isError()) {
			generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.delete.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("techniciangroup.transaction.member.delete.success", null, LocaleContextHolder.getLocale()));
		}
		return jsonResponse;
	}

	@RequestMapping(value = "loadMember", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonResponse loadMember(HttpServletRequest request) {
		logger.info("[method : loadMember][Type : Controller]");

		JsonResponse jsonResponse = new JsonResponse();
		PersonnelController personnelControler = new PersonnelController();

		if (isPermission()) {
			try {
				List<Personnel> personnels = personnelService.getPersonnelNotMember();
				List<PersonnelBean> personnelBeans = new ArrayList<PersonnelBean>();

				for (Personnel personnel : personnels) {
					PersonnelBean personnelBean = new PersonnelBean();
					personnelBean = personnelControler.populateEntityToDto(personnel);
					personnelBeans.add(personnelBean);
				}

				jsonResponse.setResult(personnelBeans);
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
	
	@RequestMapping(value = "loadTechnician/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JsonResponse loadTechnician(@PathVariable String id) {
		logger.info("[method : loadTechnician][Type : Controller]");
		//PersonnelController personnelController = new PersonnelController();
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				// load technician group
				TechnicianGroup technicianGroup = technicianGroupService.getTechnicianGroupById(Long.valueOf(id));
				if (technicianGroup != null) {
					TechnicianGroupBean technicianGroupBean = populateEntityToDto(technicianGroup);
					jsonResponse.setResult(technicianGroupBean);
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
	
	@RequestMapping(value = "loadAllTechnician", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JsonResponse loadAllTechnician() {
		logger.info("[method : loadTechnician][Type : Controller]");
		PersonnelController personnelController = new PersonnelController();
		personnelController.setMessageSource(messageSource);
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				// load technician group
				List<PersonnelBean> personnelBeanList = new ArrayList<PersonnelBean>();
				List<TechnicianGroup> technicianGroupList = technicianGroupService.findAll();
				for(TechnicianGroup technicianGroup : technicianGroupList){
					for(Personnel personnel : technicianGroup.getPersonnels()){
						PersonnelBean personnelBean = personnelController.populateEntityToDto(personnel);
						personnelBeanList.add(personnelBean);
					}
				}
				
//				if (technicianGroup != null) {
//					TechnicianGroupBean technicianGroupBean = populateEntityToDto(technicianGroup);
//					jsonResponse.setResult(technicianGroupBean);
//				}
				jsonResponse.setResult(personnelBeanList);
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

	// populate
	public TechnicianGroupBean populateEntityToDto(TechnicianGroup technicianGroup) {
		TechnicianGroupBean technicianGroupBean = new TechnicianGroupBean();
		
		if(technicianGroup != null) {
			PersonnelController personnelController = new PersonnelController();			
			technicianGroupBean.setId(technicianGroup.getId());
			technicianGroupBean.setTechnicianGroupName(technicianGroup.getTechnicianGroupName());

			// personnel leader
			PersonnelBean personnelBean = new PersonnelBean();
			if (technicianGroup.getPersonnel() != null) {
				personnelBean.setId(technicianGroup.getPersonnel().getId());
				personnelBean.setPersonnelCode(technicianGroup.getPersonnel().getPersonnelCode());
				personnelBean.setFirstName(technicianGroup.getPersonnel().getFirstName());
				personnelBean.setLastName(technicianGroup.getPersonnel().getLastName());
				personnelBean.setNickName(technicianGroup.getPersonnel().getNickName());
			}
			technicianGroupBean.setPersonnel(personnelBean);

			// personnel list
			List<PersonnelBean> personnelBeans = new ArrayList<PersonnelBean>();
			for (Personnel personnel : technicianGroup.getPersonnels()) {
				PersonnelBean PersonnelMemberBean = new PersonnelBean();
				PersonnelMemberBean = personnelController.populateEntityToDto(personnel);
				//detatch check is delete
				if(!personnel.isDeleted()) personnelBeans.add(PersonnelMemberBean);
			}
			technicianGroupBean.setMemberSize(personnelBeans.size());
			technicianGroupBean.setPersonnelList(personnelBeans);
		}
		
		return technicianGroupBean;
	}

	public void generateAlert(JsonResponse jsonResponse, HttpServletRequest request, String title, String detail) {
		new AlertUtil().generateAlert(jsonResponse, request, title, detail);
	}

	// getter and setter
	public void setTechnicianGroupService(TechnicianGroupService technicianGroupService) {
		this.technicianGroupService = technicianGroupService;
	}

	public void setPersonnelService(PersonnelService personnelService) {
		this.personnelService = personnelService;
	}

}
