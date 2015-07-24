<%@ page import="java.net.URLEncoder" %>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--<c:if test="${session.platform=='appcomm'||session.platform=='colorlifeapp'}">
<script>
    window.location.href="/webapp/message.do?title=${message}";
</script>
</c:if>--%>
<c:set value="${message}" var="message"></c:set>
<%
    Object platform = session.getAttribute("platform");
    Object message = request.getAttribute("message");
    System.out.println("--------platform:"+platform);
    if ("appcomm".equals(platform)||"colorlifeapp".equals(platform)){
        if (message!=null){
            response.sendRedirect("/webapp/message.do?title="+ URLEncoder.encode(message.toString(),"utf-8"));
        }else {
            response.sendRedirect("/webapp/message.do?title="+ URLEncoder.encode("未知","utf-8"));
        }
    }else if("colorlife_wyf_web".equals(platform)||"colorlife_wyf_app".equals(platform)){
        response.sendRedirect("/wyf/index.do?title="+URLEncoder.encode(message.toString(),"utf-8"));
    }else if("colorlifeweb".equals(platform)){
        response.sendRedirect("/cf/message.do?title="+URLEncoder.encode(message.toString(),"utf-8"));
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<style>
a:HOVER {
	color: #f07a05;
}
</style>
</head>
<body>
	<jsp:include page="/include/top.jsp"></jsp:include>
	<div class="center">
		<div class="wytz_center">
			<div
				style="text-align: center; margin-top: 130px; margin-bottom: 130px;">
				<span style="font-size: 24px; color: #eb6100;"> 
					<span id="result">
                       ${message}
                    </span>
                    <c:if test="${message=='恭喜您，投资成功。'}">
                        <br/>
                        <br/>
                        <font style="font-size: 18px;">注：为避免每月回款资金闲置，建议您设置<a href="/automaticBidInit.do" style="color: blue;">自动投标</a>，在您不需要的时候可随时手动关闭。
                        </font>
                    </c:if>
				</span>
				<br/><br/>
				<a href="index.do" style="color:#333; ">返回首页</a>
			</div>
		</div>
	</div>
	<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
</html>
