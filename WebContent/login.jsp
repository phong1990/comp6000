<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
	<div>
		<form name="loginform" method="POST" action="LoginServlet">
			<table cellpadding="5" cellspacing="10" align="center" width="600px">
				<tr>
					<td colspan="2" align="center"><h3>Login</h3></td>
				</tr>
				<tr>
					<td width="40%" align="right">Username:</td>
					<td width="60%"><input class="txt" type="text" name="username" required /></td>

				</tr>
				<tr>
					<td align="right">Password:</td>
					<td><input type="password" class="txt" name="password" required /></td>

				</tr>
				<tr>
					<td></td>
					<td><input id="yourBtn" type="submit" value="Login" /></td>
				</tr>
				<tr>
					<td></td>
					<td>
						${message}
					</td>
				</tr>
			</table>
		</form>
	</div>
	<%@ include file="parts/footer.html"%>
</body>
</html>