<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.hhn.util.DateUtil" %>
<% request.setAttribute("menuIndex", 2); %>
<!doctype html>
<html>
<head>
<%@ include file="../common/head.jsp" %>
        <meta name="viewport" content="width=640,target-densitydpi=320,user-scalable=no">
        <link rel="stylesheet" type="text/css" href="http://static.hehenian.com/m/css/base.css">
        <link rel="stylesheet" type="text/css" href="http://static.hehenian.com/m/css/record.css">
		<script src="http://static.hehenian.com/m/js/finance/balance.js"></script>
		<title>${channel_name}-收益记录</title>
	</head>
<body class="bg-2">
<header class="top-bar">
		<a href="javascript:history.go(-1);">收益记录
		<span class="icon-back">
			</span>
		</a>
	</header>
  <input type="hidden" id="flag" value="${flag}"/>
      <div class="container">
        <table>
          <tr>
            <th>订单</th>
            <th>购买产品</th>
            <th>购买金额</th>
            <th>收益金额</th>
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
			url:'http://m.hehenian.com/profile/queryTradePhone.do?flag='+flag+'&type=shouyi',
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
      if(st!=null) {
        for (var idx = 0; idx<st.length; idx++) {
          if (st[idx] != '' && st[idx] != 'undefined') {
            if(st[idx].agreementFileName) {
              htm += '<tr><td colspan="2" style="text-align:left;padding-left:22px;border:0;color: #CCC;">订单号:'+st[idx].orderId+'<a href="http://m.hehenian.com/product/queryAgreement.do?fileName=' + st[idx].agreementFileName + '" target="_blank" style="padding-left:10px;color:#08B2E6;">协议 </a></td><td colspan="2" style="text-align:right;padding-right:22px;border:0;color: #CCC;">收益日期:'+st[idx].updateTimeinfo+'</td></tr>'+'<tr><td>定期' + st[idx].productPeriod + '月</td>' +
              '<td>' + st[idx].productName + '</td>';
            }else{
              htm += '<tr><td colspan="2" style="text-align:left;padding-left:22px;border:0;color: #CCC;">订单号:'+st[idx].orderId+'</td><td colspan="2" style="text-align:right;padding-right:22px;border:0;color: #CCC;">收益日期:'+st[idx].updateTimeinfo+'</td></tr>'+'<tr><td>定期' + st[idx].productPeriod + '月</td>' +
              '<td>' + st[idx].productName + '</td>';
            }
            
             htm += '<td>￥' + st[idx].buyMoney + '</td>' +'<td>￥' + st[idx].shouyi + '</td></tr>';
          }
        }
      }else{
        htm += '<tr><td colspan="4" align="center">查无记录</td></tr>';
      }
      $("#listBox").html(htm);
    }
  </script>
  </body>
</html>