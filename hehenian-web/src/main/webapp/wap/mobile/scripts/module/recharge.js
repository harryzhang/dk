Zepto(function(){
	//充值方式
	var GateBusiId = $("#GateBusiId");
	var handler = $("#selectBank");
	var tipsInfo = $("#tipsInfo"),recharge = $("#recharge");
	//init
	GateBusiId.find("a.select").find("input").prop("checked",true);
	GateBusiId.on("click","a",function(event){
		var target = $(event.target);
		handler.find("input").val("请选择充值银行");
		$("#"+handler.attr("for")).val("");
		if(target.attr("type")=="radio"){
			var a = $(this).closest("a");
			a.addClass("select").siblings().removeClass("select");
			return;
		}
		$(this).find("input").prop("checked",true);
		$(this).addClass("select").siblings().removeClass("select");
		return false;
	});
	$("#tipsBtn").click(function(){
		var height = tipsInfo.height();
		tipsInfo.fadeOut(function(){
			recharge.css({"visibility":"visible"});	
		});
		return false;
	});
	handler.on("click",function(){
		var rechargeType = GateBusiId.find("input:checked").val();
		var bankListContent = "";
		var popupTouch = false;
		if(rechargeType==0){
			popupTouch = true;
			bankListContent = handler.attr("content0")
		} else if(rechargeType==1){
			bankListContent = handler.attr("content1")
		}
		var input = $("#"+$(this).attr("for"));
		HHN.popup({
			type:"content",
			content:bankListContent,
			fixed:true,
			touch:popupTouch,
			fn:function(){
				var bank = $("#bankList");
				bank.on("click","a",function(){
					var value = $(this).attr('data');
					var text = $(this).attr("title");
					handler.find("input").val(text);
					input.val(value);
					HHN.popupClose();
					return false;
				});
			}
		});
		return false;
	});
	var doRecharge = $("#doRecharge");
	var form = new HHN.Form("form");
	doRecharge.on("click",function(){
		var money = $("#money");
		form.validate({
			error:function(text,setting,input,$form){
				if(input[0].id=="int_openBankId"){
					input = $("#selectBank");
				}
				HHN.popup({type:"alert",content:text,element:input});
				input.focus();
			},
			success:function($form){
				$form.submit();
			}
		});
		return false;
	});
});