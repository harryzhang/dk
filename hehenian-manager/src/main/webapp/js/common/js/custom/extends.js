/*
	扩展easyui中的控件方法或修改其默认属性
 */
 
//翻页工具栏文字
if ($.fn.pagination){
	$.fn.pagination.defaults.showPageList=true;
	$.fn.pagination.defaults.beforePageText="第 <span id='currentPage'>1</span> 页  转到",
	$.fn.pagination.defaults.afterPageText = "页&nbsp; <a style='border:0;text-decoration:none; font-size:15px;font-weight:bold;color:#8DB2E3' href='javascript:void(0);' onclick='jumpPage()'>GO</a>&nbsp; 共 {pages} 页";
	$.fn.pagination.defaults.displayMsg ="当前{from}-{to} 条  共{total}条记录";

}

//分页控件上的点击"GO"实现 页面跳转方法
function jumpPage(){
  //设置键盘事件 并将其设置为按下回车
   var e=$.Event("keydown");
   e.keyCode=13;
   $("input.pagination-num:visible").trigger(e);//模拟页码框按下回车
}

if ($.fn.datagrid){
	$.fn.datagrid.defaults.loadMsg = '正在加载...';
	/*后台封装的参数是pageNo pageSize 这里继承传过去的是page rows 所以要去加载后的地方插入参数*/
	//$.fn.datagrid.defaults.pageNumber = 1;
	$.fn.datagrid.defaults.pageSize = 20;
	$.fn.treegrid.defaults.pageSize = 30;
	/*$.fn.datagrid.defaults.queryParams = {
			'pageSize':5
	}; */
	/*$.fn.datagrid.defaults.rowStyler = function(index,row){
		if (index%2 != 0){
			//$('.datagrid-row-alt').css({"background-color":"#EFEFEF","color":"#000000"});
			return 'background-color:#EFEFEF;color:#000000;';//#EBEBE4
		}
	}*/
}

//window窗体默认属性
$.fn.window.defaults.resizable=false;
$.fn.window.defaults.collapsible=false;
$.fn.window.defaults.minimizable=false;
$.fn.window.defaults.maximizable=false;
$.fn.window.defaults.closable=false;
$.fn.window.defaults.closed=false;
$.fn.window.defaults.shadow=false;
$.fn.window.defaults.modal=true;
$.fn.window.defaults.loadingMessage = '正在加载...';

//信息框按钮文字
if ($.messager){
	$.messager.defaults.ok = '确定';
	$.messager.defaults.cancel = '取消';
}

/*###########################以下暂未用到############################################################*/
/**
 * 扩展grid没有数据时显示的消息
 * 
 */
var myview = $.extend({},$.fn.datagrid.defaults.view,{
    onAfterRender:function(target){
        $.fn.datagrid.defaults.view.onAfterRender.call(this,target);
        var opts = $(target).datagrid('options');
        var vc = $(target).datagrid('getPanel').children('div.datagrid-view');
        vc.children('div.datagrid-empty').remove();
        if (!$(target).datagrid('getRows').length){
            var d = $('<div class="datagrid-empty"></div>').html(opts.emptyMsg || 'no records').appendTo(vc);
            d.css({
                position:'absolute',
                left:0,
                top:50,
                width:'100%',
                textAlign:'center'
            });
        }
    }
});


/**
 * Datagrid扩展方法tooltip 基于Easyui 1.3.3，可用于Easyui1.3.3+
 * 简单实现，如需高级功能，可以自由修改
 * 使用说明:
 *   在easyui.min.js之后导入本js
 *   代码案例:
 *      $("#dg").datagrid({....}).datagrid('tooltip'); 所有列
 *      $("#dg").datagrid({....}).datagrid('tooltip',['productid','listprice']); 指定列
 * @author ____′lml
 */
/*$.extend($.fn.datagrid.methods, {
    tooltip : function (jq, fields) {
        return jq.each(function () {
            var panel = $(this).datagrid('getPanel');
            if (fields && typeof fields == 'object' && fields.sort) {
                $.each(fields, function () {
                    var field = this;
                    bindEvent($('.datagrid-body td[field=' + field + '] .datagrid-cell', panel));
                });
            } else {
                bindEvent($(".datagrid-body .datagrid-cell", panel));
            }
        });
        function bindEvent(jqs) {
            jqs.mouseover(function () {
                var content = $(this).text();
                $(this).tooltip({
                    content : content,
                    trackMouse : true,
                    onHide : function () {
                        $(this).tooltip('destroy');
                    }
                }).tooltip('show');
            });
        }
    }
});*/

/*$.extend($.fn.datagrid.methods, {
	showCellTip:function(jq){
		$("div[data-toggle='tooltip']").each(function() {
		    $(this).tooltip({
                placement:"bottom",//tooltip的显示位置
                delay:500//显示tooltip的延时时间
          });
		});		 
	}
});*/
