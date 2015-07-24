<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
   <nav>
   <ul class="pf w10 bottom_nav db_f">
       <li class="bf1"><a href="<c:url value='/app/mhk/getLoanPerson.do'/>"><em>订单中心</em></a></li>
       <li class="bf1"><a href="<c:url value='/app/mhk/loanManage/overdueRepayPage'/>"><em>贷后管理</em></a></li>
       <li class="bf1"><a href="<c:url value='/app/mhk/performList.do'/>"><em>业绩查询</em></a></li>
      </ul>
    </nav>
<script src="${fileServerUrl }/app_res/js/libs/zepto.js?v=${jsversion}"></script>
<script src="${fileServerUrl }/app_res/js/app.js?v=${jsversion}"></script>




