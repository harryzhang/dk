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
						经纪人帐号(姓名)
					</th>
					<th scope="col">
						真实姓名
					</th>
					<th scope="col">
						总提成(元)
					</th>
					<th scope="col">
						已结算(元)
					</th>
					<th scope="col">
						未结算(元)
					</th>
					<th scope="col">
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
						<td align="center">
						${bean.userName}<br/>
							(${bean.realName})
						</td>
						<td>
							${bean.realName}
						</td>
						<td>
						${bean.totalMoney}
						</td>
						<td>
						${bean.hasPaySum }
						</td>
						<td>
							${bean.forPaySum }
						</td>
						
						<td>
						<s:if test="#bean.forPaySum > 0">
							<a style="cursor: pointer;" onclick="addAwardDetail(${bean.id })">
								结算
							</a>
							
						</s:if>
						<s:else>
						</s:else> 
						&nbsp;&nbsp;
							<a style="cursor: pointer;" onclick="showDetailById(${bean.id })">
								查看明细
							</a>
						
						</td>
					</tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>

	
	<script>
	  function addAwardDetail(uid){
	    var url = "addAwardDetailInit.do?userId="+uid;
        ShowIframe("结算",url,600,400);
	  }
	  
	  function showDetailById(uid){
	    var url = "queryAwardDetailByUserIdInit.do?userId="+uid;
        ShowIframe("查看明细",url,800,400);
	  }
	</script>