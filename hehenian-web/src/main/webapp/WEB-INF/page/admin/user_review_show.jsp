<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>评论审核</title>
		<link href="../css/admin/admin_css.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script language="javascript" type="text/javascript"
			src="../My97DatePicker/WdatePicker.js"></script>
		<script language="javascript" type="text/javascript"
			src="../script/jbox/jquery.jBox-2.3.min.js"></script>

		<script type="text/javascript">
	//审核
	function updateUserReview() {
		var id = $("#id").val();
		var status = $("input[name='status']:checked").val();
		var dsc = $("#dsc").val();
		param["paramMap.status"] = status;
		param["paramMap.id"] = id;
		param["paramMap.dsc"] = dsc;
		var size = $("input[name='status']:checked").val();
		if (status == null) {
			alert("请选择审核按钮");
			return null;
		}
		$.shovePost("updateUserReview.do", param, function(data) {
			if (data == "1") {
				alert("提交成功")
				window.parent.closeMthod(); 
			}
			if (data == "2") {
				alert("提交失败");
				return;
			}
		});
	}
</script>

	</head>
	<body>
		<input type="hidden" id="id" name="map.id" value="${map.id}" />
		<div id="right"
			style="background-image: url; background-position: top; background-repeat: repeat-x;">
			<div style="padding: 15px 10px 0px 10px;">
				<div style="width: auto; background-color: #FFF; padding: 10px;">
					<table width="100%" border="0" cellspacing="1" cellpadding="3">
						<tr>
							<td style="width: 100px; height: 25px;" align="right"
								class="blue12">
								评论者：
							</td>
							<td align="left" class="f66">
								${map.userName}
							</td>
						</tr>
						<tr>
							<td style="width: 100px; height: 25px;" align="right"
								class="blue12">
								被评论者：
							</td>
							<td align="left" class="f66">
								${map.reviewName}
							</td>
						</tr>

						<tr>
							<td style="width: 100px; height: 25px;" align="right"
								class="blue12">
								评论内容：
							</td>
							<td align="left" class="f66">
								${map.msgContent}

							</td>
						</tr>
						<tr>
							<td style="width: 100px; height: 25px;" align="right"
								class="blue12">
							</td>
							<td align="left" class="f66">
								&nbsp;
							</td>
						</tr>
						<tr>
							<td style="width: 100px; height: 25px;" align="right"
								class="blue12">
							</td>
							<td align="left" class="f66">
								&nbsp;
							</td>
						</tr>
						<tr>
							<td style="width: 100px; height: 25px;" align="right"
								class="blue12">
								审核&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
							<td align="left" class="f66">
								&nbsp;
							</td>
						</tr>
						<tr>
							<td style="height: 25px;" align="right" class="blue12">
								<s:if test="#map.status==1">
									<input type="radio" id="status1" name="status" value="1"
										checked="checked"
										style="vertical-align: text-bottom; margin-bottom: -2px;" />通过
									</s:if>
								<s:else>
									<input type="radio" id="status1" name="status" value="1"
										style="vertical-align: text-bottom; margin-bottom: -2px;" />通过
									</s:else>
							</td>
							<td align="left" class="f66">
								<s:if test="#map.status==2">
									<input type="radio" id="status" name="status" value="2"
										checked="checked"
										style="vertical-align: text-bottom; margin-bottom: -2px;" />未通过
									</s:if>
								<s:else>
									<input type="radio" id="status" name="status" value="2"
										style="vertical-align: text-bottom; margin-bottom: -2px;" />未通过
									</s:else>
							</td>

						</tr>
						<tr>
							<td style="height: 25px;" align="right" class="blue12">
								添加备注：
							</td>
							<td align="left" class="f66">
								<textarea rows="3" cols="50" id="dsc" name="map.dsc">${map.dsc}</textarea>
							</td>
						</tr>
						<tr>
							<td height="36" align="right" class="blue12">
								&nbsp;
							</td>
							<td>
								<button id="btn_save" onclick="updateUserReview();"
									style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px"></button>
								&nbsp;
							</td>
						</tr>
					</table>
					<br />
				</div>
			</div>
		</div>
	</body>
</html>
