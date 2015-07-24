package com.shove.web.action;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.vo.FileCommon;
import com.shove.vo.Files;
import com.shove.web.util.FileUtils;
import com.shove.web.util.JSONUtils;
import com.shove.web.util.ServletUtils;
import com.shove.web.util.UploadUtil;



public class UploadCommonentsAction extends BaseAction{
	
	private static final long serialVersionUID = 8283104028697725089L;
	private static Log log = LogFactory.getLog(UploadCommonentsAction.class);
	private FileCommon fileCommon = new FileCommon(); // 开发人员设置需要设置的参数(对象)
	private Files files; // 文件参数(对象)
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
//		Admin admin = (Admin) session().getAttribute(IConstants.ADMIN_SESSION);
//     	Teacher teacher = (Teacher) session().getAttribute(IConstants.TEACHER_SESSION);
//     	Parent parent = (Parent) session().getAttribute(IConstants.PARENT_SESSION);
//     	Student student = (Student) session().getAttribute(IConstants.STUDENT_SESSION);
//     	if(admin == null && teacher == null && parent == null && student == null){
//     		request().setAttribute("errorMng", "权限不够");
//     		return INPUT;
//     	}
		String objStr = request().getParameter("obj");
		String jsonStr = URLDecoder.decode(objStr, "utf-8");
		JSONObject json = JSONObject.fromObject(jsonStr);
		//自定义回调函数和数据
		if(JSONUtils.getString(json,"cfn") == null){
			fileCommon.setCallBackFunctionName("undefined");
		}else{
			fileCommon.setCallBackFunctionName(JSONUtils.getString(json,"cfn"));
		}
		if(JSONUtils.getString(json,"cp") == null){
			fileCommon.setCallBackParamsString("undefined");
		}else{
			fileCommon.setCallBackParamsString(JSONUtils.getString(json,"cp"));
		}
		//用户输入的目录名
		String inputDirName = JSONUtils.getString(json,"inputDirName");
		request().setAttribute("inputDirName", inputDirName);
		log.info("用户输入的目录名"+inputDirName);
		//是否需要文件描述
		String fileRemark = JSONUtils.getString(json,"fileRemark");
		if(org.apache.commons.lang.StringUtils.isNotBlank(fileRemark)){
			request().setAttribute("fileRemark", fileRemark);
		}
		fileCommon.setFileRemark(fileRemark);//文件描述
		fileCommon.setNormal(true); //默认普通上传  
		
		String isDir = JSONUtils.getString(json,"isDir");	
		//目录上传
		fileCommon.setDir(false);
		if (StringUtils.isNotBlank(isDir) && isDir.equalsIgnoreCase("true")) {
			fileCommon.setDir(true);
		}
		fileCommon.setFileName(JSONUtils.getString(json,"fileName"));
		fileCommon.setFileType(JSONUtils.getString(json,"fileType"));
		fileCommon.setNotAllowFileType(JSONUtils.getString(json,"nFileType"));
		
		String fileLimitSizeStr = JSONUtils.getString(json,"fileLimitSize");
		if(fileLimitSizeStr!=null){
			fileLimitSizeStr = fileLimitSizeStr.toUpperCase();
			String fileLimitSizeStrWithOutUnit = trimChart(fileLimitSizeStr);
			double fileLimitSize = Double.parseDouble(fileLimitSizeStrWithOutUnit);
			if(fileLimitSize <= 0){
				throw new Exception("上传文件尺寸不能为负数或零！");
			}
			if(fileLimitSizeStr.endsWith("K") || fileLimitSizeStr.endsWith("KB")){
				fileCommon.setSizeUnit("K");
			}else if(fileLimitSizeStr.endsWith("M") || fileLimitSizeStr.endsWith("MB")){
				fileCommon.setSizeUnit("M");
			}else{
				fileCommon.setSizeUnit("M");
			}
			fileCommon.setFileLimitSize(fileLimitSizeStrWithOutUnit);
			
		}else{
			fileCommon.setSizeUnit("M");
			fileCommon.setFileLimitSize("2");
		}
		
		if(fileCommon.getSizeUnit().equalsIgnoreCase("M")){
			double fileLimitSize = Double.parseDouble(fileCommon.getFileLimitSize());
			if(fileLimitSize < 1){
				fileCommon.setSizeUnit("K");
				fileCommon.setFileLimitSize("" + new Double(fileCommon.getFileLimitSize())*1024);
			}
		}
		
		fileCommon.setTitle("上传");
		String title = JSONUtils.getString(json,"title");
		title = new String(title.getBytes("ISO-8859-1"),"UTF-8");
		if(title!=null){
			fileCommon.setTitle(title);
		}
	    //上传目录的子路径  moduel user dirname
		String filePath = JSONUtils.getString(json,"fileSource");
		fileCommon.setFileSource(filePath);
		return "smallcomponent";//是普通上传组件
	}
	
	/**
	 * 根据对象条件设置将文件传到指定的服务器目录下
	 * @return
	 * @throws Exception
	 */
	public String uploadFiles() throws Exception {
		HttpServletRequest request = request();
//		Admin admin = (Admin) session().getAttribute(IConstants.ADMIN_SESSION);
//     	Teacher teacher = (Teacher) session().getAttribute(IConstants.TEACHER_SESSION);
//     	Parent parent = (Parent) session().getAttribute(IConstants.PARENT_SESSION);
//     	Student student = (Student) session().getAttribute(IConstants.STUDENT_SESSION);
//     	if(admin == null && teacher == null && parent == null && student == null){
//     		request().setAttribute("errorMng", "权限不够");
//     		return INPUT;
//     	}
		String realPath = ServletUtils.serverRootDirectory()+ UPLOAD + fileCommon.getFileSource() + "/";
		FileUtils.mkdirs(realPath);
		request.setAttribute("mark", "success");
		String errorMng = UploadUtil.getByAllParams(files, fileCommon ,realPath);
		
		
		if (StringUtils.isNotBlank(errorMng)) {
			request.setAttribute("errorMng", errorMng);
			return INPUT;
		}
		
		//是图片获取图片尺寸
		if ("image/jpeg".equalsIgnoreCase(files.getFilesContentType())
				|| "image/jpg".equalsIgnoreCase(files.getFilesContentType())
				|| "image/pjpeg".equalsIgnoreCase(files.getFilesContentType())) {

			BufferedImage getImage = ImageIO.read(files.getFiles());
			request.setAttribute("width", getImage.getWidth());
			request.setAttribute("height", getImage.getHeight());
		}
		return SUCCESS;
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
