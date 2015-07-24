<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/include/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="css/hhncss.css"></link>
    <link href="css/user.css" rel="stylesheet" type="text/css" />
    <script src="script/jquery-1.2.6.pack.js" type="text/javascript"></script>
    <script src="script/add_all.js" type="text/javascript"></script>
    <script src="script/MSClass.js" type="text/javascript"></script>
    <link rel="stylesheet" href="css/jbox/Gray/jbox.css" type="text/css"></link>
    <script type="text/javascript" src="css/popom.js"></script>
    <script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
    <script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
    <script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<div class="user-all">
  <div class="user-center">
      <div style=" width:1170px; margin:0px auto; padding-bottom:24px;"><img src="/images/v1/detail_logo_bg.jpg" width="1170" height="6"  alt=""/></div>
      <div style=" overflow:hidden">
          <div class="u-left">
              <jsp:include page="/include/menu_userManage.jsp"></jsp:include>
          </div>
    <div class="u-right"><div class="user-right">

       <%-- <div class="srh" id="srh_detail">
       <form action="queryMyPayingBorrowList.do" id="searchForm" >
        发布时间：<input type="text" id="startTime" name="startTime" value="${paramMap.startTime }" class="inp90 Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/> 
        到 
        <input type="text" id="endTime" value="${paramMap.endTime }" name="endTime" class="inp90 Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/> 
        <span style="margin-left:20px;">关键字：</span><input type="text" class="inp90" name="title" value="${paramMap.title }" id="title_s" />
        <a href="javascript:void(0)" class="scbtn" id="btn_search"> 搜索</a> 
       
        <a href="javascript:void(0);" class="scbtn" onclick="excel2()">导出excel表</a>
          <input type="hidden" name="curPage" id="pageNum" />
          </form></div>--%>
         <div class="biaoge" id="biaoge_details">
         <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th>标题</th>
    <th>借款协议</th>
    <th>借款类型</th>
    <th>借款金额</th>
    <th>年利率</th>
    <th>还款期限</th>
    <th>借款时间</th>
    <th>偿还本息</th>
    <th>已还本息</th>
    <th>未还本息</th>
    <th>操作</th>
    </tr>
  <s:if test="pageBean.page==null || pageBean.page.size<=0">
    <tr><td align="center" colspan="11">暂无记录</td></tr>
  </s:if>
  
  <s:else>
  
  <input id="borrow_des" name="borrow_des" value="" type="hidden"/>
    <s:iterator value="pageBean.page"  var="bean"> 
       <tr>
	    <td align="center"><a href="financeDetail.do?id=${bean.borrowId}"  target="_blank" >${bean.borrowTitle }</a></td>
	    <td align="center"><%--<a href="javascript:showAgree('${bean.borrowId}');">查看协议</a>--%>
	    <a href="getMessageBytypeId.do?typeId=15&&borrowId=${bean.borrowId}" target="_blank">查看协议</a>
	    </td>
	    <td align="center">${bean.borrowWay }</td>
	    <td align="center">${bean.borrowAmount }元</td>
	    <td align="center">${bean.annualRate }%</td>
	    <td align="center">${bean.deadline }
	    <s:if test="#bean.isDayThe == 1">
	      个月
	    </s:if>
	    <s:else>
	     天
	    </s:else>
	    </td>
	    <td align="center">${bean.publishTime}</td>
	    <td align="center">￥${bean.stillTotalSum }</td>
	    <td align="center">￥${bean.hasPI }</td>
	    <td align="center">￥${bean.hasSum }</td>
	    <td align="center"><a href="javascript:payingDetails(${bean.borrowId });" >还款明细</a></td>
	  </tr>
    </s:iterator>
  </s:else>
          </table>
           <div  class="page" >
	     <shove:page url="queryMyPayingBorrowList.do" curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
	    	<s:param name="startTime">${paramMap.startTime}</s:param>
	    	<s:param name="endTime">${paramMap.endTime}</s:param>
	    	<s:param name="title">${paramMap.title}</s:param>
	     </shove:page>
  </div>
         </div>
         <span id="borrow_details"></span>
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
	$('#li_7').addClass('on');
	$('.tabmain').find('li').click(function(){
		  $('.tabmain').find('li').removeClass('on');
        
     }); 
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
	

	
	function showAgree(borrowId){
    var url = "getMessageBytypeId.do?typeId=15&&borrowId="+borrowId;
	     jQuery.jBox.open("post:"+url, "查看协议书", 650,400,{ buttons: { } });
	     
    }
    
    function jumpUrl(obj){
          window.location.href=obj;
    }
       
    function excel2(){
       	window.location.href=encodeURI(encodeURI("exportSuccessBorrow.do?status=4"+"&&startTime="+$("#startTime2").val()+"&&endTime="+$("#endTime2").val()+"&&title="+$("#title2").val()));
    }
    
     function payingDetails(id){
        /*var url = "queryPayingDetails.do?borrowId="+id+"&status=1";
        window.location.href=url;*/
		 $("#borrow_des").attr("value",id);
		 $("#srh_detail").hide();
		 $("#biaoge_details").hide();
		 var param = {};
		 param["paramMap.borrowId"] = id;
		 param["pageBean.pageNum"]=1;
		 param["paramMap.status"] = "1";
        $.shovePost("queryPayingDetails.do",param,function(data){
          //alert(data);
	       $("#borrow_details").html(data);
	       //弹出框
	    });
     }
</script>

</body>
</html>
