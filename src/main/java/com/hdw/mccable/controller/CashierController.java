package com.hdw.mccable.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.hdw.mccable.dto.CashierBean;
import com.hdw.mccable.dto.CashierSearchBean;
import com.hdw.mccable.dto.JsonRequestCashier;
import com.hdw.mccable.dto.JsonResponse;
import com.hdw.mccable.entity.Personnel;
import com.hdw.mccable.service.PersonnelService;
import com.hdw.mccable.utils.AlertUtil;

@Controller
@RequestMapping("/cashier")
public class CashierController extends BaseController{
	final static Logger logger = Logger.getLogger(CashierController.class);
	public static final String CONTROLLER_NAME = "cashier/";
	
	@Autowired
    MessageSource messageSource;
	
	// initial service
	@Autowired(required = true)
	@Qualifier(value = "personnelService")
	private PersonnelService personnelService;

	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView initCashier(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : initCashier][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		List<CashierBean> cashierIsTrue = new ArrayList<CashierBean>();
		List<Personnel> cashierIsFalse = new ArrayList<Personnel>();
		List<Personnel> cashier = new ArrayList<Personnel>();

		HttpSession session = request.getSession();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission 
		if (isPermission()) {
			try {
				Map<String, Object> cretiria = new HashMap<String, Object>();
				cretiria.put("daterange", "noDate");
				if (session.getAttribute("daterange") != null) {
					String daterange = session.getAttribute("daterange").toString();
					cretiria.put("daterange", daterange);
				}
				
				if ("home".equals(session.getAttribute("event")) || session.getAttribute("event") == null) {
					removeSearchSession(request);	
					session.setAttribute("type", "percent");
				}
				
				cashier = personnelService.findPersonnelCashier(true);
				cashierIsFalse = personnelService.findPersonnelCashier(false);
				for (Personnel personnel : cashier) {
					Long personnelId = personnel.getId();
					cretiria.put("personnelId", personnelId);
					CashierBean cashierBean = personnelService.searchCashierByCriteria(cretiria);
					cashierBean.setId(personnelId);
					cashierBean.setFullName(personnel.getPrefix()+""+personnel.getFirstName()+" "+personnel.getLastName());
					cashierIsTrue.add(cashierBean);
				}
				
				session.setAttribute("event", "home");
			} catch (Exception e) {
				e.printStackTrace();
			}			
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}		

		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);
		modelAndView.addObject("cashierIsTrue", cashierIsTrue);
		modelAndView.addObject("cashierIsFalse", cashierIsFalse);
		
		//remove session
		session.removeAttribute("alert");
		
		modelAndView.setViewName(CONTROLLER_NAME+INIT);
		return modelAndView;
	}	
	
	@RequestMapping(value = "delete/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonResponse deleteTechnicianGroup(@PathVariable Long id, HttpServletRequest request) {
		logger.info("[method : deleteCashier][Type : Controller]");

		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				// remove technician group
				Personnel personnel = personnelService.getPersonnelById(id);
				if (personnel != null) {
					personnel.setCashier(Boolean.FALSE);
					personnelService.update(personnel);
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

		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.delete.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("cashier.transaction.delete.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	// save personnel
		@RequestMapping(value = "saveMember", method = RequestMethod.POST)
		@ResponseBody
		public JsonResponse saveMember(
				@RequestBody final List<JsonRequestCashier> jsonRequestCashier,
				HttpServletRequest request) {
			logger.info("[method : saveMember][Type : Controller]");
			JsonResponse jsonResponse = new JsonResponse();
			if (isPermission()) {
				try {
					if (jsonRequestCashier.size() > 0) {
						for (JsonRequestCashier jsonRmt : jsonRequestCashier) {
							Personnel personnel = personnelService.getPersonnelById(jsonRmt.getId());
							if (personnel != null) {
								logger.info("[method : saveMember][Type : Controller][personnel id : "+ personnel.getId() + "]");
								personnel.setCashier(Boolean.TRUE);
								// update personnel
								personnelService.update(personnel);
							}
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
			
			generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("cashier.transaction.save.success", null, LocaleContextHolder.getLocale()));
			return jsonResponse;
		}
	
		// search request
		@RequestMapping(value = "/search", method = RequestMethod.POST)
		public ModelAndView cashierInvoice(
				@ModelAttribute("cashierInvoice") CashierSearchBean cashierSearchBean,
				HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {

			logger.info("[method : cashierInvoice][Type : Controller]");
			ModelAndView modelAndView = new ModelAndView();			
			
			// check authentication
			if (!isAuthentication()) {
				modelAndView.setViewName(REDIRECT + "/login");
				return modelAndView;
			}
			// check permission
			if (isPermission()) {
				generateSearchSession(cashierSearchBean, request);
			} else {
				//no permission
				httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			}
			modelAndView.setViewName(REDIRECT + "/cashier");
			return modelAndView;
		}
		
		// create search session
		public void generateSearchSession(CashierSearchBean cashierSearchBean, HttpServletRequest request) {
			HttpSession session = request.getSession();
			session.setAttribute("event", "search");
			session.setAttribute("daterange", cashierSearchBean.getDaterange());
		}
		
		// create search session
		public void removeSearchSession(HttpServletRequest request) {
			HttpSession session = request.getSession();
			session.removeAttribute("daterange");
		}
		
	public void generateAlert(JsonResponse jsonResponse, HttpServletRequest request, String title, String detail) {
		new AlertUtil().generateAlert(jsonResponse, request, title, detail);
	}
	
	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	
}
