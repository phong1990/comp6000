<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="Models.User"%>
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
				</tr>
			</table>
			<div style="float: right; position: absolute; top: 0; right: 0;">
				<%
					if (session != null) {
						User user = (User) session.getAttribute("user");
						if (user != null) {
							out.println("Welcome " + user.getfirstName() + "!");
						}
					}
				%>
				<br>
				<br>
				<div style="float: right;">
					<a href="LogoutServlet" class="top">Logout</a>
				</div>
			</div>
			<table>
				<tr>
					<td><div style="float: right;">
							<a href="gridview.jsp" class="top">Browse Works</a>
						</div>
						<div style="float: right;">
							<a href="admin.jsp" class="top">Administration</a>
						</div></td>
				</tr>
			</table>


		</div>

	</div>
</body>
</html>



