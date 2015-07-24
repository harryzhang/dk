<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../commons/jsp-head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>获取EloveVIP类型的接口</title>
<%@ include file="../commons/html-head.jsp"%>
<link href="/docs/common.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<h3>接口说明:</h3>
<table>
	<tr>
		<td>接口地址：</td>	
		<td>api/getEloveVIPType.html</td>	
	</tr>
	<tr>
		<td>接口描述：</td>	
		<td>获取EloveVIP类型的接口</td>	
	</tr>
	<tr>
		<td>请求方式：</td>	
		<td>GET/POST</td>	
	</tr>
	<tr>
		<td>参数列表：</td>	
		<td>
			mobile: 手机号<br/>
			token: 令牌标识<br/>
			sign: sign的生成方式md5(param1+param2+。。。paramN+secret)；param1、param2，paramN是经过字典排序的<br/>
		</td>	
	</tr>
	<tr>
		<td>返回说明：</td>	
		<td>
			message: 请求结果:<br/>
			VIPType: VIP类型，1=186会员，2=199会员，3=368会员，其他值都是普通会员。
		</td>
	</tr>
	<tr>
		<td>返回示例：</td>	
		<td>
			<pre>
{
"VIPType":1,
"message":"成功返回信息"
}
			</pre>
		</td>
	</tr>
</table>
<br/><br/>
</body>
</html>