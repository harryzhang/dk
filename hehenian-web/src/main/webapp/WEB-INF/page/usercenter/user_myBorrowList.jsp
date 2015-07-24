<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<script src="script/jquery-1.7.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script>
	//分页

	function initListInfo(praData) {
		praData["paramMap.id"] = '${id}';
		$.post("myBorrowList.do", praData, initCallBack);
	}

	function initCallBack(data) {
		$("#browlist").html("");
		$("#browlist").html(data);
	}
</script>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<s:if test="pageBean.page!=null || pageBean.page.size>0">
		<s:iterator value="pageBean.page" var="finance">
			<tr>
				<td width="14%" align="center" class="tx"><a href="financeDetail.do?id=${finance.id}"> <shove:img defaulImg="images/default-img.jpg" src="${finance.imgPath}" width="80"
							height="79"></shove:img> </a></td>
				<td width="24%" align="center" class="bt"><h2>
						<s:if test="#finance.borrowWay ==1">
							<img src="images/neiye1_53.jpg" />
						</s:if>
						<s:elseif test="#finance.borrowWay ==2">
							<img src="images/neiye1_55.jpg" />
						</s:elseif>
						<s:elseif test="#finance.borrowWay ==3">&nbsp;</s:elseif>
						<s:elseif test="#finance.borrowWay ==4">
							<img src="images/neiye1_69.jpg" />
						</s:elseif>
						<s:elseif test="#finance.borrowWay ==5">
							<img src="images/neiye1_63.jpg" />
						</s:elseif>
						<a href="financeDetail.do?id=${finance.id}"> <s:property value="#finance.borrowTitle" default="---" />
						</a>
					</h2>
					<p>
						借款金额：<span class="lilv"><span>￥<s:property value="#finance.borrowAmount" default="---" />
						</span>
						</span>
					</p>
				</td>
				<td width="22%" align="center" class="xinyong">年利率：<span>￥<s:property value="#finance.annualRate" default="---" />%</span><br /> 借款期限：<span><s:property
							value="#finance.deadline" default="---" />个月</span>
				</td>
				<td width="40%" align="left" class="jindu">

					<div style="float:left; ">借款进度：</div>
					<div class="progeos">
						<img src="images/index9_56.jpg" width="<s:property value="#finance.schedules" default="0"/>%" height="10" style="float:left; margin:0; padding:0" />
					</div>
					<div style="float:left;">
						<span><s:property value="#finance.schedules" default="0" />%</span>
					</div> <br /> 状态： <s:if test="#finance.borrowStatus ==1">
    正在审核中
    </s:if> <s:elseif test="#finance.borrowStatus ==2">
    
     正在招标中
    
    </s:elseif> <s:elseif test="#finance.borrowStatus ==3">
    
     已满标
    
    </s:elseif> <s:elseif test="#finance.borrowStatus ==4">
     还款中
     </s:elseif> <s:elseif test="#finance.borrowStatus ==5">
   已还完
    </s:elseif> <s:elseif test="#finance.borrowStatus ==6">
     流标
     </s:elseif></td>
			</tr>
		</s:iterator>
	</s:if>
	<s:else>
		<div class="dylist" align="center">
			<ul>
				<li style="text-align: center;">没有数据</li>
			</ul>
		</div>
		<div class="fenye">
			<div class="fenyemain"></div>
		</div>
	</s:else>
</table>
<div align="center">
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
</div>


