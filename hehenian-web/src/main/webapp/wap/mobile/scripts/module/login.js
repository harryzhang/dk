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
	var loginBtn = $("#btnLogin");
	loginBtn.on("click",function(){
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
						HHN.popup({type:"loading",content:'登录中...'});	
					},
					success: function(datas){
						datas = HHN.parseJSON(datas);
						var data=datas.msg;
						if (data == 1) {
                            $.get("<c:url value='/updateUserUsableSum.do?_='/>"+new Date().getTime());
                            var fromUrl = $("#fromUrl").val();
                            if(fromUrl==""){
                                HHN.popLoading("/webapp/webapp-money.do","登录成功，跳转中...")
                            }else{
                                fromUrl = decodeURI(fromUrl);
                                HHN.popLoading(fromUrl,"登录成功，跳转中...")
                            }


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
	//enter->login
	$(window).on("keydown",function(event){
		var keycode = event.keyCode;
		if(keycode===13){
			loginBtn.click();
			return false;	
		}
	});
});