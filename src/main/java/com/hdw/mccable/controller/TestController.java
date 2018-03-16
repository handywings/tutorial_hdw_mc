package com.hdw.mccable.controller;

import java.io.File;
import java.io.FileInputStream;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import java.util.Iterator;

@Controller
@RequestMapping("/test")
public class TestController extends BaseController{
	
	final static Logger logger = Logger.getLogger(TestController.class);
	public static final String CONTROLLER_NAME = "test/";
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView init(Model model) {
		logger.info("[method : init][Type : Controller]");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(CONTROLLER_NAME+"test");
		return modelAndView;
	}
	
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public ModelAndView saveWorksheet(
			@RequestParam("companyFile") MultipartFile companyFile) {

		ModelAndView modelAndView = new ModelAndView();
			try {
				System.out.println("fileName : " + companyFile.getOriginalFilename());
				FileInputStream excelFile = new FileInputStream(new File("D:\\kcs\\list_file\\"+companyFile.getOriginalFilename()));
				HSSFWorkbook workbook = new HSSFWorkbook(new POIFSFileSystem(excelFile));
				HSSFSheet datatypeSheet = workbook.getSheetAt(0);
				Iterator<Row> iterator = datatypeSheet.iterator();
			
			int row = 0;
			
			while (iterator.hasNext()) {
				row++;
				//System.out.println("row : " + row);
				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();
				
				int col = 0;
				while (cellIterator.hasNext()) {
					col++;
					//System.out.println("col : " + col);
					
					Cell currentCell = cellIterator.next();
					if (currentCell.getCellTypeEnum() == CellType.STRING) {
						System.out.print(currentCell.getStringCellValue() + "|");
					} else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						System.out.print(currentCell.getNumericCellValue() + "|");
					}

				}
				System.out.println();

			}
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		modelAndView.setViewName(CONTROLLER_NAME+"test");
		return modelAndView;
	}
}
