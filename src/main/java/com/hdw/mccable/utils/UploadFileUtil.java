package com.hdw.mccable.utils;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.jpeg.JpegDirectory;

public class UploadFileUtil {

	public static String uploadFileToServer(String pathServer, MultipartFile file_temp) throws IOException, ImageProcessingException {
		
		String name = file_temp.getOriginalFilename();
		String[] fileName = name.split("\\.");
		String flieType = fileName[fileName.length - 1];
		
		Date date = new Date();
		String pathFile = date.getTime()+"."+flieType;
		if("PNG".equalsIgnoreCase(flieType)){
			pathFile = uploadFileToServerPNG(pathServer, file_temp);
		}else if("PDF".equalsIgnoreCase(flieType)){
			pathFile = uploadFileToServerPNG(pathServer, file_temp);
		}else if("JPG".equalsIgnoreCase(flieType)){
			pathFile = uploadFileToServerJPG(pathServer, file_temp);
		}
		return pathFile;
	}
	
	public static String uploadFileToServerTemp(String pathServer, byte[] bytes) throws IOException, ImageProcessingException {
		SimpleDateFormat formatDataTh_yyyy_mm_dd_HHmmss = new SimpleDateFormat("yyyyMMdd_HHmmss", new Locale("TH", "th"));
		
		Date date = new Date();
		String pathFile = formatDataTh_yyyy_mm_dd_HHmmss.format(date)+".jpg";
		
		// เช็คถ้ามี directory ให้ลบออกก่อน
		File file = new File(pathServer);
		if (file.exists()) {
			FileUtils.deleteDirectory(file);
			file.mkdirs();
		}else{
			file.mkdirs();
		}
		
		Path path = Paths.get(pathServer+pathFile);
		Files.createFile(path);

		Files.write(path, bytes , StandardOpenOption.TRUNCATE_EXISTING);

		return pathFile;
	}
	
	public static String uploadFileToServerPDF(String pathServer, byte[] bytes) throws IOException, ImageProcessingException {
		SimpleDateFormat formatDataTh_yyyy_mm_dd_HHmmss = new SimpleDateFormat("yyyyMMdd_HHmmss", new Locale("TH", "th"));
		
		Date date = new Date();
		String pathFile = formatDataTh_yyyy_mm_dd_HHmmss.format(date)+".pdf";
		
		System.out.println("pathServer : "+pathServer);
		
		// เช็คถ้ามี directory ให้ลบออกก่อน
		File file = new File(pathServer);
		if (file.exists()) {
			FileUtils.deleteDirectory(file);
			file.mkdirs();
		}else{
			file.mkdirs();
		}
		
		Path path = Paths.get(pathServer+pathFile);
		Files.createFile(path);

		Files.write(path, bytes , StandardOpenOption.TRUNCATE_EXISTING);

		return pathFile;
	}
	
	public static String uploadFileToServerJPG(String pathServer, MultipartFile file_temp) throws IOException, ImageProcessingException {
		File file = new File(pathServer);
		if (!file.exists()) {
			file.mkdirs();
		}
				
		BufferedImage img = null;
        
        File newFileJPG = convert(file_temp);
        
        
        
		String name = file_temp.getOriginalFilename();
		String[] fileName = name.split("\\.");
		String flieType = fileName[fileName.length - 1];
		
		Date date = new Date();
		String pathFile = date.getTime()+"."+flieType;
//		Path path = Paths.get(pathServer + pathFile);
//		Path path = Paths.get(pathFile);

		
        
        img = ImageIO.read(newFileJPG);

        
        Metadata metadata = ImageMetadataReader.readMetadata(newFileJPG);
        ExifIFD0Directory exifIFD0Directory = metadata.getDirectory(ExifIFD0Directory.class);
        JpegDirectory jpegDirectory = (JpegDirectory) metadata.getDirectory(JpegDirectory.class);

        int orientation = 1;
        try {
            orientation = exifIFD0Directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
        } catch (Exception ex) {
        }

        int width = 0;
		try {
			width = jpegDirectory.getImageWidth();
		} catch (MetadataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        int height = 0;
		try {
			height = jpegDirectory.getImageHeight();
		} catch (MetadataException e) {
			e.printStackTrace();
		}

        AffineTransform affineTransform = new AffineTransform();

        switch (orientation) {
        case 1:
            break;
        case 2: // Flip X
            affineTransform.scale(-1.0, 1.0);
            affineTransform.translate(-width, 0);
            break;
        case 3: // PI rotation
            affineTransform.translate(width, height);
            affineTransform.rotate(Math.PI);
            break;
        case 4: // Flip Y
            affineTransform.scale(1.0, -1.0);
            affineTransform.translate(0, -height);
            break;
        case 5: // - PI/2 and Flip X
            affineTransform.rotate(-Math.PI / 2);
            affineTransform.scale(-1.0, 1.0);
            break;
        case 6: // -PI/2 and -width
            affineTransform.translate(height, 0);
            affineTransform.rotate(Math.PI / 2);
            break;
        case 7: // PI/2 and Flip
            affineTransform.scale(-1.0, 1.0);
            affineTransform.translate(-height, 0);
            affineTransform.translate(0, width);
            affineTransform.rotate(3 * Math.PI / 2);
            break;
        case 8: // PI / 2
            affineTransform.translate(0, width);
            affineTransform.rotate(3 * Math.PI / 2);
            break;
        default:
            break;
        } 

		if(orientation == 6 || orientation == 8){                	        
	        // Image full
			AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BILINEAR);  
	        BufferedImage destinationImage = new BufferedImage(img.getHeight(), img.getWidth(), img.getType());
	        destinationImage = affineTransformOp.filter(img, destinationImage);
	        ImageIO.write(destinationImage, "jpg", new File(pathServer+pathFile));
	        
		}else{
			 // Image full
			AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BILINEAR);  
	        BufferedImage destinationImage = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
	        destinationImage = affineTransformOp.filter(img, destinationImage);
	        ImageIO.write(destinationImage, "jpg", new File(pathServer+pathFile));
	        
		}

		return pathFile;

	}
	
	public static File convert(MultipartFile file) throws IOException {
	    File convFile = new File(file.getOriginalFilename());
	    convFile.createNewFile();
	    FileOutputStream fos = new FileOutputStream(convFile);
	    fos.write(file.getBytes());
	    fos.close();
	    return convFile;
	}
	
	public static File convertByteToFile(byte[] bytes, String fileName) throws IOException {
		File convFile = new File(fileName);
	    FileOutputStream fos = new FileOutputStream(convFile);
	    fos.write(bytes);
	    fos.close();
	    return convFile;
	}
	
	public static BufferedImage resizeImage(BufferedImage img, int targetWidth, int targetHeight) {

	    int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
	    BufferedImage ret = img;
	    BufferedImage scratchImage = null;
	    Graphics2D g2 = null;

	    int w = img.getWidth();
	    int h = img.getHeight();

	    int prevW = w;
	    int prevH = h;

	    do {
	        if (w > targetWidth) {
	            w /= 2;
	            w = (w < targetWidth) ? targetWidth : w;
	        }

	        if (h > targetHeight) {
	            h /= 2;
	            h = (h < targetHeight) ? targetHeight : h;
	        }

	        if (scratchImage == null) {
	            scratchImage = new BufferedImage(w, h, type);
	            g2 = scratchImage.createGraphics();
	        }

	        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
	                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	        g2.drawImage(ret, 0, 0, w, h, 0, 0, prevW, prevH, null);

	        prevW = w;
	        prevH = h;
	        ret = scratchImage;
	    } while (w != targetWidth || h != targetHeight);

	    if (g2 != null) {
	        g2.dispose();
	    }

	    if (targetWidth != ret.getWidth() || targetHeight != ret.getHeight()) {
	        scratchImage = new BufferedImage(targetWidth, targetHeight, type);
	        g2 = scratchImage.createGraphics();
	        g2.drawImage(ret, 0, 0, null);
	        g2.dispose();
	        ret = scratchImage;
	    }

	    return ret;

	}
	
	
	public static String uploadFileToServerPNG(String pathServer, MultipartFile file_temp) throws IOException {
		File file = new File(pathServer);
		System.out.println("uploadFileToServerPNG");
		System.out.println(pathServer);
		System.out.println(file);
		System.out.println(!file.exists());
		if (!file.exists()) {
			if(file.mkdirs()){
				System.out.println("Multiple directories are created!");
			}else{
				System.out.println("Failed to create multiple directories!");
			}
		}
		
		
//		Path path1 = Paths.get(pathServer);
//		System.out.println(!Files.exists(path1));
//		if (!Files.exists(path1)) {
//            try {
//                Files.createDirectories(path1);
//            } catch (IOException e) {
//                //fail to create directory
//                e.printStackTrace();
//            }
//        }

		byte[] bytes = file_temp.getBytes();
		String name = file_temp.getOriginalFilename();
		String[] fileName = name.split("\\.");
		String flieType = fileName[fileName.length - 1];
		
		Date date = new Date();
		String pathFile = date.getTime()+"."+flieType;
		Path path = Paths.get(pathServer + pathFile);

		Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
	      //add owners permission
	      perms.add(PosixFilePermission.OWNER_READ);
	      perms.add(PosixFilePermission.OWNER_WRITE);
	      perms.add(PosixFilePermission.OWNER_EXECUTE);
	      //add group permissions
	      perms.add(PosixFilePermission.GROUP_READ);
	      perms.add(PosixFilePermission.GROUP_WRITE);
	      perms.add(PosixFilePermission.GROUP_EXECUTE);
	      //add others permissions
	      perms.add(PosixFilePermission.OTHERS_READ);
	      perms.add(PosixFilePermission.OTHERS_WRITE);
	      perms.add(PosixFilePermission.OTHERS_EXECUTE);
	      
		Files.createFile(path, PosixFilePermissions.asFileAttribute(perms));
		
//		Files.createFile(path);

		Files.write(path, bytes , StandardOpenOption.TRUNCATE_EXISTING);
		

		return pathFile;

	}
	
	public static String uploadFileToServer(String pathServer, byte[] bytes) throws IOException {
		File file = new File(pathServer);
		if (!file.exists()) {
			file.mkdirs();
		}
		
		Date date = new Date();
		String pathFile = date.getTime()+".jpg";
		Path path = Paths.get(pathServer + pathFile);

		Files.createFile(path);

		Files.write(path, bytes , StandardOpenOption.TRUNCATE_EXISTING);
		
		return pathFile;

	}
	
}
