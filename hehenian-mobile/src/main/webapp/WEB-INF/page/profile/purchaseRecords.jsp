<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.hhn.util.DateUtil" %>
<% request.setAttribute("menuIndex", 2); %>
<!doctype html>
<html>
<head>
<%@ include file="../common/head.jsp" %>
        <meta name="viewport" content="width=640,target-densitydpi=320,user-scalable=no">
        <!-- <link rel="stylesheet" type="text/css" href="http://static.hehenian.com/m/css/base.css"> -->
        <link rel="stylesheet" type="text/css" href="http://static.hehenian.com/m/css/record.css">
		<script src="http://static.hehenian.com/m/js/finance/balance.js"></script>
		<title>${channel_name}-购买记录</title>
	</head>
<body class="bg-2">
<header class="top-bar">
		<a href="javascript:history.go(-1);">购买记录
		<span class="icon-back">
			</span>
		</a>
	</header>
  <input type="hidden" id="flag" value="${flag}"/>
      <div class="container">
        <table>
          <tr>
            <th>订单</th>
            <th>买入金额</th>
            <th>收益</th>
            <th>状态</th>
            <th>操作</th>
          </tr>
          <tbody id="listBox">
          </tbody>
        </table>
      </div>
      <%@ include file="../common/tail.jsp" %>
      <script type="text/javascript">
    $(function(){
    var flag = $("#flag").val();
      getData(flag);
    });
    function getData(flag){
    $.ajax({ 
			type:"POST",
			dataType: "json",			
			url:'http://m.hehenian.com/profile/queryTradePhone.do?flag='+flag,
			success:getDataSuccess, //非必须
			error:function(data){
				alert("why?");
			}
		});
    }
    function getDataSuccess(data){
      /* if(data.returnCode != 0){
        alert(data.messageInfo);return;
      } */
      //var st = data.data.tradeList;//list
      var st = data.data;//list
      var htm ="";
      if(st!=null && st.length > 0) {
        for (var idx = 0; idx<st.length; idx++) {
          if (st[idx] != '' && st[idx] != 'undefined') {
            if(st[idx].agreementFileName) {
              htm += '<tr><td colspan="2" style="text-align:left;padding-left:22px;border:0;color: #CCC;">订单号:'+st[idx].trade_id+'<a href="http://m.hehenian.com/product/queryAgreement.do?fileName=' + st[idx].agreementFileName + '" target="_blank" style="padding-left:10px;color:#08B2E6;">协议 </a></td><td colspan="3" style="text-align:right;padding-right:22px;border:0;color: #CCC;">购买时间:'+st[idx].trade_time_info+'</td></tr>'+'<tr><td>' + st[idx].product_name + st[idx].period + '月</td>' +
              '<td>￥' + st[idx].trade_amount + '</td>';
            }else{
              htm += '<tr><td colspan="2" style="text-align:left;padding-left:22px;border:0;color: #CCC;">订单号:'+st[idx].trade_id+'</td><td colspan="3" style="text-align:right;padding-right:22px;border:0;color: #CCC;">购买时间:'+st[idx].trade_time_info+'</td></tr>'+'<tr><td>定期' + st[idx].period + '月</td>' +
              '<td>￥' + st[idx].trade_amount + '</td>';
            }
            var status = st[idx].trade_status;
            if('SUCCESS'==status){
              if(st[idx].canRedeem==1){
                htm += '<td>年化' + st[idx].rate * 100 + '%</td>' +
                '<td>投资中</td>'+
                '<td><a href="http://m.hehenian.com/profile/redeemPagePhone.do?tradeId='+st[idx].trade_id+'" target="_self" style="color:blue;">提前赎回</a></td></tr>';
              }else if(st[idx].canRedeem==2){
                htm += '<td>年化' + st[idx].rate * 100 + '%</td>' +
                '<td>自动赎回</td>'+
                '<td>&nbsp;</td></tr>';
              }else{
                htm += '<td>年化' + st[idx].rate * 100 + '%</td>' +
                '<td>投资中</td>'+
                '<td><font color="#CCC">锁定期</font></td></tr>';
              }
            }else if('PREREDEEM'==status){
              htm += '<td>' + st[idx].inCome +'</td>' +
              '<td>赎回处理中</td>'+
              '<td><font color="#CCC">提前赎回</font></td></tr>';
            }else if('REDEEM'==status){
              htm += '<td>' + st[idx].inCome +'</td>' +
              '<td>赎回成功</td>'+
              '<td><font color="#CCC">提前赎回</font></td></tr>';
            }else{
              htm += '<td>年化' + st[idx].rate * 100 + '%</td>' +
              '<td>投资失败</td>'+
              '<td>&nbsp;</td></tr>';
            }
          }
        }
      }else{
        htm += '<tr><td colspan="5" align="center">查无记录</td></tr>';
      }
      $("#listBox").html(htm);
    }
  </script>
  </body>
</html>