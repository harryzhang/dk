(function(root,$,undefined){
	var HHN = root.HHN = {};
	var doc = document,win = window,Body= $("body"),wp = $("#wrap");
	//get a element,and return it;
	function getDom(d){
		if(typeof d=="string"){
			d = (d[0]==="#" || d[0]===".") ? $(d) : $(doc.getElementById(d));
		}
		//return jquery object;
		return $(d)[0];
	}
	//get a element's attr,and complie it to json object;
	function getAttrToJSON(ele,attr){
		var data = $(ele).attr(attr);
		if(ele && attr && data!==undefined){
			try{
				//data = eval("("+data+")");
				data = HHN.parseJSON(data);
			} catch (err) {
				alert(attr+" 属性JSON格式错误!");
			}
		} else {
			alert(attr+" 属性不存在!");
		
		return data ? data : null;
	}
	//检测数据类型;
	HHN.type = function (o) {
        var _toS = Object.prototype.toString;
        var _types = {
            'undefined': 'undefined',
            'number': 'number',
            'boolean': 'boolean',
            'string': 'string',
            '[object Function]': 'function',
            '[object RegExp]': 'regexp',
            '[object Array]': 'array',
            '[object Date]': 'date',
            '[object Error]': 'error'
        };
        return _types[typeof o] || _types[_toS.call(o)] || (o ? 'object' : 'null');
    };
	
	//转换成json字符串
	// the code of these two functions is from mootools   
    var $specialChars = { '\b': '\\b', '\t': '\\t', '\n': '\\n', '\f': '\\f', '\r': '\\r', '"': '\\"', '\\': '\\\\' };
    var $replaceChars = function (chr) {
        return $specialChars[chr] || '\\u00' + Math.floor(chr.charCodeAt() / 16).toString(16) + (chr.charCodeAt() % 16).toString(16);
    };
    HHN.stringify = function (o) {
        var s = [];
        switch ($.type(o)) {
            case 'undefined':
                return 'undefined';
                break;
            case 'null':
                return 'null';
                break;
            case 'number':
            case 'boolean':
            case 'date':
            case 'function':
                return o.toString();
                break;
            case 'string':
                return '"' + o.replace(/[\x00-\x1f\\"]/g, $replaceChars) + '"';
                break;
            case 'array':
                for (var i = 0, l = o.length; i < l; i++) {
                    s.push($.toJSON(o[i]));
                }
                return '[' + s.join(',') + ']';
                break;
            case 'error':
            case 'object':
                for (var p in o) {
                    s.push('"'+p+'":' + $.toJSON(o[p]));
                }
                return '{' + s.join(',') + '}';
                break;
            default:
                return '';
                break;
        }
    };
    //转换成json对象
    HHN.parseJSON = function (s) {
        if ($.type(s) != 'string' || !s.length) return null;
        //去掉json字符串中多余的空格，tab，回车
        s = s.replace(/[\t|\n|\x0B|\f|\r]*/g, "");
        return eval('(' + s + ')');
    };
	
	//loading
	HHN.loadPage = function(wait,loading,wrap,nav){
		var loading = getDom(loading) || $("#loading"),wrap = getDom(wrap) || wp,nav=getDom(nav) || $("#nav");
		if(wait){
			setTimeout(function(){
				loading.hide();
				wrap.show();
			},wait);
		} else {
			loading.hide();
			wrap.show();
		};
		HHN.setPageBack(1);
	};
	HHN.setPageBack = function(value){
		$.fn.cookie("PAGEBACK",value,{path:"/"});
	}
	//创建interval,解决移动端按"返回键"时运行
	HHN.pageBack = function(){
		HHN.setPageBack(0);
		var pageTimer = setInterval(function(){
			if($.fn.cookie("PAGEBACK")==1){
				var popup = document.getElementById("popup");
				if(popup && $(popup).hasClass("popup-load")){
					HHN.popupClose();
				}
			}
		},100);
	}
	//监听横屏
	HHN.orientationChange = function(fn90,fn180) {
		function orientationChange(){
			switch (root.orientation) {
				case 0:
				case -90 : // Landscape: turned 90 degrees counter-clockwise   
				case 90:
					if(typeof fn90 === "function"){
						fn90();
					}
					// Landscape: turned 90 degrees clockwise   
					// Javascript to steup Landscape view   
					break;
				case 180:
					if(typeof fn180 === "function"){
						fn180();
					}
					// Upside-down Portrait   
					// Javascript to setup Portrait view   
					break;
			}
		}
		window.addEventListener("onorientationchange" in window ? "orientationchange": "resize", orientationChange, false);
	};
	
	//form object
	var Form = HHN.Form = function(_form){
		this.form = $(getDom(_form));
		this.inputs = this.form.find("input[validate=true],select[validate=true],textarea[validate=true]");
		this.init();
	};
	Form.prototype = {
		init:function(options){
			var self = this;
			var inputs = this.inputs;
			//为input绑定change事件,当input值发生改变时，会对当前input进行一次验证
			var defaults = {
				event:null,
				disabled:null	
			};
			var setting = $.extend({},defaults,options);
			if(setting.event){
				this.bindChange(setting.event);
			}
			if(setting.disabled){
				this.disabledKey(this.form);
			}
		},
		bindChange:function(evt){
			var self = this;
			//绑定某些事件
			this.inputs.off(evt).on(evt,function(){
				var $this = $(this);
				self.validate(self.validateDefault,$this);
			});
		},
		validateDefault:{
			checkall:false,
			success:null,
			error:null,
			single:true,
			tips:{
				rule:"元素\"rule\"属性格式错误!",
				tips:"未设置当前输入框的提示语",
				username:"请输入正确的用户名",
				password:"密码不能包含空格，只能由数字、字母组成",
				length:"请输入正确的长度",
				email:"请输入正确的邮箱地址",
				mobile:"请输入正确的手机号码",
				zipcode:"请输入正确的邮编",
				decmal:"请输入浮点数",
				checkbox:"请勾选",
				chinese:"仅可以输入中文",
				empty:"不能为空",
				number:"只能输入数字",
				url:"请输入正确的URL",
				equal:"输入不一致",
				size:"数值超出范围"
			}
		},
		validate:function(options,one){
			var defaults = this.validateDefault;
			var setting = $.extend({},defaults,options);
			var $form = this.form,
				successFn = setting["success"],
				errorFn = setting["error"],
				checkall = setting["checkall"],
				tips = setting["tips"];
			if(!$form[0]){ return this.form; }
			
			//get all elements that will be valified;
			var inputs = (one!==undefined) ? one : this.inputs,len = (one!==undefined) ? 1: inputs.length;
			var self = this;
			
			//show tips info
			function showTips(input,name){
				if(!input[0]){ return; }
				//var text = eval("("+input.attr("tips")+")");
				var text = HHN.parseJSON(input.attr("tips"));
				var tipsText = (text && text[name]) ? text[name] : (tips[name]) ? tips[name] : tips["tips"];
				if(errorFn && typeof errorFn==="function"){
					errorFn(tipsText,setting,input,$form);
				} else {
					var error = input.parent().find(".error");
					if(error.length==0){
						input.after("<span class='error'>"+tipsText+"</span>");
					} else {
						error.html(tipsText);
					}
					if(!checkall){
						var val = input.val();
						input.val("").focus().val(val);
					}
				}			
			}
			//hide tips info
			function hideTips(input){
				if(!input[0]){ return; }
				var error = input.parent().find(".error");
				if(error.length>0){
					error.html("");
				}
			}
			
			//检测自定义的正则表达式
			function checkRegExp(reg,value){
				reg = eval(reg);
				if(!reg.test(value)){
					return false;
				}
				return true;
			}
			
			//检测身份证
			function checkIDcard(idcard) {
				var Errors = ["验证通过!", "身份证号码位数不对!", "身份证号码出生日期超出范围或含有非法字符!", "身份证号码校验错误!", "身份证地区非法!"];
				var area = {
					11 : "北京",
					12 : "天津",
					13 : "河北",
					14 : "山西",
					15 : "内蒙古",
					21 : "辽宁",
					22 : "吉林",
					23 : "黑龙江",
					31 : "上海",
					32 : "江苏",
					33 : "浙江",
					34 : "安徽",
					35 : "福建",
					36 : "江西",
					37 : "山东",
					41 : "河南",
					42 : "湖北",
					43 : "湖南",
					44 : "广东",
					45 : "广西",
					46 : "海南",
					50 : "重庆",
					51 : "四川",
					52 : "贵州",
					53 : "云南",
					54 : "西藏",
					61 : "陕西",
					62 : "甘肃",
					63 : "青海",
					64 : "宁夏",
					65 : "新疆",
					71 : "台湾",
					81 : "香港",
					82 : "澳门",
					91 : "国外"
				};
			
				var idcard, Y, JYM,data={};
				var S, M;
				var idcard_array = new Array();
				idcard_array = idcard.split("");
				//地区检验
				if (area[parseInt(idcard.substr(0, 2))] == null) {
					return Errors[4];
				}
				data["area"] = area[idcard.slice(0,2)];
				/*
				15位的旧身份证，最后一个数是单数的为男，双数的为女；。
				18位的看倒数第二位，单数的为男，双数的为女
				*/
				//获取性别;
				data["sex"] = idcard .slice(14,17)%2?  "男" : "女";
				
				//身份号码位数及格式检验
				switch (idcard.length) {
					case 15:
						if ((parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0 || ((parseInt(idcard.substr(6, 2)) + 1900) % 100 == 0 && (parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0)) {
							ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/; //测试出生日期的合法性
						} else {
							ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/; //测试出生日期的合法性
						}
						if (ereg.test(idcard)) {
							data["check"] = true;
							data["birthday"] = (new Date(idcard.slice(6,10) , idcard.slice(10,12)-1 , idcard.slice(12,14))).toLocaleDateString();
							return data;
						} else {
							return Errors[2];
						}
						break;
					case 18:
						//18位身份号码检测
						//出生日期的合法性检查
						//闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))
						//平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))
						if (parseInt(idcard.substr(6, 4)) % 4 == 0 || (parseInt(idcard.substr(6, 4)) % 100 == 0 && parseInt(idcard.substr(6, 4)) % 4 == 0)) {
							ereg = /^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/; //闰年出生日期的合法性正则表达式
						} else {
							ereg = /^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/; //平年出生日期的合法性正则表达式
						}
						if (ereg.test(idcard)) { //测试出生日期的合法性
							//计算校验位
							S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7 + (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9 + (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10 + (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5 + (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8 + (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4 + (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2 + parseInt(idcard_array[7]) * 1 + parseInt(idcard_array[8]) * 6 + parseInt(idcard_array[9]) * 3;
							Y = S % 11;
							M = "F";
							JYM = "10X98765432";
							M = JYM.substr(Y, 1); //判断校验位
							if (M == idcard_array[17]) {
								data["check"] = true;
								data["birthday"] = (new Date(idcard.slice(6,10) , idcard.slice(10,12)-1 , idcard.slice(12,14))).toLocaleDateString();
								return data; //检测ID的校验位
							} else {
								return Errors[3];
							}
						} else {
							return Errors[2];
						}
						break;
					default:
						return Errors[1];
						break;
				}
			}
			
			//verify
			for(var i=0; i<len; i++){
				var checked = true,input = inputs.eq(i),rule = input.attr("rule");
				if(!rule || rule==""){ 
					showTips(input,"rule");
					continue; 
				}
				//rule = eval("("+rule+")");
				rule = HHN.parseJSON(rule);
				var value = $.trim(input.val());
				
				//检测是否允许为空,默认不能为空
				var empty = false;
				if(rule.empty!==undefined && rule.empty===true){
					//empty为true时，表示可以为空
					if(rule.type==="checkbox" || input.attr("type")=="checkbox"){
						if(!input.prop("checked")){
							empty = true;
						}
					} else {
						if(value==""){
							empty = true;
						}
					}
				}
				//检测是否为空
				if(rule.empty!==undefined && rule.empty===false){
					if(value==""){
						showTips(input,"empty");
						checked = false;
					};
				}
				//检测是否有长度验证
				if(checked && rule.length!==undefined && rule.empty!==true){
					var ruleLen = rule.length.split("-");
					var min = ruleLen[0],max=ruleLen[1];
					if(value.length<min || value.length>max){
						showTips(input,"length");
						checked = false;
					}
				}
				
				//检测是否需要与某个值确定
				if(checked && rule.equal!==undefined){
					var equal = $(doc.getElementById(rule.equal));
					if(input.val()!==equal.val()){
						showTips(input,"equal");
						checked = false;
					}
				}
				
				//根据类型显示
				if(checked && rule.type){
					switch(rule.type){
						//用户名：能由数字字母和下划线组成,不允许为空
						case "username":
							if(!/^[A-Za-z0-9_]+$/gi.test(value)){
								checked = false;
							}
							break;
						//密码：由数字字母组成，不允许为空
						case "password":
							if(!/^[A-Za-z0-9]+$/gi.test(value)){
								checked = false;
							}
							break;
						//邮箱
						case "email":						
							if(!empty && !/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/gi.test(value)){
								checked = false;
							}
							break;
						//手机号码
						case "mobile":
							if(!empty && !/^0?(13|15|17|18|14)[0-9]{9}$/gi.test(value)){
								checked = false;
							}
							break;
						//邮编：6位数字
						case "zipcode":
							if(!empty && !/^\d{6}$/gi.test(value) || isNaN(value)){
								checked = false;
							}
							break;
						//浮点数：带小数值的数字
						case "decmal":
							if(!empty && !/^([+-]?)\d*\.\d+$/gi.test(value)){
								checked = false;
							}
							break;
						//身份证
						case "idcard":
							if(!empty && HHN.type(checkIDcard(value))=="string"){
								checked = false;
							}
							break;
						//checkbox
						case "checkbox":
							if(!empty && !input.prop("checked")){
								checked = false;	
							}
							break;
						//chinese
						case "chinese":
							if(!empty && !/^[\u4e00-\u9fa5]+$/gi.test(value)){
								checked = false;
							}
							break;
						//number
						case "number":
							if(!empty && !/^[0-9]+$/gi.test(value) || isNaN(value)){
								checked = false;
							}
							break;
						//number2,A numeric value greater than 0
						case "number2":
							if(!empty && !/^([1-9]\d*.\d*|0.\d*[1-9]\d*|0?.0+|0)|([1-9]\d*)$/gi.test(value) || isNaN(value)){
								checked = false;
							}
							break;
						//url

						case "url":
							///^http(s)?:\/\/$/gi.test(value);
							if(!empty && !/^http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w-\.\/\?%&=]*)?$/gi.test(value)){
								checked = false;
							}
							break;
					}
					if(!checked){
						showTips(input,rule.type);
					}
				}
				//检测是否有数值大小验证
				if(checked && rule.size!==undefined){
					var size = rule.size.split("-");
					var min = Number(size[0]),max=Number(size[1]);
					if(value<min || value>max){
						showTips(input,"size");
						checked = false;
					}
				}
				
				//前面空白、长度、类型检测都OK时,检测是否有自定义正则表达式验证
				if(checked && rule.reg!==undefined){
					if(!empty && !checkRegExp(rule.reg,value)){
						showTips(input,"reg");
						checked = false;
					}
				}
				//判断是否一次验证全部还是只验证某一个
				if(checkall===true){
					if(checked){
						hideTips(input);	
					}
				} else {
					if(checked){
						hideTips(input);
					} else {
						return false;	
					}
				}
			}
			//验证通过时,运行回调函数;
			//if(checked && one===undefined){
			if(checked){
				if(successFn && typeof successFn==="function"){
					successFn($form);	
				}
				return true;
			}
			return false;
		},
		disabledKey:function(keys){
			var $form = this.form,inputs = this.inputs,len = inputs.length;
			for(var i=0; i<len; i++){
				var input = inputs.eq(i),rule = getAttrToJSON(input,"rule");
				if(rule.disabled!==undefined && $.trim(rule.disabled)!==""){
					input.on("keydown",function(event){
						var keycode = event.keyCode;
						return function disabled(disabled){
							if(disabled instanceof Array){
								for(var j=0,jlen=disabled.length; j<jlen; j++){
									if(keycode==disabled[j]){
										return false;
									}
								}
							} else if(typeof disabled==="string"){
								disabled = disabled.replace(/ /g,"").split(",");
								for(var j=0,jlen=disabled.length; j<jlen; j++){
									if(/-/g.test(disabled[j])){
										var range = disabled[j].split("-"),dMin = range[0],dMax = range[1];
										if(keycode>=dMin && keycode<=dMax){
											return false;
										}
									} else {
										if(keycode==disabled[j]){
											return false;
										}
									}
								}
							}
							keycode = null,disabled = null;
						}(rule.disabled);
					})
				}
			};
			return this;
		}
	};
	
	//tabs 
	var tab = HHN.tab =function(evt,nav,content,currentClass){
		var nav = $(getDom(nav)).find("li"),content = $(content);
		currentClass = currentClass || "tab-current";
		nav.on(evt,function(){
			var index = $(this).index();
			nav.removeClass(currentClass).eq(index).addClass(currentClass);
			content.removeClass(currentClass).eq(index).addClass(currentClass);
		});
	};
	//progress
	var progress = HHN.progress = function(list,items,color,parameter){
		//get list
		var list = $(getDom(list));
		items = items || "div.progress-round";
		color = color || ["#ccc","#08b2e6"];
		parameter = parameter || "data";
		var progress = list.find(items);
		for(var i=0,ilen=progress.length;i<ilen; i++){
			var dom = progress.eq(i),data = Number(dom.attr(parameter)),deg1 = 0,deg2=0,linear = color[0];
			data = data<0 ? 0 : data>100 ? 100 : data;
			if(data>0 && data<0.5){
				data = 0.5;	
			}
			if(data>99.5 && data<100){
				data = 99.5;	
			}
			if(data>=50){
				deg1 = -90+(data-50)*3.6;
				deg2 = 270;
				linear= color[1];
			} else {
				deg1 = 90;
				deg2 = 90+data*3.6;
			};
			var cssObject = {
				"background-image":"linear-gradient("+deg1+"deg,"+linear+" 50%,transparent 50%,transparent),linear-gradient("+deg2+"deg,"+color[1]+" 50%,"+color[0]+" 50%,"+color[0]+")"
			};
			if($.browser){
				if($.browser.firefox){
					cssObject["background-image"] = "-moz-linear-gradient("+deg1+"deg,"+linear+" 50%,transparent 50%,transparent),-moz-linear-gradient("+deg2+"deg,"+color[1]+" 50%,"+color[0]+" 50%,"+color[0]+")";
				} else if($.browser.ie){
					cssObject["background-image"] = "-ms-linear-gradient("+deg1+"deg,"+linear+" 50%,transparent 50%,transparent),-ms-linear-gradient("+deg2+"deg,"+color[1]+" 50%,"+color[0]+" 50%,"+color[0]+")";
				} else {
					cssObject["background-image"] = "-webkit-linear-gradient("+deg1+"deg,"+linear+" 50%,transparent 50%,transparent),-webkit-linear-gradient("+deg2+"deg,"+color[1]+" 50%,"+color[0]+" 50%,"+color[0]+")";
				}
			}
			dom.css(cssObject);
		}
	};
	//popup and mask
	var popupTimer = null;
	var popup = HHN.popup = function(options){
		var defaults = {
			type:"loading",
			element:null,
			cla:"",
			mask: true,
			maskClick:false,
			scroll:false,
			loadingText:"提交数据...",
			titleText:"",
			content: "",
			fn:null,
			delay:3000,
			fixed:false,
			touch:false
		};
		//init
		HHN.popupClose();
		//
		var s = $.extend({},defaults,options);
		var w=$(doc).width(),h=$(win).height(),bh=Body.height()>h ? Body.height() : h;
		var scrollTop = Body.scrollTop();
		//init
		if(s.type=="alert"){
			s.mask = false;
		}
		//create mask;
		if(s.mask){
			var mask = $("#mask");
			if(mask[0]){
				mark.css({
					width:w,
					height:bh
				});
			} else {
				mask = doc.createElement("div");
				mask.id = "mask";
				mask.className = "mask";
				$(mask).css({
					width:w,
					height:bh
				});
				Body.prepend(mask);
				if(s.maskClick){
					$(mask).off().on('click',function(){
						HHN.popupClose();
						return false;
					});
				};
			}
			mask.addEventListener('touchmove', function(e) {
				e.stopPropagation();
				e.preventDefault();
			});
		};
		//popup box;
		var popup = $("#popup");
		if(popup[0]){
			popup.remove();	
		};
		popup = doc.createElement("div");
		popup.id="popup";
		popup.className= "popup";
		popup.style.display ="block";
		//loading
		if(s.type=="loading"){
			HHN.pageBack();
			var loadingtext = s.content == "" ? s.loadingText : s.content;
			var loading = "<i class='popup-loading loading-img'></i><span class='popup-loading-text'>"+loadingtext+"</span>";
			popup.innerHTML = loading;
			$(popup).addClass("popup-load").addClass(s.cla);
		} else if(s.type=="alert"){
			popup.innerHTML = "<div class='alert-content'>"+s.content+"</div>";
			$(popup).addClass("alert").addClass(s.cla);
			if(popupTimer){
				clearTimeout(popupTimer);	
			}
			popupTimer = setTimeout(function(){
				HHN.popupClose();
			},s.delay);
		} else if(s.type == "confirm"){
			popup.innerHTML = "<div class='confirm-content'>"+s.content+"</div><div class='confirm-btn'><a href='#' id='confirmCancle'>取消</a><a href='#' id='confirmOk'>确定</a></div>";
			$(popup).addClass("popup-confirm").addClass(s.cla);
		} else if(s.type=="content") {
			var contentHTML = "<div class='popup-main'>"+s.content+"</div>";
			if(s.titleText!==""){
				contentHTML = "<div class='popup-title'><span>"+s.titleText+"</span><a href='javascript:;' onclick='HHN.popupClose()' class='close'>关闭</a></div>"+contentHTML;
			}
			popup.innerHTML = contentHTML;
			$(popup).addClass("popup-content").addClass(s.cla);
		};
		if(!s.touch){
			popup.addEventListener('touchmove', function(e) {
				e.stopPropagation();
				e.preventDefault();
			});
		};
		//append;
		if(s.mask){
			$(mask).after(popup);
		} else {
			Body.prepend(popup);
		};
		//animate position
		var ph = $(popup).height();
		var pw = $(popup).width();
		if(ph>h){
			$(popup).animate({
				position:"fixed",
				marginTop:0,
				height: "90%",
				top:"5%"
			},500);
		} else {
			var offsetData = {
				top:scrollTop,
				marginTop: 30,
				marginLeft:-pw/2
			};
			if(s.element){
				//input offset
				var iOffset = $(s.element).offset();
				var iLeft = iOffset.left,iTop=iOffset.top,iHeight=$(s.element).height()+(ph>38?ph/2+5:12);
				var offsetTop = iTop-iHeight;
				offsetData["top"] = offsetTop <0 ? scrollTop+15 : offsetTop;
				offsetData["marginTop"] = 0;
			} else {
				offsetData["marginTop"] = (h-ph)/2;
			}
			if(s.type=="loading" || s.fixed){
				offsetData["position"] = "fixed";
				offsetData["top"] = "50%";
				offsetData["marginTop"] = -ph/2;
			}
			$(popup).css(offsetData);//.addClass("animate-scale");
			//reset scroll
			var popupTop = $(popup).offset().top;
			if(scrollTop-popupTop>0){
				Body.scrollTop(popupTop-8);
			}
		}
		if(s.type=="confirm"){
			$("#confirmCancle").off().on("click",function(){
				HHN.popupClose();
				return false;
			});
			$("#confirmOk").off().on("click",function(){
				if(s.element){
					$(this).attr("href",$(s.element).attr("href"));	
				}
				HHN.popupClose();
				return true;
			});
		}
		//回调
		if(s.fn && HHN.type(s.fn)=="function"){
			s.fn();	
		}
		return $(popup);
	};
	var popupClose = HHN.popupClose =function(){
		if(popupTimer){
			clearTimeout(popupTimer);	
		}
		$("#popup,#mask").remove();
	};
	//get value from url
	var queryString = HHN.queryString = function QueryString(item){   
	  var qs= location.search.match(new RegExp("[\?\&]" + item + "=([^\&]*)(\&?)"));   
	  return qs ? decodeURIComponent(qs[1]) : decodeURIComponent(qs);
	};
	
	//scroll bottom then load data with ajax
	var isScrolling = false;
	var loadingScrollHtml = '<div class="loading-more" id="loadingScrollMore"><span class="loading-img">加载中...</span></div>';
	var loadingScrollMore = null;
	var loadingClickHtml = '<div class="loading-more" id="loadingClickMore"><a href="javascript:;">点击加载更多</a></div>';
	var loadingClickMore = null;
	var loadingClick = false;
	var isScroll = HHN.isScroll = function(options){
		//有ajax在处理时，scroll不再请求data
		if(isScrolling){ return false; }
		var defaults = {
			url:"",
			post:"",
			list: $("#list"),
			items:"li",
			height:100,
			scrollMax:30,
			click:false,
			page:0,
			success:null
		};
		var s = $.extend({},defaults,options);
		if(loadingClick){
			if(s.click){
				sendAjax();
			}
		} else {
			//滚动条到网页头部的 高度，兼容ie,ff,chrome
			var top = doc.documentElement.scrollTop + doc.body.scrollTop; //??????
			//网页的高度
			var textheight = $(doc).height();
			// 网页高度-top-当前窗口高度
			if (textheight - top - $(win).height() <= s.height) {
				sendAjax();
			}
		}
		function sendAjax(){
			//check
			if(s.url===""){
				HHN.popup({type:"alert",content:"请设置url参数"});
				return false;	
			}
			if(!s.list[0]){
				HHN.popup({type:"alert",content:"请传入列表ID"});
				return false;	
			}
            //可以根据实际情况，获取ajax动态数据加载到 divContent中
            var xhr = $.ajax({
                type: "get",
                url: s.url+"?_="+new Date().getTime(),
				data:s.post,
				beforeSend: function(){
					//freeze scroll
					isScrolling = true;
					//remove click
					if(loadingClickMore){
						loadingClickMore.remove();
						loadingClickMore = null;
					}
					//remove loading
					if(loadingScrollMore){
						loadingScrollMore.remove();
						loadingScrollMore = null;	
					}
					//loading;
					s.list.after(loadingScrollHtml);
					loadingScrollMore = $("#loadingScrollMore");
				},
                success: function (data) {
					if(loadingScrollMore){
						loadingScrollMore.remove();
						loadingScrollMore = null;
					}
					s.list.append(data);
					//判断最大滚动加载条数
					var isMax =s.list.find(s.items).length>=s.scrollMax;
					//if max
					if(isMax){
						s.list.after(loadingClickHtml);
						loadingClickMore = $("#loadingClickMore");
						if($.trim(data)==""){
							loadingClickMore.html("<em>亲，没有更多内容了</em>");
							return false;
						}
						loadingClickMore.off("click").on("click",function(){
							s["click"] = true;
							s["post"] = "curPage="+(++s.page);
							HHN.isScroll(s);
						});
						loadingClick = true;
					}
					//release scroll;
					isScrolling = false;
					if(s.success && HHN.type(s.success)=="function"){
						s.success();	
					}
                },
                error: function () {
                    HHN.popup({type:"alert",content:"服务器繁忙，请稍后重试"});
					isScrolling = false;
                }
            })
		}
	};
	
	/**
		 * 动态数字插件
		 */
	var AnimateNum = HHN.AnimateNum = function(settings) {
		this.obj = settings.obj;
		this.target = settings.target.toString();
		this.totalTime = settings.totalTime || 1000;
		this.init();
	};
	AnimateNum.prototype = {
		init: function () {
			if (!this.target)return false;
			this.animation();
		},
		animation: function () {
			var self = this;
			var fixIndex = this.target.indexOf("."); //小数点索引
			var fixLength = 0;  //小数点后有几位
			if (fixIndex >= 0) {
				fixLength = this.target.length - fixIndex - 1;
			}
			var target = this.target.replace("\.", "");
			var totalStep = (this.totalTime / 30) | 0;
			var stepLength = target / totalStep | 0;
			var currentStep = 0;
			var currentNum = 0;
			self.timer = setInterval(function () {
				currentStep++;
				currentNum += stepLength;
				self.obj.html(currentNum / Math.pow(10, fixLength));
				if (currentStep >= totalStep) {
					clearInterval(self.timer);
					self.obj.html(self.target);
				}
			}, 30)
		}
	};
	/**
	* 使用 HTML5 的 History 新 API pushState 来曲线监听 Android 设备的返回按钮
	* @author azrael
	* @date 2013/02/04
	* @version 1.0
	* @example
	* XBack.listen(function(){
	alert('oh! you press the back button');
	});
	*/
	HHN.supportPopState = !!(window.history && history.pushState);
	if(HHN.supportPopState){
		!function(pkg, undefined) {
			var STATE = 'hhn-back';
			var element;
			var onPopState = function(event) {
				event.state === STATE && fire();
			}
			var record = function(state) {
				history.replaceState(state, null, location.href);
			}
			var fire = function() {
				var event = document.createEvent('Events');
				event.initEvent(STATE, false, false);
				element.dispatchEvent(event);
			}
			var listen = function(listener) {
				element.addEventListener(STATE, listener, false);
			} 
			!function() {
				element = document.createElement('span');
				window.addEventListener('popstate', onPopState);
				this.listen = listen;
				record(STATE);
			}.call(HHN[pkg] = HHN[pkg] || {});
		}("Back");
	}
})(this,Zepto || jQuery);