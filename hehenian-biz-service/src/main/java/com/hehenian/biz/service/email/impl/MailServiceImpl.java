package com.hehenian.biz.service.email.impl;

import java.util.Date;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.base.result.ResultSupport;
import com.hehenian.biz.service.email.IMailService;

import freemarker.template.Template;

public class MailServiceImpl implements IMailService {
	private final Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private TaskExecutor taskExecutor;
	@Autowired
	private JavaMailSenderImpl mailSender;
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@Override
	public IResult<?> sendMail(final String toMail, final String mailSubject,
			final String mailTemplate, final Map<String, Object> params,
			boolean async) {
		IResult<Integer> result = new ResultSupport<Integer>();
		result.setSuccess(true);
		if (async) {// 异步发送邮件
			taskExecutor.execute(new Runnable() {
				@Override
				public void run() {
					sendMail(toMail, mailSubject, mailTemplate, params);
				}
			});
		} else {
			boolean success = sendMail(toMail, mailSubject, mailTemplate,
					params);
			result.setSuccess(success);
		}
		return result;
	}

	/**
	 * 发送邮件
	 * 
	 * @param toMail
	 * @param mailSubject
	 * @param mailTemplate
	 * @param params
	 * @return
	 */
	public boolean sendMail(String toMail, String mailSubject,
			String mailTemplate, Map<String, Object> params) {
		try {
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
			helper.setFrom(mailSender.getUsername(), "合和年在线");
			helper.setTo(toMail);
			helper.setSubject(mailSubject);
			helper.setSentDate(new Date());

			Template tpl = freeMarkerConfigurer.getConfiguration().getTemplate(
					mailTemplate);
			String htmlText = FreeMarkerTemplateUtils
					.processTemplateIntoString(tpl, params);
			helper.setText(htmlText, true);
			mailSender.send(msg);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

}
