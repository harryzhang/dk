<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page language="java" pageEncoding="UTF-8"%>
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
<style>
    body{font-family: "微软雅黑";background-color: #fafafa;line-height:22px;font-size:14px;}
</style>

<!--图片开始-->
<div><img src="img/reward_01.jpg" width="100%" alt=""/></div>
<!--图片结束--> 

<!--第一栏开始-->
<div style=" height:144px;">
  <div style=" position:absolute; width:100%">
    <div style=" width:253px; height:144px; margin:0px auto;"><img src="img/reward_first_03.png" alt=""/></div>
  </div>
  <div style=" height:70px; border-bottom:4px solid #e0e0e0"></div>
</div>
<div style=" font-size:50px; text-align:center; color:#5a5a5a; font-weight:bold; margin-top:20px; line-height:60px;font-family: '微软雅黑';">如何推荐您的好友</div>
<div style=" font-size:25px; text-align:center; color:#999; font-weight:bold;height:60px; line-height:60px;font-family: '微软雅黑';">有福需同享，奖励、收益大升级！</div>
<div style=" width:1170px; margin:0px auto; margin-top:30px; text-align:center; overflow:hidden;">
  <div style=" float:left; width:390px;"><img src="img/weixin.png" width="150" height="150"  alt=""/></div>
  <div style=" float:left; width:390px;"><img src="img/dx.png" width="150" height="150"  alt=""/></div>
  <div style=" float:left; width:390px;"><img src="img/link.png" width="150" height="150"  alt=""/></div>
  <div style=" float:left; width:390px;">微信</div>
  <div style=" float:left; width:390px;">短信</div>
  <div style=" float:left; width:390px;">专属链接</div>
</div>
<!--第一栏结束--> 

<!--第二栏开始-->
<div style=" height:144px; margin-top:50px;">
  <div style=" position:absolute; width:100%">
    <div style=" width:253px; height:144px; margin:0px auto;"><img src="img/reward_wewe_31.png" alt=""/></div>
  </div>
  <div style=" height:70px; border-bottom:4px solid #e0e0e0"></div>
</div>
<div style=" font-size:50px; text-align:center; color:#5a5a5a; font-weight:bold; margin-top:20px; line-height:60px;font-family: '微软雅黑';">豪礼相赠</div>
<div style=" font-size:25px; text-align:center; color:#999; font-weight:bold;height:60px; line-height:60px;font-family: '微软雅黑';margin-bottom:50px;">现金、ipad、iphone、Macbook、宝马2014款 525Li</div>
<div style=" width:980px; margin:0px auto; overflow:hidden">
  <div style="  margin-right:70px; width:400px; float:left; line-height:24px; font-size:14px;font-family: '微软雅黑';  padding-bottom:30px;"> 
    <p>活动期间，推荐好友来投资即可获取以下奖励。</p>
    <p style="color:#999">（好友为非彩生活及解放区等第三方渠道会员） </p>
    <p style="color:#999">&nbsp;</p>
    <p style=" font-size:20px;font-weight:bold; color:#999;line-height:50px; ">现金奖励</p>
    <p> 活动期间，推荐人推荐好友来投资，好友为首次投资。好友每月在投金额达一定金额（不包含接受转让债权的金额和投资“随存随取”产品的金额），推荐人将获得相应比例的现金奖励；</p>
    <p>&nbsp;</p>
    <p style=" font-size:20px;font-weight:bold; color:#999;line-height:50px; "> 活动时间</p>
    <p>2015年1月26日-2015年12月31日</p>
    <p>&nbsp;</p>
    <p style=" font-size:20px;font-weight:bold; color:#999; line-height:50px;"> 礼品赠送</p>
    <p> 活动期间，好友是首次投资且当月累计投资总额达50万、100万、500万、1000万、1亿（只计算投资六个月期限以上产品的金额），推荐人将获得额外礼品赠送。</p>
    <p>&nbsp;</p>
    <p style=" color:#999"> 本活动最终解释权归合和年在线所有。 </p>
  </div>
  <div style=" width:470px; float:left; background:#00a2ff; color:#FFF;font-family: '微软雅黑';　border-radius: 15px; padding:20px;">
    <table style=" width:100%;  line-height:38px;" >
      <tr>
        <td width="41%"><strong>当月好友在投总额（元）</strong></td>
        <td width="19%"><strong>现金奖励</strong></td>
        <td width="40%"><strong>实物奖励（限1季度）</strong></td>
      </tr>
      <tr>
        <td>100,000以下</td>
        <td>1%</td>
        <td></td>
      </tr>
      <tr>
        <td>100,000-500,000</td>
        <td>1.2%</td>
        <td></td>
      </tr>
      <tr>
        <td>500,000-1,000,000</td>
        <td>1.5%</td>
        <td>ipad air2  16G</td>
      </tr>
      <tr>
        <td>1,000,000-5,000,000</td>
        <td>2%</td>
        <td>iphone6 plus    16G</td>
      </tr>
      <tr>
        <td>5,000,000-10,000,000</td>
        <td>2%</td>
        <td>苹果MacBook Pro</td>
      </tr>
      <tr>
        <td>10,000,000-100,000,000</td>
        <td>2%</td>
        <td>苹果电三件套（以上三件）</td>
      </tr>
      <tr>
        <td>100,000,000以上</td>
        <td>2%</td>
        <td>宝马2014款 525Li </td>
      </tr>
    </table>
  </div>
  <div><img src="img/333.png" width="980" height="240"  alt=""/></div>
</div>
<!--第二栏结束--> 

<!--第三栏开始-->
<div style=" height:144px;">
  <div style=" position:absolute; width:100%">
    <div style=" width:253px; height:144px; margin:0px auto;"><img src="img/reward_wewe_32.png" alt=""/></div>
  </div>
  <div style=" height:70px; border-bottom:4px solid #e0e0e0"></div>
</div>
<div style=" font-size:50px; text-align:center; color:#5a5a5a; font-weight:bold; margin-top:20px; line-height:60px; margin-bottom:50px;font-family: '微软雅黑';">如何查看是否邀请成功?</div>
<div style=" width:980px; margin:0px auto; overflow:hidden">
  <div style=" width:510px; margin-right:70px; float:left"><img src="img/reward_page03d_07.png" width="467" height="396"  alt=""/></div>
  <div style=" width:400px; float:left; line-height:24px; font-size:14px;font-family: '微软雅黑'; border-bottom:5px solid #F60; padding-top:50px; padding-bottom:30px;"> 
    <p>通过微信、短信、复制链接的方式邀请好友。好友为首次投资且非彩生活及解放区等第三方渠道会员。成功邀请的好友会在邀请记录中显示。</p>
    <p>&nbsp;</p>
    <p>您可进入<font color="#FF0000">“活动中心 - 邀请记录”</font>中查看是否已成功邀请。</p>
  </div>
</div>
<!--第三栏结束--> 

<!--第四栏开始-->
<div style=" height:144px;">
  <div style=" position:absolute; width:100%">
    <div style=" width:253px; height:144px; margin:0px auto;"><img src="img/reward_wewe_33.png" alt=""/></div>
  </div>
  <div style=" height:70px; border-bottom:4px solid #e0e0e0"></div>
</div>
<div style=" font-size:50px; text-align:center; color:#5a5a5a; font-weight:bold; margin-top:20px; line-height:60px;margin-bottom:50px;font-family: '微软雅黑';">如何计算现金奖励?</div>
<div style=" width:980px; margin:0px auto; overflow:hidden">
  <div style=" width:400px; margin-right:70px; float:left; line-height:24px; font-size:14px;font-family: '微软雅黑'; border-bottom:5px solid #F60; padding-top:70px; padding-bottom:30px;">
    <p>现金奖励=所有好友的在库投资金额的总额×奖励利率      </p>
    <p>&nbsp;</p>
    <p>在库投资金额：在合和年在线上投资未转让或是提现，在计息的金额。      </p>
    <p>&nbsp;</p>
    <p>例：如果1月12日，A在平台投资金额为1,000,000元，那么推荐人在2月份可以获得推荐奖励1,000,000×1×1.5%÷365=36.99元；如果A在平台12个月内的在投金额都是1,000,000元，那么推荐人共可获得20000元的奖励。（具体金额以系统显示为准）</p>
  </div>
  <div style=" width:470px; float:left; background:#00a2ff; color:#FFF;font-family: '微软雅黑';　border-radius: 15px; padding:20px;">
    <table style=" width:100%;  line-height:38px;" >
      <tr>
        <td width="60%" style=" padding-left:30px;"><strong>当月好友在投总额（元）</strong></td>
        <td><strong>现金奖励</strong></td>
      </tr>
      <tr>
        <td style=" padding-left:30px;">100,000以下</td>
        <td>1%</td>
      </tr>
      <tr>
        <td style=" padding-left:30px;">100,000-500,000</td>
        <td>1.2%</td>
      </tr>
      <tr>
        <td style=" padding-left:30px;">500,000-1,000,000</td>
        <td>1.5%</td>
      </tr>
      <tr>
        <td style=" padding-left:30px;">1,000,000-5,000,000</td>
        <td>2%</td>
      </tr>
      <tr>
        <td style=" padding-left:30px;">5,000,000-10,000,000</td>
        <td>2%</td>
      </tr>
      <tr>
        <td style=" padding-left:30px;">10,000,000-100,000,000</td>
        <td>2%</td>
      </tr>
      <tr>
        <td style=" padding-left:30px;">100,000,000以上</td>
        <td>2%</td>
      </tr>
    </table>
  </div>
</div>
<!--第四栏结束--> 

<!--第五栏开始-->
<div style=" height:144px; margin-top:50px;">
  <div style=" position:absolute; width:100%">
    <div style=" width:253px; height:144px; margin:0px auto;"><img src="img/reward_wewe_34.png" alt=""/></div>
  </div>
  <div style=" height:70px; border-bottom:4px solid #e0e0e0"></div>
</div>
<div style=" font-size:50px; text-align:center; color:#5a5a5a; font-weight:bold; margin-top:20px; line-height:60px;margin-bottom:50px;font-family: '微软雅黑';">如何领取奖励礼品?</div>
<div style=" width:980px; margin:0px auto; overflow:hidden">
  <div style=" width:510px; margin-right:70px; float:left"><img src="img/jp.png" width="467" height="396"  alt=""/></div>
  <div style=" width:400px; float:left; line-height:24px; font-size:14px;font-family: '微软雅黑'; border-bottom:5px solid #F60; padding-top:70px; padding-bottom:30px;"> 
    <p>若系统提示您已获得礼品奖励，在次月月初的10个工作日内会有本平台的工作人员联系您，请保持您的手机信号畅通。届时会以邮寄或是其它方式将礼品寄给您，<font color="#FF0000">期间不收取任何费用</font>。若有任何疑问，请随时联系本平台的客服进行咨询。</p>
    <p>&nbsp;</p>
    <p>客服热线：4008-303-737。</p>
  </div>
</div>
<!--第五栏结束--> 

<!--第六栏开始-->
<div style=" height:144px;">
  <div style=" position:absolute; width:100%">
    <div style=" width:253px; height:144px; margin:0px auto;"><img src="img/reward_wewe_35.png" alt=""/></div>
  </div>
  <div style=" height:70px; border-bottom:4px solid #e0e0e0"></div>
</div>
<div style=" font-size:50px; text-align:center; color:#5a5a5a; font-weight:bold; margin-top:20px; line-height:60px;margin-bottom:50px;font-family: '微软雅黑';">如何查看现金奖励发放记录?</div>
<div style=" width:980px; margin:0px auto; overflow:hidden">
  <div style="width:400px; margin-right:70px; float:left; line-height:24px; font-size:14px;font-family: '微软雅黑'; border-bottom:5px solid #F60; padding-top:70px; padding-bottom:30px;">现金奖励于每月月初发放到您合和年在线平台账户。您届时可登录个人中心页面资金管理板块，点击“资金记录”即可查看您账户的所有收支明细。</div>
  <div style=" width:510px; float:left"><img src="img/reward_page03d_0711.png" width="467" height="396"  alt=""/> </div>
</div>
<!--第六栏结束-->

<input type="hidden" value="${session.user.id }" id="userId" />
<!-- 引用底部公共部分-->
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script> 
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script> 
<script type="text/javascript" src="script/jQueryRotate.2.2.js"></script> 
<script type="text/javascript" src="script/scroll.js"></script> 
<!--滚动--> 
<script type="text/javascript">
		$(function() {
			$('ul.prize-winner-list li:even').addClass('evenHighlight');
			$("div.prize-winner-js").myScroll({
				speed : 20, //滚动速度
				rowHeight : 32
			//li行高；
			});
		});
	</script> 
<script type="text/javascript">
		$(function() {
			var self = $("#scroll ul")
			var sd = null;
			var leng = parseInt($("#scroll ul").css("width",
					self.find("li").length * 459).css("width"));
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

			ImgScroll(".mjd_banner", "#mjd_banner_ul", 1000, ".contrl", "a",
					"sydp-a5", "sydp-a6", "li", 1000, 900, 4000);
		})
	</script> 
<!--抽奖转盘--> 
<script type="text/javascript">
	var flag=true;
		$(function() {
			
			$(".lot-btn").click(function() {
				if(flag){
					flag=false;
					if("${session.user.id }"!=""){
						//if(($("#drawNum").text()-0)>0){
							$.post("luckdraw.do",{activityId:1},function(data){
								if(data.ret==0){
									var awardId = data.awardId;
									zhuan((awardId-1)*58);
								}else if(data.ret==3){
									alert("在活动期间才可以抽奖哦");
									flag=true;
								}else if(data.ret==2){
									alert("您的抽奖机会已用完，追加投资可继续获取抽奖机会");
									flag=true;
								}
									else{
									alert("抽奖不成功");
									flag=true;
								}
								
							});
						//}else{
						//	alert("您的抽奖机会已用完，追加投资可继续获取抽奖机会");
						//	flag=true;
						//}
						
					}else{
						alert("您还没有登录哦，先去登录吧");
						window.location.href="/login.jsp";
					}
					
				}
			})

		});
		function chou(jiang) {
			if (jiang == "1") {

			} else if (jiang == "2") {

			} else if (jiang == "3") {

			} else if (jiang == "4") {

			} else if (jiang == "5") {

			} else if (jiang == "6") {

			}
		}
		function zhuan(jiaodu) {
			$("#imgs").rotate({
				angle : 0,//起始角度
				animateTo : jiaodu + 1440, //结束的角度
				duration : 4000, //转动时间
				callback : function() {
					$("#drawNum").text($("#drawNum").text()-1);
					flag=true;
				} //回调函数
			});
		}
		function sign(){
			if("${session.user.id }"!=""){
				if("${session.user.usrCustId}"==""||("${session.user.usrCustId}"-0)<0){
					alert("注册汇付天下后才能签到，请先注册汇付天下");
					window.location.href="/registerChinaPnr.do";
				}else{
					$.post("sign.do",{activityId:1},function(data){
						if(data==0){
							$("#sign").removeClass("redFont").addClass("currentHandler").html("签到成功");
							$("#signDays").text($("#signDays").text()-0+1);
							$("#singMoney").text($("#singMoney").text()-0+0.88);
						}else if(data==1){
							alert("已经签到过了")
						}else if(data==3){
							alert("在活动期间才可以签到哦");
						}else{
							alert("您还没有登录哦，先去登录吧");
							window.location.href="/login.jsp";
						}
					});
				}
			}else{
				alert("您还没有登录哦，先去登录吧");
				window.location.href="/login.jsp";
			}
			
			
		}
		 /*---中奖记录弹层---*/
	    $(function(){
	        var $myLottery = $('#myLottery-details-js');
	        $('button.myLottery').on("click",function(e){
	        	 $("#myRecords").load("my-activity-records.do",{activityId:1});
	            $myLottery.toggle();
	            e.stopPropagation();
	        });
	        $(document).on('click',function(e){
	           if($(e.target).is('#myLottery-details-js')){
	        	   $("#myRecords").load("my-activity-records.do",{activityId:1});
	             $myLottery.show();
	           }
	            $myLottery.hide();
	       })
	    });
	</script> 
<script type="text/javascript">
            function show_djs(){
            var djs = document.getElementById("djs");
            var e_Day = new Date("9/6/2014"); //也可以是: 月/日/年
            var e_Msg = "活动结束";
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
            if(c<=d){djs.innerHTML="活动已经结束";return false;}
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
            djs.innerHTML = "距" + e_Msg + "还有" + hrsold + "小时" + minsold + "分" + seconds + "秒";
            setTimeout("show_djs()",1000);
        }
        show_djs();
    </script>
</body>
</html>
