<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="css/jbox/Gray/jbox.css" type="text/css"></link>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="css/admin/popom.js"></script>
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	$(function() {
		$(".wdzh_top_ul li:first").addClass("wdzhxxk");
		
		$(".header_two_right_ul li").hover(function() {
			$(this).find("ul").show();
		}, function() {
			$(this).find("ul").hide();
		})

		$(".wdzh_top_ul li").click(function() {
			var ppain = $(".wdzh_top_ul li").index(this);
			$(".wdzh_top_ul li").removeClass("wdzhxxk");
			$(this).addClass("wdzhxxk");
		})
		$(".hk_tc_a").click(function() {
			$(".hk_tc_ah").show();
		})
		
		$('#search').click(function(){
		 	if($("#publishTimeStart").val()!=''&& $("#publishTimeEnd").val()!='' && $("#publishTimeStart").val() >$("#publishTimeEnd").val()){
		      	alert("开始日期不能大于结束日期");
		      	return;
			}
			$("#pageNum").val(1);
		 	$("#searchForm").submit();
	     });
	     
	     $("#jumpPage").click(function(){
	    		if($("#publishTimeStart").val()!=''&& $("#publishTimeEnd").val()!='' && $("#publishTimeStart").val() >$("#publishTimeEnd").val()){
		      	alert("开始日期不能大于结束日期");
		      	return;
			}
		 	var curPage = $("#curPageText").val();
		 	if(isNaN(curPage)){
				alert("输入格式不正确!");
				return;
			}
		 	$("#pageNum").val(curPage);
		 	$("#searchForm").submit();
		});
	});
	
	function repayById(borrowId){
		 jBox("iframe:queryPayingDetailsHHN.do?borrowId="+borrowId, {
		    title: "还款详情",
		     width: 620,
		    height: 520,
		   buttons: {}
	 	});
		 
	}
	

	function openUrl(url){
		window.open(url,'_blank');
	}
</script>
</head>

<body>
 <div style="position: absolute; height:54px; line-height:54px; background:#f3f3f3; width:100%; margin-top:151px !important; margin-top:177px;border-bottom:1px solid #e5e5e5;border-top:1px solid #e5e5e5;"></div>
	<jsp:include page="/include/top.jsp"></jsp:include>

	<div class="cle"></div>
	<div class="wytz_center">
     <div class="mjd_tzlc_all">
			<jsp:include page="/include/menu_userManage.jsp"></jsp:include></div>
		<div class="wdzh_top">
		
			<div class="wdzh_next" style="display:block;">
				<div class="wdzh_next_left">
					<ul>
						<li><a href="home.do">账户总览</a>
						</li>
					
						<li class="wdzh_next_left_ong"><a href="homeBorrowAllList.do" style=" color:#e94718;">借款列表</a>
						</li>
					</ul>
				</div>
				<div class="wdzh_next_right" style="min-height:500px;">
					<div class="wdzh_jklb_top">
						<form action="homeBorrowAllList.do" id="searchForm" method="post">
							<input type="hidden" name="type" value="${paramMap.type}" /> <input type="hidden" name="curPage" id="pageNum" /> 发布时间：<input type="text" id="publishTimeStart"
								name="publishTimeStart" value="${paramMap.publishTimeStart }" class="inp90 Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})" /> 到 <input type="text"
								id="publishTimeEnd" name="publishTimeEnd" value="${paramMap.publishTimeEnd }" class="inp90 Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})" /> <span
								style="margin-left:20px;">关键字：</span><input id="titles" name="title" value="${paramMap.title }" type="text" maxlength="200" class="inp90" /> <input type="button" id="search"
								value="搜索" />
						</form>
					</div>
					<div class="wdzh_jklb_next">
						<h3>
							<table width="766" border="0" style="font-size: 12px;">
								<tr>
									<td  align="center">标题</td>
									<td  align="center">类型</td>
									<td  align="center">金额(元)</td>
									<td  align="center">年利率</td>
									<td align="center">期限</td>
									<td  align="center">发布时间</td>
									<td  align="center">进度</td>
									<td  align="center">状态</td>
									<td  align="center">借款协议</td>
									<td  align="center">操作</td>
								</tr>
							</table>
						</h3>
						<p>
							<table width="768" border="0" cellpadding="0" cellspacing="1" bgcolor="#e5e5e5" style="font-size: 12px;">
								<s:if test="pageBean.page==null || pageBean.page.size==0">
									<tr align="center" height="30">
										<td colspan="10">暂无数据</td>
									</tr>
								</s:if>
								<s:else>
									<s:iterator value="pageBean.page" var="bean">
										<tr height="30">
											<td bgcolor="#ffffff"  align="center"><a href="financeDetail.do?id=${bean.id}" target="_blank" style="color: #666666">${bean.borrowTitle}</a>
											</td>
											<td bgcolor="#ffffff"  align="center"><s:if test="#bean.borrowWay ==1">薪金贷</s:if> <s:elseif test="#bean.borrowWay ==2"> 生意贷</s:elseif> <s:elseif
													test="#bean.borrowWay ==3">业主贷</s:elseif> <s:elseif test="#bean.borrowWay ==4">实地考察借款</s:elseif> <s:elseif test="#bean.borrowWay ==5">机构担保借款</s:elseif> <s:elseif
													test="#bean.borrowWay ==6">流转标借款</s:elseif></td>
											<td bgcolor="#ffffff"  align="center">${bean.borrowAmount}</td>
											<td bgcolor="#ffffff"  align="center">${bean.annualRate}%</td>
											<td bgcolor="#ffffff"  align="center">${bean.deadline}个月</td>
											<td bgcolor="#ffffff"  align="center"><s:date name="#bean.publishTime" format="yyyy-MM-dd HH:mm:ss" /></td>
											<td bgcolor="#ffffff"  align="center">${bean.schedules}%</td>
											<td bgcolor="#ffffff"  align="center"><s:if test="#bean.borrowStatus == 1">
										                                  初审中
										        </s:if> <s:elseif test="#bean.borrowStatus == 2">
										                                   招标中
										        </s:elseif> <s:elseif test="#bean.borrowStatus == 3">
										                                  满标
										        </s:elseif> <s:elseif test="#bean.borrowStatus == 4">
										                                   还款中
										        </s:elseif> <s:elseif test="#bean.borrowStatus == 5">
										                                  已还完
										        </s:elseif> <s:elseif test="#bean.borrowStatus == 6">
										                              流标
										        </s:elseif> <s:elseif test="#bean.borrowStatus == 7">
										                复审中
										        </s:elseif> <s:elseif test="#bean.borrowStatus == 8">
										                              待发布
										        </s:elseif> <s:elseif test="#bean.borrowStatus == 9">
										                              重新初审
										        </s:elseif>
										         <s:else>
										        	--
										        </s:else></td>

											<td bgcolor="#ffffff"  align="center">
											<a style="color: #666666" href="javascript:openUrl('agreementDetail.do?borrowId=${bean.id }')">查看</a>
											</td>

											<td bgcolor="#ffffff"  align="center"><u class="hk_tc_a" onclick="repayById(${bean.id})">还款</u>
											</td>
										</tr>
									</s:iterator>
								</s:else>
							</table>
						</p>
						<div class="page" align="center" height="30">
							<br />
							<shove:page url="homeBorrowAllList.do" curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
								<s:param name="publishTimeStart">${paramMap.publishTimeStart}</s:param>
								<s:param name="publishTimeEnd">${paramMap.publishTimeEnd}</s:param>
								<s:param name="title">${paramMap.title}</s:param>
							</shove:page>
							<br />
						</div>
					</div>
				</div>
				<div class="cle"></div>
			</div>
			<div class="cle"></div>
		</div>
	</div>
	<div class="cle"></div>

	<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
</html>
