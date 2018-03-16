package com.hdw.mccable.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.hdw.mccable.dto.AlertBean;
import com.hdw.mccable.dto.CompanyBean;
import com.hdw.mccable.dto.CustomerBean;
import com.hdw.mccable.dto.EquipmentProductBean;
import com.hdw.mccable.dto.EquipmentProductCategoryBean;
import com.hdw.mccable.dto.EquipmentProductItemBean;
import com.hdw.mccable.dto.HistoryUpdateStatusBean;
import com.hdw.mccable.dto.HistoryUseEquipmentBean;
import com.hdw.mccable.dto.JsonRequestUpdateStatus;
import com.hdw.mccable.dto.JsonResponse;
import com.hdw.mccable.dto.PersonnelBean;
import com.hdw.mccable.dto.RequisitionDocumentBean;
import com.hdw.mccable.dto.RequisitionItemBean;
import com.hdw.mccable.dto.ServiceApplicationBean;
import com.hdw.mccable.dto.StockBean;
import com.hdw.mccable.dto.UnitBean;
import com.hdw.mccable.entity.EquipmentProduct;
import com.hdw.mccable.entity.EquipmentProductCategory;
import com.hdw.mccable.entity.EquipmentProductItem;
import com.hdw.mccable.entity.HistoryUpdateStatus;
import com.hdw.mccable.entity.HistoryUseEquipment;
import com.hdw.mccable.entity.RequisitionItem;
import com.hdw.mccable.entity.ServiceApplication;
import com.hdw.mccable.entity.Stock;
import com.hdw.mccable.entity.Unit;
import com.hdw.mccable.service.EquipmentProductCategoryService;
import com.hdw.mccable.service.EquipmentProductItemService;
import com.hdw.mccable.service.EquipmentProductService;
import com.hdw.mccable.service.HistoryUpdateStatusService;
import com.hdw.mccable.service.ProductService;
import com.hdw.mccable.service.RequisitionItemService;
import com.hdw.mccable.service.StockService;
import com.hdw.mccable.service.UnitService;
import com.hdw.mccable.utils.AlertUtil;
import com.hdw.mccable.utils.DateUtil;
import com.hdw.mccable.utils.ProductUtil;

@Controller
@RequestMapping("/productorderequipmentproduct")
public class ProductOrderEquipmentProductController extends BaseController {

	final static Logger logger = Logger.getLogger(ProductOrderEquipmentProductController.class);
	public static final String CONTROLLER_NAME = "productorderequipmentproduct/";
	public static final String DETAIL_PAGE = "detail";
	public static final String DETAIL_ITEM_PAGE = "detail_item";
	public static final Long stockId = 0L;
	public static final Long equipMentCategoryId = 0L;
	public static final String EDIT_PAGE = "edit_product";
	
	Gson g = new Gson();

	// initial service
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
	@Qualifier(value = "unitService")
	private UnitService unitService;

	@Autowired(required = true)
	@Qualifier(value = "historyUpdateStatusService")
	private HistoryUpdateStatusService historyUpdateStatusService;
	
	@Autowired(required = true)
	@Qualifier(value = "equipmentProductItemService")
	private EquipmentProductItemService equipmentProductItemService;
	
	@Autowired(required = true)
	@Qualifier(value = "equipmentProductService")
	private EquipmentProductService equipmentProductService;

	@Autowired(required = true)
	@Qualifier(value = "requisitionItemService")
	private RequisitionItemService requisitionItemService;
	
	@Autowired
	MessageSource messageSource;

	// end initial service

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView initProductOrder(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : initProductOrder][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		List<EquipmentProductBean> equipmentProductBeans = new ArrayList<EquipmentProductBean>();
		List<EquipmentProductCategory> equipmentProductCategory = new ArrayList<EquipmentProductCategory>();
		List<Stock> stocks = new ArrayList<Stock>();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			try {
				// call service
				stocks = stockService.findAll();
				equipmentProductCategory = equipmentProductCategoryService.findTypeEquipmentOnly();
				List<EquipmentProduct> equipmentProducts = productService.searchByTypeEquipment(null, 0L, 0L);
				if (null != equipmentProducts && !equipmentProducts.isEmpty()) {
					for (EquipmentProduct ep : equipmentProducts) {
						ProductAddController productAddController = new ProductAddController();
						productAddController.setMessageSource(messageSource);
						equipmentProductBeans.add(productAddController.populateEntityToDto(ep));
					}
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		logger.info("[method : equipmentProductBeans][SIZE : " + equipmentProductBeans.size() + "]");
		HttpSession session = request.getSession();
		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);
		modelAndView.addObject("equipmentProducts", equipmentProductBeans);
		modelAndView.addObject("equipmentProductCategory", equipmentProductCategory);
		modelAndView.addObject("stocks", stocks);

		// remove session
		session.removeAttribute("alert");

		modelAndView.setViewName(CONTROLLER_NAME + INIT);
		return modelAndView;
	}

	@RequestMapping(value = "/searchequipmentproduct", method = RequestMethod.POST)
	public ModelAndView searchProductOrder(EquipmentProductBean equipmentProductBean, HttpServletRequest request
			, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : searchProductOrder][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		List<EquipmentProductBean> equipmentProductBeans = new ArrayList<EquipmentProductBean>();
		List<EquipmentProductCategory> equipmentProductCategory = new ArrayList<EquipmentProductCategory>();
		List<Stock> stocks = new ArrayList<Stock>();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			try {
				// call service
				Long equimentProductId = 0L;
				if (equipmentProductBean.getProductCategory() != null
						&& equipmentProductBean.getProductCategory().getId() != null
						&& ((Long.valueOf(equipmentProductBean.getProductCategory().getId()) > 0))) {

					equimentProductId = Long.valueOf(equipmentProductBean.getProductCategory().getId());
				}

				stocks = stockService.findAll();
				equipmentProductCategory = equipmentProductCategoryService.findTypeEquipmentOnly();
				List<EquipmentProduct> equipmentProducts = productService.searchByTypeEquipment(
						equipmentProductBean.getCriteria(), equimentProductId, equipmentProductBean.getStock().getId());

				if (null != equipmentProducts && !equipmentProducts.isEmpty()) {

					for (EquipmentProduct epb : equipmentProducts) {

						ProductAddController productAddController = new ProductAddController();
						productAddController.setMessageSource(messageSource);
						EquipmentProductBean ep = productAddController.populateEntityToDto(epb);
						logger.info("[method : equipmentProductBeans][Rest : " + ep.getRest() + "]");

						if ("ProductOutStock".equals(equipmentProductBean.getViewType()) && ep.getRest() == 0) {
							equipmentProductBeans.add(ep);

						} else if ("ProductStock".equals(equipmentProductBean.getViewType()) && ep.getRest() > 0) {
							equipmentProductBeans.add(ep);

						} else if ("All".equals(equipmentProductBean.getViewType())) {
							equipmentProductBeans.add(ep);
						}
					}
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		logger.info("[method : equipmentProductBeans][SIZE : " + equipmentProductBeans.size() + "]");
		logger.info("[method : equipmentProductBeans][viewType : " + equipmentProductBean.getViewType() + "]");
		HttpSession session = request.getSession();
		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);
		modelAndView.addObject("equipmentProducts", equipmentProductBeans);
		modelAndView.addObject("equipmentProductCategory", equipmentProductCategory);
		modelAndView.addObject("stocks", stocks);
		modelAndView.addObject("criteria", equipmentProductBean.getCriteria());
		modelAndView.addObject("stockId", equipmentProductBean.getStock().getId());
		modelAndView.addObject("productCategoryId", equipmentProductBean.getProductCategory().getId());
		modelAndView.addObject("viewType", equipmentProductBean.getViewType());

		// remove session
		session.removeAttribute("alert");

		modelAndView.setViewName(CONTROLLER_NAME + INIT);
		return modelAndView;
	}

	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public ModelAndView productDetail(@PathVariable Long id, Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : productDetail][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = null;
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			EquipmentProduct equipmentProduct = productService.findEquipmentProductById(id);
			if (equipmentProduct != null) {
				ProductAddController productAddController = new ProductAddController();
				productAddController.setMessageSource(messageSource);
				EquipmentProductBean equipmentProductBean = productAddController.populateEntityToDto(equipmentProduct);
				modelAndView.addObject("equipmentProductBean", equipmentProductBean);
			} else {
				// send redirect page 404 not found here
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

		modelAndView.setViewName(CONTROLLER_NAME + DETAIL_PAGE);
		return modelAndView;
	}

	@RequestMapping(value = "item/detail/{id}", method = RequestMethod.GET)
	public ModelAndView productItemDetail(@PathVariable Long id, Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : productItemDetail][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = null;
		ProductAddController productAddController = new ProductAddController();
		productAddController.setMessageSource(messageSource);

		ProductUtil productUtil = new ProductUtil();
		productUtil.setMessageSource(messageSource);
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			// load product item
			EquipmentProductItem equipmentProductItem = productService.findEquipmentProductItemById(id);
			if (equipmentProductItem != null) {
				EquipmentProductItemBean equipmentProductItemBean = new EquipmentProductItemBean();
				equipmentProductItemBean.setId(equipmentProductItem.getId());
				//equipmentProductItemBean.setCost(equipmentProductItem.getCost());
				equipmentProductItemBean.setNumberImport(equipmentProductItem.getNumberImport());
				equipmentProductItemBean.setReferenceNo(equipmentProductItem.getReference());
				//equipmentProductItemBean.setSalePrice(equipmentProductItem.getSalePrice());
				equipmentProductItemBean.setSerialNo(equipmentProductItem.getSerialNo());
				equipmentProductItemBean.setStatusReal(equipmentProductItem.getStatus());
				equipmentProductItemBean.setSalePrice(equipmentProductItem.getSalePrice());
				equipmentProductItemBean.setCost(equipmentProductItem.getCost());
				equipmentProductItemBean.setPriceIncTax(equipmentProductItem.getPriceIncTax());
				// convert mesage status
				equipmentProductItemBean
						.setStatus(productUtil.getMessageStatusProduct(equipmentProductItem.getStatus()));

				// date
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

				// personnel
				PersonnelBean PersonnelBean = new PersonnelBean();
				if (equipmentProductItem.getPersonnel() != null) {
					PersonnelBean = new PersonnelController().populateEntityToDto(equipmentProductItem.getPersonnel());
				} else {
					PersonnelBean.setFirstName("-");
				}
				equipmentProductItemBean.setPersonnelBean(PersonnelBean);

				// history status
				List<HistoryUpdateStatusBean> historyUpdateStatusBeans = new ArrayList<HistoryUpdateStatusBean>();
				for (HistoryUpdateStatus historyUpdateStatus : equipmentProductItem.getHistoryUpdateStatuses()) {
					HistoryUpdateStatusBean historyUpdateStatusBean = new HistoryUpdateStatusBean();
					historyUpdateStatusBean.setId(historyUpdateStatus.getId());
					historyUpdateStatusBean
							.setStatus(productUtil.getMessageStatusProduct(historyUpdateStatus.getStatus()));
					historyUpdateStatusBean.setRemark(historyUpdateStatus.getRemark());

					historyUpdateStatusBean.setRecordDate(null == historyUpdateStatus.getRecordDate() ? ""
							: formatDateAndTimeTh.format(historyUpdateStatus.getRecordDate()));

					historyUpdateStatusBean.setDateRepair(null == historyUpdateStatus.getDateRepair() ? ""
							: formatDataTh.format(historyUpdateStatus.getDateRepair()));

					PersonnelBean personnelBean = new PersonnelBean();
					if (historyUpdateStatus.getPersonnel() != null) {
						personnelBean.setId(historyUpdateStatus.getPersonnel().getId());
						personnelBean.setFirstName(historyUpdateStatus.getPersonnel().getFirstName());
						personnelBean.setLastName(historyUpdateStatus.getPersonnel().getLastName());
					}
					historyUpdateStatusBean.setPersonnelBean(personnelBean);
					historyUpdateStatusBean.setInformer(historyUpdateStatus.getInformer());
					historyUpdateStatusBeans.add(historyUpdateStatusBean);
				}
				equipmentProductItemBean.setHistoryUpdateStatusBeans(historyUpdateStatusBeans);

				// history of use
				List<HistoryUseEquipmentBean> historyUseEquipmentBeans = new ArrayList<HistoryUseEquipmentBean>();
				for (HistoryUseEquipment historyUseEquipment : equipmentProductItem.getHistoryUseEquipments()) {
					HistoryUseEquipmentBean historyUseEquipmentBean = new HistoryUseEquipmentBean();
					historyUseEquipmentBean.setId(historyUseEquipment.getId());
					// customer
					CustomerBean customerBean = new CustomerBean();
					customerBean.setId(historyUseEquipment.getCustomer().getId());
					customerBean.setFirstName(historyUseEquipment.getCustomer().getFirstName());
					customerBean.setLastName(historyUseEquipment.getCustomer().getLastName());
					historyUseEquipmentBean.setCustomerBean(customerBean);
					
					ServiceApplication serviceApp = historyUseEquipment.getServiceApplication();
					if(null != serviceApp){
					ServiceApplicationBean serviceAppBean = new ServiceApplicationBean();
					serviceAppBean.setId(serviceApp.getId());
					serviceAppBean.setServiceApplicationNo(serviceApp.getServiceApplicationNo());
					historyUseEquipmentBean.setServiceApplicationBean(serviceAppBean );
					}
					
					historyUseEquipmentBean.setStatus(historyUseEquipment.getStatus());
					// date
					historyUseEquipmentBean.setActiveDate(null == historyUseEquipment.getActiveDate() ? " - "
							: formatDataTh.format(historyUseEquipment.getActiveDate()));
					
					historyUseEquipmentBean.setReturnDate(null == historyUseEquipment.getReturnDate() ? " - "
							: formatDataTh.format(historyUseEquipment.getReturnDate()));
					
//					if(null == historyUseEquipment.getReturnDate() && true){
//						
//					}
					
					historyUseEquipmentBeans.add(historyUseEquipmentBean);
				}
				equipmentProductItemBean.setHistoryUseEquipmentBeans(historyUseEquipmentBeans);

				// add item to object
				modelAndView.addObject("equipmentProductItemBean", equipmentProductItemBean);

				// EquipmentProduct master
				EquipmentProductBean equipmentProductBean = productAddController
						.populateEntityToDto(equipmentProductItem.getEquipmentProduct());
				modelAndView.addObject("equipmentProductBean", equipmentProductBean);

			} else {
				// redirect error page here
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

		modelAndView.setViewName(CONTROLLER_NAME + DETAIL_ITEM_PAGE);
		return modelAndView;
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonResponse deleteEquipmentProduct(@PathVariable String id, HttpServletRequest request) {
		logger.info("[method : deleteEquipmentProduct][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				EquipmentProduct equipmentProduct = productService.findEquipmentProductById(Long.valueOf(id));
				if (equipmentProduct != null) {
					// delte master
					equipmentProduct.setDeleted(true);
					productService.update(equipmentProduct);
					// remove item
					productService.deleteProductItemGroup(equipmentProduct);
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

		generateAlert(jsonResponse, request,
				messageSource.getMessage("alert.title.delete.success", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("equipmentProduct.transaction.delete.success", null,
						LocaleContextHolder.getLocale()));
		return jsonResponse;
	}

	// loadEquipmentProduct
	@RequestMapping(value = "loadEquipmentProduct/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonResponse loadEquipmentProduct(@PathVariable String id, HttpServletRequest request) {
		logger.info("[method : loadEquipmentProduct][Type : Controller]");
		ProductAddController productAddController = new ProductAddController();
		JsonResponse jsonResponse = new JsonResponse();

		if (isPermission()) {
			try {
				EquipmentProduct equipmentProduct = productService.findEquipmentProductById(Long.valueOf(id));
				if (equipmentProduct != null) {
					productAddController.setMessageSource(messageSource);
					EquipmentProductBean equipmentProductBean = new EquipmentProductBean();
					equipmentProductBean = productAddController.populateEntityToDto(equipmentProduct);
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

	// update EquipmentProduct
	@RequestMapping(value = "updateEquipmentProduct", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse updateEquipmentProduct(@RequestBody final EquipmentProductBean equipmentProductBean,
			HttpServletRequest request) {
		logger.info("[method : updateEquipmentProduct][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			// create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try {
				if (equipmentProductBean != null) {
					if (equipmentProductBean.getId() == null){
						throw new Exception("");
					}
						
					EquipmentProduct equipmentProduct = productService.findEquipmentProductById(equipmentProductBean.getId());
					float vat = equipmentProduct.getStock().getCompany().getVat();
					
					// load stock
					Stock stock = stockService.getStockById(equipmentProductBean.getStock().getId());
					equipmentProduct.setStock(stock);
					// load unit
					Unit unit = unitService.getUnitById(equipmentProductBean.getUnit().getId());
					equipmentProduct.setUnit(unit);
					// load productCategoryType
					EquipmentProductCategory epc = equipmentProductCategoryService
							.getEquipmentProductCategoryById(equipmentProductBean.getProductCategory().getId());
					equipmentProduct.setEquipmentProductCategory(epc);

					equipmentProduct.setProductCode(equipmentProductBean.getProductCode());
					equipmentProduct.setProductName(equipmentProductBean.getProductName());
					equipmentProduct.setSupplier(equipmentProductBean.getSupplier());
					equipmentProduct.setFinancial_type(equipmentProductBean.getFinancialType());
					
					if (equipmentProductBean.isIsminimum()) {
						equipmentProduct.setMinimum(true);
						equipmentProduct.setMinimumNumber(equipmentProductBean.getMinimumNumber());
					} else {
						equipmentProduct.setMinimum(false);
						equipmentProduct.setMinimumNumber(ZERO_VALUE);
					}
					equipmentProduct.setDeleted(false);
					equipmentProduct.setCreateDate(CURRENT_TIMESTAMP);
					// ---> change when LoginController success
					equipmentProduct.setCreatedBy(getUserNameLogin());
					//equipmentProduct.setCost(equipmentProductBean.getCost());
					//equipmentProduct.setSalePrice(equipmentProductBean.getSalePrice());
					productService.update(equipmentProduct);

					// update item
					for (EquipmentProductItemBean equipmentProductItemBean : equipmentProductBean
							.getEquipmentProductItemBeans()) {
						EquipmentProductItem equipmentProductItem = productService
								.findEquipmentProductItemById(equipmentProductItemBean.getId());
						// set new value
						equipmentProductItem.setCost(equipmentProductItemBean.getCost());
						equipmentProductItem.setSalePrice(equipmentProductItemBean.getSalePrice());
						equipmentProductItem.setReference(equipmentProductItemBean.getReferenceNo());
						equipmentProductItem.setNumberImport(equipmentProductItemBean.getNumberImport());
						if(true){ // ช่วงเทส
						equipmentProductItem.setBalance(equipmentProductItemBean.getNumberImport());
						}
						equipmentProductItem.setSerialNo(equipmentProductItemBean.getSerialNo());
						equipmentProductItem.setPriceIncTax(equipmentProductItemBean.getPriceIncTax());
						// guarantee date
						if (equipmentProductItemBean.getGuaranteeDate() != null
								&& (!equipmentProductItemBean.getGuaranteeDate().isEmpty())) {
							equipmentProductItem.setGuaranteeDate(new DateUtil()
									.convertStringToDateTimeDb(equipmentProductItemBean.getGuaranteeDate()));
						} else {
							equipmentProductItem.setGuaranteeDate(null);
						}

						// order date
						if (equipmentProductItemBean.getOrderDate() != null
								&& (!equipmentProductItemBean.getOrderDate().isEmpty())) {
							equipmentProductItem.setOrderDate(
									new DateUtil().convertStringToDateTimeDb(equipmentProductItemBean.getOrderDate()));
						} else {
							equipmentProductItem.setOrderDate(null);
						}

						// update item
						productService.updateProductItem(equipmentProductItem);
						
						equipmentProduct = equipmentProductItem.getEquipmentProduct();
						
						// อัพ product ตัวแม่
						if(null != equipmentProduct){
							autoUpdateStatusEquipmentProduct(equipmentProduct);
						}
					}
					jsonResponse.setError(false);
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

		generateAlert(jsonResponse, request,
				messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("equipmentProduct.transaction.save.success", null,
						LocaleContextHolder.getLocale()));
		return jsonResponse;
	}

	// udpate status product item
	@RequestMapping(value = "updateStatusEquipmentProductItem", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse updateStatusEquipmentProductItem(
			@RequestBody final JsonRequestUpdateStatus jsonRequestUpdateStatus, HttpServletRequest request) {
		logger.info("[method : updateStatusEquipmentProductItem][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			// create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try {
				if (jsonRequestUpdateStatus != null) {
					EquipmentProductItem equipmentProductItem = productService
							.findEquipmentProductItemById(jsonRequestUpdateStatus.getEquipmentProductItemId());
					equipmentProductItem.setStatus(jsonRequestUpdateStatus.getStatus());
					if(STATUS_REPAIR == jsonRequestUpdateStatus.getStatus()){
						equipmentProductItem.setRepair(Boolean.TRUE);
					}
					// update item
					productService.updateProductItem(equipmentProductItem);
					
					EquipmentProduct equipmentProduct = equipmentProductItem.getEquipmentProduct();
					if(null != equipmentProduct){
						autoUpdateStatusEquipmentProduct(equipmentProduct);
					}

					// save history
					HistoryUpdateStatus historyUpdateStatus = new HistoryUpdateStatus();
					historyUpdateStatus.setStatus(jsonRequestUpdateStatus.getStatus());
					historyUpdateStatus.setCreateDate(CURRENT_TIMESTAMP);
					// -----> change when LoginController success
					historyUpdateStatus.setCreatedBy(getUserNameLogin());
					historyUpdateStatus.setRecordDate(CURRENT_TIMESTAMP);
					historyUpdateStatus.setRemark(jsonRequestUpdateStatus.getRemark());
					historyUpdateStatus.setDeleted(false);
					// repair date
					if (jsonRequestUpdateStatus.getDateRepair() != null
							&& (!jsonRequestUpdateStatus.getDateRepair().isEmpty())) {
						historyUpdateStatus.setDateRepair(
								new DateUtil().convertStringToDateTimeDb(jsonRequestUpdateStatus.getDateRepair()));
					} else {
						historyUpdateStatus.setDateRepair(null);
					}
					historyUpdateStatus.setEquipmentProductItem(equipmentProductItem);
					historyUpdateStatusService.save(historyUpdateStatus);
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

		generateAlert(jsonResponse, request,
				messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("equipmentProductItem.transaction.save.status.success", null,
						LocaleContextHolder.getLocale()));
		return jsonResponse;
	}

	public void autoUpdateStatusEquipmentProduct(EquipmentProduct equipmentProduct) throws Exception{
		int out_of_order = 0; // เสีย/ ชำรุด
		int balance = 0; // ที่ใช้ได้
		int repair = 0; // ซ่อม
		int sell = 0; // ขาย
		int lend = 0; // ยืม
		int spare = 0; // สำรอง
		int stockAmount = 0; // เหลือ
		int reservations = 0; // จอง
		List<EquipmentProductItem> equipmentProductItems = equipmentProduct.getEquipmentProductItems();
		if(null != equipmentProductItems && equipmentProductItems.size() > 0){
			for(EquipmentProductItem equ:equipmentProductItems){
				if(!StringUtils.isEmpty(equ.getSerialNo())){ // มี SN
					switch (equ.getStatus()) {
					case 0:
						out_of_order++;
						stockAmount++;
						break;
					case 1:
						balance++;
						stockAmount++;
						break;
					case 2:
						reservations++;
						stockAmount++;
						break;
					case 3:
						lend++;
						stockAmount++;
						break;
					case 4:
						spare++;
						stockAmount++;
						break;
					case 6:
						repair++;
						stockAmount++;
						break;
					}
				}else{
					balance += equ.getBalance();
					spare += equ.getSpare();
					reservations += equ.getReservations();
					stockAmount = balance + spare + reservations;
				}
			}
			equipmentProduct.setReservations(reservations);
			equipmentProduct.setSpare(spare);
			equipmentProduct.setLend(lend);
			equipmentProduct.setSell(sell);
			equipmentProduct.setOut_of_order(out_of_order);
			equipmentProduct.setBalance(balance);
			equipmentProduct.setStockAmount(stockAmount);
			equipmentProduct.setRepair(repair);
			equipmentProductService.update(equipmentProduct);
		}
	}
	
	@RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable Long id, Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : edit][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = null;
		EquipmentProductCategoryController epcController = new EquipmentProductCategoryController();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			EquipmentProduct equipmentProduct = productService.findEquipmentProductById(id);
			if (equipmentProduct != null) {
				ProductAddController productAddController = new ProductAddController();
				productAddController.setMessageSource(messageSource);
				EquipmentProductBean equipmentProductBean = productAddController.populateEntityToDto(equipmentProduct);

				// convert date type 3 ----------->
				equipmentProductBean.setEquipmentProductItemBeans(convertDateForCalendar(equipmentProductBean.getEquipmentProductItemBeans(), equipmentProduct.getEquipmentProductItems()));
				
				// End convert date type 3 ----------->
				modelAndView.addObject("equipmentProductBean", equipmentProductBean);

				// load master data
				// load equipment product
				List<EquipmentProductBean> epbSearchs = loadEquipmentProduct(new EquipmentProductBean());
				modelAndView.addObject("epbSearchs", epbSearchs);

				// dropdown equipmentCategory for search only
				List<EquipmentProductCategoryBean> epcSearchBeans = new ArrayList<EquipmentProductCategoryBean>();
				List<EquipmentProductCategory> epcSearchs = equipmentProductCategoryService.findTypeEquipmentOnly();
				for (EquipmentProductCategory epcSearch : epcSearchs) {
					EquipmentProductCategoryBean epcSearchBean = new EquipmentProductCategoryBean();
					epcSearchBean = epcController.populateEntityToDto(epcSearch);
					epcSearchBeans.add(epcSearchBean);
				}
				modelAndView.addObject("epcSearchBeans", epcSearchBeans);

				// dropdown stock
				List<StockBean> stockBeans = loadStockList();
				modelAndView.addObject("stockBeans", stockBeans);

				// dropdown unit
				List<Unit> units = unitService.findAll();
				List<UnitBean> unitBeans = new ArrayList<UnitBean>();
				for (Unit unit : units) {
					UnitBean unitBean = new UnitBean();
					unitBean.setId(unit.getId());
					unitBean.setUnitName(unit.getUnitName());
					unitBeans.add(unitBean);
				}
				modelAndView.addObject("unitBeans", unitBeans);

			} else {
				// send redirect page 404 not found here
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

		modelAndView.setViewName(CONTROLLER_NAME + EDIT_PAGE);
		return modelAndView;
	}
	
	@RequestMapping(value="updaterequisition", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public JsonResponse updaterequisition(@RequestBody final RequisitionDocumentBean requisitionDocumentBean, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			try{
				Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
				logger.info("[method : updaterequisition][Type : Controller]");
				if(null != requisitionDocumentBean){
					List<RequisitionItemBean> requisitionItemBeans = requisitionDocumentBean.getRequisitionItemList();
					if(null != requisitionItemBeans && requisitionItemBeans.size() > 0){
						EquipmentProduct eqmp = new EquipmentProduct();
						boolean flagSN = false;
						int returnEquipmentProductItem = 0;
						HashMap<Long, Integer> mapDataNoSN = new HashMap<Long, Integer>();
						for(RequisitionItemBean requisitionItemBean:requisitionItemBeans){
							if(null != requisitionItemBean.getId()){
								RequisitionItem requisitionItem = requisitionItemService.getRequisitionItemById(requisitionItemBean.getId());
								EquipmentProductItem equipmentProductItem = requisitionItem.getEquipmentProductItem();
								if(null != equipmentProductItem){
									eqmp = equipmentProductItem.getEquipmentProduct();
									String serialNo = equipmentProductItem.getSerialNo();
									
									// อัพเดทจำนวนที่คืนที่รายการใบเบิก
									requisitionItem.setReturnEquipmentProductItem(requisitionItemBean.getReturnEquipmentProductItem());
									requisitionItem.setUpdatedBy(getUserNameLogin());
									requisitionItem.setUpdatedDate(CURRENT_TIMESTAMP);
									requisitionItemService.update(requisitionItem);
									
									if(!"".equals(serialNo)){// มี S/N
										if(requisitionItemBean.getReturnEquipmentProductItem() > 0){
											equipmentProductItem.setSpare(0);
											equipmentProductItem.setBalance(1);
											equipmentProductItem.setStatus(STATUS_ACTIVE);										
										}else{
											equipmentProductItem.setSpare(1);
											equipmentProductItem.setBalance(0);
											equipmentProductItem.setStatus(STATUS_RESERVE);
										}
										equipmentProductItem.setUpdatedBy(getUserNameLogin());
										equipmentProductItem.setUpdatedDate(CURRENT_TIMESTAMP);
										equipmentProductItemService.update(equipmentProductItem);
									}else{ // ไม่มี S/N
										flagSN = true;
										if(mapDataNoSN.containsKey(equipmentProductItem.getId())){
											returnEquipmentProductItem += requisitionItem.getQuantity() - requisitionItemBean.getReturnEquipmentProductItem();
											mapDataNoSN.put(equipmentProductItem.getId(), returnEquipmentProductItem);
										}else{
											returnEquipmentProductItem = requisitionItem.getQuantity() - requisitionItemBean.getReturnEquipmentProductItem();
											mapDataNoSN.put(equipmentProductItem.getId(), returnEquipmentProductItem);
										}
									}
								}
							}
						}
						
						// อัพเดทข้อมูลเคส ไม่มี S/N
						if(flagSN){
							for(RequisitionItemBean requisitionItemBean:requisitionItemBeans){
								RequisitionItem requisitionItem = requisitionItemService.getRequisitionItemById(requisitionItemBean.getId());
								EquipmentProductItem equipmentProductItem = requisitionItem.getEquipmentProductItem();
								if(null != equipmentProductItem && mapDataNoSN.containsKey(equipmentProductItem.getId())){
									eqmp = equipmentProductItem.getEquipmentProduct();
									int returnEquipmentProductItemFinal = mapDataNoSN.get(equipmentProductItem.getId());
									equipmentProductItem.setSpare(returnEquipmentProductItemFinal);
									equipmentProductItem.setBalance(equipmentProductItem.getNumberImport() - (returnEquipmentProductItemFinal + equipmentProductItem.getReservations()));
									equipmentProductItem.setUpdatedBy(getUserNameLogin());
									equipmentProductItem.setUpdatedDate(CURRENT_TIMESTAMP);
									equipmentProductItemService.update(equipmentProductItem);
								}
							}
						}
						
						// อัพ product ตัวแม่
						if(null != eqmp){
							autoUpdateStatusEquipmentProduct(eqmp);
						}
					}
				}
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
	
	@RequestMapping(value="export", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public JsonResponse export(@RequestBody final EquipmentProductBean equipmentProductBean, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			//create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			EquipmentProduct equipmentProduct = new EquipmentProduct();
			try{
				logger.info("[method : export][Type : Controller]");
				logger.info("[ProductCode : "+equipmentProductBean.getProductCode()+"]");
				logger.info("[Stock : "+equipmentProductBean.getStock().getId()+"]");
				
				// item ที่เลือกในการย้าย
				List<EquipmentProductItemBean> equiBean = equipmentProductBean.getEquipmentProductItemBeans();
				if(null != equiBean && !equiBean.isEmpty()){
					if(equipmentProductBean.getStock().getId() > 0){
						List<EquipmentProduct> equis = productService.findByStockAndProductCode(equipmentProductBean.getStock().getId(), equipmentProductBean.getProductCode());
						if(null != equis && !equis.isEmpty()){
							equipmentProduct = equis.get(0); // product ตัวแม่
						}else{
							equipmentProduct = null;
						}
					}
					
					for(EquipmentProductItemBean equi:equiBean){
						logger.info("[equi : "+equi.getId()+"]");
						logger.info("[equipmentProduct : "+equipmentProduct+"]");
						
						if(equipmentProductBean.getStock().getId()==0){ // นำสินค้าออกโดยไม่ระบุคลังสินค้า
							EquipmentProductItem eq = equipmentProductItemService.getEquipmentProductItemById(equi.getId());
							eq.setDeleted(Boolean.TRUE);
							eq.setUpdatedBy(getUserNameLogin());
							eq.setUpdatedDate(CURRENT_TIMESTAMP);
							equipmentProductItemService.update(eq);
						}else{
							EquipmentProductItem eq = equipmentProductItemService.getEquipmentProductItemById(equi.getId());
							if(null != equipmentProduct){  // กรณีคลังที่ย้ายไปมีสืนค้าอยู่
								eq.setEquipmentProduct(equipmentProduct);
								eq.setUpdatedBy(getUserNameLogin());
								eq.setUpdatedDate(CURRENT_TIMESTAMP);
								equipmentProductItemService.update(eq);
							}else{ // กรณีคลังที่ย้ายไปไม่มีสืนค้าอยู่
								equipmentProduct = eq.getEquipmentProduct();
								equipmentProduct.setId(null);
								Stock stock = stockService.getStockById(equipmentProductBean.getStock().getId());
								equipmentProduct.setStock(stock);
								productService.saveMasterProduct(equipmentProduct);
								eq.setEquipmentProduct(equipmentProduct);
								eq.setUpdatedBy(getUserNameLogin());
								eq.setUpdatedDate(CURRENT_TIMESTAMP);
								equipmentProductItemService.update(eq);
							}
						}
					}
				}
				jsonResponse.setError(false);
			}catch(Exception ex){
				ex.printStackTrace();
				jsonResponse.setMessage(ex.getMessage());
				jsonResponse.setError(true);
				}
		}else{
			jsonResponse.setError(true);
		}
		
		generateAlert(jsonResponse, request,
				messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("equipmentProductItem.transaction.export.status.success", null,
						LocaleContextHolder.getLocale()));
		return jsonResponse;
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

	// load equipment process
	public List<EquipmentProductBean> loadEquipmentProduct(EquipmentProductBean equipmentProductBean) {
		// check equipment category id
		Long equimentProductId;
		if (equipmentProductBean.getProductCategory() != null
				&& equipmentProductBean.getProductCategory().getId() != null
				&& ((Long.valueOf(equipmentProductBean.getProductCategory().getId()) > 0))) {

			equimentProductId = Long.valueOf(equipmentProductBean.getProductCategory().getId());
		} else {
			equimentProductId = equipMentCategoryId;
		}
		// check stock id
		Long stockIdSearch;
		if (equipmentProductBean.getStock() != null && equipmentProductBean.getStock().getId() != null
				&& equipmentProductBean.getStock().getId() != null
				&& (Long.valueOf(equipmentProductBean.getStock().getId()) > 0)) {

			stockIdSearch = Long.valueOf(equipmentProductBean.getStock().getId());

		} else {
			stockIdSearch = stockId;
		}

		List<EquipmentProductBean> equipmentProductBeans = new ArrayList<EquipmentProductBean>();
		List<EquipmentProduct> equipmentProducts = productService
				.searchByTypeEquipment(equipmentProductBean.getProductName(), equimentProductId, stockIdSearch);
		// transfer entity to bean
		ProductAddController productAddController = new ProductAddController();
		productAddController.setMessageSource(messageSource);

		for (EquipmentProduct ep : equipmentProducts) {
			EquipmentProductBean epBean = new EquipmentProductBean();
			epBean = productAddController.populateEntityToDto(ep);
			equipmentProductBeans.add(epBean);
		}

		return equipmentProductBeans;
	}

	public List<EquipmentProductItemBean> convertDateForCalendar(List<EquipmentProductItemBean> equipmentProductItemBeans, List<EquipmentProductItem> equipmentProductItems) {
		SimpleDateFormat formatForDateSelect = new SimpleDateFormat(
				messageSource.getMessage("date.format.type3", null, LocaleContextHolder.getLocale()),Locale.US);

		for (int i = 0; i < equipmentProductItemBeans.size(); i++) {
			// guarantee date
			// -----------------------------------------------------//
			String strGuaranteeDate = null == equipmentProductItems.get(i)
					.getGuaranteeDate() ? ""
							: formatForDateSelect.format(
									equipmentProductItems.get(i).getGuaranteeDate());
			equipmentProductItemBeans.get(i).setGuaranteeDate(strGuaranteeDate);

			// order date
			// -----------------------------------------------------//
			String strOrderDate = null == equipmentProductItems.get(i).getOrderDate() ? ""
					: formatForDateSelect
							.format(equipmentProductItems.get(i).getOrderDate());
			equipmentProductItemBeans.get(i).setOrderDate(strOrderDate);
		}
		
		return equipmentProductItemBeans;
	}

	public void generateAlert(JsonResponse jsonResponse, HttpServletRequest request, String title, String detail) {
		new AlertUtil().generateAlert(jsonResponse, request, title, detail);
	}

	// getter setter
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public void setStockService(StockService stockService) {
		this.stockService = stockService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setHistoryUpdateStatusService(HistoryUpdateStatusService historyUpdateStatusService) {
		this.historyUpdateStatusService = historyUpdateStatusService;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public EquipmentProductService getEquipmentProductService() {
		return equipmentProductService;
	}

	public void setEquipmentProductService(EquipmentProductService equipmentProductService) {
		this.equipmentProductService = equipmentProductService;
	}

}
