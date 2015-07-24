<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="include/top.jsp"%>
		<title>成功</title>
	</head>
<body>
    <form action="<c:url value='saveCredit'/>" method="post">
	<article class="xin xin2">
		<div class="selbox bs">
			<p>
				<select name="ownerType" id="ownerType">
					<option value="0">业主类别</option>
					<option value="26000">有房</option>
					<option value="5000">无房</option>
				</select>	
			</p>
			<p>
			<select name="breakfast" id="breakfast">
				<option value="0">日常早餐</option>
					<option value="10">馒头</option>
					<option value="1000">茶叶蛋</option>
					<option value="2000">西点</option>
					<option value="5000">餐厅早餐</option>
				</select>	
			</p>
			<p>
			<select name="duty" id="duty">
				<option value="0">职业</option>
					<option value="10000">老板</option>
					<option value="8000">金领</option>
					<option value="3000">白领</option>
					<option value="3000">蓝领</option>
					<option value="1000">屌丝</option>
				</select>	
			</p>
			<p style="padding:10px;">
			<select name="education" id="education">
				<option value="0">学历</option>
				<option value="8000">海龟</option>
				<option value="4000">硕士</option>
				<option value="3000">本科</option>
				<option value="2000">专科</option>
				<option value="1000">初中</option>
			</select>	
			</p>
			<p>
			<select name="marriage" id="marriage">
				<option value="0">婚姻</option>
				<option value="5000">围城外</option>
				<option value="5000">围城中</option>
				<option value="5000">出城了</option>
			</select>	
			</p>
			<p style="padding:10px;">
			<select name="creditCard" id="creditCard">
				<option value="0">信用卡</option>
				<option value="5000">无</option>
				<option value="18000">五万以上</option>
				<option value="5000">五万以下</option>
			</select>
			</p>
			<p style="padding:10px;">
			<select name="traveling" id="traveling">
				<option value="0">常去的旅游地点</option>
				<option value="1000">室内游</option>
				<option value="2000">省内游</option>
				<option value="3000">国内游</option>
				<option value="8000">出国游</option>
			</select>	
			</p>
			<p>
			<select name="car" id="car">
				<option value="0">我的爱车</option>
				<option value="2000">两个轮子</option>
				<option value="8000">四个轮子</option>
			</select>	
			</p>
		</div>
		<a class="xin-btn xinbtn2" href="#" onclick="javascirt:submitCredit();"></a>
	</article>
	</form>
</body>
</html>
<script type="text/javascript">
	function submitCredit(){
		document.forms[0].submit();
	}
	
</script>