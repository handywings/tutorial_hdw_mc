package com.hdw.mccable.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;

import org.apache.commons.lang.StringUtils;

import com.drew.imaging.ImageProcessingException;


public class SmartCardReader {
	
	@SuppressWarnings({ "restriction", "rawtypes", "unused" })
	public static HashMap<String, Object> Reader() {
		HashMap<String, Object> result = new HashMap<String, Object>();
		SmartCardReaderUtil smartCardReaderUtil = new SmartCardReaderUtil();
		
		String CID = StringUtils.EMPTY;
		String TH_Fullname = StringUtils.EMPTY;
		String EN_Fullname = StringUtils.EMPTY;
		String Date_of_birth = StringUtils.EMPTY;
		String Gender = StringUtils.EMPTY;
		String Card_Issuer = StringUtils.EMPTY;
		String Issue_Date = StringUtils.EMPTY;
		String Expire_Date = StringUtils.EMPTY;
		String Address = StringUtils.EMPTY;
		String PhotoPart = StringUtils.EMPTY;
		
		try {
			// Show the list of available terminals
			// On Windows see
			// HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Cryptography\Calais\Readers
			
			Class pcscterminal = Class.forName("sun.security.smartcardio.PCSCTerminals");
		    Field contextId = pcscterminal.getDeclaredField("contextId");
		    contextId.setAccessible(true);

		    if(contextId.getLong(pcscterminal) != 0L)
		    {
		        Class pcsc = Class.forName("sun.security.smartcardio.PCSC");
		        Method SCardEstablishContext = pcsc.getDeclaredMethod(
		                                           "SCardEstablishContext",
		                                           new Class[] {Integer.TYPE }
		                                       );
		        SCardEstablishContext.setAccessible(true);

		        Field SCARD_SCOPE_USER = pcsc.getDeclaredField("SCARD_SCOPE_USER");
		        SCARD_SCOPE_USER.setAccessible(true);

		        long newId = ((Long)SCardEstablishContext.invoke(pcsc, 
		              new Object[] { Integer.valueOf(SCARD_SCOPE_USER.getInt(pcsc)) }
		              )).longValue();
		        contextId.setLong(pcscterminal, newId);
		        
		        
		    }
			
//			TerminalFactory factory = TerminalFactory.getDefault();
//			List terminals = factory.terminals().list();
//			System.out.println("Terminals count: " + terminals.size());
//			System.out.println("Terminals: " + terminals);

			// Get the first terminal in the list
//			CardTerminal terminal = (CardTerminal) terminals.get(0);

			// Establish a connection with the card using
			// "T=0", "T=1", "T=CL" or "*"
//			Card card = terminal.connect("*");
//			System.out.println("Card: " + card);
			
			Card card = getCard("*");
			System.out.println("Card: " + card);

			// Get ATR
//			byte[] baATR = card.getATR().getBytes();
//			System.out.println("ATR: " + (baATR));

			// Select Card Manager
			// - Establish channel to exchange APDU
			// - Send SELECT Command APDU
			// - Show Response APDU
			CardChannel channel = card.getBasicChannel();

			// SELECT Command
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
			ResponseAPDU r = channel.transmit(new CommandAPDU(ThaiLandIdCard.getCommandAPDU()));
			
			// อ่านเลขบัตรประชาชน CID
			ResponseAPDU response_command_idcard = channel.transmit(new CommandAPDU(ThaiLandIdCard.getCommandIdCard()));
			byte byte_idcard[] = response_command_idcard.getData();

			CID = new String(byte_idcard, "TIS-620");

			// อ่านชื่อ - นามสกุล TH Fullname
			ResponseAPDU response_TH_Fullname = channel.transmit(new CommandAPDU(ThaiLandIdCard.getCommandThFullname()));
			byte byte_TH_Fullname[] = response_TH_Fullname.getData();

			TH_Fullname = new String(byte_TH_Fullname, "TIS-620");

			// อ่านชื่อ - นามสกุล EN Fullname
			ResponseAPDU response_EN_Fullname = channel.transmit(new CommandAPDU(ThaiLandIdCard.getCommandEnFullname()));
			byte byte_EN_Fullname[] = response_EN_Fullname.getData();

			EN_Fullname = new String(byte_EN_Fullname, "TIS-620");
			
			// อ่านวันเกิด Date of birth
			ResponseAPDU response_Date_of_birth = channel.transmit(new CommandAPDU(ThaiLandIdCard.getCommandDateOfDirth()));
			byte byte_Date_of_birth[] = response_Date_of_birth.getData();

			Date_of_birth = new String(byte_Date_of_birth, "TIS-620");
			
			// อ่านเพศ Gender
			ResponseAPDU response_Gender = channel.transmit(new CommandAPDU(ThaiLandIdCard.getCommandGender()));
			byte byte_Gender[] = response_Gender.getData();

			Gender = new String(byte_Gender, "TIS-620");
			
			// Card Issuer
			ResponseAPDU response_Card_Issuer = channel.transmit(new CommandAPDU(ThaiLandIdCard.getCommandCardIssuer()));
			byte byte_Card_Issuer[] = response_Card_Issuer.getData();

			Card_Issuer = new String(byte_Card_Issuer, "TIS-620");
			
			// Issue Date
			ResponseAPDU response_Issue_Date = channel.transmit(new CommandAPDU(ThaiLandIdCard.getCommandIssueDate()));
			byte byte_Issue_Date[] = response_Issue_Date.getData();

			Issue_Date = new String(byte_Issue_Date, "TIS-620");
			
			// Expire Date
			ResponseAPDU response_Expire_Date = channel.transmit(new CommandAPDU(ThaiLandIdCard.getCommandExpireDate()));
			byte byte_Expire_Date[] = response_Expire_Date.getData();

			Expire_Date = new String(byte_Expire_Date, "TIS-620");
			
			// Address
			ResponseAPDU response_Address = channel.transmit(new CommandAPDU(ThaiLandIdCard.getCommandAddress()));
			byte byte_Address[] = response_Address.getData();

			Address = new String(byte_Address, "TIS-620");
			
			PhotoPart = convertOutputPhoto(channel);

			// Disconnect
			// true: reset the card after disconnecting card.
			card.disconnect(true);

			smartCardReaderUtil.setCitizenid(CID);
			smartCardReaderUtil.setTH_Fullname(TH_Fullname);
			smartCardReaderUtil.setEN_Fullname(EN_Fullname);
			smartCardReaderUtil.setBirthday(Date_of_birth);
			smartCardReaderUtil.setSex(Gender);
			smartCardReaderUtil.setCard_Issuer(Card_Issuer);
			smartCardReaderUtil.setIssue(Issue_Date);
			smartCardReaderUtil.setExpire(Expire_Date);
			smartCardReaderUtil.setAddress(Address);
			smartCardReaderUtil.setPhotoPart(PhotoPart);
			result.put("success", smartCardReaderUtil);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			result.put("error", ex);
		}

		return result;
	}
	
	public static Card getCard(String target) throws Exception {
	    TerminalFactory factory = TerminalFactory.getDefault();
	    List<CardTerminal> terminals = factory.terminals().list();
	    for (CardTerminal t : terminals) {
	        if (t.getName().equals("ACS ACR1252 Dual Reader PICC 0")) {
	            Card card = t.connect(target);
	            return card;
	        }
	    }
	    throw new Exception();
	}
	
	public static String convertOutputPhoto(CardChannel channel) throws UnsupportedEncodingException, IOException, CardException
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
		
		String fileName = StringUtils.EMPTY;
		try {
			fileName = UploadFileUtil.uploadFileToServerTemp(ConstantUtils.PATH_LOCAL+ConstantUtils.PATH_IMAGE_TEMP_CUSTOMER, bytes);
		} catch (ImageProcessingException e) {
			e.printStackTrace();
		}
		fileName = ConstantUtils.PATH_IMAGE_TEMP_CUSTOMER+fileName;
		return FilePathUtil.pathAvatarOnWebCustomer(fileName);
	}
	
}
