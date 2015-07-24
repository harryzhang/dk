<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<script type="text/javascript">
 $(function(){
   $("#excel").click(function(){  
       
      window.location.href="exportRechargeRecord.do";
   });
 });
</script>
<script type="text/javascript" src="../css/popom.js"></script>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../css/admin/popom.js"></script>

		<link rel="stylesheet" href="../css/jbox/Gray/jbox.css"
			type="text/css"></link>
		<script language="javascript" type="text/javascript"
			src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript">

	function select_UserInfo(id){
		$.jBox("iframe:queryUserFundRecharge.do?i="+id, {
		    title: "用户信息详情",
		    width: 679,
		    height: 500,
		    buttons: { '关闭': true }
		});
	}
	
		

	function initCallBack(data){
 		$("#dataInfo").html(data);
	}
		//弹出窗口关闭
   		function close(){
   			 ClosePop();  			  
   		}
</script>

	<div>
		<table id="help" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
				 
					<th style="width: 30px;" scope="col">
						序号
					</th>
					<th style="width: 200px;" scope="col">
						用户名
					</th>
					<th style="width: 150px;" scope="col">
						充值类型
					</th>
					
					<th style="width: 100px;" scope="col">
						充值金额
					</th>
					<th style="width: 100px;" scope="col">
						手续费
					</th>
					<th style="width: 100px;" scope="col">
						到账金额
					</th>
					<th style="width: 120px;" scope="col">
						充值时间
					</th>
					
					<th style="width: 80px;" scope="col">
						状态
					</th>
					<th style="width: 80px;" scope="col">
						操作
					</th>
					
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="9">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:set name="counts" value="#request.pageNum"/>
					<s:iterator value="pageBean.page" var="bean" status="s">
						<tr class="gvItem">
						   
							<td align="center" style="width:100px;">
								<s:property value="#s.count+#counts"/>
							</td>
							<td>
								${bean.username}
							</td>
							<td>
							<s:if test="#bean.type==1">支付宝支付</s:if>
							<s:if test="#bean.type==2">环迅支付</s:if>
							<s:if test="#bean.type==3">国付宝</s:if>
							<s:if test="#bean.type==4">手动充值</s:if>
							<s:if test="#bean.type==6">线下充值</s:if>
							<s:if test="#bean.type==51">手工充值</s:if>
							<s:if test="#bean.type==52">虚拟充值</s:if>
							<s:if test="#bean.type==53">奖励充值</s:if>
							</td>
							<td>
								${bean.dealMoney}
							</td>
							<td>
								${bean.poundage}
							</td>
							<td>
								${bean.realMoney}
							</td>
							<td>
								<s:date name="#bean.rechargeTime" format="yyyy-MM-dd HH:mm:ss" ></s:date>
							</td>
							<td>
							<s:if test="#bean.result==0">失败</s:if>
							<s:if test="#bean.result==1">成功</s:if>
							<s:if test="#bean.result==2">审核中</s:if>
							</td>
							<td>
								<span onclick="select_UserInfo(${id});" style="cursor:pointer;">查看</span>
							</td>
						</tr>
					</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
