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
   <!--  <div style=" margin:10px 0px;"><img src="/color/images/ad.jpg" width="974" height="80"  alt=""/></div> -->
    <!--左侧-->
    <div class=" nr_left">
          <div class="search_all">
        <div class="toubiao_nr">
              <div class="toubiao_nr_one">
            <div style=" font-size:18px; margin-bottom:20px;"><s:property value="#request.borrowDetailMap.moneyPurposes" default="---" /><br />
                  <font size="-2" color="#999999">编号：${borrowDetailMap.number}</font> </div>
            <div>
                  <ul>
                <li style=" margin-bottom:20px;">目前总投标金额：<br />
                      <span  style=" font-size:14px; color:#090">￥</span> <span  style=" font-size:32px;color:#090">${borrowDetailMap.hasInvestAmount}</span></li>
                <li>剩余投标金额：<br />
                      <span class="red" style=" font-size:14px;">￥</span> <span class="red" style=" font-size:32px;">${borrowDetailMap.investAmount}</span></li>
              </ul>
                </div>
          </div>
              <div  class="toubiao_nr_two">
            <ul >
                  <li>
                <input id="annualRate" value="${borrowDetailMap.annualRate}" type="hidden">
                <img src="/images/new/1.gif"  style=" float:left; margin-top:6px;" /><span>借款金额：</span>￥<s:property value="#request.borrowDetailMap.borrowAmount" default="---" /> </li>
                  <li><img src="/images/new/3.gif"  style=" float:left; margin-top:6px;" /><span>年利率：<s:property value="#request.borrowDetailMap.annualRate" default="---" />%
                <input type="hidden" value="${borrowDetailMap.monthRate}" id="monthRate" />
              </li>
                  <li><img src="/images/new/2.gif" style=" float:left; margin-top:6px;" /><span>借款期限：
                    <s:property value="#request.borrowDetailMap.deadline" default="---" />个月 </span>
                <input id="deadlinehhn" value="${borrowDetailMap.deadline}" type="hidden">
              </li>
                  <li><img src="/images/new/tt.gif" style=" float:left" /><span>还款方式： <!-- modify by houli 如果是天标，则还款方式为  到期还本付息 -->
                <s:if test="%{#request.borrowDetailMap.isDayThe ==2}">到期还本付息</s:if>
            <s:elseif
														test="%{#request.borrowDetailMap.paymentMode == 1}">按月分期还款</s:elseif>
            <s:elseif test="%{#request.borrowDetailMap.paymentMode == 2}">按月付息,到期还本</s:elseif>
            <s:elseif
														test="%{#request.borrowDetailMap.paymentMode == 3}">秒还</s:elseif>
            <s:elseif test="%{#request.borrowDetailMap.paymentMode == 4}">一次性还款</s:elseif>
              </li>
                  <li><img src="/images/new/tt.gif" style=" float:left" /><span>已投人数：
                    <s:property value="#request.borrowDetailMap.investNum" default="0" />
                    人</span></li>
                  <li><img src="/images/new/tt.gif" style=" float:left" /><span>剩余时间：<span id="remainTimeOne"></span></span>
                <div id="remainSeconds" style="display: none">${borrowDetailMap.remainTime}</div>
              </li>
                </ul>
          </div>
              <!--投标栏-->
              <div  class="toubiao_nr_three"> 
            <!--投标金额-->
            <div style=" width:275px; height:34px; float:left; line-height:32px;"> <font style="float:left">投标金额：</font>
                  <input id="amount" maxlength="20" type="text"  style=" width:200px; height:32px; line-height:32px; font-size:22px;  border:1px solid #CCC"/>
                </div>
            <!--可用余额-->
            <div  style=" width:275px; float:left; line-height:32px; height:34px;"> <font style="float:left">验 证 码：</font>
                  <input  type="text"  id="code" style=" width:200px; height:32px; line-height:32px; font-size:22px;  border:1px solid #CCC"/>
                </div>
            <div style=" width:275px; line-height:32px; float:left"> <span>您的可用余额： <strong>${userMap.usableSum}</strong> 元</span> <a href="cf-recharge.do" style=" color:#F60">我要充值</a> <span style="color: #666666;"> </span> </div>
            <!--验证码-->
            <div  style=" width:250px; line-height:32px; float:left; padding-right:25px;"> 
            <a href="javascript:void()" onclick="switchCode(this)" style="float: right;" id="a1">
            <img src="/admin/imageCode.do?pageId=userregister" title="点击更换验证码" id="codeNum1" />看不清？换一换</a></div>
            
            <!--投标按钮-->
            <div  style=" width:550px; float:left; margin-top:25px; text-align:center"> <a href="javascript:void(0);" id="invest">
              <input class="l_jkxq_ljtb" type="button" value="立即投标" />
              </a> </div>
            <div style=" width:550px; float:right; margin-top:10px; text-align:center">
                  <input
													type="checkbox" checked="checked" id="xieyi" value="1" style="margin:0px;vertical-align: middle;" />
                  <a style="margin:0px;vertical-align: middle;"
													href="javascript:openUrl('/agreementDetail.do?borrowId=${id}')">同意《借款协议》</a> </div>
														<form id="f_investBorrow" target="_blank" action="/investBorrow.do" method="post" >
		<input type="hidden" id="int_id" name="paramMap.id" />
		<input type="hidden" id="int_code" name="paramMap.code" />
		<input type="hidden" id="int_amount" name="paramMap.amount" />
		<input type="hidden" id="int_usrCustId" name="paramMap.usrCustId" />
		<input type="hidden"  name="paramMap.url" value="http://hehenian.colourlife.com/"/>
	</form>   
    <input type="hidden" value="${borrowDetailMap.id }" id="id"
												name="id" />     
          </div>
            </div>
        <div class="toubiao_left">
              <div class="toubiao_left_a">
            <div>
                  <p><strong>相关信息</strong></p>
                </div>
            <div style=" margin:15px 15px 15px 15px; border:1px solid #e5e5e5; background:#f9f9f9 ; line-height:26px; padding:20px;"> 以下基本信息资料，经用户同意披露。其中 <font style="color:#F00"> 红色字体 </font>的信息，为通过网站审核之项。<br />
                  审核意见：<font style="color:#F00">网站将以客观、公正的原则，最大程度地核实借入者信息的真实性，但不保证审核信息100%真实。如果借入者长期逾期，其提供的信息将被公布。</font> </div>
            <div style="    overflow:hidden; margin-left:15px;"><img src="/images/new/neir.png" style="float:left; margin-right:5px;" /><strong>基本信息</strong> </div>
            <div style=" margin:0px 15px 0px 15px;  line-height:26px; padding:5px 20px 15px 20px; overflow:hidden">
                  <ul style=" width:50%; float:left">
                <li>性别：
                      <s:if test="#request.borrowUserMap.auditperson == 3"> <strong>${borrowUserMap.sex}</strong> </s:if>
                      <s:else>${borrowUserMap.sex}</s:else>
                    </li>
                <li>年龄：
                      <s:if test="#request.borrowUserMap.auditperson == 3"> <strong>${borrowUserMap.age}</strong> </s:if>
                      <s:else>${borrowUserMap.age}</s:else>
                    </li>
                <li>学历：
                      <s:if test="#request.borrowUserMap.auditwork == 3"> <strong>${borrowUserMap.highestEdu}</strong> </s:if>
                      <s:else>${borrowUserMap.highestEdu}</s:else>
                    </li>
                <li>工作收入：
                      <s:if test="#request.borrowUserMap.auditwork == 3"> <strong>${borrowUserMap.monthlyIncome}</strong> </s:if>
                      <s:else>${borrowUserMap.monthlyIncome}</s:else>
                    </li>
              </ul>
                  <ul style=" width:50%; float:left">
                <li>公司名称： <span>
                  <s:if test="#request.borrowUserMap.auditwork == 3"> <strong>${request.orgName}</strong> </s:if>
                  <s:else>${request.orgName}</s:else>
                  </span></li>
                <li>公司行业： <span >
                  <s:if test="#request.borrowUserMap.auditwork == 3"> <strong>${borrowUserMap.companyLine}</strong> </s:if>
                  <s:else>${borrowUserMap.companyLine}</s:else>
                  </span></li>
                <li>职位： <span>
                  <s:if test="#request.borrowUserMap.auditwork == 3"> <strong>${borrowUserMap.job}</strong> </s:if>
                  <s:else>${borrowUserMap.job}</s:else>
                  </span></li>
                <li>现单位工作时间：<span>
                  <s:if test="#request.borrowUserMap.auditwork == 3"> <strong>${borrowUserMap.workYear}</strong> </s:if>
                  <s:else>${borrowUserMap.workYear}</s:else>
                  </span></li>
              </ul>
                </div>
            <div style=" overflow:hidden; margin-left:15px;"><img src="/images/new/neir.png" style="float:left;margin-right:5px;" /><strong>借款信息</strong> </div>
            <div style=" margin:0px 15px 0px 15px;  line-height:26px; padding:5px 20px 20px 20px; overflow:hidden">
                  <ul style=" width:50%; float:left">
                <li>发布借款标：
                      <s:if test="#request.borrowRecordMap.publishBorrow !=''"><strong>${borrowRecordMap.publishBorrow}</strong></s:if>
                      <s:else><strong>0</strong></s:else>
                    </li>
                <li>成功借款笔数：
                      <s:if test="#request.borrowRecordMap.successBorrow !=''"><strong>${borrowRecordMap.successBorrow}</strong></s:if>
                      <s:else><strong>0</strong></s:else>
                    </li>
                <li>还清笔数：
                      <s:if test="#request.borrowRecordMap.repayBorrow !=''"><strong>${borrowRecordMap.repayBorrow}</strong></s:if>
                      <s:else><strong>0</strong></s:else>
                    </li>
                <li>逾期次数：
                      <s:if test="#request.borrowRecordMap.hasFICount !=''"><strong>${borrowRecordMap.hasFICount}</strong></s:if>
                      <s:else><strong>0</strong></s:else>
                    </li>
                <li>严重逾期次数：
                      <s:if test="#request.borrowRecordMap.badFICount !=''"><strong>${borrowRecordMap.badFICount}</strong></s:if>
                      <s:else><strong>0</strong></s:else>
                    </li>
              </ul>
                  <ul style=" width:50%; float:left">
                <li>共借入：
                      <s:if test="#request.borrowRecordMap.borrowAmount !=''"><strong>${borrowRecordMap.borrowAmount}</strong></s:if>
                      <s:else><strong>0</strong></s:else>
                    </li>
                <li>待还金额：
                      <s:if test="#request.borrowRecordMap.forRepayPI !=''"><strong><strong>${borrowRecordMap.forRepayPI}</strong></strong></s:if>
                      <s:else><strong>0</strong></s:else>
                    </li>
                <li>逾期金额：
                      <s:if test="#request.borrowRecordMap.hasI !=''"><strong>${borrowRecordMap.hasI}</strong></s:if>
                      <s:else><strong>0</strong></s:else>
                    </li>
                <li>共借出：
                      <s:if test="#request.borrowRecordMap.investAmount !=''"><strong>${borrowRecordMap.investAmount}</strong></s:if>
                      <s:else><strong>0</strong></s:else>
                    </li>
                <li>待收金额：
                      <s:if test="#request.borrowRecordMap.forPI !=''"><strong>${borrowRecordMap.forPI}</strong></s:if>
                      <s:else><strong>0</strong></s:else>
                    </li>
              </ul>
                </div>
            <div> </div>
          </div>
              <div class="toubiao_left_b">
            <div>
                  <p><strong>审核信息</strong></p>
                </div>
            <div style=" margin:15px; border:1px solid #e5e5e5; background:#f9f9f9 ; line-height:26px; padding:20px;"> 本站以公正、客观为基本原则，以客户资金安全为服务宗旨，尽最大限度的保证借款者的真实信息。如遇还款问题，由合和年风险备用金全额垫付本金及利息。以达到投资者投资无风险的最终目标。 </div>
            <div>
                  <table width="680" border="0" cellpadding="0" cellspacing="1" bgcolor="#e5e5e5" style="margin-left:15px; margin-bottom: 15px;">
                <tr height="32">
                      <td align="center" bgcolor="#f9f9f9">审核项目</td>
                      <td align="center" bgcolor="#f9f9f9">状态</td>
                      <td align="center" bgcolor="#f9f9f9">认证时间</td>
                
                    </tr>
                <s:if test="%{#request.list !=null && #request.list.size()>0}">
                      <s:iterator value="#request.list" id="bean">
                    <tr height="32">
                          <td bgcolor="#ffffff" align="center">${bean.name}</td>
                          <td bgcolor="#ffffff" align="center"><s:if test="#bean.auditStatus == 1"> 审核中 </s:if>
                        <s:elseif test="#bean.auditStatus == 2"> 审核失败 </s:elseif>
                        <s:elseif test="#bean.auditStatus == 3"> <img src="/images/neiye2_44.jpg" width="14" height="15" /> </s:elseif>
                        <s:else> 等待资料上传 </s:else></td>
                          <td bgcolor="#ffffff" align="center">${bean.authenticationTime}</td>
               
                        </tr>
                  </s:iterator>
                    </s:if>
                <s:else>
                      
                    <td colspan="3" align="center">没有数据</td>
                </s:else>
              </table>
                </div>
          </div>
              <div class="toubiao_left_c">
            <div style=" margin-bottom:30px; margin-top:45px;">
                  <p>还款信息</p>
                </div>
            <div>
                  <table width="680" border="0" cellpadding="0" cellspacing="1" bgcolor="#e5e5e5" style="margin-left:15px; margin-bottom: 45px;">
                <tr height="30">
                      <th bgcolor="#f9f9f9" scope="col">期限</th>
                      <th bgcolor="#f9f9f9" scope="col">预计还款日期</th>
                      <th bgcolor="#faf9f9" scope="col">应还本金</th>
                      <th bgcolor="#faf9f9" scope="col">应还利息</th>
                      <th bgcolor="#faf9f9" scope="col">应还本息</th>
                      <th bgcolor="#faf9f9" scope="col">状态</th>
                    </tr>
                <s:if test="%{#request.pageBeanes.page==null || #request.pageBeanes.page.size==0}">
                      <tr height="30">
                    <td colspan="7" bgcolor="#ffffff" align="center">暂无数据</td>
                  </tr>
                    </s:if>
                <s:else>
                      <s:set name="counts" value="#request.pageNum" />
                      <s:iterator value="#request.pageBeanes" var="repay">
                    <tr bgcolor="#ffffff">
                          <td height="32" align="center">${repay.repayPeriod}</td>
                          <td align="center">${repay.repayDate}</td>
                          <td align="center">${repay.stillPrincipal}</td>
                          <td align="center">${repay.stillInterest}</td>
                          <td align="center">${repay.still}</td>
                          <td align="center"><s:if test="#repay.repayStatus==1&&#repay.isWebRepay==1">未偿还</s:if>
                        <s:if test="#repay.repayStatus==2||#repay.isWebRepay==2"><font color="#009900">已偿还</font></s:if></td>
                        </tr>
                  </s:iterator>
                    </s:else>
              </table>
                </div>
          </div>
            </div>
      </div>
        </div>
    <!--右侧-->
     <jsp:include page="/include/cf-right.jsp"></jsp:include>
  </div>
  <jsp:include page="/include/cf-footer.jsp"></jsp:include>
</body>
<script type="text/javascript">
		$(function() {
			var param = {};
			$('#isLogin').click(function() {
				alert("您未登录,请先登录");
				return false;
			});

			$('#invest').click(function() {
				hhnflag = false;
debugger
				var xieyiok = Number($("#xieyi:checked").val());
				if (xieyiok != 1) {
					alert("请仔细阅读并同意借款协议!");
					return;
				}

				var step = "${session.user.authStep}";
				var username = "${borrowUserMap.username}";
				var uname = "${user.username}";
		var invest = $("#invest").val();
				var code = $("#code").val();
				var amount = $("#amount").val();
				var id = $("#id").val();
				if (null != uname) {
					if (null == amount || amount == "") {
						/* $("#amountSpan").show();
						$("#amountSpan").html("<font style='color:red'>请输入交易金额</font>"); */
						alert("请输入交易金额");
						return false;
					} else {
						$("#amountSpan").html("");
					}
					if (Number(amount) % 100 != 0 || amount < 100) {
						/* $("#amountSpan").show();
						$("#amountSpan").html("<font style='color:red'>请输入正确数字，如：100,200</font>");
						$("#income").hide(); */
						alert("请输入正确数字，如：100,200");
						return false;
					} else {
						/* $("#income").show();
						$("#amountSpan").hide(); */
					}
	
		if (code == null || code == "") {
					/* 	$("#codes").show();
						$("#codes").html("<font style='color:red'>请输入验证码</font>"); */
						alert("请输入验证码");
						return false;
					} else {
						//$("#codes").hide();
					}
					if (username == uname) {
						alert("无效操作,不能投自己发布的标");
						return false;
					}
					
					
					$('#int_id').val(id);
					$('#int_code').val(code);
					$('#int_amount').val(amount);
					$('#int_usrCustId').val($("#usrCustId").val());
					
					$('#invest').attr("disabled", true);
					
					$("#f_investBorrow").submit();
					
				} else {
					alert("请先登录");
				}

			});

			//未登录用户
			$('#noLogin').click(function() {
				var username = '${borrowUserMap.username}';
				var uname = '${user.username}';
				if (null != uname && uname != "") {
					alert("您是登录用户");
					switchCodes();
					return false;
				}
				window.location.href = 'login.do';
			});

			//登录未认证用户
			$('#noAttestation').click(function() {
				window.location.href = 'userPassData.do';
			});

			$('#audit').click(function() {
				var id = $('#idStr').val();
				$(this).addClass('on');
				$('#repay').removeClass('on');
				$('#collection').removeClass('on');
				param['paramMap.id'] = id;
				$.shovePost('financeAudit.do', param, function(data) {
					$('#contentList').html(data);
				});
			});
			$('#repay').click(function() {
				var id = $('#idStr').val();
				$(this).addClass('on');
				$('#audit').removeClass('on');
				$('#collection').removeClass('on');
				param['paramMap.id'] = id;
				$.shovePost('financeRepay.do', param, function(data) {
					$('#contentList').html(data);
				});

			});
			$('#collection').click(function() {
				var id = $('#idStr').val();
				$(this).addClass('on');
				$('#audit').removeClass('on');
				$('#repay').removeClass('on');
				param['paramMap.id'] = id;
				$.shovePost('financeCollection.do', param, function(data) {
					$('#contentList').html(data);
				});

			});
		//	initListInfo(param);
		});

		/* function initListInfo(param) {
			param['paramMap.id'] = '${borrowDetailMap.id}';
			$.shovePost('borrowmessage.do', param, function(data) {
				$('#msg').html(data);
			});
		} */
		function showImg(typeId, userId, ids) {
			var session = "${session.user}";
			if (session == 'null') {
				window.location.href = 'login.do';
				return;
			}
			var url = "showImg.do?typeId=" + typeId + "&userId=" + userId;
			$.jBox("iframe:" + url, {
				title : "查看认证图片(点击图片放大)",
				width : 500,
				height : 428,
				buttons : {}
			});
		}
		function close() {
			ClosePop();
		}

		//验证码
		function switchCode(e) {
			var timenow = new Date();
			if ($(e).attr("id") == "a1") {
				$("#codeNum1").attr("src", "/admin/imageCode.do?pageId=userregister&d=" + timenow);
			} else {
				$("#codeNum2").attr("src", "/admin/imageCode.do?pageId=userregister&d=" + timenow);
			}
		}
		function switchCodes() {
			var timenow = new Date();
			$('#codeNum1').attr('src', '/admin/imageCode.do?pageId=userregister&d=' + timenow);
		}

		function reportusers() {
			var id = '${borrowUserMap.userId}';
			var username = $("#username").val();
			var uname = '${user.username}';

			if (uname == '' || uname == null) {
				alert("您未登录,请先登录");
				return false;
			}
			if (username == uname) {
				alert("您不能举报自己");
				return false;
			}
			if ($('#title').val() == "") {
				alert("标题不能为空");
				return false;
			}
			if ($('#content').val() == "") {
				alert("内容不能为空");
				return false;
			}
			if ($('#cod').val() == "") {
				alert("验证码不能为空");
				return false;
			}
			param['paramMap.id'] = '${borrowUserMap.userId}';
			param['paramMap.title'] = $('#title').val();
			param['paramMap.content'] = $('#content').val();
			param['paramMap.code'] = $('#cod').val();
			$.shovePost("reportUserAdd.do", param, function(data) {
				if (data == "1") {
					$("#codSpan").html("<font style='color:red'>验证码输入错误</font>");
					$('#cod').focus();
					return false;
				}
				if (data == "3") {
					alert("举报成功");
					jubao();
				}
				if (data == "2") {
					alert("举报失败");
					return;
				}
			});

		}
		//根据ID查询还款详情
		function queryRepaymentById() {
			var id = $("#id").val();
			param['paramMap.id'] = $('#id').val();
			//$.shovePost("queryRepaymentById.do", param, function(data) {
			//});
		}

		//根据ID查询催收记录
		function queryCollectionById() {
			var id = $("#id").val();
			param['paramMap.id'] = $('#id').val();
			$.shovePost("queryCollectionById.do", param, function(data) {
			});
		}
	</script> 
	<script type="text/javascript">
		var SysSecond;
		var InterValObj;
		var second;
		var minite;
		var hour;
		var day;
		var times;
		$(document).ready(function() {
			SysSecond = '${borrowDetailMap.remainTime}'; //这里获取倒计时的起始时间 
			SysSecond = Number(SysSecond);
			InterValObj = window.setInterval(SetRemainTime, 1000); //间隔函数，1秒执行 
		});

		//将时间减去1秒，计算天、时、分、秒 
		function SetRemainTime() {
			SysSecond = SysSecond - 1;
			if (SysSecond > 0) {
				second = Math.floor(SysSecond % 60); // 计算秒     
				minite = Math.floor((SysSecond / 60) % 60); //计算分 
				hour = Math.floor((SysSecond / 3600) % 24); //计算小时 
				day = Math.floor((SysSecond / 3600) / 24); //计算天 
				times = day + "天" + hour + "小时" + minite + "分" + second + "秒";
				$("#remainTimeOne").html(times);
				$("#remainTimeTwo").html(times);
			} else {
				window.clearInterval(InterValObj);
				$("#remainTimeOne").html("<font style='color:red'>过期</font>");
				$("#remainTimeTwo").html("<font style='color:red'>过期</font>");
			}
		}

		function openUrl(url) {
			window.open(url, '_blank');
		}
	</script>
</html>