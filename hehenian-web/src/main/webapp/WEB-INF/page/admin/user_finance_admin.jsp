<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<link rel="stylesheet" href="../css/jbox/Gray/jbox.css" type="text/css"></link>
<script language="javascript" type="text/javascript"
	src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script language="javascript" type="text/javascript">
	function showCheckImg(typeId) {
		$.jBox("iframe:queryMaterialsauthImg.do?userId=" + $("#userId").val() + "&&typeId=" + typeId, {
			title : "图片详情",
			width : 600,
			height : 400,
			buttons : {
				'关闭' : true
			}
		});
	}
	function checkImgNow(status, typeId) {
		if (confirm("确认通过此项审核?")) {
			if (status == '') {
				if (confirm("用户未上传图片,仍然通过该审核?"))
					checkSelectInfo(typeId);
			} else
				checkSelectInfo(typeId);
		}
	}

	function checkSelectInfo(typeId) {
		var param = {};
		param["paramMap.userId"] = $("#userId").val();
		param["paramMap.status"] = 3;
		param["paramMap.typeId"] = typeId;
		$.post("updateCheckByType.do", param, function(data) {
			if (data == "1") {
				alert("审核成功");
				window.location.reload();
			} else
				alert("审核失败");
		});
	}
</script>
<style>
.table_01 tr td {
	padding: 4px;
}

.table_02 {
	margin-left: -1px;
	margin-bottom: 10px;
}
</style>

<!-- 财力信息div -->
<div style="width: 100%;">
	<div style="width: 50%;float: left">
		<table border="1" cellspacing="0" cellpadding="0" width="100%"
			style=" font-size:12px;" class="table_01">
			<s:iterator value="#request.map1" var="bean">
				<tr>
					<td align="center">${bean.name}</td>
					<td align="center"><s:if test="#bean.auditStatus==1">
							<a style="color:gray; ">&nbsp;待审核</a>&nbsp;
						</s:if> <s:elseif test="#bean.auditStatus==2">
							<a style="color:red ">审核不通过</a>
						</s:elseif> <s:elseif test="#bean.auditStatus==3">
							<a style="color:green ">&nbsp;审核通过</a>
						</s:elseif> <s:else>
							<a style="color:blue; ">&nbsp;未上传</a>&nbsp;
						</s:else> &nbsp;&nbsp; 
						<a
						href="javascript:showCheckImg('${bean.materAuthTypeId}')">查看</a>
						&nbsp;&nbsp; <a
						href="javascript:checkImgNow('${bean.auditStatus}','${bean.materAuthTypeId}')">审核</a>
					</td>
				</tr>
			</s:iterator>
		</table>
	</div>
	<div style="width: 50%;float: left">
		<table border="1" cellspacing="0" cellpadding="0" width="100%"
			style="font-size:12px;" class="table_01 table_02">
			<s:iterator value="#request.map2" var="bean">
				<tr>
					<td align="center">${bean.name}</td>
					<td align="center"><s:if test="#bean.auditStatus==1">
							<a style="color:gray; ">&nbsp;待审核&nbsp;</a>
						</s:if> <s:elseif test="#bean.auditStatus==2">
							<a style="color:red ">审核不通过</a>
						</s:elseif> <s:elseif test="#bean.auditStatus==3">
							<a style="color:green ">&nbsp;审核通过</a>
						</s:elseif> <s:else>
							<a style="color:blue; ">&nbsp;未上传&nbsp;</a>
						</s:else> &nbsp;&nbsp; 
						<a
						href="javascript:showCheckImg('${bean.materAuthTypeId}')">查看</a>
						&nbsp;&nbsp; <a
						href="javascript:checkImgNow('${bean.auditStatus}','${bean.materAuthTypeId}')">审核</a>
					</td>
				</tr>
			</s:iterator>
		</table>
	</div>
</div>