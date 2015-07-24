<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%response.addHeader("X-XSS-Protection","0");%>
<jsp:include page="/include/cf-head.jsp"></jsp:include>
<link href="/css/hhncss.css" rel="stylesheet" type="text/css" />
<link href="/newcss/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script src="script/add_all.js" type="text/javascript"></script>
<script src="script/MSClass.js" type="text/javascript"></script>
<style type="text/css">
.jbcr_a {
	background: url("../images/pic_all.png") no-repeat scroll -1px -84px
		rgba(0, 0, 0, 0);
	border: medium none;
	color: #FFFFFF;
	cursor: pointer;
	height: 27px;
	text-align: center;
	width: 95px;
}
.hhns td{
background: url("../images/box_bg_l.png") no-repeat scroll right center rgba(0, 0, 0, 0);
}

.hhn_zqxq{ width:743px;  border:1px solid #e5e5e5; margin-left:0px; margin-top:15px;}
.hhn_zqxq_top{ width:743px; height:36px; border-bottom:1px solid #e5e5e5; line-height:36px;}
.hhn_zqxq_top strong{ margin-left:15px; font-size:14px; font-weight:normal;}
.hhn_zqxq_next{ width:600x;}
.hhn_zqxq_next_l{ width:400px; border-right:1px solid #e5e5e5; float:left; color:#333; font-size:14px; overflow:hidden;}
.hhn_zqxq_next_l img{ margin:10px;}
.hhn_zqxq_next_l strong{ font-size:14px; color:#e94718; font-weight:normal;}
.hhn_zqxq_next_l span{ color:#666; margin-left:5px; font-size:12px;}
.hhn_zqxq_next_l input[type="button"]{  width:95px; height:27px; border:none; text-align:center; color:#fff; background:url(../images/pic_all.png) -1px -84px no-repeat; cursor:pointer; margin-left:15px;}
.hhn_zqxq_next_l input[type="button"]:hover{ background:url(../images/pic_all.png) -1px -112px no-repeat;}
.hhn_zqxq_next_r{ width:160px; float:right;}
.hhn_zqxq_next_r img{ margin:10px 60px;}
.hhn_zqxq_next_r p{ padding-left:10px; margin-bottom:10px; color:#666;}
.hhn_zqxq_next_r p a{ color:#0d79c1;}
</style>
</head>
<body>
<script type="text/javascript">
	var param ={};
	$(function() {
		$(".nd_bdxq_jkxq_three_ul li:first").addClass("jdd")
		param["id"] = '${paramMap.borrowId}';
		$.post("queryDebtBorrowDetail.do", param, function(data) {
			$("#borrow_detail").html(data);
		});

		$(".header_two_right_ul li").hover(function() {
			$(this).find("ul").show();
		}, function() {
			$(this).find("ul").hide();
		});

		$(".nd_bdxq_jkxq_three_ul li").click(function() {
			var s = $(".nd_bdxq_jkxq_three_ul li").index(this);
			$(".nd_bdxq_jkxq_three_ul li").removeClass("jdd");//失去焦点
			$(this).addClass("jdd");//设为焦点
			$(".nd_bdxq_jkxq_three_a").hide();
			$(".nd_bdxq_jkxq_three_a").eq(s).show();
		})
		$(".jbcr_a").click(function() {
			$(".jb_taczq").show();
		})
		$(".jbcr_xxa").click(function() {
			$(".jb_taczq").hide();
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
</script>
	<!-- 引用头部公共部分 -->
<jsp:include page="/include/cf-top.jsp"></jsp:include>
<div class="s_sur_ix main el_container" style=" overflow:hidden; margin-bottom:20px;"> 
      <!-- <div style=" margin:10px 0px;"><img src="images/ad.jpg" width="974" height="80"  alt=""/></div> --> 
      <!--左侧-->
      <div class=" nr_left"> 
        <!--筛选栏-->
        <div class="search_all">
            <div class="mjd_tzlc_all">
		      <ul>
		        <li class="tzlc" onclick="javascript:window.location.href='queryFrontAllDebt.do'">返回债权列表</li>
		      </ul>
		     </div>
			<div class="hhn_zqxq">
				<div class="hhn_zqxq_top" style="background:#F3F3F3">
					<strong>债权详情</strong>
					<strong style="float: right;padding-right: 72px;">转让价格</strong>
				</div>
				<div class="hhn_zqxq_next">
					<div class="hhn_zqxq_next_l" style=" width:580px; height:120px;"><span id="remainSeconds" style="display: none;">${times}</span>
						<table width="100%" border="0" style="font-size: 12px; height:120px;" class="hhn">
							<tr height="30">
								<td width="40" align="center">剩余本金 <br/>  <strong>￥${paramMap.balance }</strong> </td>
								<td width="20" align="center">应收利息<br/>  <strong>￥${paramMap.interest }</strong></td>
								<td width="20" align="center">年化收益率<br/>  <strong>${borrowMap.annualRate }%</strong></td>
								<td width="50" align="center">剩余期限<br/ > <strong id="days">${paramMap.dayss } 天</strong></td>
								<td width="50" align="center">下一还款日<br/>  <strong>${request.nextDay }</strong></td>
								<td width="90" align="center" style=' background:url("")'>剩余交易时间<br/> <strong id="remainTimeOne">&nbsp;</strong></td>
							</tr>
						</table>
					</div>
					<div class="hhn_zqxq_next_r">
						<div style="margin:0px auto 0px auto;" align="center">
						<br/><strong style=" color: #E94718;font-size: 14px;font-weight: normal;">￥${paramMap.auctionBasePrice}</strong><br/><br/>
							<s:if test="#request.paramMap.debtStatus==1">
								<input type="button" value="申请中" class="jbcr_a" style="cursor: default;background: #ccc;" disabled="disabled" />
							</s:if>
							<s:elseif test="#request.paramMap.debtStatus==3">
								<input type="button" value="已转让" class="jbcr_a" style="cursor: default;background: #ccc;" disabled="disabled" />
							</s:elseif>
							<s:elseif test="#request.times<=0">
								<input type="button" value="已过期" class="jbcr_a" style="cursor: default;background: #ccc;" disabled="disabled" />
							</s:elseif>
							<s:elseif test="#request.paramMap.debtStatus==2">
								<input type="button" id="buyDebt" onclick="buys()" value="立即购买" class="jbcr_a" />
							</s:elseif>
								<br/><p style="margin:0;margin-top: 5px;padding-bottom: 3px;padding-left: 0;"> <input type="checkbox" checked="checked" style="vertical-align: middle;">同意<a href="/zqzr.pdf" target="_blank">《债权转让协议》</a></p>
						</div>
					</div>
					<div class="cle"></div>
				</div>
			</div>
              
            
            </div>
              <div class="search_all">
            	<div id="dataForm" style="display: none;"></div>
					<div class="cle"></div>

					<!-- 借款人详细信息div -->
					<div id="borrow_detail"></div>
            	</div>
     
      </div>
      <!--右侧-->
      <jsp:include page="/include/cf-right.jsp"></jsp:include>
    </div>
    
	<!-- 引用底部公共部分 -->
	<jsp:include page="/include/cf-footer.jsp"></jsp:include>
	<script type="text/javascript">
		var SysSecond;
		var InterValObj;
		$(function() {
			SysSecond = Number($("#remainSeconds").html()); //这里获取倒计时的起始时间 
			InterValObj = window.setInterval(SetRemainTime, 1000); //间隔函数，1秒执行 

			
		});
		
		function buys(){
			param["paramMap.id"] = '${paramMap.debtId}'
			param["paramMap.auctionPrice"] = '${paramMap.auctionBasePrice}'
			param["paramMap.dealpass"] = $("#dealpass").val();
			param["paramMap.usrCustId"] = $("#usrCustId").val();
			param["paramMap.debtUsrCustId"] = $("#debtUsrCustId").val();
			if (confirm("是否确认购买？")) {
				$("#buyDebt").attr("disabled", true);
				$.post("../addAuctingDebt.do", param, function(data) {
					if (data.length < 30) {
						alert(data);
					} else {
						$("#dataForm").html(data);
					}
					$("#buyDebt").attr("disabled", false);
				});
			}
		}

		//将时间减去1秒，计算天、时、分、秒 
		function SetRemainTime() {
			if (SysSecond > 0) {
				SysSecond = SysSecond - 1;
				var second = Math.floor(SysSecond % 60); // 计算秒     
				var minite = Math.floor((SysSecond / 60) % 60); //计算分 
				var hour = Math.floor((SysSecond / 3600) % 24); //计算小时 
				var day = Math.floor((SysSecond / 3600) / 24); //计算天 
				var times = day + "天" + hour + "小时" + minite + "分" + second + "秒";
				$("#remainTimeOne").html(times);
			} else {
				window.clearInterval(InterValObj);
				$("#remainTimeOne").html("过期");
			}
		}

	</script>
</body>
</html>
