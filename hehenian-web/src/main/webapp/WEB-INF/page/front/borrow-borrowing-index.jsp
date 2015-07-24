<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
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
        <ul>
        <li  onclick="goUrl('homeBorrowAuditList.do')">审核中的借款</li>
        <li class="on" onclick="goUrl('homeBorrowingList.do')">招标中的借款</li>
        </ul>
        </div>
<div class="box">
        <div class="boxmain2">
         <div class="srh">
          <form action="homeBorrowingList.do" id="searchForm" >
          		<input type="hidden" name="type" value="${paramMap.type}" />
	          <input type="hidden" name="curPage" id="pageNum" />
	        发布时间：<input type="text" id="publishTimeStart"  name="publishTimeStart" value="${paramMap.publishTimeStart }" class="inp90 Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/> 
	        到 
	        <input type="text" id="publishTimeEnd"  name="publishTimeEnd" value="${paramMap.publishTimeEnd }" class="inp90 Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
	         <span style="margin-left:20px;">关键字：</span><input id="titles" name="title" value="${paramMap.title }" type="text" maxlength="200" class="inp90" />
	        <a href="javascript:void(0);" id="search" class="scbtn"> 搜索</a>
        </form>
          </div>
         <div class="biaoge">
             <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th>标题</th>
    <th>类型</th>
    <th>还款方式</th>
    <th>金额（元）</th>
    <th>年利率</th>
    <th>期限</th>
    <th>发布时间</th>
    <th>进度</th>
    <th>状态</th>
    </tr>
  	<s:if test="pageBean.page==null || pageBean.page.size==0">
	   <tr align="center">
		  <td colspan="9">暂无数据</td>
	   </tr>
	</s:if>
	<s:else>
	<s:iterator value="pageBean.page" var="bean">
	 <tr>
		<td align="center"><a href="financeDetail.do?id=${bean.id}">${bean.borrowTitle}</a></td>
    	<td align="center">
    	<s:if test="#bean.borrowWay ==1">薪金贷</s:if>
        <s:elseif test="#bean.borrowWay ==2"> 生意贷</s:elseif>
        <s:elseif test="#bean.borrowWay ==3">业主贷</s:elseif>
        <s:elseif test="#bean.borrowWay ==4">实地考察借款</s:elseif>
        <s:elseif test="#bean.borrowWay ==5">机构担保借款</s:elseif>
         <s:elseif test="#bean.borrowWay ==6">流转标借款</s:elseif>
        </td>
    	<td align="center">
    	<s:if test="%{#bean.paymentMode == 1}">按月分期还款</s:if>
        <s:elseif test="%{#bean.paymentMode == 2}">按月付息,到期还本</s:elseif>
        <s:elseif test="%{#bean.paymentMode == 3}">秒还</s:elseif>
        <s:elseif test="%{#bean.paymentMode == 4}">一次性</s:elseif>
    	</td>
    	<td align="center">${bean.borrowAmount}</td>
    	<td align="center">${bean.annualRate}%</td>
    	<td align="center">${bean.deadline}个月</td>
    	<td align="center"><s:date name="#bean.publishTime" format="yyyy-MM-dd HH:mm:ss"/></td>
    	<td align="center">${bean.schedules}%</td>
    	<td align="center">
    	<s:if test="%{#bean.borrowStatus == 1}">
                                  初审中
        </s:if>
        <s:elseif test="%{#bean.borrowStatus == 2}">
                                   招标中
        </s:elseif>
        <s:elseif test="%{#bean.borrowStatus == 3}">
                                  满标
        </s:elseif>
        <s:elseif test="%{#bean.borrowStatus == 4}">
                                   还款中
        </s:elseif>
        <s:elseif test="%{#bean.borrowStatus == 5}">
                                  已经还完
        </s:elseif>
        <s:elseif test="%{#bean.borrowStatus == 6}">
                              流标
        </s:elseif>
    	</td>
       </tr>
     </s:iterator>
	</s:else>
</table>
  <div  class="page" >
	     <shove:page url="homeBorrowingList.do" curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
	    	<s:param name="publishTimeStart">${paramMap.publishTimeStart}</s:param>
	    	<s:param name="publishTimeEnd">${paramMap.publishTimeEnd}</s:param>
	    	<s:param name="title">${paramMap.title}</s:param>
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
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script>
$(function(){
    //样式选中
     $("#zh_hover").attr('class','nav_first');
	 $("#zh_hover div").removeClass('none');
	 $('#li_8').addClass('on');
	 
     $('#search').click(function(){
	 	if($("#publishTimeStart").val() >$("#publishTimeEnd").val()){
	      	alert("开始日期不能大于结束日期");
	      	return;
		}
		$("#pageNum").val(1);
	 	$("#searchForm").submit();
     });
     
     $("#jumpPage").click(function(){
		if($("#publishTimeStart").val() >$("#publishTimeEnd").val()){
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
function clearComponent(){
     $("#titles").val('');
     $("#publishTimeStart").val('');
     $("#publishTimeEnd").val('');
}

function goUrl(url){
	window.location.href = url;
}
		     
</script>
</body>
</html>
