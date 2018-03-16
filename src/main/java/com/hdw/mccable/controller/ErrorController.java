package com.hdw.mccable.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/errors")
public class ErrorController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);
	public static final String CONTROLLER_NAME = "errors/";

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView renderErrorPage(Model model, HttpServletRequest httpRequest) {
		logger.info("[method : renderErrorPage][Type : Controller]");

		ModelAndView errorPage = new ModelAndView();
		String errorMsg = "";
		String errorcode = "";
		int httpErrorCode = getErrorCode(httpRequest);

		logger.info("[httpErrorCode : " + httpErrorCode + " ]");
		
		switch (httpErrorCode) {
			case 400: {
//				errorMsg = "Http Error Code: 400. Bad Request";
				errorMsg = "ขออภัย !! เราพบข้อผิดพลาดของระบบ กรุณาลองใหม่อีกครั้ง";
				errorcode ="400";
				break;
			}
			case 401: {
//				errorMsg = "Http Error Code: 401. Unauthorized";
				errorMsg = "ขออภัย !! เราพบข้อผิดพลาดของระบบ กรุณาลองใหม่อีกครั้ง";
				errorcode ="401";
				break;
			}
			case 404: {
//				errorMsg = "ไม่พบหน้าที่คุณต้องการ หรือคุณไม่มีสิทธิ์การใช้งานหน้านี้";
				errorMsg = "ขออภัย !! เราพบข้อผิดพลาดของระบบ กรุณาลองใหม่อีกครั้ง";
				errorcode ="404";
				break;
			}
			case 405: {
//				errorMsg = "Http Error Code: 405. Request method 'GET' not supported";
				errorMsg = "ขออภัย !! เราพบข้อผิดพลาดของระบบ กรุณาลองใหม่อีกครั้ง";
				errorcode ="405";
				break;
			}
			case 500: {
//				errorMsg = "Http Error Code: 500. Internal Server Error";
				errorMsg = "ขออภัย !! เราพบข้อผิดพลาดของระบบ กรุณาลองใหม่อีกครั้ง";
				errorcode ="500";
				break;
			}
		}
		errorPage.addObject("errorMsg", errorMsg);
		errorPage.addObject("errorcode", errorcode);
		errorPage.setViewName(CONTROLLER_NAME+INIT);
		return errorPage;
	}

	private int getErrorCode(HttpServletRequest httpRequest) {
		return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
	}
}
