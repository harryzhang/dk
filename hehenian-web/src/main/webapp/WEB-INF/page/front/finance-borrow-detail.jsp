<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="/include/taglib.jsp"%>
<%@taglib prefix="hhn" uri="http://www.hehenian.com" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</meta>
<link rel="stylesheet" href="css/jbox/Gray/jbox.css" type="text/css"></link>

<link href="css/lightbox/lanrenzhijia.css" type="text/css" rel="stylesheet" />
<script type="text/javascript">
	$(function() {
		hhn(1);
		$(".header_two_right_ul li").hover(function() {
			$(this).find("ul").show();
		}, function() {
			$(this).find("ul").hide();
		})

		$(".nd_bdxq_jkxq_three_ul li:first").addClass("jdd")
		$(".nd_bdxq_jkxq_three_ul li").click(function() {
			var s = $(".nd_bdxq_jkxq_three_ul li").index(this);
			$(".nd_bdxq_jkxq_three_ul li").removeClass("jdd");//失去焦点
			$(this).addClass("jdd");//设为焦点
			$(".nd_bdxq_jkxq_three_a").hide();
			$(".nd_bdxq_jkxq_three_a").eq(s).show();
		})
		$(".jbcr_a").click(function() {
			$(".jb_tac").css("display", "block");
			$(".jb_tac").css("z-index", "10");
		})
		$(".jbcr_xxa").click(function() {
			$(".jb_tac").hide();
		})
		$(".l_jkxq_ljtb").click(function() {
			if ($(".l_jkxq_ljtb_dh").is(":hidden")) {
				$(".l_jkxq_ljtb_dh").show();
			} else
				($(function() {
					$(".l_jkxq_ljtb_dh").hide();
				}))
		})

	})

	//鼠标焦点
	$(function() {
		$("#amount333").focus(function() {
			$(this).addClass("focus");
			if ($(this).val() == this.defaultValue) {
				$(this).val("");
			}
		}).blur(function() {
			$(this).removeClass("focus");
			if ($(this).val() == '') {
				$(this).val(this.defaultValue);
			}
		});
		$("#amount").focus(function() {
			$("#amountSpan").show();
			$("#amountSpan").html("<font style='color:#F07A05'>请填写100的倍数</font>");
			$("#income").html("");
		});
	})

	//计算收益
	function monthRate() {
		var amount = $("#amount").val();
		var monthRate = $("#monthRate").val();
		var deadlinehhn = $("#deadlinehhn").val();
		if (isNaN(amount)) {
			$("#amountSpan").show();
			$("#amountSpan").html("<font style='color:red'>请输入数字，如：100,200</font>");
			return false;
		}
		if (amount <= 0) {
			$("#amountSpan").show();
			$("#amountSpan").html("<font style='color:red'>请输入100的倍数，如：100,200</font>");
			return false;
		}
		if (Number(amount) % 100 != 0 || amount < 100) {
			$("#amountSpan").show();
			$("#amountSpan").html("<font style='color:red'>请输入正确数字，如：100,200</font>");
			$("#income").hide();
			return false;
		} else {
			$("#income").show();
			$("#amountSpan").hide();
		}
		var annualRate = $("#annualRate").val();
		var deadline = "${borrowDetailMap.deadline}";
		if (null != amount) {
			$("#amountSpan").hide();
			$("#income").show();
			amount = Number(amount);
			shouyi = (amount + amount / deadline) / 2 * deadline * (annualRate / 1200);
			$("#income").html("到期收益约：" + parseFloat(shouyi).toFixed(2) + "元");
		}
	}
	//举报关闭DIV
	function jubao() {
		$("#jubao").hide();
	}
</script>
<style type="text/css">
table {
	font-size: 13px
}
#box_relative {
	position: absolute;
	left: 920px;
	top: 310px;
	z-index: 5
}
</style>
</head>
<body>
<div style="position: absolute; height:54px; line-height:54px; background:#f3f3f3; width:100%; margin-top:151px !important; margin-top:177px;border-bottom:1px solid #e5e5e5;border-top:1px solid #e5e5e5;"></div>
<jsp:include page="/include/top.jsp"></jsp:include>
<div class="center" style=" width:1140px;">
  <div class="wytz_center" style=" margin-bottom:30px;">
    <div class="mjd_tzlc_all">
      <ul>
        <li id="tab_1" class="tzlc"><a href="finance.do" style=" color:#666">返回列表</a></li>
        <%--<li id="tab_3">正在转让的债权</li>--%>
      </ul>
    </div>
  </div>
  <div class="toubiao"> 
    <!--标内容-->
    <div class="toubiao_nr">
      <div class="toubiao_nr_one">
        <div style=" font-size:18px; margin-bottom:20px;">
          <%--<s:property value="#request.borrowDetailMap.username   " default="---" />--%>
          <s:property value="#request.borrowDetailMap.borrowTitle" default="---" />
          <br />
          <font size="-2" color="#999999">编号：${borrowDetailMap.number}</font> </div>
        <div>
          <ul>
            <li style=" margin-bottom:20px;">目前总投标金额：<br />
              <span  style=" font-size:14px; color:#090">￥</span> <span  style=" font-size:32px;color:#090">${borrowDetailMap.hasInvestAmount}</span></li>
            <li>剩余投标金额：<br />
              <span class="red" style=" font-size:14px;">￥</span> <span class="red" style=" font-size:32px;">${borrowDetailMap.investAmount}</span></li>
          </ul>
        </div>
      </div>
      <div  class="toubiao_nr_two">
        <ul >
          <li>
            <input id="annualRate" value="${borrowDetailMap.annualRate}" type="hidden">
            <img src="images/new/1.gif"  style=" float:left; margin-top:6px;" /><span>借款金额：</span>￥
            <s:property
															value="#request.borrowDetailMap.borrowAmount" default="---" />
          </li>
          <li><img src="images/new/3.gif"  style=" float:left; margin-top:6px;" /><span>年利率：</span>
            <s:property value="#request.borrowDetailMap.annualRate" default="---" />
            %
            <input type="hidden" value="${borrowDetailMap.monthRate}" id="monthRate" />
          </li>
          <li><img src="images/new/2.gif" style=" float:left; margin-top:6px;" /><span>借款期限：
            <s:property value="#request.borrowDetailMap.deadline" default="---" />
            <s:if test="%{#request.borrowDetailMap.isDayThe ==1}">个月</s:if>
            <s:else>天</s:else>
            </span>
            <input id="deadlinehhn" value="${borrowDetailMap.deadline}" type="hidden">
          </li>
          <li><img src="images/new/tt.gif" style=" float:left" />
              <span>还款方式：
                  <hhn:paymentMode paymentModeId="${borrowDetailMap.paymentMode}"></hhn:paymentMode>
              <%--<c:choose>
                  <c:when test="${borrowDetailMap.paymentMode == 1}">等额本金</c:when>
                  <c:when test="${borrowDetailMap.paymentMode == 2}">先息后本</c:when>
                  <c:when test="${borrowDetailMap.paymentMode == 3}">按月付息到期还本</c:when>
                  <c:when test="${borrowDetailMap.paymentMode == 4}">等额本息</c:when>
                  <c:when test="${borrowDetailMap.paymentMode == 5}">等额本金</c:when>
                  <c:when test="${borrowDetailMap.paymentMode == 6}">等额本金</c:when>
                  <c:when test="${borrowDetailMap.paymentMode == 7}">按月付息到期还本</c:when>
                  <c:otherwise>等额本金</c:otherwise>
              </c:choose>--%>
            </span></li>
          <!--    <li><img src="images/new/tt.gif" style=" float:left" /><span>交易类型：
            <s:if test="%{#request.borrowDetailMap.tradeType == 1}">线上交易</s:if>
            <s:elseif test="%{#request.borrowDetailMap.tradeType ==2}">线下交易</s:elseif>
            </span></li>--> 
          <!--  <li><img src="images/new/tt.gif" style=" float:left" /><span>投标奖励：
            <s:if test="%{#request.borrowDetailMap.excitationType == 1}">无</s:if>
            <s:elseif
														test="%{#request.borrowDetailMap.excitationType ==2}">按固定金额</s:elseif>
            <s:elseif test="%{#request.borrowDetailMap.excitationType ==3}">按借款金额比例</s:elseif>
            </span></li>-->
          <li><img src="images/new/tt.gif" style=" float:left" /><span>已投人数：
            <s:property value="#request.borrowDetailMap.investNum" default="0" />
            人</span></li>
          <li><img src="images/new/tt.gif" style=" float:left" /><span>剩余时间：<span id="remainTimeOne"></span></span>
            <div id="remainSeconds" style="display: none">${borrowDetailMap.remainTime}</div>
          </li>
        </ul>
      </div>
      <!--投标栏-->
      <div  class="toubiao_nr_three" style="padding-top:20px;"> 
      
        <div style=" width:450px; height:50px; line-height:32px;"> <span style="width:235px; text-align:right; display:block; float:left; padding-right:10px;">您的可用余额： </span><strong style="float:left;">${userMap.usableSum}</strong> <span style="float:left; padding-left:10px;">元</span> <a href="rechargeInit.do" style="float:left; margin-left:15px;">我要充值</a> <span style="color: #666666;"> </span> </div>
        
        <!--投标金额-->
        <div style=" width:450px; height:70px;"><span style="width:235px; text-align:right; display:block; float:left; padding-right:10px; line-height:35px;">投标金额：</span>
          <input ${borrowDetailMap.borrowStatus == 2?'':'disabled'} id="amount" name="paramMap.amount" maxlength="20" type="text" title="100≥,≤${borrowDetailMap.investAmount}"  style=" width:200px; height:32px; line-height:32px; font-size:22px; font-family:'Merriweather','Microsoft YaHei'; border:1px solid #CCC; float:left;"/>
          <br />
          <span id="amountSpan" style="font-size: 14px; line-height:26px; padding-top:10px;"></span><span id="income" style="color: red;font-size: 14px; line-height:26px;"></span> </div>
        <!--验证码-->
        <!--
        <div  style=" width:450px; float:left"><span style=" margin-top: 5px;"> 验 证 码：
          <input ${borrowDetailMap.borrowStatus == 2?'':'disabled'} type="text" name="paramMap.code" id="code" style=" width:200px; height:32px; line-height:32px; font-size:22px; font-family:'Merriweather','Microsoft YaHei'; border:1px solid #CCC"/>
          <span id="codes"></span> </span><br />
          <a href="javascript:void()" onclick="switchCode(this)" style="float: right; margin-top:10px;" id="a1"><img src="admin/imageCode.do?pageId=userregister" title="点击更换验证码" id="codeNum1" />看不清？换一换</a></div>
          -->
        
        <form id="f_investBorrow" target="_blank" action="borrowInvest.do" method="post" >
          <input type="hidden" id="int_id" name="paramMap.id" />
          <input type="hidden" id="int_code" name="paramMap.code" />
          <input type="hidden" id="int_amount" name="paramMap.amount" />
          <input type="hidden" id="int_usrCustId" name="paramMap.usrCustId" />
        </form>
        <input type="hidden" value="${borrowDetailMap.id }" id="id"
												name="id" />
        <!--投标按钮-->
        <div  style=" width:450px; float:left; margin-top:5px;">
          <div style=" float:right; margin-left:12px;"><a class="btn btn-default" href="javascript:" data-featherlight="#fl2" data-featherlight-variant="fixwidth"><img src="images/v1/jsq.jpg" width="32" height="46"  alt="收益计算"/></a> </div>
          <div class="lightbox" id="fl2">
            <div style=" text-align:center; padding-bottom:20px;"><h2>收益计算器</h2></div>
            <div style=" text-align:center;">
            
      
    <table width="100%" border="0">
      <tbody><tr height="50">
        <td width="120" align="right"><span>投标金额：</span></td>
        <td><input type="text" id="borrowSum" style=" width:280px; height:40px; border:1px solid #CCC; padding-left:20px; line-height:40px;">&nbsp;元</td>
      </tr>
      <tr>
        <td width="120" align="right" style="padding-top:10px;"><span>年利率：</span></td>
        <td><input type="text" id="yearRate" style=" width:280px; height:40px; border:1px solid #CCC; padding-left:20px; margin-top:10px; line-height:40px;">
          %</td>
      </tr>
      <tr height="50">
        <td width="120" align="right" style="padding-top:10px;"><span>计息天数：</span></td>
        <td><input type="text" id="borrowTime" style=" width:280px; height:40px; border:1px solid #CCC; padding-left:20px;margin-top:10px; line-height:40px;">&nbsp;天</td>
      </tr>
     
      <tr height="50">
        <td colspan="2" align="center"><input type="button" value="计算" onclick="rateCalculate()" style=" background:#F60; color:#FFF; width:120px; height:32px; line-height:32px; margin-top:10px; border:0px;"></td>
      </tr>
      <tr>
        <td colspan="2"><strong><span style="color: #FF3; float: none;" class="formtips" id="show_error"> </span> </strong></td>
      </tr>
    </tbody></table>
    <span id="showIncome" style="color:#FF3; "></span> </div>
            
         
          </div>
          <script src="css/lightbox/jquery.min.js"></script>
<script src="css/lightbox/featherlight.js"></script>
          <%--									<input type="button" value="立即投标" class="l_jkxq_ljtb" />--%>
          <span id="hhnInvest">
          <s:if test="#session.DEMO==2">
            <%--<s:if test="%{#request.wStatus != ''}">
												<img src="images/tubiao4.png" width="97" height="31" class="l_jkxq_ljtb" />&nbsp;
											</s:if>--%>
            <s:if test="%{#request.borrowDetailMap.borrowStatus == 3}"> <img src="images/neiye2_13.jpg" width="97" height="31" class="l_jkxq_ljtb" />&nbsp; </s:if>
            <s:elseif test="%{#request.borrowDetailMap.borrowStatus == 4}">
              <input class="l_jkxq_ljtbhk" type="button" value="还款中" />
              &nbsp; </s:elseif>
            <s:elseif test="%{#request.borrowDetailMap.borrowStatus == 5}"> <img src="images/neiye1_637.jpg" width="97" height="31" class="l_jkxq_ljtb" />&nbsp; </s:elseif>
            <s:elseif test="%{#request.borrowDetailMap.borrowStatus == 1}"> <img src="images/neiye2_15.jpg" width="97" height="31" class="l_jkxq_ljtb" />&nbsp; </s:elseif>
            <s:elseif test="%{#request.borrowDetailMap.borrowStatus == 7}"> <img src="images/neiye2_16.jpg" width="97" height="31" class="l_jkxq_ljtb" />&nbsp; </s:elseif>
            <s:elseif test="%{#request.borrowDetailMap.remainTime <= 0}"> <img src="images/neiye2_18.jpg" width="97" height="31" class="l_jkxq_ljtb" />&nbsp; </s:elseif>
            <s:elseif test="%{#request.borrowDetailMap.borrowStatus == 2 }"> <a href="javascript:void(0);" id="invest">
              <input class="l_jkxq_ljtb" type="button" value="立即投标" />
              </a> </s:elseif>
            <s:else> <img src="images/neiye2_18.jpg" width="97" height="31" class="l_jkxq_ljtb" />&nbsp; </s:else>
          </s:if>
          <s:else>
            <s:if test="%{#request.borrowDetailMap.borrowStatus == 3}"> <img src="images/neiye2_13.jpg" width="97" height="31" class="l_jkxq_ljtb" />&nbsp; </s:if>
            <s:elseif test="%{#request.borrowDetailMap.borrowStatus == 4}">
              <input class="l_jkxq_ljtbhk" type="button" value="还款中" />
              &nbsp; </s:elseif>
            <s:elseif test="%{#request.borrowDetailMap.borrowStatus == 5}"> <img src="images/neiye1_637.jpg" width="97" height="31" class="l_jkxq_ljtb" />&nbsp; </s:elseif>
            <s:elseif test="%{#request.borrowDetailMap.borrowStatus == 1}"> <img src="images/neiye2_15.jpg" width="97" height="31" class="l_jkxq_ljtb" />&nbsp; </s:elseif>
            <s:elseif test="%{#request.borrowDetailMap.borrowStatus == 7}"> <img src="images/neiye2_16.jpg" width="97" height="31" class="l_jkxq_ljtb" />&nbsp; </s:elseif>
            <s:elseif test="%{#request.borrowDetailMap.remainTime <= 0}"> <img src="images/neiye2_18.jpg" width="97" height="31" class="l_jkxq_ljtb" />&nbsp; </s:elseif>
            <s:elseif test="%{#request.borrowDetailMap.borrowStatus == 2 }">
              <input class="l_jkxq_ljtb" id="invest" type="button" value="立即投标" />
            </s:elseif>
            <s:else> <img src="images/neiye2_18.jpg" width="97" height="31" class="l_jkxq_ljtb" />&nbsp; </s:else>
          </s:else>
          </span> </div>
        <div style=" width:450px; float:right; margin-top:10px;">
          <input type="checkbox" checked="checked" id="xieyi" value="1" style="margin:0px;vertical-align: middle;" />
          <a style="margin:0px;vertical-align: middle;" href="../agreement.do?borrowId=${id}">同意《借款协议》</a> 
          </div>
      </div>
    </div>
    
    <!--投标进度 -->
    <div class="toubiao_jd">
      <div style=" width:800px;height:30px; background:url(../images/new/jindu1.png) repeat-x;background-position-y:bottom; float:left">
        <div class="nd_bdxq_jkxq_b_a_right_next_two_ac">
          <s:if test="pageBean.page!=null || pageBean.page.size>0">
          <s:iterator value="pageBean.page" var="bean" status="s">
            <div class="<s:if test="#s.count%4==0">hhn_jdt_a
              </s:if>
              <s:if test="#s.count%4==1">hhn_jdt_b</s:if>
              <s:if test="#s.count%4==2">hhn_jdt_c</s:if>
              <s:if test="#s.count%4==3">hhn_jdt_d</s:if>
              "
              title="(${bean.investTime })会员${bean.username }借出${bean.investAmount}元 ，占总投资额的${bean.percent }%" style="width: ${bean.percent }%;">${bean.percent }%</div>
          </s:iterator>
          </s:if>
        </div>
      </div>
      <div style=" float:right; margin-top:10px;"> <span>撮合进度：<strong> <font color="#FF6600" size="+1">
        <s:property value="#request.borrowDetailMap.schedules" default="0" />
        %</strong> </span> </font></div>
    </div>
  </div>
  
  <!--详细信息栏-->
  <div class="toubiao_left">
    <div class="toubiao_left_a">
      <div>
        <p>相关信息</p>
      </div>
      <div style=" margin:30px 30px 15px 30px; border:1px solid #e5e5e5; background:#f9f9f9 ; line-height:26px; padding:20px;"> 以下基本信息资料，经用户同意披露。其中 <span class="red"> 红色字体 </span>的信息，为通过网站审核之项。<br />
        审核意见：<span class="red">网站将以客观、公正的原则，最大程度地核实借入者信息的真实性，但不保证审核信息100%真实。如果借入者长期逾期，其提供的信息将被公布。</span> </div>
      <div style="    overflow:hidden; margin-left:30px;"><img src="images/new/neir.png" style="float:left; margin-right:5px;" /><strong>基本信息</strong> </div>
      <div style=" margin:0px 30px 0px 30px;  line-height:26px; padding:5px 20px 15px 20px; overflow:hidden">
        <ul style=" width:50%; float:left">
          <li>性别：
            <s:if test="#request.borrowUserMap.auditperson == 3"> <strong>${borrowUserMap.sex}</strong> </s:if>
            <s:else>${borrowUserMap.sex}</s:else>
          </li>
          <li>年龄：
            <s:if test="#request.borrowUserMap.auditperson == 3"> <strong>${borrowUserMap.age}</strong> </s:if>
            <s:else>${borrowUserMap.age}</s:else>
          </li>
          <li>学历：
            <s:if test="#request.borrowUserMap.auditwork == 3"> <strong>${borrowUserMap.highestEdu}</strong> </s:if>
            <s:else>${borrowUserMap.highestEdu}</s:else>
          </li>
          <li>工作收入：
            <s:if test="#request.borrowUserMap.auditwork == 3"> <strong>${borrowUserMap.monthlyIncome}</strong> </s:if>
            <s:else>${borrowUserMap.monthlyIncome}</s:else>
          </li>
        </ul>
        <ul style=" width:50%; float:left">
          <li>公司名称： <span>
            <strong>${hhn:titleHidden(request.orgName,5)}</strong>
            </span></li>
          <li>公司行业： <span >
            <s:if test="#request.borrowUserMap.auditwork == 3"> <strong>${borrowUserMap.companyLine}</strong> </s:if>
            <s:else>${borrowUserMap.companyLine}</s:else>
            </span></li>
          <li>职位： <span>
            <s:if test="#request.borrowUserMap.auditwork == 3"> <strong>${borrowUserMap.job}</strong> </s:if>
            <s:else>${borrowUserMap.job}</s:else>
            </span></li>
          <li>现单位工作时间：<span>
            <s:if test="#request.borrowUserMap.auditwork == 3"> <strong>${borrowUserMap.workYear}</strong> </s:if>
            <s:else>${borrowUserMap.workYear}</s:else>
            </span></li>
        </ul>
      </div>
      <div style=" overflow:hidden; margin-left:30px;"><img src="images/new/neir.png" style="float:left;margin-right:5px;" /><strong>借款信息</strong> </div>
      <div style=" margin:0px 30px 0px 30px;  line-height:26px; padding:5px 20px 20px 20px; overflow:hidden">
        <ul style=" width:50%; float:left">
          <li>发布借款标：
            <s:if test="#request.borrowRecordMap.publishBorrow !=''"><strong>${borrowRecordMap.publishBorrow}</strong></s:if>
            <s:else><strong>0</strong></s:else>
          </li>
          <li>成功借款笔数：
            <s:if test="#request.borrowRecordMap.successBorrow !=''"><strong>${borrowRecordMap.successBorrow}</strong></s:if>
            <s:else><strong>0</strong></s:else>
          </li>
          <li>还清笔数：
            <s:if test="#request.borrowRecordMap.repayBorrow !=''"><strong>${borrowRecordMap.repayBorrow}</strong></s:if>
            <s:else><strong>0</strong></s:else>
          </li>
          <li>逾期次数：
            <s:if test="#request.borrowRecordMap.hasFICount !=''"><strong>${borrowRecordMap.hasFICount}</strong></s:if>
            <s:else><strong>0</strong></s:else>
          </li>
          <li>严重逾期次数：
            <strong>0</strong>
          </li>
        </ul>
        <ul style=" width:50%; float:left">
          <li>共借入：
            <s:if test="#request.borrowRecordMap.borrowAmount !=''"><strong>${borrowRecordMap.borrowAmount}</strong></s:if>
            <s:else><strong>0</strong></s:else>
          </li>
          <li>待还金额：
            <s:if test="#request.borrowRecordMap.forRepayPI !=''"><strong><strong>${borrowRecordMap.forRepayPI}</strong></strong></s:if>
            <s:else><strong>0</strong></s:else>
          </li>
          <li>逾期金额：
            <strong>0.00</strong>
          </li>
          <li>共借出：
            <s:if test="#request.borrowRecordMap.investAmount !=''"><strong>${borrowRecordMap.investAmount}</strong></s:if>
            <s:else><strong>0</strong></s:else>
          </li>
          <li>待收金额：
            <s:if test="#request.borrowRecordMap.forPI !=''"><strong>${borrowRecordMap.forPI}</strong></s:if>
            <s:else><strong>0</strong></s:else>
          </li>
        </ul>
      </div>
      <div> </div>
    </div>
    <div class="toubiao_left_b">
      <div>
        <p>审核信息</p>
      </div>
      <div style=" margin:30px; border:1px solid #e5e5e5; background:#f9f9f9 ; line-height:26px; padding:20px;"> 本站以公正、客观为基本原则，以客户资金安全为服务宗旨，尽最大限度的保证借款者的真实信息。如遇还款问题，由合和年风险备用金全额垫付本金及利息。以达到投资者投资无风险的最终目标。 </div>
      <div>
        <table width="768" border="0" cellpadding="0" cellspacing="1" bgcolor="#e5e5e5" style="margin-left:30px; margin-bottom: 15px;">
            <s:if test="%{#request.list !=null && #request.list.size()>0}">
            <tbody><tr height="32">
                <td width="280" align="center" bgcolor="#f9f9f9">审核项目</td>
                <td width="190" align="center" bgcolor="#f9f9f9">状态</td>
                <td width="180" align="center" bgcolor="#f9f9f9">认证时间</td>
                <td width="180" align="center" bgcolor="#f9f9f9">通过时间</td>
            </tr>
            <tr height="32">
                <td bgcolor="#ffffff" align="center">身份认证</td>
                <td bgcolor="#ffffff" align="center"><img src="images/neiye2_44.jpg" width="14" height="15"> </td>
                <td bgcolor="#ffffff" align="center">${list[0].passTime}</td>
                <td bgcolor="#ffffff" align="center">${list[0].passTime}</td>
            </tr>
            <tr height="32">
                <td bgcolor="#ffffff" align="center">工作认证</td>
                <td bgcolor="#ffffff" align="center"><img src="images/neiye2_44.jpg" width="14" height="15"> </td>
                <td bgcolor="#ffffff" align="center">${list[0].passTime}</td>
                <td bgcolor="#ffffff" align="center">${list[0].passTime}</td>
            </tr>
            <tr height="32">
                <td bgcolor="#ffffff" align="center">居住地认证</td>
                <td bgcolor="#ffffff" align="center"><img src="images/neiye2_44.jpg" width="14" height="15"> </td>
                <td bgcolor="#ffffff" align="center">${list[0].passTime}</td>
                <td bgcolor="#ffffff" align="center">${list[0].passTime}</td>
            </tr>
            <tr height="32">
                <td bgcolor="#ffffff" align="center">信用报告</td>
                <td bgcolor="#ffffff" align="center"><img src="images/neiye2_44.jpg" width="14" height="15"> </td>
                <td bgcolor="#ffffff" align="center">${list[0].passTime}</td>
                <td bgcolor="#ffffff" align="center">${list[0].passTime}</td>
            </tr>
            <tr height="32">
                <td bgcolor="#ffffff" align="center">收入认证</td>
                <td bgcolor="#ffffff" align="center"><img src="images/neiye2_44.jpg" width="14" height="15"> </td>
                <td bgcolor="#ffffff" align="center">${list[0].passTime}</td>
                <td bgcolor="#ffffff" align="center">${list[0].passTime}</td>
            </tr>
            <tr height="32">
                <td bgcolor="#ffffff" align="center">房产证</td>
                <td bgcolor="#ffffff" align="center"><img src="images/neiye2_44.jpg" width="14" height="15"> </td>
                <td bgcolor="#ffffff" align="center">${list[0].passTime}</td>
                <td bgcolor="#ffffff" align="center">${list[0].passTime}</td>
            </tr>
            <tr height="32">
                <td bgcolor="#ffffff" align="center">房屋租赁合同</td>
                <td bgcolor="#ffffff" align="center"><img src="images/neiye2_44.jpg" width="14" height="15"> </td>
                <td bgcolor="#ffffff" align="center">${list[0].passTime}</td>
                <td bgcolor="#ffffff" align="center">${list[0].passTime}</td>
            </tr>
            <tr height="32">
                <td bgcolor="#ffffff" align="center">水电单据</td>
                <td bgcolor="#ffffff" align="center"><img src="images/neiye2_44.jpg" width="14" height="15"> </td>
                <td bgcolor="#ffffff" align="center">${list[0].passTime}</td>
                <td bgcolor="#ffffff" align="center">${list[0].passTime}</td>
            </tr>
            <tr height="32">
                <td bgcolor="#ffffff" align="center">工作证明</td>
                <td bgcolor="#ffffff" align="center"><img src="images/neiye2_44.jpg" width="14" height="15"> </td>
                <td bgcolor="#ffffff" align="center">${list[0].passTime}</td>
                <td bgcolor="#ffffff" align="center">${list[0].passTime}</td>
            </tr>
            <tr height="32">
                <td bgcolor="#ffffff" align="center">银行流水</td>
                <td bgcolor="#ffffff" align="center"><img src="images/neiye2_44.jpg" width="14" height="15"> </td>
                <td bgcolor="#ffffff" align="center">${list[0].passTime}</td>
                <td bgcolor="#ffffff" align="center">${list[0].passTime}</td>
            </tr>
            <tr height="32">
                <td bgcolor="#ffffff" align="center">信用卡账单</td>
                <td bgcolor="#ffffff" align="center"><img src="images/neiye2_44.jpg" width="14" height="15"> </td>
                <td bgcolor="#ffffff" align="center">${list[0].passTime}</td>
                <td bgcolor="#ffffff" align="center">${list[0].passTime}</td>
            </tr>
            <tr height="32">
                <td bgcolor="#ffffff" align="center">车产证</td>
                <td bgcolor="#ffffff" align="center"><img src="images/neiye2_44.jpg" width="14" height="15"> </td>
                <td bgcolor="#ffffff" align="center">${list[0].passTime}</td>
                <td bgcolor="#ffffff" align="center">${list[0].passTime}</td>
            </tr>
            <tr height="32">
                <td bgcolor="#ffffff" align="center">社保</td>
                <td bgcolor="#ffffff" align="center"><img src="images/neiye2_44.jpg" width="14" height="15"> </td>
                <td bgcolor="#ffffff" align="center">${list[0].passTime}</td>
                <td bgcolor="#ffffff" align="center">${list[0].passTime}</td>
            </tr>
            <tr height="32">
                <td bgcolor="#ffffff" align="center">营业执照</td>
                <td bgcolor="#ffffff" align="center"><img src="images/neiye2_44.jpg" width="14" height="15"> </td>
                <td bgcolor="#ffffff" align="center">${list[0].passTime}</td>
                <td bgcolor="#ffffff" align="center">${list[0].passTime}</td>
            </tr>
            <tr height="32">
                <td bgcolor="#ffffff" align="center">租赁合同</td>
                <td bgcolor="#ffffff" align="center"><img src="images/neiye2_44.jpg" width="14" height="15"> </td>
                <td bgcolor="#ffffff" align="center">${list[0].passTime}</td>
                <td bgcolor="#ffffff" align="center">${list[0].passTime}</td>
            </tr>
            <tr height="32">
                <td bgcolor="#ffffff" align="center">其它资产证明</td>
                <td bgcolor="#ffffff" align="center"><img src="images/neiye2_44.jpg" width="14" height="15"> </td>
                <td bgcolor="#ffffff" align="center">${list[0].passTime}</td>
                <td bgcolor="#ffffff" align="center">${list[0].passTime}</td>
            </tr>
            <tr height="32">
                <td bgcolor="#ffffff" align="center">征信报告</td>
                <td bgcolor="#ffffff" align="center"><img src="images/neiye2_44.jpg" width="14" height="15"> </td>
                <td bgcolor="#ffffff" align="center">${list[0].passTime}</td>
                <td bgcolor="#ffffff" align="center">${list[0].passTime}</td>
            </tr>
            </tbody>
                </s:if>
            <s:else>

                <td colspan="4" align="center">没有数据</td>
            </s:else>
          <%--<tr height="32">
            <td width="280" align="center" bgcolor="#f9f9f9">审核项目</td>
            <td width="190" align="center" bgcolor="#f9f9f9">状态</td>
            <td width="180" align="center" bgcolor="#f9f9f9">认证时间</td>
            <td width="180" align="center" bgcolor="#f9f9f9">通过时间</td>
          </tr>--%>
          <%--<s:if test="%{#request.list !=null && #request.list.size()>0}">
            <s:iterator value="#request.list" id="bean">
              <tr height="32">
                <td bgcolor="#ffffff" align="center">${bean.name}</td>
                <td bgcolor="#ffffff" align="center"><s:if test="#bean.auditStatus == 1"> 审核中 </s:if>
                  <s:elseif test="#bean.auditStatus == 2"> 审核失败 </s:elseif>
                  <s:elseif test="#bean.auditStatus == 3"> <img src="images/neiye2_44.jpg" width="14" height="15" /> </s:elseif>
                  <s:else> 等待资料上传 </s:else></td>
                <td bgcolor="#ffffff" align="center">${bean.authenticationTime}</td>
                <td bgcolor="#ffffff" align="center">${bean.passTime}</td>
              </tr>
            </s:iterator>
          </s:if>
          <s:else>
            
              <td colspan="4" align="center">没有数据</td>
          </s:else>--%>
        </table>
      </div>
    </div>
    <div class="toubiao_left_c">
      <div style=" margin-bottom:30px; margin-top:45px;">
        <p>还款信息</p>
      </div>
      <div>
        <table width="768" border="0" cellpadding="0" cellspacing="1" bgcolor="#e5e5e5" style="margin-left:30px; margin-bottom: 45px;">
          <tr height="30">
            <th bgcolor="#f9f9f9" scope="col">期限</th>
            <th bgcolor="#f9f9f9" scope="col">预计还款日期</th>
            <th bgcolor="#faf9f9" scope="col">应还本金</th>
            <th bgcolor="#faf9f9" scope="col">应还利息</th>
            <th bgcolor="#faf9f9" scope="col">应还本息</th>
            <th bgcolor="#faf9f9" scope="col">状态</th>
          </tr>
          <s:if test="%{#request.pageBeanes.page==null || #request.pageBeanes.page.size==0}">
            <tr height="30">
              <td colspan="7" bgcolor="#ffffff" align="center">暂无数据</td>
            </tr>
          </s:if>
          <s:else>
            <s:set name="counts" value="#request.pageNum" />
            <s:iterator value="#request.pageBeanes" var="repay">
              <tr bgcolor="#ffffff">
                <td height="32" align="center">${repay.repayPeriod}</td>
                <td align="center">${repay.repayDate}</td>
                <td align="center">${repay.stillPrincipal}</td>
                <td align="center">${repay.stillInterest}</td>
                <td align="center">${repay.still}</td>
                <td align="center"><s:if test="#repay.repayStatus==1&&#repay.isWebRepay==1">未偿还</s:if>
                  <s:if test="#repay.repayStatus==2||#repay.isWebRepay==2"><font color="#009900">已偿还</font></s:if></td>
              </tr>
            </s:iterator>
          </s:else>
        </table>
      </div>
    </div>
  </div>
  
  <!--用户信息栏-->
  <div class="toubiao_right">
    <div class="toubiao_right_a">
      <div style=" width:90%; margin:0px auto;  border-bottom:1px dashed #CCCCCC; padding:20px 0px; font-size:18px; margin-bottom:20px;">借款人</div>
      <div style=" width:90%; margin:0px auto; "> 
        <!--<div style="float:left; padding-right:5px;"><shove:img src="${borrowUserMap.personalHead}" defaulImg="images/default-img.jpg" width="62" height="62"></shove:img></div>-->
        <div style="float:left; line-height:26px;">
          <p> <strong> 用户:
            <s:property value="#request.borrowUserMap.username.substring(0,2)+'***'" />
            </strong>&nbsp;&nbsp;&nbsp; <img src="images/vip.png"
									style="vertical-align: -5px; margin: 0; padding: 0;" />
            <input type="hidden" value="${borrowUserMap.username}" id="username" />
          </p>
          <p> <spaN>所在地：${borrowUserMap.address}</spaN> </p>
          <p> <spaN>最后登录时间：<br />
            ${borrowUserMap.lastDate}</spaN> </p>
        </div>
      </div>
    </div>
    <div class="toubiao_right_b">
      <div style=" width:90%; margin:0px auto;  border-bottom:1px dashed #CCCCCC; padding:20px 0px; font-size:18px;">投标情况</div>
      <div style=" width:90%; margin:0px auto; ">
        <div style=" width:50%; float:left; line-height:32px;"><strong>投资人</strong></div>
        <div style=" width:50%; float:left; text-align:right;line-height:32px;"><strong>投资金额</strong></div>
      </div>
      <div style=" width:90%; margin:0px auto;">
        <s:if test="%{#request.investList !=null && #request.investList.size()>0}">
          <s:iterator value="#request.investList" id="investList">
            <div style=" width:50%; float:left; line-height:32px; border-bottom:1px solid #e5e5e5"><a style="cursor: default;">
              <s:property
																value="#investList.username" default="---" />
              <!--   creditedStatus==2 代表该用户在转让债权 -->
              <s:if
																test="#investList.creditedStatus !=null && #investList.creditedStatus==2 "> <img src="images/zrico.jpg" width="30" height="16" /> </s:if>
              </a></div>
            <div style=" width:50%; float:left; text-align:right;line-height:32px; border-bottom:1px solid #e5e5e5"><span class="red">￥
              <s:property value="#investList.investAmount" />
              </span></div>
          </s:iterator>
        </s:if>
        <s:else>
          <div>没有数据</div>
        </s:else>
      </div>
    </div>
  </div>
</div>
<div class="cle"></div>
<div id="remainSeconds" style="display: none;">${borrowDetailMap.remainTime}</div>

<script type="text/javascript" src="css/popom.js"></script> 
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script> 
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script> 
<script type="text/javascript">
		$(function() {
			var param = {};
			$('#isLogin').click(function() {
				alert("您未登录,请先登录");
				return false;
			});

			$('#invest').click(function() {
				hhnflag = false;

				var xieyiok = Number($("#xieyi:checked").val());
				if (xieyiok != 1) {
					alert("请仔细阅读并同意借款协议!");
					return;
				}

				var step = "${session.user.authStep}";
				var username = "${borrowUserMap.username}";
				var uname = "${user.username}";
	<%--				var investPWD = $("#investPWD").val();--%>
		var invest = $("#invest").val();
				var code = $("#code").val();
				var amount = $("#amount").val();
				var id = $("#id").val();
				if (null != uname) {
					if (null == amount || amount == "") {
						$("#amountSpan").show();
						$("#amountSpan").html("<font style='color:red'>请输入交易金额</font>");
						return false;
					} else {
						$("#amountSpan").html("");
					}
					if (Number(amount) % 100 != 0 || amount < 100) {
						$("#amountSpan").show();
						$("#amountSpan").html("<font style='color:red'>请输入正确数字，如：100,200</font>");
						$("#income").hide();
						return false;
					} else {
						$("#income").show();
						$("#amountSpan").hide();
					}
	<%--					if (null == investPWD || investPWD == "") {--%>
		
	<%--						$("#pwdes").show();--%>
		
	<%--						$("#pwdes").html("<font style='color:red'>请输入交易密码</font>");--%>
		
	<%--						return false;--%>
		
	<%--					} else {--%>
		
	<%--						$("#pwdes").hide();--%>
		
	<%--					}--%>
		/*
		if (code == null || code == "") {
						$("#codes").show();
						$("#codes").html("<font style='color:red'>请输入验证码</font>");
						return false;
					} else {
						$("#codes").hide();
					}
		*/
					
					if (username == uname) {
						alert("无效操作,不能投自己发布的标");
						return false;
					}
					
					
					$('#int_id').val(id);
					$('#int_code').val(code);
					$('#int_amount').val(amount);
					$('#int_usrCustId').val($("#usrCustId").val());
					
				//	$('#invest').attr("disabled", true);
					window.location.href= "/borrowInvest.do?paramMap.amount="+amount+"&paramMap.id="+id;
					//$("#f_investBorrow").submit();
					
					/*
					$.shovePost('investBorrow.do', param, function(data) {
						if (data.length < 50) {
							alert(data);
							switchCodes();
							$('#invest').attr("disabled", false);
						} else {
							$("#insertttt").html(data);
						}
					});
					*/

				} else {
					alert("请先登录");
				}

			});

			//未登录用户
			$('#noLogin').click(function() {
				var username = '${borrowUserMap.username}';
				var uname = '${user.username}';
				if (null != uname && uname != "") {
					alert("您是登录用户");
					switchCodes();
					return false;
				}
				window.location.href = 'login.do';
			});

			//登录未认证用户
			$('#noAttestation').click(function() {
				window.location.href = 'userPassData.do';
			});

			$('#audit').click(function() {
				var id = $('#idStr').val();
				$(this).addClass('on');
				$('#repay').removeClass('on');
				$('#collection').removeClass('on');
				param['paramMap.id'] = id;
				$.shovePost('financeAudit.do', param, function(data) {
					$('#contentList').html(data);
				});
			});
			$('#repay').click(function() {
				var id = $('#idStr').val();
				$(this).addClass('on');
				$('#audit').removeClass('on');
				$('#collection').removeClass('on');
				param['paramMap.id'] = id;
				$.shovePost('financeRepay.do', param, function(data) {
					$('#contentList').html(data);
				});

			});
			$('#collection').click(function() {
				var id = $('#idStr').val();
				$(this).addClass('on');
				$('#audit').removeClass('on');
				$('#repay').removeClass('on');
				param['paramMap.id'] = id;
				$.shovePost('financeCollection.do', param, function(data) {
					$('#contentList').html(data);
				});

			});
			initListInfo(param);
		});

		function initListInfo(param) {
			param['paramMap.id'] = '${borrowDetailMap.id}';
			$.shovePost('borrowmessage.do', param, function(data) {
				$('#msg').html(data);
			});
		}
		function showImg(typeId, userId, ids) {
			var session = "${session.user}";
			if (session == 'null') {
				window.location.href = 'login.do';
				return;
			}
			var url = "showImg.do?typeId=" + typeId + "&userId=" + userId;
			$.jBox("iframe:" + url, {
				title : "查看认证图片(点击图片放大)",
				width : 500,
				height : 428,
				buttons : {}
			});
		}
		function close() {
			ClosePop();
		}

		//验证码
		function switchCode(e) {
			var timenow = new Date();
			if ($(e).attr("id") == "a1") {
				$("#codeNum1").attr("src", "admin/imageCode.do?pageId=userregister&d=" + timenow);
			} else {
				$("#codeNum2").attr("src", "admin/imageCode.do?pageId=userregister&d=" + timenow);
			}
		}
		function switchCodes() {
			var timenow = new Date();
			$('#codeNum1').attr('src', 'admin/imageCode.do?pageId=userregister&d=' + timenow);
		}

		function reportusers() {
			var id = '${borrowUserMap.userId}';
			var username = $("#username").val();
			var uname = '${user.username}';

			if (uname == '' || uname == null) {
				alert("您未登录,请先登录");
				return false;
			}
			if (username == uname) {
				alert("您不能举报自己");
				return false;
			}
			if ($('#title').val() == "") {
				alert("标题不能为空");
				return false;
			}
			if ($('#content').val() == "") {
				alert("内容不能为空");
				return false;
			}
			if ($('#cod').val() == "") {
				alert("验证码不能为空");
				return false;
			}
			param['paramMap.id'] = '${borrowUserMap.userId}';
			param['paramMap.title'] = $('#title').val();
			param['paramMap.content'] = $('#content').val();
			param['paramMap.code'] = $('#cod').val();
			$.shovePost("reportUserAdd.do", param, function(data) {
				if (data == "1") {
					$("#codSpan").html("<font style='color:red'>验证码输入错误</font>");
					$('#cod').focus();
					return false;
				}
				if (data == "3") {
					alert("举报成功");
					jubao();
				}
				if (data == "2") {
					alert("举报失败");
					return;
				}
			});

		}
		//根据ID查询还款详情
		function queryRepaymentById() {
			var id = $("#id").val();
			param['paramMap.id'] = $('#id').val();
			//$.shovePost("queryRepaymentById.do", param, function(data) {
			//});
		}

		//根据ID查询催收记录
		function queryCollectionById() {
			var id = $("#id").val();
			param['paramMap.id'] = $('#id').val();
			$.shovePost("queryCollectionById.do", param, function(data) {
			});
		}
	</script> 
<script type="text/javascript" src="css/popom.js"></script> 
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script> 
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script> 
<script type="text/javascript">
		var SysSecond;
		var InterValObj;
		var second;
		var minite;
		var hour;
		var day;
		var times;
		$(document).ready(function() {
			SysSecond = '${borrowDetailMap.remainTime}'; //这里获取倒计时的起始时间 
			SysSecond = Number(SysSecond);
			InterValObj = window.setInterval(SetRemainTime, 1000); //间隔函数，1秒执行 
		});

		//将时间减去1秒，计算天、时、分、秒 
		function SetRemainTime() {
			SysSecond = SysSecond - 1;
			if (SysSecond > 0) {
				second = Math.floor(SysSecond % 60); // 计算秒     
				minite = Math.floor((SysSecond / 60) % 60); //计算分 
				hour = Math.floor((SysSecond / 3600) % 24); //计算小时 
				day = Math.floor((SysSecond / 3600) / 24); //计算天 
				times = day + "天" + hour + "小时" + minite + "分" + second + "秒";
				$("#remainTimeOne").html(times);
				$("#remainTimeTwo").html(times);
			} else {
				window.clearInterval(InterValObj);
				$("#remainTimeOne").html("<font style='color:red'>过期</font>");
				$("#remainTimeTwo").html("<font style='color:red'>过期</font>");
			}
		}

		function openUrl(url) {
			window.open(url, '_blank');
		}
		
		
		function rateNumJudge() {//验证利息计算输入数字是否正确
			if ($("#borrowSum").val() == "") {
				$("#show_error").html("&nbsp;&nbsp;投标金额不能为空");
				$("#showIncome").html("");
			} else if (!/^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/.test($("#borrowSum").val())) {
				$("#show_error").html("&nbsp;&nbsp;请输入正确的投标金额进行计算");
				$("#showIncome").html("");
			} else if ($("#yearRate").val() == "") {
				$("#show_error").html("&nbsp;&nbsp;年利率不能为空");
				$("#showIncome").html("");
			} else if (!/^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/.test($("#yearRate").val())) {
				$("#show_error").html("&nbsp;&nbsp;请输入正确的年利率");
				$("#showIncome").html("");
			} else if ($("#borrowTime").val() == "") {
				$("#show_error").html("&nbsp;&nbsp;计息天数不能为空");
				$("#showIncome").html("");
			} else if (!/^[0-9]*[1-9][0-9]*$/.test($("#borrowTime").val())) {
				$("#show_error").html("&nbsp;&nbsp;请输入正确计息天数");
				$("#showIncome").html("");
			} else {
				//if(!/^[0-9].*$/.test($("#bidReward").val())){
				//      $("#show_error").html("&nbsp;&nbsp;请输入正确的投资奖励");
				// 	      $("#showIncome").html("");
				//   }else 
				//	if(!/^[0-9].*$/.test($("#bidRewardMoney").val())){
				//      $("#show_error").html("&nbsp;&nbsp;请输入正确的现金奖励 ");
				//      $("#showIncome").html("");
				// }else{
				$("#show_error").html("");
			}
		}

		function rateCalculate() {//利息计算
			var str = rateNumJudge();
			var borrowSum = $("#borrowSum").val();
			var yearRate = $("#yearRate").val();
			var borrowTime = $("#borrowTime").val();
			if ($("#show_error").text() != "") {
				return;
			}
			
			var income = borrowSum*yearRate*borrowTime/36500;
			$("#showIncome").html("预计收益："+income.toFixed(2)+"元<br>注：实现预计收益需开启“自动投标”或将每月返还的本金即时投标。");
		
		}
	</script>
    <jsp:include page="/include/footer.jsp"></jsp:include>
</body>
</html>