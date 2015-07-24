// JavaScript Document

//顶部滚动推荐消息 开始
window.onload=function(){ 
function $(element){ 
if(arguments.length>1){ 
for(var i=1,length=arguments.length,elements=[];i<length;i++){ 
elements.push($(arguments[i])); 
} 
return elements; 
} 
if(typeof element=="string"){ 
return document.getElementById(element); 
}else{ 
return element; 
} 
} 
var Class={ 
create:function(){ 
return function(){ 
this.initialize.apply(this,arguments); 
} 
} 
} 
Function.prototype.bind=function(object){ 
var method=this; 
return function(){ 
method.apply(object,arguments); 
} 
} 
var Scroll=Class.create(); 
Scroll.prototype={ 
initialize:function(element,height){ 
this.element=$(element); 
this.element.innerHTML+=this.element.innerHTML; 
this.height=height; 
this.maxHeight=this.element.scrollHeight/2; 
this.counter=0; 
this.scroll(); 
this.timer=""; 
this.element.onmouseover=this.stop.bind(this); 
this.element.onmouseout=function(){this.timer=setTimeout(this.scroll.bind(this),1000);}.bind(this);
}, 
scroll:function(){ 
if(this.element.scrollTop<this.maxHeight){ 
this.element.scrollTop++; 
this.counter++; 
}else{ 
this.element.scrollTop=0; 
this.counter=0; 
} 
 
if(this.counter<this.height){ 
this.timer=setTimeout(this.scroll.bind(this),20); 
}else{ 
this.counter=0; 
this.timer=setTimeout(this.scroll.bind(this),3000); 
} 
}, 
stop:function(){ 
clearTimeout(this.timer); 
} 
} 
var myscroll=new Scroll("topNewsscroll",24); 
//顶部滚动推荐消息JS 结束

}


//通用选项卡JS 开始
function nTabs(nTab,nli){
	$(".nTabNli"+nTab).removeClass("active");
	$(".nTabNli"+nTab).addClass("normal");
	$(".nTabConten"+nTab).hide();
	$("#nTab"+nTab+"_nli"+nli).addClass("active");
	$("#nTab"+nTab+"_Content"+nli).show();
	
}
//通用选项卡JS 结束

//内页左侧三级导航JS 开始（------------>>>暂无使用<<<-------------）
function ShowMenu(obj,noid){
	var block =	document.getElementById(noid);
	var n = noid.substr(noid.length-1);
	alert(noid.length);
	if(noid.length==4){//当长度为4，就是二级菜单
		var ul = document.getElementById(noid.substring(0,3)).getElementsByTagName("ul");
		var h2 = document.getElementById(noid.substring(0,3)).getElementsByTagName("h2");
		for(var i=0; i<h2.length;i++){
			h2[i].innerHTML = h2[i].innerHTML.replace("-","+");
			h2[i].style.color = "";
		}
		obj.style.color = "#333";
		for(var i=0; i<ul.length; i++){if(i!=n){ul[i].className = "no";}}
	}else{//一级菜单
		var span = document.getElementById("menu").getElementsByTagName("span");
		var h1 = document.getElementById("menu").getElementsByTagName("h1");
		for(var i=0; i<h1.length;i++){
			h1[i].innerHTML = h1[i].innerHTML.replace("-","+");
			h1[i].style.color = "";
		}
		obj.style.color = "#eb6100";
		for(var i=0; i<span.length; i++){if(i!=n){span[i].className = "no";}}
	}
	if(block.className == "no"){
		block.className = "";
		obj.innerHTML = obj.innerHTML.replace("+","-");
	}else{
		block.className = "no";
		obj.style.color = "";
	}
}
//内页左侧三级导航JS 结束

//JQ滑动展开与折叠层 开始
$(document).ready(function(){

$("#head1").click(function(){
$("#mainLeft_play_comment").stop().slideToggle("slow");
});
$("#head2").click(function(){
$("#zffs_ebzf").stop().slideToggle("slow");
});
$("#head3").click(function(){
$("#zffs_fpxx").stop().slideToggle("slow");
});
$("#shop_spxq_title01").click(function(){
$("#shop_spxq_content01").stop().slideToggle("slow");
});
$("#shop_spxq_title02").click(function(){
$("#shop_spxq_content02").stop().slideToggle("slow");
});
$("#shop_spxq_title03").click(function(){
$("#shop_spxq_content03").stop().slideToggle("slow");
});
$("#shop_spxq_title04").click(function(){
$("#shop_spxq_content04").stop().slideToggle("slow");
});
$("#shop_spxq_title05").click(function(){
$("#shop_spxq_content05").stop().slideToggle("slow");
});
$("#shop_spxq_title06").click(function(){
$("#shop_spxq_content06").stop().slideToggle("slow");
});
$("#shop_spxq_title07").click(function(){
$("#shop_spxq_content07").stop().slideToggle("slow");
});
$("#shop_spxq_title08").click(function(){
$("#shop_spxq_content08").stop().slideToggle("slow");
});

$("#course_play_title01").click(function(){
$("#course_play_content01").stop().slideToggle("slow");
});

});
//JQ滑动展开与折叠层  结束


//JQ带控制左右的图片横向滑动 师资阵容 开始
$(function(){
    var page = 1;
    var i = 5; //每版放4个图片
    //向后 按钮
    $("span.next").click(function(){    //绑定click事件
	     var $parent = $(this).parents("div.v_show");//根据当前点击元素获取到父元素
		 var $v_show = $parent.find("div.content_list"); //找到"视频内容展示区域"
		 var $v_content = $parent.find("div.content"); //找到"视频内容展示区域"外围的DIV元素
		 var v_width = $v_content.width() ;
		 var len = $v_show.find("li").length;
		 var page_count = Math.ceil(len / i) ;   //只要不是整数，就往大的方向取最小的整数
		 if( page == page_count ){  //已经到最后一个版面了,如果再向后，必须跳转到第一个版面。
				$v_show.animate({ left : '0px'}, "slow"); //通过改变left值，跳转到第一个版面
				page = 1;
			}else{
				$v_show.animate({ left : '-='+v_width }, "slow");  //通过改变left值，达到每次换一个版面
				page++;
		 }
		 $parent.find("span").eq((page-1)).addClass("current").siblings().removeClass("current");
   });
    //往前 按钮
    $("span.prev").click(function(){
	     var $parent = $(this).parents("div.v_show");//根据当前点击元素获取到父元素
		 var $v_show = $parent.find("div.content_list"); //寻找到"视频内容展示区域"
		 var $v_content = $parent.find("div.content"); //寻找到"视频内容展示区域
		 var v_width = $v_content.width();
		 var len = $v_show.find("li").length;
		 var page_count = Math.ceil(len / i) ;   //只要不是整数，就往大的方向取最小的整数
		 if( page == 1 ){  //已经到第一个版面了,如果再向前，必须跳转到最后一个版面。
				$v_show.animate({ left : '-='+v_width*(page_count-1) }, "slow");
				page = page_count;
		}else{
				$v_show.animate({ left : '+='+v_width }, "slow");
				page--;
		}
		$parent.find("span").eq((page-1)).addClass("current").siblings().removeClass("current");
    });
});
//JQ带控制左右的图片横向滑动 师资阵容 结束


//名师讲堂teacher 首页banner切换JS 开始
(function($){$.fn.albumSlider=function(j){return this.each(function(){var b=$.extend({step:2,imgContainer:'div.fullview',listContainer:'ul.imglist',event:'mouseover',direction:'v'},j||{});var c=$(b.imgContainer,this),$list=$(b.listContainer,this),currId=0,currPage=0,size=$list.children().length-1,pageSize=Math.floor(size/b.step);var f=b.direction=='v';var g=f?'top':'left';var h=(size>=b.step)?$('li',$list).eq(b.step).offset()[g]-$('li',$list).eq(0).offset()[g]:0;var i=function(){var a=$(this);if(a.is('.current')){return false}$('img',c).fadeOut(800,function(){$(this).remove()});$('<img>').hide().attr('src',$('a',a).attr('href')).appendTo(c).fadeIn(800);a.addClass('current').siblings().removeClass('current');return false};$.proxy(i,$('li',$list).eq(0))();$list.delegate('li',b.event,$.proxy(i)).bind('moveforward movebackward',function(e){var a=e.type=='moveforward';if(a){currId+=b.step;if(currId>size){currId=size}if(++currPage>pageSize){currPage=pageSize;return false}}else{currId-=b.step;if(currId<0){currId=0}if(--currPage<0){currPage=0;return false}};var d=(a?'-=':'+=')+h;$(this).stop(true,true).animate(f?{top:d}:{left:d},500,function(){$.proxy(i,$('li',$list).eq(currId))()})});$('div.button',this).click(function(){$list.trigger($(this).is('.moveforward')?'moveforward':'movebackward')})})}})(jQuery);


$(function(){

    //横向设置
    $('div.albumSlider-h').albumSlider({direction:'h',step:4});
});
//名师讲堂teacher 首页banner切换JS 结束




//带缩略图和控制按钮的横向banner切换 名师讲堂banner 以及商城的书讯快递 开始

//带缩略图和控制按钮的横向banner切换 名师讲堂banner 以及商城的书讯快递 结束