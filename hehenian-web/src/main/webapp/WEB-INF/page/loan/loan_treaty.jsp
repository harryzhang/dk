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
<script type="text/javascript" src="../script/uploadPreview.min.js"></script>
<script type="text/javascript" src="../common/dialog/popup.js"></script>
<script language="javascript" type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script>
	$(function(){
		$("#btnSearch").click(function(){
			if($("#file1").val() == '') {
				alert("请上传《委托划款协议》 ");
				return;
			}
			if($("#file2").val() == '') {
				alert("请上传《借款咨询服务协议》 ");
				return;
			}else {
				$("#btnSearch").attr("disable",true);
				window.parent.$.jBox.tip("<div class='data-submit'>提交数据中，请稍候...</div>","loading");
				$("#addForm").submit();
			}
		});
		
		$("#onSend").click(function(){
			if($("#file1").val() == '') {
				alert("请上传《委托划款协议》 ");
				return;
			}
			if($("#file2").val() == '') {
				alert("请上传《借款咨询服务协议》 ");
				return;
			}else {
				$("#onSend").attr("disable",true);
				window.parent.$.jBox.tip("<div class='data-submit'>提交数据中，请稍候...</div>","loading");
				$("#addForm").get(0).action = "uploadFileHy.do?loanPersonDo.loanDo.loanId=${loanPersonDo.loanDo.loanId}&loanPersonDo.loanDo.loanStatus=TREATY";
				$("#addForm").submit();
			}
		});
		
		for(var i=0; i < 2; i++) {
			var flag = 'file'+(i+1);
			if($('#file'+(i+1)).val().length > 0) {
				flag = 'fil'+(i+1);
			}
			new uploadPreview({ UpBtn: flag, DivShow: 'imgdiv'+(i+1), ImgShow: 'img'+(i+1) });
		}
		
		$("input[name='files']").bind("change", function() { 
			var id =this.id;
			id =id.substring(3);
			$("#filePath"+id).val('');
		});
	});

	//弹出窗口关闭
	function closeMthodes() {
		window.parent.closeMthod();
	}

	var c_window = '${c_window}';
	if(c_window == 'yes') {
		alert('${message}');
		closeMthodes();
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
<form id="addForm" action="uploadFileHy.do" method="post" enctype="multipart/form-data">
		<div align="right">
			<input type="button" style="background: #666666;" value="保存" id="btnSearch" />
			<input type="button" style="background: #666666;" title="提交后将不能修改，请确认无误！" value="提交" id="onSend" /> 
			<input type="button" style="background: #666666;"
								value="返回" onclick="closeMthodes();" />
		</div>
		<div align="center">${message}</div>
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
			<c:choose>
				<c:when test="${empty loanPersonDo.certificateDoList}">
				<tr align="center">
							<td style="width: 100px; height: 30px;"
								class="blue12" colspan="2">
								<div id="imgdiv1">
									<input type="file" id="file1" name="files" style="display: none;" onchange="" />
									<img src="../images/loan/ywsl.png" id="img1" onclick="file1.click()" style="width:80px;height:80px"/></br>
									《委托划款协议》
								</div>
							</td>
							<td class="blue12" colspan="2">
								<div id="imgdiv1">
									<input type="file" id="file2" name="files" style="display: none;"  />
									<img src="../images/loan/ywsl.png" id="img2" onclick="file2.click()" style="width:80px;height:80px"/></br>
									《借款咨询服务协议》
								</div>
							</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach items="${loanPersonDo.certificateDoList}" var="certificateDo">
							<c:set var="count" value="${count+1}" />
							<td align="left" class="f66">
							<div id="imgdiv${count }">
								<img src="${fileAccessUrl}${certificateDo.filePath }" alt="" width="120px;" />
								<input type="hidden" value="${certificateDo.certificateId}" name="loanPersonDo.certificateDoList[${count-1 }].certificateId" />
								<input type="hidden" value="${certificateDo.loanId}" name="loanPersonDo.certificateDoList[${count-1 }].loanId" />
								<input type="hidden" value="${certificateDo.loanPersonId}" name="loanPersonDo.certificateDoList[${count-1 }].loanPersonId" />
								<input type="hidden" value="${certificateDo.certificateType}" name="loanPersonDo.certificateDoList[${count-1 }].certificateType" />
								<input type="hidden" value="${certificateDo.fileType }" name="loanPersonDo.certificateDoList[${count-1 }].fileType"/>
								<input type="hidden" value="${certificateDo.certificateName}" name="loanPersonDo.certificateDoList[${count-1 }].certificateName" />
								<input type="hidden" id="file${count}"  value="${certificateDo.certificateType}"/>
								<input id="filePath${count }" name="loanPersonDo.certificateDoList[${count-1 }].filePath" value="${certificateDo.filePath}" type="hidden"/>
								<input type="file" name="files" id="fil${count}" style="display: none;"/>
								<input type="button"  onclick="fil${count}.click()" value="重新上传"/></br>
							</div>
							</td>
							</c:forEach>
					</c:otherwise>
				</c:choose>
		</table>
</form>
</body>
</html>
