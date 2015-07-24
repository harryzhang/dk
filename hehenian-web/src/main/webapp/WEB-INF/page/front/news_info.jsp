<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<style>
.lbox {
	margin-right:10px;
}

 .dongtai {

	padding-left: 10px;

}

.lbox .dongtai ul li {
	line-height: 50px;
}

li {
	list-style: none;
	text-align: left;
}

.lbox .dongtai ul li a {
	color: #666;
}

.lbox .dongtai ul li a:hover {
	color: #E94718;
}

.lbox h2 {
	font-size: 18px;
	margin-top: 5px;
	display:block;
	text-align:left;
	padding-bottom:30px;
	border-bottom:1px solid #e5e5e5;
		padding-left: 10px;
}
</style>
<div class="lbox">
	<h2 style="">公司公告</h2>
	<div class="dongtai">
		<ul>
			<s:iterator value="#request.newsList" var="bean">
				<li style="	  border-bottom:1px solid #e5e5e5;;"><a href="frontNewsDetails.do?id=${bean.id }" title="${bean.title }"><shove:sub value="title" size="19" /> </a></li>
			</s:iterator>
		</ul>
	</div>
</div>
<!--中间右侧 结束-->

