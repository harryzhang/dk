var Cache=function(){
	var _this;
	var grid;
	var searchBox;
	return {
		init:function(){
			_this=this;
			searchBox=$('#search').searchbox({   
			    width:300,   
			    searcher:function(value,name){   
			       _this.searchKey(value)
			    },   
			    menu:'#mm',   
			    prompt:'请输入缓存的Key'  
			});  
		},
		searchKey:function(value){
			if(!grid){
		    	grid=$("#tableGrid").datagrid({
				url:"/cache/queryCacheKey.html",
				height:550,
				rownumbers:true,
				pagination:false,
				fitColumns:true,
				singleSelect:true,
				autoRowHeight:true,
				striped:true,
				pageSize:20,
				pageList:[1],
				idField:"id",
				queryParams:{"cacheKey":value,"whichCache":$('#search').searchbox('getName')},
				columns:[
				[
					{field:"cacheKey",title:"缓存Key",width:100,align:"center"},
					{field:"valueType",title:"缓存值类型",width:100,align:"center"},
					{field:"edit",title:"操作",width:80,align:"center",formatter:function(value,row,index){
					  return "<a href='javascript:void(0);' onclick='cache.deleteCache(\""+row.cacheKey+"\")'>删除</a>";
					}}
				]
				]
			});
		  }else{
		  	$('#tableGrid').datagrid('options').queryParams["cacheKey"]=value;
		  	$('#tableGrid').datagrid('options').queryParams["whichCache"]=$('#search').searchbox('getName');
		  	$('#tableGrid').datagrid('reload');
		  }
		},
		deleteCache:function(cacheKey){
			if(!cacheKey){
				$.messager.alert("消息","缓存的Key不能为空");
				return;
			}
			$.ajax({
				url:'/cache/deleteCacheKey.html',
				method:'post',
				data:{cacheKey:cacheKey,whichCache:$('#search').searchbox('getName')},
				success:function(ret){
					if(ret==-1){
						$.messager.alert("消息","删除失败");
					}else{
						$.messager.alert("消息","删除成功");
					}
					$('#tableGrid').datagrid('reload');
				}
			});
		}
	}
}
$(function(){
	var cache=new Cache();
	cache.init();
	window.cache=cache;
});