<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<script src="script/jquery-1.7.1.min.js" type="text/javascript"></script>
 <script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
  <script>
  //分页
     $(function(){

     })
  
  
	   function initListInfo(praData) {
	         praData["paramMap.id"] = '${id}';
	         praData["paramMap.attention"] = '${attention}';
	   		$.post("userFrends.do",praData,initCallBack);
   		}
   		
   		function initCallBack(data){
   		  $("#frendlist").html("");
          $("#frendlist").html(data);
   		}
  </script>

    <div class="gzfirend" align="center">
    <ul>
    <s:if test="pageBean.page==null || pageBean.page.size==0">
         <div class="fenye">
  		  <div class="fenyemain">
    		</div>
   	 		</div>
		<tr align="center" class="gvItem">
		<td colspan="10">该用户还没关注的好友</td>
		</tr>
		</s:if>
		<s:else>
	<s:iterator value="pageBean.page" var="bean">
    <li><a href="userMeg.do?id=${tcmoduleId}">
    <shove:img src="${personalHead}" width="62" height="62" defaulImg="images/default-img.jpg" > </shove:img>
    </a><br/>
    <a href="userMeg.do?id=${tcmoduleId}">${aausername }</a>
    </li>
    </s:iterator>
	</s:else>
    </ul>
    </div>
     <div class="fenyemain" align="center">
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
</div>