<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
                                 催款进度记录<p/>
		<table id="gvNews" style="width: 100%; color: #333333;" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 35px;" scope="col">
						序号
					</th>
					<th style="width: 150px;" scope="col">
						署名备注
					</th>
					<th style="width: 150px;" scope="col">
						催收时间
					</th>
					<th style="width: 150px;" scope="col">
						催收结果
					</th>
					<th style="width: 150px;" scope="col">
						操作
					</th>
				</tr>
				<s:if test="#request.collectionList==null || #request.collectionList.size==0">
					<tr align="center" class="gvItem">
						<td colspan="5">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:iterator value="#request.collectionList" var="collection">
					<tr class="gvItem">
						<td align="center">
							${collection.id}
						</td>
						<td align="center">
							${collection.remark}
						</td>
						<td align="center">
							${collection.collectionDate}
						</td>
						<td align="center">
							${collection.colResult}
						</td>
						<td>
							<a href="delCollection?id=${collection.id}">删除</a>
						</td>
					</tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>