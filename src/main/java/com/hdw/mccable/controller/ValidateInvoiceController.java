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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hdw.mccable.dto.AlertBean;
import com.hdw.mccable.dto.InvoiceDocumentBean;
import com.hdw.mccable.dto.JsonResponse;
import com.hdw.mccable.dto.PersonnelBean;
import com.hdw.mccable.dto.SearchBillScanBean;
import com.hdw.mccable.dto.ValidateInvoiceBean;
import com.hdw.mccable.dto.ZoneBean;
import com.hdw.mccable.entity.Invoice;
import com.hdw.mccable.entity.Personnel;
import com.hdw.mccable.entity.Zone;
import com.hdw.mccable.service.FinancialService;
import com.hdw.mccable.service.PersonnelService;
import com.hdw.mccable.service.ZoneService;
import com.hdw.mccable.utils.AlertUtil;
import com.hdw.mccable.utils.DateUtil;

@Controller
@RequestMapping("/validateinvoice")
public class ValidateInvoiceController extends BaseController {
	final static Logger logger = Logger.getLogger(ValidateInvoiceController.class);
	public static final String CONTROLLER_NAME = "validateinvoice/";
	public static final String IMPORT = "import";
	public static final String EXPORT = "export";

	@Autowired(required = true)
	@Qualifier(value = "financialService")
	private FinancialService financialService;

	@Autowired(required = true)
	@Qualifier(value = "personnelService")
	private PersonnelService personnelService;

	@Autowired(required = true)
	@Qualifier(value = "zoneService")
	private ZoneService zoneService;

	@Autowired
	MessageSource messageSource;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView initValidateInvioce(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : initValidateInvioce][Type : Controller]");
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
				// personnel cashier
				List<Personnel> personnelCashierList = personnelService.findPersonnelCashier(Boolean.TRUE);
				modelAndView.addObject("personnelCashierList", loadPersonnelCashierList(personnelCashierList));

				// zone list
				List<Zone> zones = zoneService.findAll();
				List<ZoneBean> zoneBeans = new ArrayList<ZoneBean>();
				ZoneController zoneController = new ZoneController();
				zoneController.setMessageSource(messageSource);
				for (Zone zone : zones) {
					ZoneBean zoneBean = zoneController.populateEntityToDto(zone);
					zoneBeans.add(zoneBean);
				}
				modelAndView.addObject("zoneBeanList", zoneBeans);

				// search bean
				List<InvoiceDocumentBean> invoiceBeanList = new ArrayList<InvoiceDocumentBean>();
				SearchBillScanBean searchBillScanBean = (SearchBillScanBean) session.getAttribute("searchBillScanBean");
				if (searchBillScanBean != null) {
					modelAndView.addObject("searchBillScanBean", searchBillScanBean);
					invoiceBeanList = searchInvoiceProcess(searchBillScanBean);
				} else {
					modelAndView.addObject("searchBillScanBean", new SearchBillScanBean());
					invoiceBeanList = searchInvoiceProcess(new SearchBillScanBean());
				}
				modelAndView.addObject("invoiceBeanList", invoiceBeanList);

			} catch (Exception ex) {
				ex.printStackTrace();
				// redirect page error
			}

		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}

		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);

		// remove session
		session.removeAttribute("alert");
		session.removeAttribute("searchBillScanBean");

		modelAndView.setViewName(CONTROLLER_NAME + INIT);
		return modelAndView;
	}

	// search request
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView searchExportIvoice(@ModelAttribute("searchBillScanBean") SearchBillScanBean searchBillScanBean,
			HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {

		logger.info("[method : searchExportIvoice][Type : Controller]");
		logger.info("[method : searchExportIvoice][searchBillScanBean : " + searchBillScanBean.toString() + "]");
		ModelAndView modelAndView = new ModelAndView();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			generateSearchSession(searchBillScanBean, request);
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		modelAndView.setViewName(REDIRECT + "/validateinvoice");
		return modelAndView;
	}

	// create search session
	public void generateSearchSession(SearchBillScanBean searchBillScanBean, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("searchBillScanBean", searchBillScanBean);
	}

	public List<InvoiceDocumentBean> searchInvoiceProcess(SearchBillScanBean searchBillScanBean) {
		InvoiceController invoiceController = new InvoiceController();
		invoiceController.setMessageSource(messageSource);
		List<InvoiceDocumentBean> invoiceBeanList = new ArrayList<InvoiceDocumentBean>();

		List<Invoice> invoiceList = financialService.findInvoiceSearch(searchBillScanBean);
		for (Invoice invoice : invoiceList) {
			InvoiceDocumentBean invoiceDocumentBean = new InvoiceDocumentBean();
			invoiceDocumentBean = invoiceController.PoppulateInvoiceEntityToDto(invoice);
			invoiceBeanList.add(invoiceDocumentBean);
		}

		return invoiceBeanList;
	}

	@RequestMapping(value = "import", method = RequestMethod.GET)
	public ModelAndView initImportInvioce(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : initValidateInvioce][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			try {

			} catch (Exception ex) {
				ex.printStackTrace();
				// redirect page error
			}

		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}

		HttpSession session = request.getSession();
		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);

		// remove session
		session.removeAttribute("alert");

		modelAndView.setViewName(CONTROLLER_NAME + IMPORT);
		return modelAndView;
	}

	@RequestMapping(value = "export", method = RequestMethod.GET)
	public ModelAndView initExportInvioce(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : initExportInvioce][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			try {
				List<Personnel> personnelCashierList = personnelService.findPersonnelCashier(Boolean.TRUE);
				modelAndView.addObject("personnelCashierList", loadPersonnelCashierList(personnelCashierList));
			} catch (Exception ex) {
				ex.printStackTrace();
				// redirect page error
			}

		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		HttpSession session = request.getSession();
		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);

		// remove session
		session.removeAttribute("alert");

		modelAndView.setViewName(CONTROLLER_NAME + EXPORT);
		return modelAndView;
	}

	// load loadInvoice By id
	@RequestMapping(value = "loadInvoice", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public JsonResponse loadInvoice(@RequestBody String invoiceCode, HttpServletRequest request) {
		logger.info("[method : loadInvoice][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				invoiceCode = invoiceCode.replace("\"", "");
				Invoice invoice = financialService.getInvoiceByInvoiceCodeScan(invoiceCode, "N", "S");
				if (invoice != null) {
					InvoiceDocumentBean invoiceDocumentBean = new InvoiceDocumentBean();
					InvoiceController invoiceController = new InvoiceController();
					invoiceController.setMessageSource(messageSource);
					invoiceDocumentBean = invoiceController.PoppulateInvoiceEntityToDto(invoice);
					jsonResponse.setResult(invoiceDocumentBean);
					jsonResponse.setError(false);
				} else {
					jsonResponse.setError(true);
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

	// load loadInvoice By id
	@RequestMapping(value = "loadInvoiceImport", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public JsonResponse loadInvoiceImport(@RequestBody String invoiceCode, HttpServletRequest request) {
		logger.info("[method : loadInvoiceImport][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				invoiceCode = invoiceCode.replace("\"", "");
				Invoice invoice = financialService.getInvoiceByInvoiceCodeScan(invoiceCode, "E", "S");
				if (invoice != null) {
					InvoiceDocumentBean invoiceDocumentBean = new InvoiceDocumentBean();
					InvoiceController invoiceController = new InvoiceController();
					invoiceController.setMessageSource(messageSource);
					invoiceDocumentBean = invoiceController.PoppulateInvoiceEntityToDto(invoice);
					jsonResponse.setResult(invoiceDocumentBean);
					jsonResponse.setError(false);
				} else {
					jsonResponse.setError(true);
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

	// saveExport
	@RequestMapping(value = "saveExport", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse saveExport(@RequestBody final ValidateInvoiceBean validateInvoiceBean,
			HttpServletRequest request) {
		logger.info("[method : saveExport][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {

			try {
				if (validateInvoiceBean != null) {
					Date scanOutDate = new DateUtil().convertStringToDateTimeDb(validateInvoiceBean.getExportDate());
					Personnel personnel = personnelService
							.getPersonnelById(validateInvoiceBean.getIdPersonnelCashier());

					for (InvoiceDocumentBean invoiceDocumentBean : validateInvoiceBean.getInvoiceDocumentBeanList()) {
						Invoice invoice = financialService.getInvoiceById(invoiceDocumentBean.getId());
						if (invoice != null) {
							invoice.setScanOutDate(scanOutDate);
							invoice.setStatusScan("E");
							invoice.setPersonnel(personnel);
							financialService.updateInvoice(invoice);
						}
					}
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

		generateAlert(jsonResponse, request,
				messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("invoice.export.transaction.save.success", null,
						LocaleContextHolder.getLocale()));
		return jsonResponse;
	}

	// saveImport
	@RequestMapping(value = "saveImport", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse saveImport(@RequestBody final ValidateInvoiceBean validateInvoiceBean,
			HttpServletRequest request) {
		logger.info("[method : saveImport][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try {
				if (validateInvoiceBean != null) {
					Date scanInDate = new DateUtil().convertStringToDateTimeDb(validateInvoiceBean.getImportDate());

					for (InvoiceDocumentBean invoiceDocumentBean : validateInvoiceBean.getInvoiceDocumentBeanList()) {
						Invoice invoice = financialService.getInvoiceById(invoiceDocumentBean.getId());
						if (invoice != null) {
							invoice.setScanInDate(scanInDate);
							invoice.setStatusScan("N");

							if (validateInvoiceBean.isPayed()) {
								invoice.setStatus("S");
								invoice.getReceipt().setPaymentDate(CURRENT_TIMESTAMP);
								invoice.getReceipt().setPaymentType("P");
								invoice.setBilling(Boolean.FALSE);
								invoice.getReceipt().setStatus(messageSource.getMessage("financial.receipt.status.perm", 
										 null, LocaleContextHolder.getLocale()));
							} else {
								if (invoiceDocumentBean.isBilling()) {
									invoice.setBilling(Boolean.TRUE);
								} else {
									invoice.setPersonnel(null);
									invoice.setBilling(Boolean.FALSE);
								}
							}

							financialService.updateInvoice(invoice);
						}
					}
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

		generateAlert(jsonResponse, request,
				messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("invoice.import.transaction.save.success", null,
						LocaleContextHolder.getLocale()));
		return jsonResponse;
	}

	public List<PersonnelBean> loadPersonnelCashierList(List<Personnel> personnelList) {
		List<PersonnelBean> personnelBeanList = new ArrayList<PersonnelBean>();
		PersonnelController personnelController = new PersonnelController();
		personnelController.setMessageSource(messageSource);
		for (Personnel personnel : personnelList) {
			PersonnelBean personnelBean = personnelController.populateEntityToDto(personnel);
			personnelBeanList.add(personnelBean);
		}
		return personnelBeanList;
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

	public void setFinancialService(FinancialService financialService) {
		this.financialService = financialService;
	}
}