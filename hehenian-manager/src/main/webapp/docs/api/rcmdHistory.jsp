<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../commons/jsp-head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>推荐历史接口</title>
<%@ include file="../commons/html-head.jsp"%>
<link href="/docs/common.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<h3>接口说明:</h3>
<table>
	<tr>
		<td>接口地址：</td>	
		<td>/api/recommendedHistory.html</td>	
	</tr>
	<tr>
		<td>接口描述：</td>	
		<td>推荐历史</td>	
	</tr>
	<tr>
		<td>请求方式：</td>	
		<td>GET</td>	
	</tr>
	<tr>
		<td>参数列表：</td>	
		<td>
			page: 页码，从1开始<br/>
			pageSize: 分页大小<br/>
			userId: 用户id, 不可为null<br/>
			token: 令牌标识<br/>
			sign: sign的生成方式md5(param1+param2+。。。paramN+secret)；param1、param2，paramN是经过字典排序的<br/>
		</td>	
	</tr>
	<tr>
		<td>返回说明：</td>	
		<td>
			curPage: 当前页码.<br/>
			pageSize: 分页大小.<br/>
			total: 总记录数.<br/>
			totalPage: 总页数.<br/>
			result: [{<br/>
				objectId：会员id<br/>
				recommandTime: 用户id<br/>
				nickName：昵称<br/>
				salary: 收入<br/>
				education: 学历<br/>
				workCity: 工作地<br/>
				sex: 性别<br/>
				avatar: 形象照<br/>
				age: 年龄<br/>
			}]
		</td>
	</tr>
	<tr>
		<td>返回示例：</td>	
		<td>
			<pre>
{
    "curPage": 1,
    "pageSize": 10,
    "total": 537,
    "totalPage": 54，
    "result": [
    {
        "objectId": 251338,
        "recommandTime": "2014-03-25 14:49:55",
        "nickName": "妮妮",
        "salary": "2001-5000元",
        "education": "大专",
        "workCity": "河南郑州",
        "sex": "女",
        "avatar": "http://p4.zastatic.com/ez/3777/251338/1386928346769_200x200.jpg",
        "age": 20
    },
    {
        "objectId": 15087,
        "recommandTime": "2014-01-23 13:53:11",
        "nickName": "岁月静好",
        "salary": "2001-5000元",
        "education": "本科",
        "workCity": "河南开封",
        "sex": "女",
        "avatar": "http://p0.zastatic.com/ez/default/default1_200x200.jpg",
        "age": 28
    }]
}
			</pre>
		</td>
	</tr>
</table>
<!--  
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
-->
<br/><br/>
</body>
</html>