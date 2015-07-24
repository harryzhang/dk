<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>

<div class="tc_wenxts" id="hftx_div" style="display:none; ">
  <div style=" width:80%; margin:0px auto;text-align:center; color:#FFF">汇付天下托管账户系统，是汇付天下为P2P平台提供的资金账户体系，全面管理平台资金，帮助平台实现收款、付款、用户账户间资金划拨以及账户资金理财功能；同时，汇付天下保障用户账户中的资金独立存放，用户可通过P2P平台对本人汇付账户进行充值、提现，商户在未经用户的许可、授权或法律的规定的情况下，无权动用，从而保证投资人资金安全。</div>
  <div style=" width:80%; margin:10px auto 40px auto; padding:10px 0px;" class="button-green" > <a style=" text-align:center; color:#FFF" onclick="zchf();" href="javascript:;">注册汇付天下</a></div>
  <div style=" width:80%; margin:0px auto; text-align:center; color:#FFF"><a href="webapp-index.do" style=" color:#FF0">暂不注册，先逛逛</a></div>
</div>
<div class="tc_wenxts" id="tjr_div" style="display:none; ">
  <div style=" width:80%; margin:0px auto;text-align:center; color:#FFF"> 
  <a>请填写您的推荐人:</a>
    <input type="text" style="display: inline;height: 38px; line-height:38px; width:100%; padding-left:10px;" id="tjr">
    <a id="tjr-tip"></a>
  </div>
<div style=" width:80%; margin:0px auto;margin-top:20px;">
<div style=" width:50%; float:left">
    <div style="padding:10px 50px; margin-right:20px;" class="button-green" > 
    <a style=" text-align:center; color:#FFF" onclick="saveTjr();" href="javascript:;">保存</a> </div>
    </div>
    <div style=" width:50%; float:left">
    <div style="  padding:10px 50px;" class="button-green" > 
    <a style=" text-align:center; color:#FFF" onclick="tiaoguo();" href="javascript:;">跳过</a> </div></div>
</div>
</div>
<div class="bg"></div>
<script type="text/javascript">
var usrCustId="${session.user.usrCustId}";
if(usrCustId==''||(usrCustId-0)<=0){
	$("#hftx_div").css("display", "block");
	$('.bg').fadeIn(200);
	$('#hftx_div').fadeIn(400);
}else{
}
function zchf(){
	 if(('${session.user.refferee}'-0)>0){
		window.location.href="/registerChinaPnr.do";
	}else{
		//var ref = prompt("");
		$("#hftx_div").hide();
		$("#tjr_div").css("display", "block");
	/* 	$.post("/setMyReferee.do",{"paramMap.refferee":ref},function(data){
			if(data>0){
				alert("设置成功！");
				window.location.href="/registerChinaPnr.do";
			}else{
				alert("设置不成功！");
			}
			
		}); */
	} 
}
function saveTjr(){
	$("#tjr-tip").text("");
	var trj=$.trim($("#tjr").val());
	if(trj!=''){
		$.post("/setMyReferee.do",{"paramMap.refferee":trj},function(data){
			if(data>0){
				//alert("设置成功！");
				window.location.href="/registerChinaPnr.do";
			}else{
				$("#tjr-tip").text("推荐人不存在！");
			}
			
		}); 
	}else{
		$("#tjr-tip").text("推荐人不存在！");
	}
}
function tiaoguo(){
	window.location.href="/registerChinaPnr.do";
}
</script>