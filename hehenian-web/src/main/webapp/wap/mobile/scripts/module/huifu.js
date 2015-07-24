Zepto(function(){
	var reg = $("#btnReg"),tips = $("#huifuTips"),tj=$("#huifuTj"),save = $("#btnSave");
	reg.click(function(){
		tips.hide();
		tj.show();
		return false;
	});
	var form = new HHN.Form("form");
	var tuijianren = $("#tuijianren"),input = $("#tuijianren");
	save.click(function(){
		form.validate({
			error:function(text,setting,input,$form){
				HHN.popup({type:"alert",content:text,element:input});
				input.focus();
			},
			success:function($form){
				var url = $form.attr("action");
				var data = $form.serialize();
				$.ajax({
					type: 'POST',
					url: url+"?_="+new Date().getTime(),
					data:data,
					beforeSend: function(){
						HHN.popup({type:"loading"});	
					},
					success: function(data){
						if(data>0){
							HHN.popLoading("/registerChinaPnr.do","保存中...")
						}else if(data=="-3"){
                            HHN.popup({type:"alert",content:'推荐人不能为自己!'});
                        }else{
							HHN.popup({type:"alert",content:'推荐人不存在!'});
						}
					},
					error: function(xhr, type){
						HHN.popup({type:"alert",content:"服务器繁忙，请稍后重试!"});
					}
				});
			}
		});
		return false;
	});
});