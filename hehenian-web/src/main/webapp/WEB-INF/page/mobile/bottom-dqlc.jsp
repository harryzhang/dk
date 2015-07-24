<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value='/wap/styles/dqlc/base.css?t=1'/>" />
<script type="text/javascript">var appRefer =<%if(session.getAttribute("appstyle")==null){%>null<%}else{%>"<%=session.getAttribute("appstyle") %>"<%}%>;</script>

<style type="text/css">
    .secondMenuBox{
        display: none;
        position: fixed;
        bottom: 106px;
        background: #e9eaed;
        border-left: #d1d1d1 solid 1px;
        border-right: #d1d1d1 solid 1px;
        z-index: 100;
    }
    .secondMenuBox .secondMenu{
        display: block;
        font-size:28px;
        border-top: #d1d1d1 solid 1px;
        padding:20px;
        z-index: 100;
    }
</style>
<div class="bottom">
    <a href="/webapp/webapp-index.do">
    <span class="bottomMenu">
        <div class="icon nav1"></div>
        <p class="menuName">首页</p>
    </span>
    </a>
    <a href="/webapp/webapp-tz-intro.do">
    <span class="bottomMenu">
        <div class="icon nav2"></div>
        <p class="menuName">投资列表</p>
    </span>
    </a>
    <a id="userAccound">
    <span class="bottomMenu">
        <div class="icon nav3"></div>
        <p class="menuName">我的账户</p>
    </span>
    </a>
    <a href="/webapp/more.do">
    <span class="bottomMenu">
        <div class="icon nav4"></div>
        <p class="menuName">更多</p>
    </span>
    </a>
</div>
<div class="secondMenuBox" style="left:50%">
    <a class="secondMenu" onclick="goHref('<c:url value="/view/mobile/personCenter.jsp"/>')">爱定宝账户</a>
    <a class="secondMenu" onclick="goHref('/webapp/webapp-money.do')">E理财账户</a>
</div>
<script type="text/javascript" src="<c:url value="/js/jquery.min.js"/>"></script>
<script>
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