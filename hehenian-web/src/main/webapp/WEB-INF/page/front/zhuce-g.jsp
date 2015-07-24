<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
 <head>
  <meta charset="UTF-8">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
  <title>注册合和年在线</title>
  <link href="/css/hhncss.css?t=14" rel="stylesheet" type="text/css" />
<link href="/css/Site2.css?t=2" rel="stylesheet" type="text/css" />
<link href="/css/lytebox.css?t=2" rel="stylesheet" type="text/css" />
  <style>
  /*reset*/
  *{margin:0; padding:0; }
  body { margin:0; padding:0; font-size:12px; color:#333; }
  ul,li,dl,dd,form { margin:0; padding:0; list-style:none; }
  .w { width:1423px; }
  .banner { margin:0 auto; height:443px; background:url(/images/banner_bg.jpg) left top no-repeat; position:relative; overflow:hidden; }
  .reg { background:url(/images/reg_bg.jpg) left top no-repeat; width:309px; height:420px; position:absolute; top:0; right:212px; overflow:hidden; }
  .reg form { margin:60px 0 0 20px; }
  .reg-item { padding:0 0 4px 0; margin-left:30px; position:relative; }
  .reg-item label { display:block; height:18px; line-height:18px; font-weight:bold; font-size:12px; color:#333; margin-left:4px; }
  .reg-item input { height:32px; margin:3px 0 0 4px; padding:0 0 0 30px; width:180px; border:none; color:#4d4d4d; outline:none; border-radius:3px; }
  .reg-item input.username { background:#fff url(/images/reg_icon.gif) 5px 3px no-repeat; }
  .reg-item input.password { background:#fff url(/images/reg_icon.gif) 5px -27px no-repeat; }
  .reg-tip { position:absolute; top:20px; left:4px; font-style:normal; display:block; width:180px; height:35px; line-height:35px;  color:#cecece; padding-left:35px; }
  .reg-contact .reg-tip { padding-left:9px; width:200px; }
  .reg-contact input {  margin-left:5px; padding-left:5px; width:204px }
  .reg-code input { width:92px; margin-left:5px; }
  .reg-code span { float:right; margin-right:52px; }
  .reg-item input.focus { color:#333; }
  .reg-btn { margin:-5px 0 3px 0; padding-bottom:0; }
  .reg-btn a { display:block; margin:15px 0 0 33px; background:url(/images/reg_btn.gif) left top no-repeat; border:none; width:206px; height:40px; cursor:pointer;  }
  .reg-btn a.zhece { background-position:0 -48px;}
  .reg-agreement { margin: 0px 0 0 11px; }
  .reg-agreement input { margin:0 10px 0 0; padding:0; width:auto; vertical-align:middle; }
  .reg-agreement a { margin:0 3px; color:#2D43B8; }
  .reg-error { display:block; padding:0 0 0 23px; color:#ff0000; line-height:100%; width:235px; }

  .main { background:#f3f3f3; margin:0 auto; }
  .content { margin:0 auto; width:1028px; padding-top:28px; }
  .content p { margin-bottom:11px; position:relative; }
  .content p a { display:block; width:100px; height:30px; text-decoration: none; position:absolute; }
  .content p a.youshi,
  .content p a.liucheng { top:22px; right:16px; }
  .content p a.chengji { top:214px; left:75px;}
  .content p a.moshi { top:19px; right:14px; }
  .footer { height:75px; text-align:center; margin:0 auto; background:url("/images/copy_bg.gif?=10") repeat scroll 0 0 rgba(0, 0, 0, 0); padding-top:30px; }
  .footer p { text-align:center; color:#c2c2c2; padding-bottom:15px; margin:0 auto; }
  .sendBtn{font-family:Arial;line-height: 34px;color: #333;cursor: pointer;background: url(/images/sprit.png) no-repeat -111px -100px;text-align: center;height: 31px;width: 88px;border: none;display: inline-block;font-size: 13px;}
  .sendBtn1{font-family:Arial;line-height: 34px;color: #333;cursor: pointer;background: url(/images/sprit.png) no-repeat 0px 0px;text-align: center;height: 31px;width: 112px;border: none;display: inline-block;font-size: 13px;}

  </style>

 </head>
 <body>
 <div class="header">
  <div class="header_one">
    <div class="header_one_center">
      <div style="font-size: 13px; width:50% !important; width:49%; float:left; font-size:80%"><div style="float:left">客服热线：<font style="font-size:17px;font-family: 'Merriweather', serif;">4008-303-737</font>&nbsp;&nbsp;&nbsp;&nbsp;服务时间：8:30-20:00&nbsp;&nbsp;&nbsp;&nbsp;</div>
      
       <p style="float:left; margin-top:15px;"><a class="ui-top-img-link weibo" href="http://weibo.com/u/3893072293" target="_blank">合和年新浪微博</a></p>
              <p style="float:left; margin-top:15px;"><a class="ui-top-img-link we-chat" href="/images/er_wema.jpg"  rel="lytebox" title="请扫描二维码，添加微信账号，获取海量投资资讯及最新优惠信息">合和年微信</a></p>
      
      </div>
      <div style="font-size: 13px; width:50%; float:right; text-align:right ; ">
       <!--  <ul >
          <li style=" border-left:1px solid #FFF; padding:0px 0px 0px 10px; float:right; width:60px;"><span><a href="/account/reg.do">免费注册</a></span></li>
          <li style="border-right:1px solid #ccc ; padding:0px 20px; float:right; overflow:hidden; height:48px;"><span>
            <a href="login.do">登录</a>
            </span></li>
        </ul> -->
      </div>
    </div>
  </div>
  <div class="header_two">
    <div class="header_two_center">
      <div class="header_two_left">
			<table width="570" border="0">
          <tr>
            <td width="200"><div class="header_two_left_logo"> 
            <a href="/index.do"><img src="/images/hhnimages/logo_zaixian.png" style=" border:0px;"/> </a>
            </div></td>
            <td valign="bottom"><a href="/index.do" style=" border:0px;">
            <img src="/images/hhnimages/logo_line.png" style=" border:0px;"/></a> &nbsp;&nbsp;
            <a href="/index.do" style=" border:0px;">
            <img src="/images/hhnimages/hyn.png" style=" border:0px;"/></a>（港联交所上市企业HK1777）</td>
          </tr>
    
        </table>
      </div>
      <div class="header_two_right">
        <ul class="header_two_right_ul">
          <li class="hhn"><a href="/finance.do">我要投资</a>
          </li>
        <li class="hhn"><a href="/home.do">会员中心</a></li>
        </ul>
      </div>
      <div class="cle"></div>
    </div>
  </div>
</div>

<!--顶部主导航 结束-->
  <div class="w banner">
	<div class="reg" id="reg_div">
		<form action="/account/registerg.do" method="post" id="regForm">
			<input type="hidden" name="confirmPassword" id="confirmPassword" />
			<div class="reg-item">
				<label>用户名</label>
				<input type="text" name="userName" id="userName" class="username"  value="${param['userName']}"/>
				<em class="reg-tip">请输入字母、数字或下划线</em>${fieldErrors.userName[0]}
			</div>
			<div class="reg-item">
				<label>密  码</label>
				<input type="password"  size="16" maxlength="16" name="pwd" id="password" class="password" value="${param['pwd']}"/>
				<em class="reg-tip">请输入6-16位字符</em>${fieldErrors.pwd[0]}
			</div>
			<div class="reg-item reg-contact">
				<label>手  机</label>
				<input type="text"   name="mobilePhone" id="contact" value="${param['mobilePhone']}" style="width: 88px;"/>
				<em class="reg-tip">请输入手机号码</em>
                <a href="javascript:;" onclick="getTelephoneCode()" id="ida" disable="no" class="sendBtn">
                    获取验证码
                </a>${fieldErrors.mobilePhone[0]}
			</div>
            <div class="reg-item reg-contact">
                <label>验证码</label>
                <input type="text"  name="identifyCode" id="identifyCode" value="${param['identifyCode']}" />
                <em class="reg-tip">请输入手机验证码</em>${fieldErrors.identifyCode[0]}
            </div>
			<div class="reg-item reg-btn">
				<a href="#" class="reg-submit" id="submit"></a>
			</div>
			<div class="reg-item reg-agreement">
				<p class="reg-error" id="regError"></p>
				<p style="width: 236px;line-height: 5px;"><input type="checkbox" checked name="paramMap.agreement" id="agreement" />我已阅读并同意
				<a href="/zcxy.html" target="_blank">《注册协议》</a>和
				<a href="/callcenter.do?id=7" target="_blank">《免责声明》</a>
				<a href="/callcenter.do?id=8" target="_blank">《风险披露声明》</a></p>
			</div>
		</form>
	</div>
  </div>
  <div class="w main">
	<div class="content">
		<p><img src="/images/list_youshi.jpg" /><a href="#reg_div"  class="youshi" title="立即注册"></a></p>
		<p><img src="/images/list_liucheng.jpg" /><a href="#reg_div"  class="liucheng" title="立即注册"></a></p>
		<p><img src="/images/list_chengji.jpg" /><a href="#reg_div"  class="chengji" title="立即注册"></a></p>
		<p><img src="/images/list_moshi.jpg" /><a href="/frontNewsDetails.do?id=573" target="_blank" class="moshi" title="了解详情"></a></p>
	</div>
  </div>
  <div class="footer">
	<p class="w"><a href="/index.jsp" class="bottom_two">© 2014 深圳市彩付宝网络技术有限公司 </a>&nbsp;&nbsp;&nbsp;&nbsp;花样年集团成员（港联交所上市企业HK1777）&nbsp;&nbsp;&nbsp;&nbsp;
<a href="http://www.miibeian.gov.cn/" class="bottom_two" >粤ICP备14000649号</a>&nbsp;&nbsp;&nbsp;&nbsp;问题反馈：0755-61880564 </p>
	<p class="w">办公地址：深圳市福田区保税区内市花路与紫荆路的交汇处花样年福年广场B5栋3楼</p>
  </div>
<script type="text/javascript" src="/script/jquery-1.7.1.min.js"></script>
 <script type="text/javascript" src="/script/phone_verify.js"></script>
  <script>
  $(window).load(function(){
      if("${msg}"!=""){
          alert("${msg}");
      }
		var 
		tip = $("em.reg-tip"),
		regForm = $("#regForm")
		userName=$("#userName"),
		userPassword = $("#password"),
		userContact=$("#contact"),
		//regCode=$("#verifyCode"),
		regAgreement = $("#agreement"),
		regSubmit = $("#submit"),
		regError = $("#regError");
		//刷新时清空表单
//		regForm.find("input").val("");
		//提示
		tip.on("click",function(){
			$(this).prev().focus().end().hide();
		});
		//输入框
		regForm.find("input").on("blur",function(){
			if($(this).val()==""){
				$(this).next("em").show();
			}
		}).on("change",function(){
			hideError();
		}).on("keydown",function(event){
			$(this).next("em").hide();
			var keycode = event.keyCode;
			//不允许输入空格
			if(keycode==32){
				return false;
			}
		});
          regForm.find("input").each(function(){
              var $xx=$(this);
              if($xx.val()!=""){
                $xx.next(".reg-tip").hide();
            }
          });

		function showError(text){
			regError.html(text);
		}
		function hideError(){
			regError.html("");
		}

		regSubmit.on('click',function(){
			//验证用户名
			var user = userName.val();
			if(user == ""){
				showError("请输入用户名");
				userName.focus();
				return false;
			} else if(user.length<5 || user.length>25){
				showError("用户名长度为5-25个字符");
				userName.focus();
				return false;
			} else if(!/^[A-Za-z0-9_]+$/gi.test(user)){
				showError("用户名由字母数字下划线组成");
				userName.focus();
				return false;
			}
			//验证密码
			var pass = userPassword.val();
			if(pass == ""){
				showError("请输入密码");
				userPassword.focus();
				return false;
			} else if(pass.length<6 || pass.length>16){
				showError("密码必须为6-16位字符");
				userPassword.focus();
				return false;
			} else if(!/^[A-z0-9]+$/gi.test(pass)){
				showError("密码不能包含空格");
				userPassword.focus();
				return false;
			}
			//验证邮箱和手机号码
			var contact = userContact.val();
			if(contact==""){
				showError("请输入手机号码");
				userContact.focus();
				return false;
			}else if(!/^0?(13|15|17|18|14)[0-9]{9}$/gi.test(contact)){
				showError("请输入正确的手机号码");
				userContact.focus();
				return false;
			}
			//验证验证码

			var code = $.trim($("#identifyCode").val());
			if(code==""){
				showError("请输入验证码！");
                $("#identifyCode").focus();
				return false;
			}

			//验证协议
			var agreement = regAgreement.prop("checked");
			if(!agreement){
				alert("请勾选 \"我已阅读并同意 《注册协议》和 《免责声明》 《风险披露声明》\"!");
				return false;
			}
			//注册中
			regSubmit.addClass("zhece");
			$("#confirmPassword").val($("#password").val());
            $("#registerType").val("16");
            regForm.submit();

		});


      (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
          (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
              m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
      })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

      ga('create', 'UA-58313965-1', 'auto');
      ga('send', 'pageview');
  });
  //获取手机验证码
  function getTelephoneCode() {

      if ($("#ida").attr("disable") == "no") {
          var ret = verifyPhone_($("#contact").val());
          if(ret!=""){
              $("#regError").html(ret);
          }else {
              $("#regError").html("");
              $.post("/account/check-phone.do",{mobilePhone:$("#contact").val()},function(data){
                  if(data=="0"){
                      getTelephoneCode_($("#contact").val());
                  }else{
                      alert("手机号码已存在");
                  }
              });

          }
      }
  }
  </script>
 <span style="display:none">
     <script type="text/javascript" src="http://tajs.qq.com/stats?sId=40016600" charset="UTF-8"></script>

		</span>

 <script type="text/javascript">
     var google_tag_params = {
         dynx_itemid: 'REPLACE_WITH_VALUE',
         dynx_itemid2: 'REPLACE_WITH_VALUE',
         dynx_pagetype: 'REPLACE_WITH_VALUE',
         dynx_totalvalue: 'REPLACE_WITH_VALUE',
     };
 </script>
 <script type="text/javascript">
     /* <![CDATA[ */
     var google_conversion_id = 962632519;
     var google_custom_params = window.google_tag_params;
     var google_remarketing_only = true;
     /* ]]> */
 </script>
 <script type="text/javascript" src="//www.googleadservices.com/pagead/conversion.js">
 </script>
 <noscript>
     <div style="display:inline;">
         <img height="1" width="1" style="border-style:none;" alt="" src="//googleads.g.doubleclick.net/pagead/viewthroughconversion/962632519/?value=0&amp;guid=ON&amp;script=0"/>
     </div>
 </noscript>
 </body>
</html>
