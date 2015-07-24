<%@ page contentType="text/html; charset=utf-8"%>
<!doctype html>
<html>
<head>
<%@ include file="../common/head.jsp" %>
<% request.setAttribute("menuIndex", 2); %>
	<title>${channel_name}-我的账户</title>
</head>
<body class="bg-2">
	<header class="top-bar bg-f">
	<input type="hidden" id="flag" value="${flag}"/>
		<a href="javascript:history.go(-1);">
			<span class="icon-back2">
			</span>
		</a>
		<p id="title_id">+薪宝</p>
	</header>
	<div class="cpro-bar">
		<p>待收收益</p>
		<p class="cpro-gain"><span>${interested}</span></p>
		<p>历史累计收益<span>${totalInterest}</span>元</p>
	</div>
	<div class="chold">
	<p>持有资产（元）<span>￥${principal}</span>
	</div>
	<section class="sign-area margin-none">
		<div id="goumai" class="sign-style br-3">
			<span class="sign-lable pr75">购买记录</span>
			<span class="sign-ctn">></span>
		</div>
		
		<!-- <div id="touzi" class="sign-style br-2"> -->
		<!--	<span class="sign-lable">投资记录</span> -->
		<!--	<span class="sign-ctn">></span> -->
		<!--</div> -->
		
		<c:if test="${flag!='duo' and flag!='che' }">
		<div id="touzi" class="sign-style br-2">
			<span class="sign-lable">投资记录</span>
			<span class="sign-ctn">></span>
		</div>
		</c:if>
		<c:if test="${flag=='che' }">
		<div id="cdqx" class="sign-style br-2">
			<span class="sign-lable">停车费冲抵期限</span>
			<span class="sign-ctn">></span>
		</div>
		</c:if>
		<c:if test="${flag=='duo' }">
		<div id="cdqx" class="sign-style br-2">
			<span class="sign-lable">物业费冲抵期限</span>
			<span class="sign-ctn">></span>
		</div>
		</c:if>
		<c:if test="${flag!='che' and  flag!='duo'}">
 		<div id="shuhui" class="sign-style br-4 bb-1"> 
			<span class="sign-lable">赎回余额</span> 
			<span class="sign-ctn">></span>
		</div> 
		</c:if>
		<c:if test="${flag=='che' or flag=='duo'}">
		<div id="cdAddress" class="sign-style br-2">
			<span class="sign-lable">冲抵地址</span>
			<span class="sign-ctn">></span>
		</div>
		</c:if>
	</section>
	
	<script>
		$(function(){
			var flag = $("#flag").val();
			var pid ;
			if(flag == "zu"){
				$("#title_id").text("+族宝");
			}else{
					
					if(flag == "duo"){
						$("#title_id").text("+多宝");
						$("#cdAddress").click(function(){
						location.href="http://m.hehenian.com/product/queryOffsetList.do?pid="+14;
					});
					$("#cdqx").click(function(){
						location.href="http://m.hehenian.com/profile/queryOffsetRecords.do?pid="+14;
					});
					}else if(flag == "che"){
						$("#title_id").text("+車宝");
						$("#cdAddress").click(function(){
						location.href="http://m.hehenian.com/product/queryOffsetList.do?pid="+15;
					});
					$("#cdqx").click(function(){
						location.href="http://m.hehenian.com/profile/queryOffsetRecords.do?pid="+15;
					});
						
					}
			}
			$("#goumai").click(function(){
				location.href="http://m.hehenian.com/profile/buy.do?flag="+flag+"&tab_f=g";
			});
			
			$("#touzi").click(function(){
				location.href="http://m.hehenian.com/profile/buy.do?flag="+flag+"&tab_f=t";
			});
			
			$("#shuhui").click(function(){
				location.href="";
			});
			
		});
	</script>
	<%@ include file="../common/tail.jsp" %>
</body>
</html>