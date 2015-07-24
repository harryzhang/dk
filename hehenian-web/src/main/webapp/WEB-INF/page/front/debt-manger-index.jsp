<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   <jsp:include page="/include/head.jsp"></jsp:include>
    <link href="css/inside.css"  rel="stylesheet" type="text/css" />
<link href="css/css.css"  rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<div class="nymain">
  <div class="wdzh">
    <div class="l_nav">
      <div class="box">
     	<!-- 引用我的帐号主页左边栏 -->
         <%@include file="/include/left.jsp" %>
      </div>
      </div>
      <div class="r_main">
      <div class="tabtil">
        <ul><li class="on" onclick="goUrl('queryCanAssignmentDebt.do')">可以转让的借款</li>
        <li onclick="goUrl('queryAuctingDebt.do');">竞拍中的债权</li>
        <li onclick="goUrl('queryAuctedDebt.do');">竞拍结束的债权</li>
        <li onclick="goUrl('queryAuctionFailDebt.do');">转让失败的债权</li>
        </ul>
        </div>
      <div class="box">
        <div class="boxmain2">
						<div id="zrzq_div" class="tcbox" style="display: none;">
							<div class="tcmain">
								<p class="zqsm">简单的债权说明</p>
								<div id="debt_add" class="xzzl">

									<s:include value="debt-manger-add.jsp"></s:include>
								</div>
							</div>
						</div>

						<form id="searchForm" action="queryCanAssignmentDebt.do">
         	<input type="hidden" name="curPage" id="pageNum"/>
         <div class="srh">
        借款人：
          <input type="text" name="borrowerName" value="${paramMap.borrowerName }" class="inp90" /><span style="margin-left:20px;">标题：</span>
          <input type="text" name="borrowTitle" value="${paramMap.borrowTitle }" class="inp90" />
        <a href="javascript:void(0);" id="btn_search" class="scbtn"> 搜索</a></div>
        </form>
         <div class="biaoge">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th>借款人</th>
    <th>标题</th>
    <th>期限</th>
    <th>发布时间</th>
    <th>年利率</th>
    <th>债券期限</th>
    <th>投资金额</th>
    <th>已收金额</th>
    <th>待收金额</th>
    <th>状态</th>
    <th>竞拍时间</th>
    <th>操作</th>
    </tr>
     <s:if test="pageBean.page==null || pageBean.page.size() == 0">
    <tr>
      <td colspan="12" align="center">暂无记录</td>
      </tr>
    </s:if>
    <s:else>
   <s:iterator value="pageBean.page" var="bean">
	  <tr>
	    <td align="center">${borrowerName }</td>
	    <td align="center"><a href="financeDetail.do?id=${borrowId}"  target="_blank">${borrowTitle }</a></td>
	    <td align="center">${deadline }个月</td>
	    <td align="center">${publishTime }</td>
	    <td align="center">${annualRate }%</td>
	    <td align="center">${remainBorrowLimit }个月</td>
	    <td align="center">${realAmount }</td>
	    <td align="center">${ hasPI}</td>
	    <td align="center">${recievedPI-hasPI}</td>
	    <td align="center">
	    	<s:if test="#bean.debtStatus==null||#bean.debtStatus ==4||#bean.debtStatus ==5||#bean.debtStatus ==6||#bean.debtStatus ==7">
	    		--
	    	</s:if>
	    	<s:elseif test="#bean.debtStatus==1">
	    		申请中
	    	</s:elseif>
	    </td>
	    <td align="center">
	    <s:if test="#bean.debtStatus==null||#bean.debtStatus ==4||#bean.debtStatus ==5||#bean.debtStatus ==6||#bean.debtStatus ==7">
	    		--
	    	</s:if>
	    	<s:else>
	    	<s:elseif test="#bean.debtStatus<4">
	    		${auctionDays }天
	    	</s:elseif>
	    		
	    	</s:else>
	    
	    </td>
	    <td align="center">
	    	<s:if test="#bean.debtStatus==null||#bean.debtStatus ==4||#bean.debtStatus ==5||#bean.debtStatus ==6||#bean.debtStatus ==7">
	    		<a href="javascript:void(0)" onclick="addAssignmentDebt('${borrowId}','${recievedPI -hasPI}','${remainBorrowLimit}','${investId}')"> 债权转让</a>
	    	</s:if>
	    	<s:elseif test="#bean.debtStatus==1">
	    		<a href="cancelApplyDebt.do?debtId=${debtId}" >撤回</a>
	    	</s:elseif>
	    </td>
	  </tr>
  	</s:iterator>
  </s:else>
          </table>
 <div class="page">
    	<shove:page url="queryCanAssignmentDebt.do" curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
			<s:param name="borrowerName" >${paramMap.borrowerName }</s:param>
			<s:param name="borrowTitle" >${paramMap.borrowTitle }</s:param>
		</shove:page>
   
    </div>
          </div>
    </div>
</div>
<div class="box" style="display:none;">
        
</div>
<div class="box" style="display:none;">
        
</div>
<div class="box" style="display:none;">
       
</div>
</div>
    </div>
    </div>

 
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>

<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>

<script>
   $(function(){
       $("#zh_hover").attr('class','nav_first');
	 $("#zh_hover div").removeClass('none');
	 $('#li_14').addClass('on');
   		$("#btn_search").click(function(){
   			$("#pageNum").val(1);
   			$("#searchForm").submit();
   		});
   		$("#jumpPage").click(function(){
	 	var curPage = $("#curPageText").val();
	 	 if(isNaN(curPage)){
			alert("输入格式不正确!");
			return;
		 }
	 	$("#pageNum").val(curPage);
	 	$("#searchForm").submit();
	 });
   		
   });
	
	function addAssignmentDebt(borrowId,debtSum,debtLimit,investId){
		$("#zrzq_div").attr("style","");
		$("#debtSum").val(debtSum);
		$("#span_debtSum").html(debtSum);

		$("#borrowId").val(borrowId);
		$("#debtLimit").val(debtLimit);
		$("#span_debtLimit").html(debtLimit);
		$("#investId").val(investId);
	} 
	function goUrl(url){
   	   window.location.href = url;
   }	
	
  
   
   
</script>
</body>
</html>
