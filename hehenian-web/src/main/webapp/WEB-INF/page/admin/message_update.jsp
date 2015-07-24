<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>更新内容</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<link rel="stylesheet" href="../kindeditor/themes/default/default.css" />
<link rel="stylesheet" href="../kindeditor/plugins/code/prettify.css" />
<script charset="utf-8" src="../kindeditor/kindeditor.js"></script>
<script charset="utf-8" src="../kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="../kindeditor/plugins/code/prettify.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../common/dialog/popup.js"></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>

<script>
	var param = {};
	var editor_content;
	KindEditor.ready(function(K) {
		editor_content = K.create('textarea[name="paramMap.content"]', {
			cssPath : '../kindeditor/plugins/code/prettify.css',
			uploadJson : 'kindEditorUpload.do',
			fileManagerJson : 'kindEditorManager.do',
			allowFileManager : true
		});
	});

	$(function() {
		//提交表单
		$("#btn_save").click(function() {
			$(this).hide();
			param["paramMap.id"] = $("#id").val();
			param["paramMap.columName"] = $("#columName").val();
			param["paramMap.content"] = editor_content.html();
			param["paramMap.sort"] = $("#tb_sort").val();
			param["paramMap.time"] = $("#tb_Time").val();
			$.post("updateMessage.do", param, initCallBack);
		});
	});

	function initCallBack(data) {
		if(data=="1")
			window.parent.jBox.close();
		else
			alert("操作失败");
	}
</script>

</head>
<body>
		<s:hidden id="id" name="paramMap.id" />
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div></div>
				<div style="width: auto; background-color: #FFF; padding: 10px;">
					<table width="100%" border="0" cellspacing="1" cellpadding="3">
						<tr>
							<td style="width: 100px; height: 25px;" align="right" class="blue12">公告标题：</td>
							<td align="left" class="f66"><input type="hidden" id="columName" name="paramMap.columName" value="${paramMap.columName}" /> ${paramMap.columName}</td>
						</tr>

						<tr>
							<td style="height: 25px;" align="right" class="blue12">内容：</td>
							<td align="left" class="f66"><textarea id="tr_content" name="paramMap.content" rows="20" class="textareash" style="width: 670px; padding:5px;">
										<s:property value="paramMap.content" />
									</textarea> <span class="require-field"><s:fielderror fieldName="paramMap['content']"></s:fielderror> </span>
							</td>
						</tr>
						<s:if test="%{paramMap.typeId==1 ||paramMap.typeId==15 ||paramMap.typeId==18}">
							<tr>
								<td>&nbsp;</td>
								<td style="height: 25px;" align="left" class="blue12"><span style="color: red;font-size: 12px;">**** []内为系统定义字符,请勿修改</span>
								</td>
							</tr>
						</s:if>
						<tr>
							<td style="width: 100px; height: 25px;" align="right" class="blue12">序号：</td>
							<td align="left" class="f66"><s:textfield id="tb_sort" name="paramMap.sort" cssClass="in_text_150" theme="simple" value="%{paramMap.sort}"></s:textfield> <span
								class="require-field">&nbsp; &nbsp;<s:fielderror fieldName="paramMap['sort']"></s:fielderror> </span>
							</td>
						</tr>
						<tr>
							<td style="width: 100px; height: 25px;" align="right" class="blue12">发布时间：</td>
							<td align="left" class="f66"><input id="tb_Time" class="in_text_150" type="text" value="${paramMap.publishTime}" name="paramMap.publishTime"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})" /> <span class="require-field">&nbsp; &nbsp;<s:fielderror fieldName="paramMap['publishTime']"></s:fielderror>
							</span>
							</td>
						</tr>
						<tr>
							<td height="36" align="right" class="blue12"><s:hidden name="action"></s:hidden> &nbsp;</td>
							<td>
								<button id="btn_save" style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px"></button> &nbsp;
								<button type="reset" style="background-image: url('../images/admin/btn_chongtian.jpg'); width: 70px; height: 26px; border: 0px"></button> &nbsp; &nbsp; <span class="require-field"><s:fielderror
										fieldName="actionMsg" theme="simple"></s:fielderror> </span>
							</td>
						</tr>
						<tr>
							<td colspan="2"><img id="img" src="../images/NoImg.GIF" style="display: none; width: 100px; height: 100px;" />
							</td>
						</tr>
					</table>
					<br />
				</div>
			</div>
		</div>
</body>
</html>
