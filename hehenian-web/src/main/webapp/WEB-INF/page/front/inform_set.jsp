<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
<link href="css/hhncss.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<!-- 引用头部公共部分 -->
     <div style="position: absolute; height:54px; line-height:54px; background:#f3f3f3; width:100%; margin-top:151px !important; margin-top:177px;border-bottom:1px solid #e5e5e5;border-top:1px solid #e5e5e5;"></div>
	<jsp:include page="/include/top.jsp"></jsp:include>

	<div class="cle"></div>
	<div class="wytz_center">
    <div class="mjd_tzlc_all"><jsp:include page="/include/menu_userManage.jsp"></jsp:include></div>
		<div class="wdzh_top">
			
			<div class="cle"></div>
			<div class="wdzh_next" style="display:block;">
            	<div class="wdzh_next_left">
            	<jsp:include page="/include/menu_userManage_4_left.jsp"></jsp:include>
                	
                </div>
                <div class="wdzh_next_right">
                	<div class="nd_tzsz">
                    <table width="600" border="0">
                        <tr>
                          <td valign="top" width="130"><input type="checkbox" id="mail" onclick="checkAll_MG(this); "/> <span>站内信通知</span></td>
                            <td>
                                <div class="nd_tzsz_a">  <input type="checkbox" id="messageReceive" class="mg" onclick="mgcheck1(this);" /><span>收到还款</span></div>
                                 <div class="nd_tzsz_a">  <input type="checkbox" id="messageDeposit"  class="mg" onclick="mgcheck1(this);"/><span>提现成功</span></div>
                                <div class="nd_tzsz_a"> <input type="checkbox" id="messageBorrow" class="mg" onclick="mgcheck1(this);"/><span>借款成功</span></div>
                                <div class="nd_tzsz_a"> <input type="checkbox" id="messageRecharge"  class="mg" onclick="mgcheck1(this);"/><span>充值成功</span></div>
                                <div class="nd_tzsz_a"> <input type="checkbox" id="messageChange" class="mg" onclick="mgcheck1(this);"/><span>资金变化</span></div>
                            </td>
                        </tr>
                        <tr height="5"></tr>
                    </table>
                    <table width="600" border="0">
                        <tr>
                            <td valign="top" width="130"><input type="checkbox" id="mail" onclick="checkAll_ML(this); "/> <span>邮件通知</span></td>
                            <td>
                               <div class="nd_tzsz_a">  <input type="checkbox" id="mailReceive" class="ml" onclick="mlcheck1(this);"/><span>收到还款</span></div>
                                 <div class="nd_tzsz_a">    <input type="checkbox" id="mailDeposit" class="ml" onclick="mlcheck1(this);"/><span>提现成功</span></div>
                                <div class="nd_tzsz_a">  <input type="checkbox" id="mailBorrow" class="ml" onclick="mlcheck1(this);"/><span>借款成功</span></div>
                                <div class="nd_tzsz_a">  <input type="checkbox" id="mailRecharge" class="ml" onclick="mlcheck1(this);"/><span>充值成功</span></div>
                                <div class="nd_tzsz_a"> <input type="checkbox" id="mailChange" class="ml" onclick="mlcheck1(this);"/><span>资金变化</span></div>
                            </td>
                        </tr>
                        <tr height="5"></tr>
                    </table>
                    <table width="600" border="0">
                        <tr>
                            <td valign="top" width="130"><input type="checkbox" id="note" onclick="checkAll_NT(this); "/> <span>短信通知</span></td>
                            <td>
                                 <div class="nd_tzsz_a">  <input type="checkbox" id="noteReceive" class="nt" onclick="ntcheck1(this);" /><span>收到还款</span></div>
                                 <div class="nd_tzsz_a">  <input type="checkbox" id="noteDeposit"  class="nt" onclick="ntcheck1(this);"/><span>提现成功</span></div>
                                <div class="nd_tzsz_a"> <input type="checkbox" id="noteBorrow" class="nt" onclick="ntcheck1(this);"/><span>借款成功</span></div>
                                <div class="nd_tzsz_a"> <input type="checkbox" id="noteRecharge"  class="nt" onclick="ntcheck1(this);"/><span>充值成功</span></div>
                                <div class="nd_tzsz_a">  <input type="checkbox" id="noteChange" class="nt" onclick="ntcheck1(this);"/><span>资金变化</span></div>
                            </td>
                        </tr>
                        <tr height="5"></tr>
                    </table>
            	</div>
                    <p class="nd_tzsz_p">注：后台操作以及资金变更，系统会以以上三种方式提醒用户，请用户选择发送方式！系统默认以邮件通知！</p>
                    <input type="button" value="确定" class="nd_tzsz_but_z" onclick="addNoteSetting();"/>
                </div>
            </div>
            <div class="cle"></div>
            <div class="wdzh_next">
            	5
            </div>
            <div class="cle"></div>
			</div>
			<div class="cle"></div>
		</div>
	<div class="cle"></div>


	<!-- 引用底部公共部分 -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
	<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
	<script type="text/javascript" src="common/date/calendar.js"></script>
	<script type="text/javascript" src="css/popom.js"></script>
	<script>
		function jumpUrl(obj) {
			window.location.href = obj;
		}
	</script>
	<script>
		$(function() {
			$(".wdzh_next_left li").eq(5).addClass("wdzh_next_left_ong");
			dqzt(4);
			$('#li_5').addClass('on');
			$.shovePost("queryNotesSettingInit.do", null, function(data) {
				if (data == 1) {
					return;
				}
				var message = false, mail = false, notes = false;

				for ( var i = 0; i < data.length; i++) {
					if (i == 0) {
						message = data[i].message;
						mail = data[i].mail;
						notes = data[i].note;
					}
					if (data[i].noticeMode == "1") {//邮件通知
						if (data[i].reciveRepayEnable == "2") {
							$("#mailReceive").attr("checked", "checked")
						}
						if (data[i].showSucEnable == "2") {
							$("#mailDeposit").attr("checked", "checked")
						}
						if (data[i].loanSucEnable == "2") {
							$("#mailBorrow").attr("checked", "checked")
						}
						if (data[i].rechargeSucEnable == "2") {
							$("#mailRecharge").attr("checked", "checked")
						}
						if (data[i].capitalChangeEnable == "2") {
							$("#mailChange").attr("checked", "checked")
						}
					} else if (data[i].noticeMode == "2") {//站内信通知
						if (data[i].reciveRepayEnable == "2") {
							$("#messageReceive").attr("checked", "checked")
						}
						if (data[i].showSucEnable == "2") {
							$("#messageDeposit").attr("checked", "checked")
						}
						if (data[i].loanSucEnable == "2") {
							$("#messageBorrow").attr("checked", "checked")
						}
						if (data[i].rechargeSucEnable == "2") {
							$("#messageRecharge").attr("checked", "checked")
						}
						if (data[i].capitalChangeEnable == "2") {
							$("#messageChange").attr("checked", "checked")
						}
					} else {//短信通知
						if (data[i].reciveRepayEnable == "2") {
							$("#noteReceive").attr("checked", "checked")
						}
						if (data[i].showSucEnable == "2") {
							$("#noteDeposit").attr("checked", "checked")
						}
						if (data[i].loanSucEnable == "2") {
							$("#noteBorrow").attr("checked", "checked")
						}
						if (data[i].rechargeSucEnable == "2") {
							$("#noteRecharge").attr("checked", "checked")
						}
						if (data[i].capitalChangeEnable == "2") {
							$("#noteChange").attr("checked", "checked")
						}
					}
				}
				if (message) {//只要分类有一个被选中，则父类就选中
					$("#message").attr("checked", "checked");
					//$(".mg").attr("checked","checked");
				}
				if (mail) {
					$("#mail").attr("checked", "checked");
					//$(".ml").attr("checked","checked");
				}
				if (notes) {
					$("#note").attr("checked", "checked");
					//$(".nt").attr("checked","checked");
				}
			});
		});
		//通知设置
		function addNoteSetting() {
			//alert($("#message").attr("checked"));
			//站内信
			param["paramMap.message"] = $("#message").attr("checked");
			param["paramMap.messageReceive"] = $("#messageReceive").attr("checked");
			param["paramMap.messageDeposit"] = $("#messageDeposit").attr("checked");
			param["paramMap.messageBorrow"] = $("#messageBorrow").attr("checked");
			param["paramMap.messageRecharge"] = $("#messageRecharge").attr("checked");
			param["paramMap.messageChange"] = $("#messageChange").attr("checked");
			//邮件
			param["paramMap.mail"] = $("#mail").attr("checked");
			param["paramMap.mailReceive"] = $("#mailReceive").attr("checked");
			param["paramMap.mailDeposit"] = $("#mailDeposit").attr("checked");
			param["paramMap.mailBorrow"] = $("#mailBorrow").attr("checked");
			param["paramMap.mailRecharge"] = $("#mailRecharge").attr("checked");
			param["paramMap.mailChange"] = $("#mailChange").attr("checked");
			//短信
			param["paramMap.note"] = $("#note").attr("checked");
			param["paramMap.noteReceive"] = $("#noteReceive").attr("checked");
			param["paramMap.noteDeposit"] = $("#noteDeposit").attr("checked");
			param["paramMap.noteBorrow"] = $("#noteBorrow").attr("checked");
			param["paramMap.noteRecharge"] = $("#noteRecharge").attr("checked");
			param["paramMap.noteChange"] = $("#noteChange").attr("checked");

			$.shovePost("addNotesSetting.do", param, function(data) {
				if (data == 1) {
					alert("通知设置失败");
				} else if (data == 3) {
					alert("未绑定邮箱,通知设置失败");
				} else {
					alert("通知设置成功");
				}
			});
		}
		function checkAll_MG(e) {
			if (e.checked) {
				$(".mg").attr("checked", "checked");
			} else {
				$(".mg").removeAttr("checked");
			}
		}

		function checkAll_ML(e) {
			if (e.checked) {
				$(".ml").attr("checked", "checked");
			} else {
				$(".ml").removeAttr("checked");
			}
		}

		function checkAll_NT(e) {
			if (e.checked) {
				$(".nt").attr("checked", "checked");
			} else {
				$(".nt").removeAttr("checked");
			}
		}

		function mgcheck1(e) {
			var len = $("input[class='mg']:checked").length;
			if (len > 0) {
				$("#message").attr("checked", "checked");
			} else {
				$("#message").removeAttr("checked");
			}
		}

		function mlcheck1(e) {
			var len = $("input[class='ml']:checked").length;
			if (len > 0) {
				$("#mail").attr("checked", "checked");
			} else {
				$("#mail").removeAttr("checked");
			}
		}

		function ntcheck1(e) {
			var len = $("input[class='nt']:checked").length;
			if (len > 0) {
				$("#note").attr("checked", "checked");
			} else {
				$("#note").removeAttr("checked");
			}
		}

		//加载该用户提现银行卡信息
		function loadBankInfo(url) {
			var bb = '${person}';//设置提现银行卡必须先填写个人资料
			if (bb == "0") {
				alert("请先完善个人信息");
				window.location.href = "owerInformationInit.do";
			} else {
				window.location.href = url;
			}

		}
		//工作认证
		function loadWorkInit(url) {
			var bb = '${person}';//填写工作信息必须先填写个人资料
			if (bb == "0") {
				alert("请先完善个人信息");
				window.location.href = "owerInformationInit.do";
			} else {
				window.location.href = url;
			}
		}
		function bindingPhoneLoad(url) {
			/**	var bb = '${person}';//申请手机绑定必须先填写个人资料
				var cc = '${pass}';
				if (bb == "0") {
					alert("请先完善个人信息");
					window.location.href='owerInformationInit.do';
				} else if (cc != 3) {
					alert("请等待个人信息审核通过");
					return ;
				} else {*/
			window.location.href = url;
			//		}
		}
	</script>

</body>
</html>
