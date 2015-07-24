<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
	<h3>Message : ${message}</h3>	
	<h3>Username : ${username}</h3>	
	
	<a href="${pageContext.request.contextPath}/j_spring_security_logout"> Logout</a>
	
</body>
</html>