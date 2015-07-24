package com.shove.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.vo.FileCommon;
import com.shove.vo.Files;
import com.shove.web.util.VerifyTruePicture;

public class UploadUtil {
	
	public static Log log = LogFactory.getLog(UploadUtil.class);
	
	/**
	 * 上传文件公共方法
	 * @param file  文件
	 * @param source 路径
	 * @param fileName 文件名
	 * @throws Exception 
	 */
	public static void uploadByFile(File file,String source,String fileName) throws Exception{
		FileInputStream fis = null;
		FileOutputStream fos = null;

		try {
			if(file.length()==0){
				File _file = new File(source + fileName);
				_file.createNewFile();
				return ;
			}
			fis = new FileInputStream(file);
			fos = new FileOutputStream(source + "/" + fileName);
            
			//读取字节流
			byte[] bt = new byte[1024];
			int real = fis.read(bt);
			while (real > 0) {
				fos.write(bt, 0, real);
				real = fis.read(bt);
			}
		} finally {
			fos.flush();
			fos.close();
			fis.close();
		}
	}
	
	/**
	 * @param file开发人员设置需要设置的参数(对象)
	 * @param fileCommon 文件参数(对象)
	 * @return Map
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static String getByAllParams(Files file, FileCommon fileCommon,String realpath) throws Exception {
		  //取真实文件名
		String fileName = file.getFilesFileName();
		String getExt = findFileNameExt(fileName);
		  
		//若指定了文件名，以指定的文件名，否则以原文件名。
//		if (!StringUtils.isBlank(fileCommon.getFileName())) {
//			fileName = fileCommon.getFileName() + "." + getExt;
//		}
		File f = file.getFiles();
		if (f.exists()) {
			//String filetype1 = VerifyTruePicture.getImageFileType(f);
			//System.out.println(filetype1);
			String filetype2 = VerifyTruePicture.getFileByFile(f);
			//System.out.println(filetype2);
			if(filetype2==null)
			{
				return "上传文件类型错误!";				
			}
		}
		else		
		{
			return "请选择上传文件!";
		}
		fileName = FileUtils.getFileName() + "." + getExt;
		//传回前台页面使用
		fileCommon.setFileName(fileName);
		if(StringUtils.isBlank(fileCommon.getFileSource())){
			return "请选择上传文件!";
		}
		
		/*//若指定了类型，则进行检查  
		if (StringUtils.isNotBlank(fileCommon.getFileType())) {
			if (!fileCommon.getFileType().toUpperCase().contains(getExt.toUpperCase())) {
				return "文件类型不对!";
			}
		}
		
		//若禁止类型，则进行检查
		String notAllowFileType = fileCommon.getNotAllowFileType();
		if (StringUtils.isNotBlank(notAllowFileType)) {
			if (notAllowFileType.toUpperCase().contains(getExt.toUpperCase())) {
				return "禁止上传"+notAllowFileType+"类型的文件!";
			}
		}*/
		
		//stone modify by 2013-11-13
		if (!"GIF,JPG,JPEG,PNG,BMP".contains(getExt.toUpperCase())) {
			return "文件类型不对!";
		}

		
		//若指定了大小限制，则进行检查
		double fileLimitSize = Double.parseDouble(fileCommon.getFileLimitSize());
		if(fileLimitSize > 0){
			long getFileSize = 0L;
			if(fileCommon.getSizeUnit().equalsIgnoreCase("M")){
				getFileSize = new Double(fileLimitSize * 1024 * 1024).longValue();
			}
			if(fileCommon.getSizeUnit().equalsIgnoreCase("K")){
				getFileSize = new Double(fileLimitSize * 1024).longValue() ;
			}
			if (file.getFiles().length() > getFileSize) {
				return "文件超过上传限制!";
			}
		}
		
		//若已存在同名文件，就先删除
		deleteFile(realpath,fileName);
		
		try {
			//上传文件
			UploadUtil.uploadByFile(file.getFiles(), realpath ,fileName);
		} catch (Exception e) {
			log.error(e);
			return "上传路径不存在!";
		}
		return null;
	}
	
	/**
	 * 若有重复文件，删除已存在的文件
	 * @param source
	 * @param fileName
	 */
	public  static  void deleteFile(String parent,String fileName){
		parent=parent.replace("/", File.separator);
		File f=new File(parent,fileName);
		if(f!=null && f.exists() && f.isFile()){
			f.delete();
		}
	}
	
	/**
	 * 获取文件名后缀
	 * 李红志 Nov 25, 2010
	 * @param fileName
	 * @return
	 */
	public static String findFileNameExt(String fileName) {
		if(fileName==null || "".equals(fileName)){
			return null;
		}
		return fileName.substring(fileName.lastIndexOf(".")+1);
	}

	/**
	 * 删除文件
	 * @param th 文件路径
	 * @param fileName 文件名
	 * @throws Exception 
	 */
	public static void removeFile(String parent, String fileName){
		log.info(parent+fileName);
		File file = new File(parent, fileName);
		if (file!=null&&file.exists()&&file.isFile()) {
			file.delete();
		}
	}
}
