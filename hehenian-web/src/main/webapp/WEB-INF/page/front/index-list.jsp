<%@ page language="java" import="java.sql.*" pageEncoding="UTF-8" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
    <jsp:include page="/include/head.jsp"></jsp:include>
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>
<div class="cle"></div>
<div class="center_banner">
    <!--flash begin-->
    <div id="flashBg" style="background-color: rgb(11, 11, 11); height:300px; overflow:hidden; border-top:1px solid #e4e4e4;border-bottom:1px solid #e4e4e4 ">
        <div style=" width:1170px; margin:0px auto;">
            <!--登陆窗口浮动层-->
            <div style=" height:300px; width:320px;background: rgba(0,0,0,.5);position:absolute; z-index:9999; margin-left:848px; color:#FFF">
                <!--登陆窗口-->
                <s:if test="%{#session.user==null}">
                    <div  style="padding-left:28px; padding-top:26px; font-size:22px;">合和年在线收益率</div>
                    <div   style="padding:2px 0 10px 28px; font-size: 18px;">
                        最高可达<span style="font-size:60px;font-family: 'Arial';color: #F60;">14</span>
                        <span style="font-size:40px;font-family: 'Arial';">%</span>

                    </div>
                    <div  style=" margin-left:28px; padding-top: 18px;">
                        <input type="button" onclick="window.location.href='/account/reg.do'" value="免费注册" style=" width:264px; height:40px; border:0px; background:#F60; font-size:24px;color:#FFF; margin-left:0px;cursor: pointer;"/>
                    </div>
                    <div style="padding-left:28px; font-size:12px; margin-top:16px; letter-spacing: 0.6px;line-height: 20px; ">
                        一元起投，花样年集团承诺逾期回购<br />
                        <font size="+0.5" style="font-family: 'Arial';">100%</font>保障投资者资金安全</div>
                    <div  style=" margin-top:18px; font-size:12px; width:292px;text-align:right;">已有账号，
                        <a  href="<c:url value="/account/login-index.do"/>" style=" color:#00ccff">立即登录</a></div>
                </s:if>
                <s:else>
                <div  >
                    <div  style="padding-left:28px; padding-top:26px; font-size:22px;">合和年在线收益率</div>
                    <div   style="padding:10px 0 10px 30px; font-size: 18px;">
                        最高可达<span style="font-size:60px;font-family: 'Arial';color: #F60;">14</span>
                        <span style="font-size:40px;font-family: 'Arial';">%</span>

                    </div>
                    <div  style=" margin-left:28px; margin-top:11px; line-height:26px; ">
                        <strong  style=" font-weight: normal;">欢迎您！<u style="  text-decoration: none; font-weight: bold; margin-left:10px;font-family: 'Arial';">
                                ${user.username}</u></strong><br />
                        <strong  style="font-weight: normal;">可用余额 ：<u style="  text-decoration: none; font-weight: bold; margin-left:10px;font-family: 'Arial';">
                            <s:if test="#request.usableSum==null || #request.usableSum=='' ">0.00元</s:if>
                            <s:else> ${request.usableSum}</s:else>
                        </u></strong></div>
                    <div  style=" margin-left:28px; margin-top:10px; font-size:12px; color:#FFF; text-align:right;">
                     <c:if test="${session.user.accountType==1}">
                        <span>
                        <a onclick="alert('无需充值');return;" style=" display: block; float:left; width:125px; height:40px; background:#F60; text-align:center; border:none; color:#fff; cursor:pointer; font-size:24px;color:#FFF; line-height:40px; ">充值 </a></span>
                        <a onclick="alert('请发送提现申请邮件进行提现操作');return;" style=" width:125px; height:40px; background:#6baf00;text-align:center; border:none; color:#fff; cursor:pointer; margin-left:15px;display: block; float:left; line-height:40px;font-size:24px;">提现</a>
					</c:if>
					<c:if test="${session.user.accountType != 1}">
					 <span>
                            <a href="/rechargeInit.do" style=" display: block; float:left; width:125px; height:40px; background:#F60; text-align:center; border:none; color:#fff; cursor:pointer; font-size:24px;color:#FFF; line-height:40px; ">充值 </a></span>
                        <a href="/withdrawLoad.do" style=" width:125px; height:40px; background:#6baf00;text-align:center; border:none; color:#fff; cursor:pointer; margin-left:15px;display: block; float:left; line-height:40px;font-size:24px;">提现</a>
					</c:if>
                        </div>
                    </s:else>
                </div>
            </div>
            <!--首页大图-->
            <div id="flashLine">
                <div id="flash" style=" height:300px;">
                    <%--<a href="#" id="flash1" name="#888687" style="display: block;">
                        <img src="/images/jfwh.jpg?_=0318" width="1170" height="350">
                    </a>--%>


<!--                     <a href="/frontNewsDetails.do?id=633" id="flash1" name="#f6f5e5" style="display: block;"> -->
<!--                         <img src="/images/0101.jpg" width="1170" height="350"> -->
<!--                     </a> -->
					
					<a href="<c:url value="/inviteFriend.do" />" id="flash1" name="#fffff0" style="display:block;">
                        <img src="http://static.hehenian.com/m/img/banner/pc-invite-banner.png" width="1170" height="350">
                     </a>

					<!-- 母亲节特别活动 -->
					<a href="/webapp/motherDayNotice.do" id="flash1" name="#fffff0" style="display:block;">
                        <img src="http://static.hehenian.com/m/img/banner/pc-motherday-banner.jpg" width="1170" height="350">
                     </a>
                     
					<a href="/hhn_web/termFinance.do" id="flash2" name="#FFE97A" style="display:none;">
                        <img src="/images/zd/banner1-01.jpg" width="1170" height="350">
                     </a>    
                    <a href="/hhn_web/termFinance.do" id="flash3" name="#e83344" style="display: none;">
                        <img src="/images/zd/12.jpg" width="1170" height="350">
                    </a>
                    <a href="#" id="flash4" name="#1082e3" style="display: none;">
                        <img src="/images/zd/13.jpg" width="1170" height="350">
                    </a>
                    <a href="#" id="flash5" name="#dddddd" style="display: none;">
                        <img src="images/zd/14.jpg?t=1" width="1170" height="350">
                    </a>
                    <!--<a id="flash4" name="#dcdddd" style="display: none;cursor:default; ">
                    <img src="/images/hynhg.jpg" width="1170" height="350">
                    </a>
                    <a id="flash5" name="#027da7" style="display: none;cursor:default; ">
                    <img src="images/ad/03.jpg" width="1170" height="350">
                    </a>-->
                    <div class="flash_bar" style="padding-left:50%;">
                    	<div class="no" id="f1" ></div>
                        <div class="dq" id="f2" ></div>
                        <div class="no" id="f3" ></div>
                        <div class="no" id="f4" ></div>
                        <div class="no" id="f5"></div>
                        <!--           <div class="no" id="f4" onclick="changeflash(4)"></div>-->
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
   $(function(){
    var currentindex=0;
    var length = $(".flash_bar div").length;

    $("#flashBg").css("background-color",$("#flash1").attr("name"));
    $(".flash_bar div").click(function(){
    	var index = $(this).index()+1;
    	changeflash(index);
    	
    });
    function changeflash(i) {
        currentindex=i;
        for (j=1;j<=length;j++){
            if (j==i)
            {$("#flash"+j).fadeIn("normal");
                $("#flash"+j).css("display","block");
                $("#f"+j).removeClass();
                $("#f"+j).addClass("dq");
                $("#flashBg").css("background-color",$("#flash"+j).attr("name"));
            }
            else
            {$("#flash"+j).css("display","none");
                $("#f"+j).removeClass();
                $("#f"+j).addClass("no");}
        }
    }
    function startAm(){
        timerID = setInterval("timer_tick()",9000);
    }
    function stopAm(){
        clearInterval(timerID);
    }
    function timer_tick() {
        currentindex=currentindex>=length?1:currentindex+1;
        changeflash(currentindex);
    }
    
       /*  $(".flash_bar div").mouseover(function(){stopAm();}).mouseout(function(){startAm();}); */
     startAm();
    });
</script>


<!--新闻公告开始-->
<div style="  border-bottom:1px solid #e4e4e4; margin-top:5px;">
    <div class="center_one_right2" style=" margin-bottom:15px; width:1170px; margin:0px auto;">
        <div style=" border-bottom:0px; height:50px; line-height:50px;">
            <div style="width:94px;  float:left; font-size:16px; color:#666">网站公告</div>
            <div style=" float:left; height:50px; line-height:50px;">
                <ul style=" height:50px; overflow:hidden; padding:0px;">
                    <s:iterator value="#request.newsList" var="bean" status="st">
                        <li style=" list-style:none;border-bottom:0px; background:#FFF; line-height:50px; height:50px; "><a href="frontNewsDetails.do?id=${bean.id }" title="${bean.title }" target="_blank" style=" color:#09C; font-size:16px;"><shove:sub value="title" size="999" /><font style=" margin-left:20px; font-size:14px; color:#999">(公告日期：<fmt:formatDate value="${bean.publishTime }" pattern="yyyy-MM-dd"></fmt:formatDate>)</font></a></li>
                    </s:iterator>
                </ul>
            </div>
            <a href="/initNews.do" style=" font-size:14px; color:#06C; float:right">更多></a></div>
    </div>
</div>
<!--新闻公告结束-->

<!--定期标的开始-->
<div style="padding-top:30px;background:#f8f8f8;">
    <div style=" width:1168px; margin:0px auto;background:#fff; border:1px solid #e4e4e4; overflow:hidden;">
        <%--<p style=" text-align:center; color:#4c4c4c; font-size:36px; padding:40px 0px 0px 0px;">爱定宝</p>
         <p style="font-size:20px;color:#999999;text-align:center;margin-top:5px">快速便捷 高枕无忧</p>--%>
     <%--   <iframe src="/hhn_web/view/conductFinancialBlock.jsp" frameborder="0" scrolling="no" width="1170px" height="400px" ></iframe>--%>
        <iframe src="/hhn_web/idxPageFinance.do" frameborder="0" scrolling="no" width="1170px" height="400px" ></iframe>
    </div>
</div>
<!--定期标的结束-->


<!--普通标的开始-->
<div style="padding-top:30px;background:#f8f8f8; padding-bottom:40px;">
    <div style=" width:1168px; margin:0px auto;background:#fff; border:1px solid #e4e4e4; overflow:hidden;">
        <p style=" text-align:center; color:#4c4c4c; font-size:36px; padding:40px 0px 10px 0px;">最新投资项目</p>
        <p style=" text-align:center;  "><a onclick="yjtb();"><img src="images/moretz.gif" border="0"/></a></p>
        <div style=" margin-top:40px; padding:0px 50px 0px 50px">
            <div class="center_one_left_db">
                <s:if test="#request.mapList.size>0">
                    <s:if test="#request.mapList.size>2">
                    	<s:set var="mapsiz" value="2"></s:set>
                    </s:if>
                    <s:else>
                    	<s:set var="mapsiz" value="#request.mapList.size-1"></s:set>
                    </s:else>
                    
                    <s:iterator value="#request.mapList" var="finance" end="#mapsiz" begin="0">
                        <div class="center_one_left_db_a">

                            <!--图-->
                            <div style=" width:78px; height:137px; float:left;"><img src="${finance.borrowStatus==2?'images/hot.png':'images/v1/over1.png' }"/></div>

                            <!--内容-->
                            <div class="shouyebiao">
                                <ul>
                                    <li style=" font-size:16px;"><span><a href="/financeDetail.do?id=${finance.id}" target="_blank">${finance.borrowTitle}</a></span><br />
                                        <font size="-2" color="#999999">编号：${finance.number}</font></li>
                                    <li style=" height:26px;"><img src="images/new/1.gif"  style=" float:left" />借款金额：
                                        <s:property value="#finance.borrowAmount" default="0" />
                                        元 </li>
                                    <li style=" height:26px;"><img src="/images/new/2.gif" style=" float:left" />借款期限：
                                        <s:property value="#finance.deadline" default="0" />
                                        <s:if test="%{#finance.isDayThe ==1}">个月</s:if>
                                        <s:else>天</s:else>
                                    </li>
                                    <li style=" height:26px; "><img src="/images/new/3.gif"  style=" float:left" />年利率：
                                        <font size="3" color="#FF6600">
                                            <s:property value="#finance.annualRate" default="0" />%
                                        </font>
                                    </li>

                                </ul>
                            </div>



                            <div style=" float:right;  width:345px; padding-right: 20px; padding-top:30px;">
                                <!--投标按钮margin-right:30px;-->

                                <div style=" width:345px;text-align:right;" class="nei_button" >
                                    <s:if test="%{#finance.borrowStatus == 1}">
                                        <input type="button" value="初审中" style="background: #ccc;cursor:default;" />
                                    </s:if>
                                    <s:elseif test="%{#finance.borrowStatus == 2}">
                                        <div class="bottm">
                                            <input type="button" onclick="window.location.href='/financeDetail.do?id=${finance.id}'" value="立即投标"  />
                                        </div>
                                    </s:elseif>
                                    <s:elseif test="%{#finance.borrowStatus == 3}">
                                        <input type="button" value="满标" style="background: url(../images/pic_all.png) -174px -356px no-repeat ;cursor:default;" />
                                    </s:elseif>
                                    <s:elseif test="%{#finance.borrowStatus == 4}">
                                        <s:if test="%{#finance.borrowShow == 2}">
                                            <input type="button" value="回购中" style="background: #ccc;cursor:default;" />
                                        </s:if>
                                        <s:else>
                                            <input type="button" onclick="window.location.href='/financeDetail.do?id=${finance.id}'" value="还款中" style="background: url(../images/pic_all.png) -174px -300px no-repeat" />
                                        </s:else>
                                    </s:elseif>
                                    <s:elseif test="%{#finance.borrowStatus == 5}">
                                        <input type="button" value="已还完" style="background:url(../images/pic_all.png) -348px -300px no-repeat ;cursor:default;" />
                                    </s:elseif>
                                    <s:elseif test="%{#finance.borrowStatus == 7}">
                                        <input type="button" value="复审中" style="background:background:url(../images/pic_all.png) -348px -354px no-repeat ;cursor:default;" />
                                    </s:elseif>
                                    <s:else>
                                        <input type="button" value="流标" style="background:background:url(../images/pic_all.png) -348px -408px no-repeat ;cursor:default;" />
                                    </s:else>
                                </div>
                                <div style=" width:327px; float:right; padding-left:20px; overflow:hidden">
                                    <div style=" float:left; line-height:28px; text-align:left; ">借款进度：</div>
                                    <div class="index_jd">
                                        <div style=" font-size:0px; line-height:0px;width: <s:property value="#finance.schedules" default="0"/>%;height:12px; background:url(images/pic_all.png) 0 -67px no-repeat; "></div>
                                    </div>
                                    <div style=" padding-top:7px;text-align:right;"><s:property value="#finance.schedules" default="0" />%</div>
                                </div>
                                <div  style=" width:345px;  line-height:32px;text-align:right;" >已成功借款：<font color="#FF6600">
                                    <s:property value="#finance.hasInvestAmount" default="---" />
                                </font>元</div>
                                <div  style="  width:345px;text-align:right; " >剩余借款金额：<font color="#006600">
                                    <s:property value="#finance.investNum" default="---" />
                                </font>元</div>
                            </div>
                            <!---->

                        </div>
                        <div style="width:1068px;"><img src="images/bbjj.png" width="1068" /></div>
                    </s:iterator>
                    <div style=" margin-bottom:40px;">
                        <table width="1068" border="0">
                            <tbody>
                            <tr style="text-align: center;">
                                <td colspan="4"><input type="button" onclick="window.location.href='/finance.do'" value="查看更多..." style=" width:238px; height:40px; border:0px; background:#f90; font-size:16px;color:#FFF; margin-left:0px;"/></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="cle"></div>
                </s:if>
            </div>
        </div>
    </div>
    <!--普通标的结束-->

</div>




<p style="font-size:36px;color:#333333;text-align:center;margin-top:50px">4大优势 值得信赖</p>
<p style="font-size:20px;color:#999999;text-align:center;margin-top:10px; margin-bottom:30px;">你我的成功因相互信赖</p>
<div style=" width:1170px; margin:0px auto; text-align:center"><img src="images/zd/d.gif" width="1068" /></div>
<div style=" width:1170px; margin:0px auto; margin-bottom:80px;">
    <div style=" float:left; width:250px; text-align:right">预期收益最高14%</div>
    <div style=" float:left; width:265px; text-align:right">购买次日立享收益</div>
    <div style=" float:left; width:265px; text-align:right">在线快速查询领取</div>
    <div style=" float:left; width:240px; text-align:right">1元即可投资</div>
</div>


<div class="bottom_link">
    <h3>合作伙伴</h3>
    <div class="link">
        <ul>
            <li><a href="###"><em class="nh" ></em><i></i></a></li>
            <li><a  href="###"><em class="jh"></em><i></i></a></li>
            <li><a  href="###"><em class="gh"></em><i></i></a></li>
            <li><a  href="###"><em class="zh"></em><i></i></a></li>
            <li><a  href="###"><em class="hx"></em><i></i></a></li>
            <li><a  href="###"><em class="pa"></em><i></i></a></li>
            <li><a  href="###"><em class="pf"></em><i></i></a></li>
            <li><a  href="###"><em class="hftx"></em><i></i></a></li>
            <li><a  href="###"><em class="wdzx"></em><i></i></a></li>
            <li><a  href="###"><em class="jjlc"></em><i></i></a></li>
            <li><a  href="###"><em class="wdzj"></em><i></i></a></li>
            <li><a  href="###"><em class="xw"></em><i></i></a></li>
            <li><a  href="###"><em class="wdty"></em><i></i></a></li>
            <li><a  href="###"><em class="wddg"></em><i></i></a></li>
            <li><a  href="###"><em class="cf"></em><i></i></a></li>
             <li><a  href="###"><em style="background: none"><img src="/img/tl.png" /></em><i></i></a></li>
        </ul>
    </div>
</div>

<input type="hidden" value="${session.user.id }" id="userId"/>
<!-- 引用底部公共部分-->
<jsp:include page="/include/footer.jsp"></jsp:include>
<style>
    .gswj{
        width: 30px;
        position: absolute;
        left: 50%;
        margin: -50px 0 0 -15px;
        color:#fff;
    }
    .gswj img{width: 30px;height: 42px;}
</style>
<div class="gswj">

    <script id='ebsgovicon' src='https://cert.ebs.gov.cn/govicon.js?id=E1A1ECE1-34EF-44C9-ABE9-CA2AA72D0F19&width=100&height=137&type=1' type='text/javascript' charset='utf-8'></script>

</div>

<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
<script type="text/javascript">
$(function() {
    if('${session.user}' != ''){
        if (('${request.idNo}' == null || '${request.idNo}' == '') && '${session.hhnflag}' == '') {
            //$("#wenxts").css("display", "block");
            //	$.post("ajaxHomeFlag.do", param, function(data) {
            //	});
            //	$('.bg').fadeIn(200);
            //	$('.tc_wenxts').fadeIn(400);
        }
    }


    $("#youhui").click(function() {
        window.location.href="frontNewsDetails.do?id=555";
    });

    $("#toInfor").click(function() {
        window.location.href="owerInformationInit.do";
        $("#wenxts").css("display", "none");
        $('.bg').fadeOut(800);
        $('.content').fadeOut(800);
    });
    $("#toNone").click(function() {
        $("#wenxts").css("display", "none");
        $('.bg').fadeOut(800);
        $('.content').fadeOut(800);
    });
    $("#closes").click(function() {
        $("#wenxts").css("display", "none");
        $('.bg').fadeOut(800);
        $('.content').fadeOut(800);
    });

    dqzt(0);
    hhnCookie();//获取cookie用户名

    var tim;
    $("#scrollleft ul").css("width", $("#scrollleft li").length * 125);
    function scrooatu() {
        clearTimeout(tim);
        $("#scrollleft").animate({
            scrollLeft : $("#scrollleft").scrollLeft() + 122
        }, "slow", function() {
            $("#scrollleft").animate({
                scrollLeft : 0
            }, 0);
            $("#scrollleft li:first").appendTo($("#scrollleft ul"));
        });

        tim = setTimeout(scrooatu, 3000);
    }
    scrooatu();
    $("#code").bind('keyup', function(event) {
        if (event.keyCode == "13") {
            login();
        }
    });
    $("#scrollleft").hover(function() {
        clearTimeout(tim);
    }, function() {
        setTimeout(scrooatu, 1000);
    });

});

function hhnCookie() {
    var CookieStr = document.cookie;
    if (CookieStr == undefined || CookieStr.length <= 0 || CookieStr.indexOf("user=") < 0){
        return;
    }else if( CookieStr.indexOf("@") > 0){
        var username;//cookie用户名
        var start = CookieStr.indexOf("user=") + "user=".length+1;
        var end = CookieStr.indexOf(";", start)-1;
        if (end < 0) {
            username = CookieStr.substring(start);
        } else {
            username = CookieStr.substring(start, end);
        }
        $("#email").val(username);
    }else{
        var username;//cookie用户名
        var start = CookieStr.indexOf("user=") + "user=".length;
        var end = CookieStr.indexOf(";", start);
        if (end < 0) {
            username = CookieStr.substring(start);
        } else {
            username = CookieStr.substring(start, end);
        }
        $("#email").val(username);
    }

}

//初始化
function switchCode() {
    var timenow = new Date();
    $("#codeNum").attr("src", "/admin/imageCode.do?pageId=userlogin&d=" + timenow);
};
$(document).ready(function() {
    $("#email").focus(function() {
        $("#email").val("");
    });
    $("#code").focus(function() {
        $("#code").val("");
    });

});
function login() {
    /*$(this).attr('disabled', true);
     $('#btn_login').attr('value', '登录中...');
     //		$('#login_none').css('display','block');
     var param = {};
     param["paramMap.pageId"] = "userlogin";
     param["paramMap.email"] = $("#email").val();
     param["paramMap.password"] = $("#password").val();
     param["paramMap.code"] = $("#code").val();
     param["paramMap.afterLoginUrl"] = "${afterLoginUrl}";
     var addCookie ="0";
     if($("#addCookie").attr("checked")=="checked")
     addCookie="1";
     param["paramMap.addCookie"] = addCookie;

     $.post("logining.do", param, function(data) {
     data = data.msg;
     if (data == 1) {
     window.location.href = 'index.jsp';
     } else if (data == 2) {
     $('#btn_login').attr('value', '登录');
     alert("验证码错误!");
     switchCode();
     $("#btn_login").attr('disabled', false);
     } else if (data == 3) {
     $('#btn_login').attr('value', '登录');
     alert("用户名或密码错误!");
     switchCode();
     $("#btn_login").attr('disabled', false);
     } else if (data == 4) {
     $('#btn_login').attr('value', '登录');
     $("#s_email").attr("class", "formtips onError");
     alert("该用户已被禁用!");
     switchCode();
     $("#btn_login").attr('disabled', false);
     }else if (data == 200) {
     $("#s_email").attr("class", "formtips onError");
     switchCode();
     alert("该用户已被禁用!");
     $("#btn_login").attr('disabled', false);
     }
     });*/

};
function checkTime(num) {
    if (num == 0) {
        $('#touzi_now').attr('class', 'cur');
        $('#touzi_year').attr('class', '');
        $('#touzi_quarter').attr('class', '');
        $('#touzi_month').attr('class', '');
        $('#touzi_week').attr('class', '');
    } else if (num == 1) {
        $('#touzi_now').attr('class', '');
        $('#touzi_year').attr('class', 'cur');
        $('#touzi_quarter').attr('class', '');
        $('#touzi_month').attr('class', '');
        $('#touzi_week').attr('class', '');
    } else if (num == 2) {
        $('#touzi_now').attr('class', '');
        $('#touzi_year').attr('class', '');
        $('#touzi_quarter').attr('class', 'cur');
        $('#touzi_month').attr('class', '');
        $('#touzi_week').attr('class', '');
    } else if (num == 3) {
        $('#touzi_now').attr('class', '');
        $('#touzi_year').attr('class', '');
        $('#touzi_quarter').attr('class', '');
        $('#touzi_month').attr('class', 'cur');
        $('#touzi_week').attr('class', '');
    } else if (num == 4) {
        $('#touzi_now').attr('class', '');
        $('#touzi_year').attr('class', '');
        $('#touzi_quarter').attr('class', '');
        $('#touzi_month').attr('class', '');
        $('#touzi_week').attr('class', 'cur');
    }
    var param = {};
    param["paramMap.number"] = num;
    $.post("investRank.do", param, function(data) {
        $("#touzib").html(data);
    });
}
function checkTou(id, type) {
    var param = {};
    param["id"] = id;
    $.shovePost('financeInvestInit.do', param, function(data) {
        var callBack = data.msg;
        if (callBack != undefined) {
            alert(callBack);
        } else {
            if (type == 2) {
                var url = "subscribeinit.do?borrowid=" + id;
                $.jBox("iframe:" + url, {
                    title : "我要购买",
                    width : 450,
                    height : 450,
                    buttons : {}
                });
            } else {
                window.location.href = 'financeInvestInit.do?id=' + id;
            }
        }
    });
}
function closeMthod() {
    window.jBox.close();
    window.location.reload();
}
</script>
<script type="text/javascript">
    function reggg(){
        if(${session.user!=null}){
            alert("你已是登录用户");
        }else{
            window.location.href="/account/reg.do";
        }
    }
    $(function() {
        hhn(0);

        var self = $("#scroll ul")
        var sd = null;
        var leng = parseInt($("#scroll ul").css("width", self.find("li").length * 459).css("width"));
        $("#scroll ul").css("width", self.find("li").length * 459);
        function scrollatuo() {
            clearTimeout(sd);
            if ($("#scroll").scrollLeft() >= leng - 490) {

                $("#scroll").animate({
                    scrollLeft : 0
                }, "slow");
                $(".control a").removeClass("cur")
                $(".control a:first").addClass("cur")
                sd = setTimeout(scrollatuo, 3000);
            } else {

                $("#scroll").animate({
                    scrollLeft : $("#scroll").scrollLeft() + 459
                }, "slow", function() {

                    var insd = parseInt($("#scroll").scrollLeft() / 459);
                    $(".control a").removeClass("cur")
                    $(".control a").eq(insd).addClass("cur")
                });
            }
            sd = setTimeout(scrollatuo, 3000);

        }
        sd = setTimeout(scrollatuo, 1000);
        //控制区
        $(".control a").hover(function() {
            var sf = $(".control a").index(this);
            $("#scroll").animate({
                scrollLeft : 459 * sf
            }, "slow");
            clearTimeout(sd);
            $(".control a").not(this).removeClass("cur")
            $(this).addClass("cur");
        }, function() {
            sd = setTimeout(scrollatuo, 3000);
        });
        //选项卡
        $("#second-tal li").click(function() {
            var sd = $("#second-tal li").index(this);

            $(".job-list").hide();
            $(".job-list").eq(sd).show();
            $("#second-tal li").removeClass("cur");
            $(this).addClass("cur");
        })

        ImgScroll(".mjd_banner", "#mjd_banner_ul", 1000, ".contrl", "a", "sydp-a5", "sydp-a6", "li", 1000, 900, 4000);
    })
</script>
<div class="tc_wenxts" id="wenxts" style="display: none;">
    <h3><span>温馨提示</span><strong id="closes">X</strong></h3>
    <h2>欢迎您成为合和年在线会员！</h2>
    <p>
        <input type="button" value="完善个人资料" id="toInfor"/>
    </p>
    <p>
        <input type="button" value="先逛逛"  id="toNone"/>
    </p>
    <br />
    <p><span>注：成为网站投资人或者借款人必须注册汇付天下账号。</span></p>
</div>
<div  class="tc_wenxts" id="yjtbDiv" style="display: none;">
    <h3><span>温馨提示</span><strong id="closes1" onclick="qxyjtb();">X</strong></h3>
    <div style=" font-size:12px; padding:30px"><font style=" font-size:12px; color:#999; line-height:16px; "><strong>一键投标说明：</strong>为了方便投资者，省去各项繁琐操作，用户可通过点击一键投标进行投资，如需要使用此功能，请先在<a href="automaticBidInit.do" style=" color:#F90">【理财管理】-【自动投标】</a>页面进行设置。一键投标会根据您的设置，将您的资金一次性投入到对应的标的中。</font></div>
    <p>
        <input type="button" value="进行一键投标" onclick="qryjtb();"/>
    </p>
    <p>
        <input type="button" value="取消" onclick="qxyjtb();" />
    </p>
    <br />
    <p><span></span></p>
</div>
<div class="bg"></div>
<script>
    function yjtb(){
        var userId=$("#userId").val();
        if(userId){
            $("#yjtbDiv").css("display", "block");
            $('.bg').fadeIn(200);
            $('#yjtbDiv').fadeIn(400);
        }else{
            window.location.href="/login1.jsp";
        }
    }
    function qryjtb(){
        qxyjtb();
        $.post("onekeybid.do",function(data){
            if(data=="1"){
                alert("一键投标成功！");
            }else{
                //alert("请先开启自动投标");
                window.location.href="automaticBidInit.do";
            }
        })
    }
    function qxyjtb(){
        $("#yjtbDiv").css("display", "none");
        $('.bg').fadeOut(800);
    }
</script>
<%--<style>
	.f_dialog_bg{
position: absolute;
top: 0;
z-index: 999;
width: 100%;
height: 100%;
background: #000;
opacity: .5;
filter: alpha(opacity=50);
	}
	.dialog{
position: absolute;
top: 50%;
left: 50%;
z-index: 1000;
background: rgba(255, 255, 255, 0);
width: 900px;
height: 400px;
margin: -207px 0 0 -457px;
border-radius: 37px;
border: 7px solid #E50B0D;
box-shadow: 0 0 20px #000;
	}
	.close{
width: 45px;
height: 45px;
display: block;
background: url(img/close.png) no-repeat;
background-size: 45px;
position: absolute;
z-index: 100000000;
top: 0;
right:0;
	}
</style>
<div id="nian" >
	<div class="dialog">
		<a href="/hhn_web/termFinance.do"><img src="img/nian.png" /></a>
	
	</div>
		<span class="close"></span>
	<div class="f_dialog_bg"></div>
	<script>
	$(function(){
		var closeNian = setTimeout(function(){
			$("#nian").remove();
		},10000);
		
		$("#nian .close").click(function(){
				$("#nian").remove();
				 clearTimeout(closeNian)
		})
	})
	</script>
	
</div>--%>
</body>
</html>
