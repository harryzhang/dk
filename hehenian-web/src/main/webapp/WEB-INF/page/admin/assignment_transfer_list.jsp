<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<link href="../css/admin/admin_css.css" rel="stylesheet"
	vtype="text/css" />
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../common/dialog/popup.js"></script>
<script type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<link href="../css/jbox/Gray/jbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	//成交
	function success(id) {
		var debtStatus = 3;
		var investId = $("#investId").val();
		var insertName = $("#insertName").val();
		var alienatorId = $("#alienatorId").val(); //转让人ID
<%--		var auctionerId = $("#auctionerId").val(); //最高竞拍者ID--%>
		var debtSum = $("#debtSum").val(); //债权总额
		auctionBasePrice = $("#auctionBasePrice").val(); //转让价格
		var param = {};
		param["paramMap.id"] = id;
		param["paramMap.debtStatus"] = debtStatus;
		param["paramMap.insertName"] = insertName;
		param["paramMap.alienatorId"] = alienatorId;
		param["paramMap.auctionerId"] = auctionerId;
		param["paramMap.debtSum"] = debtSum;
		param["paramMap.auctionBasePrice"] = auctionBasePrice;
		param["paramMap.investId"] = investId;

		if (null == insertName || insertName == "") {
			alert("这笔债权无认购者，不能成交!");
			return;
		}
		if (confirm("确认成交?")) {
			$.shovePost("udadateDebtStatus.do", param, function(data) {
				if (data == "1") {
					alert("提交成功")
					var para1 = {};
					reloadList();
				}
				if (data == "2") {
					alert("提交失败");
					return;
				}
				if (data == "3") {
					alert("认购者为空，提交失败");
					return;
				}
			});

		}
	}

	//撤回
	function lose(id) {
		var debtStatus = 5;
		var investId = $("#investId").val();
		var param = {};
		param["paramMap.id"] = id;
		param["paramMap.debtStatus"] = debtStatus;
		param["paramMap.investId"] = investId;
		if (confirm("确认撤回?")) {
			$.jBox.tip("<div class='data-submit'>提交数据中，请稍候...</div>","loading");
			$.shovePost("udadateDebtStatus.do?", param, function(data) {
				if (data == "1") {
					alert("撤回成功")
					reloadList();
				}
				if (data == "2") {
					$.jBox.closeTip();
					alert("撤回失败");
					return;
				}
			});
		}
	}

	//返回到列表页
	function reloadList() {
		window.location.href = "queryTransferByIdIndex.do";
	}
</script>

<div>
	<table id="help" style="width: 100%; color: #333333;" cellspacing="1"
		cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class=gvHeader>
				<th style="width: 60px;" scope="col">
					借款人
				</th>
				<th style="width: 180px;" scope="col">
					标题
				</th>
				<th style="width: 80px;" scope="col">
					年利率
				</th>
				<th style="width: 100px;" scope="col">
					债权期限
				</th>
				<th style="width: 100px;" scope="col">
					转让者
				</th>
				<th style="width: 100px;" scope="col">
					投资金额
				</th>
				<th style="width: 100px;" scope="col">
					转让价格
				</th>
				<!--				<th style="width: 100px;" scope="col">-->
				<!--					竞拍低价-->
				<!--				</th>-->
				<!--				<th style="width: 100px;" scope="col">-->
				<!--					竞拍最高价-->
				<!--				</th>-->
				<th style="width: 100px;" scope="col">
					认购者
				</th>
				<th style="width: 180px;" scope="col">
					剩余时间
				</th>
				<th style="width: 150px;" scope="col">
					操作
				</th>
			</tr>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="12">
						暂无数据
					</td>
				</tr>
			</s:if>
			<s:else>
				<s:set name="counts" value="#request.pageNum" />
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td align="center">
							${bean.publisher}
						</td>
						<td align="center">
							${bean.borrowTitle}
						</td>
						<td align="center">
							${bean.annualRate}&nbsp;%
						</td>
						<td align="center">
							${bean.debtLimit}
						</td>
						<td align="center">
							${bean.username}
						</td>
						<td align="center">
							${bean.debtSum}
						</td>
						<td align="center">
							${bean.auctionBasePrice}
						</td>
						<!--						<td align="center">-->
						<!--							${bean.auctionBasePrice}-->
						<!--						</td>-->
						<!--						<td align="center">-->
						<!--							${bean.auctionHighPrice}-->
						<!--						</td>-->
						<td align="center">
							${bean.insertName}
						</td>
						<td align="center">
							${auctionEndTime}
						</td>
						<td>
							<s:if test="#bean.auctionPrice == #bean.auctionBasePrice && auctionEndTime != '过期'">
								<input id="id" name="id" value="${bean.id}" type="hidden"></input>
								<input id="alienatorId" name="alienatorId"
									value="${bean.alienatorId}" type="hidden"></input>
<%--								<input id="auctionerId" name="auctionerId"--%>
<%--									value="${bean.userId}" type="hidden"></input>--%>
								<input id="debtSum" name="debtSum" value="${bean.debtSum}"
									type="hidden"></input>
								<input id="auctionBasePrice" name="auctionBasePrice"
									value="${bean.auctionBasePrice}" type="hidden"></input>
								<input id="insertName" name="insertName" type="hidden"
									value="${bean.insertName}" />
								<input type="hidden" name="investId" id="investId"
									value="${bean.investId}" />
								<a href="javascript:success(${bean.id});">成交 </a>
							</s:if>
							<s:else>
								&nbsp;&nbsp;--
							</s:else>
							&nbsp;
							<input type="hidden" name="investId" id="investId"
								value="${bean.investId}" />
							<a href="javascript:lose(${bean.id});">撤回 </a>
							<!--							<a href="javascript:queryAssignmentById(${bean.id})">查看</a>-->
						</td>
					</tr>
				</s:iterator>
			</s:else>
		</tbody>
	</table>
</div>
<br>

<shove:page curPage="${pageBean.pageNum}" showPageCount="10"
	pageSize="${pageBean.pageSize }" theme="jsNumber"
	totalCount="${pageBean.totalNum}"></shove:page>

