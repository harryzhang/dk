<%@page import="com.sun.org.apache.xml.internal.serialize.Printer"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns:wb="http://open.weibo.com/wb">
<jsp:include page="/include/head.jsp"></jsp:include>
<script src="http://tjs.sjs.sinajs.cn/open/api/js/wb.js" type="text/javascript" charset="utf-8"></script>
<link rel="shortcut icon" href="<c:url value='/favicon.ico'/>">
<script type="text/javascript">
	$(function() {
		$(".header_two_right_ul li").hover(function() {
			$(this).find("ul").show();
		}, function() {
			$(this).find("ul").hide();
		})

		//ImgScroll(".mjd_banner", "#mjd_banner_ul", 1000, ".contrl", "a", "sydp-a5", "sydp-a6", "li", 1000, 900, 4000);

		//头部页面hover效果
		$(function() {
			$(".header_two_right_ul_tan li").hover(function() {
				$(this).find("a").css("color", "#e94718");
			}, function() {
				$(this).find("a").css("color", "#333333");
			});

		});

	});

	//菜单栏选中样式
	function hhn(index) {
		$(".hhn").eq(index).attr("style", "background:#fff;border-bottom:2px solid #e94718;");
		$(".hhn").eq(index).find("a:first").css("color", "#e94718");
	}
    function hhnNew(indexId) {
        $("#"+indexId).attr("style", "background:#fff;border-bottom:2px solid #e94718;");
        $("#"+indexId).find("a:first").css("color", "#e94718");
    }
	function setCookie(name,value)
	{
	    var Days = 30;
	    var exp = new Date();
	    exp.setTime(exp.getTime() + Days*24*60*60*1000);
	    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();

	}

	//读取cookies
	function getCookie(name)
	{
	    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	 
	    if(arr=document.cookie.match(reg))
	 
	        return (arr[2]);
	    else
	        return null;
	}
</script>
<!--顶部状态栏 开始-->
<div class="header">
  <div class="header_one">
    <div class="header_one_center">
      <div style="font-size: 13px; width:50% !important; width:49%; float:left; font-size:80%"><div style="float:left">客服热线：<font style="font-size:17px;font-family: 'Merriweather', serif;">4008-303-737</font>&nbsp;&nbsp;&nbsp;&nbsp;服务时间：8:30-20:00&nbsp;&nbsp;&nbsp;&nbsp;</div>
      
       <p style="float:left; margin-top:15px;"><a class="ui-top-img-link weibo" href="http://weibo.com/u/3893072293" target="_blank">合和年新浪微博</a></p>
              <p style="float:left; margin-top:15px;"><a class="ui-top-img-link we-chat" href="<c:url value='/images/er_wema.jpg'/>"  rel="lytebox" title="请扫描二维码，添加微信账号，获取海量投资资讯及最新优惠信息">合和年微信</a></p>
      
      </div>
      <div style="font-size: 13px; width:50%; float:right; text-align:right ; ">
        <ul >
          <li style=" border-left:1px solid #FFF; padding:0px 0px 0px 10px; float:right; width:60px;"><span><a href="<c:url value='/account/reg.do'/>${empty param.fromUrl ? '':'?fromUrl='}${param.fromUrl}${empty param.source ? '':'&source='}${param.source}">免费注册</a></span></li>
          <li style="border-right:1px solid #ccc ; padding:0px 20px; float:right; overflow:hidden; height:48px;"><span>
            <c:if test="${ session.user !=null}"><a href="<c:url value='/home.do'/>">[${user.username}]</a> <span></span> <a href="<c:url value='/logout.do'/>">退出</a> </c:if>
            <c:if test="${session.user ==null}"><a href="<c:url value='/account/login-index.do'/>${empty param.fromUrl ? '':'?fromUrl='}${param.fromUrl}${empty param.source ? '':'&source='}${param.source}">登录</a></c:if>
            </span></li>
        </ul>
      </div>
    </div>
  </div>
  <div class="header_two">
    <div class="header_two_center">
      <div class="header_two_left">
<table width="570" border="0">
          <tr>
            <%--<td valign="bottom"><a href="/index.do" style=" border:0px;"><img src="/images/hhnimages/logo_line.png" style=" border:0px;"/></a> &nbsp;&nbsp;<a href="/index.do" style=" border:0px;"><img src="/images/hhnimages/hyn.png" style=" border:0px;"/></a>（港联交所上市企业HK1777）</td>--%>
            <td width="200"><div class="header_two_left_logo"> <a href="<c:url value='/index.do'/>"><img src="<c:url value='/images/hhnimages/logo_zaixian.png'/>" style=" border:0px;" alt="合和年在线"/> </a> </div></td>
           <%-- <td valign="bottom"><a href="<c:url value='/index.do'/>" style=" border:0px;"><img src="<c:url value='/images/hhnimages/logo_line.png'/>" style=" border:0px;"/></a>
                &nbsp;&nbsp;<a href="<c:url value='/index.do'/>" style=" border:0px;">
                <img src="<c:url value='/images/hhnimages/hyn.png'/>" style=" border:0px;"/></a>（港联交所上市企业HK1777）
            </td>--%>
          </tr>
    
        </table>
      </div>
      <div class="header_two_right">
        <ul class="header_two_right_ul">
          <li class="hhn" id="topIndex-index"><a href="<c:url value='/index.do'/>">首 页</a>
          </li>
          <li class="hhn" id="topIndex-finance"><a href="<c:url value='/finance.do'/>">我要投资</a>
          </li>
          <li class="hhn" id="topIndex-finance"><a href="http://house.hehenian.com">我要贷款</a>
          </li>
           <li class="hhn" id="topIndex-dqlc"><a href="/hhn_web/termFinance.do">爱定宝</a></li>
		 <li class="hhn" id="topIndex-zqzr"><a href="<c:url value='/queryFrontAllDebt.do'/>">债权转让</a>
          </li>
          <li class="hhn" id="topIndex-callcenter"><a href="<c:url value='/callcenter.do'/>">新手专区</a>
          </li>
        <li class="hhn" id="topIndex-home"><a href="<c:url value='/home.do'/>">会员中心</a></li>
       <c:if test="${session.user.userGroup==1}">
         <li class="hhn" id="topIndex-zone"><a href="<c:url value='/hyn-index.do'/>" style="color: #f60;">花样年专区</a></li>
         </c:if>
           <c:if test="${session.user.userGroup==2}">
         <li class="hhn" id="topIndex-zone"><a href="<c:url value='/hyh-index.do'/>" style="color: #f60;">花样会专区</a></li>
         </c:if>
           <c:if test="${session.user.userGroup==3}">
         <li class="hhn" id="topIndex-zone"><a href="<c:url value='/zone.do?id=3'/>" style="color: #f60;">福泰年专区</a></li>
         </c:if>
        </ul>
      </div>
      <div class="cle"></div>
    </div>
  </div>
</div>

<!--顶部主导航 结束-->