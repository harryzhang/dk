<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
          <div class="lctab" style="padding:0 10px;">
             <div class="newlist">
               <ul>
                <s:if test="pageBean.page==null || pageBean.page.size==0">
                      暂无内容
                </s:if>
                <s:else>
                  <s:iterator value="pageBean.page" var="bean" status="sta">
                    <li>
                      <span><s:date name="#bean.publishTime" format="yyyy-MM-dd HH:mm:ss" /></span>
                      <a href="frontQuerySuccessStoryDetail.do?id=${bean.id}">${bean.title}</a>
                    </li>
                  </s:iterator>
                </s:else>
                              
              </ul>
            </div>
           <div class="fenye">
             <div class="fenyemain">
                <shove:page curPage="${pageBean.pageNum}" showPageCount="8" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}">
				</shove:page>
             </div>
           </div>
        </div>
