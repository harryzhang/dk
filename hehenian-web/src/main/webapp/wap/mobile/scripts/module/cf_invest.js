Zepto(function(){
	//loading
	HHN.loadPage();
	//slider
	var slider = $("#cfSlider");
	if(slider.length>0){
		var slider = $('.slider-content'); 
		var sliderItem = slider.find(".slider-item");
		var sliderNum = sliderItem.length;
		sliderItem.css({width:(100/sliderNum)+"%"});
		slider.css({width:sliderNum*100+"%"}).show();
		var flipsnap = Flipsnap('.slider-content',{
			auto:true,
			pointBar:false
		});
		var sliderPrev = $(".slider-prev"),sliderNext = $(".slider-next");
		sliderPrev.on("click",function(){
			flipsnap.toPrev();
			return false;
		});
		sliderNext.on("click",function(){
			flipsnap.toNext();
			return false;
		});
	}
});