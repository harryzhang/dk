<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<link rel="stylesheet" href="../css/jbox/Gray/jbox.css" type="text/css"></link>
<script language="javascript" type="text/javascript"
	src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script language="javascript" type="text/javascript">
	function showMustImgNow(imgPath, typeId) {
		$.jBox("iframe:queryMaterialsauthImg.do?userId=" + $("#userId").val() + "&&typeId=" + typeId+"&&imgPath="+imgPath, {
			title : "详情",
			 width: 600,
			 height: 400,
			buttons : {
				'通过审核' : 1,
				'不通过' : 0
			},
			submit : function(v, h, f) {
				if (v == 0) {
					//不通过
					checkMustImgNow(typeId, 2);
				} else {
					//通过
					checkMustImgNow(typeId, 3);
				}
				return true;
			}
		});
	}
	function checkMustImgNow(typeId, status) {
		if (confirm("确认通过此项审核?")) {
			var param = {};
			param["paramMap.userId"] = $("#userId").val();
			param["paramMap.status"] = status;
			param["paramMap.typeId"] = typeId;
			$.post("updateCheckByType.do", param, function(data) {
				if (data == "1") {
					alert("审核成功");
					window.location.reload();
				} else
					alert("审核失败");
			});
		}
	}
</script>
<style>
.table1 tr td {
	font-size: 12px;
}

.td_01 {
	border-right: 0px;
}

.td_02 {
	border-left: 0px;
}
</style>
<!-- 必填认证信息div -->
<div style="width: 100%;">
	<div style="width: 100%;float: left">
		<table border="1" cellspacing="0" cellpadding="0" width="100%"
			class="table1">
			<tr height="36px">
				<td align="center" width="40%">姓名</td>
				<td align="center" colspan="2">${request.realName }</td>
			</tr>
			<s:iterator value="#request.map" var="bean">
				<tr height="36px">
					<td align="center">${bean.name}</td>

					<td align="center" class="td_01"><s:if
							test="#bean.auditStatus==null">
							未上传
						</s:if> <s:else>
							已上传
						</s:else></td>
					<td class="td_02"><a
						href="javascript:showMustImgNow('${bean.imgPath}','${bean.materAuthTypeId}')">
							<s:if test="#bean.auditStatus==1">待审核</s:if> <s:if
								test="#bean.auditStatus==2">审核不通过</s:if> <s:if
								test="#bean.auditStatus==3">已审核</s:if> </a></td>
				</tr>
			</s:iterator>
		</table>
	</div>
</div>