package com.hehenian.sevlet.activity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.caucho.hessian.client.HessianProxyFactory;
import com.hehenian.biz.common.activity.IActivityOrderService;
import com.hehenian.biz.common.activity.dataobject.ActivityConfig;
import com.hehenian.biz.common.activity.dataobject.ActivityOrderDo;
import com.hehenian.biz.common.util.JsonUtil;
import com.hehenian.biz.common.util.Md5Utils;
import com.hehenian.web.common.util.ServletUtils;

public class ActivityOrderServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4072908254026181354L;
	

	private Logger logger = Logger.getLogger(this.getClass());

	private IActivityOrderService activityOrderService;
	
	private ActivityConfig activityConfig;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		super.init(config);
		
		ServletContext servletContext = config.getServletContext();
		WebApplicationContext webApplicationContext = WebApplicationContextUtils
				.getWebApplicationContext(servletContext);
		AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext
				.getAutowireCapableBeanFactory();
		
		activityConfig = (ActivityConfig) autowireCapableBeanFactory.getBean("activityConfig");
		
		HessianProxyFactory factory = new HessianProxyFactory();
		try {
			activityOrderService = (IActivityOrderService) factory.create(IActivityOrderService.class, activityConfig.getHehenianServiceURL()+"/activityOrderService");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	/**
	 * 总收益查询接口: 接口参数： userId bigInt 非空 20 彩之云用户账号
	 * 
	 * 返回： userId bigInt 非空 20 彩之云用户账号 totalInvestAmount 保留两位小数Decimal(18,2) 非空
	 * 累计投资金额 totalInterestAmount 保留两位小数Decimal(18,2) 非空 累计利息 withdrawalAmount
	 * 保留两位小数Decimal(18,2) 非空 可提取金额
	 * 
	 * @return json 字符
	 * @author: zhangyunhmf
	 * @date: 2014年10月30日下午2:55:31
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

        String userId = req.getParameter("userId");
        String orderSN = req.getParameter("orderSN");
		String reqTime = req.getParameter("reqTime");

		String sign = req.getParameter("sign");
        
		// 参数验证
        Map<String,String> status = new HashMap<String,String>();
        String jsonStr = "";
        int retval = checkUserId(userId);
        if( 0 == retval){
        	status.put("statusCode", "001");
        	status.put("statusDesc", "无效参数");
        	jsonStr = JsonUtil.toString(status);
        	ServletUtils.write(jsonStr, ServletUtils.RESP_CONTTYPE_JSON, resp);
        }
        if(StringUtils.isBlank(orderSN)){
        	logger.error("orderSN 无效参数");
        	status.put("statusCode", "001");
        	status.put("statusDesc", "无效参数");
        	jsonStr = JsonUtil.toString(status);
        	ServletUtils.write(jsonStr, ServletUtils.RESP_CONTTYPE_JSON, resp);
        }

		// md5 校验
		StringBuffer signSb = new StringBuffer();
		signSb.append("userId").append(userId).append("orderSN")
				.append(orderSN).append("reqTime").append(reqTime);
		boolean signOk = Md5Utils.checkMD5(sign,
				activityConfig.getHehenianKey(), signSb.toString());
		if (!signOk) {
			logger.error("md5验证失败");
			status.put("statusCode", "002");
			status.put("statusDesc", "md5验证失败");
			jsonStr = JsonUtil.toString(status);
			ServletUtils.write(jsonStr, ServletUtils.RESP_CONTTYPE_JSON, resp);
		}
        
		// 查询数据
        ActivityOrderDo activityDo = activityOrderService.queryOrderDetail(userId, orderSN,"0");
        if(null == activityDo){
			status.put("statusCode", "003");
        	status.put("status", "没有找到");
        	jsonStr = JsonUtil.toString(status);
        }else{
        	jsonStr = JsonUtil.toString(activityDo);
        }

		// 用户ID转换成彩色的ID
		activityDo.setUserId(Long.valueOf(userId));
		ServletUtils.write(jsonStr, ServletUtils.RESP_CONTTYPE_JSON, resp);
	}

	private  int checkUserId(String userId) {
		if (StringUtils.isBlank(userId)) {
			logger.error("userId 无效参数");
			return 0;
		}

		try {
			Long.parseLong(userId);
		} catch (Exception e) {
			logger.error("userId 无效参数");
			return 0;
		}
		return 1;
	}

}
