<%--
  Created by IntelliJ IDEA.
  User: liuwtmf
  Date: 2014/10/29
  Time: 15:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh">
    <head>
    <jsp:include page="/include/cf-head.jsp"></jsp:include>
    <title>彩富人生增值计划</title>
    <style>
/*投资减免物业费*/
* { margin:0; padding:0; }
.el_container { font-size:16px; width:974px; }
#banner-fade { overflow:hidden; }
ul.slider{position:relative; list-style:none;padding:0;margin:0;overflow:hidden; display:none;}
li.slider-slide{position:absolute; display:none;}
ul.slider-controls{list-style:none;margin:0;padding:0;z-index:9999;}
ul.slider-controls.v-centered li a{position:absolute; background:url(/color/images/icons_cf_web.png) 0 0 no-repeat; text-indent:-100px; width:11px; overflow:hidden; height:18px; }

ul.slider-controls.v-centered li.slider-next a{right:20px; background-position:-102px 0;}
ul.slider-controls.v-centered li.slider-prev a{left:20px;}
ol.slider-markers{list-style: none; padding: 0; margin: 0; width:100%;}
ol.slider-markers.h-centered{text-align: center;}
ol.slider-markers li{display:inline;}
ol.slider-markers li a{display:-moz-inline-stack; display:inline-block;zoom:1;*display:inline;}
p.slider-caption{display:block;width:96%;margin:0;padding:2%;position:absolute;bottom:0;background: rgba(255,255,255,0.5);}
ul.slider-controls.v-centered li a{display:block; color:#000;text-decoration: none;}
ol.slider-markers { position:absolute; top:320px; }
ol.slider-markers li a{background:#fff;color:#fff;margin:5px;text-decoration: none; text-indent:-100px; overflow:hidden; width:10px; height:10px; border-radius:100%; }
ol.slider-markers li.active-marker a,
ol.slider-markers li a:hover{background: #ff6600;}
.cf-header { background:url(/color/images/cf_invest_line.gif) left bottom repeat-x; height:122px; overflow:hidden; margin-bottom:20px; }
.cf-header-content { width:974px; margin:0 auto; padding-top:20px; }
.cf-logo { position:relative; top:20px; }
.slider {width: 100%;overflow: hidden;margin: 0 auto;-webkit-transform: translateZ(0); position:relative; }
.slider-content {width: 100%;display:none;}
.slider-content:after {content: '';display: block;clear: both;height: 0;}
.slider-item {float: left;width: 100%;}
.slider-item a { display:block; overflow:hidden; }
.slider-item img { width:100%; }
.slider-pointer { position:absolute; left:0; bottom:5px; width:100%; text-align:center; }
.slider-pointer span { display:block; background:#fff; width:7px; height:7px; border-radius:100%; border-radius:#000 solid 1px; margin-left:5px; display:-moz-inline-stack; display:inline-block;zoom:1;*display:inline; }
.slider-pointer span.current { background:#f68147; }
.slider-btns { display:none; }
.slider-btn { display:block; position:absolute; top:50%; margin-top:-20px; height:40px; width:25px; z-index:99999; }
.slider-btn span { width:11px; height:19px; display:block;position:absolute; top:50%; left:50%; margin-left:-6.5px; margin-top:-9.5px; background:url(/color/images/icons_cf_web.png) no-repeat; background-size:18.75em 5.5em; }
.slider-prev { left:10px; }
.slider-prev span { background-position:-0.6875em 0; }
.slider-next { right:10px; }
.slider-next span {  background-position:-3.875em 0; }
.cf-invest-banner { height:96px; }
.cf-invest-hr { font-size:0.75em; color:#999; border:#eee solid 1px; margin:10px 0; padding:10px; position:relative; }
.cf-invest-hr p { padding:3px 0; color:#777; }
.cf-invest-hr p span { padding-left:10px; }
.bind-bank { display:block; padding:5px 10px; background:#ff7f00; color:#fff; width:150px; text-align:center; position:absolute; right:8px; top:8px; }
.bind-bank2 { position:static; margin:20px auto 0 auto; }
.cf-invest-info { height:40px; padding:15px 15px 10px 15px; border:#eee solid 1px; }
.cf-invest-info ul { width:950px; _width:942px; overflow:hidden; }
.cf-invest-info ul li { width:235px; height:40px; float:left; text-align:center; overflow:hidden; }
.cf-invest-info ul li span { font-size:0.75em; display:block; color:#9fa0a0; padding-bottom:5px; }
.cf-invest-info ul li strong { font-size:0.875em; display:block; color:#fd6b00; font-weight:normal; }

.cf-invest-nav { padding-top:10px; height:280px; }
.cf-invest-nav ul { border-top:#eee solid 1px; height:208px; }
.cf-invest-nav ul li { width:486px; border-bottom:#eee solid 1px; height:117px; float:left; background:url(/color/images/cf_step.png) left top no-repeat; position:relative; border-right:#eee solid 1px;  }
.cf-invest-nav ul li a { display:block; height:102px; width:100%; float:left; cursor:default; }
.cf-invest-nav ul li .icon { display:block; height:42px; width:42px; margin:0 auto; overflow:hidden; background:url(/color/images/icons_cf_web.png) left top no-repeat; _background:url(/color/images/icons_cf_web.gif) left top no-repeat; margin-top:14px; }
.cf-invest-nav ul li strong { color:#ccc; display:block; height:30px; line-height:30px; text-align:center; padding-top:10px;  }
.cf-invest-nav ul li i { height:18px; width:18px; overflow:hidden;background:url(/color/images/icons_cf_web.png) -311px 0 no-repeat; display:block; position:absolute; right:1px; bottom:1px; }
.cf-invest-nav ul li i.checked { background-position:-207px 0; }
.cf-invest-nav ul li.step-1 { background-position:0 0; }
.cf-invest-nav ul li.step-1 .icon { background-position:0 -83px; }
.cf-invest-nav ul li.step-1 .icon-1 { background-position:0 -33px; }
.cf-invest-nav ul li.step-1 .text-1 { color:#898989 }
.cf-invest-nav ul li.step-2 { background-position:0 -250px; }
.cf-invest-nav ul li.step-2 .icon { background-position:-207px -83px; }
.cf-invest-nav ul li.step-2 .icon-2 { background-position:-207px -33px; }
.cf-invest-nav ul li.step-2 .text-2 { color:#898989 }
.cf-invest-nav ul li.step-3 { background-position:0 -119px; }
.cf-invest-nav ul li.step-3 .icon { background-position:-311px -83px; }
.cf-invest-nav ul li.step-3 .icon-3 { background-position:-311px -33px; }
.cf-invest-nav ul li.step-3 .text-3 { color:#898989 }
.cf-invest-nav ul li.step-4 { background-position:0 -369px; }
.cf-invest-nav ul li.step-4 .icon  { background-position:-103px -83px; }
.cf-invest-nav ul li.step-4 .icon-4 { background-position:-103px -33px; }
.cf-invest-nav ul li.step-4 .text-4 { color:#898989 }
.cf-tip { width:400px; background:#fff; border-radius:5px; box-shadow:0 0 10px rgba(0,0,0,0.3); overflow:hidden; }
.cf-tip-title { border-bottom:#ddd solid 1px; height:35px; line-height:35px; padding:0 15px; color:#4d4d4d; font-weight:bold; position:relative; }
.cf-tip-close { display:block; position:absolute; right:12px; top:12px; width:12px; height:12px; background:url(/color/images/close_bg.gif) -30px 0 no-repeat; overflow:hidden; }
.cf-tip-close:hover { background-position:0 0; }
.cf-tip-content { padding:20px 15px; }
.cf-tip-content ul li { line-height:40px; border:#ccc dashed 1px; padding:5px 10px; font-size:14px; border-radius:3px; }
.cf-tip-content ul li span,.cf-tip-content ul li i { padding:0 15px 0 0; font-size:12px; }
.cf-tip-content ul li i { color:#000; font-weight:bold; }
.cf-tip-content ul li em { font-weight:bold; color:#333; margin-right:10px; }
</style>
    </head>
    <body>
    <div class="cf-header">
    	<div class="cf-header-content">
        	<img src="/color/images/cf-logo.gif" />
        </div>
    </div>
    <div class="s_sur_ix main el_container" style=" overflow:hidden;margin:0 auto;padding:0; "> 
  <div id="banner-fade"> 
     <img src="/color/images/banner_web.jpg" />
  </div>
  
  <div class="cf-invest-hr">
    <!--<p>我的物业:<span>${activityOrderDo.cName }</span></p>-->
    
    <p><s:if test="#request.activityOrderDo.billingAddressText  != null ">
                 	${activityOrderDo.billingAddressText}
        </s:if>
        <s:else>
        	冲抵物业费地址:
        </s:else>
        <span>${activityOrderDo.billingAddress }
        	<s:if test="#request.activityOrderDo.carNumber  != null ">
                 	&nbsp;&nbsp;&nbsp;&nbsp; ${activityOrderDo.carNumber}
            </s:if> 
        </span>
        <s:if test="#request.bankList!=null||#request.bankList.size!=0">
        <a href="#" id="bindBank" class='bind-bank  hasBind'>查看提现银行卡</a>
        </s:if>
        <s:else>
        <a href="/bindCardInit.do" id="bindBank" class='bind-bank' target="_blank">绑定提现银行卡</a>
        </s:else>
    </p>
     <div style="display:none;">
            <div class="cf-tip" id="bindBankContent">
            <div class="cf-tip-title">查看提现银行卡<a href="#" class='cf-tip-close'></a></div>
            <div class="cf-tip-content">
            	<ul>
                	<s:iterator value="#request.bankList" var="var">
                	<li><p><i><s:property value="#var.bankName"></s:property> </i></p>
                    	<p><span>户名:</span><em><s:property value="#var.cardUserName"></s:property> </em><span>卡号:</span><em><s:property value="#var.cardNo"></s:property> </em></p>
                    </li>
                    </s:iterator>
            	</ul>
                <p><a href="/bindCardInit.do" class="bind-bank bind-bank2"  target="_blank">新增提现银行卡</a>
            </div>
         </div>
     </div>
  </div>
  <div class="cf-invest-info">
    <ul>
      <li><span><s:if test="#request.activityOrderDo.deductPerMonthText != null ">
                 	${activityOrderDo.deductPerMonthText}
        </s:if>
        <s:else>
        	每月冲抵物业费
        </s:else></span><strong><fmt:formatNumber value="${activityOrderDo.deductPerMonth }" type="currency"/> 元</strong></li>
      <li><span><s:if test="#request.activityOrderDo.billingDateText != null ">
                 	${activityOrderDo.billingDateText}
	        </s:if>
	        <s:else>
	        	冲抵周期
	        </s:else></span><strong><fmt:formatDate value="${activityOrderDo.beginDate }" pattern="yyyy-MM-dd"/> 至 <fmt:formatDate value="${activityOrderDo.endDate }" pattern="yyyy-MM-dd"/></strong></li>
      <li><span>保本保息收益</span><strong><fmt:formatNumber value="${activityOrderDo.profit }" type="currency"/> 元</strong></li>
      <li><span>您需要预存金额</span><strong><fmt:formatNumber value="${activityOrderDo.investAmount }" type="currency"/> 元</strong></li>
    </ul>
  </div>
  <s:if test="#request.title!=null&&#request.title!=''">
  	<div class="cf-tip" id="cfTip">
    	<div class="cf-tip-title">提示：<a href="#" class='cf-tip-close'></a></div>
    	<div class="cf-tip-content">${title}</div>
     </div>
  </s:if>
  <div style="display:none;">
  		<div class="cf-tip" id="cfTipComplete">
    	<div class="cf-tip-title">提示：<a href="#" class='cf-tip-close'></a></div>
    	<div class="cf-tip-content">预存成功，请关闭当前页面查看您的预存收益情况。</div>
     </div>
  </div>
  <div class="cf-invest-nav">
    <ul id="step">
      <li class='step-1'> <a href="#" data-step="1"> <span class='icon'></span> <strong>注册汇付天下</strong> </a> <i class='<s:if test="#request.userinfo.usrCustId>0">checked</s:if>'></i> </li>
      
      <li class='step-2'> <a href="#" data-step="2"> <span class='icon'></span> <strong>设定委托划款协议</strong> </a> <i class='<s:if test="#request.hasAuth">checked</s:if>'></i> </li>
      
      <li class='step-3'> <a href="#" data-step="3"> <span class='icon'></span> <strong>设定自动投标</strong> </a> <i class='<s:if test="#request.bidStatus==2">checked</s:if>'></i> </li>
      
      <li class='step-4'> <a href="#" data-step="4"> <span class='icon'></span><strong>充值</strong> </a> <i class='<s:if test="#request.hasRecharge">checked</s:if>'></i> </li>
    </ul>
    <form action="/addRecharge1.do" method="post" id="f_addRechargeInfo" >
        <input type="hidden" id="int_money" name="money" value="${wyf_recharge_money}"/>
        <input type="hidden"  name="ordId" value="${activityOrderDo.ordId}"/>
        <input type="hidden"  name="type" value="wyf"/>
    </form>
    <form action="/transferAuth.do" method="post" id="f_transferAuth" >
        <input type="hidden" name="ordId" value="${activityOrderDo.ordId}"/>
    </form>
    <%--${activityOrderDo}--%>
    <div id="autoDiv" style="display: none;"></div>
  </div>
    </div>
    <script type="text/javascript" src="/wap/mobile/scripts/common/slider_web.js"></script>
    <script src="/script/jquery.lightbox.js"></script>
    <script>
	$(function() {
		function recharge(){
        	$("#f_addRechargeInfo").submit();
		}
		function zxtb(){
            var param={};
            param["paramMap.rateStart"] = 9.9;
			$.post("/automaticBidModify.do", param, function(data) {
				if (data.length < 20) {
					alert(data);
					return false;
				}
				$("#autoDiv").html(data);
			});
		}
		function sq(){
			$("#f_transferAuth").submit();
		}
		
	  <s:if test="#request.title!=null&&#request.title!=''">
	    var cfTip = $("#cfTip");
	  	cfTip.lightbox_me({
			centered: true,
			onLoad: function() {
				$(".cf-tip-close").on("click",function(){
					cfTip.trigger('close');
					return false;
				});
			}
		});
	  </s:if>
	  var complete = "${activityOrderDo.ordStatus}";
	  var step =1;
	  if(complete==1){
		 var cfTipComplete = $("#cfTipComplete");
		 cfTipComplete.lightbox_me({
			centered: true,
			onLoad: function() {
				$(".cf-tip-close").on("click",function(){
					cfTipComplete.trigger('close');
					return false;
				});
			}
		 });
		 step = 5;
		 $("#step").find("i").addClass("checked");
	  }else{
          step = "${step}";
      }
	  
	  var steps = $("#step").find("a");
	  for(var i=1,ilen=steps.length; i<=ilen; i++){
		var current = steps.eq(i-1);
		if(i==step){
			current.css({"cursor":"pointer"});
			current.find('.icon').addClass("icon-"+i);
			current.find("strong").addClass("text-"+i);
			if(i==1){
				current.on("click",function(){
					location.href = "/registerChinaPnr.do";
					return false;
				});
			} else if(i==2){
				current.on("click",function(){
                    sq();
					return false;
				});
			} else if(i==3){
				current.on("click",function(){
                    zxtb();
					return false;
				});
			} else if(i==4){
				current.on("click",function(){
                    recharge();
					return false;
				});
			}
		}
	  }
	  $("#step").on("click","a",function(){
		 var index = $(this).data("step");
		 if(step==1){
			if(index>1){
				alert('请先完成\"注册汇付天下\"');
				return false;
			}
		 } else if(step==2){
			if(index>2){ 

                alert('请先完成\"设定委托划款协议\"');
				return false; 
			}; 
		 } else if(step==3){
			 if(index>3){
                 alert('请先完成\"设定自动投标\"');
			 	return false;
			 }
		 } else if(step==4){
			 if(index>4){
                 alert('请先完成\"充值\"');
			 	return false;
			 }
		 }
		 return false;
	  });
	  //slider
	  /*
	  $('#banner-fade').slider({
		animtype:'slide',
		height:350,
		width:974,
		responsive:true,
		showcontrols:true
	  });
	  */
	  $("#bindBank").on('click',function(){
		  if($(this).hasClass("hasBind")){
			var bindBankContent = $("#bindBankContent");
			bindBankContent.lightbox_me({
				centered: true,
				onLoad: function() {
					$(".cf-tip-close").on("click",function(){
						bindBankContent.trigger('close');
						return false;
					});
				}
			});
			return false;  
		  }
	  });
	});
	</script>
</body>
</html>