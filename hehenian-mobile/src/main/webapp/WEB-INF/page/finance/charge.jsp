<%@ page contentType="text/html; charset=utf-8"%>
<!doctype html>
<html>
	<head>
		<%@ include file="../common/head.jsp" %>
		<% request.setAttribute("menuIndex", 2); %>
		<script src="http://static.hehenian.com/m/js/finance/charge.js"></script>
		<title>${channel_name}业-账户充值</title>
	</head>
<body class="bg-2">
	<header class="top-bar">
		<a href="http://m.hehenian.com/balance/index.do">
			<span class="icon-back">
			</span>
		</a>
		<p>账户充值</p>
	</header>
	<c:choose>
    <c:when test="${not empty bankCard and not empty bankCard.bank_code}">
	<div class="bank-act" class="managerCard">
		<div class="icon-dcard">
		</div>
		<div class="bank-dname">
            <p>${bankCard.bank_name}</p>
            <p>尾号${bankCard.card_no}</p>
		</div>
		<div class="bank-dc">
			<a href="javascript:;">&gt;</a>
		</div>
	</div>
	</c:when>
	<c:otherwise>
	<div class="bank-act" title="bindCard" >
		<div class="icon-dcard">
		</div>
		<div class="bank-dname">
               <p><a href="javascript:;">绑定银行卡</a></p>
		</div>
		<input type="hidden" id="bank" value="${bankCard.card_no}"/>
		<div class="bank-dc">
			<a href="javascript:;">&gt;</a>
		</div>
	</div>
	</c:otherwise>
	</c:choose>
	<section class="sign-area margin-none">
			<div class="sign-style br-3">
				<span class="sign-lable pr23">金额</span>
				<input type="text" id="amount" name="amount" placeholder="输入充值金额" class="fr">
			</div>
			<div class="sign-style br-4 bb-1">
				<span class="sign-lable">手机验证码</span>
				<input type="text" id="verfiyCode" name="verfiyCode" placeholder="请输入短信验证码" class="kyt">
				<span class="sign-get-ck" id="ida">获取验证码</span>
			</div>
			<p><span></span>同意<a href="http://m.hehenian.com/balance/withholdingAgreement.do">《合和年代扣协议》</a></p>
			<div class="sign-sub">
				<input type="button" value="确认支付" id="subPayBtn">
			</div>
	</section>

	    <!--输入密码弹窗-->
    <section class="buy-pop hide" id="psdModal">
        <div class="in-code">
            <div class="ic-head">
                <p>请输入支付密码<a href="http://m.hehenian.com/account/resetPwdIndex.do?pwdFlag=pay">忘记密码</a>
                </p>
            </div>
            <div class="ic-form">
                <div>
                    <input type="password" class="ic-input" id="verifyCode" placeholder="请输入支付密码">
                </div> 
                <div class="ic-fun">
                    <span class="ic-cancel">取消</span>
                    <input type="submit" value="确定" class="ic-sub">
                </div>
            </div>
        </div>
    </section>

	<%@ include file="../common/tail.jsp" %>
	
	
	<script>
    
	</script>
</body>
</html>