<%@ page contentType="text/html; charset=utf-8"%>
<!doctype html>
<html>
<head>
<%@ include file="../common/head.jsp" %>
<% request.setAttribute("menuIndex", 2); %>
	<title>${channel_name}</title>
</head>
<body class="bg-2">
	<header class="top-bar">
		<a href="javascript:history.go(-1);">
			<span class="icon-back"></span>
		</a>
		<p>冲抵车辆管理</p>
	</header>
	<section class="sign-area">
		<form action="http://m.hehenian.com/finance/prepayCB.do?pid=${pid }" method="post" id="offsetForm">
		<input type="hidden" value="${pid} " id="pid">
		<c:forEach var="row" items="${offsetList}">
			<div class="sign-style br-3">
				<c:choose>
					<c:when test="${row.defaultset==1 }">
					<p class="cb-p">${row.mainaddressid}（车牌号 ${row.plate_number}）
					<i class="icon-chosed">√</i>
					</c:when>
				    <c:when test="${row.defaultset==0 }">
				    <p class="cb-p">${row.mainaddressid}（车牌号 ${row.plate_number}）
				    <a href="javascript:;" style="color:#08b2e6;" onclick="toDefault('${row.plate_number}');">设为默认</a>
				    </c:when>
				</c:choose>
				
			</div>
		</c:forEach>
			<div class="sign-style br-5 bb-1">
				<p id="addOffset" class="cb-p">添加冲抵车辆<a class="sign-ctn">&gt;</a></p>
			</div>
			 <input type ="button" value="继续购买" id="goBuy">
			<p class="bank-tips"></p>
			
		</form>
	</section>
	<script>
	    
		$(function(){
			//添加冲抵车辆
				$("#addOffset").click(function(){
						location.href="http://m.hehenian.com/product/setOffset.do?from=1&pid="+${pid};
				});
				
				$("#goBuy").click(function(){
						$("#offsetForm").submit();
				});
		});
		
		function toDefault(plateNo){
			 $.post('http://m.hehenian.com/product/changeDefaultOffset.do',{plateNo:plateNo},function(data){
				 if(data.returnCode==1){
				 	popWindow(data.messageInfo);
				 }else{
				    window.location.href= "${referer}";
				 }
             },"json");
		}
	</script>
	<%@ include file="../common/tail.jsp" %>
</body>
</html>