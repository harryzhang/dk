<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<%
    String isChangePhone = request.getParameter("icp");
    if("1".equals(isChangePhone)){
        request.setAttribute("j",3);
    }else{
        String isVerifyPhone = request.getParameter("ivp");
        if("1".equals(isVerifyPhone)){
            request.setAttribute("j",3);
        }
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/include/head.jsp"></jsp:include>
<link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
<link href="css/user.css" rel="stylesheet" type="text/css" />
<style>
.inp188 {
	height: 20px;
	width: 188px;
	border: 1px solid rgb(204, 204, 204);
	line-height: 20px;
}
.sendBtn{font-family:Arial;line-height: 34px;color: #333;cursor: pointer;background: url(/images/sprit.png) no-repeat -111px -100px;text-align: center;height: 31px;width: 88px;border: none;display: inline-block;font-size: 13px;}
.sendBtn1{font-family:Arial;line-height: 34px;color: #333;cursor: pointer;background: url(/images/sprit.png) no-repeat 0px 0px;text-align: center;height: 31px;width: 142px;border: none;display: inline-block;font-size: 13px;}
</style>
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="/script/phone_verify.js"></script>
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>
<div class="user-all">
  <div class="user-center">
    <div style=" width:1170px; margin:0px auto; padding-bottom:24px;"><img src="/images/v1/detail_logo_bg.jpg" width="1170" height="6"  alt=""/></div>
    <div style=" overflow:hidden">
      <div class="u-left">
        <jsp:include page="/include/menu_userManage.jsp"></jsp:include>
      </div>
      <div class="u-right">
        <div class="user-right">
          <h2>安全中心</h2>
          <ul class="grxx_aqzx_ul">
            <li>密码修改</li>
            <li>安全问题设置</li>
            <li>邮箱设置</li>
            <s:if test="!#session.user.phoneHasVerify">
                <li>手机号码验证</li>
            </s:if>
            <s:else>
                <li>手机号码修改</li>
            </s:else>

          </ul>
          <div class="cle"></div>
          <div class="grxx_aqzx">
            <div class="grxx_aqzx_one" id="update_0"
          
            <s:if test="#request.j==2||#request.j==3">style="display: none"</s:if>
            <s:else>style="padding-bottom: 10px;" </s:else>
            >
            <h3> <span>密码修改</span> </h3>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <th colspan="2" align="left" style="height: 58px; color: rgb(233, 71, 24); font-size: 14px; margin: 15px 15px 30px; line-height: 40px; padding-left: 40px">会员登录密码修改</th>
              </tr>
              <tr style="height:30px;">
                <td width="18%" align="right">原密码：</td>
                <td width="83%"><input type="password" class="inp188" id="oldPassword" />
                  <span class="txt">输入您现在的帐号密码</span></td>
              </tr>
              <tr style="height:30px;">
                <td align="right">新密码：</td>
                <td><input type="password" class="inp188" id="newPassword" />
                  <span class="txt">输入您的新密码</span></td>
              </tr>
              <tr style="height:30px;">
                <td align="right">确认新密码：</td>
                <td><input type="password" class="inp188" id="confirmPassword" />
                  <span class="txt">请再次输入您的新密码</span></td>
              </tr>
              <tr>
                <td align="right">&nbsp;</td>
                <td style="padding-top: 10px;"><a href="javascript:void(0);" class="bcbtn" onclick="javascript: updateLoginPassword();">提交</a></td>
              </tr>
              <tr>
                <td colspan="2"><span style="color: red; float: none;" id="s_tip" class="formtips"></span></td>
              </tr>
            </table>
 

          </div>


          <div class="grxx_aqzx_one" id="update_1"  style="display: none; line-height:38px;" >
            <h3 style=" margin-bottom:30px;"> <span>设置安全问题</span> </h3>
            <table width="736" border="0">
              <s:if test="#request.map.answer!='' && #request.map.question!=''">
                <tr>
                  <td width="100px" align="right"><input id="id" type="hidden" value="${map.id}" />
                    <s:if test="#request.map.answer!='' && #request.map.question!=''"> 原始问题： </s:if></td>
                  <td><s:if test="#request.map.answer!='' && #request.map.question!=''">
                      <select id="oldQuestion">
                        <option value="">请选择</option>
                        <option value="您的生日">您的生日?</option>
                        <option value="您父亲的姓名">您父亲的姓名?</option>
                        <option value="您母亲的姓名">您母亲的姓名?</option>
                        <option value="您高中班主任姓名">您高中班主任姓名?</option>
                        <option value="您的出生地">您的出生地?</option>
                        <option value="您初恋的姓名">您初恋的姓名?</option>
                        <option value="您公司的名称">您公司的名称?</option>
                        <option value="您的职业">您的职业?</option>
                        <option value="您的爱好">您的爱好?</option>
                        <option value="您的配偶">您的配偶?</option>
                        <option value="您的小学校名">您的小学校名?</option>
                        <option value="您的小学班主任姓名">您的小学班主任姓名?</option>
                      </select>
                      &nbsp;&nbsp; <font style="color:red">*</font>请选择您的原始问题。 </s:if></td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td width="100px" align="right"><input id="id" type="hidden" value="${map.id}" />
                    <s:if test="#request.map.answer!='' && #request.map.question!=''"> 原始答案： </s:if></td>
                  <td><s:if test="#request.map.answer!='' && #request.map.question!=''">
                      <input id="oldAnswer" type="text" />
                      &nbsp;&nbsp;<font style="color:red">*</font>请填写原始安全问题的答案。 </s:if></td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td width="100px" align="right">&nbsp;&nbsp;</td>
                  <td>&nbsp;&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
              </s:if>
              <tr>
                <td align="right" width="100px">安全问题：</td>
                <td><select id="question">
                    <option value="">请选择</option>
                    <option value="您的生日">您的生日?</option>
                    <option value="您父亲的姓名">您父亲的姓名?</option>
                    <option value="您母亲的姓名">您母亲的姓名?</option>
                    <option value="您高中班主任姓名">您高中班主任姓名?</option>
                    <option value="您的出生地">您的出生地?</option>
                    <option value="您初恋的姓名">您初恋的姓名?</option>
                    <option value="您公司的名称">您公司的名称?</option>
                    <option value="您的职业">您的职业?</option>
                    <option value="您的爱好">您的爱好?</option>
                    <option value="您的配偶">您的配偶?</option>
                    <option value="您的小学校名">您的小学校名?</option>
                    <option value="您的小学班主任姓名">您的小学班主任姓名?</option>
                  </select>
                  &nbsp;&nbsp; <font style="color:red">*</font>请认真选择或填写问题、答案，此将成为您找回密码的凭据。</td>
                <td align="left"></td>
              </tr>
              <tr>
                <td align="right" width="100px">问题答案：</td>
                <td><input type="text" valeu="${map.answer}" id="answer" />
                  &nbsp;&nbsp;<font style="color:red">*</font>答案不支持大小写。</td>
                <td align="left"></td>
              </tr>
              <tr height="80">
                <td align="center" colspan="2"><s:if test="#request.map.question!=''">
                    <input type="button" onclick="questionUpdate();" value="修改" id="answer" class="bcbtn" />
                  </s:if>
                  <s:else>
                    <input type="button" onclick="questionAdd();" value="提交" id="answer" class="bcbtn" />
                  </s:else></td>
                <td>&nbsp;</td>
              </tr>
            </table>
          </div>
          <div class="grxx_aqzx_one" 
        
          <s:if test="#request.j==2" > style="padding-bottom: 10px;" </s:if>
          <s:else>style="display: none" </s:else>
          >
          <h3  style=" margin-bottom:30px;"> <span>设置邮箱</span> </h3>
          <table border="0">
            <s:if test="#request.emailBound !='' && #request.emailBound != null">
              <tr height="40">
                <td width="100px" align="right"><input id="id" type="hidden" value="${map.id}" />
                  <s:if test="#request.emailBound!='' && #request.emailBound != null"> 原始邮箱： </s:if></td>
                <td><s:if test="#request.emailBound !='' && #request.emailBound != null">
                    <s:property value="#request.emailBound.substring(0,3)+'*****'" />
                  </s:if>
                  <s:else> 无绑定邮箱 </s:else></td>
                <td>&nbsp;</td>
              </tr>
            </s:if>
            <tr height="40">
              <td width="100px" align="right"><input id="id" type="hidden" value="${map.id}" />
                登录密码：</td>
              <td><input id="password" type="password" maxlength="20" />
                &nbsp;&nbsp;&nbsp; <span id="s_password"></span> <font style="color:red">*</font>请输入登录密码。</td>
              <td>&nbsp;</td>
            </tr>
            <tr height="40">
              <td align="right" width="100px">邮箱地址：</td>
              <td><input id="email" type="text" maxlength="30" onblur="checkEmail();" />
                &nbsp;&nbsp;<span id="s_email"></span> <font style="color:red">*</font>请认真填写您的邮箱</td>
              <td align="left"></td>
            </tr>
            <tr height="80">
              <td align="center" colspan="2"><s:if test="#request.emailBound!=null && #request.emailBound != ''">
                  <input type="button" onclick="updateEmail();" value="修改" id="update" class="bcbtn" />
                </s:if>
                <s:else>
                  <input type="button" onclick="updateEmail();" value="提交" id="save" class="bcbtn" />
                </s:else></td>
              <td>&nbsp;</td>
            </tr>
          </table>
        </div>

              <div class="grxx_aqzx_one" id="phoneDiv" style="display: none;">
                  <s:if test="!#session.user.phoneHasVerify">
                  <h3 style=" margin-bottom:30px;"> <span>验证手机号码${param.icp=="1"?"(原手机已与账户解除绑定，请验证新手机)":""}</span> </h3>
                  <table border="0">
                      <tbody><tr height="40">
                          <td width="120px" align="right">
                              待验证手机号码： </td>
                          <td><input type="text" value="${session.user.mobilePhone}" id="mobile"/></td>
                          <td><a href="javascript:;" onclick="getTelephoneCode();" id="ida" disable="no" class="sendBtn">
                              获取验证码
                          </a></td>
                          <td><span style="color: red;" id="s_telephone" class="formtips"></span></td>
                      </tr>
                      <tr height="40">
                          <td width="120px" align="right">
                              验证码： </td>
                          <td><input id="code"  type="text" maxlength="11" ></td>
                          <td></td>
                      </tr>
                      <tr height="80">
                          <td></td>
                          <td><a href="javascript:void(0);" class="bcbtn" onclick="phoneVerify()">提交</a>
                          </td>
                          <td>&nbsp;</td>
                      </tr>
                      </tbody></table>
                  </s:if>
                  <s:else>
                      <h3 style=" margin-bottom:30px;"> <span>验证原手机号码</span> </h3>
                      <table border="0">
                          <tbody>
                          <tr height="40">
                              <td width="120px" align="right">
                                  原手机号码： </td>
                              <td>${session.user.mobilePhone}</td>
                              <td><a href="javascript:;" onclick="getTelephoneCode_('${session.user.mobilePhone}')" id="ida" disable="no" class="sendBtn">
                                  获取验证码
                              </a></td>
                              <td><span style="color: red;" id="s_telephone" class="formtips"></span></td>
                          </tr>
                          <tr height="40">
                              <td width="120px" align="right">
                                  手机验证码： </td>
                              <td><input id="code" type="text" maxlength="11" onblur="checkEmail();"></td>
                              <td></td>
                          </tr>

                          <tr height="80">
                              <td></td>
                              <td><a href="javascript:void(0);" class="bcbtn" onclick="disablePhoneVerify();">提交</a>
                              </td>
                              <td>&nbsp;</td>
                          </tr>
                          </tbody></table>


                  </s:else>
              </div>

      </div>
  </div>   
</div>

  </div>   
</div>
<div class="cle"></div>

<!-- 引用底部公共部分 -->

<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script> 
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script> 
<script type="text/javascript" src="common/date/calendar.js"></script> 
<script type="text/javascript" src="css/popom.js"></script> 
<script>
		$(function() {
			var j = '${request.j}';
			$(".wdzh_next_left li").eq(4).addClass("wdzh_next_left_ong").find("a").attr("style","color: rgb(233, 71, 24);");
			if(j==2){
				$(".grxx_aqzx_ul li").eq(2).addClass("aqzxxk");
			}else if(j==3){
				$(".grxx_aqzx_ul li").eq(3).addClass("aqzxxk");
                $(".grxx_aqzx_one").eq(3).show();
			}else{
                $(".grxx_aqzx_ul li").eq(0).addClass("aqzxxk");
            }
			$(".grxx_aqzx_ul li").click(function() {
			var index = $(".grxx_aqzx_ul li").index(this);
				$(".grxx_aqzx_one").each(function(i) {
						if (i == index)
							$(".grxx_aqzx_one").eq(i).show();
						else
							$(".grxx_aqzx_one").eq(i).hide();
				});

				$(".grxx_aqzx_ul li").removeClass("aqzxxk");
				$(this).addClass("aqzxxk");
			})
		});
		function updateLoginPassword() {
			param["paramMap.oldPassword"] = $("#oldPassword").val();
			param["paramMap.newPassword"] = $("#newPassword").val();
			param["paramMap.confirmPassword"] = $("#confirmPassword").val();
			param["paramMap.type"] = "login";
			if ($("#oldPassword").val() == "" || $("#newPassword").val() == "" || $("#confirmPassword").val() == "") {
				$("#s_tip").html("请完整填写密码信息");
				return;
			} else if ($("#newPassword").val().length < 6 || $("#newPassword").val().length > 20) {
				$("#s_tip").html("密码长度必须为6-20个字符");
				return;
			} else if ($("#newPassword").val() != $("#confirmPassword").val()) {
				$("#s_tip").html("两次密码不一致");
				return;
			}
			$.post("updateLoginPass.do", param, function(data) {
				if (data == 1) {
					alert("两次密码输入不一致");
					$("#newPassword").attr("value", "");
					$("#confirmPassword").attr("value", "");
				} else if (data == 3) {
					alert("新密码修改失败");
					$("#oldPassword").attr("value", "");
					$("#newPassword").attr("value", "");
					$("#confirmPassword").attr("value", "");
				} else if (data == 2) {
					alert("旧密码错误");
					$("#oldPassword").attr("value", "");
					$("#newPassword").attr("value", "");
					$("#confirmPassword").attr("value", "");
				} else {//密码修改成功，跳到登录页面
					$("#oldPassword").attr("value", "");
					$("#newPassword").attr("value", "");
					$("#confirmPassword").attr("value", "");
					alert("修改密码成功,请重新登录");
					window.location.href="logout.do";
				}
			});
		}

		//交易密码修改
		function updateDealPassword() {
			param["paramMap.oldPassword"] = $("#oldDealpwd").val();
			param["paramMap.newPassword"] = $("#newDealpwd").val();
			param["paramMap.confirmPassword"] = $("#confirmDealpwd").val();
			param["paramMap.type"] = "deal";
			if ($("#oldDealpwd").val() == "" || $("#newDealpwd").val() == "" || $("#confirmDealpwd").val() == "") {
				$("#d_tip").html("请完整填写密码信息");
				return;
			} else if ($("#newDealpwd").val().length < 6 || $("#newDealpwd").val().length > 20) {
				$("#d_tip").html("密码长度必须为6-20个字符");
				return;
			} else if ($("#newDealpwd").val() != $("#confirmDealpwd").val()) {
				$("#d_tip").html("两次密码不一致");
				return;
			}
			$.post("updateLoginPass.do", param, function(data) {
				if (data == 1) {
					alert("两次密码输入不一致");
					$("#newDealpwd").attr("value", "");
					$("#confirmDealpwd").attr("value", "");
				} else if (data == 3) {
					alert("新密码修改失败");
					$("#oldDealpwd").attr("value", "");
					$("#newDealpwd").attr("value", "");
					$("#confirmDealpwd").attr("value", "");
				} else if (data == 2) {
					alert("旧密码错误");
					$("#oldDealpwd").attr("value", "");
					$("#newDealpwd").attr("value", "");
					$("#confirmDealpwd").attr("value", "");
				} else if (data == 4) {
					//add by lw
					alert("密码长度输入错误,密码长度范围为6-20");
					$("#oldDealpwd").attr("value", "");
					$("#newDealpwd").attr("value", "");
					$("#confirmDealpwd").attr("value", "");
					//end 
				} else {//密码修改成功，跳到登录页面
<%--alert("修改密码成功,新密码为:" + $("#newDealpwd").val());--%>
					$("#oldDealpwd").attr("value", "");
					$("#newDealpwd").attr("value", "");
					$("#confirmDealpwd").attr("value", "");
					alert("修改密码成功,请重新登录");
					window.location.href="logout.do";
				}
			});
		}
		//新增安全问题
		function questionAdd() {
			var question = $("#question").val();
			var answer = $("#answer").val();
			if (null == question) {
				alert("请选择安全问题");
				return false;
			}
			if (null == answer) {
				alert("请选择安全问题");
				return false;
			}
			param["paramMap.question"] = question;
			param["paramMap.answer"] = answer;
			$.post("addQuestion.do", param, function(data) {
				if (data == '1') {
					alert("新增成功");
					window.location.reload();
				}
				if (data == '2') {
					alert("新增失败");
				}

			});
		}

		//修改安全问题
		function questionUpdate() {
			var question = $("#question").val();
			var oldAnswer = $("#oldAnswer").val();
			var oldQuestion = $("#oldQuestion").val()
			var answer = $("#answer").val();
			if (null == oldQuestion) {
				alert("请选择原始问题");
				return false;
			}
			if (null == oldAnswer) {
				alert("请填写原始答案");
				return false;
			}
			if (null == question) {
				alert("请填写安全问题");
				return false;
			}
			if (null == answer) {
				alert("请填写安全问题");
				return false;
			}
			param["paramMap.question"] = question;
			param["paramMap.oldQuestion"] = oldQuestion;
			param["paramMap.oldAnswer"] = oldAnswer;
			param["paramMap.answer"] = answer;
			$.post("updateQuestion.do", param, function(data) {
				if (data == '3') {
					alert("问题或答案错误");
					return false;
				} else {
					$.post("updateQuestion.do", param, function(data) {
						if (data == '1') {
							alert("修改成功");
						}
						if (data == '2') {
							alert("修改失败");
						}

					});
				}

			});
		}

		var falg = false;
		function checkEmail() {
			param["paramMap.email"] = $("#email").val();
			if ($("#email").val() == "") {
				$("#s_email").html("<font style='color:red'>请输入邮箱地址</font>");
				return falg = false;
			} else if (!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test($("#email").val())) {
				//alert("邮箱格式错误");
				$("#s_email").html("<font style='color:red'>邮箱格式错误</font>");
				return falg = false;
			} else {
				$("#s_email").hide();
				return falg = true;
			}
		}

		//新增邮箱
		function addEmail() {
			if (falg) {
				var password = $("#password").val();
				var email = $("#email").val();
				if (null == password) {
					$("#s_password").html("<font style='color:red'>密码不能为空</font>");
					return false;
				}
				if (null == email) {
					$("#s_email").html("<font style='color:red'>请输入邮箱地址</font>");
					return false;
				}
				param["paramMap.email"] = email;
				$.post("addEmail.do", param, function(data) {
					if (data == '1') {
						alert("新增成功");
						window.location.reload();
					}
					if (data == '2') {
						alert("新增失败");
					}
				});
			}
		}

		//修改邮箱
		function updateEmail() {
			if (falg) {
				var password = $("#password").val();
				var email = $("#email").val();
				if (null == password) {
					$("#s_password").html("<font style='color:red'>密码不能为空</font>");
					return false;
				}
				if (null == email) {
					$("#s_email").html("<font style='color:red'>请输入邮箱地址</font>");
					return false;
				}
				param["paramMap.password"] = password;
				param["paramMap.email"] = email;
				$.post("updateEmail.do", param, function(data) {
					if (data == '2') {
						$("#s_password").show();
						$("#s_password").html("<font style='color:red'>密码错误</font>");
						return false;
					} else  if (data == '3') {
						$("#s_email").show();
						$("#s_email").html("<font style='color:red'>邮箱已经存在</font>");
						return false;
					} else if (data == '1') {
						alert("修改成功");
						window.location.reload();
					} else {
						alert("修改失败");
					}

				});
			}
		}
function phoneVerify(){
    var mobilePhone = $("#mobile").val();
    var code = $("#code").val();
    $.post("/account/phone-verify.do",{mobilePhone:mobilePhone,identifyCode:code},function(data){
        var ret=data.ret;
        if(ret=="0"){
            alert("验证成功");
            window.location.href="/home.do";
        }else{
            alert(data.msg);
        }
    });
}
function getTelephoneCode(){
    if ($("#ida").attr("disable") == "no") {
        var ret = verifyPhone_($("#mobile").val());
        if(ret!=""){
            $("#s_telephone").attr("class", "formtips onError");
            $("#s_telephone").html(ret);
        }else {
            $("#s_telephone").attr("class", "formtips");
            $("#s_telephone").html("");
            getTelephoneCode_($("#mobile").val());
        }
    }
}
function disablePhoneVerify(){
    var code = $("#code").val();
    $.post("/account/disable-phone-verify.do",{'identifyCode':code},function(data){
        var ret=data.ret;
        if(ret=="0"){
            window.location.href="/updatexgmm.do?icp=1";
        }else{
            alert(data.msg);
        }
    });
}
	</script>
    <jsp:include page="/include/footer.jsp"></jsp:include>
</body>
</html>
