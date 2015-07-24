<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
    <jsp:include page="/include/cf-head.jsp"></jsp:include>
    </head>
    <body>
    <jsp:include page="/include/cf-top.jsp"></jsp:include>
    <div class="s_sur_ix main el_container" style=" overflow:hidden; margin-bottom:20px;"> 
      <!--左侧-->
      <div class=" nr_left">
        <div style=" padding:15px 30px;border:1px solid #CCC; background:#f9f9f9;"><font color="#FF6600"><strong>温馨提示</strong></font>：平台推广期间，所有第三方支付公司收取的充值费用将由平台承担，推广期结束时间将另行通知。 </div>
            <div  style=" padding:15px 30px;border:1px solid #CCC; background:#f9f9f9; margin-top:30px;">
            <table width="100%" style=" font-size:14px;">
              <tr height="20" style="padding-top: 20px;">
                <td width="150" height="60" align="right">真实姓名：</td>
                <td><s:if test="#request.realName==null || #request.realName==''"> <a href="cf-owerInformationInit.do" style="color: #E94718;font-size: 12px;">您还未填写个人真实信息,点击填写</a> </s:if>
                  <s:else>${realName }</s:else></td>
              </tr>
              <tr>
                <td height="60" align="right">帐<span style="margin-left: 21px;">&nbsp;</span>号：</td>
                <td>
                  ${email }
                  <input value="${map.usrCustId}" id="usrCustId" type="hidden"></td>
              </tr>
              <s:if test="#request.map.usrCustId =='' || #request.map.usrCustId ==-1">
                <tr height="40">
                  <td height="60" colspan="2" style="text-align:center"><div class="btn" id="btn_submit"> <span style="margin-left:20px;color:#3b9cfd;">您还不是汇付天下会员,请先注册汇付天下，第三方资金托管，100%保障投资者的资金安全!</span><br />
                    <br />
               
                      <form id="f_registerChinaPnr" target="_blank" action="/registerChinaPnr.do" method="post" >
                      <input type="button" style="width: 238px; height: 40px; border: 0px; background: #892E92; font-size: 16px; color: #FFF; margin-left: 0px;" id="registerChinaPnr" value="点击注册" />
                    </form>
                    </div></td>
                </tr>
              </s:if>
              <s:else>
                <tr height="30">
                  <td height="60" align="right">充值金额：</td>
                  <td><input id="money" type="text" class="inp140"  style=" width:200px; height:30px; padding-left:10px;"/>
                    <span style="color: red; float: none;" id="money_tip" class="formtips"></span></td>
                </tr>
                <tr>
                  <td height="60" align="right">充值类型：</td>
                  <td><input type="radio" name="rechargeType" id="chinapnr" checked="checked" />
                    <u>汇付天下</u>
                    <%-- <input type="radio" name="rechargeType" id="ipay" /> <u>在线充值</u>--%></td>
                </tr>
                <tr>
                  <td height="60" align="right">银行卡类型：</td>
                  <td><input type="radio" value="D" name="cardDcFlag" id="cardDcFlagC" checked/>
                    <u>借记卡</u> 
                    <!--<input type="radio" value="C" name="cardDcFlag" id="cardDcFlagD" /> <u>信用卡</u></td>-->
                </tr>
                <!--
                 <tr>
                  <td height="60" align="right">充值方式：</td>
                  <td id="GateBusiId"> <input type="radio" name="GateBusiId" id="GateBusiId0" value="0" checked />
                  <label for="GateBusiId0"><u>快捷充值</u></label>
                   <input type="radio" name="GateBusiId" id="GateBusiId1" value="1" />
                   <label for="GateBusiId1"><u>网上银行</u></label> 
                </tr>
                -->
                <input type="hidden" name="GateBusiId" id="GateBusiId1" value="1" />
                <tr height="40">
                  <td height="80" colspan="2"><div class="btn" id="btn_submit" align="center">
                      <form id="f_addRechargeInfo" target="_blank" action="/chinapnrPayment.do" method="post" >
                      <input type="hidden" id="int_money" name="money" />
                       <input type="hidden" id="int_GateBusiId" name="gateBusiId" value="B2C"/>
                      <input type="hidden" id="int_openBankId" name="openBankId" />
                      <input type="hidden" id="int_usrCustId" name="usrCustId" />
                      <input type="hidden" id="int_cardDcFlag" name="cardDcFlag" />
                    </form>
                  
                      <input type="button" style="width: 238px; height: 40px; border: 0px; background: #892E92; font-size: 16px; color: #FFF; margin-left: 0px;"  value="充值"  onclick="javascript:addRechargeInfo();"/>
                   
                   </div></td>
                </tr>
        
                <tr>
                  <td colspan="2"><!-- 汇付天下div -->
                  <!--快捷充值-->
                  <div class="bank-list border_" style="display: none; ">
                      <table border="0" align="center" cellpadding="0" cellspacing="0" style="margin-left: 10; width:700px">
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
                    <!--网上银行-->
                    <div class="bank-list border_" id="div_chinapnr" style="display: block; width:700px; ">
                      <table border="0" align="center" cellpadding="0" cellspacing="0" style="margin-left: 10;">
                        <tr>
                          <td colspan="5"><strong>请选择支付方式：</strong></td>
                        </tr>
                        <tr>
                          <td><input type="radio" name="bankType" value="ICBC" id="rd_chinapnr_icbc" checked="checked" />
                          <img src="/images/banks/bank_01.png" width="120" height="28" style="border: none" alt="工商银行" /></td>
                          <td><input type="radio" name="bankType" value="CMB" />
                          <img src="/images/banks/bank_02.png" width="120" height="28" style="border: none" alt="招商银行" /></td>
                          <td><input type="radio" name="bankType" value="CCB" />
                          <img src="/images/banks/bank_03.png" width="120" height="28" style="border: none" alt="建设银行" /></td>
                          <td><input type="radio" name="bankType" value="ABC" />
                          <img src="/images/banks/bank_04.png" width="120" height="28" style="border: none" alt="农业银行" /></td>
                          <td><input type="radio" name="bankType" value="BOC" />
                          <img src="/images/banks/bank_05.png" width="120" height="28" style="border: none" alt="中国银行" /></td>
                        </tr>
                        <tr>
                          <td><input type="radio" name="bankType" value="SPDB" />
                          <img src="/images/banks/bank_06.png" width="120" height="28" style="border: none" alt="浦发银行" /></td>
                          <td><input type="radio" name="bankType" value="SDB" />
                          <img src="/images/banks/bank_07.png" width="120" height="28" style="border: none" alt="深圳发展银行" /></td>
                          <td><input type="radio" name="bankType" value="CIB" />
                          <img src="/images/banks/bank_08.png" width="120" height="28" style="border: none" alt="兴业银行" /></td>
                          <td><input type="radio" name="bankType" value="CEB" />
                          <img src="/images/banks/bank_10.png" width="120" height="28" style="border: none" alt="光大银行" /></td>
                          <td><input type="radio" name="bankType" value="BOCOM" />
                          <img src="/images/banks/bank_11.png" width="120" height="28" style="border: none" alt="交通银行" /></td>
                        </tr>
                        <tr>
                          <td><input type="radio" name="bankType" value="CMBC" />
                          <img src="/images/banks/bank_12.png" width="120" height="28" style="border: none" alt="民生银行" /></td>
                          <td><input type="radio" name="bankType" value="PSBC" />
                          <img src="/images/banks/bank_16.png" width="120" height="28" style="border: none" alt="中国邮政储蓄银行" /></td>
                          <td><input type="radio" name="bankType" value="BOS" />
                          <img src="/images/banks/bank_19.png" width="120" height="28" style="border: none" alt="上海银行" /></td>
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
                    </td>
                </tr>
              </s:else>
              <tr height="10"></tr>
        </table></div>
        <div style=" margin-top:30px;"> 
          <span id="p_about" class="tips2" style="border:none;"><strong style="color:#F60">温馨提示：</strong>
              <p>1)  请根据借款项目合理安排充值金额，因资金账户由用户自己管理，e理财未收取用户任何费用， </p>
              <p> &nbsp; &nbsp; 为防止套现，所充资金必须经投标回款后才能提现； </p>
              <p>2) 	充值期间，请勿关闭浏览器，待充值成功并返回首页后，所充资金才能入账，如有疑问请联系客服； </p>
              <p>3) 	充值需开通银行卡网上支付功能，如有疑问请咨询开户行客服；</p>
              <p>4) 支付限额由充值银行卡开户行决定 。</p>
              </span> </div>

          
          <!--右侧-->  
      </div>
      <jsp:include page="/include/cf-right.jsp"></jsp:include>
    </div>
    <jsp:include page="/include/cf-footer.jsp"></jsp:include>
    <input type="hidden" value="${realName }" id="realName"/>
</body>
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
				$("#money_tip").html("请输入正确的充值金额，必须为大于0的数字");
				return;
			} else if ($("#money").val() < 0.01) {
				$("#money_tip").html("最小金额不能低于0.01");
				return;
			} else {
				$("#money_tip").html("");
			}

			var payType = 2;
	<%--if ($("#chinapnr").attr("checked") == "checked") {
				payType = 1;
			} else if ($("#ipay").attr("checked") == "checked") {
				payType = 2;
			} else {
				alert("请选择充值类型");
				return;
			}--%>
			var bankType = "";
			/*
			var GateBusiIdCheck = GateBusiId.find("input:checked").val();
			if(GateBusiIdCheck==0){
				bankType = $("input[name='bankTypeQuick']:checked").val();	
			} else if(GateBusiIdCheck==1) {
				bankType = $("input[name='bankType']:checked").val();
			}
			*/
			bankType = $("input[name='bankType']:checked").val();
			var cardDcFlag = $("input[name='cardDcFlag']:checked").val();
			if (bankType == "" || bankType == undefined) {
				alert("请选择充值银行");
				return;
			}
			if (cardDcFlag == "" || cardDcFlag == undefined) {
				alert("请选择银行卡类型");
				return;
			}
			if (!window.confirm("确定进行帐户充值")) {
				return;
			}

			var money = $("#money").val();
			var type = "";
			
		         $("#int_money").val(money);
		         $("#int_openBankId").val(bankType);
		         $("#int_usrCustId").val($("#usrCustId").val());
		 		 $("#int_cardDcFlag").val(cardDcFlag);
		         
		         $("#f_addRechargeInfo").submit();
		         /*
		       	 $.shovePost("chinapnrPayment.do",$.$(param),function(data){
		       		 if(data.length<30){
		       			 alert(data);
		       			 return;
		       		 }
		       		 $("#chongzhi").html(data);
		       	 });
		       	 */
		}

<%--		function registerChinaPnr() {--%>
<%--			//alert('${request.usrCustId}');--%>
<%--			if('${request.usrCustId}' == null || '${request.usrCustId}' ==-1)--%>
<%--			{--%>
<%--				alert("您还不是汇付会员，请先注册汇付账户!");--%>
<%--				return;--%>
<%--			}--%>
<%--			 $.shovePost("registerChinaPnr.do",null,function(data){--%>
<%--	       		 if(data.length<30){--%>
<%--	       			 alert(data);--%>
<%--	       			 return;--%>
<%--	       		 }--%>
<%--	       		 $("#zhuce").html(data);--%>
<%--	       	 });--%>
<%--		}--%>

		$("#registerChinaPnr").click(function() {
			var realName=$("#realName").val();
			if(realName==''){
				alert("请先完善个人资料!");
				window.location.href="cf-wszl.do";
			}else{
				window.open("/registerChinaPnr.do");
			}
		});
		
	</script>
</html>