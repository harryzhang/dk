<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>帮助中心-内容维护-修改</title>
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
		<script charset="utf-8" src="../kindeditor/plugins/code/prettify.js"></script>
		<script charset="utf-8" src="../kindeditor/kindeditor.js"></script>
		<script charset="utf-8" src="../kindeditor/lang/zh_CN.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
		
		<script type="text/javascript">
			var editor_content;
			KindEditor.ready(function(K) {
				editor_content = K.create('textarea[name="paramMap.anwer"]', {
					cssPath : '../kindeditor/plugins/code/prettify.css',
					uploadJson : 'kindEditorUpload.do',
					fileManagerJson : '../kindEditorManager.do',
					allowFileManager : true
				});
			});
			
			function submitData(){
		var param={};
		$("#tr_content").val(editor_content.html());
		param["paramMap.question"]=$("#tb_title").val();
		param["paramMap.questionId"]=$("#id").val();
		param["paramMap.anwer"]=$("#tr_content").val();
		param["paramMap.helpType"]=$("#helpType").val();
		param["paramMap.publisher"]=$("#tb_publisher").val();
		param["paramMap.publishTime"]=$("#tb_publishTime").val();
		param["paramMap.sortId"]=$("#sort").val();
		
		$.post("updateHelpManager.do", param, function(data) {
			if (data == "1") {
				alert("修改成功")
				window.parent.closeMthod();
			}
			if (data == "2") {
				alert("修改失败");
				return;
			}
		});
		}
		</script>
		
	</head>
	<body>
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div style="width: auto; background-color: #FFF; padding: 10px;">
						<table width="100%" border="0" cellspacing="1" cellpadding="3">
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
							<s:hidden name="paramMap.questionId" id="id"/>
									标题：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_title" name="paramMap.question"
										cssClass="in_text_250" theme="simple"></s:textfield>
									<span class="require-field">*<s:fielderror fieldName="paramMap['question']"></s:fielderror></span>
								</td>
							</tr>
							<tr>
								<td style="height: 25px;" align="right" class="blue12">
									帮助类型：
								</td>
								<td align="left" class="f66" i>
									<s:select list="types" theme="simple" name="paramMap.typeId" listKey="id" listValue="helpDescribe"  id="helpType"/>
									<span class="require-field">*<s:fielderror fieldName="paramMap['helpDescribe']"></s:fielderror></span>
								</td>
							</tr>
							<tr>
								<td style="height: 25px;" align="right" class="blue12">
										<input type="hidden" name="paramMap.sortId" value="1" id="sort"/>
									内容：
								</td>
								<td align="left" class="f66">
									<textarea id="tr_content" name="paramMap.anwer" rows="20" class="textareash"
											style="width: 670px; padding:5px;">
										<s:property value="paramMap.anwer"/>
									</textarea>
									<span class="require-field">*<s:fielderror fieldName="paramMap['anwer']"></s:fielderror></span>
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									发布人：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_publisher" name="paramMap.publisher"
										cssClass="in_text_2" cssStyle="width: 150px" theme="simple"></s:textfield>
									<span class="require-field">*<s:fielderror fieldName="paramMap['publisher']"></s:fielderror></span>
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									发布时间：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_publishTime" name="paramMap.publishTime"
										cssClass="in_text_2" cssStyle="width: 150px" theme="simple" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>
									<span class="require-field"><s:fielderror fieldName="paramMap['publishTime']"></s:fielderror></span>
								</td>
							</tr>
							<tr>
								<td height="36" align="right" class="blue12">
									<s:hidden name="action"></s:hidden>
									&nbsp;
								</td>
								<td>
									<button id="btn_save"
										style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px" onclick="submitData();"></button>
									&nbsp;
									<button type="reset"
										style="background-image: url('../images/admin/btn_chongtian.jpg'); width: 70px; height: 26px; border: 0px"></button>
									&nbsp; &nbsp;
									<span class="require-field"><s:fielderror fieldName="actionMsg" theme="simple"></s:fielderror></span>
								</td>
							</tr>
						</table>
						<br />
					</div>
				</div>
			</div>
	</body>
</html>
