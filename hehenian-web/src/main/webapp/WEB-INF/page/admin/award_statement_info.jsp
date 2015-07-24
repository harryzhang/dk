<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<div id = "first"
					style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: auto; padding-top: 10px; background-color: #fff; display: none">
					<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="100" height="28" class="main_alll_h2">
									经纪人账号
								</td>
								<td width="2">
								${paramMap.username}
								</td>
								</tr>
							<tr>
							<td width="100" class="xxk_all_a">
							经纪人姓名
									</td>
									<td width="2">
										${paramMap.realName}
									</td>
							</tr>
							<tr>
							<td width="100" class="xxk_all_a">
							结算金额
									</td>
									<td width="2">
										<input type="text" value="${paramMap.notuse }" id="notuse" disabled="disabled"/>
										<input type="hidden" value="${paramMap.id }" id="id"/>
									</td>
							</tr>
							<tr>
							<td width="100" class="xxk_all_a">
							备注
									</td>
									<td width="2">
										<textarea rows="3" cols="20" id="remark"></textarea>
									</td>
							</tr>
								<tr>
							<td width="100" class="xxk_all_a">
									</td>
									<td width="2">
										<input type="button" value="结算" onclick="submitData()"/>
									</td>
							</tr>
					</table>
				</div>
				<div style="display: block" id="secend">
		<table id="gvNews" style="width: 85%; color: #333333; "
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<thead>
				<tr class=gvHeader>
					<th scope="col">
						序号
					</th>
					<th scope="col">
						经纪人
					</th>
					<th scope="col">
						姓名
					</th>
					<th scope="col">
						操作人
					</th>
					<th scope="col">
						操作金额
					</th>
					<th scope="col">
						备注
					</th>
					<th scope="col">
						操作时间 
					</th>
				</tr>
				</thead>
				<tbody id="tb"> 
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="7">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:iterator value="pageBean.page" var="bean" status="st">
					<tr class="gvItem">
						<td>
						${st.count }
						</td>
						<td>
						${economy}
						</td>
						<td>
						${realName }
						</td>
						<td>${ userName }</td>
						<td>
						${handleSum }
						</td>
						<td>
						${remark}
						</td>
						<td>
						<s:date name="#bean.checkTime" format="yyyy-MM-dd"/>
						</td></tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
		<shove:page curPage="${pageBean.pageNum}" showPageCount="10"
	pageSize="${pageBean.pageSize }" theme="jsNumber"
	totalCount="${pageBean.totalNum}"></shove:page>
	</div>

