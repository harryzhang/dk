<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<script type="text/javascript">
       function mediaDetail(param){
      
          $.post("frontMediaReportDetails.do","id="+param,function(data){
                  $("#showcontent").html("");
	              $("#showcontent").html("<h3>"+data.title+"</h3>"+
	                             "<p class='time'></p>"+
	                           "<p class='zw'>"+data.content+"</p>");
          });
       }
</script>

 <!--中间右侧 开始-->
      <h3>媒体报道</h3>
    <ul class="meiti">
    <s:if test="pageBean.page==null || pageBean.page.size==0">
	   <tr align="center">
		  <td colspan="10">暂无数据</td>
	   </tr>
	</s:if>
    <s:else>
	<s:iterator value="pageBean.page" var="bean">
     <li style="height: 80px;overflow: hidden">
       <div class="pic" >
         <a href="javascript:mediaDetail(${bean.id})"><img src="${bean.imgPath}" width="180" height="80" /></a></div>        
     
         <a href="javascript:mediaDetail(${bean.id})" style="width: 50px;color:gray; overflow: hidden;" >${bean.title}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:date name="#bean.publishTime" format="yyyy-MM-dd HH:mm:ss" />
         </a><br/>
         <span style="color: gray;" >${bean.content}&nbsp;&nbsp;&nbsp;
         <a href="javascript:mediaDetail(${bean.id})">查看详情</a></span>
        
     </li>
     </s:iterator>
     </s:else>
    </ul>
    <div class="fenye">
    <div class="fenyemain">
     <shove:page curPage="${pageBean.pageNum}" showPageCount="10" funMethod="doMtbdJumpPage" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
    </div>
    </div>
            
     
     <!--中间右侧 结束-->

