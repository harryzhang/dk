<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../css/popom.js"></script>
<script type="text/javascript" src="../css/admin/popom.js"></script>
	<div>
		<table id="help" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th scope="col">
					借款期限
					</th>
					<th scope="col">
						状态
					</th>
					<th scope="col">
						操作
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="8">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
					<s:iterator value="pageBean.page" var="bean" status="st">
						<tr class="gvItem">
							<td>
							${selectName }
							</td>
							<td>
								<s:if test="#bean.deleted==1">
									开启
								</s:if>
								<s:else>
									关闭
								</s:else>
							</td>
							<td>
								<s:if test="#bean.deleted==1">
									<a style="cursor: pointer;" id="${id }"  onclick="javascript:updatypeId(${id},2);">关闭</a> 
								</s:if>
								<s:else>
									<a style="cursor: pointer;" id="${id }"  onclick="javascript:updatypeId(${id},1)">开启</a> 
								</s:else>
								&nbsp;&nbsp;
								<a style="cursor: pointer;" ids="${id }" onclick="javascript:fff(this)">修改</a> 
								&nbsp;&nbsp;
							 
							</td>
						</tr>
					</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
<script type="text/javascript">

   function fff(data){
                var t = $(data).attr("ids");
		    	ShowIframe("修改",'updateDeadline.do?id='+t,600,300);
		    }
		    function ffff(f,d){
		    	var param = {};
		    	param["paramMap.id"]=d;
		    	param["paramMap.tagd"] = f;
		    	$.post("updateDeadlineRealM.do",param,function(data){
		    	if(data==1){
		    	  alert("修改成功");
		    	  window.location.reload();
		    	}else{
		    	alert("修改失败");
		    	}
		    	});
		      ClosePop();
		    }

		    function updatypeId(f,d){
		    	var param = {};
		    	param["id"]=f;
		    	param["typeId"] = d;
		    	$.post("updateselect.do",param,function(data){
		    	if(data==1){
		    	  alert("修改成功");
		    	  window.location.reload();
		    	}else{
		    	alert("修改失败");
		    	}
		    	});
		    }

</script>
<script>

</script>




	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
