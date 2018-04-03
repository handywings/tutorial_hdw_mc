package com.hdw.mccable.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.hdw.mccable.Manager.DataManager;
import com.hdw.mccable.dto.AlertBean;
import com.hdw.mccable.dto.BackupFileBean;
import com.hdw.mccable.dto.CustomerBean;
import com.hdw.mccable.dto.JsonResponse;
import com.hdw.mccable.dto.MigrateCustomerBean;
import com.hdw.mccable.dto.MigrateInvoiceBean;
import com.hdw.mccable.dto.SearchBillScanBean;
import com.hdw.mccable.entity.Address;
import com.hdw.mccable.entity.Amphur;
import com.hdw.mccable.entity.BackupFile;
import com.hdw.mccable.entity.Career;
import com.hdw.mccable.entity.Contact;
import com.hdw.mccable.entity.Customer;
import com.hdw.mccable.entity.CustomerFeature;
import com.hdw.mccable.entity.District;
import com.hdw.mccable.entity.DocumentFile;
import com.hdw.mccable.entity.EquipmentProduct;
import com.hdw.mccable.entity.EquipmentProductCategory;
import com.hdw.mccable.entity.EquipmentProductItem;
import com.hdw.mccable.entity.HistoryTechnicianGroupWork;
import com.hdw.mccable.entity.InternetProduct;
import com.hdw.mccable.entity.InternetProductItem;
import com.hdw.mccable.entity.Invoice;
import com.hdw.mccable.entity.InvoiceHistoryPrint;
import com.hdw.mccable.entity.ProductItem;
import com.hdw.mccable.entity.Province;
import com.hdw.mccable.entity.Receipt;
import com.hdw.mccable.entity.ReceiptHistoryPrint;
import com.hdw.mccable.entity.ServiceApplication;
import com.hdw.mccable.entity.ServiceApplicationType;
import com.hdw.mccable.entity.ServicePackage;
import com.hdw.mccable.entity.ServiceProduct;
import com.hdw.mccable.entity.Stock;
import com.hdw.mccable.entity.TemplateService;
import com.hdw.mccable.entity.Unit;
import com.hdw.mccable.entity.Worksheet;
import com.hdw.mccable.entity.WorksheetSetup;
import com.hdw.mccable.entity.Zone;
import com.hdw.mccable.service.AddressService;
import com.hdw.mccable.service.BackupFileService;
import com.hdw.mccable.service.CompanyService;
import com.hdw.mccable.service.CustomerService;
import com.hdw.mccable.service.EquipmentProductCategoryService;
import com.hdw.mccable.service.EquipmentProductItemService;
import com.hdw.mccable.service.EquipmentProductService;
import com.hdw.mccable.service.FinancialService;
import com.hdw.mccable.service.InternetProductItemService;
import com.hdw.mccable.service.InternetProductService;
import com.hdw.mccable.service.ProductItemService;
import com.hdw.mccable.service.ProductService;
import com.hdw.mccable.service.ServiceApplicationService;
import com.hdw.mccable.service.ServicePackageService;
import com.hdw.mccable.service.ServicePackageTypeService;
import com.hdw.mccable.service.ServiceProductService;
import com.hdw.mccable.service.StockService;
import com.hdw.mccable.service.UnitService;
import com.hdw.mccable.service.WorkSheetService;
import com.hdw.mccable.service.ZoneService;
import com.hdw.mccable.utils.AlertUtil;
import com.hdw.mccable.utils.FilePathUtils;
import com.hdw.mccable.utils.GenerateUtil;
import com.hdw.mccable.utils.ReadWriteExcelFile;
import com.hdw.mccable.utils.TextUtil;

@Controller
@RequestMapping("/migrate")
public class MigrateController extends BaseController{
	final static Logger logger = Logger.getLogger(MigrateController.class);
	public static final String CONTROLLER_NAME = "migrate/";	
	
	Gson gson = new Gson();
	
	@Autowired(required = true)
	@Qualifier(value = "customerService")
	private CustomerService customerService;
	
	@Autowired(required = true)
	@Qualifier(value = "addressService")
	private AddressService addressService;
	
	@Autowired(required = true)
	@Qualifier(value = "zoneService")
	private ZoneService zoneService;
	
	@Autowired(required = true)
	@Qualifier(value = "serviceApplicationService")
	private ServiceApplicationService serviceApplicationService;
	
	@Autowired(required = true)
	@Qualifier(value = "servicePackageService")
	private ServicePackageService servicePackageService;
	
	@Autowired(required = true)
	@Qualifier(value = "workSheetService")
	private WorkSheetService workSheetService;
	
	@Autowired(required = true)
	@Qualifier(value = "serviceProductService")
	private ServiceProductService serviceProductService;
	
	@Autowired(required = true)
	@Qualifier(value = "productItemService")
	private ProductItemService productItemService;
	
	@Autowired(required = true)
	@Qualifier(value = "financialService")
	private FinancialService financialService;
	
	@Autowired(required = true)
	@Qualifier(value = "equipmentProductService")
	private EquipmentProductService equipmentProductService;
	
	@Autowired(required = true)
	@Qualifier(value = "equipmentProductItemService")
	private EquipmentProductItemService equipmentProductItemService;
	
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
	@Qualifier(value = "productService")
	private ProductService productService;
	
	@Autowired(required = true)
	@Qualifier(value = "internetProductService")
	private InternetProductService internetProductService;
	
	@Autowired(required = true)
	@Qualifier(value = "internetProductItemService")
	private InternetProductItemService internetProductItemService;
	
	@Autowired(required = true)
	@Qualifier(value = "companyService")
	private CompanyService companyService;
	
	@Autowired(required = true)
	@Qualifier(value = "servicePackageTypeService")
	private ServicePackageTypeService servicePackageTypeService;
	
	@Autowired
    MessageSource messageSource;

	Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView initMigrate(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : initMigrate][Type : Controller]");
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
		
		//remove session
		session.removeAttribute("alert");
		
		modelAndView.setViewName(CONTROLLER_NAME+INIT);
		return modelAndView;
	}	
	
	@RequestMapping(value="migrateProduct", method = RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public JsonResponse migrateProduct(@RequestParam("file_product") final MultipartFile file_customer,
			HttpServletRequest request) {
		logger.info("[method : migrateProduct][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				if(null != file_customer && file_customer.getSize() > 0){
					InputStream inputStream = file_customer.getInputStream();
			        if(null != inputStream){
			        	Workbook workbook = ReadWriteExcelFile.ReadExcelProduct(inputStream);
			        	if(null != workbook){
			        		String name = file_customer.getOriginalFilename();
			        		logger.info("name : "+name);
			        		if("NO.อุปกรณ์.xlsx".equals(name)){
			        			processMigrateProductItem(workbook);
			        		}else if("ยอดคงเหลือ 17มค61.xlsx".equals(name)){
			        			processMigrateProduct1(workbook);
			        		}else if("บรอดแบนด์ กล้องวงจรปิด.xlsx".equals(name)){
			        			processMigrateProductCCTV1(workbook);
			        		}else if("มหาชัยเคเบิล  กล้องวงจรปิด.xlsx".equals(name)){
			        			processMigrateProductCCTV2(workbook);
			        		}else if("วีระพงษ์  กล้องวงจรปิด.xlsx".equals(name)){
			        			processMigrateProductCCTV3(workbook);
			        		}else if("ข้อมูลลูกค้า(เก่า)_แยกจุด.xlsx".equals(name)){
			        			processMigratePoint(workbook);
			        		}else{
			        			processMigrateProduct(workbook);
			        		}
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
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("migrate.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	@SuppressWarnings("deprecation")
	private void processMigrateProductItem(Workbook workbook) throws Exception {
		int sheetIndex = 0;
		int intRow = 0;
		String productName = "K2";
		EquipmentProduct equipmentProduct = equipmentProductService.getEquipmentProductByProductName(productName);
		if(null != equipmentProduct){
			for (Row rows : workbook.getSheetAt(sheetIndex)) {
				intRow++;
				if (intRow > 1) {
					String serialNo = getDataString(workbook, sheetIndex, "C", intRow);
					if(StringUtils.isNotEmpty(serialNo)){ // มี SN
						EquipmentProductItem equipmentProductItem = equipmentProductItemService.getEquipmentProductItemBySerialNo(serialNo);
						if(null != equipmentProductItem) continue; // เช็ค SN ต้องไม่ซ้ำ
						equipmentProductItem = new EquipmentProductItem();
						equipmentProductItem.setEquipmentProduct(equipmentProduct);
						equipmentProductItem.setSerialNo(serialNo);
						equipmentProductItem.setNumberImport(1);
						equipmentProductItem.setBalance(1);
						equipmentProductItem.setStatus(1);
//						equipmentProductItem.setReference(reference);
//						equipmentProductItem.setGuaranteeDate(datepickerGuarantee);
//						equipmentProductItem.setOrderDate(new Date());
						equipmentProductItem.setCost(equipmentProduct.getCost());
						equipmentProductItem.setPriceIncTax(equipmentProduct.getSalePrice());
						equipmentProductItem.setSalePrice(equipmentProduct.getSalePrice());
						equipmentProductItem.setCreateDate(CURRENT_TIMESTAMP);
						equipmentProductItem.setCreatedBy("Migrate");
						equipmentProductItemService.save(equipmentProductItem);
					} else { // ไม่มี SN
//						EquipmentProductItem equipmentProductItem = new EquipmentProductItem();
//						equipmentProductItem.setEquipmentProduct(equipmentProduct);
//						equipmentProductItem.setSerialNo(null);
//						equipmentProductItem.setReference(reference);
//						equipmentProductItem.setGuaranteeDate(datepickerGuarantee);
//						equipmentProductItem.setOrderDate(datepickerOrderDate);
//						equipmentProductItem.setCost(productCost);
//						equipmentProductItem.setPriceIncTax(priceIncTax);
//						equipmentProductItem.setSalePrice(productSalePrice);
//						equipmentProductItem.setCreateDate(CURRENT_TIMESTAMP);
//						equipmentProductItem.setCreatedBy("Migrate");
//						equipmentProductItemService.save(equipmentProductItem);
					}
				}
			}
			// อัพ product ตัวแม่
			if(null != equipmentProduct){
				ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
				pro.setEquipmentProductService(equipmentProductService);
				pro.setMessageSource(messageSource);
				pro.autoUpdateStatusEquipmentProduct(equipmentProduct);
			}
		}
		
		sheetIndex = 1;
		intRow = 0;
		productName = "BDCOM ONUไม่มีWIFIP1501C1 คอนเวิสเตอร์";
		equipmentProduct = equipmentProductService.getEquipmentProductByProductName(productName);
		if(null != equipmentProduct){
			for (Row rows : workbook.getSheetAt(sheetIndex)) {
				intRow++;
				if (intRow > 1) {
					String serialNo = getDataString(workbook, sheetIndex, "B", intRow);
					if(StringUtils.isNotEmpty(serialNo)){ // มี SN
						EquipmentProductItem equipmentProductItem = equipmentProductItemService.getEquipmentProductItemBySerialNo(serialNo);
						if(null != equipmentProductItem) continue; // เช็ค SN ต้องไม่ซ้ำ
						equipmentProductItem = new EquipmentProductItem();
						equipmentProductItem.setEquipmentProduct(equipmentProduct);
						equipmentProductItem.setSerialNo(serialNo);
						equipmentProductItem.setNumberImport(1);
						equipmentProductItem.setBalance(1);
						equipmentProductItem.setStatus(1);
//						equipmentProductItem.setReference(reference);
//						equipmentProductItem.setGuaranteeDate(datepickerGuarantee);
//						equipmentProductItem.setOrderDate(new Date());
						equipmentProductItem.setCost(equipmentProduct.getCost());
						equipmentProductItem.setPriceIncTax(equipmentProduct.getSalePrice());
						equipmentProductItem.setSalePrice(equipmentProduct.getSalePrice());
						equipmentProductItem.setCreateDate(CURRENT_TIMESTAMP);
						equipmentProductItem.setCreatedBy("Migrate");
						equipmentProductItemService.save(equipmentProductItem);
					}
				}
			}
			// อัพ product ตัวแม่
			if(null != equipmentProduct){
				ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
				pro.setEquipmentProductService(equipmentProductService);
				pro.setMessageSource(messageSource);
				pro.autoUpdateStatusEquipmentProduct(equipmentProduct);
			}
		}
		
		sheetIndex = 2;
		intRow = 0;
		productName = "Router TOTOLINK A850R  4 LAN/WiFi";
		equipmentProduct = equipmentProductService.getEquipmentProductByProductName(productName);
		if(null != equipmentProduct){
			for (Row rows : workbook.getSheetAt(sheetIndex)) {
				intRow++;
				if (intRow > 1) {
					String serialNo = getDataString(workbook, sheetIndex, "C", intRow);
					if(StringUtils.isNotEmpty(serialNo)){ // มี SN
						EquipmentProductItem equipmentProductItem = equipmentProductItemService.getEquipmentProductItemBySerialNo(serialNo);
						if(null != equipmentProductItem) continue; // เช็ค SN ต้องไม่ซ้ำ
						equipmentProductItem = new EquipmentProductItem();
						equipmentProductItem.setEquipmentProduct(equipmentProduct);
						equipmentProductItem.setSerialNo(serialNo);
						equipmentProductItem.setNumberImport(1);
						equipmentProductItem.setBalance(1);
						equipmentProductItem.setStatus(1);
//						equipmentProductItem.setReference(reference);
//						equipmentProductItem.setGuaranteeDate(datepickerGuarantee);
//						equipmentProductItem.setOrderDate(new Date());
						equipmentProductItem.setCost(equipmentProduct.getCost());
						equipmentProductItem.setPriceIncTax(equipmentProduct.getSalePrice());
						equipmentProductItem.setSalePrice(equipmentProduct.getSalePrice());
						equipmentProductItem.setCreateDate(CURRENT_TIMESTAMP);
						equipmentProductItem.setCreatedBy("Migrate");
						equipmentProductItemService.save(equipmentProductItem);
					}
				}
			}
			// อัพ product ตัวแม่
			if(null != equipmentProduct){
				ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
				pro.setEquipmentProductService(equipmentProductService);
				pro.setMessageSource(messageSource);
				pro.autoUpdateStatusEquipmentProduct(equipmentProduct);
			}
		}
		
		sheetIndex = 3;
		intRow = 0;
		productName = "NETIS WF2420 300Mbps";
		equipmentProduct = equipmentProductService.getEquipmentProductByProductName(productName);
		if(null != equipmentProduct){
			for (Row rows : workbook.getSheetAt(sheetIndex)) {
				intRow++;
				if (intRow > 1) {
					String serialNo = getDataString(workbook, sheetIndex, "C", intRow);
					if(StringUtils.isNotEmpty(serialNo)){ // มี SN
						EquipmentProductItem equipmentProductItem = equipmentProductItemService.getEquipmentProductItemBySerialNo(serialNo);
						if(null != equipmentProductItem) continue; // เช็ค SN ต้องไม่ซ้ำ
						equipmentProductItem = new EquipmentProductItem();
						equipmentProductItem.setEquipmentProduct(equipmentProduct);
						equipmentProductItem.setSerialNo(serialNo);
						equipmentProductItem.setNumberImport(1);
						equipmentProductItem.setBalance(1);
						equipmentProductItem.setStatus(1);
//						equipmentProductItem.setReference(reference);
//						equipmentProductItem.setGuaranteeDate(datepickerGuarantee);
//						equipmentProductItem.setOrderDate(new Date());
						equipmentProductItem.setCost(equipmentProduct.getCost());
						equipmentProductItem.setPriceIncTax(equipmentProduct.getSalePrice());
						equipmentProductItem.setSalePrice(equipmentProduct.getSalePrice());
						equipmentProductItem.setCreateDate(CURRENT_TIMESTAMP);
						equipmentProductItem.setCreatedBy("Migrate");
						equipmentProductItemService.save(equipmentProductItem);
					}
				}
			}
			// อัพ product ตัวแม่
			if(null != equipmentProduct){
				ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
				pro.setEquipmentProductService(equipmentProductService);
				pro.setMessageSource(messageSource);
				pro.autoUpdateStatusEquipmentProduct(equipmentProduct);
			}
		}
		
		sheetIndex = 4;
		intRow = 0;
		productName = "AC POWER SUPPLY PWR-150AC";
		equipmentProduct = equipmentProductService.getEquipmentProductByProductName(productName);
		if(null != equipmentProduct){
			for (Row rows : workbook.getSheetAt(sheetIndex)) {
				intRow++;
				if (intRow > 1) {
					String serialNo = "33013003394";
					if(intRow > 2){
						serialNo = "33013003390";
					}
					if(StringUtils.isNotEmpty(serialNo)){ // มี SN
						EquipmentProductItem equipmentProductItem = equipmentProductItemService.getEquipmentProductItemBySerialNo(serialNo);
						if(null != equipmentProductItem) continue; // เช็ค SN ต้องไม่ซ้ำ
						equipmentProductItem = new EquipmentProductItem();
						equipmentProductItem.setEquipmentProduct(equipmentProduct);
						equipmentProductItem.setSerialNo(serialNo);
						equipmentProductItem.setNumberImport(1);
						equipmentProductItem.setBalance(1);
						equipmentProductItem.setStatus(1);
//						equipmentProductItem.setReference(reference);
//						equipmentProductItem.setGuaranteeDate(datepickerGuarantee);
//						equipmentProductItem.setOrderDate(new Date());
						equipmentProductItem.setCost(equipmentProduct.getCost());
						equipmentProductItem.setPriceIncTax(equipmentProduct.getSalePrice());
						equipmentProductItem.setSalePrice(equipmentProduct.getSalePrice());
						equipmentProductItem.setCreateDate(CURRENT_TIMESTAMP);
						equipmentProductItem.setCreatedBy("Migrate");
						equipmentProductItemService.save(equipmentProductItem);
					}
				}
			}
			// อัพ product ตัวแม่
			if(null != equipmentProduct){
				ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
				pro.setEquipmentProductService(equipmentProductService);
				pro.setMessageSource(messageSource);
				pro.autoUpdateStatusEquipmentProduct(equipmentProduct);
			}
		}
		
		sheetIndex = 5;
		intRow = 0;
		productName = "Mikrotil hAP Lite RB941   4 LAN/WiFi";
		equipmentProduct = equipmentProductService.getEquipmentProductByProductName(productName);
		if(null != equipmentProduct){
			for (Row rows : workbook.getSheetAt(sheetIndex)) {
				intRow++;
				if (intRow > 1) {
					String serialNo = getDataString(workbook, sheetIndex, "D", intRow);
					if(StringUtils.isNotEmpty(serialNo)){ // มี SN
						EquipmentProductItem equipmentProductItem = equipmentProductItemService.getEquipmentProductItemBySerialNo(serialNo);
						if(null != equipmentProductItem) continue; // เช็ค SN ต้องไม่ซ้ำ
						equipmentProductItem = new EquipmentProductItem();
						equipmentProductItem.setEquipmentProduct(equipmentProduct);
						equipmentProductItem.setSerialNo(serialNo);
						equipmentProductItem.setNumberImport(1);
						equipmentProductItem.setBalance(1);
						equipmentProductItem.setStatus(1);
//						equipmentProductItem.setReference(reference);
//						equipmentProductItem.setGuaranteeDate(datepickerGuarantee);
//						equipmentProductItem.setOrderDate(new Date());
						equipmentProductItem.setCost(equipmentProduct.getCost());
						equipmentProductItem.setPriceIncTax(equipmentProduct.getSalePrice());
						equipmentProductItem.setSalePrice(equipmentProduct.getSalePrice());
						equipmentProductItem.setCreateDate(CURRENT_TIMESTAMP);
						equipmentProductItem.setCreatedBy("Migrate");
						equipmentProductItemService.save(equipmentProductItem);
					}
				}
			}
			// อัพ product ตัวแม่
			if(null != equipmentProduct){
				ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
				pro.setEquipmentProductService(equipmentProductService);
				pro.setMessageSource(messageSource);
				pro.autoUpdateStatusEquipmentProduct(equipmentProduct);
			}
		}
		
		sheetIndex = 6;
		intRow = 0;
		productName = "Mikrotil RB2011UIAS IN 10 LAN/WiFi";
		equipmentProduct = equipmentProductService.getEquipmentProductByProductName(productName);
		if(null != equipmentProduct){
			for (Row rows : workbook.getSheetAt(sheetIndex)) {
				intRow++;
				if (intRow > 1) {
					String serialNo = getDataString(workbook, sheetIndex, "C", intRow);
					if(StringUtils.isNotEmpty(serialNo)){ // มี SN
						EquipmentProductItem equipmentProductItem = equipmentProductItemService.getEquipmentProductItemBySerialNo(serialNo);
						if(null != equipmentProductItem) continue; // เช็ค SN ต้องไม่ซ้ำ
						equipmentProductItem = new EquipmentProductItem();
						equipmentProductItem.setEquipmentProduct(equipmentProduct);
						equipmentProductItem.setSerialNo(serialNo);
						equipmentProductItem.setNumberImport(1);
						equipmentProductItem.setBalance(1);
						equipmentProductItem.setStatus(1);
//						equipmentProductItem.setReference(reference);
//						equipmentProductItem.setGuaranteeDate(datepickerGuarantee);
//						equipmentProductItem.setOrderDate(new Date());
						equipmentProductItem.setCost(equipmentProduct.getCost());
						equipmentProductItem.setPriceIncTax(equipmentProduct.getSalePrice());
						equipmentProductItem.setSalePrice(equipmentProduct.getSalePrice());
						equipmentProductItem.setCreateDate(CURRENT_TIMESTAMP);
						equipmentProductItem.setCreatedBy("Migrate");
						equipmentProductItemService.save(equipmentProductItem);
					}
				}
			}
			// อัพ product ตัวแม่
			if(null != equipmentProduct){
				ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
				pro.setEquipmentProductService(equipmentProductService);
				pro.setMessageSource(messageSource);
				pro.autoUpdateStatusEquipmentProduct(equipmentProduct);
			}
		}
		
		sheetIndex = 7;
		intRow = 0;
		productName = "Mikrotil RB2011UIAS  RM ";
		equipmentProduct = equipmentProductService.getEquipmentProductByProductName(productName);
		if(null != equipmentProduct){
			for (Row rows : workbook.getSheetAt(sheetIndex)) {
				intRow++;
				if (intRow > 1) {
					String serialNo = getDataString(workbook, sheetIndex, "C", intRow);
					if(StringUtils.isNotEmpty(serialNo)){ // มี SN
						EquipmentProductItem equipmentProductItem = equipmentProductItemService.getEquipmentProductItemBySerialNo(serialNo);
						if(null != equipmentProductItem) continue; // เช็ค SN ต้องไม่ซ้ำ
						equipmentProductItem = new EquipmentProductItem();
						equipmentProductItem.setEquipmentProduct(equipmentProduct);
						equipmentProductItem.setSerialNo(serialNo);
						equipmentProductItem.setNumberImport(1);
						equipmentProductItem.setBalance(1);
						equipmentProductItem.setStatus(1);
//						equipmentProductItem.setReference(reference);
//						equipmentProductItem.setGuaranteeDate(datepickerGuarantee);
//						equipmentProductItem.setOrderDate(new Date());
						equipmentProductItem.setCost(equipmentProduct.getCost());
						equipmentProductItem.setPriceIncTax(equipmentProduct.getSalePrice());
						equipmentProductItem.setSalePrice(equipmentProduct.getSalePrice());
						equipmentProductItem.setCreateDate(CURRENT_TIMESTAMP);
						equipmentProductItem.setCreatedBy("Migrate");
						equipmentProductItemService.save(equipmentProductItem);
					}
				}
			}
			// อัพ product ตัวแม่
			if(null != equipmentProduct){
				ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
				pro.setEquipmentProductService(equipmentProductService);
				pro.setMessageSource(messageSource);
				pro.autoUpdateStatusEquipmentProduct(equipmentProduct);
			}
		}
		
		sheetIndex = 8;
		intRow = 0;
		productName = "BDCOM ONU WIFI P1704-4F";
		equipmentProduct = equipmentProductService.getEquipmentProductByProductName(productName);
		if(null != equipmentProduct){
			for (Row rows : workbook.getSheetAt(sheetIndex)) {
				intRow++;
				if (intRow > 1) {
					String serialNo = getDataString(workbook, sheetIndex, "B", intRow);
					if(StringUtils.isNotEmpty(serialNo)){ // มี SN
						EquipmentProductItem equipmentProductItem = equipmentProductItemService.getEquipmentProductItemBySerialNo(serialNo);
						if(null != equipmentProductItem) continue; // เช็ค SN ต้องไม่ซ้ำ
						equipmentProductItem = new EquipmentProductItem();
						equipmentProductItem.setEquipmentProduct(equipmentProduct);
						equipmentProductItem.setSerialNo(serialNo);
						equipmentProductItem.setNumberImport(1);
						equipmentProductItem.setBalance(1);
						equipmentProductItem.setStatus(1);
//						equipmentProductItem.setReference(reference);
//						equipmentProductItem.setGuaranteeDate(datepickerGuarantee);
//						equipmentProductItem.setOrderDate(new Date());
						equipmentProductItem.setCost(equipmentProduct.getCost());
						equipmentProductItem.setPriceIncTax(equipmentProduct.getSalePrice());
						equipmentProductItem.setSalePrice(equipmentProduct.getSalePrice());
						equipmentProductItem.setCreateDate(CURRENT_TIMESTAMP);
						equipmentProductItem.setCreatedBy("Migrate");
						equipmentProductItemService.save(equipmentProductItem);
					}
				}
			}
			// อัพ product ตัวแม่
			if(null != equipmentProduct){
				ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
				pro.setEquipmentProductService(equipmentProductService);
				pro.setMessageSource(messageSource);
				pro.autoUpdateStatusEquipmentProduct(equipmentProduct);
			}
		}
		
		sheetIndex = 9;
		intRow = 0;
		productName = "C DATA WIFI ONU";
		equipmentProduct = equipmentProductService.getEquipmentProductByProductName(productName);
		if(null != equipmentProduct){
			for (Row rows : workbook.getSheetAt(sheetIndex)) {
				intRow++;
				if (intRow > 2) {
					String serialNo = getDataString(workbook, sheetIndex, "C", intRow);
					if(StringUtils.isNotEmpty(serialNo)){ // มี SN
						EquipmentProductItem equipmentProductItem = equipmentProductItemService.getEquipmentProductItemBySerialNo(serialNo);
						if(null != equipmentProductItem) continue; // เช็ค SN ต้องไม่ซ้ำ
						equipmentProductItem = new EquipmentProductItem();
						equipmentProductItem.setEquipmentProduct(equipmentProduct);
						equipmentProductItem.setSerialNo(serialNo);
						equipmentProductItem.setNumberImport(1);
						equipmentProductItem.setBalance(1);
						equipmentProductItem.setStatus(1);
//						equipmentProductItem.setReference(reference);
//						equipmentProductItem.setGuaranteeDate(datepickerGuarantee);
//						equipmentProductItem.setOrderDate(new Date());
						equipmentProductItem.setCost(equipmentProduct.getCost());
						equipmentProductItem.setPriceIncTax(equipmentProduct.getSalePrice());
						equipmentProductItem.setSalePrice(equipmentProduct.getSalePrice());
						equipmentProductItem.setCreateDate(CURRENT_TIMESTAMP);
						equipmentProductItem.setCreatedBy("Migrate");
						equipmentProductItemService.save(equipmentProductItem);
					}
				}
			}
			// อัพ product ตัวแม่
			if(null != equipmentProduct){
				ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
				pro.setEquipmentProductService(equipmentProductService);
				pro.setMessageSource(messageSource);
				pro.autoUpdateStatusEquipmentProduct(equipmentProduct);
			}
		}
		
		sheetIndex = 10;
		intRow = 0;
		productName = "C DATA WIFI EOC SLAVE  ระบบRG6";
		equipmentProduct = equipmentProductService.getEquipmentProductByProductName(productName);
		if(null != equipmentProduct){
			for (Row rows : workbook.getSheetAt(sheetIndex)) {
				intRow++;
				if (intRow > 2) {
					String serialNo = getDataString(workbook, sheetIndex, "C", intRow);
					if(StringUtils.isNotEmpty(serialNo)){ // มี SN
						EquipmentProductItem equipmentProductItem = equipmentProductItemService.getEquipmentProductItemBySerialNo(serialNo);
						if(null != equipmentProductItem) continue; // เช็ค SN ต้องไม่ซ้ำ
						equipmentProductItem = new EquipmentProductItem();
						equipmentProductItem.setEquipmentProduct(equipmentProduct);
						equipmentProductItem.setSerialNo(serialNo);
						equipmentProductItem.setNumberImport(1);
						equipmentProductItem.setBalance(1);
						equipmentProductItem.setStatus(1);
//						equipmentProductItem.setReference(reference);
//						equipmentProductItem.setGuaranteeDate(datepickerGuarantee);
//						equipmentProductItem.setOrderDate(new Date());
						equipmentProductItem.setCost(equipmentProduct.getCost());
						equipmentProductItem.setPriceIncTax(equipmentProduct.getSalePrice());
						equipmentProductItem.setSalePrice(equipmentProduct.getSalePrice());
						equipmentProductItem.setCreateDate(CURRENT_TIMESTAMP);
						equipmentProductItem.setCreatedBy("Migrate");
						equipmentProductItemService.save(equipmentProductItem);
					}
				}
			}
			// อัพ product ตัวแม่
			if(null != equipmentProduct){
				ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
				pro.setEquipmentProductService(equipmentProductService);
				pro.setMessageSource(messageSource);
				pro.autoUpdateStatusEquipmentProduct(equipmentProduct);
			}
		}
		
		sheetIndex = 11;
		intRow = 0;
		productName = "C DATA CATV+WIFI ONU";
		equipmentProduct = equipmentProductService.getEquipmentProductByProductName(productName);
		if(null != equipmentProduct){
			for (Row rows : workbook.getSheetAt(sheetIndex)) {
				intRow++;
				if (intRow > 2) {
					String serialNo = getDataString(workbook, sheetIndex, "C", intRow);
					if(StringUtils.isNotEmpty(serialNo)){ // มี SN
						EquipmentProductItem equipmentProductItem = equipmentProductItemService.getEquipmentProductItemBySerialNo(serialNo);
						if(null != equipmentProductItem) continue; // เช็ค SN ต้องไม่ซ้ำ
						equipmentProductItem = new EquipmentProductItem();
						equipmentProductItem.setEquipmentProduct(equipmentProduct);
						equipmentProductItem.setSerialNo(serialNo);
						equipmentProductItem.setNumberImport(1);
						equipmentProductItem.setBalance(1);
						equipmentProductItem.setStatus(1);
//						equipmentProductItem.setReference(reference);
//						equipmentProductItem.setGuaranteeDate(datepickerGuarantee);
//						equipmentProductItem.setOrderDate(new Date());
						equipmentProductItem.setCost(equipmentProduct.getCost());
						equipmentProductItem.setPriceIncTax(equipmentProduct.getSalePrice());
						equipmentProductItem.setSalePrice(equipmentProduct.getSalePrice());
						equipmentProductItem.setCreateDate(CURRENT_TIMESTAMP);
						equipmentProductItem.setCreatedBy("Migrate");
						equipmentProductItemService.save(equipmentProductItem);
					}
				}
			}
			// อัพ product ตัวแม่
			if(null != equipmentProduct){
				ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
				pro.setEquipmentProductService(equipmentProductService);
				pro.setMessageSource(messageSource);
				pro.autoUpdateStatusEquipmentProduct(equipmentProduct);
			}
		}
		
		sheetIndex = 12;
		intRow = 0;
		productName = "กล่อง (INF)";
		equipmentProduct = equipmentProductService.getEquipmentProductByProductName(productName);
		if(null != equipmentProduct){
			for (Row rows : workbook.getSheetAt(sheetIndex)) {
				intRow++;
				if (intRow > 1) {
					String serialNo = getDataString(workbook, sheetIndex, "C", intRow);
					serialNo = serialNo + "_" +getDataString(workbook, sheetIndex, "B", intRow);
					if(StringUtils.isNotEmpty(serialNo)){ // มี SN
						EquipmentProductItem equipmentProductItem = equipmentProductItemService.getEquipmentProductItemBySerialNo(serialNo);
						if(null != equipmentProductItem) continue; // เช็ค SN ต้องไม่ซ้ำ
						equipmentProductItem = new EquipmentProductItem();
						equipmentProductItem.setEquipmentProduct(equipmentProduct);
						equipmentProductItem.setSerialNo(serialNo);
						equipmentProductItem.setNumberImport(1);
						equipmentProductItem.setBalance(1);
						equipmentProductItem.setStatus(1);
//						equipmentProductItem.setReference(reference);
//						equipmentProductItem.setGuaranteeDate(datepickerGuarantee);
//						equipmentProductItem.setOrderDate(new Date());
						equipmentProductItem.setCost(equipmentProduct.getCost());
						equipmentProductItem.setPriceIncTax(equipmentProduct.getSalePrice());
						equipmentProductItem.setSalePrice(equipmentProduct.getSalePrice());
						equipmentProductItem.setCreateDate(CURRENT_TIMESTAMP);
						equipmentProductItem.setCreatedBy("Migrate");
						equipmentProductItemService.save(equipmentProductItem);
					}
				}
			}
			// อัพ product ตัวแม่
			if(null != equipmentProduct){
				ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
				pro.setEquipmentProductService(equipmentProductService);
				pro.setMessageSource(messageSource);
				pro.autoUpdateStatusEquipmentProduct(equipmentProduct);
			}
		}
		
	}
	
	
	@SuppressWarnings("deprecation")
	private void processMigrateProduct1(Workbook workbook) throws Exception {
		int sheetIndex = 0;
		int intRow = 0;
		for (Row rows : workbook.getSheetAt(sheetIndex)) {
			intRow++;
			if (intRow > 1) {
				String productName = getDataString(workbook, 0, "B", intRow);
				String productCode = getDataString(workbook, 0, "C", intRow);
				double number = getDataDouble(workbook, 0, "D", intRow);
				
				productName = productName+" ("+productCode+")";
				
				EquipmentProduct equipmentProduct = equipmentProductService.getEquipmentProductByProductName(productName);
				
				if(null != equipmentProduct){
					EquipmentProductItem equipmentProductItem = new EquipmentProductItem();
					equipmentProductItem = new EquipmentProductItem();
					equipmentProductItem.setEquipmentProduct(equipmentProduct);
					equipmentProductItem.setSerialNo("");
					equipmentProductItem.setNumberImport((int)number);
					equipmentProductItem.setBalance((int)number);
					equipmentProductItem.setStatus(1);
					equipmentProductItem.setCost(equipmentProduct.getCost());
					equipmentProductItem.setPriceIncTax(equipmentProduct.getSalePrice());
					equipmentProductItem.setSalePrice(equipmentProduct.getSalePrice());
					equipmentProductItem.setCreateDate(CURRENT_TIMESTAMP);
					equipmentProductItem.setCreatedBy("Migrate");
					equipmentProductItemService.save(equipmentProductItem);
					
					// อัพ product ตัวแม่
					if(null != equipmentProduct){
						ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
						pro.setEquipmentProductService(equipmentProductService);
						pro.setMessageSource(messageSource);
						pro.autoUpdateStatusEquipmentProduct(equipmentProduct);
					}
				}
			}
		}
		
		sheetIndex = 1;
		intRow = 0;
		for (Row rows : workbook.getSheetAt(sheetIndex)) {
			intRow++;
			if (intRow > 1) {
				String productName = getDataString(workbook, sheetIndex, "B", intRow);
				double number = getDataDouble(workbook, sheetIndex, "C", intRow);
				
				EquipmentProduct equipmentProduct = equipmentProductService.getEquipmentProductByProductName(productName);
				
				if(null != equipmentProduct){
					EquipmentProductItem equipmentProductItem = new EquipmentProductItem();
					equipmentProductItem = new EquipmentProductItem();
					equipmentProductItem.setEquipmentProduct(equipmentProduct);
					equipmentProductItem.setSerialNo("");
					equipmentProductItem.setNumberImport((int)number);
					equipmentProductItem.setBalance((int)number);
					equipmentProductItem.setStatus(1);
					equipmentProductItem.setCost(equipmentProduct.getCost());
					equipmentProductItem.setPriceIncTax(equipmentProduct.getSalePrice());
					equipmentProductItem.setSalePrice(equipmentProduct.getSalePrice());
					equipmentProductItem.setCreateDate(CURRENT_TIMESTAMP);
					equipmentProductItem.setCreatedBy("Migrate");
					equipmentProductItemService.save(equipmentProductItem);
					
					// อัพ product ตัวแม่
					if(null != equipmentProduct){
						ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
						pro.setEquipmentProductService(equipmentProductService);
						pro.setMessageSource(messageSource);
						pro.autoUpdateStatusEquipmentProduct(equipmentProduct);
					}
				}
			}
		}
		
		sheetIndex = 2;
		intRow = 0;
		for (Row rows : workbook.getSheetAt(sheetIndex)) {
			if (intRow > 2) {
				String productName = getDataString(workbook, sheetIndex, "B", intRow);
				double number = getDataDouble(workbook, sheetIndex, "C", intRow);
				
				EquipmentProduct equipmentProduct = equipmentProductService.getEquipmentProductByProductName(productName);
				
				if(null != equipmentProduct){
					EquipmentProductItem equipmentProductItem = new EquipmentProductItem();
					equipmentProductItem = new EquipmentProductItem();
					equipmentProductItem.setEquipmentProduct(equipmentProduct);
					equipmentProductItem.setSerialNo("");
					equipmentProductItem.setNumberImport((int)number);
					equipmentProductItem.setBalance((int)number);
					equipmentProductItem.setStatus(1);
					equipmentProductItem.setCost(equipmentProduct.getCost());
					equipmentProductItem.setPriceIncTax(equipmentProduct.getSalePrice());
					equipmentProductItem.setSalePrice(equipmentProduct.getSalePrice());
					equipmentProductItem.setCreateDate(CURRENT_TIMESTAMP);
					equipmentProductItem.setCreatedBy("Migrate");
					equipmentProductItemService.save(equipmentProductItem);
					
					// อัพ product ตัวแม่
					if(null != equipmentProduct){
						ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
						pro.setEquipmentProductService(equipmentProductService);
						pro.setMessageSource(messageSource);
						pro.autoUpdateStatusEquipmentProduct(equipmentProduct);
					}
				}
			}
			intRow++;
		}
		
		sheetIndex = 3;
		intRow = 0;
		for (Row rows : workbook.getSheetAt(sheetIndex)) {
			intRow++;
			if (intRow > 1) {
				String productName = getDataString(workbook, sheetIndex, "B", intRow);
				double number = getDataDouble(workbook, sheetIndex, "C", intRow);
				
				EquipmentProduct equipmentProduct = equipmentProductService.getEquipmentProductByProductName(productName);
				
				if(null != equipmentProduct){
					EquipmentProductItem equipmentProductItem = new EquipmentProductItem();
					equipmentProductItem = new EquipmentProductItem();
					equipmentProductItem.setEquipmentProduct(equipmentProduct);
					equipmentProductItem.setSerialNo("");
					equipmentProductItem.setNumberImport((int)number);
					equipmentProductItem.setBalance((int)number);
					equipmentProductItem.setStatus(1);
					equipmentProductItem.setCost(equipmentProduct.getCost());
					equipmentProductItem.setPriceIncTax(equipmentProduct.getSalePrice());
					equipmentProductItem.setSalePrice(equipmentProduct.getSalePrice());
					equipmentProductItem.setCreateDate(CURRENT_TIMESTAMP);
					equipmentProductItem.setCreatedBy("Migrate");
					equipmentProductItemService.save(equipmentProductItem);
					
					// อัพ product ตัวแม่
					if(null != equipmentProduct){
						ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
						pro.setEquipmentProductService(equipmentProductService);
						pro.setMessageSource(messageSource);
						pro.autoUpdateStatusEquipmentProduct(equipmentProduct);
					}
				}
			}
		}
		
		sheetIndex = 4;
		intRow = 0;
		for (Row rows : workbook.getSheetAt(sheetIndex)) {
			intRow++;
			if (intRow > 1) {
				String productName = getDataString(workbook, sheetIndex, "B", intRow);
				double number = getDataDouble(workbook, sheetIndex, "C", intRow);
				
				EquipmentProduct equipmentProduct = equipmentProductService.getEquipmentProductByProductName(productName);
				
				if(null != equipmentProduct){
					EquipmentProductItem equipmentProductItem = new EquipmentProductItem();
					equipmentProductItem = new EquipmentProductItem();
					equipmentProductItem.setEquipmentProduct(equipmentProduct);
					equipmentProductItem.setSerialNo("");
					equipmentProductItem.setNumberImport((int)number);
					equipmentProductItem.setBalance((int)number);
					equipmentProductItem.setStatus(1);
					equipmentProductItem.setCost(equipmentProduct.getCost());
					equipmentProductItem.setPriceIncTax(equipmentProduct.getSalePrice());
					equipmentProductItem.setSalePrice(equipmentProduct.getSalePrice());
					equipmentProductItem.setCreateDate(CURRENT_TIMESTAMP);
					equipmentProductItem.setCreatedBy("Migrate");
					equipmentProductItemService.save(equipmentProductItem);
					
					// อัพ product ตัวแม่
					if(null != equipmentProduct){
						ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
						pro.setEquipmentProductService(equipmentProductService);
						pro.setMessageSource(messageSource);
						pro.autoUpdateStatusEquipmentProduct(equipmentProduct);
					}
				}
			}
		}
		
		sheetIndex = 5;
		intRow = 0;
		for (Row rows : workbook.getSheetAt(sheetIndex)) {
			intRow++;
			if (intRow > 1) {
				String productName = getDataString(workbook, sheetIndex, "B", intRow);
				double number = getDataDouble(workbook, sheetIndex, "C", intRow);
				
				EquipmentProduct equipmentProduct = equipmentProductService.getEquipmentProductByProductName(productName);
				
				if(null != equipmentProduct){
					EquipmentProductItem equipmentProductItem = new EquipmentProductItem();
					equipmentProductItem = new EquipmentProductItem();
					equipmentProductItem.setEquipmentProduct(equipmentProduct);
					equipmentProductItem.setSerialNo("");
					equipmentProductItem.setNumberImport((int)number);
					equipmentProductItem.setBalance((int)number);
					equipmentProductItem.setStatus(1);
					equipmentProductItem.setCost(equipmentProduct.getCost());
					equipmentProductItem.setPriceIncTax(equipmentProduct.getSalePrice());
					equipmentProductItem.setSalePrice(equipmentProduct.getSalePrice());
					equipmentProductItem.setCreateDate(CURRENT_TIMESTAMP);
					equipmentProductItem.setCreatedBy("Migrate");
					equipmentProductItemService.save(equipmentProductItem);
					
					// อัพ product ตัวแม่
					if(null != equipmentProduct){
						ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
						pro.setEquipmentProductService(equipmentProductService);
						pro.setMessageSource(messageSource);
						pro.autoUpdateStatusEquipmentProduct(equipmentProduct);
					}
				}
			}
		}
		
		
	}		
	
	@SuppressWarnings("deprecation")
	private void processMigrateProductCCTV1(Workbook workbook) throws Exception {
		int sheetIndex = 0;
		int intRow = 0;
		// ข้อมูลสินค้าและอุปกรณ์
		Long stockId = 2L;
		String equipmentProductCategory = "อุปกรณ์ในงานระบบวงจรปิด";
		
		EquipmentProductCategory epc = equipmentProductCategoryService
				.getEquipmentProductCategoryByEquipmentProductCategoryName(equipmentProductCategory);

	    
		for (Row rows : workbook.getSheetAt(sheetIndex)) {
			intRow++;
			if (intRow > 2) {
				String unitName = "ไม่ได้ระบุ";
				String financialType = "";
				String productName = getDataString(workbook, sheetIndex, "C", intRow);
				String productCode = getDataString(workbook, sheetIndex, "B", intRow);
				double minumun = 0;
				String supplier = getDataString(workbook, sheetIndex, "G", intRow)+" "+getDataString(workbook, sheetIndex, "F", intRow);
				double productCostDouble = getDataDouble(workbook, sheetIndex, "H", intRow);
				double priceIncTaxDouble = getDataDouble(workbook, sheetIndex, "I", intRow);
				double productSalePriceDouble = getDataDouble(workbook, sheetIndex, "J", intRow);
				String checkSN = getDataString(workbook, sheetIndex, "K", intRow);
				double balanceDouble = getDataDouble(workbook, sheetIndex, "D", intRow);
				
				logger.info("productName : "+productName);
				logger.info("productCode : "+productCode);
				logger.info("productCostDouble : "+productCostDouble);
				logger.info("priceIncTaxDouble : "+priceIncTaxDouble);
				logger.info("productSalePriceDouble : "+productSalePriceDouble);
				
				if(StringUtils.isEmpty(productName)) continue;
				
				productName = productName+" ("+productCode+")";
				
				EquipmentProduct equipmentProduct = equipmentProductService.getEquipmentProductByProductNameAndStock(productName, stockId);
				
				if(null != equipmentProduct) continue;
				
				equipmentProduct = new EquipmentProduct();
				equipmentProduct.setProductCode(productCode);
				equipmentProduct.setProductName(productName);
				
				float productCost = (float)productCostDouble;
				float priceIncTax = (float)priceIncTaxDouble;
				float productSalePrice = (float)productSalePriceDouble;
				
				equipmentProduct.setCost(productCost);
				equipmentProduct.setPriceIncTax(priceIncTax);
				equipmentProduct.setSalePrice(productSalePrice);
				
				int balance = (int)balanceDouble;
				equipmentProduct.setBalance(balance);
				equipmentProduct.setStockAmount(balance);
				
				if(null == epc) continue;
				equipmentProduct.setEquipmentProductCategory(epc);
				equipmentProduct.setSupplier(supplier);
				
				Stock stock = stockService.getStockById(stockId); // คลังสินค้ามหาชัยบรอดแบนด์ (บริษัท มหาชัย บรอดแบนด์)
				equipmentProduct.setStock(stock);
				
				Unit unit = unitService.getUnitByUnitName(unitName);
				if(null == unit) continue;
				equipmentProduct.setUnit(unit);
				
				financialType = "C"; // ต้นทุน
				
				equipmentProduct.setFinancial_type(financialType);
				long min = (long)minumun;
				if(0 < min){
					equipmentProduct.setMinimum(true);
					equipmentProduct.setMinimumNumber(min);
				}else{
					equipmentProduct.setMinimum(false);
					equipmentProduct.setMinimumNumber(ZERO_VALUE);
				}
				equipmentProduct.setDeleted(false);
				equipmentProduct.setCreateDate(CURRENT_TIMESTAMP);
				equipmentProduct.setCreatedBy("MigrateCCTV");
				Long equipmentProductId = productService.saveMasterProduct(equipmentProduct);
				
				if("N".equals(checkSN)){
					EquipmentProductItem equipmentProductItem = new EquipmentProductItem();
					equipmentProductItem.setEquipmentProduct(equipmentProduct);
					equipmentProductItem.setSerialNo("");
					equipmentProductItem.setNumberImport(balance);
					equipmentProductItem.setBalance(balance);
					equipmentProductItem.setStatus(1);
//					equipmentProductItem.setReference(reference);
//					equipmentProductItem.setGuaranteeDate(datepickerGuarantee);
//					equipmentProductItem.setOrderDate(new Date());
					equipmentProductItem.setCost(equipmentProduct.getCost());
					equipmentProductItem.setPriceIncTax(equipmentProduct.getSalePrice());
					equipmentProductItem.setSalePrice(equipmentProduct.getSalePrice());
					equipmentProductItem.setCreateDate(CURRENT_TIMESTAMP);
					equipmentProductItem.setCreatedBy("MigrateCCTV");
					equipmentProductItemService.save(equipmentProductItem);

				}
				
			}

		}
		
		for (int i = 1; i < workbook.getNumberOfSheets(); i++) {
			sheetIndex = i;
			intRow = 0;
			String productCode = getDataString(workbook, sheetIndex, "A", 1);
			EquipmentProduct equipmentProduct = equipmentProductService.getEquipmentProductByProductCodeAndStock(productCode, stockId);
			if(null != equipmentProduct){
				for (Row rows : workbook.getSheetAt(sheetIndex)) {
					intRow++;
					if (intRow > 1) {
						String serialNo = getDataString(workbook, sheetIndex, "B", intRow);
						if(StringUtils.isNotEmpty(serialNo)){ // มี SN
							String[] serialNoArray = serialNo.split(":");
							serialNo = serialNoArray.length>1?serialNoArray[1]:serialNo;
							EquipmentProductItem equipmentProductItem = equipmentProductItemService.getEquipmentProductItemBySerialNo(serialNo);
							if(null != equipmentProductItem) continue; // เช็ค SN ต้องไม่ซ้ำ
							equipmentProductItem = new EquipmentProductItem();
							equipmentProductItem.setEquipmentProduct(equipmentProduct);
							equipmentProductItem.setSerialNo(serialNo);
							equipmentProductItem.setNumberImport(1);
							equipmentProductItem.setBalance(1);
							equipmentProductItem.setStatus(1);
	//						equipmentProductItem.setReference(reference);
	//						equipmentProductItem.setGuaranteeDate(datepickerGuarantee);
	//						equipmentProductItem.setOrderDate(new Date());
							equipmentProductItem.setCost(equipmentProduct.getCost());
							equipmentProductItem.setPriceIncTax(equipmentProduct.getSalePrice());
							equipmentProductItem.setSalePrice(equipmentProduct.getSalePrice());
							equipmentProductItem.setCreateDate(CURRENT_TIMESTAMP);
							equipmentProductItem.setCreatedBy("MigrateCCTV");
							equipmentProductItemService.save(equipmentProductItem);
						}
					}
				}
				// อัพ product ตัวแม่
				if(null != equipmentProduct){
					ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
					pro.setEquipmentProductService(equipmentProductService);
					pro.setMessageSource(messageSource);
					pro.autoUpdateStatusEquipmentProduct(equipmentProduct);
				}
			}
		}
		
		logger.info("================== END EquipmentProduct ====================");
		
	}
	
	@SuppressWarnings("deprecation")
	private void processMigrateProductCCTV3(Workbook workbook) throws Exception {
		int sheetIndex = 0;
		int intRow = 0;
		// ข้อมูลสินค้าและอุปกรณ์
		Long stockId = 3L;
		String equipmentProductCategory = "อุปกรณ์ในงานระบบวงจรปิด";
		
		EquipmentProductCategory epc = equipmentProductCategoryService
				.getEquipmentProductCategoryByEquipmentProductCategoryName(equipmentProductCategory);

	    
		for (Row rows : workbook.getSheetAt(sheetIndex)) {
			intRow++;
			if (intRow > 2) {
				String unitName = "ไม่ได้ระบุ";
				String financialType = "";
				String productName = getDataString(workbook, sheetIndex, "C", intRow);
				String productCode = getDataString(workbook, sheetIndex, "B", intRow);
				double minumun = 0;
				String supplier = getDataString(workbook, sheetIndex, "G", intRow)+" "+getDataString(workbook, sheetIndex, "F", intRow);
				double productCostDouble = getDataDouble(workbook, sheetIndex, "H", intRow);
				double priceIncTaxDouble = getDataDouble(workbook, sheetIndex, "I", intRow);
				double productSalePriceDouble = getDataDouble(workbook, sheetIndex, "J", intRow);
				String checkSN = getDataString(workbook, sheetIndex, "K", intRow);
				double balanceDouble = getDataDouble(workbook, sheetIndex, "D", intRow);
				
				logger.info("productName : "+productName);
				logger.info("productCode : "+productCode);
				logger.info("productCostDouble : "+productCostDouble);
				logger.info("priceIncTaxDouble : "+priceIncTaxDouble);
				logger.info("productSalePriceDouble : "+productSalePriceDouble);
				
				if(StringUtils.isEmpty(productName)) continue;
				
				productName = productName+" ("+productCode+")";
				
				EquipmentProduct equipmentProduct = equipmentProductService.getEquipmentProductByProductNameAndStock(productName, stockId);
				
				if(null != equipmentProduct) continue;
				
				equipmentProduct = new EquipmentProduct();
				equipmentProduct.setProductCode(productCode);
				equipmentProduct.setProductName(productName);
				
				float productCost = (float)productCostDouble;
				float priceIncTax = (float)priceIncTaxDouble;
				float productSalePrice = (float)productSalePriceDouble;
				
				equipmentProduct.setCost(productCost);
				equipmentProduct.setPriceIncTax(priceIncTax);
				equipmentProduct.setSalePrice(productSalePrice);
				
				int balance = (int)balanceDouble;
				equipmentProduct.setBalance(balance);
				equipmentProduct.setStockAmount(balance);
				
				if(null == epc) continue;
				equipmentProduct.setEquipmentProductCategory(epc);
				equipmentProduct.setSupplier(supplier);
				
				Stock stock = stockService.getStockById(stockId); // คลังสินค้าคุณวีระพงษ์ (บริษัท มหาชัย เคเบิลทีวี) (บริษัท เคเบิล จำกัด)
				equipmentProduct.setStock(stock);
				
				Unit unit = unitService.getUnitByUnitName(unitName);
				if(null == unit) continue;
				equipmentProduct.setUnit(unit);
				
				financialType = "C"; // ต้นทุน
				
				equipmentProduct.setFinancial_type(financialType);
				long min = (long)minumun;
				if(0 < min){
					equipmentProduct.setMinimum(true);
					equipmentProduct.setMinimumNumber(min);
				}else{
					equipmentProduct.setMinimum(false);
					equipmentProduct.setMinimumNumber(ZERO_VALUE);
				}
				equipmentProduct.setDeleted(false);
				equipmentProduct.setCreateDate(CURRENT_TIMESTAMP);
				equipmentProduct.setCreatedBy("MigrateCCTV");
				Long equipmentProductId = productService.saveMasterProduct(equipmentProduct);
				
				if("N".equals(checkSN)){
					EquipmentProductItem equipmentProductItem = new EquipmentProductItem();
					equipmentProductItem.setEquipmentProduct(equipmentProduct);
					equipmentProductItem.setSerialNo("");
					equipmentProductItem.setNumberImport(balance);
					equipmentProductItem.setBalance(balance);
					equipmentProductItem.setStatus(1);
//					equipmentProductItem.setReference(reference);
//					equipmentProductItem.setGuaranteeDate(datepickerGuarantee);
//					equipmentProductItem.setOrderDate(new Date());
					equipmentProductItem.setCost(equipmentProduct.getCost());
					equipmentProductItem.setPriceIncTax(equipmentProduct.getSalePrice());
					equipmentProductItem.setSalePrice(equipmentProduct.getSalePrice());
					equipmentProductItem.setCreateDate(CURRENT_TIMESTAMP);
					equipmentProductItem.setCreatedBy("MigrateCCTV");
					equipmentProductItemService.save(equipmentProductItem);

				}
				
			}

		}
		
		for (int i = 1; i < workbook.getNumberOfSheets(); i++) {
			sheetIndex = i;
			intRow = 0;
			String productCode = getDataString(workbook, sheetIndex, "A", 1);
			EquipmentProduct equipmentProduct = equipmentProductService.getEquipmentProductByProductCodeAndStock(productCode, stockId);
			if(null != equipmentProduct){
				for (Row rows : workbook.getSheetAt(sheetIndex)) {
					intRow++;
					if (intRow > 1) {
						String serialNo = getDataString(workbook, sheetIndex, "B", intRow);
						if(StringUtils.isNotEmpty(serialNo)){ // มี SN
							String[] serialNoArray = serialNo.split(":");
							serialNo = serialNoArray.length>1?serialNoArray[1]:serialNo;
							EquipmentProductItem equipmentProductItem = equipmentProductItemService.getEquipmentProductItemBySerialNo(serialNo);
							if(null != equipmentProductItem) continue; // เช็ค SN ต้องไม่ซ้ำ
							equipmentProductItem = new EquipmentProductItem();
							equipmentProductItem.setEquipmentProduct(equipmentProduct);
							equipmentProductItem.setSerialNo(serialNo);
							equipmentProductItem.setNumberImport(1);
							equipmentProductItem.setBalance(1);
							equipmentProductItem.setStatus(1);
	//						equipmentProductItem.setReference(reference);
	//						equipmentProductItem.setGuaranteeDate(datepickerGuarantee);
	//						equipmentProductItem.setOrderDate(new Date());
							equipmentProductItem.setCost(equipmentProduct.getCost());
							equipmentProductItem.setPriceIncTax(equipmentProduct.getSalePrice());
							equipmentProductItem.setSalePrice(equipmentProduct.getSalePrice());
							equipmentProductItem.setCreateDate(CURRENT_TIMESTAMP);
							equipmentProductItem.setCreatedBy("MigrateCCTV");
							equipmentProductItemService.save(equipmentProductItem);
						}
					}
				}
				// อัพ product ตัวแม่
				if(null != equipmentProduct){
					ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
					pro.setEquipmentProductService(equipmentProductService);
					pro.setMessageSource(messageSource);
					pro.autoUpdateStatusEquipmentProduct(equipmentProduct);
				}
			}
		}
		
		logger.info("================== END EquipmentProduct ====================");
		
	}
	
	@SuppressWarnings("deprecation")
	private void processMigrateProductCCTV2(Workbook workbook) throws Exception {
		int sheetIndex = 0;
		int intRow = 0;
		// ข้อมูลสินค้าและอุปกรณ์
		Long stockId = 1L;
		String equipmentProductCategory = "อุปกรณ์ในงานระบบวงจรปิด";
		
		EquipmentProductCategory epc = equipmentProductCategoryService
				.getEquipmentProductCategoryByEquipmentProductCategoryName(equipmentProductCategory);

	    
		for (Row rows : workbook.getSheetAt(sheetIndex)) {
			intRow++;
			if (intRow > 2) {
				String unitName = "ไม่ได้ระบุ";
				String financialType = "";
				String productName = getDataString(workbook, sheetIndex, "C", intRow);
				String productCode = getDataString(workbook, sheetIndex, "B", intRow);
				double minumun = 0;
				String supplier = getDataString(workbook, sheetIndex, "G", intRow)+" "+getDataString(workbook, sheetIndex, "F", intRow);
				double productCostDouble = getDataDouble(workbook, sheetIndex, "H", intRow);
				double priceIncTaxDouble = getDataDouble(workbook, sheetIndex, "I", intRow);
				double productSalePriceDouble = getDataDouble(workbook, sheetIndex, "J", intRow);
				String checkSN = getDataString(workbook, sheetIndex, "K", intRow);
				double balanceDouble = getDataDouble(workbook, sheetIndex, "D", intRow);
				
				logger.info("productName : "+productName);
				logger.info("productCode : "+productCode);
				logger.info("productCostDouble : "+productCostDouble);
				logger.info("priceIncTaxDouble : "+priceIncTaxDouble);
				logger.info("productSalePriceDouble : "+productSalePriceDouble);
				
				if(StringUtils.isEmpty(productName)) continue;
				
				productName = productName+" ("+productCode+")";
				
				EquipmentProduct equipmentProduct = equipmentProductService.getEquipmentProductByProductNameAndStock(productName, stockId);
				
				if(null != equipmentProduct) continue;
				
				equipmentProduct = new EquipmentProduct();
				equipmentProduct.setProductCode(productCode);
				equipmentProduct.setProductName(productName);
				
				float productCost = (float)productCostDouble;
				float priceIncTax = (float)priceIncTaxDouble;
				float productSalePrice = (float)productSalePriceDouble;
				
				equipmentProduct.setCost(productCost);
				equipmentProduct.setPriceIncTax(priceIncTax);
				equipmentProduct.setSalePrice(productSalePrice);
				
				int balance = (int)balanceDouble;
				equipmentProduct.setBalance(balance);
				equipmentProduct.setStockAmount(balance);
				
				if(null == epc) continue;
				equipmentProduct.setEquipmentProductCategory(epc);
				equipmentProduct.setSupplier(supplier);
				
				Stock stock = stockService.getStockById(stockId); // คลังสินค้ามหาชัยเคเบิลทีวี (บริษัท มหาชัย เคเบิลทีวี)
				equipmentProduct.setStock(stock);
				
				Unit unit = unitService.getUnitByUnitName(unitName);
				if(null == unit) continue;
				equipmentProduct.setUnit(unit);
				
				financialType = "C"; // ต้นทุน
				
				equipmentProduct.setFinancial_type(financialType);
				long min = (long)minumun;
				if(0 < min){
					equipmentProduct.setMinimum(true);
					equipmentProduct.setMinimumNumber(min);
				}else{
					equipmentProduct.setMinimum(false);
					equipmentProduct.setMinimumNumber(ZERO_VALUE);
				}
				equipmentProduct.setDeleted(false);
				equipmentProduct.setCreateDate(CURRENT_TIMESTAMP);
				equipmentProduct.setCreatedBy("MigrateCCTV");
				Long equipmentProductId = productService.saveMasterProduct(equipmentProduct);
				
				if("N".equals(checkSN)){
					EquipmentProductItem equipmentProductItem = new EquipmentProductItem();
					equipmentProductItem.setEquipmentProduct(equipmentProduct);
					equipmentProductItem.setSerialNo("");
					equipmentProductItem.setNumberImport(balance);
					equipmentProductItem.setBalance(balance);
					equipmentProductItem.setStatus(1);
//					equipmentProductItem.setReference(reference);
//					equipmentProductItem.setGuaranteeDate(datepickerGuarantee);
//					equipmentProductItem.setOrderDate(new Date());
					equipmentProductItem.setCost(equipmentProduct.getCost());
					equipmentProductItem.setPriceIncTax(equipmentProduct.getSalePrice());
					equipmentProductItem.setSalePrice(equipmentProduct.getSalePrice());
					equipmentProductItem.setCreateDate(CURRENT_TIMESTAMP);
					equipmentProductItem.setCreatedBy("MigrateCCTV");
					equipmentProductItemService.save(equipmentProductItem);
					
				}
				
			}

		}
		
		sheetIndex = 1;
		intRow = 0;
		String productCode = getDataString(workbook, sheetIndex, "A", 1);
		EquipmentProduct equipmentProduct = equipmentProductService.getEquipmentProductByProductCodeAndStock(productCode, stockId);
		if(null != equipmentProduct){
			for (Row rows : workbook.getSheetAt(sheetIndex)) {
				intRow++;
				if (intRow > 1) {
					String serialNo = getDataString(workbook, sheetIndex, "B", intRow);
					if(StringUtils.isNotEmpty(serialNo)){ // มี SN
						String[] serialNoArray = serialNo.split(":");
						serialNo = serialNoArray.length>1?serialNoArray[1]:serialNo;
						EquipmentProductItem equipmentProductItem = equipmentProductItemService.getEquipmentProductItemBySerialNo(serialNo);
						if(null != equipmentProductItem) continue; // เช็ค SN ต้องไม่ซ้ำ
						equipmentProductItem = new EquipmentProductItem();
						equipmentProductItem.setEquipmentProduct(equipmentProduct);
						equipmentProductItem.setSerialNo(serialNo);
						equipmentProductItem.setNumberImport(1);
						equipmentProductItem.setBalance(1);
						equipmentProductItem.setStatus(1);
//						equipmentProductItem.setReference(reference);
//						equipmentProductItem.setGuaranteeDate(datepickerGuarantee);
//						equipmentProductItem.setOrderDate(new Date());
						equipmentProductItem.setCost(equipmentProduct.getCost());
						equipmentProductItem.setPriceIncTax(equipmentProduct.getSalePrice());
						equipmentProductItem.setSalePrice(equipmentProduct.getSalePrice());
						equipmentProductItem.setCreateDate(CURRENT_TIMESTAMP);
						equipmentProductItem.setCreatedBy("MigrateCCTV");
						equipmentProductItemService.save(equipmentProductItem);
					}
				}
			}
			// อัพ product ตัวแม่
			if(null != equipmentProduct){
				ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
				pro.setEquipmentProductService(equipmentProductService);
				pro.setMessageSource(messageSource);
				pro.autoUpdateStatusEquipmentProduct(equipmentProduct);
			}
		}
		
		sheetIndex = 2;
		intRow = 0;
		productCode = getDataString(workbook, sheetIndex, "A", 1);
		equipmentProduct = equipmentProductService.getEquipmentProductByProductCodeAndStock(productCode, stockId);
		if(null != equipmentProduct){
			for (Row rows : workbook.getSheetAt(sheetIndex)) {
				intRow++;
				if (intRow > 1) {
					String serialNo = getDataString(workbook, sheetIndex, "B", intRow);
					if(StringUtils.isNotEmpty(serialNo)){ // มี SN
						String[] serialNoArray = serialNo.split(":");
						serialNo = serialNoArray.length>1?serialNoArray[1]:serialNo;
						EquipmentProductItem equipmentProductItem = equipmentProductItemService.getEquipmentProductItemBySerialNo(serialNo);
						if(null != equipmentProductItem) continue; // เช็ค SN ต้องไม่ซ้ำ
						equipmentProductItem = new EquipmentProductItem();
						equipmentProductItem.setEquipmentProduct(equipmentProduct);
						equipmentProductItem.setSerialNo(serialNo);
						equipmentProductItem.setNumberImport(1);
						equipmentProductItem.setBalance(1);
						equipmentProductItem.setStatus(1);
//						equipmentProductItem.setReference(reference);
//						equipmentProductItem.setGuaranteeDate(datepickerGuarantee);
//						equipmentProductItem.setOrderDate(new Date());
						equipmentProductItem.setCost(equipmentProduct.getCost());
						equipmentProductItem.setPriceIncTax(equipmentProduct.getSalePrice());
						equipmentProductItem.setSalePrice(equipmentProduct.getSalePrice());
						equipmentProductItem.setCreateDate(CURRENT_TIMESTAMP);
						equipmentProductItem.setCreatedBy("MigrateCCTV");
						equipmentProductItemService.save(equipmentProductItem);
					}
				}
			}
			// อัพ product ตัวแม่
			if(null != equipmentProduct){
				ProductOrderEquipmentProductController pro = new ProductOrderEquipmentProductController();
				pro.setEquipmentProductService(equipmentProductService);
				pro.setMessageSource(messageSource);
				pro.autoUpdateStatusEquipmentProduct(equipmentProduct);
			}
		}
		
		logger.info("================== END EquipmentProduct ====================");
		
	}
	
	@SuppressWarnings("deprecation")
	private void processMigratePoint(Workbook workbook) throws Exception {
		int sheetIndex = 1;
		int intRow = 0;

		for (Row rows : workbook.getSheetAt(sheetIndex)) {
			intRow++;
				String customerCode = getDataString(workbook, sheetIndex, "A", intRow);
				double quantityDigital = getDataDouble(workbook, sheetIndex, "BC", intRow);
				double quantityAnalog = getDataDouble(workbook, sheetIndex, "BG", intRow);
				
				logger.info("customerCode : "+customerCode);
				logger.info("quantityDigital : "+quantityDigital);
				logger.info("quantityAnalog : "+quantityAnalog);
				
				if(StringUtils.isEmpty(customerCode)) continue;

				Customer customer = customerService.getCustomerByCustCode(customerCode);

				if(null == customer) continue;
				
				List<ServiceApplication> serviceApplicationList = customer.getServiceApplications();
				if(null != serviceApplicationList && serviceApplicationList.size() > 0){
					ServiceApplication serviceApplication = serviceApplicationList.get(0);
					List<Worksheet> worksheetList = serviceApplication.getWorksheets();
					if(null != worksheetList && worksheetList.size() > 0){
						boolean isAnalog = false;
						for(Worksheet worksheet:worksheetList){	
							WorksheetSetup worksheetSetup = worksheet.getWorksheetSetup();
							if(null != worksheetSetup){
								List<ProductItem> productItemList = worksheet.getProductItems();
								if(null != productItemList && productItemList.size() > 0){
									for(ProductItem productItem:productItemList){
										ServiceProduct serviceProduct = productItem.getServiceProduct();
										if(null != serviceProduct && "00002".equals(serviceProduct.getProductCode())){
											productItem.setQuantity((int)quantityDigital); // 00002 : ค่าติดตั้งจุด Digital
											productItemService.update(productItem);
										}
										if(null != serviceProduct && "00003".equals(serviceProduct.getProductCode())){
											isAnalog = true;
										}
									}
								}
								if(!isAnalog){
									ProductItem productItem = new ProductItem();
									productItem.setServiceProduct(serviceProductService.getSerivceProductByCode("00003")); // ค่าติดตั้งจุด Analog
									productItem.setServiceApplication(serviceApplication);
									productItem.setWorkSheet(worksheet);
									productItem.setQuantity((int)quantityAnalog);
									productItem.setPrice(0);
									productItem.setAmount(0);
									productItem.setProductTypeMatch("O");
									productItem.setProductType("S");
									productItem.setDeposit(0f);
									productItem.setCreateDate(CURRENT_TIMESTAMP);
									productItem.setCreatedBy("Migrate");
									productItem.setFree(Boolean.FALSE);
									productItem.setLend(Boolean.FALSE);
									productItem.setDeleted(Boolean.FALSE);
									productItemService.save(productItem);
								}
							}
						}
					}
				}
				
		}
		
		logger.info("================== END processMigratePoint ====================");
		
	}
	
	@SuppressWarnings("deprecation")
	private void processMigrateProduct(Workbook workbook) throws Exception {
		int sheetIndex = workbook.getNumberOfSheets();
		int intRow = 0;
		// ข้อมูลสินค้าและอุปกรณ์
		
		String equipmentProductCategory = "อุปกรณ์ในงานระบบเคเบิล";
		
		EquipmentProductCategory epc = equipmentProductCategoryService
				.getEquipmentProductCategoryByEquipmentProductCategoryName(equipmentProductCategory);
		
//		Sheet sheet = workbook.getSheetAt(0);
//	    FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
//		
//	    CellReference cellReference = new CellReference("I3"); //pass the cell which contains formula
//	    Row row = sheet.getRow(cellReference.getRow());
//	    Cell cell = row.getCell(cellReference.getCol());
//
//	    CellValue cellValue = evaluator.evaluate(cell);
//
//	    switch (cellValue.getCellType()) {
//	        case Cell.CELL_TYPE_BOOLEAN:
//	            System.out.println(cellValue.getBooleanValue());
//	            break;
//	        case Cell.CELL_TYPE_NUMERIC:
//	            System.out.println(cellValue.getNumberValue());
//	            break;
//	        case Cell.CELL_TYPE_STRING:
//	            System.out.println(cellValue.getStringValue());
//	            break;
//	        case Cell.CELL_TYPE_BLANK:
//	            break;
//	        case Cell.CELL_TYPE_ERROR:
//	            break;
//
//	        // CELL_TYPE_FORMULA will never happen
//	        case Cell.CELL_TYPE_FORMULA:
//	        	System.out.println(cellValue.getStringValue());
//	            break;
//	    }
	    
		for (Row rows : workbook.getSheetAt(0)) {
			if (intRow > 2) {

				String unitName = "-";
				String financialType = "";
				String productName = getDataString(workbook, 0, "B", intRow);
				String productCode = getDataString(workbook, 0, "C", intRow);
				double minumun = getDataDouble(workbook, 0, "E", intRow);
				String supplier = getDataString(workbook, 0, "G", intRow)+" "+getDataString(workbook, 0, "F", intRow);
				double productCostDouble = getDataDouble(workbook, 0, "H", intRow);
				double priceIncTaxDouble = getDataDouble(workbook, 0, "I", intRow);
				double productSalePriceDouble = getDataDouble(workbook, 0, "J", intRow);
				
				logger.info("productName : "+productName);
				logger.info("productCode : "+productCode);
				logger.info("productCostDouble : "+productCostDouble);
				logger.info("priceIncTaxDouble : "+priceIncTaxDouble);
				logger.info("productSalePriceDouble : "+productSalePriceDouble);
				
				if(StringUtils.isEmpty(productName)) continue;
				
				productName = productName+" ("+productCode+")";
				
				EquipmentProduct equipmentProduct = equipmentProductService.getEquipmentProductByProductName(productName);
				
				if(null != equipmentProduct) continue;
				
				equipmentProduct = new EquipmentProduct();
				equipmentProduct.setProductCode(productCode);
				equipmentProduct.setProductName(productName);
				
				float productCost = (float)productCostDouble;
				float priceIncTax = (float)priceIncTaxDouble;
				float productSalePrice = (float)productSalePriceDouble;
				
				equipmentProduct.setCost(productCost);
				equipmentProduct.setPriceIncTax(priceIncTax);
				equipmentProduct.setSalePrice(productSalePrice);

				if(null == epc) continue;
				equipmentProduct.setEquipmentProductCategory(epc);
				equipmentProduct.setSupplier(supplier);
				
				Stock stock = stockService.getStockById(1L);
				equipmentProduct.setStock(stock);
				
				Unit unit = unitService.getUnitByUnitName(unitName);
				if(null == unit) continue;
				equipmentProduct.setUnit(unit);
				
				financialType = "C"; // ต้นทุน
				
				equipmentProduct.setFinancial_type(financialType);
				long min = (long)minumun;
				if(0 < min){
					equipmentProduct.setMinimum(true);
					equipmentProduct.setMinimumNumber(min);
				}else{
					equipmentProduct.setMinimum(false);
					equipmentProduct.setMinimumNumber(ZERO_VALUE);
				}
				equipmentProduct.setDeleted(false);
				equipmentProduct.setCreateDate(CURRENT_TIMESTAMP);
				equipmentProduct.setCreatedBy("Migrate");
				Long equipmentProductId = productService.saveMasterProduct(equipmentProduct);
				
			}
			intRow++;
		}
		
		intRow = 0;
		for (Row rows : workbook.getSheetAt(1)) {
//			logger.info("rows : "+rows.getRowNum());
			if (intRow++ > 1) {

				String unitName = "-";
				String financialType = "";
				String productName = getDataString(workbook, 1, "B", intRow);
				String productCode = "";
				double minumun = getDataDouble(workbook, 1, "E", intRow);
				String supplier = getDataString(workbook, 1, "G", intRow)+" "+getDataString(workbook, 1, "F", intRow);
				double productCostDouble = getDataDouble(workbook, 1, "H", intRow);
				double priceIncTaxDouble = getDataDouble(workbook, 1, "I", intRow);
				double productSalePriceDouble = getDataDouble(workbook, 1, "J", intRow);
				
				logger.info("productName1 : "+productName);
				logger.info("productCode1 : "+productCode);
				logger.info("productCostDouble1 : "+productCostDouble);
				logger.info("priceIncTaxDouble1 : "+priceIncTaxDouble);
				logger.info("productSalePriceDouble1 : "+productSalePriceDouble);
				
				if(StringUtils.isEmpty(productName)) continue;
				
				EquipmentProduct equipmentProduct = equipmentProductService.getEquipmentProductByProductName(productName);
				
				if(null != equipmentProduct) continue;
				
				equipmentProduct = new EquipmentProduct();
				equipmentProduct.setProductCode(productCode);
				equipmentProduct.setProductName(productName);
				
				float productCost = (float)productCostDouble;
				float priceIncTax = (float)priceIncTaxDouble;
				float productSalePrice = (float)productSalePriceDouble;
				
				equipmentProduct.setCost(productCost);
				equipmentProduct.setPriceIncTax(priceIncTax);
				equipmentProduct.setSalePrice(productSalePrice);

				if(null == epc) continue;
				equipmentProduct.setEquipmentProductCategory(epc);
				equipmentProduct.setSupplier(supplier);
				
				Stock stock = stockService.getStockById(1L);
				equipmentProduct.setStock(stock);
				
				Unit unit = unitService.getUnitByUnitName(unitName);
				if(null == unit) continue;
				equipmentProduct.setUnit(unit);
				
				financialType = "C"; // ต้นทุน
				
				equipmentProduct.setFinancial_type(financialType);
				long min = (long)minumun;
				if(0 < min){
					equipmentProduct.setMinimum(true);
					equipmentProduct.setMinimumNumber(min);
				}else{
					equipmentProduct.setMinimum(false);
					equipmentProduct.setMinimumNumber(ZERO_VALUE);
				}
				equipmentProduct.setDeleted(false);
				equipmentProduct.setCreateDate(CURRENT_TIMESTAMP);
				equipmentProduct.setCreatedBy("Migrate");
				Long equipmentProductId = productService.saveMasterProduct(equipmentProduct);
				
			}
		}
		
		sheetIndex = 2;
		intRow = 0;
		for (Row rows : workbook.getSheetAt(sheetIndex)) {
//			logger.info("rows : "+rows.getRowNum());
			if (intRow > 2) {

				String unitName = "-";
				String financialType = "";
				String productName = getDataString(workbook, sheetIndex, "B", intRow);
				String productCode = "";
				double minumun = getDataDouble(workbook, sheetIndex, "E", intRow);
				String supplier = getDataString(workbook, sheetIndex, "G", intRow)+" "+getDataString(workbook, sheetIndex, "F", intRow);
				double productCostDouble = getDataDouble(workbook, sheetIndex, "H", intRow);
				double priceIncTaxDouble = getDataDouble(workbook, sheetIndex, "I", intRow);
				double productSalePriceDouble = getDataDouble(workbook, sheetIndex, "J", intRow);
				
				logger.info("productName2 : "+productName);
				logger.info("productCode2 : "+productCode);
				logger.info("productCostDouble2 : "+productCostDouble);
				logger.info("priceIncTaxDouble2 : "+priceIncTaxDouble);
				logger.info("productSalePriceDouble2 : "+productSalePriceDouble);
				
				if(StringUtils.isEmpty(productName)) continue;
				
				EquipmentProduct equipmentProduct = equipmentProductService.getEquipmentProductByProductName(productName);
				
				if(null != equipmentProduct) continue;
				
				equipmentProduct = new EquipmentProduct();
				equipmentProduct.setProductCode(productCode);
				equipmentProduct.setProductName(productName);
				
				float productCost = (float)productCostDouble;
				float priceIncTax = (float)priceIncTaxDouble;
				float productSalePrice = (float)productSalePriceDouble;
				
				equipmentProduct.setCost(productCost);
				equipmentProduct.setPriceIncTax(priceIncTax);
				equipmentProduct.setSalePrice(productSalePrice);

				if(null == epc) continue;
				equipmentProduct.setEquipmentProductCategory(epc);
				equipmentProduct.setSupplier(supplier);
				
				Stock stock = stockService.getStockById(1L);
				equipmentProduct.setStock(stock);
				
				Unit unit = unitService.getUnitByUnitName(unitName);
				if(null == unit) continue;
				equipmentProduct.setUnit(unit);
				
				financialType = "C"; // ต้นทุน
				
				equipmentProduct.setFinancial_type(financialType);
				long min = (long)minumun;
				if(0 < min){
					equipmentProduct.setMinimum(true);
					equipmentProduct.setMinimumNumber(min);
				}else{
					equipmentProduct.setMinimum(false);
					equipmentProduct.setMinimumNumber(ZERO_VALUE);
				}
				equipmentProduct.setDeleted(false);
				equipmentProduct.setCreateDate(CURRENT_TIMESTAMP);
				equipmentProduct.setCreatedBy("Migrate");
				Long equipmentProductId = productService.saveMasterProduct(equipmentProduct);
				
			}
			intRow++;
		}
		
		sheetIndex = 3;
		intRow = 0;
		for (Row rows : workbook.getSheetAt(sheetIndex)) {
//			logger.info("rows : "+rows.getRowNum());
			if (intRow > 2) {

				String unitName = "-";
				String financialType = "";
				String productName = getDataString(workbook, sheetIndex, "B", intRow);
				String productCode = "";
				double minumun = getDataDouble(workbook, sheetIndex, "E", intRow);
				String supplier = getDataString(workbook, sheetIndex, "G", intRow)+" "+getDataString(workbook, sheetIndex, "F", intRow);
				double productCostDouble = getDataDouble(workbook, sheetIndex, "H", intRow);
				double priceIncTaxDouble = getDataDouble(workbook, sheetIndex, "I", intRow);
				double productSalePriceDouble = getDataDouble(workbook, sheetIndex, "J", intRow);
				
				logger.info("productName3 : "+productName);
				logger.info("productCode3 : "+productCode);
				logger.info("productCostDouble3 : "+productCostDouble);
				logger.info("priceIncTaxDouble3 : "+priceIncTaxDouble);
				logger.info("productSalePriceDouble3 : "+productSalePriceDouble);
				
				if(StringUtils.isEmpty(productName)) continue;
				
				EquipmentProduct equipmentProduct = equipmentProductService.getEquipmentProductByProductName(productName);
				
				if(null != equipmentProduct) continue;
				
				equipmentProduct = new EquipmentProduct();
				equipmentProduct.setProductCode(productCode);
				equipmentProduct.setProductName(productName);
				
				float productCost = (float)productCostDouble;
				float priceIncTax = (float)priceIncTaxDouble;
				float productSalePrice = (float)productSalePriceDouble;
				
				equipmentProduct.setCost(productCost);
				equipmentProduct.setPriceIncTax(priceIncTax);
				equipmentProduct.setSalePrice(productSalePrice);

				if(null == epc) continue;
				equipmentProduct.setEquipmentProductCategory(epc);
				equipmentProduct.setSupplier(supplier);
				
				Stock stock = stockService.getStockById(1L);
				equipmentProduct.setStock(stock);
				
				Unit unit = unitService.getUnitByUnitName(unitName);
				if(null == unit) continue;
				equipmentProduct.setUnit(unit);
				
				financialType = "C"; // ต้นทุน
				
				equipmentProduct.setFinancial_type(financialType);
				long min = (long)minumun;
				if(0 < min){
					equipmentProduct.setMinimum(true);
					equipmentProduct.setMinimumNumber(min);
				}else{
					equipmentProduct.setMinimum(false);
					equipmentProduct.setMinimumNumber(ZERO_VALUE);
				}
				equipmentProduct.setDeleted(false);
				equipmentProduct.setCreateDate(CURRENT_TIMESTAMP);
				equipmentProduct.setCreatedBy("Migrate");
				Long equipmentProductId = productService.saveMasterProduct(equipmentProduct);
				
			}
			intRow++;
		}
		
		sheetIndex = 4;
		intRow = 0;
		for (Row rows : workbook.getSheetAt(sheetIndex)) {
			intRow++;
			if (intRow > 2) {

				String unitName = "-";
				String financialType = "";
				String productName = getDataString(workbook, sheetIndex, "B", intRow);
				String productCode = "";
				double minumun = getDataDouble(workbook, sheetIndex, "D", intRow);
				String supplier = getDataString(workbook, sheetIndex, "G", intRow)+" "+getDataString(workbook, sheetIndex, "F", intRow);
				double productCostDouble = getDataDouble(workbook, sheetIndex, "H", intRow);
				double priceIncTaxDouble = getDataDouble(workbook, sheetIndex, "I", intRow);
				double productSalePriceDouble = getDataDouble(workbook, sheetIndex, "J", intRow);
				
				logger.info("productName4 : "+productName);
				logger.info("productCode4 : "+productCode);
				logger.info("productCostDouble4 : "+productCostDouble);
				logger.info("priceIncTaxDouble4 : "+priceIncTaxDouble);
				logger.info("productSalePriceDouble4 : "+productSalePriceDouble);
				
				if(StringUtils.isEmpty(productName)) continue;
				
				EquipmentProduct equipmentProduct = equipmentProductService.getEquipmentProductByProductName(productName);
				
				if(null != equipmentProduct) continue;
				
				equipmentProduct = new EquipmentProduct();
				equipmentProduct.setProductCode(productCode);
				equipmentProduct.setProductName(productName);
				
				float productCost = (float)productCostDouble;
				float priceIncTax = (float)priceIncTaxDouble;
				float productSalePrice = (float)productSalePriceDouble;
				
				equipmentProduct.setCost(productCost);
				equipmentProduct.setPriceIncTax(priceIncTax);
				equipmentProduct.setSalePrice(productSalePrice);

				if(null == epc) continue;
				equipmentProduct.setEquipmentProductCategory(epc);
				equipmentProduct.setSupplier(supplier);
				
				Stock stock = stockService.getStockById(1L);
				equipmentProduct.setStock(stock);
				
				Unit unit = unitService.getUnitByUnitName(unitName);
				if(null == unit) continue;
				equipmentProduct.setUnit(unit);
				
				financialType = "C"; // ต้นทุน
				
				equipmentProduct.setFinancial_type(financialType);
				long min = (long)minumun;
				if(0 < min){
					equipmentProduct.setMinimum(true);
					equipmentProduct.setMinimumNumber(min);
				}else{
					equipmentProduct.setMinimum(false);
					equipmentProduct.setMinimumNumber(ZERO_VALUE);
				}
				equipmentProduct.setDeleted(false);
				equipmentProduct.setCreateDate(CURRENT_TIMESTAMP);
				equipmentProduct.setCreatedBy("Migrate");
				Long equipmentProductId = productService.saveMasterProduct(equipmentProduct);
				
			}
		}
		
		
		sheetIndex = 5;
		intRow = 0;
		for (Row rows : workbook.getSheetAt(sheetIndex)) {
			intRow++;
			if (intRow > 2) {

				String unitName = "-";
				String financialType = "";
				String productName = getDataString(workbook, sheetIndex, "B", intRow);
				String productCode = "";
				double minumun = 0;
				String supplier = getDataString(workbook, sheetIndex, "E", intRow)+" "+getDataString(workbook, sheetIndex, "D", intRow);
				double productCostDouble = getDataDouble(workbook, sheetIndex, "F", intRow);
				double priceIncTaxDouble = getDataDouble(workbook, sheetIndex, "G", intRow);
				double productSalePriceDouble = getDataDouble(workbook, sheetIndex, "H", intRow);
				
				logger.info("productName5 : "+productName);
				logger.info("productCode5 : "+productCode);
				logger.info("productCostDouble5 : "+productCostDouble);
				logger.info("priceIncTaxDouble5 : "+priceIncTaxDouble);
				logger.info("productSalePriceDouble5 : "+productSalePriceDouble);
				
				if(StringUtils.isEmpty(productName)) continue;
				
				EquipmentProduct equipmentProduct = equipmentProductService.getEquipmentProductByProductName(productName);
				
				if(null != equipmentProduct) continue;
				
				equipmentProduct = new EquipmentProduct();
				equipmentProduct.setProductCode(productCode);
				equipmentProduct.setProductName(productName);
				
				float productCost = (float)productCostDouble;
				float priceIncTax = (float)priceIncTaxDouble;
				float productSalePrice = (float)productSalePriceDouble;
				
				equipmentProduct.setCost(productCost);
				equipmentProduct.setPriceIncTax(priceIncTax);
				equipmentProduct.setSalePrice(productSalePrice);

				if(null == epc) continue;
				equipmentProduct.setEquipmentProductCategory(epc);
				equipmentProduct.setSupplier(supplier);
				
				Stock stock = stockService.getStockById(1L);
				equipmentProduct.setStock(stock);
				
				Unit unit = unitService.getUnitByUnitName(unitName);
				if(null == unit) continue;
				equipmentProduct.setUnit(unit);
				
				financialType = "C"; // ต้นทุน
				
				equipmentProduct.setFinancial_type(financialType);
				long min = (long)minumun;
				if(0 < min){
					equipmentProduct.setMinimum(true);
					equipmentProduct.setMinimumNumber(min);
				}else{
					equipmentProduct.setMinimum(false);
					equipmentProduct.setMinimumNumber(ZERO_VALUE);
				}
				equipmentProduct.setDeleted(false);
				equipmentProduct.setCreateDate(CURRENT_TIMESTAMP);
				equipmentProduct.setCreatedBy("Migrate");
				Long equipmentProductId = productService.saveMasterProduct(equipmentProduct);
				
			}
		}
		
		logger.info("================== END EquipmentProduct ====================");
		
	}
	
	private double getDataDouble(Workbook workbook, int indexSheet, String key, int index) throws Exception {
		double data = 0;
		Sheet sheet = workbook.getSheetAt(indexSheet);
	    FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
	    
	    CellReference cellReference = new CellReference(key+index); //pass the cell which contains formula
	    Row row = sheet.getRow(cellReference.getRow());
	    Cell cell = row.getCell(cellReference.getCol());

	    CellValue cellValue = evaluator.evaluate(cell);

	    if(null != cellValue){
		    switch (cellValue.getCellType()) {
		    case Cell.CELL_TYPE_NUMERIC:
		    	data = cellValue.getNumberValue();
		    	break;
		    }
	    }
		return data;
	}
	
	private String getDataString(Workbook workbook, int indexSheet, String key, int index) throws Exception {
		String data = "";
		Sheet sheet = workbook.getSheetAt(indexSheet);
	    FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

	    CellReference cellReference = new CellReference(key+index); //pass the cell which contains formula
	    int intRow = cellReference.getRow();
	    Row row = sheet.getRow(cellReference.getRow());
	    Cell cell = row.getCell(cellReference.getCol());

	    CellValue cellValue = evaluator.evaluate(cell);

	    if(null != cellValue){
		    switch (cellValue.getCellType()) {
		    case Cell.CELL_TYPE_STRING:
		    	data = cellValue.getStringValue();
		    	break;
		    }
	    }
		return data;
	}
	
	@SuppressWarnings("deprecation")
	private void processMigrateProductOld(Workbook workbook) throws Exception {
		int sheetIndex = workbook.getNumberOfSheets();
		int intRow = 0;
		// ข้อมูลสินค้าและอุปกรณ์
		for (Row row : workbook.getSheetAt(0)) {
			if (intRow > 0) {
				String productCode = row.getCell(0, Row.CREATE_NULL_AS_BLANK).toString();
				String productName = row.getCell(1, Row.CREATE_NULL_AS_BLANK).toString();
				
				if(StringUtils.isEmpty(productName)) continue;
				
				EquipmentProduct equipmentProduct = equipmentProductService.getEquipmentProductByProductName(productName);
				
				if(null != equipmentProduct) continue;
				
				String equipmentProductCategory = row.getCell(2, Row.CREATE_NULL_AS_BLANK).toString();
				String supplier = row.getCell(3, Row.CREATE_NULL_AS_BLANK).toString();
				String stockName = row.getCell(4, Row.CREATE_NULL_AS_BLANK).toString();
				String unitName = row.getCell(5, Row.CREATE_NULL_AS_BLANK).toString();
				String financialType = row.getCell(6, Row.CREATE_NULL_AS_BLANK).toString();
				String minumun = row.getCell(7, Row.CREATE_NULL_AS_BLANK).toString();
				
				if(StringUtils.isEmpty(minumun)) minumun = "0";
				
				Long min = Long.valueOf(String.format("%010d",Math.round(Float.valueOf(minumun))));
				
				logger.info("productCode : "+productCode);
				logger.info("productName : "+productName);
				logger.info("equipmentProductCategory : "+equipmentProductCategory);
				logger.info("supplier : "+supplier);
				logger.info("stockName : "+stockName);
				logger.info("unitName : "+unitName);
				logger.info("financialType : "+financialType);
				logger.info("minumun : "+min);
				
				equipmentProduct = new EquipmentProduct();
				equipmentProduct.setProductCode(productCode);
				equipmentProduct.setProductName(productName);
				
				EquipmentProductCategory epc = equipmentProductCategoryService
						.getEquipmentProductCategoryByEquipmentProductCategoryName(equipmentProductCategory);
				if(null == epc) continue;
				equipmentProduct.setEquipmentProductCategory(epc);
				equipmentProduct.setSupplier(supplier);

				Stock stock = stockService.getStockByStockName(stockName);
				if(null == stock) stock = stockService.getStockById(1L);
				if(null == stock) continue;
				equipmentProduct.setStock(stock);
				
				Unit unit = unitService.getUnitByUnitName(unitName);
				if(null == unit) continue;
				equipmentProduct.setUnit(unit);
				
				//A=Asset,C=Capital
				if("ต้นทุน".equals(financialType)){
					financialType = "C"; // ต้นทุน
				}else {
					financialType = "A"; // ทรัพย์สิน
				}
				
				equipmentProduct.setFinancial_type(financialType);
				if(0 < min){
					equipmentProduct.setMinimum(true);
					equipmentProduct.setMinimumNumber(min);
				}else{
					equipmentProduct.setMinimum(false);
					equipmentProduct.setMinimumNumber(ZERO_VALUE);
				}
				equipmentProduct.setDeleted(false);
				equipmentProduct.setCreateDate(CURRENT_TIMESTAMP);
				equipmentProduct.setCreatedBy("Migrate");
				Long equipmentProductId = productService.saveMasterProduct(equipmentProduct);
				
			}
			intRow++;
		}
		
		logger.info("================== END EquipmentProduct ====================");
		
		intRow = 0;
		// ข้อมูลสินค้าและอุปกรณ์รายชิ้น
		for (Row row : workbook.getSheetAt(1)) {
			if (intRow > 0) {
				String productName = row.getCell(0, Row.CREATE_NULL_AS_BLANK).toString();
				
				if(StringUtils.isEmpty(productName)) continue;

				EquipmentProduct equipmentProduct = equipmentProductService.getEquipmentProductByProductName(productName);
				if(null == equipmentProduct) continue;
				
				String serialNo = row.getCell(1, Row.CREATE_NULL_AS_BLANK).toString();
				String reference = row.getCell(2, Row.CREATE_NULL_AS_BLANK).toString();
				String numStr = row.getCell(3, Row.CREATE_NULL_AS_BLANK).toString();
				String datepickerGuaranteeStr = row.getCell(4, Row.CREATE_NULL_AS_BLANK).toString();
				String datepickerOrderDateStr = row.getCell(5, Row.CREATE_NULL_AS_BLANK).toString();
				String productCostStr = row.getCell(6, Row.CREATE_NULL_AS_BLANK).toString();
				String priceIncTaxStr = row.getCell(7, Row.CREATE_NULL_AS_BLANK).toString();
				String productSalePriceStr = row.getCell(8, Row.CREATE_NULL_AS_BLANK).toString();
				
				if(StringUtils.isEmpty(numStr)) numStr = "0";
				int num = Math.round(Float.valueOf(numStr));
				
				if(StringUtils.isEmpty(productCostStr)) productCostStr = "0";
				Float productCost = Float.valueOf(productCostStr);
				
				if(StringUtils.isEmpty(priceIncTaxStr)) priceIncTaxStr = "0";
				Float priceIncTax = Float.valueOf(priceIncTaxStr);
				
				if(StringUtils.isEmpty(productSalePriceStr)) productSalePriceStr = "0";
				Float productSalePrice = Float.valueOf(productSalePriceStr);
				
				Date datepickerGuarantee = null, datepickerOrderDate = null;
				
				SimpleDateFormat formatData = new SimpleDateFormat("dd-MMM-yyyy", new Locale("EN", "en"));
				if(StringUtils.isNotEmpty(datepickerGuaranteeStr)){
					datepickerGuarantee = formatData.parse(datepickerGuaranteeStr);
				}
				if(StringUtils.isNotEmpty(datepickerOrderDateStr)){
					datepickerOrderDate = formatData.parse(datepickerOrderDateStr);
				}

//				logger.info("productName : "+productName);
//				logger.info("serialNo : "+serialNo);
//				logger.info("reference : "+reference);
//				logger.info("num : "+num);
//				logger.info("datepickerGuaranteeStr : "+datepickerGuaranteeStr);
//				logger.info("datepickerOrderDateStr : "+datepickerOrderDateStr);
//				logger.info("datepickerGuarantee : "+datepickerGuarantee);
//				logger.info("datepickerOrderDate : "+datepickerOrderDate);
//				logger.info("productCost : "+productCost);
//				logger.info("priceIncTax : "+priceIncTax);
//				logger.info("productSalePrice : "+productSalePrice);
				
				if(StringUtils.isNotEmpty(serialNo)){ // มี SN
					EquipmentProductItem equipmentProductItem = equipmentProductItemService.getEquipmentProductItemBySerialNo(serialNo);
					if(null != equipmentProductItem) continue; // เช็ค SN ต้องไม่ซ้ำ
					equipmentProductItem = new EquipmentProductItem();
					equipmentProductItem.setEquipmentProduct(equipmentProduct);
					equipmentProductItem.setSerialNo(serialNo);
					equipmentProductItem.setReference(reference);
					equipmentProductItem.setGuaranteeDate(datepickerGuarantee);
					equipmentProductItem.setOrderDate(datepickerOrderDate);
					equipmentProductItem.setCost(productCost);
					equipmentProductItem.setPriceIncTax(priceIncTax);
					equipmentProductItem.setSalePrice(productSalePrice);
					equipmentProductItem.setCreateDate(CURRENT_TIMESTAMP);
					equipmentProductItem.setCreatedBy("Migrate");
					equipmentProductItemService.save(equipmentProductItem);
				} else { // ไม่มี SN
					EquipmentProductItem equipmentProductItem = new EquipmentProductItem();
					equipmentProductItem.setEquipmentProduct(equipmentProduct);
					equipmentProductItem.setSerialNo(null);
					equipmentProductItem.setReference(reference);
					equipmentProductItem.setGuaranteeDate(datepickerGuarantee);
					equipmentProductItem.setOrderDate(datepickerOrderDate);
					equipmentProductItem.setCost(productCost);
					equipmentProductItem.setPriceIncTax(priceIncTax);
					equipmentProductItem.setSalePrice(productSalePrice);
					equipmentProductItem.setCreateDate(CURRENT_TIMESTAMP);
					equipmentProductItem.setCreatedBy("Migrate");
					equipmentProductItemService.save(equipmentProductItem);
				}

			}
			intRow++;
		}
		logger.info("================== END EquipmentProductItem ====================");
		intRow = 0;
		// ผูกอุปกรณ์กับลูกค้าโดยผ่านใบสมัคร
		for (Row row : workbook.getSheetAt(1)) {
			if (intRow > 0) {
				String productName = row.getCell(0, Row.CREATE_NULL_AS_BLANK).toString();
				String cusFname = row.getCell(9, Row.CREATE_NULL_AS_BLANK).toString();
				String cusLname = row.getCell(10, Row.CREATE_NULL_AS_BLANK).toString();
				String cusCode = row.getCell(11, Row.CREATE_NULL_AS_BLANK).toString();
				String houseNumber = row.getCell(12, Row.CREATE_NULL_AS_BLANK).toString();
				logger.info("cusFname : "+cusFname);
				logger.info("cusLname : "+cusLname);
				logger.info("cusCode : "+cusCode);
				logger.info("houseNumber : "+houseNumber);
				
				if(StringUtils.isEmpty(productName)) continue;
				if(StringUtils.isEmpty(houseNumber)) continue;
				EquipmentProduct equipmentProduct = equipmentProductService.getEquipmentProductByProductName(productName);
				if(null == equipmentProduct) continue;
				ServiceApplication serviceApplication = serviceApplicationService.getServiceApplicationByHouseNumber(houseNumber);
				if(null == serviceApplication) continue;
				logger.info("ServiceApplicationNo : "+serviceApplication.getServiceApplicationNo());
				
			}
			intRow++;
		}
		
	}
	
	@RequestMapping(value="migrateInternet", method = RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public JsonResponse migrateInternet(@RequestParam("file_internet") final MultipartFile file_customer,
			HttpServletRequest request) {
		logger.info("[method : migrateInternet][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				if(null != file_customer && file_customer.getSize() > 0){
					InputStream inputStream = file_customer.getInputStream();
			        if(null != inputStream){
			        	Workbook workbook = ReadWriteExcelFile.ReadExcelProduct(inputStream);
			        	if(null != workbook)
			        		processMigrateInternet(workbook);
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
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("migrate.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
    
	@SuppressWarnings("deprecation")
	private void processMigrateInternet(Workbook workbook) throws Exception {
		int sheetIndex = workbook.getNumberOfSheets();
		int intRow = 0;
		// ข้อมูล package internet
		for (Row row : workbook.getSheetAt(0)) {
			if (intRow > 0) {
				String packageName = row.getCell(0, Row.CREATE_NULL_AS_BLANK).toString();
				String packageCode = row.getCell(1, Row.CREATE_NULL_AS_BLANK).toString();
				String stockName = row.getCell(2, Row.CREATE_NULL_AS_BLANK).toString();

				if(StringUtils.isEmpty(packageName) || StringUtils.isEmpty(packageCode) || StringUtils.isEmpty(stockName)) continue;
				
				InternetProduct internetProduct = internetProductService.getInternetProductByProductName(packageName);
				if(null != internetProduct) continue;
				Stock stock = stockService.getStockByStockName(stockName);
				if(null == stock) stock = stockService.getStockById(1L);
				if(null == stock) continue;
				
				internetProduct = new InternetProduct();
				internetProduct.setProductCode(packageCode);
				internetProduct.setProductName(packageName);
				internetProduct.setCreateDate(CURRENT_TIMESTAMP);
				internetProduct.setCreatedBy("Migrate");
				internetProduct.setDeleted(Boolean.FALSE);
				// stock
				internetProduct.setStock(stock);
				// load inter user type
				EquipmentProductCategory epc = equipmentProductCategoryService.getEquipmentProductCategoryByCode(
						messageSource.getMessage("equipmentproductcategory.type.internet", null,
								LocaleContextHolder.getLocale()));
				internetProduct.setProductCategory(epc);
				internetProductService.save(internetProduct);
			}
			intRow++;
		}
		
		logger.info("================== END Package Internet ====================");
		
		intRow = 0;
		// ข้อมูล username password
		for (Row row : workbook.getSheetAt(1)) {
			if (intRow > 0) {
				String reference = row.getCell(0, Row.CREATE_NULL_AS_BLANK).toString();
				String packageName = row.getCell(1, Row.CREATE_NULL_AS_BLANK).toString();
				String userName = row.getCell(2, Row.CREATE_NULL_AS_BLANK).toString();
				String password = row.getCell(3, Row.CREATE_NULL_AS_BLANK).toString();
				String cusFname = row.getCell(4, Row.CREATE_NULL_AS_BLANK).toString();
				String cusLname = row.getCell(5, Row.CREATE_NULL_AS_BLANK).toString();
				String cusCode = row.getCell(6, Row.CREATE_NULL_AS_BLANK).toString();

				if(StringUtils.isEmpty(packageName) || StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) continue;
				
				InternetProductItem internetProductItem = internetProductItemService.getInternetProductItemByUserNameOrPassword(userName,password);
				if(null == internetProductItem){
					internetProductItem = new InternetProductItem();
					internetProductItem.setReference(reference);
					internetProductItem.setUserName(userName);
					internetProductItem.setPassword(password);
					internetProductItem.setCreateDate(CURRENT_TIMESTAMP);
					internetProductItem.setCreatedBy("Migrate");
					internetProductItem.setStatus(messageSource.getMessage("internetProduct.item.unuse", null,
							LocaleContextHolder.getLocale()));
					internetProductItem.setDeleted(Boolean.FALSE);
					
					InternetProduct internetProduct = internetProductService.getInternetProductByProductName(packageName);
					if(null != internetProduct){
						internetProductItem.setInternetProduct(internetProduct);
						internetProductItemService.save(internetProductItem);
					}
				}
				// map customer
				
				

			}
			intRow++;
		}
	}
	
	@RequestMapping(value="migrateCustomer", method = RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public JsonResponse migrateCustomer(@RequestParam("file_customer") final MultipartFile file_customer,
			HttpServletRequest request) {
		logger.info("[method : migrateCustomer][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				if(null != file_customer && file_customer.getSize() > 0){
					InputStream inputStream = file_customer.getInputStream();
					String fileName = file_customer.getOriginalFilename();
			        if(null != inputStream){
			        	List<MigrateCustomerBean> migrateCustomerBeanList = ReadWriteExcelFile.ReadExcelCustomer(inputStream);
//			        	processMigrateCustomer(migrateCustomerBeanList, fileName.contains("internet"));
			        	
			        	// อัพเดทลูกค้าตัดสาย
			        	processMigrateCustomerCut(migrateCustomerBeanList, fileName.contains("internet"));
			        	
			        	// อัพเดตบ้านเลขที่
//			        	processMigrateCustomerUpdateNo(migrateCustomerBeanList, fileName.contains("internet"));
			        	
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
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("migrate.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
//	private void processMigrateCustomerInvoice(List<MigrateCustomerBean> migrateCustomerBeanList) throws Exception {
//		SimpleDateFormat formatData = new SimpleDateFormat("dd-MMM-yyyy", new Locale("EN", "en"));
//		if(null != migrateCustomerBeanList && migrateCustomerBeanList.size() > 0){
//			long startTime = System.currentTimeMillis();
//			int index = 1, indexNew = 0;
//			for (MigrateCustomerBean bean: migrateCustomerBeanList) {
//				if(null != bean.getDateBill() && !"".equals(bean.getDateBill()) && !"0000-00-00".equals(bean.getDateBill())){
//					Customer customer = customerService.getCustomerByCustCode(bean.getCustomerCode());
//					if(null != customer){					
//						List<ServiceApplication> serviceApplicationList = customer.getServiceApplications();
//						if(null != serviceApplicationList && serviceApplicationList.size() > 0 ){
//							ServiceApplication serviceApplication = serviceApplicationList.get(0);
//							if(null != serviceApplication){
//								Date s1 = formatData.parse(bean.getDateBill());
//								Date s2 = formatData.parse("17-Feb-2018");
//								if(s1.compareTo(s2)==0){
//									s2 = formatData.parse("17-Jan-2018");
//									logger.info(">>>> "+bean.getDateBill());
//									Invoice invoice = new Invoice();
//									invoice.setInvoiceCode(financialService.genInVoiceCode());
//									invoice.setServiceApplication(serviceApplication);
//			//						invoice.setWorkSheet(worksheet);
//									invoice.setInvoiceType("O"); //S=สำหรับติดตั้ง,R=สำหรับซ่อม,O=สำหรับตาม
//			//						invoice.setStatus("S"); //W=รอลูกค้าชำระ,S=ชำระแล้ว,O=เกินวันกำหนดชำระ
//									invoice.setStatus("W"); //W=รอลูกค้าชำระ,S=ชำระแล้ว,O=เกินวันกำหนดชำระ
//									invoice.setStatusScan("N");
//									invoice.setIssueDocDate(new Date());
//									invoice.setPaymentDate(s2); // วันครบกำหนดชำระ
//									invoice.setCreateDate(s2); // วันครบกำหนดชำระ
//									
//									float amount = Float.valueOf(bean.getCostBill());
//											
//									Date currentDate = new Date();
//									if(invoice.getCreateDate().before(currentDate)){
//										invoice.setStatus("O"); // O=เกินวันกำหนดชำระ 
//									}
//									
////									if(0 == amount){
////										invoice.setStatus("S");
////									}
//									
//									amount = 0f;
//									if(serviceApplication.isMonthlyService()){
//										amount += serviceApplication.getMonthlyServiceFee();
//									}else{
//										amount += serviceApplication.getOneServiceFee();
//									}
//									
//									invoice.setAmount(amount);
//									invoice.setBilling(Boolean.FALSE);
//									invoice.setCutting(Boolean.FALSE);
//									invoice.setDeleted(Boolean.FALSE);
//									invoice.setCreatedBy("Migrate");
//									financialService.saveInvoice(invoice);
//									
//									Receipt receipt = new Receipt();
//									receipt.setReceiptCode(financialService.genReceiptCode());
//									receipt.setStatus("H");
////									if(0 == amount){
////										amount = 0f;
////										if(serviceApplication.isMonthlyService()){
////											amount += serviceApplication.getMonthlyServiceFee();
////										}else{
////											amount += serviceApplication.getOneServiceFee();
////										}
////										receipt.setAmount(amount);
////										receipt.setStatus("P");
////										receipt.setPaymentType("C");
////										receipt.setPaymentDate(new Date());
////									}
//									receipt.setInvoice(invoice);
//									receipt.setCreatedBy("Migrate");
//									receipt.setCreateDate(CURRENT_TIMESTAMP);
//									receipt.setDeleted(Boolean.FALSE);
//									financialService.saveReceipt(receipt);
//								}
//								
//								Invoice invoice = new Invoice();
//								invoice.setInvoiceCode(financialService.genInVoiceCode());
//								invoice.setServiceApplication(serviceApplication);
//		//						invoice.setWorkSheet(worksheet);
//								invoice.setInvoiceType("O"); //S=สำหรับติดตั้ง,R=สำหรับซ่อม,O=สำหรับตาม
//		//						invoice.setStatus("S"); //W=รอลูกค้าชำระ,S=ชำระแล้ว,O=เกินวันกำหนดชำระ
//								invoice.setStatus("W"); //W=รอลูกค้าชำระ,S=ชำระแล้ว,O=เกินวันกำหนดชำระ
//								invoice.setStatusScan("N");
//								invoice.setIssueDocDate(new Date());
//								invoice.setPaymentDate(s1); // วันครบกำหนดชำระ
//								invoice.setCreateDate(s1); // วันครบกำหนดชำระ
//								
//								float amount = Float.valueOf(bean.getCostBill());
//										
//								Date currentDate = new Date();
//								if(invoice.getCreateDate().before(currentDate)){
//									invoice.setStatus("O"); // O=เกินวันกำหนดชำระ 
//								}
//								
////								if(0 == amount){
////									invoice.setStatus("S");
////								}
//								
//								amount = 0f;
//								if(serviceApplication.isMonthlyService()){
//									amount += serviceApplication.getMonthlyServiceFee();
//								}else{
//									amount += serviceApplication.getOneServiceFee();
//								}
//								
//								invoice.setAmount(amount);
//								invoice.setBilling(Boolean.FALSE);
//								invoice.setCutting(Boolean.FALSE);
//								invoice.setDeleted(Boolean.FALSE);
//								invoice.setCreatedBy("Migrate");
//								financialService.saveInvoice(invoice);
//								
//								Receipt receipt = new Receipt();
//								receipt.setReceiptCode(financialService.genReceiptCode());
//								receipt.setStatus("H");
////								if(0 == amount){
////									amount = 0f;
////									if(serviceApplication.isMonthlyService()){
////										amount += serviceApplication.getMonthlyServiceFee();
////									}else{
////										amount += serviceApplication.getOneServiceFee();
////									}
////									receipt.setAmount(amount);
////									receipt.setStatus("P");
////									receipt.setPaymentType("C");
////									receipt.setPaymentDate(new Date());
////								}
//								receipt.setInvoice(invoice);
//								receipt.setCreatedBy("Migrate");
//								receipt.setCreateDate(CURRENT_TIMESTAMP);
//								receipt.setDeleted(Boolean.FALSE);
//								financialService.saveReceipt(receipt);
//								
//							}
//						}
//					}
//					
//					
////					serviceApplication.setStartDate(formatData.parse(bean.getServiceApplicationDate()));
//				}
//			}
//		}
//	}
	
//	private void processMigrateCustomerCut(List<MigrateCustomerBean> migrateCustomerBeanList) throws Exception {
//		SimpleDateFormat formatData = new SimpleDateFormat("dd-MMM-yyyy", new Locale("EN", "en"));
//		if(null != migrateCustomerBeanList && migrateCustomerBeanList.size() > 0){
//			for (MigrateCustomerBean bean: migrateCustomerBeanList) {
//				String dateCut = bean.getDateCut();
//				String cutStatus = bean.getCutStatus();
//				Customer customer = customerService.getCustomerByCustCode(bean.getCustomerCode());
//				if (null != customer) {
//					List<ServiceApplication> serviceApplications = customer.getServiceApplications();
//					if (null != serviceApplications && serviceApplications.size() > 0) {
//						ServiceApplication serviceApp = serviceApplications.get(0);
//						if ("3.0".equals(cutStatus) && null != dateCut && !"".equals(dateCut) && !"0000-00-00".equals(dateCut)) {
//							serviceApp.setCancelServiceDate(formatData.parse(dateCut));
//							serviceApp.setStatus("I");
//							serviceApp.setUpdatedBy("Migrate");
//							serviceApp.setUpdatedDate(CURRENT_TIMESTAMP);
//							
//							customer.setActive(false);
//							customer.setUpdatedBy("Migrate");
//							customer.setUpdatedDate(CURRENT_TIMESTAMP);
//							customerService.update(customer);
//						} else {
//							serviceApp.setStatus("A");
//							serviceApp.setUpdatedBy("Migrate");
//							serviceApp.setUpdatedDate(CURRENT_TIMESTAMP);
//							
//							customer.setActive(true);
//							customer.setUpdatedBy("Migrate");
//							customer.setUpdatedDate(CURRENT_TIMESTAMP);
//							customerService.update(customer);
//						}
//						serviceApplicationService.update(serviceApp);
//					}
//				}
//			}
//		}
//	}
	
	@RequestMapping(value="migrateInvoice", method = RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public JsonResponse migrateInvoice(@RequestParam("file_inv") final MultipartFile file_inv,
			HttpServletRequest request) {
		logger.info("[method : migrateInvoice][Type : Controller]");
		JsonResponse jsonResponse = new JsonResponse();
		if (isPermission()) {
			try {
				if(null != file_inv && file_inv.getSize() > 0){
					InputStream inputStream = file_inv.getInputStream();
			        if(null != inputStream){
			        	List<MigrateInvoiceBean> migrateInvoiceBeanList = ReadWriteExcelFile.ReadExcelInvoice(inputStream);
			        	processMigrateInvoice(migrateInvoiceBeanList);
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
		generateAlert(jsonResponse, request, messageSource.getMessage("alert.title.save.success", null, LocaleContextHolder.getLocale()), messageSource.getMessage("migrate.success", null, LocaleContextHolder.getLocale()));
		return jsonResponse;
	}
	
	private void processMigrateInvoice(List<MigrateInvoiceBean> migrateInvoiceBeanList) throws Exception {
		if(null != migrateInvoiceBeanList && migrateInvoiceBeanList.size() > 0){
			long startTime = System.currentTimeMillis();
			logger.info("MigrateInvoice Size : "+migrateInvoiceBeanList.size());
			int index = 1, indexNew = 0;
			for (MigrateInvoiceBean bean: migrateInvoiceBeanList) {
				logger.info("MigrateInvoice : "+(index++)+" / "+migrateInvoiceBeanList.size());
				logger.info("InvoiceCode : "+bean.getInvoiceCode()+" | CustomerCode : "+bean.getCustomerCode());
				
				Invoice invoice = financialService.getInvoiceByCode(bean.getInvoiceCode());
				
				Customer customer = customerService.getCustomerByCustCode(bean.getCustomerCode());
				if(null != customer && null == invoice){
					List<ServiceApplication> serviceApplicationList = customer.getServiceApplications();
					if(null != serviceApplicationList && serviceApplicationList.size() > 0 ){
						ServiceApplication serviceApplication = serviceApplicationList.get(0);
						if(null != serviceApplication){
							List<Worksheet> worksheetList = serviceApplication.getWorksheets();
//							if(null != worksheetList && worksheetList.size() > 0){
//								Worksheet worksheet = worksheetList.get(0);
								invoice = new Invoice();
								invoice.setInvoiceCode(financialService.genInVoiceCode());
								invoice.setServiceApplication(serviceApplication);
//								invoice.setWorkSheet(worksheet);
								invoice.setInvoiceType("O"); //S=สำหรับติดตั้ง,R=สำหรับซ่อม,O=สำหรับตาม
//								invoice.setStatus("S"); //W=รอลูกค้าชำระ,S=ชำระแล้ว,O=เกินวันกำหนดชำระ
								invoice.setStatus("W"); //W=รอลูกค้าชำระ,S=ชำระแล้ว,O=เกินวันกำหนดชำระ
								invoice.setStatusScan("N");
								invoice.setIssueDocDate(new Date());
								SimpleDateFormat formatData = new SimpleDateFormat("dd-MMM-yyyy", new Locale("EN", "en"));
								if(null != bean.getBdate3() && !"".equals(bean.getBdate3())){
									invoice.setPaymentDate(formatData.parse(bean.getBdate3())); // วันครบกำหนดชำระ
									invoice.setCreateDate(formatData.parse(bean.getBdate3())); // วันครบกำหนดชำระ
								}else if(null != bean.getBdate2() && !"".equals(bean.getBdate2())){
									invoice.setPaymentDate(formatData.parse(bean.getBdate2())); // วันครบกำหนดชำระ
									invoice.setCreateDate(formatData.parse(bean.getBdate2())); // วันครบกำหนดชำระ
								}
								Date currentDate = new Date();
								if(invoice.getCreateDate().before(currentDate)){
									invoice.setStatus("O"); // O=เกินวันกำหนดชำระ 
								}
								
								float amount = 0f;
								if(serviceApplication.isMonthlyService()){
									amount += serviceApplication.getMonthlyServiceFee();
								}else{
									amount += serviceApplication.getOneServiceFee();
								}
//								amount += serviceApplication.getDeposit();
//								List<ProductItem> productItemList = worksheet.getProductItems();
//								if(null != productItemList && productItemList.size() > 0){
//									for(ProductItem productItem:productItemList){
//										amount += productItem.getAmount();
//									}
//								}
								invoice.setAmount(amount);
								invoice.setBilling(Boolean.FALSE);
								invoice.setCutting(Boolean.FALSE);
								invoice.setDeleted(Boolean.FALSE);
								invoice.setCreatedBy("Migrate");
								financialService.saveInvoice(invoice);
								
								Receipt receipt = new Receipt();
								receipt.setReceiptCode(financialService.genReceiptCode());
								receipt.setStatus("H");
								receipt.setInvoice(invoice);
								receipt.setCreatedBy("Migrate");
								receipt.setCreateDate(CURRENT_TIMESTAMP);
								receipt.setDeleted(Boolean.FALSE);
								financialService.saveReceipt(receipt);
								
								indexNew++;
//							}
						}
					}
				}
				
			}
			logger.info("MigrateInvoice New Total : "+indexNew);
			long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			int seconds = (int) (totalTime / 1000) % 60 ;
			int minutes = (int) ((totalTime / (1000*60)) % 60);
			int hours   = (int) ((totalTime / (1000*60*60)) % 24);
			logger.info("totalTime : "+String.format("%d hour, %d min, %d sec", hours,minutes,seconds));
		}
	}
	
	private void processMigrateCustomerUpdateNo(List<MigrateCustomerBean> migrateCustomerBeanList, boolean isInternet) throws Exception {
		SimpleDateFormat formatData = new SimpleDateFormat("dd-MMM-yyyy", new Locale("EN", "en"));
		Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
		if(null != migrateCustomerBeanList && migrateCustomerBeanList.size() > 0){
			long startTime = System.currentTimeMillis();
			logger.info("MigrateCustomer Size : "+migrateCustomerBeanList.size());
			int index = 1;
			for (MigrateCustomerBean bean: migrateCustomerBeanList) {
				String no = bean.getNo();
				logger.info("MigrateCustomer : "+(index++)+" / "+migrateCustomerBeanList.size());
				logger.info("CustomerCode : "+bean.getCustomerCode());
				Customer customerOld = customerService.getCustomerByCustCode(bean.getCustomerCode()); // เช็คลูกค้าว่ามีอยู่ในระบบหรือยัง โดยเช็คจาก รหัสลูกค้า
				logger.info("customerOld : "+customerOld);
				if(null != customerOld){
					List<Address> addressList = customerOld.getAddresses();
					if(null != addressList && addressList.size() > 0){
						for(Address address:addressList){
							logger.info("addressNo : "+address.getNo());
						}
					}
					List<ServiceApplication> serviceApplicationList = customerOld.getServiceApplications();
					if(null != serviceApplicationList){
						for(ServiceApplication serviceApplication:serviceApplicationList){
							List<Address> serviceAppAddress = serviceApplication.getAddresses();
							for(Address address:serviceAppAddress){
								logger.info("serviceAppAddressNo : "+address.getNo());
							}
						}
					}
					
				}
				logger.info("======================================================");
			}
		}
	}
	
	private void processMigrateCustomerCut(List<MigrateCustomerBean> migrateCustomerBeanList, boolean isInternet) throws Exception {
		SimpleDateFormat formatData = new SimpleDateFormat("dd-MMM-yyyy", new Locale("EN", "en"));
		Timestamp CURRENT_TIMESTAMP = new Timestamp(new Date().getTime());
		if(null != migrateCustomerBeanList && migrateCustomerBeanList.size() > 0){
			long startTime = System.currentTimeMillis();
			logger.info("MigrateCustomer Size : "+migrateCustomerBeanList.size());
			int index = 1, indexNew = 0;
			for (MigrateCustomerBean bean: migrateCustomerBeanList) {
				Date dateCut = bean.getDateCut();
				String cutStatus = bean.getCutStatus();
				
				logger.info("MigrateCustomer : "+(index++)+" / "+migrateCustomerBeanList.size());
				logger.info("CustomerCode : "+bean.getCustomerCode());
				Customer customerOld = customerService.getCustomerByCustCode(bean.getCustomerCode()); // เช็คลูกค้าว่ามีอยู่ในระบบหรือยัง โดยเช็คจาก รหัสลูกค้า
				logger.info("customerOld : "+customerOld);
				if(null != customerOld && "3.0".equals(cutStatus)){
					List<ServiceApplication> serviceApplicationList = customerOld.getServiceApplications();
					if(null != serviceApplicationList && serviceApplicationList.size() > 0){
						for(ServiceApplication serviceApplication:serviceApplicationList){
							serviceApplication.setCancelServiceDate(dateCut);
							serviceApplication.setStatus("I");
							serviceApplication.setUpdatedBy(getUserNameLogin());
							serviceApplication.setUpdatedDate(CURRENT_TIMESTAMP);
							
							List<Invoice> invoiceList = serviceApplication.getInvoices();
							if(null != invoiceList && invoiceList.size() > 0){
								for(Invoice invoice:invoiceList){
									if("O".equals(invoice.getInvoiceType())){
										invoice.setStatus("C");
										invoice.setUpdatedBy(getUserNameLogin());
										invoice.setUpdatedDate(CURRENT_TIMESTAMP);
									}
								}
							}
							
						}
					}
					customerOld.setActive(Boolean.FALSE);
					customerOld.setUpdatedBy(getUserNameLogin());
					customerOld.setUpdatedDate(CURRENT_TIMESTAMP);
					customerService.update(customerOld);
				}
				
			}
		}
	}
				
	private void processMigrateCustomer(List<MigrateCustomerBean> migrateCustomerBeanList, boolean isInternet) throws Exception {
		SimpleDateFormat formatData = new SimpleDateFormat("dd-MMM-yyyy", new Locale("EN", "en"));
		if(null != migrateCustomerBeanList && migrateCustomerBeanList.size() > 0){
			long startTime = System.currentTimeMillis();
			logger.info("MigrateCustomer Size : "+migrateCustomerBeanList.size());
			int index = 1, indexNew = 0;
			for (MigrateCustomerBean bean: migrateCustomerBeanList) {
				
				ServiceApplication serviceApplication = new ServiceApplication();
				
				Date dateCut = bean.getDateCut();
				String cutStatus = bean.getCutStatus();
				
				logger.info("MigrateCustomer : "+(index++)+" / "+migrateCustomerBeanList.size());
				logger.info("CustomerCode : "+bean.getCustomerCode());
				Customer customerOld = customerService.getCustomerByCustCode(bean.getCustomerCode()); // เช็คลูกค้าว่ามีอยู่ในระบบหรือยัง โดยเช็คจาก รหัสลูกค้า
				logger.info("customerOld : "+customerOld);
				if(null == customerOld){
					indexNew++;
					Customer customer = new Customer();
					customer.setCustCode(bean.getCustomerCode());
					customer.setSex(bean.getSex());
					customer.setPrefix(bean.getPrefix());
					customer.setFirstName(bean.getFirstName());
					customer.setLastName(bean.getLastName());
					customer.setCustType(bean.getCustomerType());
					customer.setIdentityNumber(bean.getIdentityNumber());
					
					Career career = customerService.findCareerByCareerName(bean.getCareer());
					if(null != career){
						customer.setCareer(career);
					}else{ // ถ้าไม่เจอ จะกำหนดให้เป็น อาชีพ อื่นๆ
						career = customerService.findCareerByCareerName("อื่นๆ");
						if(null != career){
							customer.setCareer(career);
						}
					}
	
					Contact contact = new Contact();
					contact.setMobile(bean.getMobile());
					contact.setFax(bean.getFax());
					contact.setEmail(bean.getEmail());
					contact.setCreateDate(CURRENT_TIMESTAMP);
					contact.setCreatedBy("Migrate_MC");
					contact.setCustomer(customer);
					customer.setContact(contact);
					
					//customerFeature
					CustomerFeature customerFeature = customerService.findCustomerFeatureById(Long.valueOf(bean.getCustomerFeatures()));
					if(null != customerFeature){
						customer.setCustomerFeature(customerFeature);
					}
					
					customer.setCreateDate(new Date());
					customer.setCreatedBy("Migrate_MC");
					customer.setDeleted(Boolean.FALSE);
										
					if ("3.0".equals(cutStatus) && null != dateCut ) {
						customer.setActive(Boolean.FALSE);
					}else{
						customer.setActive(Boolean.TRUE);
					}
					
					customerService.save(customer);
					
					serviceApplication = new ServiceApplication();
//					serviceApplication.setRemarkOtherDocuments(bean.getRemark());
					serviceApplication.setServiceApplicationNo(serviceApplicationService.genServiceApplicationCode());
					serviceApplication.setCustomer(customer);
					
					serviceApplication.setPlat(bean.getPlat());
					
					if ("3.0".equals(cutStatus) && null != dateCut ) {
						serviceApplication.setCancelServiceDate(dateCut);
						serviceApplication.setStatus("I");
					}else{
						serviceApplication.setStatus("A");
					}
					
					int perMounth = 1;
					
					if("2 เดือน".equals(bean.getServicePackage())){
						perMounth = 2;
					}else if("30 เดือน".equals(bean.getServicePackage())){
						perMounth = 30;
					}else if("ราย 3 เดือน".equals(bean.getServicePackage())){
						perMounth = 3;
					}else if("ราย 4 เดือน".equals(bean.getServicePackage())){
						perMounth = 4;
					}else if("ราย 4 ปี".equals(bean.getServicePackage())){
						perMounth = 48;
					}else if("รายเดิอน 15 เดือน".equals(bean.getServicePackage())){
						perMounth = 15;
					}else if("รายครึ่งปี".equals(bean.getServicePackage())){
						perMounth = 6;
					}else if("รายปี".equals(bean.getServicePackage())){
						perMounth = 12;
					}else if("สมาชิกราย 14 เดือน".equals(bean.getServicePackage())){
						perMounth = 14;
					}else if("สมาชิกราย 16 เดือน".equals(bean.getServicePackage())){
						perMounth = 16;
					}else if("สมาชิกราย 18 เดือน".equals(bean.getServicePackage())){
						perMounth = 18;
					}else if("สมาชิกราย 19 เดือน".equals(bean.getServicePackage())){
						perMounth = 19;
					}else if("สมาชิกราย 2 เดือน".equals(bean.getServicePackage())){
						perMounth = 2;
					}else if("สมาชิกราย 2 ปี".equals(bean.getServicePackage())){
						perMounth = 24;
					}else if("สมาชิกราย 20 เดือน".equals(bean.getServicePackage())){
						perMounth = 20;
					}else if("สมาชิกราย 27 เดือน".equals(bean.getServicePackage())){
						perMounth = 27;
					}else if("สมาชิกราย 3 เดือน".equals(bean.getServicePackage())){
						perMounth = 3;
					}else if("สมาชิกราย 3 ปี".equals(bean.getServicePackage())){
						perMounth = 36;
					}else if("สมาชิกราย 4 เดือน".equals(bean.getServicePackage())){
						perMounth = 4;
					}else if("สมาชิกราย 5 ปี".equals(bean.getServicePackage())){
						perMounth = 60;
					}else if("สมาชิกราย 8 เดือน".equals(bean.getServicePackage())){
						perMounth = 8;
					}else if("สมาชิกราย15 เดือน".equals(bean.getServicePackage())){
						perMounth = 15;
					}else if("สมาชิกราย7เดือน".equals(bean.getServicePackage())){
						perMounth = 7;
					}else if("สมาชิกรายครึ่งปี".equals(bean.getServicePackage())){
						perMounth = 6;
					}else if("สมาชิกรายปี".equals(bean.getServicePackage())){
						perMounth = 12;
					}
					
					if("0.0".equals(bean.getServiceApplicationTypeCode())){
						perMounth = 0;
					}
					
					ServicePackage servicePackage = servicePackageService.getServicePackageByName(bean.getServicePackage()+"_"+bean.getBillingFee());
					if(null != servicePackage){
						serviceApplication.setServicePackage(servicePackage);
					}else{
						servicePackage = new ServicePackage();
						servicePackage.setDeposit(0);
						servicePackage.setFirstBillFree(0);
						servicePackage.setFirstBillFreeDisCount(0);
						servicePackage.setInstallationFee(0);
						servicePackage.setActive(true);
						servicePackage.setDeleted(false);
						servicePackage.setMonthlyService(true);
						servicePackage.setMonthlyServiceFee(StringUtils.isBlank(bean.getBillingFee())?0f:Float.valueOf(bean.getBillingFee()));
						servicePackage.setOneServiceFee(0);
						servicePackage.setPackageCode(servicePackageService.genServicePackageCode());
						servicePackage.setPackageName(bean.getServicePackage()+"_"+bean.getBillingFee());
						servicePackage.setPerMounth(perMounth);
						servicePackage.setCompany(companyService.getCompanyById(1L));
						
						Long servicePackageTypeId = 1L; // เคเบิล
						if(0 == perMounth){ // กล้องวงจรปิด(จากระบบเก่า)
							servicePackageTypeId = 2L; // อินเตอร์เน็ต
						}
						
						servicePackage.setServicePackageType(servicePackageTypeService.getServicePackageTypeById(servicePackageTypeId));
						
						TemplateService templateService = new TemplateService();
						templateService.setAmount(0);
						templateService.setDeleted(false);
						templateService.setCreateDate(CURRENT_TIMESTAMP);
						templateService.setCreatedBy("Migrate_MC");
						
						servicePackage.setTemplateService(templateService);
						
						servicePackage.setCreateDate(CURRENT_TIMESTAMP);
						servicePackage.setCreatedBy("Migrate_MC");
						
						servicePackageService.save(servicePackage);
						serviceApplication.setServicePackage(servicePackage);
					}
					
					if(0 == perMounth){ // กล้องวงจรปิด(จากระบบเก่า)
						serviceApplication.setMonthlyService(false);
						serviceApplication.setOneServiceFee(StringUtils.isBlank(bean.getBillingFee())?0f:Float.valueOf(bean.getBillingFee()));
					}else{
						serviceApplication.setMonthlyService(true); // รายเดือน
						serviceApplication.setMonthlyServiceFee(StringUtils.isBlank(bean.getBillingFee())?0f:Float.valueOf(bean.getBillingFee()));
					}
					
					serviceApplication.setInstallationFee(0);
					serviceApplication.setDeposit(StringUtils.isBlank(bean.getDeposit())?0f:Float.valueOf(bean.getDeposit()));
					serviceApplication.setFirstBillFree(0);
					serviceApplication.setFirstBillFreeDisCount(0);
					
					serviceApplication.setPerMonth(perMounth);
					
					serviceApplication.setHouseRegistrationDocuments(Boolean.FALSE);
					serviceApplication.setIdentityCardDocuments(Boolean.FALSE);
					serviceApplication.setOtherDocuments(Boolean.FALSE);
					
					serviceApplication.setRemark(bean.getRemark());
					if(null != bean.getServiceApplicationDate() ){
						serviceApplication.setStartDate(bean.getServiceApplicationDate());
					}
					
//					serviceApplication.setPlateNumber(serviceApplicationBean.getPlateNumber());
//					serviceApplication.setLatitude(serviceApplicationBean.getLatitude());
//					serviceApplication.setLongitude(serviceApplicationBean.getLongitude());
					
					ServiceApplicationType serviceApplicationType = 
							serviceApplicationService.getServiceApplicationTypeByName(bean.getServiceApplicationType());
					// serviceApplicationType
					if(null != serviceApplicationType){
						serviceApplication.setServiceApplicationType(serviceApplicationType);
					}else{
						serviceApplicationType = new ServiceApplicationType();
						serviceApplicationType.setServiceApplicationTypeCode("SA"+bean.getServiceApplicationTypeCode());
						serviceApplicationType.setServiceApplicationTypeName(bean.getServiceApplicationType());
						serviceApplicationService.save(serviceApplicationType);
						serviceApplication.setServiceApplicationType(serviceApplicationType);
					}
					serviceApplication.setDeleted(Boolean.FALSE);
					serviceApplication.setCreateDate(CURRENT_TIMESTAMP);
					serviceApplication.setCreatedBy("Migrate_MC");
					
					serviceApplicationService.save(serviceApplication);
					
					for(int i = 1; i <= 5; i++){
						Address address = new Address();
						address.setAddressType(""+i);
						address.setNo(bean.getNo());
						address.setSection(bean.getSection());
						address.setBuilding(bean.getBuilding());
						address.setRoom(bean.getRoom());
						address.setFloor(bean.getFloor());
						address.setVillage(bean.getVillage());
						address.setAlley(bean.getAlley());
						address.setRoad(bean.getRoad());
						
						Province provinceModel = addressService.getProvinceById(Long.valueOf(bean.getProvince()));
						address.setProvinceModel(provinceModel);
						
						Amphur amphur = addressService.getAmphurById(Long.valueOf(bean.getAmphur()));
						address.setAmphur(amphur);
						
//						District districtModel = addressService.getDistrictByDistrictName(bean.getDistrict());
//						if(null != districtModel){
//							address.setDistrictModel(districtModel);
//						}else{// ถ้า null จะกำหนดให้เป็น มหาชัย
//							districtModel = addressService.getDistrictByDistrictName("มหาชัย");
//							if(null != districtModel){
//								address.setDistrictModel(districtModel);
//							}
//						}
						
						District districtModel = addressService.getDistrictById(Long.valueOf(bean.getDistrict()));
						if(null != districtModel){
							address.setDistrictModel(districtModel);
						}
						
						address.setPostcode(""+Math.round(Float.valueOf(bean.getPostcode())));
						address.setNearbyPlaces(bean.getNearbyPlaces());

						String zoneDetail = bean.getZone(), zoneName = "";
//						String[] zoneArray = bean.getZone().split(" ");
//						if (zoneArray.length > 1) {
//							zoneName = zoneArray[0];
//							int j = 0;
//							for (String z : zoneArray) {
//								if ((j++) > 0) {
//									zoneDetail = zoneDetail + " " + z;
//								}
//							}
//						}
						logger.info("zoneDetail : "+zoneDetail);
						Zone zone = zoneService.getZoneByZoneDetail(zoneDetail);
						if(null != zone){
							address.setZone(zone);
						}else{
							zone = new Zone();
							zone.setZoneDetail(zoneDetail);
							zone.setZoneName(zoneDetail);
							zone.setZoneType("-");
							zone.setCreateDate(CURRENT_TIMESTAMP);
							zone.setCreatedBy("Migrate_MC");
							zoneService.save(zone);
							
							address.setZone(zone);
						}
						address.setOverrideAddressId(1L); // อ้างอิงจากที่อยู่ 1
						if(i==1 || i == 2){
							address.setCustomer(customer);
							address.setServiceApplication(serviceApplication);
						}else if(i==3 || i == 4 || i == 5){
							address.setServiceApplication(serviceApplication);
						}
						
						address.setCreateDate(CURRENT_TIMESTAMP);
						address.setCreatedBy("Migrate_MC");
						addressService.save(address);	
					}
					
					Worksheet worksheet = new Worksheet();
					WorksheetSetup worksheetSetup = new WorksheetSetup();
					worksheetSetup.setCreateDate(CURRENT_TIMESTAMP);
					worksheetSetup.setCreatedBy("Migrate_MC");
					worksheetSetup.setDeleted(Boolean.FALSE);
					worksheetSetup.setWorkSheet(worksheet);
					worksheet.setWorkSheetCode(workSheetService.genWorkSheetCode());
					
					if(null != bean.getDateOrderBill() ){
						worksheet.setDateOrderBill(bean.getDateOrderBill());
					}

					worksheet.setServiceApplication(serviceApplication);
					worksheet.setWorkSheetType(messageSource.getMessage("worksheet.type.cable.setup", null, LocaleContextHolder.getLocale()));
					worksheet.setStatus(messageSource.getMessage("worksheet.status.value.s", null, LocaleContextHolder.getLocale()));
					worksheet.setWorksheetSetup(worksheetSetup);
					worksheet.setCreateDate(CURRENT_TIMESTAMP);
					worksheet.setCreatedBy("Migrate_MC");
					worksheet.setDeleted(Boolean.FALSE);
					
					// set historyTechnicianGroupWorks <!-- วันที่เริ่มใช้บริการ -->
					if(null != bean.getServiceApplicationDate() ){
						serviceApplication.setStartDate(bean.getServiceApplicationDate());
						List<HistoryTechnicianGroupWork> historyTechnicianGroupWorks = new ArrayList<HistoryTechnicianGroupWork>();
						HistoryTechnicianGroupWork his = new HistoryTechnicianGroupWork();
						his.setCreateDate(CURRENT_TIMESTAMP);
						his.setCreatedBy("Migrate_MC");
						his.setAssignDate(bean.getServiceApplicationDate());
						his.setWorkSheet(worksheet);
						historyTechnicianGroupWorks.add(his);
						worksheet.setHistoryTechnicianGroupWorks(historyTechnicianGroupWorks);
					}
					
					//save worksheet
					workSheetService.save(worksheet);
					
					ProductItem productItem = new ProductItem();
					productItem.setServiceProduct(serviceProductService.getSerivceProductByCode("00002")); // ค่าติดตั้งจุด Digital
					productItem.setServiceApplication(serviceApplication);
					productItem.setWorkSheet(worksheet);
//					productItem.setQuantity(StringUtils.isBlank(bean.getDigitalPoint())?0:Math.round(Float.valueOf(bean.getDigitalPoint())));
					productItem.setQuantity(StringUtils.isBlank(bean.getTotalPoint())?0:Math.round(Float.valueOf(bean.getTotalPoint())));
					productItem.setPrice(StringUtils.isBlank(bean.getDigitalPrice())?0f:Float.valueOf(bean.getDigitalPrice()));
					productItem.setAmount(StringUtils.isBlank(bean.getDigitalPrice())?0f:Float.valueOf(bean.getDigitalPrice()));
					productItem.setProductTypeMatch("O");
					productItem.setProductType("S");
					productItem.setDeposit(0f);
					
					logger.info("Name : "+bean.getFirstName()+" "+bean.getLastName());
					logger.info("StatusDigital : "+bean.getStatusDigital());
					productItem.setIs_Deposit(false);
					if("มัดจำ".equals(bean.getStatusDigital())){
						productItem.setIs_Deposit(true);
					}else if("ยืม".equals(bean.getStatusDigital())){																																																																																																																																																																																													
						productItem.setLend(true);
					} // ถ้า Deposit และ Lend เท่ากับ false สถานะเท่ากันขายขาด
					
					// วันที่ติดตั้ง Digital
					if(null != bean.getInstallDigital() && !"".equals(bean.getInstallDigital()) && !"0000-00-00".equals(bean.getInstallDigital())){
						productItem.setInstallDigital(formatData.parse(bean.getInstallDigital()));
					}
					
					productItem.setCreateDate(CURRENT_TIMESTAMP);
					productItem.setCreatedBy("Migrate_MC");
					productItem.setFree(Boolean.FALSE);
					productItem.setDeleted(Boolean.FALSE);
					productItemService.save(productItem);
					
//					productItem = new ProductItem();
//					productItem.setServiceProduct(serviceProductService.getSerivceProductByCode("00003")); // ค่าติดตั้งจุด Analog
//					productItem.setServiceApplication(serviceApplication);
//					productItem.setWorkSheet(worksheet);
//					productItem.setQuantity(StringUtils.isBlank(bean.getAnalogPoint())?0:Math.round(Float.valueOf(bean.getAnalogPoint())));
//					productItem.setPrice(StringUtils.isBlank(bean.getAnalogPrice())?0f:Float.valueOf(bean.getAnalogPrice()));
//					productItem.setAmount(StringUtils.isBlank(bean.getAnalogPrice())?0f:Float.valueOf(bean.getAnalogPrice()));
//					productItem.setProductTypeMatch("O");
//					productItem.setProductType("S");
//					productItem.setDeposit(0f);
//					productItem.setCreateDate(CURRENT_TIMESTAMP);
//					productItem.setCreatedBy("Migrate_MC");
//					productItem.setFree(Boolean.FALSE);
//					productItem.setLend(Boolean.FALSE);
//					productItem.setDeleted(Boolean.FALSE);
//					productItemService.save(productItem);
					
					// invoice
					if(null != bean.getDatePayment()){
						Invoice invoice = new Invoice();
						invoice.setInvoiceCode(financialService.genInVoiceCode());
						invoice.setServiceApplication(serviceApplication);
						invoice.setInvoiceType("O"); //S=สำหรับติดตั้ง,R=สำหรับซ่อม,O=สำหรับตาม
						invoice.setStatus("W"); //W=รอลูกค้าชำระ,S=ชำระแล้ว,O=เกินวันกำหนดชำระ
						invoice.setStatusScan("N");
						invoice.setIssueDocDate(new Date());
						invoice.setPaymentDate(bean.getDatePayment());
						invoice.setCreateDate(bean.getDatePayment());
	
						Date currentDate = new Date();
						if(invoice.getCreateDate().before(currentDate)){
							invoice.setStatus("O"); // O=เกินวันกำหนดชำระ 
						}
						
						float amount = 0f;
						if(serviceApplication.isMonthlyService()){
							amount += serviceApplication.getMonthlyServiceFee();
						}else{
							amount += serviceApplication.getOneServiceFee();
						}
	
						invoice.setAmount(amount);
						invoice.setBilling(Boolean.FALSE);
						invoice.setCutting(Boolean.FALSE);
						invoice.setDeleted(Boolean.FALSE);
						invoice.setCreatedBy("Migrate_MC");
						financialService.saveInvoice(invoice);
						
						Receipt receipt = new Receipt();
						receipt.setReceiptCode(financialService.genReceiptCode());
						receipt.setStatus("H");
						receipt.setInvoice(invoice);
						receipt.setCreatedBy("Migrate_MC");
						receipt.setCreateDate(CURRENT_TIMESTAMP);
						receipt.setDeleted(Boolean.FALSE);
						financialService.saveReceipt(receipt);
					}
					
				}else{
					List<ServiceApplication> serviceApplicationList = customerOld.getServiceApplications();
					if(null != serviceApplicationList && serviceApplicationList.size() > 0){
						serviceApplication = serviceApplicationList.get(0);
						// invoice
						if(null != bean.getDatePayment()){
							Invoice invoice = new Invoice();
							invoice.setInvoiceCode(financialService.genInVoiceCode());
							invoice.setServiceApplication(serviceApplication);
							invoice.setInvoiceType("O"); //S=สำหรับติดตั้ง,R=สำหรับซ่อม,O=สำหรับตาม
							invoice.setStatus("W"); //W=รอลูกค้าชำระ,S=ชำระแล้ว,O=เกินวันกำหนดชำระ
							invoice.setStatusScan("N");
							invoice.setIssueDocDate(new Date());
							invoice.setPaymentDate(bean.getDatePayment());
							invoice.setCreateDate(bean.getDatePayment());
		
							Date currentDate = new Date();
							if(invoice.getCreateDate().before(currentDate)){
								invoice.setStatus("O"); // O=เกินวันกำหนดชำระ 
							}
							
							float amount = 0f;
							if(serviceApplication.isMonthlyService()){
								amount += serviceApplication.getMonthlyServiceFee();
							}else{
								amount += serviceApplication.getOneServiceFee();
							}
		
							invoice.setAmount(amount);
							invoice.setBilling(Boolean.FALSE);
							invoice.setCutting(Boolean.FALSE);
							invoice.setDeleted(Boolean.FALSE);
							invoice.setCreatedBy("Migrate_MC");
							financialService.saveInvoice(invoice);
							
							Receipt receipt = new Receipt();
							receipt.setReceiptCode(financialService.genReceiptCode());
							receipt.setStatus("H");
							receipt.setInvoice(invoice);
							receipt.setCreatedBy("Migrate_MC");
							receipt.setCreateDate(CURRENT_TIMESTAMP);
							receipt.setDeleted(Boolean.FALSE);
							financialService.saveReceipt(receipt);
						}
					}
				}
				
			}
			
			logger.info("MigrateCustomer New Total : "+indexNew);
			long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			int seconds = (int) (totalTime / 1000) % 60 ;
			int minutes = (int) ((totalTime / (1000*60)) % 60);
			int hours   = (int) ((totalTime / (1000*60*60)) % 24);
			logger.info("totalTime : "+String.format("%d hour, %d min, %d sec", hours,minutes,seconds));
		}
	}
	
	private void processMergeCustomer(Map<String, List<Customer>> map) throws Exception {
		int index = 1;
		int countMerge = 0;
		if(null != map && map.size() > 0){
			for(String key:map.keySet()){
				logger.info("key "+(index++)+" : "+key);
				List<Customer> customerData = map.get(key);
				if(null != customerData && customerData.size() > 0 ){
			    	Collections.sort(customerData, new Comparator<Customer>() { // sort นามสกุล
				        public int compare(final Customer object1, final Customer object2) {
						return object1.getLastName().compareTo(object2.getLastName());
						}
					});
					String lastNameMain = customerData.get(0).getLastName();
					Customer customerMain = customerData.get(0);
					Boolean isMain = true;
					if(customerData.size() > 1){
					for(int i=0; i < customerData.size(); i++){
						Customer customer = customerData.get(i);
						Pattern pattern = Pattern.compile(lastNameMain);
						Matcher matcher = pattern.matcher(customer.getLastName());
						logger.info(">>> : "+customer.getFirstName()+" "+customer.getLastName()+" | "+lastNameMain);
						if(matcher.lookingAt() || lastNameMain.equals(customer.getLastName())){
							logger.info("matcher.lookingAt()");
							logger.info("isMain : "+isMain);
							if(isMain){
								isMain = false;	
							}else{
								List<ServiceApplication> serviceApplicationList = customer.getServiceApplications();
								if(null != serviceApplicationList && serviceApplicationList.size() > 0){
									for(ServiceApplication serviceApplication:serviceApplicationList){
										serviceApplication.setCustomer(customerMain);
										serviceApplication.setUpdatedDate(CURRENT_TIMESTAMP);
										serviceApplication.setUpdatedBy("MergeCustomer");
										serviceApplicationService.update(serviceApplication);
									}
									customer.setDeleted(Boolean.TRUE);
									customer.setUpdatedDate(CURRENT_TIMESTAMP);
									customer.setUpdatedBy("MergeCustomer");
									customerService.update(customer);
									countMerge++;
								}
							}
						}else{
							logger.info("isMain : "+isMain);
							logger.info("!matcher.lookingAt()");
							i--;
							lastNameMain = customer.getLastName();
							customerMain = customer;
							isMain = true;	
						}
					}
					}
				}
			}
		}
		logger.info("countMerge : "+countMerge);
	}
	
	public void generateAlert(JsonResponse jsonResponse, HttpServletRequest request, String title, String detail){
		new AlertUtil().generateAlert(jsonResponse, request, title, detail);
	}
	
	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

}
