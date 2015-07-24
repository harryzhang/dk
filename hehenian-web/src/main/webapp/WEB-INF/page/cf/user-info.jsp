<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="/include/cf-head.jsp"></jsp:include>
</head>
<body>
    <jsp:include page="/include/cf-top.jsp"></jsp:include>
    <div class="s_sur_ix main el_container" style=" overflow:hidden; margin-bottom:20px;"> 
      <!--左侧-->
      <div class=" nr_left">
    <!--左侧-->
    <div style=" padding:30px;border:1px solid #CCC; background:e5e5e5;">
				<div class="ggxxxx_box">
					<h3 style=" margin-bottom:40px;">
						<span style=" font-size:20px;">请完善个人详细信息，确保资金安全。</span>
					</h3>
					<table width="100%" border="0">
						<tr height="5"></tr>
					<tr height="45">
							<td width="200" height="55" align="right">真实姓名：</td>
							<td>
									<input type="text" class="inp188" name="paramMap.realName" id="realName" value="${map.realName}" style=" width:280px; height:30px; padding-left:10px;" />
									<span style="color: red; float: none;" id="u_realName" class="formtips"></span>
								</td>
						</tr>
						<tr>
							<td width="100" height="55" align="right">身份证号：</td>
							<td><s:if test='#request.map.auditStatus ==3'>
									<a id='idNo1'></a>
									<img src="images/neiye2_44.jpg" alt="ok" style="margin-left: 20px;" />
									<input type="hidden" class="inp188" name="paramMap.idNo" id="idNo" value="${map.idNo}" />
								</s:if> <s:else>
									<input type="text" class="inp188" name="paramMap.idNo" id="idNo" value="${map.idNo}" style=" width:280px; height:30px; padding-left:10px;" />
									<span style="color: red; float: none;" id="u_idNo" class="formtips"></span>
								</s:else></td>
						</tr>
						<tr height="45">
							<td width="100" height="55" align="right">电子邮箱：</td>
							<td><input type="text" class="inp188" name="paramMap.email" id="email" value="${map.email}" style=" width:280px; height:30px; padding-left:10px;"  />
							<span style="color: red; float: none;" id="u_email" class="formtips"></span>
							</td>
						</tr>
						<tr height="5"></tr>
						<tr>
							<td width="100" height="55" align="right"><strong>*</strong>居住地：</td>
							<td><s:select id="registedPlacePro" name="regPro"  
									cssStyle="border-left:1px solid #dedede;border-top:1px solid #dedede;border-right:1px solid #dedede;border-bottom:1px solid #dedede;" list="#request.provinceList" listKey="regionId"
									listValue="regionName" headerKey="-1" headerValue="--请选择--" onchange="javascript:if($('#registedPlacePro').val()!=-1){$('#u_reg_Pro_City').html('')}" /> <s:select
									id="registedPlaceCity" name="regCity" cssStyle="border-left:1px solid #dedede;border-top:1px solid #dedede;border-right:1px solid #dedede;border-bottom:1px solid #dedede;"
									list="#request.regcityList" listKey="regionId" listValue="regionName" headerKey="-1" headerValue="--请选择--"  
									onchange="javascript:if($('#registedPlaceCity').val()!=-1){$('#u_reg_Pro_City').html('')}" /> <span style="color: red; float: none;" id="u_reg_Pro_City" class="formtips"></span>
							</td>
						</tr>
						<tr>
							<td colspan="2">
							<br/>
							<span style="color: #F00;">
								所有资料将会严格保密。托管账户中的资金只能取现到您本人名下银行账户，为保证资金安全，请确保您填写的资料真实有效。
								</span>
							</td>
						</tr>
						<tr height="10"></tr>
						<tr>
							<td colspan="2" align="center"><input type="button" value="保存" class="bcbtn" id="jc_btn" style="width: 238px; height: 40px; border: 0px; background: #892E92; font-size: 16px; color: #FFF; margin-left: 0px;"/> <%-- <input type="button" value="彩生活认证导入" <s:if test='#request.map.source ==2'>disabled="disabled"</s:if> style="margin:0;" onclick="importFromLife()" /> --%>
							</td>
						</tr>
						<tr height="5"></tr>
						<tr>
							<td width="100" align="right"></td>
							<td>
								<%-- <i>仅限彩生活会员</i>--%>
							</td>
						</tr>
						<tr height="15"></tr>
					</table>
				</div>
			</div>
    
    <!--右侧-->

  </div>    <jsp:include page="/include/cf-right.jsp"></jsp:include></div>
  <jsp:include page="/include/cf-footer.jsp"></jsp:include>
</body>
<script>
		$(function() {
			//样式选中
			$(".wdzh_next_left li").eq(0).addClass("wdzh_next_left_ong");
		//	dqzt(0);
		//	$('#li_1').addClass('on');
			if('${map.auditStatus}'=="3"){
				$("#registedPlacePro").attr("disabled",true);
				$("#registedPlaceCity").attr("disabled",true);
				$("#email").attr("disabled",true);
				
				var t = '${map.idNo}' == '' ? '' : '${map.idNo}';
				var len = t.length;
				if (len == 15) {
					var tmplen = t.substr(0, 2);
					$('#idNo1').html(tmplen + '***** **** ****');
				} else if (len == 18) {
					var tttttt = t.substr(0, 2);
					$('#idNo1').html(tttttt + '**** **** **** ****');
				}
			}
		});
	</script>
	<script>
		//提交基本资料 start   
		$(document).ready(function() {
			$("input").focus(function(){
				$(this).next().html("");
			});
			$("input").blur(function() {
				//验证号码
				var mdd = /^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
				
				//真实姓名
				if ($(this).attr("id") == "realName") {
					var isName = new Object();
					isName = /^[a-zA-Z\u4e00-\u9fa5]+$/;
					if ($(this).val() == "") {
						$("#u_realName").attr("class", "formtips onError");
						$("#u_realName").html("请填写真实姓名！");
					} else if ($(this).val().length < 2 || $(this).val().length > 20) {
						$("#u_realName").attr("class", "formtips onError");
						$("#u_realName").html("名字长度为2-20个字符");
					} else if (!isName.test($(this).val())) {
						$("#u_realName").html("真实姓名输入有误");
					} else {
						$("#u_realName").attr("class", "formtips");
						$("#u_realName").html("");
					}
				}
				//========
				//身份号码
				if ($(this).attr("id") == "idNo" ) {
					var isIDCard1 = new Object();
					var isIDCard2 = new Object();
					//身份证正则表达式(15位) 
					isIDCard1 = /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;
					//身份证正则表达式(18位) 
					isIDCard2 = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}(x|X))$/;
					if ($(this).val() == "") {
						//alert("请填写身份证号码");
						 $("#u_idNo").html("请填写身份号码！");
					} else if (isIDCard1.test($(this).val()) || isIDCard2.test($(this).val())) {
						//验证身份证号码的有效性
						$(this).next().html("");
						var parama = {};
						parama["paramMap.idNo"] = $(this).val();
						$.post("isIDNO.do", parama, function(data) {
							if (data.putStr == "0") {
								$("#u_idNo").html("请填写身份证号码");
							} else if (data.putStr == "1") {
								$("#u_idNo").html("身份证号码不合法!");
							}
						});
					} else {
						$(this).next().html("身份证号码不正确");
					}
				}
				//========== 验证出生年月
				if ($(this).attr("id") == "birthday") {
					if ($(this).val() != "") {
						$("#u_birthday").html("");
					}
				}
			});
			$("#province").change(function() {
				var provinceId = $("#province").val();
				citySelectInit(provinceId, 2);
			});
			$("#registedPlacePro").change(function() {
				var provinceId = $("#registedPlacePro").val();
				registedPlaceCity(provinceId, 2);
			});

		});
		function registedPlaceCity(parentId, regionType) {
			var _array = [];
			_array.push("<option value='-1'>--请选择--</option>");
			var param = {};
			param["regionType"] = regionType;
			param["parentId"] = parentId;
			$.post("ajaxqueryRegion.do", param, function(data) {
				for ( var i = 0; i < data.length; i++) {
					_array.push("<option value='"+data[i].regionId+"'>" + data[i].regionName + "</option>");
				}
				$("#registedPlaceCity").html(_array.join(""));
			});
		}

		function citySelectInit(parentId, regionType) {
			var _array = [];
			_array.push("<option value='-1'>--请选择--</option>");
			var param = {};
			param["regionType"] = regionType;
			param["parentId"] = parentId;
			$.post("ajaxqueryRegion.do", param, function(data) {
				for ( var i = 0; i < data.length; i++) {
					_array.push("<option value='"+data[i].regionId+"'>" + data[i].regionName + "</option>");
				}
				$("#city").html(_array.join(""));
			});
		}

		//提交基础资料
		$("#jc_btn").click(function() {
			if(${request.map.auditStatus ==3}){
				alert("保存成功");
				window.location.href="cf-wszl.do";
				return false;
			}
			var tsex = '${map.sex}' == '' ? '' : '${map.sex}';
			if ($("#realName").val() == "") {
				 $("#u_realName").html("请填写真实姓名！");
				return false
			}
			if ($("#idNo").val() == "") {
				 $("#u_idNo").html("请填写身份号码！");
				return false
			}
			if ($("#email").val() == "") {
				 $("#u_email").html("请填电子邮箱！");
				return false
			}

			var mail = $("#email").val();
            //对电子邮件的验证
	           if(!/\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/.test(mail))
	            {
	               alert('请输入有效的电子邮箱！');
	               $("#email").focus();
	               return false;
	           	}
			
			if ($("#registedPlacePro").val() == "-1") {
				 $("#u_reg_Pro_City").html("请选择省份！");
				return false
			}
			if ($("#registedPlaceCity").val() == "-1") {
				 $("#u_reg_Pro_City").html("请选择城市！");
				return false
			}
			
			var param = {};
			param["paramMap.realName"] = $("#realName").val();
			param["paramMap.idNo"] = $("#idNo").val();
			param["paramMap.cellPhone"] = $("#cellPhone").val();
			param["paramMap.vilidataNum"] = $("#vilidataNum").val();
			param["paramMap.sex"] = $("input[name='paramMap_sex']:checked").val();
			param["paramMap.birthday"] = $("#birthday").val();
			param["paramMap.highestEdu"] = $("#highestEdu").val();
			param["paramMap.eduStartDay"] = $("#eduStartDay").val();
			param["paramMap.school"] = $("#school").val();
			param["paramMap.maritalStatus"] = $("input[name='paramMap_maritalStatus']:checked").val();
			param["paramMap.hasChild"] = $("input[name='paramMap_hasChild']:checked").val();
			param["paramMap.hasHourse"] = $("input[name='paramMap_hasHourse']:checked").val();
			param["paramMap.hasHousrseLoan"] = $("input[name='paramMap_hasHousrseLoan']:checked").val();
			param["paramMap.hasCar"] = $("input[name='paramMap_hasCar']:checked").val();
			param["paramMap.hasCarLoan"] = $("input[name='paramMap_hasCarLoan']:checked").val();
			param["paramMap.nativePlacePro"] = $("#province").val();
			param["paramMap.nativePlaceCity"] = $("#city").val();
			param["paramMap.registedPlacePro"] = $("#registedPlacePro").val();
			param["paramMap.registedPlaceCity"] = $("#registedPlaceCity").val();

			param["paramMap.address"] = $("#registedPlacePro").find("option:selected").text() + "-" + $("#registedPlaceCity").find("option:selected").text();
			param["paramMap.telephone"] = $("#telephone").val();
			param["paramMap.iscellPhone"] = $("#iscellPhone").val();
			param["paramMap.email"] = $("#email").val();
			param["paramMap.num"] = "1";
			$.post("/updateBasedate.do", param, function(data) {
				if (data.msg == "保存成功") {
					alert("保存成功");
					window.location.href="cf-userinfo.do";
				}else{
					alert(data.msg);
				}
			});
		});

		function uploadCall(basepath, fileName, cp) {
			if (cp == "img") {
				var path = "upload/" + basepath + "/" + fileName;
				$("#img").attr("src", path);
				$("#setImg").attr("src", path);
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
	</script>
</html>