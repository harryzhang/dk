package com.hehenian.common.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * freemarker生成html公共类
 * 
 * @author CalvinStyle
 * 
 */
public class FreeMarkerUtils {
	private static Configuration cfg;

	private static Configuration getCfg() {
		if (cfg == null) {
			//cfg = new Configuration();
			cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
			cfg.setDefaultEncoding("UTF-8");
			//cfg.setClassForTemplateLoading(Thread.currentThread().getClass(), "templates");
			cfg.setClassLoaderForTemplateLoading(Thread.currentThread().getContextClassLoader(), "templates");
			cfg.setNumberFormat("0.######");
			cfg.setTemplateUpdateDelay(6000);
		}
		return cfg;
	}

	public static String getHtml(Map<String, Object> paramMap, String filename) {
		return getHtml(paramMap, filename, "utf-8");
	}

	public static String getHtml(Map<String, Object> paramMap, String filename,
			String charset) {
		Configuration cfg = getCfg();
		Template t;
		try {
			t = cfg.getTemplate(filename, charset);
			Writer out = new StringWriter();
			t.process(paramMap, out);
			return out.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}

		return null;
	}
}
