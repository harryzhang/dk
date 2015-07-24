var common={};
common.form={};
common.ajax={};
common.mouse={};
common.verifyCode={};
common.constants={
	TAG_ATTR_SUBMIT_BUTTON_VALUE:'_val'
};
//��ȡ�������
common.mouse.offset=function(ev){
	ev=ev?ev:window.event;
	if(ev.pageX || ev.pageY)
		return {x:ev.pageX, y:ev.pageY};
	return {
		x:ev.clientX + document.body.scrollLeft - document.body.clientLeft,
		y:ev.clientY + document.body.scrollTop - document.body.clientTop
	};
};
//���ύ��ʱ��ʹ��ť���ͬʱ��ʾΪ�����Ե�...������ֹ�ظ��ύ
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
			.val('���Ե�...').attr('disabled','disabled');
};
//���ύʧ�ܵ�ʱ��ʹ��ťȡ�����ͬʱ��ʾԭ����value
common.form.cancel=function($form){
	$form=$($form);
	var $submit=$form.find('input[type=submit]');
	if($submit.size()&&$submit.attr(common.constants.TAG_ATTR_SUBMIT_BUTTON_VALUE)){
		$submit.val($submit.attr(common.constants.TAG_ATTR_SUBMIT_BUTTON_VALUE))
			.removeAttr('disabled').removeAttr(common.constants.TAG_ATTR_SUBMIT_BUTTON_VALUE);
	}
};
//�Զ��۽����ڵ��ı����������������
common.form.focus=function($form){
	//todo ���ͬ����������
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
//�޸�ie6��һ��bug��css��url(...)���µı���ͼƬ����˸
if($.browser.msie&&$.browser.version/1==6){
	try{
		document.execCommand("BackgroundImageCache",false,true);
	}catch(e){}
}
$(function(){
	//�����߿��Ϊǳ��ɫϸ�ߣ������۽���ʱ��ѡ�����е�����
	$(':text,:password').addClass('inputText').focus(function(){
		this.select();
	});
	$('form').submit(common.form.loading);
	//���ñ��ģ�����ʽ
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
	//���ie������������������
	if(self!=top&&parent!=top){
		window.status='���';
	}
	//�˵���ʾ���صĿ�ݼ�
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
   months: 'һ��,����,����,����,����,����,����,����,����,ʮ��,ʮһ��,ʮ����',
   shortMonths:  'һ��,����,����,����,����,����,����,����,����,ʮ��,ʮһ��,ʮ����',
   days:         '������,����һ,���ڶ�,������,������,������,������',
   shortDays:    '��,һ,��,��,��,��,��'
});
$.tools.dateinput.conf.lang='zh';