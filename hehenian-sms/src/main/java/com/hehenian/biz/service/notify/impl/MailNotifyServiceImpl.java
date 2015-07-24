/**
 * @ Project : p2pt notify
 * @ File Name : MailNotify.java
 * @ Date : 2014/8/20
 * @ Author : harry.zhang
 */

package com.hehenian.biz.service.notify.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.hehenian.biz.common.notify.dataobject.MailNotifyDo;
import com.hehenian.biz.common.notify.dataobject.NotifyDo;
import com.hehenian.biz.common.util.JsonUtil;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 邮件发送 ，包可见，不让外部直接调用
 * @author zhangyunhmf
 *
 */
class MailNotifyServiceImpl extends NotifyServiceImpl {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	private final static String DEFAULT_TEMPLATE="mail_template_default.ftl";
	private JavaMailSenderImpl mailSender;
	private TaskExecutor taskExecutor;	
	private FreeMarkerConfigurationFactory freeMarkerConfigurer;	
	
	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public void setFreeMarkerConfigurer(
			FreeMarkerConfigurationFactory freeMarkerConfigurer) {
		this.freeMarkerConfigurer = freeMarkerConfigurer;
	}

	/**
	 * 发送邮件
	 * 
	 */
    public boolean send(final NotifyDo message) {
		
		if(message.isAsync()){
			taskExecutor.execute(new Runnable(){
				public void run() {
					sendMail(message);
				}					
			});
            return true;
		}else{			
			//发送
			return sendMail(message);
		}
	}
	
	/**
	 * 发送邮件
	 * 
	 */
    public boolean sendMail(NotifyDo message) {
		
		try {
			final MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
			helper.setFrom(mailSender.getUsername(), "合和年在线");
			if(message.getRecievers().contains(";")){
				String[] recieverArray = message.getRecievers().split(";");
				helper.setTo(recieverArray);
			}else if(message.getRecievers().contains(",")){
				String[] recieverArray = message.getRecievers().split(",");
				helper.setTo(recieverArray);
			}else{
				helper.setTo(message.getRecievers());
			}
			
			helper.setSubject(message.getSubject());
			helper.setSentDate(new Date());
			if(message.getCcList() != null && !("null".equals(message.getCcList()))&& !("".equals(message.getCcList()))){
				if( message.getCcList().contains(";")){
					String[] ccArray = message.getCcList().split(";");
					helper.setCc(ccArray);
				}else if(message.getCcList().contains(",")){
					String[] ccArray = message.getCcList().split(",");
					helper.setCc(ccArray);
				}else{
					helper.setCc(message.getCcList());
				}
			}
			
			
			String messageTemplate = message.getMessageTemplate();
			String htmlText = null;
			if(null == messageTemplate || "".equals(messageTemplate)){
				messageTemplate=DEFAULT_TEMPLATE;
			}
			Template tpl = freeMarkerConfigurer.createConfiguration().getTemplate(messageTemplate);
			Map messageMap= (Map) JsonUtil.json2Bean(message.getMessage(), Map.class);
			htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, messageMap);
			if(null == htmlText){
				htmlText= message.getMessage().toString();
			}
			
			helper.setText(htmlText, true);		
			
			if(message.getFilePath() != null) {
				sendContext(msg,message);
			}
			
			//发送
			mailSender.send(msg);
			
            return true;
			
		} catch (NullPointerException e) {
			logger.error(e.getMessage(), e);
		} catch (MessagingException e) {
			logger.error(e.getMessage(), e);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (TemplateException e) {
			logger.error(e.getMessage(), e);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
        return false;
	}
    
    protected void sendContext(MimeMessage msg,NotifyDo message) throws MessagingException {
    	// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
		Multipart multipart = new MimeMultipart();

		// 设置邮件的文本内容
		BodyPart contentPart = new MimeBodyPart();
		contentPart.setText(message.MESSAGE_CONTEXT);
		multipart.addBodyPart(contentPart);
		// 添加附件
		BodyPart messageBodyPart = new MimeBodyPart();
		DataSource source = new FileDataSource(message.getFilePath());
		// 添加附件的内容
		messageBodyPart.setDataHandler(new DataHandler(source));
		// 添加附件的标题
		// 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
		sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
		messageBodyPart.setFileName("=?UTF-8?B?" + enc.encode((message.getFileName()+".xls").getBytes()) + "?=");
		multipart.addBodyPart(messageBodyPart);

		msg.setContent(multipart);
		msg.saveChanges();
    }

}
