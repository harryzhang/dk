/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2014
 */


package com.hehenian.web.view.activity;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.RequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.activity.IActivityOrderService;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Component("activityOrderAction")
public class ActivityOrderAction extends ActionSupport implements RequestAware {
	
	private final Logger logger = Logger.getLogger(this.getClass());	
	@Autowired
	private IActivityOrderService activityOrderService;
	
	private Map<String, Object> request;
	
	@Override
	public void setRequest(Map<String, Object> request) {
		this.request = request;		
	}
	

	/**
	 * 新增或修改
	 * 
	 */
	public String saveActivityOrder(){
		return "";
	}
	

	
	
	/**
	 * 删除
	 * 
	 */
	public String deleteActivityOrder(){
	    return "";
	}
	
	/**
	 * 查找
	 * 
	 * @return
	 */
	public String findById(){
		return "";
	}
	
	/**
	 * 根据条件查找
	 * 
	 * @return
	 */
	public String query(){
		return "";
	}

	/**
	 * 订单收益查询接口 接口参数： userId bigInt 非空 20 彩之云用户账号 接口参数：orderSN 字符 非空 32 订单号
	 * 
	 * 返回： userId bigInt 非空 20 彩之云用户账号 orderSN 字符 非空 32 订单号 orderInvestAmount
	 * 保留两位小数Decimal(18,2) 非空 订单投资金额 orderInterestAmount 保留两位小数Decimal(18,2) 非空
	 * 订单利息 　orderWithdrawalAmount 保留两位小数Decimal(18,2) 非空 订单可提取金额
	 * 
	 * @return json 字符串
	 * @author: zhangyunhmf
	 * @date: 2014年10月30日下午2:52:49
	 */
	public String orderDetail() {
		String userId = (String) this.request.get("userId");
		String orderSN = (String) this.request.get("orderSN");

		checkUserId(userId);
		if (StringUtils.isBlank(orderSN)) {

		}

		activityOrderService.queryOrderDetail(userId, orderSN, "0");
		return null;
	}

	private void checkUserId(String userId) {
		if (StringUtils.isBlank(userId)) {

		}

		try {
			Long.parseLong(userId);
		} catch (Exception e) {
			this.logger.error("userId无效参数");
		}
	}

}
