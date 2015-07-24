<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>


 <!--中间右侧 开始-->
    <div class="boxmain">
    <s:if test="pageBean.page==null || pageBean.page.size==0">
           暂无数据
      </s:if>
      <s:else>
      <h3>友情链接</h3>
       <ul  class="kefu">
          <s:iterator value="pageBean.page" var="bean">
              <li >
                <a target="_blank" href="${bean.companyURL}" class="tx"><img style="padding: 3px; border: 1px solid #ddd;margin:3px; " src="${bean.companyImg}" width="140" height="60" /></a><br/>
		        <a   target="_blank" href="${bean.companyURL}">${bean.companyName }</a><br/>		     
              </li>
          </s:iterator>
        </ul>
      </s:else>
      </div>
     
     <!--中间右侧 结束-->

