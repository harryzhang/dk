<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>

<!-- 左导航开始 -->
<div class="nav_bottom">
  <div class="nav_b_left"></div>
  <div class="nav_b_right"></div>
</div>
<div class="nymain">
  <div class="kfbox">
    <div class="l_nav">
     <div class="l_navmain">
    <h2>帮助中心</h2>
    <ul class="help">
    <s:if test="pageBean.page!=null || pageBean.page.size>0">
      <s:iterator value="pageBean.page" id="callcenter" var="bean">
	    <s:if test="#bean.id==#request.typeId">
		    <li class="on"><a href="callcenter.do?cid=${bean.id }">${bean.helpDescribe }</a></li>
	    </s:if>
	    <s:else>
	    	<li><a href="callcenterList.do?cid=${bean.id}">${bean.helpDescribe }</a></li>
	    	
	    </s:else>
	    <!--<li class="on"><a href="kfzx.html">新手入门</a></li>-->
		<!--  <li><a href="kfzx-1.html">我要借款</a></li>-->
		<!-- <li ><a href="kfzx-2.html">我要贷款</a></li>-->
		<!-- <li><a href="kfzx-3.html">充值提现</a></li>-->
		<!-- <li><a href="kfzx-4.html">其它</a></li>-->
	</s:iterator>
	</s:if>
    </ul>
    <h2><span><a href="kfzx-info.html">更多>></a></span>联系客服</h2>
    <ul class="lxkf"><li>
      <a href="javascript:void(0);"><img src="images/neiye7_03.jpg" width="72" height="72" /></a> </li>
      <li>
      <a href="javascript:void(0);"><img src="images/neiye7_03.jpg" width="72" height="72" /></a> </li>
      <li>
      <a href="javascript:void(0);"><img src="images/neiye7_03.jpg" width="72" height="72" /></a> </li>
      <li>
      <a href="javascript:void(0);"><img src="images/neiye7_03.jpg" width="72" height="72" /></a> </li>
    </ul>
    </div>
    </div>
