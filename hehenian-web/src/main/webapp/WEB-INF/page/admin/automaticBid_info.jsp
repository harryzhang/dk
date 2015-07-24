<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>

<link rel="stylesheet" href="../css/jbox/Gray/jbox.css" type="text/css"></link>
<script type="text/javascript" src="../css/popom.js"></script>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../css/admin/popom.js"></script>
<script language="javascript" type="text/javascript"
	src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript">
	$(function(){
		$(".s").each(function(){
			if($(this).html().trim()=="关")
			 	 $(this).parent().children().first().html("");
		});
	});

	function checkAll(e) {
		if (e.checked) {
			$(".selectId").attr("checked", "checked");
		} else {
			$(".selectId").removeAttr("checked");
		}
	}

	function displayCloseBid(id){
		$.jBox("iframe:displayCloseBid.do?id="+id, {
		    title: "关闭用户自动投标",
		    top:"10%",
		    width: 400,
		    height: 440,
		    buttons: { }
		});
	}	

	function batchCloseBid() {
		var stIdArray = [];
 		$("input[name='aid']:checked").each(function(){
 			stIdArray.push($(this).val());
 		});
 			
 		if(stIdArray.length == 0){
 			alert("请选择需要关闭的记录");
 			return;
 		}
 		
		var ids = stIdArray.join(",");
		var param={};
		param["paramMap.id"]=ids;
		param["pageBean.pageNum"] = $("#pageNum").val();
 			
     	if(confirm("确认关闭？")){
		 	$.ajax({  
		        type : 'POST',  
		        url : 'closeAutomaticBid.do',  
		        data : param,
		       	success : function(data) { 
		           if(data == "1"){
		              alert("关闭成功！");  
		           }else{
		              alert("关闭失败！");  
		           }
		           window.location.href = "automaticBidInit.do";
		       	},  
		       	error : function(data) { 
		           alert("请求失败！");
		           window.location.href = "automaticBidInit.do";
		       	}
	       });
       } 
	}
	
	//弹出窗口关闭
	function closeMthod(){
			window.jBox.close();
			window.location.reload();
	}
		
	//取消--关闭弹窗
	function closeMthodes(){
			window.jBox.close();
	}
</script>
	<div>
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th scope="col">
						选中
					</th>
					<th scope="col">
						用户名
					</th>
					<th scope="col">
						自动投标状态
					</th>
					<th scope="col">
						单笔投标金额
					</th>
					<th scope="col">
						账户保留金额
					</th>
					<th scope="col">
						设置时间
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
							<input id="aid" type="checkbox" class="selectId" name="aid" value="${bean.id }"/>
						</td>
						<td>
							${bean.username }
						</td>
						<td class="s">
							<s:if test="#bean.bidStatus==2">开</s:if>
							<s:elseif test="#bean.bidStatus==1">关</s:elseif>
						</td>
						<td>
					    	${bean.bidAmount }
						</td>
						<td>
							${bean.remandAmount }
						</td>
						<td>
							<s:date name="#bean.bidSetTime" format="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
						    <s:if test="#bean.bidStatus==2">
								<a href="javascript:displayCloseBid(${bean.id})">关闭</a>
							</s:if>
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
	<div>
		<table style="border-collapse: collapse; border-color: #cccccc"
			cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
			<tbody>
				<tr>
					<td align="left">
						&nbsp;&nbsp;
						<input id="chkAll" type="checkbox" name="chkAll" onclick="checkAll(this)" />全选&nbsp;&nbsp;
						<input id="batchClose" type="button" onclick="batchCloseBid()" value="批量关闭" name="batchClose" class="scbtn"/>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
