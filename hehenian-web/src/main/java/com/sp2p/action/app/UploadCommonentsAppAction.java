package com.sp2p.action.app;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.vo.FileCommon;
import com.shove.vo.Files;
import com.shove.web.util.FileUtils;
import com.shove.web.util.JSONUtils;
import com.shove.web.util.ServletUtils;
import com.shove.web.util.UploadUtil;



public class UploadCommonentsAppAction extends BaseAppAction{
	
	private static final long serialVersionUID = 8283104028697725089L;
	private static Log log = LogFactory.getLog(UploadCommonentsAppAction.class);
	private FileCommon fileCommon = new FileCommon(); // 开发人员设置需要设置的参数(对象)
	private Files files = new Files(); // 文件参数(对象)
	private String filePath;
	
	//文件上传，指定目录
	public static final String UPLOAD="upload/";
	
	//文件上传，临时目录
	public static final String TEMP="temp/";
	
	/**
	 * 上传调用页面将JSON数据传入设置到fileCommon对象供上传组件页面使用
	 * 普通上传  返回smallcomponent
	 * @return
	 * @throws Exception
	 */
	public String onloadFileUpload() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();

		try {
			Map<String, String> appInfoMap = getAppInfoMap();
		//自定义回调函数和数据
			if(appInfoMap.get("cfn") == null){
				fileCommon.setCallBackFunctionName("undefined");
				jsonMap.put("callBackFunctionName", "undefined");
			}else{
				fileCommon.setCallBackFunctionName(appInfoMap.get("cfn"));
				jsonMap.put("callBackFunctionName", appInfoMap.get("cfn"));
			}
			if(appInfoMap.get("cp") == null){
				fileCommon.setCallBackParamsString("undefined");
				jsonMap.put("callBackParamsString", "undefined");
			}else{
				fileCommon.setCallBackParamsString(appInfoMap.get("cp"));
				jsonMap.put("callBackParamsString", appInfoMap.get("cp"));
			}
			//用户输入的目录名
			String inputDirName = appInfoMap.get("inputDirName");
//			request().setAttribute("inputDirName", inputDirName);
			jsonMap.put("inputDirName", inputDirName);
			log.info("用户输入的目录名"+inputDirName);
			//是否需要文件描述
			String fileRemark = appInfoMap.get("fileRemark");
			if(org.apache.commons.lang.StringUtils.isNotBlank(fileRemark)){
//				request().setAttribute("fileRemark", fileRemark);
				jsonMap.put("fileRemark", fileRemark);
			}
			fileCommon.setFileRemark(fileRemark);//文件描述
			fileCommon.setNormal(true); //默认普通上传  
			jsonMap.put("normal", true);
			
			String isDir = appInfoMap.get("isDir");	
			//目录上传
			fileCommon.setDir(false);
			if (StringUtils.isNotBlank(isDir) && isDir.equalsIgnoreCase("true")) {
				fileCommon.setDir(true);
				jsonMap.put("dir", true);
			}
			fileCommon.setFileName(appInfoMap.get("fileName"));
			fileCommon.setFileType(appInfoMap.get("fileType"));
			fileCommon.setNotAllowFileType(appInfoMap.get("nFileType"));
			jsonMap.put("normal", appInfoMap.get("fileType"));
			jsonMap.put("normal", appInfoMap.get("nFileType"));
			
			String fileLimitSizeStr = appInfoMap.get("fileLimitSize");
			if(fileLimitSizeStr!=null){
				fileLimitSizeStr = fileLimitSizeStr.toUpperCase();
				String fileLimitSizeStrWithOutUnit = trimChart(fileLimitSizeStr);
				double fileLimitSize = Double.parseDouble(fileLimitSizeStrWithOutUnit);
				if(fileLimitSize <= 0){
					jsonMap.put("error", "-1");
					jsonMap.put("msg", "上传文件尺寸不能为负数或零！");
					JSONUtils.printObject(jsonMap);
					return null;
				}
				if(fileLimitSizeStr.endsWith("K") || fileLimitSizeStr.endsWith("KB")){
					fileCommon.setSizeUnit("K");
					jsonMap.put("sizeUnit", "K");
				}else if(fileLimitSizeStr.endsWith("M") || fileLimitSizeStr.endsWith("MB")){
					fileCommon.setSizeUnit("M");
					jsonMap.put("sizeUnit", "M");
				}else{
					fileCommon.setSizeUnit("M");
					jsonMap.put("sizeUnit", "M");
				}
				fileCommon.setFileLimitSize(fileLimitSizeStrWithOutUnit);
				
			}else{
				fileCommon.setSizeUnit("M");
				fileCommon.setFileLimitSize("2");
				jsonMap.put("sizeUnit", "M");
				jsonMap.put("fileLimitSize", "2");
			}
			
			if(fileCommon.getSizeUnit().equalsIgnoreCase("M")){
				double fileLimitSize = Double.parseDouble(fileCommon.getFileLimitSize());
				if(fileLimitSize < 1){
					fileCommon.setSizeUnit("K");
					fileCommon.setFileLimitSize("" + new Double(fileCommon.getFileLimitSize())*1024);
					jsonMap.put("sizeUnit", "K");
					jsonMap.put("fileLimitSize", new Double(fileCommon.getFileLimitSize())*1024);
				}
			}
			
			fileCommon.setTitle("上传");
			jsonMap.put("title", "上传");
			String title = appInfoMap.get("title");
			if(title!=null){
				title = new String(title.getBytes("ISO-8859-1"),"UTF-8");
				fileCommon.setTitle(title);
				jsonMap.put("title", title);
			}
		    //上传目录的子路径  moduel user dirname
			String filePath = appInfoMap.get("fileSource");
			fileCommon.setFileSource(filePath);
			jsonMap.put("filePath", filePath);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			JSONUtils.printObject(jsonMap);
	} catch (Exception e) {
		e.printStackTrace();
		jsonMap.put("error", "2");
		jsonMap.put("msg", "未知异常");
		JSONUtils.printObject(jsonMap);
		log.error(e);
	}
	return null;
}
	/**
	 * 根据对象条件设置将文件传到指定的服务器目录下
	 * @return
	 * @throws Exception
	 */
	public String uploadFiles() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();

		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			
			String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date()); 
			String realPath = ServletUtils.serverRootDirectory()+ UPLOAD + "user"+"/"+ dateStr+ "/";
			FileUtils.mkdirs(realPath);
			getStream(realPath);
			files.setFilesFileName(appInfoMap.get("fileName"));
			String errorMng = UploadUtil.uploadByFileapp(files,realPath);
			if (StringUtils.isNotBlank(errorMng)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", errorMng);
				JSONUtils.printObject(jsonMap);
				return null;
			}
			
			//是图片获取图片尺寸
			if ("image/jpeg".equalsIgnoreCase(files.getFilesContentType())
					|| "image/jpg".equalsIgnoreCase(files.getFilesContentType())
					|| "image/pjpeg".equalsIgnoreCase(files.getFilesContentType())) {
	
				BufferedImage getImage = ImageIO.read(files.getFiles());
				jsonMap.put("width", getImage.getWidth());
				jsonMap.put("height", getImage.getHeight());
			}
			String fileName = realPath.substring(realPath.indexOf("upload"),realPath.length())+files.getFilesFileName();
			jsonMap.put("fileName",fileName);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
		}
		return null;
	}
	
	/**
	 * 移动文件
	 * @param uploadFile
	 */
	public static void removeFile(String uploadFile){
		String oldPath = ServletUtils.serverRootDirectory()+TEMP+uploadFile;
		String newFilePath=ServletUtils.serverRootDirectory()+ UPLOAD+uploadFile;
		String dirPath = newFilePath.substring(0,newFilePath.lastIndexOf("/")+1);
		File oldFile = new File(oldPath); 
		File mkdir = new File(dirPath); 
		//判断文件夹是否存在 
		if(!mkdir.exists()){
			mkdir.mkdirs(); 
		}
		//将文件移到新文件里 
		File newFile = new File(dirPath +oldFile.getName()); 
		oldFile.renameTo(newFile); 
	}
	
	/**
	 * 删除文件
	 * @param uploadFile
	 */
	public static void deleteFile(String uploadFile){
		String newFilePath = ServletUtils.serverRootDirectory()+ UPLOAD+uploadFile;
		File newFile = new File(newFilePath); 
		if(newFile.exists()){
			newFile.delete();
		}
	}
	
	public Files getFiles() {
		return files;
	}

	public void setFiles(Files files) {
		this.files = files;
	}

	public FileCommon getModel() {
		return fileCommon;
	}

	public FileCommon getFileCommon() {
		return fileCommon;
	}

	public void setFileCommon(FileCommon fileCommon) {
		this.fileCommon = fileCommon;
	}
	
	private String trimChart(String str){
		return str.replaceAll("[a-zA-Z]", "");
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
