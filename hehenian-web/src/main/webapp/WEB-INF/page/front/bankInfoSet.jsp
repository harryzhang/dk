<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.sp2p.database.Dao.Tables.t_bankcard"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/user.css" rel="stylesheet" type="text/css" />
<jsp:include page="/include/head.jsp"></jsp:include>
<script type="text/javascript">
	$(function() {
		$(".wdzh_next_left li").eq(3).addClass("wdzh_next_left_ong").find("a").attr("style","color: rgb(233, 71, 24);");
		
		$(".header_two_right_ul li").hover(function() {
			$(this).find("ul").show();
		}, function() {
			$(this).find("ul").hide();
		});

		$(".wdzh_top_ul li").click(function() {
			var ppain = $(".wdzh_top_ul li").index(this);
			$(".wdzh_top_ul li").removeClass("wdzhxxk");
			$(this).addClass("wdzhxxk");
<%--			$(".wdzh_next").hide();--%>
<%--			$(".wdzh_next").eq(ppain).show();--%>
		});
		$(".tzjl_fwxy").click(function() {
			$(".tzjl_fwxyh").show();
		});
		$(".tzjl_fwxy1").click(function() {
			$(".tzjl_fwxy1h").show();
		});
	});

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
		if($("#usrCustId").val()=='' || $("#usrCustId").val() == '-1')
		{
			alert("请先注册汇付账户!");
			window.location.href="registerChinaHHN.do";
			return false;
		}
		$("#province").change(function() {
			var provinceId = $("#province").val();
			registedPlaceCity(provinceId, 2);
		});
	});
	
	function validateBankInfo() {
		if ($("#bankName_Id").val() == "") {
			$("#bankName_Id").html("开户银行名称不能为空");
			return false;
		}else {
			$("#bankName").html("");
		}

		if ($("#province option:selected").text() == "请选择" || $("#province option:selected").text() =='') {
			$("#s_province").html("请选择省份");
			return false;
		}
		else {
			$("#s_province").html("");
		}
		if ($("#registedPlaceCity option:selected").text() == "请选择" || $("#registedPlaceCity option:selected").text() == "--请选择--" || $("#registedPlaceCity option:selected").text() =='') {
			$("#s_city").html("请选择地区");
			return false;
		}else {
			$("#s_city").html("");
		}

		if ($("#branchBankName").val() == "") {
			$("#branchBankName").html("开户支行不能为空");
			return false;
		}else {
			$("#s_branchBankName").html("");
		}
		
		if ($("#bankCard_").val() == "") {
			$("#bankCard").html("卡号不能为空");
			return false;
		} else if (isNaN($("#bankCard_").val()) || $("#bankCard_").val().length < 16 || $("#bankCard_").val().length > 19) {
			$("#bankCard").html("请输入正确的银行卡号");
			return false;
		} else {
			$("#bankCard").html("");
		}
		
		return true;
	}

	function insertBankInfo() {
		var usrCustId = ${usrCustId};
		if(usrCustId <= 0){
			alert("您还不是汇付天下会员!");
			return;
		}
		$("#f_insertBankInfo").submit();
		
		/*$.post("bindCardInit.do", param, function(data) {
			if(data.length<20){
				alert(data);
				return;
			}
			$("#huifuForm").html(data);
		});*/
		
		/*if (!validateBankInfo()) {
			return;
		}
		param["paramMap.cardUserName"] = "${realName}";
	//	param["paramMap.bankName"] = $("#bankName_").val();
		var bankName_Id = $("#bankName_Id").val();
		param["paramMap.bankName"] = bankName_Id.substring(0, bankName_Id.indexOf(" "));
		param["paramMap.openBankId"] = bankName_Id.substring(bankName_Id.indexOf(" ")+1);
		param["paramMap.subBankName"] = $("#subBankName_").val();
		param["paramMap.bankCard"] = $("#bankCard_").val();
		param["paramMap.city"] = $("#registedPlaceCity option:selected").text();
		param["paramMap.province"] = $("#province option:selected").text();
		param["paramMap.subBankName"] = $("#branchBankName").val();
		param["paramMap.provinceId"] = $("#province").val();
		param["paramMap.cityId"] = $("#registedPlaceCity").val();
		//$('#insertBankInfo').attr("disabled",true);
		$.post("addBankInfo2.do", param, function(data) {
			if(data == 1)
			{
				alert("银行卡已经存在,请重新输入!");
				return false;
			}
			if (data == 2) {
				alert("只能绑定五张银行卡");
				$("input[type='text']").val("");
				return;
			}
			if (data == "操作成功") {
				alert("操作成功");
				window.location.href = "bankInfoSetInit.do";
				return;
			}  
			if (data == "请求参数非法") {
				alert(data + ":请检查身份证号码和银行卡号码");
			} else {
				alert(data);
			}
			$("input[type='text']").val("");
			window.location.reload();
		});*/
	}
</script>
</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>
<div class="user-all">
  <div class="user-center">
    <div style=" width:1170px; margin:0px auto; padding-bottom:24px;"><img src="/images/v1/detail_logo_bg.jpg" width="1170" height="6"  alt=""/></div>
    <div style=" overflow:hidden">
      <div class="u-left">
        <jsp:include page="/include/menu_userManage.jsp"></jsp:include>
      </div>
      <div class="u-right">
        <div class="user-right">
          <h2>银行卡管理</h2>
          <div class="nd_xgmm_one" style="margin: 12px auto; float: none">
            <div class="nd_xgmm_one_a">提现银行</div>
            <table width="340" border="0" style="margin-bottom: 20px;">
              <%--							<tr height="20"></tr>--%>
              <%--							<tr>--%>
              <%--								<td align="right" width="80">户名：</td>--%>
              <%--								<td><strong>${realName }</strong>--%>
              <%--								</td>--%>
              <%--							</tr>--%>
              <tr height="5"></tr>
              <%--<tr>
								<td align="right" width="80">开户行：<input type="hidden" id="usrCustId" value="${usrCustId}"/></td>
						<!-- 	<td><input type="text" class="nd_xgmm_one_a_in" id="bankName_" /></td>   -->
								<td style="padding-left: 10px;">
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
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td><span id="bankName">选择开户行的名称</span>
								</td>
							</tr>
							<tr height="5"></tr>
							<tr>
								<td align="right" width="80">省市地区：</td>
								<td><s:select cssClass="nd_xgmm_one_a_in" id="province" name="paramMap.province" value="paramMap.province" cssStyle="width:86px; padding-top:2px; height:26px;" list="#request.provinceList" listKey="regionId" listValue="regionName"
												headerKey="-1" headerValue="请选择" onchange="javascript:if($('#registedPlacePro').val()!=-1){$('#u_reg_Pro_City').html('')}" />-
												<s:select cssClass="nd_xgmm_one_a_in" id="registedPlaceCity" name="regCity"
												value="paramMap.regCity" cssStyle="width:96px;height:26px;padding-top:2px;" list="#request.cityList" listKey="regionId" listValue="regionName" headerKey="-1" headerValue="请选择"
												onchange="javascript:if($('#registedPlaceCity').val()!=-1){$('#u_reg_Pro_City').html('')}" />
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td><span id="s_province"></span><span id="s_city"></span>
								</td>
							</tr>
							<tr height="5"></tr>
							<tr>
								<td align="right" width="80">支行：</td>
								<td><input type="text" class="nd_xgmm_one_a_in" id="branchBankName" />
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td><span id="s_branchBankName">输入开户支行的名称</span>
								</td>
							</tr>
							<tr height="5"></tr>
							<tr>
								<td align="right" width="80">卡号：</td>
								<td><input type="text" class="nd_xgmm_one_a_in" id="bankCard_" />
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td><span id="bankCard">输入您的卡号</span>
								</td>
							</tr>
							--%>
              <tr height="5"></tr>
              <tr>
                <td height="50" align="center"><form id="f_insertBankInfo" target="_blank" action="bindCardInit.do" method="post" >
                  </form>
                  <input type="button" id="insertBankInfo" value="添加" class="nd_xgmm_one_a_but" onclick="insertBankInfo()" /></td>
              </tr>
              <tr height="5"></tr>
            </table>
          </div>
          <%--
					<div class="nd_xgmm_one" style="display: none;">
						<div class="nd_xgmm_one_a" style="display: none;">提现银行</div>
						<table width="340" border="0" style="display: none;">
							<tr height="20"></tr>
							<tr>
								<td align="right" width="80">用户名：</td>
								<td><strong>${realName}</strong>
								</td>
							</tr>
							<tr height="10"></tr>
							<tr>
								<td align="right" width="80">开户行：</td>
								<td><input type="text" class="nd_xgmm_one_a_in" id="bankName_" />
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td><span id="bankName">输入开户行的名称</span>
								</td>
							</tr>
							<tr height="10"></tr>
							<tr>
								<td align="right" width="80">支行：</td>
								<td><input type="text" class="nd_xgmm_one_a_in" id="subBankName_" />
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td><span id="subBankName">输入开户支行的名称</span>
								</td>
							</tr>
							<tr height="10"></tr>
							<tr>
								<td align="right" width="80">卡号：</td>
								<td><input type="text" class="nd_xgmm_one_a_in" id="bankCard_" />
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td><span id="bankCard">输入您的卡号</span>
								</td>
							</tr>
							<tr height="10"></tr>
							<tr>
								<td>&nbsp;</td>
								<td><input type="button" value="添加" class="nd_xgmm_one_a_but" onclick="insertBankInfo1()" />
								</td>
							</tr>
							<tr height="10"></tr>
						</table>
					</div>
					--%>
          <div class="cle"></div>
          <table id="rounded-corner" summary="2007 Major IT Companies' Profit" style=" width:100%">
            <thead>
              <tr>
                <th scope="col" class="rounded-company">户名</th>
                <th scope="col" class="rounded">开户行</th>
                <th scope="col" class="rounded">卡号</th>
                <%--<th scope="col" class="rounded">更换时间</th>--%>
                <th scope="col" class="rounded-q4">状态</th>
              </tr>
            </thead>
            <tfoot>
              <s:if test="#request.lists==null || #request.lists.size==0">
                <tr align="center" class="gvItem" height="30">
                  <td colspan="6">暂无数据</td>
                </tr>
              </s:if>
            </tfoot>
            <tbody>
              <s:else>
                <s:iterator value="#request.lists" var="bean" status="s">
                  <tr height="25">
                    <td height="50" align="center">${bean.cardUserName}</td>
                    <td height="50" align="center">${bean.bankName }</td>
                    <td height="50" align="center"><s:if test="#bean.cardNo == null || #bean.cardNo ==''"> ${bean.modifiedCardNo}</s:if>
                      <s:else>${bean.cardNo}</s:else></td>
                    <%--<td height="50" align="center"><s:date name="commitTime" format="yyyy-MM-dd HH:mm:ss"/></td>--%>
                    <td height="50" align="center"><s:if test="#bean.modifiedTime > #bean.commitTime">
                        <s:if test="(#bean.modifiedCardStatus !=  '' && #bean.modifiedCardStatus==1)" >已绑定</s:if>
                        <s:elseif test="(#bean.modifiedCardStatus != '' && #bean.modifiedCardStatus ==2 )">变更申请中</s:elseif>
                        <s:elseif  test="(#bean.modifiedCardStatus != '' && #bean.modifiedCardStatus ==3 )">失败</s:elseif>
                      </s:if>
                      <s:elseif test="#bean.commitTime > #bean.modifiedTime">
                        <s:if test="(#bean.cardStatus !=  '' && #bean.cardStatus==1)" >已绑定</s:if>
                        <s:elseif test="(#bean.cardStatus !=  '' && #bean.cardStatus==2)">申请中</s:elseif>
                        <s:elseif  test="(#bean.cardStatus !=  '' && #bean.cardStatus==3)">失败</s:elseif>
                      </s:elseif>
                      <s:else>
                        <s:if test="#bean.cardStatus==1" >已绑定</s:if>
                        <s:elseif test="#bean.cardStatus==2">申请中</s:elseif>
                        <s:elseif  test="#bean.cardStatus==3">失败</s:elseif>
                      </s:else></td>
                  </tr>
                </s:iterator>
              </s:else>
            </tbody>
          </table>
        </div>
      </div>
    </div>
    <div style=" width:1170px; margin:0px auto">&nbsp;</div>
  </div>
</div>
<div class="cle"></div>

<!-- 引用底部公共部分 -->
<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
</html>
