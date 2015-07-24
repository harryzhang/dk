<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@page import="com.shove.web.util.ServletUtils"%>
<%@page import="com.sp2p.constants.IConstants"%>
<%@page import="com.shove.vo.ShoppingCart"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="shove" uri="/shove-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>

<div class="wdzh_next_left">
  <ul>
    <li><a href="home.do">账户总览</a></li>
    <li><a href="owerInformationInit.do">个人详细信息</a></li>
    <li><a href="registerChinaHHN.do">开设汇付账户</a></li>
    <li><a href="bankInfoSetInit.do">银行卡设置</a></li>
    <%--		<li><a href="querWorkData.do">工作认证信息</a></li>--%>
    <%--		<li><a href="userPassData.do">信用认证</a></li>--%>
    <li><a href="updatexgmm.do">安全中心</a></li>
    <!--    <li><a href="boundcellphone.do">手机绑定</a></li>-->
    <%--		<li><a href="szform.do">通知设置</a></li>--%>
    <%--		<li><a href="bankInfoSetInit.do">银行卡设置</a></li>--%>
    <li><a href="friendManagerInit.do">推广链接</a></li>
    <li><a href="myReferee.do">我的推荐人</a></li>
  </ul>
</div>
<script>
$(function(){
	$(".wdzh_top_ul li:first").addClass("wdzhxxk");
	}
)
</script>
