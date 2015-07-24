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
        <li id="lab_4" onclick="jumpUrl('homeBorrowRecycledList.do');">已回收的借款</li>
        <li id="lab_5" class="on" onclick="jumpUrl('homeBorrowBackAcountList.do');">回账查询</li>
        </ul>
        </div>
<div class="box">
        <div class="boxmain2">
        <div class="srh"><strong class="blue">个人贷款：</strong></div>
         <div class="biaoge" style="margin-top:0px;">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th>&nbsp;</th>
    <th>未来一个月</th>
    <th>未来三个月</th>
    <th>未来一年</th>
    <th>全部</th>
    </tr>
  <tr>
    <td align="center">待收本息</td>
    <td align="center" id="allForPIOneMonth">￥<s:if test="#request.backAcountStatisMap.allForPIOneMonth !=''">${requestScope.backAcountStatisMap.allForPIOneMonth}</s:if><s:else>0</s:else></td>
    <td align="center" id="allForPIThreeMonth">￥<s:if test="#request.backAcountStatisMap.allForPIThreeMonth !=''">${backAcountStatisMap.allForPIThreeMonth}</s:if><s:else>0</s:else></td>
    <td align="center" id="allForPIYear">￥<s:if test="#request.backAcountStatisMap.allForPIYear !=''">${backAcountStatisMap.allForPIYear}</s:if><s:else>0</s:else></td>
    <td align="center" id="allForPI">￥<s:if test="#request.backAcountStatisMap.allForPI !=''">${backAcountStatisMap.allForPI}</s:if><s:else>0</s:else></td>
    </tr>
          </table>
          <p>&nbsp;</p>
          </div>
         <div class="srh">
        <form action="homeBorrowBackAcountList.do" id="searchForm" >
	          <input type="hidden" name="curPage" id="pageNum" />
	        发布时间：<input type="text" id="publishTimeStart"  name="publishTimeStart" value="${paramMap.publishTimeStart }" class="inp90 Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/> 
	        到 
	        <input type="text" id="publishTimeEnd"  name="publishTimeEnd" value="${paramMap.publishTimeEnd }" class="inp90 Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
	         <span style="margin-left:20px;">关键字：</span><input id="titles" name="title" value="${paramMap.title }" type="text" maxlength="200" class="inp90" />
	        <a href="javascript:void(0);" id="search" class="scbtn"> 搜索</a>
        </form>
        </div>
         <div class="biaoge" id="content">
             <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th>借款者</th>
    <th>标题</th>
    <th>借款类型</th>
    <th>年利率</th>
    <th>期限</th>
    <th>投资金额</th>
    <th>已收金额</th>
    <th>待收金额</th>
    <th>查看协议</th>
    </tr>
  	<s:if test="pageBean.page==null || pageBean.page.size==0">
	   <tr align="center">
		  <td colspan="9">暂无数据</td>
	   </tr>
	</s:if>
	<s:else>
	<s:iterator value="pageBean.page" var="bean">
	 <tr>
	    <td align="center">${bean.borrower}</td>
		<td align="center"><a href="financeDetail.do?id=${bean.borrowId}" target="_blank">${bean.borrowTitle}</a></td>
    	<td align="center">
    	<s:if test="#bean.borrowWay ==1">薪金贷</s:if>
        <s:elseif test="#bean.borrowWay ==2"> 生意贷</s:elseif>
        <s:elseif test="#bean.borrowWay ==3">业主贷</s:elseif>
        <s:elseif test="#bean.borrowWay ==4">实地考察借款</s:elseif>
        <s:elseif test="#bean.borrowWay ==5">机构担保借款</s:elseif>
        <s:elseif test="#bean.borrowWay ==6">流转标借款</s:elseif>
        </td>
    	<td align="center">${bean.annualRate}%</td>
    	<td align="center">${bean.deadline}
    	<s:if test="%{#bean.isDayThe ==1}">个月</s:if><s:else>天</s:else>
    	</td>
    	<td align="center">${bean.realAmount}</td>
    	<td align="center">${bean.forHasSum}</td>
    	<td align="center">${bean.forTotalSum}</td>
    	<td align="center">
    	   <%--<a href="javascript:showAgree('${bean.borrowId}','${bean.investId }');">查看协议</a> --%>
    	<a href="getMessageBytypeId.do?typeId=1&&borrowId=${bean.borrowId }&&investId=${bean.investId }&&styles=1" target="_blank">查看协议</a>
    	   </td>
       </tr>
     </s:iterator>
	</s:else>
</table>
<div  class="page" >
	     <shove:page url="homeBorrowBackAcountList.do" curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
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
<script type="text/javascript" src="script/nav-zh.js"></script>
<script type="text/javascript" src="css/popom.js"></script>
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

	
function jumpUrl(obj){
    window.location.href=obj;
}
function showAgree(borrowId,invest_id){
    var url = "getMessageBytypeId.do?typeId=15&&borrowId="+borrowId+"&&investId="+invest_id+"&&styles=1";
     jQuery.jBox.open("post:"+url, "查看协议书", 650,400,{ buttons: { } });
     
}     
</script>
</body>
</html>