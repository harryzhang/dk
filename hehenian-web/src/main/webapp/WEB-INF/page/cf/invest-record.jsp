﻿﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>
.tzjl_fwxy1h{ width:500px; border:1px solid #e5e5e5; position:absolute; left:350px; background:#f8f8f8; top:1200px; display: none;}
.tzjl_fwxy1h table td{ padding-left:10px;}
.tzjl_fwxy1h p{ padding:15px; font-size:14px; color:#d5b965;}
.tzjl_fwxy1h table input[type="text"]{ margin:0;}
.tzjl_fwxy1h table select{ width:60px;}
.tzjl_fwxy1h table textarea{ width:300px; height:100px; border:1px solid #e5e5e5; padding:3px; font-size:12px;}
/* 内页通用 页码翻页 */
.page{ text-align:center; font-size:12px; margin-top: 10px;margin-bottom: 5px; padding-bottom:5px}
.page a{  display:inline-block; height:20px; line-height:20px;border:1px solid #e0e0e0;color:#333;padding-left:5px; padding-right:7px;cursor:pointer; margin-right:5px; margin-top:5px;  }
.page a:hover{ text-decoration:none; }
.page_input{ width:30px; height:24px; margin-left:5px; margin-right:5px;}
.page_btn{ width:30px; height:24px; margin-left:5px; margin-right:5px; padding-left:5px; padding-right:5px; cursor:pointer;}

input[type="button"] {
	width: 95px;
	height: 27px;
	border: none;
	text-align: center;
	color: #fff;
	background: url(../images/pic_all.png) -1px -84px no-repeat;
	cursor: pointer;
}

input[type="button"]:hover {
	background: url(../images/pic_all.png) -1px -112px no-repeat;
}
</style>
<%@include file="/include/taglib.jsp"%>
        <div style=" border-bottom:1px solid #e5e5e5; padding-bottom:10px; margin-bottom:15px;"><font style=" color:#F60; font-size:20px;">投资记录 </font></div>
        
         <div style=" width:100%; margin:0px auto; line-height:24px;">
                        <table width="100%" border="0" >
                            <tr height="30">
                                <td ><span>借出本息总额：</span><strong>￥<s:if test="#request.map.realAmount == ''">0.00</s:if> <s:else><fmt:formatNumber pattern="#0.00" value="${request.map.realAmount }"></fmt:formatNumber></s:else></strong></td>
                                <td><span>应收本息总额：</span><strong>￥<s:if test="#request.map.shouldGetAmount == ''">0.00</s:if> <s:else><fmt:formatNumber pattern="#0.00" value="${request.map.shouldGetAmount }"></fmt:formatNumber></s:else></strong></td>
                            </tr>
                            <tr height="30">
                                <td><span>已收本息总额：</span><strong>￥<s:if test="#request.map.hasGetAmount == ''">0.00</s:if> <s:else><fmt:formatNumber pattern="#0.00" value="${request.map.hasGetAmount }"></fmt:formatNumber></s:else></strong></td>
                                <td><span>未收本息总额：</span><strong>￥<s:if test="#request.map.hasGetAmount == ''">0.00</s:if> <s:else><fmt:formatNumber pattern="#0.00" value="${request.map.shouldGetAmount-request.map.hasGetAmount }"></fmt:formatNumber></s:else></strong></td>
                            </tr>
                        </table>
       <div class="tzjl_cgtz_box">
                    	<h2><span>债权明细</span></h2>
                        <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#e5e5e5">
                            
                            <tr align="center" height="30">
                                <td bgcolor="#ffffff" width="60">债权编号</td>
                                <td bgcolor="#ffffff" width="60">交易日期</td>
                                <td bgcolor="#ffffff" width="50">借款者</td>
                                <td bgcolor="#ffffff" width="70">标题</td>
                                <td bgcolor="#ffffff" width="60">年利率</td>
                                <td bgcolor="#ffffff" width="70">债权期限</td>
                                <td bgcolor="#ffffff" width="60">投资金额</td>
                                <td bgcolor="#ffffff" width="60">已收金额</td>
                                <td bgcolor="#ffffff" width="60">待收金额</td>
                                <td bgcolor="#ffffff" width="60">回收阶段</td>
                                <td bgcolor="#ffffff" width="70">债权转让</td>
                            </tr>
                            <s:if test="pageBean.page!=null && pageBean.page.size>0">
								<s:iterator value="pageBean.page" var="bean">
                            <tr align="center" height="30">
                                <td bgcolor="#ffffff">${bean.invest_number }</td>
                                <td bgcolor="#ffffff">${bean.investTime }</td>
                                <td bgcolor="#ffffff">${bean.username }</td>
                                <td bgcolor="#ffffff"><a href="cf-finance.do?${bean.borrowId }">${bean.borrowTitle }</a></td>
                                <td bgcolor="#ffffff">${bean.annualRate }%</td>
                                <td bgcolor="#ffffff">${bean.debtLimit }</td>
                                <td bgcolor="#ffffff">${bean.realAmount }</td>
                                <td bgcolor="#ffffff">${bean.hasPI }</td>
                                <td bgcolor="#ffffff">${bean.notPI }</td>
                                <td bgcolor="#ffffff">${bean.hasDeadline }/${bean.deadline }</td>
                                <!-- <td bgcolor="#ffffff"><b class="tzjl_fwxy">查看</b></td> -->
                               <!--  <td bgcolor="#ffffff"><u class="tzjl_fwxy1">转让</u></td> -->
                               <td bgcolor="#ffffff">
								    <s:if test="#bean.borrowStatus!=4">--</s:if> 
								    <s:elseif
										test="#bean.debtStatus==null||#bean.debtStatus ==4||#bean.debtStatus ==5||#bean.debtStatus ==6||#bean.debtStatus ==7 || (#bean.debtStatus==3 && #bean.currentUser == #bean.auctionerId)">
										<span style="padding-left: 0;cursor: pointer;color: #333;" data-xx="${bean.whbj }"
											onclick="zhuanrang('${bean.borrowId}','${bean.blanceP }','${bean.deadline-hasDeadline}','${bean.investId}','${bean.annualRate}','${bean.num }','${bean.whbj }','${bean.annualRate }','${bean.debtId}');">转让</span>
									</s:elseif>
									<s:elseif test="#bean.debtStatus==1">
										<span style="padding-left: 0;cursor: pointer;color: #333;" onclick="window.location.href='cancelApplyDebt.do?debtId=${debtId}'">撤回</span>
									</s:elseif> 
									<s:elseif test="#bean.debtStatus==2">
										<span style="padding-left: 0;color: #333;">转让中</span>
									</s:elseif> <s:elseif test="#bean.debtStatus==3 && #bean.currentUser == #bean.alienatorId">
										<span style="padding-left: 0;color: #333;">已转让</span>
									</s:elseif>
							   </td>
                            </tr>
                            </s:iterator>
                            </s:if>
                            <s:else>
                            	<tr align="center" height="30" colspan="13">暂无数据</tr>
                            </s:else>
                        </table>
            		<div class="cle"></div>
						 <div class="tzjl_fwxy1h" id="debt_add" >
							<form id="editForm">
								<table width="480" border="0">
									<tr height="20">&nbsp;</tr>
									<tr><td align="right">温馨提示：</td>
									<td style="color: red;">投资满2个月之后才能进行债权转让</td>
									</tr>
									<tr>
										<td width="120" align="right">剩余本金：</td>
										<td width="360"><span id="whbj" style="padding-left:0px;"></span>元 
										<s:hidden id="investId" name="paramMap.investId"></s:hidden>
										<s:hidden id="debtSum" name="paramMap.debtSum"></s:hidden>
										<input id="debtLimit" name="paramMap.debtLimit" value="" type="hidden" />
										<s:hidden id="parentId" name="paramMap.parentId"></s:hidden>
										</td>
									</tr>
									<tr height="10"></tr>
									<tr>
										<td width="120" align="right">转让价格：</td>
										<td width="360"><input type="text" id="auctionBasePrice" class="inp90" name="paramMap.auctionBasePrice" value="" /> 
										<input id="borrowId" name="paramMap.borrowId" value="" type="hidden" />
										</td>
									</tr>
									 <tr>
										<td width="120" align="right"></td>
										<td width="360"> 
										<strong style="margin-left:0px;">
										不得超过剩余本金，不得少于剩余本金的90%
										</strong> 
										</td>
									</tr>
									<tr height="20"></tr>
									<tr>
										<td>&nbsp;</td>
										<td width="360"><input type="button" value="确定" onclick="zhuanrangConfirm();" /><input type="button" value="取消" id="debt_cancel" onclick="operationClose();" /></td>
									</tr>
									<tr height="10"></tr>
								</table>
							</form>
						</div>
						
						<div class="cle"></div>
						<div class="mjd_fy_all">
							<s:if test="pageBean.page!=null || pageBean.page.size>0">
								<div class="page" style=" padding-top:20px;">
									<p>
										<shove:page url="cf-userinfo.do" curPage="${pageBean.pageNum}" showPageCount="6" pageSize="${pageBean.pageSize }" theme="number"
											totalCount="${pageBean.totalNum}">
										</shove:page>
									</p>
								</div>
							</s:if>
						</div>
						<div class="cle"></div>
                    </div>
       
       </div>
       <script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
       <script>
      	 $(function(){
	    	   $("#jumpPage").live('click',function() {
					var curPage = $("#curPageText").val();
					if (isNaN(curPage)) {
						alert("输入格式不正确!");
						return;
					}
					$("#pageNum").val(curPage);
					var chk_value = [];
					$('input[name="ck_mode"]:checked').each(function() {
						chk_value.push($(this).val());
					});
					if (chk_value.length != 0) {
						param['paramMap.m'] = '1';
						$("#type").val(chk_value);
					} else {
						$("#type").val("");
					}
		           window.location.href="cf-userinfo.do?curPage="+curPage+"&publishTimeStart="+$("#publishTimeStart").val()+"&publishTimeEnd="+$("#publishTimeEnd").val();
				});
	       });
		     //关闭窗口
			function operationClose() {
				$(".tzjl_fwxy1h").hide();
			}
		     //转让
				var min;//转让最低金额
				var max;//转让最高金额
				function zhuanrang(borrowId, debtSum, debtLimit, investId, anna, num,whbj,annualRate,debtId) {//anna年利率       num 今天距离上次还款日的天数
					//anna = annas;
					debtSum = Number(debtSum);
					anna = Number(anna);
					num = Number(num);
					$(".tzjl_fwxy1h").show();
					$("#debtSum").val(debtSum);
					//$("#span_debtSum").html(debtSum);
					min = Number(debtSum * 0.9).toFixed(2);
					//------最高金额 =剩余本金 + 剩余本金*转让当天距离上次还款日的天数*月息/30
					//最高不超过剩余本金
					max = debtSum;//Number(debtSum + debtSum * num * anna / 36000).toFixed(2);
					var tips = min + " - " + max + " ";
					//$("#span_debtSum").html(tips);
					$("#whbj").html(whbj);
					$("#auctionBasePrice").val(whbj);
					//$("#annualRate").html(annualRate);
		
					$("#borrowId").val(borrowId);
					$("#debtLimit").val(debtLimit);
					$("#span_debtLimit").html(debtLimit);
					$("#investId").val(investId);
					$("#parentId").val(debtId);
				}
		
				//确定转让
				function zhuanrangConfirm() {
					var param = {};
					var price = $("#auctionBasePrice").val();
					if (isNaN(price)) {
						alert("价格非法");
						return;
					}
					price = Number(price);
					if ( max < price || min > price) {
						alert("价格非法");
						return;
					}
		
					param['investId'] = $("#investId").val();
					param['auctionBasePrice'] = $("#auctionBasePrice").val();
					param['debtSum'] = $("#debtSum").val();
					param['borrowId'] = $("#borrowId").val();
					param['debtLimit'] = $("#debtLimit").val();
					param['details'] = $("#details").val();
					param['auctionDays'] = $("#auctionDays").val();
					param['parentId'] = $("#parentId").val();
					var para = $("#editForm").serialize();
					$.post("../addAssignmentDebt.do", para, function(data) {
						if (data == 1) {
							alert("操作成功");
							$("#debt_cancel").click();
							window.location.href = "cf-userinfo.do";
						} else if (data == -1) {
							alert("操作失败");
							$("#debt_cancel").click();
						} else {
							alert(data);
							$("#debt_cancel").click();
						}
					});
				}
       </script>
          
