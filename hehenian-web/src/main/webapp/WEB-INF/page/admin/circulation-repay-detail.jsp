<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>借款管理-招标中</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<link href="../css/admin/admin_custom_css.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
			<div id="right"
				style="background-image: url(../images/admin/right_bg_top.jpg); background-position: top; background-repeat: repeat-x;">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table  border="0" cellspacing="0" cellpadding="0">
							<tr>
								   <td width="100px;" height="28" align="center" 
									class="xxk_all_a">
									<a href="circulationBorrowInit.do">所有的流转标</a>
								</td>
								<td width="2px;">
									&nbsp;
								</td>
								<td width="100px;" height="28" align="center" 
									class="xxk_all_a">
									<a href="publishCirculation.do">代发流转标</a>
								</td>
								<td width="2px;">
									&nbsp;
								</td>
								<td width="100" height="28" align="center" 
									class="xxk_all_a">
									<a href="circulationRepayRecordInit.do">流转标还款记录</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="100" align="center" class="main_alll_h2">
									流转标还款详情
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
						</table>
					</div>
					<div class="tab">
						<table cellspacing="1" cellpadding="3">
							<tr>
								<td class="blue12 left">
									用户名：${paramMap.username}
								</td>
								<td class="f66 leftp200">
								</td>
							</tr>
							<tr>
								<td colspan="2" class="blue12 left">
									标的标题：${paramMap.borrowTitle}
								</td>
							</tr>
							<tr>
							    <td class="blue12 left">
									借款金额：${paramMap.borrowAmount}&nbsp;元
								</td>
								<td class="f66 leftp200">
									年利率：${paramMap.annualRate}%
								</td>
							</tr>
							<tr>
								<td class="blue12 left">
									借款期限：${paramMap.deadline}个月
								</td>
								<td class="f66 leftp200">
								           还款方式：一次性
								</td>
							</tr>
							<tr>
								<td class="blue12 left">
									应还本息：￥${paramMap.recivedPI}元
								</td>
								<td class="f66 leftp200">
                                                                                               还款状态：<s:if test="%{paramMap.repayStatus == 1}">未偿还</s:if>
                                             <s:elseif test="%{paramMap.repayStatus == 2}">已偿还</s:elseif>
								</td>
							</tr>
							<tr>
								<td class="blue12 left" colspan="2">
								    <s:hidden id="id" value="%{paramMap.id}"></s:hidden>
								    <s:if test="%{paramMap.repayStatus == 1}">
								       <button id="mark">标记为已还款</button>
								    </s:if>
									<button id="cirAdd">添加还款记录</button>
								</td>
							</tr>
						</table>
							</div>
						<br />
					</div>
				</div>
				<div style="padding: 15px 10px 0px 10px;">
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 35px;" scope="col">
						id
					</th>
					<th style="width: 150px;" scope="col">
						借款标题
					</th>
					<th style="width: 150px;" scope="col">
						还款金额
					</th>
					<th style="width: 150px;" scope="col">
						还款总额
					</th>
					    <th style="width: 150px;" scope="col">
					    操作员
					</th>
					<th style="width: 150px;" scope="col">
					    备注
					</th>
				</tr>
				<s:if test="cirList==null || cirList.size==0">
					<tr align="center" class="gvItem">
						<td colspan="6">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:iterator value="cirList" var="bean">
					<tr class="gvItem">
						<td>
							${bean.id}
						</td>
						<td>
							${bean.borrowTitle}
						</td>
						<td>
							${bean.repayAmount}
						</td>
						<td>
							${bean.repaySum}
						</td>
						<td>
							${bean.userName}
						</td>
						<td>
							${bean.remark}
						</td>
					</tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
</div>

				<div style="padding: 15px 10px 0px 10px;">
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th  scope="col">
						投资人
					</th>
					<th  scope="col">
						投资金额
					</th>
					<th  scope="col">
						投资时间
					</th>
				</tr>
				
				 <s:if test="%{#request.investList !=null && #request.investList.size()>0}">
                    <s:iterator value="#request.investList" id="investList">
				
							<tr class="gvItem">
						<td>
							 <s:property value="#investList.username" default="---"/>
						</td>
						<td>
							￥<s:property value="#investList.investAmount"/>
						</td>
						<td>
							<s:property value="#investList.investTime"/>
						</td>
					</tr>
					</s:iterator>
					</s:if>
					  <s:else>
					  	<tr align="center" class="gvItem">
						<td colspan="3">暂无数据</td>
					     </tr>
					  </s:else>
			</tbody>
		</table>
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script>
	$(function(){
	  //提交表单
   	  $('#mark').click(function(){
	     param['paramMap.id'] = $('#id').val();
	     if(window.confirm("你确定标记为已还款吗,借款的可用金额将被扣除？")){
	        $.shovePost('markBorrow.do',param,function(data){
		    var callBack = data.msg;
		    if(callBack == 1){
		        alert("操作成功");
		        location.reload();
		        return ;
		     }
		     alert(callBack);
		    });
	     }
	   });
	   $('#cirAdd').click(function(){
	       window.location.href="circulationRepayForAdd.do";
	   });	   
	});
</script>
	</body>	
</html>