<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>添加管理组</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript">
			$(function(){
				$(".top").click(function(){
					var id = $(this).val();
					if($(this).attr("checked") == "checked"){
						$(".top_"+id).attr("checked",true);
					}else{
						$(".top_"+id).attr("checked",false);
					}
					
				});
				$(".parent").click(function(){
					var id = $(this).val();
					var a =$(this).attr("class").split(" ");
					if($(this).attr("checked") == "checked"){
						$(".parent_"+id).attr("checked",true);
						$("#"+a[0]).attr("checked",true);
					}else{
						$(".parent_"+id).attr("checked",false);
						
					}
					
					if(!this.checked){
						var topId = $(this).attr("parentId");
						$("#top_"+topId).attr("checked",this.checked);
					}
				});
				$(".bottom").click(function(){
					var a =$(this).attr("class").split(" ");
					if(this.checked){
						$("#"+a[0]).attr("checked",true);
						$("#"+a[2]).attr("checked",true);
					}
				});
			
				$("#btn_save").click(function(){
					var stIdArray = [];
					$("input[name='rightsId']:checked").each(function(){
						stIdArray.push($(this).val());
					});
					if(stIdArray.length == 0){
		 				alert("请选择权限!");
		 				return ;
	 				}
	 				param["ids"] = stIdArray.join(",");
	 				param["roleName"] = $("#roleName").val();
	 				param["description"] = $("#description").val();
	 				$.shovePost("addRole.do",param,function(data){
	 					if(data.msg==1){
	 						alert("添加成功！");
	 						window.location.href = 'queryRoleList.do';
	 						return ;
	 					}
	 					alert(data.msg);
	 				})
				});
				
			});
			
			
		</script>
	</head>
	<body>
	<input type="hidden" id="categoryIds" value="${request.categoryIds }" />
	<input type="hidden" id="auditionCandidatesId" value="${request.id }" />
				<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td height="28"  class="xxk_all_a">
									<a href="queryRoleList.do">管理组列表</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="100" class="main_alll_h2">
									<a href="addRoleInit.do">添加管理组</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="100" height="28"  class="xxk_all_a">
									<a href="queryAdminInit.do">管理员列表</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="100"  class="xxk_all_a">
									<a href="addAdminInit.do">添加管理员</a>
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
						</table>
					</div>
					 
					<div style="width: auto; background-color: #FFF; padding: 10px;">
						<table width="100%" border="0" cellspacing="1" cellpadding="3">
							<s:set name="max" >0</s:set>
								<tr>
									<td style="height: 25px;padding-left: 10px;" align="right" class="blue12" width="100px;">
										管理组名称：
									</td>
									<td align="left" class="f66">
										<input type="text" value="${name }" id="roleName"  />
									</td>
								</tr>
								<tr>
									<td style="height: 25px;padding-left: 10px;" align="right" class="blue12" width="100px;">
										管理组描述：
									</td>
									<td align="left" class="f66">
										<textarea type="text" value="${description }" id="description" cols="40" rows="4" ></textarea>
									</td>
								</tr>
								<tr>
									<td style="height: 25px;padding-left: 10px;" align="right" class="blue12" width="100px;">
										权限分配：
									</td>
									<td class="f66">
						<s:iterator value="rightsList.{?#this.id<0}" var="bean">
							<input class="top" name="rightsId" id="top_${bean.id}" type="checkbox" value="${bean.id}" style="vertical-align: middle;"/>${bean.summary }<br/>
							<s:iterator value="rightsList.{?#this.parentID==#bean.id}" var="sBean">
								&nbsp;&nbsp;&nbsp;<input class="top_${bean.id} parent" name="rightsId" id="parent_${sBean.id }" parentId="${sBean.parentId }" type="checkbox" value="${sBean.id}" style="vertical-align: middle;" />
								${sBean.summary }<s:iterator value="rightsList.{?#this.parentID==#sBean.id}" var="sbBean">&nbsp;&nbsp;&nbsp;
								<input class="top_${bean.id} bottom parent_${sBean.id}" name="rightsId" parentId="${sbBean.parentId }"  type="checkbox" value="${sbBean.id}"  style="vertical-align: middle;"/>
								${sbBean.summary }</s:iterator>
								<br/>
							</s:iterator>
							<br/>
		             </s:iterator>
									</td>
								</tr>
								<s:set name="max" >${sta.count }</s:set>
							
							<tr>
								<td colspan="2" align="left" style="padding-left: 30px;">
									<button id="btn_save"
										style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px"></button>
									&nbsp;
								</td>
							</tr>
						</table>
						<br />
					</div>
				</div>
			</div>
	</body>
</html>
