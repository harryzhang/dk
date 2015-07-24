<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>业务受理</title>
<style>
b {
color: red;
}
.hhn {
	border: 1px #ccc solid;
}
</style>
<meta http-equiv="description" content="This is my page" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<link href="../css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../common/dialog/popup.js"></script>
<script language="javascript" type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script>
	$(function(){
	});

	//弹出窗口关闭
	function closeMthodes() {
		window.parent.closeMthod();
	}

	//认证查看
	function queryMsg() {
		var id = '${map.publisher}';
		window.location.href = 'queryPerUserCreditAction.do?userId=' + id;
	}
	
	function changeSelectValue() {
		alert(this.value);
	}
	function changeValue(obj,id) {
		var aa = $(id).val()
		$(id).val('');
	}
</script>
</head>
<body>
<form id="addForm" action="uploadFile.do" method="post" enctype="multipart/form-data">
		<div align="right">
			<input type="button" style="background: #666666;"
								value="返回" onclick="closeMthodes();" />
		</div>
		<table style="margin-left: 40px; margin-right: 40px; margin-top: 20px; width: 90%" border="1">
			<tr style="height: 30px">
				<td colspan="4" style="text-align: left;">合同签约</td>
			</tr>
			<input type="hidden" value="${loanPersonDo.loanPersonId}" name="loanPersonDo.loanPersonId"  />
        	<input type="hidden" value="${loanPersonDo.loanDo.loanId}" name="loanPersonDo.loanId"/>
			<tr>
			<td style="width: 100px; height: 30px;" align="right"
					class="blue12">
					真实姓名：
				</td>
				<td align="left">
				<input id="realName" name="loanPersonDo.realName" value="${loanPersonDo.realName }"
						   style="height: 20px;line-height: 20px;" type="text" readonly="readonly"/>
				</td>
				<td style="width: 100px; height: 30px;" align="right"
					class="blue12" >
					身份证号：
				</td>
				<td align="left">
					<input id="cardNo" name="loanPersonDo.idNo" value="${loanPersonDo.idNo }"
						  style="height: 20px;line-height: 20px;" type="text" readonly="readonly"/>
				</td>
			</tr>
	</table>
	<table style="margin-left: 40px; margin-right: 40px; margin-top: 20px; width: 90%" border="1">
			<tr style="height: 30px">
				<td colspan="4" style="text-align: left;">资料上传</td>
			</tr>
			<c:forEach items="${loanPersonDo.certificateDoList}" var="certificateDo">
				<c:set var="count" value="${count}" />
				<td align="left" class="f66">
					<img src="${fileAccessUrl}${certificateDo.filePath }" alt="" />
					<input type="hidden" value="${certificateDo.certificateId}" name="loanPersonDo.certificateDoList[${count-1 }].certificateId" />
					<input type="hidden" value="${certificateDo.loanId}" name="loanPersonDo.certificateDoList[${count-1 }].loanId" />
					<input type="hidden" value="${certificateDo.loanPersonId}" name="loanPersonDo.certificateDoList[${count-1 }].loanPersonId" />
					<input type="hidden" value="${certificateDo.certificateType}" name="loanPersonDo.certificateDoList[${count-1 }].certificateType" />
					<input type="hidden" id="file${count}"  value="${certificateDo.certificateType}"/>
					<input id="filePath${count }" name="loanPersonDo.certificateDoList[${count-1 }].filePath" value="${certificateDo.filePath}" type="hidden"/>
					<input type="file" name="files" id="fil${count}" style="display: none;" onchange="changeValue(this,'#filePath${count}')"/>
				</td>
				</c:forEach>
		</table>
</form>
</body>
</html>
