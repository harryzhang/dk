/*
	主页加载方法
*/
//系统时间显示
setInterval("document.getElementById('nowTime').innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());",1000);
var setting = {
	data: {
		simpleData: {
			enable: true
		}
	},
	view: {
		selectedMulti: false
	},
	callback: {
		onClick:function(e, id, node){
			var zTree = $.fn.zTree.getZTreeObj("menuTree");
			if(node.isParent) {
				zTree.expandNode();
			} else {
				addTabs(node.name, node.file, node.id);
			}
		}
	}
};

//添加一个选项卡面板 
function addTabs(title, url, id, icon){
	var tabs = $("#tabs").tabs("tabs");
	//超过5个tab弹出提示
	if(tabs.length > 8){
		//$.messager.alert("提示","您打开的tab页过多，请手动关闭一些", "info");
		//return;
		$("#tabs").tabs("close", 1);
	}
	if(!$('#tabs').tabs('exists', title)){
		$('#tabs').tabs('add',{  
			id : id,
			title:title,  
			content:'<iframe id="iframe' + id + '" src="'+httpUrl+"/views/"+url+'" frameBorder="0" border="0" scrolling="no" style="width: 100%; height: 100%;"/>',  
			closable:true
		});
	} else {
		$('#tabs').tabs('select', title);
	}
}