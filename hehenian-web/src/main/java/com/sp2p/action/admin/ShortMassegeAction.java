package com.sp2p.action.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.shove.Convert;
import com.shove.util.SqlInfusion;
import com.shove.web.action.BasePageAction;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.admin.ShortMaseegeService;
import com.sp2p.service.admin.UserManageServic;

public class ShortMassegeAction extends BasePageAction {
	private UserManageServic userManageServic ;
	private ShortMaseegeService shortMaseegeService;
	private String ids;
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public UserManageServic getUserManageServic() {
		return userManageServic;
	}

	public void setUserManageServic(UserManageServic userManageServic) {
		this.userManageServic = userManageServic;
	}

	public ShortMaseegeService getShortMaseegeService() {
		return shortMaseegeService;
	}

	public void setShortMaseegeService(ShortMaseegeService shortMaseegeService) {
		this.shortMaseegeService = shortMaseegeService;
	}

	public String sendShortMassegeInit() {
		return SUCCESS;
	}

	public String sendShortMassege() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = userManageServic.queryUserList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		request().setAttribute("users", list);
		return SUCCESS;
	}

	public String addShortMassege() throws Exception {
		String pageId = SqlInfusion.FilteSqlInfusion(request().getParameter("pageId"));
		String code = (String) session().getAttribute(pageId + "_checkCode");
		String _code = SqlInfusion.FilteSqlInfusion(paramMap.get("code").toString().trim());
		if (code == null || !_code.equals(code)) {
			this.addFieldError("paramMap.code", "验证码错误！");
			return INPUT;
		}
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		String str =  shortMaseegeService.sendShortMaseege(paramMap,admin);
		if("SUCCESS".equals(str)){
			return SUCCESS;
		}
		if("INPUT".equals(str)){
			return INPUT;
		}
		return SUCCESS;
	}
	public String queryShortMaseegeListInit() {
		
		return SUCCESS;
	}
	public String queryShortMaseegeList() {
		Integer status=Convert.strToInt(paramMap.get("status"),0);
		String beginTime=SqlInfusion.FilteSqlInfusion(paramMap.get("beginTime"));
		String endTime=SqlInfusion.FilteSqlInfusion(paramMap.get("endTime"));
		try {
			shortMaseegeService.queryShortMassegePage(pageBean, status, beginTime, endTime);
			int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return SUCCESS;
	}
	public String deleteShortMassege()  {
		
		try {
			shortMaseegeService.deleteShortMassege(ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String queryShortMassege()  {
		
		try {
			shortMaseegeService.deleteShortMassege(ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String sendMaseege() {

		try {
			shortMaseegeService.deleteShortMassege(ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
