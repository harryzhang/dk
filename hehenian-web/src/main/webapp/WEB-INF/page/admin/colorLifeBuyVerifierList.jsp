<%--
  created by intellij idea.
  user: lenovo
  date: 2014/12/8
  time: 15:34
  to change this template use file | settings | file templates.
--%>
<%@ page contentType="text/html;charset=utf-8" language="java"%>
<%@ include file="/include/taglib.jsp"%>
<%@ include file="/include/includeJs.jsp"%>
<html>
<head>
<title>用户购买记录查询</title>
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		
		//全选
		$("#chkAll").click(function() {
			if ($(this).prop("checked")) {
				$(":input[name='trade_ids']").each(function() {
					$(this).prop("checked", true);
				});
			} else {
				$(":input[name='trade_ids']").each(function() {
					$(this).prop("checked", false);
				});
			}
		});
		
		// 导出excel时获取分页信息
		pageNumber = '${pageBean.pageNum}';
		pageSize = '${pageBean.pageSize}';
		
		$("#mutipleVerfier").click(function() {
			//获取id集合
	 		var stIdArray = [];
 			$("input[name='cb_ids']:checked").each(function(){
 				stIdArray.push($(this).val());
 			});
 			
 			if(stIdArray.length == 0){
 				alert("请选择需要审核的信息");
 				return ;
 			}
 		
 			var ids = stIdArray.join(","); 
 			verifierFn(ids);
		});
		
		/* $("div.holder").jPages({
		    containerID: "tbody", //显示数据所在的块的ID
		    first: '首页',
		    last: '尾页',
		    previous: '上页',
		    next: '下页',
		    perPage: 12,   //每页显示数据为多少行
		    startPage: 1, //起始页
		    startRange: 2, //开始页码为2个
		    midRange: 3, //最多显示几个页码页码,其余用..代替
		    endRange: 2, //结束页码为2个
		    keyBrowse: true
		}); */
	});
	
	function checkAll(e){
   		if(e.checked){
   			$(".downloadId").attr("checked","checked");
   		}else{
   			$(".downloadId").removeAttr("checked");
   		}
	}
	
	function verifierFn(orderId){
		if (!confirm("您确认继续此操作吗?")) {
			return ;
		}
		param["orderId"] = orderId;
		$.shovePost('<c:url value="/admin/colorLifeVerifier.do"/>',param,function(data){
			if(data.msg==1){
				alert("审核成功！");
				window.location.href = '<c:url value="/admin/listColorLifeVerifierInfoInit.do"/>';
				return;
			}
			if (data.msg==2) {
				var message = "";
				if (data.success) {
					message += "交易ID" + data.success + "审核成功！";
				}
				if (data.failure) {
					message += "交易ID" + data.failure + "审核失败！";
				}
				alert(message);
				window.location.href = '<c:url value="/admin/listColorLifeVerifierInfoInit.do"/>';
				return;
			}
			if (data.code) {
				alert(data.code);
			} else {
				alert("审核失败！");
			}
		})
	};
</script>
</head>
<body>
	<div id=right>
		<div
			style="padding-bottom: 0px; padding-left: 10px; padding-right: 10px; padding-top: 15px">
			<div>
				<div
					style="padding-bottom: 10px; background-color: #fff; padding-left: 10px; width: 1120px; padding-right: 10px; padding-top: 10px">
					<div>
						<table id="listTable"
							style="width: 100%; color: #333333; border-collapse: collapse;"
							bgColor="#dee7ef" border="0" cellSpacing="1" cellPadding="1">
							<thread>
							<tr class="gvHeader">
								<th width="4%">选中</th>
								<th width="10%">交易ID</th>
								<th width="8%">用户名称</th>
								<th width="8%">用户ID</th>
								<th width="10%">彩管家账户OA</th>
								<th width="8%">购买产品名称</th>
								<th width="10%">购买金额</th>
								<th width="5%">利率</th>
								<th width="8%">购买期限</th>
								<th width="8%">购买日期</th>
								<th width="5%">状态</th>
								<th width="10%">操作</th>
							</tr>
							</thread>
							<tbody id="tbody">
								<c:if test="${empty pageBean.page}">
									<tr class=gvItem>
										<td align="center" colspan="9">查询数据为空！</td>
									</tr>
								</c:if>
								<c:if test="${not empty pageBean.page}">
									<s:set name="counts" value="#request.pageNum" />
									<c:forEach items="${pageBean.page}" var="map">
										<tr class="gvItem">
											<td><input id="gvNews_ctl02_cbID" class="downloadId" type="checkbox" value="${map.orderId}" name="cb_ids" /></td>
											<td align="center"><c:out value="${map.orderId}" /></td>
											<td align="center"><c:out value="${map.realName}" /></td>
											<td align="center"><c:out value="${map.userId}" /></td>
											<td align="center"><c:out value="${map.thethirdusername}" /></td>
											<td align="center"><c:out value="${map.productName}" />·${map.productPeriod}-M</td>
											<td align="center"><c:out value="${map.buyMoney}" /></td>
											<td align="center">${map.rate}</td>
											<td align="center">${map.productPeriod}个月</td>
											<td align="center"><fmt:formatDate value="${map.buyDate}" pattern="yyyy-MM-dd"/></td>
											<td align="center">${map.statusLabel}</td>
											<td align="center" width="100"><a href="#" onclick="verifierFn(${map.orderId})">审核</a></td>
										</tr>
									</c:forEach>
									<tr class="gvItem">
										<td align="center"><input id="chkAll" onclick=checkAll(this); type="checkbox" value="checkbox" name="chkAll" /></td>
										<td align="left">全选</td>
										<td colspan="9"></td>
										<td align="center"><a id="mutipleVerfier" href="#">批量审核</a></td>
									</tr>
								</c:if>
							</tbody>
						</table>
						<!-- <div id="page" class="holder" align="center"></div> -->
					</div>
				</div>
			</div>
		</div>
	</div>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10"
		pageSize="${pageBean.pageSize}" theme="jsNumber"
		totalCount="${pageBean.totalNum}"></shove:page>
		每页：
		<select onchange="changePage(this);" id="pageSizeSelect">
			<option value="10" ${pageBean.pageSize eq 10 ? 'selected="selected"' : ""}>10</option>
			<option value="20" ${pageBean.pageSize eq 20 ? 'selected="selected"' : ""}>20</option>
			<option value="30" ${pageBean.pageSize eq 30 ? 'selected="selected"' : ""}>30</option>
			<option value="50" ${pageBean.pageSize eq 50 ? 'selected="selected"' : ""}>50</option>
			<option value="100" ${pageBean.pageSize eq 100 ? 'selected="selected"' : ""}>100</option>
		</select>条
</body>
</html>