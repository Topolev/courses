<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="by.topolev.config.InitValues"%>
<%@ page import="java.io.File"%>
<%@ page import="java.util.List"%>

<html>
<head>
<title>The firts form</title>
<link href="<c:url value="/resources/css/bootstrap.min.css" />"
	rel="stylesheet" />
<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" />
<script src="<c:url value="/resources/js/jquery-1.12.3.min.js" />">
	
</script>

</head>
<body>
	<div class="container">
		<h1>
			<span>List image</span>
			<a href="<%=request.getContextPath()%>/form_upload_image.jsp" id="return-href">Return to uploading form</a>
		</h1>
		
		<%
			String rootDirectory = InitValues.getValue("pathUploadImage");

			File directory = new File(rootDirectory);
			if (directory.isDirectory() && directory.exists()) {
				pageContext.setAttribute("files", directory.list());
				pageContext.setAttribute("rootUrl", request.getContextPath());
			}
		%>
		
		<% int i = 1; %>
		<c:forEach items="${files}" var="file">
			<% if (i % 4 == 1){ %>
				<div class="row img">
			<%} %>
			<div class="col-md-3">
				<p>Filename: ${file}</p>
				<img alt="" src="${rootUrl}/show?file=${file}"/>
				
			</div>
			<% if (i % 4 == 0){ %>
				</div>
			<%} %>
			<% i++;%>
		</c:forEach>


	</div>
</body>
</html>

