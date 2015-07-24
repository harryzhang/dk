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
		  	
		    $("#search").click(function(){
		    param["pageBean.pageNum"] = 1;
				initListInfo(param);
		    });
		   
	    });
	    //加载留言信息
	   function initListInfo(praData) {
	   		param["paramMap.userName"] = $("#userName").val();
	   		param["paramMap.startUseableSum"] = $("#startUseableSum").val();
	   		param["paramMap.endUseableSum"] = $("#endUseableSum").val();
	   		param["paramMap.realName"] = $("#realName").val();
	   		param["paramMap.startAllSum"] = $("#startAllSum").val();
	   		param["paramMap.endAllSum"] = $("#endAllSum").val();
	   		$.shovePost("queryGroupUserInfo.do",praData,initCallBack);
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
   		
   		//add by houli 查看用户详情
   		function showDetail(id){
   		   window.showModalDialog("queryUserManageBaseInfoinnerU.do?i="+id,window,"dialogWidth=800px;dialogHeight=600px");
   		   //var url = "queryUserManageBaseInfoinner.do?i="+id;
           //ShowIframe("用户详情",url,600,600);
   		}
   		
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
	 		if(!window.confirm("确定要添加所有选中记录?")){
	 			return;
	 		}
	 		var ids = "";
	 		var usernames = "";
			$(".adminId").each( function(i, n){
				if(n.checked){
					ids += n.value+",";
					usernames += n.title+","
				}
			});
			ids = ids.substring(0,ids.lastIndexOf(","));
		 	usernames = usernames.substring(0,usernames.lastIndexOf(","));
		 	window.returnValue = {"ids":ids,"names":usernames};
		 	window.close();
		 	
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
		<div id="right"
			style="background-image: url; background-position: top; background-repeat: repeat-x;">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
							<td width="100" height="28" align="center" bgcolor="#CC0000"
									class="white12">
									<a href="#">用户查询</a>
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
								用户名：<input id="userName"  type="text"> &nbsp;&nbsp;
									可用金额：<input id="startUseableSum"  type="text"> -- <input id="endUseableSum"  type="text">&nbsp;&nbsp;<br /><br />
									真实名字：<input id="realName"  type="text"> &nbsp;&nbsp;
									总金额：<input id="startAllSum"  type="text"> -- <input id="endAllSum" type="text"> &nbsp;&nbsp;
								<input id="search" type="button" value="确定"  />	
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
