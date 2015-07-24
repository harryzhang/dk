<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<%@ include file="/include/includeJs.jsp"%>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	//批量发布
	function updateReleasedList() {
		//获取id集合
		var stIdArray = [];
		$("input[name='cb_ids']:checked").each(function() {
			stIdArray.push($(this).val());
		});

		$("input[name='station']").each(function() {
			stIdArray.push($(this).val());
		});
		
		var ids = stIdArray.join(",");
		if(ids.length == 0 || ids == null)
		{
			alert("请选择需要发布的借款");
			return null;
		}
		var param = {};
		param["paramMap.id"] = ids;
		if (confirm("确认发布？")) {
			$.shovePost("updateReleased.do", param, function(data) {
				if (data == "1") {
					alert("发布成功")
					reload();
				}
				if (data == "2") {
					alert("发布失败");
					return;
				}
			});
		}
	}


	//定时发布
	function updateReleasedTimeList() {
		//获取id集合
		var stIdArray = [];
		var times = $("#times").val();
		$("input[name='cb_ids']:checked").each(function() {
			stIdArray.push($(this).val());
		});

		$("input[name='station']").each(function() {
			stIdArray.push($(this).val());
		});

		if(stIdArray == 0 || stIdArray == null)
		{
			alert("请选择需要定时发布的借款");
			return null;
		}
		
		if(times == 0 || times == null)
		{
			alert("请选择发布时间");
			return null;
		}
		
		var ids = stIdArray.join(",");
		
		var param = {};
		param["paramMap.id"] = ids;
		param["paramMap.times"] = times;
		if (confirm("确认定时发布？")) {
			$.shovePost("updateReleasedTimeList.do", param, function(data) {
				if (data == "1") {
					alert("定时成功")
					reload();
				}
				if (data == "2") {
					alert("定时失败");
					return;
				}
			});
		}
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
		$.jBox("iframe:queryReleasedById.do?id=" + id, {
			title : "待发布的借款",
			width : 600,
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
		window.location.href="queryReleasedListIndex.do";
	}
</script>
<div>
	<table id="help" style="width: 100%; color: #333333;" cellspacing="1"
		cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class=gvHeader>
				<th style="width: 35px;" scope="col">
					选中
				</th>
				<th style="width: 60px;" scope="col">
					借款编号
				</th>
				<!-- 
				<th style="width: 180px;" scope="col">
					用户名
				</th>
				 -->
				<th style="width: 80px;" scope="col">
					真实姓名
				</th>
				<!-- 
				<th style="width: 120px;" scope="col">
					借款来源
				</th>
				 -->
				<th style="width: 120px;" scope="col">
					标的类型
				</th>
				<th style="width: 100px;" scope="col">
					借款标题
				</th>
				<th style="width: 100px;" scope="col">
					借款金额
				</th>
				<th style="width: 100px;" scope="col">
					利率
				</th>
				<th style="width: 100px;" scope="col">
					期限
				</th>
				<th style="width: 100px;" scope="col">
					筹标期限
				</th>
				<th style="width: 140px;" scope="col">
					状态
				</th>
				<th style="width: 150px;" scope="col">
					操作
				</th>
			</tr>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="13">
						暂无数据
					</td>
				</tr>
			</s:if>
			<s:else>
				<s:set name="counts" value="#request.pageNum" />
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td align="center">
							<input id="gvNews_ctl02_cbID" class="downloadId" type="checkbox"
								value="${bean.id }" name="cb_ids" />
						</td>
						<td align="center">
							${bean.number}
						</td>
						<!-- 
						<td align="center">
							<span onclick="queryRecheckById(${bean.id});"
								style="cursor: pointer; color: #3366CC;">${bean.username}</span>
						</td>
						 -->
						<td align="center">
							${bean.realName}
						</td>
						<!-- 
						<td align="center">
							<s:if test="#bean.source==0">小贷公司导入</s:if>
							<s:if test="#bean.source==1">网站注册</s:if>
							<s:if test="#bean.source==2">彩生活用户</s:if>
							<s:if test="#bean.source==3">净值用户</s:if>
						</td>
						 -->
						<td align="center">
							<s:if test="#bean.borrowWay==1">薪金贷</s:if>
							<s:if test="#bean.borrowWay==2">生意贷</s:if>
							<s:if test="#bean.borrowWay==3">业主贷</s:if>
						</td>
						<td align="center">
							${bean.borrowTitle}
						</td>
						<td align="center">
							${bean.borrowAmount}
						</td>
						<td align="center">
							${bean.annualRate}&nbsp;%
						</td>
						<td align="center">
							${bean.deadline}个月
						</td>
						<td align="center">
							${bean.raiseTerm}天
						</td>
						<td align="center"><s:if test="#bean.recheck == null">复审人:${request.adminName}</s:if> <s:else>复审人:${bean.recheck}</s:else> </td>
						<td>
							<a href="javascript:queryRecheckById(${bean.id})">查看</a>
						</td>
					</tr>
				</s:iterator>
			</s:else>
		</tbody>
	</table>
</div>
<table style="border-collapse: collapse; border-color: #cccccc"
	cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
	<tbody>
		<tr>
			<td align="left" style="font-size:12px;">
				&nbsp;
				<input id="chkAll" onclick=checkAll(this); type="checkbox"
					value="checkbox" name="chkAll" />
				全选&nbsp;&nbsp;&nbsp;
				<input id="updateReleasedList" type="button"
					onclick="updateReleasedList()" style="font-size:12px;" value="批量发布"
					name="updateReleasedList" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					发布时间：<input id="times" type="text"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d %H:{%m+5}:%s',readOnly:'readOnly'});"
					name="times" />
					<input id="timesb" type="button"
					onclick="updateReleasedTimeList();" value="定时发布"
					name="timesb" />
			</td>
		</tr>
	</tbody>
</table>
<br>

<shove:page curPage="${pageBean.pageNum}" showPageCount="10"
	pageSize="${pageBean.pageSize }" theme="jsNumber"
	totalCount="${pageBean.totalNum}"></shove:page>

