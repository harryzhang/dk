<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<div style="padding: 15px 10px 0px 10px;">
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="4">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td align="center">
							用户编号:
						</td>
						<td align="center">
							${id}
						</td>
						<td align="center">
							用户名:
						</td>
						<td align="center">
							${username}
						</td>
					</tr>
					<tr class="gvItem">
						<td align="center">
							认证资料数量:
						</td>
						<td align="center">
							${counts}
						</td>
						<td align="center">
							真实姓名:
						</td>
						<td align="center">
							${realname}
						</td>
					</tr>
					<tr class="gvItem">
						<td align="center">
							授信金额:
						</td>
						<td align="center">
							${creditLimit}
						</td>
						<td align="center">
							激活状态:
						</td>
						<td align="center">
							未激活
						</td>
					</tr>
					<tr class="gvItem">
						<td align="center">
							预授信用积分:
						</td>
						<td align="center">
							${creditrating}
						</td>
						<td align="center">
							注册状态:
						</td>
						<td align="center">
							已注册
						</td>
					</tr>
					<tr class="gvItem">
						<td align="center">
							创建时间:
						</td>
						<td align="center">
							<s:date name="createTime" format="yyyy-MM-dd HH:ss:mm"/>
						</td>
						<td align="center">
							激活时间:
						</td>
						<td align="center">
							 
						</td>
					</tr>
				</s:iterator>
				<table>
					<tbody>
						<tr>
							<td>用户信用额度激活</td>
						</tr>
						<tr>
							<td>激活额度:</td>
							<td><input type="radio" value="1"/>激活</td>
						</tr>
						<tr>
							<td>添加备注:</td>
							<td><textarea rows="3" cols="20"></textarea> </td>
						</tr>
					</tbody>
				</table>
				
				<input type="button" id="btn" value="确定"/>
				</s:else>
			</tbody>
		</table>
</div>
<script type="text/javascript">
	
</script>

