package com.hdw.mccable.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import com.github.wnameless.smartcard.APDU;
//import com.github.wnameless.smartcard.AutomatedReader;
//import com.github.wnameless.smartcard.CardTask;
//import com.github.wnameless.smartcard.INS;
import com.hdw.mccable.utils.SmartCardReader;
import com.hdw.mccable.utils.SmartCardReaderUtil;
import com.hdw.mccable.utils.ThaiLandIdCard;
import com.hdw.mccable.utils.UploadFileUtil;

import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;
import javax.xml.bind.DatatypeConverter;

import org.apache.xmlbeans.impl.util.Base64;

public class Main1 {

	public static void main(String[] args) throws Exception {

		String pattern = "(\\D)";
		System.out.println("58AC0713".replaceAll(pattern, ""));
		
		pattern = "(\\D)";
		System.out.println("58AC0713".replaceAll(pattern, ""));
		
		System.out.println("C001".contains("C"));
		
		System.out.println("กิจสมบูรณ์ อาคารร่วมเก็บ JBP".contains("JBP"));
		
		double vat = 7, notVat = 0, resultVat = 0, price = 481.5;
		
		resultVat = (price*vat) / (100 + vat);
		if(price > resultVat){
			notVat = price-(resultVat);
		}
		System.out.println(notVat);
		
		String aa = "February 5, 2018 - March 6, 2018";
		System.out.println(" February 5, 2018 - March 6, 2018".substring(1));
		
		System.out.println(aa);
		
		aa = " February 5, 2018 - March 6, 2018".replaceFirst("^ *", "");
		
		System.out.println(aa);
		
		aa = " February 5, 2018 - March 6, 2018";
		
		if (aa.charAt(0) == ' ') {
		     aa = aa.substring(1);
		}
		
		System.out.println(aa);
		
		String Str = " February 5, 2018 - March 6, 2018";

	      System.out.print("Return Value :" );
	      System.out.println(Str.trim() );
	      
		
//		SmartCardReaderUtil smartCardReaderUtil = SmartCardReader.Reader();
        
//		System.out.println("smartCardReaderBean : " + smartCardReaderUtil.toString());
		
//		UploadFileUtil.uploadFileToServer("/temp/", smartCardReaderUtil.getPhotoPart());
		
//        test();
        
//        test1();
        
//        api();
		
//		testSmartCardReaderV2();
		
//		testSmartCardReader();
		
//		String au = "อำเภอเสลภูมิ";
//		au = au.substring(5);
//		
//		System.out.println(au);
//		
//		au = "จังหวัดร้อยเอ็ด";
//		au = au.substring(7);
//		
//		System.out.println(au);
//		
//		au = "ตำบลกลาง";
//		au = au.substring(4);
//		
//		System.out.println(au);
	}
	
	private static void api() {
		try {

			URL url = new URL("http://localhost:8080/smartcard/data/");
//			URL url = new URL("http://localhost:8080/smartcard/picture/");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }
	}

	public static void testSmartCardReaderV2() {
//		System.out.println("testSmartCardReaderV2");
//		// It's an example of reading a TWN Health Insurance Card
//		CommandAPDU cmd1 = APDU.builder().setCLA((byte) 0x00).setINS((byte) 0xA4).setP1((byte) 0x04).setP2((byte) 0x00)
//				.setData((byte) 0xA0, (byte) 0X00, (byte) 0x00, (byte) 0x00, (byte) 0x54, (byte) 0x48, (byte) 0x00, (byte) 0x01).build(); // Lc field is set automatically by given data length
//		CommandAPDU cmd2 = APDU.builder().setCLA((byte) 0x80).setINS((byte) 0xB0).setP1((byte) 0x00).setP2((byte) 0x04)
//				.setData((byte) 0x00).setLe((byte) 0x0D).build(); // It's fine to use a byte array to set the data
//		
//		System.out.println("cmd1 : "+cmd1);
//		System.out.println("cmd2 : "+cmd2);
//		
//		// It's an example of reading a TWN Health Insurance Card
//		CommandAPDU cmd12 = APDU.builder().setINS(INS.SELECT_FILE).setP1((byte) 0x04)
//		                                 .setData("D1580000010000000000000000001100").build(); // Lc field is set automatically by given data length
//		CommandAPDU cmd22 = APDU.builder().setINS(INS.GET_DATA) .setP1((byte) 0x11)
//		                                 .setData((byte) 0x00, (byte) 0x00).build();          // It's fine to use a byte array to set the data
//
//		AutomatedReader reader = new AutomatedReader(cmd2);
//		System.out.println("reader : "+reader);
//		reader.reading(1000, new CardTask() { // It reads from all CardChannels every second(1000ms)
//			
//		    public void execute(CardTerminal terminal, List<ResponseAPDU> responses) {
//		    	System.out.println("Terminals>>>>>>>");
//		    }
//
//		});
//		reader.stop();
	}
    
    public static String toString(byte[] bytes) {
        StringBuilder sbTmp = new StringBuilder();
        for(byte b : bytes){
                sbTmp.append(String.format("%X", b));
        }
        return sbTmp.toString();
    }
	
	private static void testSmartCardReader() {
        try {
	            // Show the list of available terminals
	            // On Windows see HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Cryptography\Calais\Readers
	            TerminalFactory factory = TerminalFactory.getDefault();
	            List terminals = factory.terminals().list();
	            System.out.println("Terminals count: " + terminals.size());
	            System.out.println("Terminals: " + terminals);
	
	            // Get the first terminal in the list
	            CardTerminal terminal = (CardTerminal) terminals.get(0);
	
	            // Establish a connection with the card using
	            // "T=0", "T=1", "T=CL" or "*"
	            Card card = terminal.connect("*");
	            System.out.println("Card: " + card);
	
	            // Get ATR
	            byte[] baATR = card.getATR().getBytes();
	            System.out.println("ATR: " + toString(baATR) );
	            
	            
	            
	            // Select Card Manager
	            // - Establish channel to exchange APDU
	            // - Send SELECT Command APDU
	            // - Show Response APDU
	            CardChannel channel = card.getBasicChannel();
	
	            //SELECT Command
	            // See GlobalPlatform Card Specification (e.g. 2.2, section 11.9)
	            // CLA: 00
	            // INS: A4
	            // P1: 04 i.e. b3 is set to 1, means select by name
	            // P2: 00 i.e. first or only occurence
	            // Lc: 08 i.e. length of AID see below
	            // Data: A0 00 00 00 03 00 00 00
	            // AID of the card manager,
	            // in the future should change to A0 00 00 01 51 00 00
	            // อ่านค่า Select
	            byte[] baCommandAPDU = {
	                (byte) 0x00, (byte) 0xA4, (byte) 0x04, 
	                (byte) 0x00, (byte) 0x08, (byte) 0xA0, 
	                (byte) 0x00, (byte) 0x00, (byte) 0x00, 
	                (byte) 0x54, (byte) 0x48, (byte) 0x00, 
	                (byte) 0x01
	            };
	            System.out.println("APDU <<<: " + toString(baCommandAPDU));
	
	            ResponseAPDU r = channel.transmit(new CommandAPDU(baCommandAPDU));
	            System.out.println("APDU >>>: " + toString(r.getBytes()));
	            // อ่านเลขบัตรประชาชน
	            byte[] command_idcard = {
	                (byte) 0x80, (byte) 0xb0, (byte) 0x00,
	                (byte) 0x04, (byte) 0x02, (byte) 0x00,
	                (byte) 0x0d
	            };
	            ResponseAPDU response_command_idcard = channel.transmit(new CommandAPDU(command_idcard));	
	            byte response_idcard[] = response_command_idcard.getData();
	            
	            String idcard = new String(response_idcard);
	            System.out.println("idcard : " + idcard);
	            
	            // อ่านเลขบัตรประชาชน
	            byte[] command_TH_Fullname = {
	                (byte) 0x80, (byte) 0xB0, (byte) 0x00,
	                (byte) 0x11, (byte) 0x02, (byte) 0x00,
	                (byte) 0x64
	            };
	            ResponseAPDU response_command_TH_Fullname = channel.transmit(new CommandAPDU(ThaiLandIdCard.getCommandThFullname()));	
	            byte response_TH_Fullname[] = response_command_TH_Fullname.getData();
	            
	            ResponseAPDU response_command_DateOfDirth = channel.transmit(new CommandAPDU(ThaiLandIdCard.getCommandDateOfDirth()));	
	            byte response_DateOfDirth[] = response_command_DateOfDirth.getData();
	            
	            ResponseAPDU response_command_Gender = channel.transmit(new CommandAPDU(ThaiLandIdCard.getCommandGender()));	
	            byte response_Gender[] = response_command_Gender.getData();
	            
	            ResponseAPDU response_command_Card_Issuer = channel.transmit(new CommandAPDU(ThaiLandIdCard.getCommandCardIssuer()));	
	            byte response_Card_Issuer[] = response_command_Card_Issuer.getData();
	            
	            ResponseAPDU response_command_Issue_Date = channel.transmit(new CommandAPDU(ThaiLandIdCard.getCommandIssueDate()));	
	            byte response_Issue_Date[] = response_command_Issue_Date.getData();
	            
	            ResponseAPDU response_command_ExpireDate = channel.transmit(new CommandAPDU(ThaiLandIdCard.getCommandExpireDate()));	
	            byte response_ExpireDate[] = response_command_ExpireDate.getData();
	            
	            ResponseAPDU response_command_Address = channel.transmit(new CommandAPDU(ThaiLandIdCard.getCommandAddress()));	
	            byte byte_Address[] = response_command_Address.getData();
	            
	            String data = "";
				try {
					data = new String(response_TH_Fullname,"TIS-620");
					System.out.println("TH_Fullname : " + data);

					data = new String(response_DateOfDirth,"TIS-620");
					System.out.println("DateOfDirth : " + data);
					
					data = new String(response_Gender,"TIS-620");
					System.out.println("Gender : " + data);
					
					data = new String(response_Card_Issuer,"TIS-620");
					System.out.println("Card_Issuer : " + data);
					
					data = new String(response_Issue_Date,"TIS-620");
					System.out.println("Issue_Date : " + data);
					
					data = new String(response_ExpireDate,"TIS-620");
					System.out.println("ExpireDate : " + data);
					
					data = new String(byte_Address,"TIS-620");
					System.out.println("Address : " + data);
					
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println("[Photo1]");
				
				try {
//					convertOutputPhoto(responseList,"photo1");
					convertOutputPhoto(channel);
//					convertOutputPhoto2(channel);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            
	            // Disconnect
	            // true: reset the card after disconnecting card.
	            card.disconnect(true);
	
	    } 
	    catch(CardException ex)  {
	    }
	}

	public static void convertOutputPhoto(CardChannel channel) throws UnsupportedEncodingException, IOException, CardException
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[][] commandCardPhoto = ThaiLandIdCard.getCommandCardPhoto();
		for (int i = 0; i < commandCardPhoto.length; i++){
			ResponseAPDU response = channel.transmit(new CommandAPDU(commandCardPhoto[i]));
			byte resp[] = response.getData();
			for (int j=0; j < resp.length; j++){
				bos.write((char)resp[j]);
			}
		}
		byte[] bytes = bos.toByteArray();
		FileOutputStream outimg = new FileOutputStream("/temp/image_test.jpg");
		
		outimg.write(bytes);
		outimg.flush();
		outimg.close();
	}
	
	public static void convertOutputPhoto2(CardChannel channel) throws UnsupportedEncodingException, IOException, CardException
	{
		FileOutputStream out = null;
		ByteArrayInputStream in = null;
		int cursor;

		byte[] destination = new byte[0];

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[][] commandCardPhoto = ThaiLandIdCard.getCommandCardPhoto();
		for (int i = 0; i < commandCardPhoto.length; i++){
			ResponseAPDU response = channel.transmit(new CommandAPDU(commandCardPhoto[i]));
			destination = concatenateByteArrays(destination, response.getBytes());
		}
		byte[] bytes = bos.toByteArray();
		FileOutputStream outimg = new FileOutputStream("/temp/image_test.jpg");

	}
	
	public static void convertOutputPhoto (List<ResponseAPDU> response,String imgname) throws UnsupportedEncodingException, IOException
	{
		
//		FileOutputStream out = null;
//        FileInputStream in = null;
//        int cursor;
//        
//        try{
//            in = new FileInputStream(new File("/temp/test.jpg"));
//            out = new FileOutputStream("/temp/TestImage.jpg");
//            while((cursor = in.read())!=-1){
//                out.write(cursor);
//            }
//        }
//        finally{
//            if(in!=null){
//                in.close();
//            }
//            if(out!=null){
//                out.close();
//            }
//            System.out.println("Read and Write complete");
//        }
        
//		FileOutputStream out = null;
//        ByteArrayInputStream in = null;
//        int cursor;
//        
//        byte[] destination = new byte[0];
//        
//        if(null != response && response.size() > 0){
//	    	for(ResponseAPDU res:response){
//	    		destination = concatenateByteArrays(destination , res.getBytes());
//	    	}
//	    }
//        
//        try{
//            in = new ByteArrayInputStream(destination);
//            out = new FileOutputStream("/temp/TestImage.jpg");
//            while((cursor = in.read())!=-1){
//                out.write(cursor);
//            }
//        }
//        finally{
//            if(in!=null){
//                in.close();
//            }
//            if(out!=null){
//                out.close();
//            }
//            System.out.println("Read and Write complete");
//        }
        
		
			String hex = "";
	        if(null != response && response.size() > 0){
	        	for(ResponseAPDU res:response){
	        		hex += DatatypeConverter.printHexBinary(res.getBytes());
	        	}
	        }
	        
	        byte[] destination = new byte[0];
	        
	        if(null != response && response.size() > 0){
		    	for(ResponseAPDU res:response){
		    		destination = concatenateByteArrays(destination , res.getBytes());
		    	}
		    }
	        
	        FileOutputStream outimg = new FileOutputStream("/temp/image_" + imgname + ".jpg");
//	        for (int i = 0; i < hex.length(); i += 2)
//	        {
//	         int byteimg = Character.digit(hex.charAt(i), 16) * 16 + 
//	                        Character.digit(hex.charAt(i + 1), 16);
//	         outimg.write(0, 0, 0);
//	         
//	         
//	        }
	        
	        outimg.write(destination, 0, destination.length);

	        
	        
	}
	
	private static byte[] concatenateByteArrays(byte[] a, byte[] b) {
	    byte[] result = new byte[a.length + b.length]; 
	    System.arraycopy(a, 0, result, 0, a.length); 
	    System.arraycopy(b, 0, result, a.length, b.length); 
	    return result;
	} 
	
	private static void test1() throws Exception {
		try {
	          String name = "java.lang.String";
	          String methodName = "toLowerCase";

	          // get String Class
	          Class cl = Class.forName(name);

	          // get the constructor with one parameter
	          Constructor constructor =
	             cl.getConstructor
	               (new Class[] {String.class});

	          // create an instance
	          Object invoker =
	             constructor.newInstance
	               (new Object[]{"REAL'S HOWTO"});

	          // the method has no argument
	          Class  arguments[] = new Class[] { };

	          // get the method
	          Method objMethod =
	             cl.getMethod(methodName, arguments);

	          // convert "REAL'S HOWTO" to "real's howto"
	          Object result =
	             objMethod.invoke
	               (invoker, (Object[])arguments);

	          System.out.println(result);
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	}
	
	private static String runMethod(Object instance, String methodName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
	    Method method = instance.getClass().getMethod(methodName);
	    return (String)method.invoke(instance);
	}
	
	private static void test() {
		//System.out.println(new DateUtil().getCurrentCeString("dd-MM-yyyy"));
		float sumVat = 0f;
		String s = new DecimalFormat("#,##0.00").format(sumVat);
		System.out.println(s);
		
		String name = "คุณสุรวีร์";
		if("คุณ".equals(name.substring(0,3))){
			name = name.substring(0,3);
		}
		System.out.println(name);
		
		name = "ร้านเลี๊ยบตุ่ยตำอร่อย";
		if("คุณ".equals(name.substring(3))){
			name = name.substring(3);
		}
		System.out.println(name);
		
		name = String.format("|%05d|", 93); // prints: |00000000000000000093|
		System.out.println(name);
		
		String a1 = "ทองสุวรรณ์", a2 = "ทองสุวรรณ์(1)", a3 = "ทองสุวรรณ์ (2)", a4 = "ทองสุวรรณ์xxx";
		Pattern pattern = Pattern.compile(a1);
		Matcher matcher = pattern.matcher(a2);
		
		System.out.println("lookingAt()12: "+matcher.lookingAt());
	    System.out.println("matches()12: "+matcher.matches());
	    
	    pattern = Pattern.compile(a1);
		matcher = pattern.matcher(a3);
		
		System.out.println("lookingAt()13: "+matcher.lookingAt());
	    System.out.println("matches()13: "+matcher.matches());
	    
	    pattern = Pattern.compile(a1);
		matcher = pattern.matcher(a4);
		
		System.out.println("lookingAt()14: "+matcher.lookingAt());
	    System.out.println("matches()14: "+matcher.matches());
	    

		Date currentDate = new Date();
		Date currentDate2 = new Date();
		
		Calendar c = Calendar.getInstance();
        c.setTime(currentDate2);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.add(Calendar.DAY_OF_MONTH, 5);
        
        currentDate2 = c.getTime();
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        // Set time fields to zero
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        currentDate = cal.getTime();
		
        System.out.println("currentDate2 : "+currentDate2);
        System.out.println("currentDate : "+currentDate);
        System.out.println("currentDate2 before : "+currentDate2.before(currentDate));
        System.out.println("currentDate2 after : "+currentDate2.after(currentDate));
        
        String url = "http://www.synergymicrotech.com/demo/login";
        if(url.contains("http")){
        	url = url.replace("http", "https");
        }
        System.out.println("url : "+url);
        
        
        String sa = "พม่า";
        System.out.println(">>> : "+ !"คุณออ  (พม่ามหาชัยนิเวศน์)".contains(sa));
        
        // พินิจนึก  (1) | พินิจนึก  (1)
        String m1 = "พินิจนึก  (1)";
        String m2 = "พินิจนึก  (1)";
        pattern = Pattern.compile(m1);
		matcher = pattern.matcher(m2);
		System.out.println("1> "+ matcher.lookingAt());
		System.out.println("2> "+ m1.equals(m2));

		// ------------------EOC------------------
		System.out.println("===========================================");
		
		m1 = "--------";
		m2 = "------------------EOC------------------";
		pattern = Pattern.compile(m1);
		matcher = pattern.matcher(m2);
		System.out.println(matcher.lookingAt());
		
		System.out.println("===========================================");
	}

}
