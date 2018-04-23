<%
    java.sql.Connection conn;                
    java.sql.ResultSet rs;                    
    java.sql.Statement st,st1;               
    
    Class.forName("org.postgresql.Driver");
	conn = java.sql.DriverManager.getConnection("jdbc:postgresql://localhost:5432/web_db","postgres","root");
    st = conn.createStatement();
    st1 = conn.createStatement();

   
    String firstname = request.getParameter("firstname");   
    String lastname = request.getParameter("lastname");     
    String username = request.getParameter("username");     
    String password = request.getParameter("password");

    String qr = "SELECT username FROM users WHERE username = '"+username+"'";   
    rs = st.executeQuery(qr);
    if(rs.next())                       
    {
        
        response.sendRedirect("register.jsp?a");                                    
    }
    else
    {
        String sql = "INSERT INTO users (firstname, lastname, username, password) values ('"+firstname+"', '"+lastname+"', '"+username+"', '"+password+"')";   //query to enter values in database
        st1.executeUpdate(sql);
        
        session.setAttribute("uname", username);    
        response.sendRedirect("home.jsp");
    }
%>