Zepto(function(){
	var call = $("#call");
	call.on("click",function(){
		HHN.popup({type:"confirm",fixed:true,content:"您确定要拨打客服电话吗？",element:$(this)});
		return false;
	});
	var qq = $("#qq");
	qq.on("click",function(){
		var iframe = qq.find("iframe")[0].contentWindow;
		iframe.document.getElementById("launchBtn").click();
		return false;
	});
});