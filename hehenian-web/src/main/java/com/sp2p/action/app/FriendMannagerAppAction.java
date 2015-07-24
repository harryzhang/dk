package com.sp2p.action.app;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.shove.web.util.DesSecurityUtil;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.User;
import com.sp2p.service.RecommendUserService;

/**
 * 好友管理
 * @author Administrator
 *
 */
public class FriendMannagerAppAction extends BaseAppAction{

	public static Log log = LogFactory.getLog(FriendMannagerAppAction.class);
	private static final long serialVersionUID = 1L;
	
	private RecommendUserService recommendUserService;
	
	//邀请好友列表
	private List<Map<String, Object>> recommendUserList;
	
	
	/**
	 * 加载邀请的好友列表
	 * @return
	 * @throws IOException 
	 */
	public String FriendManagerInit() throws IOException{
		//获取用户的信息
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
			Long userId=user.getId();
			recommendUserService.queryfindRecommendUserPage(pageBean, userId);
			List<Map<String,String>>  list=pageBean.getPage();
			DesSecurityUtil des = new DesSecurityUtil();
			String userI = des.encrypt(userId.toString());
		    String uri=getPath();
			jsonMap.put("url", uri);
			jsonMap.put("userId", userI);
			jsonMap.put("pageBean", pageBean);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
		}
		return null;
	}

	
	//返回网站地址
	private String getPath(){
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


	public RecommendUserService getRecommendUserService() {
		return recommendUserService;
	}



	public void setRecommendUserService(RecommendUserService recommendUserService) {
		this.recommendUserService = recommendUserService;
	}



	public List<Map<String, Object>> getRecommendUserList() {
		return recommendUserList;
	}



	public void setRecommendUserList(List<Map<String, Object>> recommendUserList) {
		this.recommendUserList = recommendUserList;
	}



}
