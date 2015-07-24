<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.hhn.util.DateUtil" %>
<% request.setAttribute("menuIndex", 2); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>${channel_name}-提现申请</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="yes" name="apple-touch-fullscreen">
    <link type="text/css" rel="stylesheet" href="http://static.hehenian.com/m/css/main.min.css">
    <link rel="stylesheet" href="http://static.hehenian.com/m/js/widget/modal/modalmin.css">
    <script src="http://static.hehenian.com/m/common/zepto.min.js"></script>
    <script src="http://static.hehenian.com/m/js/main.min.js"></script>
    <script src="http://static.hehenian.com/m/js/widget/modal/modalmin.js"></script>
</head>

<body class="bg-2">
    <header class="top-bar">
        <a href="http://m.hehenian.com/balance/index.do">
            <span class="icon-back">
		</span>
        </a>
        <p>提现</p>
    </header>
    <nav class="nav-title">
        <p>温馨提示：提现服务时间为每日7:00~23:00</p>
    </nav>
		<c:choose>
        <c:when test="${not empty userCard and not empty bankCode}">
        <%-- <c:when test="${true}"> --%>
        	<%-- <div class="inputDiv" onclick="changeBank();" id="bankNameBox">
	            <span onclick="changeBank();">提现至　</span>
	            <input type="text" value="<c:out value="${userCard.bank_name}"/>(<c:out value="${userCard.card_no}"/>)" disabled="disabled" id="bankName"/>
	            <img class="right" src="http://static.hehenian.com/m/img/right.png"/>
	        </div> --%>
	        <section class="sign-area" style="margin-top: 0;">
		        <div class="sign-style sign-style-flex br-1 bb-1" onclick="changeBank();" id="bankNameBox">
		            <span class="sign-lable pr75" onclick="changeBank();">提现至</span>
		            <input class="pflex pr75" type="text" value="<c:out value="${userCard.bank_name}"/>（尾号<c:out value="${userCard.card_no}"/>）" readonly="readonly" id="bankName"/>
	            	<!-- <img class="right" src="http://static.hehenian.com/m/img/right.png"/> -->
		        </div>
		    </section>
		</c:when>
		<c:otherwise>
		 <section class="sign-area" style="margin-top: 0;">
	        <div class="sign-style sign-style-flex br-1 bb-1" id="bankNameBox">
	            <span class="sign-lable pr75">提现至</span>
	            <div class="pflex pr75"><a href="http://m.hehenian.com/finance/bindCard.do">添加银行卡></a>
            	</div>
	        </div>
	    </section>
        <!-- <div class="inputDiv" id="bankNameBox">
            <span>提现至　</span>
            <a class="addCard" id="addCard" href="http://m.hehenian.com/finance/bindCard.do">添加银行卡</a>
            <img class="right" src="http://static.hehenian.com/m/img/right.png"/>
        </div> -->
		</c:otherwise>
        </c:choose>
         <p class="gray-tips">每笔提现限额${limitOne}元，每月可转出2次</p>
         <p class="gray-tips">最多可提现${balance}元。</p>
        <%-- <p class="noticeText">每笔提现限额${limitOne}元，每月可转出2次</p> --%>
        <%-- <p class="noticeText">最多可提现<span id="maxWithraw">${balance}</span>元</p> --%>
        <section class="sign-area" style="margin-top: 0;">
	        <div class="sign-style br-2 bb-1">
	            <span class="sign-lable">提现金额</span>
	            <input type="text" placeholder="请输入提现金额" maxlength="10" id="withdrawMoney" oninput="changeBtnState();judgeMoney()" onpropertychange="changeBtnState();judgeMoney()">
	        </div>
	    </section>
        <!-- <div class="inputDiv">
            <span>提现金额</span>
            <input type="text" placeholder="请输入提现金额" maxlength="10" id="withdrawMoney" oninput="changeBtnState();judgeMoney()" onpropertychange="changeBtnState();judgeMoney()">
        </div> -->
        <!-- <p class="noticeText" id="receiveText">24小时内到账</p> -->
        <p class="gray-tips">24小时内到账</p>
        <!--
            <div class="inputDiv" style="border-top:0">
            <span>到账时间</span>
            <input type="text" value="24小时内到账" disabled="disabled" id="receiveText"/>
            </div> -->
            <p class="gray-tips noticeText2" style="display: none;">已向您的手机<a style="color:#08b2e6;text-decoration: underline;">${hidPhone}</a>发送验证码</p>
           	
           	<!-- <section class="sign-area" style="margin-top: 0;">
		        <div class="sign-style br-3 bb-1">
		            <span class="sign-lable">验证码</span>
		            <input type="text" placeholder="请输入验证码" maxlength="10" id="code" oninput="changeBtnState()" onpropertychange="changeBtnState()">
                	<input type="button" value="获取验证码" class="getCode" id="getCode">
		        </div>
		    </section> -->
		    
		    <section class="sign-area" style="margin-top: 0;">
		        <div class="sign-style br-3 bb-1">
		            <span class="sign-lable">验证码</span>
		            <!-- <input type="text" placeholder="请输入提现金额" id=""> -->
		            <input type="text" placeholder="请输入验证码" maxlength="10" id="code" oninput="changeBtnState()" onpropertychange="changeBtnState()">
		            <!-- <button id="getCode">获取验证码</button> -->
		            <input type="button" value="获取验证码" class="button" id="getCode">
		        </div>
		    </section>
		    
		    
           	
            <!-- <div class="inputDiv">
                <span>验证码　</span>
                <input type="text" placeholder="请输入验证码" maxlength="10" id="code" oninput="changeBtnState()" onpropertychange="changeBtnState()">
                <input type="button" value="获取验证码" class="getCode" id="getCode">
            </div> -->
            <p class="gray-tips">
			        工作日18:00后提交，次日审核到账，节假日顺延。<br />每月2次免费，超出收取3元/笔。
			</p>
            <!-- <p class="noticeText">工作日18:00后提交，次日审核到账，节假日顺延<br/>每月2次免费，超出收取3元/笔 </p> -->
            <div class="res-sub">
				<input class="subBtn disable" id="subBtn" style="margin-bottom:106px" type="submit" value="提交" onclick="submit(this)">
			</div>
            <!-- <div class="subBtn disable" id="subBtn" style="margin-bottom:106px" onclick="submit(this)">确认</div> -->
            <input type="hidden" id="receiveTime"/>
            <%-- <div class="noticeBox">可提现${balance}元，已超出，请修改</div>
            <div class="cover"></div> --%>
            
            
	    <!--输入密码弹窗-->
    <section class="buy-pop hide" id="psdModal">
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
 <%@ include file="../common/tail.jsp" %>
    </body>
    <script type="text/javascript" src="http://static.hehenian.com/m/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="http://static.hehenian.com/m/js/common.js"></script>
    <script type="text/javascript" src="http://static.hehenian.com/m/js/module.js"></script>
    <script type="text/javascript">
    var it,count=60;
    $(function(){
         $('#receiveTime').val('1');
         $("#getCode").bind("click",function(){
             getVerifyCode();
         });
         
         
         //支付密码确认
/*          $(".ic-sub").bind("click", function() {
 			var code =$('#verifyCode').val() ;
 	        if(code=='' || code.length==0){
 	           popWindow("请输入支付密码！");
 	           return;
 	        }
 	        $(".buy-pop").css("display","none");
 	        $(".in-code").css("display","none");
//  	        异步请求验证支付密码是否正确
 	        var options = {type:"GET",url:"http://m.hehenian.com/account/checkPayPwd.do",data:{pwd:code}};
				ajaxRequest(options,function(data){
					if (data.returnCode == 1) {
						popWindow(data.messageInfo);
					}else {//密码修改成功，跳到成功页面
						subby();
					}
				});
 		});
 		 $(".ic-cancel").click(function() {
        		 $(".buy-pop").css("display","none");
     	 }); */
 		 
    });
    
	
     function relayTime(){
         count--;
         if(count>0) {
             $("#getCode").val(count + "s重获");
         }else{
             clearInterval(it);
             $("#getCode").val("获取短信验证码");
             $("#getCode").removeAttr("disabled");
             $("#getCode").removeClass("disable");
             $(".noticeText2").hide();
             $("#getCode").bind("click",function(){getVerifyCode();});
             count=60;
         }
     }
     function getVerifyCode(){
         $("#getCode").unbind("click");
         $("#getCode").attr("disabled","disabled");
         $("#getCode").addClass("disable");
         $(".noticeText2").show();
         $.post("http://m.hehenian.com/common/sendPhoneVirifyCode.do", {checkPhone:true}, function(data) {
              var ret= data.ret;
         });
         it = setInterval("relayTime()",1000);
     }
     function changeBtnState(){
         if($("#code").val()&&$("#withdrawMoney").val()){
             $("#subBtn").removeClass("disable");
         }else{
             $("#subBtn").addClass("disable");
         }
     }
     function judgeMoney(){
         var withdrawMoney = parseFloat($("#withdrawMoney").val());
         if(withdrawMoney < 10000){
             $('#receiveText').text('24小时内到账');
             $('#receiveTime').val('1');
         }else if(withdrawMoney >= 10000){
             $('#receiveText').text('审核后到账');
             $('#receiveTime').val('2');
         }
     }
     function changeBank(){
         /* var html=[];
         html.push('<div class="cover" id="newAlertCover" style="display: block;"></div>');
         html.push('<div class="popWithdraw" id="newAlertPop" style="display: block;"><div class="title" id="popTilte">选择其它银行卡</div><div class="act"><a href="<c:url value="http://m.hehenian.com/profile/managerCard.do"/>" style="color:#08b2e6">确定</a><a onclick="hideNewAlertPop()">取消</a></div></div>');
         $('body').append(html.join('')).show(); */
         
    	 new Modalmin({
   		    //title:false,      //false时，没有标题
   		    content:'选择其他银行卡',    //必填
   		    okText:'确定',         //默认“确定”
   		    cancelText:'取消',     //默认"取消"
   		    ok: function(){        //false时，没有ok按钮
   		        window.location.href='http://m.hehenian.com/profile/managerCard.do';
   		    },
   		    cancel: function(){    //false时，没有cancel按钮
   		        // alert('cancel');
   		    },
   		    close: function(){
   		        // alert('我被关闭了！！！');
   		    }
   		});

     }
     function submit(ob){
         <%  int hours = DateUtil.getCurrentHours();
             if (hours<7 || hours>=23){
         %>
         popWindow("申请提现时间是7:00 - 23:00");
             return;
         <% } %>
         if($(ob).attr("class") == "subBtn disable"){
             return;
         }
         var receiveTime = $("#receiveTime").val();
         var withdrawMoney = $("#withdrawMoney").val();
         var code = $("#code").val();
         if (code == '') {
        	 popWindow("请输入短信验证码");
        	 return;
         }
         if (code.length != 6) {
        	 popWindow("请输入6位短信验证码");
        	 return;
         }
         <c:if test="${empty userCard}">
         	popWindow("请先绑定银行卡");
             return;
         </c:if>
         if(withdrawMoney==''){
        	 popWindow("请输入提现金额");
             return;
         }else{
             var reg = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
             if(isNaN(withdrawMoney)){
            	 popWindow('请输入有效金额，只能为数字');
                 return;
             }
             if(!reg.test(withdrawMoney)){
            	 popWindow('输入金额有误');
                 return;
             }
             if(parseFloat(withdrawMoney)<5){
            	 popWindow('最小提现金额5元');
                 return;
             }
             var limitOne = <c:out value="${limitOne}"/>;
             if(parseFloat(withdrawMoney) > parseFloat(limitOne)){
            	 popWindow("输入金额已超出单笔提取上限，请重新输入");
                 return;
             }
             var balance = <c:out value="${balance}"/>;
             if(parseFloat(withdrawMoney) > parseFloat(balance)){
            	 popWindow("输入金额已超出余额，请重新输入");
                 return;
             }
             <c:if test="${todayAmt gt limitDay}">
             popWindow("对不起，您今日累计提取金额已超出提取上限<c:out value="${limitDay}"/>元，请改日操作");
                 return;
             </c:if>
         }
         if(code==''){
        	 popWindow("验证码不能为空");
             return;
         }
         //TODO duanhr 添加支付密码弹框
         psdModal.show();
//          $(".buy-pop").show();
// 		 $(".in-code").show();
     }
     
 	//输入密码弹窗
     var psdModal = new Modal({
         id: '#psdModal',
         events: {
             '.ic-cancel click': function(modal) {
             	modal.modal.find('.ic-input').val("");
                 modal.close();
                 $("#subBtn").removeClass("disable");
             },
             'input[type="submit"] click': function(modal) {
             	var code = modal.modal.find('.ic-input').val();
      	        if(code=='' || code.length==0){
      	           popWindow("请输入支付密码！");
      	           return;
      	        }
//          		$(".buy-pop").css("display","none");
//          		$(".in-code").css("display","none");
         		//异步请求验证支付密码是否正确
         		var options = {type:"POST",url:"http://m.hehenian.com/account/checkPayPwd.do",data:{pwd:code}};
         			ajaxRequest(options,function(data){
         				if (data.returnCode == 1) {
         					popWindow(data.messageInfo);
         					modal.modal.find('.ic-input').val("");
         				}else {//密码修改成功，跳到成功页面
         					subby();
         				}
         			});
             }
         }
     });
     
     //正确输入支付密码
     function subby() {
    	 psdModal.close();
    	 var receiveTime = $("#receiveTime").val();
         var withdrawMoney = $("#withdrawMoney").val();
         var code = $("#code").val();
         
//     	 $(ob).addClass('disable');
         var param = [];
         param.push("receiveTime="+receiveTime);
         param.push("withdrawMoney="+withdrawMoney);
         param.push("code="+code);
         param = param.join("&");
         ajax({
             url:'http://m.hehenian.com/balance/userWithdraw.do',
             type:'post',//非必须.默认get
             data:param,
             dataType:'json',//非必须.默认text
             async:true,//非必须.默认true
             cache:false,//非必须.默认false
             timeout:60000,//非必须.默认30秒
             success:submitSuccess,//非必须
             complete : function(XMLHttpRequest,status){ //请求完成后最终执行参数
                 if(status=='timeout') {//超时,status还有success,error等值的情况
                     ajaxTimeOut.abort(); //取消请求
                     $("#subBtn").removeClass("disable");
                     popWindow("系统处理超时,请稍后重试");
                 }
             }
         },receiveTime,withdrawMoney);
 	}
     
     function submitSuccess(data,receiveTime,withdrawMoney){
         if(data.returnCode==0 || data.returnCode==-1){
             if(receiveTime=='1'){
                 //newAlertUrl('提现申请成功,请关注银行到账消息。','http://m.hehenian.com/balance/index.do');
                 
                 //update by 2015-05-08 duan  提现成功之后跳转到个人中心
//             	 popWindow('提现申请成功,请关注银行到账消息。','http://m.hehenian.com/balance/index.do');
                 popWindowCab("提现申请成功,请关注银行到账消息。",function(){
                	 location.href="http://m.hehenian.com/profile/index.do";
            	 });
             }else{
                 //newAlertUrl('提现申请成功,我们会尽快审核，确保您的资金安全到账。','http://m.hehenian.com/balance/index.do');
                 
                 //update by 2015-05-08 duan  提现成功之后跳转到个人中心
//                  popWindow('提现申请成功,我们会尽快审核，确保您的资金安全到账。','http://m.hehenian.com/balance/index.do');
                 popWindowCab("提现申请成功,我们会尽快审核，确保您的资金安全到账。",function(){
                	 location.href="http://m.hehenian.com/profile/index.do";
            	 });
             }
         }else{
             $("#subBtn").removeClass("disable");
             popWindow(data.messageInfo);
         }
     }
    </script>
</html>