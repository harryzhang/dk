<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.sp2p.util.DateUtil"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>借款产品参数</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<link href="../css/css.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" src="../common/dialog/popup.js"></script>
		
		<script>

    		   $(function(){
					show(1);
			    	if($("#img").val() == ''){
			    		$("#img").attr("src","../images/NoImg.GIF");
				    }	        
		       }); 
		       $("#li_work").click(function() {
		      		alert("3435435");
					window.location.href = 'queryUserMangework.do?uid=${map.userId}';
				});
				function closeJbox(){
					window.parent.closeJbox();
				}
				function show(id){

					
					
					//for(var i =1;i<=3;i++){
					//	if(id == i){
					//		$("#table_"+i).css("display","block");
					//	}else{	
					//		$("#table_"+i).css("display","none");
					//	}
					//}
				}
		</script>

		<style type="text/css">
			body{}
			#id_a{font-size:16px;}
			#id_a ul li{float:left; width:100px;margin-left:50px;cursor: pointer;}
			table tr td{width:80px;border:1px solid #666666; padding:8px 18px;}
			#table_3 tr td{width:100px;line-height:1.8;}
			.userManage_left{padding-left:30px;}
			.userManage_info_k{width:1px;border-top: 0px;border-bottom: 0px; padding: 8px 10px;}
		</style>
	</head>
	<body style="font-family:'宋体';">
		<div id="id_a">
			<ul style="margin-bottom:10px;">
				<li>会员资料</li>
				<li><a href="queryUserMoneyInfoinner.do?i=${UserMsgmap.id}">账户资金</a></li>
				<li><a href="queryUserTouzInfoinner.do?i=${UserMsgmap.id}">投资借款</a></li>
<%--				<li onclick="show(1);">会员资料</li>--%>
<%--				<li onclick="show(2);">账户资金</li>--%>
<%--				<li onclick="show(3);">投资借款</li>--%>
			</ul>
		</div>
		</br>
		<table style="margin-left:40px;margin-top:20px; " id="table_1">
			<tr>
				<td>用户名：</td>
				<td>${ UserMsgmap.username}</td>
				<td class="userManage_info_k"></td>
				<td>会员编号：</td>
				<td>${ UserMsgmap.id}</td>
			</tr>
			<tr>
				<td>真实姓名：</td>
				<td>${UserMsgmap.realName}</td>
				<td class="userManage_info_k"></td>
				<td>推荐人</td>
				<td>${UserMsgmap.refferee }</td>
			</tr>
			<tr>
				<td>身份证号：</td>
				<td>${map.idNo }</td>
				<td class="userManage_info_k"></td>
				<td>网站积分：</td>
				<td>${UserMsgmap.rating }</td>
			</tr>
			<tr>
				<td>手机号码：</td>
				<td>${map.cellPhone }</td>
				<td class="userManage_info_k"></td>
				<td>月收入：</td>
				<td><select name="paramMap.monthlyIncome" id="monthlyIncome">
						<option value=""
						<s:if test='#request.map_work.monthlyIncome == ""'>selected="selected"</s:if>
						<s:else></s:else>
						>
							请选择
						</option>
						<option value="1000以下"
						<s:if test='#request.map_work.monthlyIncome == "1000以下"'>selected="selected"</s:if>
						<s:else></s:else>
						>
							1000以下
						</option>
						<option value="1000-2000"
						<s:if test='#request.map_work.monthlyIncome == "1000-2000"'>selected="selected"</s:if>
						<s:else></s:else>
						
						>
							1000-2000
						</option>
						<option value="2000-5000"
						
						<s:if test='#request.map_work.monthlyIncome == "2000-5000"'>selected="selected"</s:if>
						<s:else></s:else>
						>
							2000-5000
						</option>
						<option value="5000-10000"
						<s:if test='#request.map_work.monthlyIncome == "5000-10000"'>selected="selected"</s:if>
						<s:else></s:else>
						
						>
							5000-10000
						</option>
						<option value="10000-20000"
						<s:if test='#request.map_work.monthlyIncome == "10000-20000"'>selected="selected"</s:if>
						<s:else></s:else>
						
						>
							10000-20000
						</option>
						<option value="20000以上"
						<s:if test='#request.map_work.monthlyIncome == "20000以上"'>selected="selected"</s:if>
						<s:else></s:else>
						
						>
							10000-20000
						</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>邮箱：</td>
				<td>${ UserMsgmap.email}</td>
				<td class="userManage_info_k"></td>
				<td>用户来源：</td>
				<td>
					<s:if test="#request.UserMsgmap.source == 0">
						自动导入
					</s:if>
					<s:if test="#request.UserMsgmap.source == 1">
						网站注册
					</s:if></td>
			</tr>
			<tr>
				<td>居住地址：</td>
				<td>${ map.address}</td>
				<td class="userManage_info_k"></td>
				<td rowspan="4">认证情况</td>
				<td rowspan="4">
				<div>
				身份证认证:&nbsp;<s:if test='#request.map_rez.identifyv=="审核通过"'>
					<img id="img_identiy" width="25" height="20" src="../images/baseimage_1.jpg" />
				</s:if>
				<s:else>
					<img id="img_identiy" width="25" height="20" src="../images/baseimage_1h.jpg" />
				</s:else>
				</div>
				<div>
				工作认证:<s:if test='#request.map_rez.workv=="审核通过"'>
					<img id="img_identiy" width="25" height="20" src="../images/baseimage_2.jpg" />
				</s:if>
				<s:else>
					<img id="img_identiy" width="25" height="20" src="../images/baseimage_2h.jpg" />
				</s:else>
				</div>
				<div>
				居住地认证:<s:if test='#request.map_rez.addressv=="审核通过"'>
					<img id="img_identiy" width="25" height="20" src="../images/baseimage_3.jpg" />
				</s:if>
				<s:else>
					<img id="img_identiy" width="25" height="20" src="../images/baseimage_3h.jpg" />
				</s:else>
				</div>
				<div>
				信用报告认证:<s:if test='#request.map_rez.responsev=="审核通过"'>
					<img id="img_identiy" width="25" height="20" src="../images/baseimage_4.jpg" />
				</s:if>
				<s:else>
					<img id="img_identiy" width="25" height="20" src="../images/baseimage_4h.jpg" />
				</s:else>
				</div>
				<div>
				收入认证:<s:if test='#request.map_rez.incomev=="审核通过"'>
					<img id="img_identiy" width="25" height="20" src="../images/baseimage_5.jpg" />
				</s:if> 
				<s:else>
					<img id="img_identiy" width="25" height="20" src="../images/baseimage_5h.jpg" />
				</s:else>
				</div>
				</td>
			</tr>
			<tr>
				<td>年龄：</td>
				<td>${map.age}</td>
			</tr>
			<tr>
				<td>婚姻状况：</td>
				<td><input type="radio" name="paramMap.maritalStatus"
						id="maritalStatus" value="已婚" 
							<s:if test='#request.map.maritalStatus == "已婚"'>checked="checked"</s:if>
						<s:else></s:else>
						
						 />
					已婚
					<input type="radio" name="paramMap.maritalStatus"
						id="maritalStatus" value="未婚" 
							<s:if test='#request.map.maritalStatus == "未婚"'>checked="checked"</s:if>
						<s:else></s:else>
						
						/>
					未婚
				</td>
			</tr>
			<tr>
				<td>学历：</td>
				<td><select name="paramMap.highestEdu" id="highestEdu">
						<option value="-1">
							请选择
						</option>
						<option value="高中或以下"
						<s:if test='#request.map.highestEdu == "高中或以下"'>selected="selected"</s:if>
						<s:else></s:else>
						>
							高中或以下
						</option>
						<option value="大专"
							<s:if test='#request.map.highestEdu == "大专"'>selected="selected"</s:if>
						<s:else></s:else>
						>
							大专
						</option>
						<option value="本科"
							<s:if test='#request.map.highestEdu == "本科"'>selected="selected"</s:if>
						<s:else></s:else>
						>
							本科
						</option>
						<option value="研究生或以上"
							<s:if test='#request.map.highestEdu == "研究生或以上"'>selected="selected"</s:if>
						<s:else></s:else>
						>
							研究生或以上
						</option>
						<option value="其他"
							<s:if test='#request.map.highestEdu == "其他"'>selected="selected"</s:if>
						<s:else></s:else>
						>
							其他
					</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>职业：</td>
				<td>${map_work.job }</td>
			</tr>
			<tr>
				<td>单位名称：</td>
				<td>${map_work.orgName}</td>
			</tr>
	</table>
<%----%>
<%--	<div id="table_2"style="display: none;">--%>
<%--	<table style="margin-left:40px;margin-top:20px;">--%>
<%--			<tr>--%>
<%--				<td>总资产：</td>--%>
<%--				<td>${ map_zij.freezeSum+ map_zij.usableSum+ map_zij.dueinSum}</td>--%>
<%--				<td style="width:120px;">净收益：</td>--%>
<%--				<td>-</td>--%>
<%--			</tr>--%>
<%--			<tr>--%>
<%--				<td>可用余额：</td>--%>
<%--				<td>${ map_zij.usableSum}</td>--%>
<%--				<td>加权收益率(总充值)：</td>--%>
<%--				<td>-</td>--%>
<%--			</tr>--%>
<%--			<tr>--%>
<%--				<td>冻结总额：</td>--%>
<%--				<td>${ map_zij.freezeSum}</td>--%>
<%--				<td>待收金额：</td>--%>
<%--				<td>${ map_zij.dueinSum }</td>--%>
<%--			</tr>--%>
<%--			<tr>--%>
<%--				<td>充值总额：</td>--%>
<%--				<td>-</td>--%>
<%--				<td>可用信用额度：</td>--%>
<%--				<td>${ map_zij.creditLimit }</td>--%>
<%--			</tr>--%>
<%--			<tr>--%>
<%--				<td>提现总额：</td>--%>
<%--				<td>-</td>--%>
<%--			</tr>--%>
<%--			--%>
<%--		</table>--%>
<%--		<table style="margin-left:40px;margin-top:20px;">--%>
<%--			<tr>--%>
<%--				<td colspan="6">最新资金变动：</td>--%>
<%--			</tr>--%>
<%--			<tr>--%>
<%--				<td>时间</td>--%>
<%--				<td>影响金额</td>--%>
<%--				<td>可用余额</td>--%>
<%--				<td>交易方</td>--%>
<%--				<td>交易类型</td>--%>
<%--				<td>备注</td>--%>
<%--			</tr>--%>
<%--			<s:iterator value="#request.map_money_change" id="a" status="st">--%>
<%--			<tr>--%>
<%--				<td><s:property value="#a.recordTime" /></td>--%>
<%--				<td><s:property value="#a.handleSum" /></td>--%>
<%--				<td><s:property value="#a.usableSum" /></td>--%>
<%--				<td><s:property value="#a.traderName" /></td>--%>
<%--				<td><s:property value="#a.fundMode" /></td>--%>
<%--				<td><s:property value="#a.remarks" /></td>--%>
<%--			</tr>--%>
<%--			</s:iterator>--%>
<%--			--%>
<%--			--%>
<%--			--%>
<%--		</table>--%>
<%--	</div>--%>
<%--	--%>
<%--		--%>
<%--	<div id="table_3" style="display: none;">--%>
<%--		<table style="margin-left:40px;margin-top:20px;">--%>
<%--			<tr>--%>
<%--				<td>借出本息总额：</td>--%>
<%--				<td></td>--%>
<%--				<td>负债情况：</td>--%>
<%--				<td></td>--%>
<%--			</tr>--%>
<%--			<tr>--%>
<%--				<td>应收本息总额：</td>--%>
<%--				<td></td>--%>
<%--				<td>待还总额：</td>--%>
<%--				<td></td>--%>
<%--			</tr>--%>
<%--			<tr>--%>
<%--				<td>应收本息总额：</td>--%>
<%--				<td></td>--%>
<%--			</tr>--%>
<%--			<tr>--%>
<%--				<td>已收本息总额：</td>--%>
<%--				<td></td>--%>
<%--			</tr>--%>
<%--			<tr>--%>
<%--				<td>未收本息总额：</td>--%>
<%--				<td></td>--%>
<%--			</tr>--%>
<%--			<tr>--%>
<%--				<td>借款成功次数：</td>--%>
<%--				<td></td>--%>
<%--			</tr>--%>
<%--			<tr>--%>
<%--				<td>提前还款次数：</td>--%>
<%--				<td></td>--%>
<%--			</tr>--%>
<%--			<tr>--%>
<%--				<td>正常还款次数：</td>--%>
<%--				<td></td>--%>
<%--			</tr>--%>
<%--			<tr>--%>
<%--				<td>逾期还款次数：</td>--%>
<%--				<td></td>--%>
<%--			</tr>--%>
<%--			<tr>--%>
<%--				<td>网站代还次数：</td>--%>
<%--				<td></td>--%>
<%--			</tr>--%>
<%--			<tr>--%>
<%--				<td>迟还次数：</td>--%>
<%--				<td></td>--%>
<%--			</tr>--%>
<%--		</table>--%>
<%--	</div>--%>
	</body>
</html>
