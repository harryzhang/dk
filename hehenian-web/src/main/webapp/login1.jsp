<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <jsp:include page="/include/head.jsp"></jsp:include>
    <script type="text/javascript">
        $(function() {
            $(".header_two_right_ul li").hover(function() {
                $(this).find("ul").show();
            }, function() {
                $(this).find("ul").hide();
            });
            $("#password").val("");

            $(".fbjk_all_toux ul li:first").addClass(".inpp");
            $(".fbjk_all_toux ul li").click(function() {
                var ppin = $(".fbjk_all_toux ul li").index(this);
                $(".fbjk_all_toux ul li").removeClass(".inpp");
                $(this).addClass(".inpp");
                $(".fbjk_all_touxh").hide();
                $(".fbjk_all_touxh").eq(ppin).show();
            })

            $("#email").focus(function() {
                this.select();
            });

            hhnCookie();

        })
        function hhnCookie() {
            var CookieStr = document.cookie;
            if (CookieStr == undefined || CookieStr.length <= 0 || CookieStr.indexOf("user=") < 0){
                return;
            }else if( CookieStr.indexOf("@") > 0){
                var username;//cookie用户名
                var start = CookieStr.indexOf("user=") + "user=".length+1;
                var end = CookieStr.indexOf(";", start)-1;
                if (end < 0) {
                    username = CookieStr.substring(start);
                } else {
                    username = CookieStr.substring(start, end);
                }
                $("#email").val(username);
            }else{
                var username;//cookie用户名
                var start = CookieStr.indexOf("user=") + "user=".length;
                var end = CookieStr.indexOf(";", start);
                if (end < 0) {
                    username = CookieStr.substring(start);
                } else {
                    username = CookieStr.substring(start, end);
                }
                $("#email").val(username);
            }
        }
    </script>
</head>
<body>
<jsp:include page="/include/top.jsp"></jsp:include>
<div style=" margin-top:30px; margin-bottom:90px;">
    <div style=" width:890px; border:1px solid #e8e8e8; background:#FFF; margin:0px auto; overflow:hidden; height:400px;">
        <div style=" width:540px; float:left">
            <script language="JavaScript">
                var VeryHuo = 3
                var now = new Date()
                var sec = now.getSeconds()
                var ad = sec % VeryHuo;
                ad +=1;
                if (ad==1){
                    alt="ad1";
                    banner="/newimages/login1.jpg";
                    width="540";
                    height="400";
                }
                if (ad==2) {
                    alt="ad2";
                    banner="/newimages/login2.jpg";
                    width="540";
                    height="400";
                }
                if (ad==3) {
                    alt="ad3";
                    banner="/newimages/login3.jpg";
                    width="540";
                    height="400";
                }
                document.write('<center>');
                document.write('<img src=\"' + banner + '\" width=')
                document.write(width + ' height=' + height + ' ');
                document.write('alt=\"' + alt + '\" border=0><br>');
                document.write('</center>');
                --></script>


        </div>
        <div style=" width:290px; float:left; padding:30px;">
            <ul style=" background:#fffeef; line-height:24px; padding-left:10px; margin-bottom:10px;">
                <li><span id="s_email" class="formtips"></span></li>
                <li><span id="s_password" class="formtips"></span></li>
                <li><span id="s_code" class="formtips"></span></li>
            </ul>
            <ul>
                <li>用户名</li>
                <li>
                    <input type="text" class="login_tex1" name="userName" id="email" onfocus="this.select()" value="${request.hhnUname }" style=" background:url(/images/v1/id.png) no-repeat;background-position:270px 8px;"/>
                </li>
                <li style=" margin-top:10px;">密码</li>
                <li>
                    <input type="password" onfocus="this.select()" class="login_tex1" name="pwd" id="password" style=" background:url(/images/v1/pass.png) no-repeat;background-position:270px 8px;"/>
                </li>
                <li style=" margin-top:10px;">验证码</li>
                <li>
                    <input onfocus="this.select()" type="text" class="login_tex2" name="paramMap.code" id="code" />
                    <img src="/admin/imageCode.do?pageId=userlogin" title="点击更换验证码"
                         style="cursor: pointer; padding:5px 0px 0px 10px;" id="codeNum" height="20" onclick="javascript:switchCode()" /> &nbsp;&nbsp; <a href="javascript:void()" onclick="switchCode()" style="color:#f60; ">换一换?</a> </li>
                <li  style=" margin-top:10px; line-height:38px;">
                    <input type="checkbox" name="xixi" id="addCookie" />
                    记住我的帐号</b>&nbsp;&nbsp;<a style="color:#f60;" href="forgetpassword.do">忘记密码</a></li>
                <li>
                    <input type="button" id="btn_login" value="登录" style=" width:296px; height:40px; border:0px; background:#f90; font-size:16px;color:#FFF; margin-left:0px;"/>
                </li>
            </ul>
        </div>
    </div>
    <div style=" width:870px; margin:0px auto; overflow:hidden;padding-right:30px;">
        <div style=" background:#7cbe56; border:5px solid #e8e8e8; border-top:0px; width:150px; text-align:center; font-size:12px; color:#fff; line-height:32px; float:right; "> <a href="/account/reg.do" style=" color:#FFF">免费注册>> </a></div>
    </div>
</div>

<!-- 引用底部公共部分-->
<!-- 引用底部公共部分 -->

<jsp:include page="/include/footer.jsp"></jsp:include>
<script>
    function switchCode() {
        var timenow = new Date();
        $("#codeNum").attr("src", "/admin/imageCode.do?pageId=userlogin&d=" + timenow);
    }

    $(function() {
        $("#code").bind('keyup', function(event) {
            if (event.keyCode == "13") {
                login();
            }
        });
        $("#email").bind('keyup', function(event) {
            if (event.keyCode == "13") {
                login();
            }
        });
        $("#password").bind('keyup', function(event) {
            if (event.keyCode == "13") {
                login();
            }
        });

        //样式选中

    })

    //初始化

    $(document).ready(function() {
        //===========input失去焦点
        $("form :input").blur(function() {
            //email
            if ($(this).is("#email")) {
                if (this.value == "") {
                    $("#s_email").attr("class", "formtips onError");
                    $("#s_email").html("*请输入用户名或邮箱地址");
                } else if (/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(this.value)) { //判断用户输入的是否是邮箱地址
                    checkRegister('email');
                } else if ((/^1[3,5,8]\d{9}$/.test(this.value))) {
                    checkRegister('cellphone');
                } else {
                    checkRegister('userName');
                    $("#s_email").attr("class", "formtips");
                }
            }
            //password
            if ($(this).attr("id") == "password") {
                pwd = this.value;
                if (this.value == "") {
                    $("#s_password").attr("class", "formtips onError");
                    $("#s_password").html("*请輸入您的密码");
                } else if (this.value.length < 6) {
                    $("#s_password").attr("class", "formtips onError");
                    $("#s_password").html("*密码长度为6-20个字符");
                } else {
                    $("#s_password").html("");
                    $("#s_password").attr("class", "formtips");
                }
            }

            //验证码
            if ($(this).attr("id") == "code") {
                if (this.value == "") {
                    $("#s_code").attr("class", "formtips onError");
                    $("#s_code").html("*请输入验证码");
                } else {
                    $("#s_code").attr("class", "formtips");
                    $("#s_code").html("");
                }
            }

        });
    });

    //===验证数据
    function checkRegister(str) {
        var param = {};
        if (str == "userName") {
            param["paramMap.email"] = "";
            param["paramMap.userName"] = $("#email").val();
        } else if (str == "email") {
            param["paramMap.email"] = $("#email").val();
            param["paramMap.userName"] = "";
        } else {
            param["paramMap.email"] = "";
            param["paramMap.userName"] = "";
            param["paramMap.cellphone"] = $("#email").val();
        }
        $.post("ajaxCheckLog.do", param, function(data) {
            $("#email_").hide();
            if (data == 2 || data == 0) {
                $("#s_email").html("*无效用户！");
            } else if (data == 3 && str == "userName") {
                $("#s_email").html("*该用户还没激活！");
                //add by houli
                $("#email_").show();
            } else if (data == 4 && str == "userName") {
                $("#s_email").attr("class", "formtips");
                $("#s_email").html("");
            }
            if (data == 0 && str == "email") {
                $("#s_email").html("*无效邮箱！");
            } else if (data == 1 && str == "email") {
                $("#s_email").html("*该邮箱用户还没激活！");
                //add by houli
                $("#email_").show();
            } else if (data == 4 && str == "email") {
                $("#s_email").attr("class", "formtips");
                $("#s_email").html("");
            }
            if (data == 5 && str == "cellphone") {
                $("#s_email").html("*用户不存在！");
            } else if (data == 4 && str == "cellphone") {
                $("#s_email").html("");
            }
        });
    }
</script>
<script>
    function login() {
        $(this).attr('disabled', true);
        if ($("#email").val() == "") {
            $("#s_email").attr("class", "formtips onError");
            $("#s_email").html("<img src='/images/v1/login.png'>请输用户名或邮箱地址");
        }
        if ($("#password").val() == "") {
            $("#s_password").attr("class", "formtips onError");
            $("#s_password").html("<img src='/images/v1/login.png'>请输入密码");
            // $("#retake_password").hide();
        }
        $('#btn_login').attr('value', '登录中...');
        var param = {};
        param["paramMap.pageId"] = "userlogin";
        param["paramMap.email"] = $("#email").val();
        param["userName"] = $("#email").val();
        param["paramMap.password"] = $("#password").val();
        param["pwd"] = $("#password").val();
        param["paramMap.code"] = $("#code").val();
        var addCookie = "0";
        if ($("#addCookie").attr("checked") == "checked")
            addCookie = "1";
        param["paramMap.addCookie"] = addCookie;

        $.post("/account/login.do", param, function(datas) {
            var data=datas.msg;
            if (data == 1) {
                var fromUrl = datas.fromUrl;
//                $.get("/updateUserUsableSum.do?_="+new Date().getTime());
                window.location.href = "/index.jsp";
            } else if (data == 2) {
                $('#btn_login').attr('value', '登录');
                $("#s_code").attr("class", "formtips onError");
                $("#s_code").html("<img src='/images/v1/login.png'>验证码错误！");
                switchCode();
                $("#btn_login").attr('disabled', false);
            } else if (data == 3) {
                $('#btn_login').attr('value', '登录');
                $("#s_email").attr("class", "formtips onError");
                $("#s_email").html("<img src='/images/v1/login.png'>用户名或密码错误！");
                switchCode();
                $("#btn_login").attr('disabled', false);
            } else if (data == 4) {
                $('#btn_login').attr('value', '登录');
                $("#s_email").attr("class", "formtips onError");
                switchCode();
                $("#s_email").html("<img src='/images/v1/login.png'>该用户已被禁用！");
                $("#btn_login").attr('disabled', false);
            } else if (data == 200) {
                $("#s_email").attr("class", "formtips onError");
                switchCode();
                alert("该用户已被禁用！");
                $("#btn_login").attr('disabled', false);
            }
        });
    }

    function reSendEmail() {
        if ($("#email").val() == "") {
            alert("请输入邮箱");
            return;
        }
        window.location.href = "reActivateEmail.do?email=" + $("#email").val();
    }

    $("#btn_login").click(function() {
        login();
    });
</script>
<%--<script type="text/javascript">--%>
<%--	function $(id){return document.getElementById(id)};--%>
<%--	--%>
<%--</script>--%>
</body>
</html>
