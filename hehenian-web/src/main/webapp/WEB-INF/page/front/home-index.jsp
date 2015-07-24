<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.sp2p.entity.User"%>
<%@include file="/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
<link href="css/hhncss.css" rel="stylesheet" type="text/css" />
<link href="css/user.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	$(document).ready(function(){
		　　if('${map.refferee}' == -1)
			{
				$("#refferee").attr("");
			}else
			{
				$("#refferee").val('${map.refferee}');;
			}
        $.get("updateUserUsableSum.do?_="+new Date().getTime());
		}); 
</script>

</head>
<body id="loadOpen">
<%--<!-- 引用头部公共部分 -->--%>
<jsp:include page="/include/top.jsp"></jsp:include>
<div class="user-all">
  <div class="user-center">
    <div style=" width:1170px; margin:0px auto; padding-bottom:24px;"><img src="/images/v1/detail_logo_bg.jpg" width="1170" height="6"  alt=""/></div>
    <div style=" overflow:hidden">
      <div class="u-left">
        <jsp:include page="/include/menu_userManage.jsp"></jsp:include>
      </div>
      <div class="u-right">
        <div class="user-right"> 
          <!--账户总览-->
          <h2>账户总览</h2>
          <div class="account-box">
          	<ul class="clearfix">
            	<li>
                	<span class="t1">昨日增值</span>
                    <span class="t2 t-red">￥<s:if test="#request.userIncomeDo.dailyIncome !='' && #request.userIncomeDo.dailyIncome!=null"><fmt:formatNumber pattern="#0.00" value="${userIncomeDo.dailyIncome}"></fmt:formatNumber> </s:if><s:else>0.00</s:else></span>
                    <span class="t3">昨日的新增资产</span>
                </li>
                <li>
                	<span class="t1">您的累计收益</span>
                    <span class="t2 t-red">￥<s:if test="#request.userIncomeDo.earnSum !=''"><fmt:formatNumber pattern="#0.00" value="${userIncomeDo.earnSum}"></fmt:formatNumber></s:if><s:else>0.00</s:else></span>
                    <span class="t3">投资人获得的投资收益及其他收益</span>
                </li>
                <li>
                	<span class="t1">资产估值</span>
                    <span class="t2 t-red">
                    	￥<c:choose>
                    		<c:when test="${userIncomeDo.assetValue > 0}">
                    			<fmt:formatNumber pattern="#0.00" value="${userIncomeDo.assetValue}"/>
                    		</c:when>
                    		<c:otherwise>0.00</c:otherwise>
                    	  </c:choose>
                    </span>
                    <span class="t3">投资人当前的资产总价值</span>
                </li>
                
                <li>
                	<span class="t1 t-normal">待收本金</span>
                    <span class="t2 t-yellow">
                    	￥<c:choose>
                    		<c:when test="${userIncomeDo.recivedPrincipal > 0}">
                    			<fmt:formatNumber pattern="#0.00" value="${userIncomeDo.recivedPrincipal - userIncomeDo.hasPrincipal}"/>
                    		</c:when>
                    		<c:otherwise>0.00</c:otherwise>
                    	  </c:choose>
                    </span>
                    <span class="t3">投资人已投资尚未收回的本金</span>
                </li>
                 <li>
                	<span class="t1 t-normal">冻结金额</span>
                    <span class="t2 t-dark">￥<s:if test="#request.userIncomeDo.freezeAmount !=''"><fmt:formatNumber pattern="#0.00" value="${userIncomeDo.freezeAmount}"></fmt:formatNumber></s:if><s:else>0.00</s:else></span>
                    <span class="t3">投资中或提现中冻结的资金</span>
                </li>
                <li>
                	<span class="t1 t-normal">可用余额</span>
                    <span class="t2 t-yellow">￥<s:if test="#request.userIncomeDo.withdrawalAmount !=''"><fmt:formatNumber pattern="#0.00" value="${userIncomeDo.withdrawalAmount}"></fmt:formatNumber></s:if><s:else>0.00</s:else></span>
                    <span class="t3">
                    <c:if test="${session.user.accountType==1}">
                    <a style="left:65px;" class="bt_blue" onclick="alert('无需充值');return;"><span class="bt_blue_lft"></span><strong>充值</strong><span class="bt_blue_r"></span></a>
                    <a style="left:155px;" class="bt_green" onclick="alert('请发送提现申请邮件进行提现操作');return;"><span class="bt_green_lft"></span><strong>提现</strong><span class="bt_green_r"></span></a>
					</c:if>
					<c:if test="${session.user.accountType != 1}">
					<a style="left:65px;" class="bt_blue" href="rechargeInit.do"><span class="bt_blue_lft"></span><strong>充值</strong><span class="bt_blue_r"></span></a>
                    <a style="left:155px;" class="bt_green" href="withdrawLoad.do"><span class="bt_green_lft"></span><strong>提现</strong><span class="bt_green_r"></span></a>
					</c:if>
                    
                   </span>
                </li>
               
            </ul>
          </div>
          
          <!--一键投标-->
          <h2>便捷功能</h2>
          <div style=" background:#f0f4f7; overflow:hidden; margin:20px 0px; padding:20px 40px;; line-height:28px;">
            <div style=" float:left; width:130px;"><a onclick="yjtb();" class="bt_blue" style=" margin:0px;"><span class="bt_blue_lft"></span><strong>一键投标</strong><span class="bt_blue_r"></span></a></div>
            <div style=" float:left; width:670px;"><font style=" font-size:12px; color:#999; line-height:16px; "><strong>一键投标说明：</strong>为了方便投资者，省去各项繁琐操作，用户可通过点击一键投标进行投资，如需要使用此功能，请先在<a href="automaticBidInit.do" style=" color:#F90">【理财管理】-【自动投标】</a>页面进行设置。一键投标会根据您的设置，将您的资金一次性投入到对应的标的中。</font></div>
          </div>
          
          <!--资金-->
          
          <h2>个人信息</h2>
          
          <div style=" background:#f0f4f7; overflow:hidden; margin:20px 0px; padding:20px; line-height:28px; ">
            <div style=" width:250px; float:left; border-right:1px solid #d0d5d9;  padding:0px 10px; height:150px;">
            <ul>
            	<li>用户名：${session.user.username}</li>
                <li>电子邮箱：
                <s:if test="#session.user.email==null || #session.user.email==''"> 
                <a href="updatexgmm.do?j=2" style="color: #E94718;font-size: 12px;">您还没有绑定邮箱,点击绑定</a> </s:if>
                <s:else>${session.user.email }</s:else>
                <input value="${map.usrCustId}" id="usrCustId" type="hidden"></li>
                <li>真实姓名：<s:if test="#request.realName==null || #request.realName==''"> <a href="owerInformationInit.do" style="color: #E94718;font-size: 12px;">您还未填写个人真实信息,点击填写</a> </s:if>
                <s:else>${request.realName}</s:else></li>
            </ul>
          	</div>
            
            <div  style=" width:200px; float:left; border-right:1px solid #d0d5d9;  padding:0px 30px 0px 30px;height:150px;">
              <form id="flr" target="_blank" action="" method="post" >
                <p>汇付编号:
                  <s:if test="#request.usrCustId>0">${session.user.usrCustId }<br />
                  <a id="loginHF" class="bt_green" style=" margin:0px;">
                  <span class="bt_green_lft"></span>
                  <strong>登陆汇付</strong>
                  <span class="bt_green_r"></span>
                  </a>
                  </s:if>
                  <s:else> 
                  <span style="color:#e94718;font-size: 12px;">您还不是汇付天下会员</span><br/>
                  <a id="registerChinaPnr" class="bt_red" style=" margin:0px;">
                  <span class="bt_red_lft"></span>
                  <strong>点击注册</strong>
                  <span class="bt_red_r"></span>
                  </a>
                  </s:else>
                  </p>
              </form>
            </div>
            
            <div style="float:left; width:280px;   padding:0px 0px 0px 30px;height:150px;">
                <div>我的推广ID：${userId}
               <!-- <a class="tooltips" href="#tooltips"> 
                <img src="/images/wenhao.png" width="13" height="13"  alt=""/>
                <span>新用户在注册时，在推荐人栏里填入您的推广ID号，在他首次投资后，您即可获得60元现金红包。 </span></a>
              <style type="text/css">-->
<!--/*Tooltips*/
.tooltips{
position:relative; /*这个是关键*/
z-index:2;
}
.tooltips:hover{
z-index:3;
background:none; /*没有这个在IE中不可用*/
}
.tooltips span{
display: none;
}
.tooltips:hover span{ /*span 标签仅在 :hover 状态时显示*/
display:block;
position:absolute;
line-height:16px;
width:15em;
border:1px solid black;
background-color:#fbf7e9;
padding:8px;
color:black;
}
</style>-->
            </div>
            <div>推广链接：<a id="copy_button">点击复制链接</a></div>
            <div><a id="link">${url}account/reg.do?param=${userI}</a></div>
            <div>
            <%--   <p> 填写我的推荐人：<input style=" width:100px;" type="text" id="refferee" 
                    
                <s:if test="#request.map.refferee !='' && #request.map.refferee != -1">disabled="disabled"</s:if>
                />
                <s:if test="#request.map.refferee == '' || #request.map.refferee == -1"><font style="color:red">提示：请填写推荐人的会员号，推荐人必须是网站会员。</font></s:if>
               <a ><strong>提交</strong></a>
               
              <!-- <input type="button" value="提交" onclick="confirm();" class="bcbtn" 
                <s:if test="#request.map.refferee !='' && #request.map.refferee != -1">disabled="disabled" style="background: #ccc"</s:if>/> -->
                
                
                </p> --%>
              我的推荐人: <s:if test="#session.user.refferee=='' || #session.user.refferee == -1">暂无</s:if>
              <s:else><a style="color: red">${session.user.refferee}</a></s:else> 
            </div>
            </div>
            
          </div>
          
          
      
          
          <!--其他--> 
          
        </div>
      </div>
    </div>
  </div>
</div>

<!-- 引用底部公共部分 -->
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script> 
<script>
		//切换展现方式
		function changeShow(index) {
			$(".showTime1").hide();
			$(".showTime2").hide();
			$(".showTime" + index).show();
		}
	</script> 
<script>
		var result = 0;
		var param = {};
		$(function() {
			if (('${request.idNo}' == null || '${request.idNo}' == '') && '${session.hhnflag}' == '') {
				//$("#wenxts").css("display", "block");
			//	$.post("ajaxHomeFlag.do", param, function(data) {
				//});
				//$('.bg').fadeIn(200);
				//$('.tc_wenxts').fadeIn(400);
			}
			$("#toInfor").click(function() {
				window.location.href = "owerInformationInit.do";
				$("#wenxts").css("display", "none");
				$('.bg').fadeOut(800);
				$('.content').fadeOut(800);
			});
			$("#toNone").click(function() {
				$("#wenxts").css("display", "none");
				$('.bg').fadeOut(800);
				$('.content').fadeOut(800)
			});
			$("#closes").click(function() {
				$("#wenxts").css("display", "none");
				$('.bg').fadeOut(800);
				$('.content').fadeOut(800)
			});

			//样式选中
			$(".wdzh_next_left li").eq(0).addClass("wdzh_next_left_ong").find("a").attr("style","color: rgb(233, 71, 24);");
			dqzt(4);
			$("#btn_img").click(
					function() {
						param['paramMap.userName'] = $("#u").val();
						$.post("queryHeadImg.do", param, function(data) {
							var dir = getDirNum();
							var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'user/" + dir
									+ "','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall','cp':'img'}";
							json = encodeURIComponent(json);
							window.showModalDialog("uploadFileAction.do?obj=" + json, window, "dialogWidth=500px;dialogHeight=400px");
							var headImgPath = $("#img").attr("src");
							if (headImgPath == "") {
								alert("上传失败！");
							}
							window.location.reload();
		});

					});
		});
		function uploadCall(basepath, fileName, cp) {
			if (cp == "img") {
				var path = "upload/" + basepath + "/" + fileName;
				$("#img").attr("src", path);
				param['paramMap.imgPath'] = path;
				$.shovePost("updatePersonImg.do", param, initCallBack);
			}
		}
		function initCallBack(data) {
			alert(data.msg);
		}
		function getDirNum() {
			var date = new Date();
			var m = date.getMonth() + 1;
			var d = date.getDate();
			if (m < 10) {
				m = "0" + m;
			}
			if (d < 10) {
				d = "0" + d;
			}
			var dirName = date.getFullYear() + "" + m + "" + d;
			return dirName;
		}

		$("#loginHF").click(function() {
			$("#flr").attr("action","loginHF.do");
			$("#flr").submit();
			 /*$.shovePost("loginHF.do",null,function(data){
	       		 if(data.length<30){
	       			 alert(data);
	       			 return;
	       		 }
	       		 $("#zhuce").html(data);
	       	 });
			//window.open("loginHF.do");
			*/
		});
		$("#registerChinaPnr").click(function() {
			$("#flr").attr("action","registerChinaPnr.do");
			$("#flr").submit();
			/*
			if('${idNo}' == null || '${idNo}' == "")
			{
				alert("您还未完善个人资料,请进入会员中心 个人信息  完善个人资料");
				return;
			}
			else{
				 $.shovePost("registerChinaPnr.do",null,function(data){
		       		 if(data.length<30){
		       			 alert(data);
		       			 return;
		       		 }
		       		 $("#zhuce").html(data);
		       	 });
				//window.open("registerChinaPnr.do");
			}
			*/
		});
	</script> 
<script type="text/javascript">
		function getCookie(c_name) {
			if (document.cookie.length > 0) {
				c_start = document.cookie.indexOf(c_name + "=")
				if (c_start != -1) {
					c_start = c_start + c_name.length + 1
					c_end = document.cookie.indexOf(";", c_start)
					if (c_end == -1)
						c_end = document.cookie.length
					return unescape(document.cookie.substring(c_start, c_end))
				}
			}
			return ""
		}

		function setCookie(c_name, value, expiredays) {
			var exdate = new Date()
			exdate.setDate(exdate.getDate() + expiredays)
			document.cookie = c_name + "=" + escape(value) + ((expiredays == null) ? "" : "; expires=" + exdate.toGMTString());
		}

		function checkCookie() {
			var idNo = getCookie('idNo');
	<%--		realName = getCookie('realName');--%>
		if (idNo != "admin") {
	<%--			alert('Welcome again ' + username + '!')--%>
		$("#noLoginDiv").hide();
			} else {
	<%--			username = prompt('Please enter your name:', "")--%>
		
	<%--				realName = "{request.realName}";--%>
		$("#noLoginDiv").show();
				var idNo = "000001hhn";
				if (idNo != null && idNo != "") {
					setCookie('idNo', idNo, 1);
	<%--				setCookie('realName', realName, 1);--%>
		}
			}
		}
	</script>
<div class="tc_wenxts" id="wenxts" style="display:none; ">
  <h3> <span>温馨提示</span><strong id="closes">X</strong> </h3>
  <h2>欢迎您成为合和年在线会员！</h2>
  <p style="margin-top: -35px;margin-bottom: 35px;font-size: 12px;color: #E94718;">&nbsp;${request.tips }</p>
  <p>
    <input type="button" value="完善个人资料" id="toInfor" />
  </p>
  <p>
    <input type="button" value="先逛逛" id="toNone" />
  </p>
  <p> <span>注：成为网站投资人或者借款人必须注册汇付天下账号。</span> </p>
</div>
<div class="bg"></div>
<script type="text/javascript" src="script/ZeroClipboard.js"></script> 
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<link rel="stylesheet" href="css/jbox/Gray/jbox.css" type="text/css"></link>
<script type="text/javascript">
		var clip = null;
		$(function() {
			$(".wdzh_next_left li").eq(5).addClass("wdzh_next_left_ong").find("a").attr("style","color: rgb(233, 71, 24);");
			clip = new ZeroClipboard.Client();
			clip.setHandCursor(true);
			clip.addEventListener("mouseOver", my_mouse_over);
			clip.addEventListener("complete", my_complete);
			clip.glue("copy_button");
		});

		function my_mouse_over(client) {
			clip.setText($("#link").text());
		}
		function my_complete(client, text) {
			jBox.tip("已复制到剪贴板", "success", {
				top : "31%",
				timeout: 900
			});
		}
		function yjtb(){
			$.post("onekeybid.do",function(data){
				if(data=="1"){
					alert("一键投标成功！");
				}else{
					alert("请先开启自动投标");
					window.location.href="automaticBidInit.do";
				}
			})
		}
	</script> 
<c:if test="${param.via=='zhuce'}">
    <!-- Google Code for &#27880;&#20876;&#36716;&#21270; Conversion Page -->
    <script type="text/javascript">
        /* <![CDATA[ */
        var google_conversion_id = 962632519;
        var google_conversion_language = "en";
        var google_conversion_format = "2";
        var google_conversion_color = "ffffff";
        var google_conversion_label = "xK4hCPKDy1gQx7aCywM";
        var google_remarketing_only = false;
        /* ]]> */
    </script>
    <script type="text/javascript" src="//www.googleadservices.com/pagead/conversion.js">
    </script>
    <noscript>
        <div style="display:inline;">
            <img height="1" width="1" style="border-style:none;" alt="" src="//www.googleadservices.com/pagead/conversion/962632519/?label=xK4hCPKDy1gQx7aCywM&amp;guid=ON&amp;script=0"/>
        </div>
    </noscript>
    <script>
        (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
            (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
                m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
        })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

        ga('create', 'UA-53576224-1', 'auto');
        ga('send', 'pageview');

    </script>

</c:if>
<c:if test="${param.via=='zhuce' && session.sourcefrom=='22'}">
    <script type="text/javascript">
        var _mvq = window._mvq || [];
        window._mvq = _mvq;
        _mvq.push(['$setAccount', 'm-117079-0']);

        _mvq.push(['$setGeneral', 'register', '', /*用户名*/ '', /*用户id*/ '']);
        _mvq.push(['$logConversion']);

    </script>
    </c:if>
</body>
</html>
