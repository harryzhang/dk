package com.sp2p.action.app;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.JSONUtils;
import com.shove.vo.PageBean;
import com.sp2p.constants.IConstants;
import com.sp2p.service.HelpAndServicerService;

public class CallCenterAppAction extends BaseAppAction {
	
	public static Log log = LogFactory.getLog(CallCenterAppAction.class);
	private static final long serialVersionUID = 1L;

//	private CallCenterService callCenterService;
//	private KefuInfoService kefuService;
	private HelpAndServicerService helpAndServicerService;
	public HelpAndServicerService getHelpAndServicerService() {
		return helpAndServicerService;
	}

	public void setHelpAndServicerService(
			HelpAndServicerService helpAndServicerService) {
		this.helpAndServicerService = helpAndServicerService;
	}

	private Map<String,String> helpAnswer;
	private String question;
	

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public void setHelpAnswer(Map<String, String> helpAnswer) {
		this.helpAnswer = helpAnswer;
	}

	/**
	 * 帮助中心初始化
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws IOException
	 */
	public String callCenterInit() throws SQLException, DataException, IOException{
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try{
			Map<String, String> infoMap = this.getAppInfoMap();
			String pageNum = infoMap.get("curPage");
			String cid = infoMap.get("cid") == null?"1":infoMap.get("cid");
			request().setAttribute("cid", cid);
//			jsonMap.put("cid", cid);
			long typeId = Long.parseLong(cid);
			try{	
				//查询左边菜单
				pageBean = new PageBean();
			//	callCenterService.queryHelpTypes(pageBean);
				helpAndServicerService.queryHelpTypes(pageBean);
				List<Map<String, Object>> types = pageBean.getPage();
				
				Map<String,String> values = null;
				String curDes = null;
				if(StringUtils.isNotBlank(infoMap.get("type"))){
				//	values = callCenterService.getDescribeById(typeId);
					values = helpAndServicerService.getDescribeById(typeId);
					curDes = values.get("helpDescribe");//获得帮助类型描述
				}
				
				//右边问题
				
				pageBean = new PageBean();
				pageBean.setPageSize(4);
				if(StringUtils.isNotBlank(pageNum)){
					pageBean.setPageNum(pageNum);
				}
				
//				callCenterService.queryHelpQuestions(pageBean,typeId);
				helpAndServicerService.queryHelpQuestions(pageBean,typeId);
//				List<Map<String, Object>> questions = pageBean.getPage();
				List<Map<String, Object>> lists;//客服列表
//				lists = kefuService.queryKefuLimit4();
				lists = helpAndServicerService.queryKefuLimit4();
				jsonMap.put("types", types);
				jsonMap.put("curDes", curDes);
				jsonMap.put("typeId", typeId);
				jsonMap.put("lists", lists);
				jsonMap.put("pageBean", pageBean);
				/**
				 * 2013-04-16 顶部客服链接  链接到客服列表页面
				 */
//				String showkf = infoMap.get("showkf");
//				if(showkf != null){
//					jsonMap.put("showkf", showkf);
//				}
			}catch(DataException e){
				log.error(e);
				e.printStackTrace();
				throw e;
			}catch(SQLException e){
				log.error(e);
				e.printStackTrace();
				throw e;
			}
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "查询成功");
			JSONUtils.printObject(jsonMap);
		}catch (Exception e) {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 显示客服列表
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public String callKfsInit() throws SQLException, DataException, IOException{
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try{
			List<Map<String,Object>> lists;
	//		lists = kefuService.queryKefuList();
			lists = helpAndServicerService.queryKefuList();
			jsonMap.put("lists", lists);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "查询成功");
			JSONUtils.printObject(jsonMap);
		}catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}
	
	public String queryCallCenterInit() throws SQLException, DataException, IOException{
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try{
			Map<String, String> infoMap = this.getAppInfoMap();	
			String pageNum = infoMap.get("curPage");
			long typeId = Long.parseLong(infoMap.get("cid"));
			//前台显示列表类型
			if(StringUtils.isNotBlank(pageNum)){
				pageBean.setPageNum(pageNum);
			}
			pageBean.setPageSize(4);
//			callCenterService.queryAllAnswerLists(IConstants.SORT_TYPE_DESC, pageBean,typeId);
			helpAndServicerService.queryAllAnswerLists(IConstants.SORT_TYPE_DESC, pageBean,typeId);
			jsonMap.put("cid", typeId);
			jsonMap.put("pageBean", pageBean);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "查询成功");
			JSONUtils.printObject(jsonMap);
		}catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查看帮助中心问题回复
	 * @return
	 * @throws Exception
	 */
	public String queryCallCenterAnswer() throws Exception{
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		//添加浏览量
		try{
			Map<String, String> infoMap = this.getAppInfoMap();	
			String id = Convert.strToStr(infoMap.get("id"), "");;
	//		helpAnswer = callCenterService.queryAnswer(Convert.strToLong(id, -1));
			helpAnswer = helpAndServicerService.queryAnswer(Convert.strToLong(id, -1));
	//		callCenterService.updateQuestionBrowse(Convert.strToLong(id, -1));
			helpAndServicerService.updateQuestionBrowse(Convert.strToLong(id, -1));
			//获得对应问题的回答值
			jsonMap.put("anwer", helpAnswer.get("anwer"));
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			JSONUtils.printObject(jsonMap);
		}catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

//	public CallCenterService getCallCenterService() {
//		return callCenterService;
//	}
//
//
//	public void setCallCenterService(CallCenterService callCenterService) {
//		this.callCenterService = callCenterService;
//	}
//
//	public KefuInfoService getKefuService() {
//		return kefuService;
//	}
//
//	public void setKefuService(KefuInfoService kefuService) {
//		this.kefuService = kefuService;
//	}
}
