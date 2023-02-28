<%-- 
    Document   : chooseQuiz
    Created on : 18 Jan 2023, 16:56:36
    Author     : murtadha & Abdullah
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.naming.Context"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="java.util.List"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>chosseQuiz</title>     
        <h2>Choose the subject of the quiz</h2>
    </head>
  <body>
        <%
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/derby");
            Connection conn = ds.getConnection();
            Statement stmt = conn.createStatement();
            ArrayList<String[]> quizzes = new ArrayList<String[]>();
            ArrayList<String[]> scores = (ArrayList<String[]>) request.getSession().getAttribute("scores");
            ResultSet rs = stmt.executeQuery("SELECT * FROM quizzes");
            while (rs.next()) {
                String[] strArray = new String[2];
                strArray[0] = rs.getString("id");
                strArray[1] = rs.getString("subject");
                quizzes.add(strArray);
            }
            for (int i = 0; i < quizzes.size(); i++) 
            {
        %>
                <form id="form" action="DBServlet" method="GET" >
                    <input type="submit" name="quiz<%= i + 1%>" value="<%= quizzes.get(i)[1]%>">
                    <%
                     for (int j = 0; j < scores.size(); j++) {
                    %>
                        <% if (scores.get(j)[2].equals(quizzes.get(i)[0]) && 
                            scores.get(j)[1].equals(Integer.toString((Integer) 
                            request.getSession().getAttribute("userId")))) {
                               System.out.println(scores.get(i)[3]);%>
                        <% }
                    }
                    %>
                    <h6> Previous score: <%= scores.get(i)[3]%>/3</h6>
                </form>
            <%}%>
            
        <%
            String reload = request.getParameter("reload");
            if (reload != null && reload.equals("true")) 
        {%>
                <script>
                    location.reload();
                </script>
            <%}%>
    </body>


</html>
