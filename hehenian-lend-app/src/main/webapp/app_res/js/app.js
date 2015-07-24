$(function() {
	$("#sub").click(function() {
		var arr = $(".must");
		for(var i=0,len=arr.length; i< len; i++){
			var item = arr.eq(i);
			if (!item.val()) {
				setErrTip(item, '不能为空');
				return false;
			} else if(item.hasClass("phone")) {
				var reg= /^1\d{10}$/;
				if(!reg.test(item.val())) {
					setErrTip(item, '不正确');
					return false;
				}
			}
		}
		$("form").submit();
	});
	
	$(".must").live('blur',function() {
		var that = $(this);
		if(that.val().length<1) {
			setErrTip(that, '不能为空');
		}
	}).live('focus',function() {
		$(this).siblings(".error").remove();
		$(this).parent().siblings(".error").remove();
	});
	
	function setErrTip(obj, tipObj) {
		if(obj.prev().length){
			obj.after('<p class="pa w10 error">'+ obj.prev().text() +''+ tipObj +'</p>');
			$(".error").css('padding-left', obj.prev().width()+10);
		} else {
			obj.parent().after('<p class="pa w10 error">'+ obj.parent().prev().text() +''+ tipObj +'</p>');
			$(".error").css('padding-left', obj.parent().prev().width()+10);
		}
		return false;
	};
	
	
	$(".showimg").bind('touchstart', function(){
	    var imgurl = $(this).prev().attr("src");
	    var str = 
	    '<section class="dialog" style="overflow: scroll;">'+
	    '    <img src="'+ imgurl +'" alt="">'+
	    '    <span class="close-dialog"></span>'+
	    '</section>';
	    $("body").append(str);
	   /*  var img = $(".dialog img");
	    img.css('margin-top', img.height()*-1/2); */
	});
	$("body").on('touchstart', '.close-dialog', function(){
/*		$("body").on('touchstart', '.close-dialog', function(){*/
	    $(this).parent().remove();
	});

	
});


var popWindow = function(msg) {
    var pwCtn = '<div class="pws" id="pws"><div class="pwsTop">' + msg + '</div></div>'
    $("body").append(pwCtn);
    setTimeout("$('#pws').remove()", 1000);
};

function setDateSelect(_year, _month, _day, callback) {
	var date = new Date();
	var year_now = date.getFullYear();
	var strYear='', strMonth='', strDay='';
	var objYear=$(".year");
	var objMonth=$(".month");
	var days=31;
	
	_year = _year || year_now;
	_month = _month || date.getMonth() + 1;
	_day = _day || date.getDate();
	
	for(var year=year_now; year >= 1949;year--){
		if(_year == year){
			strYear +='<option selected value="'+ year +'">'+ year +'</option>'
			continue;
		}
		strYear +='<option value="'+ year +'">'+ year +'</option>'
	}

//	objYear.change(function() {
//		   var that = $(this);
//		   var year = that.val();
//		   var month = that.parent().next().find(".month").val();
//		   days = new Date(year, month, 0).getDate();
//		   setDay();
//		   that.parent().next().next().find(".day").html(str);
//	   });
	
	for(var month=1;month<=12;month++) {
		if(_month == month){
			strMonth +='<option selected value="'+ month +'">'+ month +'</option>'
			continue;
		}
		strMonth +='<option value="'+ month +'">'+ month +'</option>'
	}
	
	 objMonth.change(function() {
		   var that = $(this);
		   var year = that.parent().prev().find(".year").val();
		   var month = that.val();
		   days = new Date(year, month, 0).getDate();
		   setDay();
		   that.parent().next().find(".day").html(str);
	   });
	
	function setDay() {
	   for(var day=1;day<=days;day++) {
		   if(_day == day){
				strDay +='<option selected value="'+ day +'">'+ day +'</option>'
				continue;
			}
		   strDay +='<option value="'+ day +'">'+ day +'</option>'
		}
   }
	setDay();
	
	callback(strYear, strMonth,strDay);
}






/***************************************/
window.onload = function(){
	(function() {
		var index  =sessionStorage.getItem("index");
		$(".bottom_nav li").eq(index).addClass("check");
	})();
}
function scrollLoad (callback) {
	window.onscroll = function () {
	    var domHeight = document.body.scrollHeight || document.body.offsetHeight,
	        winHeight = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight,
	        top= window.document.documentElement.scrollTop + window.document.body.scrollTop;
	    if ( domHeight - winHeight > top ) {
	        return false;
	    }
	    callback();
	}
}

