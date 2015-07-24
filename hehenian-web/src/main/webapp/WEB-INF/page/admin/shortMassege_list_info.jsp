<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
	<div>
		<table id="help" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
				    <th style="width: 35px;" scope="col">
						选中
					</th>
					<th style="width: 50px;" scope="col">
						序号
					</th>
					<th style="width: 100px;" scope="col">
					    标题
					</th>
					<th style="width: 50px;" scope="col">
					   接收人
					</th>									
					<th style="width: 150px;" scope="col">
						内容
					</th>
					<th style="width: 80px;" scope="col">
						发送时间
					</th>
					<th style="width: 50px;" scope="col">
					  发送方式
					</th>	
					<th style="width: 50px;" scope="col">
					  状态
					</th>	
					<th style="width: 120px;" scope="col">
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
								<input id="gvNews_ctl02_cbID" class="downloadId" type="checkbox"
									value="${bean.id }" name="cb_ids" />
							</td>
							<td>
								<s:property value="#s.count+#counts"/>
							</td>
							<td>
								${bean.title}
							</td>
							<td>
							<s:if test="#bean.receiverType==1">所有人</s:if>
						<s:if test="#bean.receiverType==2">管理员</s:if>
						<s:if test="#bean.receiverType==3">批量发送</s:if>
					
							</td>
							<td>
								${bean.content}
							</td>
							<td>
								<s:date name="#bean.sendTime" format="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
								<s:if test="#bean.style==1">邮件</s:if>
						<s:if test="#bean.style==2">站内信</s:if>
						<s:if test="#bean.style==3">短信</s:if>
							</td>
							<td>
									<s:if test="#bean.status==1">已发送</s:if>
						<s:if test="#bean.status==2">草稿</s:if>
						<s:if test="#bean.status==3">待发送</s:if>
							</td>
							<td>
							<s:if test="#bean.status==1"><a href="javascript:del(${bean.id })">删除</a></s:if>
							<s:if test="#bean.status==2"><a href="javascript:del(${bean.id })">删除</a></s:if>
							<s:if test="#bean.status==3"><a href="sendMaseege.do?id=${bean.id }">发送</a></s:if>
							</td>
						</tr>
					</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	<table style="border-collapse: collapse; border-color: #cccccc"
		cellspacing="1" cellpadding="3" width="100%" align="center" border="1">
		<tbody>
			<tr>
				<td class="blue12" style="padding-left: 8px" align="left">
					<input id="chkAll" onclick="checkAll(this); " type="checkbox" value="checkbox" name="chkAll" />
					全选 &nbsp;&nbsp;&nbsp;&nbsp;
					<input id="btnDel" onclick="dels();" type="button"
						value="删除选中记录" name="btnDel" />
				</td>
			</tr>
		</tbody>
	</table>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
