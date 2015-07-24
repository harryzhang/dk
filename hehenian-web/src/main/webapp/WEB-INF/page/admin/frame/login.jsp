<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>合和年在线后台管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
	<meta http-equiv="description" content="This is my page"/>
	<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
	<script type="text/javascript">
			$(function(){
				$(".login_code").val("");
				document.getElementById("userName").focus();
				$(window).bind('keyup', function(event){
				   if (event.keyCode=="13"){
				   	validatorCodeIsExpired();
				   }
				});
			});
			//初始化
			function switchCode(){
				var timenow = new Date(); 
				$("#codeNum").attr("src","imageCode.do?pageId=adminlogin&d="+timenow);
			}
			//判断验证码是否过期
			function validatorCodeIsExpired(){
				var param = {};
				param["pageId"] = "adminlogin";
				$.post("codeIsExpired.do",param,function(data){
					 if(data == 1){
					 	alert("验证码已过期");
					 	switchCode();
					 	return ;
					 }
					$("#loginForm").submit();
				});
			}
	</script>
</head>
<body style=" background:url(../images/admin/login_bg.png) top;">
    <form id="form1" action="adminLogin.do" method="post">
    <input type="hidden" value="adminlogin" name="pageId" />
         <div style="width: 100%; overflow: hidden;">
            <div style="float: left; width: 100%; text-align: left; padding:120px 0px 5px 0px;">
                <strong style=" width:518px; margin:0 auto; display:block;"><img src="../images/admin/sp2p.png" border="0"/><img src="../images/admin/logo.png" border="0"/></strong>
            </div>
          </div>
        <div style="width:518px; margin:0px 0px 0px 0px; margin-left: auto; margin-right: auto;
            overflow: hidden;">
            <div style= " width: 518px; overflow:hidden;">
                <div style="margin-left: auto; margin-right: auto;">
                    <div style="background-image: url(../images/admin/login_kuang_bg.png); overflow:hidden; height:259px;">
                        <table width="518" border="0" align="center" cellpadding="0" cellspacing="0">
                        	<tr height="40"></tr>
                            <tr>
                                <td  width="100" align="right" style="color:#fff; font-size:14px; font-weight:bold;">账号：</td>
                                <td width="300">
                                	<s:textfield id="userName" name="paramMap.userName" theme="simple" maxlength="25" cssClass="login_text" cssStyle="width: 280px"></s:textfield>
                                </td>
                            </tr>
                            <tr height="20">
                            	<td></td>
                                <td><span style="color: red; font-size:12px;"><s:fielderror fieldName="paramMap.userName" /></span></td>
                            </tr>
                            <tr>
                                <td  width="100" align="right" style="color:#fff; font-size:14px; font-weight:bold;">密码：</td>
                                <td>
                                	<s:password name="paramMap.password" cssClass="login_text" cssStyle="width: 280px" theme="simple" maxlength="20"></s:password>
                                </td>
                            </tr>
                            <tr height="20">
                            	<td></td>
                                <td><span style="color: red; font-size:12px;"><s:fielderror fieldName="paramMap.password" /></span></td>
                            </tr>
                            <tr>
                                <td width="100" align="right" style="color:#fff; font-size:14px; font-weight:bold;">验证码：</td>
                                <td valign="bottom">
                                	<s:textfield  name="paramMap.code" cssClass="login_text" theme="simple" cssStyle="width: 120px"/>
                                    <img src="imageCode.do?pageId=adminlogin" title="点击更换验证码" style="cursor: pointer; vertical-align:-4px; *vertical-align:-1px; margin-left:10px;" id="codeNum" width="58px" height="24px" onclick="javascript:switchCode()"></img>
                            	</td>
                            </tr>
                            <tr height="20">
                            	<td></td>
                                <td><span style="color: red; font-size:12px;"><s:fielderror fieldName="paramMap.code" /></span></td>
                            </tr>
                            <tr>
                            	<td></td>
                                <td style="padding: 25px 0px 0px 0px;">
                                    <input type="submit" value="确认" id="btnLogin" style="background:url(../images/admin/login_menu.png); width:100px; height:32px; line-height:32px; color:#fff; font-size:14px; font-weight:bold;"/>
                                        </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </form>
</body>
</html>

