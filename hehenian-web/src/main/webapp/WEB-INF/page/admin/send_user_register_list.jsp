<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<%@ include file="/include/includeJs.jsp"%>
<script type="text/javascript">
			var idFlag = "";
			var setAccountTypeFlag = "";
			function set_accountType(id,setAccountType){
				idFlag = id;
				setAccountTypeFlag = setAccountType;
				if("1" == setAccountType){
					if(confirm("请确认是否取消代理人账户?")){
						$.post("updateUserAccountType.do?id="+id,set_accountTypeCallBack);
					}else{
						return;
					}
				 		
				}else{
					if(confirm("请确认是否设为代理人账户?")){
						$.post("updateUserAccountType.do?id="+id,set_accountTypeCallBack);
					}else{
						return;
					}
				}
				
		 	}	
			function set_accountTypeCallBack(data, id){
				 var result = data.msg;
			 	 if("Y" == result){
			 		 alert("操作成功！");
			 		 if("1" == setAccountTypeFlag){
			 			$('#do' + idFlag).text("设为代理人账户");
			 			setAccountTypeFlag = "0";
			 		 }else{
			 			$('#do' + idFlag).text("取消代理人账户");
			 			setAccountTypeFlag = "1";
			 		 }
			 		idFlag = "";
			 	 }else{
			 		alert("操作失败！");
			 	 }
			 	 //window.location.href=encodeURI(encodeURI("userListAfterSetAcconutType.do"));
			}
			
		function checkSends(){
		 		//获取id集合
		 		var stIdArray = [];
	 			$("input[name='cb_ids']:checked").each(function(){
	 				stIdArray.push($(this).val());
	 			});
	 			
	 			if(stIdArray.length == 0){
	 				alert("请选择需要导出的信息");
	 				return ;
	 			}
	 		
	 			var ids = stIdArray.join(",");
	 			var param={};
	 			param["paramMap.id"]=ids;
	 			$.post("exportUserRegisterx.do",param,function(data){
	 			});
	 			
		 	}
		 	
		 	function allSends(){
		 		//获取id集合
		 		var stIdArray = [];
	 			$("input[name='allid']").each(function(){
	 				stIdArray.push($(this).val());
	 			});
	 			var ids = stIdArray.join(",");
	 			var param={};
	 			param["paramMap.id"]=ids;	 			
	 			$.post("exportUserRegisterx.do",param,function(data){
	 			});
		 	}
		 
		 //选择所有
		 	function checkAll(e){
		   		if(e.checked){
		   			$(".downloadId").attr("checked","checked");
		   		}else{
		   			$(".downloadId").removeAttr("checked");
		   		}
   			}
   			
   	$(function(){
      $("#excel").click(function(){   
      			//获取id集合
		 		var stIdArray = [];
	 			$("input[name='cb_ids']:checked").each(function(){
	 				stIdArray.push($(this).val());
	 			});
	 			
	 			if(stIdArray.length == 0){
	 				alert("请选择需要导出的信息");
	 				return ;
	 			}
	 		
	 			var ids = stIdArray.join(",");  
                    window.location.href=encodeURI(encodeURI("exportUserRegisterx.do?ids="+ids));
        });
  });
		
</script>
<div>
	<table id="help" style="width: 100%; color: #333333;" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class=gvHeader>
				<th style="width: 30px;" scope="col">选中</th>
				<th style="width: 80px;" scope="col">会员号</th>
				<th style="width: 90px;" scope="col">用户名</th>
				<th style="width: 90px;" scope="col">手机号码</th>
				<th style="width: 90px;" scope="col">推荐人</th>
				<th style="width: 90px;" scope="col">用户来源</th>
				<th style="width: 90px;" scope="col">注册时间</th>
				<th style="width: 90px;" scope="col">注册IP</th>
				<th style="width: 90px;" scope="col">最后登陆时间</th>
				<th style="width: 90px;" scope="col">操作</th>
			</tr>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="10">暂无数据</td>
				</tr>
			</s:if>
			<s:else>
				<s:set name="counts" value="#request.pageNum" />
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td align="center"><input id="gvNews_ctl02_cbID" class="downloadId" type="checkbox" value="${bean.id }" name="cb_ids" /></td>
				<!-- 		<td align="center"><s:property value="#s.count+#counts" /></td>  -->
						<td align="center">${bean.id}</td>
						<td align="center"><span style="cursor: pointer;color:#3366CC;" onclick="userInfoAlert(${id },'${username }');"> ${bean.username} </span></td>
						<td align="center">${bean.mobilePhone}</td>
						<td align="center">${bean.refferee}</td>
						<td align="center">
						
<%--						<s:if test="#bean.source==0">自动导入</s:if> <s:elseif test="#bean.source==1">网站注册</s:elseif>--%>
						<s:if test="#request.source == 0">
									小贷公司
								</s:if> <s:elseif test="#request.source == 1">
									网站注册
								</s:elseif> <s:elseif test="#request.source == 2">
									彩生活用户
								</s:elseif> <s:elseif test="#request.source == 3">
									净值用户
								</s:elseif>
						</td>
						<td align="center"><s:date format="yyyy-MM-dd HH:mm:ss" name="#bean.createTime" /></td>
						<td align="center">${bean.registerIp}</td>
						<td><s:date format="yyyy-MM-dd HH:mm:ss" name="#bean.lastDate" /></td>
						<td><span id="do${bean.id}" onclick="set_accountType(${bean.id},'${bean.accountType}');" style="cursor:pointer;">
						<s:if test="#bean.accountType == 1">
									取消代理人账户
								</s:if> <s:else>
									设为代理人账户
								</s:else> 
						</td>
						
						
						</span></td>
					</tr>
				</s:iterator>
			</s:else>
		</tbody>
	</table>
</div>
<table style="border-collapse: collapse; border-color: #cccccc" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
	<tbody>
		<tr>
			<td align="left">&nbsp; <input id="chkAll" onclick=checkAll(this); type="checkbox" value="checkbox" name="chkAll" /> &nbsp;全选&nbsp;&nbsp;&nbsp;<input id="excel" type="button"
				onclick="checkSend()" value="导出Excel" name="excel" /></td>
		</tr>
	</tbody>
</table>
<br>
<script>
	
</script>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
