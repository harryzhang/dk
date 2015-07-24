<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="css/hhncss.css"></link>
<link href="css/user.css" rel="stylesheet" type="text/css" />
<script src="script/jquery-1.2.6.pack.js" type="text/javascript"></script>
<script src="script/add_all.js" type="text/javascript"></script>
<script src="script/MSClass.js" type="text/javascript"></script>
<link rel="stylesheet" href="css/jbox/Gray/jbox.css" type="text/css"></link>
<script type="text/javascript" src="css/popom.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	$(function() {
		$(".header_two_right_ul li").hover(function() {
			$(this).find("ul").show();
		}, function() {
			$(this).find("ul").hide();
		})

		$(".wdzh_top_ul li").eq(1).addClass("wdzhxxk");
		$(".wdzh_top_ul li").click(function() {
			var ppain = $(".wdzh_top_ul li").index(this);
			$(".wdzh_top_ul li").removeClass("wdzhxxk");
			$(this).addClass("wdzhxxk");
		})
		$(".tzjl_fwxy").click(function() {
			$(".tzjl_fwxyh").show();
		})
		$(".tzjl_fwxy1").click(function() {
			$(".tzjl_fwxy1h").show();
		})
	})
</script>
<jsp:include page="/include/head.jsp"></jsp:include>
</head>
<body>

<jsp:include page="/include/top.jsp"></jsp:include>
<div class="user-all">
  <div class="user-center">
    <div style=" width:1170px; margin:0px auto; padding-bottom:24px;"><img src="/images/v1/detail_logo_bg.jpg" width="1170" height="6"  alt=""/></div>
    <div style=" overflow:hidden">
      <div class="u-left">
        <jsp:include page="/include/menu_userManage.jsp"></jsp:include>
      </div>
      <div class="u-right"><div class="user-right">
      <h2>当前投资</h2>
      <div class="tzjl_cgtz_box"  style=" width:100%">
				 <div class="warning_box">
       <span>冻结金额：</span><strong>￥${request.map.freezeSum }</strong>
     </div>	

						<table width="882" border="0" cellpadding="0" cellspacing="1" bgcolor="#e5e5e5" class="xixihaha">
							<tr height="60">
								<td colspan="13" bgcolor="#ffffff" style="padding-left:20px;">
									<form action="investCurrentRecord.do" id="searchForm" method="post">
										<input type="hidden" name="curPage" id="pageNum" /> 交易日期： <input type="text" id="publishTimeStart" name="publishTimeStart" value="${paramMap.publishTimeStart }"
											class="inp90 Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})" /> -<input type="text" id="publishTimeEnd" name="publishTimeEnd"
											value="${paramMap.publishTimeEnd }" class="inp90 Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})" /> 
											借款人：<input type="text" name="userName" id="userName" value="${paramMap.userName }" />
											<%-- 编号：<input type="text" name="id" id="id" value="${paramMap.id }" />  --%>
											<input type="button" value="搜索" id="search" />
									</form></td>
							</tr>
							<tr align="center" height="30">
								<td bgcolor="#ffffff" width="50">债权编号</td>
								<td bgcolor="#ffffff" width="50">交易日期</td>
								<td bgcolor="#ffffff" width="50">借款人</td>
								<td bgcolor="#ffffff" width="50">标题</td>
								<td bgcolor="#ffffff" width="50">年利率</td>
								<td bgcolor="#ffffff" width="50">债权期限</td>
								<td bgcolor="#ffffff" width="50">投资金额</td>
								<td bgcolor="#ffffff" width="50">已收金额</td>
								<td bgcolor="#ffffff" width="50">待收金额</td>
								<td bgcolor="#ffffff" width="50">回收阶段</td>
						
								<td bgcolor="#ffffff" width="50">还款情况</td>
							</tr>
							<s:if test="pageBean.page!=null || pageBean.page.size>0">
								<s:iterator value="pageBean.page" var="bean">
									<tr align="center" height="30">
										<td bgcolor="#ffffff">${bean.invest_number }</td>
										<td bgcolor="#ffffff">${bean.investTime }</td>
										<td bgcolor="#ffffff">${bean.username }</td>
										<td bgcolor="#ffffff"><a href="financeDetail.do?id=${bean.borrowId }">${bean.borrowTitle }</a>
										</td>
										<td bgcolor="#ffffff">${bean.annualRate }%</td>
										<td bgcolor="#ffffff">${bean.debtLimit }个月</td>
										<td bgcolor="#ffffff">${bean.realAmount }</td>
										<td bgcolor="#ffffff">${bean.hasPI }</td>
										<td bgcolor="#ffffff">${bean.notPI }</td>
										<td bgcolor="#ffffff">${bean.hasDeadline+1 }/${bean.deadline }</td>
								
										<td bgcolor="#ffffff"><s:if test="#bean.borrowStatus==2">
												<a style="color: #ccc;cursor: default;">招标中</a>
											</s:if> <s:elseif test="#bean.borrowStatus==3">
												<a style="color: #ccc;cursor: default;">满标审核中</a>
											</s:elseif> <s:elseif test="#bean.borrowStatus==4 || #bean.borrowStatus==5">
												<a href="javascript:payDeatil(${bean.investId })">还款详情</a>
											</s:elseif></td>
									</tr>
								</s:iterator>
							</s:if>
							<s:else>
								<tr>
									<td align="center" height="30" colspan="11">暂无数据</td>
								</tr>
							</s:else>
						</table>
		<div class="cle"></div>
      </div>
					<div class="cle"></div>
					<div class="cle"></div>
					<div class="mjd_fy_all">
						<s:if test="pageBean.page!=null && pageBean.page.size>0">
							<div class="page" style=" padding-top:20px;">
								<p>
									<shove:page url="investCurrentRecord.do" curPage="${pageBean.pageNum}" showPageCount="6" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
									</shove:page>
								</p>
							</div>
						</s:if>
					</div>
				
    </div></div>
    </div>
    <div style=" width:1170px; margin:0px auto">&nbsp;</div>
  </div>
</div>
<div class="cle"></div>








	<jsp:include page="/include/footer.jsp"></jsp:include>
	<script type="text/javascript">
		$(function() {
			$("#search").click(
					function() {
						if ($("#publishTimeStart").val() != '' && $("#publishTimeEnd").val() != ''
								&& $("#publishTimeStart").val() > $("#publishTimeEnd").val()) {
							alert("开始日期不能大于结束日期");
							return;
						}
						/* if (isNaN($("#id").val())) {
							alert("编号输入错误");
							return;
						} */
						$("#pageNum").val(1);
						$("#searchForm").submit();
					});
		});

		function payDeatil(investId) {
			var url = "homeBorrowForpayDetail.do?iid=" + investId;
			jBox("iframe:" + url, {
				title : "还款详情",
				top : '20%',
				width : 750,
				height : 400,
				buttons : {
					'确定' : true
				}
			});
		}

		//关闭协议窗口
		function protocolClose() {
			$(".tzjl_fwxyh").hide();
		}
	</script>
</body>
</html>
