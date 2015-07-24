/*
	主页加载方法
*/


/*以下标签都在view/index.jsp中*/
var tabsId = '#tabs';//tabs页签Id
var tab_rightmenuId = '#tab_rightmenu';//tabs右键菜单Id

$(function() {
	/*
	 * 主页左边菜单
	 * */
	var zTree = '';
	$.post(httpUrl+"/role/rolemenu/roles/menus.do",{pageSize:1000,pageNumber:1,roles:$("#index_page").attr('data-role-ids')},function (data){
		if(data.length > 0){
			$("#ztreeList_span").hide();
			$.fn.zTree.init($("#menuTree"), setting, data);
			 zTree = $.fn.zTree.getZTreeObj("menuTree");
		}else {
			$("#ztreeList_span").text("抱歉，没有菜单数据!");
			$("#ztreeList_span").show();
		}
	},"json");
//	$.fn.zTree.init($("#menuTree"), setting, zNodes);
	//中间部分tab
	$(tabsId).tabs({  
		border:false,
		fit:true,
		onSelect: function(title, index){
			var treeNode = zTree.getNodeByParam("name", title, null);
			if(treeNode && treeNode.code){
				$("#index_page").attr("data-menu-code",treeNode.code);
				zTree.selectNode(treeNode);
			}
		},
		//tab页右键菜单
		onContextMenu : function(e, title, index){
			e.preventDefault();
			$(tab_rightmenuId).menu('show',{  
				left: e.pageX,
				top: e.pageY  
			}).data("tabTitle",title);
		}
	});
	
	//实例化menu的onClick事件
	$(tab_rightmenuId).menu({
		onClick:function(item){
		  operaTab(tabsId,tab_rightmenuId,item.id);
		}
	});
	
	/**
		tab关闭事件
	@param	tabId		tab组件Id
	@param	tabMenuId	tab组件右键菜单Id
	@param	itemId		tab组件右键菜单item id属性值
	*/
	function operaTab(tabId,tabMenuId,itemId){
		//tab组件对象
		var tabs = $(tabId);
		//tab组件右键菜单对象
		var tab_menu = $(tabMenuId);
		//获取当前tab的标题
		var curTabTitle = tab_menu.data('tabTitle');
		//刷新当前tab
		if(itemId === 'refresh'){
			var curTab = tabs.tabs("getTab", curTabTitle);
			var iframeId = curTab.panel('options').id;
			//非主流的刷新办法
			$("#iframe"+iframeId).attr("src", $("#iframe"+iframeId).attr("src") + "?rand=" + Math.random());
		}
		//关闭当前tab
		if(itemId === 'close'){
			//根据标题获取tab
			var curTab = tabs.tabs("getTab", curTabTitle);
			//判断tab是否可关闭
			if(curTab.panel('options').closable){
				//通过标题关闭tab
				tabs.tabs("close",curTabTitle);
			}
		}
		//关闭全部tab
		else if(itemId === 'closeall'){
			//获取所有关闭的tab对象
			var closeTabsTitle = getAllTabObj(tabs);
			//循环删除要关闭的tab
			$.each(closeTabsTitle,function(){
				var title = this;
				tabs.tabs('close',title);
			});
		}
		//关闭其他tab
		else if(itemId === 'closeother'){
			//获取所有关闭的tab对象
			var closeTabsTitle = getAllTabObj(tabs);
			//循环删除要关闭的tab
			$.each(closeTabsTitle,function(){
				var title = this;
				if(title != curTabTitle){
					tabs.tabs('close',title);
				}
			});
		}
		//关闭当前左侧tab
		else if(itemId === 'closeleft'){
			var closeTabsTitle = getLeftToCurrTabObj(tabs, curTabTitle);
			//获取所有关闭的tab对象
			$.each(closeTabsTitle, function(){
				var title = this;
				tabs.tabs('close',title);
			});
		}
		
		//关闭当前右侧tab
		else if(itemId === 'closeright'){
			//所有所有tab对象
			var allTabs = tabs.tabs('tabs');
			//获取所有关闭的tab对象
			for(var i = allTabs.length - 1; i >= 0; i --){
				var title = allTabs[i].panel('options').title;
				if(title == curTabTitle){
					return;
				}
				//根据标题获取tab
				var curTab = tabs.tabs("getTab", title);
				//判断tab是否可关闭
				if(curTab.panel('options').closable){
					//通过标题关闭tab
					tabs.tabs("close",title);
				}
			}
		}
	}
	
	/**
		获取所有关闭的tab对象
	@param	tabs	tab组件
	*/
	function getAllTabObj(tabs){
		//存放所有tab标题
		var closeTabsTitle = [];
		//所有所有tab对象
		var allTabs = tabs.tabs('tabs');
		$.each(allTabs,function(){
			var tab = this;
			var opt = tab.panel('options');
			//获取标题
			var title = opt.title;
			//是否可关闭 ture:会显示一个关闭按钮，点击该按钮将关闭选项卡
			var closable = opt.closable;
			if(closable){
				closeTabsTitle.push(title);
			}
		});
		return closeTabsTitle;
	}
	
	/**
		获取左侧第一个到当前的tab
	@param	tabs		tab组件
	@param	curTabTitle	到当前的tab
	*/
	function getLeftToCurrTabObj(tabs,curTabTitle){
		//存放所有tab标题
		var closeTabsTitle = [];
		//所有所有tab对象
		var allTabs = tabs.tabs('tabs');
		for(var i = 0;i < allTabs.length; i++){
			var tab = allTabs[i];
			var opt = tab.panel('options');
			//获取标题
			var title = opt.title;
			//是否可关闭 ture:会显示一个关闭按钮，点击该按钮将关闭选项卡
			var closable = opt.closable;
			if(closable){
				if(title == curTabTitle){
					return closeTabsTitle;
				}
				closeTabsTitle.push(title);
			}
		}
		return closeTabsTitle;
	}
	
	/*$('.index_panel').panel({  
	  width:300,  
	  height:200,  
	  closable:true,
	  minimizable:true,
	  title: 'My Panel'
	});*/
	
});

