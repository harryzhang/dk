<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:include page="head.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="/newcss/style.css" />
<script type="text/javascript" src="/newcss/clockp.js"></script>
<script type="text/javascript" src="/newcss/clockh.js"></script>
<script type="text/javascript" src="/newcss/jquery.min.js"></script>
<script type="text/javascript" src="/newcss/ddaccordion.js"></script>
<script type="text/javascript">
ddaccordion.init({
	headerclass: "submenuheader", //Shared CSS class name of headers group
	contentclass: "submenu", //Shared CSS class name of contents group
	revealtype: "click", //Reveal content when user clicks or onmouseover the header? Valid value: "click", "clickgo", or "mouseover"
	mouseoverdelay: 200, //if revealtype="mouseover", set delay in milliseconds before header expands onMouseover
	collapseprev: true, //Collapse previous content (so only one open at any time)? true/false 
	defaultexpanded: [], //index of content(s) open by default [index1, index2, etc] [] denotes no content
	onemustopen: false, //Specify whether at least one header should be open always (so never all headers closed)
	animatedefault: false, //Should contents open by default be animated into view?
	persiststate: true, //persist state of opened contents within browser session?
	toggleclass: ["", ""], //Two CSS classes to be applied to the header when it's collapsed and expanded, respectively ["class1", "class2"]
	togglehtml: ["suffix", "<img src='/newimages/plus.gif' class='statusicon' />", "<img src='/newimages/minus.gif' class='statusicon' />"], //Additional HTML added to the header when it's collapsed and expanded, respectively  ["position", "html1", "html2"] (see docs)
	animatespeed: "fast", //speed of animation: integer in milliseconds (ie: 200), or keywords "fast", "normal", or "slow"
	oninit:function(headers, expandedindices){ //custom code to run when headers have initalized
		//do nothing
	},
	onopenclose:function(header, index, state, isuseractivated){ //custom code to run whenever a header is opened or closed
		//do nothing
	}
})
</script>
<script type="text/javascript" src="/newcss/jconfirmaction.jquery.js"></script>
<script type="text/javascript">
$(function() {
//	hhn(4);
    hhnNew("topIndex-home");
});
/* 	$(document).ready(function() {
		$('.ask').jConfirmAction();
	}); */
	
</script>
<script language="javascript" type="text/javascript" src="/newcss/niceforms.js"></script>
<link rel="stylesheet" type="text/css" media="all" href="/newcss/niceforms-default.css" />


        <div class="sidebarmenu"> <a class="menuitem submenuheader" href="">个人中心</a>
          <div class="submenu">
            <ul>
              <li><a href="/home.do">个人主页</a></li>

            <li><a href="/owerInformationInit.do">基本资料</a></li>
            <li><a href="/updatexgmm.do">安全中心</a></li>
            </ul>
          </div>
          <a class="menuitem submenuheader" href="" >理财管理</a>
          <div class="submenu">
            <ul>
           	<li><a href="/investCurrentRecord.do">当前投资</a></li>
            <li><a href="/investSuccessedRecord.do">成功投资</a></li>
            <li><a href="/automaticBidInit.do">自动投标</a></li>
                <li><a href="/queryMyPayingBorrowList.do">我的借款</a></li>

            <%--<li><a href="investDetailCount.do">理财统计</a></li>--%>
            </ul>
          </div>
          <a class="menuitem submenuheader" href="">资金管理</a>
          <div class="submenu">
            <ul>
        	<li><a href="/fundManager.do">资金记录</a></li>
            <li><a href="/queryFontFundrecordReback.do">回收本息</a></li>
            <li><a href="/queryFontRechargeHistory.do">充值记录</a></li>
            <li><a href="/queryWithdrawList.do">提现记录</a></li>
            <li><a href="/rechargeInit.do">我要充值</a></li>
            <li><a href="/withdrawLoad.do">我要提现</a></li>
            <li><a href="/bankInfoSetInit.do">银行卡管理</a></li>
           <%-- <li><a href="/hhn_web/queryBankCard.do">定期理财银行卡绑定</a></li>--%>
            </ul>
          </div>

            <a class="menuitem submenuheader" href="">定期理财</a>
            <div class="submenu">
                <ul>
                    <li><a href="/hhn_web/accountQuery.do">定期理财账户</a></li>
                    <li><a href="/hhn_web/view/transactionDetails.jsp">购买记录</a></li>
                    <li><a href="/hhn_web/view/borrowDetails.jsp">投资记录</a></li>
                    <li><a href="/hhn_web/view/chargeList.jsp">充值记录</a></li>
                    <li><a href="/hhn_web/view/cardManage.jsp">银行卡管理</a></li>
                </ul>
            </div>

           <a class="menuitem_red" href="/logout.do">退出登录</a> </div>

