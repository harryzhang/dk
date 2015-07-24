<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
     <title>合和年在线后台管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>    
	<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="../css/user_css.css" />
	
	<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" language="javascript">
		$(function(){
			$("#btnSearch").click(function(){
				var modeSearch = $("#modeSearch").val();
				if(modeSearch == ""){
					alert("请填写搜索的内容");
				}else{
					$("#form1").submit();
				}
			});
		});
	</script>

</head>



<body style="width: 1120px;">

<div class="main_alll">
	<div style="width:400px; height:280px;float:left;">
	<h2 class="main_alll_h2">登陆信息</h2>
    <div class="main_div_a" style="min-width:380px;">
    	<p>欢迎您：${sessionScope.admin.userName }</p>
    	<p>您的权限：${roleName } </p>
    	<p>上次登陆时间/IP：${sessionScope.admin.lastTime } / ${sessionScope.admin.lastIP }</p>
    	<p></p>
    	<p></p>
    	<p></p>
    	<p></p>
    </div>
    </div>
    <div style="width:300px;float:left;">
    <h2 class="main_alll_h2">个人信息</h2>
    <div class="main_div_a">
    	<p>帐号：${sessionScope.admin.userName }</p>
    	<p>真实姓名： ${sessionScope.admin.realName}</p>
    	<p>身份证号码：${sessionScope.admin.card }</p>
    	<p>手机：${sessionScope.admin.telphone }</p>
    	<p>邮箱：${sessionScope.admin.email }</p>
    	<p>账户余额：${sessionScope.admin.subAcctMoney }</p>
    	<p> <a href="updateAdminInit.do?id=${sessionScope.admin.id }"><span style="color:red;">修改个人信息</span></a>&nbsp;&nbsp;&nbsp;<a href="updatePasswordInit.do"><span style="color:red;">修改密码</span></a></p>
    </div>
    </div>
</div>
<div class="main_alll">
	<h2 class="main_alll_h2">审核管理信息</h2>
    <div class="main_div_a">
    	<strong><b>借款管理</b></strong>
        <p>初审中的借款：  (<a href="queryFirstTrialIndex.do"><span style="color:red;">${session.map.pcount }</span></a>)条</p>
        <p>复审中的借款： (<a href="queryRecheckIndex.do"><span style="color:red;">${session.map.fscount }</span></a>)条</p>
        <p>满标借款审核： (<a href="borrowFullScale.do"><span style="color:red;">${session.map.fcount}</span></a>)条</p>
        <p>申请债权转让： (<a href="queryAuditDebtInit.do"><span style="color:red;">${session.map.zccount }</span></a>)条</p>
        <p>债权转让中：(<a href="queryTransferByIdIndex.do"><span style="color:red;"> ${session.map.zacount }</span></a>)条</p>
    </div>
<%--    <div class="main_div_a"> --%>
<%--    	<strong><b>认证管理</b></strong>--%>
<%--        <p>用户基本资料审核(待审核)：  (<a href="queryPersonInfolistindex.do"><span style="color:red;">${session.map.jbxxcount }</span></a>)条</p>--%>
<%--        <p>额度申请(审核中)： (<a href="querycreditindex.do"><span style="color:red;">${session.map.edcount }</span></a>)条</p>--%>
<%--        <p>可选资料认证：(<a href="queryPersonInfolistindex.do"><span style="color:red;"> ${session.map.kxcount }</span></a>)条</p>--%>
<%--    </div>--%>
    <div class="main_div_a">
    	<strong><b>资金管理</b></strong>
        <p>等待审核的提现(审核中)： (<a href="queryAllWithdrawInit.do?paramMap.status=1"><span style="color:red;">${session.map.ddtxcount }</span></a>)条</p>
        <p>转账中的提现(转账中)：  (<a href="queryAllWithdrawInit.do?paramMap.status=4"><span style="color:red;">${session.map.zctxcount }</span></a>)条</p>
<%--<p>银行卡变更申请(审核中)： (<a href="queryModifyBankInit.do?types=2"><span style="color:red;">${session.map.yhbgcount}</span></a>)条</p>--%>
    </div>
</div>


<div class="main_alll">
	<h2 class="main_alll_h2">功能模块搜索</h2>
	<div class="main_div_a" style="min-height:70px;min-width:360px;">
		<form id="form1" action="modesearch.do" method="post" target="left">
			<br/>
			<p>
				功能模块搜索：
				<input type="text" id="modeSearch" name="modeSearch" /> &nbsp;<input type="button" id="btnSearch" value="搜索"/>
			</p>
		</form>
	</div>
</div>	

</body>



    <!--<br />借款管理：<br />
    借款等待资料审核： (<a href="borroww.do"><span style="color:red;">${session.map.ddcount }</span></a>)条 	
    借款初审审核：  (<a href="borrowf.do"><span style="color:red;">${session.map.pcount }</span></a>)条
    借款满标审核： (<a href="borrowFullScale.do"><span style="color:red;">${session.map.fcount}</span></a>)条
    申请债权转让： (<a href="queryApplyDebtInit.do"><span style="color:red;">${session.map.zccount }</span></a>)条
    债权转让中：(<a href="queryAuctingAssignmentDebtInit.do"><span style="color:red;"> ${session.map.zacount }</span></a>)条
    <br />认证管理：<br />
    用户基本信息审核(待审核、失败)：(<a href="queryPersonInfolistindex.do"><span style="color:red;">${session.map.jbxxcount }</span></a>)条
    用户基本资料审核(待审核、失败)：(<a href="rechargeecordsInit.do"><span style="color:red;">${session.map.jbzlcount }</span></a>)条
    手机变更申请(审核中)：(<a href="updatephoneIndexChange.do"><span style="color:red;">${session.map.sjcount }</span></a>)条
    额度申请(审核中)：(<a href="querycreditindex.do"><span style="color:red;">${session.map.edcount }</span></a>)条
    可选资料认证：(<a href="queryselectInitindex.do"><span style="color:red;">${session.map.kxcount }</span></a>)条
    <br />资金管理：<br />
    等待审核的提现(审核中)：(<a href="queryCheckInit.do"><span style="color:red;">${session.map.ddtxcount }</span></a>)条
    转账中的提现(转账中)：(<a href="queryTransInit.do"><span style="color:red;">${session.map.zctxcount }</span></a>)条
    银行卡变更申请(审核中)：(<a href="queryModifyBankInit.do"><span style="color:red;"> ${session.map.yhbgcount }</span></a>)条-->

</html>


