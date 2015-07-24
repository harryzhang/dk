<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>注册汇付天下</title>
<link type="text/css" rel="stylesheet" href="http://static.hehenian.com/m/css/main.min.css">
<script src="http://static.hehenian.com/m/common/zepto.min.js"></script>
<script src="http://static.hehenian.com/m/js/main.min.js"></script>
</head>
<script type="text/javascript">
	$(function() {
		var reg = $("#btnReg"),tips = $("#huifuTips"),tj=$("#huifuTj"),save = $("#btnSave");
		reg.click(function(){
			tips.hide();
			tj.show();
			return false;
		});
		
		var form = $("#form");
		var tuijianren = $("#tuijianren"),input = $("#tuijianren");
		save.click(function(){
			form.validate({
				error:function(text,setting,input,$form){
					//HHN.popup({type:"alert",content:text,element:input});
					console.info("error");
					input.focus();
				},
				success:function($form){
					var url = $form.attr("action");
					var data = $form.serialize();
					$.ajax({
						type: 'POST',
						url: url+"?_="+new Date().getTime(),
						data:data,
						beforeSend: function(){
							//HHN.popup({type:"loading"});
							console.info("loading");
						},
						success: function(data){
							if(data>0){
								//HHN.popLoading("/registerChinaPnr.do","保存中...")
								console.info("保存中...");
							}else if(data=="-3"){
	                            //HHN.popup({type:"alert",content:'推荐人不能为自己!'});
								console.info("推荐人不能为自己");
	                        }else{
								//HHN.popup({type:"alert",content:'推荐人不存在!'});
	                        	console.info("推荐人不存在");
							}
						},
						error: function(xhr, type){
							//HHN.popup({type:"alert",content:"服务器繁忙，请稍后重试!"});
							console.info("服务器繁忙，请稍后重试");
						}
					});
				}
			});
			return false;
		});
	});
</script>
<body>
<h1 class="t title"><span>注册汇付天下</span></h1>
<!-- <div class="wrap animate-waves" id="wrap" style="display:none;"> -->
<div class="wrap animate-waves">
  <div class="pd huifu">
    <div class="huifu-tip p" id="huifuTips">
      <p>汇付天下托管账户系统，是汇付天下为P2P平台提供的资金账户体系，全面管理平台资金，帮助平台实现收款、付款、用户账户间资金划拨以及账户资金理财功能；同时，汇付天下保障用户账户中的资金独立存放，用户可通过P2P平台对本人汇付账户进行充值、提现，商户在未经用户的许可、授权或法律的规定的情况下，无权动用，从而保证投资人资金安全。
        注册汇付天下</p>
      <div class="huifu-btn"> <a href="#" class="btn-a" id="btnReg">注册汇付天下</a> <a href="http://m.hehenian.com/product/index.do" class="btn-c">暂不注册，先逛逛</a> </div>
    </div>
    <div class="huifu-tj" id="huifuTj">
    <form action="/setMyReferee.do" id="form">
      <label class="text" >请填写推荐人</label>
      <div class="input-item">
        <input type="tel" value="" placeholder="推荐人ID或者手机号码" name="paramMap.refferee" id="tuijianren" validate="true" rule="{type:'number',empty:false}" tips="{number:'请输入正确的推荐人ID或者手机号码',empty:'请填写推荐人'}" />
      </div>
      <div class="huifu-btn"> <a href="#" class="btn-a" id="btnSave">保存</a> <a href="http://m.hehenian.com/chinapnr/registerChinaPnr.do" class="btn-c">跳过</a> </div>
    </form>
    </div>
  </div>
</div>
<%@ include file="../common/tail.jsp" %>
</body>
</html>