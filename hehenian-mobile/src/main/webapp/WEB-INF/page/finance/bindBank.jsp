<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.hhn.util.DateUtil" %>
<% request.setAttribute("menuIndex", 2); %>
<!doctype html>
<html>
<head>
<%@ include file="../common/head.jsp" %>
    <meta name="viewport" content="width=640,target-densitydpi=320,user-scalable=no">
    <link rel="stylesheet"  href="http://static.hehenian.com/m/css/base.css">
    <link rel="stylesheet" href="http://static.hehenian.com/m/css/bindBank.css">
    <title>${channel_name}-银行卡绑定</title>
  </head>
  <body class="bg-2">
<header class="top-bar">
		<a href="http://m.hehenian.com/balance/index.do">绑定银行卡
		<span class="icon-back">
			</span>
		</a>
	</header>
    <a class="maimeng">温馨提示:您最多可绑5张银行卡</a>
    <div class="inputDiv">
        <span>账户名</span>
        <span id="realSpan">
               <c:choose>
                   <c:when test='${not empty result.realName and not empty result.idNo}'>
                       ${result.realName}（${result.idNo}）
                   </c:when>
                   <c:otherwise>
                      <a class="xiugai" href="http://m.hehenian.com/account/realAuth.do?fromUrl=http://m.hehenian.com/finance/bindCard.do" style="color: #0DAAAD; text-decoration: underline;">点击进行实名认证</a>
                   </c:otherwise>
               </c:choose>
        </span>
    </div>
      <div class="inputDiv" onclick="showSelectBox()">
        <span>开户银行</span>
        <input type="text" placeholder="请选择银行" id="bankName" disabled="disabled" />
        <input type="hidden" id="bankCode"/>
        <img src="http://static.hehenian.com/m/img/right.png" style="position: absolute;right: 22px;top:30px" />
      </div>
     <div class="inputDiv">
        <span>卡号　　</span>
        <input type="text" placeholder="请输入银行卡号(借记卡)" id="userAccount" onkeyup="formatBankNo(this)" onkeydown="formatBankNo(this)" onblur="$('#userAccountFormatTip').hide()" oninput="formatInput(this)" onpropertychange="formatInput(this)"/>
        <div class="accountNoO" id="userAccountFormatTip"></div>
      </div>
      <a class="maimeng">合和年将向您指定银行卡转入一笔小额验证资金， 请您查询银行卡收支明细，输入该笔金额进行账户验证 </a>
      <div class="nextBtn disable" id="nextBtn" onclick="subBankMsg()">
        下一步
      </div>
      <div class="cover" id="cover"></div>
      <div class="bankSelectBox" id="bankSelectBox">
        <div class="header">
          <span class="cancel" onclick="hideSelectBox()">取消</span>
          <span class="complete" onclick="hideSelectBox()">完成</span>
          <span style="clear: both;"></span>
        </div>
        <div class="selectBody">
          <span onclick="selectBank(this,'0104')">中国银行</span>
          <span onclick="selectBank(this,'0103')">农业银行</span>
          <span onclick="selectBank(this,'0105')">建设银行</span>
          <%--<span onclick="selectBank(this,'0301')">交通银行</span>--%>
          <span onclick="selectBank(this,'0308')">招商银行</span>
          <span onclick="selectBank(this,'0403')">邮储银行</span>
          <span onclick="selectBank(this,'0302')">中信银行</span>
          <span onclick="selectBank(this,'0304')">华夏银行</span>
          <!-- <span onclick="selectBank(this,'0305')">民生银行</span> -->
          <span onclick="selectBank(this,'0307')">平安银行</span>
          <span onclick="selectBank(this,'0310')">浦发银行</span>
          <%--<span onclick="selectBank(this,'0303')">光大银行</span>--%>
        </div>
      </div>
      
      	<!--未经实名验证-->
	<section class="buy-pop hide" id="authModal">
		<div class="none-auth">
			<div class="na-head">
				重要提示 <span class="close"></span>
			</div>
			<div class="na-tips">
				<p>您尚未完成实名认证，无法进行绑卡。</p>
				<a href='http://m.hehenian.com/account/realAuth.do?fromUrl=http://m.hehenian.com/finance/bindCard.do'> <span
					class="na-btn">立即认证</span>
				</a>
			</div>
		</div>
	</section>
	
       <%@ include file="../common/tail.jsp" %>
  <script type="text/javascript" src="http://static.hehenian.com/m/js/jquery-1.11.1.min.js"></script>
  <script type="text/javascript" src="http://static.hehenian.com/m/js/common.js"></script>
  <script type="text/javascript" src="http://static.hehenian.com/m/js/module.js"></script>
  <script type="text/javascript">
  
	//认证弹框
	var authModal = new Modal({
		id : '#authModal',
		events : {
			'.close click' : function(modal) {
				modal.close();
			}
		}
	});
	
	
    var v = getUrlParam("v");
    function showSelectBox(){
      $('#cover').show();
      $('#bankSelectBox').show();
    }
    function hideSelectBox(){
      $('#cover').hide();
      $('#bankSelectBox').hide();
    }
    function selectBank(ob,code){
      var bankName = $(ob).text();
      $('#bankName').val(bankName);
      $('#bankCode').val(code);
      if($('#userAccount').val()){
        $('#nextBtn').removeClass('disable');
      }
      hideSelectBox();
    }
    function subBankMsg(){
      if($('#nextBtn').attr('class')!='nextBtn'){
        return;
      }
      <c:if test="${empty result.realName or empty result.idNo}">
//         newAlert("未实名认证，请先实名认证！");
		authModal.show();
        return;
      </c:if>
      var bankCode = $('#bankCode').val();
      var userAccount  = $('#userAccount').val().replace(/[ ]/ig,"");
      if(bankCode==''){
          newAlert('请选择开户银行');
          return;
      }
      var reg19 = /^\d{19}$/g;
      var reg16 = /^\d{16}$/g;
      if(!reg19.test(userAccount) && !reg16.test(userAccount)){
        newAlert('银行账号输入有误');
        return;
      }
      var stParam = [];
      stParam.push('bankCode='+bankCode);
      stParam.push('userAccount='+userAccount);
      $('#nextBtn').addClass('disable');
      ajax({
        waitTagId:'center',
        url:'http://m.hehenian.com/finance/bindCardPhone.do',
        type:'post',//非必须.默认get
        data:stParam.join('&'),
        dataType:'json',//非必须.默认text
        async:true,//非必须.默认true
        cache:false,//非必须.默认false
        timeout:15000,//非必须.默认30秒
        success:subBankMsgSuccess,//非必须
        complete : function(XMLHttpRequest,status){ //请求完成后最终执行参数
          if(status=='timeout'){//超时,status还有success,error等值的情况
            ajaxTimeOut.abort(); //取消请求
            $('#nextBtn').removeClass('disable');
            newAlert("系统处理超时,请稍后重试");
          }
        }
      },bankCode,userAccount); 
    }
    function subBankMsgSuccess(data,bankCode,userAccount){
      if(data.returnCode == 0){
        if(v==1){
            window.location.href ="http://m.hehenian.com/finance/moneyVerify.do?bankCode="+bankCode+"&userAccount="+userAccount+"&v=1";
        }else{
            window.location.href ="http://m.hehenian.com/finance/moneyVerify.do?bankCode="+bankCode+"&userAccount="+userAccount;
        }
      }else if(data.returnCode==-1){
          newAlertOf("验证金额已发送，请耐心等待银行处理",'http://m.hehenian.com/profile/managerCard.do');
      }else{
          $('#nextBtn').removeClass('disable');
          newAlert(data.messageInfo);
      }
    }
    function formatInput(ob){
        var sID = $.trim(ob.id);
        var sValue = $.trim($(ob).val());
        sValue = sValue.replace(/\s/g,"");
        sValue = sValue.substring(0,19); 
        if(sID.indexOf("user") != -1){
            sValue = sValue.replace(/.{4}/g,function(str){
                return str+' '; 
            });
            sValue = $.trim(sValue);
            if(sValue.length > 25){
                sValue = $.trim(sValue.substr(0,25))+'...';
            }
        }else{
            if(sValue.length > 16){
                sValue = $.trim(sValue.substr(0,16))+'...';
            }   
        }
        sValue = $.trim(sValue);
        if(sValue != "")$('#'+sID+'FormatTip').html(sValue).show();
        else $('#'+sID+'FormatTip').hide();
        if($('#bankName').val()&&$('#userAccount').val()){
          $('#nextBtn').removeClass('disable');
        }
        if(!$('#userAccount').val()){
          $('#nextBtn').addClass('disable');
        }
    }
  </script>
  </body>
</html>