package com.hdw.mccable.Manager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.pdfbox.multipdf.PDFMergerUtility;

public class PdfMergeUtils {
	
	public static ByteArrayInputStream merge(InputStream ...is) throws Exception
	{
		ByteArrayOutputStream bos = null;
		
		try 
		{
			PDFMergerUtility mg = new PDFMergerUtility();
			
			for(InputStream o:is)
			{
				mg.addSource(o);
			}
			
			bos = new ByteArrayOutputStream();
			mg.setDestinationStream(bos);
	    	mg.mergeDocuments();
	    	
			return new ByteArrayInputStream(bos.toByteArray());
		} finally {
			if(null != bos) bos.close();
		}
	}
	
	public static ByteArrayInputStream merge(List<InputStream> is) throws Exception{
		ByteArrayOutputStream bos = null;
		try {
			PDFMergerUtility mg = new PDFMergerUtility();
			for(InputStream o:is){
				mg.addSource(o);
			}
			
			bos = new ByteArrayOutputStream();
			mg.setDestinationStream(bos);
	    	mg.mergeDocuments();
	    	
			return new ByteArrayInputStream(bos.toByteArray());
		} finally {
			if(null != bos) bos.close();
		}
	}
	
	public static byte[] mergeToByteArray(List<InputStream> is) throws Exception{
		ByteArrayOutputStream bos = null;
		try {
			PDFMergerUtility mg = new PDFMergerUtility();
			for(InputStream o:is){
				mg.addSource(o);
			}
			
			bos = new ByteArrayOutputStream();
			mg.setDestinationStream(bos);
	    	mg.mergeDocuments();
	    	
			return bos.toByteArray();
		} finally {
			if(null != bos) bos.close();
		}
	}
	
	public static byte[] mergeToByteArrayV2(List<ByteArrayInputStream> is) throws Exception{
		ByteArrayOutputStream bos = null;
		try {
			PDFMergerUtility mg = new PDFMergerUtility();
			for(InputStream o:is){
				mg.addSource(o);
			}
			
			bos = new ByteArrayOutputStream();
			mg.setDestinationStream(bos);
	    	mg.mergeDocuments();
	    	
			return bos.toByteArray();
		} finally {
			if(null != bos) bos.close();
		}
	}
	
}
