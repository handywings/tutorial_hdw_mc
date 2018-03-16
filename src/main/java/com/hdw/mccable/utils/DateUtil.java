package com.hdw.mccable.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {
	public static final Locale US = new Locale("en", "US");
	public static final Locale TH = new Locale("th", "TH");
	/**
	 * DEFAULT_FORMAT = "dd/MM/yyyy"
	 */
	public static final String DEFAULT_FORMAT = "dd/MM/yyyy";
	/**
	 * "HH:mm:ss"
	 */
	public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

	// example 01-04-2017 -> 2017-04-01 00:00:00
	public Date convertStringToDateTimeDb(String StringDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("en", "US"));
		try {
			StringDate = StringDate.replace("\"", "");
			String[] seperate = StringDate.split("-");
			StringBuffer StringDateNew = new StringBuffer(); 
			//year
			StringDateNew = StringDateNew.append(seperate[2]);
			StringDateNew = StringDateNew.append("-");
			//mounth
			StringDateNew = StringDateNew.append(seperate[1]);
			StringDateNew = StringDateNew.append("-");
			//day
			StringDateNew = StringDateNew.append(seperate[0]);
			StringDateNew = StringDateNew.append(" 00:00:00");
			
			Date dt = format.parse(StringDateNew.toString());
			return dt;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Date convertStringToDateTimeDb(String StringDate,String hr,String mn) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("en", "US"));
		try {
			StringDate = StringDate.replace("\"", "");
			String[] seperate = StringDate.split("-");
			StringBuffer StringDateNew = new StringBuffer(); 
			//year
			StringDateNew = StringDateNew.append(seperate[2]);
			StringDateNew = StringDateNew.append("-");
			//mounth
			StringDateNew = StringDateNew.append(seperate[1]);
			StringDateNew = StringDateNew.append("-");
			//day
			StringDateNew = StringDateNew.append(seperate[0]);
			String strTime = " " + hr+":"+mn+":"+"00";
			StringDateNew = StringDateNew.append(strTime);
			
			Date dt = format.parse(StringDateNew.toString());
			return dt;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String convertCeDateToBeString(Date ceDate, String format) throws ParseException {
		String ceString = convertDateToString(ceDate, format, new Locale("en", "US"));
		return convertCeStringToBeString(ceString, format, format);
	}

	public static Date convertBeStringToCeDate(String beString, String format) throws ParseException {
		String ceString = convertBeStringToCeString(beString, format, format);
		return convertStringToDate(ceString, format, new Locale("en", "US"));
	}

	public static String convertDateToString(Date date, String format, Locale locale) {
		if (date == null) {
			return "";
		}
		String s_date;
		SimpleDateFormat df = new SimpleDateFormat(format, locale);
		s_date = df.format(date);
		return s_date;
	}

	public static Date convertStringToDate(String s_date, String format, Locale locale) throws ParseException {
		if (s_date == null) {
			return null;
		}
		Date date;
		SimpleDateFormat df = new SimpleDateFormat(format, locale);
		date = df.parse(s_date);
		return date;
	}


	public static String convertBeStringToCeString(String beString, String beFormat, String ceFormat)
			throws ParseException {
		String sCEDate;
		Date beDate;
		beDate = convertStringToDate(beString, beFormat, new Locale("th", "TH"));
		sCEDate = convertDateToString(beDate, ceFormat, new Locale("en", "US"));
		return sCEDate;
	}

	public static String convertCeStringToBeString(String sCEDate, String ceFormat, String beFormat)
			throws ParseException {
		String sBEDate;
		Date ceDate;
		ceDate = convertStringToDate(sCEDate, ceFormat, new Locale("en", "US"));
		sBEDate = convertDateToString(ceDate, beFormat, new Locale("th", "TH"));
		return sBEDate;
	}

	@SuppressWarnings("deprecation")
	public static String getTimeYear(String dt) {
		int year = 0;
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat(DEFAULT_FORMAT, new Locale("en", "US"));
		try {
			date = df.parse(dt);
		} catch (Exception e) {
		}
		int thisY = new Date().getYear();
		int thisM = new Date().getMonth();
		int thisD = new Date().getDate();
		int currYr = date.getYear();
		int currM = date.getMonth();
		int currD = date.getDate();

		if (thisY < currYr) {
			return "0";
		} else if (thisY == currYr) {
			if (thisM < currM) {
				return "0";
			} else if (thisM == currM) {
				if (thisD > currD) {

					return "0.1";
				} else if (thisD == currD) {

					return "0";
				} else {

					return "0";
				}
			} else {
				return "0.1";
			}
		} else {
			if (thisM < currM) {
				year = thisY - currYr - 1;
				return year + ".1";
			} else if (thisM > currM) {
				year = thisY - currYr;
				return year + ".1";
			} else {
				if (thisD > currD) {
					year = thisY - currYr;
					return year + ".1";
				} else if (thisD == currD) {
					year = thisY - currYr;
					return year + "";
				} else {
					year = thisY - currYr - 1;
					return year + ".1";
				}
			}
		}
	}

	public static Date getCurrentBeDate() {
		return getTHCalendar().getTime();
	}

	public static Date getCurrentCeDate() {
		return getUSCalendar().getTime();
	}

	public static Date getCurrentCeDateWithoutTime() {

		Date date = getCurrentCeDate();
		try {
			date = convertStringToDate(getCurrentCeString("dd/MM/yyyy"), "dd/MM/yyyy", US);
		} catch (ParseException ex) {
			return null;
		}
		return date;

	}

	public static Calendar getUSCalendar() {
		return Calendar.getInstance(new Locale("en", "US"));
	}

	public static Calendar getTHCalendar() {
		return Calendar.getInstance(new Locale("th", "TH"));
	}

	public static String getCurrentBeString(String format) {
		Locale thai = new Locale("th", "TH");
		return convertDateToString(Calendar.getInstance(thai).getTime(), format, thai);
	}

	public static String getCurrentCeString(String format) {
		Locale en = new Locale("en", "US");
		return convertDateToString(Calendar.getInstance(en).getTime(), format, en);
	}

	public static Date addDay(Date date, int numOfAddDate) {
		Calendar c = Calendar.getInstance(new Locale("EN", "en"));
		c.setTime(date);
		c.add(Calendar.DATE, numOfAddDate);
		return c.getTime();
	}

	public static Date addMonth(Date date, int numOfAddMonth) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, numOfAddMonth);
		return c.getTime();
	}

	public static boolean isSameDay(Date date1, Date date2) {
		String d1 = convertDateToString(date1, "dd/MM/yyyy", Locale.getDefault());
		String d2 = convertDateToString(date2, "dd/MM/yyyy", Locale.getDefault());
		if (d1.equals(d2)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method is used to check date is between firstDate and lastDate
	 *
	 * @return boolean
	 */
	public static boolean isBetweenDate(Date compareDate, Date firstDate, Date lastDate) {
		if (compareDate.compareTo(firstDate) >= 0 && compareDate.compareTo(lastDate) <= 0) {
			return true;
		}
		return false;
	}

	public static String getLastDayOfMonth(Date date) {
		String dateString = new String(new SimpleDateFormat("dd/MM/yyyy", new Locale("en", "US")).format(date));
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getActualMaximum(Calendar.DATE) + "/" + dateString.split("/")[1] + "/" + dateString.split("/")[2];
	}

	public static String getCurrentMonth() {
		try {
			return convertCeDateToBeString(getCurrentCeDate(), "MM");
		} catch (ParseException ex) {

		}
		return null;
	}

	/**
	 * @deprecated move to getCurrentBeYear()
	 * @return
	 */
	public static String getCurrentYear() {
		return getCurrentBeYear();
	}

	public static String getCurrentBeYear() {
		try {
			return convertCeDateToBeString(getCurrentCeDate(), "yyyy");
		} catch (ParseException ex) {

		}
		return null;
	}

	public static String getCurrentCeYear() {
		return convertDateToString(getCurrentCeDate(), "yyyy", US);
	}

	public static boolean isWeekend(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		return (day == 1 || day == 7);
	}

	public static boolean isWeekend(String date, String formatDate, Locale locale) throws ParseException {
		return isWeekend(convertStringToDate(date, formatDate, locale));
	}

	/**
	 * +1 Day
	 * 
	 * @param date
	 */
	public static Date nextDay(Date date) {
		Date d = (Date) date.clone();
		d.setTime(date.getTime() + (1000 * 60 * 60 * 24));
		return d;
	}

	/**
	 * -1 Day
	 * 
	 * @param date
	 */
	public static Date previousDay(Date date) {
		Date d = (Date) date.clone();
		d.setTime(date.getTime() - (1000 * 60 * 60 * 24));
		return d;
	}

	public static String convertFromJulianTime(String julianTime) {
		int julian = Integer.parseInt(julianTime);
		int hours = julian / 3600;
		int minutes = (julian % 3600) / 60;
		return String.format("%02d", hours) + ":" + String.format("%02d", minutes);
	}

	public static Integer YearDiff(Calendar befDate, Calendar afDate) { 
		int years = -1;
		while (!befDate.after(afDate)) {
			befDate.add(Calendar.YEAR, 1);
			years++;
		}
		return years;
	}
	
	public boolean ExpireDate(Date expireDate){
		if(expireDate != null) {
			//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", new Locale("en", "US"));
			Date currentDate = new Date();
			if(isSameDay(expireDate,currentDate)){
				return false;
			}else if(currentDate.compareTo(expireDate) > 0){
				return true;
			}
		}
		
		return false;
	}
	
	public Date convertStringToOrderBillDateCurrent(String orderBillDate, Date dateOrderBillOld) {		
		//create date current pay
		int currentMonth;
		int currentYear;
		int lastDayCurrentMonth = 0;
		int currentDay;
		
		if(orderBillDate!= null && !orderBillDate.isEmpty()){
			String[] splitDate = orderBillDate.split("-");
			currentMonth = Integer.valueOf(splitDate[1]);
			currentYear = Integer.valueOf(splitDate[2]);
			currentDay = Integer.valueOf(splitDate[0]);
		} else {
			Calendar now = Calendar.getInstance(new Locale("EN", "en"));
			currentMonth = now.get(Calendar.MONTH) + 1;
			currentYear = now.get(Calendar.YEAR);
			currentDay = now.get(Calendar.DAY_OF_MONTH);
		}

		String dateCreateMatch = "";
		if(dateOrderBillOld != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String s = formatter.format(dateOrderBillOld);
			String[] split = s.split("-");
			
			//check last day of month
			Date dateCheck = new DateUtil().convertStringToDateTimeDb(split[2]+"-"+currentMonth+"-"+currentYear);
			lastDayCurrentMonth = new DateUtil().lastDateOfmonth(dateCheck);
			if(Integer.valueOf(split[2]) < lastDayCurrentMonth){
				dateCreateMatch = currentYear+"-"+currentMonth+"-"+currentDay;
			}else{
				dateCreateMatch = currentYear+"-"+currentMonth+"-"+split[2];
			}
			
			Date date = null;
			try {
				date = formatter.parse(dateCreateMatch.toString());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return date;
		}
		
		return null;
	}
	
	public int lastDateOfmonth(Date dateCheck){
		//Date dateCheck = new DateUtil().convertStringToDateTimeDb("01"+"-"+"05"+"-"+"2017");
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(dateCheck);  

        calendar.add(Calendar.MONTH, 1);  
        calendar.set(Calendar.DAY_OF_MONTH, 1);  
        calendar.add(Calendar.DATE, -1);  

        Date lastDayOfMonth = calendar.getTime();  

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
//        System.out.println("Today            : " + sdf.format(dateCheck));  
//        System.out.println("Last Day of Month: " + sdf.format(lastDayOfMonth));
        String lastDay = sdf.format(lastDayOfMonth);
        String[] splitGetLastDay = lastDay.split("-");
        return Integer.valueOf(splitGetLastDay[2]);
	}
	
	public static int[] createYearThreePrevious(){
		int[] years = new int[3];
		Date currentDate = new Date();
		// convert date to calendar
		Calendar c = Calendar.getInstance(new Locale("TH", "th"));

		// สร้าง 3 ปีย้อนหลัง
		c.setTime(currentDate);
		years[0] = c.getWeekYear();

		c.setTime(currentDate);
		c.add(Calendar.YEAR, -1);
		years[1] = c.getWeekYear();

		c.setTime(currentDate);
		c.add(Calendar.YEAR, -2);
		years[2] = c.getWeekYear();
		// สร้าง 3 ปีย้อนหลัง
		return years;
	}
	
	public static String[] createMonth(){
		SimpleDateFormat formatDataTh = new SimpleDateFormat("MMMM", new Locale("TH", "th"));
		SimpleDateFormat mm = new SimpleDateFormat("MM", Locale.US);
		String[] month = new String[12];
		// convert date to calendar
		Calendar c = Calendar.getInstance(new Locale("TH", "th"));

		try {
			// สร้าง เดือน
			Date dateForMonth = mm.parse("01");
			for (int i = 0; i < 12; i++) {
				c.setTime(dateForMonth);
				c.add(Calendar.MONTH, +(i));
				month[i] = formatDataTh.format(c.getTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return month;
	}
	
	public static synchronized String getDateThaiFormat(Date date) {
		try {
			TimeZone.setDefault(TimeZone.getTimeZone("Asia/Bangkok"));
			String date_str = "";
			if (date != null) {
				SimpleDateFormat simple_date = new SimpleDateFormat("dd MMMM yyyy",
						new Locale("th", "TH"));
				date_str = simple_date.format(date);
				return date_str;
			}
			return date_str;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "-";
	}
	
	public static synchronized String getDateThaiFormatFull(Timestamp timestamp) {
		try {
			TimeZone.setDefault(TimeZone.getTimeZone("Asia/Bangkok"));
			String date_str = "";
			if (timestamp != null) {
				SimpleDateFormat simple_date = new SimpleDateFormat(
						"d MMMM yyyy (HH:mm:ss น.)",
						new Locale("th", "TH"));
				date_str = simple_date.format(timestamp);
			}
			return date_str;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "-";		
	}
}
