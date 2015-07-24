<%@ page contentType="text/html; charset=utf-8"%>
<!doctype html>
<html>
<head>
<%@ include file="../common/head.jsp"%>
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
	 <div class="order-panel">
        <p class="tips"><i></i>您需预存金额（元）</p>
        <span class="maney">${preDepositAmount }</span>
        <input type="hidden" id="amount" value="${preDepositAmount }" />
        <div class="income"><i></i>到期返还本息收益：<span class="cOrange">${obtainAllAmount}</span></div>
    </div>
    <ul class="order-info">
        <li class="center">
            <div>
            <c:if test="${product.sub_channel eq 3}" >
                <p>每月冲抵停车费（元）</p>
                <span class="cOrange" id="fee">
                ${offsetFee.parking_fee}
                
                </span>
           </c:if>
            <c:if test="${product.sub_channel eq 2}" >
                <p>每月冲抵物业费（元）</p>
                <span class="cOrange" id="fee">
                ${offsetFee.property_fee}
                </span>
           </c:if>
                
            </div>
        </li>
        <li class="center">
            <div>
                <p>冲抵时间</p>
                <span class="cOrange">${offsetCur}~${offsetDelay}</span>
            </div>
        </li>
    </ul>
	<section class="sign-area margin-none">
		<form>
			<a href="http://m.hehenian.com/product/managerOffset.do?pid=${product.product_rate_id}" >
			<div class="address-list order-address br-2" >
        	<ul>
            <li><input type="hidden" id = "roomOrPlateNo" value="${roomOrPlateNo}"/>
                <c:if test="${product.sub_channel eq 3}" ><label>停车卡号：</label>${offsetDetail.plate_number}</c:if>
                <c:if test="${product.sub_channel eq 2}" ><label>房间号：</label>${offsetDetail.roomnum}</c:if></li>
            <li>
                <label>小区：</label>${addressDesc}
            </li>
        	</ul>
       		 <i class="more">></i>
   		 	</div>
   		 	</a>
			<div class="sign-style br-2 bb-1">
				<span class="sign-lable">选择银行：</span>
				<c:if test="${not empty bingCard }">
				<a href="http://m.hehenian.com/profile/managerCard.do?pid=${product.product_rate_id}" class="cb-a" val="${bingCard.bank_card_id}">${bingCard.bank_name}(尾号${bingCard.card_no})</a>
				</c:if>
				<c:if test="${ empty bingCard }">
				<a href="http://m.hehenian.com/finance/bindCard.do" class="cb-a">添加银行卡</a>
				</c:if>
			</div>
			<div class="buy-tips">
				<p class="tips-red">账户余额：<span id="balance">${balanceAmount}</span>元<span class="icon-atten"></span></p>
			</div>
			<c:if test="${ !(product.channel eq 1 and product.sub_channel eq 1) }">
			<div class="sign-style br-1 bb-1">
				<span class="sign-lable">推荐人：</span>
				<input type="tel" placeholder="(选填)请输入推荐人手机号" id="recPhone" name="recPhone" maxlength="11">
			</div>
			</c:if>
			<p><span></span>同意<a href="http://m.hehenian.com/finance/agreement.do">《${product.product_name}购买协议》</a></p>
			<div class="bnb-a">
				<input type="hidden" id="totalAm" value="${canInvest}"/>
				<input type="button" value="立即预存" class="bn-btn" id="buyBtn">
			</div>
		</form>
	</section>

	<!-- 确认您的购买信息弹框 -->
	<section class="modal hide center" id="BuyInfo">
		<div class="modal-bd">
			<h3 class="modal-title">
				<i></i>请确认您的购买信息
			</h3>
			<ul class="modal-info">
				<li><span>产品名称：</span> <i id="productName">${product.product_name}
						· ${product.period}-M</i></li>
				<li><span>预计年化：</span> ${product.rateInfo * 100 }%</li>
				<li><span>购买金额：</span> <b class="cOrange"><i
						id="buy_amount">1,000,000</i>元</b></li>
			</ul>
			<div class="modal-btns">
				<input ok type="button" value="确认"> <input cancel
					type="button" value="返回修改">
			</div>
		</div>
	</section>



	<!--未经实名验证-->
	<section class="buy-pop hide" id="authModal">
		<div class="none-auth">
			<div class="na-head">
				重要提示 <span class="close"></span>
			</div>
			<div class="na-tips">
				<p>您尚未完成实名认证，无法进行购买/充值。</p>
				<a href="http://m.hehenian.com/account/realAuth.do?from=buy"> <span
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
				<p>您尚未设置支付密码，无法进行购买/充值。</p>
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
				<p>您尚未绑定银行卡，无法进行购买/充值。</p>
				<a href="http://m.hehenian.com/finance/bindCard.do"> <span
					class="na-btn">立即绑卡</span>
				</a>
			</div>
		</div>
	</section>
	
	<!--输入支付密码弹窗-->
    <section class="buy-pop hide" id="payPsdModal">
        <div class="in-code">
            <div class="ic-head">
                <p>请输入支付密码<a href="http://m.hehenian.com/account/resetPwdIndex.do?pwdFlag=pay">忘记密码</a>
                </p>
            </div>
            <div class="ic-form">
                <div>
                    <input type="password" class="ic-input" placeholder="请输入支付密码">
                </div>
                <div class="ic-fun">
                    <span class="ic-cancel">取消</span>
                    <input type="submit" value="确定" class="ic-sub">
                </div>
            </div>
        </div>
    </section>

	<!--弹出层-->
	<section class="buy-pop hide"  id="psdModal">

		<!--输入密码弹窗-->
		<div class="in-code">
			<div class="ic-head">
				<p>请输入手机验证码</p>
			</div>
			<div class="ic-form">
				<div>
					<input type="text" class="ic-input" id="verifyCode"
						placeholder="请输入短信验证码"> <a href="Javascript:;"
						id="getCode">获取验证码</a>
				</div>
				<div class="ic-fun">
					<span id="codeBtn_cancel" class="ic-cancel">取消</span> 
					<input id="codeBtn" type="button" value="确定" class="ic-sub">
				</div>
			</div>
		</div>
	</section>
	<section class="buy-pop hide" id="succModal">
		<!--购买成功弹窗-->
		<div class="buy-succ">
			<div class="bs-head">
				<p>
					<span></span>恭喜！<br /> 提交成功交易已确认
				</p>
				<span class="close" id="succ_close"></span>
			</div>
			<div class="bs-foot">
				<p>扣款情况：</p>
				<p>合和年账户：元</p>
				<p></p>
			</div>
		</div>
	</section>
<script>
//购买成功响应
		function subBuySuccess(data) {
			if (data.returnCode == 0) {
				var accountType = "${sessionScope.user.accountType}";
				//$('#successNotice').addClass('donghua');
				var mouth = data.data.mouth;
				var invest = data.data.investAmount;
				var balance = data.data.balanceAmount;
				var charge = data.data.chargeAmount;
				var bankNo = data.data.bankNo;
				//var bankCode = data.data.bankCode;
				//var bankNam = encodeURI(data.data.bankName);
				var bankNam = data.data.bankName;
				var investPro = data.data.investPro;
				var productName = '${product.product_name}';
				//var param = "?mounth=" + mouth + "&invest=" + invest + "&balance="
				//		+ balance + "&bankNo=" + bankNo + "&bankCode=" + bankCode
				//		+ "&bankName=" + bankNam + "&charge=" + charge;
				//document.location.href = '/hhn_web/toSuccessPage.do' + param;
				
				//弹购买成功提示框
// 				$('.buy-pop').show();
				succModal.show();
				$('.buy-succ p:eq(0)').text(
						'您成功购买'+ productName +'·' + mouth + '-M，购买金额' + invest + '元。');
				$('.buy-succ p:eq(1)').text('账户余额扣除：' + balance + '元');
				if ('1' != accountType) {
					$('.buy-succ p:eq(2)').text(
							bankNam + bankNo + ' 扣除：' + charge + '元');
				}
				$('.buy-succ .bs-foot').show();
				$('.buy-succ').show();
				$('#verifyCode').val("");
			}
			/* else if (data.returnCode == 5) {
				//实名认证
				authModal.show();
			} else if (data.returnCode == 6) {
				//支付密码
				resetPwdModal.show();
			}else if(data.returnCode==7){
				//绑卡
				bindCardModal.show();
			} */
			else if (data.returnCode == 1) {
// 				$('.buy-succ p:eq(0)').text("验证码不正确");
// 				$('.buy-succ .bs-foot').hide();
				popWindow(data.messageInfo);
			} else if (data.returnCode == 2) {
				//绑卡
				bindCardModal.show();
			} else if (data.returnCode == 3) {
				//换卡
				$('.buy-succ p:eq(0)').text("输入验证金额");
				$('.buy-succ p:eq(1)').text("去验卡");
				$('.buy-succ .bs-foot').hide();
				var bankCode = data.data.bankCode;
				var bankNo = data.data.bankNo;
				window.location.href = "http://m.hehenian.com/finance/moneyVerify.do?bankCode="
						+ bankCode + "&userAccount=" + bankNo;
			} else if (data.returnCode == 4) {
				/* $('.buy-pop').show();
				$('.in-code').show();
				$('#buyBtn').removeClass("disable"); */
				
				
				//document.location.href = "mobileVerification.jsp";
			} else if (data.returnCode == -1) {
				var msg = data.messageInfo;
				if (msg.indexOf("余额不足") > -1) {
					popWindow("抱歉，银行卡余额不足");
					setTimeout(
							function() {
								document.location.href = "http://m.hehenian.com/profile/managerCard.do"
							}, 2000);
				} else {
					document.location.href = "http://m.hehenian.com/balance/chargePage.do";
				}
			} else {
				$('#buyBtn').removeClass("disable");
				popWindow(data.messageInfo);
			}
		}
		
		

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
		
		//密码框
		var psdModal = new Modal({
			id : '#psdModal',
			events : {
				'.close click' : function(modal) {
					modal.close();
				}
			}
		});
		
		
		//购买成功
		var succModal = new Modal({
			id : '#succModal',
			events : {
				'.close click' : function(modal) {
					modal.close();
				}
			}
		});
		
	    var payPsdModal = new Modal({
	        id: '#payPsdModal',
	        events: {
	            '.ic-cancel click': function(modal) {
	            	modal.modal.find('.ic-input').val("");
	                modal.close();
	            },
	            'input[type="submit"] click': function(modal) {
	            	var code = modal.modal.find('.ic-input').val();
	     	        if(code=='' || code.length==0){
	     	           popWindow("请输入支付密码！");
	     	           return;
	     	        }
	        		//异步请求验证支付密码是否正确
	        		var options = {type:"POST",url:"http://m.hehenian.com/account/checkPayPwd.do",data:{pwd:code}};
	        			ajaxRequest(options,function(data){
	        				if (data.returnCode == 1) {
	        					popWindow(data.messageInfo);
	        					modal.modal.find('.ic-input').val("");
	        				}else {//密码修改成功，跳到成功页面
	        					payPsdModal.close();
	        					checkOffsetJoin();
	        				}
	        			});
	            }
	        }
	    });
	    function checkOffsetJoin(){
	    	var rateId = ${product.product_rate_id};
	    	var roomOrPlateNo = '${roomOrPlateNo}';
	    	var community = '${offsetDetail.mainaddressid}';
	    	var stParam = [];
			stParam.push('pid=' + rateId);
			stParam.push('roomOrPlateNo=' + roomOrPlateNo);
			stParam.push('community=' + community);
	    	var options = {
				type : "post",
				url : "http://m.hehenian.com/product/checkOffsetJoin.do",
				data : stParam.join('&')
			};
			ajaxRequest(options, function(data) {
				//popWindow(data);
				if(data.result==0){
					subby();
				}else if(data.result==1){
					popWindow("您的地址已经参加冲抵活动，冲抵结束日期为"+data.enddate);
				}
			});
	    
	    }
	    
	    //前置条件都满足并且支付密码输入成功的情况下才能进行触发购买
	    function subby(){
	    	var rateId = ${product.product_rate_id};
			var bank_card_id = $('.sign-style').find('.cb-a')
					.attr('val');
			var amount = $('#amount').val();
			var phone = $("#recPhone").val();
			var jjb = "${ !(product.channel eq 1 and product.sub_channel eq 1) }";
			var telReg = /^1\d{10}$/;
			if (jjb == 'true' && phone != ''
					&& !telReg.test(phone)) {
				popWindow("手机号输入有误！");
				return;
			}
			
			
			phone = phone == undefined ? "" : phone;
			var stParam = [];
			//stParam.push('mounth='+mounth);
			stParam.push('pid=' + rateId);
			stParam.push('bank_card_id=' + bank_card_id);
			//stParam.push('curRate=' + rate);
			stParam.push('amount=' + amount);
			stParam.push('recommentPhone=' + phone);
			var code = $('#verifyCode').val();
			if (code != null && code.length > 0) {
				stParam.push('verifyCode=' + code);
			}

			//车宝，多宝参数
			var offsetDetailId = ${offsetDetail.id};
			var offsetfee = $("#fee").text();
			
		
			stParam.push('offsetDetailId='+offsetDetailId);
			stParam.push('offsetfee='+offsetfee);
			stParam.push('offsetBegin='+'${offsetCur}');
			stParam.push('offsetEnd='+'${offsetDelay}');
			//end
			var options = {
				type : "post",
				url : "http://m.hehenian.com/finance/invest.do",
				data : stParam.join('&')
			};
			ajaxRequest(options, function(data) {
				//popWindow(data);
				subBuySuccess(data);
			});
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
			digit = Math.floor(digit * Math.pow(10, length))
					/ Math.pow(10, length);
			return digit;
		}

		//得到计息日期
		function getInterestDate(AddDayCount) {
			var dd = new Date();
			dd.setDate(dd.getDate() + AddDayCount);//获取AddDayCount天后的日期
			var y = dd.getFullYear();
			var m = dd.getMonth() + 1;//获取当前月份的日期
			var d = dd.getDate();
			$('#interestDate').text(y + "-" + m + "-" + d)
		}

		//立即购买
		function subBuy() {
			/* if ($('#buyBtn').attr('class') != 'buyBtn') {
				return;
			} */
			if ($('#buyBtn').attr('class') != 'bn-btn') {
				return;
			}
			//var mounth = Month;

			//var rate = Rate;
			var amount = $('#amount').val();
			//var totalAm = $('#totalAm').val();
		/*	var reg = /^[1-9]{1}[0-9]{0,19}$/;
			if (isNaN(amount)) {
				popWindow("请输入有效购买金额，只能为数字！");
				return;
			}
			if (!reg.test(amount)) {
				popWindow("输入金额有误！");
				return;
			}
			if (amount.indexOf(' ') > -1) {
				popWindow("购买金额不能包含空格！");
				return;
			}
			if (amount < 0) {
				popWindow("买入金额不能为负数");
				return;
			}*/
			//        if(amount>parseFloat(totalAm)){
			//            newpopWindow("投资金额大于可投金额！");
			//            return;
			//        }
			/* if ($('#select').attr('class') != 'select') {
				alert('请同意爱定宝服务协议');
				return;
			} */

			//二次确认弹框
			myModal.show();

			$('#buyBtn').addClass('disable');

			/* ajax({
				waitTagId : 'center',
				url : 'http://m.hehenian.com/product/invest.do',
				type : 'post',//非必须.默认get
				data : stParam.join('&'),
				dataType : 'json',//非必须.默认text
				async : true,//非必须.默认true
				cache : false,//非必须.默认false
				timeout : 15000,//非必须.默认30秒
				success : subBuySuccess, //非必须
				complete : function(XMLHttpRequest, status) { //请求完成后最终执行参数
					if (status == 'timeout') { //超时,status还有success,error等值的情况
						ajaxTimeOut.abort(); //取消请求
						$('#buyBtn').removeClass("disable");
						popWindow("系统处理超时,请稍后重试");
					}
				}
			}); */
		}

		var myModal = new Modal(
				{
					id : "#BuyInfo",
					showCallBack : function() {
						$("#buy_amount").html($('#amount').val());
					},
					closeCallBack : function() {
					},
					events : {
						'input[ok] click' : function() {
							myModal.close();
							$('#buyBtn').removeClass('disable');
							//先验证是否实名认证 设置支付密码 绑卡
							chekp();
						},
						'input[cancel] click' : function() {
							myModal.close();
							$('#buyBtn').removeClass('disable');
						}
					}
				});

		
		//验证是否实名认证 设置支付密码 绑卡
		function chekp(){
			 var options = {type:"POST",url:"http://m.hehenian.com/finance/setUp.do"};
				ajaxRequest(options,function(data){
					if (data.returnCode == 5) {
						//实名认证
						authModal.show();
					} else if (data.returnCode == 6) {
						//支付密码
						resetPwdModal.show();
					}else if(data.returnCode==7){
						//绑卡
						bindCardModal.show();
					}else{
						//前置条件都瞒住的情况下，要求输入支付密码
						payPsdModal.show();
					}
				});
		 }
		
		
		$(function() {
			sbh();
			getInterestDate(1);
			$("#buyBtn").bind("click", function() {
				subBuy();
			});

			$("#codeBtn").bind("click", function() {
				var code = $('#verifyCode').val();
				if (code == '' || code.length == 0) {
					popWindow("请输入验证码！");
					return;
				}
				
				psdModal.close();
				subBuy();
			});
			
			//关闭购买成功记录弹框
			$("#succ_close").bind("click", function() {
				if(${product.sub_channel==3}){
					window.location.href="http://m.hehenian.com/profile/buy.do?flag=che&tab_f=g";
				}else if(${product.sub_channel==2}){
					window.location.href="http://m.hehenian.com/profile/buy.do?flag=duo&tab_f=g";
				}
			});

			$("#codeBtn_cancel").click(function() {
				psdModal.close();
				$('#verifyCode').val("");
			});

			$("#getCode").bind("click",function() {
					if ($(this).attr("disable") == null) {
						var param = {checkPhone : false};
						$.post("http://m.hehenian.com/common/sendPhoneVirifyCode.do",param,function(data) {
							if (data.ret == 1) {
								popWindow("发送验证码失败");
							} else if (data.ret == 2) {
								popWindow("手机号码不存在");
							} else if (data.ret == 3) {
								popWindow("手机号码已存在");
							} else {
								$("#getCode").attr(
										"disable",
										true).text(
										"60s重获");
								telephoneTimer_();
							}
						}, "json");
					}
				});
		});

		function showPop() {
			$('#cover').show();
			$('#pop').show();
		}
		
		//倒计时
		function telephoneTimer_() {
			var count = 60;
			var timer = setInterval(function() {
				if (count <= 0) {
					clearInterval(timer);
					$("#getCode").removeAttr("disable").text("获取验证码");
				} else {
					count--;
					$("#getCode").text(count + "s重获");
				}
			}, 1000);
		}
	</script>
</body>
</html>