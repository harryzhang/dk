package com.sp2p.system.interceptor;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.shove.data.ConnectionManager;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.User;

/**
 * 用户登录拦截
 * 
 * @author 杨程
 * @Create Jun 3, 2011
 * 
 */
public class FrontUserSeesionInterceptor implements Interceptor {
	public static Log log = LogFactory
			.getLog(FrontUserSeesionInterceptor.class);
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private final List<String> virtualUserList = Arrays.asList(
			"financeInvestInit.do", "reportInit.do", "mailInit.do",
			"focusonUser.do", "focusonBorrow.do", "financeInvest.do",
			"addBorrowMSG.do", "addDebtMSG.do", "auctingDebtInit.do",
			"addAuctingDebt.do", "updatePersonImg.do", "alipayPayment.do",
			"sendSMS.do", "addWithdraw.do", "deleteSysMails.do","deleteWithdraw.do",
			"updateSys2Readed.do", "updateSys2UNReaded.do", "addMail.do",
			"deleteSendMails.do", "deleteReceiveMails.do","addChangeBindingMobile.do","addBindingMobile.do",
			"updateReceive2Readed.do", "updateReceive2UNReaded.do",
			"updateBasedate.do", "updatework.do", "updateLoginPass.do",
			"bindingMobileInit.do", "addNotesSetting.do", "addBankInfo.do",
			"deleteBankInfo.do", "bankChangeCancel.do", "updateBankInfo.do",
			"deleteuserFrend.do", "queryMyPayData.do", "submitPay.do",
			"delBorrowConcern.do", "automaticBidModify.do","updateUserVip.do",
			"automaticBidSet.do", "addAssignmentDebt.do", "cancelApplyDebt.do",
			"auctingDebtEnd.do", "addBorrowInit.do", "addCrediting.do","updateShiping.do",
			"updateBasedate.do", "updatework.do", "addImg.do","sendSMS.do",
			"addpastPicturdate.do", "updataUserVipStatus.do", "addBorrow.do","creditingInit.do");
	
	//选择发标，发标，发布秒还标的，流转标发标,流转标投标 ，可转让的债权，竞拍中的债权， 债权竞拍,充值,提现,还款
	private final List<String>  regisCodeList  = Arrays.asList("addBorrowInit.do","addBorrow.do",
			"addBorrowSeconds.do","addCirculationBorrow.do","subscribe.do","addAuctingDebt.do","auctingDebtInit.do",
			"rechargeInit.do","withdrawLoad.do","queryCanAssignmentDebt.do","queryAuctingDebt.do","addWithdraw.do",
			"submitPay.do","queryMyPayData.do","queryAllDetails.do","queryMyPayingBorrowList.do","financeInvest.do",
			"financeInvestInit.do","subscribeinit.do");
	
	@Override
	public void destroy() {
	}

	@Override
	public void init() {
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		session = request.getSession();
		String retUrl = request.getHeader("Referer");
		log.info("-------------------" + retUrl);
		log.info(request.getRequestURL());
		int index=request.getContextPath().length()+1;
		String uri=request.getRequestURI().substring(index);
		//配置拦截器   注册码拦截
		 if (regisCodeList.contains(uri)){
			 //ConnectionManager cm = ConnectionManager.getInstance();
				//String cmm =  cm.getMID();
				if(!"MID-22824-47058-95091-87448-87678".equals(IConstants.ZCM)){
					response.setCharacterEncoding("UTF-8");
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter	out = response.getWriter();
					out.print("<script>alert('产品未注册,请联系管理员!');window.history.go(-1);</script>");
					return  null;
				}
			}
		if (!IConstants.USER_SESSION_SWITCH) {
			return invocation.invoke();
		}
		if (isAjaxRequest()) {
			return ajaxIntercept(invocation);
		}
		return _intercept(invocation);
	}

	// 普通请求拦截 没登录返回 noLogin 登录流程继续
	@SuppressWarnings("unchecked")
	private String _intercept(ActionInvocation invocation) throws Exception {
		log.info("普通请求拦截");
		AccountUserDo user = (AccountUserDo) session.getAttribute(IConstants.SESSION_USER);
		int index=request.getContextPath().length()+1;
		String uri=request.getRequestURI().substring(index);
		if (null == user) {
			/*int index = request.getContextPath().length() + 1;
			String uri = request.getRequestURI().substring(index);*/
			Map<String, Object> parameterMap = request.getParameterMap();
			StringBuffer condition = new StringBuffer("");
			if (parameterMap != null) {
				Iterator iterator = parameterMap.keySet().iterator();
				if (iterator.hasNext()) {
					String key = (String) iterator.next();
					String[] values = (String[]) parameterMap.get(key);
					String valueStr = "";
					for (int i = 0; i < values.length; i++) {
						valueStr = (i == values.length - 1) ? valueStr
								+ values[i] : valueStr + values[i] + ",";
					}
					condition.append(key + "=" + valueStr + "&");
				}
			}
			String param = condition.toString();
			if (StringUtils.isNotBlank(param)) {
				param = param.substring(0, param.length() - 1);
				session.setAttribute("afterLoginUrl", uri + "?" + param);
			} else {
				session.setAttribute("afterLoginUrl", uri);
			}
			log.info("No Login");
			return IConstants.ADMIN_AJAX_LOGIN;
		}
		/*else if(user.getVirtual() == 1){
			int index=request.getContextPath().length()+1;
			String uri=request.getRequestURI().substring(index);
			if(virtualUserList.contains(uri)){
				return IConstants.USER_VIRTUAL;
			}
		}*/
		log.info("id：" + user.getId() + " name：" + user.getUsername());
		return invocation.invoke();

	}

	// ajax请求拦截 没登录返回 NoLogin 登录流程继续
	private String ajaxIntercept(ActionInvocation invocation) throws Exception {
		log.info("ajax拦截");
	/*ConnectionManager cm = ConnectionManager.getInstance();
		String cmm =  cm.getMID();
		boolean  b = com.shove.security.License.isSystemRegister();
		if(!IConstants.ZCM.equals(cmm)){
			return "network";
		}*/
		AccountUserDo user = (AccountUserDo) session.getAttribute(IConstants.SESSION_USER);
		if (null == user) {
			response.setContentType("text/html");
			response.getWriter().print(IConstants.ADMIN_AJAX_LOGIN);
			log.info("No Login");
			//response.sendRedirect("login.do");
			return null;
		}
		/*else if(user.getVirtual() == 1){
			int index=request.getContextPath().length()+1;
			String uri=request.getRequestURI().substring(index);
			if(virtualUserList.contains(uri)){
				response.setContentType("text/html");
				response.getWriter().print(IConstants.USER_VIRTUAL);
				return null;
			}
		}*/
		log.info("id：" + user.getId() + " name：" + user.getUsername());
		return invocation.invoke();
	}

	@SuppressWarnings("unused")
	private String getRemortIP() {
		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}

	private boolean isAjaxRequest() {
		String header = request.getHeader("X-Requested-With");
		if (header != null && "XMLHttpRequest".equals(header)) {
			return true;
		}
		return false;
	}
}
