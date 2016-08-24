
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.List" %>
<html>
<head>
<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet" />
<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" />
<script src="<c:url value="/resources/js/jquery-1.12.3.min.js" />"></script>
</head>
<body>
	<div class="container">
		<h1>
			<span>Error during upload image.</span>
			<a href="<%=request.getContextPath()%>/form_upload_image.jsp" id="return-href">Return to uploading form</a>
		</h1>
		<%
			List<String> errors = (List<String>) request.getAttribute("errors");
			pageContext.setAttribute("errors", errors);
		%>
		<c:forEach items="${errors}" var="error">
			<span class="error-message">
				<c:out value="${error}"></c:out>
			</span>
		</c:forEach>
	</div>
</body>
</html>