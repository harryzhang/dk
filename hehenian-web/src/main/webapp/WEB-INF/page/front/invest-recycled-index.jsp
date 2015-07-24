<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <jsp:include page="/include/head.jsp"></jsp:include>
    <link href="css/inside.css"  rel="stylesheet" type="text/css" />
    <link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
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
       <ul><li id="lab_1" onclick="jumpUrl('homeBorrowInvestList.do');">成功借出</li>
        <li id="lab_2"  onclick="jumpUrl('homeBorrowTenderInList.do');">招标中的借款</li>
        <li id="lab_3" onclick="jumpUrl('homeBorrowRecycleList.do');">回收中的借款</li>
        <li id="lab_4" class="on" onclick="jumpUrl('homeBorrowRecycledList.do');">已回收的借款</li>
        <li id="lab_5" onclick="jumpUrl('homeBorrowBackAcountList.do');">回账查询</li>
        </ul>
        </div>
<div class="box">
        <div class="boxmain2">
         <div class="srh">
         <form action="homeBorrowRecycledList.do" id="searchForm" >
          <input type="hidden" name="curPage" id="pageNum" />
         <span style="margin-left:20px;">关键字：</span><input id="titles" name="title" value="${paramMap.title }" type="text" maxlength="200" class="inp90" />
        <a href="javascript:void(0);" id="search" class="scbtn"> 搜索</a>
        </form>
        </div>
         <div class="biaoge">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th>借款人</th>
    <th>标题</th>
    <th>类型</th>
    <th>信用等级</th>
    <th>年利率</th>
    <th>期限</th>
    <th>投资金额</th>
    <th>已收金额</th>
    <th>操作</th>
    </tr>
  	<s:if test="pageBean.page==null || pageBean.page.size==0">
	   <tr align="center">
		  <td colspan="10">暂无数据</td>
	   </tr>
	</s:if>
	<s:else>
	<s:iterator value="pageBean.page" var="bean">
	 <tr>
	    <td align="center">${bean.borrower}</td>
		<td align="center"><a href="financeDetail.do?id=${bean.borrowId}" target="_blank">${bean.borrowTitle}<s:if test="#bean.isDebt==2"><br/>(转让)</s:if></a></td>
    	<td align="center">
    	<s:if test="#bean.borrowWay ==1">薪金贷</s:if>
        <s:elseif test="#bean.borrowWay ==2"> 生意贷</s:elseif>
        <s:elseif test="#bean.borrowWay ==3">业主贷</s:elseif>
        <s:elseif test="#bean.borrowWay ==4">实地考察借款</s:elseif>
        <s:elseif test="#bean.borrowWay ==5">机构担保借款</s:elseif>
        <s:elseif test="#bean.borrowWay ==6">流转标借款</s:elseif>
        </td>
    	<td align="center"><img src="images/ico_13.jpg" title="${bean.creditrating}分" width="33" height="22" /></td>
    	<td align="center">${bean.annualRate}%</td>
    	<td align="center">${bean.deadline}
    	<s:if test="%{#bean.isDayThe ==1}">个月</s:if><s:else>天</s:else>
    	</td>
    	<td align="center">${bean.realAmount}</td>
    	<td align="center">${bean.forTotalSum}</td>
    	<td align="center">
    	   <a href="javascript:viewDetail('${bean.borrowId}','${bean.bid }');">查看详情</a>
    	</td>
       </tr>
     </s:iterator>
	</s:else>
</table>
<div  class="page" >
	     <shove:page url="homeBorrowRecycledList.do" curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">	    
	    	<s:param name="title">${paramMap.title}</s:param>
	    	<s:param name="type">${paramMap.type}</s:param>
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
<script type="text/javascript" src="css/popom.js"></script>
<script type="text/javascript" src="script/nav-zh.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
<script>
$(function(){
    //样式选中
     $("#zh_hover").attr('class','nav_first');
	 $("#zh_hover div").removeClass('none');
     $("#lab_${type}").attr('class','on');
     $('#li_10').addClass('on');
	 $('#search').click(function(){
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


function jumpUrl(obj){
    window.location.href=obj;
}
function viewDetail(obj,obj1){
    var url = "homeBorrowHaspayDetail.do?id="+obj+"&iid="+obj1;
    jQuery.jBox.open("post:"+url, "还款详情", 800,500,{ buttons: { } });
}		     
</script>
</body>
</html>