<%@page import="Models.ImageSubmission"%>
<%@page import="java.util.ArrayList"%>
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
     .submissionColumn {
 					float: left;
 					width: 33.33%;
 					padding: 5px;
 					position: relative;
 					margin: 0 auto;
 					max-width: 800px;
					}
					
	.submissionColumn .content{
					position:absolute;
					bottom:0;
					background: rgba(0,0,0,0.5);
					color: #f1f1f1;
					width: 90%;
					padding: 20px;
					}
	.submissionRow::after{
    				content: "";
    				clear: both;
    				display: table;
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
                
                List<Submission> sublist = db.getAllSubmissions();
                List<ImageSubmission> imageList = new ArrayList<ImageSubmission>();
                 
                for(int i=0; i<sublist.size(); i++){
                    String image = Util.getImageForBrowser(sublist.get(i).getDBID(), db, sublist.get(i));  
                    String description = sublist.get(i).getDescription();
                    if(image.equals(null))
                                {
                                response.setStatus(response.SC_NOT_FOUND);
                                }
                    else{
                    	ImageSubmission is = new ImageSubmission();
                    	is.setDescription(description);
                    	is.setImage(image);
                    	is.setSubID(sublist.get(i).getDBID());
                    	imageList.add(i, is);	
                        }
                 }
                  int count =0; 
                  String image1;
                  String description1;   
                  int subID;
                  for(int j=0; j<imageList.size(); j++){
                	  image1 = imageList.get(j).getImage();
                	  description1 = imageList.get(j).getDescription();
                	  subID = imageList.get(j).getSubID();
                	  if(count >= 3){
                		  count = 0; //reset counter after 3 images in row
                	      }
                	  if(count == 0){
                		  %>
                		  <div class = "submissionRow">
                		  <%
                	      }
                	  %>
                	  <div class = "submissionColumn">
                	  <a href= "DetailPageServlet?submissionID=<%=subID%>">
                	  <img src= "<%=image1 %>" alt="Submission" style="width:100%">
                	  </a>
                	  <div class ="content">
                	  
                	  <p style="color: white"><%=description1 %></p>
                	  
                	  </div>
                	  </div>
                	 <% 
                	 count++;
                	 if(count == 0){
               		  %>
               		  </div>
               		  <%
               	      }
                    }
                  }
                    catch(Exception e)
                        {
                        out.println(e.getMessage());
                        }
                    %>
        

           <%}
%>
           
                    
        </article>
    </body>
</html>
