package com.shove.vo;
 
import java.io.File;

/**
 * 文件参数
 * @author ss_yanxiu
 *
 */
public class Files {
	
	private File files;       //文件
	private String filesFileName;  //文件名
	private String filesContentType; //文件类型
	private String filesRemark; //文件描述
	
	public String getFilesRemark() {
		return filesRemark;
	}
	
	public void setFilesRemark(String filesRemark) {
		this.filesRemark = filesRemark;
	}
	
	public File getFiles() {
		return files;
	}
	
	public void setFiles(File files) {
		this.files = files;
	}
	
	public String getFilesFileName() {
		return filesFileName;
	}
	
	public void setFilesFileName(String filesFileName) {
		this.filesFileName = filesFileName;
	}
	
	public String getFilesContentType() {
		return filesContentType;
	}
	
	public void setFilesContentType(String filesContentType) {
		this.filesContentType = filesContentType;
	}
}
