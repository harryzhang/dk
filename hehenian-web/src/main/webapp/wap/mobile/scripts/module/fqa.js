Zepto(function(){
	var list = $("#fqaList");
	var dd = null,dt = null;
	list.on("click","dt",function(){
		if(dt){
			dt.removeClass("select");	
		}
		if(this=== (dt ? dt[0] : dt)){
			dt.next().hide();
			dt = null;
		} else {
			dt = $(this);
			if(dd){
				dd.removeClass("animate-route").hide();	
			}
			dd = dt.next();
			dd.show().addClass("animate-route");
			dt.addClass("select");
		}
		HHN.iScroller();
	});
});