<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value='/wap/mobile/styles/hhn.common.css?t=1'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/wap/mobile/styles/hhn.layout.css?t=1'/>" />

<%if(session.getAttribute("appstyle")!=null){%>
<link rel="stylesheet" type="text/css" href='/wap/mobile/styles/hhn.<%=session.getAttribute("appstyle") %>.css?t=2' />
<%}%>
<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<style type="text/css">
    .secondMenuBox {
        display: none;
        width: 25%;
        position: fixed;
        bottom: 55px;
        background: #e9eaed;
        border-left: #d1d1d1 solid 1px;
        border-right: #d1d1d1 solid 1px;
        z-index: 99999;
        min-width: 110px;
    }
    .secondMenuBox .secondMenu {
        display: block;
        width: 100%;
        text-align: center;
        font-size: 0.875em;
        border-top: #d1d1d1 solid 1px;
        z-index: 100;
        padding: 10px 0;
    }

</style>
<script type="text/javascript">var appRefer =<%if(session.getAttribute("appstyle")==null){%>null<%}else{%>"<%=session.getAttribute("appstyle") %>"<%}%>;</script>
<script type="text/javascript" src="<c:url value="/wap/mobile/scripts/common/zepto.min.js"/>"></script>
<nav class="nav" id="nav">
    <ul>
        <li><a href="<c:url value='/webapp/webapp-index.do'/>"><i class="nav1-icon"></i><span>首页</span></a></li>
        <li><a href="<c:url value='/webapp/webapp-tz-intro.do'/>"><i class="nav2-icon"></i><span>投资列表</span></a></li>

        <% if(session.getAttribute("user")!=null) {%>
        <li id="userAccound"><a><i class="nav3-icon"></i><span>我的帐户</span></a></li>
        <%}else{%>
        <li><a href="<c:url value='/webapp/webapp-login.do'/>"><i class="nav3-icon"></i><span>我的帐户</span></a></li>
        <%}%>
        <li><a href="<c:url value='/webapp/more.do'/>"><i class="nav4-icon"></i><span>更多</span></a></li>
    </ul>
</nav>
<div class="secondMenuBox" style="left:50%">
    <a class="secondMenu" onclick="goHref('<c:url value="/hhn_web/view/mobile/personCenter.jsp"/>')">爱定宝</a>
    <a class="secondMenu" onclick="goHref('/webapp/webapp-money.do')">项目投资</a>
</div>
<script>
    document.cookie = "token=;path=/";
    $(document).ready(function(){
        $('#userAccound').on("click",function(){
            if($('.secondMenuBox').css("display") == "none"){
                $('.secondMenuBox').show();
                event.stopPropagation();
            }else{
                $('.secondMenuBox').hide();
            }
        });
        $(document).click(function(){
            $(".secondMenuBox").css("display","none");
        })

        $('.secondMenuBox').click(function(){
            return false;
        });
    })
    function goHref(path){
        document.location.href = path;
    }
</script>