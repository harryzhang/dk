<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Expires" content="0">
<meta http-equiv="kiben" content="no-cache">
<title>完善个人资料</title>
<jsp:include page="/include/app-jfq-head.jsp"></jsp:include>
</head>
<body>
<div id="preloader">
  <div id="status">
    <p class="center-text"> 正在加载... </p>
  </div>
</div>
<!--顶部底部浮动层-->

<jsp:include page="/include/app-comm.jsp"></jsp:include>

<!-- main -->
<div id="main-wrap">
  <div id="container">
    <div id="content" class="page-content">
      <div class="section-title" style=" padding-bottom:10px;">
        <h4>完善个人资料</h4>
      </div>
      <div class="content">
        <div class="container no-bottom">
          <div class="formFieldWrap">
            <label class="field-title contactNameField" for="contactNameField">真实姓名:</label>
            <input type="text" name="paramMap.realName" value="${map.realName}" class="contactField requiredField" id="realName"/>
          </div>
          <div class="formFieldWrap">
            <label class="field-title contactEmailField" for="contactEmailField">身份证号： </label>
            <input type="text" name="paramMap.idNo" value="${map.idNo}" class="contactField requiredField requiredEmailField" id="idNo"/>
          </div>
          <%--  <div class="formFieldWrap">
          <label class="field-title contactEmailField" for="contactEmailField">电子邮件： </label>
          <input type="text" name="paramMap.email" value="${map.email}" class="contactField requiredField requiredEmailField" id="email"/>
        </div> --%>
          <div class="formSubmitButtonErrorsWrap">
            <input type="button" class="buttonWrap button button-green contactSubmitButton" id="jc_btn" value="保存" data-formId="contactForm" onclick="bcbtn();"/>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
<script>
function bcbtn() {
			
			var tsex = '${map.sex}' == '' ? '' : '${map.sex}';
			if ($("#realName").val() == "") {
				 alert("请填写真实姓名！");
				return false
			}
			if ($("#idNo").val() == "") {
				 alert("请填写身份号码！");
				return false
			}
			/* if ($("#email").val() == "") {
				 alert.html("请填电子邮箱！");
				return false
			} */

			//var mail = $("#email").val();
            //对电子邮件的验证
	         //  if(!/\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/.test(mail))
	         //   {
	         //      alert('请输入有效的电子邮箱！');
	         //      $("#email").focus();
	         //      return false;
	          // 	}
			/*
			if ($("#registedPlacePro").val() == "-1") {
				 $("#u_reg_Pro_City").html("请选择省份！");
				return false
			}
			if ($("#registedPlaceCity").val() == "-1") {
				 $("#u_reg_Pro_City").html("请选择城市！");
				return false
			}
*/
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
			param["paramMap.registedPlacePro"] = "";//$("#registedPlacePro").val();
			param["paramMap.registedPlaceCity"] = "";//$("#registedPlaceCity").val();

			param["paramMap.address"] = $("#registedPlacePro").find("option:selected").text() + "-" + $("#registedPlaceCity").find("option:selected").text();
			param["paramMap.telephone"] = $("#telephone").val();
			param["paramMap.iscellPhone"] = $("#iscellPhone").val();
			param["paramMap.email"] = "";
			param["paramMap.num"] = "1";
			$.post("/updateBasedate.do", param, function(data) {
				if (data.msg == "保存成功") {
					alert("保存成功");
					window.location.href="userinfo.do";
				}else{
					alert(data.msg);
				}
			});
		}
	</script>
</html>
