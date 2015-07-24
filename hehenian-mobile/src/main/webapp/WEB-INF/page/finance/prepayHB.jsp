<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html>
<head>
<%@ include file="../common/head.jsp" %>
	<script src="http://static.hehenian.com/m/js/jquery-1.11.1.min.js"></script>
	<script src="http://static.hehenian.com/m/js/common.js"></script>
	<title>${channel_name}-购买-${result.product_name}</title>
</head>
<body class="bg-2">
	<header class="top-bar bg-f">
		<a href="http://m.hehenian.com/product/plist.do">
			<span class="icon-back2">
			</span>
		</a>
		<p>购买</p>
	</header>
	<div class="prodetail-top">
		<span class="rate-num">${product.rateInfo * 100 }%</span>
		<p class="prohead-name">${product.product_name} · ${product.period}-M</p>
		<p class="prohead-feature">预期年化收益率${product.rateInfo * 100 }%</p>
	</div>
	<section class="sign-area margin-none">
		<form>
			<div class="sign-style br-3 bb-1">
				<span class="sign-lable">购买金额：</span>
				<input type="text" id="amount" maxlength="7" placeholder="500元起购" oninput="getExpectedReturn()" onpropertychange="getExpectedReturn()">
			</div>
			<div class="buy-tips">
				<p>预期收益：<span id="expectedReturn">0</span>元</p>
				<!-- <p>计息日期：<span id="interestDate"></span></p> -->
			</div>
			
			<div class="buy-tips">
				<p class="tips-red">账户余额：<span id="balance"><fmt:formatNumber value="${balanceAmount}" type="number" pattern="0.00" maxFractionDigits="2"/></span>元<span class="icon-atten"></span></p>
			</div>
			<p><span></span>同意<a href="http://m.hehenian.com/finance/agreement.do">《购买协议》</a></p>
			<div class="bnb-a">
				<input type="hidden" id="totalAm" value="${canInvest}"/>
				<input type="button" value="立即购买" class="bn-btn" id="buyBtn">
			</div>
		</form>
</section>
	<!--输入密码弹窗-->
	<section class="buy-pop" style="display: none;">
		<div class="in-code">
			<div class="ic-head">
				<p>请输入红包支付密码</p>
			</div>
			<div class="ic-form">
					<div>
						<input type="password" class="ic-input" id="verifyCode" placeholder="请输入红包支付密码">
					</div>
					<div class="ic-fun">
						<span class="ic-cancel">取消</span>
						<input type="button" value="确定" class="ic-sub">
					</div>
		</div>
		</div>
	<!--购买成功弹窗-->
	<div class="buy-succ" style="display: none;">
		<div class="bs-head">
		<p>
			<span></span>恭喜！<br />
			提交成功交易已确认
			</p>
			<span class="close-w"></span>
		</div>
		<div class="bs-foot">
		<p>扣款情况：</p>
		<p>红包账户：元</p>
		<p></p>
		</div>
	</div>
</section>
<script>
	function subBuySuccess(data) {
		if (data.returnCode == 0) {
			var mouth = data.data.mouth;
			var invest = data.data.investAmount;
			var balance = data.data.balanceAmount;
			$(".buy-pop").show();
			$('.buy-succ p:eq(0)').text('您成功购买爱定宝·' + mouth + '-M，购买金额' + invest + '元。');
			$('.buy-succ p:eq(1)').text('账户余额扣除：' + invest + '元');
			$('.buy-succ p:eq(2)').text('红包账户余额：' + balance +'元');
			$('.buy-succ .bs-foot').show();
			$('.buy-succ').show();
			$('#verifyCode').val("");
			//window.location.href="http://m.hehenian.com/profile/buy.do";
		} else if(data.returnCode == 1){
			popWindow(data.messageInfo);
			setTimeout(function() {
				document.location.href = "http://m.hehenian.com/account/realAuth.do"
			},2000);
		}else {
			$('#buyBtn').removeClass("disable");
			popWindow(data.messageInfo);
		}
	}
	
   function showPop(){
	      $('#cover').show();
	      $('#pop').show();
	    }

	//获取预期收益
	function getExpectedReturn() {
		//$('#buyBtn').addClass('disable');
		var money = $('#amount').val();
		if (isNaN(money)) {
			return;
		}
		if (money == "") {
			$('#expectedReturn').text('0');
			return;
		}
		//var rate = $('#rate').text();
		var rate = ${product.rateInfo};
		var month = ${product.period};
		var expectedReturn = money * rate * month / 12;
		if (expectedReturn > 0 && $('#select').attr('class') == "select") {
			$('#buyBtn').removeClass('disable');
		}

		if (expectedReturn > 1000000) {
			expectedReturn = floor(expectedReturn / 1000000) + '百万';
			$('#expectedReturn').text(expectedReturn);
			return;
		} else if (expectedReturn > 10000) {
			expectedReturn = floor((expectedReturn / 10000), 2) + '万';
			$('#expectedReturn').text(expectedReturn);
			return;
		} else {
			$('#expectedReturn').text(floor(expectedReturn, 2));
			return;
		}
	}

	function floor(digit, length) {
		length = length ? parseInt(length) : 0;
		if (length <= 0)
			return Math.floor(digit);
		digit = Math.floor(digit * Math.pow(10, length)) / Math.pow(10, length);
		return digit;
	}

	//得到计息日期
	function getInterestDate(AddDayCount) {
		var dd = new Date();
		dd.setDate(dd.getDate() + AddDayCount);//获取AddDayCount天后的日期
		var y = dd.getFullYear();
		var m = dd.getMonth() + 1;//获取当前月份的日期
		var d = dd.getDate();
		//$('#interestDate').text(y + "-" + m + "-" + d)
	}

	//立即购买前校验
	function Check() {
		/* if ($('#buyBtn').attr('class') != 'buyBtn') {
			return;
		} */
		if ($('#buyBtn').attr('class') != 'bn-btn') {
			return;
		}
		//var mounth = Month;
		var rateId = ${product.product_rate_id};
		//var rate = Rate;
		var amount = $('#amount').val();
		//var totalAm = $('#totalAm').val();
		var reg = /^[1-9]{1}[0-9]{0,19}$/;
		if (isNaN(amount)) {
			popWindow("请输入有效购买金额，只能为数字！");
			return;
		}
		if (!reg.test(amount)) {
			popWindow("输入金额有误！");
			return;
		}
		if (amount<500) {
			popWindow("购买金额大于等于500！");
			return;
		}
		if (amount.indexOf(' ') > -1) {
			popWindow("购买金额不能包含空格！");
			return;
		}
		if (amount < 0) {
			popWindow("买入金额不能为负数");
			return;
		}
		$(".buy-pop").show();
		$('.in-code').show();
		
				
	}
	//立即购买前校验
	function subby() {
		var rateId = ${product.product_rate_id};
		var amount = $('#amount').val();
		var stParam = [];
		stParam.push('pid=' + rateId);
		stParam.push('amount=' + amount);
		var code =$('#verifyCode').val() ;
		if(code!=null&&code.length>0){
			stParam.push('code='+code);
		}
		$('#buyBtn').addClass('disable');
		var options = {type:"post",url:"http://m.hehenian.com/finance/investHb.do",data:stParam.join('&')};
		ajaxRequest(options,function(data){
			//popWindow(data);
			subBuySuccess(data);
		});
	}
	
	$(function() {
		sbh();
		getInterestDate(1);
		$("#buyBtn").bind("click", function() {
			Check();
		});
		$(".ic-sub").bind("click", function() {
			var code =$('#verifyCode').val() ;
	        if(code=='' || code.length==0){
	           popWindow("请输入红包支付密码！");
	           return;
	        }
	        $(".buy-pop").css("display","none");
	        $(".in-code").css("display","none");
	        subby();
		});
		 $(".ic-cancel").click(function() {
       		 $(".buy-pop").css("display","none");
    	 });
		
	});
	
	//倒计时
	function telephoneTimer_() {
		var count = 60;
		var timer=setInterval(function(){
			if(count <= 0){
				clearInterval(timer);
				$("#getCode").removeAttr("disable").text("获取验证码");
			}else{
				count--;
				$("#getCode").text(count+"s重获");
			}
		},1000);
	}
	//每次进入页面执行一次，解决点击查看协议返回，预期收益没有的BUG
	getExpectedReturn();
</script>
</body>
</html>