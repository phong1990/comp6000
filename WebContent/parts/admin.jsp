<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/style.css" type="text/css" />
</head>
<body>
	<div class="left">
		<div id="heading">
			<table>
				<tr>
					<td><a href="home.jsp"><img src="img/images.png"
							style="width: 60px; height: 80px; border-top: auto"
							title="Home Page" align="left"></a></td>
					<td><h1>MAGIC (Mentoring Alabama Girls in Computing)</h1></td>
					<td></td>
					<td>
						<%
							String userName = request.getParameter("username");
							out.println("Welcome " + userName);
						%>
					</td>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td><div
							style="float: right; padding-right: 20px; margin-right: 20px">
							<a href="register.jsp" class="top">Register</a>
						</div></td>
				</tr>
			</table>
		</div>

	</div>
</body>

<div class="left">
	<div id="heading">
		<img src="img/images.png" height="60px" width="80px" align="left"
			style="border-top: auto">

		<h1>MAGIC (Mentoring Alabama Girls in Computing)</h1>
	</div>

	<h2></h2>

	<input type="submit" value="Logout"
		style="width: 70px; font-size: 16px">

	<div id="alignment">
		<form action="gridview.jsp">
			<input type="submit" value="Grid View"
				style="width: 120px; font-size: 16px">
		</form>
	</div>
	<div id="rightalignment">
		<form action="admin.jsp">
			<input type="submit" value="Admin"
				style="width: 120px; font-size: 16px">

		</form>

	</div>

</div>

</head>
<body>

</body>
</html>



