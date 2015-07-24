<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../commons/jsp-head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>创建Elove订单/新旧站用户资料转换接口</title>
<%@ include file="../commons/html-head.jsp"%>
<link href="/docs/common.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<h3>接口说明:</h3>
<table>
	<tr>
		<td>接口地址：</td>	
		<td>api/createEloveOrder.html</td>	
	</tr>
	<tr>
		<td>接口描述：</td>	
		<td>查询到账之后，请再传指定参数调用此接口生成elove订单</td>	
	</tr>
	<tr>
		<td>请求方式：</td>	
		<td>GET/POST</td>	
	</tr>
	<tr>
		<td>参数列表：</td>	
		<td>
			memberId: （必须传）<br/>
			bank: 用户转账银行（没有则不传）<br/>
			payAmount: 确认到账的金额，需要生成Elove订单时这个值也是必须的<br/>
			token: 令牌标识<br/>
			sign: sign的生成方式md5(param1+param2+。。。paramN+secret)；param1、param2，paramN是经过字典排序的<br/>
		</td>	
	</tr>
	<tr>
		<td>返回说明：</td>	
		<td>
			message: 请求结果: <br/>
		</td>
	</tr>
	<tr>
		<td>返回示例：</td>	
		<td>
			<pre>
			</pre>
		</td>
	</tr>
</table>
<br/><br/>
</body>
</html>