if(!noIScroller){
	var noIScroller;		
};
Zepto(function(){
	//loading
	HHN.loadPage();
	var tabs = $(".tabs");
	function setSomeHeight(){
		var indexSlider = $("#indexSlider");
		if(indexSlider[0]){
			var image = new Image(),imageURL = $(".slider-content").find("img").eq(0).attr("src");
			image.src = imageURL;
			var imageWidth = $(window).width();
			indexSlider.css({"height":image.height/(image.width/imageWidth)});
		}
	}
	var myScroll;
	HHN.iScroller = function(){
		if($.os.iphone){
			if(myScroll){ myScroll.destroy(); }
			myScroll = new IScroll('#wrap', {
				scrollbars: true,
				mouseWheel: true,
				interactiveScrollbars: true,
				shrinkScrollbars: 'scale',
				fadeScrollbars: true
			});
		}
	}
	//fixed
	function setScroller(){
		if($.os.iphone){
				if(HHN.type(noIScroller)=="undefined"){
					setSomeHeight();
					var nav = $("#nav"),wrap = $("#wrap");
					var temp = wrap.html();
					wrap.html("<div class='iphone-scroller' id='scroller'>"+temp+"</div>");
					nav.addClass("iphone-footer");
					wrap.addClass("iphone-wrapper");
					
					if($("h1.title").length>0 && appRefer!="cf"){
						wrap.css({"top":30});		
					}
				}
				if(tabs.length==0){
					if(HHN.type(noIScroller)=="undefined"){
						HHN.iScroller();
					}
				}
			//document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
		}
	}
	setScroller();
	//弹出loading
	HHN.popLoading = function(url,obj){
		if(!url){ return; }
		if(HHN.type(obj)==="string"){
			var text = obj;
		} else {
			var text = (obj && obj.attr("load-tip")) ? obj.attr("load-tip") : "加载中...";
		}
		if(HHN.supportPopState){
			HHN.popup({type:"loading",content:text});
			setTimeout(function(){
				location.href = url;
			},500);
			return;
		} else {
			HHN.pageBack();	
		}
		location.href = url;
	}
	//监听用户按下返回键
	if(HHN.supportPopState){
		HHN.Back.listen(function(){
			HHN.popupClose();
		});
	};
	//监控横屏
	HHN.orientationChange(function(){
		location.reload();	
	});
	//loading
	$("a").on('click',function(){
		var href = $.trim($(this).attr("href"));
		if(href!="" && href!="#" && !/javascript:/gi.test(href) && !/tel:/gi.test(href)){
			HHN.popLoading(href,$(this));
			return false;
		}
		return true;
	});
	
	//slider
	var slider = $("#indexSlider");
	if(slider.length>0){
		var slider = $('.slider-content'); 
		var sliderItem = slider.find(".slider-item");
		var sliderNum = sliderItem.length;
		sliderItem.css({width:(100/sliderNum)+"%"});
		slider.css({width:sliderNum*100+"%"}).show();
		var flipsnap = Flipsnap('.slider-content',{
			auto:true,
			pointBar:true
		});
	}
	//recharge
	var recharge = $("#recharge"),idNo = $("#idNo"),usrCustId=$("#usrCustId");
	if(recharge[0] && idNo[0] && usrCustId[0]){
		recharge.on("click",function(){
			if(usrCustId==""||(usrCustId-0)<=0){
				HHN.popLoading("/registerChinaPnr.do");
				return false;
			}
		});
	}
	HHN.initInvestList = function(){
		//list
		var list = $("#list");
		if(list[0]){
			list.on("click",".list-item",function(event){
				var target = $(event.target);
				if(target.attr("type")=="tel"){
					target.focus();
				} else {
					HHN.popLoading($(this).attr("href"));
				}
			});
			//validate form
			var form = new HHN.Form("form");
			var myAccount  = $("#myAccount");
			var userid = $("#userid").val();
			var redirect = $("#redirect");
			if(redirect.get(0) == null) {
				redirect = window.parent.$("#redirect");
			}
			$(".doTender").on("click",function(event){
				if($(this).hasClass("btn-dark") || $(this).hasClass("btn-red")){
					$(this).closest(".list-item").click();
					return false;	
				}
				var $this = $(this);
				var input = $("#"+$(this).attr("for"));
				var value = input.val();
				var isInvestList = this.id!='BtnInvest';
				var postData = {};
				if(isInvestList){
					postData = HHN.parseJSON($(this).attr("data"));
					if(HHN.type(postData)=="object"){
						postData["paramMap.amount"] = value;
					}
				}
				var financeid = $(this).attr("financeid");
				var investamount = $(this).attr("investamount").replace(/,/gi,"");
				//如果不是单击按钮，则跳转到详情页
				if($.trim(value)=="" && isInvestList){
//					$(this).closest(".list-item").click();
                    HHN.popup({type:"alert",content:"请输入正确的投资额，<br/>最小投资额是100元",element:input});
					return false;	
				}

				if(myAccount.val()==""){
					HHN.popup({type:"alert",content:"请先登录",element:input});
					setTimeout(function(){
						HHN.popLoading("/webapp/webapp-login.do","跳转到登录页面,请稍候...");
					},1000);
					return false;	
				}
                if(financeid == userid){
                    HHN.popup({type:"alert",content:"无效操作,不能投自己发布的标",element:input});
                    return false;
                }
                if(Number(value)>Number(myAccount.val())){
                    HHN.popup({type:"alert",content:"帐户余额不足",element:input});
                    input.focus();
                    return false;
                }
                if(Number(value)>Number(investamount)){
                    HHN.popup({type:"alert",content:"投资金额超过剩余可投金额",element:input});
                    input.focus();
                    return false;
                }
                if(Number(value)<100){
                    HHN.popup({type:"alert",content:"请输入正确的投资额，<br/>最小投资额是100元",element:input});
                    input.focus();
                    return false;
                }
                if(Number(value)%100!=0){
                    HHN.popup({type:"alert",content:"请输入正确数字，如：100,200...<br/>投资额必须是100的整数倍",element:input});
                    input.focus();
                    return false;
                }
				form.validate({
					error:function(text){
						HHN.popup({type:"alert",content:text,element:input});
						input.focus();
					},
					success:function($form){
						//判断是否列表页或首页直接投资
                        var redirect = $("#redirect");

						if(!postData["paramMap.amount"]){
							postData = $form.serialize();
                            if(redirect.length<=0) {
                                window.parent.location.href= "/investBorrow.do?"+postData;
                            }else{
                                window.location.href= "/investBorrow.do?"+postData;
                            }
						}else{
                            if(redirect.length<=0) {
                                window.parent.location.href="/investBorrow.do?paramMap.amount="+postData["paramMap.amount"]+"&paramMap.id="+postData["paramMap.id"];
                            }else{
                                window.location.href="/investBorrow.do?paramMap.amount="+postData["paramMap.amount"]+"&paramMap.id="+postData["paramMap.id"];
                            }
                        }
//                        var xdata = $this.attr("data");
//                        alert(xdata)
//                        alert(value+","+financeid)
//                        window.location.href= "/investBorrow.do?"+postData;
//alert(postData["paramMap.amount"])
//alert(postData["paramMap.id"])
//                        $form.submit();
//                        window.location.href="/investBorrow.do?_="+new Date().getTime();
						/*$.ajax({
							type: 'POST',
							url: '/investBorrow.do?_='+new Date().getTime(),
							data:postData,
							beforeSend: function(){
								setTimeout(function(){
									HHN.popup({type:"loading",content:"投标中..."});	
								},100);	
							},
							success: function(data){
//								HHN.setPageBack(1);
								redirect.html(data);
							},
							error: function(xhr, type){
								HHN.popup({type:"alert",content:"服务器繁忙，请稍后重试!"});
							}
						})*/
					}
				},input);
				return false;
			});
			//各项止进度
			switch(appRefer){
				case "cf":
					HHN.progress("list",null,["#ccc","#894d8d"]);
					break;
				case "wygj":
					HHN.progress("list",null,["#ccc","#0092c3"]);
					break;
				default:
					HHN.progress("list");
			}
		}
	}
	HHN.initInvestList();
});