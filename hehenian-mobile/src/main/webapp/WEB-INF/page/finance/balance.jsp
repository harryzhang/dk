<%@ page contentType="text/html; charset=utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
	<head>
		<%@ include file="../common/head.jsp" %>
		<% request.setAttribute("menuIndex", 2); %>
		<script src="http://static.hehenian.com/m/js/finance/balance.js"></script>
		<title>${channel_name}-账户余额</title>
	</head>
<body class="bg-2">
	<header class="top-bar">
		<a href="http://m.hehenian.com/profile/index.do">
		<span class="icon-back">
			</span>
		</a>
		<p>可用余额</p>
	</header>
	<div class="io-bar">
	<div class="ib-top">
	<p><span>账户余额</span>￥${balanceAmount + withdrawalAmount}</p>
	</div>
	<div class="ib-bot">
	<c:if test="${sessionScope.user.accountType==1}">
		<span class="in-btn"><a href="#" onclick="accountTypeShow('2');">充值</a></span>
		<span class="out-btn"><a href="#" onclick="accountTypeShow('1');">提现</a></span>
    </c:if>
    <c:if test="${sessionScope.user.accountType!=1}">
	    <span id="recharge" class="in-btn"><a href="###">充值</a></span>
		<span id="withdraw" class="out-btn"><a href="###">提现</a></span>
    </c:if>
	
	</div>
	</div>
	<section class="sign-area margin-none">
			<div class="sign-style br-3" id="chargeDiv">
				<span class="sign-lable pr75">充值记录</span>
				<span class="sign-ctn">></span>
			</div>
			<div class="sign-style br-2" id="withdrawDiv">
			<span class="sign-lable">提现记录</span>
				<span class="sign-ctn">></span>
			</div>
			<c:if test="${withdrawalAmount gt 0}">
				<div class="sign-style3 br-5 bb-1">
					<span class="sign-lable">汇付余额</span>
					<span class="sign-ctn">￥${withdrawalAmount}</span>
					<p>温馨提示：仅用于项目投资、彩富人生产品，手机暂不支持汇付提现，请登录www.hehenian.com的会员中心进行操作。 </p>
				</div>
			</c:if>
			
	</section>
	
	
			<!--未经实名验证-->
	<section class="buy-pop hide" id="authModal">
		<div class="none-auth">
			<div class="na-head">
				重要提示 <span class="close"></span>
			</div>
			<div class="na-tips">
				<p>您尚未完成实名认证，无法进行充值/提现。</p>
				<a href="http://m.hehenian.com/account/realAuth.do"> <span
					class="na-btn">立即认证</span>
				</a>
			</div>
		</div>
	</section>


	<!-- 未设置支付密码 -->
	<section class="buy-pop hide" id="resetPwdModal">
		<div class="none-auth">
			<div class="na-head">
				重要提示 <span class="close"></span>
			</div>
			<div class="na-tips">
				<p>您尚未设置支付密码，无法进行充值/提现。</p>
				<a href="http://m.hehenian.com/account/resetPwdIndex.do?pwdFlag=pay">
					<span class="na-btn">立即设置</span>
				</a>
			</div>
		</div>
	</section>
	
	
	<!-- 未绑卡 -->
	<section class="buy-pop hide" id="bindCardModal">
		<div class="none-auth">
			<div class="na-head">
				重要提示 <span class="close"></span>
			</div>
			<div class="na-tips">
				<p>您尚未绑定银行卡，无法进行充值/提现。</p>
				<a href="http://m.hehenian.com/finance/bindCard.do"> <span
					class="na-btn">立即绑卡</span>
				</a>
			</div>
		</div>
	</section>
	
	
	<%@ include file="../common/tail.jsp" %>
</body>
</html>
 <script type="text/javascript">
 
	//认证弹框
	var authModal = new Modal({
		id : '#authModal',
		events : {
			'.close click' : function(modal) {
				modal.close();
			}
		}
	});

	//设置支付密码
	var resetPwdModal = new Modal({
		id : '#resetPwdModal',
		events : {
			'.close click' : function(modal) {
				modal.close();
			}
		}
	});
	
	//绑卡
	var bindCardModal = new Modal({
		id : '#bindCardModal',
		events : {
			'.close click' : function(modal) {
				modal.close();
			}
		}
	});
	
		$(function(){
				$("#recharge").click(function(){
					chekp("http://m.hehenian.com/balance/chargePage.do");
				});
				
				$("#withdraw").click(function(){
					chekp("http://m.hehenian.com/balance/withdrawPage.do");
				});
			});
						
			 function chekp(_url){
				 var options = {type:"POST",url:"http://m.hehenian.com/finance/setUp.do"};
					ajaxRequest(options,function(data){
						if(data.returnCode==5){
							//实名认证
							authModal.show();
						}else if(data.returnCode==6){
							//支付密码
							resetPwdModal.show();
						}else if(data.returnCode==7){
							//绑卡
							bindCardModal.show();
						}else{
							//location.href="http://m.hehenian.com/balance/chargePage.do";
							location.href=_url;
						}
					});
			 }
 
 function accountTypeShow(type){
 	if('1'==type){
 		popWindow("请发送提现申请邮件进行提现操作");
 	}else if('2'==type){
 		popWindow("无需充值");
 	}else{
 		popWindow("无需绑定银行卡");
 	}
   return;
 }
 </script>