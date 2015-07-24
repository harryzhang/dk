<%@ page pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>爱定宝-赎回</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=640,target-densitydpi=320,user-scalable=no">
        <link rel="stylesheet" type="text/css" href="http://static.hehenian.com/m/css/base.css">
        <link rel="stylesheet" type="text/css" href="http://static.hehenian.com/m/css/redeem.css">
        <%@ include file="../common/head.jsp" %>
    </head>
    <body>
        <p class="noticeText">已向您的手机<a style="color:#08b2e6;text-decoration: underline;"><c:out value="${userHidPhone}"/></a>发送验证码</p>
        <div class="inputDiv">
            <span>验证码　</span>
            <input type="text" placeholder="请输入验证码" maxlength="10" id="code" oninput="changeBtnState()" onpropertychange="changeBtnState()">
            <input type="button" value="获取验证码" class="getCode" id="getCode">
        </div>
        <a class="subBtn" id="subBtn" onclick="submit(this)">确认赎回至账户余额</a>
      <%@ include file="../common/tail.jsp" %>
        

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
    
    
    </body>
<script src="http://static.hehenian.com/m/js/jquery-1.11.1.min.js"></script>
<script src="http://static.hehenian.com/m/js/common.js"></script>
<script type="text/javascript" src="http://static.hehenian.com/m/js/module.js"></script>
    <script type="text/javascript">
        var it,count=60;
        $(function(){
            $("#getCode").bind("click",function(){
                getVerifyCode();
            });
            
            
          //支付密码确认
/*             $(".ic-sub").bind("click", function() {
    			var code =$('#verifyCode').val() ;
    	        if(code=='' || code.length==0){
    	           popWindow("请输入支付密码！");
    	           return;
    	        }
    	        $(".buy-pop").css("display","none");
    	        $(".in-code").css("display","none");
    	        //异步请求验证支付密码是否正确
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
        
    	//输入密码弹窗
        var psdModal = new Modal({
            id: '#psdModal',
            events: {
                '.ic-cancel click': function(modal) {
                	modal.modal.find('.ic-input').val("");
                    modal.close();
                },
                'input[type="submit"] click': function(modal) {
                    var code =modal.modal.find('.ic-input').val();
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
            					subby();
            					modal.modal.find('.ic-input').val("");
            					psdModal.close();
            				}
            			});
                }
            }
        });
        
	     //正确输入支付密码
        function subby() {
        	var code = $("#code").val();
	    	 
        	var param = [];
            param.push("tradeId=<c:out value="${tradeId}"/>");
            param.push("verifyCode="+code);
            param = param.join("&");
            ajax({
                url:'http://m.hehenian.com/profile/redeemProcess.do',
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
                        newAlert("系统处理超时,请稍后重试");
                    }
                }
            });
   	 	} 
        function relayTime(){
            count--;
            if(count>0) {
                $("#getCode").val(count + "秒后可重新发送");
            }else{
                clearInterval(it);
                $("#getCode").val("获取短信验证码");
                $("#getCode").removeAttr("disabled");
                $("#getCode").removeClass("disable");
                $("#getCode").bind("click",function(){getVerifyCode();});
                count=60;
            }
        }
        function getVerifyCode(){
            $("#getCode").unbind("click");
            $("#getCode").attr("disabled","disabled");
            $("#getCode").addClass("disable");
            $.post("http://m.hehenian.com/common/sendPhoneVirifyCode.do", {mobilePhone:'<c:out value="${userPhone}" />'}, function(data) {
                //     var ret= data.ret;
                //     if (ret != "0") {
                // //       alert("验证码获取失败");
                //    $("#getCode").removeAttr("disabled");
                //    $("#getCode").removeClass('disable');
                //    $("#getCode").bind("click",function(){getVerifyCode();});
                //     return;
                //  }
            });
            it = setInterval("relayTime()",1000);
        }
        
        function submit(ob){
        	//TODO duanhr 添加支付密码的弹出框
            if($(ob).attr("class") == "subBtn disable"){
                return;
            }
            var code = $("#code").val();
            if(code==''){
                newAlert("验证码不能为空");
                return;
            }
            $(ob).addClass('disable');
            
            //弹出支付密码输入框
            psdModal.show();
       /*      $(".buy-pop").show();
    		$(".in-code").show(); */
        }
        
        function submitSuccess(data){
            if(data.returnCode==0){
//                 document.location.href = "<c:url value="/view/mobile/purchaseRecords.jsp"/>";
            		popWindowCab(data.messageInfo,function(){
            			location.href = "http://m.hehenian.com/profile/buy.do?flag=xin&tab_f=g";
            		});
            }else{
                $("#subBtn").removeClass("disable");
                newAlert(data.messageInfo);
            }
        }
    </script>
</html>