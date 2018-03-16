package com.hdw.mccable.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.hdw.mccable.dto.AlertBean;
import com.hdw.mccable.dto.CompanyBean;
import com.hdw.mccable.dto.EquipmentProductCategoryBean;
import com.hdw.mccable.dto.InternetProductBean;
import com.hdw.mccable.dto.InternetProductBeanItem;
import com.hdw.mccable.dto.JsonResponse;
import com.hdw.mccable.dto.StockBean;
import com.hdw.mccable.entity.EquipmentProductCategory;
import com.hdw.mccable.entity.InternetProduct;
import com.hdw.mccable.entity.InternetProductItem;
import com.hdw.mccable.entity.Stock;
import com.hdw.mccable.service.EquipmentProductCategoryService;
import com.hdw.mccable.service.InternetProductItemService;
import com.hdw.mccable.service.InternetProductService;
import com.hdw.mccable.service.StockService;
import com.hdw.mccable.utils.AlertUtil;
import com.hdw.mccable.utils.GenerateUtil;
import com.hdw.mccable.utils.TextUtil;

@Controller
@RequestMapping("/productorderinternetproduct")
public class ProductOrderInternetProductController extends BaseController {

	final static Logger logger = Logger.getLogger(ProductOrderInternetProductController.class);
	public static final String CONTROLLER_NAME = "productorderinternetproduct/";
	public static final String DETAIL_PAGE = "detail";
	public static final String EDIT_PAGE = "edit_product";
	Gson g = new Gson();

	// initial service
	@Autowired(required = true)
	@Qualifier(value = "stockService")
	private StockService stockService;

	@Autowired(required = true)
	@Qualifier(value = "internetProductService")
	private InternetProductService internetProductService;

	@Autowired(required = true)
	@Qualifier(value = "equipmentProductCategoryService")
	private EquipmentProductCategoryService equipmentProductCategoryService;

	@Autowired(required = true)
	@Qualifier(value = "internetProductItemService")
	private InternetProductItemService internetProductItemService;

	@Autowired
	MessageSource messageSource;

	// end initial service

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView initInternetProduct(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : initInternetProduct][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		List<Stock> stocks = new ArrayList<Stock>();
		List<InternetProductBean> internetProductBeans = new ArrayList<InternetProductBean>();
		
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
				List<InternetProduct> internetProducts = internetProductService.findAll();
				if (null != internetProducts && !internetProducts.isEmpty()) {
					for (InternetProduct internetProduct : internetProducts) {
						internetProductBeans.add(populateEntityToDto(internetProduct));
					}
				}

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
		modelAndView.addObject("stocks", stocks);
		modelAndView.addObject("internetProduct", internetProductBeans);

		// remove session
		session.removeAttribute("alert");

		modelAndView.setViewName(CONTROLLER_NAME + INIT);
		return modelAndView;
	}

	@RequestMapping(value = "/searchinternetproduct", method = RequestMethod.POST)
	public ModelAndView searchInternetProduct(InternetProductBean internetProductBean, 
			HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : searchInternetProduct][Type : Controller]");
		// logger.info("[InternetProductBean : "+g.toJson(internetProductBean)+"
		// ]");

		ModelAndView modelAndView = new ModelAndView();
		List<Stock> stocks = new ArrayList<Stock>();
		List<InternetProductBean> internetProductBeans = new ArrayList<InternetProductBean>();
		
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
				if (null != internetProductBean) {
					List<InternetProduct> internetProducts = internetProductService.searchByStockOrCriteria(
							internetProductBean.getCriteria(), internetProductBean.getStock().getId());
					if (null != internetProducts && !internetProducts.isEmpty()) {
						for (InternetProduct internetProduct : internetProducts) {
							internetProductBeans.add(populateEntityToDto(internetProduct));
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

		HttpSession session = request.getSession();
		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);
		modelAndView.addObject("stocks", stocks);
		modelAndView.addObject("internetProduct", internetProductBeans);
		modelAndView.addObject("criteria", internetProductBean.getCriteria());
		modelAndView.addObject("stockId", internetProductBean.getStock().getId());

		// remove session
		session.removeAttribute("alert");

		modelAndView.setViewName(CONTROLLER_NAME + INIT);
		return modelAndView;
	}

	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public ModelAndView detail(@PathVariable Long id, Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : detail][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		InternetProductBean internetProductBean = new InternetProductBean();
		HttpSession session = null;
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			InternetProduct internetProduct = internetProductService.getInternetProductById(id);
			if (internetProduct != null) {
				internetProductBean = populateEntityToDto(internetProduct);
				// logger.info("internetProductBean : "+internetProductBean);
			}
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}

		// alert session
		session = request.getSession();
		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);
		modelAndView.addObject("internetProductBean", internetProductBean);

		// remove session
		session.removeAttribute("alert");

		modelAndView.setViewName(CONTROLLER_NAME + DETAIL_PAGE);
		return modelAndView;
	}
	
	@RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable Long id, Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : edit][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		InternetProductBean internetProductBean = new InternetProductBean();
		HttpSession session = null;
		List<Stock> stocks = new ArrayList<Stock>();
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			stocks = stockService.findAll();
			InternetProduct internetProduct = internetProductService.getInternetProductById(id);
			if (internetProduct != null) {
				internetProductBean = populateEntityToDto(internetProduct);
				// logger.info("internetProductBean : "+internetProductBean);
			}
		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}

		// alert session
		session = request.getSession();
		AlertBean alertBean = (AlertBean) session.getAttribute("alert");
		modelAndView.addObject("alertBean", alertBean);
		modelAndView.addObject("internetProductBean", internetProductBean);
		modelAndView.addObject("stocks", stocks);

		// remove session
		session.removeAttribute("alert");

		modelAndView.setViewName(CONTROLLER_NAME + EDIT_PAGE);
		return modelAndView;
	}

	@RequestMapping(value = "update", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public JsonResponse update(@RequestBody final InternetProductBean internetProductBean, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			// create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try {
				logger.info("[method : update][Type : Controller]");
				if (null != internetProductBean) {
					InternetProduct internetProduct = internetProductService
							.getInternetProductById(internetProductBean.getId());
					if (null != internetProduct) {
						// update
						internetProduct.setProductName(internetProductBean.getProductName());
						internetProduct.setProductCode(internetProductBean.getProductCode());

						Stock stock = stockService.getStockById(internetProductBean.getStock().getId());
						internetProduct.setStock(stock);

						internetProduct.setUpdatedDate(CURRENT_TIMESTAMP);
						internetProduct.setUpdatedBy(getUserNameLogin());
						internetProductService.update(internetProduct);
						jsonResponse.setError(false);
					} else {
						jsonResponse.setError(true);
					}
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

		generateAlert(jsonResponse, request,
				messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("internetProduct.transaction.save.success", null,
						LocaleContextHolder.getLocale()));
		return jsonResponse;
	}

	@RequestMapping(value = "item/update", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public JsonResponse updateItem(@RequestBody final InternetProductBeanItem internetProductBeanItem,
			HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			// create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try {
				logger.info("[method : updateItem][Type : Controller]");
				if (null != internetProductBeanItem) {
					InternetProductItem internetProductItem = internetProductItemService
							.getInternetProductItemById(internetProductBeanItem.getId());
					if (null != internetProductItem) {
						// update
						internetProductItem.setUserName(internetProductBeanItem.getUserName());
						internetProductItem.setPassword(internetProductBeanItem.getPassword());
						internetProductItem.setReference(internetProductBeanItem.getReference());

						internetProductItem.setUpdatedDate(CURRENT_TIMESTAMP);
						internetProductItem.setUpdatedBy(getUserNameLogin());
						internetProductItemService.update(internetProductItem);
						jsonResponse.setError(false);
					} else {
						jsonResponse.setError(true);
					}
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

		generateAlert(jsonResponse, request,
				messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("internetProduct.transaction.save.success", null,
						LocaleContextHolder.getLocale()));
		return jsonResponse;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "delete/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonResponse delete(@PathVariable String id, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				InternetProduct internetProduct = internetProductService.getInternetProductById(Long.valueOf(id));
				internetProduct.setDeleted(Boolean.TRUE);

				if (internetProduct != null) {
					internetProductService.update(internetProduct);
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

		generateAlert(jsonResponse, request,
				messageSource.getMessage("alert.title.delete.success", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("stock.transaction.delete.success", null, LocaleContextHolder.getLocale()));

		generateAlert(jsonResponse, request,
				messageSource.getMessage("alert.title.delete.success", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("internetProduct.transaction.delete.success", null,
						LocaleContextHolder.getLocale()));
		return jsonResponse;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "item/delete/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonResponse deleteItem(@PathVariable String id, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				InternetProductItem internetProductitem = internetProductItemService
						.getInternetProductItemById(Long.valueOf(id));
				internetProductitem.setDeleted(Boolean.TRUE);

				if (internetProductitem != null) {
					internetProductItemService.update(internetProductitem);
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

		generateAlert(jsonResponse, request,
				messageSource.getMessage("alert.title.delete.success", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("internetProduct.transaction.delete.success", null,
						LocaleContextHolder.getLocale()));
		return jsonResponse;
	}

	@RequestMapping(value = "get/detail/json/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JsonResponse getDataDetail(@PathVariable String id) {
		JsonResponse jsonResponse = new JsonResponse();
		InternetProductBeanItem internetProductBeanItem = new InternetProductBeanItem();

		if (isPermission()) {
			try {
				InternetProductItem internetProductitem = internetProductItemService
						.getInternetProductItemById(Long.valueOf(id));
				if (internetProductitem != null) {
					internetProductBeanItem = populateEntityToDtoItem(internetProductitem);
					jsonResponse.setError(false);
					jsonResponse.setResult(internetProductBeanItem);
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

	@RequestMapping(value = "get/json/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JsonResponse getData(@PathVariable String id) {
		JsonResponse jsonResponse = new JsonResponse();
		InternetProductBean internetProductBean = new InternetProductBean();

		if (isPermission()) {
			try {
				InternetProduct internetProduct = internetProductService.getInternetProductById(Long.valueOf(id));
				if (internetProduct != null) {
					internetProductBean = populateEntityToDto(internetProduct);
					jsonResponse.setError(false);
					jsonResponse.setResult(internetProductBean);
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

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addInternetProduct(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : addInternetProduct][Type : Controller]");
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

				// dropdown stock
				List<StockBean> stockBeans = loadStockList();
				modelAndView.addObject("stockBeans", stockBeans);

				// modal search init
				List<InternetProductBean> internetProductBeans = loadInternetProduct(new InternetProductBean());
				modelAndView.addObject("internetProductBeans", internetProductBeans);

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
		// remove session
		session.removeAttribute("alert");

		modelAndView.setViewName(CONTROLLER_NAME + ADD);
		return modelAndView;
	}

	// save saveInternetProductNew
	@RequestMapping(value = "saveInternetProductNew", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse saveInternetProductNew(@RequestBody final InternetProductBean internetProductBean,
			HttpServletRequest request) {
		logger.info("[method : saveInternetProductNew][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			// create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try {
				if (internetProductBean != null) {
					InternetProduct internetProduct = new InternetProduct();
					internetProduct.setProductCode(internetProductBean.getProductCode());
					internetProduct.setProductName(internetProductBean.getProductName());
					internetProduct.setCreateDate(CURRENT_TIMESTAMP);
					// -------------> change when LonginController success
					internetProduct.setCreatedBy(getUserNameLogin());
					internetProduct.setDeleted(Boolean.FALSE);
					// stock
					Stock stock = stockService.getStockById(internetProductBean.getStock().getId());
					internetProduct.setStock(stock);
					// load inter user type
					EquipmentProductCategory epc = equipmentProductCategoryService.getEquipmentProductCategoryByCode(
							messageSource.getMessage("equipmentproductcategory.type.internet", null,
									LocaleContextHolder.getLocale()));
					internetProduct.setProductCategory(epc);

					// internet item
					List<InternetProductItem> internetProductItems = new ArrayList<InternetProductItem>();
					for (InternetProductBeanItem internetProductBeanItem : internetProductBean
							.getInternetProductBeanItems()) {
						InternetProductItem internetProductItem = new InternetProductItem();
						internetProductItem.setReference(internetProductBeanItem.getReference());
						internetProductItem.setUserName(internetProductBeanItem.getUserName());
						
						//password
						if(!TextUtil.isNotEmpty(internetProductBeanItem.getPassword())){
							internetProductItem.setPassword(GenerateUtil.generatePassword(8).toLowerCase());
						}else{
							internetProductItem.setPassword(internetProductBeanItem.getPassword().toLowerCase());
						}
						
						internetProductItem.setCreateDate(CURRENT_TIMESTAMP);
						internetProductItem.setCreatedBy(getUserNameLogin());
						internetProductItem.setStatus(messageSource.getMessage("internetProduct.item.unuse", null,
								LocaleContextHolder.getLocale()));
						internetProductItem.setDeleted(Boolean.FALSE);
						internetProductItem.setInternetProduct(internetProduct);
						internetProductItems.add(internetProductItem);
					}
					internetProduct.setInternetProductItems(internetProductItems);
					internetProductService.save(internetProduct);

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

	// loadInternetProductWithId
	@RequestMapping(value = "loadInternetProductWithId/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonResponse loadInternetProductWithId(@PathVariable String id, HttpServletRequest request) {
		logger.info("[method : loadInternetProductWithId][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				InternetProduct internetProduct = internetProductService.getInternetProductById(Long.valueOf(id));
				if (internetProduct != null) {
					InternetProductBean internetProductBean = new InternetProductBean();
					internetProductBean = populateEntityToDto(internetProduct);
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

	// loadInternetProduct
	@RequestMapping(value = "loadInternetProduct", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public JsonResponse loadInternetProduct(@RequestBody final InternetProductBean internetProductBean,
			HttpServletRequest request) {
		logger.info("[method : loadInternetProduct][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				logger.info("param internetProductBean : " + internetProductBean.toString());
				List<InternetProductBean> internetProductBeans = loadInternetProduct(internetProductBean);
				jsonResponse.setError(false);
				jsonResponse.setResult(internetProductBeans);

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

	// save saveInternetProductOld
	@RequestMapping(value = "saveInternetProductOld", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse saveInternetProductOld(@RequestBody final InternetProductBean internetProductBean,
			HttpServletRequest request) {
		logger.info("[method : saveInternetProductOld][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			// create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try {
				if (internetProductBean != null) {
					InternetProduct internetProduct = internetProductService.getInternetProductById(internetProductBean.getId()); 
					
					if(internetProduct != null){
					// internet item
					List<InternetProductItem> internetProductItems = new ArrayList<InternetProductItem>();
					for (InternetProductBeanItem internetProductBeanItem : internetProductBean
							.getInternetProductBeanItems()) {
						InternetProductItem internetProductItem = new InternetProductItem();
						internetProductItem.setReference(internetProductBeanItem.getReference());
						internetProductItem.setUserName(internetProductBeanItem.getUserName());
						
						//password
						if("".equals(internetProductBeanItem.getPassword()) || "".equals(internetProductBeanItem.getPassword().trim())){
							internetProductItem.setPassword(GenerateUtil.generatePassword(8).toLowerCase());
						}else{
							internetProductItem.setPassword(internetProductBeanItem.getPassword().toLowerCase());
						}
						
						internetProductItem.setCreateDate(CURRENT_TIMESTAMP);
						internetProductItem.setCreatedBy(getUserNameLogin());
						internetProductItem.setStatus(messageSource.getMessage("internetProduct.item.unuse", null,
								LocaleContextHolder.getLocale()));
						internetProductItem.setDeleted(Boolean.FALSE);
						internetProductItem.setInternetProduct(internetProduct);
						internetProductItems.add(internetProductItem);
					}
						internetProduct.setInternetProductItems(internetProductItems);
						internetProductService.update(internetProduct);
					}else{
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

	@RequestMapping(value = "updateInternetProduct", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse updateInternetProduct(@RequestBody final InternetProductBean internetProductBean,
			HttpServletRequest request) {
		logger.info("[method : saveInternetProductOld][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			// create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try {
				if (internetProductBean != null) {
					InternetProduct internetProduct = internetProductService.getInternetProductById(internetProductBean.getId()); 
					
					if(internetProduct != null){
						internetProduct.setProductName(internetProductBean.getProductName());
						internetProduct.setProductCode(internetProductBean.getProductCode());
						
						Stock stock = stockService.getStockById(internetProductBean.getStock().getId());
						internetProduct.setStock(stock);
					// internet item
					List<InternetProductItem> internetProductItems = new ArrayList<InternetProductItem>();
					for (InternetProductBeanItem internetProductBeanItem : internetProductBean.getInternetProductBeanItems()) {
						InternetProductItem internetProductItem = new InternetProductItem();
						internetProductItem = internetProductItemService.getInternetProductItemById(internetProductBeanItem.getId());
						internetProductItem.setReference(internetProductBeanItem.getReference());
						internetProductItem.setUserName(internetProductBeanItem.getUserName());
						//password
						if("".equals(internetProductBeanItem.getPassword()) || "".equals(internetProductBeanItem.getPassword().trim())){
							internetProductItem.setPassword(GenerateUtil.generatePassword(8).toLowerCase());
						}else{
							internetProductItem.setPassword(internetProductBeanItem.getPassword().toLowerCase());
						}
						internetProductItem.setUpdatedDate(CURRENT_TIMESTAMP);
						internetProductItem.setUpdatedBy(getUserNameLogin());
						internetProductItems.add(internetProductItem);
					}
						internetProduct.setInternetProductItems(internetProductItems);
						internetProductService.update(internetProduct);
					}else{
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
	

	@RequestMapping(value = "checkDuplicateUsername", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse checkDuplicateUsername(@RequestBody String userName,
			HttpServletRequest request) {
		logger.info("[method : checkDuplicateUsername][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				userName = userName.replace("\"", "");
				logger.info("param userName : " + userName);
				boolean checkDup = internetProductService.checkDubplicateUsername(userName);
				if(StringUtils.isWhitespace(userName)){
					jsonResponse.setResult(true);
				}else{
					jsonResponse.setResult(checkDup);
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
	
	@RequestMapping(value = "checkDuplicateUsernameForUpdate", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse checkDuplicateUsernameForUpdate(@RequestBody final InternetProductBeanItem internetProductBeanItem,
			HttpServletRequest request) {
		logger.info("[method : checkDuplicateUsernameForUpdate][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				logger.info("param internetProductBeanItem : " + internetProductBeanItem.toString());
				boolean checkDup = internetProductService.checkDuplicateUsernameForUpdate(internetProductBeanItem.getUserName(),internetProductBeanItem.getId());
				jsonResponse.setResult(checkDup);
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
	
		
	// load InternetProduct process
	public List<InternetProductBean> loadInternetProduct(InternetProductBean internetProductBean) {
		String key = "";
		Long stockId = 0L;
		// stock
		if (internetProductBean.getStock() != null && internetProductBean.getStock().getId() != null
				&& (Long.valueOf(internetProductBean.getStock().getId()) > 0)) {
			stockId = Long.valueOf(internetProductBean.getStock().getId());
		}

		// key
		if (null != internetProductBean.getProductName() && (!internetProductBean.getProductName().isEmpty())) {
			key = internetProductBean.getProductName();
		}
		List<InternetProduct> internetProducts = internetProductService.searchByStockOrCriteria(key, stockId);
		List<InternetProductBean> internetProductBeans = new ArrayList<InternetProductBean>();
		for (InternetProduct internetProduct : internetProducts) {
			InternetProductBean bean = populateEntityToDto(internetProduct);
			internetProductBeans.add(bean);
		}

		return internetProductBeans;
	}

	public InternetProductBean populateEntityToDto(InternetProduct internetProduct) {
		InternetProductBean internetProductBean = new InternetProductBean();

		if (null != internetProduct) {
			List<InternetProductBeanItem> internetProductBeanItems = new ArrayList<InternetProductBeanItem>();
			if (null != internetProduct) {

				internetProductBean.setId(internetProduct.getId());
				internetProductBean.setProductName(internetProduct.getProductName());
				internetProductBean.setProductCode(internetProduct.getProductCode());

				List<InternetProductItem> internetProductItems = internetProduct.getInternetProductItems();
				if (null != internetProductItems && internetProductItems.size() > 0) {
					for (InternetProductItem internetProductItem : internetProductItems) {
						if (!internetProductItem.isDeleted()) {
							InternetProductBeanItem item = new InternetProductBeanItem();
							item.setId(internetProductItem.getId());
							item.setUserName(internetProductItem.getUserName());
							item.setPassword(internetProductItem.getPassword());
							item.setReference(internetProductItem.getReference());
							item.setStatus(internetProductItem.getStatus());
							internetProductBeanItems.add(item);
						}
					}
				}

				internetProductBean.setInternetProductBeanItems(internetProductBeanItems);
				ProductOverviewController productOverviewController = new ProductOverviewController();

				StockBean stockBean = new StockBean();
				Stock stock = internetProduct.getStock();
				stockBean = productOverviewController.populateEntityToDto(stock);

				internetProductBean.setStock(stockBean);
				//product type
				EquipmentProductCategoryBean epc = new EquipmentProductCategoryBean();
				epc.setId(internetProduct.getEquipmentProductCategory().getId());
				epc.setEquipmentProductCategoryName(internetProduct.getEquipmentProductCategory().getEquipmentProductCategoryName());
				epc.setEquipmentProductCategoryCode(internetProduct.getEquipmentProductCategory().getEquipmentProductCategoryCode());
				internetProductBean.setProductCategory(epc);
			}
			internetProductBean.setMessageSource(messageSource);
			internetProductBean.unitTypeInternet();
			
		}
		return internetProductBean;
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

	// getter setter

	public InternetProductBeanItem populateEntityToDtoItem(InternetProductItem internetProductItem) {
		InternetProductBeanItem item = new InternetProductBeanItem();
		if (null != internetProductItem) {
			if (!internetProductItem.isDeleted()) {
				item.setId(internetProductItem.getId());
				item.setUserName(internetProductItem.getUserName());
				item.setPassword(internetProductItem.getPassword());
				item.setReference(internetProductItem.getReference());
			}
		}
		return item;
	}

	// alert generat
	public void generateAlert(JsonResponse jsonResponse, HttpServletRequest request, String title, String detail) {
		new AlertUtil().generateAlert(jsonResponse, request, title, detail);
	}

	public void setStockService(StockService stockService) {
		this.stockService = stockService;
	}

	public void setInternetProductService(InternetProductService internetProductService) {
		this.internetProductService = internetProductService;
	}

	public void setEquipmentProductCategoryService(EquipmentProductCategoryService equipmentProductCategoryService) {
		this.equipmentProductCategoryService = equipmentProductCategoryService;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

}
