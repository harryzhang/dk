<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../commons/jsp-head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>用户搜索接口</title>
<%@ include file="../commons/html-head.jsp"%>
<link href="/docs/common.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<h3>接口说明:</h3>
<table>
	<tbody>
	<tr>
		<td>接口地址：</td>	
		<td>/api/searchData.html</td>	
	</tr>
	<tr>
		<td>接口描述：</td>	
		<td>搜索接口</td>	
	</tr>
	<tr>
		<td>请求方式：</td>	
		<td>POST</td>	
	</tr>
	<tr>
		<td>参数列表：</td>	
		<td>
		    以下参数不限则不传或者传-1
			userId: 用户ID（查用户ID请使用另一个信息接口）<br/>
			ageStart:最小年龄，如18<br/>
			ageEnd:最大年龄，如35<br/>
			gender:性别，0代表男性，1代表女性<br/>
			heightStart:最小身高,如160<br/>
			heightEnd:最大身高，如170<br/>
			marriage:婚姻状况，
				0-保留、
				1-未婚、
				2-已婚、
				3-离异、
				4-丧偶、
				5-分居
			<br/>
			workCity:传入新站的syscode<br/>
			education:
				21-高中及以下、
				22-中专、
				23-大专、
				24-本科、
				25-硕士、
				26-博士及以上
			<br/>
			salary:21-2000及以下、
					22-2001-5000元、
					23-5001-8000元、
					24-8001-10000元、
					25-10001-20000元、
					26-20001-50000元、
					27-50000以上
			<br/>
			pageSize:一页显示多少个，默认10个<br/>
			page:当前页，从1开始<br/>
			token: 令牌标识<br/>
			sign: sign的生成方式md5(param1+param2+。。。paramN+secret)；param1、param2，paramN是经过字典排序的<br/>
		</td>	
	</tr>
	<tr>
		<td>返回说明：</td>	
		<td>
			status: 请求结果: 1&gt;&gt;成功; -1&gt;&gt;失败; -11&gt;&gt;异常.<br/>
			msg: 提示信息, 没有则不返回.<br/>
			curPage:当前页<br/>
			pageSize:一页显示数<br/>
			total:总数<br/>
			totalPage:总页数<br/>
			data: {<br/>
				userId: 用户id<br/>
				gender: 性别<br/>
				nickName: 昵称<br/>
				trueName: 真实姓名<br/>
				age: 年龄<br/>
				avatar: 形象照<br/>
				height: 身高<br/>
				weight: 体重<br/>
				marriage: 婚姻状况<br/>
				workCity: 工作地<br/>
				hometown: 籍贯<br/>
				education: 学历<br/>
				occupation: 职业<br/>
				salary: 收入<br/>
				house: 购房情况<br/>
				car: 购车情况<br/>
				children: 孩子情况<br/>
				smoking: 抽烟情况<br/>
				drinking: 喝酒情况<br/>
				lastLogTime: 最后一次登录时间<br/>
				lastModIP: 最后一次修改资料的IP<br/>
				createTime: 注册时间<br/>
				modifyTime: 最后一次修改资料时间<br/>
			}
		</td>
	</tr>
	<tr>
		<td>返回示例：</td>	
		<td>
			<pre>
{
status:1,
"message":"成功返回信息",
"data":{"age":28,"avatar":"http://p0.zastatic.com/ez/2113/314592/1393481370713_200x200.jpg","car":"计划购车",
		"children":"有小孩，归自己","createTime":"2013-12-17 11:15:00","drinking":"未填写","education":"本科",
		"gender":"男","height":164,"hometown":"未填写","house":"保密，不显示","lastLogTime":"2014-03-28 17:57:52",
		"lastModIP":"183.62.134.100","marriage":"未婚","modifyTime":"2014-03-28 17:57:52","nickName":"会员62867430",
		"occupation":"未填写","salary":"2001-5000元","smoking":"未填写","trueName":"未填写","userId":314592,
		"weight":64,"workCity":"河南郑州"}
}
			</pre>
		</td>
	</tr>
	</tbody>
</table>
<br/><br/>
</body>
</html>