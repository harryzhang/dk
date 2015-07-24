<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>集团贷</title>
	<%@include file="../include/taglib.jsp"%>
	<link href="<c:url value='web_res/css/dk/css/zzscGroup.css'/>" rel="stylesheet" />
	<script type="text/javascript" src="<c:url value='web_res/css/dk/js/jquery.js'/>"></script>
	<script type="text/javascript" src="<c:url value='web_res/css/dk/js/jquery.caroufredsel-6.0.4-packed.js'/>"></script>
	<script type="text/javascript" src="<c:url value='web_res/css/dk/js/focus.js'/>"></script>
	<script type="text/javascript" src="<c:url value='web_res/css/dk/js/jquery-1.4.4.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='web_res/css/dk/js/jquery.reveal.js'/>"></script>
	
	<!-- 统计流量 -->
	<script type="text/javascript" src="http://tajs.qq.com/stats?sId=48753041" charset="UTF-8"></script>
	
	<style>

		.err{
		  position: absolute;
		  color: #FF8181;
		  margin-top: -11px;
		  font-size: 12px;
				}
		.sub{  display: block;
		  width: 280px;
		  text-align: center;
		  font-size: 18px;
		  color: #fff;
		  text-decoration: none;
		  background: #F60;
		  height: 38px;
		  line-height: 38px;
			border:0px;}
		.sub.hui{
			background: #ccc;
		}
		.yq_banner_list {
  width: 100px;
  height: 25px;
  position: absolute;
  /* top: 485px; */
  text-align: center;
  z-index: 100;
  /* left: 120px; */
  /* padding-right: 12px; */
  margin: 50px 0 0 -50px;
  left: 50%;
}
.banner_magbox{
	padding: 20px 30px ;
}
.regist-tip{
	font-size: 36px;
	font-weight: 900;
	color: #4fcdf6;
	text-align: center;
	height: 68px;
}
.regist li{
	padding-bottom: 20px;
}
.regist *{
	width: 285px;
	font-size: 15px;
	font-family: "微软雅黑";
	height: 30px;
	line-height: 30px;
	color: #666;
}
	</style>
</head>
<body>

	<!-- 引用头部公共部分 -->
	<jsp:include page="../include/top.jsp"></jsp:include>
	<div class="banner">
		<div class="banner_show" id="banner_show">
			<a class="bannger_inbox"><img src="<c:url value='web_res/img/banner1.jpg'/>" width="2000" height="572"/></a>
			<a class="bannger_inbox"><img src="<c:url value='web_res/img/banner2.jpg'/>" width="2000" height="572"/></a> 
			<a class="bannger_inbox"><img src="<c:url value='web_res/img/banner3.jpg'/>" width="2000" height="572"/></a>
			<div class="banner_mag">
				<div class="banner_magbox">
					<div class="regist-tip">
						申请贷款
					</div>
					<ul class="regist">
						<li>
							<input type="text" name="user.realName" placeholder="真实姓名" maxlength="6" value="" />
						</li>
						<li>
							<input type="text" name="user.idNo" placeholder="身份证号" maxlength="18" value="" />
						</li>
						<li>
							<input type="text" name="user.mobile" placeholder="手机号码" maxlength="11" value="" />
						</li>
						<li>
							<input type="text" name="user.income" maxlength="10" placeholder="月收入(每月实际到账收入)" value="" />
						</li>
						<li>
							<select  id="company">
								<option value="0" >选择公司</option>
								<option value="地产集团">地产集团</option>
								<option value="文旅集团">文旅集团</option>
								<option value="物业国际">物业国际</option>
								<option value="商管公司">商管公司</option>
								<option value="金融集团">金融集团</option>
								<option value="彩生活集团">彩生活集团</option>
								<option value="福泰年">福泰年</option>
								<option value="花样年教育">花样年教育</option>
								<option value="邻里乐">邻里乐</option>
							</select> 
						</li>
						<li>
							<input type="text" id="dept"  value="" placeholder="所在部门"  />
						</li>
						<li>
							<a style="display: block; text-align: center; margin-top:10px; font-size: 18px; color: #fff; text-decoration: none; background: #F60; height: 38px; line-height: 38px;"
							href="javascript:void(0);" data-reveal-id="myModal"> 立即申请</a>
							<p style="font-size:12px; padding-top:4px;">目前仅针对深圳客户,其他区域将陆续开放,敬请期待.</p>
						</li>
						
					</ul>
				</div>
				<div class="yq_banner_list" id="yq_banner_list">
					<a href="javascript:;" rel="0" class="hover">&nbsp;</a> <a href="javascript:;" rel="1" >&nbsp;</a> <a href="javascript:;" rel="2" class="">&nbsp;</a>
				</div>
			</div>
			<div class="banner_pre_next">
				<a href="javascript:;" class="banner_btn_left">上一个</a>
				<a href="javascript:;" class="banner_btn_right">下一个</a>
			</div>
		</div>
		<!--蓝色文字部分-->
		<div class="focus-news">
			<div style="float: left; width: 320px; margin-left: 35px;">
				<div style="width: 320px;">
					<h2>极简申请&nbsp;&nbsp;&nbsp;&nbsp;秒批放款&nbsp;&nbsp;&nbsp;&nbsp;只贷给您</h2>
				</div>
			</div>

			<div style="float: right; width: 470px; margin-right: 35px;">
				<div>
					借款进度查询：
					<input name="idNo" type="text" style="font-size: 16px; color: #999" value="请输入身份证号码进行查询" maxlength="18"/> 
					<a style="float: right; display: block; width: 80px; text-align: center; font-size: 18px; color: #fff; text-decoration: none; background: #F60; height: 34px; line-height: 34px;" 
					href="javascript:void(0);" data-reveal-id="myModal-cx"> 查询</a>
				</div>
			</div>
		</div>
		<!--蓝色文字部分end-->
	</div>

	<div >
<!-- 	<div style="background: #f0f4ff"> -->
		<div style="width: 1200px; margin: 0px auto;">
			<img src="<c:url value='web_res/css/dk/images/ad.gif'/>" width="1200" height="421" alt="" />
		</div>
	</div>
	<div style="padding: 20px 0px;">
		<div style="width: 980px; margin: 0px auto; line-height: 32px;">
			<h2>特别提醒</h2>
			1.您的贷款申请最终能否得到核准，以合和年的最终审核结果为准。<br />
			2.根据中国银行业监督管理委员会有关规定，贷款不可用于购买不动产、认购和购买股票或任何其他股本权益性投资。有任何疑问，敬请致电合和年服务热线4008-303-737。<br />
			3.填写者同意并确认：其填写上述个人信息即表明其授权合和年致电填写者以核实所填信息的真实性；且其填写的个人信息将仅用于向合和年申请贷款之目的。
		</div>
	</div>

	<div id="myModal" class="reveal-modal">
		<form id="loanDetail" name="loanDetail" action="addLoanDetailGroup" method="post">
			<input type="hidden" name="remark" id ="remark"  value=""  />
			<input type="hidden" name="realName" value="" /> 
			<input type="hidden" name="idNo" value="" /> 
			<input type="hidden" name="mobile" value="" /> 
			<input type="hidden" name="income" value="" />
			<div style="float: left">
				<h2>完善借款信息</h2>
				<p>
					借款期数：<select name="loanPeriod">
						<option value="3">3个月</option>
						<option value="6">6个月</option>
					</select>
				</p>
				<p>
					借款金额：<input name="loanAmount" type="text"
						class="input" value="" maxlength="6" style="font-size: 18px;" />
				</p>
				<p>
					根据系统计算您最多可以借贷：<span id="creditAmountTip">0.00</span>元
				</p>
				<p>到账金额请以最终放款金额为准。</p>
				<p>
					<input type="checkbox" id="chkAgreement1"  style="width: 12px; height: 12px; margin-right: 10px;" /> 我已阅读并同意
					<a href="<c:url value='/web_res/word/concat.html'/>" target="_black">《合和年在线借款协议》</a>
				<p>
					<input type="checkbox" id="chkAgreement2"  style="width: 12px; height: 12px; margin-right: 10px;" /> 我已阅读并同意
					<a href="<c:url value='/web_res/word/consult.html'/>" target="_black">《借款咨询服务协议》</a>
				</p>
				<p>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input id="btnSubmit" type="button" value="提交申请" onclick=""
						style="background: #F60; color: #FFF; font-size: 18px; height: 32px; line-height: 32px; border: 0px;" />
				</p>
			</div>
			<div style=" float:left; margin-left:16px;">
				<h2>还款计划表</h2>
				<div>
					<table id="repayDetailTab" width="328" border="0" cellspacing="0" cellpadding="0" style="border:1px solid #999; line-height:38px; text-align:center;">
						<tr>
							<td bgcolor="#FF6600" style="color: #FFF; line-height: 42px;"><strong>期数</strong></td>
							<td bgcolor="#FF6600" style="color: #FFF; line-height: 42px;"><strong>应还本金</strong></td>
							<td bgcolor="#FF6600" style="color: #FFF; line-height: 42px;"><strong>应还利息</strong></td>
							<td bgcolor="#FF6600" style="color: #FFF; line-height: 42px;"><strong>应还总额</strong></td>
						</tr>
					</table>
				</div>
			</div>
			<a class="close-reveal-modal">×</a>
		</form>
	</div>

	<!--查询弹出-->
	<div id="myModal-cx" class="reveal-modal">
		<div style="overflow: hidden">
			<h2>尊敬的<span id="realName-cx"></span></>用户：</h2>
			<div class="jindu"></div>
			<div class="jindu-text"></div>
		</div>
		<div id="errorBox" style="margin: 0px auto; width: 80%;" >
			<input name="sub" type="button" value="您的贷款正在处理，请联系客服！"
				style="background: #F60; color: #FFF; font-size: 18px; height: 32px; line-height: 32px; border: 0px; width: 100%;" />
		</div>
		<a class="close-reveal-modal">×</a>
	</div>

	<!-- 引用底部公共部分-->
	<jsp:include page="../include/footer.jsp"></jsp:include>
	<script type="text/javascript">
	$(function () {
	/* 	var session ="${user}";
		if(!session.length) {
			$(".focus-img").append('<p><em>请登录后，再填写以下内容</em></p>');	
			$("input").attr("disabled","disabled");
			return false;
		}  */
	});
		var loanDetail = {};
		loanDetail.checkUserDetail = function() {
			var realName = $("input[name='user.realName']").val();
			var idNo = $("input[name='user.idNo']").val();
			var mobile = $("input[name='user.mobile']").val();
			var income = $("input[name='user.income']").val();
		 	var company = $("#company").val();
			var dept = $("#dept").val();
			
			if (company == "" || company==0 ) {
				alert("请选择所在公司!");
				return false;
			 }
			if (dept == "" || dept.length==0) {
				alert("请输入部门信息!");
				 return false;
			 } 
			if (realName == null || realName == "") {
				alert("真实姓名不能为空!");
				return false;
			}
			var re = /^[\u4E00-\u9FA5]{2,5}$/ig;
			if (!re.test(realName)) {
				alert("真实姓名只能为2到5中文字符!");
				return false;
			}
			if (idNo == null || idNo == "") {
				alert("身份证号不能为空!");
				return false;
			}
			if (!isIdCardNo(idNo)) {
				alert("身份证号格式有误!");
				return false;
			}
			if (mobile == null || mobile == "") {
				alert("手机号码不能为空!");
				return false;
			}
			re = /^1{1}[3,4,5,7,8]{1}\d{9}$/gi;
			if (!re.test(mobile)) {
				alert("手机号码格式有误!");
				return false;
			}
			if (income == null || income == "") {
				alert("月收入不能为空!");
				return false;
			}
			var re_1 = /^[-\+]?\d+$/;
			var re_2 = /^[-\+]?\d+(\.\d+)?$/;
			if (!re_1.test(income) && !re_2.test(income)) {
				alert("月收入只能为数字!");
				return false;
			}
			$("input[name='realName']").val(realName);
			$("input[name='idNo']").val(idNo);
			$("input[name='mobile']").val(mobile);
			$("input[name='income']").val(income);
			$('#remark').val(company+":"+dept);
			return true;
		};

		loanDetail.calCreditAmount = function() {
			var income = $("input[name='user.income']").val();
			var loanPeriod = $("select[name='loanPeriod']").find("option:selected").val();
			$.get("calCreditAmountGroup.do", {
				income : income,
				loanPeriod : loanPeriod
			}, function(result) {
				if (!result.error) {
					$("#creditAmountTip").text(result.creditAmount);
				} else {
					alert(result.errorMessage);
				}
			});
		};

		loanDetail.calRepayDetail = function() {
			var loanAmount = $("input[name='loanAmount']").val();
			if (loanAmount == "" || loanAmount <= 0) {
				return;
			}
			var loanPeriod = $("select[name='loanPeriod']").find("option:selected").val();
			$.get("calRepayDetailGroup.do",{loanAmount : loanAmount,loanPeriod : loanPeriod},
					function(jsonArray) {
						$("#repayDetailTab").find("tr:not(:first)").remove();
						var totalAmount = 0.00;
						for (var i = 0; i < jsonArray.length; i++) {
							$('#repayDetailTab tr:last').after('<tr><td style="border-bottom: 1px solid #CCC">第'+ jsonArray[i].times+ '月</td><td style="border-bottom: 1px solid #CCC">'+ jsonArray[i].principal+ '</td><td style="border-bottom: 1px solid #CCC">'+ jsonArray[i].interest+ '</td><td style="border-bottom: 1px solid #CCC">'+ jsonArray[i].repayAmount+ '</td></tr>');
							totalAmount = jsonArray[i].totalAmount;
						}
						$('#repayDetailTab tr:last').after('<tr><td colspan="4" align="right">总共应还&nbsp;'+totalAmount+'&nbsp;元</td></tr>');
						});
		};
		
		loanDetail.success = false;
		$.ajaxSetup({ 
		    async : false 
		});
		loanDetail.getLoanStatus = function(){
			var idNo = $("input[name='idNo']").val();
			if(idNo == '请输入身份证号码进行查询' || idNo == "" || (idNo.length != 15 && idNo.length != 18)){
				alert('请输入身份证号码进行查询');
				return false;
			}
			if (!isIdCardNo(idNo)) {
				alert("身份证号格式有误!");
				return false;
			}
			$.get("getByIdNoGroup.do", {
				idNo : idNo
			}, function(result) {
				if (!result.error) {
					$("#realName-cx").text(result.realName);
					if(result.loanStatus == 'PENDING'){
						$(".jindu").html('<ul><li class="jj">申请</li><li class="jz"></li><li class="jj">审批</li><li class="jn"></li><li class="jnn">成功</li><li class="jn"></li><li class="jnn">放款</li></ul>');
						$("#errorBox").attr("style","display:none;");
					} else if(result.loanStatus == 'AUDITED'){
						$(".jindu").html('<ul><li class="jj">申请</li><li class="jz"></li><li class="jj">审批</li><li class="jz"></li><li class="jj">成功</li><li class="jn"></li><li class="jnn">放款</li></ul>');
						$("#errorBox").attr("style","display:none;");
					} else if(result.loanStatus == 'SUBJECTED'){
						$(".jindu").html('<ul><li class="jj">申请</li><li class="jz"></li><li class="jj">审批</li><li class="jz"></li><li class="jj">成功</li><li class="jz"></li><li class="jj">放款</li></ul>');
						$("#errorBox").attr("style","display:none;");
					} else {
						$(".jindu").html('<ul><li class="jj">申请</li><li class="jz"></li><li class="jj">审批</li><li class="jn"></li><li class="jnn">成功</li><li class="jn"></li><li class="jnn">放款</li></ul>');
						$("#errorBox").attr("style","display:block;");
					}
					$(".jindu-text").html('<ul><li class="txt">'+result.applyDate+'</li><li class="zw"></li><li class="txt">'+result.auditDate+'</li><li class="zw"></li><li class="txt">'+result.auditDate+'</li><li class="zw"></li><li class="txt">'+result.loanDate+'</li></ul>');
					loanDetail.success = true;
					return ;
				} else {
					alert(result.message);
					loanDetail.success = false;
					return;
				}
			});
			return (loanDetail.success?true:false);
		}
		
		$("input[name='idNo']").bind("focus", function() {
			if($(this).val() == '请输入身份证号码进行查询'){
				$(this).val("");
			}
		});
		
		$("input[name='idNo']").bind("blur", function() {
			var idNo = $(this).val();
			if(idNo == ""){
				$(this).val("请输入身份证号码进行查询");
			}
		});

		//借款金额失去焦点事件
		$("input[name='loanAmount']").bind("blur", function() {
			loanDetail.calRepayDetail();
		});

		//借款期限项变更事件
		$("select[name='loanPeriod']").bind("change", function() {
			loanDetail.calRepayDetail();
			loanDetail.calCreditAmount();
		});

		$("#btnSubmit").bind("click", function() {
			var loanAmount = $("input[name='loanAmount']").val();
			if (loanAmount == "" || loanAmount <= 0) {
				alert("借款金额有误!");
				return false;
			}
			var maxLoanAmount = parseFloat($("#creditAmountTip").text());
			if (loanAmount > maxLoanAmount) {
				alert("您的最多可以借贷金额为" + maxLoanAmount + "元!");
				return false;
			}
			if($("#chkAgreement1").attr("checked") != 'checked'){
				alert("请先已阅读并同意[合和年在线借款协议]!");
				return false;
			}
			if($("#chkAgreement2").attr("checked") != 'checked'){
				alert("请先已阅读并同意[借款咨询服务协议]!");
				return false;
			}
			$("#loanDetail").submit();
		});
		
		
	</script>
</body>

</html>
