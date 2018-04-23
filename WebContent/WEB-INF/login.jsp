<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Magic Camp</title>
<script type="text/javascript" src="js/validation.js"></script>
<link type="text/css" rel="stylesheet" href="css/style.css" />
</head>
<body>
	<header>
	<div style="float: left; padding-left: 20px">
		<a href="index.jsp" class="top">Magic Camp</a>
	</div>
	<div style="float: right; padding-right: 20px; margin-right: 20px">
		<a href="register.jsp" class="top">Register</a>
	</div>
	<div style="float: right; padding-right: 20px">
		<a href="login.jsp" class="top">Login</a>
	</div>
	</header>

	<div>
		<form name="loginform" method="POST" action="loginprocess.jsp">
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
						<%
							if (request.getParameter("c") != null) {
								out.println("Username or password is incorrect.!");
							}
						%>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>