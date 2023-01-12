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
                GameModel myGame = (GameModel) request.getSession().getAttribute("game");
                out.print("YOU WIN !!!&#128512;&#128512;&#127867;&#127881;");
                out.print("<br />You have made " + myGame.getAttempts() + " attempts <br />");
               
            %>
        </span>
        <form id="form" action="controller" method="GET" >
            <input type="text" name="guess">
            <input type="submit" value="Guess">
        </form>
    </body>
</html>
