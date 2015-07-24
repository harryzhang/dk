<%@ page import="java.net.URLEncoder" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/include/head.jsp"></jsp:include>
    <s:set value="#message" var="message"></s:set>
    <%
        Object message = request.getAttribute("title");
        Object platform = session.getAttribute("platform");
        System.out.println("--------platform:"+platform);
        if ("appcomm".equals(platform)||"colorlifeapp".equals(platform)){

            if (message!=null){
                response.sendRedirect("/webapp/message.do?title="+ URLEncoder.encode(message.toString(), "utf-8"));
            }else {
                response.sendRedirect("/webapp/message.do?title="+ URLEncoder.encode("未知","utf-8"));
            }

        }else if("colorlife_wyf_web".equals(platform)||"colorlife_wyf_app".equals(platform)){
            response.sendRedirect("/wyf/index.do?title="+URLEncoder.encode(message.toString(),"utf-8"));
        }else if(session.getAttribute("user")==null){
            response.sendRedirect("http://hehenian.colourlife.com/message.do?title="+ URLEncoder.encode(message==null?"成功":message.toString(),"utf-8"));
        }
    %>
<style>
a:HOVER {
	color: #f07a05;
}
</style>
</head>

<s:if test="#session.user==null">
<script>
if(window.opener){
	alert("${request.title }");
	window.close();
}else{

}
</script>
</s:if>

<body>
	<jsp:include page="/include/top.jsp"></jsp:include>
	<div class="center">
		<div class="wytz_center">
			<!--提示消息主要 开始-->
			<div style="text-align: center;margin-top: 130px;margin-bottom: 130px;">
				<span style="font-size: 24px;color:#eb6100;">
				<span id="result"></span>
				<s:if test="#request.title=='投标成功'">
				<br/>
				<br/>
				<font style="font-size: 18px;">注：为避免每月回款资金闲置，建议您设置<a href="/automaticBidInit.do" style="color: blue;">自动投标</a>，在您不需要的时候可随时手动关闭。
				</font>
				</s:if>
				 </span> <br /> <br /> 
				<a href="index.do" style="color:#333; ">返回首页</a>
			</div>
		</div>
	</div>
	<div id="datas" style="display: none;">${request.title }</div>
	<!--底部快捷导航 开始-->
	<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
<script>
$(function(){
	var url = window.location+"";
	url = decodeURI(url);
	var start = url.indexOf("title=") + "title=".length;
	var ret = url.substring(start);
	if(start<6){
		ret = $("#datas").html();
		if(ret	== undefined || ret =='' || ret == null){
			ret = "未知状态";
		}
	}
	$("#result").html(ret);
})
</script>


</html>
