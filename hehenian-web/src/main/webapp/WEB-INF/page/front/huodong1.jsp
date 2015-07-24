<%@page import="java.text.DecimalFormat"%>
<%@ page language="java" import="java.sql.*" pageEncoding="UTF-8" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.*" %>
<%@ page import="com.sp2p.entity.*" %>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>
<a href="#" onclick="chou();" style="margin-left: 400px;">xxxxxxx</a><br/>
<a href="#" onclick="sign();" style="margin-left: 400px;">yyyyyyy</a><br/>
<s:iterator value="#request.records" var="var">
${var }<br/>
</s:iterator>
----------------<br/>
${userinfo }

<script>
function chou(){
	$.post("luckdraw.do",{activityId:1},function(data){
		alert(data.ret+"--"+data.awardId);
	});
}
function sign(){
	$.post("sign.do",{activityId:1},function(data){
		alert(data);
	});
}
</script>
</body>
</html>





