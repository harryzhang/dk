Zepto(function(){
	var doSet = $("#doFeedback");
	var form = new HHN.Form("form");
	var feedbackContent =$("#feedbackContent");
	doSet.on("click",function(){
		form.validate({
			error:function(text,setting,input,$form){
				HHN.popup({type:"alert",content:text,element:input});
				input.focus();
			},
			success:function($form){
				var url = $form.attr("action");
				$.ajax({
					type: 'GET',
					url: url+"?_="+new Date().getTime(),
					beforeSend: function(){
						HHN.popup({type:"loading"});	
					},
					success: function(data){
						setTimeout(function(){
							HHN.popup({type:"alert",content:'反馈成功!',cla:'success'});
						},2000);
					},
					error: function(xhr, type){
						HHN.popup({type:"alert",content:"服务器繁忙，请稍后重试!"});
					}
				})
			}
		},feedbackContent);
		return false;
	});
});