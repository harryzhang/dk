<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<style>
.lctab .newlist { }
.lctab .newlist ul li {	line-height: 42px;background-image: url(images/neiye1_333.jpg);background-repeat: repeat-x;	background-position: left bottom;padding-right: 15px;width: 760px;text-align: left;border-bottom: 1; list-style:none; font-size:16px;}
.lctab .newlist ul li span {color: #999;float: right;}
.lctab .newlist ul li a {color: #666; padding-left: 10px;display: block;}
.lctab .newlist ul li a:hover {	color: #E94718;	}
.fenye {text-align: center;	padding-top: 10px;	padding-bottom: 10px;font-size: 13px;}
.fenye .fenyemain {	line-height: 40px;	display: inline-block;	color: #666;}
.fenye .fenyemain a {	color: #666;vertical-align: baseline;}
.fenye .fenyemain a:hover {	color: red;}
.fenye .fenyemain a.yema {	line-height: 18px;color: #666;text-align: center;display: inline-block;	height: 18px;	width: 18px;margin-right: 4px;	border: 1px solid #e5e5e5;}
.fenye .fenyemain a.yema:hover {color: #FFF;background-color: #148dd9;	height: 20px;width: 20px;	border-top-width: 0px;border-right-width: 0px;border-bottom-width: 0px;	border-left-width: 0px;line-height: 20px;}
.fenye .fenyemain a.yema.on {line-height: 20px;	color: #FFF;background-color: #148dd9;height: 20px;	width: 20px;	border-top-width: 0px;border-right-width: 0px;border-bottom-width: 0px;	border-left-width: 0px;}
</style>
<!--中间右侧 开始-->
<div class="lctab">
	<div class="newlist">
		<ul>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
                      	没有网站公告
                </s:if>
			<s:else>
				<s:iterator value="pageBean.page" var="bean" status="sta">
					<li><span><s:date name="#bean.publishTime" format="yyyy-MM-dd" /> </span> <a href="frontNewsDetails.do?id=${bean.id}">${bean.title}</a>
					</li>
				</s:iterator>
			</s:else>

		</ul>
		<div class="fenye">
			<div class="fenyemain">
				<shove:page curPage="${pageBean.pageNum}" showPageCount="8" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}">
				</shove:page>
			</div>
		</div>
	</div>

</div>



<!--中间右侧 结束-->




