<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="/include/mobile/head.jsp"></jsp:include>
<title>绑定合和年帐号</title>
</head>
<body>
<jsp:include page="/include/mobile/loading.jsp"></jsp:include>
<h1 class="t title bind-hhn-title"><span>绑定合和年帐号</span></h1>
<div class="wrap" id="wrap" style="display:none;">
	<div class="tips-content p bind-tips hr"><img src="/images/hhnimages/logo_zaixian.png" /><p>您若在合和年在线注册过投资账户，请绑定您的合和年账户</p></div>
    <div class="pd bind-content">
    <form action="/account/bind.do" id="form">
      <input type="hidden" value="userlogin" name="paramMap.pageId" />
          <div class="input-item login-line"><input type="text" name="userName" class="username" placeholder = "请输入合和年登录用户名" validate="true" rule="{type:'username',empty:false,length:'2-25',disabled:''}" tips="{username:'请输入正确的合和年登录用户名',empty:'请输入合和年登录用户名'}"></div>
          <div class="input-item login-line"><input type="password" name="pwd" class="password" placeholder = "请输入合和年登录密码" validate="true" rule="{empty:false,length:'6-20'}" tips="{length:'合和年登录密码长度为6-20个字符',empty:'请输入合和年登录密码'}"></div>
          <div class="login-code">
              <div class="half fl">
              	<div class="input-item"><input onfocus="this.select()" type="tel" placeholder = "请输入验证码"  name="paramMap.code" id="code" validate="true" rule="{empty:false,length:'4-4',disabled:''}" tips="{empty:'请输入验证码'}" /></div>
              </div>
              <img src="/admin/imageCode.do?pageId=userlogin" title="点击更换验证码" id="codeNum" />
          </div>
          <a href="#" class="btn-a" id="btnLogin">绑定</a>

        <div class="pd" style="padding: 0 0;">
            <div class="login-info">你还没有账户？</div>
            <a href="/sjm-reg.do" class="btn-c ">立即免费注册</a>
        </div>
    </form>
    </div>
</div>
<jsp:include page="/include/mobile/common-js.jsp"></jsp:include>
<script>
    Zepto(function(){
        var codeNum = $("#codeNum"),username = $("input.username"),code=$("#code");
        function switchCode() {
            codeNum.attr("src", "/admin/imageCode.do?pageId=userlogin&d=" + new Date().getTime());
        }
        //code
        codeNum.on("click",function(){
            switchCode();
        });

        var form = new HHN.Form("form");
        $("#btnLogin").on("click",function(){
            form.validate({
                error:function(text,setting,input,$form){
                    HHN.popup({type:"alert",content:text,element:input});
                    input.focus();
                },
                success:function($form){
                    var url = $form.attr("action");
                    var postdata = $form.serialize();
                    $.ajax({
                        type: 'POST',
                        url: url+"?_="+new Date().getTime(),
                        data:postdata,
                        beforeSend: function(){
                            HHN.popup({type:"loading",content:'绑定中...'});
                        },
                        success: function(datas){
                            datas = HHN.parseJSON(datas);
                            var data=datas.msg;
                            if (data == 1) {
                                HHN.popLoading("/sjm/index.jsp","绑定成功，跳转中...");
                            } else if(data == 2){
                                code.val("").focus();
                                switchCode();
                                HHN.popup({type:"alert",content:'验证码错误',element:code});
                            } else if (data == 3) {
                                username.focus();
                                HHN.popup({type:"alert",content:'用户名或密码错误',element:username});
                            }else if (data == 6) {
                                username.focus();
                                HHN.popup({type:"alert",content:'该用户已与其他微信账号绑定',element:username});
                            }
                        },
                        error: function(xhr, type){
                            HHN.popup({type:"alert",content:"服务器繁忙，请稍后重试!"});
                        }
                    });
                }
            });
            return false;
        });
    });
</script>
</body>
</html>
