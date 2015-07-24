<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<script type="text/javascript" src="../css/popom.js"></script>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../css/admin/popom.js"></script>
<link rel="stylesheet" href="../css/jbox/Gray/jbox.css" type="text/css"></link>
<script language="javascript" type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript">

			function edit(id){
			$.jBox("iframe:updateDownloadInit.do?id="+id, {
			    title: "编辑",
			    top:"2%",
			    width: 987,
			    height:610,
			    align:"center",
			    buttons: { '关闭': true }
			});
		}
		function closeMthod(){
			window.jBox.close();
			window.location.reload();
		}
	
	//取消--关闭弹窗
		function closeMthodes(){
				window.jBox.close();
		}
		function initCallBack(data){
	 		$("#dataInfo").html(data);
		}
</script>
	<div>
		<table id="help" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 50px;" scope="col">
						序号
					</th>
					<th style="width: 200px;" scope="col">
						标题
					</th>
					<th style="width: 150px;" scope="col">
						状态
					</th>
					<th style="width: 150px;" scope="col">
						上传者
					</th>									
					<th style="width: 80px;" scope="col">
						操作
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="8">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:set name="counts" value="#request.pageNum"/>
					<s:iterator value="pageBean.page" var="bean" status="s">
						<tr class="gvItem">
							<td>
							<s:property value="#s.count+#counts"/>
							</td>
							<td>
								${bean.title}
							</td>
							<td>
								<s:if test="#bean.state==1">
								   显示
								</s:if>
								<s:if test="#bean.state==2">
								   隐藏
								</s:if>
							</td>			
							<td>
								${bean.userName}
							</td>						
							<td>
								<a href="javascript:edit(${bean.id})" target="main">
								    编辑
								</a>
								&nbsp;&nbsp;
								<a href="javascript:del(${bean.id})" target="main">
								删除
								</a>
							</td>
						</tr>
					</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
