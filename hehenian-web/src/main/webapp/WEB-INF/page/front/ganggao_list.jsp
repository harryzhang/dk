<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="/include/taglib.jsp"%>


<h2>
	<span><a href="initNews.do">更多></a> </span>网站公告
</h2>
<div class="gg_main">
	<ul>
		<s:iterator value="#request.newsList" var="bean">
			<li style="overflow : hidden"><span> <s:date name="publishTime" format="yyyy-MM-dd" /> </span><a href="frontNewsDetails.do?id=${bean.id }" title="${bean.title }"><shove:sub
						value="title" size="14" /> </a></li>
		</s:iterator>
	</ul>
</div>

