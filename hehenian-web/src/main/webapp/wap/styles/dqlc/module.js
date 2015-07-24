//常量声明
//当前环境，local=本地，beta=测试，idc=生产
var ENVIRONMENT = "local";
//图片服务地址
var PHOTO_HOST = "";
if(ENVIRONMENT == "idc"){
	PHOTO_HOST = "http://photo.huigouquan.com";
}

function isLogin(){
	var result = false;
	ajax({
		url:"/data/login_state.php",
		dataType:"json",
		async:false,
		success:function(data){
			if(data.MsgCode == 1)result = true;		
		}
	});
	return result;
}
function exitLogin(){
	ajax({
		url:"/deal/exit_login.php",
		dataType:"json",
		success:function(data){
			locationHref("index.html");	
		}
	});
}
function loadHtml(domId,url,async){
	if(typeof(async) == 'undefined')async = true;
	ajax({
		url:url,
		type:"get",
		dateType:"text",
		async:async,
		success:function(html){
			$("#"+domId).html(html);
		}
	});
};
function loadHeader(){
	loadHtml('header','/include/header.html');	
}
function loadFooter(){
	loadHtml('footer','/include/footer.html');		
}
function loadHeaderAndFooter(){
	loadHeader();	
	loadFooter();
} 
//判断是否是IE
function isIE(){
	var arVersion = navigator.appVersion.split("MSIE");
    var version = parseFloat(arVersion[1]);
    if ((version > 5.5))return version;
	else return false;
}
//得到元素实际坐标
function getElementPosition(OB){
	var posX=OB.offsetLeft;
	var posY=OB.offsetTop;
	var aBox=OB;//需要获得位置的对象
	while(aBox.tagName.toLowerCase()!="body"){
		aBox=aBox.offsetParent;
		if(!aBox)break;
		posX+=aBox.offsetLeft;
		posY+=aBox.offsetTop;
	}
	return {left:posX,top:posY};
}
function goLocation(iTop,iSpeed){
	var iscrollTop=0;
	var BoxTop=document.documentElement;
	if(window.pageYOffset)iscrollTop=window.pageYOffset;
	else iscrollTop=BoxTop.scrollTop;
	//if(window.chrome)BoxTop=document.body;
	if(iTop>(BoxTop.scrollHeight-BoxTop.clientHeight))iTop=BoxTop.scrollHeight-BoxTop.clientHeight;
	if(iTop<0)iTop=0;
	if(iscrollTop<iTop)goLocationUp(iTop,iSpeed);
	else if(iscrollTop>iTop)goLocationDown(iTop,iSpeed);
}
function goLocationUp(iTop,iSpeed){
	var iscrollTop=0;
	var BoxTop=document.documentElement;
	if(window.chrome)BoxTop=document.body;
	if(window.pageYOffset)iscrollTop=window.pageYOffset;
	else iscrollTop=BoxTop.scrollTop;
	if(iscrollTop<iTop){
		var iSpeedTop = 80;
		if(iSpeed)iSpeedTop = iSpeed;
		iscrollTop+=iSpeedTop;
		window.scrollTo(0,iscrollTop);
		window.setTimeout(function(){goLocationUp(iTop,iSpeed)},1);
	}else window.scrollTo(0,iTop);
}
function goLocationDown(iTop,iSpeed){
	var iscrollTop=0;
	var BoxTop=document.documentElement;
	if(window.chrome)BoxTop=document.body;
	if(window.pageYOffset)iscrollTop=window.pageYOffset;
	else iscrollTop=BoxTop.scrollTop;
	if(iscrollTop>iTop){
		var iSpeedTop = 80;
		if(iSpeed)iSpeedTop = iSpeed;
		iscrollTop-=iSpeedTop;
		window.scrollTo(0,iscrollTop);
		window.setTimeout(function(){goLocationDown(iTop,iSpeed)},1);
	}else window.scrollTo(0,iTop);	
}
//获取url参数
function getUrlParam(sName){
	var sValue = '';
	sName = trim(sName);
 	if(sName != ""){
		var re = new RegExp("\\b"+sName+"\\b=([^&=]+)");
		var st = window.location.search.match(re);
		if(st && st.length == 2)sValue = trim(st[1]);
		else {
			var st = window.location.hash.match(re);
			if(st && st.length == 2)sValue = trim(st[1]);
		}
	}
	return sValue;
}
function getDomain(){
	var stUrlReg=/[^\.]*?\.com\.cn|[^\.]*?\.net\.cn|[^\.]*?\.org\.cn|[^\.]*?\.edu\.cn|[^\.]*?\.com|[^\.]*?\.cn|[^\.]*?\.net|[^\.]*?\.org|[^\.]*?\.edu|[^\.]*?\.tk/i;
	var sBaseAreaName=window.location.host.toLowerCase().match(stUrlReg)[0];
	return sBaseAreaName;
}
function locationHref(url,target){
	//可以支持多种跳转方式，而且document.referrer做记录
	var fun=function(){
		target=target?target:'_self';
		var a=document.createElement("a");
		a.style.display='none';
		a.href=url;
		a.target=target;
		a.innerHTML='&nbsp;';
		document.body.appendChild(a);
		a.click();
		if(target!='_self')document.body.removeChild(a);
	};
	//解决IE6超链接javascript:void(0)不跳转
	if(isIE()&&isIE()<7.0)setTimeout(fun,1);
	else fun();
}
/**************************************************** 设置cookie **********************************************/
function getCookie(name){
	var start=document.cookie.indexOf(name+"=");
	if(start==-1) return null;
	var len=start + name.length + 1;
	var end = document.cookie.indexOf(';',len);
	if (end==-1) end=document.cookie.length;
	return decodeURIComponent(document.cookie.substring(len, end));
}
function setCookie(name,value,expires){
	if(expires){
		expires=expires*1000*60*60*24;
		var today = new Date();
		expires = new Date(today.getTime()+(expires));
		expires = 'expires='+expires.toGMTString();
	}else expires = '';
	value = encodeURIComponent(value);
	//sBaseAreaName来自common.js
	document.cookie = name+'='+value+';path=/;domain='+getDomain()+';'+expires;
}
function deleteCookie(name){
	//sBaseAreaName来自common.js
	document.cookie = name+'=;path=/;domain='+getDomain()+';expires=Thu, 01-Jan-1970 00:00:01 GMT'; 
}
//得到当前时间,stDate是日期对象，iDay当前日期对象偏移的天数，long是否显示小时，分钟,秒
function getTime(stDate,iDay,long){
	var alldate="";
	var dt=null;
	if(stDate)dt=stDate;
	else dt=new Date();
	dt.setDate(dt.getDate()+iDay);
	var sYear=dt.getFullYear();
	var sMonth=dt.getMonth();
	sMonth++;
	if(sMonth<=9)sMonth="0"+sMonth;
	var sDay=dt.getDate();
	if(sDay<=9)sDay="0"+sDay;
	if(!long)alldate=sYear+"-"+sMonth+"-"+sDay;
	else{
		var sHours=dt.getHours();
		if(sHours<=9)sHours="0"+sHours;
		var sMinutes=dt.getMinutes();
		if(sMinutes<=9)sMinutes="0"+sMinutes;
		var sSeconds=dt.getSeconds();
		if(sSeconds<=9)sSeconds="0"+sSeconds;
		alldate=sYear+"-"+sMonth+"-"+sDay+' '+sHours+':'+sMinutes+':'+sSeconds;
	}
	return alldate;
}
//保证图片按比例显示
function imgScale(ob,width,height){
	var iWidth=$(ob).width();
	var iHeight=$(ob).height();
	var sStyle='';
	if((iWidth/width)>(iHeight/height))sStyle='width:'+width+'px;height:auto;';
	else sStyle='width:auto;height:'+height+'px;';
	$(ob).attr('style',sStyle).show()
}
//select按键快速定位
function selectCNSort(html){
	//判断是否是数组,你可以添加option字符串数组，也可以添加option字符串集合
	if(html.join)html=html.join('');
	var option=html.match(/<option[^<>]*?>.*?<\/option>/gi);
	if(!option)return html;
	//拼音排列的最小中文
	var stTemp='吖[a]|八[b]|嚓[c]|咑[d]|妸[e]|发[f]|噶[g]|哈[h]|丌[j]|咔[k]|垃[l]|嘸[m]|拏[n]|噢[o]|妑[p]|七[q]|亽[r]|仨[s]|他[t]|屲[w]|夕[x]|丫[y]|帀[z]'.split('|');
	for(var i=0,length=stTemp.length;i<length;i++){
		option.push('<option>'+stTemp[i]+'</option>');		
	}
	//首次按拼音排列
	option.sort(function(x,y){
		var str1=x.replace(/<.*?>/g,'');
		var str2=y.replace(/<.*?>/g,'');
		return str1.localeCompare(str2);
	});
	//给首字符为中文的option加上拼音的第一个字符(便于按键快速定位)
	var stNew=[];
	var tag='';
	for(var i=0,length=option.length;i<length;i++){
		option[i]=option[i].replace(/(^\s*)|(\s*$)/g,'');
		var stTag=option[i].match(/\[([a-z])]/);
		if(stTag){
			tag=stTag[1];
			continue;
		}
		var newStr=option[i].replace(/(<option[^<>]*?>)(.*?)(<\/option>)/gi,function(a,b,c,d){
			return 	b+tag+c+d;
		});	
		stNew.push(newStr);		
	}
	//再次按拼音排列
	stNew.sort(function(x,y){
		var str1=x.replace(/<.*?>/g,'');
		var str2=y.replace(/<.*?>/g,'');
		return str1.localeCompare(str2);
	});
	html=stNew.join('');
	return html;
}
function htmlEncode(html){
	var temp = document.createElement("div");
	(temp.textContent != null) ? (temp.textContent = html) : (temp.innerText = html);
	var output = temp.innerHTML;
	temp = null;
	return output;
}
function htmlDecode(text){
	var temp = document.createElement("div");
	temp.innerHTML = text;
	var output = temp.innerText || temp.textContent;
	temp = null;
	return output;
}
/************************************************************* 修正浏览器JSON对象 *******************************************************/
//JSON其实就是高级浏览器的一个window.JSON,低于IE8是没有JSON对象的,大家注意啦不要乱使用
if(typeof(JSON)=='undefined')JSON={};
if(!JSON.parse)JSON.parse=function(str){
	var json = (new Function("return " + str))();
	return json;	
};
if(!JSON.stringify)JSON.stringify=function(obj){
	switch(typeof(obj)){
		case 'string':
			return '"' + obj.replace(/(["\\])/g, '\\$1') + '"';
		case 'array':
			return '[' + obj.map(arguments.callee).join(',') + ']';
		case 'object':
			 if(obj instanceof Array){
				var strArr = [];
				var len = obj.length;
				for(var i=0; i<len; i++){
					strArr.push(arguments.callee(obj[i]));
				}
				return '[' + strArr.join(',') + ']';
			}else if(obj==null){
				return 'null';

			}else{
				var string = [];
				for (var property in obj) string.push(arguments.callee(property) + ':' + arguments.callee(obj[property]));
				return '{' + string.join(',') + '}';
			}
		case 'number':
			return obj;
		case false:
			return obj;
	}
};
/************************************************************** 自定义弹框 *************************************************************/
/*
showBox('内标签','footerId')
showBox('内标签',dom对象)
showBox('自定义文本','你好我是自定义文本')
showBox('显示Iframe','/index.html?width=500&height=300')
showBox('显示AjaxHTML','/include/header.html?ajax')
showBox('alert框','用户不存在','alert',function(){alert('执行自定义函数')})
showBox('confirm框','用户不存在','confirm',function(){alert('执行自定义函数')})
showBox('tip提示','提示内容','tip',{'tagId':'xxx','left':0,'top':0,'time':500})//time是毫秒
*/
function showBox(title,param,winType,callBack){
	if(typeof(winType)=='undefined')winType='';
	winType=trim(winType.toLowerCase());
	title=trim(title);
	param=trim(param);
	var id=(new Date()).getTime();
	var html=[];
	html.push('<table cellpadding="0" cellspacing="0" ');
	var boxStyle='z-index:'+id+';';
	if(winType!='tip')html.push(' width="100%" height="100%" ');
	else{
		boxStyle+='display:none;border:1px solid #CCCCCC;';
		if(callBack&&callBack.tagId){
			var dom=document.getElementById(callBack.tagId);
			if(dom){
				var ps=getElementPosition(dom);
				var letf=ps.left;
				var top=ps.top;
				if(callBack.left)letf+=parseInt(callBack.left);
				if(callBack.top)top+=parseInt(callBack.top);
				boxStyle+='position:absolute;left:'+letf+'px;top:'+top+'px;';	
			}
		}
	}
	html.push(' class="showBox" id="showBox');
	html.push(id);
	html.push('" style="');
	html.push(boxStyle);
	html.push('"><tr><td align="center" valign="middle"  style="width:100%;height:100%">');
	/************************* 添加css Begin *************************/
	html.push('<!--[if IE 6]><style type="text/css">');
	html.push('html{height:100%;overflow:hidden;}');
	html.push('body{height:100%;overflow:auto;margin:0;}');
	html.push('.showBox,.box_overlay{position:absolute;}');
	html.push('</style><![endif]-->');
	html.push('<style type="text/css">');
	html.push('.showBox{position:fixed;top:0px;left:0px}.box_overlay{position:fixed;top:0;left:0;width:100%;height:100%;background:#000;filter:alpha(opacity=50);-moz-opacity:0.50;opacity:0.50;}');
	html.push('.box_main{text-align:left;background-color:#fff;position:relative;top:0;left:0;border-collapse:collapse;}');
	html.push('.box_title{font-size:14px;font-weight:700;overflow:hidden;background-color:#E8F2FF;}');
	html.push('.box_title .text{padding-left:10px;height:30px;line-height:30px;color:#999999}');
	html.push('.box_title .close_box{ text-align:right;padding-right:10px;}');
	html.push('.box_title .close{display:inline-block;height:30px;line-height:30px;overflow:hidden;cursor:pointer;color:#999999;}');
	html.push('.box_txt{padding:10px;}');
	html.push('.box_btn{padding:0 0 6px;text-align:center;}');
	html.push('.box_btn button{padding:0 15px;height:24px;border:0;cursor:pointer;font-weight:700;color:#fff;margin:0 3px}');
	html.push('.box_btn button.yes{background-color:#63ADEE}');
	html.push('.box_btn button.no{background-color:#949494}');
	html.push('.box_ie6_html{height:100%;_overflow:hidden}');
	html.push('.box_ie6_body{height:100%;_overflow:auto;margin:0}');
	html.push('</style>');
	/************************* 添加css End *************************/
	if(winType!='tip')html.push('<div class="box_overlay"></div>');
	html.push('<table id="box_main');
	html.push(id);
	html.push('" class="box_main">');
	if(winType!='tip'){
		html.push('<tr class="box_title"><td class="text">');
		html.push(title);
		html.push('</td><td class="close_box">');
		var isAddCloseButton=true;
		if(winType=='alert'||winType=='confirm')isAddCloseButton=false;
		if(isAddCloseButton){
			html.push('<span class="close" onclick="closeBox(');
			html.push(id);
			html.push(');">关闭</span>');
		}
		html.push('</td></tr>');		
	}
	html.push('<tr><td id="box_content');
	html.push(id);
	html.push('" colspan="2" class="box_content">');
	var isAjaxHTML=false;
	var isInnerHTML=false;
	var isIframeHTML=false;
	var innerHTML='';
	var element=null;
	if(param&&param.nodeType==1)element=param;
	else element=document.getElementById(param);
	if(element){
		isInnerHTML=true;
		innerHTML=element.innerHTML;
		element.innerHTML='';
		html.push(innerHTML);	
	}
	else if(/^[^<>]*?\.(html|htm|jsp)[^<>]*$/i.test(param)){
		//xxxxx.html?width=xxx&height=xxx
		if(/\?[^&=\?]*?ajax/i.test(param)){
			isAjaxHTML=true;	
		}else{
			isIframeHTML=true;
			var width='';
			var height='';
			var stWidth=param.match(/width=([0-9%]+)/i);
			if(stWidth&&stWidth.length)width=stWidth[1];
			var stHeight=param.match(/height=([0-9%]+)/i);
			if(stHeight&&stHeight.length)height=stHeight[1];
			if(param.indexOf('?')==-1)param+='?boxId='+id;
			else param+='&boxId='+id;
			html.push('<iframe id="contentIframe'+id+'" name="contentIframe" onload="this.contentWindow.document.body.style.background=\'#fff\'" frameborder="0" marginheight="0" marginwidth="0" ');
			if(width!='') {html.push('width="'+width+'"');}
			if(height!='') {html.push(' height="'+height+'"');}
			html.push(' src="'+param+'"></iframe>');
		}
	}
	else{
		html.push('<div class="box_txt">'+param+'</div>');	
	}
	html.push('</td></tr><tr style="display:none"><td colspan="2" class="box_btn" id="box_btn');
	html.push(id);
	html.push('"></td></tr></table></td></tr></table>');
	var div=document.createElement('div');
	div.innerHTML=html.join('');
	document.body.appendChild(div.firstChild);
	if(isIE()&&isIE()<7.0&&isIframeHTML){
		//IE6 iframe空白 bug
		document.getElementById("contentIframe"+id).contentWindow.location.reload();
	}
	//插入内标签缓存内容
	if(isInnerHTML){
		var input=document.createElement('input');
		input.innerDom=element;
		input.id="box_InnerTag"+id;
		input.type='hidden';
		input.value=innerHTML;
		document.getElementById("box_content"+id).appendChild(input);
	}
	if(isAjaxHTML&&loadHtml)loadHtml('box_content'+id,param);
	if(winType=='alert'||winType=='confirm'){
		var oBtn1=document.createElement('button');
		oBtn1.className="yes"; 
		oBtn1.innerHTML='确定';
		oBtn1.onclick=function(){
			closeBox(id);
			if(typeof(callBack)!="undefined"){callBack();};
		};
		var buttonBox=document.getElementById("box_btn"+id);
		buttonBox.parentNode.style.display="";
		buttonBox.appendChild(oBtn1);
		oBtn1.focus();
		if(winType=='confirm'){
			var oBtn2=document.createElement('button');
			oBtn2.className="no";
			oBtn2.innerHTML='取消';
			oBtn2.onclick=function(){
				closeBox(id);
			};
			buttonBox.appendChild(oBtn2);
		}
	}	
	if(winType!='tip'){	
		var main=document.getElementById('box_main'+id);
		var width=main.offsetWidth;
		var height=main.offsetHeight;
		if(width<250)main.style.width='250px';
		if(height<100)main.style.height='100px';
	}else{
		$('#showBox'+id).fadeIn('slow',function(){
			if(typeof(callBack.time)!='undefined'){								
				setTimeout(function(){
					$('#showBox'+id).fadeOut('slow',function(){
						closeBox(id,'flag');
					});
				},callBack.time);
			}
		});
	}
	return id;
}
function closeBox(id){
	//如果没有传id,关闭的将是最近的打开的showBox
	if(typeof(id)=='undefined'){
		var stTable=document.getElementsByTagName('table');
		for(var i=0,length=stTable.length;i<length;i++){
			if(stTable[i].className=='showBox'){
				id=stTable[i].id.replace(/\D/g,'');
			}
		}	
	}
	if(typeof(id)=='undefined')return;
	//还原内标签
	var input=document.getElementById("box_InnerTag"+id);
	if(input){
		var innerTag=input.innerDom;
		if(innerTag)innerTag.innerHTML=input.value;
	}
	var domContent=document.getElementById('box_content'+id);
	var domIframe=domContent?domContent.firstChild:null;
	var dombox=document.getElementById('showBox'+id);
	//防止IE下Iframe失去焦点
	if(domContent&&domIframe)domContent.removeChild(domIframe);
	if(dombox)document.body.removeChild(dombox);
}

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function (fmt) { //author: meizz
	var o = {
		"M+": this.getMonth() + 1, //月份
		"d+": this.getDate(), //日
		"h+": this.getHours(), //小时
		"m+": this.getMinutes(), //分
		"s+": this.getSeconds(), //秒
		"q+": Math.floor((this.getMonth() + 3) / 3), //季度
		"S": this.getMilliseconds() //毫秒
	};
	if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}
function formatBankNo (BankNo){
	if (BankNo.value == "") return;
	var account = new String (BankNo.value);
	account = account.substring(0,23);
	if (account.match (".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}") == null){
		/* 对照格式 */
		if (account.match (".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}|" + ".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}|" +
			".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}|" + ".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}") == null){
			var accountNumeric = accountChar = "", i;
			for (i=0;i<account.length;i++){
				accountChar = account.substr (i,1);
				if (!isNaN (accountChar) && (accountChar != " ")) accountNumeric = accountNumeric + accountChar;
			}
			account = "";
			for (i=0;i<accountNumeric.length;i++){
				if (i == 4) account = account + " ";
				if (i == 8) account = account + " ";
				if (i == 12) account = account + " ";
				if (i == 16) account = account + " ";
				account = account + accountNumeric.substr (i,1)
			}
		}
	}
	else
	{
		account = " " + account.substring (1,5) + " " + account.substring (6,10) + " " + account.substring (14,18) + "-" + account.substring(18,25);
	}
	if (account != BankNo.value) BankNo.value = account;
}
function getSource(){
	var system ={
		win : false,
		mac : false,
		xll : false
	};
	//检测平台
	var p = navigator.platform;
	system.win = p.indexOf("Win") == 0;
	system.mac = p.indexOf("Mac") == 0;
	system.x11 = (p == "X11") || (p.indexOf("Linux") == 0);
	//跳转语句，如果是手机访问就自动跳转到wap.seostudying.com页面
	if(system.win||system.mac||system.xll){
		return('PC');
	}else{
		getPhoneSource();
	}
}
function getPhoneSource(){
	var u = navigator.userAgent;
	if (u.indexOf("Android") > -1 || u.indexOf("Linux") > -1){
		return('ANDROID');
	}else if(u.indexOf("iPhone") > -1) {
		return('ISO');
	}else if(u.indexOf("Windows Phone") > -1) {
		return('WP');
	}else{
		return('OTHER');
	}
}