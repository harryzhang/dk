<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <link rel="stylesheet" type="text/css" href="/wap/styles/dqlc/personMsg.css?t=1">
    <link rel="stylesheet" type="text/css" href="/wap/styles/dqlc/base.css?t=1">
    <title>手机号码</title>
</head>
<body>
<%--<jsp:include page="/include/mobile/loading.jsp"></jsp:include>--%>
<%--<h1 class="t title"><span>个人信息</span></h1>--%>
<s:if test="!#session.user.phoneHasVerify">
    <div class="inputBox mt30">
        <span>手机号码</span>
        <input type="text" placeholder="输入需绑定的手机号码" id="mobile" value="${session.user.mobilePhone}"/>
    </div>
    <div class="inputBox">
        <span>验证码　</span>
        <input type="text" placeholder="输入验证码" id="code"/>
        <a class="yanzhengBtn" id="ida" onclick="getTelephoneCode();"  disable="no">获取验证码</a>
    </div>
    <a class="submit" onclick="phoneVerify();">提交</a>
</s:if>
<s:else>
    <div class="inputBox mt30">
        <span>原手机号码</span>
        <input type="text"  id="mobile" value="${session.user.mobilePhone}" contenteditable="false"/>
    </div>
    <div class="inputBox">
        <span>验证码　</span>
        <input type="text" placeholder="输入验证码" id="code"/>
        <a class="yanzhengBtn" id="ida" onclick="getTelephoneCode();" disable="no">获取验证码</a>
    </div>
    <a class="submit" onclick="disablePhoneVerify();">提交</a>
</s:else>
<jsp:include page="/include/mobile/bottom.jsp"></jsp:include>
<jsp:include page="/include/mobile/common-js.jsp"></jsp:include>
<script type="text/javascript" src="/script/phone_verify.js"></script>
<script>
    function getTelephoneCode(){
        if ($("#ida").attr("disable") == "no") {
            var ret = verifyPhone_($("#mobile").val());
            if(ret!=""){
                HHN.popup({content:ret,type:"alert"});
            }else {
                getTelephoneCode_($("#mobile").val());
            }
        }
    }
    function phoneVerify(){
        var mobilePhone = $("#mobile").val();
        var code = $("#code").val();
        debugger
        if(code!=''){
            $.getJSON("/account/phone-verify.do",{mobilePhone:mobilePhone,identifyCode:code},function(data){
                var ret=data.ret;
                if(ret=="0"){
                    HHN.popup({content:"验证成功",type:"alert"});
                    window.location.href="/webapp/webapp-userinfo.do";
                }else{
                    HHN.popup({content:data.msg,type:"alert"});
                }
            });
        }else{
            HHN.popup({content:"请输入验证码",type:"alert"});
        }

    }
    function disablePhoneVerify() {
        var code = $("#code").val();
        if(code!='') {
            $.getJSON("/account/disable-phone-verify.do", {'identifyCode': code}, function (data) {
                var ret = data.ret;
                if (ret == "0") {
                    window.location.href = "/webapp/modify-phone.do";
                } else {
                    HHN.popup({content: data.msg, type: "alert"});
                }
            });
        }else{
            HHN.popup({content:"请输入验证码",type:"alert"});
        }
    }
</script>
</body>
</html>