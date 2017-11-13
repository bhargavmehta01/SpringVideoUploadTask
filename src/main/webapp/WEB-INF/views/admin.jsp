<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin page</title>
<link href="<c:url value='/static/css/bootstrap.css' />"
	rel="stylesheet"></link>
<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>
<body>
	<div class="success">
		Dear <strong>${user}</strong>, Welcome to Admin Page. <br /> <a
			href="<c:url value='/newUser' />">Add a User</a> ? <br /> <a
			href="<c:url value="/logout" />">Logout</a>
	</div>
	<div>
 		<form action="s3/uploadfile" method="post" enctype="multipart/form-data">
			<table>
				<tr>
					<td>Upload File</td>
					<td><input type="file" name="samplefile"></td>
					<td><button type="submit">SUBMIT</button></td>
				</tr>
			</table>
		</form> 
	</div>
</body>
</html>