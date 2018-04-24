<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Submitting your work here</title>
</head>
<body>
	<h1>Upload your work</h1>
	<form method="post" action="SubmissionServlet"
		enctype="multipart/form-data">
		<table>
			<tr>
				<td>Description:</td>
				<td><input type="text" name="description" size="200" /></td>
			</tr>
			<tr>
				<td>Thumbnail Photo:</td>
				<td><input type="file" name="thumbnail" size="50" /></td>
			</tr>
			<tr>
				<td>File:</td>
				<td><input type="file" name="file" size="50" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Submit"></td>
			</tr>
		</table>
	</form>
</body>
</html>