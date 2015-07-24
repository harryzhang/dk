<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/include/head.jsp"></jsp:include>
<link href="css/user.css" rel="stylesheet" type="text/css" />
<link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
<style>
table {
	line-height: 33px;
}
table tr {
}
table tr td {
	line-height: 33px;
}
table tr td input {
	height: 24px;
	cursor: pointer;
	margin: 1px;
	float: left
}
table tr td label {
	line-height: 33px;
	height: 33px
}
.border_ {
	border: #eeeeee 1px double;
	width: 720px;
	overflow: hidden;
	margin-left: 0px;
	margin-right: 0px;
}
.border_ table tr td .border_ table tr td strong {
	font-size: 14px;
}
.wytz_center a:HOVER {
	text-decoration: none;
}
</style>
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
          <h2>充值</h2>
          <div class="warning_box" style=" margin-top:20px;"> 温馨提示：平台现免收任何费用。</div>
          <table width="100%;" border="0" class="wdzh_cztx_one_tb"  style=" width:100%">
            <tr height="20" style="padding-top: 20px;">
              <td width="380" align="right">真实姓名：</td>
              <td height="20"><s:if test="#request.realName==null || #request.realName==''"> <a href="owerInformationInit.do" style="color: #E94718;font-size: 12px;">您还未填写个人真实信息,点击填写</a> </s:if>
                <s:else>${realName }</s:else></td>
            </tr>
            <tr>
              <td align="right">电子邮件：</td>
              <td><s:if test="#request.email==null || #request.email==''"> <a href="updatexgmm.do?j=2" style="color: #E94718;font-size: 12px;">您还没有绑定邮箱,点击绑定</a> </s:if>
                <s:else>${email }</s:else>
                <input value="${map.usrCustId}" id="usrCustId" type="hidden"></td>
            </tr>
            <s:if test="#request.map.usrCustId =='' || #request.map.usrCustId ==-1">
              <tr height="40">
                <td colspan="2"><div class="btn" id="btn_submit"> <span style="margin-left:310px;color:#3b9cfd;">您还不是汇付天下会员,请先注册!</span><br />
                    <%--										 <a href="javascript:void(0);" id="registerChinaPnr" class="bcbtn" style="color: black;margin-left: 60px;">注册</a>--%>
                    <form id="f_registerChinaPnr" target="_blank" action="registerChinaPnr.do" method="post" >
                      <input type="button" style="margin-left:310px;" id="registerChinaPnr" value="点击注册" />
                    </form>
                  </div></td>
              </tr>
            </s:if>
            <s:else>
              <tr height="30">
                <td align="right">充值金额：</td>
                <td><input id="money" type="text" class="inp140" style="height: 22px;cursor:text;" />
                  <span style="color: red; float: none;" id="money_tip" class="formtips"></span></td>
              </tr>
              <tr>
                <td align="right">充值类型：</td>
                <td><input type="radio" name="rechargeType" id="chinapnr" checked="checked" />
                  <u>汇付天下</u>
                  <%-- <input type="radio" name="rechargeType" id="ipay" /> <u>在线充值</u>--%></td>
              </tr>
              <tr>
                <td align="right">银行卡类型：</td>
                <td><input name="cardDcFlag" type="radio" id="cardDcFlagC" value="D" checked="checked" />
                  <u>借记卡</u></td>
              </tr>
              <!--
              <tr>
                  <td align="right">充值方式：</td>
                  <td id="GateBusiId"> <input type="radio" name="GateBusiId" id="GateBusiId0" value="0" checked/>
                  <label for="GateBusiId0" style="font-weight:normal;"><u>快捷充值</u></label>
                   <input type="radio" name="GateBusiId" id="GateBusiId1" value="1"/>
                   <label for="GateBusiId1" style="font-weight:normal;"><u>网上银行</u></label> 
                </tr>
              -->
              <input type="hidden" name="GateBusiId" id="GateBusiId1" value="1"/>
              <tr>
                <td colspan="2"><!-- 汇付天下div -->
                 <!--快捷充值-->
                  <div class="bank-list" style="display: none; ">
                      <table border="0" width="824" align="center" cellpadding="0" cellspacing="0" style="width:100%">
                        <tr>
                          <td colspan="5"><strong>请选择支付方式：</strong></td>
                        </tr>
                        <tr>
                          <td><input type="radio" name="bankTypeQuick" value="ICBC" id="rd_chinapnr_icbc" checked="checked" />
                          <img src="/images/banks/bank_01.png" width="100" height="28" style="border: none" alt="工商银行" /></td>
                          <td><input type="radio" name="bankTypeQuick" value="ABC" />
                          <img src="/images/banks/bank_04.png" width="120" height="28" style="border: none" alt="农业银行" /></td>                
                          <td><input type="radio" name="bankTypeQuick" value="CMB" />
                          <img src="/images/banks/bank_02.png" width="120" height="28" style="border: none" alt="招商银行" /></td>
                          <td><input type="radio" name="bankTypeQuick" value="CCB" />
                            <img src="/images/banks/bank_03.png" width="120" height="28" style="border: none" alt="建设银行" /></td>
                          <td><input type="radio" name="bankTypeQuick" value="BOC" />
                            <img src="/images/banks/bank_05.png" width="120" height="28" style="border: none" alt="中国银行" /></td>
                        </tr>
                        <tr>
                        <td><input type="radio" name="bankTypeQuick" value="BOCOM" />
                            <img src="/images/banks/bank_11.png" width="120" height="28" style="border: none" alt="交通银行" /></td>
                            <td><input type="radio" name="bankTypeQuick" value="CEB" />
                            <img src="/images/banks/bank_10.png" width="120" height="28" style="border: none" alt="光大银行" /></td>
                             <td><input type="radio" name="bankTypeQuick" value="CIB" />
                            <img src="/images/banks/bank_08.png" width="120" height="28" style="border: none" alt="兴业银行" /></td>
                             <td><input type="radio" name="bankTypeQuick" value="CITIC" />
                            <img src="/images/banks/bank_13.png" width="120" height="28" style="border: none" alt="中信银行" /></td>
                             <td><input type="radio" name="bankTypeQuick" value="GDB" />
                            <img src="/images/banks/bank_14.png" width="120" height="28" style="border: none" alt="广发银行" /></td>
                        </tr>
                        <tr>
                          <td><input type="radio" name="bankTypeQuick" value="HXB" />
                            <img src="/images/banks/bank_20.png" width="120" height="28" style="border: none" alt="华夏银行" /></td>
                             <td><input type="radio" name="bankTypeQuick" value="PINGAN" />
                            <img src="/images/banks/bank_15.png" width="120" height="28" style="border: none" alt="平安银行" /></td> 
                             <td><input type="radio" name="bankTypeQuick" value="SPDB" />
                            <img src="/images/banks/bank_06.png" width="120" height="28" style="border: none" alt="浦发银行" /></td> 
                        </tr>
                        <tr>
                          <td height="10"></td>
                          <td height="10"></td>
                          <td height="10"></td>
                          <td height="10"></td>
                          <td height="10"></td>
                        </tr>
                      </table>
                    </div>
                  <style>
                  #div_chinapnr table td img{height: 24px;}
                  </style>
                  <!--网上银行-->
                  <div class="bank-list" id="div_chinapnr" style="display: block; ">
                    <table width="824" border="0" align="center" cellpadding="0" cellspacing="0" style=" width:100%">
                      <tr>
                        <td colspan="5"><strong>请选择支付方式：</strong></td>
                      </tr>
                      <tr>
                    <td><input type="radio" name="bankType" value="ICBC" id="rd_chinapnr_icbc" checked="checked" />
                    <img src="images/banks/bank_01.png" width="100" height="28" style="border: none" />
                    </td>
                    <td><input type="radio" name="bankType" value="CMB" />
                    <img src="images/banks/bank_02.png" width="120" height="28" style="border: none" />
                    </td>
                    <td><input type="radio" name="bankType" value="CCB" />
                    <img src="images/banks/bank_03.png" width="120" height="28" style="border: none" />
                    </td>
                    <td><input type="radio" name="bankType" value="ABC" />
                    <img src="images/banks/bank_04.png" width="120" height="28" style="border: none" />
                    </td>
                    <td><input type="radio" name="bankType" value="BOC" />
                    <img src="images/banks/bank_05.png" width="120" height="28" style="border: none" />
                    </td>
                </tr>
                <tr>
                    <td><input type="radio" name="bankType" value="SPDB" />
                    <img src="images/banks/bank_06.png" width="120" height="28" style="border: none" />
                    </td>
                    <td><input type="radio" name="bankType" value="SDB" />
                    <img src="images/banks/bank_07.png" width="120" height="28" style="border: none" />
                    </td>
                    <td><input type="radio" name="bankType" value="CIB" />
                    <img src="images/banks/bank_08.png" width="120" height="28" style="border: none" />
                    </td>
                    <!--
                    <td><input type="radio" name="bankType" value="BOBJ" /> <img src="images/banks/bank_09.png" width="120" height="28" style="border: none" />
                    </td>-->
                    <td><input type="radio" name="bankType" value="CEB" />
                    <img src="images/banks/bank_10.png" width="120" height="28" style="border: none" />
                    </td>
                    <td><input type="radio" name="bankType" value="BOCOM" />
                    <img src="images/banks/bank_11.png" width="120" height="28" style="border: none" />
                    </td>
                </tr>
                <tr>
                    
                    <td><input type="radio" name="bankType" value="CMBC" />
                    <img src="images/banks/bank_12.png" width="120" height="28" style="border: none" />
                    </td>
                    <td><input type="radio" name="bankType" value="PSBC" />
                    <img src="images/banks/bank_16.png" width="120" height="28" style="border: none" />
                    </td>
                    <td><input type="radio" name="bankType" value="BOS" />
                    <img src="images/banks/bank_19.png" width="120" height="28" style="border: none" />
                    </td>
                    <!--
                    <td><input type="radio" name="bankType" value="CITIC" /> <img src="images/banks/bank_13.png" width="120" height="28" style="border: none" />
                    </td>
                    <td><input type="radio" name="bankType" value="GDB" /> <img src="images/banks/bank_14.png" width="120" height="28" style="border: none" />
                    </td>
                    <td><input type="radio" name="bankType" value="PAB" /> <img src="images/banks/bank_15.png" width="120" height="28" style="border: none" />
                    </td>-->
                </tr>
                      <tr>
                        <td height="10"></td>
                        <td height="10"></td>
                        <td height="10"></td>
                        <td height="10"></td>
                        <td height="10"></td>
                      </tr>
                      <!-- <tr>
											<td colspan="2"><input type="radio" name="bankType" value="DEFAULT" /> <span style="float:left; margin-left:10px">使用汇付天下支付<font color="#999999"></font> </span></td>
											<td colspan="3"></td>
										</tr>-->
                    </table>
                  </div>
                  
                  <!-- 财付通div
								<div class="border_" id="div_ipay" style="display:none; ">
									<table border="0" align="center" cellpadding="0" cellspacing="0">
										<tr>
											<td colspan="5"><strong>请选择支付方式：</strong></td>
										</tr>
										<tr valign="middle">
											<td><input type="radio" name="bankType" value="01" id="rd_ipay_jieji" /> 人名币借记卡</td>
											<td><input type="radio" name="bankType" value="128" /> 信用卡支付</td>
											<td><input type="radio" name="bankType" value="04" /> IPS账户支付</td>
											<td><input type="radio" name="bankType" value="16" /> 电话支付</td>
											<td><input type="radio" name="bankType" value="32" /> 手机支付</td>
										</tr>
										<tr>
											<td colspan="5"><input type="radio" name="bankType" value="1024" /> 手机语音支付</td>
										</tr>
										<tr>
											<td height="10"></td>
											<td height="10"></td>
											<td height="10"></td>
											<td height="10"></td>
											<td height="10"></td>
										</tr>
										<tr>
											<td colspan="5"></td>
										</tr>
									</table>
								</div> --></td>
              </tr>
              <tr height="40">
                <td colspan="2"><div class="btn" id="btn_submit" align="center">
                    <form id="f_addRechargeInfo" target="_blank" action="addRecharge1.do" method="post" >
                      <input type="hidden" id="int_money" name="money" />
                      <input type="hidden" id="int_openBankId" name="openBankId" />
                      <input type="hidden" id="int_gateBusiId" name="gateBusiId" />
                      <input type="hidden" id="int_usrCustId" name="usrCustId" />
                      <input type="hidden" id="int_cardDcFlag" name="cardDcFlag" />
                    </form>
                    <div style=" margin-left:355px;"><a href="javascript:addRechargeInfo();" class="bt_blue"><span class="bt_blue_lft"></span><strong style="color: #ffffff;">充&nbsp;&nbsp;值</strong><span class="bt_blue_r"></span></a></div>
                    
                    
                    
                </div></td>
              </tr>
            </s:else>
            <tr height="10"></tr>
          </table>
                      <div class="sidebar_box">
                <div class="sidebar_box_top"></div>
                <div class="sidebar_box_content">
                <h3>温馨提示：</h3>
                <img src="newimages/info.png" alt="" title="" class="sidebar_icon_right" />
                <ul>
                <li>请根据借款项目合理安排充值金额，因资金账户由用户自己管理，合和年在线未收取用户任何费用，为防止套现，所充资金必须经投标回款后才能提现； </li>
                 <li>充值期间，请勿关闭浏览器，待充值成功并返回首页后，所充资金才能入账，如有疑问请联系客服； </li>
                  <li>充值需开通银行卡网上支付功能，如有疑问请咨询开户行客服；</li>
                   <li>支付限额由充值银行卡开户行决定。</li>
  
                </ul>                
                </div>
                <div class="sidebar_box_bottom"></div>
            </div>

        </div>
      </div>
    </div>
    <div style=" width:1170px; margin:0px auto">&nbsp;</div>
  </div>
</div>
<div class="cle"></div>

<!-- 引用底部公共部分 -->
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script> 
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script> 
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script> 
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script> 
<script type="text/javascript" src="css/popom.js"></script> 
<script>
		var GateBusiId = $("#GateBusiId");
		var bankList = $("div.bank-list");
		var int_GateBusiId = $("#int_GateBusiId");
		GateBusiId.on("click","input",function(){
			var value = $(this).val();
			int_GateBusiId.val(value);
			var index = 0;
			if(value==0){
				index = 0;
			} else if(value==1){
				index = 1;
			}
			bankList.hide().eq(index).show();
			
		});
		
	<%--查看充值记录--%>
		function showRechargeHistory() {
			$.jBox.open("iframe:queryFontRechargeHistory.do", "充值记录", 700, 400);
		}
		$(function() {
			//样式选中
			$(".wdzh_top_ul li").eq(2).addClass("wdzhxxk");
			$("#chinapnr").attr("checked", "checked");
			$("#rd_chinapnr_icbc").attr("checked", "checked");

		});

		function addRechargeInfo() {
			if ($("#money").val() == "") {
				$("#money_tip").html("请输入充值金额");
				return;
			} else if (!/^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/.test($("#money").val())) {
				$("#money_tip").html("请输入正确的充值金额，必须为大于1的数字");
				return;
			} else if ($("#money").val() < 1) {
                $("#money_tip").html("请输入正确的充值金额，必须为大于1的数字");
				return;
			} else if($("#money").val()>10000000){
				$("#money_tip").html("请输入正确的充值金额，必须为小于10000000的数字");	
				return;
			} else {
				$("#money_tip").html("");
			}

			var payType = 2;

		var bankType = "";
            var gateBusiId = "";
			/*
		var GateBusiIdCheck = GateBusiId.find("input:checked").val();
			if(GateBusiIdCheck==0){
				bankType = $("input[name='bankTypeQuick']:checked").val();
                gateBusiId="QP";
			} else if(GateBusiIdCheck==1) {
				bankType = $("input[name='bankType']:checked").val();
                gateBusiId="B2C";
			}
			*/
			bankType = $("input[name='bankType']:checked").val();
                gateBusiId="B2C";
			var cardDcFlag = $("input[name='cardDcFlag']:checked").val();
			if (bankType == "" || bankType == undefined) {
				alert("请选择充值银行");
				return;
			}
			if (cardDcFlag == "" || cardDcFlag == undefined) {
				alert("请选择银行卡类型");
				return;
			}
			/*if (!window.confirm("确定进行帐户充值")) {
				return;
			}*/

			var money = $("#money").val();
			var type = "";
			
		         $("#int_money").val(money);
		         $("#int_openBankId").val(bankType);
		         $("#int_usrCustId").val($("#usrCustId").val());
		 		 $("#int_cardDcFlag").val(cardDcFlag);
		 		 $("#int_gateBusiId").val(gateBusiId);
//		 		 $("#int_gateBusiId").val("B2C");

		         $("#f_addRechargeInfo").submit();

		}



		$("#registerChinaPnr").click(function() {
			$("#f_registerChinaPnr").submit();

		});
	</script>
</body>
</html>
