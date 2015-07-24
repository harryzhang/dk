Zepto(function(){
	var codeNum = $("#codeNum"),username = $("input.username"),code=$("#code");
	function switchCode() {
		codeNum.attr("src", "/admin/imageCode.do?pageId=userlogin&d=" + new Date().getTime());
	}
	//code 
	codeNum.on("click",function(){
		switchCode();
	});
	
	var form = new HHN.Form("form");
	$("#btnLogin").on("click",function(){
		form.validate({
			error:function(text,setting,input,$form){
				HHN.popup({type:"alert",content:text,element:input});
				input.focus();
			},
			success:function($form){
				var url = $form.attr("action");
				var postdata = $form.serialize();
				$.ajax({
					type: 'POST',
					url: url+"?_="+new Date().getTime(),
					data:postdata,
					beforeSend: function(){
						HHN.popup({type:"loading",content:'绑定中...'});	
					},
					success: function(datas){
						datas = HHN.parseJSON(datas);
						var data=datas.msg;
						if (data == 1) {
							HHN.popLoading("/webapp/webapp-index.do","绑定成功，跳转中...");
						} else if(data == 2){
							code.val("").focus();
							switchCode();
							HHN.popup({type:"alert",content:'验证码错误',element:code});
						} else if (data == 3) {
							username.focus();
							HHN.popup({type:"alert",content:'用户名或密码错误',element:username});
						} else if (data == 4 || data==200) {
							username.focus();
							HHN.popup({type:"alert",content:'该用户已被禁用',element:username});
							$("#btn_login").attr('disabled', false);
						} else if(data==5){
							HHN.popup({type:"alert",content:'输入账号的手机号码<br/>和彩之云手机号码不一致'});
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