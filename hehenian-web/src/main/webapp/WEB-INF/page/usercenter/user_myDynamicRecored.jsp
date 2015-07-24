<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %> 
<script src="script/jquery-1.7.1.min.js" type="text/javascript"></script>
 <script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
  <script>
  //分页
	   function initListInfo(praData) {
	         praData["paramMap.id"] = '${id}';
	   		$.post("queryUserRecoreIndex.do",praData,initCallBack);
   		}
   		
   		function initCallBack(data){
   		  $("#Dynamiclist").html("");
          $("#Dynamiclist").html(data);
   		}
  </script>
    <ul>
      <s:if test="pageBean.page!=null || pageBean.page.size>0">
    <s:iterator value="pageBean.page" var="finance">
    <li><span><s:date name="#finance.time" format="yyyy-MM-dd HH:mm:ss"/></span>${finance.url}</li> 
     </s:iterator>
  </s:if>
  <s:else>
     <li style="text-align: center;">没有数据</li>
  </s:else>
    </ul>
  <div class="fenyemain" align="center">
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
</div>
  