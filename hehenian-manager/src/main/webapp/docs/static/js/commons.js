var common={};
common.form={};
common.ajax={};
common.mouse={};
common.verifyCode={};
common.constants={
	TAG_ATTR_SUBMIT_BUTTON_VALUE:'_val'
};
//获取鼠标坐标
common.mouse.offset=function(ev){
	ev=ev?ev:window.event;
	if(ev.pageX || ev.pageY)
		return {x:ev.pageX, y:ev.pageY};
	return {
		x:ev.clientX + document.body.scrollLeft - document.body.clientLeft,
		y:ev.clientY + document.body.scrollTop - document.body.clientTop
	};
};
//表单提交的时候，使按钮变灰同时显示为“请稍等...”，防止重复提交
common.form.loading=function(x){
	if($(this).attr('onsubmit')){
		return;
	}else if(!$(this).attr('target')){
	}else if($(this).attr('target')=='_self'){
	}else{
		return;
	}
	var submitButton=$(this).find('input[type=submit]');
	if(submitButton.size())
		submitButton.attr(common.constants.TAG_ATTR_SUBMIT_BUTTON_VALUE,submitButton.val())
			.val('请稍等...').attr('disabled','disabled');
};
//表单提交失败的时候，使按钮取消变灰同时显示原来的value
common.form.cancel=function($form){
	$form=$($form);
	var $submit=$form.find('input[type=submit]');
	if($submit.size()&&$submit.attr(common.constants.TAG_ATTR_SUBMIT_BUTTON_VALUE)){
		$submit.val($submit.attr(common.constants.TAG_ATTR_SUBMIT_BUTTON_VALUE))
			.removeAttr('disabled').removeAttr(common.constants.TAG_ATTR_SUBMIT_BUTTON_VALUE);
	}
};
//自动聚焦表单内的文本输入框和密码输入框
common.form.focus=function($form){
	//todo 辨别不同的数据类型
	var $inputs=$form.find('input');
	var html='';
	for(var i=0;i<$inputs.size();i++){
		var $input=$inputs.eq(i);
		var t=$input.attr('type');
		if(t=='text'||t=='password'){
			if(!$.trim($input.val())){
				$input.focus();
				break;
			}
		}
	}
};
common.closeWindow=function(w){
	w.opener=null;
	w.open('','_self');
	w.close();
};
common.fromHexString=function(src){
	var str;
	eval('str="'+src+'"');
	return str;
}
common.resizeIframe=function(iframeDom){
	var $iframe=$(iframeDom).filter(':visible'),$body=$iframe.contents().find('body');
	if($iframe.size()){
		var w=$body.width(),h=$body.height();
		if(w>0&&h>0)
			$iframe.width(w).height(h);
	}
};
common.resizeIframeHeight=function(iframeDom){
	var $iframe=$(iframeDom).filter(':visible'),$body=$iframe.contents().find('body');
	if($iframe.size()){
		var h=$body.height();
		if(h>0)
			$iframe.height(h);
	}
}
common.openIframeTab=function(url,label,noDuplicate,reload){
	if(top.iframeTabs){
		top.iframeTabs('mainTabs').add(url,label,noDuplicate,reload);
	}else{
		alert('iframeTab not exists.');
	}
}
common.replaceIframeTab=function(url,label){
	if(top.iframeTabs){
		top.iframeTabs('mainTabs').add(url,label,null,null,true);
	}else{
		alert('iframeTab not exists.');
	}
}
common.getIframeTabWindow=function(noDuplicate){
	if(top.iframeTabs){
		return top.iframeTabs('mainTabs').getIframeWindow(noDuplicate);
	}
	return null;
}
common.initIframeTabsLink=function($element){
	$element=$element?$element:$('a.iframeTabsLink,input.iframeTabsLink');
	$element.attr('target','_blank').click(function(){
		var href=$.trim($(this).attr('href'));
		if(!href){
			return false;
		}else if(/^javascript:/.test(href)){
			$(this).removeAttr('target');
			return true;
		}
		if($(this).hasClass('noDuplicate')){
			var noDuplicate=$(this).attr('noDuplicate');
			if(!noDuplicate){
				noDuplicate=Math.random();
				$(this).attr('noDuplicate',noDuplicate);
			}
			if($(this).hasClass('reloadIfDuplicate')){
				common.openIframeTab(href,$(this).text()||$(this).val(),noDuplicate,true);
			}else{
				common.openIframeTab(href,$(this).text()||$(this).val(),noDuplicate);
			}
		}else{
			common.openIframeTab(href,$(this).text()||$(this).val());
		}
		return false;
	});
}
//修改ie6的一个bug，css的url(...)导致的背景图片的闪烁
if($.browser.msie&&$.browser.version/1==6){
	try{
		document.execCommand("BackgroundImageCache",false,true);
	}catch(e){}
}
$(function(){
	//输入框边框变为浅灰色细线，输入框聚焦的时候选中其中的内容
	$(':text,:password').addClass('inputText').focus(function(){
		this.select();
	});
	$('form').submit(common.form.loading);
	//设置表格模板的样式
	$('table.queryResultTable tbody tr').click(function(){
		$('table.queryResultTable tr.queryResultTable-selected').removeClass('queryResultTable-selected');
		$(this).addClass('queryResultTable-selected');
	});
	$('table.queryResultTable .queryResultTable-foot,table.queryResultTable thead').click(function(){
		$('table.queryResultTable tr.queryResultTable-selected').removeClass('queryResultTable-selected');
	});

	common.initIframeTabsLink();
	$('input.buttonLink').click(function(){
		if($(this).attr('href')){
			$(this).attr('disabled','disabled');
			location.href=$(this).attr('href');
		}
	});
	$('input[type=text]').attr('autocomplete','off');
	//解决ie进度条不结束的问题
	if(self!=top&&parent!=top){
		window.status='完毕';
	}
	//菜单显示隐藏的快捷键
	$(document).keydown(function(ev){
		// Alt+M
		if(top.resetFrameSize&&ev.altKey&&ev.keyCode==77){
			top.window.hideMenu=!top.window.hideMenu;
			top.resetFrameSize();
			return false;
		}
	});
	$('a.newWindow').click(function(){
		if(top.cti&&top.cti.ctiActiveX){
			top.cti.ctiActiveX.openURL($(this).attr('href'));
			return false;
		}
	});
});
$.tools.dateinput.localize("zh", {
   months: '一月,二月,三月,四月,五月,六月,七月,八月,九月,十月,十一月,十二月',
   shortMonths:  '一月,二月,三月,四月,五月,六月,七月,八月,九月,十月,十一月,十二月',
   days:         '星期天,星期一,星期二,星期三,星期四,星期五,星期六',
   shortDays:    '日,一,二,三,四,五,六'
});
$.tools.dateinput.conf.lang='zh';