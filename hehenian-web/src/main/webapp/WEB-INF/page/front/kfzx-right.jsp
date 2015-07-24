<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<div class="box">
      <h2><s:property value="#request.curDes" default="新手入门" ></s:property>aa</h2>
      <div class="boxmain">
      <ul>
	      <s:if test="pageBean.page!=null || pageBean.page.size>0">
	      <s:iterator value="pageBean.page"  var="bean" status="sta">
	       <li><a id="qs" href="javascript:showAnswer(${bean.questionId})" onclick="javascript:cp(${bean.questionId});"><strong>问：</strong><span id="qss${bean.questionId}">${bean.question}</span></a>
	      </li>
	      </s:iterator>
	      <s:else>
	          暂无数据
	      </s:else>
	      </s:if>
      </ul>
      </div>
      </div>
      <div class="fenye">
    <div class="page">
	<p>
	   	<shove:page url="callcenter.do" curPage="${pageBean.pageNum}" showPageCount="4" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
	   		<s:param name="cid" >${cid}</s:param>
		</shove:page>
    </p>
</div> 
<div class="box" align="left"><h2>
  <span id="qs1">答：如何注册网站会员</span></h2>
	<div class="boxmain" align="left">
	<p class="txt">
	<span id="dataInfo">通过会员注册页面，完成必填项目进行网站会员注册</span></p></div></div>
    </div>