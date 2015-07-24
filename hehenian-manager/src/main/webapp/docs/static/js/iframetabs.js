var iframeTabs=function(iframeTabsId){
	var $tabs=$('#'+iframeTabsId),iframeHolderId=$tabs.attr('iframeHolderId');
	//alert("$tabs:"+$tabs);
	//alert("iframeHolderId:"+iframeHolderId);
	if(!iframeHolderId){
		return {
			add:function(){},
			select:function(){},
			label:function(){}
		};
	}
	var $ul=$tabs.children('ul'),$iframeHolder=$('#'+iframeHolderId);
	var filterUrl=function(url){
		var interrogationIndex=url?url.indexOf('?'):-1;
		var r='tab'+parseInt(Math.random()*1000000);
        if(url.indexOf('#') >= 0){
            r+=url.substring(url.indexOf('#'));
            url=url.substring(0,url.indexOf('#'));
        }
		if(interrogationIndex==-1){
			url+='?'+r;
		}else if(interrogationIndex==url.length-1){
			url+=r;
		}else{
			url+='&'+r;
		}
		return url;
	}
	return {
		add:function(url,label,noDuplicate,reload,replace){
			//先检查是否已经被打开过
			if(noDuplicate){
				$oldLabel=$ul.find('span[noDuplicate='+noDuplicate+']');
				if($oldLabel.size()){
					if(reload){
						var index=$ul.children('li').children('span').index($oldLabel);
						this.select(index);
						$iframeHolder.children('iframe').eq(index).attr('src',filterUrl(url));
						return false;
					}else{
						var index=$ul.children('li').children('span').index($oldLabel);
						this.select(index);
						return false;
					}
				}
			}
			//判断是否超过了最大tab数
			if($ul.children('li').size()>=8){
                /* 不替换已打开的标签页
				$replacableLabel=$ul.find('span[replace]');
				if($replacableLabel.size()){
					this.close($replacableLabel.eq(0));
				}else{
					top.iframeDialog.alert('标签页打开太多了，请先关闭不需要的。');
					return false;
				}
                */ 
                top.iframeDialog.alert('标签页打开太多了，请先关闭不需要的。');
				return false;
			}
			var id=new Date().getTime();
			var labelHtml=iframeTabs.templates.label.replace(new RegExp('\\{id\\}', 'g'), id)
					.replace(new RegExp('\\{noDuplicateAttr\\}', 'g'), noDuplicate?' noDuplicate="'+noDuplicate+'"':'')
					.replace(new RegExp('\\{label\\}', 'g'), label?label:'加载中...')
					.replace(new RegExp('\\{replace\\}', 'g'), replace?' replace="true"':'');
			$ul.append(labelHtml);
			var iframeHtml=iframeTabs.templates.iframe.replace(new RegExp('\\{id\\}', 'g'), id)
					.replace(new RegExp('\\{iframeTabsId\\}', 'g'), iframeTabsId);
			$iframeHolder.append(iframeHtml);
			$('#iframetabs-iframe-'+id).attr('src',filterUrl(url));
			var index=$ul.children('li').children('span').index($('#iframetabs-label-'+id));
			this.select(index);
			var selcect=this.select;
			$('#iframetabs-label-'+id).click(function(){
				selcect($ul.children('li').children('span').index(this));
			}).dblclick(function(){
				iframeTabs(iframeTabsId).close($(this));
			});
		},
		select:function(index){
			var currentLabel=$('.iframetabs-label-current');
			if(currentLabel.size()){
				$('#iframetabs-iframe-'+currentLabel.attr('id').substring(17)).attr('height','0');
				currentLabel.removeClass('iframetabs-label-current');
			}
			$ul.children('li').children('span').eq(index).addClass('iframetabs-label-current');
			$iframeHolder.children('iframe').eq(index).attr({width:'100%',height:'100%'});
		},
		label:function(tabId,iframe){
			try{
				var label=$(iframe).contents().find('title').html();
				$('#iframetabs-label-'+tabId).text(label);
			}catch(e){}
		},
		close:function($label){
			if(!$label.size()){
				return;
			}
			var id=$label.attr('id').substring(17);
			var index=$tabs.find('ul li').children('span').index($('#iframetabs-label-'+id));
			$('#iframetabs-iframe-'+id).attr('src','about:blank').remove();
			if($.browser.msie){
				//如果是ie，要强制回收垃圾，否则会有内存泄露
				CollectGarbage();
			}
			$label.parents('li:first').remove();
			index=(index>=$tabs.find('ul li').children('span').size()?index-1:index);
			if(index>=0){
				iframeTabs(iframeTabsId).select(index);
			}
		},
		getIframeWindow:function(noDuplicate){
			var iframeTabId=$ul.find('li span[noDuplicate='+noDuplicate+']').attr('iframeTabId');
			if(iframeTabId){
				return $('#iframetabs-iframe-'+iframeTabId).get(0).contentWindow;
			}
			return null;
		}
	};
};
iframeTabs.templates={
	label:'<li><span id="iframetabs-label-{id}" iframeTabId="{id}" onselect="return false;"{noDuplicateAttr}{replace}>{label}</span></li>',
	iframe:'<iframe id="iframetabs-iframe-{id}" name="iframetabs-iframe-{id}" src="about:blank" width="1" height="1" frameborder="0" \
			onload="iframeTabs(\'{iframeTabsId}\').label(\'{id}\',this)"></iframe>'
}
iframeTabs.newInstance=function(iframeTabsId,iframeHolderId){
	var $tabs=$('#'+iframeTabsId);
	$tabs.attr('iframeHolderId',iframeHolderId).addClass('iframetabs-holder');
	$tabs.children('.iframetabs-close').click(function(){
		iframeTabs(iframeTabsId).close($('.iframetabs-label-current'));
		return false;
	});
	$tabs.children('.iframetabs-reload').click(function(){
		var currentLabel=$('.iframetabs-label-current');
		if(currentLabel.size()){
			var id=currentLabel.attr('id').substring(17);
			try{
				frames['iframetabs-iframe-'+id].location.reload();
			}catch(e){
				alert('不同域名，不能刷新。');
			}
		}
		return false;
	});
	$tabs.parent().bind('selectstart',function(){
		return false;
	}).bind('drag',function(){
		return false;
	});
};