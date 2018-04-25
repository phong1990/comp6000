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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Magic Camp</title>
<script type="text/javascript" src="js/validation.js"></script>
<link type="text/css" rel="stylesheet" href="css/style.css" />
</head>
<body>

	<form name="signupform" method="POST" action="RegisterServlet"
		onSubmit="return validate()">

		<table cellpadding="5" cellspacing="10" align="center" width="50%">
			<tr>
				<td align="center" colspan="2"><h3>Register</h3></td>
			</tr>

			<tr>
				<td width="40%" align="right">First Name:</td>
				<td width="60%"><input class="txt" type="text" name="firstname" /></td>

			</tr>

			<tr>
				<td align="right">Last Name:</td>
				<td><input class="txt" type="text" name="lastname" /></td>

			</tr>
			<tr>
				<td align="right">Username:</td>
				<td><input class="txt" type="text" name="username" /></td>

			</tr>
			<tr>
				<td align="right">Password:</td>
				<td><input class="txt" type="password" name="password" /></td>

			</tr>
			<tr>
				<td align="right">Confirm Password:</td>
				<td><input class="txt" type="password" name="confirm" /></td>

			</tr>
			<tr>
				<td></td>
				<td><input id="yourBtn" type="submit" name="signup"
					value="Sign up" /></td>

			</tr>
			<tr>
				<td></td>
				<td><span id="msg"> </span> ${message}</td>

			</tr>
			
		</table>
	</form>
	<%@ include file="parts/footer.html"%>
</body>
</html>