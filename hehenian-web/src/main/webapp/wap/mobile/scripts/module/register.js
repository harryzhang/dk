Zepto(function(){
	var $form = $("#form");
	var form = new HHN.Form($form);
	var refferee = $("#refferee");
	var username = $("#userName"),email=$("#email");
	var tel = $("#telephone");
	//验证用户名是否已经存在
	function checkRegister(str) {
		var param = {};
		if (str == "userName") {
			param["paramMap.email"] = "";
			param["paramMap.userName"] = username.val();
		} else {
			param["paramMap.email"] = email.val();
			param["paramMap.userName"] = "";
		}
		$.post("/ajaxCheckRegister.do", param, function(data) {
			if (data == 3 || data == 4) {
				if (str == "userName") {
					username.focus();
					HHN.popup({type:"alert",content:"该用户已存在",element:username});
				}
			} else {
                register($form);
			}
		});
	}
	//验证手机号码是否已经注册
	/*function checkTel() {
		var param = {};
		param["paramMap.telephone"] = tel.val();
		$.post("/telephoneCode.do", param, function(data) {
			if (data == "-1") {
				tel.focus();
				HHN.popup({type:"alert",content:"验证码获取失败",element:tel});
			} else if (data == "5") {
				tel.focus();
				HHN.popup({type:"alert",content:"该手机号码已被使用",element:tel});
			} else {
				//if($.trim(refferee.val())!==""){
				//	check = checkRefferee();
				//} else {
					register($form);
				//}
			}
		});
	}*/
	//验证推荐人是否存在 
	function checkRefferee(){
		var param = {};
		param["refferee"] = refferee.val();
		$.post("/queryValidRecommer.do", param, function(data) {
			if (data == 1) {
				refferee.focus();
				HHN.popup({type:"alert",content:"推荐人不存在",element:refferee});
			} else {
				register($form);	
			}
		});
	};
	function register($form){
        $form.submit();
		/*var url = $form.attr("action");
		var postdata = $form.serialize();
		$.ajax({
			type: 'POST',
			url: url+"?_="+new Date().getTime(),
			data:postdata,
			success: function(datas){
				var data = ""+datas;
				if (data == "注册成功") {
					HHN.popLoading("/webapp/webapp-index.do","注册成功，跳转中...");
				} else {
					HHN.popup({type:"alert",content:data});	
				}				
			},
			error: function(xhr, type){
				HHN.popup({type:"alert",content:"服务器繁忙，请稍后重试!"});
			}
		});*/
	}
	
	$("#btnLogin").on("click",function(){
		form.validate({
			error:function(text,setting,input,$form){
				input.focus();
				var at = HHN.popup({type:"alert",content:text,element:input});
				var offset = at.offset(),top=offset.top,height=at.height()+20;
				var win = $(window),scrollTop = win.scrollTop();
				if(top+height<scrollTop){
					if(top+height-scrollTop<0){
							win.scrollTop(0)
					} else {
						win.scrollTop(scrollTop-100)
					}
				}
			},
			success:function($form){
				HHN.popup({type:"loading",content:'注册中...'});
				//验证用户名，手机，推荐人是否存在 
				checkRegister("userName");
			}
		});
		return false;
	});
	var zcnr = $("#zcnr").html();
	$("#zcxy").on("click",function(){
		HHN.popup({type:"content",content:zcnr,titleText:'注册协议',cla:'popup-text',maskClick:true});
		return false;
	})
	
});