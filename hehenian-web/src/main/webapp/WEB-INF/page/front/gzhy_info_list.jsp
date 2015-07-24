<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<script type="text/javascript">
  function deleteFriend(userId,attentionId){
      if(window.confirm("你确定要删除该好友吗？")){
          var param={};
          param["paramMap.userId"] =userId;
          param["paramMap.attentionId"] =attentionId;
        $.post("deleteuserFrend.do",param,function(data){
        	if(data == "virtual"){
					window.location.href = "noPermission.do";
					return ;
	     	}
          $("#userfrends").html(data);
        });
      }
     
  }
  
</script>

<!--中间右侧 开始-->
         <div class="boxmain2">
         <ul class="friend">
          <s:if test="pageBean.page==null || pageBean.page.size==0">
		    <tr align="center" class="gvItem">
		      <td colspan="10">该用户还没关注的好友</td>
		    </tr>
		 </s:if>
		 <s:else>		
          <s:iterator value="pageBean.page" var="bean"  >
            <li>
              <a href="userMeg.do?id=${tcmoduleId}">
              <shove:img  defaulImg="images/default-img.jpg" src="${personalHead}" width="62" height="62"  ></shove:img></a><br/>
              <a href="userMeg.do?id=${tcmoduleId}">${aausername }</a><br/>
              <a onclick="deleteFriend('${user.id}','${tcmoduleId}')"  class="sc">[删除]</a>
            </li>
          </s:iterator>
         </s:else>
         </ul>
        </div>
    <div class="fenye">
    <div class="fenyemain">
      <shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
   </div>
    </div>
            
     
     <!--中间右侧 结束-->


 

