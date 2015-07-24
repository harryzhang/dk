<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
		$(function() {
			$(".header_two_right_ul li").hover(function() {
				$(this).find("ul").show();
			}, function() {
				$(this).find("ul").hide();
			})
			$(".tzlc_gjx_all ul li:first").addClass("tzlcgjx")
			$(".tzlc_gjx_all ul li").click(function() {
				var s = $(".tzlc_gjx_all ul li").index(this);
				$(".tzlc_gjx_all ul li").removeClass("tzlcgjx");//失去焦点
				$(this).addClass("tzlcgjx");//设为焦点
				$(".tzlc_gjx_one").hide();
				$(".tzlc_gjx_one").eq(s).show();
			})
		})
	</script>
<div class="wytz_center">
  <div class="tzlc_gjx_all">
    <!--<div class="tzlc_gjx_one" style="display:block;">
                    <div class="tzlc_gjx_one_a">
                        <p><span style="color:#e94718;">借款成本计算器：为您计算出您在网站借款所花费的借款成本</span></p><br><br>
                        <P>
                            <span style="font-size:16px;">成本设置：</span>
                            <table width="989" border="0">
                            	<tr height="20"></tr>
                                <tr>
                                    <td width="259" align=""><span>借款金额：</span><input type="text" /> <span>元</span></td>
                                    <td width="269" align=""><span>还款方式：</span><select><option>按月还款</option></select></td>
                                    <td width="251" align=""><span>年利率：</span><input type="text" /> <span>%</span></td>
                                    <td width="200"></td>
                                </tr>
                            	<tr height="20"></tr>
                                <tr>
                                    <td width="259" align=""><span>借款期限：</span><input type="text" /> <span>月</span></td>
                                    <td width="269" align=""><span>投资奖励：</span><input type="text" /> <span>%</span></td>
                                    <td width="251" align="">&nbsp;</td>
                                    <td width="200"></td>
                                </tr>
                            	<tr height="20"></tr>
                                <tr>
                                    <td width="259"><input type="checkbox" />包括手续费<input type="button"  value="开始计算"></td>
                                    <td width="269">&nbsp;</td>
                                    <td width="251">&nbsp;</td>
                                    <td width="200"></td>
                                </tr>
                            	<tr height="20"></tr>
                            </table>
                        </P>
                        <span style="font-size:16px;">借款成本描述：</span>
                        <table width="989" border="0">
                            <tr height="20"></tr>
                            <tr>
                                <td width="259"><span>投资奖励：</span> <span>￥0.00</span></td>
                                <td width="269"><span>借款手续费：</span> <span>￥0.00</span></td>
                                <td width="471"></td>
                            </tr>
                            <tr height="10"></tr>
                            <tr>
                                <td width="259"><span>利息：</span> <span>￥0.00</span></td>
                                <td width="269"><span>提现手续费：</span> <span>￥0.00</span></td>
                                <td width="471"></td>
                            </tr>
                            <tr height="10"></tr>
                            <tr>
                                <td width="259"><span>合计支出借款费用：</span> <span>￥0.00</span></td>
                                <td width="269"><span>借款成功后的提现金额</span> <span>￥0.00</span></td>
                                <td width="471"></td>
                            </tr>
                        </table>
                    </div>
                </div>
                -->
    <div class="tzlc_gjx_one" style="display:block;">
      <div class="tzlc_gjx_one_a"> <span>借款金额：</span>
        <input id="borrowSum" type="text" class="inp100x" />
        <span>年利率：</span>
        <input id="yearRate" type="text" class="inp100x" />
        % <span>借款期限：</span>
        <input
						id="borrowTime" type="text" class="inp100x" />
        <span id="timeType">月</span> <span>还款方式：</span>
        <select id="repayWay" name="select">
          <option selected="selected" value="-1">等额本金</option>
         <!--  <option value="0">按月等额本息还款</option>
          <option value="1">先息后本</option> -->
          <%--						<option value="2">一次还本付</option>--%>
        </select>
        <input type="button" onclick="javascript:rateCalculate();" value="计算" />
      </div>
      <div class="gjxtab"> <span style="color: #e94718; float: none;padding-left: 18px" class="formtips" id="show_error"></span> <span id="showPayTable"></span> </div>
      <div class="tzlc_gjx_one_b"> <span>每月将偿还：</span> <strong id="aa">元</strong> <span>月利率：</span> <strong id="bb">%</strong> <span>还款本息总额：</span> <strong id="cc">元</strong> </div>
    </div>
    <div class="lcmain">
      <div class="lcmain_l" style="float:none; margin-left:0px; width:auto; padding:10px 25px 50px;">
        <div class="box">
          <div class="tzlc_gjx_one">
            <div class="tzlc_gjx_one_a">
              <p> <span style="color:#666;">在下面输入框中输入您要查询的IP地址,点击查询按钮即可查询该IP所在地区。</span> </p>
              <br/>
              <br/>
              <p> <span>IP地址：</span>
                <input type="text" class="inp280" id="ipAddress" />
                <input type="button" onclick="queryIP();" value="查询" />
              </p>
            </div>
            <div class="tzlc_gjx_one_b">
              <p class="gjjg"> <span>查询结果：</span> <span id="queryIPInfo" style="color: #e94718;">暂无查询信息</span> </p>
            </div>
            <div class="box"> <span id="borrow_clause"></span> </div>
          </div>
          <div class="tzlc_gjx_one">
            <div class="tzlc_gjx_one_a">
              <P > <span>请输入手机号码：</span>
                <input type="text" class="inp280" id="phoneNum" />
                <input type="button" onclick="javascript:queryPhone();" value="查询" />
              </P>
            </div>
            <div class="tzlc_gjx_one_b">
              <p class="gjjg"> <span>查询结果：</span> <span id="queryPhoneInfo" style="color: #e94718;">暂无查询信息</span> </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script> 
<script type="text/javascript">
		function initList() {
			$.post("getMessageBytypeId.do?typeId=15", function(data) {
				$("#borrow_clause").html(data);
			});
		}

		function queryPhone() {//手机归属地查询
			param["paramMap.phoneNum"] = $("#phoneNum").val();
			$.shovePost("queryPhoneInfo.do", param, queryPhoneBack);
		}

		function queryPhoneBack(data) {
			$("#queryPhoneInfo").html(data);
			//alert(data);
			/*var array=data.split("|");
			if(array[1]=="true"){//新窗口打开查询结果
			  $("#queryPhoneInfo").html("");
			  window.open(array[0]);
			}else{//显示错误信息
			   $("#queryPhoneInfo").html(array[0]);
			}*/
			//window.location = $("#queryPhoneInfo").text();
			//document.getElementById("myPhone").href=$("#queryPhoneInfo").text();
		}

		function queryIP() {//IP地址查询
			param["paramMap.ipAddress"] = $("#ipAddress").val();
			$.shovePost("queryIPInfo.do", param, queryIPBack);
		}

		function queryIPBack(data) {
			$("#queryIPInfo").html(data);
			/*var array=data.split("|");
			if(array[1]=="true"){//新窗口打开查询结果
			  $("#queryIPInfo").html("");
			  window.open(array[0]);
			}else{//显示错误信息
			   $("#queryIPInfo").html(array[0]);
			}*/
		}

		function rateNumJudge() {//验证利息计算输入数字是否正确
			if ($("#borrowSum").val() == "") {
				$("#show_error").html("借款金额不能为空");
				$("#showPayTable").html("");
				return;
			} else if (!/^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/.test($("#borrowSum").val())) {
				$("#show_error").html("请输入正确的数字进行计算");
				$("#showPayTable").html("");
				return;
			} else if ($("#yearRate").val() == "") {
				$("#show_error").html("年利率不能为空");
				$("#showPayTable").html("");
				return;
			} else if (!/^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/.test($("#yearRate").val())) {
				$("#show_error").html("请输入正确的年利率");
				$("#showPayTable").html("");
				return;
			}
			if ($("#manual").attr("checked")) {
				if ($("#borrowTime").val() == "") {
					$("#show_error").html("借款期限不能为空");
					$("#showPayTable").html("");
					return;
				} else if (isNaN($("#borrowTime").val())) {
					$("#show_error").html("请输入正确借款期限");
					$("#showPayTable").html("");
					return;
				} else if ($("#borrowTime").val() > 25) {
					$("#show_error").html("天标时间不能超过25天");
					$("#showPayTable").html("");
					return;
				}
			} else {
				if ($("#borrowTime").val() == "") {
					$("#show_error").html("借款期限不能为空");
					$("#showPayTable").html("");
					return;
				} else if (!/^[0-9]*[1-9][0-9]*$/.test($("#borrowTime").val())) {
					$("#show_error").html("请输入正确借款期限");
					$("#showPayTable").html("");
					return;
				}
			}

			$("#show_error").html("");
		}
		function rateCalculate() {//利息计算
			rateNumJudge();

			var aa = 0;
			var bb = 0;
			var cc = 0;
			if ($("#show_error").text() != "") {
				return;
			}
			if ($("#manual").attr("checked")) {//天标计算
				var _array = [];
				_array.push("<table>");
				_array.push("<tr><th style='width: 215px;'>期限</th><th style='width: 215px;'>所还本息</th><th style='width: 215px;'>所还本金</th>"
						+ "<th style='width: 215px;'>所还利息</th><th style='width: 215px;'>总还款额</th></tr>");
				param["paramMap.borrowSum"] = $("#borrowSum").val();
				param["paramMap.yearRate"] = $("#yearRate").val();
				param["paramMap.borrowTime"] = $("#borrowTime").val();
				param["paramMap.repayWay"] = $("#repayWay").get(0).selectedIndex;

				$.shovePost("repayWayrepayWay.do", param, function(data) {
					if (data == 1) {
						$("#show_error").html("请填写正确信息");
						$("#showPayTable").html("");
						return;
					} else if (data == 2) {
						$("#show_error").html("年利率太低，请重新输入");
						$("#showPayTable").html("");
						return;
					}
					aa = data.map.monForRateA;
					bb = data.map.monRate;
					cc = data.map.allPay;

					_array.push("<tr><td align='center'>" + data.map.mon + "天</td><td align='center'>" + data.map.monForRateA + "</td>"
							+ "<td align='center'>" + data.map.monForA + "</td>" + "<td align='center'>" + data.map.monForRate
							+ "</td><td align='center'>" + cc + "</td></tr>");
					_array.push("</table>");
					$("#showPayTable").html(_array.join(""));
					$("#result").show();
					$("#totalResult").hide();
					$("#dd").html(cc);
				});

			} else {

				var _array = [];
				_array.push("<table>");
				_array.push("<tr><th style='width: 215px;'>期数</th><th style='width: 215px;'>月还本息</th><th style='width: 215px;'>月还本金</th>"
				+"<th style='width: 215px;'>月还利息</th><th style='width: 215px;'>本息余额</th></tr>");
				param["paramMap.borrowSum"] = $("#borrowSum").val();
				param["paramMap.yearRate"] = $("#yearRate").val();
				param["paramMap.borrowTime"] = $("#borrowTime").val();
				param["paramMap.repayWay"] = $("#repayWay").get(0).selectedIndex;

				$.shovePost("frontfinanceTools.do", param, function(data) {
					if (data == 1) {
						$("#show_error").html("请填写正确信息");
						$("#showPayTable").html("");
						return;
					} else if (data == 2) {
						$("#show_error").html("年利率太低，请重新输入");
						$("#showPayTable").html("");
						return;
					}
					for ( var i = 0; i < data.length; i++) {
						if (i == 0) {
							aa = data[i].monForRateA;
							bb = data[i].monRate;
							cc = data[i].allPay;
						}
						_array.push("<tr><td align='center'>"+data[i].mon+"</td><td align='center'>"+data[i].monForRateA+"</td>"
						+"<td align='center'>"+data[i].monForA+"</td>"
						+"<td align='center'>"+data[i].monForRate+"</td><td align='center'>"+data[i].rateARemain+"</td></tr>");
					}
					_array.push("</table>");
					$("#showPayTable").html(_array.join(""));
					$("#result").hide();
					$("#totalResult").show();
					$("#aa").html(aa);
					$("#bb").html(bb + "%");
					$("#cc").html(cc);
				});
			}
		}

		function changeStatus() {
			if ($("#manual").attr("checked")) {
				$("#timeType").html("天");
				$("#borrowTime").attr('value', '');
				$("#repayWay").attr('disabled', 'disabled');
			} else {
				$("#timeType").html("月");
				$("#repayWay").removeAttr('disabled');
			}
		}
		/*function rateCalculate(){//利息计算
		    param["paramMap.borrowSum"] = $("#borrowSum").val();
		   param["paramMap.yearRate"] = $("#yearRate").val();
		   param["paramMap.borrowTime"] = $("#borrowTime").val();
		   param["paramMap.repayWay"] = $("#repayWay").get(0).selectedIndex;
		$.shovePost("frontfinanceTools.do",param,function(data){
		   //alert(data);
			    var array=data.split("|");
				$("#aa").html(array[0]);
				$("#bb").html(array[1]+"%");
				$("#cc").html(array[2]);
		});
		}*/

		/*function initCallBack(data){
		    alert(data);
		    var array=data.split("|");
			$("#aa").html(array[0]);
			$("#bb").html(array[1]);
			$("#cc").html(array[2]);
		}*/

		$(function() {
			$('.tabmain').find('li').click(function() {
				$('.tabmain').find('li').removeClass('on');
				$(this).addClass('on');
				$('.lcmain_l').children('div').hide();
				$('.lcmain_l').children('div').eq($(this).index()).show();
			})
		});

		//加载借款协议范本
		/*function queryBrrowClause(){
		    //alert($("#borrowClause").text());
		    param["paramMap.title"] = $("#borrowClause").text();
			$.shovePost("queryBorrowClause.do",param,function(data){
			   //alert(data);
			    var array=data.split("|");
			    $("#borrowClauseTitle").html(array[0]);
			    $("#borrowClauseContent").html(array[1]);
			});
		}*/
	</script>