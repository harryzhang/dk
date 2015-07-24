<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <link href="${basePath}/js/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet"
        type="text/css" />
    <script type="text/javascript" src="${basePath}/js/jquery-1.4.2.min.js"></script>
    <script src="${basePath}/js/ligerUI/js/core/base.js" type="text/javascript"></script>
    <script src="${basePath}/js/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
    <script src="${basePath}/js/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
    <script src="${basePath}/js/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
    <script src="http://www.ligerui.com/demos/grid/base/draggable.js" type="text/javascript"></script>
    <script type="text/javascript" src="${basePath}/js/ligerUI/js/plugins/ligerToolBar.js"></script>
    <script type="text/javascript">
    	qnId = ${questionnaireId};
        function getColumns()
        {
            return [{ display: '题目', name: 'content', width: 400, align: 'left' },
                    { display: 'ID', name: 'id', width: 50 }];
        }
        $(function()
        {    	
            window['g1'] =
            $("#maingrid1").ligerGrid({
                columns: getColumns(), pageSize: 20, checkbox: true,
                root :'rows', tree: { columnName: 'id' },
                allowHideColumn: false, rownumbers: true, colDraggable: true, rowDraggable: false,
                width: '500', height: '600',
                url:'/questionnaire/questionListData.html',
                record:'total',
                parms:{"classify": 1, "status": 1, "pageSize": 20}
            });

            window['g2'] =
            $("#maingrid2").ligerGrid({
                columns: getColumns(), pageSize: 100, checkbox: true,
                root :'rows', tree: { columnName: 'id' },
                allowHideColumn: false, rownumbers: true, colDraggable: true, rowDraggable: true,
                width: '500', height: '600', 
                url:'/questionnaire/questionnaireDetailData.html',
                record:'total',
                parms:{"questionnaireId": qnId, "pageSize": 100}
            });

            gridDraggable(g1, g2);
            $("#pageloading").hide();
            
            $("#toolbar").ligerToolBar({ items: [
           		{
           	        text: '提交', click: function (item)
           	        {
           	        	var grid = g2.rows;
           	        	var list = '';
           	        	$(grid).each(function() {
           	        		list += this.id + '>';
           	        	});
           	        	list = list.substr(0, list.length - 1);
           	        	$.post("/questionnaire/reset.html", {"qnId": qnId, "list": list}, function(ret) {
           	        		if(ret > 0) {
           	        			alert("ok");
           	        			g1.loadData();
           	        			g2.loadData();
           	        		} else {
           	        			alert("出错了");
           	        		}
           	        	});
           	        }
           	    },
           	    { line:true },
	           	 {
	           	        text: '删除', click: function (item)
	           	        {
	           	         	g2.deleteSelectedRow();
	           	        }
	           	 },
	           	 { line:true }
           	]
           	});
        });
        
    </script>
</head>
<body style="padding: 6px; overflow: hidden; width: 100%; height: 100%;"> 
	<div id="toolbar">
	</div>
    <div id="maingrid1" style="margin: 4px; padding: 0; float: left;">
    </div>
    <div id="maingrid2" style="margin: 4px; padding: 0; margin-left: 10px; float: left;">
    </div>
</body>
</html>

