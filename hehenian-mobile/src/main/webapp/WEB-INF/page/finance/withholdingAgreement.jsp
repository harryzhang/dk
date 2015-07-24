<%@ page contentType="text/html; charset=utf-8"%>
<% request.setAttribute("menuIndex", 2); %>
<!doctype html>
<html>
	<head>
		<%@ include file="../common/head.jsp" %>
		<title>用户线上代扣协议</title>
		<style type="text/css">
        p {
            font-size: .75rem;
            line-height: 1.6;
            padding: .5rem 0;
        }
        h1 {
            text-align: center;
            font-size: 1rem;
            padding-bottom: 1rem;
        }
        article{
        	padding: 1rem;
        }
        </style>

	</head>
<body class="bg-2">
		 <article>
            <h1 class="title">充值授权协议</h1>
            <p class="user-data">授权人姓名：<span></span>
            </p>
            <p class="user-data">证件类型：<span>身份证</span>
            </p>
            <p class="user-data">证件号码：<span></span>
            </p>
            <p class="user-data">合和年在线用户名：<span></span>
            </p>
            <p class="user-data">日期：<span></span>
            </p>
            <p class="ps">被授权人：深圳市彩付宝网络技术有限公司（“合和年在线”）就授权人向其合 和年在线用户名项下账户（“授权人合和年在线账户”）充值的相关事宜向合和 年在线授权如下：
            </p>
            <p class="paragraph">一、授权人授权合和年在线根据授权人的充值指令从本授权书第二条所述的 授权 人的银行账户通过合和年在线指定的第三方支付机构主动扣收本授权书第二 条所 述的等值于充值金额的款项，用于向授权人合和年在线账户充值（“充值 服务”）。
            </p>
            <p class="paragraph">二、授权人的银行账户及充值金额如下： 户名： 账号： 开户银行： 充值金额：人民币元（含第三方需收的手续费（如有））。
            </p>
            <p class="paragraph">三、授权人知晓并确认，本授权书系使用授权人合和年在线用户名、以网络 在线点击确认的方式向合和年在线发出。本授权书自授权人在合和年在线网站点 击确认时生效，由合和年在线通过第三方支付机构从授权人的银行账户中代扣相 当于充值金额的款项。授权人已经通过本授权书确认代扣款项的银行账户信息， 在代扣的过程中，合和年在线根据本授权书提供的银行账户信息进行相关操作， 无需再向授权人确认银行账户信息和密码。本授权书一经生效即不可撤销。授权 人确认并承诺，合和年在线根据本授权书所采取的全部行动和措施的法律后果均 由授权人承担。
            </p>
            <p class="paragraph">四、授权人知晓并同意，受授权人银行账户状态、银行、第三方支付机构及 网络等原因所限，本授权书项下充值可能会通过多次代扣交易方可完成，合和年 在线不对充值服务的资金到账时间做任何承诺。合和年在线仅根据本授权书所述 的授权人的授权进行相关操作，合和年在线无义务对其根据本授权书所采取的全 部行动和措施的时效性和结果承担任何责任。
            </p>
            <p class="paragraph">特此授权。</p>



        </article>

	<%@ include file="../common/tail.jsp" %>
</body>
</html>