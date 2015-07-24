Zepto(function(){
	var accountList = $("#accountList");
	if(accountList[0]){
		accountList.on("click","li",function(){
			var content = $.trim($(this).attr("content"));
			var title = $.trim($(this).attr("title"));
			if(content!=="" && title!==""){
				HHN.popup({type:"content",titleText:title,content:content,fixed:true,cla:"accout-popup",maskClick:true});
			}
			return false;
		});
		//check number
		(function () {
			var $animateNum = $(".animateNum");
			checkAnimateNum();
			/*
			$(window).bind("scroll", function () {
				checkAnimateNum();
			});
			*/
			function checkAnimateNum() {
				//var winRange = {top: $(window).scrollTop(), bottom: $(window).scrollTop() + $(window).height()};
				$animateNum.each(function () {
					var targetNum = $(this).attr("data-animateTarget");
					//if (winRange.top <= ($(this).offset().top + $(this).height()) && winRange.bottom >= $(this).offset().top && !$(this).data("start")) {
						$(this).data("start", true);
						new HHN.AnimateNum({
							obj: $(this),
							target: targetNum,
							totalTime: 1000
						})
					//}
				})
			}
		})();
	}
	var fundTable = $(".fund-table");
	if(fundTable.length>0){
		var curPage = 1;
		HHN.isScroll({
			list:$("#table"),
			items:"dd",
			url:"webapp/webapp-invest-zr-more.do",
			post:"curPage="+curPage,
			page: curPage,
			scrollMax:10,
			success:function(){}
		});
	}
});