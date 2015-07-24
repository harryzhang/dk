<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<%
DateFormat dateFormat=new SimpleDateFormat("yyyyMMdd");
Date date1=dateFormat.parse("20140901");
Date date2=dateFormat.parse("20140905");
Date date3=dateFormat.parse(dateFormat.format(new Date()));
if(!(date3.before(date1)||date3.after(date2))){
	response.sendRedirect("huodong.do?activityId=1");
}else{
	response.sendRedirect("index.do");
}
%>