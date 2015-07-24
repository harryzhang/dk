<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<div>
	<table id="help" style=" color: #333333;width:100%" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class=gvHeader>
				<th scope="col">选中</th>
				<th scope="col">借款编号</th>
		<!--    <th scope="col">用户名</th>    -->
				<th scope="col">真实姓名</th>
		<!--    <th scope="col">来源</th>   -->
				<th scope="col">标类型</th>
				<th scope="col">借标标题</th>
				<th scope="col">借款金额</th>
				<th scope="col">利率</th>
				<th scope="col">期限</th>
				<th scope="col">筹标期限</th>
				<th scope="col">投标状态</th>
				<th scope="col">操作</th>
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
					
				 		<td><input type="checkbox" name="check" class="selected" value="${bean.id }" /></td>
						<td align="center">${bean.number }</td>
				<!-- 	<td>${bean.userName}</td>   -->
						<td>${bean.realName}</td>
				<!-- 		<td><s:if test="#bean.source==0">小贷公司导入</s:if> <s:elseif test="#bean.source==1">网站注册</s:elseif> <s:elseif test="#bean.source==2">彩生活用户</s:elseif> <s:elseif test="#bean.source==3">净值用户</s:elseif></td>  -->
						<td><s:if test="#bean.borrowWay==1">薪金贷</s:if> <s:elseif test="#bean.borrowWay==2">生意贷</s:elseif> <s:elseif test="#bean.borrowWay==3">普通借款</s:elseif> <s:elseif
								test="#bean.borrowWay==4">实地考察借款</s:elseif> <s:elseif test="#bean.borrowWay==5">机构担保借款</s:elseif> <s:elseif test="#bean.borrowWay==6">流转表</s:elseif></td>
						<td>${bean.borrowTitle}</td>
						<td>${bean.borrowAmount}</td>
						<td>${bean.annualRate}%</td>
						<td>${bean.deadLine }个月</td>
						<td>${bean.raiseTerm }天</td>
				<!-- 		
						<td><s:if test="#bean.borrowStatus==1">审核中</s:if> <s:elseif test="#bean.borrowStatus==2">已提现</s:elseif> <s:elseif test="#bean.borrowStatus==3">取消</s:elseif> <s:elseif
								test="#bean.borrowStatus==4">转账中</s:elseif> <s:elseif test="#bean.borrowStatus==6">失败</s:elseif></td>
				 -->
				  <td>${bean.amount_scale }% </td>   				
						<td><a href="javascript:failedBorrow_show(${bean.id });">查看</a></td>
					</tr>
				</s:iterator>
			</s:else>
			<tr class="gvItem">
				<td colspan="13" align="left"><font size="2"> <input type="checkbox" onclick="selectAll(this)" />全选&nbsp;&nbsp;&nbsp; <input type="button" value="批量删除" onclick="deleteAll()" />
						共有${totalNum }条记录</font></td>
			</tr>
		</tbody>
	</table>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
	<input type="hidden" id="pageNum" value="${pageBean.pageNum}" /> <input type="hidden" id="userName" value="${paramMap.userName }" /> <input type="hidden" id="borrowWay"
		value="${paramMap.borrowWay }" />
</div>
<script type="text/javascript" src="../css/admin/popom.js"></script>
<link rel="stylesheet" href="../css/jbox/Gray/jbox.css" type="text/css"></link>
<script type="text/javascript" language="javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" language="javascript" src="../script/jbox/jquery.jBox-zh-CN.js"></script>
<script>
	function selectAll(e) {
		if (e.checked) {
			$(".selected").attr("checked", "checked");
		} else {
			$(".selected").removeAttr("checked");
		}
	}
	function deleteAll() {
		var stIdArray = [];
		$("input[name='check']:checked").each(function() {
			stIdArray.push($(this).val());
		});

		if (stIdArray.length == 0) {
			alert("请选择需要删除的信息");
			return;
		}

		var ids = stIdArray.join(",");
		var param = {};
		param["paramMap.id"] = ids;
		param["pageBean.pageNum"] = $("#pageNum").val();
		param["paramMap.userName"] = $("#userName").val();
		param["paramMap.borrowWay"] = $("#borrowWay").val();

		if (confirm("确认删除？")) {
			$.ajax({
				type : 'POST',
				url : 'deleteFailedBorrow.do',
				data : param,
				success : function(data) {
					if (data == "1") {
						alert("删除成功！");
					} else {
						alert("删除失败！");
					}
					window.location.href = "failedBorrowIndex.do";
				},
				error : function(data) {
					alert("删除成功！");
					window.location.href = "failedBorrowIndex.do";
				}
			});
		}
	}
	function failedBorrow_show(id) {
		var url = "iframe:queryFailedBorrowById.do?id=" + id;
		$.jBox(url, {
			title : "撮合的借款",
			width : 600,
			height : 520,
			top : 25,
			buttons : {}
		});
		//ShowIframe("借款失败查看", url, 600, 600);

	}
	function closeMthod() {
		window.close();
	}
</script>