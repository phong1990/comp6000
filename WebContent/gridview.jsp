<%@page import="java.util.List"%>
<%@page import="Utils.Util"%>
<%@page import="Utils.PostgresDB"%>
<%@page import="Models.User"%>
<%@page import="Models.Submission"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    session = request.getSession(false);
    if(session.getAttribute("user") == null)
        response.sendRedirect("login.jsp");
    else
    {
%>
<html>
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

    <head>
    <style>
    .submission tr {
 					display: block;
 					border: 1px solid blue;
					}
	.submission td{
    				border: 1px solid red;
    				padding: 20px;
					}
    
    </style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/style.css" />
        <title>Main Page</title>
    </head>
    
    <body style="overflow:scroll">
    
        <article>
        <%
            try{
                User user =(User) session.getAttribute("user");
                PostgresDB db = PostgresDB.getInstance();
                String role = "Guest";
                role = user.getRole();
                if(role.equals("Student")){ %>
                
           <%} 
           else{
               
               List<Submission> sublist = db.getAllSubmissions();
               %>

               <table class="submission" align="center" >
               
               <br>
              
               <%
               for(int i=0; i<sublist.size(); i++){
                    String image = Util.getImageForBrowser(sublist.get(i).getDBID(), db, sublist.get(i));  
                    String description = sublist.get(i).getDescription();
                    if(image.equals(null))
                                {
                                response.setStatus(response.SC_NOT_FOUND);
                                }
                    else{
                        %>
                            <tr>
                            <td>
                            <a href= "DetailPageServlet?submissionID=<%=sublist.get(i).getDBID()%>">
                            <img src="<%=image%>" height="300px" width="300px" alt="Submission">
                            </a>
                            </td>
		            		<td> Description : <a href= "DetailPageServlet?submissionID=<%=sublist.get(i).getDBID()%>"><%=description %></a></td>
                            </tr>
                         <br>
                        <%if(role.equals("Admin")){  %>
                                <tr>
                                <td><input type="button" value="View"/></td>
                                <td><input type="button" value="Delete"/></td>                                                        
                                </tr>
                            <%
                                                }
                                        }
                                } 
                        }
            }
                    catch(Exception e)
                        {
                        out.println(e.getMessage());
                        }
                    %>
                </table>

           <%}
%>
           
                    
        </article>
    </body>
</html>
