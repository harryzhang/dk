package com.sp2p.service;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.sp2p.constants.IConstants;
import com.sp2p.service.admin.GetMailMsgOnUpService;

public class MailSendService {
	private static Log log = LogFactory.getLog(MailSendService.class);
	private static boolean isFlag = false;
	private static String EmailNick = IConstants.NICK;
	private GetMailMsgOnUpService getMailMsgOnUpService;

	private JavaMailSenderImpl sender;

	private ExecutorService pool = Executors.newFixedThreadPool(10);

	private ThreadPoolTaskExecutor taskExecutor;

	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public static void setIsFlag(boolean flag) {
		isFlag = flag;
	}

	private void setProperty() throws SQLException {
			log.info("start query mailSet........");
			Map<String, String> mailSet = getMailMsgOnUpService.getMailSet();
			if (mailSet != null) {
				try {
				IConstants.MAIL_HOST = mailSet.get("host");
				IConstants.MAIL_USERNAME = mailSet.get("mailaddress");
				IConstants.MAIL_PASSWORD = mailSet.get("mailpassword");
				IConstants.MAIL_FROM = mailSet.get("sendmail");
				IConstants.NICK =javax.mail.internet.MimeUtility.encodeText(mailSet.get("sendname"));
				} catch (UnsupportedEncodingException e) {
					log.error(e);
					e.printStackTrace();
				} ;
			}
			
			
			sender.setHost(IConstants.MAIL_HOST.trim());
			sender.setUsername(IConstants.MAIL_USERNAME.trim());
			sender.setPassword(IConstants.MAIL_PASSWORD.trim());
			isFlag = true;
			log.info("end query mailSet........");
	}
	/**
	 * 发送邮件
	 */
	public MailSendService() {
		sender = new JavaMailSenderImpl();
		sender.setHost(IConstants.MAIL_HOST.trim());
		sender.setUsername(IConstants.MAIL_USERNAME.trim());
		sender.setPassword(IConstants.MAIL_PASSWORD.trim());
		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.auth", "true");
		javaMailProperties.put("mail.smtp.timeout", "25000");

		sender.setJavaMailProperties(javaMailProperties);

	}
	/**
	 * 发送验证邮件
	 * @param userName
	 * @param email
	 * @param verificationUrl
	 */
	public void sendRegEmail(final String userName, final String email,
			final String verificationUrl) {
		try {
			setProperty();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		pool.execute(new Runnable() {
			@Override
			public void run() {
				log.info("1=== start send email....");
				sender = new JavaMailSenderImpl();
				sender.setHost(IConstants.MAIL_HOST.trim());
				sender.setUsername(IConstants.MAIL_USERNAME.trim());
				sender.setPassword(IConstants.MAIL_PASSWORD.trim());
				Properties javaMailProperties = new Properties();
				javaMailProperties.put("mail.smtp.auth", "true");
				javaMailProperties.put("mail.smtp.timeout", "25000");

				sender.setJavaMailProperties(javaMailProperties);
				
				MimeMessage mms = sender.createMimeMessage();
				try {

					MimeMessageHelper helper = new MimeMessageHelper(mms, true,
							"utf-8");
					//String nick = javax.mail.internet.MimeUtility.encodeText(IConstants.NICK);
					//helper.setFrom(IConstants.NICK+"<"+IConstants.MAIL_FROM+">");
					//helper.setFrom(IConstants.MAIL_FROM);
					helper.setFrom(new InternetAddress(IConstants.MAIL_FROM,IConstants.NICK));
					helper.setTo(email);
					helper.setSubject("桂林市合和年信贷激活邮件通知");
					StringBuffer buf = new StringBuffer();
					buf.append("<HTML><BODY style='border-width:0px'>");
					buf
							.append("<H4 style='font-weight:normal;font-size:14px'>尊敬的<span>");
					buf.append(userName);
					buf.append("</span>:</H4>");
					buf.append("<BR/>");
					buf.append("<DIV>您的桂林市合和年信贷账号已经成功创建，请点击此链接激活账号：</DIV>");
					buf.append("<BR/>");
					buf.append("<DIV><a href='");
					buf.append(verificationUrl);
					buf.append("'>");
					buf.append(verificationUrl);
					buf.append("</a></DIV>");
					buf.append("</BODY></HTML>");
					helper.setText(buf.toString(), true);
					sender.send(mms);
					log.info("2=== end  email.");
				} catch (MessagingException e) {
					log.error(e);
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

			}

		});

	}

	public void setGetMailMsgOnUpService(
			GetMailMsgOnUpService getMailMsgOnUpService) {
		this.getMailMsgOnUpService = getMailMsgOnUpService;
	}

}
