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
<div class="box">
<div class="tabtil">
       <ul><li class="on">我关注的借款</li></ul>
        </div>
        <div class="boxmain2">
         <div class="srh">
       	 <form action="borrowConcernList.do" id="searchForm" >
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
    <th>进度/剩余时间</th>
    <th>信用等级</th>
    <th>操作</th>
    </tr>
  <tr>
    </tr>
  	<s:if test="pageBean.page==null || pageBean.page.size==0">
	   <tr align="center">
		  <td colspan="10">暂无数据</td>
	   </tr>
	</s:if>
	<s:else>
	<s:iterator value="pageBean.page" var="bean">
	 <tr>
		<td align="center"><a href="financeDetail.do?id=${bean.borrowId}" target="_blank">${bean.borrowTitle}</a></td>
    	<td align="center">
    	<s:if test="#bean.borrowWay ==1">薪金贷</s:if>
        <s:elseif test="#bean.borrowWay ==2"> 生意贷</s:elseif>
        <s:elseif test="#bean.borrowWay ==3">业主贷</s:elseif>
        <s:elseif test="#bean.borrowWay ==4">实地考察借款</s:elseif>
        <s:elseif test="#bean.borrowWay ==5">机构担保借款</s:elseif>
        </td>
    	<td align="center">
    	<s:if test="%{#bean.paymentMode == 1}">按月分期还款</s:if>
        <s:elseif test="%{#bean.paymentMode == 2}">按月付息,到期还本</s:elseif>
        <s:elseif test="%{#bean.paymentMode == 3}">秒还</s:elseif>
    	</td>
    	<td align="center">${bean.borrowAmount}</td>
    	<td align="center">${bean.annualRate}%</td>
    	<td align="center">${bean.deadline}
    	<s:if test="%{#bean.isDayThe ==1}">个月</s:if><s:else>天</s:else>
    	</td>
    	<td align="center">${bean.publishTime}</td>
    	<td align="center">${bean.schedules}%/<br/>${bean.times}</td>
    	<td align="center"><img src="images/ico_13.jpg" title="${bean.creditrating}分" width="33" height="22" /></td>
    	<td align="center"><a href="javascript:del('${bean.id}');">删除</a></td>
       </tr>
     </s:iterator>
	</s:else>
</table>
<div  class="page" >
	     <shove:page url="borrowConcernList.do" curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
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
	 $('#li_11').addClass('on');
	 
   	 $("#search").click(function(){
	 	if($("#startTime").val() >$("#endTime").val()){
	      	alert("开始日期不能大于结束日期");
	      	return;
	  	}
		$("#pageNum").val(1);
	 	$("#searchForm").submit();
	 });
	 
	  	$("#jumpPage").click(function(){
		  	if($("#startTime").val() >$("#endTime").val()){
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


function del(obj){
     param["paramMap.id"] = obj;
     $.shovePost("delBorrowConcern.do",param,initCallBack);
}		     
</script>
</body>
</html>
