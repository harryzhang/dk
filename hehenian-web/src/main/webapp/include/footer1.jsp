<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>


<!--底部快捷导航 开始-->
<div class="footz">
<div class="footer01">
  <ul>
    <li>
      <dl>
        <dt class="dt00">新手入门</dt>
        <dd><a href="/account/reg.do">免费注册</a></dd>
        <dd><a href="home.do">我的账户</a></dd>
        <dd><a href="getMessageBytypeId.do?typeId=4">关于我们</a></dd>
      </dl>
    </li>
    <li>
      <dl>
        <dt class="dt01">我要投资</dt>
        <dd><a href="finance.do">浏览借款</a></dd>
      </dl>
    </li>
    <li>
      <dl>
        <dt class="dt02">我要贷款</dt>
        <dd><a href="borrow.do">发布借款</a></dd>
        <dd><a href="financetool.do">利息计算器</a></dd>
      </dl>
    </li>
    <li>
      <dl>
        <dt class="dt03">安全保障</dt>
        <dd><a href="capitalEnsure.do">本金保障</a></dd>
        <dd><a href="getMessageBytypeId.do?typeId=5">法律政策</a></dd>
      </dl>
    </li>
    <li>
      <dl>
        <dt class="dt04">帮助信息</dt>
        <dd><a href="callcenter.do">帮助中心</a></dd>
        <dd><a href="getMessageBytypeId.do?typeId=4">联系我们</a></dd>
      </dl>
    </li>
  </ul>
</div>
<div class="footer02" style="display: none;">
  <p>
  	版权所有 © 2013 ${sitemap.companyName} ${sitemap.regionName }  备案号：<span>${sitemap.certificate }</span><br>
  客服热线（9：00-18：00）: ${sitemap.servicePhone }
</p>

</div>
<div class="footer02">
  <p><img src="images/maitl_bottom1.jpg" />	版权所有 © 2013 ${sitemap.companyName} ${sitemap.regionName }  备案号：<span>${sitemap.certificate }</span><br>
  客服热线（9：00-18：00）: ${sitemap.servicePhone }
    <div style=" clear:both;"></div>
  </div>
  </div>
<!--底部footer 结束-->
<script type="text/javascript" src="script/jqueryV172.js"></script>
<script type="text/javascript" src="script/xl.js"></script>
<script type="text/javascript">
function addCookie()
{
 if (document.all){
       window.external.addFavorite('<%=application.getAttribute("basePath")%>','合和年在线');
    }
    else if (window.sidebar) {
       window.sidebar.addPanel('合和年在线', '<%=application.getAttribute("basePath")%>', "");
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
    var prefs = Components.classes['@mozilla.org/preferences-service;1'].getService(Components. interfaces.nsIPrefBranch);
    prefs.setCharPref('browser.startup.homepage','<%=application.getAttribute("basePath")%>');
 }
}
$(function(){
	$(window).scroll(function(){
		if($(window).scrollTop()>=109){
			$(".nav-zdh").css("position","fixed")
		}
		else{
		$(".nav-zdh").css("position","relative")
		}
	});
});
</script>
