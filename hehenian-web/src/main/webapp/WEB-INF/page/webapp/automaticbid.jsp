<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="/include/wap-head.jsp"></jsp:include>
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

<jsp:include page="/include/wap-bottom.jsp"></jsp:include>

<!-- main -->
<div id="main-wrap"  style=" background:#f3f1ef; border:0px;">
  <div id="container">
    <div class="content">
       <div class="section-title" style=" padding:10px 0px;; background-color:#50c2e9;border-top:#6fd0f0 1px solid;border-bottom:#32aadf 1px solid; color:#FFF; font-weight:bold; font-size:16px; text-align:center;">
        设置自动投标
      </div>
      <div class="one-half-responsive">
        <div class="container" >
          <div class="submenu-navigation" style=" background:#f3f1ef"> 
          <a href="#" class="deploy-toggle-3" style=" background:#eeeeee">您的可用余额：${automaticBidMap.usableSum}元
            <%-- <s:if test="%{automaticBidMap.bidStatus ==2}"> 
            <em class="toggle-3-active-background" onclick="closeZdtb();">
            <strong class="toggle-3-active-ball" ></strong>
            </em>
            </s:if>
            <s:else> 
            <em onclick="openZdtb();">
            <strong ></strong>
             </em>
            </s:else> --%>
            
         </a>
          
            <div style=" padding:20px 0px; border-top:1px solid #ccc">
              <div class="formFieldWrap">
                <label class="field-title contactNameField" for="contactNameField"></label>
               
              </div>
              <div class="formFieldWrap">
                <label class="field-title contactNameField" for="contactNameField">账户保留金额(元)：<span></span></label>
                <input type="text" data="${automaticBidMap.remandAmount}" name="contactNameField" value="${automaticBidMap.remandAmount==''?0:automaticBidMap.remandAmount}" class="contactField requiredField" id="remandAmount"/>
              </div>
             <!--  <div class="formFieldWrap">
                <label class="field-title contactNameField" for="contactNameField">每次（最大）投资金额：</label>
                <input type="text" name="contactNameField" value="" class="contactField requiredField" id="contactNameField"/>
              </div> -->
              <div class="formFieldWrap">
                <label class="field-title contactNameField" for="contactNameField">利率范围(0-24%)：</label>
              </div>
               <div class="formFieldWrap">
                   <div style=" width:47%; float:left">
                    <input type="text" name="contactNameField" value="${automaticBidMap.rateStart == '' ? 0:automaticBidMap.rateStart}" class="contactField requiredField" id="rateStart" style=" padding-left:10px;"/>
                   </div>
                 <div style=" width:6%; float:left; line-height:34px; text-align:center;">-</div>
                 <div style=" width:47%; float:left"><input type="text" name="contactNameField" value="${automaticBidMap.rateEnd== '' ? 24:automaticBidMap.rateEnd}" class="contactField requiredField" id="rateEnd"  style=" padding-left:10px;"/>
                   </div>
              </div>
               <div class="formFieldWrap">
                <label class="field-title contactNameField" for="contactNameField">借款期限：</label>
              </div>
               <div class="formFieldWrap">
                <div class="one-half-responsive">
                    <div class="container">
                        <div class="submenu-navigation" style=" background:#f3f1ef">
                        	<div style=" float:left; width:47%;border:1px solid #cacaca; background:#f5f5f5">
                            <a href="#" class="submenu-nav-deploy" id="deadlineStart">${automaticBidMap.deadlineStart==''?'1':automaticBidMap.deadlineStart}个月</a>
                            <div class="submenu-nav-items">
                                <a href="#">6个月</a>
                                <a href="#">12个月</a>
                                <a href="#">18个月</a>
                                <a href="#">24个月</a>
                                <a href="#">36个月</a>
                            </div>
                            </div>
                             <div style=" width:6%; float:left; line-height:34px; text-align:center;">-</div>
                            <div style=" float:left; width:47%;border:1px solid #cacaca; background:#f5f5f5">
                                  <a href="#" class="submenu-nav-deploy" id="deadlineEnd">${automaticBidMap.deadlineEnd==''?'36':automaticBidMap.deadlineEnd}个月</a>
                            <div class="submenu-nav-items">
                                 <a href="#">6个月</a>
                                <a href="#">12个月</a>
                                <a href="#">18个月</a>
                                <a href="#">24个月</a>
                                <a href="#">36个月</a>
                            </div></div>
                        </div>
                    </div>
               </div>
           
	
             <!--   <s:select cssStyle="width: 70px; border:1px solid #e5e5e5"
									list="#{1:'1个月',2:'2个月',3:'3个月',4:'4个月',5:'5个月',6:'6个月',7:'7个月',8:'8个月',9:'9个月',10:'10个月',11:'11个月',12:'12个月',18:'18个月',24:'24个月',30:'30个月',36:'36个月'}"
									id="deadlineStart" cssClass="sel_70" listKey="key" listValue="value" name="automaticBidMap.deadlineStart"></s:select>
                    --&nbsp;
                    <s:select
								cssStyle="width: 70px; border:1px solid #e5e5e5"	list="#{1:'1个月',2:'2个月',3:'3个月',4:'4个月',5:'5个月',6:'6个月',7:'7个月',8:'8个月',9:'9个月',10:'10个月',11:'11个月',12:'12个月',18:'18个月',24:'24个月',30:'30个月',36:'36个月'}"
									id="deadlineEnd" cssClass="sel_70" listKey="key" listValue="value" name="automaticBidMap.deadlineEnd" headerKey="36" headerValue="36个月"></s:select>-->
              </div>
                  <div style=" margin:0px auto; text-align:center; margin-top:15px;"><s:if test="%{automaticBidMap.bidStatus ==2}"> 
            <span>
            <a  onclick="closeZdtb();" class="button-big button-turqoise" style="color:#FFF">已开启，点击关闭</a> 
            </span>
            </s:if>
            <s:else> 
             <span> <a  onclick="openZdtb();" class="button-big button-dark" style="color:#FFF">已关闭，点击开启</a> </span>
            </s:else></div>
             <%--  <s:if test="%{automaticBidMap.bidStatus ==2}"> <a href="javascript:;" class="button-big button-orange" onclick="closeZdtb();"  style=" width:100%; color:#FFF">关闭自动投资</a> 
                  </s:if>
                  <s:else> <a href="javascript:;" class="button-big button-green" onclick="openZdtb();"  style=" width:100%; color:#FFF">开启自动投资</a> 
                  </s:else> --%>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
    <div class="container no-bottom"  style="padding: 0px 10px;">
    <div class="section-title"> <strong><img src="/wap/images/misc/icons/leaf.png" width="20" alt="img"></strong>
      <h3 style=" text-align:left">自动投资工具说明</h3>
    </div>
    <style>
	li { list-style:none;}
	</style>
     <ul>
                  <li>自动投资设置完成后即生效；</li>
                  <li style=" margin-bottom:20px;">自动投资工具投资规则如下：
                    <ul style=" margin-left:20px;">
                      <li>投资序列按照开启自动投资的时间先后进行排序;</li>
                      <li>帐户可用余额&lt;100时，不执行自动投资；</li>
                      </ul></li>
                  <li>每次（最大）投资金额是默认值，不需要设置；</li>
                </ul>
  </div>
</div>

<input type="hidden" class="inp100x" id="bidAmt" maxlength="20" value="100000" />
<div id="autoDiv" style="display: none;"></div>

<jsp:include page="hftx.jsp"></jsp:include>
</body>
<%--<script type="text/javascript">
var usrCustId="${session.user.usrCustId}";
if(usrCustId==''||(usrCustId-0)<=0){
	$("#wenxts").css("display", "block");
	$('.bg').fadeIn(200);
	$('.tc_wenxts').fadeIn(400);
}

</script>--%>

</body>
<script type="text/javascript">
function closeZdtb() {//关闭自动投资
	param = {};
	param["paramMap.close"] = "close";
	$.post("/automaticBidModify.do", param, function(data) {
		if (data.length < 30) {
			alert(data);
			return false;
		}
		$("#autoDiv").html(data);
	});
};
function openZdtb() {//开启自动投资
	var maxAmt = $("#maxAmt").val();
	var bidAmt = $("#bidAmt").val();
	var rateStart = $("#rateStart").val();
	
	if(isNaN(rateStart)||(rateStart-0)>24||(rateStart-0)<0){
		xxxx("年利率范围为0-24%");
		return;
	}
	var rateEnd = $("#rateEnd").val();
	if(isNaN(rateEnd)||(rateEnd-0)>24||(rateEnd-0)<0){
		xxxx("年利率范围为0-24%");
		return;
	}
	if((rateEnd-0)<(rateStart-0)){
		xxxx("年利率范围为0-24%");
		return;
	}
	/* var reg1 =  /^\d+$/;
	if(rateStart.match(reg1) == null||rateEnd.match(reg1) == null) {
		 xxxx("请输入正确的利率范围");
			return;
	 } */
	var deadlineStart = $("#deadlineStart").text().replace("个月","")-0;
	var deadlineEnd = $("#deadlineEnd").text().replace("个月","")-0;
	if(deadlineStart>deadlineEnd){
		xxxx("请选择正确的借款期限");
		return;
	}
	 
	var remandAmount=$("#remandAmount").val();
	 if (remandAmount==""||isNaN(remandAmount)||(remandAmount-0)<0 ||(remandAmount-0)>999999999) {
		 xxxx("请输入正确的保留金额");
		 return;
	}
	param={};
	param["paramMap.maxAmt"] = maxAmt;
	param["paramMap.remandAmount"] = remandAmount;
	param["paramMap.bidAmt"] = bidAmt;
	param["paramMap.rateStart"] = rateStart;
	param["paramMap.rateEnd"] = rateEnd;
	param["paramMap.deadlineStart"] = deadlineStart;
	param["paramMap.deadlineEnd"] = deadlineEnd;
	$.post("/automaticBidModify.do", param, function(data) {
		if (data.length < 20) {
			xxxx(data);
			return false;
		}
		$("#autoDiv").html(data);
	});
};
$(".submenu-nav-items a").click(function(){
	var $this=$(this);
	$this.parent().parent().find(".submenu-nav-deploy").text($this.text()).trigger("click");
});
function xxxx(msg){
	$("#alertMsg").text(msg);
	$("#wenxts").css("display", "block");
	$('.bg').fadeIn(200);
	$('.tc_wenxts').fadeIn(400);
}
function cloeseAlert(){
	$("#alertMsg").text("");
	$("#wenxts").css("display", "none");
	$('.bg').hide();
	$('.tc_wenxts').hide();
}
</script>
</html>
