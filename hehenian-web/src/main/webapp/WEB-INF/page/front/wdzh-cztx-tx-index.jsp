<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/include/head.jsp"></jsp:include>
<link href="css/user.css" rel="stylesheet" type="text/css" />

</head>
<body>
	<!-- 引用头部公共部分 -->
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
					<h2>提现</h2>
          <div class="warning_box" style=" margin-top:20px;"> 温馨提示：平台现免收任何费用。 </div>
					<table border="0" class="wdzh_cztx_one_tb">
				
						<tr>
							<td width="280" height="40" align="right">可提现金额：</td>
							<td><span><s:property value="#request.withdrawAmount" default="---"></s:property>元 </span>
							</td>
						</tr>
				
						<tr>
							<td width="280" height="40" align="right">提现金额：</td>
							<s:if test="#request.banks!=null && #request.banks.size>0">
							<td><input type="text" class="inp140" id="dealMoney"/>元 <span style=" float: none;" id="money_tip" class="formtips"></span>
							</td>
							</s:if>
							<s:else>
							<td><input type="text" class="inp140" id="dealMoney" disabled/>元 <span style=" float: none;" id="money_tip" class="formtips"></span>
							</td>
							</s:else>
						</tr>
						<%--						<tr height="40">--%>
						<%--							<td width="100" align="right">确认提现金额：</td>--%>
						<%--							<td><input type="text" class="inp140" id="dealMoney_confirm" />元 <span style=" float: none;" id="money_tip_confirm" class="formtips"></span>--%>
						<%--							</td>--%>
						<%--						</tr>--%>
						
						<tr>
							<s:if test="#request.banks==null || #request.banks.size == 0">
								<td colspan="9" align="left"><a href="bankInfoSetInit.do" style="margin-left: 246px;">暂未设置提现银行，请先进行设置</a> <!--   暂未设置提现银行，请先进行设置 --></td>
							</s:if>
						</tr>
						<%--						<tr height="40">--%>
						<%--							<td width="100" align="right">提现密码：</td>--%>
						<%--							<td><input type="password" class="inp140" id="dealpwd" /> <span style=" float: none;" id="pwd_tip" class="formtips"></span>--%>
						<%--							</td>--%>
						<%--						</tr>--%>
						<%--						<tr height="40">--%>
						<%--							<td width="100" align="right">验证码：</td>--%>
						<%--							<td><input type="text" class="inp100x" id="code" /> <input type="button" id="clickCode" value="发送验证码"--%>
						<%--								style=" width:126px; height:24px; background:url(images/pic_alla.png) -283px 0 no-repeat; color:#666;" /> <input id="type" name="type" value="drawcash" type="hidden" /> <span--%>
						<%--								style="float: none;" id="code_tip" class="formtips"> </span>--%>
						<%--							</td>--%>
						<%--						</tr>--%>
						<tr>
							<td>&nbsp;</td>
							<td>
                             <a onclick="addWithdraw();" class="bt_blue"><span class="bt_blue_lft"></span><strong style="color: #ffffff;">确认提现</strong><span class="bt_blue_r"></span></a>
							</td>
						</tr>
						<tr>
							<td></td>
							<td><span>* 温馨提示：禁止信用卡套现</span>
							</td>
						</tr>
					</table>
					<form id="f_addWithdraw" target="_blank" action="addWithdraw.do" method="post" >
		 					<input type="hidden" id="int_dealpwd" name="dealpwd" />
		 					<input type="hidden" id="int_money" name="money" />
		 					<input type="hidden" id="int_cellPhone" value="${bindingPhone}" name="cellPhone" />
		 					<input type="hidden" id="int_code" name="code" />
		 					<input type="hidden" id="int_type" name="type" />
		  </form>
					<div class="biaoge" id="biaoge" style="margin-top:25px;margin-right: 10px;">
						<span id="withdraw"></span>
					</div>
		
        
                              <div class="sidebar_box">
                <div class="sidebar_box_top"></div>
                <div class="sidebar_box_content">
                <h3>温馨提示：</h3>
                <img src="newimages/info.png" alt="" title="" class="sidebar_icon_right" />
                <ul>
                <li>（1）会员须开通第三方托管账户、绑定银行卡才能进行取现操作，请确保您绑定的银行户名与您在网站填写的真实姓名一致。 </li>
                 <li>（2）为防止套现，所充资金必须经投标回款后才能取现。</li>
                  <li>（3）到账时间规定：会员在工作日18：00前的取现申请，通过审核后一般可于下1个工作日到账；周末或节假日提交的取现申请，在之后第1个工作日到账。</li>
                   <li>（4）您目前能提现的最高额度是 <font color="#FF0000"><s:property value="#request.withdrawAmount" default="---"></s:property></font>元。</li>
  
                </ul>                
                </div>
                <div class="sidebar_box_bottom"></div>
            </div>
        
        
        
        
        
        
        
        </div>
      </div>
    </div>
  </div>
</div>








	<!-- 引用底部公共部分 -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
	<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
	<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
	<script>
		$(function() {
			//样式选中
			$(".wdzh_top_ul li").eq(2).addClass("wdzhxxk");
		})
		var flags = false;
		//手机号码绑定
		var timers = 60;
		var tipId;
		document.getElementById("biaoge").style.display = "none";
		$(function() {
			$("#lookdealMoney").click(function() {
				if (document.getElementById("biaoge").style.display == "") {
					document.getElementById("biaoge").style.display = "none";
				} else if (document.getElementById("biaoge").style.display == "none") {
					document.getElementById("biaoge").style.display = "";
				}

			});
			$("#clickCode").click(function() {

				var phone = '${request.bindingPhone}';
				if (phone == "") {
					alert("尚未绑定手机号");
					return;
				}
				if ($("#clickCode").val() == "重新发送验证码" || $("#clickCode").val() == "发送验证码") {
				 
	                $.post("phoneCheck.do", "phone=" + phone, function(datas) {
						if (datas.ret != 1) {
						alert(datas.msg);
						return;
						}
						phone = datas.phone;
						var test = {};
						test["paramMap.phone"] = phone;
					$.post("sendSMS.do", "phone=" + phone, function(data) {
						if (data == "virtual") {
							window.location.href = "noPermission.do";
							return;
						}
						if (data == 1) {
							timers = 60;
							tipId = window.setInterval(timer, 1000);
						} else {
							alert("手机验证码发送失败");
						}
					});
					});
				}
			});
		});

		//定时
		function timer() {

			if (timers >= 0) {
				$("#clickCode").val("验证码获取：" + timers + "秒");
				timers--;
			} else {
				window.clearInterval(tipId);
				$("#clickCode").val("重新发送验证码");
				$.post("removeCode.do", "", function(data) {
				});

			}
		}
	</script>
	<script src="script/add_all.js" type="text/javascript"></script>
	<script src="script/MSClass.js" type="text/javascript"></script>
	<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
	<script type="text/javascript">
		var flag = false;
		$(function() {
			$("#zh_hover").attr('class', 'nav_first');
			$('#li_2').addClass('on');
			$('.tabmain').find('li').click(function() {
				$('.tabmain').find('li').removeClass('on');

			});
			param["pageBean.pageNum"] = 1;
			initListInfo(param);
		});

		function initListInfo(praData) {
			//$.shovePost("queryWithdrawList.do", praData, initCallBack);
		}
		function initCallBack(data) {
			$("#withdraw").html(data);
		}

		function jumpUrl(obj) {
			window.location.href = obj;
		}

		/*$("#dealpwd").blur(function(){
		     if($("#dealpwd").val()==""){
		       $("#pwd_tip").html("提现密码不能为空");
		     }else{
		       $("#pwd_tip").html("");
		     }
		});
		
		$("#dealMoney").blur(function(){
		     if($("#dealMoney").val()=="" ){
		       $("#money_tip").html("提现金额不能为空");
		       return false
		     }else if(!/^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/.test($("#dealMoney").val())){
		       $("#money_tip").html("请输入正确的提现金额，必须为大于0的数字"); 
		       return false
		     }
		     else if(Number($("#dealMoney").val())<0.01 ){
		       $("#money_tip").html("提现金额不得低于0.01"); 
		       return false
		     }
		     else if(Number($("#dealMoney").val())+2> Number(${request.withdrawAmount})){
		       $("#money_tip").html("提现金额不能大于可用余额"); 
		       return false;
		     }else{
		       $("#money_tip").html(""); 
		     }
		});
		
		 $("#dealMoney_confirm_test").blur(function(){
		     /*if($("#dealMoney_confirm").val()=="" ){
		       $("#money_tip_confirm").html("提现金额不能为空");
		     }else if(!/^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/.test($("#dealMoney").val())){
		       $("#money_tip_confirm").html("请输入正确的提现金额，必须为大于0的数字"); 
		     }else if($("#dealMoney_confirm").val()<=100 ){
		       $("#money_tip_confirm").html("提现金额不得低于100"); 
		     }else if( $("#dealMoney").val()> ${usableSum}){
		      //alert(${usableSum});
		       $("#money_tip_confirm").html("提现金额不能大于可用余额"); 
		     }else 
		     if($("#dealMoney").val()!=$("#dealMoney_confirm").val()){
		       $("#money_tip_confirm").html("两次输入的提现金额不一致"); 
		     }else{
		     	 $("#money_tip_confirm").html("");
		     }
		});
		
		$("#code").blur(function(){
		     if($("#code").val()==""){
		       $("#code_tip").html("验证码不能为空");
		     }else{
		       $("#code_tip").html("");
		    }
		});*/

		function addWithdraw() {
			if ($("#dealMoney").val() == "") {
				alert("请填写提现金额");
				return;
			}else if(isNaN($("#dealMoney").val())){
				alert("请填写正确的金额");
				return;
			}
			if ($("#money_tip").text() != "") {
				alert("请填写正确的金额");
				$("#dealMoney").attr("value", "")
				return;
			}
			if (!window.confirm("确定进行申请提现?")) {
				return;
			}


			$("#int_dealpwd").val($("#dealpwd").val());
			$("#int_money").val($("#dealMoney").val());
			$("#int_code").val($("#code").val());
			$("#int_type").val($("#type").val());
			
			$("#f_addWithdraw").submit();
			
			/*
			$.shovePost("addWithdraw.do", $.param(param), function(data) {
				if (data.length < 30) {
					alert(data);
					return;
				}
				$("#tixian").html(data);
			});
			*/
			// flag = false;
			//if(data.msg == 1){
			//   flag  = true;
			//   window.clearInterval(tipId);
			//   $("#clickCode").html("发送手机验证码");
			//    alert("申请提现成功");
			//    jumpUrl('withdrawLoad.do');
			// }else{
			// 	  alert(data.msg);
			// }
		}

		$("#send_phoneCode").click(function() {
			var param = {
				"type" : "drawcash"
			};
			$.shovePost("sendPhoneCode.do", param, function(data) {
				if (data == 1) {
					alert("发送成功");
				}
				alert("验证码：" + data);
			});
		});
	</script>
</body>
</html>