package com.hehenian.web.common.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.sp2p.constants.IConstants;

public class ServletUtils {
    private static final Logger logger                 = Logger.getLogger(ServletUtils.class);
    public static final String  RESP_CONTTYPE_HTMLTEXT = "text/html; charset=UTF-8";          // 文本
    public static final String  RESP_CONTTYPE_JSON     = "text/x-json;charset=UTF-8";         // json

    /**
     * 获取客户端IP地址
     * 
     * @return
     */
    public static String getClientIp() {
        HttpServletRequest request = ServletActionContext.getRequest();
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }

    /**
     * 将表单数据写入响应流
     * 
     * @param htmlText
     */
    public static void write(String htmlText) {
        write(htmlText, RESP_CONTTYPE_HTMLTEXT);
    }

    public static void writeJson(String htmlText) {
        write(htmlText, RESP_CONTTYPE_JSON);
    }

    /**
     * 将数据写入响应流
     * 
     * @param htmlText
     */
    public static void write(String content, String contentType) {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("UTF-8");
        response.setContentType(contentType);
        PrintWriter out;
        try {
            out = response.getWriter();
            out.print(content);
            out.flush();
            out.close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 将数据写入响应流
     * 
     * @param htmlText
     */
    public static void write(String content, String contentType, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(contentType);
        PrintWriter out;
        try {
            out = response.getWriter();
            out.print(content);
            out.flush();
            out.close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 打印参数日志
     * 
     * @author: liuzgmf
     * @throws UnsupportedEncodingException
     * @date: 2014年11月3日下午4:17:18
     */
    public static void logRequestParameters() throws UnsupportedEncodingException {
        logger.info("请求回调参数：" + getRequestParameters());
    }

    /**
     * 获取参数日志
     * 
     * @author: liuzgmf
     * @throws UnsupportedEncodingException
     * @date: 2014年11月3日下午4:17:18
     */
    public static Map<String, Object> getRequestParameters() throws UnsupportedEncodingException {
        HttpServletRequest request = ServletActionContext.getRequest();
        Enumeration<String> enumeration = request.getParameterNames();
        Map<String, Object> params = new HashMap<String, Object>();
        while (enumeration.hasMoreElements()) {
            String parameterName = enumeration.nextElement();
            if (parameterName.equals("RespDesc") || parameterName.equals("RetUrl") || parameterName.equals("BgRetUrl")) {
                params.put(parameterName, URLDecoder.decode(request.getParameter(parameterName), "UTF-8"));
            } else {
                params.put(parameterName, request.getParameter(parameterName));
            }
        }
        return params;
    }

    public static String getBasePath() {
        return getWebPath();

    }

    /**
     * @MethodName: getWebPath
     * @Param: WebUtil
     * @Author: gang.lv
     * @Date: 2013-5-12 下午10:57:47
     * @Return:
     * @Descb: 获取web路径
     * @Throws:
     */
    public static String getWebPath() {
        return IConstants.WEB_URL;/*
                                   * System.getProperty("web.root",
                                   * IConstants.WEB_URL);
                                   */
    }

    /**
     * 过滤SQL注入
     * 
     * @param input
     * @return
     * @author: zhangyunhmf
     * @date: 2014年9月23日上午11:17:08
     */
    public static String FilteSqlInfusion(String input) {
        if ((input == null) || (input.trim() == "")) {
            return "";
        }
        if (!StringUtils.isNumeric(input)) {
            return input.replace("'", "’").replace("update", "ｕｐｄａｔｅ").replace("drop", "ｄｒｏｐ")
                    .replace("delete", "ｄｅｌｅｔｅ").replace("exec", "ｅｘｅｃ").replace("create", "ｃｒｅａｔｅ")
                    .replace("execute", "ｅｘｅｃｕｔｅ").replace("where", "ｗｈｅｒｅ").replace("truncate", "ｔｒｕｎｃａｔｅ")
                    .replace("insert", "ｉｎｓｅｒｔ");
        } else {
            return input;
        }
    }

}
