<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>


<%@ include file="/include/includeJs.jsp"%>
<script type="text/javascript">

	function select_UserInfo(id){
		
		$.jBox("iframe:queryUserManageBaseInfoinner.do?i="+id, {
		    title: "用户信息详情",
		    width: 679,
		    height: 500,
		    buttons: {}
		});
	}

	function initCallBack(data){
 		$("#dataInfo").html(data);
	}
		//弹出窗口关闭
   		function close(){
   			 ClosePop();  			  
   		}

	//弹出窗口关闭
	function closeMthod(id){
			window.jBox.close();
			if(id==1)
			window.location.reload();
	}		

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
		                    window.location.href=encodeURI(encodeURI("exportUserInfo.do?ids="+ids));
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
				<th style="width: 80px;" scope="col">真实姓名</th>
				<th style="width: 90px;" scope="col">手机号码</th>
				<th style="width: 110px;" scope="col">身份证</th>
				<th style="width: 80px;" scope="col">用户来源</th>
				<th style="width: 80px;" scope="col">注册时间</th>
				<th style="width: 80px;" scope="col">可用金额</th>
				<th style="width: 80px;" scope="col">冻结金额</th>
				<th style="width: 80px;" scope="col">待收金额</th>
				<th style="width: 80px;" scope="col">操作</th>
			</tr>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="12">暂无数据</td>
				</tr>
			</s:if>
			<s:else>
				<s:set name="counts" value="#request.pageNum" />
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td><input id="gvNews_ctl02_cbID" class="downloadId" type="checkbox" value="${bean.id }" name="cb_ids" /></td>
						<td>${id }</td>
						<td><span style="cursor: pointer;color:#3366CC;" onclick="select_UserInfo(${id});"> ${username } </span></td>
						<td>${realName }</td>
						<td>${cellPhone }</td>
						<td>${idNo }</td>
						<td><s:if test="#request.source == 0">
									小贷公司
								</s:if> <s:elseif test="#request.source == 1">
									网站注册
								</s:elseif> <s:elseif test="#request.source == 2">
									彩生活用户
								</s:elseif> <s:elseif test="#request.source == 3">
									净值用户
								</s:elseif></td>
						<td><s:date name="#bean.createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
						<td>${usableSum }</td>
						<td>${freezeSum }</td>
						<td>${dueinSum }</td>
						<td><span onclick="select_UserInfo(${id});" style="cursor:pointer;">查看</span></td>
					</tr>
				</s:iterator>
			</s:else>
		</tbody>
	</table>
</div>
<div>
	&nbsp;&nbsp; <input id="chkAll" onclick=checkAll(this); type="checkbox" value="checkbox" name="chkAll" /> &nbsp;全选&nbsp;&nbsp;&nbsp;<input id="excel" type="button" onclick="checkSend()"
		value="导出Excel" name="excel" />

</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
