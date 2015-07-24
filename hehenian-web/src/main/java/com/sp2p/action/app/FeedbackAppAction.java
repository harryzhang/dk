package com.sp2p.action.app;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.web.util.JSONUtils;
import com.sp2p.service.FeedbackService;


/**
 * 前台意见反馈
 * @author Administrator
 *
 */
@SuppressWarnings("unchecked")
public class FeedbackAppAction extends BaseAppAction {
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(FeedbackAppAction.class);
	private FeedbackService feedbackService;

	public FeedbackService getFeedbackService() {
		return feedbackService;
	}

	public void setFeedbackService(FeedbackService feedbackService) {
		this.feedbackService = feedbackService;
	}
	
	/**
	 * 增加意见反馈
	 * @return String
	 * @throws SQLException
	 * @throws DataException
	 * @throws IOException 
	 * @throws UnsupportedEncodingException
	 */
	public String frontAddFeedback() throws SQLException, DataException, IOException{
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> infoMap = this.getAppInfoMap();
			Map<String, String> appAuthMap = getAppAuthMap();
			String uid = appAuthMap.get("uid");
			if(StringUtils.isBlank(uid)){
				jsonMap.put("error", "1");
				jsonMap.put("msg", "请先登陆");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long userId = Convert.strToLong(uid, -1l);
			String content = infoMap.get("content");
			if(StringUtils.isBlank(content)){
				jsonMap.put("error", "2");
				jsonMap.put("msg", "请输入反馈内容");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long returnId = -1;
			returnId = feedbackService.addFeedback(content, userId);
			if(returnId <= 0){
				jsonMap.put("error", "4");
				jsonMap.put("msg", "反馈失败");
				JSONUtils.printObject(jsonMap);
			}
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "反馈成功");
			JSONUtils.printObject(jsonMap);
		} catch (IOException e) {
			jsonMap.put("error", "3");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(paramMap);
			log.error(e);
		}
		return null;
	}
}
