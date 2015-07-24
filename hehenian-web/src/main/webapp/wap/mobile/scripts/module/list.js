Zepto(function(){
	var curPage = 2;
	$(window).bind("scroll", function (event) {
		HHN.isScroll({
			url:"/webapp/webapp-finance-more.do",
			post:"curPage="+curPage,
			page:curPage,
			success:function(){
				curPage++;
				if(HHN.type(HHN.initInvestList)=="function"){
					HHN.initInvestList();	
				}
			}
		});
	});
});