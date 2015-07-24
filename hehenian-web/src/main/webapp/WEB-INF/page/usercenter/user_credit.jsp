<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
<jsp:include page="/include/head.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/include/top.jsp"></jsp:include>
	<div class="nymain">

		<!-- 个人信息div -->
		<div class="wdzh">
			<div class="r_main" style="border:none; width:950px">
				<div class="box">
					<h2 style="font-size:16px">个人信息</h2>
					<div class="box-main">
						<div style="overflow:hidden; height:100%;">
							<div class="pic_info">
								<div class="pic">
									<shove:img defaulImg="images/default-img.jpg" src="${userMsg.personalHead}" width="128" height="128"></shove:img>
								</div>
								<div class="guanzhu">
									<a id="focusonUser" href="javascript:void(0);"> 关注此用户</a>
								</div>
								<div class="znx">
									<a href="javascript:void(0);" id="sendmail">发送站内信</a>
								</div>
							</div>
							<div class="xx_info">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<th align="right">用户名：</th>
										<td>${userMsg.username } <s:if test="#request.userVipPicture.vipStatus==2">
											</s:if></td>
									</tr>
									<tr>
										<th align="right">注册时间：</th>
										<td>${userMsg.createTime }</td>
									</tr>
									<tr>
										<th align="right">籍  贯：</th>
										<td>${userMsg.nativePro }&nbsp;&nbsp;${userMsg.nativeCity }</td>
									</tr>
									<tr>
										<th align="right">居住地：</th>
										<td>${userMsg.regProregionName }&nbsp;&nbsp;${userMsg.regCityregionName }</td>
									</tr>
								</table>
							</div>
							<div class="hy_info" style=" float:left;">
								<p style="padding:0">
									最后登录： <span>${userMsg.lastDate }</span>
								</p>
								<p style="padding:0">个人统计：${BorrowRecode.publisher }条借款记录，${inverseRecorde.investor }条投标记录</p>
								<p>&nbsp;</p>
								<a href="userCridit.do?id=${userMsg.id }" class="scbtn">信用报告</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<!-- 信用认证div -->
		<div class="boxmain2" id="info1s">
			<div class="biaoge" style="margin-top:0px;" id="info1"></div>
		</div>

	</div>
	<!-- 引用底部公共部分 -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
	<script type="text/javascript" src="css/popom.js"></script>
	<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
	<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
	<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
	<script>
		$(function() {
			$.post("queryUserIntegral.do", null, function(data) {
				$("#info1").html(data);
			});

			$('#focusonUser').click(function() {
				var param = {};
				param['paramMap.id'] = '${userMsg.id }';
				$.post('focusonUser.do', param, function(data) {
					var callBack = data.msg;
					alert(callBack);
				});
			});

			$('#sendmail').click(function() {
				var param = {};
				var id = '${userMsg.id }';
				var username = '${userMsg.username }';
				var url = "mailInit.do?id=" + id + "&username=" + username;
				$.post('mailInit.do', param, function(data) {
					$.jBox("iframe:" + url, {
						title : "发送站内信",
						width : 500,
						height : 400,
						buttons : {}
					});
				});
			});
		});
	</script>
</body>
</html>
