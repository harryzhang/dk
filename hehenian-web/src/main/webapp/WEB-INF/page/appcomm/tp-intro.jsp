<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>投资详情</title>
<jsp:include page="/include/app-jfq-head.jsp"></jsp:include>
</head>
<body>
<div id="preloader">
  <div id="status">
    <p class="center-text"> 正在加载... </p>
  </div>
</div>
<!--顶部底部浮动层-->
<jsp:include page="/include/app-comm.jsp"></jsp:include>

<!-- main -->
<div id="main-wrap" style=" margin-bottom:120px;">
  <div id="container" style=" padding-left:0px; padding-right:0px; ">
    <div class="content" >
      <div class="container no-bottom" style="background:#edf0f3">
        <div class="section-title" style=" text-align:center; padding-top:15px;">
          <h4>
            <s:property value="#request.borrowDetailMap.moneyPurposes" default="---" />
          </h4>
          <em1>编号：${borrowDetailMap.number}</em1>
        </div>
      </div>
      <div class="decoration"></div>
      <div class="container no-bottom container-b">
        <ul>
          <li>借款金额：
            <s:property value="#request.borrowDetailMap.borrowAmount" default="---" />
          </li>
          <li>已投金额：${borrowDetailMap.hasInvestAmount}</li>
          <li>剩余可投金额：${borrowDetailMap.investAmount}</li>
          <li>年利率：
            <s:property value="#request.borrowDetailMap.annualRate" default="---" />
            %</li>
          <li>借款期限：
            <s:property value="#request.borrowDetailMap.deadline" default="---" />
            个月</li>
          <li>还款方式：
            <s:if test="%{#request.borrowDetailMap.isDayThe ==2}">到期还本付息</s:if>
            <s:elseif
														test="%{#request.borrowDetailMap.paymentMode == 1}">按月分期还款</s:elseif>
            <s:elseif test="%{#request.borrowDetailMap.paymentMode == 2}">按月付息,到期还本</s:elseif>
            <s:elseif
														test="%{#request.borrowDetailMap.paymentMode == 3}">秒还</s:elseif>
            <s:elseif test="%{#request.borrowDetailMap.paymentMode == 4}">一次性还款</s:elseif>
          </li>
          <li>已投人数：
            <s:property value="#request.borrowDetailMap.investNum" default="0" />
            人</li>
          <div class="chart" id="tbjr"> <strong>投资进度</strong> <em>
            <s:property value="#request.borrowDetailMap.schedules" default="0" />
            %</em>
            <div class="chart-background">
              <div class="green-chart p90" style='width: <s:property value="#request.borrowDetailMap.schedules" default="0"/>%;'></div>
            </div>
          </div>
          <div class="formFieldWrap" >
            <label class="field-title contactNameField" for="contactNameField">请输入投资金额(您的可投金额为${userMap.usableSum}元):</label>
            <input type="text" name="contactNameField" value="" class="contactField requiredField" id="contactNameField" />
            <span id="amountSpan" style="font-size: 12px;display: none; "></span>
          </div>
         <!--  <div class="formFieldWrap">
           请输入验证码：<a href="javascript:void()" onclick="switchCode(this)"  id="a1"> <img src="/admin/imageCode.do?pageId=userregister" title="点击更换验证码" id="codeNum1" /> 看不清？换一换</a>
            <input type="text" name="contactEmailField"  value="" class="contactField requiredField requiredEmailField" id="contactEmailField"/>
            <span id="codes" style="font-size: 12px;display: none; "></span>
          </div> -->
        </ul>
      </div>
      <div class="decoration"></div>
      <div class="container no-bottom" style=" width:90%; margin:0px auto;">
        <div class="one-half-responsive">
          <h4>相关信息</h4>
          <ul>
            <li>用户:
              <s:property value="#request.borrowUserMap.username.substring(0,2)+'***'" />
            </li>
            <li>性别： ${borrowUserMap.sex}</li>
            <li>年龄：${borrowUserMap.age} </li>
            <li>学历：${borrowUserMap.highestEdu} </li>
            <li>收入：${borrowUserMap.monthlyIncome} </li>
            <li>职位：${borrowUserMap.job}</li>
            <li>公司名称： ${request.orgName}</li>
            <li>公司行业：${borrowUserMap.companyLine}</li>
            <li>现单位工作时间：${borrowUserMap.workYear}年</li>
          </ul>
        </div>
        <div class="decoration hide-if-responsive"></div>
        <div class="one-half last-column">
          <h4>审核信息</h4>
          <ul class="icon-list">
            <li class="tick-list">身份认证</li>
            <li class="tick-list">工作认证</li>
            <li class="tick-list">居住地认证</li>
            <li class="tick-list">信用报告</li>
            <li class="tick-list">收入认证</li>
            <li class="tick-list">房产证</li>
            <li class="tick-list">房屋租赁合同</li>
            <li class="tick-list">水电单据</li>
            <li class="tick-list">工作证明</li>
            <li class="tick-list">银行流水</li>
            <li class="tick-list">信用卡账单</li>
            <li class="tick-list">车产证</li>
            <li class="tick-list">社保</li>
            <li class="tick-list">营业执照</li>
            <li class="tick-list">租赁合同</li>
            <li class="tick-list">其它资产证明</li>
            <li class="tick-list">征信报告</li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</div>










<div style=" text-align: center; bottom: 60px;  position: fixed; z-index: 3; width:100%"><!-- footer -->
<div >
            <s:if test="%{#request.borrowDetailMap.borrowStatus == 2}"> <a href="javascript:;" class="button-big button-green" onclick="invest();" id="invest" style=" width:90%">立即投资</a> </s:if>
            <s:elseif test="%{#request.borrowDetailMap.borrowStatus == 3}"> <a href="javascript:;" class="button-big button-red" style=" width:90%">满标</a> </s:elseif>
            <s:elseif test="%{#request.borrowDetailMap.borrowStatus == 4}"> <a href="javascript:;" class="button-big button-orange" style=" width:90%">还款中</a> </s:elseif>
            <s:elseif test="%{#request.borrowDetailMap.borrowStatus == 5}"> <a href="javascript:;" class="button-big button-blue" style=" width:90%">已还完</a> </s:elseif>
            <s:elseif test="%{#request.borrowDetailMap.borrowStatus == 6}"> <a href="javascript:;" class="button-big button-dark" style=" width:90%">流标</a> </s:elseif>
            <s:elseif test="%{#request.borrowDetailMap.borrowStatus == 8}"> <a href="javascript:;" class="button-big button-dark" style=" width:90%">待发布</a> </s:elseif>
          </div>
</div>
<input type="hidden" value="${borrowDetailMap.id }" id="id" name="id" />
<input type="hidden" value="${userMap.usableSum }" id="useSum" />
<input type="hidden" value="${borrowDetailMap.investAmount}" id="investAmount" />
<form id="f_investBorrow" action="/investBorrow.do" method="post">
  <input type="hidden" id="int_id" name="paramMap.id" />
  <input type="hidden" id="int_code" name="paramMap.code" />
  <input type="hidden" id="int_amount" name="paramMap.amount" />
  <input type="hidden" id="int_usrCustId" name="paramMap.usrCustId" />
    <input type="hidden"  name="paramMap.usrCustId" />
</form>
<script>
//验证码
		function switchCode(e) {
			var timenow = new Date();
			if ($(e).attr("id") == "a1") {
				$("#codeNum1").attr("src", "/admin/imageCode.do?pageId=userregister&d=" + timenow);
			} else {
				$("#codeNum2").attr("src", "/admin/imageCode.do?pageId=userregister&d=" + timenow);
			}
		}
			function invest() {
				
				var xieyiok = Number($("#xieyi:checked").val());
				if (xieyiok != 1) {
					//alert("请仔细阅读并同意借款协议!");
					//return;
				}

				var step = "${session.user.authStep}";
				var username = "${borrowUserMap.username}";
				var uname = "${user.username}";
		var invest = '立即投资';
				var code = $("#contactEmailField").val();
				var amount = $("#contactNameField").val();
				var id = $("#id").val();
				var investAmount=$("#investAmount").val();
				investAmount=investAmount.replace(new RegExp(/(,)/g),'')-0;
				if (null != uname) {
					if (null == amount || amount == "") {
						$("#amountSpan").html("<font style='color:red'>请输入投资金额</font>");
						$("#amountSpan").show();
						//alert("请输入交易金额");
						return false;
					} else {
						$("#amountSpan").hide();
						$("#amountSpan").html("");
					}
					if (Number(amount) % 100 != 0 || amount < 100) {
						$("#amountSpan").show();
						$("#amountSpan").html("<font style='color:red'>请输入正确数字，如：100,200</font>");
						//$("#income").hide();
						//alert("请输入正确数字，如：100,200");
						return false;
					}else if(investAmount<(amount-0)){
						$("#amountSpan").show();
						$("#amountSpan").html("<font style='color:red'>投资金额超过剩余可投金额</font>");
						return false;
					}
					else if(($("#useSum").val()-0)<(amount-0)){
							$("#amountSpan").show();
							$("#amountSpan").html("<font style='color:red'>可用余额不足</font><a href='recharge.do' style='color:blue;'>立即充值</a>");
						//	alert("可用余额不足");
							return false;
						}
					else{
						$("#amountSpan").hide();
					}
						//$("#income").show();
						
					
	
					/* if (code == null || code == "") {
						$("#codes").show();
						$("#codes").html("<font style='color:red'>请输入验证码</font>");
						//alert("请输入验证码");
						return false;
					} else {
						$("#codes").hide();
					} */
					if (username == uname) {
						alert("无效操作,不能投自己发布的标");
						return false;
					}
					
					
					$('#int_id').val(id);
					$('#int_code').val(code);
					$('#int_amount').val(amount);
					$('#int_usrCustId').val($("#usrCustId").val());
					
					$('#invest').attr("disabled", true);
					
					$("#f_investBorrow").submit();
					
					

				} else {
					alert("请先登录");
				}

			};
var retmsg="${param.retMsg}";
var url="${param.url}";
if(retmsg){
	alert(retmsg);
	if(url){
		window.location.href=url;
	}
}

</script>

<!--提示没有注册汇付天下-->
<s:if test="#session.user.usrCustId==null||#session.user.usrCustId<=0">
<div  style=" text-align: center; bottom: 60px;  position: fixed; z-index: 3; width:100%"><!-- footer -->
<div >
          <a href="javascript:;" class="button-big button-green" onclick="invest();" id="invest" style=" width:90%">您还没有注册汇付天下</a> 
          </div>
</div>
</s:if>


</body>
</html>