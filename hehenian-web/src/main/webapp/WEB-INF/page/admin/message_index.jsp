<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
<head>
<title>信息管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../css/admin/popom.js"></script>
<script type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript">
	$(function() {
		initListInfo(null);
	});
	function initListInfo(praData) {
		$.shovePost("queryMessageListPage.do", praData, initCallBack);
	}
	function initCallBack(data) {
		$("#dataInfo").html(data);
	}

	function moveUp(_a) {
		var _row = _a.parentNode.parentNode;
		//如果不是第一行，则与上一行交换顺序
		var _node = _row.previousSibling;
		if (_node == null)
			alert("已经是第一行了");
		while (_node && _node.nodeType != 1) {
			_node = _node.previousSibling;
		}
		if (_node) {
			swapNode(_row, _node);
		}
	}

	function moveDown(_a) {
		var _row = _a.parentNode.parentNode;
		//如果不是最后一行，则与下一行交换顺序
		var _node = _row.nextSibling;
		while (_node && _node.nodeType != 1) {
			_node = _node.nextSibling;
		}
		if (_node) {
			swapNode(_row, _node);
		}
	}

	function swapNode(node1, node2) {
		//获取父结点
		var _parent = node1.parentNode;
		//获取两个结点的相对位置
		var _t1 = node1.nextSibling;
		var _t2 = node2.nextSibling;
		//将node2插入到原来node1的位置
		if (_t1)
			_parent.insertBefore(node2, _t1);
		else
			_parent.appendChild(node2);
		//将node1插入到原来node2的位置
		if (_t2)
			_parent.insertBefore(node1, _t2);
		else
			_parent.appendChild(node1);
	}

	//信息管理预览  houli
	function preview(id) {
		var url = "previewMessageInit.do?id=" + id;
		//$.jBox("信息详情显示", url, 600, 450);
		$.jBox("iframe:"+url, {
		    title: "信息详情显示",
		    width: 700,
		    height: 500,
		    buttons: {}
		});
	}
	//弹出窗口关闭 houli
	function close() {
		window.jBox.close();
	}
</script>
</head>
<body>
	<div id="right">
		<div style="padding: 15px 10px 0px 10px;">
			<div>
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="100" height="28" class="main_alll_h2"><a href="queryMessageListInit.do">信息管理</a></td>
						<td width="2">&nbsp;</td>

						<td width="2">&nbsp;</td>
						<td width="2">&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
				</table>
				<div style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0" width="100%" border="0">
						<tbody>

						</tbody>
					</table>
					<span id="dataInfo"> </span>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
