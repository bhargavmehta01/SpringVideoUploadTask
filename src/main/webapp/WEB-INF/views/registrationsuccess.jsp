<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Registration Form</title>
<link href="<c:url value='/static/css/bootstrap.css' />"
	rel="stylesheet"></link>
<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>
<body>
	<div class="generic-container">
		<div class="alert alert-success lead">Confirmation message :
			${success}</div>
	</div>

	<span class="well pull-left"> <a
		href="<c:url value='/newuser' />">Add More Users?</a>
	</span>

	<span class="well pull-right"> Go to <a
		href="<c:url value='login' />">Login Page</a>
	</span>

</body>
</html>