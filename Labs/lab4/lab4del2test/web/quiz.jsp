<%-- 
    Document   : quiz
    Created on : 18 Jan 2023, 16:56:36
    Author     : murtadha & Abdullah
--%>


<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quiz</title>
    </head>
    <body>

        <%
            String quizName = (String) request.getSession().getAttribute("selectedQuiz");
            String quizId = Integer.toString((Integer) request.getSession().getAttribute("selectedQuizId"));
        %>

        <h1>Quiz:  <%= quizName%></h1>
        <form id="form" action="GradingServlet" method="GET" >
            <%

                ArrayList<String[]> questions = (ArrayList<String[]>) request.getSession().getAttribute("questions");
                ArrayList<String[]> selectors = (ArrayList<String[]>) request.getSession().getAttribute("selectors");
                ArrayList<String[]> selectedQuestions = new ArrayList<>();
                int counter = 0;

                for (int i = 0; i < questions.size(); i++) {
                    if (selectors.get(i)[0].equals(quizId)) {
                        selectedQuestions.add(questions.get(i));
                        String[] options = questions.get(i)[2].split("/", 3);
                        String[] answers = questions.get(i)[3].split("/", 3);
                        counter++;
            %>
            <a> <%= questions.get(i)[1]%></a>
            <br>
            <%
                for (int j = 0; j < options.length; j++) {
            %>
            <a><%= options[j]%></a>
            <input type="checkbox" name="question<%=questions.get(i)[0]%>_option<%=j%>" value="1">


            <%
                System.out.println("From quiz.jsp: question"+questions.get(i)[0]+"_option"+j);
                    }
                }
            %>
            <br>
            <%
                }

            %>


            <%  String latestScore = (String) request.getSession().getAttribute("score");
                request.getSession().setAttribute("selcetedQuestions", selectedQuestions);
            %>
            <input type="submit"  value="Submit">
        </form>
        <h4>Previous score: <%= latestScore%> / <%= counter%></h4> 
    </body>
</html>
