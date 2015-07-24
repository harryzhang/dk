<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../commons/jsp-head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>用户登录接口</title>
<%@ include file="../commons/html-head.jsp"%>
<link href="/docs/common.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<h3>接口说明:</h3>
<table>
	<tr>
		<td>接口地址：</td>	
		<td>login/login.do</td>	
	</tr>
	<tr>
		<td>接口描述：</td>	
		<td>用户登录</td>	
	</tr>
	<tr>
		<td>请求方式：</td>	
		<td>POST</td>	
	</tr>
	<tr>
		<td>参数列表：</td>	
		<td>
			account: 邮箱或手机帐号或用户id, 不可为null<br/>
			password: 密码(6-20位数字或字母), 不可为null<br/>
		</td>	
	</tr>
	<tr>
		<td>返回说明：</td>	
		<td>
			status: 请求结果: 1&gt;&gt;成功; -1&gt;&gt;失败; -11&gt;&gt;异常.<br/>
			msg: 提示信息, 没有则不返回.<br/>
			data: {<br/>
				rtnCode：手机号是否未验证（2：表示未验证，1：表示正常已验证；只在lgType传值为1的时候此值才返回）<br/>
				memberId: 用户id<br/>
				mobile：用户手机号（rtnCode存在且等于2返回）<br/>
				nickname: 昵称（rtnCode存在且等于2时不返回）<br/>
			}
		</td>
	</tr>
	<tr>
		<td>返回示例：</td>	
		<td>
			<pre>
{
"status":1,
"msg":"登录成功",
"data":{
	"memberId":"100014",
	"sex":"1",
	"memberType":"1",
	"cookieName":"remember",
	"cookieValue":"83f8fb53dec27bf3"
	}
}
			</pre>
		</td>
	</tr>
</table>
<h3>测试:</h3>
<form action="/login/login.do" method="post">
<table style="width: 400px;">
	<tr>
		<td style="width: 150px;">手机帐号/ID：</td>	
		<td><input type="text" name="account" value=""/></td>	
	</tr>
	<tr>
		<td>密码：</td>	
		<td><input type="text" name="password" value=""/></td>
	</tr>
	<tr>
		<td colspan="2" style="text-align: center;"><input type="submit" value="登录"/></td>
	</tr>
</table>
</form>
<br/><br/>
</body>
</html>