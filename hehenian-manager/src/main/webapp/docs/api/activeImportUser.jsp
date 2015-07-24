<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../commons/jsp-head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>用户信息接口</title>
<%@ include file="../commons/html-head.jsp"%>
<link href="/docs/common.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<h3>接口说明:</h3>
<table>
	<tr>
		<td>接口地址：</td>	
		<td>api/active.html</td>	
	</tr>
	<tr>
		<td>接口描述：</td>	
		<td>激活导入用户</td>	
	</tr>
	<tr>
		<td>请求方式：</td>	
		<td>GET/POST</td>	
	</tr>
	<tr>
		<td>参数列表：</td>	
		<td>
			name:用户真实姓名<br/>
			phone:用户电话号<br/>
			code: 企业代码<br/>
			uid: 注册流程后生成的userid<br/>
			token: 令牌标识<br/>
			sign: sign的生成方式md5(param1+param2+。。。paramN+secret)；param1、param2，paramN是经过字典排序的<br/>
		</td>	
	</tr>
	<tr>
		<td>返回说明：</td>	
		<td>
			state: 请求结果: 参数异常; 用户资料异常; 成功返回信息<br/>
			data: {<br/>
			成功与否
			}
		</td>
	</tr>
	<tr>
		<td>返回示例：</td>	
		<td>
			<pre>
{"data":true,"state":200}
			</pre>
		</td>
	</tr>
</table>
<br/><br/>
</body>
</html>