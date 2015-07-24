<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="/include/mobile/wyf-head.jsp"></jsp:include>
<title>彩富人生增值计划</title>
<style>
/*投资减免物业费*/
.cf-invest-banner { height:96px; }
.cf-invest-info { height:135px; padding:10px 15px 0 15px; overflow:hidden; }
.cf-invest-info ul { width:100%; }
.cf-invest-info ul li { width:50%; height:70px; float:left; }
.cf-invest-info ul li span { font-size:0.75em; display:block; color:#9fa0a0; padding-bottom:5px; }
.cf-invest-info ul li strong { font-size:0.875em; display:block; color:#fd6b00; font-weight:normal; }

.cf-invest-hr { font-size:0.75em; color:#999; border-bottom:#eee solid 1px; padding:10px 15px }
.cf-invest-hr p { padding:3px 0; color:#777 }
.cf-invest-hr p span { padding-left:10px; }

.cf-invest-nav { height:200px; }
.cf-invest-nav ul { border-top:#eee solid 1px; }
.cf-invest-nav ul li { width:50%; border-bottom:#eee solid 1px; height:117px; float:left; background:url(/color/images/cf_step.png) left top no-repeat;  overflow:hidden; }
.cf-invest-nav ul li a { display:block; height:116px; float:left; width:100%;  cursor:default; position:relative;}
.cf-invest-nav ul li .icon { display:block; height:42px; width:42px; margin:0 auto; overflow:hidden; background:url(/color/images/icons_cf.png) left top no-repeat; background-size:18.75em 10.9375em; margin-top:14px; }
.cf-invest-nav ul li strong { color:#898989; display:block; height:30px; line-height:30px; text-align:center; padding-top:10px; }
.cf-invest-nav ul li i { height:18px; width:18px; overflow:hidden;background:url(/color/images/icons_cf.png) -10.4375em 0 no-repeat; background-size:18.75em 10.9375em; display:block; position:absolute; right:1px; bottom:1px; }
.cf-invest-nav ul li i.checked { background-position:-7.15625em 0; }
.cf-invest-nav ul li.step-1 { background-position:0 0; }
.cf-invest-nav ul li.step-1 .icon { background-position:-0.6875em -7.875em; }
.cf-invest-nav ul li.step-1 .icon-1 { background-position:-0.6875em -3.28125em; }
.cf-invest-nav ul li.step-1 .text-1 { color:#898989 }
.cf-invest-nav ul li.step-2 { background-position:0 -250px; }
.cf-invest-nav ul li.step-2 .icon { background-position:-7.15625em -7.875em; }
.cf-invest-nav ul li.step-2 .icon-2 { background-position:-3.90625em -3.28125em; }
.cf-invest-nav ul li.step-2 .text-2 { color:#898989 }
.cf-invest-nav ul li.step-3 { background-position:0 -119px; }
.cf-invest-nav ul li.step-3 .icon { background-position:-10.4375em -7.875em; }
.cf-invest-nav ul li.step-3 .icon-3 { background-position:-7.15625em -3.28125em; }
.cf-invest-nav ul li.step-3 .text-3 { color:#898989 }
.cf-invest-nav ul li.step-4 { background-position:0 -369px; }
.cf-invest-nav ul li.step-4 .icon  { background-position:-3.90625em -7.875em; }
.cf-invest-nav ul li.step-4 .icon-4 { background-position:-10.4375em -3.28125em; }
.cf-invest-nav ul li.step-4 .text-4 { color:#898989 }
.cf-tip { border-top:#eee solid 1px; color:#e84c3d; font-size:12px; padding:10px 15px; }
.popup-text { box-shadow:0 0 10px rgba(0,0,0,0.3); }
.popup-text .popup-title a.close { position:absolute; right:12px; top:9px; width:12px; height:12px; background:url(/color/images/close_bg.gif) -30px 0 no-repeat; text-indent:-100px; border-left:none; overflow:hidden; }
.popup-text .popup-title a.close:hover { background-position:0 0; }
.popup-text .popup-main { padding:20px 10px; }
.popup-text ul li { line-height:25px; border:#ccc dashed 1px; padding:5px 10px; font-size:12px; border-radius:3px; }
.popup-text ul li p { margin-bottom:10px; padding:0; }
.popup-text ul li span,.cf-tip-content ul li i { padding:0 5px 0 0; font-size:12px; }
.popup-text ul li i { color:#000; font-weight:bold; }
.popup-text ul li em { font-weight:bold; color:#333; margin-right:10px; }

.bindBankDiv { padding:0 10px 20px 10px; }
.bind-bank { display:block; padding:5px 10px; background:#ff7f00; color:#fff; width:100%; text-align:center; }
.bind-bank:hover { color:#fff; }
.bind-bank2 { position:static; margin:20px auto 0 auto; }
</style>
</head>
<body class="index">
<jsp:include page="/include/mobile/loading.jsp"></jsp:include>
<div class="wrap" id="wrap" style="display:none;">
  <div class="slider">
    <div class="slider-content" style="display:block;">
      <div class="slider-item"><img src="/color/images/banner_app.jpg" /></div>
    </div>
  </div>
  <div class="cf-invest-hr">
    <!--<p>我的物业:<span>${activityOrderDo.cName }</span></p>-->
    <p>
    	<s:if test="#request.activityOrderDo.billingAddressText  != null ">
                 	${activityOrderDo.billingAddressText}
        </s:if>
        <s:else>
        	冲抵物业费地址:
        </s:else>
      <span>${activityOrderDo.billingAddress }
            <s:if test="#request.activityOrderDo.carNumber  != null ">
                 	&nbsp;&nbsp;&nbsp;&nbsp; ${activityOrderDo.carNumber}
        	</s:if> 
      </span></p>
  </div>
  <div class="cf-invest-info">
    <ul>
      <li><span>
      		<s:if test="#request.activityOrderDo.deductPerMonthText != null ">
                 	${activityOrderDo.deductPerMonthText}
        </s:if>
        <s:else>
        	每月冲抵物业费
        </s:else>
          </span><strong><fmt:formatNumber value="${activityOrderDo.deductPerMonth }" type="currency"/> 元</strong></li>
      <li><span>
      		<s:if test="#request.activityOrderDo.billingDateText != null ">
                 	${activityOrderDo.billingDateText}
	        </s:if>
	        <s:else>
	        	冲抵周期
	        </s:else>
          </span><strong><fmt:formatDate value="${activityOrderDo.beginDate }" pattern="yyyy-MM-dd"/> 至 <br/><fmt:formatDate value="${activityOrderDo.endDate }" pattern="yyyy-MM-dd"/></strong></li>
      <li><span>保本保息收益</span><strong><fmt:formatNumber value="${activityOrderDo.profit }" type="currency"/> 元</strong></li>
      <li><span>您需要预存金额</span><strong><fmt:formatNumber value="${activityOrderDo.investAmount }" type="currency"/> 元</strong></li>
    </ul>
  </div>
  <div class="cf-invest-nav">
    <ul id="step">
      <li class='step-1'> <a href="#" data-step="1"> <span class='icon'></span> <strong>注册汇付天下</strong> <i class='<s:if test="#request.userinfo.usrCustId>0">checked</s:if>'></i></a>  </li>
      
      <li class='step-2'> <a href="#" data-step="2"> <span class='icon'></span> <strong>设定委托划款协议</strong> <i class='<s:if test="#request.hasAuth">checked</s:if>'></i></a>  </li>
      
      <li class='step-3'> <a href="#" data-step="3"> <span class='icon'></span> <strong>设定自动投标</strong> <i class='<s:if test="#request.bidStatus==2">checked</s:if>'></i></a>  </li>

        <li class='step-4'> <a href="#" data-step="4"> <span class='icon'></span> <strong>充值 </strong><%--<em style="font-size: 5px;color: red;">快捷支付将收取手续费</em>--%>   <i class='<s:if test="#request.hasRecharge">checked</s:if>'></i></a>  </li>
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
 <div class="bindBankDiv">
     <s:if test="#request.bankList!=null||#request.bankList.size!=0">
        <a href="#" id="bindBank" class='bind-bank hasBind'>查看提现银行卡</a>
     </s:if>
     <s:else>
         <a href="/bindCardInit.do" id="bindBank" class='bind-bank'>绑定提现银行卡</a>
     </s:else>
        <div class="cf-tip" id="bindBankContent" style="display:none;">
            <div class="cf-tip-content">
                <ul>
                   <s:iterator value="#request.bankList" var="var">
                	<li><p><i><s:property value="#var.bankName"></s:property></i></p>
                    	<p><span>户名:</span><em><s:property value="#var.cardUserName"></s:property></em><br/><span>卡号:</span><em><s:property value="#var.cardNo"></s:property></em></p>
                    </li>
                    </s:iterator>
                </ul>
                <p><a href="/bindCardInit.do" class="bind-bank bind-bank2">新增提现银行卡</a>
            </div>
	    </div>
  
  </div>
<jsp:include page="/include/mobile/base-js.jsp"></jsp:include>
<script type="text/javascript" src="/wap/mobile/scripts/common/slider.js"></script>
<script>
Zepto(function(){
	//loading
	HHN.loadPage();
	//slider
	var slider = $("#cfSlider");
	if(slider.length>0){
		var slider = $('.slider-content'); 
		var sliderItem = slider.find(".slider-item");
		var sliderNum = sliderItem.length;
		sliderItem.css({width:(100/sliderNum)+"%"});
		slider.css({width:sliderNum*100+"%"}).show();
		var flipsnap = Flipsnap('.slider-content',{
			auto:true,
			pointBar:false
		});
		var sliderPrev = $(".slider-prev"),sliderNext = $(".slider-next");
		sliderPrev.on("click",function(){
			flipsnap.toPrev();
			return false;
		});
		sliderNext.on("click",function(){
			flipsnap.toNext();
			return false;
		});
	}
	
		function recharge(){
			$("#f_addRechargeInfo").submit();
		}
		function zxtb(){
            var param={};
            param["paramMap.rateStart"] = 9.9;
			$.post("/automaticBidModify.do",param, function(data) {
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
	  <s:if test="#request.title!=null&&#request.title!=''&&#request.title!='操作失败,请稍后再试!'&&#request.title!='操作失败,请稍后再试%21'">
	  HHN.popup({type:"content",content:'${title}',titleText:'提示：',cla:'popup-text',maskClick:true});
	  </s:if>
	  var complete = "${activityOrderDo.ordStatus}";
	  var step =1;
	  if(complete==1){
		 HHN.popup({type:"content",content:'预存成功，请点击顶部关闭图标查看您的预存收益情况。',titleText:'提示：',cla:'popup-text',maskClick:true});
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
				HHN.popup({type:"alert",content:'请先完成\"注册汇付天下\"'});
				return false;
			}
		 } else if(step==2){
			if(index>2){
                HHN.popup({type:"alert",content:'请先完成\"设定委托划款协议\"'});
				return false;
			}; 
		 } else if(step==3){
			 if(index>3){
                 HHN.popup({type:"alert",content:'请先完成\"设定自动投标\"'});
				return false;
			 }
		 } else if(step==4){
			 if(index>4){ 
                HHN.popup({type:"alert",content:'请先完成\"充值\"'});
				return false;
			 }
		 }
		 return false;
	  });
	  
	   $("#bindBank").on('click',function(){
		  if($(this).hasClass("hasBind")){
			var content = $("#bindBankContent");
			 HHN.popup({type:"content",content:content.html(),titleText:'查看提现银行卡',cla:'popup-text',maskClick:true});
			return false;  
		  }
	  });

});
</script>
<script type="text/javascript" src="/wap/mobile/scripts/module/cf_invest.js"></script> 
<script>$.get("/updateUserUsableSum.do?_="+new Date().getTime());</script>
</body>
</html>