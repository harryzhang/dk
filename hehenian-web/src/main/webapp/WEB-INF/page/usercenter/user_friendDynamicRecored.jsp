<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %> 
<script src="script/jquery-1.7.1.min.js" type="text/javascript"></script>
 <script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
  <script>
  //分页
  
	   function initListInfo(praData) {
	         praData["paramMap.id"] = '${id}';
	   		$.post("queryfrendrecoredIndex.do",praData,initCallBack);
   		}
   		
   		function initCallBack(data){
   		  $("#userDynamiclist").html("");
          $("#userDynamiclist").html(data);
   		}
  </script>
    <ul>
      <s:if test="pageBean.page!=null || pageBean.page.size>0">
    <s:iterator value="pageBean.page" var="finance">
    <li><span>${finance.time }</span>${finance.url}</li> 
     </s:iterator>
  </s:if>
  <s:else>
     <li style="text-align: center;">暂无记录</li>
        <div class="fenye">
  <div class="fenyemain">
   </div>
  </div>
  </s:else>
    </ul>
  <div class="fenyemain" align="center">
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
</div>
  