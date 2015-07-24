/**
 * 
 */
package com.hehenian.common.utils;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**  
 * @Project: hehenian-barcode
 * @Package com.hehenian.barcode.generator.tdcode
 * @Title: TowDimensionGenerator
 * @Description: TODO
 * @author: chenzhpmf
 * @date 2015年5月14日
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
public class TowDimensionCodeUtil {
	
	private static final String IMAGE_SAVE_PATH = "image.save.path";

	private static final String CHARSET = "UTF-8";

	private String imageSavePath;

	private String imageFormat;

	private int imageWidth;

	private int imageHeight;


	private ErrorCorrectionLevel errorCorrectionLevel = ErrorCorrectionLevel.L;
	
	public TowDimensionCodeUtil() {
		Properties properties = PropertiesUtils.loadSystemProperty("catalina.home", "hehenian.properties");
		String imageSavePath = (String) properties.get(IMAGE_SAVE_PATH);
		this.setImageSavePath(imageSavePath);
	}

	/**
	 * @Title: creator
	 * @param: content[]
	 * @param: imageName[]
	 * @Description: 根据指定的content生成2D图片
	 * @Return: void
	 */
	public void creator(String[] content, String[] imageName) {
		if (content.length != imageName.length) {
			throw new RuntimeException("请为每个要生成二维码的内容指定图片的名称");
		}
		for (int i = 0; i < content.length; i++) {
			creator(content[i], imageName[i]);
		}
	}

	public void setImageSavePath(String imageSavePath) {
		this.imageSavePath = imageSavePath;
		File file = new File(imageSavePath);
		if (!file.exists()) {
			file.mkdir();
		}
	}
	
	/**
	 * @return the imageSavePath
	 */
	public String getImageSavePath() {
		return imageSavePath;
	}

	public void setImageFormat(String imageFormat) {
		this.imageFormat = imageFormat;
	}

	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

	public void setErrorCorrectionLevel(ErrorCorrectionLevel errorCorrectionLevel) {
		this.errorCorrectionLevel = errorCorrectionLevel;
	}

	/* (non-Javadoc)
	 * @see com.hehenian.barcode.generator.ImageGenerator#creator(java.lang.String, java.lang.String)
	 */
	public String creator(String content, String imageName) {
		String saveImagePath = "";
		try {
			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
			hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
			hints.put(EncodeHintType.ERROR_CORRECTION, errorCorrectionLevel);
			BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, imageWidth, imageHeight, hints);
			File file = new File(getImageSavePath(), new StringBuffer().append(imageName).append(".").append(imageFormat).toString());
			MatrixToImageWriter.writeToFile(bitMatrix, imageFormat, file);
			saveImagePath = file.getPath();
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return saveImagePath;
	}
}
