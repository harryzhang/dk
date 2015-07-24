<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<%@ include file="/include/includeJs.jsp"%>
<script type="text/javascript">
	//批量审核
	function updateRecheckList() {
		//获取id集合
		var stIdArray = [];
		var mailContent = [];
		var reciver = [];
		var borrowTitle = [];
		var minTenderedSum=[];
		var maxTenderedSum=[];
		var borrowAmount=[];
		var userId=[];
		var raiseTerm=[];
		var deadline=[];
		var annualRate=[];
		var borrowGroup=[];
		$("input[name='cb_ids']:checked").each(function() {
			stIdArray.push($(this).val());
			mailContent.push($(this).attr("mailContent"));
			reciver.push($(this).attr("reciver"));
			borrowTitle.push($(this).attr("borrowTitle"));
			minTenderedSum.push($(this).attr("minTenderedSum"));
			maxTenderedSum.push(-1);
			userId.push($(this).attr("userId"));
			borrowAmount.push($(this).attr("borrowAmount"));
			raiseTerm.push($(this).attr("raiseTerm"));
			deadline.push($(this).attr("deadline"));
			annualRate.push($(this).attr("annualRate"));
			borrowGroup.push($(this).attr("borrowGroup"));
		});

		$("input[name='station']").each(function() {
			stIdArray.push($(this).val());
		});

		if (stIdArray.length == 0) {
			alert("请选择需要审核的借款");
			return;
		}
		var ids = stIdArray.join(",");
		var mailContents = mailContent.join(",");
		var recivers = reciver.join(",");
		var borrowTitles = borrowTitle.join(",");
		var minTenderedSums = minTenderedSum.join(",");
		var maxTenderedSums = maxTenderedSum.join(",");
		var userIds = userId.join(",");
		var borrowAmounts = borrowAmount.join(",");
		var raiseTerms = raiseTerm.join(",");
		var deadlines = deadline.join(",");
		var annualRates = annualRate.join(",");
		var borrowGroups = borrowGroup.join(",");
		var param = {};
		param["paramMap.id"] = ids;
		param["paramMap.userIds"] = userIds;
		param["paramMap.mailContent"] = mailContents;
		param["paramMap.reciver"] = recivers;
		param["paramMap.borrowTitle"] = borrowTitles;
		param["paramMap.borrowStatus"] = 8;
		param["paramMap.minTenderedSum"] = minTenderedSums;
		param["paramMap.maxTenderedSum"] = maxTenderedSums;
		param["paramMap.borrowAmount"] = borrowAmounts;
		param["paramMap.raiseTerm"] = raiseTerms;
		param["paramMap.deadline"] = deadlines;
		param["paramMap.annualRate"] = annualRates;
		param["paramMap.borrowGroup"] = borrowGroups;
		if (confirm("确认审核？")) {
			$.shovePost("updateRecheckList.do", param, function(data) {
				if (data == "1") {
					alert("审核成功")
					reload();
				}
				if (data == "2") {
					alert("审核失败");
					return;
				}
			});
		}
	}
	
	//彩生活导入
	function colorlife(){
		//borrowUserFromColorlife.do
		alert("未完成");
	}

	//选择所有
	function checkAll(e) {
		if (e.checked) {
			$(".downloadId").attr("checked", "checked");
		} else {
			$(".downloadId").removeAttr("checked");
		}
	}

	//查看
	function queryRecheckById(id) {
		$.jBox("iframe:queryRecheckById.do?id=" + id, {
			title : "复审的借款",
			width : 620,
			height : 580,
			top : 25,
			buttons : {

			}
		});
	}

	//弹出窗口关闭
	function closeMthod() {
		window.jBox.close();
		window.location.reload();
	}

	//取消--关闭弹窗
	function closeMthodes() {
		window.jBox.close();
	}
	
	//返回列表
	function reload()
	{
		window.location.href="queryRecheckIndex.do";
	}
	
</script>
<div>
	<table id="help" style="width: 100%; color: #333333;" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class=gvHeader>
				<th style="width: 35px;" scope="col" nowrap="nowrap">选中</th>
				<th style="width: 60px;" scope="col" nowrap="nowrap">借款编号</th>
		<!-- 	<th style="width: 180px;" scope="col">用户名</th>   -->
				<th style="width: 100px;" scope="col" nowrap="nowrap">真实姓名</th>
		<!--<th style="width: 120px;" scope="col">借款来源</th> -->
				<th style="width: 120px;" scope="col" nowrap="nowrap">标的类型</th>
				<th style="width: 100px;" scope="col" nowrap="nowrap">借款标题</th>
				<th style="width: 100px;" scope="col" nowrap="nowrap">借款金额</th>
				<th style="width: 100px;" scope="col" nowrap="nowrap">利率</th>
				<th style="width: 100px;" scope="col" nowrap="nowrap">期限</th>
				<th style="width: 100px;" scope="col" nowrap="nowrap">筹标期限</th>
				<th style="width: 140px;" scope="col" nowrap="nowrap">状态</th>
				<th style="width: 150px;" scope="col" nowrap="nowrap">操作</th>
			</tr>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="13">暂无数据</td>
				</tr>
			</s:if>
			<s:else>
				<s:set name="counts" value="#request.pageNum" />
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td align="center"><input id="gvNews_ctl02_cbID" class="downloadId" type="checkbox" value="${bean.id }" name="cb_ids" userId="${bean.userId }"  mailContent="您申请的的借款:[${bean.borrowTitle}]通过了复审.审核状况:复审通过"
							borrowTitle="${bean.borrowTitle}" deadline="${bean.deadline }" annualRate="${bean.annualRate }" reciver="${bean.publisher}" raiseTerm="${bean.raiseTerm }" borrowAmount="${bean.borrowAmount }" minTenderedSum="100" maxTenderedSum="1000" borrowGroup="${bean.borrowGroup }"/>
						</td>
						<td align="center">${bean.number}</td>
				<!-- 	<td align="center"><span onclick="queryRecheckById(${bean.id});" style="cursor: pointer; color: #3366CC;">${bean.username}</span>   -->
						<td align="center">${bean.realName}</td>
				<!-- 	<td align="center"><s:if test="#bean.source==0">小贷公司导入</s:if> <s:if test="#bean.source==1">网站注册</s:if> <s:if test="#bean.source==2">彩生活用户</s:if> <s:if test="#bean.source==3">净值用户</s:if>   -->
						<td align="center"><s:if test="#bean.borrowWay==1">薪金贷</s:if> <s:if test="#bean.borrowWay==2">生意贷</s:if>
						 <s:if test="#bean.borrowWay==3">业主贷</s:if>
						 
						</td>
						<td align="center">${bean.borrowTitle}</td>
						<td align="center">${bean.borrowAmount}</td>
						<td align="center">${bean.annualRate}&nbsp;%</td>
						<td align="center">${bean.deadline}个月</td>
						<td align="center">${bean.raiseTerm}天</td>
						<td align="center"><s:if test="#bean.firstTrial == null "> 初审人:${request.adminName} </s:if><s:else> 初审人:${bean.firstTrial} </s:else> </td>
						<td><a href="javascript:queryRecheckById(${bean.id})">查看</a>
						</td>
					</tr>
				</s:iterator>
			</s:else>
		</tbody>
	</table>
</div>
<table style="border-collapse: collapse; border-color: #cccccc" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
	<tbody>
		<tr>
			<td align="left" style="padding-left: 9px;"><input id="chkAll" onclick=checkAll(this); type="checkbox" value="checkbox" name="chkAll" /> 全选 <input id="updateRecheckList"
				type="button" onclick="updateRecheckList()" value="批量审核通过" name="updateRecheckList" style="margin-left: 10px;" /> 
			</td>
		</tr>
	</tbody>
</table>
<br>

<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>

