package com.hdw.mccable.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hdw.mccable.dto.UnitBean;
import com.hdw.mccable.entity.Unit;
import com.hdw.mccable.service.UnitService;
import com.hdw.mccable.utils.Pagination;

@Controller
@RequestMapping("/paginationExample")
public class PaginationExampleController extends BaseController {

	final static Logger logger = Logger.getLogger(PaginationExampleController.class);
	public static final String CONTROLLER_NAME = "pagination/";

	// initial service
	@Autowired(required = true)
	@Qualifier(value = "unitService")
	private UnitService unitService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView init(Model model, HttpServletRequest request) {
		logger.info("[method : pagination][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		// check permission
		if (isPermission()) {
			try {
				Pagination pagination = createPagination(1, 10, "paginationExample");
				 
				modelAndView.addObject("pagination",pagination);
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			// no permission
			modelAndView.setViewName(PERMISSION_DENIED);
			return modelAndView;
		}

		modelAndView.setViewName(CONTROLLER_NAME + INIT);
		return modelAndView;
	}
	
	@RequestMapping(value = "page/{id}/itemPerPage/{itemPerPage}", method = RequestMethod.GET)
	public ModelAndView pagination(@PathVariable int id, @PathVariable int itemPerPage, Model model, HttpServletRequest request) {
		logger.info("[method : pagination][Type : Controller]");
		logger.info("[method : pagination][itemPerPage : "+itemPerPage+"]");
		ModelAndView modelAndView = new ModelAndView();
		// check permission
		if (isPermission()) {
			try {
				Pagination pagination = createPagination(id, itemPerPage, "paginationExample");
				 
				modelAndView.addObject("pagination",pagination);
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			// no permission
			modelAndView.setViewName(PERMISSION_DENIED);
			return modelAndView;
		}

		modelAndView.setViewName(CONTROLLER_NAME + INIT);
		return modelAndView;
	}
	
	@SuppressWarnings("unchecked")
	Pagination createPagination(int currentPage, int itemPerPage, String controller){
		if(itemPerPage==0)itemPerPage=10;
		Pagination pagination = new Pagination();
		pagination.setTotalItem(unitService.getCountTotal());
		pagination.setCurrentPage(currentPage);
		pagination.setItemPerPage(itemPerPage);
		pagination.setUrl(controller);
		pagination = unitService.getByPage(pagination);
		//populate
		List<UnitBean> unitBeans = new ArrayList<UnitBean>();
		for(Unit unit : (List<Unit>) pagination.getDataList()){
			UnitBean unitBean = new UnitBean();
			unitBean.setId(unit.getId());
			unitBean.setUnitName(unit.getUnitName());
			unitBean.setCreateBy(unit.getCreatedBy());
			unitBean.setCreateDate(unit.getCreateDate());
			unitBeans.add(unitBean);
		}
		pagination.setDataListBean(unitBeans);
		//end populate
		
		return pagination;
	}
}
