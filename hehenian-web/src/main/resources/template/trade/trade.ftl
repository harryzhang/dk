<html>
	<meta http-equiv='Content-Type' content='text/html' />
	<head>
		<title>trade_request</title>
	</head>
	<body>
		<form id="trade_form" name="trade_form" action="${form.action!''}" method="${form.method!''}">
			<#list form.fields as field>
			<input type="hidden" name="${field.name!''}" value="${field.value!''}"/>
			</#list>
			<input type="submit" value="${form.sumbit!''}" style="display:none;"/>
		</form>
	</body>
<script>
	window.onload=document.forms['trade_form'].submit();
</script>
</html>