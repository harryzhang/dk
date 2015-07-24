<%@ page language="java" import="java.util.*,java.sql.*,com.sp2p.entity.*"  pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<link id="skin" rel="stylesheet" href="../css/jbox/Gray/jbox.css" />
<jsp:include page="../include/head.jsp"></jsp:include>
<link href="../css/Site2.css" rel="stylesheet" type="text/css" />
</head>
<body>ces
	<!-- 引用头部公共部分 -->
	<jsp:include page="../include/top.jsp"></jsp:include>
	
<div style=" background:#bc5043">
   	  <div style=" margin:0px auto; width:1000px; background:url(../js/s.jpg); text-align:center"><img src="js/q.png" /></div>
        <div style=" margin:0px auto; width:1000px; background:url(../js/s.jpg); text-align:center">
            <s:if test="!#request.hasYuyue">
                <a href="?cmd=yy"><img src="../js/w.png" /></a>
            </s:if>
            <s:elseif test="#request.hasYuyue">
                <a><img src="../js/u.png" /></a>
            </s:elseif>
        </div>
    </div>
    <div style=" margin:0px auto; width:1000px; background:url(../js/x.png); height:69px; text-align:center; line-height:69px; color:#FFF">活动日期：5月9日 仅此一天</div>
    
    <div style=" margin:0px auto; width:1000px; overflow:hidden">
   	  <div style=" width:499px; float:left; border-right:1px solid #CCC; height:600px;"><div><span id="djs" style=" font-size:22px; font-weight:bold; color:#bc5043"></span>
<script type="text/javascript">
function show_djs(){
var djs = document.getElementById("djs");
var e_Day = new Date("9 May 2014"); //也可以是: 月/日/年
var e_Msg = "活动开始";
var today = new Date();
var a = (e_Day.getMonth()+1);
if(a<10){a="0"+a};
var a1 = e_Day.getDate();
if(a1<10){a1="0"+a1};
var b = (today.getMonth()+1);
if(b<10){b="0"+b};
var b1 = today.getDate();
if(b1<10){b1="0"+b1};
var c = e_Day.getYear()+""+a+""+a1;
var d = today.getYear()+""+b+""+b1;
if(c<d){djs.innerHTML="活动已经开始";return false;}
else if(c==d){djs.innerHTML="今天是"+e_Msg;return false;}
var timeold = (e_Day.getTime() - today.getTime());
var secondsold = Math.floor(timeold/1000);
var e_daysold = timeold/(24*60*60*1000);
var daysold = Math.floor(e_daysold);
var e_hrsold = (e_daysold - daysold) * 24;
var hrsold = Math.floor(e_hrsold);
var e_minsold = (e_hrsold - hrsold) * 60;
var minsold = Math.floor((e_hrsold - hrsold) * 60);
var seconds = Math.floor((e_minsold - minsold) * 60);
var mm = Math.floor((timeold % 1000)/100);
djs.innerHTML = "距" + e_Msg + "还有" + daysold + "天" + hrsold + "小时" + minsold + "分" + seconds + "." + mm + "秒";
setTimeout("show_djs()",100);
}
show_djs();
</script></div>
	<div style=" font-size:13px; padding:50px 20px 20px 0px;"><font style=" font-size:32px; font-weight:bold; color:#bc5043">献给妈妈的爱</font><br />
	  ——给妈妈送个礼物，带妈妈出去吃个饭，合和年在线买单！</div>
    <div style=" font-size:14px; padding:5px 20px 20px 0px;">活动日期：2014年5月9日0点-24点</div>
    <div style=" font-size:14px; padding:5px 20px 5px 0px; line-height:26px;">在合和年在线投资“献给妈妈的爱”母亲节特别标，即得投资总额1%额外收益作为母亲节关爱现金奖，标的正常年化12%的收益不变。</div>
    <div style=" font-size:14px; padding:15px 20px 15px 0px;">当日在合和年在线投资</div>
     <div style=" font-size:14px; padding:15px 20px 15px  0px;">投资完成后，马上返投资额的1%到您的投资帐户</div>
     <div style=" font-size:11px; padding:20px 20px 20px 0px;">*本活动最终解释权归合和年在线所有</div>
      <div style=" font-size:11px; padding:65px 20px 20px 0px;"><img src="js/ad.png" /></div>
</div>
        <div style=" width:499px; float:left;background:url(js/e.png); height:800px; ">
        
        <div style=" width:500px; height:150px; background:url(js/r.png) repeat-x;">
         <table width="479" height="122" border="0" cellpadding="0" cellspacing="0" style=" color:#FFF">
           <tr>
             <td width="148" style=" padding-left:20px;"><p><span>借款金额<br />
             </span><span style=" color:#FFF; font-size:42px;">5.00</span>万元</p></td>
             <td width="191" style=" line-height:32px;">编号：HHN1405061175<span style="padding-top: 5px;"><br />
             年利率： 12.00%+1.00%<br />
             </span>
											<div class="index_jd" style="margin: 3px 1px;">
												<div style="width: 61.00%; height:12px; background:url(images/pic_all.png) 0 -70px no-repeat; "></div>
			 </div> </td>
             <td width="167" style=" text-align:center"  class="tball">
													<div class="bottm">
													<input type="button" onclick="window.location.href='financeDetail.do?id=${finance.id}'" value=""  />

												</div>
														
												
              
             </td>
           </tr>
          </table>
  
        </div>
<div style=" width:500px; height:150px; background:url(js/r.png) repeat-x;">
         <table width="479" height="122" border="0" cellpadding="0" cellspacing="0" style=" color:#FFF">
           <tr>
             <td width="148" style=" padding-left:20px;"><p><span>借款金额<br />
             </span><span style=" color:#FFF; font-size:42px;">9.00</span>万元</p></td>
             <td width="191" style=" line-height:32px;">编号：HHN1405061175<span style="padding-top: 5px;"><br />
             年利率： 12.00%+1.00%<br />
             </span>
											<div class="index_jd" style="margin: 3px 1px;">
												<div style="width: 61.00%; height:12px; background:url(images/pic_all.png) 0 -70px no-repeat; "></div>
			 </div> </td>
             <td width="167" style=" text-align:center"  class="tball">
													<div class="bottm">
													<input type="button" onclick="window.location.href='financeDetail.do?id=${finance.id}'" value=""  />

												</div>
														
												
              
             </td>
           </tr>
          </table>
  
        </div>
        <div style=" width:500px; height:150px; background:url(js/r.png) repeat-x;">
         <table width="479" height="122" border="0" cellpadding="0" cellspacing="0" style=" color:#FFF">
           <tr>
             <td width="148" style=" padding-left:20px;"><p><span>借款金额<br />
             </span><span style=" color:#FFF; font-size:42px;">7.00</span>万元</p></td>
             <td width="191" style=" line-height:32px;">编号：HHN1405061175<span style="padding-top: 5px;"><br />
             年利率： 12.00%+1.00%<br />
             </span>
											<div class="index_jd" style="margin: 3px 1px;">
												<div style="width: 61.00%; height:12px; background:url(images/pic_all.png) 0 -70px no-repeat; "></div>
			 </div> </td>
             <td width="167" style=" text-align:center"  class="tball">
													<div class="bottm">
													<input type="button" onclick="window.location.href='financeDetail.do?id=${finance.id}'" value=""  />

												</div>
														
												
              
             </td>
           </tr>
          </table>
  
        </div>
        <div style=" width:500px; height:150px; background:url(js/r.png) repeat-x;">
         <table width="479" height="122" border="0" cellpadding="0" cellspacing="0" style=" color:#FFF">
           <tr>
             <td width="148" style=" padding-left:20px;"><p><span>借款金额<br />
             </span><span style=" color:#FFF; font-size:42px;">3.00</span>万元</p></td>
             <td width="191" style=" line-height:32px;">编号：HHN1405061175<span style="padding-top: 5px;"><br />
             年利率： 12.00%+1.00%<br />
             </span>
											<div class="index_jd" style="margin: 3px 1px;">
												<div style="width: 61.00%; height:12px; background:url(images/pic_all.png) 0 -70px no-repeat; "></div>
			 </div> </td>
             <td width="167" style=" text-align:center"  class="tball">
													<div class="bottm">
													<input type="button" onclick="window.location.href='financeDetail.do?id=${finance.id}'" value=""  />

												</div>
														
												
              
             </td>
           </tr>
          </table>
  
        </div>
        <div style=" width:500px; height:150px; background:url(js/r.png) repeat-x;">
         <table width="479" height="122" border="0" cellpadding="0" cellspacing="0" style=" color:#FFF">
           <tr>
             <td width="148" style=" padding-left:20px;"><p><span>借款金额<br />
             </span><span style=" color:#FFF; font-size:42px;">15.00</span>万元</p></td>
             <td width="191" style=" line-height:32px;">编号：HHN1405061175<span style="padding-top: 5px;"><br />
             年利率： 12.00%+1.00%<br />
             </span>
											<div class="index_jd" style="margin: 3px 1px;">
												<div style="width: 61.00%; height:12px; background:url(images/pic_all.png) 0 -70px no-repeat; "></div>
			 </div> </td>
             <td width="167" style=" text-align:center"  class="tball">
													<div class="bottm">
													<input type="button" onclick="window.location.href='financeDetail.do?id=${finance.id}'" value=""  />

												</div>
														
												
              
             </td>
           </tr>
          </table>
  
        </div>
          <div style=" width:500px; height:30px; font-size:16px; text-align:center; color:#666">
		  <a href="finance.do">查看更多爱心标</a> </div>
        </div>
        
</div>
</div>
	<!-- 引用底部公共部分-->
	<jsp:include page="../include/footer.jsp"></jsp:include>
	<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
	<script type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
	<script type="text/javascript" src="../script/jbox/jquery.jBox-zh-CN.js"></script>
</body>
</html>
