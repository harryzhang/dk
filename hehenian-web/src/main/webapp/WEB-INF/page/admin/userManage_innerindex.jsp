<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>借款产品参数</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<link href="../css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../common/dialog/popup.js"></script>
<script>
	$(function() {
		if ($("#img").val() == '') {
			$("#img").attr("src", "../images/NoImg.GIF");
		}
	});

	$("#li_work").click(function() {
		//alert("3435435");
		//window.location.href = 'queryUserMangework.do?uid=${map.userId}';
	});

	function closeJbox() {
		window.parent.closeMthod(0);
	}

	function btn_update_info() {
		var radios = document.getElementsByName("sex");
		var values = "";
		for ( var i = 0; i < radios.length; i++) {
			if (radios[i].checked == true) {
				values = radios[i].value;
			}
		}
		var param = {};
		param["paramMap.id"] = '${UserMsgmap.id}';
		param["paramMap.realName"] = $("#realName").val();
		param["paramMap.highestEdu"] = $("#highestEdu").val();
		param["paramMap.idNo"] = $("#idNo").val();
		param["paramMap.address"] = $("#address").val();
		param["paramMap.sex"] = values;
		param["paramMap.birthday"] = $("#birthday").val();
		param["paramMap.cellPhone"] = $("#cellPhone").val();

		param["paramMap.work_id"] = $("#work_id").val();
		param["paramMap.job"] = $("#job").val();
		param["paramMap.monthlyIncome"] = $("#monthlyIncome").val();
		param["paramMap.orgName"] = $("#orgName").val();
		param["paramMap.companyTel"] = $("#companyTel").val();
		param["paramMap.companyType"] = $("#companyType").val();
		param["paramMap.workEmail"] = $("#workEmail").val();
		param["paramMap.companyScale"] = $("#companyScale").val();
		param["paramMap.companyAddress"] = $("#companyAddress").val();
		param["paramMap.workYear"] = $("#workYear").val();

		param["paramMap.usrCustId"] = $("#usrCustId").val();
		$.post("userManageUpd.do", param, function(data) {
			if (data == 0) {
				alert("修改失败");
				window.parent.closeMthod(0);
			} else if (data == 1) {
				alert("修改成功");
				window.parent.closeMthod(1);
			}

		});
	}
</script>
<style type="text/css">
#id_a {
	font-size: 16px;
}

#id_a ul li {
	float: left;
	width: 100px;
	margin-left: 50px;
	cursor: pointer;
}

table tr td {
	width: 100px;
	border: 1px solid #666666;
	padding: 0px 4px;
}

#table_3 tr td {
	width: 100px;
	line-height: 1.8;
}

.userManage_left {
	padding-left: 30px;
}

.userManageMoney_table_02 td {
	width: 60px;
	border: 1px solid #666666;
}

.userManage_info_k {
	width: 1px;
	border-top: 0px;
	border-bottom: 0px;
	padding: 8px 10px;
}

#companyType {
	width: 150px;
}

.userManage_inner_kong td {
	border-left: 0px;
	border-right: 0px;
	height: 10px;
}
</style>
</head>
<body>
	<form id="form1" action="userManageUpd.do" method="post">
		<table style="margin-left:65px;margin-top: 10px;margin-bottom: 10px;">
			<tr style="height:30px">
				<td colspan="4" style="text-align: center;">个人信息</td>
			</tr>
			<tr>
				<td>用户名：</td>
				<td><input type="text" name="paramMap.username" readonly="readonly" style="border:1px solid #D9D9D9;" value="${ UserMsgmap.username}" /></td>
				<td>会员编号：</td>
				<td>${ UserMsgmap.id}<input type="hidden" name="paramMap.id" value="${ UserMsgmap.id}" /></td>
			</tr>
			<tr>
				<td>真实姓名：</td>
				<td><input type="text" style="border:1px solid #D9D9D9;" id="realName" name="paramMap.realName" value="${UserMsgmap.realName}" />
				</td>
				<td>最高学历：</td>
				<td><select name="UserMsgmap.highestEdu" id="highestEdu">
						<option value="-1">请选择</option>
						<option value="高中或以下" <s:if test='#request.map.highestEdu == "高中或以下"'>selected="selected"</s:if> <s:else></s:else>>高中或以下</option>
						<option value="大专" <s:if test='#request.map.highestEdu == "大专"'>selected="selected"</s:if> <s:else></s:else>>大专</option>
						<option value="本科" <s:if test='#request.map.highestEdu == "本科"'>selected="selected"</s:if> <s:else></s:else>>本科</option>
						<option value="研究生或以上" <s:if test='#request.map.highestEdu == "研究生或以上"'>selected="selected"</s:if> <s:else></s:else>>研究生或以上</option>
						<option value="其他" <s:if test='#request.map.highestEdu == "其他"'>selected="selected"</s:if> <s:else></s:else>>其他</option>
				</select>
				</td>
			</tr>
			<tr>
				<td>身份证号：</td>
				<td><input type="text" style="border:1px solid #D9D9D9;" name="paramMap.idNo" id="idNo" value="${map.idNo }" /></td>
				<td>居住地址：</td>
				<td><input type="text" style="border:1px solid #D9D9D9;" name="paramMap.address" id="address" value="${ map.address}" /></td>
			</tr>
			<tr>
				<td>性别：<input type="hidden" name="paramMap.sex" id="sexs" /></td>
				<td><input type="radio" name="sex" id="sex" value="男" <s:if test='#request.map.sex == "男"'>checked="checked"</s:if> <s:else></s:else> /> 男 <input type="radio" name="sex" id="sex"
					value="女" <s:if test='#request.map.sex == "女"'>checked="checked"</s:if> <s:else></s:else> /> 女</td>
				<td>邮箱：</td>
				<td><input type="text" style="border:1px solid #D9D9D9;" readonly="readonly" name="paramMap.email" value="${ UserMsgmap.email}" /></td>
			</tr>
			<tr>
				<td>出生日期</td>
				<td><input type="text" style="border:1px solid #D9D9D9;" name="paramMap.birthday" id="birthday" onclick="selectStartDate();" value="${birth }" /></td>
				<td>手机号码：</td>
				<td><input type="text" style="border:1px solid #D9D9D9;" name="paramMap.cellPhone" id="cellPhone" value="${map.cellPhone }" /> <input type="hidden" id="usrCustId"
					name="paramMap.usrCustId" value="${UserMsgmap.usrCustId}" />
				</td>
			</tr>
			<tr class="userManage_inner_kong">
				<td colspan="4"></td>
			</tr>
			<tr style="height:30px">
				<td colspan="4" style="text-align: center;">工作信息</td>
			</tr>
			<tr>
				<td>职业：<input type="hidden" id="work_id" value="${map_work.id }" /></td>
				<td><input type="text"  name="paramMap.job" id="job" value="${map_work.job }" /></td>
				<td>月收入：</td>
				<td><select name="paramMap.monthlyIncome" id="monthlyIncome">
						<option value="" <s:if test='#request.map_work.monthlyIncome == ""'>selected="selected"</s:if> <s:else></s:else>>请选择</option>
						<option value="1000以下" <s:if test='#request.map_work.monthlyIncome == "1000以下"'>selected="selected"</s:if> <s:else></s:else>>1000以下</option>
						<option value="1000-2000" <s:if test='#request.map_work.monthlyIncome == "1000-2000"'>selected="selected"</s:if> <s:else></s:else>>1000-2000</option>
						<option value="2000-5000" <s:if test='#request.map_work.monthlyIncome == "2000-5000"'>selected="selected"</s:if> <s:else></s:else>>2000-5000</option>
						<option value="5000-10000" <s:if test='#request.map_work.monthlyIncome == "5000-10000"'>selected="selected"</s:if> <s:else></s:else>>5000-10000</option>
						<option value="10000-20000" <s:if test='#request.map_work.monthlyIncome == "10000-20000"'>selected="selected"</s:if> <s:else></s:else>>10000-20000</option>
						<option value="20000以上" <s:if test='#request.map_work.monthlyIncome == "20000以上"'>selected="selected"</s:if> <s:else></s:else>>10000-20000</option>
				</select></td>
			</tr>
			<tr>
				<td>单位名称：</td>
				<td><input type="text" name="paramMap.orgName" id="orgName"  value="${map_work.orgName}" /></td>
				<td>单位电话：</td>
				<td><input type="text" style="border:1px solid #D9D9D9;" name="paramMap.companyTel" id="companyTel" value="${map_work.companyTel }" /></td>
			</tr>
			<tr>
				<td>公司类别：</td>
				<td><select id="companyType" name="UserMsgmap.companyType">
						<option value="" <s:if test='#request.map_work.companyType == ""'>selected="selected"</s:if> <s:else></s:else>>请选择</option>
						<option value="事业单位" <s:if test='#request.map_work.companyType == "事业单位"'>selected="selected"</s:if> <s:else></s:else>>事业单位</option>
						<option value="国家单位" <s:if test='#request.map_work.companyType == "国家单位"'>selected="selected"</s:if> <s:else></s:else>>国家单位</option>
						<option value="央企(包括下级单位)" <s:if test='#request.map_work.companyType == "央企(包括下级单位)"'>selected="selected"</s:if> <s:else></s:else>>央企(包括下级单位)</option>
						<option value="地方国资委直属企业" <s:if test='#request.map_work.companyType == "地方国资委直属企业"'>selected="selected"</s:if> <s:else></s:else>>地方国资委直属企业</option>
						<option value="世界500强(包括合资企业及下级单位)" <s:if test='#request.map_work.companyType == "世界500强(包括合资企业及下级单位)"'>selected="selected"</s:if> <s:else></s:else>>世界500强(包括合资企业及下级单位)</option>
						<option value="外资企业(包括合资企业)" <s:if test='#request.map_work.companyType == "外资企业(包括合资企业)"'>selected="selected"</s:if> <s:else></s:else>>外资企业(包括合资企业)</option>
						<option value="一般上市公司(包括国外上市公司)" <s:if test='#request.map_work.companyType == "一般上市公司(包括国外上市公司)"'>selected="selected"</s:if> <s:else></s:else>>一般上市公司(包括国外上市公司)</option>
						<option value="一般民营企业" <s:if test='#request.map_work.companyType == "一般民营企业"'>selected="selected"</s:if> <s:else></s:else>>一般民营企业</option>
				</select>
				</td>
				<td>单位邮箱：</td>
				<td><input type="text"  name="paramMap.workEmail" id="workEmail" value="${map_work.workEmail }" /></td>
			</tr>
			<tr>
				<td>公司规模：</td>
				<td><select name="paramMap.companyScale" id="companyScale">
						<option value="50人以下" <s:if test='#request.map_work.companyScale == "50人以下"'>selected="selected"</s:if> <s:else></s:else>>50人以下</option>
						<option value="50-100人" <s:if test='#request.map_work.companyScale == "50-100人"'>selected="selected"</s:if> <s:else></s:else>>50-100人</option>
						<option value="100-500人" <s:if test='#request.map_work.companyScale == "100-500人"'>selected="selected"</s:if> <s:else></s:else>>100-500人</option>
						<option value="500人以上" <s:if test='#request.map_work.companyScale == "500人以上"'>selected="selected"</s:if> <s:else></s:else>>500人以上</option>
				</select>
				</td>
				<td>单位地址：</td>
				<td><input type="text" name="paramMap.companyAddress" id="companyAddress" value="${map_work.companyAddress }" /></td>
			</tr>
			<tr>
				<td>现单位工作年限：</td>
				<td colspan="3"><select name="paramMap.workYear" id="workYear">
						<option value="" <s:if test='#request.map_work.workYear == ""'>selected="selected"</s:if> <s:else></s:else>>请选择</option>
						<option value="1年" <s:if test='#request.map_work.workYear == "1年"'>selected="selected"</s:if> <s:else></s:else>>1年</option>
						<option value="1-3年" <s:if test='#request.map_work.workYear == "1-3年"'>selected="selected"</s:if> <s:else></s:else>>1-3年</option>
						<option value="3-5年" <s:if test='#request.map_work.workYear == "3-5年"'>selected="selected"</s:if> <s:else></s:else>>3-5年</option>
						<option value="5年以上" <s:if test='#request.map_work.workYear == "5年以上"'>selected="selected"</s:if> <s:else></s:else>>5年以上</option>
				</select></td>
			</tr>
			<tr class="userManage_inner_kong">
				<td colspan="4"></td>
			</tr>
			<tr style="height:30px">
				<td colspan="4" style="text-align: center;">财务信息</td>
			</tr>
			<s:iterator value="pageBean.page" var="bean">
				<tr>
					<td align="center" height="30" width="150px;">${bean.name}</td>
					<td align="center" colspan="3"><s:if test="#bean.auditStatus==1">
							<a style="color:gray; ">待审核</a>
						</s:if> <s:elseif test="#bean.auditStatus==2">
							<a style="color:red ">审核不通过</a>
						</s:elseif> <s:elseif test="#bean.auditStatus==3">
							<a style="color:green ">审核通过</a>
						</s:elseif> <s:else>
							<a style="color:blue; ">未上传</a>
						</s:else>
					</td>
				</tr>
			</s:iterator>
			<tr>
				<td colspan="2"><input type="button" style="margin-left:150px;background:#666666;" value="取消" onclick="closeJbox();" /></td>
				<td colspan="2"><input onclick="btn_update_info();" type="button" style="background:#666666;" value="修改" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
