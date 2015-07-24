Zepto(function(){
	var investFixed = $("investFixed");
	if($.os.iphone){
		investFixed.css({"position":"absolute","bottom":51});
		$("#wrap").css({"bottom":"135px"});
	}
	var tabs = $("div.tabs");
	var line = tabs.find("span.tabs-line");
	var tabsNav = tabs.find(".tabs-nav li");
	var tabsContent = tabs.find("div.tabs-content");
	var tabBox = tabsContent.find(".tabs-box");
	var tabBoxHeight = [tabBox.eq(0).height(),tabBox.eq(1).height()]
	function setCss(translate,index){
		if(tabBoxHeight[index]){
			tabs.css({"height":tabBoxHeight[index]});
		}
		HHN.iScroller(true);
		return {
			'height':tabBoxHeight[index],
			'-webkit-transform':'translate('+translate+')',
			'-webkit-transition':'500ms linear',
			'transform':'translate('+translate+')',
			'transition':'500ms linear'
		}
	}
	function setSlider(direction,index){
		var d = (direction=="left") ? 1 : (direction=="right") ? 0 : null;
		if(index==d){
			return false;
		}
		index = d==1 ? index+1 : d==0 ? index-1 : index;
		var len=-(100/tabsNav.length)*index+"%";
		tabsContent.css(setCss(len,index));
		tabsNav.eq(index).addClass('on').siblings().removeClass('on');
		line.css(setCss(100*index+"%")); 		
	};
	//init
	setSlider("click",0);
	//bind 
	tabsNav.click(function(){
		setSlider("click",$(this).index());
	});
	tabBox.swipeLeft(function(){
		setSlider("left",$(this).index());
	})
	tabBox.swipeRight(function(){
		setSlider("right",$(this).index());
	})
	/*
	if(appRefer!="cf"){
		var investFixed = document.getElementById("investFixed");
		var int_amount = document.getElementById("int_amount");
		var BtnInvest = document.getElementById("BtnInvest");
		$(investFixed).on("click",function(event){
			var target = event.target;
			if(target===int_amount){
				$(window).scrollTop(0);
				$(investFixed).css({"top":"54px"});
				return;
			}
			if(target!==int_amount && target!=BtnInvest){
				$(investFixed).css({"top":"auto"});	
			}
		});
	}
	*/
});