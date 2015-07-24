<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>我的通知</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" />
<meta name="format-detection" content="telephone=no" />
<link href="${fileServerUrl }/app_res/css/elend/eLoan.css?v=${jsversion}"  rel="stylesheet">
</head>
<body>
	<header>
		<h1 class="tong-tit">我的通知</h1>
		<span class="tong-eve">管理</span>
	</header>
	<section style="padding-bottom:60px;">
		<c:forEach var="notifyDoList" items="${notifyDoList}" varStatus="status">
			<c:if test="${notifyDoList.sendFlag=='T'}"><article class="tong-list db_f"></c:if>
			<c:if test="${notifyDoList.sendFlag=='F'}"><article class="tong-list db_f new"></c:if>
				<div class="bf1">
					<a href="<c:url value='/app/elend/tongzhi2?messageId=${notifyDoList.messageId}'/>">
						<%-- <h4>${status.index+1}</h4> --%>
						<h4>${notifyDoList.subject}</h4>
						<time>${notifyDoList.createTimeString}</time>
						<p>${notifyDoList.message}</p>
					</a>
				</div>
				<div class="tong-sel">
					<span data-id="${notifyDoList.messageId}"></span>
				</div>
			</article>
		</c:forEach>
	</section>
	<footer>
		<div class="tong-btn db_f bs">
			<div class="bf1 p1"><span id="delete" style="border:1px solid #894c8d; background:#fff; color:#894c8d" class="apply">删除</span></div>
			<div class="bf1 p1"><span id="cancel" class="apply">取消</span></div>
		</div>
	</footer>
	<script src="${fileServerUrl }/app_res/js/libs/zepto.js?v=${jsversion}"></script>
	<script>
	$(function(){
		$(".tong-eve").bind('touchend', function(){
			var that = $(this);
			if(that.hasClass("all")){
				$(".tong-sel span").addClass("checked");
			} else {
				$(".tong-sel").show();
				that.addClass('all').text('全选');
				$(".tong-btn").css("height",'auto');
			}
		});

		$(".tong-sel span").live('touchend', function(){
			var that = $(this);
			if(that.hasClass("checked")){
				that.removeClass("checked");
			} else {
				that.addClass("checked");
			}
		});

		$("#cancel").bind('touchend', function(){
			 window.history.go(-1);
		});

		$("#delete").bind('touchend', function(){
			var selItem = $(".checked");
			var ids='';
			for(var i=0,len=selItem.length;i<len;i++){
				ids+=selItem.eq(i).attr("data-id");
				if(!(i==len-1)){
					ids+=";"
				}
			}
			$.get("tongzhiD",{'messageId':ids}, function(result){
			})
			selItem.parent().parent().remove();
		});
	})
	</script>
</body>
</html>