
<%
    java.sql.Connection conn;                 
    java.sql.ResultSet rs;                    
    java.sql.Statement st;                   
    
    Class.forName("org.postgresql.Driver");
	conn = java.sql.DriverManager.getConnection("jdbc:postgresql://localhost:5432/web_db","postgres","root");
    st = conn.createStatement();
    
	
    String username = request.getParameter("username");     
    String password = request.getParameter("password");

    String qr = "SELECT username FROM users WHERE username = '"+username+"' and password = '"+password+"'";   
    rs = st.executeQuery(qr);
    if(rs.next())                       
    {
        session.setAttribute("uname", username);    
        response.sendRedirect("home.jsp");
    }
    else
    {
        response.sendRedirect("login.jsp?c");
    }
%>