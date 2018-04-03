package com.hdw.mccable.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hdw.mccable.dto.CashierBean;
import com.hdw.mccable.dto.MigrateCustomerBean;
import com.hdw.mccable.dto.MigrateInvoiceBean;
import com.hdw.mccable.dto.ReportWorksheetBean;

public class ReadWriteExcelFile {

    public static FileInputStream fis = null;
    public static XSSFWorkbook workbook = null;
    public static XSSFSheet sheet = null;
    public static XSSFRow row = null;
    public static XSSFCell cell = null;
	
    public static List<MigrateCustomerBean> ReadExcel(InputStream inputStream) throws IOException {

    	List<MigrateCustomerBean> result = new ArrayList<MigrateCustomerBean>();

        Workbook workbook = new XSSFWorkbook(inputStream);

        Sheet sheet = workbook.getSheetAt(0);

        boolean isTitle = true;
        for(Row row : sheet) {
        		if(isTitle){ // ไม่นำข้อมูลหัวตาราง
        			isTitle = false;
        		}else{
        			MigrateCustomerBean bean = new MigrateCustomerBean();
        			for(int cn=0; cn<row.getLastCellNum(); cn++) {
	        	       // If the cell is missing from the file, generate a blank one
	        	       // (Works by specifying a MissingCellPolicy)
	        	       Cell cell = row.getCell(cn, Row.CREATE_NULL_AS_BLANK);
	        	       // Print the cell for debugging
	        	       System.out.println("CELL: " + cn + " --> " + cell.toString());
        			}
        			result.add(bean);
	        	}
        	}
        return result;
    }
    
    public static Workbook ReadExcelProduct(InputStream inputStream) throws IOException {
    	long startTime = System.currentTimeMillis();
    	
    	Workbook workbook = new XSSFWorkbook(inputStream);

        long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		int seconds = (int) (totalTime / 1000) % 60 ;
		int minutes = (int) ((totalTime / (1000*60)) % 60);
		int hours   = (int) ((totalTime / (1000*60*60)) % 24);
		System.out.println("ReadExcelProduct TotalTime : "+String.format("%d hour, %d min, %d sec", hours,minutes,seconds));
        return workbook;
    }
    
    
    public static List<MigrateCustomerBean> ReadExcelCustomer(InputStream inputStream) throws Exception {
    	long startTime = System.currentTimeMillis();
    	List<MigrateCustomerBean> result = new ArrayList<MigrateCustomerBean>();

    	Workbook workbook = new XSSFWorkbook(inputStream);

      	int sheetIndex = 0, intRow = 1;

        for (Row rows : workbook.getSheetAt(sheetIndex)) {
        	MigrateCustomerBean bean = new MigrateCustomerBean();
			bean = setDataMigrateCustomer(workbook, bean, sheetIndex, intRow++);
    	    result.add(bean);
        }
        
        
        long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		int seconds = (int) (totalTime / 1000) % 60 ;
		int minutes = (int) ((totalTime / (1000*60)) % 60);
		int hours   = (int) ((totalTime / (1000*60*60)) % 24);
		System.out.println("ReadExcelCustomer TotalTime : "+String.format("%d hour, %d min, %d sec", hours,minutes,seconds));
        return result;
    }
    
	private static MigrateCustomerBean setDataMigrateCustomer(Workbook workbook, MigrateCustomerBean bean, int sheetIndex, int intRow) throws Exception {
    		String customerCode = getDataString(workbook, sheetIndex, "A", intRow);
    		String firstName = getDataString(workbook, sheetIndex, "C", intRow);
    		String lastName = getDataString(workbook, sheetIndex, "D", intRow);
    		String customerFeatures = "1"; // fix
    		String career = "".equals(getDataString(workbook, sheetIndex, "AH", intRow))?"อื่นๆ":getDataString(workbook, sheetIndex, "AH", intRow);
    		String mobile = getDataString(workbook, sheetIndex, "P", intRow);
    		String no = getDataString(workbook, sheetIndex, "F", intRow);
    		String nearbyPlaces = getDataString(workbook, sheetIndex, "E", intRow);
    		String zone = StringUtils.isBlank(getDataString(workbook, sheetIndex, "Y", intRow))?"ไม่ได้ระบุ":getDataString(workbook, sheetIndex, "Y", intRow);
    		Date serviceApplicationDate = getDataStringDate(workbook, sheetIndex, "T", intRow);
    		String serviceApplicationTypeName = getDataString(workbook, sheetIndex, "AA", intRow);
    		String serviceApplicationTypeCode = String.valueOf(getDataDouble(workbook, sheetIndex, "Z", intRow));
    		String servicePackage = getDataString(workbook, sheetIndex, "AA", intRow);
    		Date dateOrderBill = getDataStringDate(workbook, sheetIndex, "U", intRow);
    		String billingFee = String.valueOf(getDataDouble(workbook, sheetIndex, "AE", intRow));
    		
    		String totalPoint = String.valueOf(getDataDouble(workbook, sheetIndex, "AJ", intRow));
    		String costBill = String.valueOf(getDataDouble(workbook, sheetIndex, "AB", intRow));
    		
    		Date dateCut = getDataStringDate(workbook, sheetIndex, "AK", intRow);
    		String cutStatus = String.valueOf(getDataDouble(workbook, sheetIndex, "AI", intRow));
    		
    		Date datePayment = getDataStringDate(workbook, sheetIndex, "U", intRow); // วันที่นัดชำระ
    		
			bean.setCustomerCode(customerCode);
			bean.setSex("");// *เพศ // ข้อมูลเดิมไม่มี
			bean.setPrefix("");// คำนำหน้า
			bean.setIdentityNumber("");// *เลขที่บัตรประชาชน // ข้อมูลเดิมไม่มี

			bean.setFirstName(firstName);
			bean.setLastName(lastName);

			String customerType = "I"; // I = บุคคลธรรมดา, C = นิติบุคคล
			bean.setCustomerType(customerType);// *ประเภทลูกค้า
			
			bean.setCustomerFeatures(customerFeatures);// ลักษณะของลูกค้า
			bean.setCareer(career);
			
			bean.setMobile(mobile);// เบอร์ติดต่อ
			bean.setFax("");// Fax
			bean.setEmail("");// E-mail
			
			bean.setNo(no);// *บ้านเลขที่
			bean.setProvince("11");// *จังหวัด : -> fix ชลบุรี = 11
			bean.setAmphur("136");// *อำเภอ / เขต : -> fix เมืองชลบุรี = 136
			bean.setDistrict("1085");// *ตำบล / แขวง : -> fix แสนสุข = 1085
			bean.setPostcode("20130");// *รหัสไปรษณีย์  -> fix 20130
			bean.setPlat("");// เพลท000015
			bean.setNearbyPlaces(nearbyPlaces);// สถานที่ใกล้เคียง
			bean.setZone(zone);// *เขตชุมชน
			bean.setServiceApplicationDate(serviceApplicationDate);// วันที่สมัคร : bdate
			
			bean.setServiceApplicationType(serviceApplicationTypeName);// *ประเภทใบสมัคร
			bean.setServiceApplicationTypeCode(serviceApplicationTypeCode);
			bean.setServicePackage(servicePackage);
			
			bean.setDateOrderBill(dateOrderBill);// *ครบกำหนด
			bean.setBillingFee(billingFee); // *ค่าบริการรอบบิล : 30

			bean.setTotalPoint(totalPoint); // จำนวนจุดทั้งหมด : 35
			
			bean.setDateBill(dateOrderBill);
			bean.setCostBill(costBill);
			
			bean.setDateCut(dateCut);
			bean.setCutStatus(cutStatus);
			
			bean.setDatePayment(datePayment);
			
//			bean.setDigitalPoint(row.getCell(54, Row.CREATE_NULL_AS_BLANK).toString());// จำนวนจุด Digital : 54
//			bean.setInstallDigital(row.getCell(55, Row.CREATE_NULL_AS_BLANK).toString());// วันที่ติด Digital : 55
//			bean.setStatusDigital(row.getCell(56, Row.CREATE_NULL_AS_BLANK).toString());// วันที่ติด Digital : 56
//			bean.setDigitalPrice(row.getCell(57, Row.CREATE_NULL_AS_BLANK).toString());// ราคา Digital : 57
//			bean.setAnalogPoint(row.getCell(58, Row.CREATE_NULL_AS_BLANK).toString());// จำนวนจุด Analog : 58
//			bean.setAnalogPrice(row.getCell(59, Row.CREATE_NULL_AS_BLANK).toString());// ราคา Analog : 59
//			bean.setDeposit(row.getCell(60, Row.CREATE_NULL_AS_BLANK).toString());// เงินประกัน/ค่ามัดจำ : 60
//			bean.setRemark(row.getCell(61, Row.CREATE_NULL_AS_BLANK).toString());// หมายเหตุ / ข้อมูลเพิ่มเติม : 61

		return bean;
	}
    
	private static Date getDataStringDate(Workbook workbook, int indexSheet, String key, int index) throws Exception {
		Date date = null;
		Sheet sheet = workbook.getSheetAt(indexSheet);
	    FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

	    CellReference cellReference = new CellReference(key+index); //pass the cell which contains formula
	    int intRow = cellReference.getRow();
	    Row row = sheet.getRow(cellReference.getRow());
	    Cell cell = row.getCell(cellReference.getCol());

	    CellValue cellValue = evaluator.evaluate(cell);
	    
	    if(null != cellValue){
		    switch (cellValue.getCellType()) {
		    case Cell.CELL_TYPE_NUMERIC:
		    	date = cell.getDateCellValue();
		    	break;
		    }
	    }

		return date;
	}
	
	private static String getDataString(Workbook workbook, int indexSheet, String key, int index) throws Exception {
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
    
	private static double getDataDouble(Workbook workbook, int indexSheet, String key, int index) throws Exception {
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
    
    @SuppressWarnings("deprecation")
	public static String getCellData(String sheetName, String colName, int rowNum)
    {
        try
        {
            int col_Num = -1;
            sheet = workbook.getSheetAt(0);
            row = sheet.getRow(0);
            for(int i = 0; i < row.getLastCellNum(); i++)
            {
            	if(null != row.getCell(i))
            		if(colName.trim().equals(row.getCell(i).getStringCellValue().trim()))
            			col_Num = i;
            }
 
            row = sheet.getRow(rowNum - 1);
            cell = row.getCell(col_Num);
 
            if(cell.getCellTypeEnum() == CellType.STRING)
                return cell.getStringCellValue();
            else if(cell.getCellTypeEnum() == CellType.NUMERIC || cell.getCellTypeEnum() == CellType.FORMULA)
            {
                String cellValue = String.valueOf(cell.getNumericCellValue());
                if(HSSFDateUtil.isCellDateFormatted(cell))
                {
                    DateFormat df = new SimpleDateFormat("dd/MM/yy");
                    Date date = cell.getDateCellValue();
                    cellValue = df.format(date);
                }
                return cellValue;
            }else if(cell.getCellTypeEnum() == CellType.BLANK)
                return "";
            else
                return String.valueOf(cell.getBooleanCellValue());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return "Exception";
        }
    }
    
    public static void readFormula(InputStream inputStream) throws IOException {
    Workbook wb = new XSSFWorkbook(inputStream);
    Sheet sheet = wb.getSheetAt(0);
    FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();

    CellReference cellReference = new CellReference("A"); //pass the cell which contains formula
    Row row = sheet.getRow(cellReference.getRow());
    Cell cell = row.getCell(cellReference.getCol());

    CellValue cellValue = evaluator.evaluate(cell);

    switch (cellValue.getCellType()) {
        case Cell.CELL_TYPE_BOOLEAN:
            System.out.println(cellValue.getBooleanValue());
            break;
        case Cell.CELL_TYPE_NUMERIC:
            System.out.println(cellValue.getNumberValue());
            break;
        case Cell.CELL_TYPE_STRING:
            System.out.println(cellValue.getStringValue());
            break;
        case Cell.CELL_TYPE_BLANK:
            break;
        case Cell.CELL_TYPE_ERROR:
            break;

        // CELL_TYPE_FORMULA will never happen
        case Cell.CELL_TYPE_FORMULA:
            break;
    }

}

    static int indexData = 1;
    
    public static List<MigrateInvoiceBean> ReadExcelInvoice(InputStream inputStream) throws IOException {
    	System.out.println("ReadExcelInvoice");
    	long startTime = System.currentTimeMillis();
    	List<MigrateInvoiceBean> result = new ArrayList<MigrateInvoiceBean>();
    	System.out.println("pre workbook");
        Workbook workbook = new XSSFWorkbook(inputStream);
        System.out.println("workbook");
        Sheet sheet = workbook.getSheetAt(0);

        boolean isTitle = true;
        int index = 1;
        for(Row row : sheet) {
        		if(isTitle){ // ไม่นำข้อมูลหัวตาราง
        			isTitle = false;
        		}else{
        			System.out.println("index : "+(index++));
        			MigrateInvoiceBean bean = new MigrateInvoiceBean();
        			bean = setDataMigrateInvoice(row, bean);
	        	    if(null != bean){
	        	    	result.add(bean);
	        	    }
	        	}
        	}
        
        if (result.size() > 0) {
			  Collections.sort(result, new Comparator<MigrateInvoiceBean>() {
			      public int compare(final MigrateInvoiceBean object1, final MigrateInvoiceBean object2) {
					return object1.getCustomerCode().compareTo(object2.getCustomerCode()); // CustomerCode น้อยไปมาก
			      }
			  });
		}
        long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		int seconds = (int) (totalTime / 1000) % 60 ;
		int minutes = (int) ((totalTime / (1000*60)) % 60);
		int hours   = (int) ((totalTime / (1000*60*60)) % 24);
		System.out.println("ReadExcelInvoice TotalTime : "+String.format("%d hour, %d min, %d sec", hours,minutes,seconds));
        return result;
    }
    
    @SuppressWarnings("deprecation")
	private static MigrateInvoiceBean setDataMigrateInvoiceStatus_S(Row row, MigrateInvoiceBean bean) {
		if(null != row){
			if(StringUtils.isNotEmpty(row.getCell(4, Row.CREATE_NULL_AS_BLANK).toString())){ // Bdate4 -> วันที่ชำระ
				System.out.println("indexData : "+(indexData++));
				String invoiceCode = row.getCell(0, Row.CREATE_NULL_AS_BLANK).toString();
				bean.setInvoiceCode(String.format("%010d",Math.round(Float.valueOf(invoiceCode))));// รหัสใบแจ้งหนี้ -> inv_no : 0
				String invoiceCode2 = row.getCell(1, Row.CREATE_NULL_AS_BLANK).toString();
				bean.setInvoiceCode2(String.format("%010d",Math.round(Float.valueOf(invoiceCode2))));//
				bean.setBdate2(row.getCell(2, Row.CREATE_NULL_AS_BLANK).toString());//
				bean.setBdate3(row.getCell(3, Row.CREATE_NULL_AS_BLANK).toString());//
				bean.setBdate4(row.getCell(4, Row.CREATE_NULL_AS_BLANK).toString());//
				bean.setBdate5(row.getCell(5, Row.CREATE_NULL_AS_BLANK).toString());//
				bean.setCustomerCode(row.getCell(6, Row.CREATE_NULL_AS_BLANK).toString());//
				bean.setFirstName(row.getCell(7, Row.CREATE_NULL_AS_BLANK).toString());//
				bean.setLastName(row.getCell(8, Row.CREATE_NULL_AS_BLANK).toString());//
				bean.setVat(row.getCell(12, Row.CREATE_NULL_AS_BLANK).toString());//
				bean.setNum(row.getCell(13, Row.CREATE_NULL_AS_BLANK).toString());//
				bean.setSta(row.getCell(14, Row.CREATE_NULL_AS_BLANK).toString());//
				
				bean.setCh1(row.getCell(19, Row.CREATE_NULL_AS_BLANK).toString());//
				bean.setCh2(row.getCell(20, Row.CREATE_NULL_AS_BLANK).toString());//
				bean.setCh3(row.getCell(21, Row.CREATE_NULL_AS_BLANK).toString());//
				bean.setCh4(row.getCell(22, Row.CREATE_NULL_AS_BLANK).toString());//
				bean.setCh5(row.getCell(23, Row.CREATE_NULL_AS_BLANK).toString());//
				bean.setChk(row.getCell(24, Row.CREATE_NULL_AS_BLANK).toString());//
				bean.setPchk(row.getCell(25, Row.CREATE_NULL_AS_BLANK).toString());//
				
				bean.setTxtnote(row.getCell(46, Row.CREATE_NULL_AS_BLANK).toString());//
			}else{
				bean = null;
			}
		}else{
			bean = null;
		}
		return bean;
	}
    
    @SuppressWarnings("deprecation")
	private static MigrateInvoiceBean setDataMigrateInvoice(Row row, MigrateInvoiceBean bean) {
		if(null != row){
			if(StringUtils.isEmpty(row.getCell(4, Row.CREATE_NULL_AS_BLANK).toString())){ // Bdate4 -> วันที่ชำระ
				System.out.println("indexData : "+(indexData++));
				String invoiceCode = row.getCell(0, Row.CREATE_NULL_AS_BLANK).toString();
				bean.setInvoiceCode(String.format("%010d",Math.round(Float.valueOf(invoiceCode))));// รหัสใบแจ้งหนี้ -> inv_no : 0
				String invoiceCode2 = row.getCell(1, Row.CREATE_NULL_AS_BLANK).toString();
				bean.setInvoiceCode2(String.format("%010d",Math.round(Float.valueOf(invoiceCode2))));//
				bean.setBdate2(row.getCell(2, Row.CREATE_NULL_AS_BLANK).toString());//
				bean.setBdate3(row.getCell(3, Row.CREATE_NULL_AS_BLANK).toString());//
				bean.setBdate4(row.getCell(4, Row.CREATE_NULL_AS_BLANK).toString());//
				bean.setBdate5(row.getCell(5, Row.CREATE_NULL_AS_BLANK).toString());//
				bean.setCustomerCode(row.getCell(6, Row.CREATE_NULL_AS_BLANK).toString());//
				bean.setFirstName(row.getCell(7, Row.CREATE_NULL_AS_BLANK).toString());//
				bean.setLastName(row.getCell(8, Row.CREATE_NULL_AS_BLANK).toString());//
				bean.setVat(row.getCell(12, Row.CREATE_NULL_AS_BLANK).toString());//
				bean.setNum(row.getCell(13, Row.CREATE_NULL_AS_BLANK).toString());//
				bean.setSta(row.getCell(14, Row.CREATE_NULL_AS_BLANK).toString());//
				
				bean.setCh1(row.getCell(19, Row.CREATE_NULL_AS_BLANK).toString());//
				bean.setCh2(row.getCell(20, Row.CREATE_NULL_AS_BLANK).toString());//
				bean.setCh3(row.getCell(21, Row.CREATE_NULL_AS_BLANK).toString());//
				bean.setCh4(row.getCell(22, Row.CREATE_NULL_AS_BLANK).toString());//
				bean.setCh5(row.getCell(23, Row.CREATE_NULL_AS_BLANK).toString());//
				bean.setChk(row.getCell(24, Row.CREATE_NULL_AS_BLANK).toString());//
				bean.setPchk(row.getCell(25, Row.CREATE_NULL_AS_BLANK).toString());//
				
				bean.setTxtnote(row.getCell(46, Row.CREATE_NULL_AS_BLANK).toString());//
			}else{
				bean = null;
			}
		}else{
			bean = null;
		}
		return bean;
	}
	
	public static void readXLSFile() throws IOException
	{
		InputStream ExcelFileToRead = new FileInputStream("C:/Test.xls");
		
		HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

		HSSFSheet sheet=wb.getSheetAt(0);
		HSSFRow row; 
		HSSFCell cell;

		Iterator rows = sheet.rowIterator();

		while (rows.hasNext())
		{
			row=(HSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			
			while (cells.hasNext())
			{
				cell=(HSSFCell) cells.next();
		
				if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
				{
					System.out.print(cell.getStringCellValue()+" ");
				}
				else if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
				{
					System.out.print(cell.getNumericCellValue()+" ");
				}
				else
				{
					//U Can Handel Boolean, Formula, Errors
				}
			}
			System.out.println();
		}
	
	}
	
	public static void writeXLSFile() throws IOException {
		
		String excelFileName = "C:/Test.xls";//name of excel file

		String sheetName = "Sheet1";//name of sheet

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(sheetName) ;

		//iterating r number of rows
		for (int r=0;r < 5; r++ )
		{
			HSSFRow row = sheet.createRow(r);
	
			//iterating c number of columns
			for (int c=0;c < 5; c++ )
			{
				HSSFCell cell = row.createCell(c);
				
				cell.setCellValue("Cell "+r+" "+c);
			}
		}
		
		FileOutputStream fileOut = new FileOutputStream(excelFileName);
		
		//write this workbook to an Outputstream.
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}
	
	public static void readXLSXFile() throws IOException
	{
		InputStream ExcelFileToRead = new FileInputStream("C:/Test.xlsx");
		XSSFWorkbook  wb = new XSSFWorkbook(ExcelFileToRead);
		
		XSSFWorkbook test = new XSSFWorkbook(); 
		
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row; 
		XSSFCell cell;

		Iterator rows = sheet.rowIterator();

		while (rows.hasNext())
		{
			row=(XSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			while (cells.hasNext())
			{
				cell=(XSSFCell) cells.next();
		
				if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
				{
					System.out.print(cell.getStringCellValue()+" ");
				}
				else if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC)
				{
					System.out.print(cell.getNumericCellValue()+" ");
				}
				else
				{
					//U Can Handel Boolean, Formula, Errors
				}
			}
			System.out.println();
		}
	
	}
	
	public static void writeXLSXFile() throws IOException {
		
		String excelFileName = "C:/Test.xlsx";//name of excel file

		String sheetName = "Sheet1";//name of sheet

		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(sheetName) ;

		//iterating r number of rows
		for (int r=0;r < 5; r++ )
		{
			XSSFRow row = sheet.createRow(r);

			//iterating c number of columns
			for (int c=0;c < 5; c++ )
			{
				XSSFCell cell = row.createCell(c);
	
				cell.setCellValue("Cell "+r+" "+c);
			}
		}

		FileOutputStream fileOut = new FileOutputStream(excelFileName);

		//write this workbook to an Outputstream.
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}

	public static void main(String[] args) throws IOException {
		
		writeXLSFile();
		readXLSFile();
		
		writeXLSXFile();
		readXLSXFile();

	}

}
