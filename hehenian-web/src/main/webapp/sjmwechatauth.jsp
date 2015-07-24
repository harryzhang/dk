<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="com.hehenian.web.common.util.HttpClientUtils"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.hehenian.biz.common.base.result.IResult"%>
<%@ page import="net.sf.json.JSONObject" %>
<html>
<head>
<title>网页授权获取用户基本信息</title>
</head>
<body>
	<%

        String wenxinToken;
        String appid = "wx0f32ba344f39a5aa";
        String secret = "ea4cec6f594215471966efe9dd8cbc21";

        String code = request.getParameter("code");
        if (StringUtils.isBlank(code)) {
            out.print("授权失败!");

        }else {
            String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+secret+"&code="
                    + code + "&grant_type=authorization_code";
            IResult<?> result = HttpClientUtils.post(url, new HashMap<String, String>());
            JSONObject jsonObject = JSONObject.fromObject(result.getModel());
            if (jsonObject.containsKey("openid")){
                String openid = jsonObject.getString("openid");
                System.out.println("openid:"+openid);
                out.print("Code:" + code);
                out.print("授权成功，微信返回的数据:" + result.getModel());
               // response.sendRedirect("http://183.11.217.181:8000/weixin-sjm.do?_sourcefrom_=18&wxToken="+openid);
			    response.sendRedirect("http://www.hehenian.com/weixin-sjm.do?_sourcefrom_=18&wxToken="+openid);
            }else{
                out.print("授权错误" );
            }

//            response.sendRedirect("http://10.111.2.78/weixin-sjm.do?_sourcefrom_=18&wxToken="+openid);
//            wenxinToken=openid;

        }
    %>
</body>

</html>