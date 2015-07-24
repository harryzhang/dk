<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>已放款标的明细查询</title>
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="../common/date/calendar.css"/>
<%@ include file="/include/includeJs.jsp"%>
<script type="text/javascript" src="../common/date/calendar.js"></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	
	function loanBorrowCheckList(id) {
		$.jBox("iframe:loanBorrowCheckList.do?id=" + id, {
			title : "查看投资人",
			width : 400,
			height : 400,
			top : 25,
			buttons : {
				'关闭': true 
			}
		});
	}
	
	function loanborrowFeeList(deadline) {
		$.jBox("iframe:loanborrowFeeList.do?rate=" + deadline, {
			title : "查看利率与管理费率",
			width : 400,
			height : 400,
			top : 25,
			buttons : {
				'关闭': true 
			}
		});
	}

	//弹出窗口关闭
	function closeMthod() {
		window.jBox.close();
		window.location.reload();
	}

	//取消--关闭弹窗
	function closeMthodes() {
		window.jBox.close();
	}
	
	function disablePhoneVerify() {
        var code = $("#loanTime1").val();
        if(code!='' && code!= null) {
            $.getJSON("loan.do", {'repayDate': code}, function (data) {
                var ret = data.success;
                if (ret == "true") {
                	HHN.popup({content: data.loanList, type: "alert"});
                } else {
                    HHN.popup({content: data.loanList, type: "alert"});
                }
            });
        }else{
            HHN.popup({content:"请输入验证码",type:"alert"});
        }
    }
	
	function disablePhoneVerify2() {
        var code = $("#loanTime1").val();
        if(code!='' && code!= null) {
            $.getJSON("repayment.do", {'repayDate': code}, function (data) {
                var ret = data.success;
                if (ret == true) {
                	HHN.popup({content: "哈哈", type: "alert"});
                } else {
                    HHN.popup({content: data.loanList, type: "alert"});
                }
            });
        }else{
            HHN.popup({content:"请输入验证码",type:"alert"});
        }
    }
	
	
</script>
</head>
<body>
	<div id="right">
		<div style="padding: 15px 10px 0px 10px;">
			<div>
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="120" height="28" class="main_alll_h2"><a
							href="javascript:void(0)">放款标的明细查询</a>
						<td width="2">&nbsp;</td>
						<td width="2">&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
				</table>
				<div
					style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
					<form action="loanBorrowList.do" method="post">
					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
						<tbody>
							<tr>
								<td class="f66" align="left" width="80%" height="36px">
									产品类型：
									<s:select id="fundWay" name="fundWay" list="#request.map" label="aa" listKey="key" listValue="value" headerKey="-1" value="#request.fundWay" headerValue="请选择"></s:select>
									
									&nbsp;&nbsp;&nbsp;放款日期：
									<input id="loanTime1" name="loanTime1" class="Wdate"  value="${loanTime1} " onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
									&nbsp;到
									<input id="loanTime2" name="loanTime2" class="Wdate"  value="${loanTime2 }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
									
									 <input id="bt_search"	type="submit" value="搜 索" /> &nbsp;&nbsp;
								</td>
							</tr>
						</tbody>
					</table>
					</form>
				
				<div>
					<table id="gvNews" style="width: 100%; color: #333333;"
						cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
						<tbody>
							<tr class=gvHeader>
								<th style="width: 150px;" scope="col">分行</th>
								<th style="width: 80px;" scope="col">真实姓名</th>
								<th style="width: 80px;" scope="col">产品类型</th>
								<th style="width: 80px;" scope="col">期限</th>
								<th style="width: 120px;" scope="col">放款日</th>
				
								<th style="width: 80px;" scope="col">利率</th>
								<th style="width: 80px;" scope="col">管理费率</th>
								<th style="width: 120px;" scope="col">借款金额</th>
								<th style="width: 120px;" scope="col">实际放款</th>
								<th style="width: 100px;" scope="col">应提现管理费</th>
				
								<th style="width: 80px;" scope="col">操作</th>
							</tr>
							<c:choose>
							<c:when test="${pageDo.commonModeList ==null} ">
								<tr align="center" class="gvItem">
									<td colspan="11">暂无数据</td>
								</tr>
							</c:when>
							<c:otherwise>
							<c:set var="count" value="0" />
							<c:forEach items="${pageDo.commonModeList}" var="bean" >
								<tr class="gvItem">
									<td>${bean.advisorybranch}</td>
									<td>${bean.realName}</td>
									<td><c:if test="${bean.paymentmode == 8 || bean.paymentmode == 9}">
											合和贷
										</c:if>
										<c:if test="${bean.paymentmode == 10 || bean.paymentmode == 11 }">
											精英贷
										</c:if></td>
									<td>${bean.deadline } <c:if test="${bean.isDayThe ==1}">个月</c:if><c:if test="${bean.isDayThe ==2}">天</c:if></td>
									<td>${bean.auditTime}</td>
						
									<td>1.30% &nbsp;&nbsp;&nbsp;<a style="color:red;font-weight: bolder  ;" href="javascript:loanborrowFeeList(${bean.deadline})">+</a></td>
									<td>0.80% &nbsp;&nbsp;&nbsp;<a style="color:red;font-weight: bolder  ;" href="javascript:loanborrowFeeList(${bean.deadline})">+</a></td>
									<td>${bean.borrowAmount}</td>
									<td>${bean.borrowAmount-bean.consultFee }</td>
									<td>${bean.consultFee }</td>
									<td>
										<a href="javascript:loanBorrowCheckList(${bean.id})">查看投资人</a>
										<!--  <a href="javascript:disablePhoneVerify();">提交1</a>
										<a href="javascript:disablePhoneVerify2();">提交2</a>-->
									</td>
								</tr>
							</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
			</div>
			</div>
		</div>
	</div>
	<shove:page curPage="${pageDo.currentPage}" showPageCount="10"
							pageSize="${pageDo.pageSize}" theme="jsText"
							totalCount="${pageDo.totalCount}" funMethod="toPage">
						</shove:page>
	
</body>
<script type="text/javascript">
	function toPage(pageNo) {
		window.location.href = "loanBorrowList.do?fundWay=${fundWay}&loanTime1=${loanTime1}&loanTime2=${loanTime2}&curPage="
			+ pageNo + "&pageSize=10";
	}
</script>
</html>
