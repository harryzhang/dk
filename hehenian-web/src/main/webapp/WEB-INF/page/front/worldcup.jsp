<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<LINK id="skin" href="worldcup/jbox.css" rel="stylesheet"><LINK href="css/hhncss.css" rel="stylesheet" type="text/css">
<LINK href="worldcup/Site2.css" rel="stylesheet" type="text/css">
<LINK href="worldcup/lytebox.css" rel="stylesheet" type="text/css">
<!--IE6透明判断--><!--[if IE 6]>
<script src="../css/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
DD_belatedPNG.fix('.flash_bar,#tit_fc1,#tit_fc2,#tit_fc3,#tit_fc4,#flashLine,#right_tel,#right_qq,#right_tip,.login_all,.wytz_center_onez,.wytz_center_one,img');
</script>
<![endif]-->
<SCRIPT src="worldcup/add_all.js" type="text/javascript"></SCRIPT>
<SCRIPT src="worldcup/MSClass.js" type="text/javascript"></SCRIPT>
<SCRIPT src="worldcup/jquery-1.7.1.min.js" type="text/javascript"></SCRIPT>
<SCRIPT src="worldcup/jquery.shove-1.0.js" type="text/javascript"></SCRIPT>
<SCRIPT src="worldcup/lytebox.js" type="text/javascript"></SCRIPT>
<style>

#win2 {
 width:387px;height:414px; display:block;  position: absolute;
 top:141px; left:50%;margin-left:200px; 
}
#main_promo{height:380px; width:1170px;; margin-left:-585px; position:relative;  top:0;  left:50%; overflow:hidden; z-index:0; }
.promoWD{width:1170px; margin:0 auto; position:relative; }
.slide{display:block; float:left;  width:1170px;}
#dots{height:13px; position:absolute; bottom:13px; left:585px; z-index:99;}
#dots li{ width:23px; height:13px; float:left; }
#dots li a{ width:10px; display:block; height:10px; background-color:#7f0705; border:1px solid #FFFFFF;}
#dots li.act a{background-color:#000000;}
</style>
</HEAD>
<BODY>
<jsp:include page="/include/top.jsp"></jsp:include>
<script src="worldcup/wb.js" type="text/javascript" charset="utf-8"></script>
<div style=" width:100%; height:379px;">
  <div class="promoWD">
       <div id="main_promo">
       <div id="slides">
          <div class="slide"><a><img src="worldcup/88.jpg" /></a></div>       
          <div class="slide"><a><img src="worldcup/99.jpg" ></a></div> 
    </div>
</div>
<div id="dots">
            <ul>
              <li class="menuItem"><a href="javascript:;"></a></li>
              <li class="menuItem"><a href="javascript:;"></a></li>
            
            </ul>
          </div>
  </div>

<script>
$(document).ready(function(){
	var totWidth=0;
	var positions = new Array();
	$('#slides .slide').each(function(i){
		positions[i]= totWidth;
		totWidth += $(this).width();
		if(!$(this).width())
		{
			alert("Please, fill in width & height for all your images!");
			return false;
		}
	});
	$('#slides').width(totWidth);
	$('#dots ul li a').mouseover(function(e,keepScroll){
			$('li.menuItem').removeClass('act').addClass('inact');
			$(this).parent().addClass('act');
			
			var pos = $(this).parent().prevAll('.menuItem').length;
			
			$('#slides').stop().animate({marginLeft:-positions[pos]+'px'},450);
			e.preventDefault();
			if(!keepScroll) clearInterval(itvl);
	});
	$('#dots ul li.menuItem:first').addClass('act').siblings().addClass('inact');
	var current=1;
	function autoAdvance()
	{
		if(current==-1) return false;
		$('#dots ul li a').eq(current%$('#dots ul li a').length).trigger('mouseover',[true]);
		current++;
	}
	var changeEvery = 7;
	
	var itvl = setInterval(function(){autoAdvance()},changeEvery*8000);//设置自动播放时间，越大越慢
});
</script>
  <div id="win2" ><img src="worldcup/jb.png" width="387" height="414"  alt=""/></div>
</div>
<div style=" width:1170px; margin:0px auto; height:195px;background:url(worldcup/tpbg.gif) repeat-x">
  <DIV class="center" >
    <div style=" width:788px; height:195px; float:left">
      <div style=" line-height:50px; font-size:18px; font-weight:bolder; overflow:hidden">
        <div style=" float:left"><img src="worldcup/time.png" width="39" height="38"  alt="" style=" float:left; margin-right:10px;"/>06/14  赛程表 小组赛</div>
        <div style=" float:right">本日活动奖励>></div>
      </div>
     <style>
		.sc {}
		.sc ul { text-align:center; }
		.sc ul li { float:left}
		.sc ul li.guoqi { width:63px; }
		.sc ul li.vs { width:39px; font-size:14px}
	  </style>
      <div style=" padding:10px 0px 20px 0px;; overflow:hidden" class="sc"> 
        <!--1-->
        <form action="jc.do" method="post" id="f1">
        <ul style=" width:185px; float:left">
          <li class="guoqi"><img src="worldcup/1002.png" width="48" height="48"  alt=""/></li>
          <li class="vs">vs<br />
          <font style=" font-size:12px;">00:00</font></li>
          <li class="guoqi"><img src="worldcup/1003.png" width="48" height="48"  alt=""/></li>
          <li class="guoqi" style="font-size:12px;">墨西哥</li>
          <li class="vs" style=" line-height:15px;">平</li>
          <li class="guoqi" style="font-size:12px;">喀麦隆</li>
          <li class="guoqi" style="font-size:12px;margin-top:10px;">
            <input type="radio" name="winTeam1" checked value="1"/>
          </li>
          <li class="vs" style=" line-height:10px;margin-top:10px;">
          <input type="radio" name="winTeam1"  value="2"></li>
          <li class="guoqi" style="font-size:12px;margin-top:10px;">
            <input type="radio" name="winTeam1" value="3"/>
          </li>
           <input type="hidden" name="gameId1" value="2"/>
        </ul>
        <ul style=" height:90px; border-right:1px solid #ccc; float:left; margin-right:15px;">
        </ul>
		
		
		<!--  2 -->
		<ul style=" width:185px; float:left">
          <li class="guoqi"><img src="worldcup/1004.png" width="48" height="48"  alt=""/></li>
          <li class="vs">vs<br />
          <font style=" font-size:12px;">03:00</font></li>
          <li class="guoqi"><img src="worldcup/1005.png" width="48" height="48"  alt=""/></li>
          <li class="guoqi" style="font-size:12px;">西班牙</li>
          <li class="vs" style=" line-height:15px;">平</li>
          <li class="guoqi" style="font-size:12px;">荷兰 </li>
          <li class="guoqi" style="font-size:12px;margin-top:10px;">
            <input type="radio" name="winTeam2" checked value="1"/>
          </li>
          <li class="vs" style=" line-height:10px;margin-top:10px;">
          <input type="radio" name="winTeam2"  value="2"></li>
          <li class="guoqi" style="font-size:12px;margin-top:10px;">
            <input type="radio" name="winTeam2" value="3"/>
          </li>
           <input type="hidden" name="gameId2" value="3"/>
        </ul>
        <ul style=" height:90px; border-right:1px solid #ccc; float:left; margin-right:15px;">
        </ul>
		
		<!--  3 -->
		<ul style=" width:185px; float:left">
          <li class="guoqi"><img src="worldcup/1006.png" width="48" height="48"  alt=""/></li>
          <li class="vs">vs<br />
          <font style=" font-size:12px;">06:00</font></li>
          <li class="guoqi"><img src="worldcup/1007.png" width="48" height="48"  alt=""/></li>
          <li class="guoqi" style="font-size:12px;">智利</li>
          <li class="vs" style=" line-height:15px;">平</li>
          <li class="guoqi" style="font-size:12px;">澳大利亚</li>
          <li class="guoqi" style="font-size:12px;margin-top:10px;">
            <input type="radio" name="winTeam3" checked value="1"/>
          </li>
          <li class="vs" style=" line-height:10px;margin-top:10px;">
          <input type="radio" name="winTeam3"  value="2"></li>
          <li class="guoqi" style="font-size:12px;margin-top:10px;">
            <input type="radio" name="winTeam3" value="3"/>
          </li>
           <input type="hidden" name="gameId3" value="4"/>
        </ul>
        <ul style=" height:90px; border-right:1px solid #ccc; float:left; margin-right:15px;">
        </ul>
		
		
		
		
		
		
		
       
        </form>
      </div>
    </div>
    <div style=" width:382px; height:195px; background:url(worldcup/tpbbj.gif); float:left">
      <div style=" height:137px; float:left; margin-left:75px; padding-top:58px;">
        <div>当日投资额返现</div>
        <div style=" font-size: 40px; font-weight:bolder">0.5%</div>
      </div>
      <div style=" height:137px; float:left; margin-left:50px; padding-top:58px;">
        <div style=" color:#FFF">积分奖励（每场）</div>
        <div style=" font-size:40px; font-weight:bolder;color:#FFF">x2</div>
      </div>
    </div>
  </DIV>
</div>

<!---->
<DIV class="center" style=" height:50px; background:url(worldcup/tpdw.png) no-repeat; text-align:center;">
<a onclick="jc();">
<img src="worldcup/tj.png" width="252" height="53"  alt=""/> 
</a> 
</DIV>

<!---->
<DIV class="center" style=" overflow:hidden; margin-top:20px; margin-bottom:30px;">
  <div style=" float:left; margin-right:20px; overflow:hidden; width:770px;" >
    <div style=" overflow:hidden; background:#f7f7f7 url(worldcup/wctimer.png) no-repeat;">
      <div style=" float:left; padding-left:70px; line-height:29px; color:#169249; font-size:18px;">投资列表</div>
    </div>
    
    
    
    <!--                  开始-->
     <s:iterator value="#request.mapList" var="finance">
    <div style="margin-top:20px; border:10px solid #f7f7f7">
      <DIV class="center_one_left_db_a"><!--图-->
        <DIV style="width: 30px; height: 137px; float: left;"></DIV>
        <!--内容-->
        
        <DIV class="shouyebiao">
          <UL>
            <LI style="font-size: 16px;"><SPAN><A href="/financeDetail.do?id=${finance.id}" 
   style="color:#169249">${finance.borrowTitle}</A></SPAN><BR>
              <FONT color="#999999" 
  size="-2">编号：${finance.number }</FONT></LI>
            <LI style="height: 26px;"><IMG style="float: left;" src="worldcup/1.gif">借款金额： 
              <s:property value="#finance.borrowAmount" default="0" />元 </LI>
            <LI style="height: 26px;"><IMG style="float: left;" src="worldcup/2.gif">借款期限： 
              <s:property value="#finance.deadline" default="0" />个月</LI>
            <LI style="height: 26px;"><IMG style="float: left;" src="worldcup/3.gif">年利率： <FONT color="#ff6600" size="3">12.00              %&nbsp; </FONT> </LI>
          </UL>
        </DIV>
        <DIV 
style="width: 345px; padding-top: 30px; padding-right: 100px; float: right;"><!--投标按钮margin-right:30px;-->
          
          <DIV class="nei_button" style="width: 345px; text-align: right;">
           <s:if test="%{#finance.borrowStatus == 1}">
            <input type="button" value="初审中" style="background: #ccc;cursor:default;" />
          </s:if>
          <s:elseif test="%{#finance.borrowStatus == 2}">
            <div class="bottm"> <input type="button" onclick="window.location.href='financeDetail.do?id=${finance.id}'" value="立即投标" /> </div>
          </s:elseif>
          <s:elseif test="%{#finance.borrowStatus == 3}">
            <input type="button" value="满标" style="background: url(/images/pic_all.png) -174px -356px no-repeat ;cursor:default;" />
          </s:elseif>
          <s:elseif test="%{#finance.borrowStatus == 4}">
            <s:if test="%{#finance.borrowShow == 2}">
              <input type="button" value="回购中" style="background: #ccc;cursor:default;" />
            </s:if>
            <s:else>
              <input type="button" onclick="window.location.href='financeDetail.do?id=${finance.id}'" value="还款中" style="background: url(/images/pic_all.png) -174px -300px no-repeat" />
            </s:else>
          </s:elseif>
          <s:elseif test="%{#finance.borrowStatus == 5}">
            <input type="button" value="已还完" style="background: #ccc;cursor:default;" />
          </s:elseif>
          <s:elseif test="%{#finance.borrowStatus == 7}">
            <input type="button" value="复审中" style="background: #ccc;cursor:default;" />
          </s:elseif>
          <s:else>
            <input type="button" value="流标" style="background: #ccc;cursor:default;" />
          </s:else>
          </DIV>
          <DIV style="width: 327px; overflow: hidden; padding-left: 20px; float: right;">
            <DIV style="text-align: left; line-height: 28px; float: left;">借款进度：</DIV>
            <DIV class="index_jd">
              <DIV style='background: url("images/pic_all.png") no-repeat 0px -67px; width: <s:property value="#finance.schedules" default="0"/>%; height: 12px; line-height: 0px; font-size: 0px;'></DIV>
            </DIV>
            <DIV style="text-align: right; padding-top: 7px;"><s:property value="#finance.schedules" default="0"/>%</DIV>
          </DIV>
          <DIV style="width: 345px; text-align: right; line-height: 32px;">已成功借款：<FONT 
color="#ff6600"><s:property value="#finance.hasInvestAmount" default="---" /></FONT>元</DIV>
          <DIV style="width: 345px; text-align: right;">剩余借款金额：<FONT 
color="#006600"><s:property value="#finance.investNum" default="---" /></FONT>元</DIV>
        </DIV>
        <!----> </DIV>
    </div>
      </s:iterator>
    
   
  </div>
  
  <!---->
   <div style=" height:120px; background:#FFF"><img src="worldcup/ad.png" width="380" height="100"  alt=""/></div>

    <div style="background:#FFF; margin-bottom:20px; width:380px; float:right">    
    <s:if test="#session.user!=null">
   <div style=" background:#0d8136 url(worldcup/green_bg.jpg) no-repeat; text-align:center; padding:8px;font-size:22px;color:#fff;">我的积分：
${score }+${investScore}×?</div>
    </s:if>
    <s:elseif test="#session.user==null" ><div style=" background:#0d8136 url(worldcup/green_bg.jpg) no-repeat; text-align:center; padding:8px;font-size:22px;">
   <a href="/login.jsp" style="color:#fff;">登录查看积分</a> 
</div>
    </s:elseif>
    </div>
 
 
  <div style=" overflow:hidden; width:380px; float:left; background:#0d8136 url(worldcup/green_bg.jpg) no-repeat ;margin-bottom:20px;">
   
    <div style=" line-height:44px; padding-left:20px; color:#FFF; font-size:16px;">球王排行榜(每天更新一次)<font style=" font-size:14px; padding-left:10px; color:#E0E0E0">冲击冠军，赢取万元大奖</font></div>
    <style>
		.even { background:#059593;}
		.even span { display:inline-block; zoom:1; overflow:hidden; height:30px; line-height:30px; text-align:center; color:#fff;}
		.td_1 { width:80px;}
		.td_2 { width:150px;}
		.td_3 { width:140px;}
		.paim { background:#f7f7f7}
		.paim span{ display:inline-block; zoom:1; overflow:hidden; height:30px; line-height:30px; text-align:center; color:#444;}
		.top1 { background:#f30; border:1px solid #ea8d07; color:#fff; height:14px; line-height:14px; width:14px; display:inline-block; zoom:1; text-align:center; border-radius:2px; margin-top:10px;}
		.top2 { background:#f60; border:1px solid #ea8d07; color:#fff; height:14px; line-height:14px; width:14px; display:inline-block; zoom:1; text-align:center; border-radius:2px; margin-top:10px;}
		.top3 { background:#f90; border:1px solid #ea8d07; color:#fff; height:14px; line-height:14px; width:14px; display:inline-block; zoom:1; text-align:center; border-radius:2px; margin-top:10px;}
	  </style>
    <div style=" background:#f7f7f7; overflow:hidden">
      <div class="even"> <span class="td_1">排名</span> <span class="td_2">用户</span> <span class="td_3">积分</span> </div>
      <ul class="paim">
      <s:iterator value="#request.tops" var="top" status="status">
        <li style="${status.count%2==0?'':'background:#fff'} ">
        <span class="td_1"><b class="top${status.count} ">${status.count}</b></span> 
        <span class="td_2">${top.username }</span> 
        <span class="td_3">${top.score }</span>
        </li>
        </s:iterator>
         </ul>
   
    </div>
  </div>
  <div><a href="http://wx.wsq.qq.com/253462128" target="_blank"><img src="images/huafei.jpg"  style=" border:0px;"/></a></div>
</div>
<div style=" width:100%;  background:#f8f8f8;margin-bottom:40px;">
<div style=" width:1170px; margin:0px auto; background:#f8f8f8; padding:50px 0px;">
<div style=" width:1170px; margin:0px auto; background:#000">
  <div style=" width:1170px; margin:0px auto; overflow:hidden">
    <div style=" float:left; margin-right:30px;"><img src="worldcup/logo.png" width="268" height="141" alt=""></div>
    <div style=" float:left; line-height:28px; width:869px;">
      <div><strong>活动细则</strong></div>
      <div style=" padding:10px 0px;"><strong>银球奖规则：</strong>根据每天比赛场次，每个用户每场可以猜一次，只要猜中当天的任意一场比赛，可以获得相应现金奖励；<br>

      <font style=" padding-left:90px;">1、小组赛时，只要猜中当天的任意一场比赛，可获得本人当天投资额的0.5%的现金奖励；</font><br>
<font style=" padding-left:90px;">2、1/8、1/4、1/2、三四名赛时，只要猜中当天的任意一场比赛，可获得本人当天投资额的0.6%的现金奖励；</font><br>
<font style=" padding-left:90px;">3、决赛时，只要猜中冠军，可获得本人当天投资额的1%的现金奖励；</font><br>
<font style=" padding-left:90px;">4、奖励在活动结束后5个工作日内发放到合和年在线帐户里。</font></div>
      <div><strong>金球奖规则：</strong>1、投资获得投资积分，投资积分=投资额/100；<br>
<font style=" padding-left:83px;">2、猜球积分：猜球积分可累计</font><br>
<font style=" padding-left:100px;">A、小组赛时，猜中1场球，当天投资积分*1，猜中2场球，当天投资积分*2，以此类推</font><br>
<font style=" padding-left:100px;">B 、1/8赛时，猜中1场球，当天投资积分*2，猜中2场球，当天投资积分*4，以此类推</font><br>
<font style=" padding-left:100px;">C 、1/4赛时，猜中1场球，当天投资积分*3，猜中2场球，当天投资积分*6，以此类推</font><br>
<font style=" padding-left:100px;">D、1/2和三四名比赛时，猜中1场球，当天投资积分*4，猜中2场球，当天投资积分*8</font><br>
<font style=" padding-left:100px;">E、决赛时，猜中冠军，当天投资积分*5</font><br>
<font style=" padding-left:83px;">3、总积分=投资积分<font style=" font-family:Arial, '微软雅黑', '黑体'; font-size:18px;">&nbsp;+&nbsp;</font>猜球积分；</font><br>
<font style=" padding-left:83px;">4、2014年7月13号世界杯决赛后，总积分最高的投资者获得金球奖；</font><br>
<font style=" padding-left:83px;">5、如有相同积分情况出现，按活动期间投资总金额多少排序。</font><br></div>
      <div style=" padding:10px 0px; font-size:11px; color:#999">活动奖励均在世界杯结束后5个工作日内发送到用户的投资账户，用户可自行提现或继续投资。</div>
      <div style=" padding:0px 0px 10px 0px; font-size:11px; color:#999">本次活动最终解释权归合和年在线所有</div></div>
    </div>
  </div>
</div>


<div style=" width:100%; background:rgb(13, 24, 30); ">
<LINK href="worldcup/sports.lUxzeWcW7D4G.1.css" rel="stylesheet" 
type="text/css">

<LINK href="worldcup/wc_data.cnfgJBNQ22hP.2.css" rel="stylesheet" 
type="text/css">
<LINK href="worldcup/sch.732475.css" rel="stylesheet" 
type="text/css">
<input type="hidden" value="${myJc}" id="myJc"/>
<div style=" width:1170px; background:rgb(13, 24, 30); margin:0px auto;">

<DIV class="pwrap sch_pwrap sch_calendar_pwrap">
  <DIV class="pbody">
    <DIV class="sect_calendar sect">
      <DIV class="mod_sch_calendar mod js_floating">
        <DIV class="wc_sch sch_red">
          <DIV class="wctable_sch_title">
            <H2><I class="zh">小组赛</I></H2>
          </DIV>
          <TABLE class="wctable wctable_sch" id="xzs">
            <COLGROUP>
            <COL class="tb_block">
            <COL class="tb_col1">
            <COL class="tb_col2">
            <COL class="tb_col3">
            <COL class="tb_col4">
            <COL class="tb_col5">
            <COL class="tb_col6">
            <COL class="tb_col7">
            <THEAD>
              <TR>
                <TH></TH>
                <TH></TH>
                <TH>时间</TH>
                <TH>对阵</TH>
                <TH>城市</TH>
                <TH>我的竞猜结果</TH>
              </TR>
            </THEAD>
            <TBODY>
              <TR class="tr01">
                <TD class="tb_block"></TD>
                <TD class="tb_col1">1场</TD>
                <TD>06月13日 星期五 04:00</TD>
                <TD>巴西 <SPAN class="match_score">VS </SPAN>克罗地亚</TD>
                <TD>圣保罗</TD>
                <TD><s:if test="%{#request.myJc.get('0').get('winTeam') == 1}">巴西胜</s:if>
                <s:elseif test="%{#request.myJc == 3}">巴西负</s:elseif>
                <s:elseif test="%{#request.myJc == 2}">平</s:elseif>
                </TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">2场</TD>
                <TD>06月14日 星期六 00:00</TD>
                <TD>墨西哥<SPAN class="match_score">VS </SPAN>喀麦隆</TD>
                <TD>纳塔尔</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">3场</TD>
                <TD>06月14日 星期六 03:00</TD>
                <TD>西班牙<SPAN class="match_score">VS </SPAN> 荷兰</TD>
                <TD>萨尔瓦多</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">4场</TD>
                <TD>06月14日 星期六 06:00</TD>
                <TD>智利 <SPAN class="match_score">VS </SPAN>澳大利亚</TD>
                <TD>库亚巴</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">5场</TD>
                <TD>06月15日 星期日 00:00</TD>
                <TD>哥伦比亚 <SPAN class="match_score">VS </SPAN> 希腊</TD>
                <TD>贝罗奥里藏特</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">7场</TD>
                <TD>06月15日 星期日 03:00</TD>
                <TD>乌拉圭<SPAN class="match_score">VS </SPAN> 哥斯达黎加</TD>
                <TD>福塔莱萨</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">8场</TD>
                <TD>06月15日 星期日 06:00</TD>
                <TD>英格兰<SPAN class="match_score">VS </SPAN> 意大利</TD>
                <TD>玛瑙斯</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">6场</TD>
                <TD>06月15日 星期日 09:00</TD>
                <TD>科特迪瓦<SPAN class="match_score">VS </SPAN> 日本</TD>
                <TD>累西腓</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">9场</TD>
                <TD>06月16日 星期一 00:00</TD>
                <TD>瑞士<SPAN class="match_score">VS </SPAN> 厄瓜多尔</TD>
                <TD>巴西利亚</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">10场</TD>
                <TD>06月16日 星期一 03:00</TD>
                <TD>法国<SPAN class="match_score">VS </SPAN>洪都拉斯</TD>
                <TD>阿雷格里港</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">11场</TD>
                <TD>06月16日 星期一 06:00</TD>
                <TD>阿根廷 <SPAN class="match_score">VS </SPAN>波黑</TD>
                <TD>里约热内卢</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">13场</TD>
                <TD>06月17日 星期二 00:00</TD>
                <TD>德国 <SPAN class="match_score">VS </SPAN> 葡萄牙</TD>
                <TD>萨尔瓦多</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">12场</TD>
                <TD>06月17日 星期二 03:00</TD>
                <TD>伊朗 <SPAN class="match_score">VS </SPAN> 尼日利亚</TD>
                <TD>库亚巴</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">14场</TD>
                <TD>06月17日 星期二 06:00</TD>
                <TD>加纳 <SPAN class="match_score">VS </SPAN> 美国</TD>
                <TD>纳塔尔</TD>
                <TD></TD>
              </TR>
              <TR id="cc15">
                <TD class="tb_block"></TD>
                <TD class="tb_col1">15场</TD>
                <TD>06月18日 星期三 00:00</TD>
                <TD>比利时<SPAN class="match_score">VS </SPAN> 阿尔及利亚</TD>
                <TD>贝罗奥里藏特</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">17场</TD>
                <TD>06月18日 星期三 03:00</TD>
                <TD>巴西<SPAN class="match_score">VS </SPAN>墨西哥</TD>
                <TD>福塔莱萨</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">16场</TD>
                <TD>06月18日 星期三 06:00</TD>
                <TD>俄罗斯<SPAN class="match_score">VS </SPAN>韩国</TD>
                <TD>库亚巴</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">20场</TD>
                <TD>06月19日 星期四 00:00</TD>
                <TD>澳大利亚<SPAN class="match_score">VS </SPAN> 荷兰</TD>
                <TD>阿雷格里港</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">19场</TD>
                <TD>06月19日 星期四 03:00</TD>
                <TD>西班牙<SPAN class="match_score">VS </SPAN> 智利</TD>
                <TD>里约热内卢</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">18场</TD>
                <TD>06月19日 星期四 06:00</TD>
                <TD>喀麦隆<SPAN class="match_score">VS </SPAN>克罗地亚</TD>
                <TD>玛瑙斯</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">21场</TD>
                <TD>06月20日 星期五 00:00</TD>
                <TD>哥伦比亚<SPAN class="match_score">VS </SPAN> 科特迪瓦</TD>
                <TD>巴西利亚</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">23场</TD>
                <TD>06月20日 星期五 03:00</TD>
                <TD>乌拉圭<SPAN class="match_score">VS </SPAN> 英格兰</TD>
                <TD>圣保罗</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">22场</TD>
                <TD>06月20日 星期五 06:00</TD>
                <TD>日本<SPAN class="match_score">VS </SPAN>希腊</TD>
                <TD>纳塔尔</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">24场</TD>
                <TD>06月21日 星期六 00:00</TD>
                <TD>意大利<SPAN class="match_score">VS </SPAN> 哥斯达黎加</TD>
                <TD>累西腓</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">25场</TD>
                <TD>06月21日 星期六 03:00</TD>
                <TD>瑞士<SPAN class="match_score">VS </SPAN> 法国</TD>
                <TD>萨尔瓦多</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">26场</TD>
                <TD>06月21日 星期六 06:00</TD>
                <TD>洪都拉斯<SPAN class="match_score">VS </SPAN> 厄瓜多尔</TD>
                <TD>库亚巴</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">27场</TD>
                <TD>06月22日 星期日 00:00</TD>
                <TD>阿根廷<SPAN class="match_score">VS </SPAN>伊朗</TD>
                <TD>贝罗奥里藏特</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">29场</TD>
                <TD>06月22日 星期日 03:00</TD>
                <TD>德国<SPAN class="match_score">VS </SPAN> 加纳</TD>
                <TD>福塔莱萨</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">28场</TD>
                <TD>06月22日 星期日 06:00</TD>
                <TD>尼日利亚<SPAN class="match_score">VS </SPAN> 波黑</TD>
                <TD>库亚巴</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">31场</TD>
                <TD>06月23日 星期一 00:00</TD>
                <TD>比利时<SPAN class="match_score">VS </SPAN> 俄罗斯</TD>
                <TD>里约热内卢</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">32场</TD>
                <TD>06月23日 星期一 03:00</TD>
                <TD>韩国<SPAN class="match_score">VS </SPAN>阿尔及利亚</TD>
                <TD>阿雷格里港</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">30场</TD>
                <TD>06月23日 星期一 06:00</TD>
                <TD>美国<SPAN class="match_score">VS </SPAN> 葡萄牙</TD>
                <TD>玛瑙斯</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">35场</TD>
                <TD>06月24日 星期二 00:00</TD>
                <TD>澳大利亚<SPAN class="match_score">VS </SPAN> 西班牙</TD>
                <TD>库亚巴</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">36场</TD>
                <TD>06月24日 星期二 00:00</TD>
                <TD>荷兰<SPAN class="match_score">VS </SPAN>智利</TD>
                <TD>圣保罗</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">33场</TD>
                <TD>06月24日 星期二 04:00</TD>
                <TD>喀麦隆<SPAN class="match_score">VS </SPAN> 巴西</TD>
                <TD>巴西利亚</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">34场</TD>
                <TD>06月24日 星期二 04:00</TD>
                <TD>克罗地亚<SPAN class="match_score">VS </SPAN> 墨西哥</TD>
                <TD>累西腓</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">39场</TD>
                <TD>06月25日 星期三 00:00</TD>
                <TD>意大利<SPAN class="match_score">VS </SPAN>乌拉圭</TD>
                <TD>纳塔尔</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">40场</TD>
                <TD>06月25日 星期三 00:00</TD>
                <TD>哥斯达黎加<SPAN class="match_score">VS </SPAN> 英格兰</TD>
                <TD>贝罗奥里藏特</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">37场</TD>
                <TD>06月25日 星期三 04:00</TD>
                <TD>日本<SPAN class="match_score">VS </SPAN> 哥伦比亚</TD>
                <TD>库亚巴</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">38场</TD>
                <TD>06月25日 星期三 04:00</TD>
                <TD>希腊<SPAN class="match_score">VS </SPAN> 科特迪瓦</TD>
                <TD>福塔莱萨</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">43场</TD>
                <TD>06月26日 星期四 00:00</TD>
                <TD>尼日利亚<SPAN class="match_score">VS </SPAN> 阿根廷</TD>
                <TD>阿雷格里港</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">44场</TD>
                <TD>06月26日 星期四 00:00</TD>
                <TD>波黑<SPAN class="match_score">VS </SPAN> 伊朗</TD>
                <TD>萨尔瓦多</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">41场</TD>
                <TD>06月26日 星期四 04:00</TD>
                <TD>洪都拉斯<SPAN class="match_score">VS </SPAN> 瑞士</TD>
                <TD>玛瑙斯</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">42场</TD>
                <TD>06月26日 星期四 04:00</TD>
                <TD>厄瓜多尔<SPAN class="match_score">VS </SPAN> 法国</TD>
                <TD>里约热内卢</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">45场</TD>
                <TD>06月27日 星期五 00:00</TD>
                <TD>美国<SPAN class="match_score">VS </SPAN>德国</TD>
                <TD>累西腓</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">46场</TD>
                <TD>06月27日 星期五 00:00</TD>
                <TD>葡萄牙<SPAN class="match_score">VS </SPAN> 加纳</TD>
                <TD>巴西利亚</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">47场</TD>
                <TD>06月27日 星期五 04:00</TD>
                <TD>韩国<SPAN class="match_score">VS </SPAN> 比利时</TD>
                <TD>圣保罗</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">48场</TD>
                <TD>06月27日 星期五 04:00</TD>
                <TD>阿尔及利亚<SPAN class="match_score">VS </SPAN> 俄罗斯</TD>
                <TD>库亚巴</TD>
                <TD></TD>
              </TR>
            </TBODY>
          </TABLE>
        </DIV>
      </DIV>
      <DIV class="mod_sch_calendar mod js_floating">
        <DIV class="wc_sch sch_green">
          <DIV class="wctable_sch_title">
            <H2><I class="en">1/8</I>决赛</H2>
          </DIV>
          <TABLE class="wctable wctable_sch">
            <COLGROUP>
            <COL class="tb_block">
            <COL class="tb_col1">
            <COL class="tb_col2">
            <COL class="tb_col3">
            <COL class="tb_col4">
            <COL class="tb_col5">
            <COL class="tb_col6">
            <COL class="tb_col7">
            <THEAD>
              <TR>
                <TH></TH>
                <TH></TH>
                <TH>时间</TH>
                <TH>对阵</TH>
                <TH>城市</TH>
                <TH></TH>
              </TR>
            </THEAD>
            <TBODY>
              <TR class="tr01">
                <TD class="tb_block"></TD>
                <TD class="tb_col1">49场</TD>
                <TD>06月29日 星期日 00:00</TD>
                <TD> 1A <SPAN 
      class="match_score">VS </SPAN> 2B </TD>
                <TD>贝罗奥里藏特</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">50场</TD>
                <TD>06月29日 星期日 04:00</TD>
                <TD> 1C <SPAN 
      class="match_score">VS </SPAN> 2D </TD>
                <TD>里约热内卢</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">51场</TD>
                <TD>06月30日 星期一 00:00</TD>
                <TD> 1B <SPAN 
      class="match_score">VS </SPAN> 2A </TD>
                <TD>福塔莱萨</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">52场</TD>
                <TD>06月30日 星期一 04:00</TD>
                <TD> 1D <SPAN 
      class="match_score">VS </SPAN> 2C </TD>
                <TD>累西腓</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">53场</TD>
                <TD>07月01日 星期二 00:00</TD>
                <TD> 1E <SPAN 
      class="match_score">VS </SPAN> 2F </TD>
                <TD>巴西利亚</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">54场</TD>
                <TD>07月01日 星期二 04:00</TD>
                <TD> 1G <SPAN 
      class="match_score">VS </SPAN> 2H </TD>
                <TD>阿雷格里港</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">55场</TD>
                <TD>07月02日 星期三 00:00</TD>
                <TD> 1F <SPAN 
      class="match_score">VS </SPAN> 2E </TD>
                <TD>圣保罗</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">56场</TD>
                <TD>07月02日 星期三 04:00</TD>
                <TD> 1H <SPAN 
      class="match_score">VS </SPAN> 2G </TD>
                <TD>萨尔瓦多</TD>
                <TD></TD>
              </TR>
            </TBODY>
          </TABLE>
        </DIV>
      </DIV>
      <DIV class="mod_sch_calendar mod js_floating">
        <DIV class="wc_sch sch_violet">
          <DIV class="wctable_sch_title">
            <H2><I class="en">1/4</I>决赛</H2>
          </DIV>
          <TABLE class="wctable wctable_sch">
            <COLGROUP>
            <COL class="tb_block">
            <COL class="tb_col1">
            <COL class="tb_col2">
            <COL class="tb_col3">
            <COL class="tb_col4">
            <COL class="tb_col5">
            <COL class="tb_col6">
            <COL class="tb_col7">
            <THEAD>
              <TR>
                <TH></TH>
                <TH></TH>
                <TH>时间</TH>
                <TH>对阵</TH>
                <TH>城市</TH>
                <TH></TH>
              </TR>
            </THEAD>
            <TBODY>
              <TR class="tr01">
                <TD class="tb_block"></TD>
                <TD class="tb_col1">58场</TD>
                <TD>07月05日 星期六 00:00</TD>
                <TD> W53 <SPAN 
      class="match_score">VS </SPAN> W54 </TD>
                <TD>里约热内卢</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">57场</TD>
                <TD>07月05日 星期六 04:00</TD>
                <TD> W49 <SPAN 
      class="match_score">VS </SPAN> W50 </TD>
                <TD>福塔莱萨</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">60场</TD>
                <TD>07月06日 星期日 00:00</TD>
                <TD> W55 <SPAN 
      class="match_score">VS </SPAN> W56 </TD>
                <TD>巴西利亚</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">59场</TD>
                <TD>07月06日 星期日 04:00</TD>
                <TD> W51 <SPAN 
      class="match_score">VS </SPAN> W52 </TD>
                <TD>萨尔瓦多</TD>
                <TD></TD>
              </TR>
            </TBODY>
          </TABLE>
        </DIV>
      </DIV>
      <DIV class="mod_sch_calendar mod js_floating">
        <DIV class="wc_sch sch_yellow">
          <DIV class="wctable_sch_title">
            <H2><I class="en">1/2</I>决赛</H2>
          </DIV>
          <TABLE class="wctable wctable_sch">
            <COLGROUP>
            <COL class="tb_block">
            <COL class="tb_col1">
            <COL class="tb_col2">
            <COL class="tb_col3">
            <COL class="tb_col4">
            <COL class="tb_col5">
            <COL class="tb_col6">
            <COL class="tb_col7">
            <THEAD>
              <TR>
                <TH></TH>
                <TH></TH>
                <TH>时间</TH>
                <TH>对阵</TH>
                <TH>城市</TH>
                <TH></TH>
              </TR>
            </THEAD>
            <TBODY>
              <TR class="tr01">
                <TD class="tb_block"></TD>
                <TD class="tb_col1">61场</TD>
                <TD>07月09日 星期三 04:00</TD>
                <TD> W57 <SPAN 
      class="match_score">VS </SPAN> W58 </TD>
                <TD>贝罗奥里藏特</TD>
                <TD></TD>
              </TR>
              <TR>
                <TD class="tb_block"></TD>
                <TD class="tb_col1">62场</TD>
                <TD>07月10日 星期四 04:00</TD>
                <TD> W59 <SPAN 
      class="match_score">VS </SPAN> W60 </TD>
                <TD>圣保罗</TD>
                <TD></TD>
              </TR>
            </TBODY>
          </TABLE>
        </DIV>
      </DIV>
      <DIV class="mod_sch_calendar mod js_floating">
        <DIV class="wc_sch sch_red">
          <DIV class="wctable_sch_title">
            <H2><I class="zh">决赛</I></H2>
          </DIV>
          <TABLE class="wctable wctable_sch">
            <COLGROUP>
            <COL class="tb_block">
            <COL class="tb_col1">
            <COL class="tb_col2">
            <COL class="tb_col3">
            <COL class="tb_col4">
            <COL class="tb_col5">
            <COL class="tb_col6">
            <COL class="tb_col7">
            <THEAD>
              <TR>
                <TH></TH>
                <TH></TH>
                <TH>时间</TH>
                <TH>对阵</TH>
                <TH>城市</TH>
                <TH></TH>
              </TR>
            </THEAD>
            <TBODY>
              <TR class="tr01">
                <TD class="tb_block"></TD>
                <TD class="tb_col1">63场</TD>
                <TD>07月13日 星期日 04:00</TD>
                <TD> L61 <SPAN 
      class="match_score">VS </SPAN> L62 </TD>
                <TD>巴西利亚</TD>
                <TD></TD>
              </TR>
              <TR class="tr01">
                <TD class="tb_block"></TD>
                <TD class="tb_col1">64场</TD>
                <TD>07月14日 星期一 03:00</TD>
                <TD> W61 <SPAN 
      class="match_score">VS </SPAN> W62 </TD>
                <TD>里约热内卢</TD>
                <TD></TD>
              </TR>
            </TBODY>
          </TABLE>
        </DIV>
      </DIV>
    </DIV>
  </DIV>
</DIV> 
</div></div>
<jsp:include page="/include/footer.jsp"></jsp:include>
</BODY>
<script>
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
function jc(){
	var d= new Date().Format("yyyy-MM-dd");
	if("2014-06-13"==d){
		if(${session.user!=null}){
			alert("竞猜成功");
			setCookie("jc","1");
		}
		$("#f1").submit();
	}else{
		alert("竞猜时间已过！");
	}
	
}
$(function(){
	var myJc=$("#myJc").val().split(",");
	if(myJc.length==49){
		var games=$("#xzs TBODY tr");
		for (var i=0;i<48;i++)
		{
			var jc=myJc[i];
			var $game=$(games[i]);
			var x=$game.find("td:eq(3)").text();
			var teams=x.split("VS ");
			if(jc=="1"){
				$game.find("td:eq(5)").attr("style","color:red;").text($.trim(teams[0])+" 胜");
			}else if(jc=="2"){
				$game.find("td:eq(5)").attr("style","color:red;").text("平");
			}else if(jc=="3"){$game.find("td:eq(5)").attr("style","color:red;").text($.trim(teams[1])+" 胜");
			}
		}
	}
	var xjc=getCookie("jc");
	if(xjc=="1"){
		location.hash="cc15";
		setCookie("jc","0");
	}

	var xjc=getCookie("jc");
	if(xjc=="1"){
		location.hash="cc15";
		setCookie("jc","0");
	}

	
})
</script>
</HTML>
