<%@ page contentType="text/html; charset=utf-8"%>
<!doctype html>
<html>
<head>
<script src="http://static.hehenian.com/m/js/jquery-1.11.1.min.js"></script>
	<script src="http://static.hehenian.com/m/js/common.js"></script>
<%@ include file="../common/head.jsp" %>
<% request.setAttribute("menuIndex", 2); %>
	<title>${channel_name}</title>
</head>
<body class="bg-2">
	<header class="top-bar">
		<a href="javascript:history.go(-1);">
			<span class="icon-back"></span>
		</a>
		<p>银行卡管理</p>
	</header>
	<section class="sign-area">
		<form>
		<c:forEach var="row" items="${cardList}">
			<div class="sign-style br-3">
				<c:choose>
					<c:when test="${row.bank_status==2 }"><p class="cb-p">${row.bank_name}（尾号 ${row.subCardNo}）<a href="http://m.hehenian.com/finance/moneyVerify.do?bankCode=${row.bank_code }&userAccount=${row.card_no}">继续验证</a></c:when>
					<c:when test="${row.bank_status==3 }">
					<p class="cb-p">${row.bank_name}（尾号 ${row.subCardNo}）
					<i class="icon-chosed">√</i>
					</c:when>
				    <c:when test="${row.bank_status==6 }"><p class="cb-p">${row.bank_name}（尾号 ${row.subCardNo}）<a href="javascript:;" style="color:#08b2e6;" onclick="toDefault('${row.bank_card_id}');">设为默认</a></c:when>
				</c:choose>
				
			</div>
		</c:forEach>
			<div class="sign-style br-5 bb-1">
				<p id="addCard" class="cb-p">添加银行卡<a class="sign-ctn">&gt;</a></p>
			</div>
			<p class="bank-tips">目前购买+理财产品可使用<br />
			中国银行，建设银行，农业银行，中国邮政银行，招商银行，中信银行，华夏银行，平安银行，浦发银行</p>
		</form>
	</section>
	
		<!--未经实名验证-->
	<section class="buy-pop hide" id="authModal">
		<div class="none-auth">
			<div class="na-head">
				重要提示 <span class="close"></span>
			</div>
			<div class="na-tips">
				<p>您尚未完成实名认证，无法进行绑卡。</p>
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
				<p>您尚未设置支付密码，无法进行绑卡。</p>
				<a href="http://m.hehenian.com/account/resetPwdIndex.do?pwdFlag=pay">
					<span class="na-btn">立即设置</span>
				</a>
			</div>
		</div>
	</section>
	<script>
	    var accountType = "${sessionScope.user.accountType}";
	    
	    
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
		
		
		$(function(){
			//添加银行卡
				$("#addCard").click(function(){
					if(accountType == '1'){
						popWindow("无需绑定银行卡");
					}else{
						var options = {type:"POST",url:"http://m.hehenian.com/finance/setUp.do"};
						ajaxRequest(options,function(data){
							if(data.returnCode==5){
								//实名认证
								authModal.show();
							}else if(data.returnCode==6){
								//支付密码
								resetPwdModal.show();
							}else{
								location.href="http://m.hehenian.com/finance/bindCard.do";
							}
						});
					}
				});
		});
		
		function toDefault(bankId){
			 $.post('http://m.hehenian.com/profile/changeBankCard.do',{bankCardId:bankId},function(data){
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