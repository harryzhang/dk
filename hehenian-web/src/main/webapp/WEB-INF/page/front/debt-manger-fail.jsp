<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   <jsp:include page="/include/head.jsp"></jsp:include>
    <link href="css/inside.css"  rel="stylesheet" type="text/css" />

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
        <ul><li onclick="goUrl('queryCanAssignmentDebt.do');">可以转让的借款</li>
        <li onclick="goUrl('queryAuctingDebt.do');">竞拍中的债权</li>
       <li onclick="goUrl('queryAuctedDebt.do');">竞拍结束的债权</li>
        <li class="on" onclick="goUrl('queryAuctionFailDebt.do');">转让失败的债权</li>
        </ul>
        </div>
      <div class="box" style="display: none;">
       
	 </div>
<div class="box" style="display: none;" >
       
</div>
<div class="box" style="display:none;">
       
</div>
<div class="box" >
        <div class="boxmain2">
         <form id="searchForm" action="queryAuctionFailDebt.do">
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
    <th>年利率</th>
    <th>债券期限</th>
    <th>投资金额</th>
    <th>转让金额</th>
    <th>竞拍底价</th>
    <th>状态</th>
    </tr>
    
   <s:if test="pageBean.page==null || pageBean.page.size() == 0">
    <tr>
      <td colspan="9" align="center">暂无记录</td>
      </tr>
    </s:if>
    <s:else>
    <s:iterator value="pageBean.page" var="bean">
	  <tr>
	    <td align="center">${borrowerName }</td>
		<td align="center"><a href="financeDetail.do?id=${borrowId}" target="_blank">${borrowTitle }</a></td>
		 <td align="center">${deadline }个月</td>
		<td align="center">${annualRate }%</td>
	    <td align="center">${debtLimit} 个月</td>
	    <td align="center">${realAmount }</td>
	     <td align="center">${debtSum }</td>
	    <td align="center">${auctionBasePrice }</td>
	   
	    <td align="center"><s:if test="#bean.debtStatus==4">
	    	竞拍失败
	    </s:if>
	    <s:elseif test="#bean.debtStatus==6">
	    	审核失败
	    </s:elseif>
	     <s:elseif test="#bean.debtStatus==5">
	    	撤销
	    </s:elseif>
	    <s:elseif test="#bean.debtStatus==7">
	    	提前还款
	    </s:elseif>
	    </td>
	  </tr>
  </s:iterator>
</s:else>
  
          </table>
<div class="page">
   
    	<shove:page url="queryAuctionFailDebt.do" curPage="${pageBean.pageNum}" showPageCount="6" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
			<s:param name="borrowerName" >${paramMap.borrowerName }</s:param>
			<s:param name="borrowTitle" >${paramMap.borrowTitle }</s:param>
		</shove:page>
   
    </div>
          </div>
    </div>
</div>
</div>
    </div>
    </div>

 
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="/script/jquery-1.7.1.min.js"></script>
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
	 
   function goUrl(url){
   	 window.location.href = url;
   }	
	
  
   
   
</script>
</body>
</html>
