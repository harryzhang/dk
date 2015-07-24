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
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" language="javascript">
	$(function() {
		//提交表单
		$("#btn_save").click(function() {
			var fileName = $("#userFile").val();
			if (fileName == '') {
				alert("请选择需要导入的已还款信息");
				return;
			}
			var fileExt = fileName.substring(fileName.lastIndexOf("."));
			if (fileExt != ".xls") {
				alert("请按下载好的模版填写完整的信息后再导入");
				return;
			}
			$("#importForm").submit();
		});

		$("#userFile").click(function() {
			$("#resultDiv").html("");
		});
	});
</script>
</head>
<body>
	<form id="importForm" action="importBorrowDetailHHN.do" enctype="multipart/form-data" method="post">
		<div id="right" style="background-image: url; background-position: top; background-repeat: repeat-x;">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td id="admins" width="100" height="28" class="main_alll_h2"><a href="javascript:void(0)">导入用户资料</a></td>
							<td width="2">&nbsp;</td>
						</tr>
					</table>
				</div>
				<div style="width: auto; background-color: #FFF; padding: 30px;">
					<table width="100%" border="0" cellspacing="1" cellpadding="3">
						<tr>
							<td style="width: auto; height: 25px;" align="center" class="blue12" colspan="2">如您需要批量导入借款信息，请在附件下载表格。将表内的信息填写完整。<br /></td>
						</tr>
						<tr>
							<td style="width: 412px;"></td>
							<td style=" height: 25px;" class="blue12"><span style="color: red;">注意：<br /> 1.&nbsp;请严格按照模板填写资料,否则会导致导入数据失败。 <br /> 2.&nbsp;请注意不要重复导入数据,会导致标的重复导入。 <br /> </span></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td></td>
						</tr>
						<tr>
							<td style="width: auto; height: 25px;" align="center" class="blue12" colspan="2"><a href="../templete/templete.xls" style="color: blue;">下载表格：《客户注册信息》</a></td>
						</tr>
						<tr>
							<td style="width: auto; height: 25px;" align="center" class="blue12" colspan="2">导入文件：&nbsp;&nbsp; <input type="file" id="userFile" style="height: 25px;border: 1px #ccc solid;"
								name="userFile" />&nbsp;&nbsp; <input type="button" id="btn_save" value="导入" /> <span style="color: red;"><br /> <br /> <s:fielderror name="userFile"></s:fielderror> <s:actionmessage />
							</span></td>
						</tr>
					</table>
				</div>
				<%--<s:if test="#request.resultList != null">
					<div style="padding-left: 426px;">
						<span class="blue12" style="font-weight: bold;">导入不成功的信息</span>
						<ul>
							<s:iterator value="#request.resultList" var="val">
								<li class="blue12" style="color: red;">${val}</li>
							</s:iterator>
						</ul>
					</div>
				</s:if>--%>
				<div style="padding-left: 426px;" id="resultDiv">
					<s:if test="#request.result != null">
						<span class="blue12" style="font-weight: bold;">导入结果:</span>
						<ul>
							<s:iterator value="#request.result" var="val">
								<li class="blue12" style="color: red;">${val}</li>
							</s:iterator>
						</ul>
					</s:if>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
