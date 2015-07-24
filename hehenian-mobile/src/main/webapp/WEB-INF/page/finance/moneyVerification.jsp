<%@ page contentType="text/html; charset=utf-8"%>
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
  <body>
      <div class="inputDiv">
        <span>验证金额</span><span style="margin:0">0.</span>
        <input type="tel" placeholder="请输入验证金额" maxlength="2" id="checkMoney" oninput="changeBtnState()" onpropertychange="changeBtnState()"/>
      </div>
      <a class="maimeng">您只有3次输入机会，请确认后再输入</a>
      <div class="bindNotice"><span style="color:#ff9833">*我们已经给您转入验证金额，如收不到银行短信，请登录网银查询交易明细</span><br/>如无法验证，请<a style="color:#08b2e6" id="modifySpan" href="">修改银行卡</a></div>
      <div class="yuqishouyi" style="padding-left:50px">
        <div class="select" id="select" onclick="selectAgreeMent()"></div>
        同意《<a style="color:#08b2e6" href="http://m.hehenian.com/balance/withholdingAgreement.do">合和年代扣协议</a>》
      </div>
      <div class="nextBtn disable" id="nextBtn" onclick="checkMoney()">
        下一步
      </div>
      <div class="cover" id="cover"></div>
      <div class="pop" id="pop">
        <div class="title" id="popTilte"></div>
        <div class="act">
          <a class="center" id="act-right-Url"><span class="right centerSpan" id="act-right"></span></a>
        </div>
      </div>
       <%@ include file="../common/tail.jsp" %>
  </body>
  <script type="text/javascript" src="http://static.hehenian.com/m/js/jquery-1.11.1.min.js"></script>
  <script type="text/javascript" src="http://static.hehenian.com/m/js/common.js"></script>
  <script type="text/javascript" src="http://static.hehenian.com/m/js/module.js"></script>
  <script type="text/javascript">
    var bankCode = getUrlParam("bankCode");
    var userAccount = getUrlParam("userAccount");
    var v = getUrlParam("v");
    $(function(){
      $('#userAccount').text(userAccount.substr(0,3)+'...'+userAccount.substr(userAccount.length-3,userAccount.length-1));
      if(v==1){
        $("#modifySpan").attr("href","http://m.hehenian.com/finance/bindCard.do?v=1");
      }else{
        $("#modifySpan").attr("href","http://m.hehenian.com/finance/bindCard.do");
      }
    });
    function selectAgreeMent(){
      if($('#select').attr('class') == "select"){
        $('#select').attr('class','select unselect');
        $('#nextBtn').addClass('disable');
      }else{
        $('#select').attr('class','select');
        if($('#checkMoney').val()){
          $('#nextBtn').removeClass('disable');
        }
        
      }
    }
    function showPop(){
      $('#cover').show();
      $('#pop').show();
    }
    function hidePop(){
      $('#cover').hide();
      $('#pop').hide();
    }
    function changeBtnState(){
        if($('#select').attr('class')=='select'&&$('#checkMoney').val()){
          $('#nextBtn').removeClass('disable');
        }
    }
    function checkMoney(){
      if($("#nextBtn").attr("class")!='nextBtn'){
        return;
      }
      var checkMoney = $('#checkMoney').val();
      if(checkMoney == ""){
        newAlert('金额填写有误');
        return;
      }
      if($("#select").attr("class")!='select'){
        newAlert('请先同意合和年代扣协议');
        return;
      }
      var checkMoney = "0."+checkMoney;
      var sUrl = "http://m.hehenian.com/finance/verifyCard.do";
      $('#nextBtn').addClass('disable');
      ajax({
        waitTagId:'center',
        url:sUrl,
        data:{money:checkMoney,account:userAccount},
        type:'post',//非必须.默认get
        dataType:'json',//非必须.默认text
        async:true,//非必须.默认true
        cache:false,//非必须.默认false
        timeout:30000,//非必须.默认30秒
        success:checkMoneySuccess,//非必须
        complete : function(XMLHttpRequest,status){ //请求完成后最终执行参数
          if(status=='timeout') {//超时,status还有success,error等值的情况
            ajaxTimeOut.abort(); //取消请求
            $('#nextBtn').removeClass('disable');
            newAlert("系统处理超时,请稍后重试");
          }
        }
      });
    }
    function checkMoneySuccess(data){
      if(data.returnCode==0){
          window.location.href ="http://m.hehenian.com/profile/managerCard.do";
      }else if(data.returnCode==2){
        $('#popTilte').text('验卡次数超过3次，请重新绑定');
        $('#act-right').text('确定');
        if(v==1){
          $('#act-right-Url').attr('href','http://m.hehenian.com/finance/bindCard.do?v=1');
        }else{
          $('#act-right-Url').attr('href','http://m.hehenian.com/finance/bindCard.do');
        }
        showPop();
      }else{
        $('#nextBtn').removeClass('disable');
        newAlert(data.messageInfo);
      }
    }
  </script>
</html>