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
  <div class="lcnav">
    <div class="tab">
    <div class="srh">
    标题： <input id="borrowTitle" type="text"  name="borrowTitle" value="${borrowTitle}" class="inp200" /><a id="btn_title_search" href="javascript:void(0);" class="chaxun" style="margin-left:6px;">查询</a>
    </div>
<div class="tabmain">
  <ul><li onclick="goUrl('queryFrontAllDebt.do');">全部债权列表</li><li class="on">最近成功的债权转让</li>
  </ul>
    </div>
    </div>
    <div class="line">
    </div>
  </div>
  <div class="lcmain" >
    <div class="lcmain_l"  style="float:none; margin-left:0px; width:auto;">
		 <div class="zqsrh">
     <form id="searchForm" action="queryFrontSuccessDebt.do">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>待收金额：<s:select list="#{1:'3000元以下',2:'3000-5000',3:'5000-10000',4:'1万-2万',5:'2万-5万',6:'5万以上'}" name="debtSum" theme="simple" value="paramMap.debtSum" headerKey="" headerValue="全部"></s:select>
      </td>
    <td>拍卖底价：<s:select list="#{1:'3000元以下',2:'3000-5000',3:'5000-10000',4:'1万-2万',5:'2万-5万',6:'5万以上'}" name="auctionBasePrice" value="paramMap.auctionBasePrice" theme="simple" headerKey="" headerValue="全部"></s:select>
      </td>
    <td>拍卖模式：<s:select list="#{1:'明拍',2:'暗拍'}" name="auctionMode" value="paramMap.auctionMode" theme="simple" headerKey="" headerValue="全部"></s:select></td>
    <td>逾期状态：<s:select list="#{2:'逾期',1:'没有逾期'}" name="isLate" value="paramMap.isLate" theme="simple" headerKey="" headerValue="全部"></s:select></td>
    <td>发布时间：<s:select list="#{1:'1天内',3:'3天内',7:'7天内',10:'10天内',30:'30天内',31:'一个月以上'}" name="publishDays" value="paramMap.publishDays" theme="simple" headerKey="" headerValue="全部"></s:select>     
      <input id="pageNum" name="curPage" value="1" type="hidden"/>
      <a href="javascript:void(0);" id="btn_search" class="scbtn">搜索</a></td>
    </tr>
</table>
   </form>
   
    </div>
    <div class="lctab0" ><table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th align="center"><p>转让人</p></th>
    <th align="center"><p>借款人头像/用户名/信用指数</p></th>
    <th align="center"><p>借款标题</p></th>
    <th align="center"><p>是否有逾期</p></th>
    <th align="center"><p>竞拍方式</p></th>
    <th align="center"><p>债权总额/竞拍底价/年利率</p></th>
    <th align="center"><p>竞拍最高价</p></th>
    <th align="center"><p>竞拍者</p></th>
  </tr>
 <s:if test="pageBean.page==null || pageBean.page.size<=0">
    <tr><td align="center" colspan="8">暂无记录</td></tr>
  </s:if>
  
  <s:else>
  <s:iterator value="pageBean.page" var="bean">
  <tr>
    <td align="center" class="name" width="80"><a href="userMeg.do?id=${alienatorId}" target="_blank">${alienatorName}</a></td>
    <td class="tx"><a href="userMeg.do?id=${publisher}" target="_blank" >
    <shove:img src="${personalHead }" defaulImg="images/default-img.jpg" width="80" height="79"></shove:img></a>
      &nbsp;<a href="userMeg.do?id=${publisher}" target="_blank">${borrowerName }</a>&nbsp; <img src="images/ico_13.jpg" title="${creditrating }分" /></td>
    <td align="center"><a href="queryDebtDetailHHN.do?id=${id }" target="_blank" > ${borrowTitle }</a></td>
    <td align="center"><s:if test="#bean.isLate==2">有</s:if>
    <s:else>无</s:else></td>
    <td align="center"><s:if test="#bean.auctionMode ==1">
    		明拍
    	</s:if>
    	<s:else>
    		暗拍
    	</s:else>
    	</td>
    <td align="center" >${debtSum }元<br />${auctionBasePrice }元/${annualRate }%
  </td>
   
     <td align="center">${auctionHighPrice }元</td>
    <td align="center" class="fred">${auctionerName }</td>
  </tr>
  </s:iterator>
  </s:else>
  </table>
    
    <div  class="page" >
     <shove:page url="queryFrontSuccessDebt.do" curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
    	<s:param name="debtSum">${paramMap.debtSum}</s:param>
    	<s:param name="auctionBasePrice">${paramMap.auctionBasePrice}</s:param>
    	<s:param name="auctionMode">${paramMap.auctionMode}</s:param>
    	<s:param name="isLate">${paramMap.isLate}</s:param>
    	<s:param name="publishDays">${paramMap.publishDays}</s:param>
     </shove:page>
   
  </div></div>
    </div>


    </div>
  </div>
 

<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="script/nav-zq.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script>
$(function(){
    //样式选中
     $("#zq_hover").attr('class','nav_first');
	 $("#zq_hover div").removeClass('none');
	 
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
	 
	  $("#btn_title_search").click(function(){
	 	window.location.href = "queryFrontSuccessDebt.do?borrowTitle="+$("#borrowTitle").val();
	 });
});		     

function goUrl(url){
	window.location.href = url;
}



</script>
</body>
</html>
