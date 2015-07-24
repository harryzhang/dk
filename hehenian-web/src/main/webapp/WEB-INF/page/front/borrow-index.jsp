<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
<script type="text/javascript">
	$(function() {
		hhn(2);
		$(".header_two_right_ul li").hover(function() {
			$(this).find("ul").show();
		}, function() {
			$(this).find("ul").hide();
		})
	})
	
	
function reg(){
	if(${session.user!=null}){
		alert("你已是登录用户");
	}else{
		window.location.href="/account/reg.do";
	}
}
</script>
</head>
<body>
	<!-- 引用头部公共部分 -->
	<jsp:include page="/include/top.jsp"></jsp:include>

	<div class="center">
		<div class="wytz_center">
			<div class="wytz_center_onez">
				<s:if test="#request.username == ''"><a href="javascript:isReg()" class="wytz_a1z">注册合和年在线</a></s:if>
				<s:else><a  href="javascript:reg()" id="is_reg"  class="wytz_a1z">注册合和年在线</a></s:else>
				<a href="owerInformationInit.do" class="wytz_a2z">完善个人信息</a> 
				<a href="registerChinaHHN.do" class="wytz_a2" style="margin-left: 34px;">开设第三方托管账户</a> 
					<a href="borrow.do" class="wytz_a5z">发布借款需求</a>
				<a href="javascript:void(0)" class="wytz_a4z">审批借款</a> 
			</div>
			<div class="cle"></div>
			<div class="wyjb_two">
			
				<div class="wyjb_twoa">
					<div class="wyjb_twoan_top">
			<img src="images/xing.png" /><span class="wyjb_twoan_top_span1">信用借款</span>
					</div>
					<div class="wyjb_twoan_bot">
						<p>信用借款是以个人的信用为担保，免抵押、免担保的小额个人信用贷款。借款人需按照求完善个人信息，通过合和年在线的严格审核后在平台发布借款信息。主要对象为18-55周岁有稳定收入，具备较强偿还能力的中国公民。</p>
						<input type="button" value="立即申请" class="wyjb_twoan_put1" onclick="location.href='addBorrowInit.do?t=3'" />
					</div>
					<div class="wyjb_twoan"></div>
				</div>
				
				<div class="wyjb_twoa">
					<div class="wyjb_twoan_top">
				<img src="images/jing.png" />  <span class="wyjb_twoan_top_span2">净值借款</span>
					</div>
					<div class="wyjb_twoan_bot">
						<p>借款人以其在合和年在线的的净资产总额为担保，按照一定比例作为借款额度申请的借款。这是一种安全系数高的借款项目，因此借款利率较低。净值标通常用于临时周转，使您的资金利用率最大化。</p>
			<!-- 			<input type="button" value="立即申请" class="wyjb_twoan_put2" onclick="location.href='addBorrowInit.do?t=1'" />  -->
						<input type="button" value="暂未开通" class="wyjb_twoan_put2" onclick="javascript:void(0)" />
					</div>
					<div class="wyjb_twoan"></div>
				</div>
				
				<div class="wyjb_twoa">
					<div class="wyjb_twoan_top">
				<img src="images/cai.png" />  <span class="wyjb_twoan_top_span">社区金融服务</span>
					</div>
					<div class="wyjb_twoan_bot">
						<p>合和年在线合作社区中住户，无需抵押不动产，只需通过合和年在线个人信息、不动产信息确认即可发布借款需求。这是一种安全系数较高、周期短、操作简易的借款。</p>
					<!-- 	<input type="button" value="立即申请" class="wyjb_twoan_put"  />   -->
						<input type="button" value="暂未开通" class="wyjb_twoan_put"  /> 
					</div>
					<div class="wyjb_twoan"></div>
				</div>
				
				
				<div class="cle"></div>
			</div>
			
			<div class="wyjb_three_all">
				<div class="wyjb_three">
					<span>您当前的信用级别：</span>
					<s:if test="#request.creditLevel==null || #request.creditLevel==0">
						<img src="images/xy_aa.jpg"/>
					</s:if>
					<s:else>
							<img src="images/ico_13.jpg" style="margin: 0; padding: 0; vertical-align: -5px;" title="${request.credit}分" />
					</s:else>
				</div>
			</div>
		</div>
	</div>
	<!-- 引用底部公共部分 -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
</html>
