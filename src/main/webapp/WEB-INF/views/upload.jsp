<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home page</title>
<link href="<c:url value='/static/css/bootstrap.css' />"
	rel="stylesheet"></link>
<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>
<body>
<div class="generic-container">
		<div class="alert alert-success lead">
		Dear <strong>${user}</strong>, Welcome to the Home Page. <br /> <a
			href="<c:url value='/newuser' />">Add a User</a> ? <br /> <a
			href="<c:url value="/logout" />">Logout</a>
	</div>
	</div>
	<div class="generic-container">
 		<form method="POST" action="upload?${_csrf.parameterName}=${_csrf.token}" enctype="multipart/form-data">
		File to upload: <input type="file" name="file" onchange="setFileInfo(this.files)" accept="video/mp4">
 
 		<br/>
		Name: <input type="text" name="name">
 
		<input id="Button" type="submit" value="Upload"> Press here to upload the file!
		<br/>
		<div id="infos"></div>
		<br/>
		
		Your uploaded videos will appear below:
			<c:forEach items="${videos}" var="vid" varStatus="counter">
				<div class="row videopadding">
					<video width="400" controls>
						<source src="${vid.url}" type="video/mp4">
					</video>
				</div>
			</c:forEach>
		</form>
	</div>
	<script src="//cdn.jsdelivr.net/hls.js/latest/hls.min.js"></script>

	<script>
		var button = document.getElementById("Button");
		var input = document.getElementsByTagName('input')[0];
		var myVideo;
		window.URL = window.URL || window.webkitURL;

		function setFileInfo(files) {
			button.disabled = false;
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
			if (myVideo.duration > 600) {
				alert("Upload not allowed, because the duration of the video is "
						+ myVideo.duration
						+ " seconds. Please upload mp4 files with time duration less than 10 minutes(600 seconds) only.");
				button.disabled = true;
				input.value = null;
				myVideo = null;
			}
			document.querySelector('#infos').innerHTML = "";
			document.querySelector('#infos').innerHTML += "<div>"
					+ "Duration of the video is : " + myVideo.duration
					+ " seconds" + '</div>';
		}
	</script>
</body>
</html>