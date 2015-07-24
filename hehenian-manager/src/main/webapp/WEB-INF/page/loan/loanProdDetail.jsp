<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- --------------------------------产品基础信息--------------------------------- -->
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
.fontsize{
 font-size: 10px;
}
</style>
</head>
<body>
<div>
<div style="width:100%; height:28px; padding:5px 0px; border-bottom:1px solid #ccc;">
	
	<a class="l-btn l_btn_size" onclick="closeDialog()"><i class="fa fa-close"></i>&nbsp;关闭</a>
	
	<a class="l-btn l_btn_size" onclick="add_sett()"><i class="fa fa-check-circle-o"></i>&nbsp;增加方案</a>
</div>

<table>
<thead><span style="color:red;">产品基础信息</span></thead>
	<tr >
		<td width="190" class="">
			<label style="font-size:10px;" ><b>名称:</b></label><span style="color:blue;font-size:10px;"> ${prodDo.name} </span>
		</td>	
		<td width="140">
			<label style="font-size:10px;"><b>产品类型:</b></label><span style="color:blue;font-size:10px;"> ${prodDo.code}</span>
		</td>
		<td width="140">
			<label style="font-size:10px;" ><b>最低额度:</b></label><span style="color:blue;font-size:10px;">${prodDo.minLines}</span>
		</td>
		<td width="140">
			<label style="font-size:10px;"><b>最高额度:</b></label><span style="color:blue;font-size:10px;">${prodDo.maxLines}</span>
		</td>
		<td width="140">
			<label style="font-size:10px;"><b>贷款期限类型:</b></label><span style="color:blue;font-size:10px;"> ${prodDo.loanIssue}</span>
		</td>
		<td width="190">
			<label style="font-size:10px;"><b>渠道编码:</b></label><span style="color:blue;font-size:10px;"> ${prodDo.publishCode}</span>
		</td>
	</tr>
	<tr >
		<td>
			<label style="font-size:10px;"><b>是否需担保:</b></label><span style="color:blue;font-size:10px;"> ${prodDo.guarantee eq '0' ? '否' : '是'} </span>
		</td>	
		<td>
			<label style="font-size:10px;"><b>是否需抵押:</b></label><span style="color:blue;font-size:10px;"> ${prodDo.mortgage eq '0' ? '否' : '是'} </span>
		</td>
		<td>
			<label style="font-size:10px;"><b>利率锁定:</b></label><span style="color:blue;font-size:10px;">${prodDo.retaLock eq '0' ? '否' : '是'}</span> 
		</td>
		<td>
			<label style="font-size:10px;"><b>状态:</b></label><span style="color:blue;font-size:10px;">${prodDo.status eq 'T' ? '有效' : '失效'}</span>
		</td>
		<td>
			<label style="font-size:10px;"><b>推荐奖励:</b></label><span style="color:blue;font-size:10px;"> ${prodDo.refeCode}</span>
		</td>
		<td>
			<label style="font-size:10px;"><b>备注:</b></label><span style="color:blue;font-size:10px;"> ${prodDo.remark}</span>
		</td>
	</tr>
</table>

</div>

<!-- --------------------------------产品前提--------------------------------- -->
<div>
<table>
<thead><span style="color:red;">产品前提</span></thead>
	<c:if test="${provDoBolean eq '1'}">
		<tr >
			<td width="190">
				<label style="font-size:10px;"><b>最小年龄:</b></label><span style="color:blue;font-size:10px;">${provDo.minAge} </span>
			</td>	
			<td width="140">
				<label style="font-size:10px;"><b>最大年龄:</b></label><span style="color:blue;font-size:10px;">${provDo.maxAge}</span>
			</td>
			<td width="140">
				<label style="font-size:10px;" ><b>最低收入:</b></label><span style="color:blue;font-size:10px;">${provDo.minIncome}</span>
			</td>
			<td width="140">
				<label style="font-size:10px;"><b>工作岗位:</b></label><span style="color:blue;font-size:10px;">${provDo.job}</span>
			</td>
			<td width="140">
				<label style="font-size:10px;"><b>最低工作年限:</b></label><span style="color:blue;font-size:10px;"> ${provDo.minYearWork}</span>
			</td>
		</tr>
		<tr >
			<td>
				<label style="font-size:10px;"><b>工作地址:</b></label><span style="color:blue;font-size:10px;"> ${provDo.workAddr} </span>
			</td>	
			<td>
				<label style="font-size:10px;"><b>居住地址:</b></label><span style="color:blue;font-size:10px;"> ${provDo.livAddr} </span>
			</td>
			<td>
				<label style="font-size:10px;"><b>备注:</b></label><span style="color:blue;font-size:10px;">${provDo.remark}</span> 
			</td>
			<td>
				<label style="font-size:10px;"><b>工资是否打卡:</b></label><span style="color:blue;font-size:10px;">${provDo.ifpunch eq '0' ? '否' : '是'}</span>
			</td>
			<td>
				<label style="font-size:10px;"><b>状态:</b></label><span style="color:blue;font-size:10px;"> ${provDo.status eq 'T' ? '有效' : '失效'}</span>
			</td>
			
		</tr>
	</c:if>
	
	<c:if test="${provDoBolean eq '0'}">
		<tr >
			<td><span style="color:blue;font-size:10px;">暂无信息</span></td>
		</tr>
	</c:if>
	
</table>
</div>

<!-- --------------------------------产品方案--------------------------------- -->
<div>
<table>
	<c:if test="${settDoBolean eq '1'}">
		<c:forEach var="item" items="${settDoList}" varStatus="no">
			<table style="border:1px dashed #ccc;">
			<thead><span style="color:red;">产品方案${no.index + 1} </span></thead>
				<tr >
					<td width="260" colspan="2">
						<label style="font-size:10px;"><b>方案名称:</b></label><span style="color:blue;font-size:10px;">${item['name']} </span>
					</td>	
					<td width="160">
						<label style="font-size:10px;"><b>默认借款年利率:</b></label><span style="color:blue;font-size:10px;">${item['defaultAnnualRate']} </span>
					</td>
					<td width="160">
						<label style="font-size:10px;"><b>提前结清顺延期限:</b></label><span style="color:blue;font-size:10px;">${item['aheadSettlePeriod']} </span>
					</td>
					
				</tr>
				<tr >
					<td width="80">
						<label style="font-size:10px;"><b>方案代码:</b></label><span style="color:blue;font-size:10px;">${item['code']} </span>
					</td>
					<td width="160">
						<label style="font-size:10px;"><b>还款方式:</b></label><span style="color:blue;font-size:10px;">${item['repayWay']} </span>
					</td>
					<td width="160">
						<label style="font-size:10px;"><b>回款方式:</b></label><span style="color:blue;font-size:10px;">${item['receiptWay']} </span>
					</td>
					<td width="160">
						<label style="font-size:10px;"><b>状态:</b></label><span style="color:blue;font-size:10px;">${item['status'] eq 'E' ? '启用' : item['status'] eq 'D' ? '禁用' : item['status'] eq 'P' ? '已发布' :''} </span>
					</td>
				</tr>
					<c:forEach var="itemFee" items="${item['loanFeeRuleDoList']}" varStatus="feeNo">
						<tr >
							<td colspan="4">
								<table style="margin-left:10px;">
									<thead><span style="color:red;font-size:10px;"> 规则  ${feeNo.index + 1} </span></thead>
									<tr >
										<td width="220">
											<label style="font-size:10px;"><b>规则名称:</b></label><span style="color:blue;font-size:10px;">${itemFee['name']} </span>
										</td>
										<td width="220">
											<label style="font-size:10px;"><b>规则类型:</b></label><span style="color:blue;font-size:10px;">${itemFee['type']} </span>
										</td>
										<td width="220">
											<label style="font-size:10px;"><b>收取方式:</b></label><span style="color:blue;font-size:10px;">${itemFee['gatherWay']} </span>
										</td>
										<td width="220">
											<label style="font-size:10px;"><b>收取比率:</b></label><span style="color:blue;font-size:10px;">${itemFee['gatherRate']} </span>
										</td>
									</tr>
									<tr >
										<td width="220">
											<label style="font-size:10px;"><b>费用金额:</b></label><span style="color:blue;font-size:10px;">${itemFee['feeAmount']} </span>
										</td>
										<td width="220">
											<label style="font-size:10px;"><b>费用是否包含在借款利率:</b></label><span style="color:blue;font-size:10px;">${itemFee['isInclude'] eq 'F' ? '不包含' : itemFee['isInclude'] eq 'T' ? '包含' :''} </span>
										</td>
										<td width="220">
											<label style="font-size:10px;"><b>乘数:</b></label><span style="color:blue;font-size:10px;">${itemFee['baseAmountType'] eq '1' ? '借款金额': itemFee['baseAmountType'] eq '0' ? '剩余本金':''} </span>
										</td>
										<td width="220">
											<label style="font-size:10px;"><b>是否在生成还款计划时用:</b></label><span style="color:blue;font-size:10px;">${itemFee['isInitRepayPlanUse'] eq 'F' ? '否':是} </span>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</c:forEach>
					
			</table>
		</c:forEach>
	</c:if>
	
	<c:if test="${settDoBolean eq '0'}">
		<tr >
			<td><span style="color:blue;font-size:10px;">暂无信息</span></td>
		</tr>
	</c:if>
	
</table>
</div>
</body>
</html>