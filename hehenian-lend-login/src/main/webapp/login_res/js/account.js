	$(function(){
		
		
		
		
			//下一步
			$("#resetNextStep").bind("click",function(){
				var mobilePhone = $("#mobilePhone").val();
				var identifyCode = $("#identifyCode").val();
				if(mobilePhone==null || mobilePhone.length==0){
					popWindow("请输入手机号");
					return;
				}
				if(identifyCode==null || identifyCode.length==0){
					popWindow("请输入手机验证码");
					return;
				}
				var options = {type:"GET",url:"${loginServerUrl }/account/verifyPwdStep1.do",data:{mobilePhone:mobilePhone,identifyCode:identifyCode}};
				ajaxRequest(options,function(data){
					if(data.result==1){
						popWindow("手机验证码不正确");
					}else{
						window.location.href="${loginServerUrl }/account/resetJumpPage.do";
					}
				});
			});

			$("#resetNextStep2").bind("click",function(){
				var realName = $("#realName").val();
				var idNo = $("#idNo").val();
				var pwdFlag = $("#pwdFlag").val();
				if(realName==null || realName.length==0){
					popWindow("请输入真实姓名");
					return;
				}
				if(idNo==null || idNo.length==0){
					popWindow("请输入身份证号码");
					return;
				}
				var options = {type:"POST",url:"${loginServerUrl }/account/verifyPwdStep2.do",data:{realName:realName,idNo:idNo}};
				ajaxRequest(options,function(data){
					if(data.result==1){
						popWindow("认证超时，请返回上一步");
					}else if(data.result==2){
						popWindow("认证失败");
					}else{
						window.location.href="${loginServerUrl }/account/reInputPwd.do?pwdFlag="+pwdFlag;
					}
				});
			});

			$("#updPwdBtn").bind("click",function(){
				var password = $("#pwd").val();
				var confirmPwd = $("#confirmPwd").val();
				var pwdFlag = $("#pwdFlag").val();
				var p = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$/;
				if(pwdFlag == 'pay'){//支付密码
					if (password == "" || confirmPwd == "") {
						popWindow("请完整填写密码信息");
						return;
					} else if (password.length < 8 || password.length > 20) {
						popWindow("密码长度为8-20位,由数字与字母组成");
						return;
					}
					else if(!p.test(password)){
						popWindow("密码长度为8-20位,由数字与字母组成");
						return;
					}
					else if (password != confirmPwd) {
						popWindow("两次密码不一致");
						return;
					}
					var options = {type:"POST",url:"${loginServerUrl }/account/updateLoginPwd.do",data:{pwd:password,confirmPwd:confirmPwd,pwdFlag:pwdFlag}};
					ajaxRequest(options,function(data){
						if(data.result == 1){
							popWindow("填写的密码信息格式有误");
						}else if (data.result == 2) {
							popWindow("两次密码输入不一致");
						} else if (data.result == 3) {
							popWindow("新密码不能与原密码相同");
						} else if (data.result == 4) {
							popWindow("修改密码失败");
						} else {//密码修改成功，跳到成功页面
								window.location.href="${loginServerUrl }/account/resetPaySucc.do";
						}
					});
				}else{//登陆密码
					if (password == "" || confirmPwd == "") {
						popWindow("请完整填写密码信息");
						return;
					} else if (password.length < 6 || password.length > 20) {
						popWindow("密码长度必须为6-20个字符");
						return;
					} else if (password != confirmPwd) {
						popWindow("两次密码不一致");
						return;
					}
					var options = {type:"POST",url:"${loginServerUrl }/account/updateLoginPwd.do",data:{pwd:password,confirmPwd:confirmPwd,pwdFlag:pwdFlag}};
					ajaxRequest(options,function(data){
						if(data.result == 1){
							popWindow("填写的密码信息格式有误");
						} else if (data.result == 2) {
							popWindow("两次密码输入不一致");
						} else if (data.result == 3) {
							popWindow("新密码不能与原密码相同");
						} else if (data.result == 4 || data.result == 5) {
							popWindow("修改密码失败");
						} else {//密码修改成功，跳到登录页面
							popWindowCab("修改密码成功,请重新登录",function(){
								window.location.href="${loginServerUrl }/login/index.do";
							});
						}
					});
				}
			});


			$("#login_btn").bind("click",function(){
				login();
			});

			//register
			$("#regBtn").bind("click",function(){
				var userName = $("#userName").val();
				if(userName==null || userName.length==0){
					popWindow("请输入登录用户名");
					return;
				}
				if (userName.length < 5 || userName.length > 25) {
					popWindow("用户名长度为5-25个字符");
					return;
				}
				if (!/^[A-Za-z0-9_]+$/.test(userName)) {
					popWindow("用户名由字母数字下划线组成");
					return;
				} 
				//password
				var password = $('#password').val();
				if (password == "" || password.length==0) {
					popWindow("请设置您的密码");
					return;
				}
				if (password.indexOf(" ")>=0) {
                    popWindow("密码不能包含空格");
					return;
                }
                if (password.length < 6) {
                	popWindow("密码长度为6-20个字符");
					return;
				} 
				//confirmPassword
				var confirmPassword = $('#confirmPassword').val();
				if (confirmPassword == "" || confirmPassword.length==0) {
					popWindow("请再次输入密码确认");
					return;
				}
				if (confirmPassword != password) {
					popWindow("两次输入的密码不一致");
					return;
				} 
				//telephone
				var mobilePhone = $('#mobilePhone').val();
				if (mobilePhone == "" || mobilePhone.length==0) {
					popWindow("请输入手机号");
					return;
				}
                var re =new RegExp("/^1[3|5|7|8|][0-9]{9}$/");
                if (!/^0{0,1}(13[0-9]|15[0-9]|18[0-9]|14[0-9]|17[0-9])[0-9]{8}$/.test(mobilePhone)){
                     popWindow("输入手机号的格式有误");
                     return;
                }
				var identifyCode = $("#identifyCode").val();
				if(identifyCode==null || identifyCode.length==0){
					popWindow("请输入验证码");
					return;
				}
				$(this).attr('disabled',true);
				var options = {type:"POST",url:"${loginServerUrl }/account/register.do",data:{userName:userName,password:password,mobilePhone:mobilePhone,identifyCode:identifyCode}};
				ajaxRequest(options,function(data){
					if(data.result=='注册成功'){
						window.location.href="${loginServerUrl }/profile/index.do";
					}else{
						popWindow(data.result);
						$('#regBtn').removeAttr('disabled');
					}
				});
			});

			//code 
			$("#codeNum").bind("click",function(){
				$(this).attr("src", "${loginServerUrl }/common/imageCode.do?pageId=userlogin&d=" + new Date().getTime());
			});

			//邀请码
			$("#invite-btn").bind("click",function(){
				var code = $("#code").val();
				if(code==null || code==''){
					popWindow("请输入邀请码");
					return;
				}
				var options = {type:"POST",url:"${loginServerUrl }/account/bindInviteCode.do",data:{code:$("#code").val()}};
				ajaxRequest(options,function(data){
					if(data.result==1){
						popWindow("邀请码不能为空");
					}else if(data.result==2){
						popWindow("邀请码无效");
					}else if(data.result==3){
						popWindow("该邀请码已被使用");
					}else{
						popWindowCab("邀请码使用成功",function(){
							window.location.href="${loginServerUrl }/product/plist.do?channel=1&subChannel=1";
						});
					}
				});
			});
			
			//获取手机验证码
			$('#ida').bind("click",function(){
				if ($(this).attr("disable") == null) {
				   var param = {checkPhone:true};
				   sendPhoneVirifyCode(param);
				}
			});
	});


	function login(){
		var userName = $("#userName").val();
		var password = $("#password").val();
		var code = $("#code").val();
		if(userName.length==0 || password.length==0 || code.length==0){
			popWindow("登录信息填写不完整");
			return;
		}
		var options = {type:"POST",url:"${loginServerUrl }/login/login.do",data:{userName:userName,password:password,code:code}};
		ajaxRequest(options,function(data){
		    if(data.result==1){
				popWindow("登录信息填写不完整");
			}else if(data.result==2){
				popWindow("验证码输入有误");
			}else if(data.result==3){
				popWindow("用户名或密码错误");
			}else{
				window.location.href="${loginServerUrl }/product/plist.do";
			}
		});
	}


    //异步请求
	function ajaxRequest(options,callback){
		$.ajax({ 
			type:options.type,
			dataType: "json",			
			url:options.url, 
			data:options.data,
			success:callback,
			error:function(data){
				popWindow("网络异常，稍后重试");
			}
		});
	}


		
	function resetPwdNextStep(data){
		var dt = data;
		popWindow(dt.message);
	}

	function sendPhoneVirifyCode(param) {
			var mobilePhone = $("#mobilePhone");
			if(mobilePhone!=null){
				if(mobilePhone.val()==null || mobilePhone.val().length==0){
					popWindow("请输入手机号码");
					return;
				}
				var verifyR = verifyPhone_(mobilePhone.val());
				if(verifyR!=""){
					popWindow(verifyR);
					return;
				}
				param["mobilePhone"]=mobilePhone.val();
			}
			
			$.post("${loginServerUrl }/common/sendPhoneVirifyCode.do", param, function(data) {
				if(data.ret==1){
					popWindow("发送验证码失败");
				}else if(data.ret==2){
					popWindow("手机号码不存在");
				}else if(data.ret==3){
					popWindow("手机号码已存在");
				}else{
					$("#ida").attr("disable", true).text("60s重获");
					telephoneTimer_();
					//popWindow("验证码："+data.ret);
				}
			},"json");
	}
	//倒计时
	function telephoneTimer_() {
		var count = 60;
		var timer=setInterval(function(){
			if(count <= 0){
				clearInterval(timer);
				$("#ida").removeAttr("disable").text("获取验证码");
			}else{
				count--;
				$("#ida").text(count+"s重获");
			}
		},1000);
	}

	function verifyPhone_(telephone){
	    if (telephone == "") {
	        return "请输入手机号";
	    } else if (!/^1[3|4|5|8|7][0-9]\d{4,8}$/.test(telephone)) {
	        return "输入手机号的格式有误";
	    }else if(telephone.length!=11){
	        return "输入手机号的格式有误";
	    }else{
	        return "";
	    }
	}

