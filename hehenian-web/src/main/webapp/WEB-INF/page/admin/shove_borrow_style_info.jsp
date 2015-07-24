<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<script type="text/javascript" src="../css/popom.js"></script>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../css/admin/popom.js"></script>

<link rel="stylesheet" href="../css/jbox/Gray/jbox.css" type="text/css"></link>

<script language="javascript" type="text/javascript"
	src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript">

			function edit(id){
						$.jBox("iframe:updateShoveBorrowStyleInit.do?id="+id, {
						    title: "编辑",
						    top:"10%",
						    width: 550,
						    height: 440,
						    buttons: { '关闭': true }
						});
					}					
						
					function initCallBack(data){
				 		$("#dataInfo").html(data);
					}
						//弹出窗口关闭
					function closeMthod(){
							window.jBox.close();
							window.location.reload();
					}
						
						//取消--关闭弹窗
					function closeMthodes(){
							window.jBox.close();
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
					<th style="width: 80px;" scope="col">
						还款方式
					</th>
					<th style="width: 50px;" scope="col">
						标识符
					</th>
					<th scope="col"  style="width: 50px;" align="center">
						标题
					</th>
					<th style="width: 50px;" scope="col">
						状态
					</th>
					<th style="width: 200px;" scope="col">
						算法信息
					</th>
					<th style="width: 100px;" scope="col">
						操作
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="7">暂无数据</td>
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
								${name }
							</td>
							<td>
								${nid}
							</td>
							<td align="center">
						   		${title }
							</td>
							<td align="center">
							<s:if test="%{status == 1}">
								开启
							</s:if>
						    <s:else>
						    	关闭
						    </s:else>
							</td>	
							<td align="center">
						   		${contents }
							</td>	
							<td>
							<a href="javascript:edit(${id})">修改</a>
							</td>		
						</tr>
					</s:iterator>
				</s:else>
			</tbody>
		</table>
	
	</div>
	
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
		<div style="height: 10px;"></div>