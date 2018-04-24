<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import="Models.User"%>
<html>
<head>
<%
	if (session != null) {
		User user1 = (User) session.getAttribute("user");
		if (user1 != null) {
			switch (user1.getRole()) {
			case User.ADMIN:
%>
<%@ include file="parts/admin.jsp"%>
<%
	break;
			case User.STUDENT:
%>
<%@ include file="parts/student.jsp"%>
<%
	break;
			case User.TEACHER:
%>
<%@ include file="parts/teacher.jsp"%>
<%
	break;
			}

		} else {
%>
<%@ include file="parts/header1.html"%>
<%
	}
	} else {
%>

<%@ include file="parts/header1.html"%>
<%
	}
%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Magic Camp</title>
<script type="text/javascript" src="js/validation.js"></script>
<link type="text/css" rel="stylesheet" href="css/style.css" />
</head>

<body>
	asf
	<%@ include file="parts/footer.html"%>
</body>
</html>
