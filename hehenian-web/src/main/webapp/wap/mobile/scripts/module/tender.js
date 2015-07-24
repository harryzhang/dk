Zepto(function(){
	var tipsInfo = $("#tipsInfo"),recharge = $("#recharge");
	$("#tipsBtn").click(function(){
		var height = tipsInfo.height();
		tipsInfo.fadeOut(function(){
			recharge.fadeIn();	
		});
		return false;
	});
	var handler = $("div.select-month");
	handler.on("click",function(index){
		var input = $("#"+$(this).attr("for"));
		var parent = $(this).parent();
		HHN.popup({
			type:"content",
			content:handler.attr("content"),
			fn:function(){
				var bank = $("#bankList");
				bank.on("click","a",function(){
					var value = $(this).attr('data'),text = $(this).text();
					parent.find("input").val(value);
					parent.find(".input").html(text);
					input.val(value);
					HHN.popupClose();
				});
			}
		});
	});
	var doSet = $("#doSet");
	var form = new HHN.Form("form");
	var rateStart = $("#rateStart"),rateEnd = $("#rateEnd");
	var monthStart = $("#monthStart"),monthEnd = $("#monthEnd");
	var redirect = $("#redirect");
	//开启
	doSet.on("click",function(){
		form.validate({
			error:function(text,setting,input,$form){
				HHN.popup({type:"alert",content:text,element:input});
				input.focus();
			},
			success:function($form){
				if(rateStart.val()>rateEnd.val()){
					HHN.popup({type:"alert",content:'年利率范围为0-24%!'});
					return false;	
				}
				if(monthStart.val()>monthEnd.val()){
					HHN.popup({type:"alert",content:'请选择正确的借款期限'});
					return false;
				}
				var url = $form.attr("action");
				var data = $form.serialize();
				$.ajax({
					type: 'POST',
					url: url+"?_="+new Date().getTime(),
					data: data,
					beforeSend: function(){
						HHN.popup({type:"loading"});	
					},
					success: function(data){
						if (data.length < 20) {
							HHN.popup({type:"alert",content:data});
							return false;
						}
						HHN.setPageBack(1);
						redirect.html(data);
					},
					error: function(xhr, type){
						HHN.popup({type:"alert",content:"服务器繁忙，请稍后重试!"});
					}
				})
			}
		});
		return false;
	});
	//关闭
	var doClose =$("#doClose"),closeUrl = "/automaticBidModify.do";
	doClose.on("click",function(){
		$.ajax({
			type: 'POST',
			url: closeUrl+"?_="+new Date().getTime(),
			data: "paramMap.close=close",
			beforeSend: function(){
				HHN.popup({type:"loading"});	
			},
			success: function(data){
				if (data.length < 30) {
					HHN.popup({type:"alert",content:data});
                    location.reload();
					return false;
				}
				HHN.setPageBack(1);
				redirect.html(data);
			},
			error: function(xhr, type){
				HHN.popup({type:"alert",content:"服务器繁忙，请稍后重试!"});
			}
		})
		return false;
	});
});