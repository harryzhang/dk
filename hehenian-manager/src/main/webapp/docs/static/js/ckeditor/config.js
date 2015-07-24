/*
Copyright (c) 2003-2011, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

CKEDITOR.editorConfig = function( config )
{
	 	config.language = 'zh-cn'; // 配置语言  
	    config.uiColor = '#FFF'; // 背景颜色  
	    config.width = 'auto'; // 宽度  
	    config.height = '300px'; // 高度  
	    config.skin = 'office2003';// 界面v2,kama,office2003  
	    config.toolbar = 'MyToolbar';// 工具栏风格（基础'Basic'、全能'Full'、自定义）plugins/toolbar/plugin.js  
	    config.toolbarCanCollapse = false;// 工具栏是否可以被收缩  
	    config.resize_enabled = false;// 取消 “拖拽以改变尺寸”功能 plugins/resize/plugin.js  
	    config.enterMode = CKEDITOR.ENTER_BR;
	    config.shiftEnterMode = CKEDITOR.ENTER_P; //取消自动加<p>
	    
	    //图片处理  
	    config.pasteFromWordRemoveStyles = true;
	    //自定义图片上传action
	    config.filebrowserImageUploadUrl = "ckUploadImage.action?type=image";  
	    
	    //自定义的工具栏      
	    config.toolbar_MyToolbar =  
	    [  
	        ['Source','-','Preview'],  
	        ['Cut','Copy','Paste','PasteText','PasteFromWord','-','Scayt'],  
	        ['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat'],  
	        ['Image','Table','HorizontalRule','SpecialChar','PageBreak'],  
	        '/',  
	        ['Styles','Format'],  
	        ['Bold','Italic','Strike'],  
	        ['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'],  
	        ['Link','Unlink','Anchor'],  
	        ['Maximize','-','About']  
	    ];  
};
