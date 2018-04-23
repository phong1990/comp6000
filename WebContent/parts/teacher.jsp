<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>  MAGIC (Mentoring Alabama Girls in Computing)  </title>
<link rel="stylesheet" href="style2.css" type="text/css"/>


    <div class="left">
   <div id="heading">
     <img src="images.png" height="60px" width="80px" align="left" style="border-top:auto">
    
      <h1> MAGIC (Mentoring Alabama Girls in Computing) </h1>
    </div>
    
 <h2> 
 <% String userName=request.getParameter("username");
     out.println("Welcome "+userName);
  %>
     
</h2>
    
      <input type="submit" value="Logout" style="width: 70px;font-size: 16px">
     
 <div id="alignment">
    <form action="gridview.jsp">
     <input type="submit" value="Grid View" style="width: 120px;font-size: 16px">
        </form>
     </div>
      
     
     </div>
        
     </head>
    <body>
       
</body>
</html>
    
    
    
    