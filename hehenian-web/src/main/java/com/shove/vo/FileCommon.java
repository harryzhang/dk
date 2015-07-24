package com.shove.vo;
 
/**
 * 调用上传文件的页面用此类设置上传的相关参数传送到上传组件。
 */
public class FileCommon {
	/**参数(是普通上传组件还是断点续传组件) true=普通上传*/
	private boolean isNormal = true; 
	
	/**判断断点上传是否为目录上传 默认为文件上传*/
	private boolean isDir = false;
	
	/** 文件名  不指定采用原文件名 */
	private String fileName;
	
	/** 上传文件类型 只要字串中含有就允许,不指定就不检查。*/
	private String fileType;   
	
	/** 禁止文件类型 只要字串中含有就允许,不指定就不检查。*/
	private String notAllowFileType;
	
	/** 文件远程路径 相对于上传根目录UPLOAD/    格式：模块名/用户ID/时间目录  */
	private String fileSource;  
	
	/** 文件描述 */
	private String fileRemark;  
	
	/** 文件限定大小 */
	private String fileLimitSize;
	
	/** 文件限定大小单位 */
	private String sizeUnit;
	
	/** 文件上传数量 */
	private Integer fileCount;  
	
	/** 上传窗口title */
	private String title;    
	
	/**自定义回调函数, 上传成功后,会调用默认的回调函数,也可以指定自己的回调函数
	简写:cfn*/
    private String callBackFunctionName;
  
    /**自定义数据,传给上传组件,然后作为回调函数参数回传
    简写:cp*/
    private String callBackParamsString;
	
	/** 文件远程路径 相对于临时目录    格式：模块名/用户ID/时间目录  */
	public String getFileSource() {
		return fileSource;
	}
	
	/** 文件远程路径 相对于上传根目录UPLOAD/    
	 * 格式：模块名/用户ID/时间目录  */
	public void setFileSource(String fileSource) {
		this.fileSource = fileSource;
	}
	
	public Integer getFileCount() {
		return fileCount;
	}
	
	public void setFileCount(Integer fileCount) {
		this.fileCount = fileCount;
	}
	
	public String getFileRemark() {
		return fileRemark;
	}
	
	public void setFileRemark(String fileRemark) {
		this.fileRemark = fileRemark;
	}
	
	public boolean isNormal() {
		return isNormal;
	}
	
	public void setNormal(boolean isNormal) {
		this.isNormal = isNormal;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getSizeUnit() {
		return sizeUnit;
	}
	
	public void setSizeUnit(String sizeUnit) {
		this.sizeUnit = sizeUnit;
	}
	
	public String getNotAllowFileType() {
		return notAllowFileType;
	}
	
	public void setNotAllowFileType(String notAllowFileType) {
		this.notAllowFileType = notAllowFileType;
	}
	
	public boolean isDir() {
		return isDir;
	}
	
	public void setDir(boolean isDir) {
		this.isDir = isDir;
	}
	
	public String getFileLimitSize() {
		return fileLimitSize;
	}
	
	public void setFileLimitSize(String fileLimitSize) {
		this.fileLimitSize = fileLimitSize;
	}
	
	public String getCallBackFunctionName() {
		return callBackFunctionName;
	}
	
	public void setCallBackFunctionName(String callBackFunctionName) {
		this.callBackFunctionName = callBackFunctionName;
	}
	
	public String getCallBackParamsString() {
		return callBackParamsString;
	}
	
	public void setCallBackParamsString(String callBackParamsString) {
		this.callBackParamsString = callBackParamsString;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFileType() {
		return fileType;
	}
	
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
}
