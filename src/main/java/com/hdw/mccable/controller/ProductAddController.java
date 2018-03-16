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
import org.springframework.util.StringUtils;
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
import com.hdw.mccable.dto.JsonResponse;
import com.hdw.mccable.dto.PersonnelBean;
import com.hdw.mccable.dto.ProductBean;
import com.hdw.mccable.dto.RequisitionDocumentBean;
import com.hdw.mccable.dto.RequisitionItemBean;
import com.hdw.mccable.dto.ServiceProductBean;
import com.hdw.mccable.dto.StockBean;
import com.hdw.mccable.dto.TechnicianGroupBean;
import com.hdw.mccable.dto.UnitBean;
import com.hdw.mccable.entity.EquipmentProduct;
import com.hdw.mccable.entity.EquipmentProductCategory;
import com.hdw.mccable.entity.EquipmentProductItem;
import com.hdw.mccable.entity.InternetProduct;
import com.hdw.mccable.entity.Personnel;
import com.hdw.mccable.entity.RequisitionDocument;
import com.hdw.mccable.entity.RequisitionItem;
import com.hdw.mccable.entity.ServiceProduct;
import com.hdw.mccable.entity.Stock;
import com.hdw.mccable.entity.TechnicianGroup;
import com.hdw.mccable.entity.Unit;
import com.hdw.mccable.service.EquipmentProductCategoryService;
import com.hdw.mccable.service.EquipmentProductService;
import com.hdw.mccable.service.InternetProductService;
import com.hdw.mccable.service.ProductService;
import com.hdw.mccable.service.ServiceProductService;
import com.hdw.mccable.service.StockService;
import com.hdw.mccable.service.UnitService;
import com.hdw.mccable.utils.AlertUtil;
import com.hdw.mccable.utils.DateUtil;
import com.hdw.mccable.utils.ProductUtil;

@Controller
@RequestMapping("/productadd")
public class ProductAddController extends BaseController {

	final static Logger logger = Logger.getLogger(ProductAddController.class);
	public static final String CONTROLLER_NAME = "productadd/";
	public static final String PAGE_TYPE_EQUIPMENT = "E"; // equiment page
	public static final String PAGE_TYPE_INTERNET = "I"; // internet user page
	public static final String PAGE_TYPE_CHARGE = "C"; // charge page
	public static final String key = "";
	public static final Long equipMentCategoryId = 0L;
	public static final Long stockId = 0L;
	
	public static final String TYPE_EQUIMENT = "E";
	public static final String TYPE_INTERNET_USER = "I";
	public static final String TYPE_SERVICE = "S";
	
	// initial service
	@Autowired(required = true)
	@Qualifier(value = "equipmentProductCategoryService")
	private EquipmentProductCategoryService equipmentProductCategoryService;
	
	@Autowired(required = true)
	@Qualifier(value = "equipmentProductService")
	private EquipmentProductService equipmentProductService;

	@Autowired(required = true)
	@Qualifier(value = "productService")
	private ProductService productService;

	@Autowired(required = true)
	@Qualifier(value = "stockService")
	private StockService stockService;

	@Autowired(required = true)
	@Qualifier(value = "unitService")
	private UnitService unitService;

	@Autowired(required = true)
	@Qualifier(value = "internetProductService")
	private InternetProductService internetProductService;
	
	@Autowired(required = true)
	@Qualifier(value = "serviceProductService")
	private ServiceProductService serviceProductService;
	
	@Autowired
	MessageSource messageSource;

	// end initial service

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView initProductAdd(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : initProductAdd][Type : Controller]");
		EquipmentProductCategoryController epcController = new EquipmentProductCategoryController();
		ModelAndView modelAndView = new ModelAndView();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			try {
				// call service
				// equipmentProductType
				List<EquipmentProductCategoryBean> epcBeans = new ArrayList<EquipmentProductCategoryBean>();
				List<EquipmentProductCategory> epcs = equipmentProductCategoryService.findAll();
				for (EquipmentProductCategory epc : epcs) {
					EquipmentProductCategoryBean epcBean = new EquipmentProductCategoryBean();
					epcBean = epcController.populateEntityToDto(epc);
					epcBeans.add(epcBean);
				}
				modelAndView.addObject("epcBeans", epcBeans);

				// load equipment product for modal
//				List<EquipmentProductBean> epbSearchs = loadEquipmentProduct(new EquipmentProductBean());
//				modelAndView.addObject("epbSearchs", epbSearchs);

				// dropdown equipmentCategory for search only
				List<EquipmentProductCategoryBean> epcSearchBeans = new ArrayList<EquipmentProductCategoryBean>();
				List<EquipmentProductCategory> epcSearchs = equipmentProductCategoryService.findTypeEquipmentOnly();
				for (EquipmentProductCategory epcSearch : epcSearchs) {
					EquipmentProductCategoryBean epcSearchBean = new EquipmentProductCategoryBean();
					epcSearchBean = epcController.populateEntityToDto(epcSearch);
					epcSearchBeans.add(epcSearchBean);
				}
				modelAndView.addObject("epcSearchBeans", epcSearchBeans);
				
				// Autocomplete Supplier
				List<String> supplierList = equipmentProductService.getSupplier();
				modelAndView.addObject("supplierList", supplierList);

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

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}

		HttpSession session = request.getSession();
		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);
		// push page type
		modelAndView.addObject("pageType", PAGE_TYPE_EQUIPMENT);

		// remove session
		session.removeAttribute("alert");

		modelAndView.setViewName(CONTROLLER_NAME + INIT);
		return modelAndView;
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
				List<EquipmentProductBean> equipmentProductBeans = loadEquipmentProduct(equipmentProductBean);
				jsonResponse.setError(false);
				jsonResponse.setResult(equipmentProductBeans);

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
		for (EquipmentProduct ep : equipmentProducts) {
			EquipmentProductBean epBean = new EquipmentProductBean();
			epBean = populateEntityToDto(ep);
//			if(epBean.getUsable() > 0){
				equipmentProductBeans.add(epBean);
//			}
			
		}

		return equipmentProductBeans;
	}

	// loadEquipmentProduct
		@RequestMapping(value = "loadEquipmentProductNotSn", method = RequestMethod.POST, produces = "application/json")
		@ResponseBody
		public JsonResponse loadEquipmentProductNotSn(@RequestBody final EquipmentProductBean equipmentProductBean,
				HttpServletRequest request) {
			logger.info("[method : loadEquipmentProductNotSn][Type : Controller]");
			JsonResponse jsonResponse = new JsonResponse();
			if (isPermission()) {
				try {
					logger.info("param EquipmentProductBean : " + equipmentProductBean.toString());
					List<EquipmentProductBean> equipmentProductBeans = loadEquipmentProductNotSN(equipmentProductBean);
					jsonResponse.setError(false);
					jsonResponse.setResult(equipmentProductBeans);

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
		
	// load equipment process ไม่เอา อุปกรณ์ ที่ไม่มี SN
	public List<EquipmentProductBean> loadEquipmentProductNotSN(EquipmentProductBean equipmentProductBean) {
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
		for (EquipmentProduct ep : equipmentProducts) {
			EquipmentProductBean epBean = new EquipmentProductBean();
			epBean = populateEntityToDto(ep);
			boolean isAdd = true;
			List<EquipmentProductItemBean> epItemBean = epBean.getEquipmentProductItemBeans();
			if(null != epItemBean && epItemBean.size() > 0){
				if(StringUtils.isEmpty(epItemBean.get(0).getSerialNo()))isAdd = false;
			}
			if(isAdd){
				if(epBean.getUsable() > 0){
					equipmentProductBeans.add(epBean);
				}
				
			}
		}

		return equipmentProductBeans;
	}
	
	// loadEquipmentProduct
	@RequestMapping(value = "loadEquipmentProductAllNotSn", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public JsonResponse loadEquipmentProductAllNotSn(@RequestBody final EquipmentProductBean equipmentProductBean,
			HttpServletRequest request) {
		logger.info("[method : loadEquipmentProductNotSn][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				logger.info("param EquipmentProductBean : " + equipmentProductBean.toString());
				List<EquipmentProductBean> equipmentProductBeans = loadEquipmentProductAllNotSN(equipmentProductBean);
				jsonResponse.setError(false);
				jsonResponse.setResult(equipmentProductBeans);

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

	// load equipment process ไม่เอา อุปกรณ์ ที่ไม่มี SN โหลดทุกตัว
		public List<EquipmentProductBean> loadEquipmentProductAllNotSN(EquipmentProductBean equipmentProductBean) {
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
			for (EquipmentProduct ep : equipmentProducts) {
				EquipmentProductBean epBean = new EquipmentProductBean();
				epBean = populateEntityToDto(ep);
				boolean isAdd = true;
				List<EquipmentProductItemBean> epItemBean = epBean.getEquipmentProductItemBeans();
				if(null != epItemBean && epItemBean.size() > 0){
					if(StringUtils.isEmpty(epItemBean.get(0).getSerialNo())){
						isAdd = false;
					}
				}
				if(isAdd){
					equipmentProductBeans.add(epBean);
					
				}
			}

			return equipmentProductBeans;
		}
	
	// load equipment and service process
	public List<ProductBean> loadEquipmentProductAndServiceProduct(EquipmentProductBean equipmentProductBean) {
		// check equipment category id
		Long equimentProductId;
		List<ProductBean> productBeans = new ArrayList<ProductBean>();
		
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

		List<EquipmentProduct> equipmentProducts = productService
				.searchByTypeEquipment(equipmentProductBean.getProductName(), equimentProductId, stockIdSearch);
		
		List<ServiceProduct> serviceProducts = productService
				.searchByTypeService(equipmentProductBean.getProductName(), equimentProductId, stockIdSearch);
		
		List<InternetProduct> internetProducts = productService
				.searchByTypeInternet(equipmentProductBean.getProductName(), equimentProductId, stockIdSearch);
		
		// transfer entity to bean
		for (EquipmentProduct ep : equipmentProducts) {
			EquipmentProductBean epBean = new EquipmentProductBean();
			epBean = populateEntityToDto(ep);
			epBean.setType(TYPE_EQUIMENT);
//			equipmentProductBeans.add(epBean);
			
			productBeans.add((ProductBean)epBean);
		}
		
		for (ServiceProduct sp : serviceProducts) {
			ServiceProductBean spBean = new ServiceProductBean();
			spBean = populateEntityToDto(sp);
			spBean.setType(TYPE_SERVICE);
//			equipmentProductBeans.add(epBean);
			
			productBeans.add((ProductBean)spBean);
		}
		
		for (InternetProduct inter : internetProducts) {
			InternetProductBean interBean = new InternetProductBean();
			interBean = populateEntityToDto(inter);
			interBean.setType(TYPE_INTERNET_USER);
//			equipmentProductBeans.add(epBean);
			
			productBeans.add((ProductBean)interBean);
		}
		
		return productBeans;
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

	// save EquipmentProductOld
	@RequestMapping(value = "saveEquipmentProductOld", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse saveEquipmentProductOld(@RequestBody final EquipmentProductBean equipmentProductBean,
			HttpServletRequest request) {
		logger.info("[method : saveEquipmentProductOld][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			// create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try {
				if (equipmentProductBean.getId() != null) {
					EquipmentProduct equipmentProduct = productService
							.findEquipmentProductById(equipmentProductBean.getId());
					
					if (equipmentProduct != null) {
						int balance = 0;
						int stockAmount = 0;
						float vat = equipmentProduct.getStock().getCompany().getVat();
						
						for (EquipmentProductItemBean equipmentProductItemBean : equipmentProductBean
								.getEquipmentProductItemBeans()) {
							// validate here
							// set entity for save
							EquipmentProductItem equipmentProductItem = new EquipmentProductItem();
							equipmentProductItem.setCost(equipmentProductItemBean.getCost());
							//priceIncTax
							equipmentProductItem.setPriceIncTax(equipmentProductItemBean.getPriceIncTax());
							
							equipmentProductItem.setCreateDate(CURRENT_TIMESTAMP);
							// ---> change when LoginController success
							equipmentProductItem.setCreatedBy(getUserNameLogin());
							equipmentProductItem.setDeleted(false);
							equipmentProductItem.setNumberImport(equipmentProductItemBean.getNumberImport());
							equipmentProductItem.setBalance(equipmentProductItemBean.getNumberImport());
							equipmentProductItem.setImportSystemDate(CURRENT_TIMESTAMP);
							equipmentProductItem.setReference(equipmentProductItemBean.getReferenceNo());
							equipmentProductItem.setSalePrice(equipmentProductItemBean.getSalePrice());
							equipmentProductItem.setSerialNo(equipmentProductItemBean.getSerialNo());
							equipmentProductItem.setStatus(STATUS_ACTIVE);
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
								equipmentProductItem.setOrderDate(new DateUtil()
										.convertStringToDateTimeDb(equipmentProductItemBean.getOrderDate()));
							} else {
								equipmentProductItem.setOrderDate(null);
							}

							// map equipmentProduct and item
							equipmentProductItem.setEquipmentProduct(equipmentProduct);
							
							// save equipment product item
							productService.save(equipmentProductItem);
							jsonResponse.setError(false);
						}
						
						List<EquipmentProductItem> equipmentProductItems = equipmentProduct.getEquipmentProductItems();
						if(null != equipmentProductItems && equipmentProductItems.size() > 0){
							for(EquipmentProductItem equipmentProductItem:equipmentProductItems){
								balance += equipmentProductItem.getBalance();
								stockAmount += equipmentProductItem.getNumberImport();
							}
						}
						equipmentProduct.setBalance(balance);
						equipmentProduct.setStockAmount(stockAmount);
						productService.update(equipmentProduct);
						
					} else {
						// input text for message exception
						throw new Exception("");
					}
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
				messageSource.getMessage("addproduct.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}

	// save EquipmentProductNew
	@RequestMapping(value = "saveEquipmentProductNew", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse saveEquipmentProductNew(@RequestBody final EquipmentProductBean equipmentProductBean,
			HttpServletRequest request) {
		logger.info("[method : saveEquipmentProductNew][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			// create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try {
				if (equipmentProductBean != null) {
					EquipmentProduct equipmentProduct = new EquipmentProduct();
					// load stock
					Stock stock = stockService.getStockById(equipmentProductBean.getStock().getId());
					equipmentProduct.setStock(stock);
					// load unit
					Unit unit = unitService.getUnitById(equipmentProductBean.getUnit().getId());
					equipmentProduct.setUnit(unit);
					
					//financial type
					equipmentProduct.setFinancial_type(equipmentProductBean.getFinancialType());
					
					// load productCategoryType
					EquipmentProductCategory epc = equipmentProductCategoryService
							.getEquipmentProductCategoryById(equipmentProductBean.getProductCategory().getId());
					equipmentProduct.setEquipmentProductCategory(epc);
					if(StringUtils.isEmpty(equipmentProductBean.getProductCode())){
						equipmentProduct.setProductCode(productService.genProductCode());
					}else{
						equipmentProduct.setProductCode(equipmentProductBean.getProductCode());
					}
					equipmentProduct.setProductName(equipmentProductBean.getProductName());
					equipmentProduct.setSupplier(equipmentProductBean.getSupplier());
					if(equipmentProductBean.isIsminimum()){
						equipmentProduct.setMinimum(true);
						equipmentProduct.setMinimumNumber(equipmentProductBean.getMinimumNumber());
					}else{
						equipmentProduct.setMinimum(false);
						equipmentProduct.setMinimumNumber(ZERO_VALUE);
					}
					equipmentProduct.setDeleted(false);
					equipmentProduct.setCreateDate(CURRENT_TIMESTAMP);
					// ---> change when LoginController success
					equipmentProduct.setCreatedBy(getUserNameLogin());
					//equipmentProduct.setCost(equipmentProductBean.getCost());
					//equipmentProduct.setSalePrice(equipmentProductBean.getSalePrice());
					int balance = 0;
					for (EquipmentProductItemBean equipmentProductItemBean : equipmentProductBean
							.getEquipmentProductItemBeans()) {
						balance += equipmentProductItemBean.getNumberImport();
					}
					equipmentProduct.setBalance(balance);
					equipmentProduct.setStockAmount(balance);
					
					Long equipmentProductId = productService.saveMasterProduct(equipmentProduct);
					if (equipmentProductId != null) {
						// load master product
						EquipmentProduct equipmentProductMaster = productService
								.findEquipmentProductById(equipmentProductId);
						
						float vat = equipmentProductMaster.getStock().getCompany().getVat();
						
						// save item
						for (EquipmentProductItemBean equipmentProductItemBean : equipmentProductBean
								.getEquipmentProductItemBeans()) {
							// validate here
							// set entity for save
							EquipmentProductItem equipmentProductItem = new EquipmentProductItem();
							equipmentProductItem.setCost(equipmentProductItemBean.getCost());
							//priceIncTax
							equipmentProductItem.setPriceIncTax(equipmentProductItemBean.getPriceIncTax());
							
							equipmentProductItem.setCreateDate(CURRENT_TIMESTAMP);
							// ---> change when LoginController success
							equipmentProductItem.setCreatedBy(getUserNameLogin());
							equipmentProductItem.setDeleted(false);
							equipmentProductItem.setNumberImport(equipmentProductItemBean.getNumberImport());
							equipmentProductItem.setBalance(equipmentProductItemBean.getNumberImport());
							equipmentProductItem.setImportSystemDate(CURRENT_TIMESTAMP);
							equipmentProductItem.setReference(equipmentProductItemBean.getReferenceNo());
							equipmentProductItem.setSalePrice(equipmentProductItemBean.getSalePrice());
							equipmentProductItem.setSerialNo(equipmentProductItemBean.getSerialNo());
							equipmentProductItem.setStatus(STATUS_ACTIVE);
							
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
								equipmentProductItem.setOrderDate(new DateUtil()
										.convertStringToDateTimeDb(equipmentProductItemBean.getOrderDate()));
							} else {
								equipmentProductItem.setOrderDate(null);
							}

							// map equipmentProduct and item
							equipmentProductItem.setEquipmentProduct(equipmentProductMaster);

							// save equipment product item
							productService.save(equipmentProductItem);
							jsonResponse.setError(false);
						}

					} else {
						// input text for message exception
						throw new Exception("");
					}
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
				messageSource.getMessage("addproduct.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}

	// save saveInternetUser
	@RequestMapping(value = "saveInternetUser", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse saveInternetUser(@RequestBody final InternetProductBean internetProductBean,
			HttpServletRequest request) {
		logger.info("[method : saveInternetUser][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				if (internetProductBean != null) {


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
				messageSource.getMessage("addproduct.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}

	// save saveServiceCharge
	@RequestMapping(value = "saveServiceCharge", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse saveServiceCharge(@RequestBody final ServiceProductBean serviceProductBean,
			HttpServletRequest request) {
		logger.info("[method : saveServiceCharge][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			// create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try {
				 if(serviceProductBean != null){
					 ServiceProduct serviceProduct = new ServiceProduct();
					// load stock
					Stock stock = stockService.getStockById(serviceProductBean.getStock().getId());
					serviceProduct.setStock(stock);
					
					// load unit
					Unit unit = unitService.getUnitById(serviceProductBean.getUnit().getId());
					serviceProduct.setUnit(unit);
					
					// load inter user type
					EquipmentProductCategory epc = equipmentProductCategoryService.getEquipmentProductCategoryByCode(
							messageSource.getMessage("equipmentproductcategory.type.charge", null,
									LocaleContextHolder.getLocale()));
					
					
					serviceProduct.setProductCategory(epc);
					serviceProduct.setServiceChargeName(serviceProductBean.getProductName());
					serviceProduct.setPrice(serviceProductBean.getPrice());
					serviceProduct.setDeleted(false);
					serviceProduct.setCreateDate(CURRENT_TIMESTAMP);
					// ---> change when LoginController success
					serviceProduct.setCreatedBy(getUserNameLogin());
					Long id = serviceProductService.save(serviceProduct);
					if(id != null){
						ServiceProduct ServiceProductNew = serviceProductService.getServiceProductById(id);
						if(ServiceProductNew != null){
							ServiceProductNew.setProductCode(String.format("%05d", ServiceProductNew.getId()));
							
							serviceProductService.update(ServiceProductNew);
						}
					}else{
						// input text for message exception
						throw new Exception(""); 
					}
					jsonResponse.setError(false);
					
				 }else{
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
				messageSource.getMessage("addproduct.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}

	
	// loadVatWithStockId
	@RequestMapping(value = "loadVatWithStockId/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonResponse loadVatWithStockId(@PathVariable String id, HttpServletRequest request) {
		logger.info("[method : loadVatWithStockId][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				Stock stock = stockService.getStockById(Long.valueOf(id));
				jsonResponse.setResult(stock.getCompany().getVat());
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
			companyBean.setVat(stock.getCompany().getVat());
			stockBean.setCompany(companyBean);
			stockBeans.add(stockBean);
		}

		return stockBeans;
	}

	// alert generat
	public void generateAlert(JsonResponse jsonResponse, HttpServletRequest request, String title, String detail) {
		new AlertUtil().generateAlert(jsonResponse, request, title, detail);
	}

	// populate bean
	public EquipmentProductBean populateEntityToDto(EquipmentProduct ep) {
		int rest = 0; // เหลือ
		int usable = 0; // ใช้ได้
		int spare = 0; // สำรอง
		int lend = 0; // ยืม
		int out_of_order = 0; // เสีย/ ชำรุด
		int repair = 0; // ซ่อม
		int reservations = 0; // จอง
		EquipmentProductBean equipmentProductBean = new EquipmentProductBean();

		// set equimemtProductBean
		equipmentProductBean.setId(ep.getId());
		equipmentProductBean.setProductCode(ep.getProductCode());
		equipmentProductBean.setProductName(ep.getProductName());
		equipmentProductBean.setSupplier(ep.getSupplier());
		equipmentProductBean.setIsminimum(ep.isMinimum());
		equipmentProductBean.setMinimumNumber(ep.getMinimumNumber());
		equipmentProductBean.setFinancialType(ep.getFinancial_type());
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
			if(!equipmentProductItem.isDeleted()){
			EquipmentProductItemBean equipmentProductItemBean = new EquipmentProductItemBean();
			equipmentProductItemBean.setId(equipmentProductItem.getId());
			equipmentProductItemBean.setCost(equipmentProductItem.getCost());
			equipmentProductItemBean.setReferenceNo(equipmentProductItem.getReference());
			equipmentProductItemBean.setSerialNo(equipmentProductItem.getSerialNo());
			equipmentProductItemBean.setNumberImport(equipmentProductItem.getNumberImport());
			equipmentProductItemBean.setSalePrice(equipmentProductItem.getSalePrice());
			equipmentProductItemBean.setSpare(equipmentProductItem.getSpare());
			equipmentProductItemBean.setPriceIncTax(equipmentProductItem.getPriceIncTax());
			
			SimpleDateFormat formatDataTh = new SimpleDateFormat(
					messageSource.getMessage("date.format.type2", null, LocaleContextHolder.getLocale()),
					new Locale("TH", "th"));
			SimpleDateFormat formatDataTimeTh = new SimpleDateFormat(
					messageSource.getMessage("date.format.type1", null, LocaleContextHolder.getLocale()),
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
			
			EquipmentProduct equipmentProduct = equipmentProductItem.getEquipmentProduct();
			
			rest = equipmentProduct.getStockAmount();
			lend = equipmentProduct.getLend();
			spare = equipmentProduct.getSpare();
			usable = equipmentProduct.getBalance();
			if(usable < 0){
				usable = 0;
			}
			repair = equipmentProduct.getRepair();
			out_of_order = equipmentProduct.getOut_of_order();
			reservations = equipmentProduct.getReservations();
			
			List<RequisitionItemBean> requisitionItemBeans = new ArrayList<RequisitionItemBean>(); 
			List<RequisitionItem> requisitionItems = equipmentProductItem.getRequisitionItems();
			if(null != requisitionItems && requisitionItems.size() > 0){
				for(RequisitionItem requisitionItem:requisitionItems){
					RequisitionItemBean requisitionItemBean = new RequisitionItemBean();
					RequisitionDocument requisitionDocument = requisitionItem.getRequisitionDocument();
					RequisitionDocumentBean requisitionDocumentBean = new RequisitionDocumentBean();
					if(null != requisitionDocument){
						requisitionDocumentBean.setId(requisitionDocument.getId());
						requisitionDocumentBean.setRequisitionDocumentCode(requisitionDocument.getRequisitionDocumentCode());
						
						String withdraw_result = "";
						
						if("R".equals(requisitionDocument.getWithdraw())){
							withdraw_result = messageSource.getMessage("withdraw.reserve", null,
									LocaleContextHolder.getLocale());
						}else if("O".equals(requisitionDocument.getWithdraw())){
							withdraw_result = messageSource.getMessage("withdraw.office", null,
									LocaleContextHolder.getLocale());
						}else if("U".equals(requisitionDocument.getWithdraw())){
							withdraw_result = messageSource.getMessage("withdraw.update", null,
									LocaleContextHolder.getLocale());
						}
						
						requisitionDocumentBean.setWithdrawText(withdraw_result);
						requisitionDocumentBean.setWithdraw(requisitionDocument.getWithdraw());
						TechnicianGroupBean technicianGroupBean = new TechnicianGroupBean();
						TechnicianGroup technicianGroup = requisitionDocument.getTechnicianGroup();
						PersonnelBean personnelBean = new PersonnelBean();
						if(null != technicianGroup){
							Personnel personnel = technicianGroup.getPersonnel();
							if(null != personnel){
								personnelBean.setId(personnel.getId());
								personnelBean.setFirstName(personnel.getFirstName());
								personnelBean.setLastName(personnel.getLastName());
								personnelBean.setNickName(personnel.getNickName());
							}
							technicianGroupBean.setId(technicianGroup.getId());
							technicianGroupBean.setPersonnel(personnelBean);
						}
						requisitionDocumentBean.setTechnicianGroup(technicianGroupBean);
						requisitionDocumentBean.setCreateDateTh(null==requisitionDocument.getCreateDate()?"":formatDataTimeTh.format(requisitionDocument.getCreateDate()));	
					}
					requisitionItemBean.setRequisitionDocumentBean(requisitionDocumentBean);
					requisitionItemBean.setId(requisitionItem.getId());
					requisitionItemBean.setQuantity(requisitionItem.getQuantity());
					requisitionItemBean.setReturnEquipmentProductItem(requisitionItem.getReturnEquipmentProductItem());
					requisitionItemBean.setSellEquipmentProductItem(requisitionItem.getSellEquipmentProductItem());
					requisitionItemBeans.add(requisitionItemBean );
				}
			}
			
			equipmentProductItemBean.setRequisitionItemBeans(requisitionItemBeans);
			
			// add to equipmentproductBean List
			equipmentProductItemBeans.add(equipmentProductItemBean);
			}
		}
		// set product item to product
		equipmentProductBean.setEquipmentProductItemBeans(equipmentProductItemBeans);
		equipmentProductBean.setReservations(reservations);
		equipmentProductBean.setRest(rest);
		equipmentProductBean.setUsable(usable);
		equipmentProductBean.setReserve(spare);
		equipmentProductBean.setLend(lend);
		equipmentProductBean.setRepair(repair);
		equipmentProductBean.setOutOfOrder(out_of_order);
		

		return equipmentProductBean;
	}

	public static ServiceProductBean populateEntityToDto(ServiceProduct sp) {
		ServiceProductBean serviceProductBean = new ServiceProductBean();
		if(null != sp){
			serviceProductBean.setId(sp.getId());
			serviceProductBean.setProductName(sp.getServiceChargeName());
			serviceProductBean.setProductCode(sp.getProductCode());
			serviceProductBean.setPrice(sp.getPrice());
			
			// equipmentProductCategoryBean
			EquipmentProductCategoryBean epc = new EquipmentProductCategoryBean();
			epc.setId(sp.getEquipmentProductCategory().getId());
			epc.setEquipmentProductCategoryName(sp.getEquipmentProductCategory().getEquipmentProductCategoryName());
			epc.setEquipmentProductCategoryCode(sp.getEquipmentProductCategory().getEquipmentProductCategoryCode());
			serviceProductBean.setProductCategory(epc);
			
			UnitBean unit = new UnitBean();
			unit.setUnitName(sp.getUnit().getUnitName());
			serviceProductBean.setUnit(unit);
			
			serviceProductBean.setType(TYPE_SERVICE);
		}
		return serviceProductBean;
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
		}
		return internetProductBean;
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

	public void setInternetProductService(InternetProductService internetProductService) {
		this.internetProductService = internetProductService;
	}

	public void setServiceProductService(ServiceProductService serviceProductService) {
		this.serviceProductService = serviceProductService;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
