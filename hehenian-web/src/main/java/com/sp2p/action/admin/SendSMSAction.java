package com.sp2p.action.admin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.SMSUtil;
import com.shove.util.SqlInfusion;
import com.shove.web.action.BasePageAction;
import com.shove.web.util.JSONUtils;
import com.sp2p.service.admin.SMSInterfaceService;
import com.sp2p.service.admin.SendSMSService;

/**
 * 网站公告Action
 * @author zhongchuiqing
 *
 */
@SuppressWarnings("unchecked")
public class SendSMSAction extends BasePageAction {

	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(SendSMSAction.class);
	
	private SendSMSService sendSMSService;
	
	private SMSInterfaceService sMSInterfaceService;


	public SendSMSService getSendSMSService() {
		return sendSMSService;
	}

	public void setSendSMSService(SendSMSService sendSMSService) {
		this.sendSMSService = sendSMSService;
	}

	public SMSInterfaceService getSMSInterfaceService() {
		return sMSInterfaceService;
	}

	public void setSMSInterfaceService(SMSInterfaceService interfaceService) {
		sMSInterfaceService = interfaceService;
	}
	
	
	

	/**
	 * 初始化分页用户列表
	 * @return
	 */
	public String queryUserListInit(){
		return SUCCESS;
	}
	
	/**
	 * 初始化短信发送列表
	 * @return
	 */
	public String querySendSMSListInit(){
		return SUCCESS;
	}
	
	/**
	 * 初始化短信详情类别
	 * @return
	 */
	public String getSendSMSByDetailpageInit(){
		return SUCCESS;
	}
	
	/**
	 * 分页查询用户列表列表
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String queryUserListPage()throws SQLException,DataException{
		String userName=SqlInfusion.FilteSqlInfusion(paramMap.get("userName"));
		String realName=SqlInfusion.FilteSqlInfusion(paramMap.get("realName"));
		try {
			sendSMSService.queryUserPage(pageBean, userName, realName,null);
			int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	
	
	/**
	 * 分页查询短信发送列表
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws UnsupportedEncodingException 
	 */
	public String querySendSMSListPage()throws SQLException,DataException, UnsupportedEncodingException{
		String beginTime=SqlInfusion.FilteSqlInfusion(paramMap.get("beginTime"));
		String endTime=SqlInfusion.FilteSqlInfusion(paramMap.get("endTime"));
		
		try {
			sendSMSService.querySendSMSPage(pageBean,beginTime, endTime);
			List<Map<String,Object>> list=pageBean.getPage();
			if(list!=null){
				for(Map<String,Object> map:list){
					String[] num=map.get("splitId").toString().split(",");
					map.put("nums", num.length);
				}
			}
			int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	
	/**
	 * 获取编辑内容
	 * @return
	 */
	public String getSendSMSContent(){
		Object object=session().getAttribute("content");
		
		if(object!=null){
			request().setAttribute("contents",object);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 添加发送短信内容信息
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String addsendSMSContent()throws SQLException,DataException,IOException{
		String content=SqlInfusion.FilteSqlInfusion(paramMap.get("content"));
		if(StringUtils.isNotBlank(content)){
			session().setAttribute("content", content);
			JSONUtils.printStr("1");
			
			return null;
		}else{
			
			JSONUtils.printStr("0");
			return null;
		}
	}
	

	
	/**
	 *根据Id获取网发送短信信息详情
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String getSendSMSByDetailpage()throws SQLException,DataException{
	    	Long id=Convert.strToLong(request("id"), 0);
	    	String userName=SqlInfusion.FilteSqlInfusion(paramMap.get("userName"));
			String realName=SqlInfusion.FilteSqlInfusion(paramMap.get("realName"));
		try {
			Map<String,String> map=sendSMSService.getSendSMSByDetail(id);
			sendSMSService.queryUserPage(pageBean, userName, realName,map.get("splitId"));
			
			
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}catch(DataException e){
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		
		return SUCCESS;
	}
	
	
	
	
	/**
	 * 发送短信
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws DocumentException 
	 */
public String SendSMSs()throws SQLException,DataException,IOException, DocumentException{
		String ids=SqlInfusion.FilteSqlInfusion(paramMap.get("id"));//id拼接 用，隔开
	    String cellphones="";
		Object object=session().getAttribute("content");
		String content="";
		if(object!=null){
			content=object.toString();
		}else{
			JSONUtils.printStr("0");
			return null;
		}
		try {
			//根据id集合获取用户phone
			sendSMSService.queryUserPage(pageBean, null, null,ids);
			List<Map<String, Object>> list=pageBean.getPage();
			int count = 0;
			if(list!=null && list.size() == 1){
				for(Map<String,Object> map:list){
					if(map.get("cellPhone") == null){
						JSONUtils.printStr("4");
						return null;
					}
					String phone=map.get("cellPhone").toString();
					cellphones+=phone+",";
				}
			}
			else if(list.size() > 1){
				for(Map<String,Object> map:list){
					if(map.get("cellPhone") == null){
						count++;
						if(count == list.size()){
							JSONUtils.printStr("5");
							return null;
						}
						continue;
					}
					String phone=map.get("cellPhone").toString();
					cellphones+=phone+",";
				}
			}
			else{
				JSONUtils.printStr("3");
				return null;
			}
			
			
			Map<String,String> map=sMSInterfaceService.getSMSById(1);
//			
//			//获取短信接口url
//			String url=SMSUtil.getSMSPort(map.get("url"), map.get("UserID"), map.get("Account"), map.get("Password"), null, content, cellphones, null);
//			//发送短信
//			String retCode = SMSUtil.sendSMS(url); 
			
//			//获取短信接口url
//			String url=SMSUtil.getSMSPort(map.get("url"), map.get("UserID"), map.get("Account"), map.get("Password"), null, content, cellphones, null);
//			//发送短信
//			String retCode = SMSUtil.sendSMS(url); 
			StringBuffer buffer = new StringBuffer();
			buffer.append(cellphones);
			buffer.delete(buffer.lastIndexOf(","),buffer.lastIndexOf(",") + 1);
			String retCode = SMSUtil.sendSMS(map.get("Account"),map.get("Password"),content,buffer.toString(),null);
			if(retCode.equals("Sucess")){
				//添加短信记录
				@SuppressWarnings("unused")
				Long result=sendSMSService.SendSMSs(content, ids, cellphones);
				
				JSONUtils.printStr("1");				
				return null;
			}else{
				JSONUtils.printStr("2");
				return null;
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		
	}
}
