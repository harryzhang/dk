<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
    <link href="css/inside.css"  rel="stylesheet" type="text/css" />

</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	


<div class="nymain">
  <div class="lcnav">
    <div class="tab">
<div class="tabmain">
  <ul><li class="on" style="padding:0 20px;">债权转让及受让协议</li>
  </ul>
    </div>
    </div>
    <div class="line">
    </div>
  </div>
  <div class="lcmain">
    <div class="lcmain_l" style="width: 98%">
    <div class="lctab" style="padding:0 10px; width: 98%">
    <div class="gginfo">
    <h2>${paramMap.columName}</h2>
    <p>

	出借编号：${map.borrowId}${map.id }<br/>
	
	尊敬的 <u>&nbsp;&nbsp;
		<s:if test="#request.map.dbrealNam==''">
					${map.username }
		</s:if>
		<s:else>
			${map.dbrealName }
		</s:else>
	&nbsp;&nbsp;</u>  先生/女士，您好！
	通过合和年在线资质评估与筛选，推荐您以受让他人既有的借贷合同的方式，通过p2p网络平台的方式，自助出借资金给如下借款人，详见《债权列表》，在您接受该批债权转让时，预期您的出借获益情况如下：
	    </p>
		<h2>债权列表</h2>
<ul>
	<li style="width: 200px;" >
		转让人（原债权人）
		<s:if test="#request.map.realName==''">
					${map.username }
		</s:if>
		<s:else>
			${map.realName }
		</s:else>
	</li>
	<li style="width: 350px;">
		  身份证号码：  ${map.idNo}
	</li>
	<li style="width: 200px;">
		  平台用户名 ： ${map.username }
	</li>
</ul>                   
<ul>
	<li style="width: 200px;" >
		  受让人（新债权人）： <s:if test="#request.map.dbrealName==''">
					${map.dbusername }
		</s:if>
		<s:else>
			${map.dbrealName }
		</s:else>
	</li>
	<li style="width: 350px;">
                         身份证号码 ： ${map.dbisno }
	</li>
	<li style="width: 200px;">
		  平台用户名 ： ${map.dbusername }
	</li>
</ul>
               <div style="clear: both;"></div>                                                      
转让债权明细：<span style="width: 300px;"></span>  货币单位：人民币（元） 
<table border="1" cellpadding="0" cellspacing="0" width="95%" >
	<tr>
		<td align="center">序号</td>
		<td align="center">借款人姓名</td>
		<td align="center">身份证号</td>
		<td align="center">出借到期日</td>
		<td align="center">债权价值</td>
		<td align="center">转让比例</td>
		<td align="center">本次转让债权价值</td>
		<td align="center">到期日资产总额</td>
	</tr>
	<tr>
		<td align="center">1</td>
		<td align="center">${mapContent.realName }</td>
		<td align="center">${mapContent.idNo }</td>
		<td align="center">${mapContent.auditTime }</td>
		<td align="center">${sumMap.sumPI }</td>
		<td align="center">${map.debtSum / sumMap.sumPI * 100}%</td>
		<td align="center">${map.debtSum }</td>
		<td align="center">${map.debtSum }</td>
	</tr>
</table>
    <p class="zw">${paramMap.content}</p>
    </div>

    </div>
    </div>
  
  </div>
</div>

<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>

<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script>
dqzt(3);
</script>

</body>
</html>

