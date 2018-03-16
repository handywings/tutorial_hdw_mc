package com.hdw.mccable.controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hdw.mccable.Manager.JasperJrxmlComponent;
import com.hdw.mccable.Manager.JasperRender;
import com.hdw.mccable.Manager.ParamsEnum;
import com.hdw.mccable.dto.CompanyBean;
import com.hdw.mccable.dto.EquipmentProductBean;
import com.hdw.mccable.dto.EquipmentProductCategoryBean;
import com.hdw.mccable.dto.ReportRequisitionproductBean;
import com.hdw.mccable.dto.ReportStockBean;
import com.hdw.mccable.dto.ReportStockSummaryBean;
import com.hdw.mccable.dto.StockBean;
import com.hdw.mccable.entity.EquipmentProduct;
import com.hdw.mccable.entity.EquipmentProductCategory;
import com.hdw.mccable.entity.EquipmentProductItem;
import com.hdw.mccable.entity.Personnel;
import com.hdw.mccable.entity.RequisitionDocument;
import com.hdw.mccable.entity.RequisitionItem;
import com.hdw.mccable.entity.Stock;
import com.hdw.mccable.entity.TechnicianGroup;
import com.hdw.mccable.service.EquipmentProductCategoryService;
import com.hdw.mccable.service.ProductService;
import com.hdw.mccable.service.StockService;

@Controller
@RequestMapping("/stockreport")
public class StockReportController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(StockReportController.class);
	
	@Autowired(required = true)
	@Qualifier(value = "equipmentProductCategoryService")
	private EquipmentProductCategoryService equipmentProductCategoryService;
	
	@Autowired(required = true)
	@Qualifier(value = "stockService")
	private StockService stockService;
	
	@Autowired(required = true)
	@Qualifier(value = "productService")
	private ProductService productService;
	
	@Autowired(required = true)
	@Qualifier(value = "jasperJrxmlComponent")
	private JasperJrxmlComponent jasperJrxmlComponent;
	
	@Autowired
	MessageSource messageSource;
	
	@RequestMapping(value = "productimport", method = RequestMethod.GET)
	public ModelAndView productimport(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : productimport][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView(); 
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
		if (isPermission()) {
			// dropdown equipmentCategory for search only
			EquipmentProductCategoryController epcController = new EquipmentProductCategoryController();
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
			
			ProductAddController proAddCont = new ProductAddController();
			proAddCont.setMessageSource(messageSource);
			List<EquipmentProductBean> equipmentProductBeans = new ArrayList<EquipmentProductBean>();
			List<EquipmentProduct> equipmentProductList = productService.findAllSupplier();
			// transfer entity to bean
			for (EquipmentProduct ep : equipmentProductList) {
				EquipmentProductBean epBean = new EquipmentProductBean();
				epBean = proAddCont.populateEntityToDto(ep);
				equipmentProductBeans.add(epBean);
			}
			modelAndView.addObject("suppliers", equipmentProductBeans);

		} else {
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		modelAndView.setViewName((String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE));
		return modelAndView;
	}
	
	@RequestMapping(value = "productimport/exportPdf/reportrange/{reportrange}/"
			+ "purchasingNumber/{purchasingNumber}/equipmentProductType/{equipmentProductType}/"
			+ "equipmentStock/{equipmentStock}/supplier/{supplier}/split/{split}/sort/{sort}", method = RequestMethod.GET)
	public ModelAndView productimportExportPdf(Model model,
			@PathVariable String reportrange,
			@PathVariable String purchasingNumber,
			@PathVariable String equipmentProductType,
			@PathVariable String equipmentStock,
			@PathVariable String supplier,
			@PathVariable String split, 
			@PathVariable String sort,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("[method : productimportExportPdf][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView(); 
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission
		if(isPermission()){
		logger.info("reportrange : {}, purchasingNumber : {}, equipmentProductType : {}, equipmentStock : {}, "
				+ "supplier : {}, split : {}, sort : {}",reportrange,purchasingNumber,equipmentProductType,
				equipmentStock,supplier,split,sort);
		String title = "";
		List<Object> resUse = new ArrayList<Object>();
		reportrange = reportrange.substring(2);
		List<EquipmentProduct> equipmentProductList = productService.getDataProductForReport(reportrange,purchasingNumber,equipmentProductType,equipmentStock,supplier,split,sort);
		logger.info("equipmentProductList size : {}",equipmentProductList.size());
		String startDate = reportrange.split(" - ")[0];
		String endDate = reportrange.split(" - ")[1];
		SimpleDateFormat formatMMMMyyyy = new SimpleDateFormat("MMMM yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataTh = new SimpleDateFormat("dd-MMMM-yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataEngRange = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
		SimpleDateFormat formatDataThRange = new SimpleDateFormat("วันที่ dd เดือน MMMM ปี yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataTimeTh = new SimpleDateFormat("พิมพ์เมื่อวันที่ dd เดือน MMMM ปี yyyy เมื่อเวลา HH:mm น.", new Locale("TH", "th"));
		JasperRender jasperRender = new JasperRender();
		if(null != equipmentProductList && equipmentProductList.size() > 0){

			ReportStockBean reportStockBean = new ReportStockBean();
			float sumPrice = 0f;
			int no = 1, sumAmount = 0;
			if("1".equals(split)){ // รายการสินค้านำเข้า (รายการนำเข้าสินค้าตามวันสั่งซื้อ)
				List<ReportStockBean> reportStockBeanList = setReportStockBean(equipmentProductList, sort);
				
				logger.info("reportStockBeanList size : {}",reportStockBeanList.size());
				if(null != reportStockBeanList && reportStockBeanList.size() > 0){

					title = "รายการสินค้านำเข้า (รายการนำเข้าสินค้าตามวันสั่งซื้อ)";
					
					reportStockBean = new ReportStockBean();
					reportStockBean.setNoText("ลำดับ");
					reportStockBean.setImportSystemDateText("วันที่สั่งซื้อ");
					reportStockBean.setReferenceText("ใบสั่งซื้อ");
					reportStockBean.setSupplierText("ผู้จำหน่าย");
					reportStockBean.setProductNameText("สินค้า");
					reportStockBean.setProductCategoryText("หมวดหมู่");
					reportStockBean.setStockText("คลังสินค้า");
					reportStockBean.setAmountText("จำนวน");
					reportStockBean.setPricePerUnitText("ราคาต่อหน่วย");
					reportStockBean.setPricePerUnitTotalText("มูลค่ารวม");
					reportStockBean.setCheckHeaderTable(true);
					reportStockBean.setCheckSum(false);
					reportStockBean.setCheckGroupData(false);
					resUse.add(reportStockBean);
					
					for(int i = 0; reportStockBeanList.size() > i; i++){
						
						reportStockBean = new ReportStockBean();
						reportStockBean.setNo(no++);
						reportStockBean.setImportSystemDate(reportStockBeanList.get(i).getImportSystemDate());
						reportStockBean.setReference(reportStockBeanList.get(i).getReference());
						reportStockBean.setSupplier(reportStockBeanList.get(i).getSupplier());
						reportStockBean.setProductName(reportStockBeanList.get(i).getProductName());
						reportStockBean.setProductCategory(reportStockBeanList.get(i).getProductCategory());
						reportStockBean.setStock(reportStockBeanList.get(i).getStock());
						reportStockBean.setAmount(reportStockBeanList.get(i).getAmount());
						reportStockBean.setPricePerUnit(reportStockBeanList.get(i).getPricePerUnit());
						reportStockBean.setPricePerUnitTotal(reportStockBeanList.get(i).getPricePerUnitTotal());
						reportStockBean.setCheckHeaderTable(false);
						reportStockBean.setCheckSum(false);
						reportStockBean.setCheckGroupData(false);
						resUse.add(reportStockBean);
						Number numberAmount, numberPrice;
						try {
							numberAmount = NumberFormat.getNumberInstance(Locale.UK).parse(reportStockBeanList.get(i).getAmount());
							sumAmount += numberAmount.intValue();
							numberPrice = NumberFormat.getNumberInstance(Locale.UK).parse(reportStockBeanList.get(i).getPricePerUnitTotal());
							sumPrice += numberPrice.intValue();
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
							
							if((reportStockBeanList.size()-1) == i){
								reportStockBean = new ReportStockBean();
								reportStockBean.setSumAmountText("รวมจำนวน");
								reportStockBean.setSumAmount(new DecimalFormat("#,###").format(sumAmount));
								reportStockBean.setSumPriceText("มูลค่าทั้งสิ้น");
								reportStockBean.setSumPrice(new DecimalFormat("#,##0.00").format(sumPrice));
								reportStockBean.setCheckHeaderTable(false);
								reportStockBean.setCheckSum(true);
								reportStockBean.setCheckGroupData(false);
								resUse.add(reportStockBean);
							}
					}
				}
				
			}else if("2".equals(split)){ // รายการสินค้านาเข้า (รายการนาเข้าแยกตามบริษัทผู้จาหน่าย)
				List<ReportStockBean> reportStockBeanList = setReportStockBean(equipmentProductList, sort);
				
				if (reportStockBeanList.size() > 0) {
					  Collections.sort(reportStockBeanList, new Comparator<ReportStockBean>() {
					      public int compare(final ReportStockBean object1, final ReportStockBean object2) {
					          return object1.getSupplier().compareTo(object2.getSupplier());
					      }
					  });
				}
				
				logger.info("reportStockBeanList size : {}",reportStockBeanList.size());
				if(null != reportStockBeanList && reportStockBeanList.size() > 0){
					
					String supplierMain = reportStockBeanList.get(0).getSupplier();
					title = "รายการสินค้านำเข้า (รายการนำเข้าแยกตามบริษัทผู้จำหน่าย)";
					
					reportStockBean = new ReportStockBean();
					reportStockBean.setGroupData("บริษัทผู้จำหน่าย"+supplierMain);
					reportStockBean.setCheckHeaderTable(false);
					reportStockBean.setCheckSum(false);
					reportStockBean.setCheckGroupData(true);
					resUse.add(reportStockBean);
					
					reportStockBean = new ReportStockBean();
					reportStockBean.setNoText("ลำดับ");
					reportStockBean.setImportSystemDateText("วันที่สั่งซื้อ");
					reportStockBean.setReferenceText("ใบสั่งซื้อ");
					reportStockBean.setSupplierText("ผู้จำหน่าย");
					reportStockBean.setProductNameText("สินค้า");
					reportStockBean.setProductCategoryText("หมวดหมู่");
					reportStockBean.setStockText("คลังสินค้า");
					reportStockBean.setAmountText("จำนวน");
					reportStockBean.setPricePerUnitText("ราคาต่อหน่วย");
					reportStockBean.setPricePerUnitTotalText("มูลค่ารวม");
					reportStockBean.setCheckHeaderTable(true);
					reportStockBean.setCheckSum(false);
					reportStockBean.setCheckGroupData(false);
					resUse.add(reportStockBean);
					
					for(int i = 0; reportStockBeanList.size() > i; i++){
						if(!supplierMain.equals(reportStockBeanList.get(i).getSupplier())){
							
							reportStockBean = new ReportStockBean();
							reportStockBean.setSumAmountText("รวมจำนวน");
							reportStockBean.setSumAmount(new DecimalFormat("#,###").format(sumAmount));
							reportStockBean.setSumPriceText("มูลค่าทั้งสิ้น");
							reportStockBean.setSumPrice(new DecimalFormat("#,##0.00").format(sumPrice));
							reportStockBean.setCheckHeaderTable(false);
							reportStockBean.setCheckSum(true);
							reportStockBean.setCheckGroupData(false);
							resUse.add(reportStockBean);
							
							supplierMain = reportStockBeanList.get(i).getSupplier();
							
							reportStockBean = new ReportStockBean();
							reportStockBean.setGroupData("บริษัทผู้จำหน่าย"+supplierMain);
							reportStockBean.setCheckHeaderTable(false);
							reportStockBean.setCheckSum(false);
							reportStockBean.setCheckGroupData(true);
							resUse.add(reportStockBean);
							
							reportStockBean = new ReportStockBean();
							reportStockBean.setNoText("ลำดับ");
							reportStockBean.setImportSystemDateText("วันที่สั่งซื้อ");
							reportStockBean.setReferenceText("ใบสั่งซื้อ");
							reportStockBean.setSupplierText("ผู้จำหน่าย");
							reportStockBean.setProductNameText("สินค้า");
							reportStockBean.setProductCategoryText("หมวดหมู่");
							reportStockBean.setStockText("คลังสินค้า");
							reportStockBean.setAmountText("จำนวน");
							reportStockBean.setPricePerUnitText("ราคาต่อหน่วย");
							reportStockBean.setPricePerUnitTotalText("มูลค่ารวม");
							reportStockBean.setCheckHeaderTable(true);
							reportStockBean.setCheckSum(false);
							reportStockBean.setCheckGroupData(false);
							resUse.add(reportStockBean);
							
							sumAmount = 0;
							sumPrice = 0;
							no = 1;
							--i;
							
							continue;
						}
						
						reportStockBean = new ReportStockBean();
						reportStockBean.setNo(no++);
						reportStockBean.setImportSystemDate(reportStockBeanList.get(i).getImportSystemDate());
						reportStockBean.setReference(reportStockBeanList.get(i).getReference());
						reportStockBean.setSupplier(reportStockBeanList.get(i).getSupplier());
						reportStockBean.setProductName(reportStockBeanList.get(i).getProductName());
						reportStockBean.setProductCategory(reportStockBeanList.get(i).getProductCategory());
						reportStockBean.setStock(reportStockBeanList.get(i).getStock());
						reportStockBean.setAmount(reportStockBeanList.get(i).getAmount());
						reportStockBean.setPricePerUnit(reportStockBeanList.get(i).getPricePerUnit());
						reportStockBean.setPricePerUnitTotal(reportStockBeanList.get(i).getPricePerUnitTotal());
						reportStockBean.setCheckHeaderTable(false);
						reportStockBean.setCheckSum(false);
						reportStockBean.setCheckGroupData(false);
						resUse.add(reportStockBean);
						Number numberAmount, numberPrice;
						try {
							numberAmount = NumberFormat.getNumberInstance(Locale.UK).parse(reportStockBeanList.get(i).getAmount());
							sumAmount += numberAmount.intValue();
							numberPrice = NumberFormat.getNumberInstance(Locale.UK).parse(reportStockBeanList.get(i).getPricePerUnitTotal());
							sumPrice += numberPrice.intValue();
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
							
							if((reportStockBeanList.size()-1) == i){
								reportStockBean = new ReportStockBean();
								reportStockBean.setSumAmountText("รวมจำนวน");
								reportStockBean.setSumAmount(new DecimalFormat("#,###").format(sumAmount));
								reportStockBean.setSumPriceText("มูลค่าทั้งสิ้น");
								reportStockBean.setSumPrice(new DecimalFormat("#,##0.00").format(sumPrice));
								reportStockBean.setCheckHeaderTable(false);
								reportStockBean.setCheckSum(true);
								reportStockBean.setCheckGroupData(false);
								resUse.add(reportStockBean);
							}
					}
				}
				
			}else if("3".equals(split)){ // รายการสินค้านำเข้า (รายการนำเข้าแยกตามหมวดหมู่สินค้า)
				List<ReportStockBean> reportStockBeanList = setReportStockBean(equipmentProductList, sort);
				
				if (reportStockBeanList.size() > 0) {
					  Collections.sort(reportStockBeanList, new Comparator<ReportStockBean>() {
					      public int compare(final ReportStockBean object1, final ReportStockBean object2) {
					          return object1.getProductCategory().compareTo(object2.getProductCategory());
					      }
					  });
				}
				
				logger.info("reportStockBeanList size : {}",reportStockBeanList.size());
				if(null != reportStockBeanList && reportStockBeanList.size() > 0){
					
					String productCategoryMain = reportStockBeanList.get(0).getProductCategory();
					title = "รายการสินค้านำเข้า (รายการนำเข้าแยกตามหมวดหมู่สินค้า)";
					
					reportStockBean = new ReportStockBean();
					reportStockBean.setGroupData("หมวดหมู่สินค้า "+productCategoryMain);
					reportStockBean.setCheckHeaderTable(false);
					reportStockBean.setCheckSum(false);
					reportStockBean.setCheckGroupData(true);
					resUse.add(reportStockBean);
					
					reportStockBean = new ReportStockBean();
					reportStockBean.setNoText("ลำดับ");
					reportStockBean.setImportSystemDateText("วันที่สั่งซื้อ");
					reportStockBean.setReferenceText("ใบสั่งซื้อ");
					reportStockBean.setSupplierText("ผู้จำหน่าย");
					reportStockBean.setProductNameText("สินค้า");
					reportStockBean.setProductCategoryText("หมวดหมู่");
					reportStockBean.setStockText("คลังสินค้า");
					reportStockBean.setAmountText("จำนวน");
					reportStockBean.setPricePerUnitText("ราคาต่อหน่วย");
					reportStockBean.setPricePerUnitTotalText("มูลค่ารวม");
					reportStockBean.setCheckHeaderTable(true);
					reportStockBean.setCheckSum(false);
					reportStockBean.setCheckGroupData(false);
					resUse.add(reportStockBean);
					
					for(int i = 0; reportStockBeanList.size() > i; i++){
						if(!productCategoryMain.equals(reportStockBeanList.get(i).getProductCategory())){
							
							reportStockBean = new ReportStockBean();
							reportStockBean.setSumAmountText("รวมจำนวน");
							reportStockBean.setSumAmount(new DecimalFormat("#,###").format(sumAmount));
							reportStockBean.setSumPriceText("มูลค่าทั้งสิ้น");
							reportStockBean.setSumPrice(new DecimalFormat("#,##0.00").format(sumPrice));
							reportStockBean.setCheckHeaderTable(false);
							reportStockBean.setCheckSum(true);
							reportStockBean.setCheckGroupData(false);
							resUse.add(reportStockBean);
							
							productCategoryMain = reportStockBeanList.get(i).getProductCategory();
							
							reportStockBean = new ReportStockBean();
							reportStockBean.setGroupData("หมวดหมู่สินค้า "+productCategoryMain);
							reportStockBean.setCheckHeaderTable(false);
							reportStockBean.setCheckSum(false);
							reportStockBean.setCheckGroupData(true);
							resUse.add(reportStockBean);
							
							reportStockBean = new ReportStockBean();
							reportStockBean.setNoText("ลำดับ");
							reportStockBean.setImportSystemDateText("วันที่สั่งซื้อ");
							reportStockBean.setReferenceText("ใบสั่งซื้อ");
							reportStockBean.setSupplierText("ผู้จำหน่าย");
							reportStockBean.setProductNameText("สินค้า");
							reportStockBean.setProductCategoryText("หมวดหมู่");
							reportStockBean.setStockText("คลังสินค้า");
							reportStockBean.setAmountText("จำนวน");
							reportStockBean.setPricePerUnitText("ราคาต่อหน่วย");
							reportStockBean.setPricePerUnitTotalText("มูลค่ารวม");
							reportStockBean.setCheckHeaderTable(true);
							reportStockBean.setCheckSum(false);
							reportStockBean.setCheckGroupData(false);
							resUse.add(reportStockBean);
							
							sumAmount = 0;
							sumPrice = 0;
							no = 1;
							--i;
							
							continue;
						}
						
						reportStockBean = new ReportStockBean();
						reportStockBean.setNo(no++);
						reportStockBean.setImportSystemDate(reportStockBeanList.get(i).getImportSystemDate());
						reportStockBean.setReference(reportStockBeanList.get(i).getReference());
						reportStockBean.setSupplier(reportStockBeanList.get(i).getSupplier());
						reportStockBean.setProductName(reportStockBeanList.get(i).getProductName());
						reportStockBean.setProductCategory(reportStockBeanList.get(i).getProductCategory());
						reportStockBean.setStock(reportStockBeanList.get(i).getStock());
						reportStockBean.setAmount(reportStockBeanList.get(i).getAmount());
						reportStockBean.setPricePerUnit(reportStockBeanList.get(i).getPricePerUnit());
						reportStockBean.setPricePerUnitTotal(reportStockBeanList.get(i).getPricePerUnitTotal());
						reportStockBean.setCheckHeaderTable(false);
						reportStockBean.setCheckSum(false);
						reportStockBean.setCheckGroupData(false);
						resUse.add(reportStockBean);
						Number numberAmount, numberPrice;
						try {
							numberAmount = NumberFormat.getNumberInstance(Locale.UK).parse(reportStockBeanList.get(i).getAmount());
							sumAmount += numberAmount.intValue();
							numberPrice = NumberFormat.getNumberInstance(Locale.UK).parse(reportStockBeanList.get(i).getPricePerUnitTotal());
							sumPrice += numberPrice.intValue();
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
							
							if((reportStockBeanList.size()-1) == i){
								reportStockBean = new ReportStockBean();
								reportStockBean.setSumAmountText("รวมจำนวน");
								reportStockBean.setSumAmount(new DecimalFormat("#,###").format(sumAmount));
								reportStockBean.setSumPriceText("มูลค่าทั้งสิ้น");
								reportStockBean.setSumPrice(new DecimalFormat("#,##0.00").format(sumPrice));
								reportStockBean.setCheckHeaderTable(false);
								reportStockBean.setCheckSum(true);
								reportStockBean.setCheckGroupData(false);
								resUse.add(reportStockBean);
							}
					}
				}
				
			}else if("4".equals(split)){ // รายการสินค้านำเข้า (รายการนาเข้าแยกตามคลังสินค้า)
				List<ReportStockBean> reportStockBeanList = setReportStockBean(equipmentProductList, sort);
				
				if (reportStockBeanList.size() > 0) {
					  Collections.sort(reportStockBeanList, new Comparator<ReportStockBean>() {
					      public int compare(final ReportStockBean object1, final ReportStockBean object2) {
					          return object1.getStock().compareTo(object2.getStock());
					      }
					  });
				}
				
				logger.info("reportStockBeanList size : {}",reportStockBeanList.size());
				if(null != reportStockBeanList && reportStockBeanList.size() > 0){
					
					String stockMain = reportStockBeanList.get(0).getStock();
					title = "รายการสินค้านำเข้า (รายการนาเข้าแยกตามคลังสินค้า)";
					
					reportStockBean = new ReportStockBean();
					reportStockBean.setGroupData("คลังสินค้า "+stockMain);
					reportStockBean.setCheckHeaderTable(false);
					reportStockBean.setCheckSum(false);
					reportStockBean.setCheckGroupData(true);
					resUse.add(reportStockBean);
					
					reportStockBean = new ReportStockBean();
					reportStockBean.setNoText("ลำดับ");
					reportStockBean.setImportSystemDateText("วันที่สั่งซื้อ");
					reportStockBean.setReferenceText("ใบสั่งซื้อ");
					reportStockBean.setSupplierText("ผู้จำหน่าย");
					reportStockBean.setProductNameText("สินค้า");
					reportStockBean.setProductCategoryText("หมวดหมู่");
					reportStockBean.setStockText("คลังสินค้า");
					reportStockBean.setAmountText("จำนวน");
					reportStockBean.setPricePerUnitText("ราคาต่อหน่วย");
					reportStockBean.setPricePerUnitTotalText("มูลค่ารวม");
					reportStockBean.setCheckHeaderTable(true);
					reportStockBean.setCheckSum(false);
					reportStockBean.setCheckGroupData(false);
					resUse.add(reportStockBean);
					
					for(int i = 0; reportStockBeanList.size() > i; i++){
						if(!stockMain.equals(reportStockBeanList.get(i).getStock())){
							
							reportStockBean = new ReportStockBean();
							reportStockBean.setSumAmountText("รวมจำนวน");
							reportStockBean.setSumAmount(new DecimalFormat("#,###").format(sumAmount));
							reportStockBean.setSumPriceText("มูลค่าทั้งสิ้น");
							reportStockBean.setSumPrice(new DecimalFormat("#,##0.00").format(sumPrice));
							reportStockBean.setCheckHeaderTable(false);
							reportStockBean.setCheckSum(true);
							reportStockBean.setCheckGroupData(false);
							resUse.add(reportStockBean);
							
							stockMain = reportStockBeanList.get(i).getStock();
							
							reportStockBean = new ReportStockBean();
							reportStockBean.setGroupData("คลังสินค้า "+stockMain);
							reportStockBean.setCheckHeaderTable(false);
							reportStockBean.setCheckSum(false);
							reportStockBean.setCheckGroupData(true);
							resUse.add(reportStockBean);
							
							reportStockBean = new ReportStockBean();
							reportStockBean.setNoText("ลำดับ");
							reportStockBean.setImportSystemDateText("วันที่สั่งซื้อ");
							reportStockBean.setReferenceText("ใบสั่งซื้อ");
							reportStockBean.setSupplierText("ผู้จำหน่าย");
							reportStockBean.setProductNameText("สินค้า");
							reportStockBean.setProductCategoryText("หมวดหมู่");
							reportStockBean.setStockText("คลังสินค้า");
							reportStockBean.setAmountText("จำนวน");
							reportStockBean.setPricePerUnitText("ราคาต่อหน่วย");
							reportStockBean.setPricePerUnitTotalText("มูลค่ารวม");
							reportStockBean.setCheckHeaderTable(true);
							reportStockBean.setCheckSum(false);
							reportStockBean.setCheckGroupData(false);
							resUse.add(reportStockBean);
							
							sumAmount = 0;
							sumPrice = 0;
							no = 1;
							--i;
							
							continue;
						}
						
						reportStockBean = new ReportStockBean();
						reportStockBean.setNo(no++);
						reportStockBean.setImportSystemDate(reportStockBeanList.get(i).getImportSystemDate());
						reportStockBean.setReference(reportStockBeanList.get(i).getReference());
						reportStockBean.setSupplier(reportStockBeanList.get(i).getSupplier());
						reportStockBean.setProductName(reportStockBeanList.get(i).getProductName());
						reportStockBean.setProductCategory(reportStockBeanList.get(i).getProductCategory());
						reportStockBean.setStock(reportStockBeanList.get(i).getStock());
						reportStockBean.setAmount(reportStockBeanList.get(i).getAmount());
						reportStockBean.setPricePerUnit(reportStockBeanList.get(i).getPricePerUnit());
						reportStockBean.setPricePerUnitTotal(reportStockBeanList.get(i).getPricePerUnitTotal());
						reportStockBean.setCheckHeaderTable(false);
						reportStockBean.setCheckSum(false);
						reportStockBean.setCheckGroupData(false);
						resUse.add(reportStockBean);
						Number numberAmount, numberPrice;
						try {
							numberAmount = NumberFormat.getNumberInstance(Locale.UK).parse(reportStockBeanList.get(i).getAmount());
							sumAmount += numberAmount.intValue();
							numberPrice = NumberFormat.getNumberInstance(Locale.UK).parse(reportStockBeanList.get(i).getPricePerUnitTotal());
							sumPrice += numberPrice.intValue();
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
							
							if((reportStockBeanList.size()-1) == i){
								reportStockBean = new ReportStockBean();
								reportStockBean.setSumAmountText("รวมจำนวน");
								reportStockBean.setSumAmount(new DecimalFormat("#,###").format(sumAmount));
								reportStockBean.setSumPriceText("มูลค่าทั้งสิ้น");
								reportStockBean.setSumPrice(new DecimalFormat("#,##0.00").format(sumPrice));
								reportStockBean.setCheckHeaderTable(false);
								reportStockBean.setCheckSum(true);
								reportStockBean.setCheckGroupData(false);
								resUse.add(reportStockBean);
							}
					}
				}
				
			}

		}
		
		jasperRender.setBeanList(resUse);
		Map<String, Object> params = new HashMap<String, Object>();
		String s = "", e = "";
		try {
			s = formatDataThRange.format(formatDataEngRange.parse(startDate));
			e = formatDataThRange.format(formatDataEngRange.parse(endDate));
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		Personnel personnel = getPersonnelLogin();
		String printBy = String.format("โดย คุณ%1s %2s (%3s) ตำแหน่ง %4s %5s",personnel.getFirstName(),personnel.getLastName(),personnel.getNickName(),personnel.getPosition().getPositionName(),personnel.getCompany().getCompanyName());
		String pathLogo = request.getSession().getServletContext().getRealPath("/jasper/logo-invoice.png");
		params.put("header", String.format("ข้อมูล %1s ถึง %2s",s,e));
		params.put("title", title);
		params.put("footer", formatDataTimeTh.format(new Date())+"\n"+printBy);
		params.put("pathLogo", pathLogo);
		
		jasperRender.setParams(params);
		//new Boolean(($V{COLUMN_COUNT}.intValue()%2) == 1)
		try {
			byte[] bytes = jasperRender.processStream(ParamsEnum.StreamType.PDF, jasperJrxmlComponent.compileJasperReport("stockreport",request));
			response.reset();
			response.resetBuffer();
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			ServletOutputStream ouputStream = response.getOutputStream();
			ouputStream.write(bytes, 0, bytes.length);
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		}else{
			//no permission
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		return null;
	}
	
	public List<ReportStockBean> setReportStockBean(List<EquipmentProduct> equipmentProductList, final String sort){
		final SimpleDateFormat formatDataTh = new SimpleDateFormat("dd MMM yyyy", new Locale("TH", "th"));
		List<ReportStockBean> reportStockBeanList = new ArrayList<ReportStockBean>();
		if(null != equipmentProductList && equipmentProductList.size() > 0){
			Long productId = equipmentProductList.get(0).getId();
			List<EquipmentProductItem> equipmentProductItemList = equipmentProductList.get(0).getEquipmentProductItems();
			int amount = 0, no = 1;
			float pricePerUnit = 0f, pricePerUnitTotal = 0f;
			String importSystemDate = " - ", reference = " - ";
			if(null != equipmentProductItemList){
				for(EquipmentProductItem equipmentProductItem:equipmentProductItemList){
					pricePerUnit = equipmentProductItem.getCost();
					amount += equipmentProductItem.getNumberImport();
					if(null != equipmentProductItem.getImportSystemDate()){
						importSystemDate = formatDataTh.format(equipmentProductItem.getImportSystemDate());
					}
					reference = equipmentProductItem.getReference();
				}
			}
			pricePerUnitTotal = pricePerUnit * amount;
			ReportStockBean reportStockBean = new ReportStockBean();
			reportStockBean.setNo(no++);
			reportStockBean.setImportSystemDate(importSystemDate);
			reportStockBean.setReference(reference);
			reportStockBean.setSupplier("".equals(equipmentProductList.get(0).getSupplier())?" - ":equipmentProductList.get(0).getSupplier());
			reportStockBean.setProductName(equipmentProductList.get(0).getProductName());
			reportStockBean.setProductCategory(equipmentProductList.get(0).getEquipmentProductCategory().getEquipmentProductCategoryName());
			reportStockBean.setStock(equipmentProductList.get(0).getStock().getStockName());
			reportStockBean.setAmount(new DecimalFormat("#,###").format(amount));
			reportStockBean.setPricePerUnit(new DecimalFormat("#,##0.00").format(pricePerUnit));
			reportStockBean.setPricePerUnitTotal(new DecimalFormat("#,##0.00").format(pricePerUnitTotal));
			reportStockBean.setCheckHeaderTable(false);
			reportStockBean.setCheckSum(false);
			reportStockBean.setCheckGroupData(false);
			reportStockBeanList.add(reportStockBean);

			for(int i = 0; equipmentProductList.size() > i; i++){
				
				if(!productId.equals(equipmentProductList.get(i).getId())){
				equipmentProductItemList = equipmentProductList.get(i).getEquipmentProductItems();
				amount = 0;
				pricePerUnit = 0f; pricePerUnitTotal = 0f;
				importSystemDate = " - "; reference = " - ";
				if(null != equipmentProductItemList){
					for(EquipmentProductItem equipmentProductItem:equipmentProductItemList){
						pricePerUnit = equipmentProductItem.getCost();
						amount += equipmentProductItem.getNumberImport();
						if(null != equipmentProductItem.getImportSystemDate()){
							importSystemDate = formatDataTh.format(equipmentProductItem.getImportSystemDate());
						}
						reference = equipmentProductItem.getReference();
					}
				}
				productId = equipmentProductList.get(i).getId();
				pricePerUnitTotal = pricePerUnit * amount;
				reportStockBean = new ReportStockBean();
				reportStockBean.setNo(no++);
				reportStockBean.setImportSystemDate(importSystemDate);
				reportStockBean.setReference(reference);
				reportStockBean.setSupplier("".equals(equipmentProductList.get(i).getSupplier())?" - ":equipmentProductList.get(i).getSupplier());
				reportStockBean.setProductName(equipmentProductList.get(i).getProductName());
				reportStockBean.setProductCategory(equipmentProductList.get(i).getEquipmentProductCategory().getEquipmentProductCategoryName());
				reportStockBean.setStock(equipmentProductList.get(i).getStock().getStockName());
				reportStockBean.setAmount(new DecimalFormat("#,###").format(amount));
				reportStockBean.setPricePerUnit(new DecimalFormat("#,##0.00").format(pricePerUnit));
				reportStockBean.setPricePerUnitTotal(new DecimalFormat("#,##0.00").format(pricePerUnitTotal));
				reportStockBean.setCheckHeaderTable(false);
				reportStockBean.setCheckSum(false);
				reportStockBean.setCheckGroupData(false);
				reportStockBeanList.add(reportStockBean);
				--i;
				}
				
			}
		}
		
		if (reportStockBeanList.size() > 0) {
			  Collections.sort(reportStockBeanList, new Comparator<ReportStockBean>() {
			      public int compare(final ReportStockBean object1, final ReportStockBean object2) {
					if("1".equals(sort)){
						try {
							return formatDataTh.parse(object2.getImportSystemDate()).compareTo(formatDataTh.parse(object1.getImportSystemDate()));
						} catch (ParseException e) {
							e.printStackTrace();
							return 0;
						}
					}else if("2".equals(sort)){
						try {
							return formatDataTh.parse(object1.getImportSystemDate()).compareTo(formatDataTh.parse(object2.getImportSystemDate()));
						} catch (ParseException e) {
							e.printStackTrace();
							return 0;
						}
					}else{
						return object1.getReference().compareTo(object2.getReference());
					}
			      }
			  });
		}
		
		return reportStockBeanList;
	}
	
	@RequestMapping(value = "requisitionproduct", method = RequestMethod.GET)
	public ModelAndView requisitionproduct(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : requisitionproduct][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView(); 
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission
		if(isPermission()){
			// dropdown equipmentCategory for search only
			EquipmentProductCategoryController epcController = new EquipmentProductCategoryController();
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
			
		}else{
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		modelAndView.setViewName((String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE));
		return modelAndView;
	}
	
	public List<ReportRequisitionproductBean> setReportRequisitionproductBean(List<RequisitionDocument> requisitionDocumentList, final String sort, final String split){
		final SimpleDateFormat formatDataTh = new SimpleDateFormat("dd MMM yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatMMMMyyyy = new SimpleDateFormat("เดือนMMMM ปี yyyy", new Locale("TH", "th"));
		List<ReportRequisitionproductBean> reportRequisitionproductBeanList = new ArrayList<ReportRequisitionproductBean>();
		if(null != requisitionDocumentList && requisitionDocumentList.size() > 0){
			for(int j = 0; requisitionDocumentList.size() > j; j++){
				List<RequisitionItem> requisitionItemList = requisitionDocumentList.get(j).getRequisitionItems();
				if(null != requisitionItemList){
					for(int i = 0; requisitionItemList.size() > i; i++){

						String withdrawalDate = formatDataTh.format(requisitionDocumentList.get(j).getCreateDate());
//						String withdrawalDateGroupData = formatMMMMyyyy.format(requisitionDocumentList.get(j).getCreateDate());
						float pricePerUnit = requisitionItemList.get(i).getEquipmentProductItem().getCost();
						float pricePerUnitTotal = requisitionItemList.get(i).getQuantity() * requisitionItemList.get(i).getEquipmentProductItem().getCost();
						
						ReportRequisitionproductBean reportRequisitionproductBean = new ReportRequisitionproductBean();
//						reportRequisitionproductBean.setGroupData(withdrawalDateGroupData);
						reportRequisitionproductBean.setRequisitionDocumentCode(requisitionDocumentList.get(j).getRequisitionDocumentCode());
						reportRequisitionproductBean.setWithdrawalDate(withdrawalDate);
						reportRequisitionproductBean.setProductName(requisitionItemList.get(i).getEquipmentProduct().getProductName());
						reportRequisitionproductBean.setProductCategory(requisitionItemList.get(i).getEquipmentProduct().getEquipmentProductCategory().getEquipmentProductCategoryName());
						reportRequisitionproductBean.setStock(requisitionItemList.get(i).getEquipmentProduct().getStock().getStockName());
						TechnicianGroup technicianGroup = requisitionDocumentList.get(j).getTechnicianGroup();
						String technicianGroupStr = " - ";
						if(null != technicianGroup){
							Personnel personnel = technicianGroup.getPersonnel();
							if(null != personnel){
								technicianGroupStr = String.format("%1s %2s (%3s)",personnel.getFirstName(),personnel.getLastName(),personnel.getNickName());
							}else{
								technicianGroupStr = requisitionDocumentList.get(j).getCreatedBy();
							}
						}else{
							technicianGroupStr = requisitionDocumentList.get(j).getCreatedBy();
						}
						reportRequisitionproductBean.setTechnicianGroup(technicianGroupStr);
						reportRequisitionproductBean.setAmount(new DecimalFormat("#,###").format(requisitionItemList.get(i).getQuantity()));
						reportRequisitionproductBean.setPricePerUnit(new DecimalFormat("#,##0.00").format(pricePerUnit));
						reportRequisitionproductBean.setPricePerUnitTotal(new DecimalFormat("#,##0.00").format(pricePerUnitTotal));
						reportRequisitionproductBeanList.add(reportRequisitionproductBean);
						
					}
				}
			}
		}
		
		if (reportRequisitionproductBeanList.size() > 0) {
			  Collections.sort(reportRequisitionproductBeanList, new Comparator<ReportRequisitionproductBean>() {
			      public int compare(final ReportRequisitionproductBean object1, final ReportRequisitionproductBean object2) {
					if("1".equals(sort)){
						try {
							return formatDataTh.parse(object2.getWithdrawalDate()).compareTo(formatDataTh.parse(object1.getWithdrawalDate()));
						} catch (ParseException e) {
							e.printStackTrace();
							return 0;
						}
					}else{
						try {
							return formatDataTh.parse(object1.getWithdrawalDate()).compareTo(formatDataTh.parse(object2.getWithdrawalDate()));
						} catch (ParseException e) {
							e.printStackTrace();
							return 0;
						}
					}
			      }
			  });
			  
			  if(!"1".equals(split)){
				  Collections.sort(reportRequisitionproductBeanList, new Comparator<ReportRequisitionproductBean>() {
				      public int compare(final ReportRequisitionproductBean object1, final ReportRequisitionproductBean object2) {
						if("2".equals(split)){
							return object1.getProductCategory().compareTo(object2.getProductCategory());
						}else if("3".equals(split)){
							return object1.getStock().compareTo(object2.getStock());
						}else if("3".equals(split)){
							return object1.getTechnicianGroup().compareTo(object2.getTechnicianGroup());
						}else{
							return 0;
						}
				      }
				  });
			  }
		}
		
		return reportRequisitionproductBeanList;
	}
	
	@RequestMapping(value = "requisitionproduct/exportPdf/reportrange/{reportrange}/"
			+ "equipmentProductType/{equipmentProductType}/"
			+ "equipmentStock/{equipmentStock}/split/{split}/sort/{sort}", method = RequestMethod.GET)
	public ModelAndView requisitionproductExportPdf(Model model,
			@PathVariable String reportrange,
			@PathVariable String equipmentProductType,
			@PathVariable String equipmentStock,
			@PathVariable String split, 
			@PathVariable String sort,
			HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		logger.info("[method : requisitionproductExportPdf][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView(); 
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission
		if(isPermission()){
		logger.info("reportrange : {}, equipmentProductType : {}, equipmentStock : {}, "
				+ "split : {}, sort : {}",reportrange,equipmentProductType,equipmentStock,
				split,sort);
		
		try {
		
		String title = "";
		List<Object> resUse = new ArrayList<Object>();
		reportrange = reportrange.substring(2);
		List<RequisitionDocument> requisitionDocumentList = productService.getDataRequisitionProductForReport(reportrange,equipmentProductType,equipmentStock,split,sort);
		logger.info("requisitionDocumentList size : {}",requisitionDocumentList.size());
		String startDate = reportrange.split(" - ")[0];
		String endDate = reportrange.split(" - ")[1];
		SimpleDateFormat formatMMMMyyyy = new SimpleDateFormat("เดือนMMMM ปี yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataTh = new SimpleDateFormat("dd MMM yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataEngRange = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
		SimpleDateFormat formatDataThRange = new SimpleDateFormat("วันที่ dd เดือน MMMM ปี yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataTimeTh = new SimpleDateFormat("พิมพ์เมื่อวันที่ dd เดือน MMMM ปี yyyy เมื่อเวลา HH:mm น.", new Locale("TH", "th"));
		JasperRender jasperRender = new JasperRender();
		if(null != requisitionDocumentList && requisitionDocumentList.size() > 0){
			List<ReportRequisitionproductBean> reportRequisitionproductBeanList = setReportRequisitionproductBean(requisitionDocumentList,sort,split);
			ReportRequisitionproductBean reportRequisitionproductBean = new ReportRequisitionproductBean();
			if("1".equals(split)){ // รายการเบิกสินค้า (รายการเบิกสินค้าแยกตามเดือนเบิก)
					if (null != reportRequisitionproductBeanList && reportRequisitionproductBeanList.size() > 0) {
						title = "รายการเบิกสินค้า (รายการเบิกสินค้าแยกตามเดือนเบิก)";
						String withdrawalDateGroupData = formatMMMMyyyy.format(formatDataTh.parse(reportRequisitionproductBeanList.get(0).getWithdrawalDate()));
						int no = 1, sumAmount = 0;
						float pricePerUnit = 0f, pricePerUnitTotal = 0f, sumPrice = 0f;

						reportRequisitionproductBean = new ReportRequisitionproductBean();
						reportRequisitionproductBean.setGroupData(withdrawalDateGroupData);
						reportRequisitionproductBean.setCheckHeaderTable(false);
						reportRequisitionproductBean.setCheckSum(false);
						reportRequisitionproductBean.setCheckGroupData(true);
						resUse.add(reportRequisitionproductBean);

						reportRequisitionproductBean = new ReportRequisitionproductBean();
						reportRequisitionproductBean.setNoText("ลำดับ");
						reportRequisitionproductBean.setRequisitionDocumentCodeText("ใบเบิก");
						reportRequisitionproductBean.setWithdrawalDateText("วันที่เบิก");
						reportRequisitionproductBean.setProductNameText("อุปกรณ์");
						reportRequisitionproductBean.setProductCategoryText("หมวดหมู่");
						reportRequisitionproductBean.setStockText("คลังสินค้า");
						reportRequisitionproductBean.setTechnicianGroupText("ผู้เบิก");
						reportRequisitionproductBean.setAmountText("จำนวน");
						reportRequisitionproductBean.setPricePerUnitText("ราคาต่อหน่วย");
						reportRequisitionproductBean.setPricePerUnitTotalText("มูลค่ารวม");
						reportRequisitionproductBean.setCheckHeaderTable(true);
						reportRequisitionproductBean.setCheckSum(false);
						reportRequisitionproductBean.setCheckGroupData(false);
						resUse.add(reportRequisitionproductBean);

						for (int i = 0; reportRequisitionproductBeanList.size() > i; i++) {
							String withdrawalDateGroupDataNew = formatMMMMyyyy.format(formatDataTh.parse(reportRequisitionproductBeanList.get(i).getWithdrawalDate()));
							if (!withdrawalDateGroupData.equals(withdrawalDateGroupDataNew)) {

								reportRequisitionproductBean = new ReportRequisitionproductBean();
								reportRequisitionproductBean.setSumAmountText("รวมจำนวน");
								reportRequisitionproductBean.setSumAmount(new DecimalFormat("#,###").format(sumAmount));
								reportRequisitionproductBean.setSumPriceText("มูลค่าทั้งสิ้น");
								reportRequisitionproductBean.setSumPrice(new DecimalFormat("#,##0.00").format(sumPrice));
								reportRequisitionproductBean.setCheckHeaderTable(false);
								reportRequisitionproductBean.setCheckSum(true);
								reportRequisitionproductBean.setCheckGroupData(false);
								resUse.add(reportRequisitionproductBean);

								withdrawalDateGroupData = withdrawalDateGroupDataNew;

								reportRequisitionproductBean = new ReportRequisitionproductBean();
								reportRequisitionproductBean.setGroupData(withdrawalDateGroupData);
								reportRequisitionproductBean.setCheckHeaderTable(false);
								reportRequisitionproductBean.setCheckSum(false);
								reportRequisitionproductBean.setCheckGroupData(true);
								resUse.add(reportRequisitionproductBean);

								reportRequisitionproductBean = new ReportRequisitionproductBean();
								reportRequisitionproductBean.setNoText("ลำดับ");
								reportRequisitionproductBean.setRequisitionDocumentCodeText("ใบเบิก");
								reportRequisitionproductBean.setWithdrawalDateText("วันที่เบิก");
								reportRequisitionproductBean.setProductNameText("อุปกรณ์");
								reportRequisitionproductBean.setProductCategoryText("หมวดหมู่");
								reportRequisitionproductBean.setStockText("คลังสินค้า");
								reportRequisitionproductBean.setTechnicianGroupText("ผู้เบิก");
								reportRequisitionproductBean.setAmountText("จำนวน");
								reportRequisitionproductBean.setPricePerUnitText("ราคาต่อหน่วย");
								reportRequisitionproductBean.setPricePerUnitTotalText("มูลค่ารวม");
								reportRequisitionproductBean.setCheckHeaderTable(true);
								reportRequisitionproductBean.setCheckSum(false);
								reportRequisitionproductBean.setCheckGroupData(false);
								resUse.add(reportRequisitionproductBean);

								sumAmount = 0;
								sumPrice = 0f;
								pricePerUnitTotal = 0f;
								no = 1;
								--i;
								continue;
							}
							
							Number numberAmount, numberPrice;
							try {
								numberAmount = NumberFormat.getNumberInstance(Locale.US).parse(reportRequisitionproductBeanList.get(i).getAmount());
								sumAmount += numberAmount.intValue();
								numberPrice = NumberFormat.getNumberInstance(Locale.US).parse(reportRequisitionproductBeanList.get(i).getPricePerUnitTotal());
								sumPrice += numberPrice.intValue();
							} catch (ParseException e1) {
								e1.printStackTrace();
							}

							reportRequisitionproductBean = new ReportRequisitionproductBean();
							reportRequisitionproductBean.setNo(no++);
							reportRequisitionproductBean.setRequisitionDocumentCode(reportRequisitionproductBeanList.get(i).getRequisitionDocumentCode());
							reportRequisitionproductBean.setWithdrawalDate(reportRequisitionproductBeanList.get(i).getWithdrawalDate());
							reportRequisitionproductBean.setProductName(reportRequisitionproductBeanList.get(i).getProductName());
							reportRequisitionproductBean.setProductCategory(reportRequisitionproductBeanList.get(i).getProductCategory());
							reportRequisitionproductBean.setStock(reportRequisitionproductBeanList.get(i).getStock());
							reportRequisitionproductBean.setTechnicianGroup(reportRequisitionproductBeanList.get(i).getTechnicianGroup());
							reportRequisitionproductBean.setAmount(reportRequisitionproductBeanList.get(i).getAmount());
							reportRequisitionproductBean.setPricePerUnit(reportRequisitionproductBeanList.get(i).getPricePerUnit());
							reportRequisitionproductBean.setPricePerUnitTotal(reportRequisitionproductBeanList.get(i).getPricePerUnitTotal());
							reportRequisitionproductBean.setCheckHeaderTable(false);
							reportRequisitionproductBean.setCheckSum(false);
							reportRequisitionproductBean.setCheckGroupData(false);
							resUse.add(reportRequisitionproductBean);

							if ((reportRequisitionproductBeanList.size() - 1) == i) {
								reportRequisitionproductBean = new ReportRequisitionproductBean();
								reportRequisitionproductBean.setSumAmountText("รวมจำนวน");
								reportRequisitionproductBean.setSumAmount(new DecimalFormat("#,###").format(sumAmount));
								reportRequisitionproductBean.setSumPriceText("มูลค่าทั้งสิ้น");
								reportRequisitionproductBean.setSumPrice(new DecimalFormat("#,##0.00").format(sumPrice));
								reportRequisitionproductBean.setCheckHeaderTable(false);
								reportRequisitionproductBean.setCheckSum(true);
								reportRequisitionproductBean.setCheckGroupData(false);
								resUse.add(reportRequisitionproductBean);
							}
						}
					}
			}else if("2".equals(split)){ // รายการเบิกสินค้า (รายการเบิกสินค้าแยกตามหมวดหมู่สินค้า)
				if (null != reportRequisitionproductBeanList && reportRequisitionproductBeanList.size() > 0) {
					title = "รายการเบิกสินค้า (รายการเบิกสินค้าแยกตามหมวดหมู่สินค้า)";
					String productCategoryMain = reportRequisitionproductBeanList.get(0).getProductCategory();
					int no = 1, sumAmount = 0;
					float pricePerUnit = 0f, pricePerUnitTotal = 0f, sumPrice = 0f;

					reportRequisitionproductBean = new ReportRequisitionproductBean();
					reportRequisitionproductBean.setGroupData(String.format("หมวดหมู่สินค้า %1s",productCategoryMain));
					reportRequisitionproductBean.setCheckHeaderTable(false);
					reportRequisitionproductBean.setCheckSum(false);
					reportRequisitionproductBean.setCheckGroupData(true);
					resUse.add(reportRequisitionproductBean);

					reportRequisitionproductBean = new ReportRequisitionproductBean();
					reportRequisitionproductBean.setNoText("ลำดับ");
					reportRequisitionproductBean.setRequisitionDocumentCodeText("ใบเบิก");
					reportRequisitionproductBean.setWithdrawalDateText("วันที่เบิก");
					reportRequisitionproductBean.setProductNameText("อุปกรณ์");
					reportRequisitionproductBean.setProductCategoryText("หมวดหมู่");
					reportRequisitionproductBean.setStockText("คลังสินค้า");
					reportRequisitionproductBean.setTechnicianGroupText("ผู้เบิก");
					reportRequisitionproductBean.setAmountText("จำนวน");
					reportRequisitionproductBean.setPricePerUnitText("ราคาต่อหน่วย");
					reportRequisitionproductBean.setPricePerUnitTotalText("มูลค่ารวม");
					reportRequisitionproductBean.setCheckHeaderTable(true);
					reportRequisitionproductBean.setCheckSum(false);
					reportRequisitionproductBean.setCheckGroupData(false);
					resUse.add(reportRequisitionproductBean);

					for (int i = 0; reportRequisitionproductBeanList.size() > i; i++) {
						String productCategory = reportRequisitionproductBeanList.get(i).getProductCategory();
						if (!productCategory.equals(productCategoryMain)) {

							reportRequisitionproductBean = new ReportRequisitionproductBean();
							reportRequisitionproductBean.setSumAmountText("รวมจำนวน");
							reportRequisitionproductBean.setSumAmount(new DecimalFormat("#,###").format(sumAmount));
							reportRequisitionproductBean.setSumPriceText("มูลค่าทั้งสิ้น");
							reportRequisitionproductBean.setSumPrice(new DecimalFormat("#,##0.00").format(sumPrice));
							reportRequisitionproductBean.setCheckHeaderTable(false);
							reportRequisitionproductBean.setCheckSum(true);
							reportRequisitionproductBean.setCheckGroupData(false);
							resUse.add(reportRequisitionproductBean);

							productCategoryMain = productCategory;

							reportRequisitionproductBean = new ReportRequisitionproductBean();
							reportRequisitionproductBean.setGroupData(String.format("หมวดหมู่สินค้า %1s",productCategoryMain));
							reportRequisitionproductBean.setCheckHeaderTable(false);
							reportRequisitionproductBean.setCheckSum(false);
							reportRequisitionproductBean.setCheckGroupData(true);
							resUse.add(reportRequisitionproductBean);

							reportRequisitionproductBean = new ReportRequisitionproductBean();
							reportRequisitionproductBean.setNoText("ลำดับ");
							reportRequisitionproductBean.setRequisitionDocumentCodeText("ใบเบิก");
							reportRequisitionproductBean.setWithdrawalDateText("วันที่เบิก");
							reportRequisitionproductBean.setProductNameText("อุปกรณ์");
							reportRequisitionproductBean.setProductCategoryText("หมวดหมู่");
							reportRequisitionproductBean.setStockText("คลังสินค้า");
							reportRequisitionproductBean.setTechnicianGroupText("ผู้เบิก");
							reportRequisitionproductBean.setAmountText("จำนวน");
							reportRequisitionproductBean.setPricePerUnitText("ราคาต่อหน่วย");
							reportRequisitionproductBean.setPricePerUnitTotalText("มูลค่ารวม");
							reportRequisitionproductBean.setCheckHeaderTable(true);
							reportRequisitionproductBean.setCheckSum(false);
							reportRequisitionproductBean.setCheckGroupData(false);
							resUse.add(reportRequisitionproductBean);

							sumAmount = 0;
							sumPrice = 0f;
							pricePerUnitTotal = 0f;
							no = 1;
							--i;
							continue;
						}
						
						Number numberAmount, numberPrice;
						try {
							numberAmount = NumberFormat.getNumberInstance(Locale.US).parse(reportRequisitionproductBeanList.get(i).getAmount());
							sumAmount += numberAmount.intValue();
							numberPrice = NumberFormat.getNumberInstance(Locale.US).parse(reportRequisitionproductBeanList.get(i).getPricePerUnitTotal());
							sumPrice += numberPrice.intValue();
						} catch (ParseException e1) {
							e1.printStackTrace();
						}

						reportRequisitionproductBean = new ReportRequisitionproductBean();
						reportRequisitionproductBean.setNo(no++);
						reportRequisitionproductBean.setRequisitionDocumentCode(reportRequisitionproductBeanList.get(i).getRequisitionDocumentCode());
						reportRequisitionproductBean.setWithdrawalDate(reportRequisitionproductBeanList.get(i).getWithdrawalDate());
						reportRequisitionproductBean.setProductName(reportRequisitionproductBeanList.get(i).getProductName());
						reportRequisitionproductBean.setProductCategory(reportRequisitionproductBeanList.get(i).getProductCategory());
						reportRequisitionproductBean.setStock(reportRequisitionproductBeanList.get(i).getStock());
						reportRequisitionproductBean.setTechnicianGroup(reportRequisitionproductBeanList.get(i).getTechnicianGroup());
						reportRequisitionproductBean.setAmount(reportRequisitionproductBeanList.get(i).getAmount());
						reportRequisitionproductBean.setPricePerUnit(reportRequisitionproductBeanList.get(i).getPricePerUnit());
						reportRequisitionproductBean.setPricePerUnitTotal(reportRequisitionproductBeanList.get(i).getPricePerUnitTotal());
						reportRequisitionproductBean.setCheckHeaderTable(false);
						reportRequisitionproductBean.setCheckSum(false);
						reportRequisitionproductBean.setCheckGroupData(false);
						resUse.add(reportRequisitionproductBean);

						if ((reportRequisitionproductBeanList.size() - 1) == i) {
							reportRequisitionproductBean = new ReportRequisitionproductBean();
							reportRequisitionproductBean.setSumAmountText("รวมจำนวน");
							reportRequisitionproductBean.setSumAmount(new DecimalFormat("#,###").format(sumAmount));
							reportRequisitionproductBean.setSumPriceText("มูลค่าทั้งสิ้น");
							reportRequisitionproductBean.setSumPrice(new DecimalFormat("#,##0.00").format(sumPrice));
							reportRequisitionproductBean.setCheckHeaderTable(false);
							reportRequisitionproductBean.setCheckSum(true);
							reportRequisitionproductBean.setCheckGroupData(false);
							resUse.add(reportRequisitionproductBean);
						}
					}
				}
			}else if("3".equals(split)){ // รายการเบิกสินค้า (รายการเบิกสินค้าแยกตามคลังสินค้า)
				if (null != reportRequisitionproductBeanList && reportRequisitionproductBeanList.size() > 0) {
					title = "รายการเบิกสินค้า (รายการเบิกสินค้าแยกตามคลังสินค้า)";
					String stockMain = reportRequisitionproductBeanList.get(0).getStock();
					int no = 1, sumAmount = 0;
					float pricePerUnit = 0f, pricePerUnitTotal = 0f, sumPrice = 0f;

					reportRequisitionproductBean = new ReportRequisitionproductBean();
					reportRequisitionproductBean.setGroupData(String.format("คลังสินค้า %1s",stockMain));
					reportRequisitionproductBean.setCheckHeaderTable(false);
					reportRequisitionproductBean.setCheckSum(false);
					reportRequisitionproductBean.setCheckGroupData(true);
					resUse.add(reportRequisitionproductBean);

					reportRequisitionproductBean = new ReportRequisitionproductBean();
					reportRequisitionproductBean.setNoText("ลำดับ");
					reportRequisitionproductBean.setRequisitionDocumentCodeText("ใบเบิก");
					reportRequisitionproductBean.setWithdrawalDateText("วันที่เบิก");
					reportRequisitionproductBean.setProductNameText("อุปกรณ์");
					reportRequisitionproductBean.setProductCategoryText("หมวดหมู่");
					reportRequisitionproductBean.setStockText("คลังสินค้า");
					reportRequisitionproductBean.setTechnicianGroupText("ผู้เบิก");
					reportRequisitionproductBean.setAmountText("จำนวน");
					reportRequisitionproductBean.setPricePerUnitText("ราคาต่อหน่วย");
					reportRequisitionproductBean.setPricePerUnitTotalText("มูลค่ารวม");
					reportRequisitionproductBean.setCheckHeaderTable(true);
					reportRequisitionproductBean.setCheckSum(false);
					reportRequisitionproductBean.setCheckGroupData(false);
					resUse.add(reportRequisitionproductBean);

					for (int i = 0; reportRequisitionproductBeanList.size() > i; i++) {
						String stock = reportRequisitionproductBeanList.get(i).getStock();
						if (!stock.equals(stockMain)) {

							reportRequisitionproductBean = new ReportRequisitionproductBean();
							reportRequisitionproductBean.setSumAmountText("รวมจำนวน");
							reportRequisitionproductBean.setSumAmount(new DecimalFormat("#,###").format(sumAmount));
							reportRequisitionproductBean.setSumPriceText("มูลค่าทั้งสิ้น");
							reportRequisitionproductBean.setSumPrice(new DecimalFormat("#,##0.00").format(sumPrice));
							reportRequisitionproductBean.setCheckHeaderTable(false);
							reportRequisitionproductBean.setCheckSum(true);
							reportRequisitionproductBean.setCheckGroupData(false);
							resUse.add(reportRequisitionproductBean);

							stockMain = stock;

							reportRequisitionproductBean = new ReportRequisitionproductBean();
							reportRequisitionproductBean.setGroupData(String.format("คลังสินค้า %1s",stockMain));
							reportRequisitionproductBean.setCheckHeaderTable(false);
							reportRequisitionproductBean.setCheckSum(false);
							reportRequisitionproductBean.setCheckGroupData(true);
							resUse.add(reportRequisitionproductBean);

							reportRequisitionproductBean = new ReportRequisitionproductBean();
							reportRequisitionproductBean.setNoText("ลำดับ");
							reportRequisitionproductBean.setRequisitionDocumentCodeText("ใบเบิก");
							reportRequisitionproductBean.setWithdrawalDateText("วันที่เบิก");
							reportRequisitionproductBean.setProductNameText("อุปกรณ์");
							reportRequisitionproductBean.setProductCategoryText("หมวดหมู่");
							reportRequisitionproductBean.setStockText("คลังสินค้า");
							reportRequisitionproductBean.setTechnicianGroupText("ผู้เบิก");
							reportRequisitionproductBean.setAmountText("จำนวน");
							reportRequisitionproductBean.setPricePerUnitText("ราคาต่อหน่วย");
							reportRequisitionproductBean.setPricePerUnitTotalText("มูลค่ารวม");
							reportRequisitionproductBean.setCheckHeaderTable(true);
							reportRequisitionproductBean.setCheckSum(false);
							reportRequisitionproductBean.setCheckGroupData(false);
							resUse.add(reportRequisitionproductBean);

							sumAmount = 0;
							sumPrice = 0f;
							pricePerUnitTotal = 0f;
							no = 1;
							--i;
							continue;
						}
						
						Number numberAmount, numberPrice;
						try {
							numberAmount = NumberFormat.getNumberInstance(Locale.US).parse(reportRequisitionproductBeanList.get(i).getAmount());
							sumAmount += numberAmount.intValue();
							numberPrice = NumberFormat.getNumberInstance(Locale.US).parse(reportRequisitionproductBeanList.get(i).getPricePerUnitTotal());
							sumPrice += numberPrice.intValue();
						} catch (ParseException e1) {
							e1.printStackTrace();
						}

						reportRequisitionproductBean = new ReportRequisitionproductBean();
						reportRequisitionproductBean.setNo(no++);
						reportRequisitionproductBean.setRequisitionDocumentCode(reportRequisitionproductBeanList.get(i).getRequisitionDocumentCode());
						reportRequisitionproductBean.setWithdrawalDate(reportRequisitionproductBeanList.get(i).getWithdrawalDate());
						reportRequisitionproductBean.setProductName(reportRequisitionproductBeanList.get(i).getProductName());
						reportRequisitionproductBean.setProductCategory(reportRequisitionproductBeanList.get(i).getProductCategory());
						reportRequisitionproductBean.setStock(reportRequisitionproductBeanList.get(i).getStock());
						reportRequisitionproductBean.setTechnicianGroup(reportRequisitionproductBeanList.get(i).getTechnicianGroup());
						reportRequisitionproductBean.setAmount(reportRequisitionproductBeanList.get(i).getAmount());
						reportRequisitionproductBean.setPricePerUnit(reportRequisitionproductBeanList.get(i).getPricePerUnit());
						reportRequisitionproductBean.setPricePerUnitTotal(reportRequisitionproductBeanList.get(i).getPricePerUnitTotal());
						reportRequisitionproductBean.setCheckHeaderTable(false);
						reportRequisitionproductBean.setCheckSum(false);
						reportRequisitionproductBean.setCheckGroupData(false);
						resUse.add(reportRequisitionproductBean);

						if ((reportRequisitionproductBeanList.size() - 1) == i) {
							reportRequisitionproductBean = new ReportRequisitionproductBean();
							reportRequisitionproductBean.setSumAmountText("รวมจำนวน");
							reportRequisitionproductBean.setSumAmount(new DecimalFormat("#,###").format(sumAmount));
							reportRequisitionproductBean.setSumPriceText("มูลค่าทั้งสิ้น");
							reportRequisitionproductBean.setSumPrice(new DecimalFormat("#,##0.00").format(sumPrice));
							reportRequisitionproductBean.setCheckHeaderTable(false);
							reportRequisitionproductBean.setCheckSum(true);
							reportRequisitionproductBean.setCheckGroupData(false);
							resUse.add(reportRequisitionproductBean);
						}
					}
				}
			}else if("4".equals(split)){ // รายการเบิกสินค้า (รายการเบิกสินค้าแยกตามพนักงานขอเบิก)
				if (null != reportRequisitionproductBeanList && reportRequisitionproductBeanList.size() > 0) {
					title = "รายการเบิกสินค้า (รายการเบิกสินค้าแยกตามพนักงานขอเบิก)";
					String technicianGroupMain = reportRequisitionproductBeanList.get(0).getTechnicianGroup();
					int no = 1, sumAmount = 0;
					float pricePerUnit = 0f, pricePerUnitTotal = 0f, sumPrice = 0f;

					reportRequisitionproductBean = new ReportRequisitionproductBean();
					reportRequisitionproductBean.setGroupData(String.format("รายการเบิกของ %1s",technicianGroupMain));
					reportRequisitionproductBean.setCheckHeaderTable(false);
					reportRequisitionproductBean.setCheckSum(false);
					reportRequisitionproductBean.setCheckGroupData(true);
					resUse.add(reportRequisitionproductBean);

					reportRequisitionproductBean = new ReportRequisitionproductBean();
					reportRequisitionproductBean.setNoText("ลำดับ");
					reportRequisitionproductBean.setRequisitionDocumentCodeText("ใบเบิก");
					reportRequisitionproductBean.setWithdrawalDateText("วันที่เบิก");
					reportRequisitionproductBean.setProductNameText("อุปกรณ์");
					reportRequisitionproductBean.setProductCategoryText("หมวดหมู่");
					reportRequisitionproductBean.setStockText("คลังสินค้า");
					reportRequisitionproductBean.setTechnicianGroupText("ผู้เบิก");
					reportRequisitionproductBean.setAmountText("จำนวน");
					reportRequisitionproductBean.setPricePerUnitText("ราคาต่อหน่วย");
					reportRequisitionproductBean.setPricePerUnitTotalText("มูลค่ารวม");
					reportRequisitionproductBean.setCheckHeaderTable(true);
					reportRequisitionproductBean.setCheckSum(false);
					reportRequisitionproductBean.setCheckGroupData(false);
					resUse.add(reportRequisitionproductBean);

					for (int i = 0; reportRequisitionproductBeanList.size() > i; i++) {
						String technicianGroup = reportRequisitionproductBeanList.get(i).getTechnicianGroup();
						if (!technicianGroup.equals(technicianGroupMain)) {

							reportRequisitionproductBean = new ReportRequisitionproductBean();
							reportRequisitionproductBean.setSumAmountText("รวมจำนวน");
							reportRequisitionproductBean.setSumAmount(new DecimalFormat("#,###").format(sumAmount));
							reportRequisitionproductBean.setSumPriceText("มูลค่าทั้งสิ้น");
							reportRequisitionproductBean.setSumPrice(new DecimalFormat("#,##0.00").format(sumPrice));
							reportRequisitionproductBean.setCheckHeaderTable(false);
							reportRequisitionproductBean.setCheckSum(true);
							reportRequisitionproductBean.setCheckGroupData(false);
							resUse.add(reportRequisitionproductBean);

							technicianGroupMain = technicianGroup;

							reportRequisitionproductBean = new ReportRequisitionproductBean();
							reportRequisitionproductBean.setGroupData(String.format("รายการเบิกของ %1s",technicianGroupMain));
							reportRequisitionproductBean.setCheckHeaderTable(false);
							reportRequisitionproductBean.setCheckSum(false);
							reportRequisitionproductBean.setCheckGroupData(true);
							resUse.add(reportRequisitionproductBean);

							reportRequisitionproductBean = new ReportRequisitionproductBean();
							reportRequisitionproductBean.setNoText("ลำดับ");
							reportRequisitionproductBean.setRequisitionDocumentCodeText("ใบเบิก");
							reportRequisitionproductBean.setWithdrawalDateText("วันที่เบิก");
							reportRequisitionproductBean.setProductNameText("อุปกรณ์");
							reportRequisitionproductBean.setProductCategoryText("หมวดหมู่");
							reportRequisitionproductBean.setStockText("คลังสินค้า");
							reportRequisitionproductBean.setTechnicianGroupText("ผู้เบิก");
							reportRequisitionproductBean.setAmountText("จำนวน");
							reportRequisitionproductBean.setPricePerUnitText("ราคาต่อหน่วย");
							reportRequisitionproductBean.setPricePerUnitTotalText("มูลค่ารวม");
							reportRequisitionproductBean.setCheckHeaderTable(true);
							reportRequisitionproductBean.setCheckSum(false);
							reportRequisitionproductBean.setCheckGroupData(false);
							resUse.add(reportRequisitionproductBean);

							sumAmount = 0;
							sumPrice = 0f;
							pricePerUnitTotal = 0f;
							no = 1;
							--i;
							continue;
						}
						
						Number numberAmount, numberPrice;
						try {
							numberAmount = NumberFormat.getNumberInstance(Locale.US).parse(reportRequisitionproductBeanList.get(i).getAmount());
							sumAmount += numberAmount.intValue();
							numberPrice = NumberFormat.getNumberInstance(Locale.US).parse(reportRequisitionproductBeanList.get(i).getPricePerUnitTotal());
							sumPrice += numberPrice.intValue();
						} catch (ParseException e1) {
							e1.printStackTrace();
						}

						reportRequisitionproductBean = new ReportRequisitionproductBean();
						reportRequisitionproductBean.setNo(no++);
						reportRequisitionproductBean.setRequisitionDocumentCode(reportRequisitionproductBeanList.get(i).getRequisitionDocumentCode());
						reportRequisitionproductBean.setWithdrawalDate(reportRequisitionproductBeanList.get(i).getWithdrawalDate());
						reportRequisitionproductBean.setProductName(reportRequisitionproductBeanList.get(i).getProductName());
						reportRequisitionproductBean.setProductCategory(reportRequisitionproductBeanList.get(i).getProductCategory());
						reportRequisitionproductBean.setStock(reportRequisitionproductBeanList.get(i).getStock());
						reportRequisitionproductBean.setTechnicianGroup(reportRequisitionproductBeanList.get(i).getTechnicianGroup());
						reportRequisitionproductBean.setAmount(reportRequisitionproductBeanList.get(i).getAmount());
						reportRequisitionproductBean.setPricePerUnit(reportRequisitionproductBeanList.get(i).getPricePerUnit());
						reportRequisitionproductBean.setPricePerUnitTotal(reportRequisitionproductBeanList.get(i).getPricePerUnitTotal());
						reportRequisitionproductBean.setCheckHeaderTable(false);
						reportRequisitionproductBean.setCheckSum(false);
						reportRequisitionproductBean.setCheckGroupData(false);
						resUse.add(reportRequisitionproductBean);

						if ((reportRequisitionproductBeanList.size() - 1) == i) {
							reportRequisitionproductBean = new ReportRequisitionproductBean();
							reportRequisitionproductBean.setSumAmountText("รวมจำนวน");
							reportRequisitionproductBean.setSumAmount(new DecimalFormat("#,###").format(sumAmount));
							reportRequisitionproductBean.setSumPriceText("มูลค่าทั้งสิ้น");
							reportRequisitionproductBean.setSumPrice(new DecimalFormat("#,##0.00").format(sumPrice));
							reportRequisitionproductBean.setCheckHeaderTable(false);
							reportRequisitionproductBean.setCheckSum(true);
							reportRequisitionproductBean.setCheckGroupData(false);
							resUse.add(reportRequisitionproductBean);
						}
					}
				}
			}
		}
		
		jasperRender.setBeanList(resUse);
		Map<String, Object> params = new HashMap<String, Object>();
		String s = "", e = "";
		try {
			s = formatDataThRange.format(formatDataEngRange.parse(startDate));
			e = formatDataThRange.format(formatDataEngRange.parse(endDate));
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		Personnel personnel = getPersonnelLogin();
		String printBy = String.format("โดย คุณ%1s %2s (%3s) ตำแหน่ง %4s %5s",personnel.getFirstName(),personnel.getLastName(),personnel.getNickName(),personnel.getPosition().getPositionName(),personnel.getCompany().getCompanyName());
		String pathLogo = request.getSession().getServletContext().getRealPath("/jasper/logo-invoice.png");
		params.put("header", String.format("ข้อมูล %1s ถึง %2s",s,e));
		params.put("title", title);
		params.put("footer", formatDataTimeTh.format(new Date())+"\n"+printBy);
		params.put("pathLogo", pathLogo);
		
		jasperRender.setParams(params);
		//new Boolean(($V{COLUMN_COUNT}.intValue()%2) == 1)
		
			byte[] bytes = jasperRender.processStream(ParamsEnum.StreamType.PDF, jasperJrxmlComponent.compileJasperReport("requisitionproduct",request));
			response.reset();
			response.resetBuffer();
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			ServletOutputStream ouputStream = response.getOutputStream();
			ouputStream.write(bytes, 0, bytes.length);
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		}else{
			//no permission
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		return null;
	}
	
	@RequestMapping(value = "stocksummary", method = RequestMethod.GET)
	public ModelAndView stocksummary(Model model, HttpServletRequest request) {
		logger.info("[method : stocksummary][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView(); 
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		// check permission
				if (isPermission()) {
					// dropdown equipmentCategory for search only
					EquipmentProductCategoryController epcController = new EquipmentProductCategoryController();
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

				} else {
					// no permission
					modelAndView.setViewName(PERMISSION_DENIED);
					return modelAndView;
				}
		modelAndView.setViewName((String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE));
		return modelAndView;
	}
	
	public List<ReportStockSummaryBean> setReportStockSummaryBean(List<EquipmentProduct> equipmentProductList, final String split){
		List<ReportStockSummaryBean> reportStockSummaryBeanList = new ArrayList<ReportStockSummaryBean>();
		if(null != equipmentProductList && equipmentProductList.size() > 0){
			Long productId = equipmentProductList.get(0).getId();
			List<EquipmentProductItem> equipmentProductItemList = equipmentProductList.get(0).getEquipmentProductItems();
			int amount = equipmentProductList.get(0).getBalance();
			float pricePerUnit = 0f, pricePerUnitTotal = 0f;
			if(null != equipmentProductItemList && equipmentProductItemList.size() > 0){
				pricePerUnit = equipmentProductItemList.get(0).getCost();
			}
			pricePerUnitTotal = pricePerUnit * amount;
			ReportStockSummaryBean reportStockSummaryBean = new ReportStockSummaryBean();
			reportStockSummaryBean.setProductCode(equipmentProductList.get(0).getProductCode());
			reportStockSummaryBean.setProductName(equipmentProductList.get(0).getProductName());
			reportStockSummaryBean.setSupplier("".equals(equipmentProductList.get(0).getSupplier())?" - ":equipmentProductList.get(0).getSupplier());
			reportStockSummaryBean.setProductCategory(equipmentProductList.get(0).getEquipmentProductCategory().getEquipmentProductCategoryName());
			reportStockSummaryBean.setStock(equipmentProductList.get(0).getStock().getStockName());
			reportStockSummaryBean.setAmount(new DecimalFormat("#,###").format(amount));
			reportStockSummaryBean.setPricePerUnitTotal(new DecimalFormat("#,##0.00").format(pricePerUnitTotal));
			reportStockSummaryBean.setCheckHeaderTable(false);
			reportStockSummaryBean.setCheckSum(false);
			reportStockSummaryBean.setCheckGroupData(false);
			reportStockSummaryBeanList.add(reportStockSummaryBean);

			for(int i = 0; equipmentProductList.size() > i; i++){
				
				if(productId != equipmentProductList.get(i).getId()){
				equipmentProductItemList = equipmentProductList.get(i).getEquipmentProductItems();
				amount = equipmentProductList.get(i).getBalance();
				pricePerUnit = 0f; pricePerUnitTotal = 0f;
				if(null != equipmentProductItemList && equipmentProductItemList.size() > 0){
					pricePerUnit = equipmentProductItemList.get(0).getCost();
				}
				productId = equipmentProductList.get(i).getId();
				pricePerUnitTotal = pricePerUnit * amount;
				reportStockSummaryBean = new ReportStockSummaryBean();
				reportStockSummaryBean.setProductCode(equipmentProductList.get(i).getProductCode());
				reportStockSummaryBean.setProductName(equipmentProductList.get(i).getProductName());
				reportStockSummaryBean.setSupplier("".equals(equipmentProductList.get(i).getSupplier())?" - ":equipmentProductList.get(i).getSupplier());
				reportStockSummaryBean.setProductCategory(equipmentProductList.get(i).getEquipmentProductCategory().getEquipmentProductCategoryName());
				reportStockSummaryBean.setStock(equipmentProductList.get(i).getStock().getStockName());
				reportStockSummaryBean.setAmount(new DecimalFormat("#,###").format(amount));
				reportStockSummaryBean.setPricePerUnitTotal(new DecimalFormat("#,##0.00").format(pricePerUnitTotal));
				reportStockSummaryBean.setCheckHeaderTable(false);
				reportStockSummaryBean.setCheckSum(false);
				reportStockSummaryBean.setCheckGroupData(false);
				reportStockSummaryBeanList.add(reportStockSummaryBean);
				--i;
				}
			}
			
			if (reportStockSummaryBeanList.size() > 0) {
				  Collections.sort(reportStockSummaryBeanList, new Comparator<ReportStockSummaryBean>() {
				      public int compare(final ReportStockSummaryBean object1, final ReportStockSummaryBean object2) {
						if("1".equals(split)){
							return object2.getStock().compareTo(object1.getStock());
						}else if("2".equals(split)){
							return object2.getSupplier().compareTo(object1.getSupplier());
						}else{
							return object2.getProductCategory().compareTo(object1.getProductCategory());
						}
				      }
				  });

			}
			
		}

		return reportStockSummaryBeanList;
	}
	
	@RequestMapping(value = "stocksummary/exportPdf/"
			+ "equipmentProductType/{equipmentProductType}/"
			+ "equipmentStock/{equipmentStock}/split/{split}", method = RequestMethod.GET)
	public ModelAndView stocksummaryExportPdf(Model model,
			@PathVariable String equipmentProductType,
			@PathVariable String equipmentStock,
			@PathVariable String split, 
			HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		logger.info("[method : requisitionproductExportPdf][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView(); 
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		//check permission
		if(isPermission()){
		logger.info("equipmentProductType : {}, equipmentStock : {}, split : {}",equipmentProductType,equipmentStock,split);
		String title = "";
		List<Object> resUse = new ArrayList<Object>();
		List<EquipmentProduct> equipmentProductList = productService.getDataStocksummaryForReport(equipmentProductType,equipmentStock,split);
		logger.info("equipmentProductList size : {}",equipmentProductList.size());

		SimpleDateFormat formatMMMMyyyy = new SimpleDateFormat("เดือนMMMM ปี yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataTh = new SimpleDateFormat("dd MMM yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataEngRange = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
		SimpleDateFormat formatDataThRange = new SimpleDateFormat("วันที่ dd เดือน MMMM ปี yyyy", new Locale("TH", "th"));
		SimpleDateFormat formatDataTimeTh = new SimpleDateFormat("พิมพ์เมื่อวันที่ dd เดือน MMMM ปี yyyy เมื่อเวลา HH:mm น.", new Locale("TH", "th"));
		JasperRender jasperRender = new JasperRender();
		if(null != equipmentProductList && equipmentProductList.size() > 0){
			ReportStockSummaryBean reportStockSummaryBean = new ReportStockSummaryBean();
			if("1".equals(split)){ // รายงานสินค้าคงคลัง (สินค้าคงคลังแยกตามคลังสินค้า)
				int no = 1, sumAmount = 0;
				float sumPrice = 0f;
				title = "รายงานสินค้าคงคลัง (สินค้าคงคลังแยกตามคลังสินค้า)";
				List<ReportStockSummaryBean> reportStockSummaryBeanList = setReportStockSummaryBean(equipmentProductList,split);
				logger.info("reportStockSummaryBeanList size : {}",reportStockSummaryBeanList.size());
				if(null != reportStockSummaryBeanList && reportStockSummaryBeanList.size() > 0){
					String stockMain = reportStockSummaryBeanList.get(0).getStock();
					reportStockSummaryBean = new ReportStockSummaryBean();
					reportStockSummaryBean.setGroupData("คลังสินค้า "+stockMain);
					reportStockSummaryBean.setCheckHeaderTable(false);
					reportStockSummaryBean.setCheckSum(false);
					reportStockSummaryBean.setCheckGroupData(true);
					resUse.add(reportStockSummaryBean);

					reportStockSummaryBean = new ReportStockSummaryBean();
					reportStockSummaryBean.setNoText("ลำดับ");
					reportStockSummaryBean.setProductCodeText("รหัสสินค้า");
					reportStockSummaryBean.setProductNameText("อุปกรณ์");
					reportStockSummaryBean.setSupplierText("บริษัทผู้จาหน่าย");
					reportStockSummaryBean.setProductCategoryText("หมวดหมู่");
					reportStockSummaryBean.setStockText("คลังสินค้า");
					reportStockSummaryBean.setAmountText("จำนวน");
					reportStockSummaryBean.setPricePerUnitTotalText("มูลค่ารวม");
					reportStockSummaryBean.setCheckHeaderTable(true);
					reportStockSummaryBean.setCheckSum(false);
					reportStockSummaryBean.setCheckGroupData(false);
					resUse.add(reportStockSummaryBean);

					for(int i = 0; reportStockSummaryBeanList.size() > i; i++){
						if(!stockMain.equals(reportStockSummaryBeanList.get(i).getStock())){
							
							reportStockSummaryBean = new ReportStockSummaryBean();
							reportStockSummaryBean.setSumText("รวม");
							reportStockSummaryBean.setSumAmount(new DecimalFormat("#,###").format(sumAmount));
							reportStockSummaryBean.setSumPrice(new DecimalFormat("#,##0.00").format(sumPrice));
							reportStockSummaryBean.setCheckHeaderTable(false);
							reportStockSummaryBean.setCheckSum(true);
							reportStockSummaryBean.setCheckGroupData(false);
							resUse.add(reportStockSummaryBean);
							
							stockMain = reportStockSummaryBeanList.get(i).getStock();
							
							reportStockSummaryBean = new ReportStockSummaryBean();
							reportStockSummaryBean.setGroupData("คลังสินค้า "+stockMain);
							reportStockSummaryBean.setCheckHeaderTable(false);
							reportStockSummaryBean.setCheckSum(false);
							reportStockSummaryBean.setCheckGroupData(true);
							resUse.add(reportStockSummaryBean);
							
							reportStockSummaryBean = new ReportStockSummaryBean();
							reportStockSummaryBean.setNoText("ลำดับ");
							reportStockSummaryBean.setProductCodeText("รหัสสินค้า");
							reportStockSummaryBean.setProductNameText("อุปกรณ์");
							reportStockSummaryBean.setSupplierText("บริษัทผู้จาหน่าย");
							reportStockSummaryBean.setProductCategoryText("หมวดหมู่");
							reportStockSummaryBean.setStockText("คลังสินค้า");
							reportStockSummaryBean.setAmountText("จำนวน");
							reportStockSummaryBean.setPricePerUnitTotalText("มูลค่ารวม");
							reportStockSummaryBean.setCheckHeaderTable(true);
							reportStockSummaryBean.setCheckSum(false);
							reportStockSummaryBean.setCheckGroupData(false);
							resUse.add(reportStockSummaryBean);
							
							sumAmount = 0;
							sumPrice = 0;
							no = 1;
							--i;
							
							continue;
						}
						
						reportStockSummaryBean = new ReportStockSummaryBean();
						reportStockSummaryBean.setNo(no++);
						reportStockSummaryBean.setSupplier(reportStockSummaryBeanList.get(i).getSupplier());
						reportStockSummaryBean.setProductCode(reportStockSummaryBeanList.get(i).getProductCode());
						reportStockSummaryBean.setProductName(reportStockSummaryBeanList.get(i).getProductName());
						reportStockSummaryBean.setProductCategory(reportStockSummaryBeanList.get(i).getProductCategory());
						reportStockSummaryBean.setStock(reportStockSummaryBeanList.get(i).getStock());
						reportStockSummaryBean.setAmount(reportStockSummaryBeanList.get(i).getAmount());
						reportStockSummaryBean.setPricePerUnitTotal(reportStockSummaryBeanList.get(i).getPricePerUnitTotal());
						reportStockSummaryBean.setCheckHeaderTable(false);
						reportStockSummaryBean.setCheckSum(false);
						reportStockSummaryBean.setCheckGroupData(false);
						resUse.add(reportStockSummaryBean);
						Number numberAmount, numberPrice;
						try {
							numberAmount = NumberFormat.getNumberInstance(Locale.UK).parse(reportStockSummaryBeanList.get(i).getAmount());
							sumAmount += numberAmount.intValue();
							numberPrice = NumberFormat.getNumberInstance(Locale.UK).parse(reportStockSummaryBeanList.get(i).getPricePerUnitTotal());
							sumPrice += numberPrice.intValue();
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
							
							if((reportStockSummaryBeanList.size()-1) == i){
								reportStockSummaryBean = new ReportStockSummaryBean();
								reportStockSummaryBean.setSumText("รวม");
								reportStockSummaryBean.setSumAmount(new DecimalFormat("#,###").format(sumAmount));
								reportStockSummaryBean.setSumPrice(new DecimalFormat("#,##0.00").format(sumPrice));
								reportStockSummaryBean.setCheckHeaderTable(false);
								reportStockSummaryBean.setCheckSum(true);
								reportStockSummaryBean.setCheckGroupData(false);
								resUse.add(reportStockSummaryBean);
							}
					}
					
				}
				
			}else if("2".equals(split)){
				int no = 1, sumAmount = 0;
				float sumPrice = 0f;
				title = "รายงานสินค้าคงคลัง (สินค้าคงคลังแยกตามบริษัทผู้จาหน่าย)";
				List<ReportStockSummaryBean> reportStockSummaryBeanList = setReportStockSummaryBean(equipmentProductList,split);
				logger.info("reportStockSummaryBeanList size : {}",reportStockSummaryBeanList.size());
				if(null != reportStockSummaryBeanList && reportStockSummaryBeanList.size() > 0){
					String supplierMain = reportStockSummaryBeanList.get(0).getSupplier();
					reportStockSummaryBean = new ReportStockSummaryBean();
					reportStockSummaryBean.setGroupData("หมวดหมู่สินค้า "+supplierMain);
					reportStockSummaryBean.setCheckHeaderTable(false);
					reportStockSummaryBean.setCheckSum(false);
					reportStockSummaryBean.setCheckGroupData(true);
					resUse.add(reportStockSummaryBean);

					reportStockSummaryBean = new ReportStockSummaryBean();
					reportStockSummaryBean.setNoText("ลำดับ");
					reportStockSummaryBean.setProductCodeText("รหัสสินค้า");
					reportStockSummaryBean.setProductNameText("อุปกรณ์");
					reportStockSummaryBean.setSupplierText("บริษัทผู้จาหน่าย");
					reportStockSummaryBean.setProductCategoryText("หมวดหมู่");
					reportStockSummaryBean.setStockText("คลังสินค้า");
					reportStockSummaryBean.setAmountText("จำนวน");
					reportStockSummaryBean.setPricePerUnitTotalText("มูลค่ารวม");
					reportStockSummaryBean.setCheckHeaderTable(true);
					reportStockSummaryBean.setCheckSum(false);
					reportStockSummaryBean.setCheckGroupData(false);
					resUse.add(reportStockSummaryBean);

					for(int i = 0; reportStockSummaryBeanList.size() > i; i++){
						if(!supplierMain.equals(reportStockSummaryBeanList.get(i).getSupplier())){
							
							reportStockSummaryBean = new ReportStockSummaryBean();
							reportStockSummaryBean.setSumText("รวม");
							reportStockSummaryBean.setSumAmount(new DecimalFormat("#,###").format(sumAmount));
							reportStockSummaryBean.setSumPrice(new DecimalFormat("#,##0.00").format(sumPrice));
							reportStockSummaryBean.setCheckHeaderTable(false);
							reportStockSummaryBean.setCheckSum(true);
							reportStockSummaryBean.setCheckGroupData(false);
							resUse.add(reportStockSummaryBean);
							
							supplierMain = reportStockSummaryBeanList.get(i).getSupplier();
							
							reportStockSummaryBean = new ReportStockSummaryBean();
							reportStockSummaryBean.setGroupData("หมวดหมู่สินค้า "+supplierMain);
							reportStockSummaryBean.setCheckHeaderTable(false);
							reportStockSummaryBean.setCheckSum(false);
							reportStockSummaryBean.setCheckGroupData(true);
							resUse.add(reportStockSummaryBean);
							
							reportStockSummaryBean = new ReportStockSummaryBean();
							reportStockSummaryBean.setNoText("ลำดับ");
							reportStockSummaryBean.setProductCodeText("รหัสสินค้า");
							reportStockSummaryBean.setProductNameText("อุปกรณ์");
							reportStockSummaryBean.setSupplierText("บริษัทผู้จาหน่าย");
							reportStockSummaryBean.setProductCategoryText("หมวดหมู่");
							reportStockSummaryBean.setStockText("คลังสินค้า");
							reportStockSummaryBean.setAmountText("จำนวน");
							reportStockSummaryBean.setPricePerUnitTotalText("มูลค่ารวม");
							reportStockSummaryBean.setCheckHeaderTable(true);
							reportStockSummaryBean.setCheckSum(false);
							reportStockSummaryBean.setCheckGroupData(false);
							resUse.add(reportStockSummaryBean);
							
							sumAmount = 0;
							sumPrice = 0;
							no = 1;
							--i;
							
							continue;
						}
						
						reportStockSummaryBean = new ReportStockSummaryBean();
						reportStockSummaryBean.setNo(no++);
						reportStockSummaryBean.setSupplier(reportStockSummaryBeanList.get(i).getSupplier());
						reportStockSummaryBean.setProductCode(reportStockSummaryBeanList.get(i).getProductCode());
						reportStockSummaryBean.setProductName(reportStockSummaryBeanList.get(i).getProductName());
						reportStockSummaryBean.setProductCategory(reportStockSummaryBeanList.get(i).getProductCategory());
						reportStockSummaryBean.setStock(reportStockSummaryBeanList.get(i).getStock());
						reportStockSummaryBean.setAmount(reportStockSummaryBeanList.get(i).getAmount());
						reportStockSummaryBean.setPricePerUnitTotal(reportStockSummaryBeanList.get(i).getPricePerUnitTotal());
						reportStockSummaryBean.setCheckHeaderTable(false);
						reportStockSummaryBean.setCheckSum(false);
						reportStockSummaryBean.setCheckGroupData(false);
						resUse.add(reportStockSummaryBean);
						Number numberAmount, numberPrice;
						try {
							numberAmount = NumberFormat.getNumberInstance(Locale.UK).parse(reportStockSummaryBeanList.get(i).getAmount());
							sumAmount += numberAmount.intValue();
							numberPrice = NumberFormat.getNumberInstance(Locale.UK).parse(reportStockSummaryBeanList.get(i).getPricePerUnitTotal());
							sumPrice += numberPrice.intValue();
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
							
							if((reportStockSummaryBeanList.size()-1) == i){
								reportStockSummaryBean = new ReportStockSummaryBean();
								reportStockSummaryBean.setSumText("รวม");
								reportStockSummaryBean.setSumAmount(new DecimalFormat("#,###").format(sumAmount));
								reportStockSummaryBean.setSumPrice(new DecimalFormat("#,##0.00").format(sumPrice));
								reportStockSummaryBean.setCheckHeaderTable(false);
								reportStockSummaryBean.setCheckSum(true);
								reportStockSummaryBean.setCheckGroupData(false);
								resUse.add(reportStockSummaryBean);
							}
					}
					
				}
				
			}else if("3".equals(split)){
				int no = 1, sumAmount = 0;
				float sumPrice = 0f;
				title = "รายงานสินค้าคงคลัง (สินค้าคงคลังแยกตามหมวดหมู่)";
				List<ReportStockSummaryBean> reportStockSummaryBeanList = setReportStockSummaryBean(equipmentProductList,split);
				logger.info("reportStockSummaryBeanList size : {}",reportStockSummaryBeanList.size());
				if(null != reportStockSummaryBeanList && reportStockSummaryBeanList.size() > 0){
					String productCategoryMain = reportStockSummaryBeanList.get(0).getProductCategory();
					reportStockSummaryBean = new ReportStockSummaryBean();
					reportStockSummaryBean.setGroupData("ประเภทใบงาน "+productCategoryMain);
					reportStockSummaryBean.setCheckHeaderTable(false);
					reportStockSummaryBean.setCheckSum(false);
					reportStockSummaryBean.setCheckGroupData(true);
					resUse.add(reportStockSummaryBean);

					reportStockSummaryBean = new ReportStockSummaryBean();
					reportStockSummaryBean.setNoText("ลำดับ");
					reportStockSummaryBean.setProductCodeText("รหัสสินค้า");
					reportStockSummaryBean.setProductNameText("อุปกรณ์");
					reportStockSummaryBean.setSupplierText("บริษัทผู้จาหน่าย");
					reportStockSummaryBean.setProductCategoryText("หมวดหมู่");
					reportStockSummaryBean.setStockText("คลังสินค้า");
					reportStockSummaryBean.setAmountText("จำนวน");
					reportStockSummaryBean.setPricePerUnitTotalText("มูลค่ารวม");
					reportStockSummaryBean.setCheckHeaderTable(true);
					reportStockSummaryBean.setCheckSum(false);
					reportStockSummaryBean.setCheckGroupData(false);
					resUse.add(reportStockSummaryBean);

					for(int i = 0; reportStockSummaryBeanList.size() > i; i++){
						if(!productCategoryMain.equals(reportStockSummaryBeanList.get(i).getProductCategory())){
							
							reportStockSummaryBean = new ReportStockSummaryBean();
							reportStockSummaryBean.setSumText("รวม");
							reportStockSummaryBean.setSumAmount(new DecimalFormat("#,###").format(sumAmount));
							reportStockSummaryBean.setSumPrice(new DecimalFormat("#,##0.00").format(sumPrice));
							reportStockSummaryBean.setCheckHeaderTable(false);
							reportStockSummaryBean.setCheckSum(true);
							reportStockSummaryBean.setCheckGroupData(false);
							resUse.add(reportStockSummaryBean);
							
							productCategoryMain = reportStockSummaryBeanList.get(i).getProductCategory();
							
							reportStockSummaryBean = new ReportStockSummaryBean();
							reportStockSummaryBean.setGroupData("ประเภทใบงาน "+productCategoryMain);
							reportStockSummaryBean.setCheckHeaderTable(false);
							reportStockSummaryBean.setCheckSum(false);
							reportStockSummaryBean.setCheckGroupData(true);
							resUse.add(reportStockSummaryBean);
							
							reportStockSummaryBean = new ReportStockSummaryBean();
							reportStockSummaryBean.setNoText("ลำดับ");
							reportStockSummaryBean.setProductCodeText("รหัสสินค้า");
							reportStockSummaryBean.setProductNameText("อุปกรณ์");
							reportStockSummaryBean.setSupplierText("บริษัทผู้จาหน่าย");
							reportStockSummaryBean.setProductCategoryText("หมวดหมู่");
							reportStockSummaryBean.setStockText("คลังสินค้า");
							reportStockSummaryBean.setAmountText("จำนวน");
							reportStockSummaryBean.setPricePerUnitTotalText("มูลค่ารวม");
							reportStockSummaryBean.setCheckHeaderTable(true);
							reportStockSummaryBean.setCheckSum(false);
							reportStockSummaryBean.setCheckGroupData(false);
							resUse.add(reportStockSummaryBean);
							
							sumAmount = 0;
							sumPrice = 0;
							no = 1;
							--i;
							
							continue;
						}
						
						reportStockSummaryBean = new ReportStockSummaryBean();
						reportStockSummaryBean.setNo(no++);
						reportStockSummaryBean.setSupplier(reportStockSummaryBeanList.get(i).getSupplier());
						reportStockSummaryBean.setProductCode(reportStockSummaryBeanList.get(i).getProductCode());
						reportStockSummaryBean.setProductName(reportStockSummaryBeanList.get(i).getProductName());
						reportStockSummaryBean.setProductCategory(reportStockSummaryBeanList.get(i).getProductCategory());
						reportStockSummaryBean.setStock(reportStockSummaryBeanList.get(i).getStock());
						reportStockSummaryBean.setAmount(reportStockSummaryBeanList.get(i).getAmount());
						reportStockSummaryBean.setPricePerUnitTotal(reportStockSummaryBeanList.get(i).getPricePerUnitTotal());
						reportStockSummaryBean.setCheckHeaderTable(false);
						reportStockSummaryBean.setCheckSum(false);
						reportStockSummaryBean.setCheckGroupData(false);
						resUse.add(reportStockSummaryBean);
						Number numberAmount, numberPrice;
						try {
							numberAmount = NumberFormat.getNumberInstance(Locale.UK).parse(reportStockSummaryBeanList.get(i).getAmount());
							sumAmount += numberAmount.intValue();
							numberPrice = NumberFormat.getNumberInstance(Locale.UK).parse(reportStockSummaryBeanList.get(i).getPricePerUnitTotal());
							sumPrice += numberPrice.intValue();
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
							
							if((reportStockSummaryBeanList.size()-1) == i){
								reportStockSummaryBean = new ReportStockSummaryBean();
								reportStockSummaryBean.setSumText("รวม");
								reportStockSummaryBean.setSumAmount(new DecimalFormat("#,###").format(sumAmount));
								reportStockSummaryBean.setSumPrice(new DecimalFormat("#,##0.00").format(sumPrice));
								reportStockSummaryBean.setCheckHeaderTable(false);
								reportStockSummaryBean.setCheckSum(true);
								reportStockSummaryBean.setCheckGroupData(false);
								resUse.add(reportStockSummaryBean);
							}
					}
					
				}
				
			}
		}
		
		jasperRender.setBeanList(resUse);
		Map<String, Object> params = new HashMap<String, Object>();
		Personnel personnel = getPersonnelLogin();
		String printBy = String.format("โดย คุณ%1s %2s (%3s) ตำแหน่ง %4s %5s",personnel.getFirstName(),personnel.getLastName(),personnel.getNickName(),personnel.getPosition().getPositionName(),personnel.getCompany().getCompanyName());
		String pathLogo = request.getSession().getServletContext().getRealPath("/jasper/logo-invoice.png");
		params.put("header", "");
		params.put("title", title);
		params.put("footer", formatDataTimeTh.format(new Date())+"\n"+printBy);
		params.put("pathLogo", pathLogo);
		
		jasperRender.setParams(params);
		try {
			byte[] bytes = jasperRender.processStream(ParamsEnum.StreamType.PDF, jasperJrxmlComponent.compileJasperReport("stocksummary",request));
			response.reset();
			response.resetBuffer();
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			ServletOutputStream ouputStream = response.getOutputStream();
			ouputStream.write(bytes, 0, bytes.length);
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		}else{
			//no permission
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		return null;
	}
	
	@RequestMapping(value = "polist", method = RequestMethod.GET)
	public ModelAndView polist(Model model, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
		logger.info("[method : polist][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView(); 
		
		// check authentication
		if (!isAuthentication()) {
			modelAndView.setViewName(REDIRECT + "/login");
			return modelAndView;
		}
		if(isPermission()){
			modelAndView.setViewName((String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE));
		}else{
			//no permission
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		
		return modelAndView;
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
	
}
