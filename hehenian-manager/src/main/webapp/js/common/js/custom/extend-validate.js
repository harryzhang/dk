$.extend($.fn.validatebox.defaults.rules, {
				CHS: {
					validator: function (value, param) {
						return /^[\u0391-\uFFE5]+$/.test(value);
					},
					message: '请输入汉字'
				},
				english : {// 验证英语
			        validator : function(value) {
			            return /^[A-Za-z]+$/i.test(value);
			        },
			        message : '请输入英文'
			    },
			    ip : {// 验证IP地址
			        validator : function(value) {
			            return /\d+\.\d+\.\d+\.\d+/.test(value);
			        },
			        message : 'IP地址格式不正确'
			    },
				ZIP: {
					validator: function (value, param) {
						return /^[1-9]\d{5}$/.test(value);
					},
					message: '邮政编码不存在'
				},
				QQ: {
					validator: function (value, param) {
						return /^[1-9]\d{4,10}$/.test(value);
					},
					message: 'QQ号码不正确'
				},
				mobile : {// 验证手机号码
			        validator : function(value) {
			            return /^1[34578]\d{9}$/.test(value);
			        },
			        message : "手机号码格式不正确"
			    },
			    phone : {// 验证电话号码
			        validator : function(value) {
			            return /^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/.test(value);
			        },
			        message : "格式不正确,请使用下面格式:020-88888888"
			    },
			    phoneAndMobile : {// 验证电话号码
			        validator : function(value) {
//			    		if(value.length == 11){
//			    			 return /^1[3458]\d{9}$/.test(value);
//			    		}else {
//			    			if(value.substring(0,4) == '400')
//			    				return /^400[016789]\d{6}$/.test(value);
//			    			else 
//			    				return /^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/.test(value);
//			    		}
			    		return /^(1[3,5,8,7]{1}[\d]{9})|(((400)-(\d{3})-(\d{4}))|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{3,7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)$/.test(value);
			        },
			        message : "格式不正确,请输入座机或移动电话！"
			    },
				number: {
					validator: function (value, param) {
						return /^[0-9]+.?[0-9]*$/.test(value);
					},
					message: '请输入数字'
				},
				integer:{
					validator:function(value,param){
						return /^[+]?[1-9]\d*$/.test(value);
					},
					message: '请输入整数'
				},
				range:{
					validator:function(value,param){
						if(/^[1-9]\d*$/.test(value)){
							return value >= param[0] && value <= param[1]
						}else{
							return false;
						}
					},
					message:'输入的数字在{0}到{1}之间'
				},
				minLength:{
					validator:function(value,param){
						return value.length >=param[0]
					},
					message:'至少输入{0}个字'
				},
				maxLength:{
					validator:function(value,param){
						return value.length<=param[0]
					},
					message:'最多{0}个字'
				},
				//select即选择框的验证
				selectValid:{
					validator:function(value,param){
						if(value == param[0]){
							return false;
						}else{
							return true ;
						}
					},
					message:'请选择'
				},
				loginName: {
					validator: function (value, param) {
						return /^[\u0391-\uFFE5\w]+$/.test(value);
					},
					message: '登录名称只允许汉字、英文字母、数字及下划线。'
				},
				equalTo: {
					validator: function (value, param) {
						return value == $(param[0]).val();
					},
					message: '两次输入的字符不一至'
				},
				idcard : {
			    	// 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X  
			        validator : function(value) {
			            return /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(value);
			        },
			        message : "身份证号码格式不正确"
			    },
			});
