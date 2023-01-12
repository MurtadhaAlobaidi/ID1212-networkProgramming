<%-- 
    Document   : index
    Created on : 30 Dec 2022, 12:23:22
    Author     : murtadha
--%>

<%@page import="se.kth.lab44.GameModel"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html"; charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Number Guessing Game</title>
        <meta >
    </head>
    <body>
        <span id="header">
            <%
                out.print("Number Guessing Game<br/>");
                out.print("Guess a number between 1 and 100?");
            %>
        </span>
        <form id="form" action="controller" method="GET" >
            <input type="text" name="guess">
            <input type="submit" value="Guess">
        </form>
    </body>
</html>
