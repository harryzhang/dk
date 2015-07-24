<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%--<jsp:include page="/include/mobile/head.jsp"></jsp:include>--%>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <link rel="stylesheet" type="text/css" href="/wap/styles/dqlc/personMsg.css?t=1">
    <link rel="stylesheet" type="text/css" href="/wap/styles/dqlc/base.css?t=1">
<title>个人信息</title>
</head>
<body>
<%--<jsp:include page="/include/mobile/loading.jsp"></jsp:include>--%>
<%--<h1 class="t title"><span>个人信息</span></h1>--%>
<%--<div class="wrap animate-waves" id="wrap" style="display:none;">--%>
<s:if test="#request.realName==null || #request.realName==''">
    <div class="notice" style="color: red;" >身份信息直接影响交易操作，请务必如实填写<br/>注：身份信息保存后将无法修改</div>
    <div class="inputBox">
        <span>真实姓名</span>
        <input type="text" placeholder="请填写您的真实姓名" value="${map.realName}" id="realName" />
    </div>
    <div class="inputBox">
        <span>身份证号</span>
        <input type="text" placeholder="请填写您的身份证号" value="${map.idNo}" id="idNo" />
    </div>
    <div class="inputBox">
        <span>电子邮箱</span>
        <input type="text" placeholder="请填写常用邮箱" value="${map.email}" id="email" />
    </div>
    <a class="submit" onclick="save();" style="${request.realName!=null && request.realName!='' ?'display: none;':''}">提交</a>

</s:if>
<s:else>
    <div class="inputBox">
        <span>真实姓名</span>
        <a style="font-size: 15px;">${map.realName}</a> </div>
    <div class="inputBox">
        <span>身份证号</span>
        <a style="font-size: 15px;">${map.idNo}</a>
    </div>
    <div class="inputBox">
        <span>电子邮箱</span>
        <a style="font-size: 15px;">${map.email}</a>
    </div>
</s:else>

<%--</div>--%>
<jsp:include page="/include/mobile/bottom.jsp"></jsp:include>
<jsp:include page="/include/mobile/common-js.jsp"></jsp:include>
<script>
    function save(){
        if ($("#realName").val() == "") {
            HHN.popup({content:"请填写真实姓名",type:"alert"});
            return false
        }else{
            var isName = /^[a-zA-Z\u4e00-\u9fa5]+$/;
            if ($("#realName").val() == "") {
                HHN.popup({content:"请填写真实姓名",type:"alert"});
                return false
            } else if ($("#realName").val().length < 2 || $("#realName").val().length > 20) {
                HHN.popup({content:"名字长度为2-20个字符",type:"alert"});
                return false
            } else if (!isName.test($("#realName").val())) {
                HHN.popup({content:"真实姓名输入有误",type:"alert"});
                return false
            }

        }
        if ($("#idNo").val() == "") {
            HHN.popup({content:"请填写身份号码",type:"alert"});
            return false
        }else{
            var isIDCard1 = new Object();
            var isIDCard2 = new Object();
            //身份证正则表达式(15位)
            isIDCard1 = /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;
            //身份证正则表达式(18位)
            isIDCard2 = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}(x|X))$/;
            if ($("#idNo").val() == "") {
                //alert("请填写身份证号码");
                HHN.popup({content:"请填写身份号码",type:"alert"});
                return false;
            } else if (isIDCard1.test($("#idNo").val()) || isIDCard2.test($("#idNo").val())) {
                //验证身份证号码的有效性
//                $(this).next().html("");

            } else {
                HHN.popup({content:"身份证号码不正确",type:"alert"});
                return false
            }
        }
        if ($("#email").val() == "") {
            HHN.popup({content:"请填电子邮箱",type:"alert"});
            return false
        }
        var mail = $("#email").val();
        //对电子邮件的验证
        if(!/\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/.test(mail))
        {
            HHN.popup({content:"请输入有效的电子邮箱",type:"alert"});
            $("#email").focus();
            return false;
        }
        var parama = {};
        parama["paramMap.idNo"] = $("#idNo").val();
        $.getJSON("/isIDNO.do", parama, function(data) {
            if(data){
                if (data.putStr == "0") {
                    HHN.popup({content:"请填写身份证号码",type:"alert"});
                    return false
                } else if (data.putStr == "1") {
                    HHN.popup({content:"身份证号码不合法",type:"alert"});
                    return false
                }
            }

            var param = {};
            param["paramMap.realName"] = $("#realName").val();
            param["paramMap.idNo"] = $("#idNo").val();
            param["paramMap.email"] = $("#email").val();
            $.getJSON ("/updateBasedate.do", param, function(data) {
                if (data.msg == "保存成功") {
                    HHN.popup({content:"保存成功",type:"alert"});

                    var fromUrl = "${param.fromUrl}";
                    if(fromUrl==""){
                        fromUrl = "/webapp/webapp-userinfo.do";
                    }else{
                        fromUrl = decodeURI(fromUrl);
                    }
                    window.location.href=fromUrl;
                }else{
                    HHN.popup({content:data.msg,type:"alert"});

                }
            });
        });

    }
</script>
</body>
</html>