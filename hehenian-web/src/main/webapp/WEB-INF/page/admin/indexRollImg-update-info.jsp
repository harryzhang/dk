<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>首页图片修改</title>
		<link href="../css/admin/admin_css.css" rel="stylesheet"
			type="text/css" />
		<link href="../css/css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" src="../common/dialog/popup.js"></script>
		<script language="javascript" type="text/javascript"
			src="../script/jbox/jquery.jBox-2.3.min.js"></script>

		<script type="text/javascript">
	$(function() {
		//提交表单

		//上传图片
		$("#btnUpImg")
				.click(
						function() {
							var dir = getDirNum();
							var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'lists/"
									+ dir
									+ "','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall','cp':'img'}";
							json = encodeURIComponent(json);
							window.showModalDialog("uploadFileAction.do?obj="
									+ json, window,
									"dialogWidth=500px;dialogHeight=400px");
						});
		if ("${paramMap.companyImg}" != "") {
			$("#img").attr("src", "../${paramMap.companyImg}");
		}

	});

	function uploadCall(basepath, fileName, cp) {
		if (cp == "img") {
			var path = "upload/" + basepath + "/" + fileName;
			$("#companyImg").val(path);
			$("#img").attr("src", "../" + path);
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

	function submitData() {
		param["paramMap.companyImg"] = $("#companyImg").val();
		param["paramMap.serialCount"] = $("#serialCount").val();
		param["paramMap.ordershort"] = $("#ordershort").val();
		param["paramMap.commonid"] = $("#commonid").val();
		//$.post("updateindexrollimginfo.do", param,callback); 
		//}
		
		$.post("updateIndexRollImgInfo.do", param,function(data){
				if (data == "1") {
				alert("提交成功");
				window.parent.closeMthod();
			}
			if (data == "2") {
				alert("提交失败");
				return;
			}
		},'json');
	}
	function callback(data) {

		alert(data);
	}
</script>
	</head>
	<body> 
			<s:hidden id="commonId" name="paramMap.commonId" />
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div>

					</div>

					<div style="width: auto; background-color: #FFF; padding: 10px;">
						<table width="100%" border="0" cellspacing="1" cellpadding="3">
							<!--  
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									公司名称：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_title" name="paramMap.companyName"
										cssClass="in_text_2" cssStyle="width: 250px" theme="simple"></s:textfield>
									<span class="require-field">*<s:fielderror fieldName="paramMap['companyName']"></s:fielderror></span>
								</td>
							</tr>
							-->
							<tr>
								<td style="height: 25px;" align="right" class="blue12">
									图片：
								</td>
								<td align="left" class="f66">
									<img id="img" src="../images/NoImg.GIF"
										style="width: 100px; height: 100px;" />
									<s:hidden id="companyImg" name="paramMap.companyImg" />
									<input id="btnUpImg" value="浏览..." type="button" />
									<span class="require-field">*<s:fielderror
											fieldName="paramMap['companyImg']"></s:fielderror> </span>
								</td>
							</tr>
							<!-- 
							<tr>
								<td style="height: 25px;" align="right" class="blue12">
									网站地址：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tr_content" name="paramMap.companyURL" 
											cssClass="in_text_2" cssStyle="width: 150px" theme="simple"></s:textfield>
									<span class="require-field">*<s:fielderror fieldName="paramMap['companyURL']"></s:fielderror></span>
								</td>
							</tr>
							 -->
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									序号：
								</td>
								<td align="left" class="f66">
									<s:textfield id="serialCount" name="paramMap.serialCount"
										cssClass="in_text_2" cssStyle="width: 150px" theme="simple"
										readonly="true"></s:textfield>
									<span class="require-field"><s:fielderror
											fieldName="paramMap['serialCount']"></s:fielderror> </span>
								</td>
							</tr>

							<!-- 排序 -->
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									排序：
								</td>
								<td align="left" class="f66">
									<s:textfield id="ordershort" name="paramMap.ordershort"
										cssClass="in_text_2" cssStyle="width: 150px" theme="simple"></s:textfield>
									<span class="require-field"><s:fielderror
											fieldName="paramMap['ordershort']"></s:fielderror> </span>
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									发布时间：
								</td>
								<td align="left" class="f66">
									<s:textfield name="paramMap.publishTime" cssClass="in_text_2"
										cssStyle="width: 150px" theme="simple"
										onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"></s:textfield>
									<!-- <s:date name="paramMap.publishTime" format="dd/MM/yyyy" /> -->
									<span class="require-field">*<s:fielderror
											fieldName="paramMap['publishTime']"></s:fielderror> </span>
								</td>
							</tr>
							<tr>
								<td height="36" align="right" class="blue12">
									<s:hidden name="action"></s:hidden>
									&nbsp;
								</td>
								<td>
									<button id="btn_save"
										style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px"
										onclick=
	submitData();
></button>
									&nbsp;
									<button type="reset"
										style="background-image: url('../images/admin/btn_chongtian.jpg'); width: 70px; height: 26px; border: 0px"></button>
									&nbsp; &nbsp;
									<span class="require-field"><s:fielderror
											fieldName="actionMsg" theme="simple"></s:fielderror> </span>
								</td>
							</tr>
						</table>
						<br />
					</div>
				</div>
			</div>
	</body>
</html>

