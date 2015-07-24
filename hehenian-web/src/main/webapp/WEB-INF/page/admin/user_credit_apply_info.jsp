<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>

<%@ include file="/include/includeJs.jsp"%>
<script type="text/javascript">
<!--
	//进入额度申请详情
	function select_CreditInfo(id,ti){
	
		$.jBox("iframe:querycreditMsg.do?uId="+id+"&ti="+ti, {
	    title: "额度申请详情11111",
	    width: 600,
	    height: 596,
	    top:25,
	    buttons: { '关闭': true }
		});
		
	}
	
	//弹出窗口关闭
	function closeMthod(){
			window.jBox.close();
			window.location.reload();
	}
	//关闭帐号详情
	function closeMthodInfo(id){
		window.jBox.close();
		window.location.href='queryPerUserCreditAction.do?userId='+id;
	}
	
//-->
</script>
	<div>
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th scope="col">
						序号
					</th>
					<th scope="col">
						用户账号
					</th>
					<th scope="col">
						原来额度
					</th>
					<th scope="col">
						申请额度
					</th>
					<th scope="col">
						申请时间
					</th>
					<!--<th scope="col">
						跟踪审核
					</th>-->
					<th scope="col">
						状态
					</th>
						<th scope="col">
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
						<td align="center">
							<a href="queryPerUserCreditAction.do?userId=${id }">${uername}</a>
						</td>
						<td>
							${creditLimit}
						</td>
						<td>
					     ${applyAmount }
						
						<!-- 
						  <s:if test="#bean.auditStatus==1">
							等待审核
							</s:if>
							<s:elseif test="#bean.status==3">
							审通过
							</s:elseif>
							<s:else>
							审核通过
							</s:else>
						
							<s:date name="#bean.addDate" format="yyyy-MM-dd HH:mm:ss" />
							 -->
						</td>
						<td>
							<s:date name="#bean.applyTime" format="yyyy-MM-dd hh:mm:ss" />	
						</td>
							<!--<td>
	                      
	                      	<s:if test="#bean.tausername!=null"> ${tausername }</s:if>			    
								<s:else>未分配</s:else>
						</td>-->
						<td>
							
				     	<s:if test="#bean.applystatus==1">
						审核中
						</s:if>
						<s:elseif test="#bean.applystatus==2">
						失败
						</s:elseif>
						<s:elseif test="#bean.applystatus==3">
						成功
						</s:elseif>
						<s:else>
					     --
						</s:else>
							
					</td>
					<td>
						
					    
					    <s:if test="#bean.applystatus==1">
					    <span onclick="select_CreditInfo(${id},${tcid });" style="cursor:pointer;">审核</span>
<!--						<a href="querycreditMsg.do?uId=${id }&ti=${tcid}">审核</a>-->
						</s:if>
						<s:elseif test="#bean.applystatus==2">
						<span onclick="select_CreditInfo(${id},${tcid });" style="cursor:pointer;">查看</span>
<!--						<a href="querycreditMsg.do?uId=${id }&ti=${tcid}">查看</a>-->
						</s:elseif>
						<s:elseif test="#bean.applystatus==3">
						<span onclick="select_CreditInfo(${id},${tcid });" style="cursor:pointer;">查看</span>
<!--						<a href="querycreditMsg.do?uId=${id }&ti=${tcid}">查看</a>-->
						</s:elseif>
						<s:else>
					     --
						</s:else>
					</td>
					</tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
