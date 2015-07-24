<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
	<head>
		<title>帮助中心-栏目管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" src="../css/admin/popom.js"></script>
		<link rel="stylesheet" href="../css/jbox/Gray/jbox.css" type="text/css"></link>
		<script language="javascript" type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
		<script type="text/javascript" language="javascript" src="../script/edit.js">
</script>
		<script type="text/javascript">
			
			function del(id){
				if(!window.confirm("确定要删除吗?")){
		 			return;
		 		}
				window.location.href='deleteHelpCategory.do?typeId='+id;
			}
			
			function moveUp(_a){
			    var _row = _a.parentNode.parentNode;
			    //如果不是第一行，则与上一行交换顺序
			    var _node = _row.previousSibling;
			    if(_node == null)
			      alert("已经是第一行了");
			    while(_node && _node.nodeType != 1){
			        _node = _node.previousSibling;
			    }
			    if(_node){
			        swapNode(_row,_node);
			    }
			}
			
			function moveDown(_a){
			    var _row = _a.parentNode.parentNode;
			    //如果不是最后一行，则与下一行交换顺序
			    var _node = _row.nextSibling;
			    while(_node && _node.nodeType != 1){
			        _node = _node.nextSibling;
			    }
			    if(_node){
			        swapNode(_row,_node);
			    }
			}
			
			function swapNode(node1,node2){
			    //获取父结点
			    var _parent = node1.parentNode;
			    //获取两个结点的相对位置
			    var _t1 = node1.nextSibling;
			    var _t2 = node2.nextSibling;
			    //将node2插入到原来node1的位置
			    if(_t1)_parent.insertBefore(node2,_t1);
			    else _parent.appendChild(node2);
			    //将node1插入到原来node2的位置
			    if(_t2)_parent.insertBefore(node1,_t2);
			    else _parent.appendChild(node1);
			}
			
			function saveOrder(){
			  var stIdArray = [];
			  $("#gvItem").find("tr").each(function(){
			      //alert($(this).attr("id"));
			      stIdArray.push($(this).attr("id"));
			  });
			  var ids = stIdArray.join(",");
			  $.shovePost("updateCategoryIndex.do",{ids:ids},function(data){
			      if(data == 1){
			         alert("排序失败");
			         return;
			      }
			      alert("排序成功");
			  });
			  
			}
			
			
    	</script>

		<style type="text/css">
.collapsed {
	display: none;
}

.tablemain {
	background-color: #278296;
	border-collapse: collapse;
	border: solid 1px #447;
	padding: 0px;
	text-align: left;
}

.tablemain td {
	margin-left: 3px;
}

.treet {
	background-color: #F4FAFB
}

.treet td {
	font: normal 10pt Arial;
	padding: 0px 2px 0px 0px;
	cursor: pointer;
}

.adeimg,.ttimage,.parimg,.preimg {
	border: none;
	margin: 0px;
	padding: 0px;
	vertical-align: bottom;
	width: 16px;
	height: 16px;
}

.adeimg,.parimg {
	cursor: pointer;
}

.even {
	background-color: #BBDDE5;
}

.over {
	background-color: #ffc;
}
</style>

	</head>
	<body>
		<!--  <form action="queryHelpCategoryListPage.do" method="post" > -->
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100" height="28" class="main_alll_h2">
								<a href="queryHelpCategoryListPage.do">帮助中心列表</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100" class="xxk_all_a">
								<a href="queryHelpListPageInit.do">管理帮助中心</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100" class="xxk_all_a">
								<a href="addHelpCategoryInit.do">添加帮助类型</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100" class="xxk_all_a">
								<a href="addHelpManagerInit.do">添加内容</a>
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
					</table>
				</div>
				<div
					style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: auto; padding-top: 10px; background-color: #fff;">
					<div>
						<table id="gvItem" style="width: 100%; color: #333333;"
							cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
							<tbody>
								<tr class=gvHeader>
									<th scope="col">
										序号
									</th>
									<th scope="col" style="text-align: left;">
										帮助类型名称
									</th>
									<th style="width: 200px;" scope="col">
										操作
									</th>
									<th style="width: 200px;" scope="col">
										排序
									</th>
								</tr>
							</tbody>
							<s:if test="#request.typeList.size > 0">
								<s:iterator value="#request.typeList" var="bean">

									<tr id="${id }" class="gvItem">
										<td>
											${id}
										</td>
										<td style="text-align: left;">
											${helpDescribe}
										</td>
										<td>
											<a id="gvNews_ctl05_lbManage"
												href="queryHelpListPageInit.do?typeId=${id}">管理列表</a>
											&nbsp;&nbsp;
											<a id="gvNews_ctl05_lbEdit"
												href="javascript:editSize('updateHelpCategoryInit.do?typeId=${id}',600,377);"
												target="main"> 编辑</a> &nbsp;&nbsp;
<%--											<a id="gvNews_ctl05_lbDelete" href="javascript:del(${id})">删除</a>--%>
										</td>
										<td>
											<a href="javascript:void(0)" onclick="moveUp(this)">上移</a>
											&nbsp;&nbsp;
											<a href="javascript:void(0)" onclick="moveDown(this)">下移</a>

										</td>
									</tr>
								</s:iterator>
							</s:if>
							<s:else>
								<tr>
									<td class="gvItem"
										style="text-align: center; background-color: #f7f7f7;"
										colspan="8">
										暂无数据
									</td>
								</tr>
							</s:else>


						</table>
					</div>
					<table>
						<tbody>
							<tr>
								<td height="36" align="right" class="blue12">
									&nbsp;
								</td>
								<td align="right">
									<button id="btnSave"
										style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px"
										onclick="saveOrder();"></button>

								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<!--   </form> -->
	</body>
</html>
