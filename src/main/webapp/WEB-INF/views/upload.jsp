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
 		<form method="POST" action="upload?${_csrf.parameterName}=${_csrf.token}" enctype="multipart/form-data">
		File to upload: <input type="file" name="file" onchange="setFileInfo(this.files)" accept="video/mp4">
 
		Name: <input type="text" name="name">
 
 
		<input id="Button" type="submit" value="Upload"> Press here to upload the file!
		<br/>
		<div id="infos"></div>
	</form>
	</div>
	<script>
		var button = document.getElementById("Button");
		var myVideo;
		window.URL = window.URL || window.webkitURL;
		function setFileInfo(files) {
			myVideo = files[0];
			var video = document.createElement('video');
			video.preload = 'metadata';
			video.onloadedmetadata = function() {
				window.URL.revokeObjectURL(this.src)
				var duration = video.duration;
				myVideo.duration = duration;
				updateInfos();
			}
			video.src = URL.createObjectURL(files[0]);
		}

		function updateInfos() {
			document.querySelector('#infos').innerHTML = "";
			document.querySelector('#infos').innerHTML += "<div>"
					+ "Duration of the video is : " + myVideo.duration
					+ '</div>';
			if(myVideo.duration>600){
				alert('Sorry');
				button.disabled = true;
			}
		}

	</script>
</body>
</html>