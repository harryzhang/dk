<%@ page language="java" import="java.util.*,java.io.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<link rel="stylesheet" href="../css/jbox/Gray/jbox.css" type="text/css"></link>
<script type="text/javascript" language="javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" language="javascript">

	$(function() {
		if ($('#paramMap_img').val() == '' || $('#paramMap_img').val() == undefined) {
			$("#img").attr("src", "../images/NoImg.GIF");
		} else {
			$('#img').attr('src', "../" + $('#paramMap_img').val());
		}
		//提交表单
		$("#btn_save").click(function() {
			var telphone = $.trim($("#telphone").val());
			var regex = /^1[3|4|5|8]\d{9}$/;
			if (regex.test(telphone)) {
				//$(this).hide();
				$("#tips").html("");
				//$("#addAdmin").submit();
				var param = {};
				if ($("#password").val() == '' || $("#confirmPassword").val() == '') {
					alert("密码不能为空");
					return;
				}
				if ($("#password").val() != $("#confirmPassword").val()) {
					alert("俩次密码不一致");
					return;
				}
				param["paramMap.password"] = $("#password").val();
				param["paramMap.confirmPassword"] = $("#confirmPassword").val();
				param["paramMap.userName"] = $("#userName").val();
				param["paramMap.realName"] = $("#realName").val();
				param["paramMap.telphone"] = $("#telphone").val();
				<%--param["paramMap.qq"] = $("#qq").val();--%>
				param["paramMap.email"] = $("#email").val();
				param["paramMap.img"] = $("#img").attr("src");
				param["paramMap.enable"] = $("input[name=paramMap.enable]:checked").val();
				param["paramMap.roleId"] = $("#roleIds").val();
				$.post("addAdmin.do",param,function(data) {
					if(data.length>1000){
						$("#hhnhhn").html(data);
						return;
					}
					alert(data);
					if("添加成功"==data){
						window.location.href='addAdminInit.do';
					}
				});
			} else {
				$("#tips").html("手机号码不正确!请重新输入");
				return false;
			}
		});
		$("#btn_personalHead").click(function() {
					var dir = getDirNum();
					var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'user/" + dir
							+ "','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall','cp':'img'}";
					json = encodeURIComponent(json);
					window.showModalDialog("uploadFileAction.do?obj=" + json, window, "dialogWidth=500px;dialogHeight=400px");
					var headImgPath = $("#img").attr("src");
					if (headImgPath == "") {
						alert("上传失败！");
					}
			});
		});
	function uploadCall(basepath, fileName, cp) {
		if (cp == "img") {
			var path = "upload/" + basepath + "/" + fileName;
			$("#img").attr("src", "../" + path);
			$("#paramMap_img").val(path);
		}
	}
	function getDirNum() {
		var date = new Date();
		var m = date.getMonth() + 1;
		var d = date.getDate();
		if (m < 10) {
			m = "0" + m;
		}
		if (d < 10) {
			d = "0" + d;
		}
		var dirName = date.getFullYear() + "" + m + "" + d;
		return dirName;
	}
	function hhnCallBack(data){
		alert(data);
	}
</script>
</head>
<body id="hhnhhn">
	<%--	<form id="addAdmin" action="addAdmin.do" method="post">--%>
	<div id="right">
		<div style="padding: 15px 10px 0px 10px;">
			<div>
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="28" class="xxk_all_a"><a href="queryRoleList.do">管理组列表</a></td>
						<td width="2">&nbsp;</td>
						<td width="100" class="xxk_all_a"><a href="addRoleInit.do">添加管理组</a></td>
						<td width="2">&nbsp;</td>
						<td width="100" height="28" class="xxk_all_a"><a href="queryAdminInit.do">管理员列表</a></td>
						<td width="2">&nbsp;</td>
						<td width="100" class="main_alll_h2"><a href="addAdminInit.do">添加管理员</a></td>
						<td>&nbsp;</td>
					</tr>
				</table>
			</div>
			<div style="width: auto; background-color: #FFF; padding: 10px;">
				<table width="100%" border="0" cellspacing="1" cellpadding="3">
					<tr>
						<td style="height: 25px;" align="right" class="blue12">管理组：</td>
						<td align="left" class="f66"><s:select list="roleList.{?#this.id!=1&&#this.id!=2}" name="paramMap.roleId" listKey="id" id="roleIds" listValue="name" headerKey="-2"
								headerValue="--请选择--"></s:select> <span style="color: red;">*<s:fielderror fieldName="paramMap.roleId" /> </span></td>

					</tr>
					<tr>
						<td style="width: 100px; height: 25px;" align="right" class="blue12">用户名：</td>
						<td align="left" class="f66"><s:textfield name="paramMap.userName"  theme="simple" id="userName" cssClass="in_text_2" cssStyle="width: 150px"></s:textfield> <span
							style="color: red;">*<s:fielderror fieldName="paramMap.userName" /> </span></td>
					</tr>
					<tr>
						<td style="height: 25px;" align="right" class="blue12">密码：</td>
						<td align="left" class="f66"><s:password name="paramMap.password"  theme="simple" id="password" cssClass="in_text_2" cssStyle="width: 150px" /> <span style="color: red;">*<s:fielderror
									fieldName="paramMap.password" /> </span></td>
					</tr>
					<tr>
						<td style="height: 25px;" align="right" class="blue12">确认密码：</td>
						<td align="left" class="f66"><s:password name="paramMap.confirmPassword"   id="confirmPassword" theme="simple" cssClass="in_text_2" cssStyle="width: 150px" /> <span
							style="color: red;">*<s:fielderror fieldName="paramMap.confirmPassword" /> </span></td>
					</tr>
					<tr>
						<td style="width: 100px; height: 25px;" align="right" class="blue12">真实姓名：</td>
						<td align="left" class="f66"><s:textfield name="paramMap.realName" theme="simple"  id="realName" cssClass="in_text_2" cssStyle="width: 150px"></s:textfield> <span
							style="color: red;">*<s:fielderror fieldName="paramMap.realName" /> </span></td>
					</tr>
					<tr>
						<td style="width: 100px; height: 25px;" align="right" class="blue12">手机号码：</td>
						<td align="left" class="f66"><s:textfield name="paramMap.telphone" theme="simple"  cssClass="in_text_2" cssStyle="width: 150px" id="telphone"></s:textfield> <span
							style="color: red;">* <span id="tips"></span> <s:fielderror fieldName="paramMap.telphone" /> </span></td>
					</tr>
<%--					<tr>--%>
<%--						<td style="width: 100px; height: 25px;" align="right" class="blue12">QQ：</td>--%>
<%--						<td align="left" class="f66"><s:textfield name="paramMap.qq" id="qq" theme="simple"  cssClass="in_text_2" cssStyle="width: 150px"></s:textfield> <span style="color: red;">*<s:fielderror--%>
<%--									fieldName="paramMap.qq" /> </span></td>--%>
<%--					</tr>--%>
					<tr>
						<td style="width: 100px; height: 25px;" align="right" class="blue12">邮箱：</td>
						<td align="left" class="f66"><s:textfield name="paramMap.email" id="email" theme="simple" cssClass="in_text_2" cssStyle="width: 150px"></s:textfield> <span style="color: red;">*<s:fielderror
									fieldName="paramMap.email" /> </span></td>
					</tr>
					<tr>
						<td style="width: 100px; height: 25px;" align="right" class="blue12">图片：</td>
						<td align="left" class="f66"><s:hidden name="paramMap.img" id="imgs"></s:hidden> <img id="img" src="${headImg}" width="62" height="62" /> <a href="javascript:void(0);"
							id="btn_personalHead" class="scbtn" style="margin-top: -10px;">上传图片</a> <span style="color: red;"><s:fielderror fieldName="paramMap.img" /> </span></td>
					</tr>
					<%--<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									是否为组长：
								</td>
								<td align="left" class="f66">
									<s:checkbox name="paramMap.isLeader" theme="simple"/>
								</td>
							</tr>
							--%>
					<tr>
						<td style="height: 25px;" align="right" class="blue12">状态：</td>
						<td align="left" class="f66"><s:radio name="paramMap.enable" theme="simple" list="#{1:'启用',2:'禁用'}" /> <span style="color: red;">*<s:fielderror fieldName="paramMap.enable" />
						</span></td>

					</tr>
					<tr>
						<td height="25"></td>
						<td align="left" class="f66" style="color: Red;"><s:fielderror fieldName="paramMap.allError" /></td>
					</tr>
					<tr>
						<td height="36" align="right" class="blue12">&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td height="36" align="right" class="blue12">&nbsp;</td>
						<td>
							<button id="btn_save" style="background-image: url('../images/admin/btn_queding.jpg');width: 70px;border: 0;height: 26px"></button> &nbsp;
							<button type="reset" style="background-image: url('../images/admin/btn_chongtian.jpg');width: 70px;height: 26px;border: 0px"></button>&nbsp; &nbsp; <span style="color: red;"> <s:fielderror
									fieldName="actionMsg" theme="simple"></s:fielderror> </span></td>
					</tr>
				</table>
				<br />
			</div>
		</div>
	</div>
</body>
</html>
