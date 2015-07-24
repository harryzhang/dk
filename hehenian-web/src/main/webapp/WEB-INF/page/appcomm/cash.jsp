<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="/include/app-jfq-head.jsp"></jsp:include>
<style>
.bg {
	display: none;
	position: fixed;
	width: 100%;
	height: 100%;
	background: #000;
	z-index: 80;
	top: 0;
	left: 0;
	opacity: 0.9;
}
.tc_wenxts {
	display: none;
	width: 100%;
	position: fixed;
	top: 50%;
	margin-top: -150px;
	z-index: 90;
}
</style>
</head>
<body>
<div id="preloader">
  <div id="status">
    <p class="center-text">正在加载...</p>
  </div>
</div>

<!--顶部底部浮动层-->

<jsp:include page="/include/app-comm.jsp"></jsp:include>

<!-- main -->
<div id="main-wrap">
<div id="container"  style=" padding-left:0px; padding-right:0px;" > 
  
  <!--内容-->
  <div style=" background:#50c2e9; border-bottom:1xp solid #32aadf; padding:50px 30px 20px 30px; color:#FFF;"> <font style=" font-size:12px; line-height:36px;">您可提取的金额为（元）</font><br>
    <font style=" font-size:42px;"><s:property value="#request.usableSum" default="---"></s:property></font></div>
    
    <!--如果没绑定银行卡提示绑定银行卡-->
   
   <s:if test="#request.banks!=null && #request.banks.size>0">
   <table cellspacing="0" class="table" style=" margin-bottom:0px;">
		<s:iterator value="#request.banks" var="bean" status="sta">
			<!-- 并且银行卡的状态为绑定状态 -->
			<s:if test="#bean.cardStatus==1">
		         <tbody>
		         <tr class="even">
		   
		           <td >   <div class="one-half last-column" style=" padding-left:20px;">
                  
                        <a href="#" class="checkbox radio-two ${sta.count==1?'radio-two-checked':''}" onclick="checkRadio(this);" data-id="${bean.id}-${bean.cardNo}">${bean.bankName} 银行账号：
                        <s:property value="#request.bean.cardNo.substring(0,4)+' '+'****' +' ' + #request.bean.cardNo.substring(#request.bean.cardNo.length() - 4)" />
                        </a>
                    </div>
                   
                   <!--<input ${sta.count==1?'checked':''} type="radio" name="wbank" value="${bean.id}-${bean.cardNo}" />--></td>
		         </tr>
		        
	       	    </tbody>
			</s:if>
		</s:iterator>
	</table>
	<div class="toggle-1" style=" width:150px; margin:0px auto 0px auto; font-size:12px;">
                        <a href="#" class="deploy-toggle-1" style=" padding-left:40px;" onclick="bindCard();">新增提现银行卡</a>
                    </div>
     
     <!--绑定银行卡了，显示银行卡，以及新增银行卡-->
    <div class="formFieldWrap" style=" width:80%; margin:50px auto 0px auto">
            <label class="field-title contactNameField" for="contactNameField">提现金额：</label>
            <input id="money" type="text"  class="contactField requiredField" />
            <span style="color: red; float: none;" id="money_tip" class="formtips"></span> </div>
               <div style=" width:80%; margin:10px auto 50px auto; padding:10px 0px;" class="button-green" > <a style=" text-align:center; color:#FFF" onclick="addWithdraw();">提现</a></div>
	</s:if>
        
	<s:else>
		<div style=" width:80%; margin:50px auto 50px auto; padding:10px 0px;" class="button-green" > <a style=" text-align:center; color:#FFF" href="/bindCardInit.do">您需要绑定银行卡才能提现</a></div>
	</s:else>
   
   
   <div style=" width:100%; background:#E8E8E8; padding:20px;">
   	温馨提示：<br>
	1、请先将本人的银行卡与“汇付天下”托管账户进行绑定，否则无法提现；<br>
	2、确保本人的银行卡开户人真实姓名与实名认证的真实姓名一致；<br>
	3、提交提现请求后，1至2个工作日(国家节假日除外)之内将钱转入本人绑定的银行卡账户；<br>
	4、为防止套现，所充资金必须经投标回款后才能取现；<br>
    5、若提现过程中遇到任何问题，请及时联系我们 4008-303-737
   </div>
    
</div>
</div>
<form id="f_addWithdraw"  action="/addWithdraw.do" method="post" >
		 					<input type="hidden" id="int_dealpwd" name="dealpwd" />
		 					<input type="hidden" id="int_money" name="money" />
		 					<input type="hidden" id="int_cellPhone" value="${bindingPhone}" name="cellPhone" />
		 					<input type="hidden" id="int_code" name="code" />
		 					<input type="hidden" id="int_bankId" name="bankId" />
		 					<input type="hidden" id="int_openAcctId" name="openAcctId" />
		 					<input type="hidden" id="int_type" name="type" />
		  </form>
<script type="text/javascript">
function addWithdraw() {
	var bank = $(".radio-two-checked").data("id");//$("input:radio[name='wbank'][checked]").val();
	 if (bank == undefined || bank == '') {
		alert("请设置或者选择提现银行卡信息");
		return;
	} else if ($("#money").val() == "") {
		alert("请填写提现金额");
		return;
	}else if(isNaN($("#money").val())){
		alert("请填写正确的金额");
		return;
	}
	
	var b = bank.indexOf("-");
	var bankId = bank.substring(0, b);
	var openAcctId = bank.substring(b + 1);
	$("#int_money").val($("#money").val());
	$("#int_bankId").val(bankId);
	$("#int_openAcctId").val(openAcctId);
	
	$("#f_addWithdraw").submit();
	
}
function checkRadio(elem){
	$(".checkbox").removeClass("radio-two-checked");
	$(elem).addClass(radio-two-checked);
}
function bindCard(){
	window.location.href="/bindCardInit.do";
	return false;
}
</script>
<div class="tc_wenxts" id="wenxts" style="display:none; ">
  <div style=" width:80%; margin:0px auto;text-align:center; color:#FFF">汇付天下托管账户系统，是汇付天下为P2P平台提供的资金账户体系，全面管理平台资金，帮助平台实现收款、付款、用户账户间资金划拨以及账户资金理财功能；同时，汇付天下保障用户账户中的资金独立存放，用户可通过P2P平台对本人汇付账户进行充值、提现，商户在未经用户的许可、授权或法律的规定的情况下，无权动用，从而保证投资人资金安全。</div>
  <div style=" width:80%; margin:10px auto 40px auto; padding:10px 0px;" class="button-green" > <a style=" text-align:center; color:#FFF" href="/registerChinaPnr.do">注册汇付天下</a></div>
  <div style=" width:80%; margin:0px auto; text-align:center; color:#FFF"><a href="index.do" style=" color:#FF0">暂不注册，先逛逛</a></div>
</div>
<div class="bg"></div>
</body>
<script type="text/javascript">
//var usrCustId="${map.usrCustId}";
//if(usrCustId==''||(usrCustId-0)<=0){
	// $("#wenxts").css("display", "block");
	//$('.bg').fadeIn(200);
	//$('.tc_wenxts').fadeIn(400); 
//}

</script>
<script>
function cz(){
	var idNo=$("#idNo").val();
	var usrCustId=$("#usrCustId").val();
	if(usrCustId==""||(usrCustId-0)<=0){
		alert("请先注册汇付天下");
		window.location.href="/registerChinaPnr.do";
	}else{
		window.location.href="recharge.do";
	}
}
</script>
<script type="text/javascript">
var usrCustId="${session.user.usrCustId}";
if(usrCustId==''||(usrCustId-0)<=0){
	$("#wenxts").css("display", "block");
	$('.bg').fadeIn(200);
	$('.tc_wenxts').fadeIn(400);
}else{
	//$("#xxxx").load("investDetailCount.do");
}
function zhmm(){
	$("#zhmm").submit();
}
</script>
</html>

</body>
</html>
