<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>客服管理-内容维护</title>
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
				$('#search').click(function(){
					param["pageBean.pageNum"] = 1;
					//param["paramMap.kefuname"] = $("#kefuname").val();
					param["paramMap.username"] = $("#username").val();
					param["paramMap.kefuname"] = '${paramMap.name}';
					param["paramMap.unfor"]=$("#unfor").val();
				   initListInfo(param);
				});	 
				param["paramMap.kefuname"] = '${paramMap.name}';
				param["paramMap.unfor"]=$("#unfor").val();
				var time = 1;
				param["pageBean.pageNum"] = 1;
				initListInfo(param);		
				
							
			});
			
			function initListInfo(praData){
			    praData["paramMap.kefuid"]='${paramMap.id}'	
		 		$.shovePost("queryKefuForInfo.do",praData,initCallBack);
		 	}
		 	
		 	function initCallBack(data){
		 	  
				$("#dataInfo").html(data);
			}
			
			function kefufors(data){
		 		if(!window.confirm("确定吗?")){
		 			return;
		 		} 
		 		var stIdArray = [];
	 			$("input[name='cb_ids']:checked").each(function(){
	 				stIdArray.push($(this).val()+"_"+$("#gg_"+$(this).val()).val());
	 			});
	 			if(stIdArray.length == 0){
	 				alert("请选择需要分配的内容");
	 				return ;
	 			}
	 			var ids = stIdArray.join(",");
	 			var paramMap = {};
	 			paramMap["paramMap.ids"]=ids;
	 			paramMap["paramMap.kid"] = '${paramMap.id}';
	 			//paramMap["paramMap.isF"] = $("#borrowWay").val();
	 			//window.location.href= "deleteKefu.do?id="+ids;
	 			$.shovePost("addUserForkefubyId.do",paramMap,function(data){
	 				if(data=='1'){
		 			  	alert("分配成功");
		 			  window.location.href="queryKefuForInit.do?id="+'${paramMap.id}';
		 			}else{
	             		alert("分配失败");
		 			}
	 			});
	 			
		 	}
             /*取消分配*/
              
              function kefuforsoff(data){
              
              	if(!window.confirm("确定吗?")){
		 			return;
		 		} 
		 		var stIdArray = [];
	 			$("input[name='cb_ids']:checked").each(function(){
	 				stIdArray.push($(this).val()+"_"+$("#gg_"+$(this).val()).val());
	 			});
	 			if(stIdArray.length == 0){
	 				alert("请选择需要分配的内容");
	 				return ;
	 			}
	 			var ids = stIdArray.join(",");
	 			var paramMap = {};
	 			paramMap["paramMap.ids"]=ids;
	 			//paramMap["paramMap.kid"] = '${paramMap.id}';
	 			//paramMap["paramMap.isF"] = $("#borrowWay").val();
	 			//window.location.href= "deleteKefu.do?id="+ids;
	 			$.shovePost("addUserForkefubyId.do",paramMap,function(data){
	 				if(data=='1'){
		 			  	alert("分配成功");
		 			  window.location.href="queryKefuForInit.do?id="+'${paramMap.id}';
		 			}else{
	             		alert("分配失败");
		 			}
	 			});
	 			
              
              }


			
		 	function kefufor(id){
		 		if(!window.confirm("确定要删除吗?")){
		 			return;
		 		}
	 			//window.location.href= "deleteKefu.do?id="+id;
	 			$.shovePost("queryKefuForInfo.do","",function(data){
                  	 			
	 			});
		 	}
		 	
		 	function checkAll(e){
		   		if(e.checked){
		   			$(".downloadId").attr("checked","checked");
		   		}else{
		   			$(".downloadId").removeAttr("checked");
		   		}
   			}
		 	
		</script>
		<script>
	  function sure(data){
		     var  t = data;
		     var tt = $('#gg_'+t).val();
		    param["paramMap.selectid"]= tt;
		    if(tt==""){
		       alert("请选择跟踪人员");
		       return false;
		    }
		    param["paramMap.userId"]= t;
		   $.post("addUserForkefu.do",param,function(data){
		        if(data=1){alert("确认成功")
		        window.location.reload();
		      }else{alert("确认失败")}
		  })
	  
	  }
		</script>
	</head>
	<body>
		<div id="right"
			style="background-image: url(../images/admin/right_bg_top.jpg); background-position: top; background-repeat: repeat-x;">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100" height="28" align="center"
								class="xxk_all_a">
								<a href="queryKefuListInit.do">客服管理</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100" align="center"   class="xxk_all_a">
								<a href="addKefuInit.do">添加客服</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
								<td width="100" align="center" class="main_alll_h2">
							分配
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
					</table>
					<div
						style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: auto; padding-top: 10px; background-color: #fff;">
						<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
							width="100%" border="0">
							<tbody>   
							 <tr>
							 <td>
							 <span style="font-family: serif ;font-family: 12px;"> 客服姓名</span>：${paramMap.name }
							 &nbsp;&nbsp;
							 <span style="font-family: serif ;font-family: 12px;"> 用户名</span>：
							 &nbsp;
							 <input type="text" id="username">
							 &nbsp;&nbsp;
							 <s:select list="#{'2':'已分配','1':'未分配','3':'全部'}" id="unfor"></s:select>
							 <input id="search" type="button" value="查找" name="search" />
							 &nbsp;&nbsp;
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
