<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
	<form name="signupform" method="POST" action="signupprocess.jsp"
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
				<td><input id="yourBtn" type="submit" name="signup" value="Sign up" /></td>
				
			</tr>
			<tr>
				<td></td>
				<td><span id="msg"> </span> <%
			 	if (request.getParameter("a") != null) {
			
			  		out.println("Username already exists..! Please login to continue.!");
			  	}
			
			  	if (request.getParameter("b") != null) {
			
			 		out.println("You must login to continue.!");
			 	}
			 %></td>

			</tr>
		</table>
	</form>
</body>
</html>