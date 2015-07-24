<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<jsp:include page="/include/head.jsp"></jsp:include>
<!--底部快捷导航 开始-->

<div class="bottom" style="margin:0;">
  
  <div class="bottom_one">
    <div class="bottom_one_all">
      <ul>
        <li>
          <h2>关于我们</h2>
        </li>
        <li style=" height:5px;"></li>
        <li><a href="/callcenter.do?id=1#g1">关于我们</a> </li>
        <li><a href="/callcenter.do?id=1#g2">平台原理</a> </li>
        <li><a href="/callcenter.do?id=1#g3">资质证件</a> </li>
        <li><a href="/callcenter.do?id=1#g5">合作伙伴</a> </li>
      </ul>
      <ul>
        <li>
          <h2>安全保障</h2>
        </li>
        <li style=" height:5px; *height:2px;"></li>
        <li><a href="/callcenter.do?id=1#g4">法律政策</a> </li>
        <li><a  href="/callcenter.do?id=4">本金保障</a> </li>
        <li><a  href="/callcenter.do?id=8">风险披露</a> </li>
        <li><a  href="/callcenter.do?id=7">免责声明</a> </li>
      </ul>
      <ul>
        <li>
          <h2>帮助信息</h2>
        </li>
        <li style=" height:5px;"></li>
        <li style="display: none"><a href="/callcenter.do?type=true&cid=1">新手入门</a> </li>
        <li><a href="/callcenter.do?id=2">操作指引</a> </li>
        <li> 
          <!--<a href="callcenter.do?showkf=kfs">客服中心</a>--> 
          <a href="/callcenter.do?id=3">常见问题</a> </li>
       <!--  <li><a href="callcenter.do?id=5">收益计算</a> </li> -->
      </ul>
      <!--
			<ul>
				<li><h2>资费说明</h2>
				</li>
				<li style=" height:5px;"></li>
				<li><a href="getMessageByHhn.do?id=6">收费标准</a>
				</li>
				<li><a href="getMessageByHhn.do?id=9"></a>
				</li>
				<li><a href="javascript:void(0)"></a>
				</li>
				<li><a href="javascript:void(0)"></a>
				</li>
			</ul>
			-->
      
      <ul>
        <li>
          <h2>联系我们</h2>
        </li>
        <li style=" height:5px;"></li>
        <!--
				<li><a href="callcenter.do">帮助中心</a>
				</li>
				-->
        
        <li><a href="/callcenter.do?showkf=kfs">客服中心</a> </li>
        <li><a href="javascript:void(0)"></a> </li>
        <li><a href="javascript:void(0)"></a> </li>
      </ul>
      <%--<div class="bottom_erwm"> <img src="/images/hynn.gif"  /> </div>--%>
      <div class="bottom_logo"> <img src="/images/er_wema.jpg"  width="142" height="142" /> </div>

      <div class="bottom_logo" style=" margin-right:35px;"> 
      	<p style="  font-size:11px;">客服热线（工作时间8:30-20:00）</p>
        <p><font style=" font-size:22px; ">4008-303-737 </font> </p>
        <p style=" font-size:11px; margin-top:5px;">客服邮箱</p>
        <p><font style="font-size:22px; ">p2p@hehenian.com </font></p>
        <!--<img src="/images/logo_zaixian.png" /><br><br>&nbsp;-->
        <%--				<a href="http://webscan.360.cn/index/checkwebsite/url/www.hehenian.com" target="right">--%>
     
          
       
        	
             <p style="float:left; margin-top:15px;"><a class="ui-footer-img-link weibo" href="http://weibo.com/u/3893072293" target="_blank">合和年新浪微博</a></p>
              <p style="float:left; margin-top:15px;"><a class="ui-footer-img-link we-chat" href="/images/er_wema.jpg"  rel="lytebox" title="请扫描二维码，添加微信账号<br />
获取海量投资资讯及最新优惠信息">合和年微信</a></p>
         
 
                 
  
      </div>
    </div>
  </div>
  <div class="bottom_two" style=" padding-top:25px; ">
  	<div style=" width:1170px; margin:0px auto">
    	<div  style="float:left; width:100%; text-align:center;">  <a href="/index.jsp" class="bottom_two">© 2014 深圳市彩付宝网络技术有限公司 </a>&nbsp;&nbsp;&nbsp;&nbsp;花样年集团成员（港联交所上市企业HK1777）&nbsp;&nbsp;&nbsp;&nbsp;
<a href="http://www.miibeian.gov.cn/" class="bottom_two" >粤ICP备14000649号</a>&nbsp;&nbsp;&nbsp;&nbsp;问题反馈：4008-303-737 </div>
      
    </div>
    <!--  <div style="text-align: center;padding-top: 20px;">
<script id="ebsgovicon" src="https://cert.ebs.gov.cn/govicon.js?id=e1a1ece1-34ef-44c9-abe9-ca2aa72d0f19&width=36&height=50&type=1" type="text/javascript" charset="utf-8"></script>
</div> -->
 </div>

</div>
<!--底部footer 结束--> 
<script type="text/javascript" src="/script/jqueryV172.js"></script>
<script type="text/javascript" src="/script/xl.js"></script>
<script type="text/javascript">
function addCookie()
{
 if (document.all){
       window.external.addFavorite('<%=application.getAttribute("basePath")%>','合和年在线');
    }
    else if (window.sidebar) {
       window.sidebar.addPanel('合和年信贷', '<%=application.getAttribute("basePath")%>', "");
    }else{
       alert('请手动设为首页');
    }
}

function setHomepage(){
    if (document.all){
        document.body.style.behavior='url(#default#homepage)';
        document.body.setHomePage('<%=application.getAttribute("basePath")%>');
    }else if (window.sidebar){
        if(window.netscape){
         try{  
            netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");  
         }  
         catch (e)  
         {  
            alert( "该操作被浏览器拒绝，如果想启用该功能，请在地址栏内输入 about:config,然后将项 signed.applets.codebase_principal_support 值该为true" );  
         }
    }else{
        alert('请手动添加收藏');
    }
    var prefs = Components.classes['@mozilla.org/preferences-service;1'].getService(Components.interfaces.nsIPrefBranch);
    prefs.setCharPref('browser.startup.homepage','<%=application.getAttribute("basePath")%>');
		}
	}
	$(function() {
		$(window).scroll(function() {
			if ($(window).scrollTop() >= 109) {
				$(".nav-zdh").css("position", "fixed")
			} else {
				$(".nav-zdh").css("position", "relative")
			}
		})
	})
</script> 




<style type="text/css">
ul,li {
	margin: 0;
	padding: 0
}

#scrollDiv {
	height: 182px;
	overflow: hidden
}

.load {
	width: 18px;
	height: 18px;
	margin: 10px;
}
@font-face{font-family:"Merriweather";src:url("/font/Merriweather.ttf");}


#rightButton{ position:fixed; _position:absolute; top:208px; right:0; z-index:999999; display:block;}
#right_ul{ position:relative;}
#right_qq{  background:url(/images/7_03.png) no-repeat; width:38px; height:41px; }
#right_tel{ background:url(/images/7_05.png) no-repeat; width:38px; height:41px; }
#right_tip{  background:url(/images/flag_right.png) no-repeat; width:252px; height:91px; position:absolute; right:40px; top:-10px; display:none; z-index:999999; }
.flagShow_p1{ float:left; margin-left:15px; _margin-left:5px; font-size:18px; line-height:91px;}
.flagShow_p2{ float:left; margin-left:10px; _margin-left:5px; font-size:18px;  color:#FA7C00;}
.flagShow_p2 a{ display:block; margin: 20px 0 5px 12px; line-height:0;}
.flagShow_p2 span{ margin: 0 0 0 14px; }
.flag_qq{ display:none;}
#backToTop{z-index:999999; display:none;}
a.backToTop_a{  background:url(/images/7_08.png) no-repeat; width:38px; height:44px; display:block; }
a.backToTop_a:active{  background:url(/images/7_11.png) no-repeat; }
.line91{ line-height:91px; }


</style>
<div id="rightButton">
	<ul id="right_ul">
		<li id="right_qq" class="right_ico" show="qq" hide="tel"></li>
		<li id="right_tel" class="right_ico" show="tel" hide="qq"></li>
        
		<li id="right_tip" class="png">
		<p class="flagShow_p1 flag_tel">咨询电话</p>
		<p class="flagShow_p2 flag_tel line91">4008-303-737</p>
		<p class="flagShow_p1 flag_qq">咨询QQ</p>
		<p class="flagShow_p2 flag_qq">
		<a>
		<!-- WPA Button Begin -->
<script charset="utf-8" type="text/javascript" src="http://wpa.b.qq.com/cgi/wpa.php?key=XzkzODAwNDI4MV8xNzM0NDVfNDAwODMwMzczN18"></script>
<!-- WPA Button End --></a> <span>4008303737</span> </p>
		<span style="display:none">
		<script charset="utf-8" type="text/javascript" src="http://wpa.b.qq.com/cgi/wpa.php?key=XzkzODAwNDI4MV8xMDk2OTBfNDAwODMwMzczN18" ></script>
		</span>
		</li>
        <li><div id="backToTop"><a href="javascript:;" onfocus="this.blur();" class="backToTop_a png"></a></div></li>
	</ul>
</div>



<script type="text/javascript">
$(document).ready(function(e) {
	$("#rightButton").css("right", "0px");
	
    var button_toggle = true;
	$(".right_ico").live("mouseover", function(){
		var tip_top;
		var show= $(this).attr('show');
		var hide= $(this).attr('hide');
		tip_top = show == 'tel' ?  15 :  -20;
		button_toggle = false;
		$("#right_tip").css("top" , tip_top).show().find(".flag_"+show).show();
		$(".flag_"+hide).hide();
		
	}).live("mouseout", function(){
		button_toggle = true;
		hideRightTip();
	});
	
	
	$("#right_tip").live("mouseover", function(){
		button_toggle = false;
		$(this).show();
	}).live("mouseout", function(){
		button_toggle = true;
		hideRightTip();
	});
	
	function hideRightTip(){
		setTimeout(function(){		
			if( button_toggle ) $("#right_tip").hide();
		}, 500);
	}
	
	$("#backToTop").live("click", function(){
		var _this = $(this);
		$('html,body').animate({ scrollTop: 0 }, 500 ,function(){
			_this.hide();
		});
	});

	$(window).scroll(function(){
		var htmlTop = $(document).scrollTop();
		if( htmlTop > 0){
			$("#backToTop").fadeIn();	
		}else{
			$("#backToTop").fadeOut();
		}
	});
});
</script>
<script type="text/javascript">
    var _mvq = window._mvq || [];
    window._mvq = _mvq;
    _mvq.push(['$setAccount', 'm-117079-0']);

    _mvq.push(['$setGeneral', '', '', /*用户名*/ '', /*用户id*/ '']);//如果不传用户名、用户id，此句可以删掉
    _mvq.push(['$logConversion']);
    (function() {
        var mvl = document.createElement('script');
        mvl.type = 'text/javascript'; mvl.async = true;
        mvl.src = ('https:' == document.location.protocol ? 'https://static-ssl.mediav.com/mvl.js' : 'http://static.mediav.com/mvl.js');
        var s = document.getElementsByTagName('script')[0];
        s.parentNode.insertBefore(mvl, s);
    })();

</script>

