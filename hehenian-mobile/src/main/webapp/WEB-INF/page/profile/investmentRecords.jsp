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
		<title>${channel_name}-投资记录</title>
	</head>
<body class="bg-2">
<header class="top-bar">
		<a href="javascript:history.go(-1);">投资记录
		<span class="icon-back">
			</span>
		</a>
	</header>
  <input type="hidden" id="flag" value="${flag}"/>
      <div class="container">
        <table>
          <tr>
            <th>标的编号</th>
            <th>标的名称</th>
            <th>交易金额</th>
            <th>交易时间</th>
            <%--<th>利率</th>--%>
            <%--<th>预期收益</th>--%>
          </tr>
          <tbody id="listBox">
          </tbody>
        </table>
      </div>
       <%@ include file="../common/tail.jsp" %>
  </body>
  <script type="text/javascript">
    $(function(){
    var flag = $("#flag").val();
      getData(flag);
    });
    function getData(flag){
    $.ajax({ 
			type:"POST",
			dataType: "json",			
			url:'http://m.hehenian.com/profile/getProductPhone.do?flag='+flag,
			success:getDataSuccess, //非必须
			error:function(data){
				alert("why?");
			}
		});
    }
    function getDataSuccess(data){
      /* if(data.returnCode == 1){
        newAlert(data.messageInfo);return;
      }else if(data.returnCode==2){
        self.location.href = data.data;
      } */
      var st = data.productList;//list
      var htm ="";
      if(st!=null && st.length > 0) {
        for (var idx = 0; idx<st.length; idx++) {
          if (st[idx] != '' && st[idx] != 'undefined') {
            htm += '<tr><td' + st[idx].product_id + '</td>' +
            '<td>' + st[idx].product_id + '</td>' +
            '<td>' + st[idx].product_name + '</td>' +
            '<td>￥' + st[idx].trade_amount + '</td>' +
            '<td>' + st[idx].trade_time_info + '</td></tr>';
//            '<td>' + st[idx].annual_rate + '%</td>'+
//            '<td>￥' + st[idx].receiveAmount + '</td></tr>';
          }
        }
      }else{
        htm += '<tr><td colspan="4" align="center">查无记录</td></tr>';
      }
      $("#listBox").html(htm);
    }
  </script>
</html>