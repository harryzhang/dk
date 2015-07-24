<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
	<div>
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 35px;" scope="col">
						序号
					</th>
					<th scope="col" style="width: 200px;">
						经纪人姓名(姓名)
					</th>
					<th scope="col">
						${level==3?'投资人':'理财人' }
					</th>
					<th scope="col">
						是否有效
					</th>
					<th scope="col">
						真实姓名
					</th>
					<th scope="col">
						信用积分
					</th>
					<th scope="col">
						会员积分
					</th>
					<th scope="col">
						身份证
					</th>
					<th scope="col">
						投资奖励总提成
					</th>
					<th style="width: 70px;" scope="col">
						操作
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="10">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:set name="counts" value="#request.pageNum"/>
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td>
					<s:property value="#s.count+#counts"/>
						</td>
						<td align="center">
						${bean.level2userName }<br/>
							(${bean.level2realName})
						</td>
						<td>
							${userName}
						</td>
						<td>
						<s:if test="#bean.enable==2">
						已过期
						</s:if>
						<s:else>
						有效
						</s:else>
						</td>
						<td>
						${realName }
						</td>
						<td>
							${creditrating }
						</td>
						<td>
							${rating}
						</td>
						<td>
						${idNo }
						</td>
						<td>
						${level2moneys }
						</td>
						<td>
						<a href="queryIoRInit.do?level2userId=${level2userId }&userId=${userId}&level=${level}" >查看</a>
						</td>
					</tr>
				</s:iterator>
				</s:else>
				<tr >
				<td  colspan="2"><strong>合计项</strong></td>
				<td colspan="1" align="right" >投资奖励提成合计:</td>
				<td  colspan="3" >￥<s:if test="%{paramMap.sumLevel2moneys==''}">0</s:if><s:else>
		                <s:property value="paramMap.sumLevel2moneys" default="0"/></s:else></td>
				<td colspan="2" align="right" >当前页的投资奖励提成合计:</td>
				<td  align="center" >￥${coumtMomeys}</td>			
				</tr>
			</tbody>
		</table>
	</div>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
