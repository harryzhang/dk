<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<script type="text/javascript" src="../css/popom.js"></script>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../css/admin/popom.js"></script>

		<link rel="stylesheet" href="../css/jbox/Gray/jbox.css"
			type="text/css"></link>
		<script language="javascript" type="text/javascript"
			src="../script/jbox/jquery.jBox-2.3.min.js"></script>
		<script type="text/javascript">
			function edit(id){
				$.jBox("iframe:updatePlatformCostbyIdInit.do?id="+id, {
				    title: "修改",
				    top:"2%",
				    width: 450,
				    height:300,
				    buttons: { '关闭': true }
				});
			}
			function closeMthod(){
				window.jBox.close();
				window.location.reload();
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
					<th style="width: 35px;" scope="col">
						序号
					</th>
					<th style="width: 200px;" scope="col">
						描述
					</th>
					<th style="width: 200px;" scope="col">
						金额或者金额百分比
					</th>
					<th scope="col" align="center">
						备注
					</th>
					<th style="width: 100px;" scope="col">
						操作
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="9">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:set name="counts" value="#request.pageNum"/>
					<s:iterator value="pageBean.page" var="bean" status="s">
						<tr class="gvItem">
							<td>
								<s:property value="#s.count+#counts"/>
							</td>
							<td align="left">
								${costName }
							</td>
							<td>
							<s:if test="#bean.costMode==1">
								${costFee}%
							</s:if>
							<s:else>
								${costFee}元
							</s:else>
							</td>
							<td align="left">
						   		${remark }
							</td>	
							<td>
							<a href="javascript:edit(${id })">修改</a>
							
							</td>		
						</tr>
					</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
	