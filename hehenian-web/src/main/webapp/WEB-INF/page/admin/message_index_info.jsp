<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<%@ include file="/include/includeJs.jsp"%>
<div>
	<table id="help" style="width: 100%; color: #333333;" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class=gvHeader>
				<th scope="col">序号</th>
				<th scope="col">栏目名称</th>
				<th scope="col">操作</th>
				<th scope="col">排序</th>
			</tr>
		</tbody>
		<s:if test="list==null || list.size==0">
			<tr align="center" class="gvItem">
				<td colspan="8">暂无数据</td>
			</tr>
		</s:if>
		<s:else>
			<s:iterator value="list" var="bean" status="st">
				<tr class="gvItem">
					<td id="messageSort_${bean.sort}">${st.count}</td>
					<td id="messageColumName_${bean.sort}">${bean.columName}</td>
					<td><a id="update_${bean.sort}" href="javascript:edit('updateMessageInit.do',${bean.id})"> 编辑 </a> &nbsp;&nbsp; <a id="update_${bean.sort}" href="javascript:preview(${bean.id});">
							预览 </a></td>
					<td>
						<!-- <a id="upMove_${bean.sort}" onclick="upMove(${bean.sort})" > --> <a href="javascript:void(0);" id="upMove_${bean.sort}" onclick="moveUp(this)"> 上移 </a> &nbsp;&nbsp; <!-- <a id="downMove_${bean.sort}" onclick="downMove(${bean.sort})" > -->
						<a href="javascript:void(0);" id="upMove_${bean.sort}" onclick="moveDown(this)"> 下移 </a></td>
				</tr>
			</s:iterator>
		</s:else>
	</table>
</div>
