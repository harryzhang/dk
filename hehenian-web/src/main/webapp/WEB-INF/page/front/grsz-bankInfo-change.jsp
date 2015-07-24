<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/include/head.jsp"></jsp:include>
<link href="css/inside.css" rel="stylesheet" type="text/css" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<!--<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script> 
-->
<script type="text/javascript">
	$(function() {
		//$("#bankName_").focus();
		//提现银行卡设置1
		$("#bankName_").blur(function() {
			if ($("#bankName_").val() == "") {
				$("#bk1_tip_").html("开户银行名称不能为空");
			} else {
				$("#bk1_tip_").html("");
			}
		});

		$("#subBankName_").blur(function() {
			if ($("#subBankName_").val() == "") {
				$("#bk1_tip_").html("开户支行不能为空");
			} else {
				$("#bk1_tip_").html("");
			}
		});

		$("#bankCard_").blur(function() {
			if ($("#bankCard_").val() == "") {
				$("#bk1_tip_").html("卡号不能为空");
			} else if (isNaN($("#bankCard_").val()) || $("#bankCard_").val().length < 12 || $("#bankCard_").val().length > 19) {
				$("#bk1_tip_").html("请输入正确的银行卡号");
			}else if($("#bankCardes").val() == $("#bankCard_").val())
			{
				$("#bk1_tip_").html("新卡不能与原卡一样");
			}
			 else {
				$("#bk1_tip_").html("");
			}
		});

	});

	function changeBankInfo() {
		if ($("#bankName_").val() == "" || $("#subBankName_").val() == "" || $("#bankCard_").val() == "") {
			$("#bk1_tip_").html("请填写完整信息");
			return;
		}

		if ($("#bk1_tip_").text() != "") {
			return;
		}
		if (window.confirm("确定要申请变更吗?")) {
			param["paramMap.bankId"] = $("#bankId").val();
			param["paramMap.mBankCard"] = $("#bankCard_").val();
			var bankName_Id = $("#bankName_Id").val();
			param["paramMap.mBankName"] = bankName_Id.substring(0, bankName_Id.indexOf(" "));//变更后银行名称
			param["paramMap.modifiedOpenBankId"] = bankName_Id.substring(bankName_Id.indexOf(" ")+1); //变更后银行代号
			param["paramMap.province"] = $("#province option:selected").text();
			param["paramMap.city"] =  $("#registedPlaceCity option:selected").text();
			param["paramMap.provinceId"] = $("#province").val();
			param["paramMap.cityId"] = $("#registedPlaceCity").val()
			param["paramMap.mSubBankName"] = $("#subBankName_").val(); //变更后支行
			$.shovePost("updateBankInfo.do", param, function(data) {
				if (data == 1) {
					alert("变更申请失败，请重新变更")
					return;
				}
				alert("变更申请成功");
				window.location.href = "bankInfoSetInit.do";
			});
			return;
		}
		param["paramMap.bankId"] = $
		{
			bankId
		}
		;
		
	}
	
	function registedPlaceCity(parentId, regionType) {
		var _array = [];
		_array.push("<option value='-1'>--请选择--</option>");
		var param = {};
		param["regionType"] = regionType;
		param["parentId"] = parentId;
		$.post("ajaxqueryRegionsHHN.do", param, function(data) {
			for ( var i = 0; i < data.length; i++) {
				_array.push("<option value='"+data[i].regionId+"'>" + data[i].regionName + "</option>");
			}
			$("#registedPlaceCity").html(_array.join(""));
		});
	}

	$(document).ready(function() {
		$("#province").change(function() {
			var provinceId = $("#province").val();
			registedPlaceCity(provinceId, 2);
		});
	});
</script>
</head>

<body>

	<div class="nymain" style="width: 620px;">
		<div class="wdzh" style="width: 600px;">
			<div class="r_main" style="width: 580px;">
				<div class="box" style="width: 600px;">
					<div class="boxmain2" style="padding-top:10px; width: 570px;">
						<div class="biaoge2" style="margin-top:0px; width: 600px;">
							<table width="560px;" border="0" cellspacing="0" cellpadding="0" style="margin-left: 40px;">
								<tr>
									<th colspan="2" align="left" style="padding-top:0px;">变更银行卡信息</th>
								</tr>
								<tr>
									<td width="18%" align="right">真实姓名：</td>
									<td width="83%">${realName }<span class="txt"></span>
									</td>
								</tr>
								<tr>
									<td width="18%" align="right">原银行卡号：</td>
									<td width="83%">${bankCard }<span class="txt"><input type="hidden" id="bankCardes" value="${bankCard }" /></span>
									<input type="hidden" id="bankId" value="${bankId }" />
									</td>
								</tr>
								<tr>
									<td align="right">新的卡号：</td>
									<td><input type="text" class="inp188" id="bankCard_" /> <span class="txt">输入您的卡号</span>
									</td>
								</tr>
								<tr>
									<td align="right">新的开户行：</td>
									<td>
										<select id="bankName_Id" style="height: 26px; padding-top:2px; width: 160px; " >
										<option value="工商银行 ICBC">工商银行</option>
										<option value="农业银行 ABC">农业银行</option>
										<option value="招商银行 CMB">招商银行</option>
										<option value="建设银行 CCB">建设银行</option>
										<option value="北京银行 BCCB">北京银行</option>
										<option value="北京农村商业银行 BJRCB">北京农村商业银行</option>
										<option value="中国银行 BOC">中国银行</option>
										<option value="交通银行 BOCOM">交通银行</option>
										<option value="民生银行 CMBC">民生银行</option>
										<option value="上海银行 BOS">上海银行</option>
										<option value="渤海银行 CBHB">渤海银行</option>
										<option value="光大银行 CEB">光大银行</option>
										<option value="兴业银行 CIB">兴业银行</option>
										<option value="中信银行 CITIC">中信银行</option>
										<option value="浙商银行 CZB">浙商银行</option>
										<option value="广发银行 GDB">广发银行</option>
										<option value="东亚银行 HKBEA">东亚银行</option>
										<option value="华夏银行 HXB">华夏银行</option>
										<option value="杭州银行 HZCB">杭州银行</option>
										<option value="南京银行 NJCB">南京银行</option>
										<option value="平安银行 PINGAN">平安银行</option>
										<option value="邮储银行 PSBC">邮储银行</option>
										<option value="深发银行 SDB">深发银行</option>
										<option value="浦发银行 SPDB">浦发银行</option>
										<option value="上海农村商业银行 SRCB">上海农村商业银行</option>
									</select>
									<span class="txt">输入您的开户银行名称</span>
									</td>
								</tr>
								<tr>
									<td align="right">省市地区：</td>
									<td><s:select id="province" name="paramMap.province" value="paramMap.province" cssStyle="width:86px; padding-top:2px; height:26px;" list="#request.provinceList" listKey="regionId" listValue="regionName"
												headerKey="-1" headerValue="请选择" onchange="javascript:if($('#registedPlacePro').val()!=-1){$('#u_reg_Pro_City').html('')}" />-
												<s:select cssClass="nd_xgmm_one_a_in" id="registedPlaceCity" name="regCity"
												value="paramMap.regCity" cssStyle="width:96px;height:26px;padding-top:2px;" list="#request.cityList" listKey="regionId" listValue="regionName" headerKey="-1" headerValue="请选择"
												onchange="javascript:if($('#registedPlaceCity').val()!=-1){$('#u_reg_Pro_City').html('')}" />
								</td>
								</tr>
								<tr>
									<td align="right">新的开户支行：</td>
									<td><input type="text" class="inp188" id="subBankName_" /> <span class="txt">输入您的开户支行名称</span>
									</td>
								</tr>
								<tr>
									<td align="right">&nbsp;</td>
									<td style="padding-top:10px;"><a href="javascript:changeBankInfo();" class="bcbtn">变更</a> <a href="javascript:window.history.back();" class="bcbtn">返回</a>
									</td>
								</tr>
								<tr>
									<td colspan="2"><span style="color: red; float: none;" id="bk1_tip_" class="formtips"></span>
									</td>
								</tr>
							</table>

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


</body>
</html>
