<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav>
  <ul class="bs db_f footer_nav">
    <li class="bf1"><a href="<c:url value='/app/elend/welcome'/>"><span>首页</span></a></li>
    <li class="bf1"><a href="<c:url value='/app/elend/personalCenter'/>"><span>个人中心</span></a></li>
    <li class="bf1"><a href="tel:4008-303-737"><span>电话咨询</span></a></li>
    <%-- <li class="bf1"><a href="<c:url value='/app/credit/getCredit'/>"><span>人品加油站</span></a></li> --%>
  </ul>
</nav>

<script src="${fileServerUrl }/app_res/js/libs/zepto.js?v=${jsversion}"></script>

