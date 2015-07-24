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
        <li  onclick="jumpUrl('queryAllDetails.do');">还款明细账</li>
        <li class="on" onclick="jumpUrl('queryBorrowInvestorInfo.do');">借款明细账</li>
        <li onclick="jumpUrl('queryPayoffList.do');">已还完的借款</li>
        </ul>
        </div>
   
<div class="box" >
        <div class="boxmain2">
         <div class="srh">
          <form action="queryBorrowInvestorInfo.do" id="searchForm" >
        投资者：
          <input type="text" class="inp140" id="investor" name="investor" value="${paramMap.investor}"/>
          <a href="javascript:void(0)" class="scbtn" id="btn_search"> 搜索</a> 
          <input type="hidden" name="curPage" id="pageNum" />
          </form>
          </div>
         <div class="biaoge">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th>投资者</th>
    <th>借入总额</th>
    <th>还款总额</th>
    <th>已还本金</th>
    <th>已还利息</th>
    <th>待还本金</th>
    <th>待还利息</th>
    </tr>
    
    <s:if test="pageBean.page==null || pageBean.page.size==0">
	   <tr align="center">
		  <td colspan="7">暂无数据</td>
	   </tr>
	</s:if>
	<s:else>
	<s:iterator value="pageBean.page" var="bean">
	   <tr>
	    <td align="center">${bean.username} </td>
	    <td align="center"><strong>￥${bean.realAmount }</strong></td>
	    <td align="center">￥${bean.recivedPI}</td>
	    <td align="center">￥${bean.hasPrincipal}</td>
	    <td align="center">￥${bean.hasInterest}</td>
	    <td align="center">￥${bean.forPrincipal}</td>
	    <td align="center">￥${bean.forInterest}</td>
	  </tr>
	</s:iterator>
	</s:else>
          </table>
	 <div  class="page" >
		     <shove:page url="queryBorrowInvestorInfo.do" curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
		    	<s:param name="investor">${paramMap.investor}</s:param>
		    	
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
	
	
	

	function showAgree(){
	     var url = "getMessageBytypeId.do?typeId=1";
	     jQuery.jBox.open("post:"+url, "查看协议书", 600,400,{ buttons: {} });
	     
    }
    
    function jumpUrl(obj){
          window.location.href=obj;
       }
</script>

</body>
</html>
