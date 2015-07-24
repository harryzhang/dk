<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
       <ul><li  onclick="jumpUrl('queryMySuccessBorrowList.do');">成功借款</li>
        <li  onclick="jumpUrl('queryMyPayingBorrowList.do');">正在还款的借款</li>
        <li class="on" onclick="jumpUrl('queryAllDetails.do');">还款明细账</li>
        <li onclick="jumpUrl('queryBorrowInvestorInfo.do');">借款明细账</li>
        <li onclick="jumpUrl('queryPayoffList.do');">已还完的借款</li>
        </ul>
        </div>
      

<div class="box" >
        <div class="boxmain2">
         <div class="srh">
        <form action="queryAllDetails.do" id="searchForm" >
        发布时间：<input type="text" id="startTime" name="startTime" value="${paramMap.startTime }" class="inp90 Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/> 
        到 
        <input type="text" id="endTime" value="${paramMap.endTime }" name="endTime" class="inp90 Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/> 
        <span style="margin-left:20px;">关键字：</span><input type="text" class="inp90" name="title" value="${paramMap.title }" id="title_s" />
        <a href="javascript:void(0)" class="scbtn" id="btn_search"> 搜索</a> 
        <a href="javascript:void(0);" class="scbtn" onclick="excels()">导出excel表</a>
         <input type="hidden" name="curPage" id="pageNum" />
        </form>
        </div>
        <div class="biaoge">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th>标题</th>
    <th>第几期</th>
    <th>应还款日期</th>
    <th>实际还款日期</th>
    <th>本期应还本息</th>
    <th>利息</th>
    <th>逾期罚款</th>
    <th>逾期天数</th>
    <th>还款状态</th>
    <th>操作</th>
    </tr>
    
    <s:if test="pageBean.page==null || pageBean.page.size<=0">
    <tr><td align="center" colspan="11">暂无记录</td></tr>
  </s:if>
  
  <s:else>
    <s:iterator value="pageBean.page"  var="bean"> 
       <tr>
	    <td align="center"><a href="financeDetail.do?id=${bean.id}"  target="_blank" >${bean.borrowTitle }</a></td>
	    <td align="center">${bean.repayPeriod }</td>
	    <td align="center">${bean.repayDate}</td>
	    <td align="center">${bean.realRepayDate}</td>
	    <td align="center">￥${bean.forPI} </td>
	    <td align="center">￥${bean.stillInterest}</td>
	    <td align="center">￥0.00</td>
	    <td align="center">0</td>
	    <td align="center">
	        <s:if test="#bean.repayStatus==1">未偿还</s:if>
	        <s:else>已偿还</s:else>
	    </td>
	    <td align="center">
	    <s:if test="#bean.repayStatus==1"><a href="javascript:myPay(${bean.payId });">还款</a></s:if>
	        <s:else>---</s:else>
	    </td>
	  </tr>
    </s:iterator>
    </s:else>
          </table>
          
          <div  class="page" >
	     <shove:page url="queryAllDetails.do" curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
	    	<s:param name="startTime">${paramMap.startTime}</s:param>
	    	<s:param name="endTime">${paramMap.endTime}</s:param>
	    	<s:param name="title">${paramMap.title}</s:param>
	     </shove:page>
  </div>
	
          </div>
          
    <span id="my_pay_"></span>  
         
    </div>
</div>

    </div>
  </div>
</div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>

<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="css/popom.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
<script>
$(function(){
    $("#zh_hover").attr('class','nav_first');
	$("#zh_hover div").removeClass('none');
	$('#li_7').addClass('on');
	$("#btn_search").click(function(){
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
	

	function showAgree(){
		var url = "getMessageBytypeId.do?typeId=1";
		 jQuery.jBox.open("post:"+url, "查看协议书", 600,400,{ buttons: {} });
			
    }
    
    function jumpUrl(obj){
    	window.location.href=obj;
    }
       
    function excels(){
    	window.location.href=encodeURI(encodeURI("exportrepayment.do?startTime="+$("#startTime1").val()+"&&endTime="+$("#endTime1").val()+"&&title="+$("#title1").val()));
    }
    
    function myPay(id){//还款
       var url = "queryMyPayData.do?payId="+id;
       jQuery.jBox.open("post:"+url, "还款", 450,450,{ buttons: { } });
       
    }
</script>

</body>
</html>
