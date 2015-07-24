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
<div class="zqbanner">
<img src="images/neiye-zq_13.jpg" width="981" height="270" /></div>
<div class="zqmain">
  <div class="l-nav">
  <h2 style="padding-left:0px;"><a href="queryFrontAllDebt.do">查看债权转让列表</a></h2>
  <div class="question">
  <h3>相关问题 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  
  <a href="callcenter.do?type=true&cid=5"  >    更多>></a></h3>
  <div class="qt-main">
  
  <s:if test="#request.lists ==null || #request.lists.size<=0">
        暂无数据
    </s:if>
    <s:else>
    <ul >
	    <s:iterator value="#request.lists"  var="bean">
		     <li>
		     <a href="callcenter.do?type=true&cid=5">${bean.questionDescribe }</a>
		      </li>
	    </s:iterator>
     </ul>
    </s:else>
 
  
  <!--  <ul> <li><a href="#">什么是债权转让？</a></li>
<li><a href="#">如何转让已有的债权？</a></li>
<li><a href="#">什么是债权转让？</a></li>
<li><a href="#">如何转让已有的债权？</a></li>
<li><a href="#">如何转让已有的债权？</a></li></ul>-->


  </div>
  </div>
  </div>
  <div class="r-main">
  <ul><li  style="width: 340px;">
    <div class="pic">
      <img src="images/neiye-zq_19.jpg" width="87" height="108" />
      </div>
       <p>债权人在借款过程中遇到急用款的情况，通过债权转让更加<strong>及时的回收</strong>资金以备不时之需</p>
  </li><li style="width: 340px;">
    <div class="pic">
      <img src="images/neiye-zq_21.jpg" width="87" height="108" />
      </div>
        <p>债权人在借款过程中遇到逾期情况进行债权转让，最大程度的<strong>降低资金的损失度</strong></p>
  </li><li  style="width: 340px;">
    <div class="pic">
      <img src="images/neiye-zq_31.jpg" width="87" height="108" />
      </div>
         <p>竞拍者进行债权转入，可以低价转入债权，<strong>增加了资金收益</strong></p>
  </li>
  </ul>
  </div>
  <div class="clear"></div>
</div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/nav-zq.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script>
$(function(){
    //样式选中
     $("#zq_hover").attr('class','nav_first');
	 $("#zq_hover div").removeClass('none');
});		     
</script>
</body>
</html>
