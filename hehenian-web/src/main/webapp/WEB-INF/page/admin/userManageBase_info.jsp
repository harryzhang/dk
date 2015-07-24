<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<script type="text/javascript" src="../css/popom.js"></script>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript">
			$(function(){	
				$("#excel").click(function(){
				   window.location.href=encodeURI(encodeURI("exportusermanageinfo.do?paramMap.userName="+$("#userName").val()+"&paramMap.realName="+$("#realName").val()));
				
				});
				});
</script>

	<div>
		<table id="help" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 100px;" scope="col">
						序号
					</th>
						<th style="width: 100px;" scope="col">
						用户名
					</th>
						<th style="width: 100px;" scope="col">
						真实姓名
					</th>
						<th style="width: 100px;" scope="col">
						邮箱
					</th>
						<th style="width: 100px;" scope="col">
						QQ号码
					</th>
						<th style="width: 100px;" scope="col">
						手机号码
					</th>
						<th style="width: 100px;" scope="col">
						注册时间
					</th>
						<th style="width: 100px;" scope="col">
						最后登录时间
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
							<td>
							${username }
							</td>
							<td>
							${realName }
							</td>
							<td>
							${email }
							</td>
							<td>
							${qq }
							</td>
							<td>
							${cellPhone }
							</td>
							<td>
							${createTime }
							</td>
							<td>
							${lastIP }
							</td>
							<td>
								<a href="javascript:withdraw_check(${id });">修改</a> 
							</td>
						</tr>
					</s:iterator>
				</s:else>
				<tr><td><input id="excel" type="button" value="导出Excel" name="excel" /></td></tr>
			</tbody>
		</table>
	</div>
   <script>
	  function withdraw_check(wid){
			    var url = "queryUserInfo.do?id="+wid;
               ShowIframe("基本信息查看/修改",url,600,600);
			}
			
			
   </script>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
