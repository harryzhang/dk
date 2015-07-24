<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<style type="text/css">
ul,li{margin:0;padding:0}
#scrollDiv{height:182px;overflow:hidden}
</style>
<script src="script/jquery-1.7.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
function nTabs(thisObj,Num){
	if(thisObj.className == "active")return;
		var tabObj = thisObj.parentNode.id;
		var tabList = document.getElementById(tabObj).getElementsByTagName("li");
		for(i=0; i <tabList.length; i++)
		{
			if (i == Num)
			{
			thisObj.className = "active";
			document.getElementById(tabObj+"_Content"+i).style.display = "block";
			}else{
		tabList[i].className = "normal";
		document.getElementById(tabObj+"_Content"+i).style.display = "none";
		}
	}
}
</script>
</head>

<body>
<div class="top">
  <div class="logo"> <a href="#"><img src="images/index9_03.jpg" width="222" height="77" /></a></div>
  <div class="login"> <a href="zhuce.html">注册</a> <span>-</span> <a href="login.html">登录</a> <span>|</span> <a href="#" class="kf">客服</a> </div>
</div>
<div class="nav">
  <div class="nav_left"> </div>
  <div class="nav_main">
    <ul>
      <li id="sy_hover"><a href="index.html">首页</a>
        <div class="h_menu" id="sy_menu">
          <ul>
            <li class="first"><a href="#">平台原理</a></li>
            <li class="s-line2"></li>
            <li><a href="#">团队介绍</a></li>
            <li class="s-line2"></li>
            <li><a href="#">法律政策</a></li>
            <li class="s-line2"></li>
            <li><a href="#">关于我们</a></li>
            <li class="s-line2"></li>
            <li><a href="#">下载专区</a></li>
          </ul>
        </div>
      </li>
      <li id="licai_hover"><a href="wylc.html">我要理财</a>
      <div class="h_menu none" id="licai_menu">
          <ul>
            <li class="first"><a href="#">如何理财</a></li>
            <li class="s-line2"></li>
            <li><a href="#">工具箱</a></li>
            <li class="s-line2"></li>
            <li><a href="#">成为理财人</a></li>
          </ul>
        </div>
      </li>
      <li id="jk_hover"><a href="wyjk.html">我要借款</a>
      <div class="h_menu none" id="jk_menu">
          <ul>
            <li class="first"><a href="#">申请贷款</a></li>
            <li class="s-line2"></li>
            <li><a href="#">担保人列表</a></li>
          </ul>
        </div>
      </li>
      <li id="zq_hover"><a href="#">债权转让</a></li>
      <li id="zh_hover" class="navact"><a href="wdzh.html">我的账户</a></li>
      <li id="bj_hover"><a href="bjbz.html">本金保障</a></li>
      <li id="kf_hover"><a href="kfzx.html">客服中心</a></li>
      <li id="lt_hover" style="background:none;"><a href="#">论坛</a></li>
    </ul>
  </div>
  <div class="nav_right"> </div>
</div>
<div class="nav_bottom">
  <div class="nav_b_left"></div>
  <div class="nav_b_right"></div>
</div>
<div class="nymain">
  <div class="wdzh">
    <div class="l_nav">
      <div class="box">
      <div class="til"><h2>我的合和年在线</h2></div>
      <ul><li class="fir on"><a href="#">我的主页</a></li>
<li><a href="#">充值提现</a></li>
<li><a href="#">合和年在线认证</a></li>
<li><a href="#">站内信</a></li>
<li><a href="#">个人设置</a></li>
<li><a href="#">好友管理</a></li>
</ul>
      </div>
      <div class="box">
      <div class="til"><h2>借款管理</h2>
      </div>
      <ul><li class="fir"><a href="#">还款管理</a></li>
<li><a href="#">已发布的借款</a></li>
<li><a href="#">贷款统计</a></li>
</ul>
      </div>
      <div class="box">
      <div class="til"><h2>理财管理</h2>
      </div>
      <ul>
      <li class="fir"><a href="#">我的投标</a></li>
<li><a href="#">我关注的借款</a></li>
<li><a href="#">担保管理</a></li>
<li><a href="#">理财统计</a></li>
<%--<li><a href="#">自动投标</a></li>--%>
</ul>
      </div>
      <div class="box">
      <div class="til"><h2>债权管理</h2>
      </div>
      <ul><li class="fir"><a href="#">债权转让</a></li>
			<li><a href="#">债权购买</a></li>
</ul>
      </div>
    </div>
    <div class="r_main">
      <div class="box">
      <h2>我的个人信息</h2>
      <div class="box-main">
      <div style="overflow:hidden; height:100%;">
        <div class="pic_info">
          <div class="pic">
            <img src="images/neiye3_18.jpg" width="128" height="128" /></div>
           <p><a href="#">更换头像</a></p>
           <p class="ipaddress">最后登录IP地址：192.168.1.125</p>
        </div>
        <div class="xx_info">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th align="right">用户名： </th>
    <td>jianman1202 <img src="images/022713_3.jpg" width="14" height="17" /></td>
  </tr>
  <tr>
    <th align="right">注册时间： </th>
    <td>2012-07-04 </td>
  </tr>
  <tr>
    <th align="right">会员积分：</th>
    <td><img src="images/022713_11.jpg" width="95" height="16" /></td>
  </tr>
  <tr>
    <th align="right">信用积分：</th>
    <td><img src="images/neiye1_36.jpg" width="33" height="22" /></td>
  </tr>
  <tr>
    <th align="right"><p>信用额度：</p></th>
    <td>50000.00</td>
  </tr>
  <tr>
    <th align="right"><p>个人统计：</p></th>
    <td><p>9条借款记录，10条投标记录</p></td>
  </tr>
</table>

        </div>
        <div class="hy_info">
        <p>会员到期：<span>2013.01.01  20:20:30</span></p>
        <a href="#" class="vipbtn">成为VIP会员</a>
        </div>
      </div>
        <div class="tips">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th><img src="images/022713_28.jpg" width="12" height="12" /> 温馨提示：</th>
    <td>未读站内信<a href="#">（6）</a>封</td>
    <td>等待审核借款<a href="#">（0）</a>个</td>
    <td>本月代还款<a href="#">（0）</a>个</td>
    <td>本月代收款<a href="#">（0）</a>个</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>逾期待还款<a href="#">（0）</a>个</td>
    <td>上传资料<a href="#">（0）</a></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>

        </div>
      </div>
      </div>
      <div class="box">
      <h2 class="otherh2">快速通道：</h2>
      <div class="box-main" style="padding-left:70px;">
		<div class="mid" >
        <ul><li><a href="#"><img src="images/022713_32.jpg" width="32" height="35" /></a><br/>
        <a href="#">我要充值</a>
        </li>
        <li><a href="#"><img src="images/022713_34.jpg" width="32" height="35" /></a><br/>
        <a href="#">我要贷出</a>
        </li>
        <li><a href="#"><img src="images/022713_36.jpg" width="32" height="35" /></a><br/>
        <a href="#">债权转让</a>
        </li>
        <li><a href="#"><img src="images/022713_38.jpg" width="32" height="35" /></a><br/>
        <a href="#">我要还款</a>
        </li>
         <li><a href="#"><img src="images/022713_40.jpg" width="32" height="35" /></a><br/>
        <a href="#">待收款</a>
        </li>
        <li><a href="#"><img src="images/022713_42.jpg" width="32" height="35" /></a><br/>
        <a href="#">资金流水</a>
        </li>
        <li><a href="#"><img src="images/022713_44.jpg" width="32" height="35" /></a><br/>
        <a href="wyjk.html">我要借款</a>
        </li>
        </ul>
        </div>
      </div>
      </div>
      <div class="box" style="border-bottom:1px solid #d1d9de;">
      <h2 class="otherh2">账户详情：</h2>
      <div class="box-main">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="zhtab">
  <tr>
    <th colspan="3" align="left">账户总汇：</th>
    </tr>
  <tr>
    <td>账户总额：￥0.00</td>
    <td>可用余额：￥0.00 </td>
    <td>冻结金额：￥0.00</td>
  </tr>
  <tr>
    <td>总收益：￥0.00</td>
    <td>利息收益：￥0</td>
    <td>其他收益：￥0.00</td>
  </tr>
  <tr>
    <th colspan="3" align="left">放款总汇：</th>
    </tr>
  <tr>
    <td>已收总额：￥0.00</td>
    <td>已收本金：￥0.00</td>
    <td>已收利息：￥0.00</td>
  </tr>
  <tr>
    <td>待收总额：￥0.00</td>
    <td>待收本金：￥0</td>
    <td>待收利息：￥0.00</td>
  </tr>
  <tr>
    <th colspan="3" align="left">借款总汇：</th>
    </tr>
  <tr>
    <td>已收总额：￥0.00</td>
    <td>已收本金：￥0.00</td>
    <td>已收利息：￥0.00</td>
  </tr>
  <tr>
    <td>待收总额：￥0.00</td>
    <td>待收本金：￥0</td>
    <td>待收利息：￥0.00</td>
  </tr>
  <tr>
    <th colspan="3" align="left">额度总汇：</th>
    </tr>
  <tr>
    <td>借款总额度：￥0.00 </td>
    <td>可用额度：￥0.00</td>
    <td>&nbsp;</td>
  </tr>
</table>

      </div>
      </div>
    </div>
  </div>
</div>
<div class="footer">
  <ul>
    <li>
      <div class="link"> <a href="#"><img src="images/index9_112.jpg" width="49" height="49" /></a></div>
      <a href="#">登陆注册</a> </li>
      <li class="foot_line"></li>
    <li>
      <div class="link"> <a href="#"><img src="images/index9_114.jpg" width="49" height="49" /></a></div>
      <a href="#">充值提现</a> </li>
      <li class="foot_line"></li>
    <li>
      <div class="link"> <a href="#"><img src="images/index9_116.jpg" width="49" height="49" /></a></div>
      <a href="#">待收款</a> </li>
      <li class="foot_line"></li>
    <li>
      <div class="link"> <a href="#"><img src="images/index9_118.jpg" width="49" height="49" /></a></div>
      <a href="#">我要还款</a> </li>
      <li class="foot_line"></li>
    <li>
      <div class="link"> <a href="#"><img src="images/index9_120.jpg" width="49" height="49" /></a></div>
      <a href="wdzh.html">我的账户</a> </li>
      <li class="foot_line"></li>
    <li>
      <div class="link"> <a href="#"><img src="images/index9_122.jpg" width="49" height="49" /></a></div>
      <a href="#">关注用户</a> </li>
      <li class="foot_line"></li>
    <li>
      <div class="link"> <a href="#"><img src="images/index9_125.jpg" width="49" height="49" /></a></div>
      <a href="#">使用帮助</a> </li>
      <li class="foot_line"></li>
    <li>
      <div class="link"> <a href="#"><img src="images/index9_127.jpg" width="49" height="49" /></a></div>
      <a href="#">工具箱</a> </li>
  </ul>
</div>
<div class="footer_bottom"> 版权所有 © 2013 合和年在线 www.hehenian.com<br/></div>
</body>
</html>
