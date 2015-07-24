package com.hehenian.biz.service.email;

import java.util.Map;

import com.hehenian.biz.common.base.result.IResult;

public interface IMailService {

	/**
	 * 发送邮件
	 * 
	 * @param toMail
	 *            eamil地址
	 * @param mailSubject
	 *            邮件主题
	 * @param mailTemplate
	 *            邮件模板
	 * @param params
	 *            邮件模板所需参数
	 * @param async
	 *            是否异步发送邮件
	 * @return
	 */
	IResult<?> sendMail(String toMail, String mailSubject, String mailTemplate,
			Map<String, Object> params, boolean async);
}
