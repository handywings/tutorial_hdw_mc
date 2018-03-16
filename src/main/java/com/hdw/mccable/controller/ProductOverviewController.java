package com.hdw.mccable.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.google.gson.Gson;
import com.hdw.mccable.dto.AddressBean;
import com.hdw.mccable.dto.AlertBean;
import com.hdw.mccable.dto.CompanyBean;
import com.hdw.mccable.dto.JsonResponse;
import com.hdw.mccable.dto.StockBean;
import com.hdw.mccable.entity.Address;
import com.hdw.mccable.entity.Company;
import com.hdw.mccable.entity.EquipmentProduct;
import com.hdw.mccable.entity.EquipmentProductItem;
import com.hdw.mccable.entity.Stock;
import com.hdw.mccable.service.CompanyService;
import com.hdw.mccable.service.StockService;
import com.hdw.mccable.utils.AlertUtil;

@Controller
@RequestMapping("/productoverview")
public class ProductOverviewController extends BaseController{
	
	final static Logger logger = Logger.getLogger(ProductOverviewController.class);
	public static final String CONTROLLER_NAME = "productoverview/";
	Gson g = new Gson();
	
	//initial service
	
	@Autowired(required=true)
	@Qualifier(value="stockService")
	private StockService stockService;
	
	@Autowired(required=true)
	@Qualifier(value="companyService")
	private CompanyService companyService;	
	
	@Autowired
    MessageSource messageSource;
	
	//end initial service
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView initProductOverview(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : initZone][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		List<StockBean> stockBeans = new ArrayList<StockBean>();
		List<CompanyBean> companyBeans = new ArrayList<CompanyBean>();
		StockBean stockBeanFinal = new StockBean();
		int allProducts = 0;		// รายการสินค้าทั้งหมด
		int lowInStock = 0;			// รายการสินค้าใกล้หมด
		int outOfStock = 0;			// รายการสินค้าหมด
		int insuranceExpire = 0; 	// รายการสินค้าหมดประกัน
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission
		if(isPermission()){
			try{
				//call service
				List<Company> companys = companyService.findAll();
				if(null != companys && !companys.isEmpty()){
					CompanyController companyController = new CompanyController();
					for(Company company:companys){
						companyBeans.add(companyController.populateEntityToDto(company));
					}
				}
				
				List<Stock> stocks = stockService.findAll();
				if(null != stocks && !stocks.isEmpty()){
					for(Stock stock:stocks){
						StockBean stockBean = populateEntityToDto(stock);
						allProducts += stockBean.getAllProducts();
						lowInStock += stockBean.getLowInStock();
						outOfStock += stockBean.getOutOfStock();
						insuranceExpire += stockBean.getInsuranceExpire();
						stockBeans.add(stockBean);
					}
					stockBeanFinal.setAllProducts(allProducts);
					stockBeanFinal.setLowInStock(lowInStock);
					stockBeanFinal.setOutOfStock(outOfStock);
					stockBeanFinal.setInsuranceExpire(insuranceExpire);
				}
				
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
		modelAndView.addObject("stocks", stockBeans);
		modelAndView.addObject("stock", stockBeanFinal);
		modelAndView.addObject("companys", companyBeans);
		
		
		//remove session
		session.removeAttribute("alert");
		
		modelAndView.setViewName(CONTROLLER_NAME+INIT);
		return modelAndView;
	}
	
	@RequestMapping(value="getByCompany/json/{id}", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody JsonResponse getByCompany(@PathVariable String id) {
		JsonResponse jsonResponse = new JsonResponse();
		List<StockBean> stockBeans = new ArrayList<StockBean>();
		List<Stock> stocks = new ArrayList<Stock>();
		if(isPermission()){
			
			try{
				if(!"0".equals(id)){
					stocks = stockService.findByCompany(Long.valueOf(id));
				}else{
					stocks = stockService.findAll();
				}
				if(stocks != null && !stocks.isEmpty()){
					for(Stock stock:stocks){
						StockBean stockBean = populateEntityToDto(stock);
						stockBeans.add(stockBean);
					}
					jsonResponse.setError(false);
					jsonResponse.setResult(stockBeans);
				}else{
					jsonResponse.setResult(stockBeans);
					jsonResponse.setError(false);
				}
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
	
	@RequestMapping(value="get/json/{id}", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody JsonResponse getDataForEdit(@PathVariable String id) {
		JsonResponse jsonResponse = new JsonResponse();
		
		if(isPermission()){
			try{
				Stock stock = stockService.getStockById(Long.valueOf(id));
				if(stock != null){
					StockBean stockBean = populateEntityToDto(stock);
					jsonResponse.setError(false);
					jsonResponse.setResult(stockBean);
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
		
		return jsonResponse;
	}
	
	@RequestMapping(value="update", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public JsonResponse updateServicePackageTypeBean(@RequestBody final StockBean stockBean, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			//create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try{
				logger.info("[method : updateServicePackageTypeBean][Type : Controller]");
//				logger.info("[servicePackageTypeBean : "+g.toJson(stockBean)+"]");
				if(null != stockBean){
					Stock stock = stockService.getStockById(Long.valueOf(stockBean.getId()));
					if(null != stock){
						//update
						stock.setStockName(stockBean.getStockName());
						stock.setStockDetail(stockBean.getStockDetail());
						stock.setCompany(companyService.getCompanyById(stockBean.getCompany().getId()));
						
						if(null != stock.getAddress()){
							stock.getAddress().setDetail(stockBean.getAddress().getDetail());
							stock.getAddress().setUpdatedBy(getUserNameLogin());
							stock.getAddress().setUpdatedDate(CURRENT_TIMESTAMP);
						}else{
							Address address = new Address();
							address.setDetail(stockBean.getAddress().getDetail());
							address.setCreateDate(CURRENT_TIMESTAMP);
							address.setCreatedBy(getUserNameLogin());
							address.setStock(stock);
							stock.setAddress(address);
						}
						
						stock.setUpdatedDate(CURRENT_TIMESTAMP);
						stock.setUpdatedBy(getUserNameLogin());
						stockService.update(stock);
						jsonResponse.setError(false);
					}else{
						jsonResponse.setError(true);
					}
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
		
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("stock.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	@RequestMapping(value="save", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public JsonResponse saveStock(@RequestBody final StockBean stockBean, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			//create timestamp
			Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
			try{
				logger.info("[method : saveStock][Type : Controller]");
//				logger.info("[stockBean : "+g.toJson(stockBean)+"]");
				if(null != stockBean){
					//save
					Stock stock = new Stock();
					stock.setStockCode(String.format("%1s%2s", "STOCK-", stockService.genStockCode()));
					stock.setStockName(stockBean.getStockName());
					stock.setStockDetail(stockBean.getStockDetail());
					
					Address address = new Address();
					address.setDetail(stockBean.getAddress().getDetail());
					address.setCreateDate(CURRENT_TIMESTAMP);
					address.setCreatedBy(getUserNameLogin());
					address.setStock(stock);
					stock.setAddress(address);
					
					stock.setCompany(companyService.getCompanyById(stockBean.getCompany().getId()));
					
					stock.setCreateDate(CURRENT_TIMESTAMP);
					stock.setCreatedBy(getUserNameLogin());
					stockService.save(stock);
				}

				
			}catch(Exception ex){
				ex.printStackTrace();
				jsonResponse.setMessage(ex.getMessage());
				jsonResponse.setError(true);
				}
		}else{
			jsonResponse.setError(true);
		}
		
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("stock.transaction.save.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value="delete/{id}", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public JsonResponse deleteStock(@PathVariable String id, HttpServletRequest request) {
		JsonResponse jsonResponse = new JsonResponse();
		if(isPermission()){
			try{
				Stock stock = stockService.getStockById(Long.valueOf(id));
				stock.setDeleted(Boolean.TRUE);
				
				if(stock != null){
					stockService.update(stock);
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
		
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.delete.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("stock.transaction.delete.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	public StockBean populateEntityToDto(Stock stock) {

		StockBean stockBean = new StockBean();
		int allProducts = 0;		// รายการสินค้าทั้งหมด
		int lowInStock = 0;			// รายการสินค้าใกล้หมด
		int outOfStock = 0;			// รายการสินค้าหมด
		int insuranceExpire = 0; 	// รายการสินค้าหมดประกัน
		
		if(null != stock){
			stockBean.setId(stock.getId());
			stockBean.setStockCode(stock.getStockCode());
			stockBean.setStockName(stock.getStockName());
			stockBean.setStockDetail(stock.getStockDetail());
			
			CompanyController companyController = new CompanyController();
			CompanyBean companyBean = companyController.populateEntityToDto(stock.getCompany());
			stockBean.setCompany(companyBean);
			
			AddressBean addressBean = new AddressBean();
			Address address = stock.getAddress();
			addressBean.setDetail(null == address?"":address.getDetail());
			stockBean.setAddress(addressBean);
			
			List<EquipmentProduct> equipmentProducts = stock.getEquipmentProducts();
			if(null != equipmentProducts && !equipmentProducts.isEmpty()){
				
				for(EquipmentProduct equipmentProduct:equipmentProducts){
					boolean flagCheckCountMinimum = false;
					
					List<EquipmentProductItem> equipmentProductItem = equipmentProduct.getEquipmentProductItems();
					if(null != equipmentProductItem && !equipmentProductItem.isEmpty() && 
							equipmentProductItem.size() > 0 && !equipmentProductItem.get(0).getSerialNo().isEmpty()){
						allProducts ++;
						int countItem = 0;
						int countguaranteeDate = 0;
						for(EquipmentProductItem eq:equipmentProductItem){
							if(!eq.isDeleted()){
								if (eq.getGuaranteeDate() != null) {
									if (eq.getGuaranteeDate().compareTo(removeTime(new Date())) < 0) {
										countguaranteeDate++;
									}
								}
								countItem++;
							}
						}
						
						if(countguaranteeDate > 0){
							insuranceExpire++;
						}
						
						if(equipmentProduct.isMinimum()){
							if(equipmentProduct.getMinimumNumber() <= countItem ){
								lowInStock++;
							}
						}
						
						if(countItem <= 0){
							outOfStock++;
						}
						
					}else{
					//case ไม่มี s/n
						allProducts ++;
						int countItem = 0;
						int countguaranteeDate = 0;
						for(EquipmentProductItem eq:equipmentProductItem){
							if(!eq.isDeleted()){
								if (eq.getGuaranteeDate() != null) {
									if (eq.getGuaranteeDate().compareTo(removeTime(new Date())) < 0) {
										countguaranteeDate++;
									}
								}
								countItem++;
							}
						}
						
						if(countguaranteeDate > 0){
							insuranceExpire++;
						}
						
						if(equipmentProduct.isMinimum()){
							if(equipmentProduct.getMinimumNumber() <= countItem ){
								lowInStock++;
							}
						}
						
					}
					
				}//end for
			}
			
//			List<InternetProduct> internetProducts = stock.getInternetProducts();
//			if(null != internetProducts && !internetProducts.isEmpty()){
//				allProducts++;
//			}
//			
//			List<ServiceProduct> serviceProducts = stock.getServiceProducts();
//			if(null != serviceProducts && !serviceProducts.isEmpty()){
//				allProducts++;
//			}

			stockBean.setAllProducts(allProducts);
			stockBean.setLowInStock(lowInStock);
			stockBean.setOutOfStock(outOfStock);
			stockBean.setInsuranceExpire(insuranceExpire);
		}
		
		return stockBean;
	}
	
	public static Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
	
	public void generateAlert(JsonResponse jsonResponse, HttpServletRequest request, String title, String detail){
		new AlertUtil().generateAlert(jsonResponse, request, title, detail);
	}
	
	
}
