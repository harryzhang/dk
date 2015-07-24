<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="../css/jbox/Gray/jbox.css" type="text/css"></link>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../css/popom.js"></script>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script language="javascript" type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript">
		 	
		 	 //批量删除
	    function deleteReviewList(){
	       //获取id集合
		 		var stIdArray = [];
	 			$("input[name='cb_ids']:checked").each(function(){
	 				stIdArray.push($(this).val());
	 			});
	 			
	 			if(stIdArray.length == 0){
	 				alert("请选择需要删除的信息");
	 				return ;
	 			}
	 		
	 			var ids = stIdArray.join(",");
	 			var param={};
	 			param["paramMap.id"]=ids;
	 			
	     	 if(confirm("确认删除？")){
		 	 $.ajax( {  
	         	type : 'POST',  
	         	url : 'deleteUserReview.do',  
	         	data :param,
	         	success : function(data) { 
		            if(data == "success"){
		               alert("删除成功！");  
		            }else{
		               alert("删除失败！");  
		            }
		            reloadList();
	         	},  
	         	error : function(data) { 
	             alert("删除成功！");
	             reloadList();
	         	}  
	       });
	      } 
	  } 
		 	
		 	 //删除
	    function deleteReview(id){
		 	if(confirm("确认删除？")){
		 	param["paramMap.id"]=id;
		 	$.ajax( {  
	         	type : 'POST',  
	         	url : 'deleteUserReview.do',  
	         	data : param,
	         	success : function(data) { 
	            if(data == "success"){
	               alert("删除成功！");  
	            }else{
	               alert("删除失败！");  
	            } 
	            reloadList();
	         	},  
	         	error : function(data) { 
	            	alert("删除成功!");
	            	reloadList();
	         	}  
	       });
	      } 	  
	    } 	 
		 	
		 	//返回到列表页
		    function reloadList()
		    {
		     	window.location.href = "findUserReviewIndex.do";
		    }
		    
		 
		 //选择所有
		 	function checkAll(e){
		   		if(e.checked){
		   			$(".downloadId").attr("checked","checked");
		   		}else{
		   			$(".downloadId").removeAttr("checked");
		   		}
   			}
   			
		 	
		 	//批量审核
		 	 function updateStatusList(){
	       //获取id集合
		 		var stIdArray = [];
	 			$("input[name='cb_ids']:checked").each(function(){
	 				stIdArray.push($(this).val());
	 			});
	 			
	 			if(stIdArray.length == 0){
	 				alert("请选择需要删除的信息");
	 				return ;
	 			}
	 		
	 			var ids = stIdArray.join(",");
	 			var param={};
	 			param["paramMap.id"]=ids;
	 			param["paramMap.status"]=1;
	     	 if(confirm("确认审核？")){
		 	 $.ajax( {  
	         	type : 'POST',  
	         	url : 'updateUserReview.do',  
	         	data :param,
	         	success : function(data) { 
		            if(data == "1"){
		               alert("审核成功！");  
		            }else{
		               alert("审核失败！");  
		            }
		            reloadList();
	         	},  
	         	error : function(data) { 
	             alert("审核成功！");
	             reloadList();
	         	}  
	       });
	      } 
	  } 

	// 跳转详情页面
	function updateStatusShow(id) {
		$.jBox("iframe:findByUserReview.do?id=" + id, {
			title : "用户审核处理",
			width : 679,
			height : 500,
			buttons : {'关闭' : true}
		});
	}
	
	//弹出窗口关闭
	function closeMthod(){
			window.jBox.close();
			window.location.reload();
		}
		 	
</script>
<div>
	<table id="help" style="width: 100%; color: #333333;" cellspacing="1"
		cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class=gvHeader>
				<th style="width: 25px;" scope="col">
					选中
				</th>
				<th style="width: 50px;" scope="col">
					序号
				</th>
				<th style="width: 100px;" scope="col">
					评论者
				</th>
				<th style="width: 100px;" scope="col">
					被评论者
				</th>
				<th style="width: 100px;" scope="col">
					评论内容
				</th>
				<th style="width: 100px;" scope="col">
					评论时间
				</th>
				<th style="width: 100px;" scope="col">
					状态
				</th>
				<th style="width: 100px;" scope="col">
					操作
				</th>
			</tr>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="9">
						暂无数据
					</td>
				</tr>
			</s:if>
			<s:else>
				<s:set name="counts" value="#request.pageNum" />
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td align="center">
							<input id="gvNews_ctl02_cbID" class="downloadId" type="checkbox"
								value="${bean.id }" name="cb_ids" />
						</td>
						<td align="center">
							<s:property value="#s.count+#counts" />
						</td>
						<td align="center">
							<span style="cursor: pointer;"
								onclick="updateStatusShow(${bean.id});"> ${bean.userName}
							</span>
						</td>
						<td align="center">
							${bean.reviewName}
						</td>
						<td align="center">
							${bean.msgContent}
						</td>
						<td align="center">
							<s:date format="yyyy-MM-dd HH:mm:ss" name="#bean.msgTime" />
						</td>
						<td align="center">
							<s:if test="#bean.status==0">等待处理</s:if>
							<s:if test="#bean.status==2">未通过</s:if>
							<s:elseif test="#bean.status==1">通过</s:elseif>
						</td>
						<td>
							<a href="javascript:updateStatusShow(${bean.id });" target="main">处理
							</a>
							<a href="javascript:deleteReview(${bean.id})">删除</a>
						</td>
					</tr>
				</s:iterator>
			</s:else>
		</tbody>
	</table>
</div>
<table style="border-collapse: collapse; border-color: #cccccc"
	cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
	<tbody>
		<tr>
			<td align="left">
				&nbsp;
				<input id="chkAll" onclick=checkAll(this); type="checkbox"
					value="checkbox" name="chkAll" />
				&nbsp;全选&nbsp;&nbsp;&nbsp;
				<input id="updateStatusList" type="button"
					onclick="updateStatusList()" value="选中项通过" name="updateStatusList" />
				&nbsp;&nbsp;&nbsp;
				<input id="excel" type="button" onclick="deleteReviewList()"
					value="选中项删除" name="excel" />
			</td>
		</tr>
	</tbody>
</table>
<br>
<script>
	
</script>

<shove:page curPage="${pageBean.pageNum}" showPageCount="10"
	pageSize="${pageBean.pageSize }" theme="jsNumber"
	totalCount="${pageBean.totalNum}"></shove:page>

