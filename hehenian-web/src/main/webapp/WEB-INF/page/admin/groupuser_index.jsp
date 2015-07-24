<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" language="javascript">
	    $(function(){
	    	param["pageBean.pageNum"]=1;
		    initListInfo(param);
		  	
		    $("#bt_search").click(function(){
		    param["pageBean.pageNum"] = 1;
				initListInfo(param);
		    });
		   
	    });
	    //加载留言信息
	   function initListInfo(praData) {
	        //modify by houli 添加了一个按照用户组搜索的功能，这里传一个参数
	        praData['paramMap.groupName'] = $("#groupName").val();
	   		$.shovePost("queryGroupInfo.do",praData,initCallBack);
   		}
   		
   		function sendMsg(groupId){
   			window.showModalDialog("sendGroupMessageInit.do?paramMap.groupId="+groupId,window,"dialogWidth=800px;dialogHeight=600px");
   		}
   		function sendEmail(groupId){
   			window.showModalDialog("sendGroupEmailInit.do?paramMap.groupId="+groupId,window,"dialogWidth=800px;dialogHeight=600px");
   		}
   		
   		
   		function initCallBack(data){
		 	$("#dataInfo").html(data);
   		}
   		
   		//分页
   		function pageInfo(pageId){
   			param["pageBean.pageNum"] = pageId;
   			initListInfo(param);
   		}
   		
   		//删除单个
   		function deleteById(ids){
	 		if(window.confirm("确定要删除吗?")){
	 			window.location.href = "deleteGroup.do?id="+ids;
	 		}
   		}
   		
   		//修改用户组信息 add by houli
   		function modifyById(id){
	 		$.shovePost("modifyGroup.do",praData,initCallBack);
   		}
   		//---end---
   		
   		//判断是否有选中项
   		function checked(str){
   			var c = 0;
   			$(".adminId").each( function(i, n){
				if(n.checked){
					c = 1;
				}
			});
			if(c==0){
				alert("请先选中您要"+str+"的项！");
				return false;
			}
			return true;
   		}
   		
   		function addGroupInit(){
   			window.location.href = "addGroupInit.do";
   		}
   		//删除多个选中项
   		function deleteAll(){
   			if(!checked("删除")){
   				return;
   			}
	 		if(!window.confirm("确定要删除所有选中记录?")){
	 			return;
	 		}
	 		var ids = "";
			$(".adminId").each( function(i, n){
				if(n.checked){
					ids += n.value+",";
				}
			});
			ids = ids.substring(0,ids.lastIndexOf(","));
		 	window.location.href = "deleteGroup.do?id="+ids;
   		}

   		//全选
   		function checkAll(e){
	   		if(e.checked){
	   			$(".adminId").attr("checked","checked");
	   		}else{
	   			$(".adminId").removeAttr("checked");
	   		}
   		}
	</script>

	</head>
	<body>
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
							<tr>
							<td width="100" height="28"  class="main_alll_h2">
									<a href="#">用户组列表</a>
								</td>
								<td width="2px">
									&nbsp;
								</td>
								<!-- modify by houli 暂时不需要这个功能项
								  <td width="100" height="28" align="center" bgcolor="#8594A9"
									class="white12">
									<a href="queryDrawCashManagerInit.do">提现管理</a>
								</td>
								<td width="2px">
									&nbsp;
								</td>-->
								<td width="100" height="28"  class="xxk_all_a">
									<a href="queryGroupUsersInit.do">检索成员</a>
								</td>
								<td>
									&nbsp;
								</td>
						</tr>
						</table>
				</div>
				<div
					style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
						<tbody>
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
								<!-- add by houli  添加一个按照用户组检索的功能 -->
								  用户组：&nbsp;&nbsp;
										<input id="groupName" name="paramMap.groupName" type="text"/>
										&nbsp;&nbsp;
										<input id="bt_search" type="button" value="查 找"  />
								</td>
							</tr>
						</tbody>
					</table>
					<span id="dataInfo"> </span>
				</div>
			</div>
		</div>
		
	
	</body>
</html>
