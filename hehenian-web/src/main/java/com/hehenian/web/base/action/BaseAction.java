package com.hehenian.web.base.action;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.util.JsonUtil;
import com.hehenian.web.common.contant.WebConstants;
import com.hehenian.web.common.util.JSONUtils;
import com.hehenian.web.common.util.ServletUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.sp2p.constants.IAmountConstants;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.entity.User;
import com.sp2p.system.exception.FrontHelpMessageException;
import com.sp2p.util.WebUtil;

/**
 * action的基类
 * 
 */
public class BaseAction extends ActionSupport {
    
	private static final long serialVersionUID = 1L;
	// 页面表单存放数据
	protected Map<String, String> paramMap = new HashMap<String, String>();
	protected HelpMessage helpMessage = new HelpMessage();
	//PrintWriter打印输出
	protected PrintWriter out;
    

	protected HttpServletRequest request() {
		return ServletActionContext.getRequest();
	}

	protected String request(String key) {
		return ServletUtils.FilteSqlInfusion(request().getParameter(key));
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

	protected AccountUserDo getUser() {
        AccountUserDo user = (AccountUserDo)session().getAttribute(IConstants.SESSION_USER);
        if(user == null){
            user = new AccountUserDo();
        }
        return user;
    }
	
	protected long getUserId() {
        AccountUserDo user = (AccountUserDo) session().getAttribute(WebConstants.SESSION_USER);
		long userId = -1;
		if (user != null) {
			userId = user.getId();
		}
		return userId;
	}
    protected int getSourceFrom(){
        Object sourceFromObj = session("sourcefrom");
        int sourceFrom = 1;
        if (sourceFromObj !=null ){
            try {
                sourceFrom = Integer.parseInt(sourceFromObj.toString());
            }catch (Exception e){

            }
        }
        return sourceFrom;
    }
	
	public PrintWriter getOut() throws Exception {
        response().setCharacterEncoding("UTF-8");
        response().setContentType("text/html; charset=UTF-8");
        out = response().getWriter();
        return out;
    }  
	
	 protected long getAdminId() {
        Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
        long id = -1;
        if (admin != null) {
            id = admin.getId();
        }
        return id;
    }

	
	/**
     * 跳转拦截
     * 
     * @param title
     * @param msg
     * @param url
     * @throws FrontHelpMessageException
     */
    public void createHelpMessage(String title, String msg, String url)
            throws FrontHelpMessageException {
        /* helpMessage.setTitle("用户不存在"); */
        helpMessage.setMsg(new String[] { "返回首页" });
        helpMessage.setUrl(new String[] { "index" });
        helpMessage.setTitle(title);
        /*
         * helpMessage.setMsg(new String[]{msg}); helpMessage.setUrl(new
         * String[]{url});
         */
        throw new FrontHelpMessageException();
    }
      
    
    /**
     * 输出result 格式成json后输出 到response
     * @param result
     * @throws Exception  
     * @author: zhangyunhmf
     * @date: 2014年9月23日下午3:56:52
     */
    protected void  printResultToResponse(IResult result)throws Exception{
        JSONObject json = new JSONObject();
        if(result.isSuccess()){
            json.put("msg", "ok");
        }else{
            if(null == result.getErrorMessage()){
                json.put("msg", "操作失败");
            }else{
                json.put("msg", result.getErrorMessage());
            }
        }
        JSONUtils.printObject(json);
    }
    
    
    public String returnParentUrl(String function,String url) throws Exception{
        getOut().print("<script type='text/javascript'>"+function+"parent.location.href='" + request().getContextPath()
                            + "/"+url+";</script>");
        return null;
    }
    
    public String getPath() {
        int port = request().getServerPort();
        String portStr = "";
        if(port != 80){
            portStr = ":"+port; 
        }
        String path = request().getScheme() + "://" + request().getServerName()
        + portStr + request().getContextPath()
        + "/";
        return path;
    }
    
    protected void sendHtml(String html) throws Exception{
        PrintWriter out = getOut();
        out.println(html);
        out.flush();
        out.close();
    }
     
    @SuppressWarnings("unchecked")
    public Map<String, Object> getPlatformCost(){
        //获取平台收费
        Map<String,Object> platformCostMap = (Map<String, Object>) application().getAttribute(WebConstants.FEE_APPLICATION);
        if(platformCostMap == null)
            platformCostMap = new HashMap<String, Object>();
        return platformCostMap;
    }
    

    
    /**
     * 将request中的参数设置到paramMap中去
     */
    @SuppressWarnings("unchecked")
    protected void setRequestToParamMap(){
        Enumeration<String> keyNames = request().getParameterNames();
        while(keyNames.hasMoreElements()){
            String attrName = keyNames.nextElement();
            paramMap.put(attrName, request(attrName));
        }
    }
    protected long getLongParam(String paramName,long defaultValue){
        try {
            return Long.parseLong(request(paramName));
        }catch (Exception e){
            return defaultValue;
        }
    }
    protected int getIntParam(String paramName,int defaultValue){
        try {
            return Integer.parseInt(request(paramName));
        }catch (Exception e){
            return defaultValue;
        }
    }
    protected double getDoubleParam(String paramName,double defaultValue){
        try {
            return Double.parseDouble(request(paramName));
        }catch (Exception e){
            return defaultValue;
        }
    }
    protected int getSessionIntAttr(String attrName,int defaultValue){
        try {
            return (Integer)session().getAttribute(attrName);
        }catch (Exception e){
            return defaultValue;
        }
    }
    protected String getSessionStrAttr(String attrName){
        try {
            return session().getAttribute(attrName).toString();
        }catch (Exception e){
            return null;
        }
    }
    public String getBasePath(){
        return ServletUtils.getWebPath();
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
