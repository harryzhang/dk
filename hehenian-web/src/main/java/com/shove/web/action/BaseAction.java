package com.shove.web.action;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.shove.util.SqlInfusion;
import com.shove.vo.HelpMessage;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.User;

/**
 * action的基类
 * 
 */
public class BaseAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	// 页面表单存放数据
	protected Map<String, String> paramMap = new HashMap<String, String>();
	protected HelpMessage helpMessage = new HelpMessage();

	protected HttpServletRequest request() {
		return ServletActionContext.getRequest();
	}

	protected String request(String key) {
		return SqlInfusion.FilteSqlInfusion(request().getParameter(key));
	}

	protected void export(HSSFWorkbook wb, String fileName) throws IOException {
		HttpServletResponse response = response();
		// 设置response的编码方式
		response.setContentType("application/x-msdownload");

		// 写明要下载的文件的大小
		// response.setContentLength((int)fileName.length());

		// 设置附加文件名
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

		// 解决中文乱码
		// response.setHeader("Content-Disposition","attachment;filename="+new
		// String
		// (filename.getBytes("gbk"),"iso-8859-1"));
		OutputStream output = response().getOutputStream();
		wb.write(output);

		output.flush();
		output.close();

	}

	protected long getUserId() {
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		long userId = -1;
		if (user != null) {
			userId = user.getId();
		}
		return userId;
	}

	protected HttpSession session() {
		return ServletActionContext.getRequest().getSession();
	}

	protected Object session(String key) {
		return session().getAttribute(key);
	}

	protected ServletContext application() {
		return ServletActionContext.getServletContext();
	}

	protected HttpServletResponse response() {
		return ServletActionContext.getResponse();
	}

	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}

	public HelpMessage getHelpMessage() {
		return helpMessage;
	}

	public void setHelpMessage(HelpMessage helpMessage) {
		this.helpMessage = helpMessage;
	}
}
