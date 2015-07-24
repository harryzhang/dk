<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>充值</title>
<jsp:include page="/include/app-jfq-head.jsp"></jsp:include>
</head>
<style>
.bg{display:none;position:fixed;width:100%;height:100%;background:#000;z-index:80;top:0;left:0;opacity:0.9;}
.tc_wenxts{display:none;width:100%;position:fixed;top:50%;margin-top:-150px;z-index:90;}
</style>
<body>
<div id="preloader">
  <div id="status">
    <p class="center-text"> 正在加载... </p>
  </div>
</div>
<!--顶部底部浮动层-->

<jsp:include page="/include/app-comm.jsp"></jsp:include>

<!-- main -->
<div id="main-wrap">
  <div id="container">
    <div class="content">
           <div class="section-title" style=" padding:10px 0px;; background-color:#50c2e9;border-top:#6fd0f0 1px solid;border-bottom:#32aadf 1px solid; color:#FFF; font-weight:bold; font-size:16px; text-align:center;">
        充值
      </div>
      <div class="one-half-responsive">
        <p style=" padding:20px 20px 0px 20px;"><strong>温馨提示：</strong>您正在使用手机网银进行充值，请确认您的银行卡是否开通了手机网银；另，由于目前手机端支持的银行只有招、建、交、浦、兴5家银行，若你的银行卡不在此列，请到http://www.hehenian.com网站充值。因各家银行限制单笔充值数额，如需大额充值，请登录您对应的网上银行进修改。</p>
        <div class="container no-bottom">
          <div class="formFieldWrap">
            <label class="field-title contactNameField" for="contactNameField">充值金额：</label>
            <input id="money" type="text"  class="contactField requiredField" />
            <span style="color: red; float: none;" id="money_tip" class="formtips"></span> </div>
          <div class="formFieldWrap">
         
                       <div class="one-half-responsive">
                <div class="container">
                    <div class="submenu-navigation">
                        <a href="#" class="submenu-nav-deploy">请选择充值银行</a>
                        <div class="submenu-nav-items">
                            <a href="#" data-id="CMB">招商银行</a>
                            <a href="#" data-id="CCB">建设银行</a>
                            <a href="#" data-id="BOCOM" >交通银行</a>
                            <a href="#" data-id="SPDB">浦发银行</a>
                            <a href="#" data-id="CIB">兴业银行</a>
                        </div>
                    </div>
                    <span style="color: red; float: none;" id="bank_tip" class="formtips"></span> 
                </div>
                
                
        <!--    <style>
			  	.table-sub-title ul {}
				.table-sub-title ul li { list-style-type:none; float:left}
				.table-sub-title ul li input { float:left; margin-top:10px;}
				.table-sub-title ul li img { height:33px;}
				.worksbox{position:relative;}
				.worksbox a{padding:6px;display:block;}
				.worksbox a:hover{text-decoration: none;}
				.worksbox-ok {border:1px solid #999;text-decoration: none;}
				.worksbox a img{}
			  </style>
                  <ul class="worksbox">
                    <li> <a href="#" data-id="CMB" class="checkbox radio-one" ><img src="/images/banks/bank_02.png"  /></a></li>
                    <li> <a href="#"  data-id="CCB"><img src="/images/banks/bank_03.png" /></a></li>
                    <li><a href="#"  data-id="BOCOM"  > <img src="/images/banks/bank_11.png" /></a></li>
                    <li> <a href="#" data-id="SPDB"><img src="/images/banks/bank_06.png"  /></a></li>
                    <li> <a href="#" data-id="CIB" ><img src="/images/banks/bank_08.png"  /></a></li>
                  </ul>-->
            <div class="formSubmitButtonErrorsWrap">
              <input value="${map.usrCustId}" id="usrCustId" type="hidden">
              <form id="f_addRechargeInfo" action="/chinapnrPayment.do" method="post" >
                <input type="hidden" id="int_money" name="money" />
                <input type="hidden" id="int_openBankId" name="openBankId" />
                <input type="hidden" id="int_usrCustId" name="usrCustId" />
                <input type="hidden" id="int_cardDcFlag" name="cardDcFlag" />
              </form>
              <input onclick="addRechargeInfo();" type="submit" class="buttonWrap button button-green contactSubmitButton" id="contactSubmitButton" value="充值" data-formId="contactForm"/>
            </div>
            
            <!--<a href="javascript:addRechargeInfo();" class="button-big button-green" style="margin-left: -10px;">充值</a> --> 
            
          </div>
        </div>
      </div>
    </div>
    <p style=" padding:20px 20px 0px 20px;">
    <strong>温馨提示：</strong><br/>
    1) 请根据借款项目合理安排充值金额，因资金账户由用户自己管理，合和年在线未收取用户任何费用，为防止套现，所充资金必须经投标回款后才能提现；<br/>
2) 充值期间，请勿关闭浏览器，待充值成功并返回首页后，所充资金才能入账，如有疑问请联系客服；<br/>
3) 充值需开通银行卡网上支付功能，如有疑问请咨询开户行客服；<br/>
4) 支付限额由充值银行卡开户行决定 ;<br/>
5) 因各手机银行限制单笔充值数额，如需大额充值，请登录您对应的网上银行进行进行修改。
    </p>
    
  </div>
</div>
<div class="tc_wenxts" id="wenxts" style="display:none; ">
   	  <div style=" width:80%; margin:0px auto;text-align:center; color:#FFF">您正在使用手机网银进行充值，请确认您的银行卡是否开通了手机网银；另，由于目前手机端支持的银行只有招、建、交、浦、兴5家银行，若你的银行卡不在此列，请到http://www.hehenian.com网站充值。因各家银行限制单笔充值数额，如需大额充值，请登录您对应的网上银行进修改。</div>
	  <div style=" width:80%; margin:10px auto 40px auto; padding:10px 0px;" class="button-green"  onclick="closexx();"> <a style=" text-align:center; color:#FFF">我知道了</a></div>
</div>
    <div class="tc_wenxts" id="wenxts1" style="display:none; ">
        <div style=" width:80%; margin:0px auto;text-align:center; color:#FFF">汇付天下托管账户系统，是汇付天下为P2P平台提供的资金账户体系，全面管理平台资金，帮助平台实现收款、付款、用户账户间资金划拨以及账户资金理财功能；同时，汇付天下保障用户账户中的资金独立存放，用户可通过P2P平台对本人汇付账户进行充值、提现，商户在未经用户的许可、授权或法律的规定的情况下，无权动用，从而保证投资人资金安全。</div>
        <div style=" width:80%; margin:10px auto 40px auto; padding:10px 0px;" class="button-green" > <a style=" text-align:center; color:#FFF" href="/registerChinaPnr.do">注册汇付天下</a></div>
        <div style=" width:80%; margin:0px auto; text-align:center; color:#FFF"><a href="index.do" style=" color:#FF0">暂不注册，先逛逛</a></div>
    </div>
    <div class="bg"></div>



</body>
<script>
function addRechargeInfo() {
	
			if ($("#money").val() == "") {
				$("#money_tip").html("请输入充值金额");
				return;
			} else if (!/^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/.test($("#money").val())) {
				$("#money_tip").html("请输入正确的充值金额，必须为大于0的数字");
				return;
			} else if ($("#money").val() < 0.01) {
				$("#money_tip").html("最小金额不能低于0.01");
				return;
			} else {
				$("#money_tip").html("");
			}
			
			if($(".yhChecked").length<=0){
				//alert("请选择充值银行");
				$("#bank_tip").html("请选择充值银行");
				return;
			}else{
				$("#bank_tip").html("");
			}

			/* var payType = 2;
	
		var bankType = "";
			bankType = $("input[name='bankType']:checked").val();
			var cardDcFlag = "D";
			if (bankType == "" || bankType == undefined) {
				alert("请选择充值银行");
				return;
			} */
			
			/*
			if (!window.confirm("确定进行帐户充值")) {
				return;
			}
*/
			var money = $("#money").val();
			var type = "";
			
		         $("#int_money").val(money);
		         $("#int_openBankId").val($(".yhChecked").data("id"));
		         $("#int_usrCustId").val($("#usrCustId").val());
		 		 $("#int_cardDcFlag").val("D");
		         
		         $("#f_addRechargeInfo").submit();
		       
		}
</script>
<script src="http://libs.baidu.com/jqmobi/1.0.0/jq.mobi.min.js "></script>
<script type="text/javascript">
$(".worksbox a").click(function(){
	var $this=$(this);
	$(".worksbox-ok").removeClass("worksbox-ok");
	$this.addClass("worksbox-ok");
});
$(".submenu-navigation .submenu-nav-items a").click(function(){
	var $this=$(this);
	$(".yhChecked").removeClass("yhChecked");
	$(".submenu-navigation .submenu-nav-deploy").text($this.text());
	$this.addClass("yhChecked");
	$(".submenu-nav-deploy").click();
});
</script>
<script type="text/javascript">
    var usrCustId="${session.user.usrCustId}";
    if(usrCustId==''||(usrCustId-0)<=0){
        $("#wenxts1").css("display", "block");
        $('.bg').fadeIn(200);
        $('#wenxts1').fadeIn(400);
    }else{
        $("#wenxts").css("display", "block");
        $('.bg').fadeIn(200);
        $('#wenxts').fadeIn(400);
    }

function closexx(){
	$("#wenxts").hide();
	$('.bg').hide();
	$('#wenxts').hide();
}
</script>
</html>
