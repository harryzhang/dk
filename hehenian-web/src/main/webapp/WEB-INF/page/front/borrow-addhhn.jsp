<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
<style>
.fred {
	color: #ff0000;
}

#deadLine,#deadDay {
	float: left;
	margin-top: 8px;
	margin-right: 15px;
}
</style>
</head>
<body>
	<!-- 引用头部公共部分 -->
	<jsp:include page="/include/top.jsp"></jsp:include>
	<form id="form" action="addBorrow.do" method="post">
		<div class="center">
			<div class="wytz_center">
				<div class="fbjk_all">
					<h5>
						<strong> 发布借款 </strong> <span> <b>  </b> 以下所有资料为必填项，均会严格保密。 </span>
					</h5>
					<div class="rmainbox" style="margin-top: -20px;" align="center">
						<%--    <p class="tips"><span class="fred"></span> </p>--%>
						<div class="tab" align="center">
							<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
								<tr>
									<th colspan="2" align="left"></th>
								</tr>
								<tr>
									<td align="right">资金用途：</td>
									<td align="left"><input name="paramMap.title" type="text" class="inp280" id="title" maxlength="12" value="${paramMap.title}" /> <span class="fred" style="color:#ff0000;"><s:fielderror
												fieldName="paramMap['title']"></s:fielderror> </span></td>
								</tr>
<%--								<tr height="10"></tr>--%>
<%--								<tr>--%>
<%--									<td align="right" width="90" valign="top">借款图片：<input type="hidden" id="imgPath" name="paramMap.imgPath" value="${paramMap.imgPath}" /></td>--%>
<%--									<td><input type="hidden" id="personalImg" value="${user.personalHead}" /> <label><input type="radio" name="radio" id="r_1" checked="checked" value="1" />上传借款图片</label> <label><input--%>
<%--											type="radio" name="radio" id="r_2" value="2" />使用用户头像 </label> <label><input type="radio" name="radio" id="r_3" value="3" />使用系统头像</label> <span style="color:#ff0000;"><s:fielderror--%>
<%--												fieldName="paramMap['imgPath']"></s:fielderror> </span> <input type="hidden" id="radioval" name="paramMap.radioval" value="${paramMap.radioval}" /></td>--%>
<%--								</tr>--%>
<%--								<tr>--%>
<%--									<td align="right">&nbsp;</td>--%>
<%--									<td style="margin-top: -10px;">--%>
<%--										<div id="tab_1">--%>
<%--											<div class="fbjk_all_touxh" style="display:block;width: 100%">--%>
<%--												<p>--%>
<%--													<input type="button" onclick="javascript:void(0);" id="btn_personalHead" value="上传图片" />（推荐使用您的生活近照，或其他与借款用途相关的图片，有助增加借款成功几率。严禁使用他人照片）--%>
<%--												</p>--%>
<%--											</div>--%>
<%--										</div>--%>
<%--										<div id="tab_2" style="display:none;"></div>--%>
<%--										<div id="tab_3" style="display:none;">--%>
<%--											<table id="sysimg" style="width:400px;">--%>
<%--												<tr>--%>
<%--													<td class="tx"><s:iterator var="sysImage" value="sysImageList">--%>
<%--															<span><img src="${sysImage.selectName}" class="selimg" /> </span>--%>
<%--														</s:iterator></td>--%>
<%--												</tr>--%>
<%--											</table>--%>
<%--										</div></td>--%>
<%--								</tr>--%>
<%--								<tr>--%>
<%--									<td align="right">&nbsp;</td>--%>
<%--									<td class="tx"><img id="img" src="${headImg}" width="100" height="100" />--%>
<%--									</td>--%>
<%--								</tr>--%>
<%--								<tr height="20"></tr>--%>
<%--								<tr>--%>
<%--									<td align="right" width="90"><span>借款类型：</span>--%>
<%--									</td>--%>
<%--									<td><select disabled="disabled" style="color: black;"><option>${session.typeName}</option>--%>
<%--									</select>--%>
<%--									</td>--%>
<%--								</tr>--%>
<%--								<tr height="10"></tr>--%>
<%--								<tr>--%>
<%--									<td align="right" width="90"><span>借款用途：</span>--%>
<%--									</td>--%>
<%--									<td><s:select list="borrowPurposeList" id="purpose" name="paramMap.purpose" cssClass="sel_140" listKey="selectName" listValue="selectName" headerKey="" headerValue="--请选择--"></s:select>--%>
<%--										<span style="color:#ff0000;"><s:fielderror fieldName="paramMap['purpose']"></s:fielderror> </span>--%>
<%--									</td>--%>
<%--								</tr>--%>
								<tr height="10"></tr>
								<tr>
									<td align="right"><span>借款期限：</span>
									</td>
									<td><s:select list="borrowDeadlineMonthList" id="deadLine" name="paramMap.deadLine" cssClass="sel_140" listKey="key" listValue="value" headerKey="" headerValue="--请选择--"></s:select>
										<%--<s:select list="borrowDeadlineDayList" id="deadDay" cssStyle="display:none;" name="paramMap.deadDay" cssClass="sel_140" listKey="key" listValue="value" headerKey=""--%> <%--headerValue="--请选择--"></s:select> <s:checkbox name="paramMap.daythe" id="daythe" />&nbsp;设为天标--%>
										<span style="color:#ff0000;"><s:fielderror fieldName="paramMap['deadLine']"></s:fielderror>
									</span></td>
								</tr>
								<tr height="10"></tr>
								<tr>
									<td align="right"><span>借款总额：</span>
									</td>
									<td align="left"><input type="text" id="amount" name="paramMap.amount" class="inp100x" value="${paramMap.amount}" />元<span style="color:#ff0000;"> <span id="amountIp"
											style="color: red;margin-left: 10px;"> <s:fielderror fieldName="paramMap['amount']"></s:fielderror> <s:fielderror fieldName="enough"></s:fielderror> </span>
									</td>
								</tr>
								<tr height="10"></tr>
								<tr>
									<td align="right"><span>所在地区：</span>
									</td>
									<td align="left"><input type="text" value="${mapCity}" name="paramMap.mapCity" id="city" readonly="readonly"/><span id="s_city" style="color:#ff0000;">
									</span></td>
								</tr>
								<tr height="10"></tr>
<%--								<tr>--%>
<%--									<td align="right" width="90"><span>年利率：</span>--%>
<%--									</td>--%>
<%--									<td><input type="text" name="paramMap.annualRate" maxlength="5" value="${paramMap.annualRate}" class="inp100x" />%<span style="color:#ff0000;"><s:fielderror--%>
<%--												fieldName="paramMap['annualRate']"></s:fielderror> </span>--%>
<%--									</td>--%>
<%--								</tr>--%>
<%--								<tr height="10"></tr>--%>
<%--								<tr>--%>
<%--									<td align="right" width="90"><span>最多投标人数：</span>--%>
<%--									</td>--%>
<%--									<td><input type="text" name="paramMap.maxInvest" id="maxInvest" value="${paramMap.maxInvest}" style="color:#9D9D9D" />人<span id="maxInvestip"--%>
<%--										style="color: red;margin-left: 10px;"></span></td>--%>
<%--								</tr>--%>
<%--								<tr height="10"></tr>--%>
<%--								<tr>--%>
<%--									<td align="right"><span>筹标期限：</span>--%>
<%--									</td>--%>
<%--									<td><input type="hidden" name="validateDay" value="${validateDay }" /> <s:if test="#request.validateDay==1">--%>
<%--											<input type="hidden" name="paramMap.raiseTerm" value="0" />无限制--%>
<%--					    </s:if> <s:else>--%>
<%--											<s:select list="borrowRaiseTermList" id="raiseTerm" name="paramMap.raiseTerm" cssClass="sel_140" listKey="key" listValue="value" headerKey="" headerValue="--请选择--"></s:select>--%>
<%--											<span style="color:#ff0000;"><s:fielderror fieldName="paramMap['raiseTerm']"></s:fielderror> </span>--%>
<%--										</s:else></td>--%>
<%--								</tr>--%>
								<tr height="10"></tr>
<%--								<tr>--%>
<%--									<td align="right" width="90" valign="top"><span>借款详情：</span>--%>
<%--									</td>--%>
<%--									<td><textarea name="paramMap.detail" class="txt420">${paramMap.detail}</textarea>--%>
<%--									</td>--%>
<%--								</tr>--%>
<%--							</table>--%>
<%--							<h6>--%>
<%--								<span>投标奖励</span>--%>
<%--							</h6>--%>
<%--							<table width="955" border="0">--%>
<%--								<tr>--%>
<%--									<td><input type="hidden" name="award_status" value="${award_status }" /><input type="hidden" id="excitation" name="paramMap.excitationType" value="${paramMap.excitationType}" />--%>
<%--										<input type="radio" id="notReward" checked="checked" name="excitationType" />不设置奖励</td>--%>
<%--								</tr>--%>
<%--								<tr height="10"></tr>--%>
<%--								<tr>--%>
<%--									<td><input type="radio" id="hasSum" name="excitationType" />按固定比例金额分摊奖励 <input type="text" id="sum" name="paramMap.sum" value="${paramMap.sum}" class="inp100x gray"--%>
<%--										disabled="disabled" /> 元<span style="color:#ff0000;"><s:fielderror fieldName="paramMap['sum']"></s:fielderror> </span>--%>
<%--									</td>--%>
<%--								</tr>--%>
<%--								<tr height="10"></tr>--%>
<%--								<tr>--%>
<%--									<td><input type="radio" id="hasSumRate" name="excitationType" />按投标金额比例奖励 <input type="text" id="sumRate" name="paramMap.sumRate" maxlength="20" value="${paramMap.sumRate}"--%>
<%--										class="inp100x gray" disabled="disabled" /> %<span style="color:#ff0000;"><s:fielderror fieldName="paramMap['sumRate']"></s:fielderror> </span>--%>
<%--									</td>--%>
<%--								</tr>--%>
<%--								<tr height="10"></tr>--%>
<%--								<tr>--%>
<%--									<td><input type="checkbox" />标的失败时也同样奖励</td>--%>
<%--								</tr>--%>
<%--								<tr height="10"></tr>--%>
<%--							</table>--%>
<%--							<h6>--%>
<%--								<span>担保机构</span>--%>
<%--							</h6>--%>
<%--							<table width="955" border="0">--%>
<%--								<tr>--%>
<%--									<td><span>担保机构：</span>--%>
<%--									</td>--%>
<%--									<td><input type="text" value="汇付天下" readonly="readonly" /></td>--%>
<%--								</tr>--%>
<%--								<tr height="10"></tr>--%>
<%--							</table>--%>
<%--							<h6 style="margin-top: 30px;margin-bottom: -40px;">--%>
<%--								<span>提交借款信息</span>--%>
<%--							</h6>--%>
<%--							<table width="955" border="0">--%>
<%--								<tr>--%>
<%--									<td>&nbsp;                     <input  id="hasPWD" name="paramMap.hasPWD"  type="checkbox" />设置投标密码：<input type="password" id="investPWD" value="${paramMap.investPWD }" disabled="disabled" name="paramMap.investPWD" maxlength="20" class="inp100x" />--%>
<%--									</td>--%>
<%--								</tr>--%>
<%--								<tr height="10"></tr>--%>
								<tr>
								<td align="right"><span>验证码：</span></td>
									<td align="left"><input type="text" style=" width:90px; float:left;" class="inp100x" name="paramMap.code" id="code" /> <img src=""
										title="点击更换验证码" style="cursor: pointer;margin-top: 2px;" id="codeNum" width="56" height="25" onclick="javascript:switchCode()" />
										<span style="color:#ff0000;"><s:fielderror fieldName="paramMap['code']"></s:fielderror> </span></td>
								</tr>
<%--								<tr height="10"></tr>--%>
<%--								<tr>--%>
<%--									<td><input type="checkbox" checked="checked" />我已阅读并接受<a href="#">《借款协议范本》</a>中所列出的条款</td>--%>
<%--								</tr>--%>
								<tr height="20"></tr>
								<td></td>
								<td align="left">
								<s:token>
									<input type="button" value="发布借款" id="bcbtn" class="fbjk_aniu" />
								</s:token>
								</td>
								</tr>
								<tr height="10"></tr>
							</table>
						</div>
						<div class="cle"></div>
					</div>
				</div>
			</div>
		</div>
	</form>
	<!-- 引用底部公共部分 -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
	<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
	<script>
		var flag = true;
		$(document).ready(function() {
<%--			var sd = parseInt($(".l-nav").css("height"));--%>
<%--			var sdf = parseInt($(".r-main").css("height"));--%>
<%--			$(".l-nav").css("height", sd > sdf ? sd : sdf - 15);--%>
<%--			var img = '${paramMap.imgPath}';--%>
<%--			if (img == "") {--%>
<%--				img = "images/default-img.jpg";--%>
<%--			}--%>
<%--			$('#imgPath').val(img);--%>
<%--			$("#img").attr("src", img);--%>
<%--			//$('#imgPath').val('${paramMap.imgPath}');--%>
<%--			// $("#img").attr("src",'${paramMap.imgPath}');--%>
<%--			$('#purpose').val('${paramMap.purpose}');--%>
<%--			$('#deadLine').val('${paramMap.deadLine}');--%>
<%--			$('#paymentMode').val('${paramMap.paymentMode}');--%>
<%--			$('#raiseTerm').val('${paramMap.raiseTerm}');--%>
<%--			$('#excitation').val('${paramMap.excitationType}');--%>
<%--			$('#excitationMode').val('${paramMap.excitationMode}');--%>
<%--			$('#radioval').val('${paramMap.radioval}');--%>
<%--			var excitation = $('#excitation').val();--%>
<%--			var mode = $('#excitationMode').val();--%>
<%--			var radioval = $('#radioval').val();--%>
<%--			if (radioval != '') {--%>
<%--				$('#r_' + radioval).attr('checked', 'true');--%>
<%--				if (radioval == 1) {--%>
<%--					$('#tab_1').css('display', 'block');--%>
<%--				}--%>
<%--				if (radioval == 2) {--%>
<%--					$('#tab_2').css('display', 'block');--%>
<%--					$('#tab_1').css('display', 'none');--%>
<%--				}--%>
<%--				if (radioval == 3) {--%>
<%--					$('#tab_3').css('display', 'block');--%>
<%--					$('#tab_1').css('display', 'none');--%>
<%--				}--%>
<%--			}--%>
<%--			if (excitation != '') {--%>
<%--				$('#radio_' + excitation).attr('checked', 'true');--%>
<%--				if (excitation == 2) {--%>
<%--					$('#sum').removeClass('gray');--%>
<%--					$('#sum').removeAttr('disabled');--%>
<%--					$('#sumRate').addClass('gray');--%>
<%--					$('#sumRate').attr('disabled');--%>
<%--					$('#sumRate').val('');--%>
<%--				} else if (excitation == 3) {--%>
<%--					$('#sumRate').removeClass('gray');--%>
<%--					$('#sumRate').removeAttr('disabled');--%>
<%--					$('#sum').addClass('gray');--%>
<%--					$('#sum').attr('disabled');--%>
<%--					$('#sum').val('');--%>
<%--				}--%>
<%--			} else {--%>
<%--				$('#excitation').val('1');--%>
<%--			}--%>
<%--			if (mode == '2') {--%>
<%--				$('#mode').attr('checked', 'true');--%>
<%--			} else {--%>
<%--				$('#excitationMode').val("1");--%>
<%--			}--%>
<%--		});--%>
<%--		var paymentModeHtml;--%>
<%--		$(function() {--%>
<%--			hhn(2);--%>
<%--			paymentModeHtml = $("#paymentMode").html();--%>
<%--			$('input[name="excitationType"]').click(function() {--%>
<%--				if ($(this).val() == 2) {--%>
<%--					$('#sum').removeClass('gray');--%>
<%--					$('#sum').removeAttr('disabled');--%>
<%--					$('#sumRate').addClass('gray');--%>
<%--					$('#sumRate').attr('disabled');--%>
<%--					$('#sumRate').val('');--%>
<%--				} else if ($(this).val() == 3) {--%>
<%--					$('#sumRate').removeClass('gray');--%>
<%--					$('#sumRate').removeAttr('disabled');--%>
<%--					$('#sum').addClass('gray');--%>
<%--					$('#sum').attr('disabled');--%>
<%--					$('#sum').val('');--%>
<%--				} else {--%>
<%--					$('#sumRate').addClass('gray');--%>
<%--					$('#sumRate').attr('disabled');--%>
<%--					$('#sumRate').val('');--%>
<%--					$('#sum').addClass('gray');--%>
<%--					$('#sum').attr('disabled');--%>
<%--					$('#sum').val('');--%>
<%--				}--%>
<%--				$('#excitation').val($(this).val());--%>
<%--			});--%>
<%--			$('#mode').click(function() {--%>
<%--				var check = $(this).attr('checked');--%>
<%--				if (check == 'checked') {--%>
<%--					$('#excitationMode').val('2');--%>
<%--				} else {--%>
<%--					$('#excitationMode').val('1');--%>
<%--				}--%>
<%--			});--%>
<%--			$('#hasPWD').click(function() {--%>
<%--				var hasPWD = $('#hasPWD').attr('checked');--%>
<%--				if (hasPWD == 'checked') {--%>
<%--					$('#investPWD').attr('disabled', false);--%>
<%--				} else {--%>
<%--					$('#investPWD').attr('disabled', true);--%>
<%--				}--%>
<%--				$('#investPWD').val('');--%>
<%--			});--%>
<%--			$('#notReward').click(function() {--%>
<%--				var notReward = $('#notReward').attr('checked');--%>
<%--				if (notReward == 'checked') {--%>
<%--					$('#sum').attr('disabled', true);--%>
<%--					$('#sumRate').attr('disabled', true);--%>
<%--				}--%>
<%--				$('#sum').val('');--%>
<%--				$('#sumRate').val('');--%>
<%--			});--%>
<%--			$('#hasSum').click(function() {--%>
<%--				var hasSum = $('#hasSum').attr('checked');--%>
<%--				if (hasSum == 'checked') {--%>
<%--					$('#sum').attr('disabled', false);--%>
<%--					$('#sumRate').attr('disabled', true);--%>
<%--				} else {--%>
<%--					$('#sum').attr('disabled', true);--%>
<%--					$('#sumRate').attr('disabled', false);--%>
<%--				}--%>
<%--				$('#sum').val('');--%>
<%--			});--%>
<%--			$('#hasSumRate').click(function() {--%>
<%--				var hasSumRate = $('#hasSumRate').attr('checked');--%>
<%--				if (hasSumRate == 'checked') {--%>
<%--					$('#sumRate').attr('disabled', false);--%>
<%--					$('#sum').attr('disabled', true);--%>
<%--				} else {--%>
<%--					$('#sumRate').attr('disabled', true);--%>
<%--					$('#sum').attr('disabled', false);--%>
<%--				}--%>
<%--				$('#sumRate').val('');--%>
<%--			});--%>
<%--			$('#daythe').click(function() {--%>
<%--				showdays();--%>
<%--			});--%>
			$('#bcbtn').click(function() {
				//var aa = $("[name='purpose']").val();
				if ($("#subscribeStatus").val() != 1) {
					var arStart = $('#paramMap_minTenderedSum').val();
					var amount = $('#amount').val();
					arStart = parseFloat(arStart);
					amount = parseFloat(amount);
					var arEnd = $('#paramMap_maxTenderedSum').val();
					arEnd = parseFloat(arEnd);
					var deadLine = $('#deadLine').val();
					var title = $('#title').val();
					var title = $('#title').val();
					if (title == "") {
						alert('请填写借款用途!');
						$('#title').focus();
						return false;
					}
					if (deadLine == "" ) {
						alert('请选择借款期限!');
						$('#deadLine').focus();
						return false;
					}

<%--					if (arStart > arStart) {--%>
<%--						alert('最多投标金额不能小于最低投标金额!');--%>
<%--						$('#paramMap_maxTenderedSum')[0].selectedIndex = 0;--%>
<%--						return false;--%>
<%--					}--%>
<%--					if (arStart > amount) {--%>
<%--						alert('最低投标金额不能超过借款金额!');--%>
<%--						$('#paramMap_minTenderedSum')[0].selectedIndex = 0;--%>
<%--						return false;--%>
<%--					}--%>
<%--					if (arEnd > amount) {--%>
<%--						alert('最多投标金额不能超过借款金额!');--%>
<%--						$('#paramMap_maxTenderedSum')[0].selectedIndex = 0;--%>
<%--						return false;--%>
<%--					}--%>

<%--					if (isNaN($("#maxInvest").val())) {--%>
<%--						alert("请输入正确的人数数值");--%>
<%--						$("#maxInvest").focus();--%>
<%--						return false;--%>
<%--					}--%>
<%--					var num = parseInt($("#maxInvest").val());--%>
<%--					if (num<2 || num>49) {--%>
<%--						alert("最多投标人数范围是:不少于2人,不多于49人");--%>
<%--						$("#maxInvest").focus();--%>
<%--						return false;--%>
<%--					}--%>
					if (isNaN($("#amount").val())) {
						alert("请输入正确的金额");
						return false;
					}
					var num = parseInt($("#amount").val());
					if (Number(num) % 100 != 0 || num < 100) {
						alert("请输入正确金额，如：100,200")
						$("#amount").focus();
						return false;
					}

					if ($("#city").val() =="" || $("#city").val() == null) {
						alert("请输填写所在地区");
						$("#city").focus();
						return false;
					}
					
				}
				if (flag) {
					$('#form').submit();
					flag = false;
				}
			});
			$("#paramMap_maxTenderedSum").change(function() {
				var arStart = $('#paramMap_minTenderedSum').val();
				arStart = parseFloat(arStart);
				var arEnd = $(this).val();
				arEnd = parseFloat(arEnd);
				if (arEnd < arStart) {
					alert('最多投标金额不能小于最低投标金额!');
					$('#paramMap_maxTenderedSum')[0].selectedIndex = 0;
				}
			});
			$("#paramMap_minTenderedSum").change(function() {
				$('#paramMap_maxTenderedSum')[0].selectedIndex = 0;
			});
			//上传图片
<%--			$("#btn_personalHead").click(--%>
<%--					function() {--%>
<%--						var dir = getDirNum();--%>
<%--						var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'user/" + dir--%>
<%--								+ "','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall','cp':'img'}";--%>
<%--						json = encodeURIComponent(json);--%>
<%--						window.showModalDialog("uploadFileAction.do?obj=" + json, window, "dialogWidth=500px;dialogHeight=400px");--%>
<%--						var headImgPath = $("#img").attr("src");--%>
<%--						if (headImgPath == "") {--%>
<%--							alert("上传失败！");--%>
<%--						}--%>
<%--					});--%>
<%--			$('#sysimg img').click(function() {--%>
<%--				$('#imgPath').val($(this).attr('src'));--%>
<%--				$('#img').attr('src', $(this).attr('src'));--%>
<%--			});--%>
<%--			$('#r_1').click(function() {--%>
<%--				$('#tab_1').css('display', 'block');--%>
<%--				$('#tab_2').css('display', 'none');--%>
<%--				$('#tab_3').css('display', 'none');--%>
<%--				$('#radioval').val('1');--%>
<%--			});--%>
<%--			$('#r_2').click(function() {--%>
<%--				var personalImg = $('#personalImg').val();--%>
<%--				$('#tab_1').css('display', 'none');--%>
<%--				$('#tab_2').css('display', 'block');--%>
<%--				$('#tab_3').css('display', 'none');--%>
<%--				$('#radioval').val('2');--%>
<%--				$('#img').attr('src', personalImg);--%>
<%--				$('#imgPath').val(personalImg);--%>
<%--			});--%>
<%--			$('#r_3').click(function() {--%>
<%--				$('#tab_1').css('display', 'none');--%>
<%--				$('#tab_2').css('display', 'none');--%>
<%--				$('#tab_3').css('display', 'block');--%>
<%--				$('#radioval').val('3');--%>
<%--			});--%>
<%--			$('#paymentMode').change(function() {--%>
<%--				var check = $('#daythe').attr('checked');--%>
<%--				if (check == true) {--%>
<%--					$('#paymentMode').get(0).selectedIndex = 1;--%>
<%--				}--%>
<%--			});--%>

			switchCode();
			showdays();

			//add by houli 进入页面发布借款的时候给用户提示信息
			var msg = '${request.msg}';
			var ulimit = '${request.usableCreditLimit}';
			var limit = '${request.creditLimit}';
<%--			if (msg != null && msg != "") {--%>
<%--				alert("   您的信用额度是" + limit + "，可用借款额度是" + ulimit + "。 \n" + msg);--%>
<%--					alert("您的个人资料已完善，请认真填写以下借款信息!");--%>
<%--			}--%>
			if ($("#investPWD").val() != '') {
				$('#investPWD').attr('disabled', false);
				$('#hasPWD').attr('checked', true);
			}
			//--------end
		});
		function switchCode() {
			var timenow = new Date();
			$('#codeNum').attr('src', 'admin/imageCode.do?pageId=borrow&d=' + timenow);
		};
		var flag = true;
		function chkRadio(id) {
			id.checked = flag;
			flag = !flag;
		}
		function showdays() {
			var check = $('#daythe').attr('checked');
			if (check == 'checked') {
				$('#deadLine').css('display', 'none');
				$('#deadDay').css('display', 'block');
				// $('#paymentMode').attr('disabled',true);
				// $('#paymentMode').get(0).selectedIndex = 1;

				//$("#paymentMode").html("<option value='1'>到期还本付息</option>");
			} else {
				// $('#paymentMode').attr('disabled',false);
				//$('#paymentMode').get(0).selectedIndex = 0;
				$('#deadLine').css('display', 'block');
				$('#deadDay').css('display', 'none');
				//$("#paymentMode").html("");
				//$("#paymentMode").html(paymentModeHtml);
			}
		}
	</script>
	<script>
		function uploadCall(basepath, fileName, cp) {
			if (cp == "img") {
				var path = "upload/" + basepath + "/" + fileName;
				$("#img").attr("src", path);
				$("#setImg").attr("src", path);
				$("#imgPath").val(path);
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
</body>
</html>