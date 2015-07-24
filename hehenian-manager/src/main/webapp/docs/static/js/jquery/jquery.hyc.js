/**
 * 设置表单背景颜色
 * 
 */
( function($) {
	//插件
	$.extend($, {
		//命名空间1
		hyc : {
			//命名空间2
			color : {			
				//初始化依赖关系
				init : function(){
					$("[BEDEPENDED]").each(function(){				
						$(this).click(function(){
							var check = $(this).attr("checked");
							var depends = $(this).attr("BEDEPENDED");
							if(check){
								$(depends).removeAttr("disabled");
							}else{
								$(depends).attr("disabled","disabled");
							}
						});
					});	
				},
				//设置颜色
				set : function(group){
					$("[SETBGC="+group+"]").each(function(){
						 var node = $(this);
						 var v = node.val().replace(/(^\s+)|(\s+$)/g,"");	
						 if(v.length < 1) return;
						 var skipSetValue = node.attr("SKIPSETVALUE");
						 if(v == skipSetValue) return;						 
						 var isCheckbox = /checkbox/.test(node.attr("type"));
						 if(isCheckbox && !node.attr("checked")){return ;}
						 var isUseDepend = node.attr("DEPEND")
						 var isNotDepend = (!!isUseDepend && !$(isUseDepend).attr("checked"));				
						 if(isNotDepend){return;}
						 var isUseInt = (node.attr("ISINT") == "1");
						 var isInt = /^-*\d+$/.test(v);
						 if(isUseInt && !isInt){return }						 
						 node.css("background-color","#FFE8F0");						 
					});
				},
				//清除颜色
				clear : function(group){
					$("[SETBGC="+group+"]").each(function(){
						$(this).css("background-color","");
					});
				}	
				
			}
		}		
	});
})(jQuery);
