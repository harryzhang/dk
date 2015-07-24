<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" language="javascript">
	$(function() {
		$('#img').attr('src', "../" + $('#updateAdmin_paramMap_img').val());
		$("#paramMap_password").val("");
		//提交表单
		$("#btn_save").click(function() {
			$(this).hide();
			$("#updateAdmin").submit();
			return false;
		});
		$("#btn_personalHead").click(
				function() {
					var dir = getDirNum();
					var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'user/" + dir
							+ "','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall','cp':'img'}";
					json = encodeURIComponent(json);
					window.showModalDialog("uploadFileAction.do?obj=" + json, window, "dialogWidth=500px;dialogHeight=400px");
					var headImgPath = $("#img").attr("src");
					if (headImgPath == "") {
						alert("上传失败！");
						return;
					}
					$("#xximg").val(headImgPath);
				});
	});
	function uploadCall(basepath, fileName, cp) {
		if (cp == "img") {
			var path = "upload/" + basepath + "/" + fileName;
			$("#img").attr("src", "../" + path);
			$("#updateAdmin_paramMap_img").val(path);
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
</script>
</head>
<body>
	<form action="updateAdmin.do" id="updateAdmin" method="post">
		<s:hidden name="paramMap.id" />
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
							<td width="100" class="main_alll_h2">修改管理员</td>
							<td>&nbsp;</td>
						</tr>
					</table>
				</div>
				<div style="width: auto; background-color: #FFF; padding: 10px;">
					<table width="100%" border="0" cellspacing="1" cellpadding="3">
						<tr>
							<td style="height: 25px;" align="right" class="blue12">用户组：</td>
							<td align="left" class="f66"><s:select list="roleList.{?#this.id!=1&&#this.id!=2}" name="paramMap.roleId" listKey="id" listValue="name" headerKey="-2" headerValue="--请选择--"></s:select>
								<span style="color: red;">*<s:fielderror fieldName="paramMap.roleId" /> </span></td>
						</tr>
						<tr>
							<td style="width: 100px; height: 25px;" align="right" class="blue12">用户名：</td>
							<td align="left" class="f66"><s:textfield name="paramMap.userName" theme="simple" readonly="true" cssClass="in_text_2" cssStyle="width: 150px" /> <span style="color: red;">*<s:fielderror
										fieldName="paramMap.userName" /> </span></td>
						</tr>
						<tr>
							<td style="height: 25px;" align="right" class="blue12">密码：</td>
							<td align="left" class="f66"><s:password id="paramMap_password" name="paramMap.password" theme="simple" cssClass="in_text_2" cssStyle="width: 150px" /> <span style="color: red;">*<s:fielderror
										fieldName="paramMap.password" /> </span></td>
						</tr>
						<tr>
							<td style="height: 25px;" align="right" class="blue12">确认密码：</td>
							<td align="left" class="f66"><s:password name="paramMap.confirmPassword" theme="simple" cssClass="in_text_2" cssStyle="width: 150px" /> <span style="color: red;">*<s:fielderror
										fieldName="paramMap.confirmPassword" /> </span></td>
						</tr>
						<tr>
							<td style="width: 100px; height: 25px;" align="right" class="blue12">真实姓名：</td>
							<td align="left" class="f66"><s:textfield name="paramMap.realName" theme="simple" cssClass="in_text_2" cssStyle="width: 150px"></s:textfield> <span style="color: red;">*<s:fielderror
										fieldName="paramMap.realName" /> </span></td>
						</tr>
						<tr>
							<td style="width: 100px; height: 25px;" align="right" class="blue12">手机号码：</td>
							<td align="left" class="f66"><s:textfield name="paramMap.telphone" theme="simple" cssClass="in_text_2" cssStyle="width: 150px"></s:textfield> <span style="color: red;">*<s:fielderror
										fieldName="paramMap.telphone" /> </span></td>
						</tr>
						<tr>
							<td style="width: 100px; height: 25px;" align="right" class="blue12">QQ：</td>
							<td align="left" class="f66"><s:textfield name="paramMap.qq" theme="simple" cssClass="in_text_2" cssStyle="width: 150px"></s:textfield> <span style="color: red;">*<s:fielderror
										fieldName="paramMap.qq" /> </span></td>
						</tr>
						<tr>
							<td style="width: 100px; height: 25px;" align="right" class="blue12">邮箱：</td>
							<td align="left" class="f66"><s:textfield name="paramMap.email" theme="simple" cssClass="in_text_2" cssStyle="width: 150px"></s:textfield> <span style="color: red;">*<s:fielderror
										fieldName="paramMap.email" /> </span></td>
						</tr>
						<tr>
							<td style="width: 100px; height: 25px;" align="right" class="blue12">图片：</td>
							<td align="left" class="f66">
							<s:hidden name="paramMap.img" id="xximg" /> 
							<img id="img" src="" width="62" height="62"  /> <a href="javascript:void(0);" id="btn_personalHead" class="scbtn">上传图片</a>
								<span style="color: red;">*<s:fielderror fieldName="paramMap.img" /> </span></td>
						</tr>
						<tr>
							<td style="width: 100px; height: 25px;" align="right" class="blue12">是否为组长：</td>
							<td align="left" class="f66"><s:checkbox name="paramMap.isLeader" theme="simple" /></td>
						</tr>
						<tr>
							<td style="height: 25px;" align="right" class="blue12">是否禁用：</td>
							<td align="left" class="f66"><s:radio name="paramMap.enable" id="enable" theme="simple" list="#{1:'启用',2:'禁用'}" /> <span style="color: red;">*<s:fielderror
										fieldName="paramMap.enable" /> </span></td>
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
								<button type="reset" style="background-image: url('../images/admin/btn_chongtian.jpg');width: 70px;height: 26px;border: 0px"></button>&nbsp; &nbsp; <span style="color: red;">
									<s:fielderror fieldName="actionMsg" theme="simple"></s:fielderror> </span></td>
						</tr>
					</table>
					<br />
				</div>
			</div>
		</div>
	</form>
</body>
</html>
