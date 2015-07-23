/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.util
 * @Title: NotifyTemplateUtil.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年9月24日 下午4:33:28
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.util;

import java.io.File;
import java.io.StringWriter;
import java.net.URL;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 发送消息时构建模版的工具类
 * 
 * @author: zhangyunhmf
 * @date 2014年9月24日 下午4:33:28
 */
public class NotifyTemplateUtil {

	private static Configuration config = null;

	private static void initTemplate() throws Exception {
		config = new Configuration();
		URL templateURL = NotifyTemplateUtil.class.getResource("/template");
		String fileName = templateURL.toURI().getPath();
		File file = new File(fileName);
		config.setDirectoryForTemplateLoading(file);
		config.getTemplateLoader().findTemplateSource(
				"sms_template_default.ftl");
	}

	public static String getContentByTemplate(String templateName,
			Map messageObject) {

		try {

			if (null == config) {
				initTemplate();
			}
			// JSONObject json = JSONObject.fromObject(messageObject);
			Template tp = config.getTemplate(templateName);
			StringWriter out = new StringWriter();
			tp.process(messageObject, out);
			return out.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
