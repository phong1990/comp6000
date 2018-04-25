<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>Insert title here</title>
</head>
<body>
	${message}
	<table style="width: 60%">
		<tr>
			<td colspan="3"><img src="${thumbnail}" alt="No image" height="200" width="200"></td>
		</tr>
		<tr>
			<td><h4>Description:</h4></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td colspan="3">${description}</td>
		</tr>

	</table>
	<br></br>
	<form method="post" action="RatingServlet">
		<table style="width: 60%">
			<tr>
				<td>Average Rating:<input type="hidden" name="submissionid"
					value="${subid}" /></td>
				<td>${avgrating}</td>
				<td>${dropdownList}</td>
			</tr>
		</table>
	</form>
	${teacherForm}
	<br></br>
	<form method="post" action="CommentServlet">
		<table>
			<tr>
				<td><h4>
						Leave your comment here:<input type="hidden" name="submissionid"
							value="${subid}" />
					</h4></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td colspan="3"><input type="text" name="comment" size="200" /></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td><input type="submit" value="Submit Comment"></td>
			</tr>
		</table>
	</form>
	<br></br>
	<table>
		<tr>
			<td><h4>Comments</h4></td>
			<td></td>
			<td></td>
		</tr>
		<c:forEach items="${commentList}" var="comment">
			<tr>
				<td colspan="3">"${comment.text}"<br>
					<h5>${comment.fname}</h5></td>
			</tr>
			<hr />
		</c:forEach>
	</table>
	<%@ include file="parts/footer.html"%>
</body>
</html>